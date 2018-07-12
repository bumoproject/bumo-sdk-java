package io.bumo.contract;

import io.bumo.model.request.*;
import io.bumo.model.response.*;

/**
 * @Author riven
 * @Date 2018/7/5 13:20
 */
public interface ContractService {
    /**
     * @Author riven
     * @Method getInfo
     * @Params [contractGetRequest]
     * @Return io.bumo.model.response.ContractGetInfoResponse
     * @Date 2018/7/5 14:16
     */
    public ContractGetInfoResponse getInfo(ContractGetInfoRequest contractGetRequest);

    /**
     * @Author riven
     * @Method checkValid
     * @Params [contractCheckValidRequest]
     * @Return io.bumo.model.response.ContractCheckValidResponse
     * @Date 2018/7/5 15:37
     */
    public ContractCheckValidResponse checkValid(ContractCheckValidRequest contractCheckValidRequest);

    /**
     * @Author riven
     * @Method call
     * @Params [contractCallRequest]
     * @Return io.bumo.model.response.ContractCallResponse
     * @Date 2018/7/11 18:50
     */
    public ContractCallResponse call(ContractCallRequest contractCallRequest);
}
