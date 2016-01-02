package myFrameU.address.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.init.InitCleanEntity;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;

import org.aspectj.lang.ProceedingJoinPoint;

public class AddressAop implements AOPI{
	
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz){
		return null;
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
		try{
			System.out.println("AOP子模块----执行了AddressAOP-aopBefore");
			
			ProceedingJoinPoint pjp=(ProceedingJoinPoint) req.getAttribute("proceedingJoinPoint");
			String controllerClassName=pjp.getTarget().getClass().getName();
			String method=pjp.getSignature().getName();
			
			
			myFrameU.address.init.InitConfig ic = myFrameU.address.init.InitMavenImpl.ic;
			HashMap<String,String> map = (HashMap<String, String>) ic.getClassMethodsMap();
			String className = null;
			String methods = null;
			for(Map.Entry<String, String> entry : map.entrySet()){   
				className = entry.getKey();
				methods = entry.getValue();
				methods=methods+",";
				if(controllerClassName.equals(className)){
					if(methods.contains(method+",")){
						System.out.println("address AOP 执行了.........................................................");
						HashMap<String,Address> allmap=CacheKey.CKAddress.ALLMAP.getMap(cacheManager);
						req.setAttribute("addressAllMap", allmap);
						HashMap<String,Address> rootMap = CacheKey.CKAddress.ROOTMAP.getMap(cacheManager);
						req.setAttribute("addressRootMap", rootMap);
					}
				}
			}  
			
		}catch(Exception e){
			e.printStackTrace();
		}
				return null;
		
	}

	
}
