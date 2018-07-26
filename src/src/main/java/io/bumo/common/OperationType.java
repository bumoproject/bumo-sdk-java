package io.bumo.common;

/**
 * @Author riven
 * @Date 2018/7/9 16:18
 */
public enum OperationType {
    // Unknown operation
    UNKNOWN(0),

    // Activate an account
    ACCOUNT_ACTIVATE(1),

    // Set metadata
    ACCOUNT_SET_METADATA(2),

    // Set privilege
    ACCOUNT_SET_PRIVILEGE(3),

    // Issue asset
    ASSET_ISSUE(4),

    // Send asset
    ASSET_SEND(5),

    // Send bu
    BU_SEND(6),

    // Issue token
    TOKEN_ISSUE(7),

    // Transfer token
    TOKEN_TRANSFER(8),

    // Transfer token from an account
    TOKEN_TRANSFER_FROM(9),

    // Approve token
    TOKEN_APPROVE(10),

    // Assign token
    TOKEN_ASSIGN(11),

    // Change owner of token
    TOKEN_CHANGE_OWNER(12),

    // Create contract
    CONTRACT_CREATE(13),

    // Invoke contract by sending asset
    CONTRACT_INVOKE_BY_ASSET(14),

    // Invoke contract by sending bu
    CONTRACT_INVOKE_BY_BU(15),

    // Create log
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
