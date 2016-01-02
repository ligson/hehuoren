package myFrameU.spring.aop.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import myFrameU.admin.entity.Admin;
import myFrameU.shop.entity.Shop;
import myFrameU.user.entity.User;

public class AOPUtil {
	public static final String mySetResultNULL="mySetResultNULL";
	public static String modifyResult(String result,HttpServletRequest request){
		String setResultNULL= (String) request.getAttribute(mySetResultNULL);
		if(null==setResultNULL){
			return result;
		}else{
			return null;
		}
	}
	
	//看看是admin还是shop还是user登录着
/*	public static String role(HttpServletRequest req){
		HttpSession session  = req.getSession();
		User user = (User)session.getAttribute("myUser");
		Shop shop = (Shop)session.getAttribute("myShop");
		Admin admin = (Admin)session.getAttribute("myAdmin");
		if(null!=user){
			return "USER";
		}else if(null!=shop){
			return "SHOP";
		}else if(null!=admin){
			return "ADMIN";
		}
		return "";
	}*/
	
	
	

	
	
	
	
}
