package myFrameU.spring.aop.init;

public class MavenAopEntity {
	private Class aopClass;
	private String interceptPrefix;
	private String method;
	public Class getAopClass() {
		return aopClass;
	}
	public void setAopClass(Class aopClass) {
		this.aopClass = aopClass;
	}
	public String getInterceptPrefix() {
		return interceptPrefix;
	}
	public void setInterceptPrefix(String interceptPrefix) {
		this.interceptPrefix = interceptPrefix;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
}
