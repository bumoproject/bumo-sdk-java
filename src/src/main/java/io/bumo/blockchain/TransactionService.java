package io.bumo.blockchain;

import io.bumo.model.request.*;
import io.bumo.model.response.*;
import io.bumo.model.response.result.TransactionParseBlobResult;

/**
 * @Author riven
 * @Date 2018/7/3 17:22
 */
public interface TransactionService {
    /**
     * @Author riven
     * @Method buildBlob
     * @Params [transactionBuildBlobRequest]
     * @Return io.bumo.model.response.TransactionBuildBlobResponse
     * @Date 2018/7/5 16:57
     */
    public TransactionBuildBlobResponse buildBlob(TransactionBuildBlobRequest transactionBuildBlobRequest);

    /**
     * @Author riven
     * @Method parseBlob
     * @Params [transactionParseBlobRequest]
     * @Return io.bumo.model.response.TransactionParseBlobResponse
     * @Date 2018/7/10 17:02
     */
    public TransactionParseBlobResponse parseBlob(TransactionParseBlobRequest transactionParseBlobRequest);

    /**
     * @Author riven
     * @Method evaluationFee
     * @Params [transactionEvaluationFeeRequest]
     * @Return io.bumo.model.response.TransactionEvaluationFeeResponse
     * @Date 2018/7/5 16:57
     */
    public TransactionEvaluationFeeResponse evaluationFee(TransactionEvaluationFeeRequest transactionEvaluationFeeRequest);

    /**
     * @Author riven
     * @Method sign
     * @Params [transactionSignRequest]
     * @Return io.bumo.model.response.TransactionSignResponse
     * @Date 2018/7/5 16:57
     */
    public TransactionSignResponse sign(TransactionSignRequest transactionSignRequest);

    /**
     * @Author riven
     * @Method submit
     * @Params [transactionSubmitRequest]
     * @Return io.bumo.model.response.TransactionSubmitResponse
     * @Date 2018/7/5 16:58
     */
    public TransactionSubmitResponse submit(TransactionSubmitRequest transactionSubmitRequest);

    /**
     * @Author riven
     * @Method getInfo
     * @Params [transactionGetInfoRequest]
     * @Return io.bumo.model.response.TransactionGetInfoResponse
     * @Date 2018/7/5 21:14
     */
    public TransactionGetInfoResponse getInfo(TransactionGetInfoRequest transactionGetInfoRequest);
}
