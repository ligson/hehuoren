package myFrameU.shop.aop;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.shop.entity.Shop;
import myFrameU.shop.init.FGShopAopEntity;
import myFrameU.spring.aop.interf.AOPI;
import myFrameU.spring.aop.web.WebAop;

public class FGShopAOP implements AOPI{
	
	//级联清理缓存
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res,UICacheManager cacheManager,AbstractBizI aBiz){
		return null;
	}

	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
		myFrameU.shop.init.InitConfig initConfig = myFrameU.shop.init.InitMavenImpl.ic;
		FGShopAopEntity fgsae = initConfig.getFgshopAopEntity();
		String requestPrefix = fgsae.getPrefix();
		String webPrefix = WebAop.getReqPrefix(req);
		//分析获取shopId
		if(webPrefix.equals(req.getRequestURI())){
			HashMap<String,String> cacheKeyMap = (HashMap<String, String>) fgsae.getCacheKeyMap();
			if(null!=cacheKeyMap){
				String shoop_cacheKey=cacheKeyMap.get("shop");
				Shop shop = (Shop) cacheManager.getObjectCached(fgsae.getCacheName(), shoop_cacheKey);
				req.setAttribute("shop", shop);
			}
		}
		return null;
	}

	
}
