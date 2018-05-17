package io.bumo.sdk.core.adapter.exception;

import java.util.HashMap;
import java.util.Map;

public enum BlockchainError{


    SUCCESS(0, "Success"),

    INTERNAL(1, "Internal service error"),

    WRONG_ARGUMENT(2, "Parameter error"),

    TARGET_EXIST(3, "Objects already exist, such as repeated submission of transactions"),

    TARGET_NOT_EXIST(4, "Object does not exist, such as failure to query account, TX, block"),

    TX_TIMEOUT(5, "TX timeout means that the TX has been removed from the TX cache queue by the current node, but it does not mean that this must not be executed"),

    EXPR_CONDITION_RESULT_FALSE(20, "The result of the expression is false, which means that the TX is not executing successfully at the moment, but that does not mean that the block will not succeed in the future block"),

    EXPR_CONDITION_SYNTAX_ERROR(21, "An error in expression syntax analysis, which means that the TX will fail"),

    ILLEGAL_PUB_KEY(90, "Illegal public key"),

    ILLEGAL_PRIV_KEY(91, "Illegal private key"),

    ILLEGAL_ASSET(92, "Illegal asset issuing address"),

    WRONG_SIGNATURE(93, "The signature weight is not enough to reach the threshold value of the operation"),

    ILLEGAL_ADDRESS(94, "Illegal address"),

    OUT_OF_TIME_SPAN(95, "Not in the time range"),

    NO_CONSENSUS(96, "No consensus"),

    TX_EMPTY_OPERATIONS(97, "Lack operation in transaction"),

    TX_OUT_OF_MAX_OPERATIONS(98, "The number of operations contained in the transaction exceeds the limit"),

    TX_WRONG_SEQUENCE_NO(99, "Illegal transaction serial number"),

    NO_MONEY(100, "Insufficient balance of available token"),

    ILLEGAL_TARGET_EQ_SOURCE(101, "The target address is equal to the source address"),

    TARGET_ACCOUNT_EXIST(102, "The target account has already existed"),

    TARGET_ACCOUNT_NOT_EXIST(103, "The target account does not exist"),

    ASSET_NO_AMOUNT(104, "Insufficient balance of available assets"),

    ASSET_AMOUNT_TOO_LARGE(105, "The amount of assets is too large, beyond the scope of Int64"),

    LACK_FEE(111, "Insufficient handling fee provided"),

    TX_TOO_EARLY(112, "Too early submission of transaction"),

    TX_TOO_LATE(113, "Too late submission of transaction"),

    TX_TOO_MANY(114, "The number of transactions received by the server is too much and is being processed"),

    ILLEGAL_WEIGHT(120, "Invalid weight value"),

    NO_INPUT(130, "Input does not exist"),

    ILLEGAL_INPUT(131, "Illegal input"),

    IS_NOT_SCF_TX(132, "Non-supply-chain transaction"),

    ILLEGAL_VERSION(144, "Incorrect metadata version of account"),

    CONTRACT_EXECUTE_FAIL(151, "Failure of contract execution"),

    CONTRACT_SYNTAX_ERROR(152, "Failure of contract syntax analysis"),;

    private int code;

    private String description;

    private static Map<Integer, BlockchainError> maps = new HashMap<>();

    static{
        for (BlockchainError error : BlockchainError.values()) {
            maps.put(error.getCode(), error);
        }
    }

    private BlockchainError(int code, String desc){
        this.code = code;
        this.description = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDescription(){
        return description;
    }

    public static boolean isError(int code){
        return code != SUCCESS.code && maps.containsKey(code);
    }

    public static String getDescription(int code){
        BlockchainError error = maps.get(code);
        if (error == null) {
            return null;
        }
        return error.getDescription();
    }
}
