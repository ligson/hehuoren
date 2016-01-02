package myFrameU.history.aop;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.init.EhcacheEntity;
import myFrameU.ehcache.init.InitCleanEntity;
import myFrameU.ehcache.init.InitConfig;
import myFrameU.ehcache.init.InitMavenImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;

import org.aspectj.lang.ProceedingJoinPoint;

public class HistoryAOP implements AOPI{
	
	//级联清理缓存
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz){
		try{
			System.out.println("AOP子模块----执行了CacheAOP-aopAfter");
			
			ProceedingJoinPoint pjp=(ProceedingJoinPoint) req.getAttribute("proceedingJoinPoint");
			String controllerClassName=pjp.getTarget().getClass().getName();
			String method=pjp.getSignature().getName();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
				return null;
		
	}

	
}
