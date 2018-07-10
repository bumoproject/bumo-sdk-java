package io.bumo.common;

/**
 * @Author riven
 * @Date 2018/7/9 16:18
 */
public enum OperationType {
    UNKNOWN(0),
    ACCOUNT_ACTIVATE(1),
    ACCOUNT_SET_METADATA(2),
    ACCOUNT_SET_PRIVILEGE(3),
    ASSET_ISSUE(4),
    ASSET_SEND(5),
    BU_SEND(6),
    TOKEN_ISSUE(7),
    TOKEN_TRANSFER(8),
    TOKEN_TRANSFER_FROM(9),
    TOKEN_APPROVE(10),
    TOKEN_ASSIGN(11),
    TOKEN_CHANGE_OWNER(12),
    CONTRACT_CREATE(13),
    CONTRACT_INVOKE_BY_ASSET(14),
    CONTRACT_INVOKE_BY_BU(15),
    LOG_CREATE(16),
    ;

    public final int getNumber() {
        return value;
    }

    private final int value;

    private OperationType(int value) {
        this.value = value;
    }
}
