package myFrameU.ibatis.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	
	private String ibatisXMLPath;

	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public String getIbatisXMLPath() {
		return ibatisXMLPath;
	}

	public void setIbatisXMLPath(String ibatisXMLPath) {
		this.ibatisXMLPath = ibatisXMLPath;
	}
	
	
	
	
}
