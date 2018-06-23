package io.bumo.model.request;

import java.util.Arrays;

/**
 * @Author riven
 * @Date 2018/7/5 16:05
 */
public class TransactionSignRequest {
    private String blob;
    private String[] privateKeys;

    /**
     * @Author riven
     * @Method getBlob
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:06
     */
    public String getBlob() {
        return blob;
    }

    /**
     * @Author riven
     * @Method setBlob
     * @Params [blob]
     * @Return void
     * @Date 2018/7/5 16:06
     */
    public void setBlob(String blob) {
        this.blob = blob;
    }

    /**
     * @Author riven
     * @Method getPrivateKey
     * @Params []
     * @Return java.lang.String[]
     * @Date 2018/7/5 16:06
     */
    public String[] getPrivateKeys() {
        return privateKeys;
    }

    /**
     * @Author riven
     * @Method setPrivateKey
     * @Params [privateKey]
     * @Return void
     * @Date 2018/7/5 16:10
     */
    public void setPrivateKeys(String[] privateKeys) {
        this.privateKeys = privateKeys;
    }

    /**
     * @Author riven
     * @Method addPrivateKey
     * @Params [privateKey]
     * @Return void
     * @Date 2018/7/5 23:53
     */
    public void addPrivateKey(String privateKey) {
        if (null == privateKeys) {
            privateKeys = new String[1];
        } else {
            privateKeys = Arrays.copyOf(privateKeys, privateKeys.length + 1);
        }
        privateKeys[privateKeys.length - 1] = privateKey;
    }
}
