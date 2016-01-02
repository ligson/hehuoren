package myFrameU.user.controller;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import myFrame.cache.CacheKey;
import myFrameU.account.entity.Account;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.user.init.InitConfig;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.PasswordUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.commonUtil.twoDimensional.TwoDimensionalCodeSwetakeQRCodeUtil;
import myFrameU.util.httpUtil.cookie.CookieUtil;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;
import myFrameU.weixin.base.entity.WXUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController extends FatherController {
	@Autowired
	private UserBizI userBiz;
	
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}
	@RequestMapping(value={"/toUserLogin"})
	public String toLogin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String fix = WebAop.getReqPrefix(req);
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
			String className=initConfig.getAfterClass();
			String method=(initConfig.getAfterMethodMap()).get("afterLogin");
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(method, User.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class);
			m.invoke(c.newInstance(), user,req,uICacheManager,aBiz);
			if(null!=fix && fix.contains("/wrap/")){
				return "wrap/manage/user/index";
			}else{
				return "manage/user/index";
			}
		}
		if(null!=fix && fix.contains("/wrap/")){
			return "wrap/fg/userLogin";
		}else{
			return "pcjsp/userLogin";
		}
		
	}
	@RequestMapping(value={"/wrap/toUserLogin"})
	public String toLoginWrap(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/**
		 * 判断是否是点击微信要登录的，如果是
		 * 		则判断是否有这个微信号的User，如果有则直接自动登录
		 * 		如果没有则调到真正的登录页面
		 * 
		 * 得看看这个user当时注册时怎么注册的，如果是PC端注册的，这时候要wrap微信端登录，就得完善wxId
		 */
		SDynaActionForm form = getSDynaActionForm(req);
		
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==user){
			String code = req.getParameter("code");
			if(null!=code && !code.equals("")){
				 myFrameU.weixin.base.service.impl.UserImpl ui = new myFrameU.weixin.base.service.impl.UserImpl();
				 String openId = ui.getUserOpenId(code);
				 WXUser wxuser = ui.getWXUserByOpenId(openId, uICacheManager,"user");
				 req.getSession().setAttribute("myUserWX", wxuser);
				 user=(User)aBiz.findObjectById("from User as u where u.wxId=?", new Object[]{openId});
				 req.getSession().setAttribute("myUser", user);
			}
		}
		
		if(null!=user){
			InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
			String className=initConfig.getAfterClass();
			String method=(initConfig.getAfterMethodMap()).get("afterLogin");
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(method, User.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class);
			m.invoke(c.newInstance(), user,req,uICacheManager,aBiz);
			return "wrap/manage/user/index";
		}
		return "wrap/fg/userLogin";
		
	}
	
	
	@RequestMapping(value={"/userLogin","/wrap/userLogin"})
	public String userLogin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User userold = (User)req.getSession().getAttribute("myUser");
		String fix = WebAop.getReqPrefix(req);
		if(null==userold){
			String name = form.getString("name");
			//String forwardPage=form.getString("forwardPage");
			String password = form.getString("password");
			Integer rememberMe=form.getInteger("rememberMe");
			if(null==rememberMe){
				rememberMe=0;
			}
			if(null!=name && null!=password){
				//name可以是手机
				User user = (User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{name});
				if(null!=user){
					String status = user.getStatus();
					if(EnumUtil.equalsE(User.STATUS.FORZEN, status)){
						throw new MyRefererException("该用户已经被管理员冻结");
					}else{
						//存在这个用户
						String password_db = user.getPassword();
						if(null!=password_db){
							if(password.equals(password_db)){
								//登录成功
								req.getSession().setAttribute("myUser", user);
								if(rememberMe==1){
									CookieUtil.addCookie(res, "user_NamePwd", name+"_"+password, 3600*24*30*1);//1个月
								}
								
								InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
								String className=initConfig.getAfterClass();
								String method=(initConfig.getAfterMethodMap()).get("afterLogin");
								Class c = Class.forName(className);
								Method m = c.getDeclaredMethod(method, User.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class);
								m.invoke(c.newInstance(), user,req,uICacheManager,aBiz);
								
								
								
								String url=PathUtil.getBasePath(req)+"history/add?roleClassName=myFrameU.user.entity.User&roleId="+user.getId()+"&roleName="+user.getName();
								String content = HttpClientUtil.get(url, null);
								System.out.println(content);
								
								if(isWrap(req)){
									return "wrap/manage/user/index";
								}else{
									return "manage/user/index";
								}
							}else{
								throw new MyRefererException("您的密码填写错误");
							}
						}
					}
				}else{
					throw new MyRefererException("您的账号填写错误");
				}
			}else{
				throw new MyRefererException("请填写账号和密码");
			}
		}else{
			if(isWrap(req)){
				return "redirect:/wrap/user/index";
			}else{
				return "redirect:/user/index";
			}
		}
		return null;
	}
	
	

	@RequestMapping(value={"/user/index","/wrap/user/index"})
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		//登录成功
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			InitConfig initConfig = myFrameU.user.init.InitMavenImpl.ic;
			String className=initConfig.getAfterClass();
			String method=(initConfig.getAfterMethodMap()).get("afterLogin");
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(method, User.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class);
			m.invoke(c.newInstance(), user,req,uICacheManager,aBiz);
		}
		if(isWrap(req)){
			return "wrap/manage/user/index";
		}else{
			return "manage/user/index";
		}
		
	}
	
	
	
	@RequestMapping(value={"/userVerUserName"})
	public void verUserName( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name=form.getString("name");
		if(null!=name && !name.equals("")){
			String vailterPhone = PhoneUtil.vailterTelPhone(name);
			if(null!=vailterPhone){
				throw new MyStrException("登录账号必须是手机："+vailterPhone);
			}else{
			}
		}else{
			throw new MyStrException("请输入登录账号");
		}
	}
	
	@RequestMapping(value={"/userVerPassword"})
	public void verPassword( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String password=form.getString("password");
		if(null!=password && !password.equals("")){
			PasswordUtil.verPassword(password);
		}else{
			throw new MyStrException("请输入密码");
		}
	}
	
	
	@RequestMapping(value={"admin/user/regist"})
	public String regist(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		String name = form.getString("name");
		Integer addressId=form.getInteger("addressId");
		String password = form.getString("password");
		String touxiang = form.getString("touxiang");
		String nicheng = form.getString("nicheng");
		Integer userLevelId = form.getInteger("userLevelId");
		if(null==userLevelId){
			userLevelId=1;
		}
		if(null!=name && null!=password && null!=addressId){
			if(!name.equals("") && !password.equals("") ){
				/*boolean b = TextUtil.isEnglish(name);
				if(!b){
					throw new MyStrException("登录账号必须为英文字符");
				}*/
				PasswordUtil.verPassword(password);
				
				boolean phoneOk=true;
				String vailterPhone=null;
				if(userLevelId==1){
					phoneOk=true;
				}else{
					vailterPhone = PhoneUtil.vailterTelPhone(name);
					if(null!=vailterPhone){
						phoneOk=false;
					}else{
						
					}
				}
				if(phoneOk){
					User userOld=(User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{name});
					if(null!=userOld){
						throw new MyStrException("您的用户名或者手机号已经被注册");
					}
					User user = new User();
					Address a = (Address)aBiz.findObjectById("from Address as a where a.id=?", new Object[]{addressId});
					if(null!=a){
						user.setAddressTreeIds(a.getTreeId());
						user.setAddressId(a.getId());
					}
					
					//user.setTelPhone(telPhone);
					user.setZhuceDate(new Date());
					//user.setZhuceIp(ip);
					
					name=name.trim();
					user.setName(name);
					user.setTelPhone(name);
					password=password.trim();
					user.setPassword(password);
					if(null==touxiang || touxiang.equals("")){
						user.setTouxiang("/img/user/touxiang.jpg");
					}else{
						user.setTouxiang(touxiang);
					}
					
					user.setStatus(User.STATUS.OK.toString());
					
					user.setUserLevelId(userLevelId);
					user.setTotalScore(0);
					user.setGrade(0);
					user.setNicheng(nicheng);
					user.setFensiCount(0);
					
					userBiz.registUser(user,a);
					
					req.getSession().setAttribute("ganggangzhuce", "yes");
					req.getSession().setAttribute("myUser", user);
					
					/**
					 * 生成二维码
					 */
					String my2wm="/img/user/user2weima"+user.getId()+".png";
					TwoDimensionalCodeSwetakeQRCodeUtil.createImg(PathUtil.getPhysicsPath()+my2wm,
							PathUtil.getBasePath(req)+"/wrap/fguser/id"+user.getId()+".htm");
					user.setMy2wm(my2wm);
					userBiz.modifyObject(user);
				}else{
					throw new MyStrException(vailterPhone);
				}
			}else{
				throw new MyStrException("用户名、密码、昵称三者不能为空");
			}
		}else{
			throw new MyStrException("用户名、密码、昵称三者不能为空");
		}
		/*String prefix = WebAop.getReqPrefix(req);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/admin/")){
				return "manage/admin";
			}
		}*/
		
		return redirect;
	}
	

	
	
	
	
	@RequestMapping(value={"/user/logout","/wrap/user/logout"})
	public String logout(HttpServletRequest req,HttpServletResponse res) throws Exception{
		HttpSession session = req.getSession();
		session.removeAttribute("myUser");
		session.removeAttribute("myAddress");
		CookieUtil.removeCookieByName(req, "user_NamePwd",res);
		if(isWrap(req)){
			return "wrap/fg/userLogin";
		}else{
			return "pcjsp/userLogin";
		}
		//return "manage/user/login";
	}
	
	
	
/*	@RequestMapping(value="/user/modifyTx")
	public String modifyTx(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String touxiang=form.getString("touxiang");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			user.setTouxiang(touxiang);
			aBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
			req.getSession().removeAttribute("ganggangzhuce");
		}
		return null;
	}
	*/
	
	
	@RequestMapping(value="/user/modifyUser")
	public String modifyUser(HttpServletRequest req,HttpServletResponse res) throws Exception{
		req.getSession().removeAttribute("modifyOK");
		SDynaActionForm form = getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		//String email = form.getString("email");
		//String telPhone = form.getString("telPhone");
		String nicheng = form.getString("nicheng");
		String touxiang = form.getString("touxiang");
		//String qq = form.getString("qq");
		//String msn = form.getString("msn");
		Integer addressId = form.getInteger("addressId");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			/*user.setQq(qq);
			user.setMsn(msn);
			user.setEmail(email);
			user.setTelPhone(telPhone);*/
			user.setNicheng(nicheng);
			user.setTouxiang(touxiang);
			if(null!=addressId){
				Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				if(null!=a){
					user.setAddressTreeIds(a.getTreeId());
					user.setAddressId(a.getId());
				}
			}
			userBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
			req.getSession().setAttribute("modifyOK", "modifyOK");
		}
		return redirect;
	}
	
	@RequestMapping(value="wrap/user/modifyUser")
	public void modifyUserNC(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String nicheng = form.getString("nicheng");
		int addressId=form.getInteger("addressId");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			user.setNicheng(nicheng);
			if(addressId==0){
				addressId=420;
			}
			int oldAddId = user.getAddressId();
			if(addressId!=oldAddId){
				Address add = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
				if(null!=add){
					user.setAddressId(addressId);
					user.setAddressTreeIds(add.getTreeId());
				}
				
			}
			userBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
		}
	}
	
	
	
	@RequestMapping(value="/admin/user/finds")
	public String findAllUsers(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		String orderBy=form.getString("orderBy");
		String queryArgs=form.getString("queryArgs");
		String searchStr=form.getString("searchStr");
		aBiz.findEntitysByArgs(User.class, EntityTableUtil.tableName(User.class.getName()),
				queryArgs, orderBy, searchStr, true, 0, "userList", req);
		return this.getForward("user/userList", req);
	}
	
	
	@RequestMapping(value="/admin/user/findById")
	public String findUserById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		Integer id=form.getInteger("id");
		if(null!=id){
			User user = (User)userBiz.findObjectById("get", User.class, id, new String[]{"getUjfSet"});
			req.setAttribute("user", user);
		}
		return this.getForward("user/user", req);
	}
	
	@RequestMapping(value="/admin/user/openClose")
	public void openCloseUser(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		Integer id=form.getInteger("id");
		Integer status = (Integer) form.getInteger("status");
		if(null!=id){
			userBiz.j_execute("update t_user set status=? where id=?", new Object[]{status,id});
		}
	}
	
	
	@RequestMapping(value="/admin/user/modfyLevel")
	public void modfyLevel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String forwardPage=form.getString("forwardPage");
		Integer id=form.getInteger("id");
		Integer userLevelId = (Integer) form.getInteger("userLevelId");
		if(null!=id && null!=userLevelId && userLevelId.intValue()!=0 && id.intValue()!=0){
			User user = (User)userBiz.findObjectById("from User as u where u.id=?", new Object[]{id});
			if(null!=user){
				int oldUserLevelId=user.getUserLevelId();
				if(oldUserLevelId==1){
					throw new MyStrException("抱歉，虚拟用户不能修改为其他级别");
				}else{
					if(oldUserLevelId!=userLevelId){
						user.setUserLevelId(userLevelId);
						userBiz.modifyObject(user);
					}
				}
			}
		}
	}
	
	
	//=========================================================================================================================
	@RequestMapping(value={"user/myUser","wrap/user/myUser"})
	public String findMyUser(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		req.setAttribute("myUser", user);
		int addId = user.getAddressId();
		if(addId==0){
			addId=420;
		}
		Address add = (Address)CacheKey.CKAddress.ALLMAP.getObject(uICacheManager,addId);
		req.setAttribute("currentAddress", add);
		if(isWrap(req)){
			return "wrap/manage/user/user/user";
		}else{
			return "manage/user/user/user";
		}
		
	}
	
	@RequestMapping(value={"user/myUserLevel","wrap/user/myUserLevel"})
	public String findMyShopLevel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			//去掉虚拟用户的级别
		/*	List<UserLevel> userLevelList1=new ArrayList<UserLevel>();
			List<UserLevel> userLevelList = CacheKey.CKUserLevel.ALLMAP.getList(uICacheManager);
			int size = userLevelList.size();
			UserLevel ul1 = null;
			for(int i=0;i<size;i++){
				ul1=userLevelList.get(i);
				if(ul1.getId()!=1){
					userLevelList1.add(ul1);
				}
			}
			req.setAttribute("userLevelList", userLevelList1);
			int level=user.getUserLevelId();
			
			UserLevel ul = CacheKey.CKUserLevel.ALLMAP.getObject(uICacheManager, level);
			req.setAttribute("currentLevel", ul);
			
			Global g13=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 13);
			req.setAttribute("g13", g13);*/
		}
		if(isWrap(req)){
			return "wrap/manage/user/user/userLevel";
		}else{
			return "manage/user/user/userLevel";
		}
		
	}
	
	//===========================================================================================================
	@RequestMapping(value="user/security")
	public String security(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		req.setAttribute("myUser", user);
		
		/**
		 * 1、shopUser的密码，肯定设置了，这里就不用查询了
		 * 2、手机号绑定
		 * 3、支付宝绑定
		 * 4、支付密码设置
		 */
		Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{User.class.getName(),user.getId()});
		req.setAttribute("account", a);
		return "manage/user/security/index";
	}
	@RequestMapping(value={"user/security/toPassword","wrap/user/security/toPassword"})
	public String security2Password(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			
		}
		req.setAttribute("myUser", user);
		if(isWrap(req)){
			return "wrap/manage/user/security/password";
		}else{
			return "manage/user/security/password";
		}
		
	}
	@RequestMapping(value={"user/security/password","wrap/user/security/password"})
	public void securityModifyPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String password = form.getString("password");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			PasswordUtil.verPassword(password);
			myFrameU.sms.util.Util.verYZM(req);
			
			user.setPassword(password);
			aBiz.modifyObject(user);
			req.getSession().setAttribute("myUser", user);
			
		}
	}
	@RequestMapping(value={"user/security/toTelphone","wrap/user/security/toTelphone"})
	public String security2Telphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		req.setAttribute("myUser", user);
		if(isWrap(req)){
			return "wrap/manage/user/security/telPhone";
		}else{
			return "manage/user/security/telPhone";
		}
	}
	
	//用户的修改手机一定要注意，因为用户的登录账号就是手机号，必须要一起操作。
	@RequestMapping(value={"user/security/telphone","wrap/user/security/telphone"})
	public void securityModifyTelphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		String telPhone = form.getString("telPhones");
		String verTEL=PhoneUtil.vailterTelPhone(telPhone);
		if(null==verTEL){
			User userold = (User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{telPhone});
			if(null==userold){
				myFrameU.sms.util.Util.verYZM(req);
				
				user.setTelPhone(telPhone);
				user.setName(telPhone);
				aBiz.modifyObject(user);
				req.getSession().setAttribute("myUser", user);
			}else{
				throw new MyStrException("抱歉该手机号已经注册该平台，请您更换手机号！");
			}
		}else{
			throw new MyStrException(verTEL);
		}
	}
	//=================================================================================
	@RequestMapping(value={"toUserRegist","admin/user/toRegist"})
	public String userRegist(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String reqPrefix = WebAop.getReqPrefix(req);
		/*List<UserLevel> userLevelList = CacheKey.CKUserLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("userLevelList", userLevelList);*/
		
		Address currentAddress=CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 165);
		req.setAttribute("currentAddress", currentAddress);
		
		if(null!=reqPrefix && !reqPrefix.equals("")){
			if(reqPrefix.equals("/admin/")){
				return "manage/admin/user/regist";
			}else{
				return "pcjsp/userRegist";
			}
		}
		return "pcjsp/userRegist";
	}
	
	
	

	@RequestMapping(value={"userRegist_step_1"})
	public void regist_step_1(HttpServletRequest req,HttpServletResponse res) throws Exception{
		req.getSession().removeAttribute("registUser");
		SDynaActionForm form = getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		String name = form.getString("name");
		Integer addressId=form.getInteger("addressId");
		String password = form.getString("password");
		//String touxiang = form.getString("touxiang");
		String nicheng = form.getString("nicheng");
		Integer userLevelId = form.getInteger("userLevelId");
		if(null==userLevelId){
			userLevelId=3;
		}
		if(null!=name && null!=password){
			if(!name.equals("") && !password.equals("") ){
				/*boolean b = TextUtil.isEnglish(name);
				if(!b){
					throw new MyStrException("登录账号必须为英文字符");
				}*/
				PasswordUtil.verPassword(password);
				
				boolean phoneOk=true;
				String vailterPhone=null;
				if(userLevelId==1){
					phoneOk=true;
				}else{
					vailterPhone = PhoneUtil.vailterTelPhone(name);
					if(null!=vailterPhone){
						phoneOk=false;
					}else{
						
					}
				}
				if(phoneOk){
					User userOld=(User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{name});
					if(null!=userOld){
						throw new MyStrException("您的用户名或者手机号已经被注册");
					}
					User user = new User();
					Address a = (Address)aBiz.findObjectById("from Address as a where a.id=?", new Object[]{addressId});
					if(null!=a){
						user.setAddressTreeIds(a.getTreeId());
						user.setAddressId(a.getId());
					}
					
					//user.setTelPhone(telPhone);
					user.setZhuceDate(new Date());
					//user.setZhuceIp(ip);
					
					name=name.trim();
					user.setName(name);
					user.setTelPhone(name);
					password=password.trim();
					user.setPassword(password);
					user.setTouxiang("/img/user/touxiang.jpg");
					
					user.setStatus(User.STATUS.OK.toString());
					
					user.setUserLevelId(userLevelId);
					user.setTotalScore(0);
					user.setGrade(0);
					user.setNicheng(nicheng);
					
					
					req.getSession().setAttribute("registUser", user);
				}else{
					throw new MyStrException(vailterPhone);
				}
			}else{
				throw new MyStrException("用户名、密码、昵称三者不能为空");
			}
		}else{
			throw new MyStrException("用户名、密码、昵称三者不能为空");
		}
		/*String prefix = WebAop.getReqPrefix(req);
		if(null!=prefix && !prefix.equals("")){
			if(prefix.equals("/admin/")){
				return "manage/admin";
			}
		}*/
		
	}
	
	@RequestMapping(value={"toUserRegist2"})
	public String toUserRegist2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user=(User)req.getSession().getAttribute("registUser");
		if(null!=user){
			//myFrameU.sms.util.Util.verYZM(req);
			
		}else{
			Address currentAddress=CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 165);
			req.setAttribute("currentAddress", currentAddress);
			return "pcjsp/userRegist";
		}
		return "pcjsp/userRegist2";
	}
	
	@RequestMapping(value={"userRegist_step_2"})
	public String userRegist_step_2(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user=(User)req.getSession().getAttribute("registUser");
		if(null!=user){
			myFrameU.sms.util.Util.verYZM(req);
			int addressId = user.getAddressId();
			if(addressId==0){
				addressId=165;
			}
			Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
			if(null!=a){
				userBiz.registUser(user, a);
				req.getSession().removeAttribute("registUser");
				req.getSession().setAttribute("myUser", user);
			}
		}
		return "pcjsp/userRegist3";
	}
	
//===============================================================================================================================
	@RequestMapping(value={"wrap/userRegist"})
	public void wrap_regist(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		//String redirect = form.getString(CommonField.redirect);
		String name = form.getString("name");
		Integer addressId=form.getInteger("addressId");
		if(null==addressId || addressId.intValue()==0){
			addressId=420;
		}
		String password = form.getString("password");
		String touxiang = form.getString("touxiang");
		String nicheng = form.getString("nicheng");
		String wxId = form.getString("wxId");
		if(null!=wxId && !wxId.equals("")){
			User userOld=(User)aBiz.findObjectById("from User as u where u.wxId=?", new Object[]{wxId});
			if(null!=userOld){
				throw new MyStrException("抱歉，这个微信已经注册过用户了！");
			}
		}
		Integer userLevelId = form.getInteger("userLevelId");
		if(null==userLevelId){
			userLevelId=1;
		}
		//myFrameU.sms.util.Util.verYZM(req);
		
		if(null!=name && null!=password){
			if(!name.equals("") && !password.equals("") ){
				/*boolean b = TextUtil.isEnglish(name);
				if(!b){
					throw new MyStrException("登录账号必须为英文字符");
				}*/
				PasswordUtil.verPassword(password);
				
				boolean phoneOk=true;
				String vailterPhone=null;
				/*if(userLevelId==1){
					phoneOk=true;
				}else{
					vailterPhone = PhoneUtil.vailterTelPhone(name);
					if(null!=vailterPhone){
						phoneOk=false;
					}else{
						
					}
				}*/
				if(phoneOk){
					User userOld=(User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{name});
					if(null!=userOld){
						throw new MyStrException("您的用户名或者手机号已经被注册");
					}
					User user = new User();
					Address a = (Address)aBiz.findObjectById("from Address as a where a.id=?", new Object[]{addressId});
					if(null!=a){
						user.setAddressTreeIds(a.getTreeId());
						user.setAddressId(a.getId());
					}
					
					//user.setTelPhone(telPhone);
					user.setZhuceDate(new Date());
					//user.setZhuceIp(ip);
					
					name=name.trim();
					user.setName(name);
					user.setTelPhone("");
					password=password.trim();
					user.setPassword(password);
					
					user.setWxId(wxId);
					if(null!=touxiang && !touxiang.equals("")){
						user.setTouxiang(touxiang);
					}
					
					
					user.setStatus(User.STATUS.OK.toString());
					
					user.setUserLevelId(userLevelId);
					user.setTotalScore(0);
					user.setGrade(0);
					user.setNicheng(nicheng);
					
					userBiz.registUser(user, a);
					req.getSession().setAttribute("myUser", user);
					
					
					
					
				}else{
					throw new MyStrException(vailterPhone);
				}
			}else{
				throw new MyStrException("用户名、密码、昵称三者不能为空");
			}
		}else{
			throw new MyStrException("用户名、密码、昵称三者不能为空");
		}
		
		//toLogin(req, res);
		//return nicheng;
		
	}
	
	
	
	
	//==================================================================================
	@RequestMapping(value={"/wrap/user/userInfoIndex"})
	public String userInfoIndex(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		return "wrap/manage/user/user/index";
	}
	
	
	
	
	
	
		@RequestMapping(value={"verUserTelphone"})
		public void verUserTelphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			String telPhone = form.getString("telPhones");
			String verTEL=PhoneUtil.vailterTelPhone(telPhone);
			if(null==verTEL){
				User userold = (User)aBiz.findObjectById("from User as u where u.name=?", new Object[]{telPhone});
				if(null==userold){
					
				}else{
					boolean zijide=false;
					User user = (User)req.getSession().getAttribute("myUser");
					if(null!=user){
						String dbTelphone = user.getName();
						if(null!=dbTelphone && !dbTelphone.equals("")){
							if(dbTelphone.equals(telPhone)){
								//自己的
								zijide=true;
							}
						}
					}
					if(zijide){
						
					}else{
						throw new MyStrException("抱歉该手机号已经注册该平台，请您更换手机号！");	
					}
					
				}
			}else{
				throw new MyStrException(verTEL);
			}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
