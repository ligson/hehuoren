package myFrameU.verify.init;

import java.util.HashMap;


public class InitConfig{
	private Class initMavenClass;
	
	private HashMap<String,String> namesqlMap;
	private String valuesDelimiter;
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public HashMap<String, String> getNamesqlMap() {
		return namesqlMap;
	}

	public void setNamesqlMap(HashMap<String, String> namesqlMap) {
		this.namesqlMap = namesqlMap;
	}

	public String getValuesDelimiter() {
		return valuesDelimiter;
	}

	public void setValuesDelimiter(String valuesDelimiter) {
		this.valuesDelimiter = valuesDelimiter;
	}
	
	
	
}
