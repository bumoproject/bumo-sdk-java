package org.bumo.sdk.core.event.bottom;

import org.bumo.encryption.utils.hex.HexFormat;
import org.bumo.sdk.core.event.EventBusService;
import org.bumo.sdk.core.event.message.LedgerSeqEventMessage;
import org.bumo.sdk.core.event.message.TransactionExecutedEventMessage;
import org.bumo.sdk.core.event.source.EventSourceEnum;
import org.bumo.sdk.core.exception.SdkError;
import org.bumo.sdk.core.exception.SdkException;
import org.bumo.sdk.core.extend.protobuf.Chain;
import org.bumo.sdk.core.extend.protobuf.Overlay;
import org.bumo.sdk.core.extend.websocket.BlockChainAdapter;
import org.bumo.sdk.core.utils.spring.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The underlying MQ message processor
 */
public class BlockchainMqHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockchainMqHandler.class);
    private static final Pattern URI_PATTERN = Pattern.compile("(ws://)(.*)(:[\\d+])");

    private BlockChainAdapter mQBlockChainExecute;
    private TxMqHandleProcess mqHandleProcess;
    private EventBusService eventBusService;
    private String eventUri;
    private String host;


    public BlockchainMqHandler(String eventUri, TxMqHandleProcess mqHandleProcess, EventBusService eventBusService) throws SdkException{
        this.eventUri = eventUri;
        this.host = getHostByUri(eventUri);
        this.mqHandleProcess = mqHandleProcess;
        this.eventBusService = eventBusService;
    }

    private static String getHostByUri(String eventUri) throws SdkException{
        try {
            Matcher matcher = URI_PATTERN.matcher(eventUri);
            if (!matcher.find()) {
                throw new SdkException(SdkError.PARSE_URI_ERROR);
            }
            return matcher.group(2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SdkException(SdkError.PARSE_URI_ERROR);
        }
    }

    public void init(){
        // initiate link
        BlockChainAdapter mQBlockChainExecute = new BlockChainAdapter(eventUri);

        // Receive handshake message
        mQBlockChainExecute.AddChainResponseMethod(Overlay.ChainMessageType.CHAIN_HELLO_VALUE, this :: onHelloCallback);
        
        // Receive subscription message
        mQBlockChainExecute.AddChainResponseMethod(Overlay.ChainMessageType.CHAIN_SUBSCRIBE_TX_VALUE, this :: subscribeTx);

        // Consensus post callback
        mQBlockChainExecute.AddChainMethod(Overlay.ChainMessageType.CHAIN_TX_ENV_STORE_VALUE, this :: afterConsensusCallback);

        // Block seq
        mQBlockChainExecute.AddChainMethod(Overlay.ChainMessageType.CHAIN_LEDGER_HEADER_VALUE, this :: ledgerSeqCallback);

        Thread thread = new Thread(() -> {
            long future = LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            long wait = TimeUnit.MINUTES.toMillis(1);
            boolean helloSuccess = false;
            while (wait > 0) {
                if (!mQBlockChainExecute.IsConnected()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        //
                        LOGGER.error("", e);
                        Thread.currentThread().interrupt();
                    }
                    wait = future - System.currentTimeMillis();
                } else {
                    Overlay.ChainHello.Builder chain_hello = Overlay.ChainHello.newBuilder();
                    chain_hello.setTimestamp(new Date().getTime());
                    mQBlockChainExecute.Send(Overlay.ChainMessageType.CHAIN_HELLO_VALUE, chain_hello.build().toByteArray());
                    helloSuccess = true;
                    break;
                }
            }
            LOGGER.debug("hello success : " + helloSuccess);
        }, "hello-" + this.host);
        thread.start();


        this.mQBlockChainExecute = mQBlockChainExecute;
    }

    public void destroy(){
        if (mQBlockChainExecute != null) {
            mQBlockChainExecute.Stop();
        }
    }

    @SuppressWarnings("unused")
	private void afterConsensusCallback(byte[] msg, int length) {
    	try {
    		Chain.TransactionEnvStore tranEnvStore = Chain.TransactionEnvStore.parseFrom(msg);
    		
    		int errCode = tranEnvStore.getErrorCode();
    		String txHash = HexFormat.byteToHex(tranEnvStore.getHash().toByteArray()).toLowerCase();
    		
    		TransactionExecutedEventMessage message = new TransactionExecutedEventMessage();
            message.setHash(HexFormat.byteToHex(tranEnvStore.getHash().toByteArray()).toLowerCase());
            message.setErrorCode(String.valueOf(tranEnvStore.getErrorCode()));
            message.setSponsorAddress(tranEnvStore.getTransactionEnv().getTransaction().getSourceAddress());
            message.setSequenceNumber(tranEnvStore.getTransactionEnv().getTransaction().getNonce());
    		if(errCode == 0) {
    			message.setSuccess(true);
    		} else {
    			message.setSuccess(false);
    			message.setErrorMessage(tranEnvStore.getErrorDesc());
    		}
    		
    		if (StringUtils.isEmpty(message.getSponsorAddress())) {
                LOGGER.error("received empty source address. TransactionExecutedEventMessage : " + message);
                return;
            }
    		
    		if (message.getSuccess() != null)
    			mqHandleProcess.process(message);
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    /**
     * Receive SEQ increase
     */
    private void ledgerSeqCallback(byte[] msg, int length){
        try {
            Chain.LedgerHeader ledger_header = Chain.LedgerHeader.parseFrom(msg);

            LedgerSeqEventMessage seqEventMessage = new LedgerSeqEventMessage();
            seqEventMessage.setHost(host);
            seqEventMessage.setSeq(ledger_header.getSeq());

            eventBusService.publishEvent(EventSourceEnum.LEDGER_SEQ_INCREASE.getEventSource(), seqEventMessage);

        } catch (Exception e) {
            LOGGER.error("Receiving SEQ to add exceptions", e);
        }
    }
    
    private void subscribeTx(byte[] msg, int length){
    	try {
    		Overlay.ChainResponse chainResponse = Overlay.ChainResponse.parseFrom(msg);
    		LOGGER.trace("subscribeTx errorCode: " + chainResponse.getErrorCode() + ", desc: " + chainResponse.getErrorDesc());
    		
    	 } catch (Exception e) {
             LOGGER.error("Receiving SEQ to add exceptions", e);
         }
    }


    /**
     * Receive handshake message
     */
    private void onHelloCallback(byte[] msg, int length){
        LOGGER.debug("!!!!!receive hello successful , to host:" + host);
    }

}
