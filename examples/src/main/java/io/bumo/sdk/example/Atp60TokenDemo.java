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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
     * Fifth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets acceptance.
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
        JSONArray skuIds = null; //new JSONArray();
        //skuIds.add("1");
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = null;
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
     * Sixth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) issues the SKU Tokens.
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
        JSONArray skuLabel = new JSONArray();
        skuLabel.add("iphone");
        skuLabel.add("iphone 5s");
        skuLabel.add("iphone 5s 白色");
        skuLabel.add("iphone 5s 白色 64G");
        skuLabel.add("iphone 5s 白色 64G 中国大陆版");
        // The address that will be sent SKU Tokens when the redemption finishes.
        String redemptionAddress = "buQqudnoPPV2utx92jfdcLkFGDaB7v3iasPM";
        // The sku reference price.
        String skuPrice = "3000";
        // The sku color.
        String skuColor = "白色";
        // The sku memory.
        String skuMemory = "64";
        // The sku model.
        String skuModel = "中国大陆";
        // The sku abstract
        JSONArray skuAbstract = new JSONArray();
        skuAbstract.add("1"); // id "1" in attributes
        skuAbstract.add("3"); // id "3" in attributes
        skuAbstract.add("2"); // id "2" in attributes
        // The token name
        String skuName = "iphone 5s 白色 64G 中国大陆版";
        // The token symbol.
        String tokenSymbol = "IPWSFC";
        // The token supply.
        String tokenSupply = "1";
        // The token decimals.
        String decimals = "0";
        // Whether the tokens with ids.
        Boolean withTokenIds = true;
        // The token ids.
        JSONArray tokenIds = new JSONArray();
        tokenIds.add("1");
        // The token description.
        String skuDesc = "iphone 5s 白色 64G 中国大陆版";
        // The main icon
        String mainIcon = "png|https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557751338635&di=3f6b989903ddf1cf9c10cc530c849d93&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01815657c24f1b0000012e7eb901a1.jpg|md5|2938472190312847";
        // The vice icons
        JSONArray viceIcons = new JSONArray();
        viceIcons.add(mainIcon);
        // The acceptance ids
        JSONArray acceptanceIds = new JSONArray();
        acceptanceIds.add("1");

        // The sku attributes
        JSONObject attributes = new JSONObject();
        attributes.put("id", buildAdditionIndex("parentId", "name", "type", "value", "decimals", "uint"));
        attributes.put("1", buildAdditionIndex("0",         "参考价", "int",  skuPrice,     "-",       "CNY"));
        attributes.put("2", buildAdditionIndex("0",         "颜色",  "text",  skuColor,   "-",        "-"));
        attributes.put("3", buildAdditionIndex("0",         "内存",  "int",   skuMemory,       "-",        "G"));
        attributes.put("4", buildAdditionIndex("0",         "型号",  "text",  skuModel, "-",       "-"));

        // Issuing SKU Tokens.
        issueSKUTokensTx(sellerPrivateKey, sellerAddress, skuId, defaultTrancheId, spuId, skuName, tokenSymbol, tokenSupply, decimals, withTokenIds, tokenIds, skuDesc, mainIcon, viceIcons, skuLabel, redemptionAddress, acceptanceIds, skuAbstract, attributes);
    }

    @Test
    public void SKUTokenInfo() {
        // The sku id.
        String skuId = "1";

        SKUTokenInfoQuery(skuId);
    }


    /**
     * Seventh: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) adds issuance of the SKU Tokens.
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
     * Eighth: Manufacturer (buQamKpa9vmNwA7PTknnbgWRhyVZLPWy2bCu) authorizes the issuance of SKU Tokens.
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
     * NinTh: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) sets a controller.
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
     * Tenth: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) create a lockup, the unlocker is a controller (buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD).
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
     * Eleventh: Seller (buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC) transfers 200 sku tokens to an account (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP).
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
     * Twelfth: Token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) approve other account (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) 100 SKU Tokens.
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
     * Thirteenth: The spender (buQXmv2C8hLAArdR2HtJZwz44x9eiJ1hERYK) tranfers the tokens of the token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) 50 SKU Tokens to other account (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor).
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
     * Fourteenth: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) requests redemption.
     */
    @Test
    public void requestRedemption() {
        // The token holder public key to request redemption.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "1";
        // The sku id.
        String skuId = "1";
        // The tranche id. If the trancheId is ignored, SKU Tokens will be sent to default tranche in sku.
        String trancheId = "1";
        // The amount to be redemptioned.
        String value = "50";
        // The acceptance id.
        String acceptanceId = "1";
        // The lockup id.
        String lockupId = "1";

        // Requesting redemption.
        requestRedemptionTx(holderPrivateKey, holderAddress, redemptionId, skuId, trancheId, value, acceptanceId, lockupId);
    }

    @Test
    public void redemptionInfo() {
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        redemptionInfoQuery(redemptionId, applicant);
    }


    /**
     * Fifteenth: The acceptor (buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor) redeems.
     */
    @Test
    public void redeem() {
        // The acceptor public key to redemption.
        String acceptorPrivateKey = "privbUbdoe6co99ykomqQRUDiD8rh3XvWvmexNUS1bZbu5gb8RuKJA8Y";
        // The acceptor address.
        String acceptorAddress = "buQiHTjDSjQedxR2vF9WULhgWMtbo8rRLmor";
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        // Redeeming.
        redemptionTx(acceptorPrivateKey, acceptorAddress, redemptionId, applicant);
    }


    /**
     * Sixteenth: The redemption finished or causes dispute.
     * Sixteenth1: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) confirms redemption right, then finishes the redemption.
     */
    @Test
    public void confirmRedemption() {
        // The token holder public key to confirm redemption.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        // Confirming redemption.
        confirmRedemptionTx(holderPrivateKey, holderAddress, redemptionId, applicant);
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
     * Sixteenth: The redemption finished or causes dispute.
     * Sixteenth2: The token holder (buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP) applies dispute.
     */
    @Test
    public void applyDispute() {
        // The token holder public key to apply dispute.
        String holderPrivateKey = "privbUCxLLYNCPP1smBiNEYVnErMDwT8eJ7PWJZyioJQhXcHApwgqKsP";
        // The token holder address.
        String holderAddress = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The dispute reason.
        String reason = "未收到货";
        // The controller address.
        String controller = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
        // The lockup id for disupte.
        String lockupId = "2";

        // Applying dispute.
        applyDisputeTx(holderPrivateKey, holderAddress, redemptionId, applicant, reason, controller, lockupId);
    }

    @Test
    public void disputeInfo() {
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";


        disputeInfoQuery(redemptionId, applicant);
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
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The evidence description.
        String description = "未收到货，查询不到快递信息";

        // Setting evidence.
        setEvidenceTx(holderPrivateKey, holderAddress, redemptionId, applicant, description);
    }

    @Test
    public void evidenceInfo() {
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The evidence provider.
        String provider = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";

        evidenceInfoQuery(redemptionId, applicant, provider);
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
        // The redemption id.
        String redemptionId = "1";
        // The redemption applicant.
        String applicant = "buQWJ6jNak1stGEkQfZEZPvUwZR2W2YybUUP";
        // The dispute result status.
        int status = 1;
        // The evidence description.
        String description = "确实查询不到快递信息，SKU Tokens 返回给兑付申请人。";

        // Handling dispute.
        handleDisputeTx(controllerPrivateKey, controllerAddress, redemptionId, applicant, status, description);
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
        // The redemption id.
        String redemptionId = null;
        // The amount to be lockuped.
        String value = "100";

        // Lockuping the tokens
        lockupByTrancheTx(controllerPrivateKey, controllerAddress, address, skuId, trancheId, lockupId, redemptionId, value);
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
        String payLoad = getContractCodeFromFile("ATP60.js");
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 10.03BU
        Long feeLimit = ToBaseUnit.BU2MO("12");

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
        String registerTxHash = "ec5f729decc7bffd16aab4cc3d39c76ec3bb4f5a15e279493cfbb4b02ecbe096"; //registerTx();

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
        Long feeLimit = ToBaseUnit.BU2MO("1.4");

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
    public String issueSKUTokensTx(String sourcePrivateKey, String sourceAddress, String skuId, String defaultTrancheId, String spuId, String skuName, String tokenSymbol, String tokenSupply, String decimals, Object withTokenIds, JSONArray tokenIds, String skuDesc, String mainIcon, JSONArray viceIcons, JSONArray skuLabel, String redemptionAddress, JSONArray acceptanceIds, JSONArray skuAbstract, JSONObject attributes) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("1.5");

        // 1. Building the input of 'issueByTranche'.
        JSONObject input = new JSONObject();
        input.put("method", "issueByTranche");
        JSONObject params = new JSONObject();
        params.put("skuId", skuId);
        params.put("defaultTrancheId", defaultTrancheId);
        params.put("spuId", spuId);
        params.put("name", skuName);
        params.put("symbol", tokenSymbol);
        params.put("supply", tokenSupply);
        params.put("decimals", decimals);
        params.put("withTokenIds", withTokenIds);
        params.put("tokenIds",  tokenIds);
        params.put("description", skuDesc);
        params.put("mainIcon", mainIcon);
        params.put("viceIcons", viceIcons);
        params.put("labels", skuLabel);
        params.put("redemptionAddress", redemptionAddress);
        params.put("acceptanceIds", acceptanceIds);
        params.put("abstract", skuAbstract);
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
     * @param redemptionId The redemption id.
     * @return The tx hash.
     */
    public String lockupByTrancheTx(String sourcePrivateKey, String sourceAddress, String toAddress, String skuId, String trancheId, String lockupId, String redemptionId, String value) {
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
        if (!Tools.isEmpty(redemptionId)) {
            params.put("redemptionId", redemptionId);
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
     * Requesting the redemption.
     * @return The tx hash.
     */
    public String requestRedemptionTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String skuId, String trancheId, String value, String acceptanceId, String lockupId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'requestRedemption'.
        JSONObject input = new JSONObject();
        input.put("method", "requestRedemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
     * Querying the redemption information.
     * @return The redemption information.
     */
    public String redemptionInfoQuery(String redemptionId, String applicant) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "redemptionInfo");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The acceptor pays redemption.
     * @return The tx hash.
     */
    public String redemptionTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String applicant) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'redemption'.
        JSONObject input = new JSONObject();
        input.put("method", "redemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
     * The token holder confirm the redemption right.
     * @return The tx hash.
     */
    public String confirmRedemptionTx(String  sourcePrivateKey, String sourceAddress, String redemptionId, String applicant) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'confirmRedemption'.
        JSONObject input = new JSONObject();
        input.put("method", "confirmRedemption");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
     * The seller or the redemption applicant applys dispute.
     * @return The tx hash.
     */
    public String applyDisputeTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String applicant, String reason, String controller, String lockupId) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'applyDispute'.
        JSONObject input = new JSONObject();
        input.put("method", "applyDispute");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
    public String disputeInfoQuery(String redemptionId, String applicant) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "disputeInfo");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
        params.put("applicant", applicant);
        input.put("params", params);

        // Querying
        String result = queryInfo(input.toJSONString());

        return result;
    }


    /**
     * The seller, redemption applicant or acceptor can set evidence.
     * @return The tx hash.
     */
    public String setEvidenceTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String applicant, String description) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'setEvidence'.
        JSONObject input = new JSONObject();
        input.put("method", "setEvidence");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
    public String evidenceInfoQuery(String redemptionId, String applicant, String provider) {
        // Init input
        JSONObject input = new JSONObject();
        input.put("method", "evidenceInfo");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
    public String handleDisputeTx(String sourcePrivateKey, String sourceAddress, String redemptionId, String applicant, int status, String description) {
        // The fixed write 1000L, the unit is MO
        Long gasPrice = 1000L;
        // Setting up the maximum cost 0.01BU
        Long feeLimit = ToBaseUnit.BU2MO("0.1");

        // 1. Building the input of 'handleDispute'.
        JSONObject input = new JSONObject();
        input.put("method", "handleDispute");
        JSONObject params = new JSONObject();
        params.put("redemptionId", redemptionId);
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
     * 查询指定合约文件的合约代码
     * @param fileName　合约文件名称
     * @return String
     */
    public String getContractCodeFromFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try{
            String classPath = this.getClass().getClassLoader().getResource("").getPath();
            File file = new File(classPath + fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
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
                JSONObject errorResult = null;
                try {
                    errorResult = (JSONObject)JSON.parse(errorDesc);
                } catch (Exception e) {
                    result.put("code", errorCode);
                    result.put("msg", errorDesc);
                }
                if (errorResult != null) {
                    try {
                        result = (JSONObject)JSON.parse(errorResult.getString("exception"));
                    } catch (Exception e) {
                        result.put("msg", errorDesc);
                        result.put("code", errorCode);
                    }
                }
            }
        } else {
            result.put("code", response.getErrorCode());
            result.put("msg", response.getErrorDesc());
        }

        return result;
    }

}
