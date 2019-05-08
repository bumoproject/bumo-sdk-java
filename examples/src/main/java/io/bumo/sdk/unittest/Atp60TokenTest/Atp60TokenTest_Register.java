package io.bumo.sdk.unittest.Atp60TokenTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.bumo.sdk.example.Atp60TokenDemo;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @Author riven
 * @Date 2019/5/7 15:07
 */
public class Atp60TokenTest_Register {
    @Test
    public void normal() {
        // The seller private key to registers information.
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void name_null() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = null;
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20000, result.getIntValue("code"));
    }

    @Test
    public void name_empty() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20000, result.getIntValue("code"));
    }

    @Test
    public void name_one() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "1";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void name_1024() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void name_1025() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20000, result.getIntValue("code"));
    }

    @Test
    public void contact_null() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = null;
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20001, result.getIntValue("code"));
    }

    @Test
    public void contact_empty() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = "";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20001, result.getIntValue("code"));
    }

    @Test
    public void contact_one() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = "1";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void contact_64() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = "1111111111111111111111111111111111111111111111111111111111111111";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 0, result.getIntValue("code"));
    }

    @Test
    public void contact_65() {
        // The seller private key to registers information.
        String sellerPrivateKey = "privC15YAp4M4oDLcJ6JqyPqYH55VXPeNckE2AVqWcHN8BB4PAVwGjJr";
        // The seller account address.
        String sellerAddress = "buQfTPaQBzFGBzGy87pSsc6MmNJKKKmzTSyC";
        // Company name.
        String name = "bumo";
        // Company contact.
        String contact = "11111111111111111111111111111111111111111111111111111111111111111";
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
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        String txHash = atp60TokenDemo.registerTx(sellerPrivateKey, sellerAddress, name, contact, organizationalCode, corporateName, cardNumber, businessLicense, cardFrontPhoto, cardBackPhoto);

        // Check Tx status.
        JSONObject result = atp60TokenDemo.getTxStatus(txHash);
        System.out.println(JSON.toJSONString(result, true));
        assertEquals(result.getString("desc"), 20001, result.getIntValue("code"));
    }

    @Test
    public void sellInfo() {
        Atp60TokenDemo atp60TokenDemo = new Atp60TokenDemo();
        atp60TokenDemo.sellInfoQuery();
    }
}
