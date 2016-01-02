package myFrameU.queryArgs.entityForm;

import java.util.Map;

public class EntityForm {
	private String className;
	private Map<String,EntityFiledFormItem> effiMap;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, EntityFiledFormItem> getEffiMap() {
		return effiMap;
	}
	public void setEffiMap(Map<String, EntityFiledFormItem> effiMap) {
		this.effiMap = effiMap;
	}
	
}
