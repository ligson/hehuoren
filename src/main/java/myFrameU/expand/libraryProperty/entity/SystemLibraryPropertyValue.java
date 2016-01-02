package myFrameU.expand.libraryProperty.entity;

import java.io.Serializable;

public class SystemLibraryPropertyValue   implements Serializable{
	private int id;
	private SystemLibraryProperty sysLibraryProperty;
	private String pvalue;
	private boolean defaultValue;//是否是默认的值
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public SystemLibraryProperty getSysLibraryProperty() {
		return sysLibraryProperty;
	}
	public void setSysLibraryProperty(SystemLibraryProperty sysLibraryProperty) {
		this.sysLibraryProperty = sysLibraryProperty;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public boolean getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
}
