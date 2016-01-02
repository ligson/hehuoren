package myFrameU.expand.distribution.entity;

import java.io.Serializable;

/**
 * 
 * 属性分配范围
 *
 */
public class DistributionRange   implements Serializable{
	
	private String keyName;//产品类型中文名称
	private String key;//product中的字段，如productTypeId
	private String value;//
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(null!=key && null!=value && !key.equals("") && !value.equals("")){
			sb.append("{'key':'").append(key).append("','value':'").append(value).append("'}");
			return sb.toString();
		}else{
			return "ALL";
		}
	}
	
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
	
}
