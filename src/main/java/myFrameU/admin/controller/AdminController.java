package myFrameU.admin.controller;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myFrameU.admin.entity.Admin;
import myFrameU.admin.init.InitConfig;
import myFrameU.admin.vo.MergerShopUser;
import myFrameU.biz.AbstractBizI;
import myFrameU.biz.util.MyActionUtil;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.pager.pager.Pager;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.PasswordUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.commonUtil.twoDimensional.QRCodeEventsZXING;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.path.PathUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller

public class AdminController extends FatherController {
	@RequestMapping(value="/toAdminLogin")
	public String toLogin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		if(null!=admin){
			InitConfig initConfig = myFrameU.admin.init.InitMavenImpl.ic;
			req.getSession().setAttribute("myAdmin", admin);
			String className = initConfig.getAfterClass();
			HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
			String method=afterMethodMap.get("afterLogin");
			if(null!=className && !className.equals("") && null!=method && !method.equals("")){
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod(method,Admin.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
				m.invoke(c.newInstance(),admin, req,uICacheManager,aBiz);
			}
			return "manage/admin/index";
		}
		return "manage/admin/login";
	}
	
	@RequestMapping(value="/adminLogin")
	public String login(HttpServletRequest req,HttpServletResponse res) throws Exception{
		InitConfig initConfig = myFrameU.admin.init.InitMavenImpl.ic;
		//String errorPage=initConfig.getLoginErrorPage();
		String errorPage=myFrameU.spring.aop.init.InitMavenImpl.ic.getLoginEntityMap().get("/admin/").getIfNotLoginPath();
		HttpSession session= req.getSession();
		Admin amdinOld  = (Admin)session.getAttribute("myAdmin");
		if(null==amdinOld){
			PrintWriter out = res.getWriter();
			SDynaActionForm form = getSDynaActionForm(req);
			String name = form.getString("name");
			String password = form.getString("password");
			if(null!=name && null!=password){
				Admin a = (Admin)aBiz.findObjectById("from Admin as a where a.name=? and a.password=?", new Object[]{name,password});
				if(null!=a){
					int status = a.getStatus();
					if(status==-1){
						//已被冻结
						//req.setAttribute("loginError", "您的账号已被冻结");
						throw new MyRefererException("您的账号已被冻结");
					}else{
						session.setAttribute("myAdmin", a);
						String className = initConfig.getAfterClass();
						HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
						String method=afterMethodMap.get("afterLogin");
						if(null!=className && !className.equals("") && null!=method && !method.equals("")){
							Class c = Class.forName(className);
							Method m = c.getDeclaredMethod(method,Admin.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
							m.invoke(c.newInstance(),a, req,uICacheManager,aBiz);
						}
						HttpClientUtil.get(PathUtil.getBasePath(req)+"history/add?roleClassName=myFrameU.admin.entity.Admin&roleId="+a.getId()+"&roleName="+a.getName(), null);
						return "manage/admin/index";
						
					}
				}else{
					throw new MyRefererException("登录账户不存在，或者密码错误");
					//req.setAttribute("loginError", "登录账户不存在，或者密码错误");
				}
			}else{
				throw new MyRefererException("请输入账号和密码");
			}
		}else{
			return "redirect:/admin/index";
		}
	}
	
	
	@RequestMapping(value="admin/logout")
	public String logout(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		req.getSession().removeAttribute("myAdmin");
		return "manage/admin/login";
	}
	
	
	
	@RequestMapping(value="/admin/index")
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Admin a = (Admin)req.getSession().getAttribute("myAdmin");
		InitConfig initConfig = myFrameU.admin.init.InitMavenImpl.ic;
		if(null!=a){
			req.getSession().setAttribute("myAdmin", a);
			String className = initConfig.getAfterClass();
			HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
			String method=afterMethodMap.get("afterLogin");
			if(null!=className && !className.equals("") && null!=method && !method.equals("")){
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod(method,Admin.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
				m.invoke(c.newInstance(),a, req,uICacheManager,aBiz);
			}
			return "manage/admin/index";
		}
		return null;
	}
	
	
	
	@RequestMapping(value="/findId")
	public String findId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id= (Integer)form.getInteger("id");
		if(null!=id){
			Admin a = (Admin)aBiz.findObjectById("from Admin as a where a.id=?", new Object[]{id});
			req.setAttribute("admin", a);
		}
		return getForward(form.getString("forwardPage"), "admin");
	}
	
	
	
	@RequestMapping(value="/findAll")
	public String findAll(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Pager pager = new MyActionUtil(req,"hql").getPager(req,aBiz,"select count(id) from admin where status=1",null);
		String hql = "from Admin as bq where bq.status=1";
		List<Admin> adminList = (List<Admin>)aBiz.findObjectList(Admin.class, null, hql, null, true, pager.getStartRow(), pager.getPageSize());
		req.setAttribute("adminList", adminList);
		return getForward(form.getString("forwardPage"), "adminList");
	}
	
	
	@RequestMapping(value="admin/modfiyPwd")
	public void modfiyPwd(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String error="ok";
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		String password = form.getString("password");
		Integer id = (Integer)form.getInteger("id");
		if(null!=id){
			if(null!=name && null!=password){
				if(!name.equals("") && !password.equals("")){
					Admin oldA = (Admin)aBiz.findObjectById("from Admin as a where a.name=?", new Object[]{name});
					if(null==oldA){
						aBiz.j_execute("update admin set name=?,password=? where id=?", new Object[]{name,password,id});
					}else{
						if(oldA.getId()==id.intValue()){
							aBiz.j_execute("update admin set name=?,password=? where id=?", new Object[]{name,password,id});
						}else{
							error="管理员名不能重复";
						}
					}
				}else{
					error="账号和密码不能为空";
				}
			}
		}
		res.getWriter().print(error);
	}
	
	
	
	
	
	
	
	//==========================================================================


	@RequestMapping(value={"/admin/likRoles"})
	public String likeAccountList(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String whoName= form.getString("whoName");
		if(null!=whoName && !whoName.equals("")){
			List<Shop> shopList = (List<Shop>)aBiz.findObjectList(Shop.class, null, "from Shop as s where s.name like '%"+whoName+"%' order by s.id desc", null, false, 0, 0);
			List<User> userList = (List<User>)aBiz.findObjectList(User.class, null, "from User as s where s.nicheng like '%"+whoName+"%' order by s.id desc", null, false, 0, 0);
			
			List<MergerShopUser> msuList = new ArrayList<MergerShopUser>();
			
			int size1=shopList.size();
			Shop shop = null;
			MergerShopUser msu=null;
			for(int i=0;i<size1;i++){
				shop=shopList.get(i);
				msu=new MergerShopUser();
				msu.setId(shop.getId());
				msu.setName(shop.getName());
				msu.setTelPhone(shop.getPhone());
				msu.setLogo(shop.getLogo());
				msu.setUserOrShop(MergerShopUser.USERORSHOP.SHOP.toString());
				msuList.add(msu);
			}
			int size2=userList.size();
			User user = null;
			for(int i=0;i<size2;i++){
				user=userList.get(i);
				msu=new MergerShopUser();
				msu.setId(user.getId());
				msu.setName(user.getNicheng());
				msu.setTelPhone(user.getTelPhone());
				msu.setLogo(user.getTouxiang());
				msu.setUserOrShop(MergerShopUser.USERORSHOP.USER.toString());
				msuList.add(msu);
			}
			
			req.setAttribute("msuList", msuList);
			
			
		}
		return getForward("systools/roleListAjax", req);
	}
	
	
	
	
	
	//==================================================
	@RequestMapping(value="admin/security/toPassword")
	public String security2Password(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		req.setAttribute("myAdmin", admin);
		return "manage/admin/security/password";
	}
	
	@RequestMapping(value="admin/security/password")
	public void securityModifyPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String password = form.getString("password");
		String oldPassword = form.getString("oldPassword");
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		if(null!=admin){
			String pwdDB = admin.getPassword();
			if(pwdDB.equals(oldPassword)){
				PasswordUtil.verPassword(password);
				admin.setPassword(password);
				aBiz.modifyObject(admin);
				req.getSession().setAttribute("myAdmin", admin);
			}else{
				throw new MyStrException("抱歉，您输入的老密码不正确");
			}
		}
	}
	
	@RequestMapping(value="admin/security/toTelphone")
	public String security2Telphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		req.setAttribute("myAdmin", admin);
		return "manage/admin/security/telPhone";
	}
	@RequestMapping(value="admin/security/telphone")
	public void securityModifyTelphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		String telPhone = form.getString("telPhones");
		String verTEL=PhoneUtil.vailterTelPhone(telPhone);
		if(null==verTEL){
			admin.setTelPhone(telPhone);
			aBiz.modifyObject(admin);
			req.getSession().setAttribute("myAdmin", admin);
		}else{
			throw new MyStrException(verTEL);
		}
	}
	
	
	//=============================================================================
	//扫描二维码生成admin在微信公共平台上的openId，用来管理员接收信息。
	@RequestMapping(value={"/adminWxId"})
	public String adminWxId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Admin admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=?", new Object[]{1});
		if(null!=admin){
			String wxId = admin.getWxId();
			if(null==wxId || wxId.equals("")){
				String code = req.getParameter("code");
				if(null!=code && !code.equals("")){
					 myFrameU.weixin.base.service.impl.UserImpl ui = new myFrameU.weixin.base.service.impl.UserImpl();
					 String openId = ui.getUserOpenId(code);
					 if(null!=openId && !openId.equals("")){
						 admin.setWxId(openId);
						 aBiz.modifyObject(admin);
						 
						 req.getSession().setAttribute("myAdmin", admin);
					 }
				}
			}
		}
		return "wrap/manage/user/adminWxIdOk";
	}
	
	
	@RequestMapping(value={"/admin/create2wm_adminWxId"})
	public void create2wm_adminWxId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Admin admin = (Admin)req.getSession().getAttribute("myAdmin");
		if(null!=admin){
			myFrameU.weixin.init.InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
			String adminWxId_2wmaUrl = ic.getAdminWxid_2wmUrl();
			if(null!=adminWxId_2wmaUrl && !adminWxId_2wmaUrl.equals("")){
				String imagePathName = "/img/user/admin2weima1.png";
				String path=PathUtil.getPhysicsPath();
				QRCodeEventsZXING.createImg(path+imagePathName,
						adminWxId_2wmaUrl, 400);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
