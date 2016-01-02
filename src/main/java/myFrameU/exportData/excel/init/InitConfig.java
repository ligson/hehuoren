package myFrameU.exportData.excel.init;

import java.util.Map;

public class InitConfig{
	private Class initMavenClass;
	
	private Map<String,ExcelDataEntity> edeMap;

	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public Map<String, ExcelDataEntity> getEdeMap() {
		return edeMap;
	}

	public void setEdeMap(Map<String, ExcelDataEntity> edeMap) {
		this.edeMap = edeMap;
	}

	
}
