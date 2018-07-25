package io.bumo.exception;

import io.bumo.model.response.BaseResponse;

public enum SdkError {


    SUCCESS(0, "Success"),

    ACCOUNT_CREATE_ERROR(11001, "Create account failed"),
    INVALID_SOURCEADDRESS_ERROR(11002, "Invalid sourceAddress"),
    INVALID_DESTADDRESS_ERROR(11003, "Invalid destAddress"),
    INVALID_INITBALANCE_ERROR(11004, "InitBalance must between 1 and max(int64)"),
    SOURCEADDRESS_EQUAL_DESTADDRESS_ERROR(11005, "SourceAddress cannot be equal to destAddress"),
    INVALID_ADDRESS_ERROR(11006, "Invalid address"),
    CONNECTNETWORK_ERROR(11007, "Connect network failed"),
    INVALID_ISSUE_AMMOUNT_ERROR(11008, "AssetAmount this will be issued mustbetween 1 and max(int64"),
    NO_ASSET_ERROR(11009, "The account does not have this asset"),
    NO_METADATA_ERROR(11010, "The account does not have this metadata"),
    INVALID_DATAKEY_ERROR(11011, "The length of key must between 1 and 1024"),
    INVALID_DATAVALUE_ERROR(11012, "The length of value must between 0 and 256000"),
    INVALID_DATAVERSION_ERROR(11013, "The version must be bigger than 0"),
    INVALID_MASTERWEIGHT_ERROR(11015, "MasterWeight must between 0 and max(uint32)"),
    INVALID_SIGNER_ADDRESS_ERROR(11016, "Invalid signer address"),
    INVALID_SIGNER_WEIGHT_ERROR(11017, "Signer weight must between 0 and max(uint32)"),
    INVALID_TX_THRESHOLD_ERROR(11018, "TxThreshold must between 0 and max(int64)"),
    INVALID_TYPETHRESHOLD_TYPE_ERROR(11019, "Type of TypeThreshold is invalid"),
    INVALID_TYPE_THRESHOLD_ERROR(11020, "TypeThreshold must between 0 and max(int64)"),
    INVALID_ASSET_CODE_ERROR(11023, "The length of key must between 1 and 64"),
    INVALID_ASSET_AMOUNT_ERROR(11024, "AssetMount must between 0 and max(int64)"),
    INVALID_BU_AMOUNT_ERROR(11026, "BuAmount must between 0 and max(int64)"),
    INVALID_ISSUER_ADDRESS_ERROR(11027, "Invalid issuer address"),
    NO_SUCH_TOKEN_ERROR(11030, "No such token"),
    INVALID_TOKEN_NAME_ERROR(11031, "The length of token name must between 1 and 1024"),
    INVALID_TOKEN_SYMBOL_ERROR(11032, "The length of symbol must between 1 and 1024"),
    INVALID_TOKEN_DECIMALS_ERROR(11033, "Decimals must less than 8"),
    INVALID_TOKEN_TOTALSUPPLY_ERROR(11034, "TotalSupply must between 1 and max(int64)"),
    INVALID_TOKENOWNER_ERROR(11035, "Invalid token owner"),
    INVALID_CONTRACTADDRESS_ERROR(11037, "Invalid contract address"),
    CONTRACTADDRESS_NOT_CONTRACTACCOUNT_ERROR(11038, "contractAddress is not a contract account"),
    INVALID_TOKEN_AMOUNT_ERROR(11039, "TokenAmount must between 1 and max(int64)"),
    SOURCEADDRESS_EQUAL_CONTRACTADDRESS_ERROR(11040, "SourceAddress cannot be equal to contractAddress"),
    INVALID_FROMADDRESS_ERROR(11041, "Invalid fromAddress"),
    FROMADDRESS_EQUAL_DESTADDRESS_ERROR(11042, "FromAddress cannot be equal to destAddress"),
    INVALID_SPENDER_ERROR(11043, "Invalid spender"),
    PAYLOAD_EMPTY_ERROR(11044, "Payload cannot be empty"),
    INVALID_LOG_TOPIC_ERROR(11045, "The length of log topic must between 1 and 128"),
    INVALID_LOG_DATA_ERROR(11046, "The length of one of log data must between 1 and 1024"),
    INVALID_CONTRACT_TYPE_ERROR(11047, "Type must be equal or bigger than 0"),
    INVALID_NONCE_ERROR(11048, "Nonce must between 1 and max(int64)"),
    INVALID_GASPRICE_ERROR(11049, "GasPrice must between 1 and max(int64)"),
    INVALID_FEELIMIT_ERROR(11050, "FeeLimit must between 1 and max(int64)"),
    OPERATIONS_EMPTY_ERROR(11051, "Operations cannot be empty"),
    INVALID_CEILLEDGERSEQ_ERROR(11052, "CeilLedgerSeq must be equal or bigger than 0"),
    OPERATIONS_ONE_ERROR(11053, "One of operations cannot be resoled"),
    INVALID_SIGNATURENUMBER_ERROR(11054, "SignagureNumber must between 1 and max(int32)"),
    INVALID_HASH_ERROR(11055, "Invalid transaction hash"),
    INVALID_BLOB_ERROR(11056, "Invalid blob"),
    PRIVATEKEY_NULL_ERROR(11057, "PrivateKeys cannot be empty"),
    PRIVATEKEY_ONE_ERROR(11058, "One of privateKeys is invalid"),
    SIGNDATA_NULL_ERROR(11059, "SignData cannot be empty"),
    INVALID_BLOCKNUMBER_ERROR(11060, "BlockNumber must bigger than 0"),
    PUBLICKEY_NULL_ERROR(11061, "PublicKey cannot be empty"),
    URL_EMPTY_ERROR(11062, "Url cannot be empty"),
    CONTRACTADDRESS_CODE_BOTH_NULL_ERROR(11063, "ContractAddress and code cannot be empty at the same time"),
    INVALID_OPTTYPE_ERROR(11064, "OptType must between 0 and 2"),
    GET_ALLOWANCE_ERRPR(11065, "Get allowance failed"),
    GET_TOKEN_INFO_ERRPR(11066, "Get token info failed"),
    CONNECTN_BLOCKCHAIN_ERROR(19999, "Connect blockchain failed"),
    SYSTEM_ERROR(20000, "System error"),
    REQUEST_NULL_ERROR(13001, "Request parameter cannot be null"),
    ;
	
	
    private final Integer code;
    private final String description;

    private SdkError(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static void checkErrorCode(BaseResponse baseResponse) throws SDKException {
        do {
            Integer errorCode = baseResponse.getErrorCode();
            if (null == errorCode) {
                throw new SDKException(SdkError.CONNECTNETWORK_ERROR);
            }
            if (errorCode != null && errorCode.intValue() != 0) {
                String errorDesc = baseResponse.getErrorDesc();
                throw new SDKException(errorCode, (null == errorDesc ? "error" : errorDesc));
            }
        } while (false);
    }
}