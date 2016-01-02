package myFrameU.spring.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.app.util.AppJsonResultUtil;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.spring.aop.web.WebAop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 */

public class FatherController extends MultiActionController {
	
	
	
	
	@Autowired
	public AbstractBizI aBiz;
	@Autowired
	public UICacheManager uICacheManager;

	public AbstractBizI getaBiz() {
		return aBiz;
	}

	public void setaBiz(AbstractBizI aBiz) {
		this.aBiz = aBiz;
	}

	public UICacheManager getuICacheManager() {
		return uICacheManager;
	}

	public void setuICacheManager(UICacheManager uICacheManager) {
		this.uICacheManager = uICacheManager;
	}

	/**
	 */
	public String getForward(String forwardPage, String defaultPage) {
		// String forwardPage= (String) req.getParameter("forwardPage");
		if (null != forwardPage && !forwardPage.equals("")) {
			return forwardPage;
		} else {
			return defaultPage;
		}
	}
	public String getForward(String defaultPage,HttpServletRequest req) {
		String prefix = WebAop.getReqPrefix(req);
		String forwardPage=req.getParameter(CommonField.forwardPage);
		if (null != forwardPage && !forwardPage.equals("")) {
			if(prefix.equals("/admin/")|| prefix.equals("/shop/") || prefix.equals("/user/")){
				forwardPage="/manage/"+forwardPage;
			}else if(prefix.equals("/wrap/user/")){
				forwardPage="/wrap/manage/user/"+forwardPage;
			}else if(prefix.equals("/wrap/shop/")){
				forwardPage="/wrap/manage/shop/"+forwardPage;
			}
			return forwardPage;
		} else {
			if(prefix.equals("/admin/")|| prefix.equals("/shop/") || prefix.equals("/user/")){
				if(prefix.equals("/admin/")){
					if(!defaultPage.contains("/admin/")){
						defaultPage="/admin/"+defaultPage;
					}
				}else if(prefix.equals("/shop/")){
					if(!defaultPage.contains("/shop/")){
						defaultPage="/shop/"+defaultPage;
					}
				}else if(prefix.equals("/user/")){
					if(!defaultPage.contains("/user/")){
						defaultPage="/user/"+defaultPage;
					}
				}
				defaultPage="/manage/"+defaultPage;
			}else{
				if(prefix.equals("/wrap/user/")){
					defaultPage="/wrap/manage/user/"+defaultPage;
				}else if(prefix.equals("/wrap/shop/")){
					defaultPage="/wrap/manage/shop/"+defaultPage;
				}
			}
			return defaultPage;
		}
	}

	public SDynaActionForm getSDynaActionForm(HttpServletRequest req) {
		SDynaActionForm sDynaActionForm = (SDynaActionForm) uICacheManager
				.getObjectCached("web", "sDynaActionForm");
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
		req.setAttribute("formMap", sDynaActionForm.getFormMap());
		
		return sDynaActionForm;
	}
	
	protected void success(HttpServletRequest req){
		AppJsonResultUtil.tipResult("ok", "请求成功", req);
	}
	
	
	
	
	//判断这些值是不是都是不为空的，如果都不为空，则返回true
	public boolean isAllNotNull(Object[] array){
		int size=array.length;
		Object o = null;
		for(int i=0;i<size;i++){
			o=array[i];
			if(null==o){
				return false;
			}
		}
		return true;
	}
	
	
	
	//===================================================================================================
	
	
	
	
	
	public void renderData(HttpServletResponse response, String data) {
	    PrintWriter printWriter = null;
	    try {
	      printWriter = response.getWriter();
	      printWriter.print(data);
	    } catch (Exception ex) {
	    } finally {
	      if (null != printWriter) {
	        printWriter.flush();
	        printWriter.close();
	      }
	    }
	  }
	
	
	
	public static boolean isWrap(HttpServletRequest req){
		String fix = WebAop.getReqPrefix(req);
		if(null!=fix && fix.contains("/wrap/")){
			return true;
		}
		return false;
	}
	
	
	
	
	
	

}
