package myFrameU.weixin.base.util;

import javax.servlet.http.HttpServletRequest;

import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopUser;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.user.entity.User;

public class GetWXId {
	public static String getWxId(HttpServletRequest req){
		String wxId = null;
		String roleClassName = WebAop.getCurrentRoleClassName(req);
		if(roleClassName.equals(Shop.class.getName())){
			ShopUser su = (ShopUser)req.getSession().getAttribute("myShopUser");
			if(null!=su){
				wxId=su.getWxId();
			}
		}else if(roleClassName.equals(User.class.getName())){
			User user = (User)req.getSession().getAttribute("myUser");
			if(null!=user){
				wxId = user.getWxId();
			}
		}
		return wxId;
	}
}
