package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.Priv;

/**
 * @Author riven
 * @Date 2018/7/4 14:56
 */
public class AccountGetInfoResult {
    @JSONField(name = "address")
    private String address;

    @JSONField(name = "balance")
    private Long balance;

    @JSONField(name = "nonce")
    private Long nonce;

    @JSONField(name = "priv")
    private Priv priv;

    /**
     * @Author riven
     * @Method getAddress
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/4 15:12
     */
    public String getAddress() {
        return address;
    }

    /**
     * @Author riven
     * @Method setAddress
     * @Params [address]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @Author riven
     * @Method getBalance
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:10
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * @Author riven
     * @Method setBalance
     * @Params [balance]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * @Author riven
     * @Method getNonce
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/4 15:10
     */
    public Long getNonce() {
        return nonce;
    }

    /**
     * @Author riven
     * @Method setNonce
     * @Params [nonce]
     * @Return void
     * @Date 2018/7/4 15:10
     */
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    /**
     * @Author riven
     * @Method getPriv
     * @Params []
     * @Return io.bumo.model.response.result.data.Priv
     * @Date 2018/7/4 15:10
     */
    public Priv getPriv() {
        return priv;
    }

    /**
     * @Author riven
     * @Method setPriv
     * @Params [priv]
     * @Return void
     * @Date 2018/7/4 15:09
     */
    public void setPriv(Priv priv) {
        this.priv = priv;
    }
}
