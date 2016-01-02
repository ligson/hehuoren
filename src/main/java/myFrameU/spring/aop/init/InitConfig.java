package myFrameU.spring.aop.init;

import java.util.HashMap;
import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private String login_status;
	private HashMap<String,LoginEntity> loginEntityMap;
	
	
	private List<MavenAopEntity> aopBeforeList;
	private List<MavenAopEntity> aopAfterList;
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getLogin_status() {
		return login_status;
	}
	public void setLogin_status(String login_status) {
		this.login_status = login_status;
	}
	public HashMap<String, LoginEntity> getLoginEntityMap() {
		return loginEntityMap;
	}
	public void setLoginEntityMap(HashMap<String, LoginEntity> loginEntityMap) {
		this.loginEntityMap = loginEntityMap;
	}
	public List<MavenAopEntity> getAopBeforeList() {
		return aopBeforeList;
	}
	public void setAopBeforeList(List<MavenAopEntity> aopBeforeList) {
		this.aopBeforeList = aopBeforeList;
	}
	public List<MavenAopEntity> getAopAfterList() {
		return aopAfterList;
	}
	public void setAopAfterList(List<MavenAopEntity> aopAfterList) {
		this.aopAfterList = aopAfterList;
	}

	
	
	
	
}
