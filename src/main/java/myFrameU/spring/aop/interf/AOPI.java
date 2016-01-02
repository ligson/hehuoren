package myFrameU.spring.aop.interf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;

public interface AOPI {
	//在执行请求之前
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz) throws Exception;
	
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz) throws Exception;
}
