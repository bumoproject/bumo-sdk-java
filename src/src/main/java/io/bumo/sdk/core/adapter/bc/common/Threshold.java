package io.bumo.sdk.core.adapter.bc.common;

import com.alibaba.fastjson.annotation.JSONField;

public class Threshold {
	@JSONField(name = "high_threshold")
	private int highThreshold;
	@JSONField(name = "low_threshold")
	private int lowThreshold;
	@JSONField(name = "master_weight")
	private int masterThreshold;
	@JSONField(name = "med_threshold")
	private int medThreshold;
	
	public int getHighThreshold() {
		return highThreshold;
	}
	public void setHighThreshold(int highThreshold) {
		this.highThreshold = highThreshold;
	}
	public int getLowThreshold() {
		return lowThreshold;
	}
	public void setLowThreshold(int lowThreshold) {
		this.lowThreshold = lowThreshold;
	}
	public int getMasterThreshold() {
		return masterThreshold;
	}
	public void setMasterThreshold(int masterThreshold) {
		this.masterThreshold = masterThreshold;
	}
	public int getMedThreshold() {
		return medThreshold;
	}
	public void setMedThreshold(int medThreshold) {
		this.medThreshold = medThreshold;
	}
}
