package myFrameU.freemarker.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private String ftlPackage;
	private String ftlToPagePath;
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getFtlPackage() {
		return ftlPackage;
	}
	public void setFtlPackage(String ftlPackage) {
		this.ftlPackage = ftlPackage;
	}
	public String getFtlToPagePath() {
		return ftlToPagePath;
	}
	public void setFtlToPagePath(String ftlToPagePath) {
		this.ftlToPagePath = ftlToPagePath;
	}
	
	
}
