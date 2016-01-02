package myFrameU.history.init;

import java.util.Map;

public class InitConfig{
	private Class initMavenClass;
	
	//requestURI--hisotoryEnity
	private Map<String,HistoryEntity> map;

	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public Map<String, HistoryEntity> getMap() {
		return map;
	}

	public void setMap(Map<String, HistoryEntity> map) {
		this.map = map;
	}
	
	
	
}
