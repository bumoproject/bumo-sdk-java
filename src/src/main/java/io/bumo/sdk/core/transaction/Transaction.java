package io.bumo.sdk.core.transaction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;

import io.bumo.encryption.key.PublicKey;
import io.bumo.sdk.core.adapter.bc.RpcService;
import io.bumo.sdk.core.adapter.bc.request.SignatureRequest;
import io.bumo.sdk.core.adapter.bc.request.SubTransactionRequest;
import io.bumo.sdk.core.adapter.bc.request.TransactionRequest;
import io.bumo.sdk.core.adapter.exception.BlockchainException;
import io.bumo.sdk.core.balance.NodeManager;
import io.bumo.sdk.core.event.bottom.TxFailManager;
import io.bumo.sdk.core.exception.SdkError;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.extend.protobuf.Chain;
import io.bumo.sdk.core.operation.BcOperation;
import io.bumo.sdk.core.operation.BuildConsume;
import io.bumo.sdk.core.pool.SponsorAccount;
import io.bumo.sdk.core.seq.SequenceManager;
import io.bumo.sdk.core.transaction.model.Digest;
import io.bumo.sdk.core.transaction.model.Signature;
import io.bumo.sdk.core.transaction.model.TransactionBlob;
import io.bumo.sdk.core.transaction.model.TransactionCommittedResult;
import io.bumo.sdk.core.transaction.model.TransactionSerializable;
import io.bumo.sdk.core.transaction.sync.AsyncFutureTx;
import io.bumo.sdk.core.transaction.sync.TransactionSyncManager;
import io.bumo.sdk.core.utils.Assert;
import io.bumo.sdk.core.utils.SwallowUtil;
import io.bumo.sdk.core.utils.http.HttpServiceException;
import io.bumo.sdk.core.utils.io.ByteBlob;
import io.bumo.sdk.core.utils.security.ShaUtils;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 * The execution of a transaction does not represent a transaction
 * Threads are not secure. Multiple threads do not share a transaction operation
 */
public class Transaction{

    private static Logger logger = LoggerFactory.getLogger(Transaction.class);

    public static final int LOW_FINAL_NOTIFY_SEQ_OFFSET = 10;
    public static final int MID_FINAL_NOTIFY_SEQ_OFFSET = 20;
    public static final int HIGHT_FINAL_NOTIFY_SEQ_OFFSET = 30;

    private String sponsorAddress; // sponsor
    private long nonce; // to get it when getting the blob
    // The caller can set the SEQ offset itself, and the time corresponding to a block offset is 3 seconds or a minute, suggesting interval [2,30]
    private int finalNotifySeqOffset = MID_FINAL_NOTIFY_SEQ_OFFSET;
    private List<BcOperation> operationList = new ArrayList<>(); // Operation list
    private List<Signature> signatures = new ArrayList<>(); // Signature list
    private List<Digest> digests = new ArrayList<>(); // Signature summary list
    private TransactionBlob transactionBlob; // blob

    private String txMetadata; // Transaction metadata

    private final SequenceManager sequenceManager;
    private final RpcService rpcService;
    private final TransactionSyncManager transactionSyncManager;
    private final NodeManager nodeManager;
    private final TxFailManager txFailManager;

    private boolean complete = false;
    
    private long feeLimit;
    private long gasPrice;
    private long ceilLedgerSeq;


	/**
     * Normal sponsor
     */
    public Transaction(String sponsorAddress, SequenceManager sequenceManager, RpcService rpcService, TransactionSyncManager transactionSyncManager, NodeManager nodeManager, TxFailManager txFailManager){
        this.sponsorAddress = sponsorAddress;
        this.sequenceManager = sequenceManager;
        this.rpcService = rpcService;
        this.transactionSyncManager = transactionSyncManager;
        this.nodeManager = nodeManager;
        this.txFailManager = txFailManager;
    }

    /**
     * Initiating through a pool of accounts
     */
    public Transaction(SponsorAccount sponsorAccount, SequenceManager sequenceManager, RpcService rpcService, TransactionSyncManager transactionSyncManager, NodeManager nodeManager, TxFailManager txFailManager){
        this(sponsorAccount.getAddress(), sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager);
        this.signatures.add(new Signature(sponsorAccount.getPublicKey(), sponsorAccount.getPrivateKey()));
    }

    /**
     * Continue Transaction from blob
     */
    public Transaction(TransactionSerializable transactionSerializable, SequenceManager sequenceManager, RpcService rpcService, TransactionSyncManager transactionSyncManager, NodeManager nodeManager, TxFailManager txFailManager){
        this("", sequenceManager, rpcService, transactionSyncManager, nodeManager, txFailManager);
        this.transactionBlob = transactionSerializable.getTransactionBlob();
        if (transactionSerializable.getSignatures() != null && !transactionSerializable.getSignatures().isEmpty())
            this.signatures = transactionSerializable.getSignatures();

        this.feeLimit = transactionSerializable.getFeeLimit();
        this.gasPrice = transactionSerializable.getGasPrice();
    }

    public Transaction buildAddSigner(String publicKey, String privateKey) throws SdkException{
        return buildTemplate(() -> this.signatures.add(new Signature(publicKey, privateKey)));
    }
    /***
     * TODO
     * @param fee
     * @return
     * @throws SdkException
     */
    public Transaction buildAddFeeLimit(long feeLimit) throws SdkException{
        return buildTemplate(() -> this.feeLimit = feeLimit);
    }

    public Transaction buildAddGasPrice(long gasPrice) throws SdkException{
        return buildTemplate(() -> this.gasPrice = gasPrice);
    }

    public Transaction buildAddCeilLedgerSeq(long ceilLedgerSeq) throws SdkException{
        return buildTemplate(() -> this.ceilLedgerSeq = ceilLedgerSeq);
    }
    public Transaction buildAddDigest(String publicKey, byte[] originDigest) throws SdkException{
        return buildTemplate(() -> digests.add(new Digest(publicKey, originDigest)));
    }

    public Transaction buildFinalNotifySeqOffset(int offset) throws SdkException{
        return buildTemplate(() -> finalNotifySeqOffset = offset);
    }

    public Transaction buildAddOperation(BcOperation operation) throws SdkException{
        return buildTemplate(() -> {
            if (operation != null) {
                operationList.add(operation);
            }
        });
    }

    public Transaction buildTxMetadata(String txMetadata) throws SdkException{
        return buildTemplate(() -> this.txMetadata = txMetadata);
    }

    private Transaction buildTemplate(BuildConsume buildConsume) throws SdkException{
        checkCanExecute();
        buildConsume.build();
        return this;
    }

    private void checkCanExecute() throws SdkException{
        Assert.notTrue(complete, SdkError.TRANSACTION_ERROR_STATUS);
    }

    /**
     * A fast access method with only one signature
     */
    public TransactionCommittedResult commit(String publicKey, String privateKey) throws SdkException{
        buildAddSigner(publicKey, privateKey);
        return commit();
    }

    public TransactionCommittedResult commit() throws SdkException{
        return commit(true);
    }

    public TransactionCommittedResult commit(boolean sync) throws SdkException{
        if (transactionBlob == null) {
            generateBlob();
        }
        return submit(sync);
    }

    /**
     * The initiator SEQ can also be reset by trading
     */
    public void resetSponsorAddress(){
        sequenceManager.reset(sponsorAddress);
    }

    /**
     * For blob, we need to get blob from front-end signature
     */
    public TransactionBlob generateBlob() throws SdkException{
        checkGeneratorBlobStatus();
        nonce = sequenceManager.getSequenceNumber(sponsorAddress);
        transactionBlob = generateTransactionBlob();
        return transactionBlob;
    }
    // TODO
    public TransactionSerializable forSerializable(){
        return new TransactionSerializable(transactionBlob, signatures, feeLimit, gasPrice);
    }

    public TransactionBlob getTransactionBlob() throws SdkException{
        Assert.notTrue(transactionBlob == null, SdkError.TRANSACTION_ERROR_BLOB_NOT_NULL);
        return transactionBlob;
    }

    private TransactionCommittedResult submit(boolean sync) throws SdkException{
        complete();
        checkCommitStatus();
        TransactionCommittedResult committedResult = new TransactionCommittedResult();
        String txHash = transactionBlob.getHash();
        logger.debug("提交交易txHash=" + txHash);
        AsyncFutureTx txFuture = new AsyncFutureTx(txHash);
        try {
            transactionSyncManager.addAsyncFutureTx(txFuture);

            try {
                SubTransactionRequest subTransactionRequest = getSubTransactionRequest();
                verifyPre(subTransactionRequest);
                rpcService.submitTransaction(subTransactionRequest);
            } catch (RuntimeException e) {
                // Synchronous reset seq
                sequenceManager.reset(sponsorAddress);
                if (e instanceof HttpServiceException && e.getCause() instanceof BlockchainException) {
                    throw new SdkException((BlockchainException) e.getCause());
                }
                throw e;
            }

            if (sync) {

                txFuture.await();

                if (!success(txFuture.getErrorCode())) {
                    throw new SdkException(Integer.valueOf(txFuture.getErrorCode()), txFuture.getErrorMessage());
                }

            }

            committedResult.setHash(txHash);
        } catch (Exception e) {
            try {
                throw e;
            } catch (InterruptedException e1) {
                logger.error("submit transaction found InterruptedException:", e1);
            }
        } finally {
            transactionSyncManager.remove(txFuture);
        }

        return committedResult;

    }

    private boolean success(String errorCode){
        String success = "0";
        return errorCode == null || success.equals(errorCode);
    }


    private TransactionBlob generateTransactionBlob() throws SdkException{
    	
        Chain.Transaction.Builder builder = Chain.Transaction.newBuilder();
        if (txMetadata != null) {
            builder.setMetadata(ByteString.copyFromUtf8(Hex.encodeHexString(txMetadata.getBytes())));
        }
        builder.setSourceAddress(sponsorAddress);
        builder.setNonce(nonce);
        
        builder.setFeeLimit(feeLimit);
        builder.setGasPrice(gasPrice);
        if(ceilLedgerSeq !=0 ){
            buildAddCeilLedgerSeq(ceilLedgerSeq);
        }

        long specifiedSeq = nodeManager.getLastSeq() + finalNotifySeqOffset;
        logger.debug("specified seq:" + specifiedSeq);

        for (BcOperation bcOperation : operationList) {
            bcOperation.buildTransaction(builder, specifiedSeq);
        }

        Chain.Transaction transaction = builder.build();
        logger.debug("transaction:" + transaction);
        byte[] bytesBlob = transaction.toByteArray();
        
        TransactionBlob transactionBlob = new TransactionBlob(bytesBlob, nodeManager.getCurrentSupportHashType());

        // Setting up the longest waiting time notice
        txFailManager.finalNotifyFailEvent(specifiedSeq, transactionBlob.getHash(), SdkError.TRANSACTION_ERROR_TIMEOUT);

        return transactionBlob;
    }

    private SubTransactionRequest getSubTransactionRequest() throws SdkException{
        TransactionRequest tranRequest = new TransactionRequest();
        tranRequest.setSignatures(getSignatures(transactionBlob.getBytes()));
        tranRequest.setTransactionBlob(transactionBlob.getHex());
        TransactionRequest[] transactionRequests = new TransactionRequest[1];
        transactionRequests[0] = tranRequest;
        SubTransactionRequest subTranRequest = new SubTransactionRequest();
        subTranRequest.setItems(transactionRequests);
        return subTranRequest;
    }

    private SignatureRequest[] getSignatures(ByteBlob byteBlob) throws SdkException{
        List<SignatureRequest> signatureRequests = new ArrayList<>();
        for (Signature signature : signatures) {
            SignatureRequest signatureRequest = new SignatureRequest();
            signatureRequest.setPublicKey(signature.getPublicKey());
            SwallowUtil.swallowException(() -> {
                signatureRequest.setSignData(Hex.encodeHexString(ShaUtils.signV3(byteBlob.toBytes(), signature.getPrivateKey(), signature.getPublicKey())));// 将签名信息转换为16进制
            }, SdkError.SIGNATURE_ERROR_PUBLIC_PRIVATE);
            signatureRequests.add(signatureRequest);
        }
        digests.forEach(digest -> {
            SignatureRequest signatureRequest = new SignatureRequest();
            signatureRequest.setPublicKey(digest.getPublicKey());
            signatureRequest.setSignData(Hex.encodeHexString(digest.getOriginDigest())); // Convert the signature information to 16
            signatureRequests.add(signatureRequest);
        });
        return signatureRequests.toArray(new SignatureRequest[signatureRequests.size()]);
    }

    private void verifyPre(SubTransactionRequest subTransactionRequest) throws SdkException{
        TransactionRequest transactionRequest = subTransactionRequest.getItems()[0];
        for (SignatureRequest signatureRequest : transactionRequest.getSignatures()) {
            SwallowUtil.swallowException(() -> {
                boolean thisSignatureResult = PublicKey.verify(transactionBlob.getBytes().toBytes(), Hex.decodeHex(signatureRequest.getSignData().toCharArray()), signatureRequest.getPublicKey());
                Assert.isTrue(thisSignatureResult, SdkError.EVENT_ERROR_SIGNATURE_VERIFY_FAIL);
            }, SdkError.EVENT_ERROR_SIGNATURE_VERIFY_FAIL);
        }
    }

    private void complete() throws SdkException{
        Assert.notTrue(complete, SdkError.TRANSACTION_ERROR_STATUS);
        complete = true;
    }

    private void checkGeneratorBlobStatus() throws SdkException{
        Assert.notEmpty(sponsorAddress, SdkError.TRANSACTION_ERROR_SPONSOR);
        Assert.isNull(transactionBlob, SdkError.TRANSACTION_ERROR_BLOB_REPEAT_GENERATOR);
        Assert.notTrue(operationList.isEmpty(), SdkError.TRANSACTION_ERROR_OPERATOR_NOT_EMPTY);
        Assert.notTrue(complete, SdkError.TRANSACTION_ERROR_STATUS);
    }

    private void checkCommitStatus() throws SdkException{
        Assert.notTrue(signatures.isEmpty() && digests.isEmpty(), SdkError.TRANSACTION_ERROR_SIGNATURE);
        Assert.checkCollection(signatures, signature -> Assert.notEmpty(signature.getPublicKey(), SdkError.TRANSACTION_ERROR_PUBLIC_KEY_NOT_EMPTY));
        Assert.checkCollection(signatures, signature -> Assert.notEmpty(signature.getPrivateKey(), SdkError.TRANSACTION_ERROR_PRIVATE_KEY_NOT_EMPTY));
        Assert.notTrue(transactionBlob == null, SdkError.TRANSACTION_ERROR_BLOB_NOT_NULL);
        Assert.isTrue(complete, SdkError.TRANSACTION_ERROR_STATUS);
        Assert.notTrue(feeLimit <= 0, SdkError.TRANSACTION_ERROR_FEE_ILLEGAL);
        Assert.notTrue(gasPrice <= 0, SdkError.TRANSACTION_ERROR_GAS_ILLEGAL);
    }
}
