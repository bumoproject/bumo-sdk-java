package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/5 16:46
 */
public class LogInfo {
    @JSONField(name = "topic")
    private String topic;

    @JSONField(name = "datas")
    private String[] datas;

    /**
     * @Author riven
     * @Method getTopic
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/5 16:47
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @Author riven
     * @Method setTopic
     * @Params [topic]
     * @Return void
     * @Date 2018/7/5 16:47
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @Author riven
     * @Method getDatas
     * @Params []
     * @Return java.lang.String[]
     * @Date 2018/7/5 16:47
     */
    public String[] getDatas() {
        return datas;
    }

    /**
     * @Author riven
     * @Method setDatas
     * @Params [datas]
     * @Return void
     * @Date 2018/7/5 16:54
     */
    public void setDatas(String[] datas) {
        this.datas = datas;
    }
}
