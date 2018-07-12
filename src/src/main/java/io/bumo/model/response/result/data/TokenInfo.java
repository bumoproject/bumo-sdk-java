package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 23:02
 */
public class TokenInfo {
    @JSONField(name = "ctp")
    private String ctp;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "symbol")
    private String symbol;

    @JSONField(name = "decimals")
    private Integer decimals;

    @JSONField(name = "contractOwner")
    private String contractOwner;

    @JSONField(name = "totalSupply")
    private String totalSupply;

    /**
     * @Author riven
     * @Method getCtp
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:04
     */
    public String getCtp() {
        return ctp;
    }

    /**
     * @Author riven
     * @Method setCtp
     * @Params [ctp]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setCtp(String ctp) {
        this.ctp = ctp;
    }

    /**
     * @Author riven
     * @Method getName
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:04
     */
    public String getName() {
        return name;
    }

    /**
     * @Author riven
     * @Method setName
     * @Params [name]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @Author riven
     * @Method getSymbol
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:04
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @Author riven
     * @Method setSymbol
     * @Params [symbol]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @Author riven
     * @Method getDecimals
     * @Params []
     * @Return java.lang.Integer
     * @Date 2018/7/11 23:04
     */
    public Integer getDecimals() {
        return decimals;
    }

    /**
     * @Author riven
     * @Method setDecimals
     * @Params [decimals]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    /**
     * @Author riven
     * @Method getContractOwner
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:04
     */
    public String getContractOwner() {
        return contractOwner;
    }

    /**
     * @Author riven
     * @Method setContractOwner
     * @Params [contractOwner]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setContractOwner(String contractOwner) {
        this.contractOwner = contractOwner;
    }

    /**
     * @Author riven
     * @Method getTotalSupply
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/11 23:04
     */
    public String getTotalSupply() {
        return totalSupply;
    }

    /**
     * @Author riven
     * @Method setTotalSupply
     * @Params [totalSupply]
     * @Return void
     * @Date 2018/7/11 23:04
     */
    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }
}
