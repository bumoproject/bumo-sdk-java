package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;
import io.bumo.model.response.result.data.Signer;
import io.bumo.model.response.result.data.TypeThreshold;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/9 16:48
 */
public class AccountSetPrivilegeOperation extends BaseOperation {
    private OperationType operationType = OperationType.ACCOUNT_SET_PRIVILEGE;
    private String masterWeight;
    private Signer[] signers;
    private String txThreshold;
    private TypeThreshold[] typeThresholds;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:17
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getMasterWeight
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:51
     */
    public String getMasterWeight() {
        return masterWeight;
    }

    /**
     * @Author riven
     * @Method setMasterWeight
     * @Params [masterWeight]
     * @Return void
     * @Date 2018/7/9 16:51
     */
    public void setMasterWeight(String masterWeight) {
        this.masterWeight = masterWeight;
    }

    /**
     * @Author riven
     * @Method getSigners
     * @Params []
     * @Return io.bumo.model.response.result.data.Signer[]
     * @Date 2018/7/9 16:51
     */
    public Signer[] getSigners() {
        return signers;
    }

    /**
     * @Author riven
     * @Method setSigners
     * @Params [signers]
     * @Return void
     * @Date 2018/7/9 16:51
     */
    public void setSigners(Signer[] signers) {
        this.signers = signers;
    }

    /**
     * @Author riven
     * @Method addSigner
     * @Params [signer]
     * @Return void
     * @Date 2018/7/9 16:51
     */
    public void addSigner(Signer signer) {
        if (null == signers) {
            signers = new Signer[1];
        } else {
            signers = Arrays.copyOf(signers, signers.length + 1);
        }
        signers[signers.length - 1] = signer;
    }

    /**
     * @Author riven
     * @Method getTxThreshold
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 16:51
     */
    public String getTxThreshold() {
        return txThreshold;
    }

    /**
     * @Author riven
     * @Method setTxThreshold
     * @Params [txThreshold]
     * @Return void
     * @Date 2018/7/9 16:51
     */
    public void setTxThreshold(String txThreshold) {
        this.txThreshold = txThreshold;
    }

    /**
     * @Author riven
     * @Method getTypeThresholds
     * @Params []
     * @Return io.bumo.model.response.result.data.TypeThreshold[]
     * @Date 2018/7/9 16:51
     */
    public TypeThreshold[] getTypeThresholds() {
        return typeThresholds;
    }

    /**
     * @Author riven
     * @Method setTypeThresholds
     * @Params [typeThresholds]
     * @Return void
     * @Date 2018/7/9 16:51
     */
    public void setTypeThresholds(TypeThreshold[] typeThresholds) {
        this.typeThresholds = typeThresholds;
    }

    /**
     * @Author riven
     * @Method addTypeThreshold
     * @Params [typeThreshold]
     * @Return void
     * @Date 2018/7/9 16:52
     */
    public void addTypeThreshold(TypeThreshold typeThreshold) {
        if (null == typeThresholds) {
            typeThresholds = new TypeThreshold[1];
        } else {
            typeThresholds = Arrays.copyOf(typeThresholds, typeThresholds.length + 1);
        }
        typeThresholds[typeThresholds.length - 1] = typeThreshold;
    }
}
