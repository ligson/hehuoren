package myFrameU.apply.entity;

import java.io.Serializable;

/**
 * 
 * 申请种类
 *
 */
public class ApplyType   implements Serializable{
	private String applyTypeName;//种类名称
	private String applyTypeKey;//
	
	private String dealWithApplyClassName;//处理这个申请的类名称

	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String getApplyTypeKey() {
		return applyTypeKey;
	}

	public void setApplyTypeKey(String applyTypeKey) {
		this.applyTypeKey = applyTypeKey;
	}

	public String getDealWithApplyClassName() {
		return dealWithApplyClassName;
	}

	public void setDealWithApplyClassName(String dealWithApplyClassName) {
		this.dealWithApplyClassName = dealWithApplyClassName;
	}
	
	
	
}
