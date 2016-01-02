package myFrameU.exportData.excel.init;

public class ExcelDataEntity {
	private String classNameChinese;//中文类名，如全局变量。
	private String simpleClassName;
	private String className;//myFrameU....Shop
	private String fields;//需要导出到excel到字段,用，隔开
	private String fieldNames;//字段的中文名称
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(String fieldNames) {
		this.fieldNames = fieldNames;
	}
	public String getSimpleClassName() {
		return simpleClassName;
	}
	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}
	public String getClassNameChinese() {
		return classNameChinese;
	}
	public void setClassNameChinese(String classNameChinese) {
		this.classNameChinese = classNameChinese;
	}
	
	
	
}
