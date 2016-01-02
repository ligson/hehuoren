package myFrameU.util.sshUtil.init;

import java.util.HashMap;

public class InitConfig{
	private Class initMavenClass;
	private HashMap<String,String> entityTableMap;
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public HashMap<String, String> getEntityTableMap() {
		return entityTableMap;
	}
	public void setEntityTableMap(HashMap<String, String> entityTableMap) {
		this.entityTableMap = entityTableMap;
	}
	
	
}
