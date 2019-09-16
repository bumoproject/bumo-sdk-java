package io.bumo.model.response.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.bumo.model.response.result.data.Rewards;

import java.util.Arrays;
/**
 * @Author riven
 * @Date 2018/7/12 01:43
 */
public class BlockGetRewardResult {
    @JSONField(name = "validators")
    private Rewards[] validators;
    @JSONField(name = "kols")
    private Rewards[] kols;

    public Rewards[] getValidators() {
        return validators;
    }

    public void setValidators(Rewards[] validators) {
        this.validators = validators;
    }

    public void addValidator(Rewards validator) {
        if (null == validators) {
            validators = new Rewards[1];
        } else {
            validators = Arrays.copyOf(validators, validators.length + 1);
        }
        validators[validators.length - 1] = validator;
    }

    public Rewards[] getKols() {
        return kols;
    }

    public void setKols(Rewards[] kols) {
        this.kols = kols;
    }

    public void addKol(Rewards kol) {
        if (null == kols) {
            kols = new Rewards[1];
        } else {
            kols = Arrays.copyOf(kols, kols.length + 1);
        }
        kols[kols.length - 1] = kol;
    }
}
