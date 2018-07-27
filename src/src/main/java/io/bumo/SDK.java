package io.bumo;

import io.bumo.account.AccountService;
import io.bumo.account.impl.AccountServiceImpl;
import io.bumo.asset.AssetService;
import io.bumo.asset.TokenService;
import io.bumo.asset.impl.AssetServiceImpl;
import io.bumo.asset.impl.TokenServiceImpl;
import io.bumo.blockchain.BlockService;
import io.bumo.blockchain.TransactionService;
import io.bumo.blockchain.impl.BlockServiceImpl;
import io.bumo.blockchain.impl.TransactionServiceImpl;
import io.bumo.common.General;
import io.bumo.contract.ContractService;
import io.bumo.contract.impl.ContractServiceImpl;
import io.bumo.exception.SDKException;
import io.bumo.exception.SdkError;

/**
 * @Author riven
 * @Date 2018/7/4 12:23
 */
public class SDK {
    private static SDK sdk = null;

    /**
     * @Author riven
     * @Method Structure
     * @Params [url]
     * @Date 2018/7/15 14:50
     */
    private SDK(String url) {
        General.url = url;
    }

    /**
     * @Author riven
     * @Method getInstance
     * @Params [url]
     * @Return io.bumo.SDK
     * @Date 2018/7/15 14:51
     */
    public synchronized static SDK getInstance(String url) throws SDKException {
        if (sdk == null) {
            sdk = new SDK(url);
        }
        sdk.init(url);
        return sdk;
    }

    /**
     * @Author riven
     * @Method getAccountService
     * @Params []
     * @Return io.bumo.account.AccountService
     * @Date 2018/7/15 14:50
     */
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }

    /**
     * @Author riven
     * @Method getAssetService
     * @Params []
     * @Return io.bumo.asset.AssetService
     * @Date 2018/7/15 14:50
     */
    public AssetService getAssetService() {
        return new AssetServiceImpl();
    }

    /**
     * @Author riven
     * @Method getTransactionService
     * @Params []
     * @Return io.bumo.blockchain.TransactionService
     * @Date 2018/7/15 14:50
     */
    public TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }

    /**
     * @Author riven
     * @Method getBlockService
     * @Params []
     * @Return io.bumo.blockchain.BlockService
     * @Date 2018/7/15 14:50
     */
    public BlockService getBlockService() {
        return new BlockServiceImpl();
    }

    /**
     * @Author riven
     * @Method getContractService
     * @Params []
     * @Return io.bumo.contract.ContractService
     * @Date 2018/7/15 14:50
     */
    public ContractService getContractService() {
        return new ContractServiceImpl();
    }

    /**
     * @Author riven
     * @Method getTokenService
     * @Params []
     * @Return io.bumo.asset.TokenService
     * @Date 2018/7/15 14:50
     */
    public TokenService getTokenService() {
        return new TokenServiceImpl();
    }

    /**
     * @Author riven
     * @Method init
     * @Params [url]
     * @Return void
     * @Date 2018/7/15 14:50
     */
    private void init(String url) throws SDKException {
        if (url == null || url.isEmpty()) {
            throw new SDKException(SdkError.URL_EMPTY_ERROR);
        }
    }
}
