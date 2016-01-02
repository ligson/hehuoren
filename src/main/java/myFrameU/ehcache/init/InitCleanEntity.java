package myFrameU.ehcache.init;

import java.util.List;

public class InitCleanEntity {
	private String className;
	private String method;//应该是methods,多个方法中间用英文逗号隔开
	
	private List<EhcacheEntity> clears;
	
	public List<EhcacheEntity> getClears() {
		return clears;
	}
	public void setClears(List<EhcacheEntity> clears) {
		this.clears = clears;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
