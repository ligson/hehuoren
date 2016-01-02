package myFrameU.global.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	
	private String status;
	private String modGlobalClass;
	private List<String> needModGlobalNamepysList;
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public String getModGlobalClass() {
		return modGlobalClass;
	}

	public void setModGlobalClass(String modGlobalClass) {
		this.modGlobalClass = modGlobalClass;
	}

	public List<String> getNeedModGlobalNamepysList() {
		return needModGlobalNamepysList;
	}

	public void setNeedModGlobalNamepysList(List<String> needModGlobalNamepysList) {
		this.needModGlobalNamepysList = needModGlobalNamepysList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
