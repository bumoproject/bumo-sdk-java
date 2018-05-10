package io.bumo.sdk.core.adapter.bc;

public enum OperationType{

    CREATE_ACCOUNT(1), // Create account
    ISSUE_ASSET(2), // Issue assets
    PAYASSET(3), // Transfer assets
    SET_METADATA(4), // Set metadata
    SET_SIGNER_WEIGHT(5), // Set signer's weight
    SET_THRESHOLD(6), // Set threshold
    PAY_COIN(7),    // Pay coin
    ;

    private int value;

    private OperationType(int value){
        this.value = value;
    }

    public int intValue(){
        return value;
    }

    /**
     * Returns the type of operation corresponding to the specified value; if not, returns null
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
