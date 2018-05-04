package io.bumo.sdk.core.operation;

import io.bumo.sdk.core.adapter.bc.OperationType;
import io.bumo.sdk.core.adapter.bc.response.operation.SetMetadata;
import io.bumo.sdk.core.exception.SdkException;
import io.bumo.sdk.core.operation.impl.*;

/**
 * @author bumo
 * @since 18/03/12 下午3:03.
 * It provides parameterized creation operation method, which is just portable access. All operations can be self created. The upper layer can self encapsulate and easily invoke
 */
public class OperationFactory{
	
	/**
     * Create new account operation
     *
     * @param destAddress the address of destination account
     * @param initBalance Initial balance of BU token
     */
	public static CreateAccountOperation newCreateAccountOperation(String destAddress, long initBalance) throws SdkException{
        return new CreateAccountOperation.Builder().buildDestAddress(destAddress).buildAddInitBalance(initBalance).build();
    }

    /**
     * Create a call contract operation
     *
     * @param destAddress Destination address
     * @param inputData   Contract parameters
     */
    @SuppressWarnings("unused")
	private static InvokeContractOperation newInvokeContractOperation(String destAddress, String inputData) throws SdkException{
        return new InvokeContractOperation.Builder().buildDestAddress(destAddress).buildInputData(inputData).build();
    }

    /**
     * Issue assets
     *
     * @param assetCode Asset code
     * @param amount    The amount of the assets to be issued
     */
    public static IssueAssetOperation newIssueAssetOperation(String assetCode, long amount) throws SdkException{
        return new IssueAssetOperation.Builder().buildAmount(amount).buildAssetCode(assetCode).build();
    }

    /**
     * Asset transfer
     *
     * @param targetAddress Target address
     * @param issuerAddress The address of asset issuer
     * @param assetCode     Asset code
     * @param amount        The amount of the assets to be transfered
     */
    public static PayAssetOperation newPayAssetOperation(String targetAddress, String issuerAddress, String assetCode, long amount) throws SdkException{
        return new PayAssetOperation.Builder().buildAmount(amount).buildAssetCode(assetCode).buildTargetAddress(targetAddress).buildIssuerAddress(issuerAddress).build();
    }

    /**
     * Asset transfer
     *
     * @param targetAddress Target address
     * @param issuerAddress Asset publisher address
     * @param assetCode     Asset code
     * @param amount        Asset amount
     * @param input
     * @return
     * @throws SdkException
     */
    @SuppressWarnings("unused")
	private static PayAssetOperation newPayAssetOperation(String targetAddress, String issuerAddress, String assetCode, long amount, String input) throws SdkException{
        return new PayAssetOperation.Builder().buildAmount(amount).buildAssetCode(assetCode).buildTargetAddress(targetAddress).buildIssuerAddress(issuerAddress).buildInput(input).build();
    }
    /***
     * Pay BU token
     * @author bumo
     * @since 2018/3/16 10:27 a.m.
     * @param targetAddress
     * @param amount
     * @return
     * @throws SdkException
     */
    public static PayCoinOperation newPayCoinOperation(String targetAddress, long amount )  throws SdkException{
        return new PayCoinOperation.Builder().buildTargetAddress(targetAddress).buildAmount(amount).build();
    }
    /***
     * Pay BU currency
     * @author bumo
     * @since 2018/3/16 10:27 a.m. 
     * @param targetAddress
     * @param amount
     * @param input
     * @return
     * @throws SdkException
     */
    @SuppressWarnings("unused")
	private static PayCoinOperation newPayCoinOperation(String targetAddress, long amount ,String input) throws SdkException{
    	return new PayCoinOperation.Builder().buildTargetAddress(targetAddress).buildAmount(amount).buildInput(input).build();
    }

    /**
     * Increase metadata, can only increase, can not be modified
     *
     * @param key   metadata-key
     * @param value metadata-value
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newSetMetadataOperation(String key, String value) throws SdkException{
        return new SetMetadataOperation.Builder().buildMetadata(key, value).build();
    }

    /**
     * Metadata operation, handle version yourself, notice that version+1 will be set up at the time of submission
     *
     * @param key            metadata-key
     * @param value          metadata-value
     * @param currentVersion Version that is currently querying, no 0
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newSetMetadataOperation(String key, String value, long currentVersion) throws SdkException{
        return new SetMetadataOperation.Builder().buildMetadata(key, value, currentVersion).build();
    }

    /**
     * Modify metadata
     * The modification must be querying first, otherwise the modification will fail
     *
     * @param setMetadats metadata list
     */
    @SuppressWarnings("unused")
	private static SetMetadataOperation newUpdateSetMetadataOperation(SetMetadata setMetadats) throws SdkException{
        return new SetMetadataOperation.Builder().buildMetadata(setMetadats).build();
    }

    /**
     * Setting / updating weight
     *
     * @param masterWeight owner weight
     */
    @SuppressWarnings("unused")
	private static SetSignerWeightOperation newSetSignerWeightOperation(long masterWeight) throws SdkException{
        return new SetSignerWeightOperation.Builder().buildMasterWeight(masterWeight).build();
    }

    /**
     * Setting / updating the signature list
     *
     * @param address Signature address
     * @param weight  weight
     */
    @SuppressWarnings("unused")
	private static SetSignerWeightOperation newSetSignerWeightOperation(String address, long weight) throws SdkException{
        return new SetSignerWeightOperation.Builder().buildAddSigner(address, weight).build();
    }

    /**
     * Set / update transaction threshold
     *
     * @param txThreshold Trading threshold
     */
    @SuppressWarnings("unused")
	private static SetThresholdOperation newSetThresholdOperation(long txThreshold) throws SdkException{
        return new SetThresholdOperation.Builder().buildTxThreshold(txThreshold).build();
    }

    /**
     * Set / update operation threshold
     *
     * @param operationTypeV3 Operation type
     * @param threshold       Specific threshold
     */
    @SuppressWarnings("unused")
	private static SetThresholdOperation newSetThresholdOperation(OperationType operationTypeV3, long threshold) throws SdkException{
        return new SetThresholdOperation.Builder().buildAddTypeThreshold(operationTypeV3, threshold).build();
    }
}
