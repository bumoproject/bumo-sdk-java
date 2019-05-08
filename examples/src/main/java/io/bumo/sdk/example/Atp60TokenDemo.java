package io.bumo.sdk.example;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.bumo.common.Tools;
import io.bumo.model.request.*;
import io.bumo.model.request.operation.ContractInvokeByBUOperation;
import io.bumo.model.response.*;
import io.bumo.model.response.result.ContractCallResult;
import io.bumo.model.response.result.data.TransactionHistory;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.bumo.SDK;
import io.bumo.common.ToBaseUnit;
import io.bumo.model.request.operation.BaseOperation;
import io.bumo.model.request.operation.ContractCreateOperation;
import io.bumo.model.response.result.TransactionBuildBlobResult;

public class Atp60TokenDemo {
    /* Bumo 1.2.0 test version */
	public SDK sdk = SDK.getInstance("http://13.112.159.231");
	
	/**
	 * First: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) registers the information in ATP 60.
	 */
	@Test
	public void register() {
        // The seller public key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = "Contact@bumo.io";
        // Company organizational code.
        String organizationalCode = "N5464**";
        // Company corporate name.
        String corporateName = "某某";
        // Company corporate identity card number
        String cardNumber = "1**";
        // Company business license photo.
        String businessLicense = "[url_hash类型_hash值]";
        // Company corporate identity card front photo.
        String cardFrontPhoto = "[url_hash类型_hash值]";
        // Company corporate identity card back photo.
        String cardBackPhoto = "[url_hash类型_hash值]";

        // Registerring.
        registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);
	}

	@Test
	public void getContractAddress() {
        getContractAddressQuery();
    }

    @Test
    public void sellInfo() {
        sellInfoQuery();
    }


    /**
     * Second: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets the document.
     */
    @Test
    public void setDocument() {
        // The seller public key to set document.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The document id.
        String documentId = "1";
        // The document name.
        String documentName = "BUMO白皮书";
        // The document url.
        String documentUrl = "https://BUMO.io/BUMO-Technology-White-Paper-cn";
        // The document hash type.
        String documentHashType = "md5";
        // The document hash.
        String documentHash = "31be016368639ba1a7ae7b63247807a1";

        // Setting the document.
        setDocumentTx(sellerPrivateKey, sellerAddress, documentId, documentName, documentUrl, documentHashType, documentHash);
    }

    @Test
    public void documentInfo() {
        // The document id.
        String documentId = "1";

        documentInfoQuery(documentId);
    }


    /**
     * Third: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) creates the SPU.
     */
    @Test
    public void createSPU() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "000000001";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "手机";
        // The spu reference price.
        String spuPrice = "3000.00";
        // The spu brand.
        String spuBrand = "苹果";
        // The spu model.
        String spuModel = "5s";

        // Creating SPU.
        createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);
    }

    @Test
    public void SPUInfo() {
        // The spu id.
        String spuId = "000000001";

        SPUInfoQuery(spuId);
    }


    /**
     * Fourth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) creates the Tranche.
     */
    @Test
    public void createTranche() {
        // The seller public key to create tranche.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The tranche id.
        String trancheId = "1";
        // The tranche description.
        String trancheDesc = "有效期";
        // The start time, the unit is ms.
        String startTime = "1517032155872949";
        // The end time, the unit is ms.
        String endTime = "1517470155872949";

        // Creating tranche.
        createTrancheTx(sellerPrivateKey, sellerAddress, trancheId, trancheDesc, startTime, endTime);
    }

    @Test
    public void trancheInfo() {
        // The tranche id.
        String trancheId = "1";

        trancheInfoQuery(trancheId);
    }


    /**
     * Fifth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) issues the SKU Tokens.
     *
     * Notice:  The spu and tranche can be ignored.
     *    1. That the spu is ignored means that the sku does not have spu.
     *    2. If the tranche is ignored, the sku will issue to the default tranche which id is '0'.
     */
    @Test
    public void issueSKUTokens() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The default tranche.
        // Notice: If this is not setting, the default tranche of id '0' will be used.
        String defaultTrancheId = "1";
        // The spu id.
        // Notice: If this is not setting, this sku tokens don't have spu.
        String spuId = "000000001";
        // The sku label.
        String skuLabel = "iphone 5s 白色 64G 中国大陆版";
        // The address that will be sent SKU Tokens when the cash finishes.
        String cashAddress = "buQqudnoPPV2utx92jfdcLkFGDaB7v3iasPM";
        // The sku reference price.
        String skuPrice = "3000";
        // The sku color.
        String skuColor = "白色";
        // The sku memory.
        String skuMemory = "64";
        // The sku model.
        String skuModel = "中国大陆";
        // The token name
        String tokenName = "iphone 5s 白色 64G 中国大陆版";
        // The token symbol.
        String tokenSymbol = "IPWSFC";
        // The token supply.
        String tokenSupply = "10000";
        // The token decimals.
        int decimals = 0;
        // The token description.
        String tokenDesc = "iphone 5s 白色 64G 中国大陆版";

        // Issuing SKU Tokens.
        issueSKUTokensTx(sellerPrivateKey, sellerAddress, skuId, defaultTrancheId, spuId, skuLabel, cashAddress, skuPrice, skuColor, skuMemory, skuModel, tokenName, tokenSymbol, tokenSupply, decimals, tokenDesc);
    }

    @Test
    public void SKUTokenInfo() {
        // The sku id.
        String skuId = "1";

        SKUTokenInfoQuery(skuId);
    }


    /**
     * Sixth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) adds issuance of the SKU Tokens.
     *
     * Notice: The tranche can be ignored.
     *    If the trancheId is ignored, SKU Tokens will be sent to default tranche which id is '0'.
     */
    @Test
    public void addIssuanceByTranche() {
        // The seller public key to issue SKU Tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche which id is '0'.
        String trancheId = "1";
        // The token supply.
        String tokenSupply = "10000";

        // Adding issuance by tranche.
        addIssuanceByTrancheTx(sellerPrivateKey, sellerAddress, skuId, trancheId, tokenSupply);
    }


    /**
     * Seventh: Manufacturer (buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu) authorizes the issuance of SKU Tokens.
     */
    @Test
    public void authorizeSKU() {
        // The manufacturer public key to authrize sku tokens.
        String manufacturerPrivateKey = "privbyGpWyTTBgJoj3WezCv2D5cCCuPoL1dHYpMCmaFVSTvQmuUpgz6j";
        // The seller address.
        String manufacturerAddress = "buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");
        // The list of sku ids.
        JSONArray skuIds = new JSONArray();
        skuIds.add("1");

        // Autorizing SKU.
        autorizeSKUTx(manufacturerPrivateKey, manufacturerAddress, skuIds);
    }


    /**
     * Eighth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets a controller.
     */
    @Test
    public void setController() {
        // The seller public key to set controller.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The controller name.
        String controllerName = "北京法院";
        // The controller contact.
        String controllerContact = "dispute@fy.com";

        // Setting controller.
        setControllerTx(sellerPrivateKey, sellerAddress, controllerAddress, controllerName, controllerContact);
    }

    @Test
    public void controllerInfo() {
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";

        controllerInfoQuery(controllerAddress);
    }


    /**
     * NinTh: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) create a lockup, the unlocker is a controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD).
     */
    @Test
    public void createLockup() {
        // The seller public key to create lockup.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The lockup id.
        String lockupId = "1";
        // The start time of lockup.
        String startTime = "1";
        // The end time.
        String endTime = "1588057810000000";
        // The unlocker.
        String unlocker = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";

        // Creating lockup.
        createLockupTx(sellerPrivateKey, sellerAddress, lockupId, startTime, endTime, unlocker);
    }

    @Test
    public void lockupInfo() {
        // The lockup id.
        String lockupId = "1";

        lockupInfoQuery(lockupId);
    }


    /**
     * Tenth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) transfers 200 sku tokens to an account (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP).
     */
    @Test
    public void transferByTranche() {
        // The seller public key to transfer sku tokens.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The target address.
        String toAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku tokens amount.
        String value = "200";

        // Transferring tokens.
        transferByTrancheTx(sellerPrivateKey, sellerAddress, skuId, trancheId, toAddress, value);
    }

    @Test
    public void balanceOf() {
        // The sku id.
        String skuId = "1";
        // The address to be queried.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        balanceOfQuery(address, skuId);
    }

    @Test
    public void balanceOfByTranche() {
        // The sku id.
        String skuId = "1";
        // The tranche id.
        String trancheId = "1";
        // The address to be queried.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        balanceOfByTrancheQuery(address, skuId, trancheId);
    }

    /**
     * Eleventh: Token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) approve other account (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) 100 SKU Tokens.
     */
    @Test
    public void approve() {
        // The token holder public key to approve sku tokens.
        String sellerPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String sellerAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String skuId = "1";
        // The spender address.
        String spender = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The sku tokens amount.
        String value = "100";

        // Approving
        approveTx(sellerPrivateKey, sellerAddress, skuId, spender, value);
    }

    @Test
    public void allowance() {
        // The token owner.
        String owner = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The spender.
        String spender = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The sku id.
        String skuId = "1";

        allowanceQuery(owner, spender, skuId);
    }


    /**
     * Twelfth: The spender (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) tranfers the tokens of the token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) 50 SKU Tokens to other account (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor).
     */
    @Test
    public void transferFromByTranche() {
        // The spender public key to transfer sku tokens.
        String spenderPrivateKey = "privbwMxt4BFNjN6vbjizrFEMhiFJyxMydWCb3sr1utDokApEMGXB9mD";
        // The spender address.
        String spenderAddress = "buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK";
        // The from address.
        String fromAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The target address.
        String toAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The sku tokens amount.
        String value = "50";

        // Transferring the Tokens.
        transferFromByTrancheTx(spenderPrivateKey, spenderAddress, fromAddress, skuId, trancheId, toAddress, value);
    }


    /**
     * Thirteenth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets acceptance.
     */
    @Test
    public void setAcceptance() {
        // The seller public key to set acceptance.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The acceptance id.
        String acceptanceId = "1";
        // The sku ids.
        JSONArray skuIds = new JSONArray();
        skuIds.add("1");
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The acceptor address.
        String acceptorAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The acceptance name.
        String acceptanceName = "北京贸易集中处理中心";
        // The acceptance contact.
        String acceptanceContact = "contact@my.com";
        // The acceptance period.
        String acceptancePeriod = "7天";

        setAcceptanceTx(sellerPrivateKey, sellerAddress, acceptanceId, skuIds, trancheId, acceptorAddress, acceptanceName, acceptanceContact, acceptancePeriod);
    }

    @Test
    public void acceptanceInfo() {
        // The acceptance id.
        String acceptanceId = "1";

        acceptanceInfoQuery(acceptanceId);
    }


    /**
     * Fourteenth: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) requests cash.
     */
    @Test
    public void requestCash() {
        // The token holder public key to request cash.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The cash id.
        String cashId = "1";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The amount to be cashed.
        String value = "50";
        // The acceptance id.
        String acceptanceId = "1";
        // The lockup id.
        String lockupId = "1";

        // Requesting cash.
        requestCashTx(holderPrivateKey, holderAddress, cashId, skuId, trancheId, value, acceptanceId, lockupId);
    }

    @Test
    public void cashInfo() {
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        cashInfoQuery(cashId, applicant);
    }


    /**
     * Fifteenth: The acceptor (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor) pays cash.
     */
    @Test
    public void cash() {
        // The acceptor public key to cash.
        String acceptorPrivateKey = "privbUbdoe6co99ykomqQRUDiD8rh3XvWvmexNUS1bZbu5gb8RuKJA8Y";
        // The acceptor address.
        String acceptorAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        // Cashing.
        cashTx(acceptorPrivateKey, acceptorAddress, cashId, applicant);
    }


    /**
     * Sixteenth: The cash finished or causes dispute.
     * Sixteenth1: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) confirms cash right, then finishes the cash.
     */
    @Test
    public void confirmCash() {
        // The token holder public key to confirm cash.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        // Confirming cash.
        confirmCashTx(holderPrivateKey, holderAddress, cashId, applicant);
    }


    /**
     * Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) create a lockup, the unlocker is a controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD).
     */
    @Test
    public void createLockupForDispute() {
        // The seller public key to create lockup.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The lockup id.
        String lockupId = "1";
        // The start time of lockup.
        String startTime = "2";
        // The end time.
        String endTime = "1588057810000000";
        // The unlocker.
        String unlocker = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";

        // Creating lockup.
        createLockupTx(sellerPrivateKey, sellerAddress, lockupId, startTime, endTime, unlocker);
    }

    /**
     * Sixteenth: The cash finished or causes dispute.
     * Sixteenth2: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) applies dispute.
     */
    @Test
    public void applyDispute() {
        // The token holder public key to apply dispute.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The dispute reason.
        String reason = "未收到货";
        // The controller address.
        String controller = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The lockup id for disupte.
        String lockupId = "2";

        // Applying dispute.
        applyDisputeTx(holderPrivateKey, holderAddress, cashId, applicant, reason, controller, lockupId);
    }

    @Test
    public void disputeInfo() {
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";


        disputeInfoQuery(cashId, applicant);
    }


    /**
     * Seventeenth: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) sets evidence.
     */
    @Test
    public void setEvidence() {
        // The token holder public key to set evidence.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The evidence description.
        String description = "未收到货，查询不到快递信息";

        // Setting evidence.
        setEvidenceTx(holderPrivateKey, holderAddress, cashId, applicant, description);
    }

    @Test
    public void evidenceInfo() {
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The evidence provider.
        String provider = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        evidenceInfoQuery(cashId, applicant, provider);
    }


    /**
     * Eighteenth: The controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD) handle evidence.
     */
    @Test
    public void handleDispute() {
        // The controller public key to handle dispute.
        String controllerPrivateKey = "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The cash id.
        String cashId = "1";
        // The cash applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The dispute result status.
        int status = 1;
        // The evidence description.
        String description = "确实查询不到快递信息，SKU Tokens 返回给兑付申请人。";

        // Handling dispute.
        handleDisputeTx(controllerPrivateKey, controllerAddress, cashId, applicant, status, description);
    }


    /**
     * Nineteenth: The controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD) lockups 100 SKU Tokens of the account (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP).
     */
    @Test
    public void lockupByTranche() {
        // The controller public key to lockup other's tokens.
        String controllerPrivateKey = "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The account to be lockuped.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The lockup id.
        String lockupId = "1";
        // The cash id.
        String cashId = null;
        // The amount to be lockuped.
        String value = "100";

        // Lockuping the tokens
        lockupByTrancheTx(controllerPrivateKey, controllerAddress, address, skuId, trancheId, lockupId, cashId, value);
    }

    @Test
    public void balanceOfByLockup() {
        // The account address.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The lockup id.
        String lockupId = "1";

        balanceOfByLockupQuery(address, skuId, trancheId, lockupId);
    }


    /**
     * Twentieth: The controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD) unlocks 50 SKU Tokens of the account (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP);
     */
    @Test
    public void unlockByTranche() {
        // The controller public key to unlockup other's tokens.
        String controllerPrivateKey = "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The account to be lockuped.
        String address = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The sku id.
        String skuId = "1";
        // The tranche id.
        String trancheId = "1";
        // The lockup id.
        String lockupId = "1";
        // The amount to be lockuped.
        String value = "50";

        // Unlocking tokens.
        unlockByTrancheTx(controllerPrivateKey, controllerAddress, address, skuId, trancheId, lockupId, value);
    }


    /**
     * Twenty-first: The controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD) redeems 1000 SKU Tokens of the seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC);
     */
    @Test
    public void redeemByTranche() {
        // The controller public key to redeem tokens.
        String controllerPrivateKey = "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The account to be redeemed.
        String address = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens of default tranche in sku will be redeemed.
        String trancheId = "1";
        // The amount to be redeemed.
        String value = "1000";

        // Redeeming the tokens.
        redeemByTrancheTx(controllerPrivateKey, controllerAddress, address, skuId, trancheId, value);
    }

    /**
     * Registering.
     * @return The register tx hash.
     */
    public String registerTx(String sourcePrivateKey, String sourceAddress, String name, String contact, String organizationalCode, String corporateName, String cardNumber, String businessLicense, String cardFrontPhoto, String cardBackPhoto) {
        // The contract account init balance.
        Long initBalance = ToBaseUnit.BU2MO("0.01");
        // The contract codes
        String payLoad = "'use strict';const keys={seller:'seller',spu:'spu_',sku:'sku_token_',document:'document_',balance:'balance_',tranche:'tranche_',lockup:'lockup_',allowance:'allowance_',acceptance:'acceptance_',cash:'cash_',controller:'controller_',dispute:'dispute_',evidence:'evidence_'};const error={INVALID_COMPANY_NAME:{code:20000,msg:'The companyName must be a string and its length must be between 1 and 1024 .'},INVALID_COMPANY_CONTACT:{code:20001,msg:'The companyContact must be a string and its length must be between 1 and 64 .'},INVALID_COMPANY_CERTIFICATION:{code:20002,msg:'The companyCertification is invalid .'},INVALID_METHOD:{code:20003,msg:'The method type is invalid .'},INVALID_DOCUMENT_ID:{code:20004,msg:'The document id must be string and its length must be between 1 and 64 .'},INVALID_NAME:{code:20005,msg:'The name must be string and its length must be between 1 and 1024 .'},INVALID_DOCUMENT_URL:{code:20006,msg:'The document url must be string and its length must be between 1 and 20480 .'},INVALID_HASH_TYPE:{code:20007,msg:'The hash_type must be string and its length must be between 1 and 64 .'},INVALID_HASH:{code:20008,msg:'The hash must be string and its length must be between 1 and 2048 .'},NOT_SELLER_CONTROLLER:{code:20009,msg:'The sender must be seller or controller .'},DOCUMENT_NOT_EXIST:{code:20010,msg:'The specified document does not exist .'},INVALID_TRANCHE_ID:{code:20011,msg:'The tranche id must be string and its length must be between 1 and 64 .'},INVALID_SPU_ID:{code:20012,msg:'The spu id must be string and its length must be between 1 and 64 .'},INVALID_CASH_ADDRESS:{code:20013,msg:'The cash address is invalid .'},INVALID_SPU_TYPE:{code:20015,msg:'The spu type must be string and its length must be between 1 and 64 .'},INVALID_ATTRIBUTES:{code:20016,msg:'The \\'attributes\\' is invalid .'},NOT_SELLER:{code:20017,msg:'The sender should be a seller or controller .'},SPU_EXIST:{code:20018,msg:'The spu already exists .'},SPU_NOT_EXIST:{code:20019,msg:'The spu does not exist .'},INVALID_SKU_ID:{code:20021,msg:'The sku id must be string and its length must be between 1 and 64 .'},CASH_LOCK_ID_EXIST:{code:20023,msg:'The cash has been lockuped .'},CASH_VALUE_NOT_EQUAL_VALUE:{code:20024,msg:'The value of tokens to been lockuped must be equal to value of cash .'},INVALID_TOKEN_SYMBOl:{code:20025,msg:'The token symbol must be string and its length must be between 1 and 16 .'},INVALID_TOKEN_SUPPLY:{code:20026,msg:'The token supply must be bigger than 0 .'},INVALID_TOKEN_DECIMALS:{code:20027,msg:'The token decimals must be between 0 and 8 .'},INVALID_DESCRIPTION:{code:20028,msg:'The description must be string and its length must be between 0 and 64K .'},INVALID_LABEL:{code:20029,msg:'The label must be string and its length must be between 0 and 128 .'},INVALID_SKU_CASH_HASHES:{code:20030,msg:'The cash hash is invalid .'},TRANCHE_NOT_EXIST:{code:20031,msg:'The tranche does not exist .'},SKU_EXIST:{code:20032,msg:'The sku already exists .'},INVALID_SKU_IDS:{code:20033,msg:'The \\'sku_ids\\' is invalid .'},INVALID_ADDRESS:{code:20034,msg:'The address is invalid .'},INVALID_LIMITS:{code:20035,msg:'The \\'limits\\' are invalid .'},TRANCHE_EXIST:{code:20036,msg:'The tranche already exists .'},SKU_NOT_EXIST:{code:20037,msg:'The sku does not exist .'},BALANCE_NOT_EXIST:{code:20038,msg:'The balance of the address is 0 .'},TRANCHE_BALANCE_NOT_EXIST:{code:20039,msg:'The tranche balance of the address is 0 .'},INVALID_LOCKUP_ID:{code:20040,msg:'The lockup id must be string and its length must be between 1 and 64 .'},INVALID_START_TIME:{code:20041,msg:'The startTime must be bigger than 0 .'},INVALID_END_TIME:{code:20042,msg:'The endTime must be bigger than this block time .'},INVALID_UNLOCKERS:{code:20043,msg:'The \\'controllers\\' is invalid .'},START_TIME_NOT_SMALLER_END_TIME:{code:20044,msg:'The startTime should be smaller than the endTime .'},LOCKUP_EXIST:{code:20045,msg:'The lockup already exists .'},LOCKUP_NOT_EXIST:{code:20046,msg:'The lockup does not exist .'},INVALID_VALUE:{code:20047,msg:'The value must be bigger than 0 .'},NOT_CONTROLLER:{code:20048,msg:'The sender must be controller .'},LOCKUP_EXPIRED:{code:20049,msg:'The lockup has expired .'},SELLER_LOCKUP_OTHER:{code:20050,msg:'The seller cannot lockup other\\'s tokens .'},BALANCE_NOT_ENOUGH:{code:20051,msg:'The available balance is not enough .'},LOCKUP_LOCKER_NOT_SAME:{code:20052,msg:'The locker of lockup must be same .'},NO_RIGHT:{code:20053,msg:'The sender hash no right .'},NO_LOCKUP_BALANCE:{code:20054,msg:'The address does not have this lockup .'},BALANCE_LOCKUP_NOT_ENOUGH:{code:20055,msg:'The lockup balance is not enough .'},SELLER_UNLOCK_OTHER:{code:20056,msg:'The seller cannot unlock other\\'s tokens .'},SELLER_REDEEM_OTHER:{code:20057,msg:'The seller cannot redeem other\\'s tokens .'},INVALID_FROM:{code:20058,msg:'The from is a invalid address .'},INVALID_TO:{code:20059,msg:'The to is a invalid address .'},LOCKER_NOT_SELLER:{code:20060,msg:'The seller can only transfer the token of the lockup which is lockuped by the seller .'},TO_LOCKER_NOT_SELLER:{code:20061,msg:'The locker of target lockup is not seller .'},INVALID_SPENDER:{code:20062,msg:'The spender is invalid .'},NO_ALLOWANCE:{code:20063,msg:'The spender does not have the allowance .'},INVALID_ACCEPTANCE_ID:{code:20064,msg:'The acceptance id must be string and its length must be between 1 and 64 .'},INVALID_CONTACT:{code:20065,msg:'The contract must be a string and its length must be between 1 and 64 .'},INVALID_PERIOD:{code:20066,msg:'The period must be a string and its length must be between 1 and 16 .'},ACCEPTANCE_NOT_EXIST:{code:20067,msg:'The acceptance does not exist .'},INVALID_CASH_ID:{code:20068,msg:'The cash id must be string and its length must be between 1 and 64 .'},INVALID_ACCEPTOR:{code:20069,msg:'The acceptor is invalid .'},CASH_EXIST:{code:20070,msg:'The cash already exists .'},INVALID_APPLICANT:{code:20071,msg:'The applicantAddress is invalid .'},CASH_NOT_EXIST:{code:20072,msg:'The cash does not exist .'},LOCKUP_CASH_ID_EXIST:{code:20073,msg:'The lockup of the account is in cash .'},INVALID_CASH_STATUS:{code:20074,msg:'The cash status must be 0 .'},CASH_IN_DISPUTE:{code:20075,msg:'The cash is in dispute .'},NOT_APPLICANT:{code:20076,msg:'The sender should be the applicant of the cash .'},BALANCE_LOCKUP_EXIST:{code:20077,msg:'The lockup which already exists in this account cannot be use in cash and dispute.'},CONTROLLER_NOT_EXIST:{code:20078,msg:'The controller does not exist .'},INVALID_REASON:{code:20079,msg:'The reason must be string and its length must be between 0 and 64K .'},INVALID_CONTROLLER:{code:20080,msg:'The controller is invalid .'},NOT_SELLER_APPLICANT:{code:20081,msg:'The sender must be sellr or cash applicant .'},DISPUTE_EXIST:{code:20082,msg:'The dispute already exists .'},INVALID_STATUS_FOR_DISPUTE:{code:20083,msg:'The dispute cannot be applied when the cash status is not 0 and 1 .'},DISPUTE_NOT_EXIST:{code:20084,msg:'The dispute does not exist .'},NOT_APPLICANT_SELLER_ACCEPTOR:{code:20085,msg:'The provider must be applicant, seller or acceptor .'},INVALID_PROVIDER:{code:20086,msg:'The provider is invalid .'},EVIDENCE_NOT_EXIST:{code:20087,msg:'The evidence does not exist .'},INVALID_STATUS:{code:20088,msg:'The status must be 1 or 2 .'},DISPUTE_FINISH:{code:20089,msg:'The dispute has finished .'},INVALID_OWNER:{code:20090,msg:'The owner is invalid .'},ALLOWANCE_NOT_ENOUGH:{code:20091,msg:'The allowance is not enough .'},NOT_ACCEPTOR:{code:20092,msg:'The sender is not acceptor .'},NOT_DISPUTE_CONTROLLER:{code:20093,msg:'The lockup in dispute can only be unlocked by the controller in dispute .'},LOCKUP_IN_CASH:{code:20094,msg:'The lockup in cash cannot be unlocked .'}};function _throwError(_errorCode){return JSON.stringify(_errorCode);}function _throwErrorMsg(_code,_msg){const definedError={code:_code,msg:_msg};return JSON.stringify(definedError);}function _makeSellerKey(){return`${keys.seller}`;}function _makeSpuKey(_spuId){return`${keys.spu}${_spuId}`;}function _makeSkuKey(_skuId){return`${keys.sku}${_skuId}`;}function _makeTrancheKey(_trancheId){return`${keys.tranche}${_trancheId}`;}function _makeLockupKey(_lockupId){return`${keys.lockup}${_lockupId}`;}function _makeDocumentKey(_documentId){return`${keys.document}${_documentId}`;}function _makeControllerKey(_address){return`${keys.controller}${_address}`;}function _makeBalanceKey(_skuId,_address){return`${keys.balance}${_skuId}_${_address}`;}function _makeBalanceTrancheKey(_skuId,_trancheId,_address){return`${keys.balance}${keys.tranche}${_skuId}_${_trancheId}_${_address}`;}function _makeBalanceLockupKey(_skuId,_trancheId,_lockupId,_address){return`${keys.balance}${keys.lockup}${_skuId}_${_trancheId}_${_lockupId}_${_address}`;}function _makeAllowanceKey(_owner,_skuId,_trancheId,_spender){return`${keys.allowance}${_owner}_${_skuId}_${_trancheId}_${_spender}`;}function _makeAcceptanceKey(_acceptanceId){return`${keys.acceptance}${_acceptanceId}`;}function _makeCashKey(_cashId,_applicant){return`${keys.cash}${_cashId}_${_applicant}`;}function _makeDisputeKey(_cashId,_applicant){return`${keys.dispute}${_cashId}_${_applicant}`;}function _makeEvidenceKey(_cashId,_applicant,_sender){return`${keys.evidence}${_cashId}_${_applicant}_${_sender}`;}function _makeTlogSender(){return`${Chain.tx.sender}_${Chain.msg.sender}`;}function _checkString(_variable,_minLength,_maxLength){return(_minLength===0&&_variable===undefined)||(typeof _variable==='string'&&_variable.length>=_minLength&&_variable.length<=_maxLength);}function _checkInteger(_variable,_min,_max){return Utils.int64Compare(_variable,_min)>=0&&Utils.int64Compare(_variable,_max)<=0;}function _checkJSONObject(_variable){return typeof _variable==='object'&&_variable.length===undefined;}function _checkJSONArray(_variable,_minSize,_maxSize){return(_minSize===0&&_variable===undefined)||(typeof _variable==='object'&&_variable.length>=_minSize&&_variable.length<=_maxSize);}function _checkExist(_key,_errorCode){const value=Chain.load(_key);Utils.assert(value!==false,_throwError(_errorCode));return value;}function _checkNotExist(_key,_errorCode){const value=Chain.load(_key);Utils.assert(value===false,_throwError(_errorCode));}function _checkIsSeller(_address){const seller=JSON.parse(Chain.load(_makeSellerKey()));return(seller.address===_address);}function _checkTrancheId(_defaultTranche,_trancheId){if(_trancheId===undefined){_trancheId=_defaultTranche;}else{Utils.assert(_checkString(_trancheId,1,64),_throwError(error.INVALID_TRANCHE_ID));}return _trancheId;}function _checkSkuTranche(_skuId,_trancheId){if(_trancheId===undefined){const skuTokenKey=_makeSkuKey(_skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);_trancheId=skuToken.defaultTrancheId;}else{Utils.assert(_checkString(_trancheId,1,64),_throwError(error.INVALID_TRANCHE_ID));}return _trancheId;}function _addTrancheBalance(_to,_skuId,_trancheId,_value){const toBalanceKey=_makeBalanceKey(_skuId,_to);const toBalanceValue=Chain.load(toBalanceKey);let toBalance={};toBalance.totalBalance='0';toBalance.availableBalance='0';toBalance.tranches=[];if(toBalanceValue!==false){toBalance=JSON.parse(toBalanceValue);}toBalance.totalBalance=Utils.int64Add(toBalance.totalBalance,_value);toBalance.availableBalance=Utils.int64Add(toBalance.availableBalance,_value);const toBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,_to);const toBalanceTrancheValue=Chain.load(toBalanceTrancheKey);let toBalanceTranche={};toBalanceTranche.totalBalance='0';toBalanceTranche.availableBalance='0';toBalanceTranche.lockups=[];if(toBalanceTrancheValue!==false){toBalanceTranche=JSON.parse(toBalanceTrancheValue);}else{toBalance.tranches.push(_trancheId);}toBalanceTranche.totalBalance=Utils.int64Add(toBalanceTranche.totalBalance,_value);toBalanceTranche.availableBalance=Utils.int64Add(toBalanceTranche.availableBalance,_value);Chain.store(toBalanceTrancheKey,JSON.stringify(toBalanceTranche));Chain.store(toBalanceKey,JSON.stringify(toBalance));}function _checkExpiredLockups(_balanceTranche,_address,_skuId,_cashAddress,_trancheId){let i=0;let lockups=_balanceTranche.lockups;const len=lockups.length;let totalExpiredBalance='0';for(i=len-1;i>=0;i-=1){const lockupId=lockups[i];const lockupValue=Chain.load(_makeLockupKey(lockupId));const lockup=JSON.parse(lockupValue);const balanceLockupKey=_makeBalanceLockupKey(_skuId,_trancheId,lockupId,_address);if(Utils.int64Compare(lockup.endTime,Chain.block.timestamp)<0){const balanceLockupValue=Chain.load(balanceLockupKey);const balanceLockup=JSON.parse(balanceLockupValue);if(balanceLockup.cashId!==undefined){const cashKey=_makeCashKey(balanceLockup.cashId,_address);const cashValue=Chain.load(cashKey);let cash=JSON.parse(cashValue);if(cash.status===1){_addTrancheBalance(_cashAddress,_skuId,_trancheId,cash.value);}}totalExpiredBalance=Utils.int64Add(totalExpiredBalance,balanceLockup.balance);Chain.del(balanceLockupKey);const index=lockups.indexOf(lockupId);lockups.splice(index,1);}}let unexpiredLockups={};unexpiredLockups.lockups=lockups;unexpiredLockups.totalExpiredBalance=totalExpiredBalance;return unexpiredLockups;}function _approveByTranche(_spender,_skuId,_trancheId,_value){Utils.assert(Utils.addressCheck(_spender),_throwError(error.INVALID_SPENDER));Utils.assert(_checkString(_skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.stoI64Check(_value)&&Utils.int64Compare(_value,0)>0,_throwError(error.INVALID_VALUE));const skuTokenKey=_makeSkuKey(_skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);_trancheId=_checkTrancheId(skuToken.defaultTrancheId,_trancheId);_checkExist(_makeTrancheKey(_trancheId),error.TRANCHE_NOT_EXIST);const balanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,Chain.msg.sender);const balanceTrancheValue=_checkExist(balanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);let balanceTranche=JSON.parse(balanceTrancheValue);const unexpiredLocks=_checkExpiredLockups(balanceTranche,Chain.msg.sender,_skuId,skuToken.cashAddress,_trancheId);balanceTranche.availableBalance=Utils.int64Add(balanceTranche.availableBalance,unexpiredLocks.totalExpiredBalance);balanceTranche.lockups=unexpiredLocks.lockups;Utils.assert(Utils.int64Compare(balanceTranche.availableBalance,_value)>=0,_throwError(error.BALANCE_NOT_ENOUGH));const allowanceKey=_makeAllowanceKey(Chain.msg.sender,_skuId,_trancheId,_spender);let allowance=_value;Chain.store(allowanceKey,allowance);}function _checkBalanceEnough(_sender,_from,_skuId,_trancheId,_value,_withDefault){const skuTokenKey=_makeSkuKey(_skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);_trancheId=_checkTrancheId(skuToken.defaultTrancheId,_trancheId);_checkExist(_makeTrancheKey(_trancheId),error.TRANCHE_NOT_EXIST);const fromBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,_from);const fromBalanceTrancheValue=_checkExist(fromBalanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);let fromBalanceTranche=JSON.parse(fromBalanceTrancheValue);let unexpiredLocks=_checkExpiredLockups(fromBalanceTranche,_from,_skuId,skuToken.cashAddress,_trancheId);let availableBalance=Utils.int64Add(fromBalanceTranche.availableBalance,unexpiredLocks.totalExpiredBalance);const isController=Chain.load(_makeControllerKey(_sender));const allowanceKey=_makeAllowanceKey(_from,_skuId,_trancheId,_sender);let allowanceValue='0';if(_sender!==_from&&isController===false){allowanceValue=_checkExist(allowanceKey,error.NO_ALLOWANCE);}if(_sender!==_from&&isController===false){Utils.assert(Utils.int64Compare(allowanceValue,_value)>=0,_throwError(error.ALLOWANCE_NOT_ENOUGH));allowanceValue=Utils.int64Sub(allowanceValue,_value);if(Utils.int64Compare(allowanceValue,0)===0){Chain.del(allowanceKey);}else{Chain.store(allowanceKey,allowanceValue);}}if(Utils.int64Compare(availableBalance,_value)<0&&_withDefault===true){const defaultBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,skuToken.defaultTrancheId,_from);const defaultBalanceTrancheValue=Chain.load(defaultBalanceTrancheKey);let defaultBalanceTranche=JSON.parse(defaultBalanceTrancheValue);const seller=JSON.parse(Chain.load(_makeSellerKey()));if(seller.address===_sender&&_from===seller.address&&_trancheId!==skuToken.defaultTrancheId){availableBalance=Utils.int64Add(availableBalance,defaultBalanceTranche.availableBalance);}}Utils.assert(Utils.int64Compare(availableBalance,_value)>=0,_throwError(error.BALANCE_NOT_ENOUGH));unexpiredLocks.trancheId=_trancheId;unexpiredLocks.defaultTrancheId=skuToken.defaultTrancheId;return unexpiredLocks;}function _subTrancheBalance(_from,_skuId,_unlockedBalance,_value){const fromBalanceKey=_makeBalanceKey(_skuId,_from);const fromBalanceValue=_checkExist(fromBalanceKey,error.BALANCE_NOT_EXIST);const fromBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,_unlockedBalance.trancheId,_from);const fromBalanceTrancheValue=_checkExist(fromBalanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);let fromBalance=JSON.parse(fromBalanceValue);fromBalance.availableBalance=Utils.int64Add(fromBalance.availableBalance,_unlockedBalance.totalExpiredBalance);fromBalance.availableBalance=Utils.int64Sub(fromBalance.availableBalance,_value);fromBalance.totalBalance=Utils.int64Sub(fromBalance.totalBalance,_value);let fromBalanceTranche=JSON.parse(fromBalanceTrancheValue);fromBalanceTranche.availableBalance=Utils.int64Add(fromBalanceTranche.availableBalance,_unlockedBalance.totalExpiredBalance);fromBalanceTranche.lockups=_unlockedBalance.lockups;if(Utils.int64Compare(fromBalanceTranche.availableBalance,_value)<0){const reduceAmount=fromBalanceTranche.availableBalance;fromBalanceTranche.availableBalance='0';fromBalanceTranche.totalBalance=Utils.int64Sub(fromBalanceTranche.totalBalance,reduceAmount);const remainAmount=Utils.int64Sub(_value,fromBalanceTranche.availableBalance);const defaultBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,_unlockedBalance.defaultTrancheId,_from);const defaultBalanceTrancheValue=Chain.load(defaultBalanceTrancheKey);let defaultBalanceTranche=JSON.parse(defaultBalanceTrancheValue);defaultBalanceTranche.availableBalance=Utils.int64Sub(defaultBalanceTranche.availableBalance,remainAmount);defaultBalanceTranche.totalBalance=Utils.int64Sub(defaultBalanceTranche.totalBalance,remainAmount);Chain.store(defaultBalanceTrancheKey,JSON.stringify(defaultBalanceTranche));}else{fromBalanceTranche.availableBalance=Utils.int64Sub(fromBalanceTranche.availableBalance,_value);fromBalanceTranche.totalBalance=Utils.int64Sub(fromBalanceTranche.totalBalance,_value);}if(Utils.int64Compare(fromBalance.totalBalance,0)===0){Chain.del(fromBalanceKey);Chain.del(fromBalanceTrancheKey);}else{if(Utils.int64Compare(fromBalanceTranche.totalBalance,0)===0){Chain.del(fromBalanceTrancheKey);fromBalance.tranches.splice(_unlockedBalance.trancheId,1);}else{Chain.store(fromBalanceTrancheKey,JSON.stringify(fromBalanceTranche));}Chain.store(fromBalanceKey,JSON.stringify(fromBalance));}}function _transferFromByTranche(_from,_skuId,_trancheId,_to,_value){const unlockedBalance=_checkBalanceEnough(Chain.msg.sender,_from,_skuId,_trancheId,_value,true);_subTrancheBalance(_from,_skuId,unlockedBalance,_value);_addTrancheBalance(_to,_skuId,unlockedBalance.trancheId,_value);}function _transfer(_from,_skuId,_trancheId,_to,_amount){Utils.assert(Utils.addressCheck(_from)&&int64Compare(Chain.getBalance(_from),'0')>0,_throwError(error.INVALID_FROM));Utils.assert(Utils.addressCheck(_to)&&int64Compare(Chain.getBalance(_to),'0')>0,_throwError(error.INVALID_TO));Utils.assert(_checkString(_skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.stoI64Check(_amount)&&Utils.int64Compare(_amount,0)>0,_throwError(error.INVALID_VALUE));_transferFromByTranche(_from,_skuId,_trancheId,_to,_amount);}function _lockupByTranche(_sender,_address,_skuId,_unlockedBalance,_lockupId,_cashId,_value){const lockupValue=_checkExist(_makeLockupKey(_lockupId),error.LOCKUP_NOT_EXIST);const lockup=JSON.parse(lockupValue);Utils.assert(Utils.int64Compare(lockup.endTime,Chain.block.timestamp)>=0,_throwError(error.LOCKUP_EXPIRED));const balanceTrancheKey=_makeBalanceTrancheKey(_skuId,_unlockedBalance.trancheId,_address);const balanceTrancheValue=_checkExist(balanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);const balanceKey=_makeBalanceKey(_skuId,_address);const balanceValue=_checkExist(balanceKey,error.BALANCE_NOT_EXIST);let balanceTranche=JSON.parse(balanceTrancheValue);balanceTranche.availableBalance=Utils.int64Add(balanceTranche.availableBalance,_unlockedBalance.totalExpiredBalance);Utils.assert(Utils.int64Compare(balanceTranche.availableBalance,_value)>=0,_throwError(error.BALANCE_NOT_ENOUGH));balanceTranche.availableBalance=Utils.int64Sub(balanceTranche.availableBalance,_value);balanceTranche.lockups=_unlockedBalance.lockups;Chain.store(balanceTrancheKey,JSON.stringify(balanceTranche));let balance=JSON.parse(balanceValue);balance.availableBalance=Utils.int64Add(balance.availableBalance,_unlockedBalance.totalExpiredBalance);balance.availableBalance=Utils.int64Sub(balance.availableBalance,_value);Chain.store(balanceKey,JSON.stringify(balance));const balanceLockupKey=_makeBalanceLockupKey(_skuId,_unlockedBalance.trancheId,_lockupId,_address);const balanceLockupValue=Chain.load(balanceLockupKey);let balanceLockup={};balanceLockup.balance='0';balanceLockup.locker=_sender;if(balanceLockupValue!==false){balanceLockup=JSON.parse(balanceLockupValue);Utils.assert(balanceLockup.cashId===undefined,_throwError(error.LOCKUP_CASH_ID_EXIST));Utils.assert(balanceLockup.locker===_sender,_throwError(error.LOCKUP_LOCKER_NOT_SAME));}else{balanceTranche.lockups.push(_lockupId);}balanceLockup.balance=Utils.int64Add(balanceLockup.balance,_value);balanceLockup.cashId=_cashId;Chain.store(balanceLockupKey,JSON.stringify(balanceLockup));Chain.store(balanceKey,JSON.stringify(balance));Chain.store(balanceTrancheKey,JSON.stringify(balanceTranche));}function _unlockByTranche(_sender,_address,_skuId,_trancheId,_lockupId,_value){const balanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,_address);const balanceTrancheValue=_checkExist(balanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);const balanceKey=_makeBalanceKey(_skuId,_address);const balanceValue=_checkExist(balanceKey,error.BALANCE_NOT_EXIST);let balanceTranche=JSON.parse(balanceTrancheValue);const index=balanceTranche.lockups.indexOf(_lockupId);const balanceLockupKey=_makeBalanceLockupKey(_skuId,_trancheId,_lockupId,_address);let balanceLockupValue=Chain.load(balanceLockupKey);Utils.assert(balanceLockupValue!==false&&index!==-1,_throwError(error.NO_LOCKUP_BALANCE));let balanceLockup=JSON.parse(balanceLockupValue);const disputeKey=_makeDisputeKey(balanceLockup.cashId,_sender);const disputeValue=Chain.load(disputeKey);if(disputeValue!==false){const dispute=JSON.parse(disputeValue);Utils.assert(_sender===dispute.controller,_throwError(error.NOT_DISPUTE_CONTROLLER));}const cashKey=_makeCashKey(balanceLockup.cashId,_sender);_checkNotExist(cashKey,error.LOCKUP_IN_CASH);Utils.assert(Utils.int64Compare(balanceLockup.balance,_value)>=0,_throwError(error.BALANCE_LOCKUP_NOT_ENOUGH));balanceLockup.balance=Utils.int64Sub(balanceLockup.balance,_value);if(Utils.int64Compare(balanceLockup.balance,0)===0){balanceTranche.lockups.splice(index,1);Chain.del(balanceLockupKey);}else{Chain.store(balanceLockupKey,JSON.stringify(balanceLockup));}balanceTranche.availableBalance=Utils.int64Add(balanceTranche.availableBalance,_value);Chain.store(balanceTrancheKey,JSON.stringify(balanceTranche));let balance=JSON.parse(balanceValue);balance.availableBalance=Utils.int64Add(balance.availableBalance,_value);Chain.store(balanceKey,JSON.stringify(balance));}function _redeemByTranche(_address,_skuId,_trancheId,_value){const unlockedBalance=_checkBalanceEnough(_address,_address,_skuId,_trancheId,_value);_subTrancheBalance(_address,_skuId,unlockedBalance,_value);const skuTokenKey=_makeSkuKey(_skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);skuToken.token.totalSupply=Utils.int64Sub(skuToken.token.totalSupply,_value);Chain.store(skuTokenKey,JSON.stringify(skuToken));}function _subLockupBalance(_from,_skuId,_trancheId,_lockupId,_value,_balanceLockupKey,_balanceLockup){const lockupValue=_checkExist(_makeLockupKey(_lockupId),error.LOCKUP_NOT_EXIST);const lockup=JSON.parse(lockupValue);Utils.assert(Utils.int64Compare(lockup.endTime,Chain.block.timestamp)>=0,_throwError(error.LOCKUP_EXPIRED));const balanceKey=_makeBalanceKey(_skuId,_from);const balanceValue=_checkExist(balanceKey,error.BALANCE_NOT_EXIST);let balance=JSON.parse(balanceValue);const balanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,_from);const balanceTrancheValue=_checkExist(balanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);let balanceTranche=JSON.parse(balanceTrancheValue);Utils.assert(Utils.int64Compare(_balanceLockup.balance,_value)>=0,_throwError(error.BALANCE_LOCKUP_NOT_ENOUGH));balance.totalBalance=Utils.int64Sub(balance.totalBalance,_value);if(Utils.int64Compare(balance.totalBalance,'0')===0){Chain.del(_balanceLockupKey);Chain.del(balanceTrancheKey);Chain.del(balanceKey);}else{balanceTranche.totalBalance=Utils.int64Sub(balanceTranche.totalBalance,_value);if(Utils.int64Compare(balanceTranche.totalBalance,'0')===0){Chain.del(_balanceLockupKey);Chain.del(balanceTrancheKey);const trancheIndex=balance.tranche.indexOf(_trancheId);balance.tranche.splice(trancheIndex,1);}else{_balanceLockup.balance=Utils.int64Sub(_balanceLockup.balance,_value);if(Utils.int64Compare(_balanceLockup.balance,'0')===0){Chain.del(_balanceLockupKey);const lockupIndex=balanceTranche.lockups.indexOf(_lockupId);balanceTranche.lockups.splice(lockupIndex,1);}else{Chain.store(_balanceLockupKey,JSON.stringify(_balanceLockup));}balanceTranche.totalBalance=Utils.int64Sub(balanceTranche.totalBalance,_value);Chain.store(balanceTrancheKey,JSON.stringify(balanceTranche));}Chain.store(balanceKey,JSON.stringify(balance));}}function _addLockupBalance(_to,_skuId,_trancheId,_lockupId,_value){const toBalanceKey=_makeBalanceKey(_skuId,_to);const toBalanceValue=Chain.load(toBalanceKey);let toBalance={};toBalance.totalBalance='0';toBalance.availableBalance='0';toBalance.tranches=[];if(toBalanceValue!==false){toBalance=JSON.parse(toBalanceValue);}toBalance.totalBalance=Utils.int64Add(toBalance.totalBalance,_value);const toBalanceTrancheKey=_makeBalanceTrancheKey(_skuId,_trancheId,_to);const toBalanceTrancheValue=Chain.load(toBalanceTrancheKey);let toBalanceTranche={};toBalanceTranche.totalBalance='0';toBalanceTranche.availableBalance='0';toBalanceTranche.lockups=[];if(toBalanceTrancheValue!==false){toBalanceTranche=JSON.parse(toBalanceTrancheValue);}else{toBalance.tranches.push(_trancheId);}toBalanceTranche.totalBalance=Utils.int64Add(toBalanceTranche.totalBalance,_value);const toBalanceLockupKey=_makeBalanceLockupKey(_skuId,_trancheId,_lockupId,_to);const toBalanceLockupValue=Chain.load(toBalanceLockupKey);let toBalanceLockup={};toBalanceLockup.balance='0';toBalanceLockup.locker='';if(toBalanceLockupValue!==false){toBalanceLockup=JSON.parse(toBalanceLockupValue);Utils.assert(_checkIsSeller(toBalanceLockup.locker),_throwError(error.TO_LOCKER_NOT_SELLER));}else{toBalanceTranche.lockups.push(_lockupId);}toBalanceLockup.balance=Utils.int64Add(toBalanceLockup.balance,_value);toBalanceLockup.locker=Chain.msg.sender;Chain.store(toBalanceKey,JSON.stringify(toBalance));Chain.store(toBalanceTrancheKey,JSON.stringify(toBalanceTranche));Chain.store(toBalanceLockupKey,JSON.stringify(toBalanceLockup));}function sellerInfo(){return Chain.load(_makeSellerKey());}function setController(address,name,contact,addition){Utils.assert(Utils.addressCheck(address)&&int64Compare(Chain.getBalance(address),'0')>0,_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(name,1,1024),_throwError(error.INVALID_NAME));Utils.assert(_checkString(contact,1,64),_throwError(error.INVALID_CONTACT));const isSeller=_checkIsSeller(Chain.tx.sender);const isController=Chain.load(_makeControllerKey(Chain.tx.sender));Utils.assert(isSeller!==false||isController!==false,_throwError(error.NOT_SELLER_CONTROLLER));const controllerKey=_makeControllerKey(address);let controller={};controller.name=name;controller.contact=contact;controller.addition=addition;Chain.store(controllerKey,JSON.stringify(controller));Chain.tlog('setController',_makeTlogSender(),address,name,contact);}function controllerInfo(address){Utils.assert(Utils.addressCheck(address)&&int64Compare(Chain.getBalance(address),'0')>0,_throwError(error.INVALID_ADDRESS));const controllerKey=_makeControllerKey(address);const controllerValue=_checkExist(controllerKey,error.CONTROLLER_NOT_EXIST);return controllerValue;}function setDocument(id,name,url,hashType,hash){Utils.assert(_checkString(id,1,64),_throwError(error.INVALID_DOCUMENT_ID));Utils.assert(_checkString(name,1,1024),_throwError(error.INVALID_NAME));Utils.assert(_checkString(url,1,20480),_throwError(error.INVALID_DOCUMENT_URL));Utils.assert(_checkString(hashType,1,64),_throwError(error.INVALID_HASH_TYPE));Utils.assert(_checkString(hash,1,2048),_throwError(error.INVALID_HASH));const isSeller=_checkIsSeller(Chain.tx.sender);const isController=Chain.load(_makeControllerKey(Chain.tx.sender));Utils.assert(isSeller!==false||isController!==false,_throwError(error.NOT_SELLER_CONTROLLER));let documentInfo={};documentInfo.name=name;documentInfo.url=url;documentInfo.hashType=hashType;documentInfo.hash=hash;documentInfo.provider=Chain.tx.sender;Chain.store(_makeDocumentKey(id),JSON.stringify(documentInfo));Chain.tlog('setDocument',_makeTlogSender(),id,name);}function documentInfo(documentId){Utils.assert(_checkString(documentId,1,64),_throwError(error.INVALID_DOCUMENT_ID));const documentValue=_checkExist(_makeDocumentKey(documentId),error.DOCUMENT_NOT_EXIST);return documentValue;}function createSpu(id,name,type,attributes){Utils.assert(_checkString(id,1,64),_throwError(error.INVALID_SPU_ID));Utils.assert(_checkString(name,1,1024),_throwError(error.INVALID_NAME));Utils.assert(_checkString(type,1,64),_throwError(error.INVALID_SPU_TYPE));Utils.assert(_checkJSONObject(attributes)&&_checkJSONArray(attributes.id,6,6),_throwError(error.INVALID_ATTRIBUTES));Utils.assert(_checkIsSeller(Chain.tx.sender),_throwError(error.NOT_SELLER));const spuKey=_makeSpuKey(id);_checkNotExist(spuKey,error.SPU_EXIST);let spu={};spu.name=name;spu.type=type;spu.attributes=attributes;Chain.store(spuKey,JSON.stringify(spu));Chain.tlog('createSpu',_makeTlogSender(),id,name,type);}function spuInfo(spuId){Utils.assert(_checkString(spuId,1,64),_throwError(error.INVALID_SPU_ID));const spuValue=_checkExist(_makeSpuKey(spuId),error.SPU_NOT_EXIST);return spuValue;}function createTranche(id,description,limits){Utils.assert(_checkString(id,1,64),_throwError(error.INVALID_TRANCHE_ID));Utils.assert(_checkString(description,0,64000),_throwError(error.INVALID_DESCRIPTION));Utils.assert(limits===undefined||_checkJSONObject(limits),_throwError(error.INVALID_LIMITS));Utils.assert(_checkIsSeller(Chain.tx.sender),_throwError(error.NOT_SELLER));const trancheKey=_makeTrancheKey(id);_checkNotExist(trancheKey,error.TRANCHE_EXIST);limits=(limits===undefined?{}:limits);let tranche={};tranche.description=description;tranche.limits=limits;Chain.store(trancheKey,JSON.stringify(tranche));Chain.tlog('createTranche',_makeTlogSender(),id);}function trancheInfo(trancheId){Utils.assert(_checkString(trancheId,1,64),_throwError(error.INVALID_TRANCHE_ID));const trancheValue=_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);return trancheValue;}function issueByTranche(skuId,defaultTrancheId,spuId,label,cashAddress,cashHashes,attributes,token){defaultTrancheId=_checkTrancheId('0',defaultTrancheId);Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(spuId,0,64),_throwError(error.INVALID_SPU_ID));Utils.assert(_checkString(token.name,1,1024),_throwError(error.INVALID_NAME));Utils.assert(_checkString(token.symbol,1,16),_throwError(error.INVALID_TOKEN_SYMBOl));Utils.assert(Utils.stoI64Check(token.supply)&&Utils.int64Compare(token.supply,0)>0,_throwError(error.INVALID_TOKEN_SUPPLY));Utils.assert(_checkInteger(token.decimals,0,8),_throwError(error.INVALID_TOKEN_DECIMALS));Utils.assert(_checkString(token.description,0,64000),_throwError(error.INVALID_DESCRIPTION));Utils.assert(_checkString(label,0,128),_throwError(error.INVALID_LABEL));Utils.assert(Utils.addressCheck(cashAddress)&&Utils.int64Compare(Chain.getBalance(cashAddress),0)>0,_throwError(error.INVALID_CASH_ADDRESS));Utils.assert(_checkJSONArray(cashHashes,0),_throwError(error.INVALID_SKU_CASH_HASHES));Utils.assert(_checkJSONObject(attributes)&&_checkJSONArray(attributes.id,6,6),_throwError(error.INVALID_ATTRIBUTES));Utils.assert(_checkIsSeller(Chain.tx.sender),_throwError(error.NOT_SELLER));let spuValue=undefined;if(spuId!==undefined&&spuId.length>0){spuValue=_checkExist(_makeSpuKey(spuId),error.SPU_NOT_EXIST);}_checkExist(_makeTrancheKey(defaultTrancheId),error.TRANCHE_NOT_EXIST);_checkNotExist(_makeSkuKey(skuId),error.SKU_EXIST);let skuInfo={};skuInfo.defaultTrancheId=defaultTrancheId;skuInfo.spuId=spuId;skuInfo.token={};skuInfo.token.mame=token.name;skuInfo.token.symbol=token.symbol;skuInfo.token.totalSupply=token.supply;skuInfo.token.decimals=token.decimals;skuInfo.token.description=token.description;skuInfo.label=label;skuInfo.cashAddress=cashAddress;cashHashes=(cashHashes===undefined?[]:cashHashes);skuInfo.cashHashes=cashHashes;skuInfo.time=Chain.block.timestamp;skuInfo.attributes=attributes;skuInfo.authorization=[];Chain.store(_makeSkuKey(skuId),JSON.stringify(skuInfo));if(spuValue!==undefined){let spu=JSON.parse(spuValue);if(spu.skus===undefined){spu.skus=[];}spu.skus.push(skuId);}let balance={};balance.totalBalance=token.supply;balance.availableBalance=token.supply;balance.tranches=[];balance.tranches.push(defaultTrancheId);Chain.store(_makeBalanceKey(skuId,Chain.tx.sender),JSON.stringify(balance));let trancheBalance={};trancheBalance.totalBalance=token.supply;trancheBalance.availableBalance=token.supply;trancheBalance.lockups=[];Chain.store(_makeBalanceTrancheKey(skuId,defaultTrancheId,Chain.tx.sender),JSON.stringify(trancheBalance));Chain.tlog('issueByTranche',_makeTlogSender(),skuId,defaultTrancheId,spuId,token.name);}function addIssuanceByTranche(skuId,trancheId,supply){Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.stoI64Check(supply),_throwError(error.INVALID_TOKEN_SUPPLY));Utils.assert(_checkIsSeller(Chain.tx.sender),_throwError(error.NOT_SELLER));const skuTokenKey=_makeSkuKey(skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);let skuToken=JSON.parse(skuTokenValue);trancheId=_checkTrancheId(skuToken.defaultTrancheId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);skuToken.token.totalSupply=Utils.int64Add(skuToken.token.totalSupply,supply);Chain.store(skuTokenKey,JSON.stringify(skuToken));const balanceKey=_makeBalanceKey(skuId,Chain.tx.sender);let balance=JSON.parse(Chain.load(balanceKey));balance.totalBalance=Utils.int64Add(balance.totalBalance,supply);balance.availableBalance=Utils.int64Add(balance.availableBalance,supply);Chain.store(balanceKey,JSON.stringify(balance));let newTrancheBalance={};const trancheBalanceKey=_makeBalanceTrancheKey(skuId,trancheId,Chain.tx.sender);const trancheBalanceValue=Chain.load(trancheBalanceKey);if(trancheBalanceValue===false){newTrancheBalance.totalBalance=supply;newTrancheBalance.availableBalance=supply;}else{newTrancheBalance=JSON.parse(trancheBalanceValue);newTrancheBalance.totalBalance=Utils.int64Add(newTrancheBalance.totalBalance,supply);newTrancheBalance.availableBalance=Utils.int64Add(newTrancheBalance.availableBalance,supply);}Chain.store(trancheBalanceKey,JSON.stringify(newTrancheBalance));Chain.tlog('addIssuanceByTranche',_makeTlogSender(),skuId,trancheId,supply);}function authorizeSku(skuIds){Utils.assert(_checkJSONArray(skuIds,1,15),_throwError(error.INVALID_SKU_IDS));let i=0;const len=skuIds.length;for(i=0;i<len;i+=1){const skuId=skuIds[i];Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));const skuTokenKey=_makeSkuKey(skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);let skuTokenInfo=JSON.parse(skuTokenValue);const index=skuTokenInfo.authorization.indexOf(Chain.tx.sender);if(index===-1){skuTokenInfo.authorization.push(Chain.tx.sender);Chain.store(skuTokenKey,JSON.stringify(skuTokenInfo));}}Chain.tlog('authorizeSku',_makeTlogSender(),JSON.stringify(skuIds));}function tokenInfo(skuId){Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));const skuTokenValue=_checkExist(_makeSkuKey(skuId),error.SKU_NOT_EXIST);return skuTokenValue;}function balanceOf(address,skuId){Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));_checkExist(_makeSkuKey(skuId),error.SKU_NOT_EXIST);const balanceKey=_makeBalanceKey(skuId,address);const balanceValue=_checkExist(balanceKey,error.BALANCE_NOT_EXIST);return balanceValue;}function balanceOfByTranche(address,skuId,trancheId){Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));const skuTokenKey=_makeSkuKey(skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);trancheId=_checkTrancheId(skuToken.defaultTrancheId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);const balanceTrancheKey=_makeBalanceTrancheKey(skuId,trancheId,address);const balanceTrancheValue=_checkExist(balanceTrancheKey,error.TRANCHE_BALANCE_NOT_EXIST);return balanceTrancheValue;}function createLockup(id,startTime,endTime,unlockers){Utils.assert(_checkString(id,1,64),_throwError(error.INVALID_LOCKUP_ID));Utils.assert(Utils.stoI64Check(startTime)&&Utils.int64Compare(startTime,0)>0,_throwError(error.INVALID_START_TIME));Utils.assert(Utils.stoI64Check(endTime)&&Utils.int64Compare(endTime,Chain.block.timestamp)>0,_throwError(error.INVALID_END_TIME));Utils.assert(_checkJSONArray(unlockers,0,5),_throwError(error.INVALID_UNLOCKERS));Utils.assert(Utils.int64Compare(startTime,endTime)<0,_throwError(error.START_TIME_NOT_SMALLER_END_TIME));const isSeller=_checkIsSeller(Chain.tx.sender);const isController=Chain.load(_makeControllerKey(Chain.tx.sender));Utils.assert(isSeller!==false||isController!==false,_throwError(error.NOT_SELLER_CONTROLLER));_checkNotExist(_makeLockupKey(id),error.LOCKUP_EXIST);if(unlockers!==undefined&&unlockers.length!==0){const len=unlockers.length;let i=0;for(i=0;i<len;i+=1){const controller=unlockers[i];Utils.assert(Utils.addressCheck(controller)&&Utils.int64Compare(Chain.getBalance(controller),'0')>0,_throwErrorMsg(error.INVALID_UNLOCKERS.code,`The controller(${controller})is invalid.`));}}let lockup={};lockup.startTime=startTime;lockup.endTime=endTime;lockup.creator=Chain.tx.sender;unlockers=(unlockers===undefined?[]:unlockers);lockup.unlockers=unlockers;Chain.store(_makeLockupKey(id),JSON.stringify(lockup));Chain.tlog('createLockup',_makeTlogSender(),id,startTime,endTime,JSON.stringify(unlockers));}function lockupInfo(lockupId){Utils.assert(_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));const lockupValue=_checkExist(_makeLockupKey(lockupId),error.LOCKUP_NOT_EXIST);return lockupValue;}function lockupByTranche(address,skuId,trancheId,lockupId,cashId,value){Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));Utils.assert(_checkString(cashId,0,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.stoI64Check(value)&&Utils.int64Compare(value,0)>0,_throwError(error.INVALID_VALUE));const isSeller=_checkIsSeller(Chain.tx.sender);if(isSeller){Utils.assert(address===Chain.tx.sender,_throwError(error.SELLER_LOCKUP_OTHER));}else{const isController=Chain.load(_makeControllerKey(Chain.tx.sender));Utils.assert(isController!==false,_throwError(error.NOT_CONTROLLER));}let cash={};if(cashId!==undefined){const cashKey=_makeCashKey(cashId,address);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);cash=JSON.parse(cashValue);Utils.assert(cash.lockupId===undefined,_throwError(error.CASH_LOCK_ID_EXIST));Utils.assert(cash.value===value,_throwError(error.CASH_VALUE_NOT_EQUAL_VALUE));}const unlockedBalance=_checkBalanceEnough(address,address,skuId,trancheId,value);_lockupByTranche(Chain.tx.sender,address,skuId,unlockedBalance,lockupId,cashId,value);Chain.tlog('lockupByTranche',_makeTlogSender(),address,`${skuId}_${trancheId}_${lockupId}`,value);}function balanceOfByLockup(address,skuId,trancheId,lockupId){Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));trancheId=_checkSkuTranche(skuId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);_checkExist(_makeLockupKey(lockupId),error.LOCKUP_NOT_EXIST);const balanceLockupKey=_makeBalanceLockupKey(skuId,trancheId,lockupId,address);const balanceLockupValue=_checkExist(balanceLockupKey,error.NO_LOCKUP_BALANCE);return balanceLockupValue;}function unlockByTranche(address,skuId,trancheId,lockupId,value){Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));Utils.assert(Utils.stoI64Check(value)&&Utils.int64Compare(value,0)>0,_throwError(error.INVALID_VALUE));const isController=Chain.load(_makeControllerKey(Chain.tx.sender));const lockupValue=_checkExist(_makeLockupKey(lockupId),error.LOCKUP_NOT_EXIST);const isSeller=_checkIsSeller(Chain.tx.sender);if(isSeller){Utils.assert(address===Chain.tx.sender,_throwError(error.SELLER_UNLOCK_OTHER));}else{const lockup=JSON.parse(lockupValue);Utils.assert(isController!==false||lockup.unlockers.indexOf(Chain.tx.sender)!==-1,_throwError(error.NO_RIGHT));}trancheId=_checkSkuTranche(skuId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);_unlockByTranche(Chain.tx.sender,address,skuId,trancheId,lockupId,value);Chain.tlog('unlockByTranche',_makeTlogSender(),address,`${skuId}_${trancheId}_${lockupId}`,value);}function redeemByTranche(address,skuId,trancheId,value){Utils.assert(Utils.addressCheck(address),_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.stoI64Check(value)&&Utils.int64Compare(value,0)>0,_throwError(error.INVALID_VALUE));const isSeller=_checkIsSeller(Chain.tx.sender);if(isSeller){Utils.assert(address===Chain.tx.sender,_throwError(error.SELLER_REDEEM_OTHER));}else{const isController=Chain.load(_makeControllerKey(Chain.tx.sender));Utils.assert(isController!==false,_throwError(error.NOT_CONTROLLER));}trancheId=_checkSkuTranche(skuId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);_redeemByTranche(address,skuId,trancheId,value);Chain.tlog('redeem',_makeTlogSender(),address,skuId,trancheId,value);}function approveByTranche(spender,skuId,trancheId,value){_approveByTranche(spender,skuId,trancheId,value);Chain.tlog('approveByTranche',_makeTlogSender(),spender,skuId,trancheId,value);}function approve(spender,skuId,value){_approveByTranche(spender,skuId,undefined,value);Chain.tlog('approveByTranche',_makeTlogSender(),spender,skuId,value);}function allowanceByTranche(owner,skuId,trancheId,spender){Utils.assert(Utils.addressCheck(owner),_throwError(error.INVALID_OWNER));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(Utils.addressCheck(spender),_throwError(error.INVALID_SPENDER));trancheId=_checkSkuTranche(skuId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);const allowanceKey=_makeAllowanceKey(owner,skuId,trancheId,spender);const allowanceValue=_checkExist(allowanceKey,error.NO_ALLOWANCE);return allowanceValue;}function allowance(owner,skuId,spender){return allowanceByTranche(owner,skuId,undefined,spender);}function transferFromByTranche(from,skuId,trancheId,to,value){_transfer(from,skuId,trancheId,to,value);Chain.tlog('transferFromByTranche',_makeTlogSender(),from,`${skuId}_${trancheId}`,to,value);}function transferFrom(from,skuId,to,value){_transfer(from,skuId,undefined,to,value);Chain.tlog('transferFrom',_makeTlogSender(),from,skuId,to,value);}function transferByTranche(skuId,trancheId,to,value){_transfer(Chain.msg.sender,skuId,trancheId,to,value);Chain.tlog('transferByTranche',_makeTlogSender(),`${skuId}_${trancheId}`,to,value);}function transfer(skuId,to,value){_transfer(Chain.msg.sender,skuId,undefined,to,value);Chain.tlog('transfer',_makeTlogSender(),skuId,to,value);}function transferByLockup(skuId,trancheId,lockupId,to,value){Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));Utils.assert(Utils.addressCheck(to)&&int64Compare(Chain.getBalance(to),'0')>0,_throwError(error.INVALID_TO));Utils.assert(Utils.stoI64Check(value)&&Utils.int64Compare(value,0)>0,_throwError(error.INVALID_VALUE));const isSeller=_checkIsSeller(Chain.msg.sender);Utils.assert(isSeller,_throwError(error.NOT_SELLER));trancheId=_checkSkuTranche(skuId,trancheId);_checkExist(_makeTrancheKey(trancheId),error.TRANCHE_NOT_EXIST);const balanceLockupKey=_makeBalanceLockupKey(skuId,trancheId,lockupId,Chain.msg.sender);const balanceLockupValue=Chain.load(balanceLockupKey);Utils.assert(balanceLockupValue!==false,_throwError(error.NO_LOCKUP_BALANCE));const balanceLockup=JSON.parse(balanceLockupValue);Utils.assert(_checkIsSeller(balanceLockup.locker),_throwError(error.LOCKER_NOT_SELLER));_subLockupBalance(Chain.msg.sender,skuId,trancheId,lockupId,value,balanceLockupKey,balanceLockup);_addLockupBalance(to,skuId,trancheId,lockupId,value);Chain.tlog('transferByLockup',_makeTlogSender(),`${skuId}_${trancheId}_${lockupId}`,to,value);}function setAcceptance(id,skuIds,trancheId,address,name,contact,period,addition){Utils.assert(_checkString(id,1,64),_throwError(error.INVALID_ACCEPTANCE_ID));Utils.assert(_checkJSONArray(skuIds,1,15),_throwError(error.INVALID_SKU_IDS));Utils.assert(_checkString(trancheId,0,64),_throwError(error.INVALID_TRANCHE_ID));Utils.assert(Utils.addressCheck(address)&&Utils.int64Compare(Chain.getBalance(address),'0')>0,_throwError(error.INVALID_ADDRESS));Utils.assert(_checkString(name,1,1024),_throwError(error.INVALID_NAME));Utils.assert(_checkString(contact,1,64),_throwError(error.INVALID_CONTACT));Utils.assert(_checkString(period,1,16),_throwError(error.INVALID_PERIOD));const isSeller=_checkIsSeller(Chain.msg.sender);Utils.assert(isSeller,_throwError(error.NOT_SELLER));let i=0;const len=skuIds.length;for(i=0;i<len;i+=1){const skuId=skuIds[i];Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));const skuTokenKey=_makeSkuKey(skuId);const skuTokenValue=Chain.load(skuTokenKey);Utils.assert(skuTokenValue!==false,_throwErrorMsg(error.SKU_NOT_EXIST,`The ${skuId}sku does not exist.`));const skuToken=JSON.parse(skuTokenValue);trancheId=_checkTrancheId(skuToken.defaultTrancheId,trancheId);const trancheKey=_makeTrancheKey(trancheId);const trancheValue=Chain.load(trancheKey);Utils.assert(trancheValue!==false,_throwErrorMsg(error.TRANCHE_NOT_EXIST,`The ${trancheId}tranche for ${skuId}sku does not exist.`));}const acceptanceKey=_makeAcceptanceKey(id);let acceptance={};acceptance.id=id;acceptance.skuIds=skuIds;acceptance.trancheId=trancheId;acceptance.address=address;acceptance.name=name;acceptance.contact=contact;acceptance.period=period;acceptance.addition=addition;Chain.store(acceptanceKey,JSON.stringify(acceptance));}function acceptanceInfo(acceptanceId){Utils.assert(_checkString(acceptanceId,1,64),_throwError(error.INVALID_ACCEPTANCE_ID));const accetanceKey=_makeAcceptanceKey(acceptanceId);const accetanceValue=_checkExist(accetanceKey,error.ACCEPTANCE_NOT_EXIST);return accetanceValue;}function requestCash(cashId,skuId,trancheId,value,acceptanceId,lockupId,addition){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(_checkString(skuId,1,64),_throwError(error.INVALID_SKU_ID));Utils.assert(_checkString(trancheId,0,64),_throwError(error.INVALID_TRANCHE_ID));Utils.assert(Utils.stoI64Check(value)&&Utils.int64Compare(value,0)>0,_throwError(error.INVALID_VALUE));Utils.assert(_checkString(acceptanceId,1,64),_throwError(error.INVALID_ACCEPTANCE_ID));Utils.assert(lockupId===undefined||_checkString(lockupId,1,64),_throwError(error.INVALID_LOCKUP_ID));const cashKey=_makeCashKey(cashId,Chain.msg.sender);_checkNotExist(cashKey,error.CASH_EXIST);const acceptanceKey=_makeAcceptanceKey(acceptanceId);_checkExist(acceptanceKey,error.ACCEPTANCE_NOT_EXIST);const unlockedBalance=_checkBalanceEnough(Chain.msg.sender,Chain.msg.sender,skuId,trancheId,value);if(lockupId!==undefined){_lockupByTranche('',Chain.msg.sender,skuId,unlockedBalance,lockupId,cashId,value);}let cash={};cash.skuId=skuId;cash.trancheId=unlockedBalance.trancheId;cash.value=value;cash.acceptanceId=acceptanceId;cash.lockupId=lockupId;cash.status=0;cash.time=Chain.block.timestamp;cash.addition=addition;Chain.store(cashKey,JSON.stringify(cash));}function cash(cashId,applicant){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);let cashJson=JSON.parse(cashValue);Utils.assert(cashJson.status===0,_throwError(error.INVALID_CASH_STATUS));const acceptanceKey=_makeAcceptanceKey(cashJson.acceptanceId);const acceptanceValue=Chain.load(acceptanceKey);const acceptance=JSON.parse(acceptanceValue);Utils.assert(Chain.msg.sender===acceptance.address,_throwError(error.NOT_ACCEPTOR));let disputeKey=_makeDisputeKey(cashId,applicant);_checkNotExist(disputeKey,error.CASH_IN_DISPUTE);cashJson.status=1;Chain.store(cashKey,JSON.stringify(cashJson));}function confirmCash(cashId,applicant){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));Utils.assert(Chain.msg.sender===applicant,_throwError(error.NOT_APPLICANT));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);let cashJson=JSON.parse(cashValue);Utils.assert(cashJson.status===1,_throwError(error.INVALID_CASH_STATUS));let disputeKey=_makeDisputeKey(cashId,applicant);_checkNotExist(disputeKey,error.CASH_IN_DISPUTE);const skuTokenKey=_makeSkuKey(cashJson.skuId);const skuToken=JSON.parse(Chain.load(skuTokenKey));if(cashJson.lockupId!==undefined){_addTrancheBalance(skuToken.cashAddress,cashJson.skuId,cashJson.trancheId,cashJson.value);const balanceLockupKey=_makeBalanceLockupKey(cashJson.skuId,cashJson.trancheId,cashJson.lockupId,applicant);const balanceLockupValue=Chain.load(balanceLockupKey);Utils.assert(balanceLockupValue!==false,_throwError(error.NO_LOCKUP_BALANCE));const balanceLockup=JSON.parse(balanceLockupValue);_subLockupBalance(applicant,cashJson.skuId,cashJson.trancheId,cashJson.lockupId,cashJson.value,balanceLockupKey,balanceLockup);}cashJson.status=2;Chain.store(cashKey,JSON.stringify(cashJson));}function cashInfo(cashId,applicant){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);return cashValue;}function applyDispute(cashId,applicant,reason,controller,lockupId,addition){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));Utils.assert(_checkString(reason,1,64000),_throwError(error.INVALID_REASON));Utils.assert(Utils.addressCheck(controller)&&Utils.int64Compare(Chain.getBalance(controller),'0')>0,_throwError(error.INVALID_CONTROLLER));Utils.assert(_checkIsSeller(Chain.msg.sender)||Chain.msg.sender===applicant,_throwError(error.NOT_SELLER_APPLICANT));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);const cashJson=JSON.parse(cashValue);Utils.assert(cashJson.status===0||cashJson.status===1,_throwError(error.INVALID_STATUS_FOR_DISPUTE));_checkExist(_makeControllerKey(controller),error.CONTROLLER_NOT_EXIST);const disputeKey=_makeDisputeKey(cashId,applicant);_checkNotExist(disputeKey,error.DISPUTE_EXIST);if(lockupId!==undefined&&cashJson.lockupId!==undefined&&lockupId!==cashJson.lockupId){const lockupKey=_makeLockupKey(lockupId);const lockupValue=_checkExist(lockupKey,error.LOCKUP_NOT_EXIST);const lockup=JSON.parse(lockupValue);Utils.assert(Utils.int64Compare(lockup.endTime,Chain.block.timestamp)>=0,_throwError(error.LOCKUP_EXPIRED));const balanceLockupKey=_makeBalanceLockupKey(cashJson.skuId,cashJson.trancheId,lockupId,applicant);_checkNotExist(balanceLockupKey,error.BALANCE_LOCKUP_EXIST);const balanceTrancheKey=_makeBalanceTrancheKey(cashJson.skuId,cashJson.trancheId,applicant);const balanceTrancheValue=Chain.load(balanceTrancheKey);let balanceTranche=JSON.parse(balanceTrancheValue);let balanceLockup={};balanceLockup.balance=cashJson.value;balanceLockup.locker='';balanceTranche.lockups.push(lockupId);balanceLockup.cashId=cashId;Chain.store(balanceLockupKey,JSON.stringify(balanceLockup));Chain.del(_makeBalanceLockupKey(cashJson.skuId,cashJson.trancheId,cashJson.lockupId,applicant));const index=balanceTranche.lockups.indexOf(cashJson.lockupId);balanceTranche.lockups.splice(index,1);Chain.store(balanceTrancheKey,JSON.stringify(balanceTranche));}let dispute={};dispute.applicant=Chain.msg.sender;dispute.reason=reason;dispute.controller=controller;dispute.lockupId=lockupId;dispute.status=0;dispute.time=Chain.block.timestamp;dispute.addition=addition;Chain.store(disputeKey,JSON.stringify(dispute));return;}function setEvidence(cashId,applicant,description,addition){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));Utils.assert(_checkString(description,1,64000),_throwError(error.INVALID_DESCRIPTION));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);const cashJson=JSON.parse(cashValue);const acceptanceKey=_makeAcceptanceKey(cashJson.acceptanceId);const acceptanceValue=Chain.load(acceptanceKey);const acceptance=JSON.parse(acceptanceValue);Utils.assert(Chain.msg.sender===applicant||_checkIsSeller(Chain.msg.sender)||Chain.msg.sender===acceptance.address,_throwError(error.NOT_APPLICANT_SELLER_ACCEPTOR));const disputeKey=_makeDisputeKey(cashId,applicant);_checkExist(disputeKey,error.DISPUTE_NOT_EXIST);const evidenceKey=_makeEvidenceKey(cashId,applicant,Chain.msg.sender);let evidence={};evidence.description=description;evidence.addition=addition;Chain.store(evidenceKey,JSON.stringify(evidence));}function evidenceInfo(cashId,applicant,provider){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));Utils.assert(Utils.addressCheck(provider)&&Utils.int64Compare(Chain.getBalance(provider),'0')>0,_throwError(error.INVALID_PROVIDER));const evidenceKey=_makeEvidenceKey(cashId,applicant,provider);const evidenceValue=_checkExist(evidenceKey,error.EVIDENCE_NOT_EXIST);return evidenceValue;}function handleDispute(cashId,applicant,status,description){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));Utils.assert(status===1||status===2,_throwError(error.INVALID_STATUS));Utils.assert(_checkString(description,1,64000),_throwError(error.INVALID_DESCRIPTION));const cashKey=_makeCashKey(cashId,applicant);const cashValue=_checkExist(cashKey,error.CASH_NOT_EXIST);let cashJson=JSON.parse(cashValue);const disputeKey=_makeDisputeKey(cashId,applicant);const disputeValue=_checkExist(disputeKey,error.DISPUTE_NOT_EXIST);let dispute=JSON.parse(disputeValue);Utils.assert(dispute.controller===Chain.msg.sender,_throwError(error.NOT_CONTROLLER));Utils.assert(dispute.status===0,_throwError(error.DISPUTE_FINISH));let lockupId=undefined;if(dispute.lockupId!==undefined){lockupId=dispute.lockupId;}else if(cashJson.lockupId!==undefined){lockupId=cashJson.lockupId;}if(lockupId!==undefined&&status===1){_unlockByTranche(Chain.msg.sender,applicant,cashJson.skuId,cashJson.trancheId,lockupId,cashJson.value);cashJson.status=3;}if(lockupId!==undefined&&status===2){const skuTokenKey=_makeSkuKey(cashJson.skuId);const skuTokenValue=_checkExist(skuTokenKey,error.SKU_NOT_EXIST);const skuToken=JSON.parse(skuTokenValue);_addTrancheBalance(skuToken.cashAddress,cashJson.skuId,cashJson.trancheId,cashJson.value);const balanceLockupKey=_makeBalanceLockupKey(cashJson.skuId,cashJson.trancheId,lockupId,applicant);const balanceLockupValue=Chain.load(balanceLockupKey);Utils.assert(balanceLockupValue!==false,_throwError(error.NO_LOCKUP_BALANCE));const balanceLockup=JSON.parse(balanceLockupValue);_subLockupBalance(applicant,cashJson.skuId,cashJson.trancheId,lockupId,cashJson.value,balanceLockupKey,balanceLockup);cashJson.status=2;}dispute.status=status;Chain.store(disputeKey,JSON.stringify(dispute));Chain.store(cashKey,JSON.stringify(cashJson));return;}function disputeInfo(cashId,applicant){Utils.assert(_checkString(cashId,1,64),_throwError(error.INVALID_CASH_ID));Utils.assert(Utils.addressCheck(applicant)&&Utils.int64Compare(Chain.getBalance(applicant),'0')>0,_throwError(error.INVALID_APPLICANT));const disputeKey=_makeDisputeKey(cashId,applicant);const disputeValue=_checkExist(disputeKey,error.DISPUTE_NOT_EXIST);return disputeValue;}function init(bar){let params=JSON.parse(bar);Utils.assert(_checkString(params.companyName,1,1024),_throwError(error.INVALID_COMPANY_NAME));Utils.assert(_checkString(params.companyContact,1,64),_throwError(error.INVALID_COMPANY_CONTACT));Utils.assert(_checkJSONObject(params.companyCertification)&&_checkJSONArray(params.companyCertification.id,6,6),_throwError(error.INVALID_COMPANY_CERTIFICATION));params.address=Chain.tx.sender;params.version='ATP60';const sellerKey=_makeSellerKey();Chain.store(sellerKey,JSON.stringify(params));const defaultTranche={\"description\":\"default\"};Chain.store(_makeTrancheKey('0'),JSON.stringify(defaultTranche));Chain.tlog('init',_makeTlogSender(),params.companyName,params.companyContact);}function main(input){const data=JSON.parse(input);const method=data.method||'';const params=data.params||{};switch(method){case'setController':setController(params.address,params.name,params.contact,params.addition);break;case'setDocument':setDocument(params.id,params.name,params.url,params.hashType,params.hash);break;case'createSpu':createSpu(params.id,params.name,params.type,params.attributes);break;case'issueByTranche':issueByTranche(params.skuId,params.defaultTrancheId,params.spuId,params.label,params.cashAddress,params.cashHashes,params.attributes,params.token);break;case'addIssuanceByTranche':addIssuanceByTranche(params.skuId,params.trancheId,params.supply);break;case'authorizeSku':authorizeSku(params.skuIds);break;case'createTranche':createTranche(params.id,params.description,params.limits);break;case'createLockup':createLockup(params.id,params.startTime,params.endTime,params.unlockers);break;case'lockupByTranche':lockupByTranche(params.address,params.skuId,params.trancheId,params.lockupId,params.cashId,params.value);break;case'unlockByTranche':unlockByTranche(params.address,params.skuId,params.trancheId,params.lockupId,params.value);break;case'redeemByTranche':redeemByTranche(params.address,params.skuId,params.trancheId,params.value);break;case'transferFromByTranche':transferFromByTranche(params.from,params.skuId,params.trancheId,params.to,params.value);break;case'transferFrom':transferFrom(params.from,params.skuId,params.to,params.value);break;case'transferByTranche':transferByTranche(params.skuId,params.trancheId,params.to,params.value);break;case'transfer':transfer(params.skuId,params.to,params.value);break;case'transferByLockup':transferByLockup(params.skuId,params.trancheId,params.lockupId,params.to,params.value);break;case'approve':approve(params.spender,params.skuId,params.value);break;case'approveByTranche':approveByTranche(params.spender,params.skuId,params.trancheId,params.value);break;case'setAcceptance':setAcceptance(params.id,params.skuIds,params.trancheId,params.address,params.name,params.contact,params.period,params.addition);break;case'requestCash':requestCash(params.cashId,params.skuId,params.trancheId,params.value,params.acceptanceId,params.lockupId,params.addition);break;case'cash':cash(params.cashId,params.applicant);break;case'confirmCash':confirmCash(params.cashId,params.applicant);break;case'applyDispute':applyDispute(params.cashId,params.applicant,params.reason,params.controller,params.lockupId,params.addition);break;case'handleDispute':handleDispute(params.cashId,params.applicant,params.status,params.description);break;case'setEvidence':setEvidence(params.cashId,params.applicant,params.description,params.addition);break;default:throw _throwError(error.INVALID_METHOD);}}function query(input){const data=JSON.parse(input);const method=data.method||'';const params=data.params||{};let value=null;switch(method){case'sellerInfo':value=sellerInfo();break;case'controllerInfo':value=controllerInfo(params.address);break;case'documentInfo':value=documentInfo(params.documentId);break;case'spuInfo':value=spuInfo(params.spuId);break;case'tokenInfo':value=tokenInfo(params.skuId);break;case'trancheInfo':value=trancheInfo(params.trancheId);break;case'balanceOf':value=balanceOf(params.address,params.skuId);break;case'balanceOfByTranche':value=balanceOfByTranche(params.address,params.skuId,params.trancheId);break;case'lockupInfo':value=lockupInfo(params.lockupId);break;case'balanceOfByLockup':value=balanceOfByLockup(params.address,params.skuId,params.trancheId,params.lockupId);break;case'allowance':value=allowance(params.owner,params.skuId,params.spender);break;case'allowanceByTranche':value=allowanceByTranche(params.owner,params.skuId,params.trancheId,params.spender);break;case'acceptanceInfo':value=acceptanceInfo(params.acceptanceId);break;case'cashInfo':value=cashInfo(params.cashId,params.applicant);break;case'disputeInfo':value=disputeInfo(params.cashId,params.applicant);break;case'evidenceInfo':value=evidenceInfo(params.cashId,params.applicant,params.provider);break;default:throw _throwError(error.INVALID_METHOD);}return value;}";
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 10.03BU
        Long feeLimit = ToBaseUnit.BU2MO("11");

        // 1. Getting the account nonce, and the nonce must add 1.
        Long nonce = getAccountNonce(sourceAddress) + 1;

        // 2. Building the initInput
        JSONObject initInput = new JSONObject();
        initInput.put("companyName", name);
        initInput.put("companyContact", contact);
        JSONObject companyCertification = new JSONObject();
        companyCertification.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        companyCertification.put("1", buildAdditionIndex("0", "企业组织机构代码", "text", organizationalCode, "-", "-"));
        companyCertification.put("2", buildAdditionIndex("0", "法人名称", "text", corporateName, "-", "-"));
        companyCertification.put("3", buildAdditionIndex("0", "法人身份证号", "text", cardNumber, "-", "-"));
        companyCertification.put("4", buildAdditionIndex("0", "营业执照照片", "image", businessLicense, "-", "-"));
        companyCertification.put("5", buildAdditionIndex("0", "法人身份证正面照片", "image", cardFrontPhoto, "-", "-"));
        companyCertification.put("6", buildAdditionIndex("0", "法人身份证反面照片", "image", cardBackPhoto, "-", "-"));
        initInput.put("companyCertification", companyCertification);

        // 3. Building ContractCreateOperation.
        ContractCreateOperation operation = new ContractCreateOperation();
        operation.setSourceAddress(sourceAddress);
        operation.setInitBalance(initBalance);
        operation.setPayload(payLoad);
        operation.setInitInput(initInput.toJSONString());


        BaseOperation[] operations = { operation };

        // 4. Broadcasting the transaction
        String txHash = broadcastTransaction(sourcePrivateKey, sourceAddress, operations, nonce, gasPrice, feeLimit, null);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Getting the contract address.
     * @return The contract address.
     */
    public String getContractAddressQuery() {
        // Getting the register tx hash.
        String registerTxHash = "5352496f2262afc8ff1cff814717f0f39b39a99827f7c9db26264276b600c2f9"; //registerTx();

        // Making sure the register tx success.
        if (!MakeSureTxSuccess(registerTxHash)) {
            System.out.println("The register tx runs failed!");
            return null;
        }

        // Initializing the request.
        ContractGetAddressRequest request = new ContractGetAddressRequest();
        request.setHash(registerTxHash);

        // Getting the contract address.
        ContractGetAddressResponse response = sdk.getContractService().getAddress(request);
        String address = null;
        if (response.getErrorCode() == 0) {
            address = response.getResult().getContractAddressInfos().get(0).getContractAddress();
            System.out.println("Contract address: " + address);
        } else {
            System.out.println("Error: " + response.getErrorDesc());
        }

        return address;
    }

    public String sellInfoQuery() {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "sellerInfo");

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Setting document.
     * @return The tx hash.
     */
    public String setDocumentTx(String sourcePrivateKey, String sourceAddress, String documentId, String documentName, String documentUrl, String documentHashType, String documentHash) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1");

        // 1. Building the input of 'setDocument'.
        JSONObject input = new JSONObject();
        input.put("method", "setDocument");
        JSONObject params = new JSONObject();
        params.put("id", documentId);
        params.put("name", documentName);
        params.put("url", documentUrl);
        params.put("hashType", documentHashType);
        params.put("hash", documentHash);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the document information.
     * @return The document information.
     */
    public String documentInfoQuery(String documentId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "documentInfo");
        JSONObject params = new JSONObject();
        params.put("documentId", documentId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Creating the SPU.
     * @return The hash.
     */
    public String createSPUTx(String sourcePrivateKey, String sourceAddress, String spuId, String spuName, String spuType, String spuPrice, String spuBrand, String spuModel) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");


        // 1. Building the input of 'createSpu'.
        JSONObject input = new JSONObject();
        input.put("method", "createSpu");
        JSONObject params = new JSONObject();
        params.put("id", spuId);
        params.put("name", spuName);
        params.put("type", spuType);
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0", "参考号", "float", spuPrice, "2", "CNY"));
        attributes.put("2", buildAdditionIndex("0", "品牌", "text", spuBrand, "-", "-"));
        attributes.put("3", buildAdditionIndex("0", "型号", "text", spuModel, "-", "-"));
        params.put("attributes", attributes);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the spu information.
     * @return The spu information.
     */
    public String SPUInfoQuery(String spuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "spuInfo");
        JSONObject params = new JSONObject();
        params.put("spuId", spuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Creating the tranche.
     * @return The tx hash.
     */
    public String createTrancheTx(String sourcePrivateKey, String sourceAddress, String trancheId, String trancheDesc, String startTime, String endTime) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("2");

        // 1. Building the input of 'createTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "createTranche");
        JSONObject params = new JSONObject();
        params.put("id", trancheId);
        params.put("description", trancheDesc);
        JSONObject limits = new JSONObject();
        JSONObject validityPeriod = new JSONObject();
        validityPeriod.put("startTime", startTime);
        validityPeriod.put("endTime", endTime);
        limits.put("validityPeriod", validityPeriod);
        params.put("limits", limits);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the tranche information.
     * @return The tranche information.
     */
    public String trancheInfoQuery(String trancheId) {
         // Init input
        JSONObject input = new JSONObject();
        input.put("method", "trancheInfo");
        JSONObject params = new JSONObject();
        params.put("trancheId", trancheId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Issuing the SKU tokens
     * @return The tx hash.
     */
    public String issueSKUTokensTx(String sourcePrivateKey, String sourceAddress, String skuId, String defaultTrancheId, String spuId, String skuLabel, String cashAddress, String skuPrice, String skuColor, String skuMemory, String skuModel, String tokenName, String tokenSymbol, String tokenSupply, int decimals, String tokenDesc) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'issueByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "issueByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("defaultTrancheId", defaultTrancheId);
        params.put("spuId", spuId);
        params.put("label", skuLabel);
        params.put("cashAddress", cashAddress);
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0",         "参考价", "int",  skuPrice,     "-",       "CNY"));
        attributes.put("2", buildAdditionIndex("0",         "颜色",  "text",  skuColor,   "-",        "-"));
        attributes.put("3", buildAdditionIndex("0",         "内存",  "int",   skuMemory,       "-",        "G"));
        attributes.put("4", buildAdditionIndex("0",         "型号",  "text",  skuModel, "-",       "-"));
        params.put("attributes", attributes);
        JSONObject token = new JSONObject();
        token.put("name", tokenName);
        token.put("symbol", tokenSymbol);
        token.put("supply", tokenSupply);
        token.put("decimals", decimals);
        token.put("description", tokenDesc);
        params.put("token", token);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Adding the issuance of SKU Tokens
     * @return The tx hash.
     */
    public String addIssuanceByTrancheTx(String sourcePrivateKey, String sourceAddress, String skuId, String trancheId, String tokenSupply) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'addIssuanceByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "addIssuanceByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("supply", tokenSupply);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Autorizing the issuance of sku tokens.
     * @return The tx hash.
     */
    public String autorizeSKUTx(String sourcePrivateKey, String sourceAddress, JSONArray skuIds) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'authorizeSku'.
        JSONObject input = new JSONObject();
        input.put("method", "authorizeSku");
        JSONObject params = new JSONObject();
        params.put("skuIds", skuIds);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the SKU Token information.
     * @return The SKU Token information
     */
    public String SKUTokenInfoQuery(String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "tokenInfo");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Setting controller.
     * @return The tx hash.
     */
    public String setControllerTx(String sourcePrivateKey, String sourceAddress, String controllerAddress, String controllerName, String controllerContact) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 3. Building the input of 'setController'.
        JSONObject input = new JSONObject();
        input.put("method", "setController");
        JSONObject params = new JSONObject();
        params.put("address", controllerAddress);
        params.put("name", controllerName);
        params.put("contact", controllerContact);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the controller information.
     * @return The controller information.
     */
    public String controllerInfoQuery(String controllerAddress) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "controllerInfo");
        JSONObject params = new JSONObject();
        params.put("address", controllerAddress);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Creating the lockup.
     * @return The tx hash.
     */
    public String createLockupTx(String sourcePrivateKey, String sourceAddress, String lockupId, String startTime, String endTime, String unlocker) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'createLockup'.
        JSONObject input = new JSONObject();
        input.put("method", "createLockup");
        JSONObject params = new JSONObject();
        params.put("id", lockupId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        JSONArray unlockers = new JSONArray();
        unlockers.add(unlocker);
        params.put("unlockers", unlockers);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the lockup information.
     * @return The lockup information.
     */
    public String lockupInfoQuery(String lockupId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "lockupInfo");
        JSONObject params = new JSONObject();
        params.put("lockupId", lockupId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Lockuping the tokens
     * @param lockupId The lockup id.
     * @param cashId The cash id.
     * @return The tx hash.
     */
    public String lockupByTrancheTx(String sourcePrivateKey, String sourceAddress, String toAddress, String skuId, String trancheId, String lockupId, String cashId, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'lockupByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "lockupByTranche");
        JSONObject params = new JSONObject();
        params.put("address", toAddress);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("lockupId", lockupId);
        if (!Tools.isEmpty(cashId)) {
            params.put("cashId", cashId);
        }
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the balance of specified lockup.
     * @return The balance.
     */
    public String balanceOfByLockupQuery(String address, String skuId, String trancheId, String lockupId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOfByLockup");
        JSONObject params = new JSONObject();
        params.put("address", address);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("lockupId", lockupId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Unlock the tokens of specified tranche.
     * @param lockupId The lockup id.
     * @return The tx hash.
     */
    public String unlockByTrancheTx(String sourcePrivateKey, String sourceAddress, String toAddress, String skuId, String trancheId, String lockupId, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");


        // 1. Building the input of 'unlockByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "unlockByTranche");
        JSONObject params = new JSONObject();
        params.put("address", toAddress);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("lockupId", lockupId);
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Transferring the sku tokens of specified tranche to other account.
     * @return The tx hash.
     */
    public String transferByTrancheTx(String sourcePrivateKey, String sourceAddress, String skuId, String trancheId, String toAddress, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'transferByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "transferByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("to", toAddress);
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the balance of an account.
     * @return The balance.
     */
    public String balanceOfQuery(String address, String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOf");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Querying the balance of specified tranche.
     * @return The tranche balance.
     */
    public String balanceOfByTrancheQuery(String address, String skuId, String trancheId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "balanceOfByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("address", address);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Approving the SKU Tokens to spender.
     * @return The tx hash.
     */
    public String approveTx(String sourcePrivateKey, String sourceAddress, String skuId, String spender, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'approve'.
        JSONObject input = new JSONObject();
        input.put("method", "approve");
        JSONObject params = new JSONObject();
        params.put("spender", spender);
        params.put("skuId", skuId);
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the allowance.
     * @return The allowance.
     */
    public String allowanceQuery(String owner, String spender, String skuId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "allowance");
        JSONObject params = new JSONObject();
        params.put("owner", owner);
        params.put("spender", spender);
        params.put("skuId", skuId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The spender move the allownce to other account.
     * @return The tx hash.
     */
    public String transferFromByTrancheTx(String sourcePrivateKey, String sourceAddress, String fromAddress, String skuId, String trancheId, String toAddress, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'transferFromByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "transferFromByTranche");
        JSONObject params = new JSONObject();
        params.put("from", fromAddress);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("to", toAddress);
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Setting an accetance.
     * @return The tx hash.
     */
    public String setAcceptanceTx(String sourcePrivateKey, String sourceAddress, String acceptanceId, JSONArray skuIds, String trancheId, String acceptorAddress, String acceptanceName, String acceptanceContact, String acceptancePeriod) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'setAcceptance'.
        JSONObject input = new JSONObject();
        input.put("method", "setAcceptance");
        JSONObject params = new JSONObject();
        params.put("id", acceptanceId);
        params.put("skuIds", skuIds);
        params.put("trancheId", trancheId);
        params.put("address", acceptorAddress);
        params.put("name", acceptanceName);
        params.put("contact", acceptanceContact);
        params.put("period", acceptancePeriod);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the acceptance information.
     * @return The acceptance information.
     */
    public String acceptanceInfoQuery(String acceptanceId) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "acceptanceInfo");
        JSONObject params = new JSONObject();
        params.put("acceptanceId", acceptanceId);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Requesting the cash.
     * @return The tx hash.
     */
    public String requestCashTx(String sourcePrivateKey, String sourceAddress, String cashId, String skuId, String trancheId, String value, String acceptanceId, String lockupId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'requestCash'.
        JSONObject input = new JSONObject();
        input.put("method", "requestCash");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("value", value);
        params.put("acceptanceId", acceptanceId);
        params.put("lockupId", lockupId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the cash information.
     * @return The cash information.
     */
    public String cashInfoQuery(String cashId, String applicant) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "cashInfo");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The acceptor pays cash.
     * @return The tx hash.
     */
    public String cashTx(String sourcePrivateKey, String sourceAddress, String cashId, String applicant) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'cash'.
        JSONObject input = new JSONObject();
        input.put("method", "cash");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * The token holder confirm the cash right.
     * @return The tx hash.
     */
    public String confirmCashTx(String  sourcePrivateKey, String sourceAddress, String cashId, String applicant) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'confirmCash'.
        JSONObject input = new JSONObject();
        input.put("method", "confirmCash");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * The seller or the cash applicant applys dispute.
     * @return The tx hash.
     */
    public String applyDisputeTx(String sourcePrivateKey, String sourceAddress, String cashId, String applicant, String reason, String controller, String lockupId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'applyDispute'.
        JSONObject input = new JSONObject();
        input.put("method", "applyDispute");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        params.put("reason", reason);
        params.put("controller", controller);
        params.put("lockupId", lockupId);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the dispute information.
     * @return The dispute information.
     */
    public String disputeInfoQuery(String cashId, String applicant) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "disputeInfo");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The seller, cash applicant or acceptor can set evidence.
     * @return The tx hash.
     */
    public String setEvidenceTx(String sourcePrivateKey, String sourceAddress, String cashId, String applicant, String description) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'setEvidence'.
        JSONObject input = new JSONObject();
        input.put("method", "setEvidence");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        params.put("description", description);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Querying the evidence information.
     * @return The evidence information.
     */
    public String evidenceInfoQuery(String cashId, String applicant, String provider) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "evidenceInfo");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        params.put("provider", provider);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * Handling the dispute according to the evidence.
     * @return The tx hash.
     */
    public String handleDisputeTx(String sourcePrivateKey, String sourceAddress, String cashId, String applicant, int status, String description) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'handleDispute'.
        JSONObject input = new JSONObject();
        input.put("method", "handleDispute");
        JSONObject params = new JSONObject();
        params.put("cashId", cashId);
        params.put("applicant", applicant);
        params.put("status", status);
        params.put("description", description);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Redeeming the tokens.
     * @return The tx hash.
     */
    public String redeemByTrancheTx(String sourcePrivateKey, String sourceAddress, String toAddress, String skuId, String trancheId, String value) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'redeemByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "redeemByTranche");
        JSONObject params = new JSONObject();
        params.put("address", toAddress);
        params.put("skuId", skuId);
        params.put("trancheId", trancheId);
        params.put("value", value);
        input.put("params", params);

        // 2. Submitting the transaction.
        String txHash = submitTrasaction(sourcePrivateKey, sourceAddress, input.toJSONString(), null, gasPrice, feeLimit);
        if (txHash != null) {
            System.out.println("Success, hash: " + txHash);
        }

        return txHash;
    }


    /**
     * Getting the nonce of an account.
     * @param address The address of an account.
     * @return long
     */
    public long getAccountNonce(String address) {
        long nonce = 0;
        // Init request
        AccountGetNonceRequest request = new AccountGetNonceRequest();
        request.setAddress(address);

        // Call getNonce
        AccountGetNonceResponse response = sdk.getAccountService().getNonce(request);
        if (0 == response.getErrorCode()) {
            nonce = response.getResult().getNonce();
        }

        return nonce;
    }


    /**
     * Building custom attribute data
     * @param parentId The id of parent
     * @param name     The name of attribute
     * @param type     The type of attribute
     * @param value    The data of attribute
     * @param decimals The decimals of data
     * @param unit     The uint of data
     * @return JSONArray
     */
	public JSONArray buildAdditionIndex(String parentId, String name, String type, String value, String decimals, String unit) {
		JSONArray index = new JSONArray();
        index.add(parentId);
        index.add(name);
        index.add(type);
        index.add(value);
        index.add(decimals);
        index.add(unit);
        return index;
	}


    /**
     * Querying information on chain.
     * @param input The query function parameter in contract.
     * @return The information.
     */
	public String queryInfo(String input) {
        // The contract address.
        String contractAddress = getContractAddressQuery();

        // Init request
        ContractCallRequest request = new ContractCallRequest();
        request.setContractAddress(contractAddress);
        request.setFeeLimit(1000000000L);
        request.setOptType(2);
        request.setInput(input);

        // Call call
        JSONObject queryResult = new JSONObject();
        ContractCallResponse response = sdk.getContractService().call(request);
        if (response.getErrorCode() == 0) {
            ContractCallResult result = response.getResult();
            JSONObject errorResult = result.getQueryRets().getJSONObject(0).getJSONObject("error");
            if (errorResult != null) {
                String dataException = errorResult.getJSONObject("data").getString("exception");
                queryResult = JSON.parseObject(dataException);
                System.out.println("Error: " + queryResult.getString("msg"));
            } else {
                queryResult.put("code", 0);
                queryResult.put("msg", result.getQueryRets().getJSONObject(0).getJSONObject("result").getString("value"));
                System.out.println(queryResult.getString("msg"));
            }
        } else {
            queryResult.put("code", response.getErrorCode());
            queryResult.put("msg", response.getErrorDesc());
            System.out.println("error: " + response.getErrorDesc());
        }

        return queryResult.toJSONString();
    }


    /**
     * Submitting transaction.
     * @param privateKey The source public key to submit transaction.
     * @param sourceAddress The source address to submit transaction.
     * @param input The main function parameter in contract.
     * @param transMetadata The transaction metadata.
     * @param gasPrice The gas price.
     * @param feeLimit The fee limit.
     * @return The tx hash.
     */
	public String submitTrasaction(String privateKey, String sourceAddress, String input, String transMetadata, Long gasPrice, Long feeLimit) {
        // 1. Transaction initiation account's Nonce + 1
        Long nonce = getAccountNonce(sourceAddress) + 1;


        // 2. Getting the contract address.
        String contractAddress = getContractAddressQuery();

        // 3. Building ContractInvokeByBUOperation
        ContractInvokeByBUOperation operation = new ContractInvokeByBUOperation();
        operation.setContractAddress(contractAddress);
        operation.setInput(input);
        operation.setBuAmount(0L);

        BaseOperation[] operations = { operation };

        // 4. Broadcasting the transaction
        String txHash = broadcastTransaction(privateKey, sourceAddress, operations, nonce, gasPrice, feeLimit, transMetadata);

        return txHash;
    }


    /**
     * @param senderPrivateKey The account public key to start transaction
     * @param senderAddresss   The account address to start transaction
     * @param operations       operations
     * @param senderNonce      Transaction initiation account's Nonce
     * @param gasPrice         Gas price
     * @param feeLimit         fee limit
     * @return java.lang.String transaction hash
     * @author riven
     */
    public String broadcastTransaction(String senderPrivateKey, String senderAddresss, BaseOperation[] operations, Long senderNonce, Long gasPrice, Long feeLimit, String transMetadata) {
        // 1. Build transaction
        TransactionBuildBlobRequest transactionBuildBlobRequest = new TransactionBuildBlobRequest();
        transactionBuildBlobRequest.setSourceAddress(senderAddresss);
        transactionBuildBlobRequest.setNonce(senderNonce);
        transactionBuildBlobRequest.setFeeLimit(feeLimit);
        transactionBuildBlobRequest.setGasPrice(gasPrice);
        for (int i = 0; i < operations.length; i++) {
            transactionBuildBlobRequest.addOperation(operations[i]);
        }

        transactionBuildBlobRequest.setMetadata(transMetadata);

        // 2. Build transaction BLob
        String transactionBlob;
        TransactionBuildBlobResponse transactionBuildBlobResponse = sdk.getTransactionService().buildBlob(transactionBuildBlobRequest);
        if (transactionBuildBlobResponse.getErrorCode() != 0) {
            System.out.println("Error: " + transactionBuildBlobResponse.getErrorDesc());
            return null;
        }
        TransactionBuildBlobResult transactionBuildBlobResult = transactionBuildBlobResponse.getResult();
        transactionBlob = transactionBuildBlobResult.getTransactionBlob();

        // 3. Sign transaction BLob
        String[] signerPrivateKeyArr = {senderPrivateKey};
        TransactionSignRequest transactionSignRequest = new TransactionSignRequest();
        transactionSignRequest.setBlob(transactionBlob);
        for (int i = 0; i < signerPrivateKeyArr.length; i++) {
            transactionSignRequest.addPrivateKey(signerPrivateKeyArr[i]);
        }
        TransactionSignResponse transactionSignResponse = sdk.getTransactionService().sign(transactionSignRequest);
        if (transactionSignResponse.getErrorCode() != 0) {
            System.out.println("Error: " + transactionSignResponse.getErrorDesc());
            return null;
        }

        // 4. Broadcast transaction
        String Hash = null;
        TransactionSubmitRequest transactionSubmitRequest = new TransactionSubmitRequest();
        transactionSubmitRequest.setTransactionBlob(transactionBlob);
        transactionSubmitRequest.setSignatures(transactionSignResponse.getResult().getSignatures());
        TransactionSubmitResponse transactionSubmitResponse = sdk.getTransactionService().submit(transactionSubmitRequest);
        if (0 == transactionSubmitResponse.getErrorCode()) {
            Hash = transactionSubmitResponse.getResult().getHash();
        } else {
            System.out.println("Error: " + transactionSubmitResponse.getErrorDesc());
        }
        return Hash;
    }

    /**
     * Getting the tx status.
     * @param txHash The hash of a tx.
     * @return 1 Success, 0 Failure, -1 Timeout.
     */
    public JSONObject getTxStatus(String txHash) {
        long startTime = System.currentTimeMillis();
        JSONObject txStatus;
        while (true) {
            JSONObject result = checkTxStatusByHash(txHash);
            int code = result.getIntValue("code");
            if (0 == code || code != 4) {
                txStatus = result;
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 50000) {
                txStatus = result;
                txStatus.put("code", -1);
                txStatus.put("msg", "Time out");
            }
        }
        return txStatus;
    }


    /**
     * Checking whether the tx succeeded.
     * @param txHash The hash of a tx.
     * @return boolean
     */
    public boolean MakeSureTxSuccess(String txHash) {
        long startTime = System.currentTimeMillis();
        while (true) {
            JSONObject result = checkTxStatusByHash(txHash);
            int code = result.getIntValue("code");
            if (0 == code) {
                break;
            } else if (code != 4) {
                System.out.println("error: 交易(" + txHash + ") 执行失败");
                return false;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 50000) {
                System.out.println("error: 交易(" + txHash + ") 执行超时");
                return false;
            }
        }
        return true;
    }

    /**
     * Checking the tx status
     * @param txHash The hash of tx
     * @return int 1: Success, 0: Failure, -1: Not Found
     */
    public JSONObject checkTxStatusByHash(String txHash) {
        // Initializing the request.
        TransactionGetInfoRequest request = new TransactionGetInfoRequest();
        request.setHash(txHash);

        // Getting the contract address.
        TransactionGetInfoResponse response = sdk.getTransactionService().getInfo(request);
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("msg", "SUCCESS");
        if (response.getErrorCode() == 0) {
            int errorCode = response.getResult().getTransactions()[0].getErrorCode();
            if (errorCode != 0){
                String errorDesc = response.getResult().getTransactions()[0].getErrorDesc();
                JSONObject errorResult = (JSONObject)JSON.parse(errorDesc);
                result = (JSONObject)JSON.parse(errorResult.getString("exception"));
            }
        } else {
            result.put("code", response.getErrorCode());
            result.put("msg", response.getErrorDesc());
        }

        return result;
    }

}
