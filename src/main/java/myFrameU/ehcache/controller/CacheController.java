package myFrameU.ehcache.controller;

import javax.servlet.http.HttpServletRequest;

import myFrameU.ehcache.util.UICacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
@Controller
@RequestMapping(value={"/cache"})
public class CacheController extends MultiActionController {
	@Autowired
	private UICacheManager uICacheManager;
	
	public UICacheManager getuICacheManager() {
		return uICacheManager;
	}

	public void setuICacheManager(UICacheManager uICacheManager) {
		this.uICacheManager = uICacheManager;
	}

	@RequestMapping(value="/clear",method=RequestMethod.GET)
	public void clearAllCaches(HttpServletRequest req){
		String cacheName=(String)req.getParameter("cacheName");
		if(null!=cacheName){
			uICacheManager.clearAllDatas(cacheName);
		}
	}
}
