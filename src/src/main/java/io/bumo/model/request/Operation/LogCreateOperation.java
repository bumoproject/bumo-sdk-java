package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author riven
 * @Date 2018/7/9 17:29
 */
public class LogCreateOperation extends BaseOperation {
    private OperationType operationType = OperationType.LOG_CREATE;
    private String topic;
    private List<String> datas;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:30
     */
    @Override
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @Author riven
     * @Method getTopic
     * @Params []
     * @Return java.lang.String
     * @Date 2018/7/9 17:30
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @Author riven
     * @Method setTopic
     * @Params [topic]
     * @Return void
     * @Date 2018/7/9 17:30
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * @Author riven
     * @Method getDatas
     * @Params []
     * @Return java.util.List<java.lang.String>
     * @Date 2018/7/23 15:16
     */
    public List<String> getDatas() {
        return datas;
    }

    /**
     * @Author riven
     * @Method setDatas
     * @Params [datas]
     * @Return void
     * @Date 2018/7/23 15:16
     */
    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    /**
     * @Author riven
     * @Method addData
     * @Params [data]
     * @Return void
     * @Date 2018/7/23 15:17
     */
    public void addData(String data) {
        if (null == datas) {
            datas = new ArrayList<String>();
        }
        datas.add(data);
    }
}
