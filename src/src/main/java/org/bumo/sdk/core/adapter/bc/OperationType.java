package org.bumo.sdk.core.adapter.bc;

public enum OperationType{

    CREATE_ACCOUNT(1), // 创建账号
    ISSUE_ASSET(2), // 发行资产
    PAYMENT(3), // 转移资产
    SET_METADATA(4), // 设置metadata
    SET_SIGNER_WEIGHT(5), // set_signer_weight
    SET_THRESHOLD(6), // set_threshold
    PAY_COIN(7),    // pay_coin
    ;

    private int value;

    private OperationType(int value){
        this.value = value;
    }

    public int intValue(){
        return value;
    }

    /**
     * 返回指定值对应的操作类型；如果没有，则返回 null；
     *
     * @param value
     * @return
     */
    public static OperationType getOperationType(int value){
        for (OperationType type : OperationType.values()) {
            if (type.intValue() == value) {
                return type;
            }
        }
        return null;
    }
}
