package myFrameU.pager.init;

import java.util.List;

public class InitConfig{
	private Class initMavenClass;
	private int pageSize;
	private String staticHtmlEnd;
	private String staticPager;
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStaticHtmlEnd() {
		return staticHtmlEnd;
	}
	public void setStaticHtmlEnd(String staticHtmlEnd) {
		this.staticHtmlEnd = staticHtmlEnd;
	}
	public String getStaticPager() {
		return staticPager;
	}
	public void setStaticPager(String staticPager) {
		this.staticPager = staticPager;
	}
	
	
	
}
