package io.bumo.asset;

import io.bumo.model.request.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.TokenCheckValidResult;

/**
 * @Author riven
 * @Date 2018/7/6 11:08
 */

public interface TokenService {
    /**
     * @Author riven
     * @Method checkValid
     * @Params [tokenCheckValidRequest]
     * @Return io.bumo.model.response.TokenCheckValidResponse
     * @Date 2018/7/15 15:36
     */
    public TokenCheckValidResponse checkValid(TokenCheckValidRequest tokenCheckValidRequest);
    /**
     * @Author riven
     * @Method allowance
     * @Params [tokenAllowanceRequest]
     * @Return io.bumo.model.response.TokenAllowanceResponse
     * @Date 2018/7/6 16:29
     */
    public TokenAllowanceResponse allowance(TokenAllowanceRequest tokenAllowanceRequest);
    
    /**
     * @Author riven
     * @Method getInfo
     * @Params [tokenGetInfoRequest]
     * @Return io.bumo.model.response.TokenGetInfoResponse
     * @Date 2018/7/6 16:30
     */
    public TokenGetInfoResponse getInfo(TokenGetInfoRequest tokenGetInfoRequest);
    
    /**
     * @Author riven
     * @Method getName
     * @Params [tokenGetNameRequest]
     * @Return io.bumo.model.response.TokenGetNameResponse
     * @Date 2018/7/6 16:30
     */
    public TokenGetNameResponse getName(TokenGetNameRequest tokenGetNameRequest);

    /**
     * @Author riven
     * @Method getSymbol
     * @Params [tokenGetSymbolRequest]
     * @Return io.bumo.model.response.TokenGetSymbolResponse
     * @Date 2018/7/6 16:34
     */
    public TokenGetSymbolResponse getSymbol(TokenGetSymbolRequest tokenGetSymbolRequest);

    /**
     * @Author riven
     * @Method getDecimals
     * @Params [tokenGetDecimalsRequest]
     * @Return io.bumo.model.response.TokenGetDecimalsResponse
     * @Date 2018/7/11 23:07
     */
    public TokenGetDecimalsResponse getDecimals(TokenGetDecimalsRequest tokenGetDecimalsRequest);

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params [tokenGetTotalSupplyRequest]
     * @Return io.bumo.model.response.TokenGetTotalSupplyResponse
     * @Date 2018/7/6 16:46
     */
    public TokenGetTotalSupplyResponse getTotalSupply(TokenGetTotalSupplyRequest tokenGetTotalSupplyRequest);

    /**
     * @Author riven
     * @Method getBalance
     * @Params [tokenGetBalanceRequest]
     * @Return io.bumo.model.response.TokenGetBalanceResponse
     * @Date 2018/7/6 16:46
     */
    public TokenGetBalanceResponse getBalance(TokenGetBalanceRequest tokenGetBalanceRequest);
}
