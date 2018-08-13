package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/6 15:25
 */
public class Ctp10TokenGetNameResult {
    @JSONField(name = "name")
    private String name;

    /**
     * @Author riven
     * @Method getName
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/6 15:34
     */
    public String getName() {
        return name;
    }

    /**
     * @Author riven
     * @Method setName
     * @Params [name]
     * @Return void
     * @Date 2018/7/6 15:39
     */
    public void setName(String name) {
        this.name = name;
    }
}
