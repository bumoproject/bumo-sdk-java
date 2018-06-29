package io.bumo.model.response.result.data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author riven
 * @Date 2018/7/11 18:24
 */
public class ContractStat {
    @JSONField(name = "apply_time")
    private Long applyTime;

    @JSONField(name = "memory_usage")
    private Long memoryUsage;

    @JSONField(name = "stack_usage")
    private Long stackUsage;

    @JSONField(name = "stack_usage")
    private Long step;

    /**
     * @Author riven
     * @Method getStackUsage
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/17 11:42
     */
    public Long getStackUsage() {
        return stackUsage;
    }

    /**
     * @Author riven
     * @Method setStackUsage
     * @Params [stackUsage]
     * @Return void
     * @Date 2018/7/17 11:42
     */
    public void setStackUsage(Long stackUsage) {
        this.stackUsage = stackUsage;
    }

    /**
     * @Author riven
     * @Method getApplyTime
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/11 18:26
     */
    public Long getApplyTime() {
        return applyTime;
    }

    /**
     * @Author riven
     * @Method setApplyTime
     * @Params [applyTime]
     * @Return void
     * @Date 2018/7/11 18:26
     */
    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * @Author riven
     * @Method getMemoryUsage
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/11 18:26
     */
    public Long getMemoryUsage() {
        return memoryUsage;
    }

    /**
     * @Author riven
     * @Method setMemoryUsage
     * @Params [memoryUsage]
     * @Return void
     * @Date 2018/7/11 18:26
     */
    public void setMemoryUsage(Long memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    /**
     * @Author riven
     * @Method getStep
     * @Params []
     * @Return java.lang.Long
     * @Date 2018/7/11 18:26
     */
    public Long getStep() {
        return step;
    }

    /**
     * @Author riven
     * @Method setStep
     * @Params [step]
     * @Return void
     * @Date 2018/7/11 18:26
     */
    public void setStep(Long step) {
        this.step = step;
    }
}
