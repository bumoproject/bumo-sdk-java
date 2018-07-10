package io.bumo;

import com.alibaba.fastjson.JSONObject;
import io.bumo.account.AccountService;
import io.bumo.account.impl.AccountServiceImpl;
import io.bumo.asset.AssetService;
import io.bumo.asset.impl.AssetServiceImpl;
import io.bumo.blockchain.BlockService;
import io.bumo.blockchain.TransactionService;
import io.bumo.blockchain.impl.BlockServiceImpl;
import io.bumo.blockchain.impl.TransactionServiceImpl;
import io.bumo.common.General;
import io.bumo.contract.ContractService;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.crypto.http.HttpKit;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 12:23
 */
public class SDK {
    public static SDK sdk = null;
    private SDK(String url) {
        General.url = url;
    }
    public synchronized static SDK getInstance(String url) throws SDKException {
        if (sdk == null) {
            sdk = new SDK(url);
        }
        //sdk.init(url);
        return sdk;
    }
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }
    public AssetService getAssetService() {
        return new AssetServiceImpl();
    }
    public TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }
    public BlockService getBlockService() {
        return new BlockServiceImpl();
    }
    public ContractService getContractService() {
        return new ContractServiceImpl();
    }


    private void init(String url) throws SDKException {
        if (url == null || url.isEmpty()) {
            throw new SDKException(SdkError.URL_EMPTY_ERROR);
        }
        hello(url);
    }

    private void hello(String url) throws SDKException {
        try {
            String helloUrl = url + "/hello";
            String result = HttpKit.get(helloUrl);
            if (result == null || result.isEmpty()) {
                throw new SDKException(SdkError.CONNECTN_BLOCKCHAIN_ERROR);
            }
            JSONObject resultJson = JSONObject.parseObject(result);
            if (resultJson == null || resultJson.isEmpty()) {
                throw new SDKException(SdkError.CONNECTN_BLOCKCHAIN_ERROR);
            }
        } catch (Exception e) {
            throw new SDKException(SdkError.CONNECTN_BLOCKCHAIN_ERROR);
        }
    }
}
