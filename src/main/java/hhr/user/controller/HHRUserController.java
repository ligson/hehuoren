package hhr.user.controller;

import hhr.user.biz.HHRUserI;
import hhr.user.util.UserUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.account.biz.AccountBizI;
import myFrameU.exception.exception.MyStrException;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.commonUtil.twoDimensional.QRCodeEventsZXING;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.base.entity.IfNosubscribe_subscribeEvent;
import myFrameU.weixin.base.service.impl.AutoRegistAndLoginImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HHRUserController extends FatherController {
	@Autowired
	private AccountBizI accountBiz;
	
	@Autowired
	private HHRUserI hhrUserBiz;
	
	
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}
	@Autowired
	private UserBizI userBiz;
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}

	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}


	public HHRUserI getHhrUserBiz() {
		return hhrUserBiz;
	}


	public void setHhrUserBiz(HHRUserI hhrUserBiz) {
		this.hhrUserBiz = hhrUserBiz;
	}


	@RequestMapping(value={"/wrap/user/searchTJR"})
	public String searchTuijianrens(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		User user = (User)req.getSession().getAttribute("myUser");
		String search=form.getString("search");
		/*if(null!=user){
			String myTelphone = user.getTelPhone();
			if(null!=myTelphone && !myTelphone.equals("")){
				if(myTelphone.equals(search)){
					req.setAttribute("", arg1);
				}
			}
		}*/
		
		if(null!=search && !search.equals("")){
			search=search.trim();
			String verPhone = PhoneUtil.vailterTelPhone(search);
			String queryType=null;
			if(null==verPhone){
				//说明是合格的手机号码
				queryType="phone";
			}else{
				queryType="nicheng";
			}
			List<User> userList = null;
			if(queryType.equals("phone")){
				userList=(List<User>)aBiz.findObjectList(User.class, new Object[]{search}, "from User as u where u.telPhone=? and u.status='OK'", null, false, 0, 0);
			}else if(queryType.equals("nicheng")){
				userList=(List<User>)aBiz.findObjectList(User.class, new Object[]{"%"+search+"%"}, 
						"from User as u where u.nicheng like ? and u.status='OK'", null, false, 0, 0);
			}
			//不能选自己
			//且必须是合伙人
			req.setAttribute("userList", userList);
		}
		return "wrap/manage/user/user/tjrListAjax";
	}
	
	
	@RequestMapping(value={"/wrap/user/beHeFensi"})
	public void beHeFensi(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		String queryUserType = form.getString("queryUserType");
		User user = (User)req.getSession().getAttribute("myUser");
		if(null==queryUserType || queryUserType.equals("")){
			queryUserType="id";
		}
		if(null!=queryUserType && !queryUserType.equals("") && null!=user){
			User he = null;
			if(queryUserType.equals("id")){
				Integer id = form.getInteger("id");
				he=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{id});
			}else if(queryUserType.equals("telPhone")){
				String telPhone = form.getString("telPhone");
				he=(User)aBiz.findObjectById("from User as u where u.telPhone=?", new Object[]{telPhone});
			}
			if(null!=he){
				/**
				 * 判断这个user是否可以成为he的粉丝
				 * 	1、这个user必须没有其他的推荐人，他的推荐人只能有一个，也就是他之i能隶属于一个人
				 *  2、这个he是不是合伙人（试用期+正式的），普通的人没有这个资格吸收粉丝
				 *  
				 *  3、相互不能是彼此的推荐人
				 */
				int he_levelId=he.getUserLevelId();
				if(he_levelId==1){
					throw new MyStrException("抱歉，该用户不是合伙人，您不能指定他为您的推荐人");
				}else{
					//正式和试用期的都算
					int tjrId = user.getTuijianRenId();
					if(tjrId==0){
						int heTuijianrenId=he.getTuijianRenId();
						if(heTuijianrenId!=user.getId()){
							hhrUserBiz.beFensi(user, he, uICacheManager, accountBiz);
							user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{user.getId()});
							req.getSession().setAttribute("myUser", user);
						}else{
							throw new MyStrException("抱歉，对方的推荐人是您，您不能指定对方为推荐人");
						}
						
					}else{
						throw new MyStrException("抱歉，您已经有了自己的推荐人了，不可再指定");
					}
				}
			}
		}
	}
	
	

	/**
	 * 扫描二维码，弹出一个页面来，这个页面有一个确定按钮
	 * /wrap/user/beHeFensiJSP?queryUserType=id&id=
	 * 判断是否登录了，如果没有登录，则根据微信号自动登录，如果没有微信号的则提示
	 */
	@RequestMapping(value={"/wrap/beHeFensiJSP"})
	public String beHeFensiJSP(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String guanzhuWX="yes";
		User user = (User)req.getSession().getAttribute("myUser");
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer id = form.getInteger("userId");
		if(null==user){
			String code = req.getParameter("code");
			if(null!=code && !code.equals("")){
				IfNosubscribe_subscribeEvent e = new IfNosubscribe_subscribeEvent();
				e.setEventType(IfNosubscribe_subscribeEvent.EVENTTYP.BeHeFensi.toString());
				Map<String,String> argsMap = new HashMap<String,String>();
				argsMap.put("heId", id+"");
				e.setArgs(argsMap);
				
				AutoRegistAndLoginImpl wx_aral = new AutoRegistAndLoginImpl();
				user = wx_aral.autoRegistLogin(user, code, userBiz, uICacheManager,e);
				if(null!=user){
					String nickName = user.getNicheng();
					String ife = user.getIfNosubscribe_subscribeEvent();
					if(null==nickName || nickName.equals("")){
						guanzhuWX="no";
					}
					if(null!=ife && !ife.equals("")){
						guanzhuWX="no";
					}
					
					req.getSession().setAttribute("myUser", user);
				}else{
					guanzhuWX="no";
				}
			}
		}
		req.setAttribute("guanzhuWX", guanzhuWX);
		if(guanzhuWX.equals("no")){
			//自动登录
			//不要自动登录了，那样就必须限定在微信里了
			//return "wrap/fg/userLogin";
			//经历了自动登录和自动注册，之后发现user还是为空，说明什么？说明他根本就没有关注微信公共平台
		}else{
			if(null!=id && id.intValue()!=0){
				User he = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{id});
				if(null!=he){
					req.setAttribute("he", he);
				}
			}
		}
		return "wrap/manage/user/user/beHeFensiJSP";
	}
	
	@RequestMapping(value={"/wrap/user/findMyFensi"})
	public String findMyFensi(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		if(null!=user){
			List<User> fensiList = UserUtil.getFSs(user.getId(), aBiz, req);
			req.setAttribute("fensiList", fensiList);
		}
		return "wrap/manage/user/user/myFensiList";
	}
	
	@RequestMapping(value={"/wrap/user/findMyTJR"})
	public String findMyTJR(HttpServletRequest req,HttpServletResponse res) throws Exception{
		User user = (User)req.getSession().getAttribute("myUser");
		User tjr = UserUtil.getMyTJR(user, accountBiz);
		req.setAttribute("myTJR", tjr);
		return "wrap/manage/user/user/myTjr";
	}
	
	
	
	@RequestMapping(value={"/admin/user/modifyScore"})
	public void modifyScore(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Float totalScore = form.getFloat("totalScore");
		Integer id = form.getInteger("id");
		if(null!=totalScore && totalScore.floatValue()>0 && null!=id && id.intValue()!=0){
			User user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{id});
			if(null!=user){
				user.setTotalScore(totalScore);
				aBiz.modifyObject(user);
			}
		}
	}
	
	
	
	
	
	
	
	//===============================================================
	//user/beHeFensi?queryUserType=id&id=
	@RequestMapping(value={"/wrap/user/my2wm"})
	public String my2wm(HttpServletRequest req,HttpServletResponse res) throws Exception{
		System.out.println("wrap/user/my2wm......................................");
		SDynaActionForm form = this.getSDynaActionForm(req);
		User user=(User)req.getSession().getAttribute("myUser");
		if(null==user){
			
			String code = req.getParameter("code");
			if(null!=code && !code.equals("")){
				/*IfNosubscribe_subscribeEvent e = new IfNosubscribe_subscribeEvent();
				e.setEventType(IfNosubscribe_subscribeEvent.EVENTTYP.BeHeFensi.toString());
				Map<String,String> argsMap = new HashMap<String,String>();
				argsMap.put("heId", id+"");
				e.setArgs(argsMap);*/
				
				AutoRegistAndLoginImpl wx_aral = new AutoRegistAndLoginImpl();
				user = wx_aral.autoRegistLogin(user, code, userBiz, uICacheManager,null);
				req.getSession().setAttribute("myUser", user);
			}
		}
		
		if(null!=user){
			String my2wm = user.getMy2wm();
			if(null==my2wm || my2wm.equals("")){
				//没有，就得现在去生成
				//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx247c7e42bcde403d
				//&redirect_uri=http://weixin.024lm.com/024lm/wrap/user/my2wm
				//&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
				/*InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
				String appId= ic.getAppId();
				StringBuffer urlsb = new StringBuffer();
				urlsb.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(appId)
				.append("&redirect_uri=").append(PathUtil.getBasePath_no(req)).append("/wrap/user/beHeFensiJSP?queryUserType=id&id=").append(user.getId())
				.append("&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				String url = urlsb.toString();*/
				
				myFrameU.weixin.init.InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
				
				StringBuffer urlsb = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
				urlsb.append(ic.getAppId()).append("&redirect_uri=").append(PathUtil.getBasePath_no(req))
				.append("/wrap/beHeFensiJSP?userId=").append(user.getId()).append("&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				String imagePathName = "/img/user/user2weima"+user.getId()+".png";
				QRCodeEventsZXING.createImg(PathUtil.getPhysicsPath()+imagePathName,
						urlsb.toString(), 400);
				
				user.setMy2wm(imagePathName);
				aBiz.modifyObject(user);
				req.getSession().setAttribute("myUser", user);
			}
		}else{
			//如果还为空，说明需要去注册
			return "wrap/fg/userRegist";
		}
		return "wrap/manage/user/user/my2wm";
	}
	
	
	@RequestMapping(value={"/batchCreateUser2MA"})
	public void batchCreateUser2MA(HttpServletRequest req,HttpServletResponse res) throws Exception{
		List<User> userList = (List<User>)aBiz.findObjectList(User.class, null, "from User", null, false, 0, 0);
		if(null!=userList){
			int size = userList.size();
			User user = null;
			String wxId = null;
			myFrameU.weixin.init.InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
			
			String url="";
			for(int i=0;i<size;i++){
				user = userList.get(i);
				wxId = user.getWxId();
				if(null!=wxId && !wxId.equals("")){
					StringBuffer urlsb = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
					urlsb.append(ic.getAppId()).append("&redirect_uri=").append(PathUtil.getBasePath_no(req))
					.append("/wrap/beHeFensiJSP?userId=").append(user.getId()).append("&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
					String imagePathName = "/img/user/user2weima"+user.getId()+".png";
					QRCodeEventsZXING.createImg(PathUtil.getPhysicsPath()+imagePathName,
							urlsb.toString(), 400);
					System.out.println("重新-------------------------------------"+urlsb.toString());
					
				}
			}
		}
	}
	
	
	
	
	
	
	
}
