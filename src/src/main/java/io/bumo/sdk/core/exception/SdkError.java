package io.bumo.sdk.core.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bumo
 * @since 18/03/12 3:03 p.m.
 */
public enum SdkError{

    SUCCESS(0, "Success"),

    OPERATION_ERROR_NOT_DESC_ADDRESS(10001, "The operation must set the destination address"),
    OPERATION_ERROR_STATUS(10002, "Operation state abnormality"),
    OPERATION_ERROR_NOT_CONTRACT_ADDRESS(10003, "The contract address can not be empty"),
    OPERATION_ERROR_ISSUE_SOURCE_ADDRESS(10004, "The asset issuer cannot be empty"),
    OPERATION_ERROR_ISSUE_CODE(10005, "Asset code can not be empty"),
    OPERATION_ERROR_PAYMENT_AMOUNT_ZERO(10006, "The amount of the transfer must be more than 0"),
    OPERATION_ERROR_SET_METADATA_EMPTY(10007, "Setting metadata can not be empty"),
    OPERATION_ERROR_SET_SIGNER_WEIGHT(10008, "You must set up masterWeight or signer"),
    OPERATION_ERROR_SET_THRESHOLD(10009, "You must set up txThreshold or typeThresholds"),
    OPERATION_ERROR_ISSUE_AMOUNT_ZERO(10010, "The amount of assets issued should not be less than 0"),
    OPERATION_ERROR_TX_THRESHOLD_LT_ZERO(10011, "txThreshold can't be less than 0"),
    OPERATION_ERROR_MASTER_WEIGHT_LT_ZERO(10012, "masterWeight can't be less than 0"),
    OPERATION_ERROR_SINGER_WEIGHT_LT_ZERO(10013, "The weight of the signer should not be less than 0"),
    OPERATION_ERROR_TX_THRESHOLD_TYPE_LT_ZERO(10014, "The specific operation threshold should not be less than 0"),
    OPERATION_ERROR_SET_SIGNER_ADDRESS_NOT_EMPTY(10015, "The added signer's address can not be empty"),
    OPERATION_ERROR_TX_THRESHOLD_TYPE_NOT_NULL(10016, "Specific threshold operation type can not be empty"),
    OPERATION_ERROR_PAYMENT_COIN_ZERO(10017, "Payment of BU currency must be more than 0"),
    OPERATION_ERROR_TEST_OPER(10018, "Evaluation operation cannot be empty"),
    OPERATION_ERROR_NOT_INITINPUT(10019,"initInput cannot be empty"),
    OPERATION_ERROR_INITBALANCE_ILLEGAL(10020,"InitBalance value is illegal"),
    OPERATION_ERROR_NOT_INPUT(10021,"input cannot be empty"),
    OPERATION_ERROR_NOT_METADATA(10022,"metadata的key or value cannot be empty"),

    TRANSACTION_ERROR_SPONSOR(10100, "The initiator of the transaction cannot be empty"),
    TRANSACTION_ERROR_SIGNATURE(10101, "Transaction signature list cannot be empty"),
    TRANSACTION_ERROR_STATUS(10102, "Abnormal transaction status"),
    TRANSACTION_ERROR_TIMEOUT(10103, "The transaction failed! No notice was received"),
    TRANSACTION_ERROR_BLOB_NOT_NULL(10104, "blob Must not be empty"),
    TRANSACTION_ERROR_PUBLIC_KEY_NOT_EMPTY(10105, "The public key can not be empty"),
    TRANSACTION_ERROR_BLOB_REPEAT_GENERATOR(10106, "Do not repeat the generation of Blob"),
    TRANSACTION_ERROR_OPERATOR_NOT_EMPTY(10107, "The operation list cannot be empty"),
    TRANSACTION_ERROR_PRIVATE_KEY_NOT_EMPTY(10108, "The private key cannot be empty"),
    TRANSACTION_ERROR_FEE_ILLEGAL(10109, "Fee value is illegal"),
    TRANSACTION_ERROR_GAS_ILLEGAL(10110, "gasPrice value is illegal"),


    PARSE_URI_ERROR(90001, "Parsing URI error, please check"),
    PARSE_IP_ERROR(90002, "Parsing IP error, please check"),
    NODE_MANAGER_INIT_ERROR(90003, "Initializing the node manager failed! please confirm that at least one node can be accessed."),
    RPC_INVOKE_ERROR_TIMEOUT(90004, "Access bottom timeout!"),
    EVENT_ERROR_SIGNATURE_VERIFY_FAIL(90005, "verification of local signature failed"),
    EVENT_ERROR_NOT_FOUND_HANDLE(90006, "Event handlers that was registered are not found"),
    SIGNATURE_ERROR_PUBLIC_PRIVATE(90007, "Signature exception! Please confirm the correctness of the public key or private key"),
    EVENT_ERROR_ROUTER_HOST_FAIL(90008, "Routing node failure!Please confirm that the monitor configuration and access configuration can match"),
    TRANSACTION_SYNC_TIMEOUT(90009, "Timeout exception: Not notified over 24 hours, so Transaction synchronizer automatically removed"),
    REDIS_ERROR_LOCK_TIMEOUT(90010, "Get redis lock  timeout！"),

    ERROR(99999, "Internal error"),;

    private int code;

    private String description;

    private static Map<Integer, SdkError> maps = new HashMap<>();

    static{
        for (SdkError error : SdkError.values()) {
            maps.put(error.getCode(), error);
        }
    }

    private SdkError(int code, String desc){
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
        SdkError error = maps.get(code);
        if (error == null) {
            return null;
        }
        return error.getDescription();
    }
}
