package org.bumo.sdk.core.adapter.bc;

import org.bumo.sdk.core.adapter.bc.request.SubTransactionRequest;
import org.bumo.sdk.core.adapter.bc.request.test.EvalTXReq;
import org.bumo.sdk.core.adapter.bc.response.Account;
import org.bumo.sdk.core.adapter.bc.response.Hello;
import org.bumo.sdk.core.adapter.bc.response.TransactionHistory;
import org.bumo.sdk.core.adapter.bc.response.converter.GetAccountMetadataResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.converter.GetAccountResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.converter.GetLedgerResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.converter.GetServiceResponse;
import org.bumo.sdk.core.adapter.bc.response.converter.GetTransactionHistoryResponse;
import org.bumo.sdk.core.adapter.bc.response.converter.HelloResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.converter.ServiceResponse;
import org.bumo.sdk.core.adapter.bc.response.converter.SubmitTranactionResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.converter.TestTXResponseConverter;
import org.bumo.sdk.core.adapter.bc.response.ledger.Ledger;
import org.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import org.bumo.sdk.core.adapter.bc.response.test.EvalTxResult;
import org.bumo.sdk.core.utils.http.HttpAction;
import org.bumo.sdk.core.utils.http.HttpMethod;
import org.bumo.sdk.core.utils.http.HttpService;
import org.bumo.sdk.core.utils.http.RequestBody;
import org.bumo.sdk.core.utils.http.RequestParam;

@HttpService
public interface RpcService{

    /**
     * 获取账号信息
     */
    @HttpAction(path = "/getAccount", method = HttpMethod.GET, responseConverter = GetAccountResponseConverter.class)
    Account getAccount(@RequestParam(name = "address") String address);

    /**
     * 获取底层基本信息
     */
    @HttpAction(path = "/hello", method = HttpMethod.GET, responseConverter = HelloResponseConverter.class)
    Hello hello();

    /**
     * 获取账号信息
     */
    @HttpAction(path = "/getAccount", method = HttpMethod.GET, responseConverter = GetAccountMetadataResponseConverter.class)
    SetMetadata getAccountMetadata(@RequestParam(name = "address") String address, @RequestParam(name = "key") String key);

    /**
     * 获取账号信息
     */
    @HttpAction(path = "/getLedger", method = HttpMethod.GET, responseConverter = GetLedgerResponseConverter.class)
    Ledger getLedger();

    /**
     * 获取账号信息
     */
    @HttpAction(path = "/getLedger", method = HttpMethod.GET, responseConverter = GetLedgerResponseConverter.class)
    Ledger getLedgerBySeq(@RequestParam(name = "seq") long seq);

    /**
     * 提交
     */
    @HttpAction(path = "/submitTransaction", method = HttpMethod.POST, responseConverter = SubmitTranactionResponseConverter.class)
    String submitTransaction(@RequestBody SubTransactionRequest request);
    
    /**
     * 
     * @param addrs
     * @return
     */
    @HttpAction(path = "/testTransaction", method = HttpMethod.POST, responseConverter = TestTXResponseConverter.class)
    EvalTxResult testTransaction(@RequestBody EvalTXReq request);

    /**
     * 根据地址获取交易历史信息
     */
    @HttpAction(path = "/getTransactionHistory", method = HttpMethod.GET, responseConverter = GetTransactionHistoryResponse.class)
    TransactionHistory getTransactionHistoryByAddress(@RequestParam(name = "address") String address);

    /**
     * 根据区块序列号获取交易历史信息
     */
    @HttpAction(path = "/getTransactionHistory", method = HttpMethod.GET, responseConverter = GetTransactionHistoryResponse.class)
    TransactionHistory getTransactionHistoryBySeq(@RequestParam(name = "ledger_seq", required = false) Long seq, @RequestParam(name = "start") int start, @RequestParam(name = "limit") int limit);

    /**
     * 根据区块序列号获取交易历史信息
     */
    @HttpAction(path = "/getTransactionHistory", method = HttpMethod.GET, responseConverter = GetTransactionHistoryResponse.class)
    TransactionHistory getTransactionHistoryByLedgerSeq(@RequestParam(name = "ledger_seq") Long ledgerSeq);
    
    /**
     * 根据hash获取交易信息
     */
    @HttpAction(path = "/getTransactionHistory", method = HttpMethod.GET, responseConverter = GetTransactionHistoryResponse.class)
    TransactionHistory getTransactionHistoryByHash(@RequestParam(name = "hash") String hash);

    /**
     * 根据hash获取交易信息
     */
    @HttpAction(path = "/getTransactionHistory", method = HttpMethod.GET, responseConverter = GetServiceResponse.class)
    ServiceResponse getTransactionResultByHash(@RequestParam(name = "hash") String hash);
    

}

