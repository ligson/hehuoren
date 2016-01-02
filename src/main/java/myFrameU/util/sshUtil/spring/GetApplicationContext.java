package myFrameU.util.sshUtil.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class GetApplicationContext {
	public static ApplicationContext getApplicationContext(HttpServletRequest req){
		ApplicationContext actx = WebApplicationContextUtils.getWebApplicationContext(req.getSession(true).getServletContext());
		return actx;
	}
}
