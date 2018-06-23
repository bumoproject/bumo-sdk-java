package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/7/10 17:01
 */
public class TransactionParseBlobRequest {
    private String blob;

    /**
     * @Author riven
     * @Method getBlob
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/10 16:53
     */
    public String getBlob() {
        return blob;
    }

    /**
     * @Author riven
     * @Method setBlob
     * @Params [blob]
     * @Return void
     * @Date 2018/7/10 17:01
     */
    public void setBlob(String blob) {
        this.blob = blob;
    }
}
