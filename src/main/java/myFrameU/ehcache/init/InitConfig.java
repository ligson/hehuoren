package myFrameU.ehcache.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private List<Class> initDataClasses;
	private List<InitCleanEntity> initCleans;
	

	
	
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public List<Class> getInitDataClasses() {
		return initDataClasses;
	}
	public void setInitDataClasses(List<Class> initDataClasses) {
		this.initDataClasses = initDataClasses;
	}
	public List<InitCleanEntity> getInitCleans() {
		return initCleans;
	}
	public void setInitCleans(List<InitCleanEntity> initCleans) {
		this.initCleans = initCleans;
	}
	
	
}
