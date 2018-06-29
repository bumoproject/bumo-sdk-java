package io.bumo.model.request.Operation;

import io.bumo.common.OperationType;

/**
 * @Author riven
 * @Date 2018/7/9 17:29
 */
public class LogCreateOperation extends BaseOperation {
    private OperationType operationType = OperationType.LOG_CREATE;
    private String topic;
    private String[] data;

    /**
     * @Author riven
     * @Method getOperationType
     * @Params []
     * @Return io.bumo.common.OperationType
     * @Date 2018/7/9 17:30
     */
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
     * @Method getData
     * @Params []
     * @Return java.lang.String[]
     * @Date 2018/7/9 17:30
     */
    public String[] getData() {
        return data;
    }

    /**
     * @Author riven
     * @Method setData
     * @Params [data]
     * @Return void
     * @Date 2018/7/9 17:33
     */
    public void setData(String[] data) {
        this.data = data;
    }
}
