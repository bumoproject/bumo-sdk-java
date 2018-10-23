package io.bumo.model.request;

/**
 * @Author riven
 * @Date 2018/10/23 10:07
 */
public class SDKConfigure {
    private String url;
    private int httpConnectTimeOut;
    private int httpReadTimeOut;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHttpConnectTimeOut() {
        return httpConnectTimeOut;
    }

    public void setHttpConnectTimeOut(int httpConnectTimeOut) {
        this.httpConnectTimeOut = httpConnectTimeOut;
    }

    public int getHttpReadTimeOut() {
        return httpReadTimeOut;
    }

    public void setHttpReadTimeOut(int httpReadTimeOut) {
        this.httpReadTimeOut = httpReadTimeOut;
    }
}
