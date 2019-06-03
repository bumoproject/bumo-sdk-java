package io.bumo.sdk.unittest.Atp60TokenTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.sdk.example.Atp60TokenDemo;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author riven
 * @Date 2019/5/7 15:07
 */
public class Atp60TokenTest_createSpu {
    @Test
    public void normal() {
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void id_null() {
        checkSpuId(null, 20012);
    }

    @Test
    public void id_empty() {
        checkSpuId("", 20012);
    }

    @Test
    public void id_one() {
        checkSpuId("1", 0);
    }

    @Test
    public void id_64() {
        checkSpuId("1111111111111111111111111111111111111111111111111111111111111111", 0);
    }

    @Test
    public void id_65() {
        checkSpuId("11111111111111111111111111111111111111111111111111111111111111111", 20012);
    }

    @Test
    public void id_already_exist() {
        checkSpuId("000000001", 20018);
    }

    @Test
    public void name_null() {
        checkName("001", null, 20005);
    }

    @Test
    public void name_empty() {
        checkName("0011", "", 20005);
    }

    @Test
    public void name_one() {
        checkName("00111", "2", 0);
    }

    @Test
    public void name_1024() {
        checkName("001111", "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", 0);
    }

    @Test
    public void name_1025() {
        checkName("001111", "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111", 20005);
    }

    @Test
    public void type_null() {
        checkType("01", null, 20015);
    }

    @Test
    public void type_empty() {
        checkType("011", "", 20015);
    }

    @Test
    public void type_one() {
        checkType("0111", "1", 0);
    }

    @Test
    public void type_64() {
        checkType("01111", "1111111111111111111111111111111111111111111111111111111111111111", 0);
    }

    @Test
    public void type_65() {
        checkType("011111", "11111111111111111111111111111111111111111111111111111111111111111", 20015);
    }

    @Test
    public void not_seller() {
        checkNotSeller("001", "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu", "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD", 20017);
    }

    @Test
    public void get_normal() {
        checkGetting("000000001", 0);
    }

    @Test
    public void get_not_exist() {
        checkGetting("0000000011", 20019);
    }

    private void checkSpuId(String spuId, long expectedCode) {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), expectedCode, result.getIntValue("code"));
    }

    private void checkName(String spuId, String spuName, long expectedCode) {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu type.
        String spuType = "手机";
        // The spu reference price.
        String spuPrice = "3000.00";
        // The spu brand.
        String spuBrand = "苹果";
        // The spu model.
        String spuModel = "5s";

        // Creating SPU.
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), expectedCode, result.getIntValue("code"));
    }


    private void checkType(String spuId, String spuType, long expectedCode) {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu reference price.
        String spuPrice = "3000.00";
        // The spu brand.
        String spuBrand = "苹果";
        // The spu model.
        String spuModel = "5s";

        // Creating SPU.
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.createSPUTx(sellerPrivateKey, sellerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), expectedCode, result.getIntValue("code"));
    }

    private void checkNotSeller(String spuId, String privateKey, String address, long expectedCode) {
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.createSPUTx(privateKey, address, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), expectedCode, result.getIntValue("code"));
    }

    private void checkGetting(String spuId, long expectedCode) {
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String result = atp60TokenDemo.SPUInfoQuery(spuId);
        JSONObject resultJson = JSON.parseObject(result);

        System.out.println(JSON.toJSONString(resultJson, true));
        assertEquals(resultJson.getString("desc"), expectedCode, resultJson.getIntValue("code"));
    }
}
