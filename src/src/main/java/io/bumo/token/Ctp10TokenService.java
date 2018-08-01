package io.bumo.token;

import io.bumo.model.request.*;
import io.bumo.model.response.*;

/**
 * @Author riven
 * @Date 2018/7/6 11:08
 */

public interface Ctp10TokenService {
    /**
     * @Author riven
     * @Method checkValid
     * @Params [tokenCheckValidRequest]
     * @Return io.bumo.model.response.TokenCheckValidResponse
     * @Date 2018/7/15 15:36
     */
    public Ctp10TokenCheckValidResponse checkValid(Ctp10TokenCheckValidRequest ctp10TokenCheckValidRequest);

    /**
     * @Author riven
     * @Method allowance
     * @Params [tokenAllowanceRequest]
     * @Return io.bumo.model.response.TokenAllowanceResponse
     * @Date 2018/7/6 16:29
     */
    public Ctp10TokenAllowanceResponse allowance(Ctp10TokenAllowanceRequest ctp10TokenAllowanceRequest);

    /**
     * @Author riven
     * @Method getInfo
     * @Params [tokenGetInfoRequest]
     * @Return io.bumo.model.response.TokenGetInfoResponse
     * @Date 2018/7/6 16:30
     */
    public Ctp10TokenGetInfoResponse getInfo(Ctp10TokenGetInfoRequest ctp10TokenGetInfoRequest);

    /**
     * @Author riven
     * @Method getName
     * @Params [tokenGetNameRequest]
     * @Return io.bumo.model.response.TokenGetNameResponse
     * @Date 2018/7/6 16:30
     */
    public Ctp10TokenGetNameResponse getName(Ctp10TokenGetNameRequest ctp10TokenGetNameRequest);

    /**
     * @Author riven
     * @Method getSymbol
     * @Params [tokenGetSymbolRequest]
     * @Return io.bumo.model.response.TokenGetSymbolResponse
     * @Date 2018/7/6 16:34
     */
    public Ctp10TokenGetSymbolResponse getSymbol(Ctp10TokenGetSymbolRequest ctp10TokenGetSymbolRequest);

    /**
     * @Author riven
     * @Method getDecimals
     * @Params [tokenGetDecimalsRequest]
     * @Return io.bumo.model.response.TokenGetDecimalsResponse
     * @Date 2018/7/11 23:07
     */
    public Ctp10TokenGetDecimalsResponse getDecimals(Ctp10TokenGetDecimalsRequest ctp10TokenGetDecimalsRequest);

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params [tokenGetTotalSupplyRequest]
     * @Return io.bumo.model.response.TokenGetTotalSupplyResponse
     * @Date 2018/7/6 16:46
     */
    public Ctp10TokenGetTotalSupplyResponse getTotalSupply(Ctp10TokenGetTotalSupplyRequest ctp10TokenGetTotalSupplyRequest);

    /**
     * @Author riven
     * @Method getBalance
     * @Params [tokenGetBalanceRequest]
     * @Return io.bumo.model.response.TokenGetBalanceResponse
     * @Date 2018/7/6 16:46
     */
    public Ctp10TokenGetBalanceResponse getBalance(Ctp10TokenGetBalanceRequest ctp10TokenGetBalanceRequest);
}
