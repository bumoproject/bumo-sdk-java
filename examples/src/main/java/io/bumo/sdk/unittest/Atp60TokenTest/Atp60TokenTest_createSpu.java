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
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = null;
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
        assertEquals(result.getString("desc"), 20012, result.getIntValue("code"));
    }

    @Test
    public void id_empty() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "";
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
        assertEquals(result.getString("desc"), 20012, result.getIntValue("code"));
    }

    @Test
    public void id_one() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "1";
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
    public void id_64() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "1111111111111111111111111111111111111111111111111111111111111111";
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
    public void id_65() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "11111111111111111111111111111111111111111111111111111111111111111";
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
        assertEquals(result.getString("desc"), 20012, result.getIntValue("code"));
    }

    @Test
    public void name_null() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "1";
        // The spu name.
        String spuName = null;
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
        assertEquals(result.getString("desc"), 20005, result.getIntValue("code"));
    }

    @Test
    public void name_empty() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "1";
        // The spu name.
        String spuName = "";
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
        assertEquals(result.getString("desc"), 20005, result.getIntValue("code"));
    }

    @Test
    public void name_one() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "11";
        // The spu name.
        String spuName = "1";
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
    public void name_1024() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "111";
        // The spu name.
        String spuName = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
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
    public void name_1025() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "1";
        // The spu name.
        String spuName = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
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
        assertEquals(result.getString("desc"), 20005, result.getIntValue("code"));
    }

    @Test
    public void type_null() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "000000001";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = null;
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
        assertEquals(result.getString("desc"), 20015, result.getIntValue("code"));
    }

    @Test
    public void type_empty() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "000000001";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "";
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
        assertEquals(result.getString("desc"), 20015, result.getIntValue("code"));
    }

    @Test
    public void type_one() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "01";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "1";
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
    public void type_64() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "011";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "1111111111111111111111111111111111111111111111111111111111111111";
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
    public void type_65() {
        // The seller public key to create spu.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // The spu id.
        String spuId = "011";
        // The spu name.
        String spuName = "苹果 5s";
        // The spu type.
        String spuType = "11111111111111111111111111111111111111111111111111111111111111111";
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
        assertEquals(result.getString("desc"), 20015, result.getIntValue("code"));
    }

    @Test
    public void not_seller() {
        // The controller private key to create spu.
        String controllerPrivateKey = "privbtttvTCVHMCeUTZU6qEmRNxFGo5Hd3Bk2BPgZyy5WCCMaEghgecu";
        // The controller address.
        String controllerAddress = "buQVzjctnsuSyCiAVDMTFsGggDhb12GEuQcD";
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
        String txHash = atp60TokenDemo.createSPUTx(controllerPrivateKey, controllerAddress, spuId, spuName, spuType, spuPrice, spuBrand, spuModel);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20017, result.getIntValue("code"));
    }

    @Test
    public void already_exist() {
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
        assertEquals(result.getString("desc"), 20018, result.getIntValue("code"));
    }

    @Test
    public void get_normal() {
        // The spu id.
        String spuId = "000000001";

        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String result = atp60TokenDemo.SPUInfoQuery(spuId);
        JSONObject resultJson = JSON.parseObject(result);

        System.out.println(JSON.toJSONString(resultJson, true));
        assertEquals(resultJson.getString("desc"), 0, resultJson.getIntValue("code"));
    }

    @Test
    public void get_not_exist() {
        // The spu id.
        String spuId = "0000000011";

        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String result = atp60TokenDemo.SPUInfoQuery(spuId);
        JSONObject resultJson = JSON.parseObject(result);

        System.out.println(JSON.toJSONString(resultJson, true));
        assertEquals(resultJson.getString("desc"), 20019, resultJson.getIntValue("code"));
    }
}
