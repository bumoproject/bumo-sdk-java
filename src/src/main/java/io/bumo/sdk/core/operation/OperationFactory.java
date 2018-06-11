package io.bumo.sdk.core.operation;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.impl.*;

/**
 * @author bumo
 * @since 18/03/12 P.M.3:03.
 * It provides parameterized creation operation method, which is just portable access. All operations can be self created. The upper layer can self encapsulate and easily invoke
 */
public class OperationFactory{
	
	/**
     * Create new account operation
     *
     * @param sourceAddress Source Address
     * @param destAddress the address of destination account
     * @param initBalance Initial balance of BU token
     */
	public static CreateAccountOperation newCreateAccountOperation(String sourceAddress, String destAddress, long initBalance) throws SdkException{
        return new CreateAccountOperation.Builder().buildOperationSourceAddress(sourceAddress).buildDestAddress(destAddress).buildAddInitBalance(initBalance).build();
    }

    /**
     * Create new account operation
     *
     * @param sourceAddress Source Address
     * @param destAddress the address of destination account
     * @param initBalance Initial balance of BU token
     */
    public static CreateAccountOperation newCreateContractOperation(String sourceAddress, String destAddress, long initBalance, String payload, String initInput) throws SdkException{
        return new CreateAccountOperation.Builder().buildOperationSourceAddress(sourceAddress).buildDestAddress(destAddress).buildScript(payload).buildAddInitInput(initInput).buildAddInitBalance(initBalance).build();
    }

    /**
     * Create a call contract operation
     *
     * @param sourceAddress Source Address
     * @param targetAddress target address
     * @param inputData   Contract parameters
     */
    @SuppressWarnings("unused")
	public static BcOperation newInvokeContractByAssetOperation(String sourceAddress, String targetAddress, String issuerAddress, String assetCode, long amount, String inputData) throws SdkException{
        BcOperation operation = null;
        if (assetCode == null || assetCode.length() == 0 || issuerAddress == null || issuerAddress.length() == 0 || amount == 0)
            operation = new InvokeContractOperation.Builder().buildDestAddress(targetAddress).buildInputData(inputData).build();
        else
            operation = new PayAssetOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAmount(amount).buildAssetCode(assetCode).buildTargetAddress(targetAddress).buildIssuerAddress(issuerAddress).buildInput(inputData).build();

        return operation;
    }

    public static BcOperation newInvokeContractByBUOperation(String sourceAddress, String targetAddress, long amount ,String inputData) throws SdkException{
        return new PayCoinOperation.Builder().buildOperationSourceAddress(sourceAddress).buildTargetAddress(targetAddress).buildAmount(amount).buildInput(inputData).build();
    }

    /**
     * Issue assets
     *
     * @param sourceAddress Source Address
     * @param assetCode Asset code
     * @param amount    The amount of the assets to be issued
     */
    public static IssueAssetOperation newIssueAssetOperation(String sourceAddress, String assetCode, long amount) throws SdkException{
        return new IssueAssetOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAmount(amount).buildAssetCode(assetCode).build();
    }

    /**
     * Asset transfer
     *
     * @param sourceAddress Source Address
     * @param targetAddress Target address
     * @param issuerAddress The address of asset issuer
     * @param assetCode     Asset code
     * @param amount        The amount of the assets to be transfered
     */
    public static PayAssetOperation newPayAssetOperation(String sourceAddress, String targetAddress, String issuerAddress, String assetCode, long amount) throws SdkException{
        return new PayAssetOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAmount(amount).buildAssetCode(assetCode).buildTargetAddress(targetAddress).buildIssuerAddress(issuerAddress).build();
    }

    /**
     * Asset transfer
     *
     * @param sourceAddress Source Address
     * @param targetAddress Target address
     * @param issuerAddress Asset publisher address
     * @param assetCode     Asset code
     * @param amount        Asset amount
     * @param input
     * @return
     * @throws SdkException
     */
    @SuppressWarnings("unused")
	private static PayAssetOperation newPayAssetOperation(String sourceAddress, String targetAddress, String issuerAddress, String assetCode, long amount, String input) throws SdkException{
        return new PayAssetOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAmount(amount).buildAssetCode(assetCode).buildTargetAddress(targetAddress).buildIssuerAddress(issuerAddress).buildInput(input).build();
    }
    /***
     * Pay BU token
     * @author bumo
     * @since 2018/3/16 10:27 a.m.
     * @param sourceAddress Source Address
     * @param targetAddress
     * @param amount
     * @return
     * @throws SdkException
     */
    public static PayCoinOperation newPayCoinOperation(String sourceAddress, String targetAddress, long amount) throws SdkException{
        return new PayCoinOperation.Builder().buildOperationSourceAddress(sourceAddress).buildTargetAddress(targetAddress).buildAmount(amount).build();
    }
    /***
     * Pay BU currency
     * @author bumo
     * @since 2018/3/16 10:27 a.m.
     * @param sourceAddress Source Address
     * @param targetAddress
     * @param amount
     * @param input
     * @return
     * @throws SdkException
     */
    @SuppressWarnings("unused")
	private static PayCoinOperation newPayCoinOperation(String sourceAddress, String targetAddress, long amount ,String input) throws SdkException{
    	return new PayCoinOperation.Builder().buildOperationSourceAddress(sourceAddress).buildTargetAddress(targetAddress).buildAmount(amount).buildInput(input).build();
    }

    /**
     * Increase metadata, can only increase, can not be modified
     * @param sourceAddress Source Address
     * @param key   metadata-key
     * @param value metadata-value
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newSetMetadataOperation(String sourceAddress, String key, String value) throws SdkException{
        return new SetMetadataOperation.Builder().buildOperationSourceAddress(sourceAddress).buildMetadata(key, value).build();
    }

    /**
     * Metadata operation, handle version yourself, notice that version+1 will be set up at the time of submission
     *
     * @param sourceAddress Source Address
     * @param key            metadata-key
     * @param value          metadata-value
     * @param version Version that is currently querying, no 0
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newSetMetadataOperation(String sourceAddress, String key, String value, long version) throws SdkException{
        return new SetMetadataOperation.Builder().buildOperationSourceAddress(sourceAddress).buildMetadata(key, value, version).build();
    }

    /**
     * Modify metadata
     * The modification must be querying first, otherwise the modification will fail
     *
     * @param sourceAddress Source Address
     * @param setMetadats metadata list
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newUpdateSetMetadataOperation(String sourceAddress, SetMetadata setMetadats) throws SdkException{
        return new SetMetadataOperation.Builder().buildOperationSourceAddress(sourceAddress).buildMetadata(setMetadats).build();
    }

    /**
     * Setting / updating weight
     *
     * @param sourceAddress Source Address
     * @param masterWeight owner weight
     */
    @SuppressWarnings("unused")
	private static SetSignerWeightOperation newSetSignerWeightOperation(String sourceAddress, long masterWeight) throws SdkException{
        return new SetSignerWeightOperation.Builder().buildOperationSourceAddress(sourceAddress).buildMasterWeight(masterWeight).build();
    }

    /**
     * Setting / updating the signature list
     *
     * @param sourceAddress Source Address
     * @param address Signature address
     * @param weight  weight
     */
    @SuppressWarnings("unused")
	private static SetSignerWeightOperation newSetSignerWeightOperation(String sourceAddress, String address, long weight) throws SdkException{
        return new SetSignerWeightOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAddSigner(address, weight).build();
    }

    /**
     * Set / update transaction threshold
     *
     * @param sourceAddress Source Address
     * @param txThreshold Trading threshold
     */
    @SuppressWarnings("unused")
	private static SetThresholdOperation newSetThresholdOperation(String sourceAddress, long txThreshold) throws SdkException{
        return new SetThresholdOperation.Builder().buildOperationSourceAddress(sourceAddress).buildTxThreshold(txThreshold).build();
    }

    /**
     * Set / update operation threshold
     *
     * @param sourceAddress Source Address
     * @param operationTypeV3 Operation type
     * @param threshold       Specific threshold
     */
    @SuppressWarnings("unused")
	private static SetThresholdOperation newSetThresholdOperation(String sourceAddress, OperationType operationTypeV3, long threshold) throws SdkException{
        return new SetThresholdOperation.Builder().buildOperationSourceAddress(sourceAddress).buildAddTypeThreshold(operationTypeV3, threshold).build();
    }
}
