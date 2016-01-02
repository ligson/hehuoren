package myFrameU.ehcache.initData;

import javax.servlet.ServletContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;



public interface WebInitInterface {
	public void initData(ServletContext sc,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception;
}
