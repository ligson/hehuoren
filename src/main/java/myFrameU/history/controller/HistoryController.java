package myFrameU.history.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.admin.entity.Admin;
import myFrameU.history.entity.History;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.httpUtil.ip.GetIp;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HistoryController extends FatherController {

	@RequestMapping(value={"/user/history/finds","/shop/history/finds","/admin/history/finds"})
	public String finds(HttpServletRequest req,HttpServletResponse res) throws Exception{
		System.out.println("ddddddddddddddddddddddddddddddddddddddddddd");
		SDynaActionForm form = this.getSDynaActionForm(req);
		String historyType = form.getString("historyType");
		if(null!=historyType && !historyType.equals("")){
			StringBuffer queryArgsSB=new StringBuffer();
			String prefix = WebAop.getReqPrefix(req);
			if(null!=prefix && !prefix.equals("")){
				if(prefix.equals("/shop/")){
					Shop shop = (Shop)req.getSession().getAttribute("myShop");
					if(null!=shop){
						queryArgsSB.append("[{'key':'historyType','value':'").append(historyType).append("'},{'key':'whoclassName','value':'myFrameU.shop.entity.Shop'},{'key':'whoId','value':'").append(shop.getId()).append("'}]");
					}
				}else if(prefix.equals("/user/")){
					User user = (User)req.getSession().getAttribute("myUser");
					if(null!=user){
						queryArgsSB.append("[{'key':'historyType','value':'").append(historyType).append("'},{'key':'whoclassName','value':'myFrameU.user.entity.User'},{'key':'whoId','value':'").append(user.getId()).append("'}]");
					}
				}else if(prefix.equals("/admin/")){
					Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
					if(null!=admin){
						queryArgsSB.append("[{'key':'historyType','value':'").append(historyType).append("'},{'key':'whoclassName','value':'myFrameU.admin.entity.Admin'},{'key':'whoId','value':'").append(admin.getId()).append("'}]");
					}
				}
				
				String queryArgs = queryArgsSB.toString();
				if(null!=queryArgs && !queryArgs.equals("")){
					aBiz.findEntitysByArgs(History.class, EntityTableUtil.tableNameC(History.class), queryArgs, null, null, true, 0, "historyList", req);
				}
				
			}
		}
		return this.getForward("history/historyList", req);
	}
	
	
	
	
	
	

	@RequestMapping(value={"/history/add"})
	public void add(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer roleId = form.getInteger("roleId");
		String roleName=form.getString("roleName");
		String roleClassName=form.getString("roleClassName");
		
		if(null!=roleClassName && !roleClassName.equals("") && null!=roleId && roleId.intValue()!=0){
			History h = new History();
			h.setExtraData(null);
			h.setHistoryType(History.HISTROYTYPE.LOGIN.toString());
			String ip = GetIp.getIpAddr(req);
			//String iplocation=GetIp.ipLocation(ip);
			h.setAddressStr(null);
			h.setIp(ip);
			h.setRelDate(new Date());
			h.setTitle("用户["+roleName+"]登录成功");
			h.setWhoclassName(roleClassName);
			h.setWhoName(roleName);
			h.setWhoId(roleId);
			aBiz.addObject(h);
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
