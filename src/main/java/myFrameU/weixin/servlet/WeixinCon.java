package myFrameU.weixin.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.httpUtil.cookie.CookieUtil;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;
import myFrameU.weixin.base.entity.WXUser;
import myFrameU.weixin.base.service.impl.ReceiveMessageImpl;
import myFrameU.weixin.base.service.impl.SendMessageImpl;
import myFrameU.weixin.base.util.WeixinUtil;

public class WeixinCon extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("远程调用本地项目成功~~~~@@@@@@@@@@@@@@@@~~~~~~~~~~");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String echostr=WeixinUtil.verIsTrue(request);
		if(null!=echostr){
			System.out.println(echostr+"********************************88");
			response.getWriter().print(echostr);
			//response.getWriter().write(echostr);
		}
		
		ApplicationContext applicationContext = (ApplicationContext) request.getSession().getServletContext().getAttribute("applicationContext");
		UICacheManager uICacheManager=(UICacheManager)applicationContext.getBean("uICacheManager");
		AbstractBizI aBiz=(AbstractBizI)applicationContext.getBean("aBiz");
		System.out.println(uICacheManager+"@@@@#################################################################");
		
		try{
			ReceiveMessageImpl rmi = new ReceiveMessageImpl();
			ReceiveXmlEntity rxe = rmi.receiveMessage(request, response,uICacheManager,aBiz);
			SendMessageImpl smi = new SendMessageImpl();
			smi.reply(rxe,request, response,uICacheManager,aBiz);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
}