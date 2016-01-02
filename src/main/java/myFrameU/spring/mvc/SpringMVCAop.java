package myFrameU.spring.mvc;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.interf.AOPI;

public class SpringMVCAop implements AOPI {
	@Override
	public Object aopBefore(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
		return null;
	}

	@Override
	public Object aopAfter(HttpServletRequest req,HttpServletResponse res, UICacheManager cacheManager,
			AbstractBizI aBiz) {
		return null;
	}

	private SDynaActionForm getSDynaActionForm(HttpServletRequest req,UICacheManager cacheManager) {
		SDynaActionForm sDynaActionForm = (SDynaActionForm) cacheManager.getObjectCached("web", "sDynaActionForm");
		Enumeration e = (Enumeration) req.getParameterNames();
		while (e.hasMoreElements()) {
			String parName = (String) e.nextElement();
			String value = req.getParameter(parName);
			if(null!=parName && !parName.equals("")){
				if(null!=value){
					sDynaActionForm.getFormMap().put(parName, value);
				}
			}
		}
		return sDynaActionForm;
	}
}
