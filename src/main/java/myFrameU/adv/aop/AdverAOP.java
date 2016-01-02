package myFrameU.adv.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.adv.entity.AdvertingPage;
import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.init.EhcacheEntity;
import myFrameU.ehcache.init.InitCleanEntity;
import myFrameU.ehcache.init.InitConfig;
import myFrameU.ehcache.init.InitMavenImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;
import myFrameU.spring.aop.web.WebAop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AdverAOP implements AOPI{
	
	//级联清理缓存
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz){
		//fg前台的展示，则需要调用出广告
		String prefix = WebAop.getReqPrefix(req);
		System.out.println("AdverAOP=========prefix==="+prefix);
		if(null!=prefix){
			if(!prefix.equals("/shop/") && !prefix.equals("/admin/") && !prefix.equals("/user/")){
				//List<Advertisement> list= CacheKey.CKAdvertisement.addId_advertingMarked_map.getList(cacheManager);
				HashMap<String,Advertisement> advertisementMap = CacheKey.CKAdvertisement.addId_advertingMarked_map.getMap(cacheManager);
				HashMap<String,AdvertingPage> advertingPageMap = CacheKey.CKAdvertingPage.ALLMAP.getMap(cacheManager);
				HashMap<String,Advertising> advertisingMap = CacheKey.CKAdvertising.markedNumMap.getMap(cacheManager);
				
				req.setAttribute("advertisementMap", advertisementMap);
				req.setAttribute("advertingPageMap", advertingPageMap);
				req.setAttribute("advertisingMap", advertisingMap);
				
				
			}
		}
		return null;
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
				return null;
		
	}

	
}
