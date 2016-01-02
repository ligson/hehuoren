package myFrameU.upload.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	
	private List<UploadImageEntity> list;

	public Class getInitMavenClass() {
		return initMavenClass;
	}

	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}

	public List<UploadImageEntity> getList() {
		return list;
	}

	public void setList(List<UploadImageEntity> list) {
		this.list = list;
	}
	
	
	
}
