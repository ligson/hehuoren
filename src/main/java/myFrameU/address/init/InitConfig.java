package myFrameU.address.init;

import java.util.List;
import java.util.Map;

public class InitConfig{
	private Class initMavenClass;
	private Map<String,String> classMethodsMap;
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public Map<String, String> getClassMethodsMap() {
		return classMethodsMap;
	}
	public void setClassMethodsMap(Map<String, String> classMethodsMap) {
		this.classMethodsMap = classMethodsMap;
	}

	
	
}
