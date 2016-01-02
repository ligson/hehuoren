package hhr.wrap.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.address.entity.Address;
import myFrameU.product.entity.Product;
import myFrameU.spring.mvc.FatherController;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.weixin.base.entity.WXUser;
import myFrameU.weixin.base.service.impl.AutoRegistAndLoginImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller("wrapIndexController")
@RequestMapping("/wrap")
public class IndexController extends FatherController {
	@Autowired
	private UserBizI userBiz;
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}


	@RequestMapping("/index")
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/**
		 * 自动登录
		 */
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==user){
			String code = req.getParameter("code");
			if(null!=code && !code.equals("")){
				AutoRegistAndLoginImpl wx_aral = new AutoRegistAndLoginImpl();
				user = wx_aral.autoRegistLogin(user, code, userBiz, uICacheManager,null);
				req.getSession().setAttribute("myUser", user);
			}
		}
		
		/**
		 * 查找所有的product,本项目产品比较少，不大于20个
		 */
		List<Product> proList = (List<Product>)aBiz.findObjectList(Product.class, 
				null,
				"from Product as p where p.status='OK' order by p.id desc", null, true, 0, 30);
		req.setAttribute("productList", proList);
		
		return "wrap/fg/index";
	}
	
	
	@RequestMapping(value={"/toUserRegist"})
	public String toUserRegist(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String code = req.getParameter("code");
		if(null!=code && !code.equals("")){
			 myFrameU.weixin.base.service.impl.UserImpl ui = new myFrameU.weixin.base.service.impl.UserImpl();
			 String openId = ui.getUserOpenId(code);
			 WXUser user = ui.getWXUserByOpenId(openId, uICacheManager,"user");
			 if(null!=user){
				 System.out.println("wxUser=="+user.toString());
				 User u=(User)aBiz.findObjectById("from User as u where u.wxId=?", new Object[]{openId});
				 if(null!=u){
					 //已经存在过了
					// req.setAttribute("wxUser", null);
				 }else{
					 req.setAttribute("wxUser", user);
				 }
			 }
			 
		}
		return "/wrap/fg/userRegist";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
