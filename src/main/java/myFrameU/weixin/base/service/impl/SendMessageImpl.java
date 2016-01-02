package myFrameU.weixin.base.service.impl;

import hhr.user.biz.HHRUserI;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.user.biz.UserBizI;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.base.entity.IfNosubscribe_subscribeEvent;
import myFrameU.weixin.base.entity.NewsTuwen;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;
import myFrameU.weixin.base.entity.TemplateData;
import myFrameU.weixin.base.entity.TemplateMsg;
import myFrameU.weixin.base.entity.WXUser;
import myFrameU.weixin.base.service.SendMessageInterface;
import myFrameU.weixin.base.util.FormatXmlProcess;
import myFrameU.weixin.base.util.GetWXUserUtil;
import myFrameU.weixin.base.util.HttpClientConnectionManager;
import myFrameU.weixin.base.util.SendTemplateMsgArgsUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.context.ApplicationContext;


public class SendMessageImpl implements SendMessageInterface {
	/*@Autowired
	private UserBizI userBiz;
	public UserBizI getUserBiz() {
		return userBiz;
	}
	public void setUserBiz(UserBizI userBiz) {
		this.userBiz = userBiz;
	}*/
	public static DefaultHttpClient httpclient;
	static {    
        httpclient = new DefaultHttpClient();    
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端    
    }  
	
	@Override
	public void reply(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception{
		if(null!=rxe){
			//ApplicationContext applicationContext = (ApplicationContext) req.getSession().getServletContext().getAttribute("applicationContext");
			//UICacheManager uICacheManager=(UICacheManager)applicationContext.getBean("uICacheManager");
			
			String MsgType=rxe.getMsgType();
			String eventType=rxe.getEvent();
			String eventKey=rxe.getEventKey();
			String fromUser=rxe.getFromUserName();
			String toUser=rxe.getToUserName();
			String createTime=rxe.getCreateTime();
			String msgId=rxe.getMsgId();
			
			
			//System.out.println("MsgType="+MsgType+";;eventType="+eventType+";;eventKey="+eventKey);
			
			//WXUser wxUser = GetWXUserToCookie.copyUserToCookie(uICacheManager, rxe, req, response);
			
			if(MsgType.equals("event")){
				if(eventType.equals("subscribe")){
					reply_event_subscribe(rxe,req,response,uICacheManager,aBiz);
				}else if(eventType.equals("CLICK")){
					reply_event_click(rxe,req,response,uICacheManager,aBiz);
				}else if(eventType.equals("VIEW")){
					if(eventKey.contains("wrap/toUserRegist") || eventKey.contains("wrap/toShopRegist")){
						
					}
				}
			}else if(MsgType.equals("text")){
				//reply_text(rxe,req,response);
			}
		}
	}

	@Override
	public void reply_event_subscribe
	(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager
			,AbstractBizI aBiz) throws Exception{
		String fromUser=rxe.getFromUserName();
		String toUser=rxe.getToUserName();
	
		/**
		 * 因为在扫描的时候已经自动注册了，虽然那时候的注册是只有wxId，还有if.....的字段
		 * 所以：
		 * 第一：根据openId，find wxUser（这时候的wxUser肯定是完善的）
		 * 第二：根据openId查找我们系统中的user
		 * 		1、如果没有，说明没有扫描二维码注册的
		 * 		2、如果有，则说明之前是扫描了别人的二维码而注册的
		 * 			这时候只有wxId，还有IF.....字段
		 * 		对于这两种情况分别处理，如果没有则重新insert一个完善的user
		 * 		如果有，则找到修缮，并且执行if的信息，并且清空if
		 */
		
		
		//第一步》获取微信用户，自动注册
		String userOpenId=fromUser;
		UserImpl ui = new UserImpl();
		WXUser wxUser = GetWXUserUtil.getWXUser();
		 if(null==wxUser){
			 wxUser = ui.getWXUserByOpenId(userOpenId, uICacheManager,"user");
		 }
		 
		//WXUser wxUser = ui.getWXUserByOpenId(userOpenId, uICacheManager, "user");
		if(null!=wxUser){
			ApplicationContext applicationContext = (ApplicationContext) req.getSession().getServletContext().getAttribute("applicationContext");
			UserBizI userBiz=(UserBizI)applicationContext.getBean("userBiz");
			if(null!=userBiz){
				System.out.println("1111111111111111111111111");
				User user=(User)aBiz.findObjectById("from User as u where u.wxId=?", new Object[]{userOpenId});
				if(null==user){
					System.out.println("222222222222222222222");
					int addressId = 420;
					Address cityAddress = null;
					String city=wxUser.getCity();
					if(null!=city && !city.equals("")){
						if(!city.endsWith("市")){
							city=city+"市";
						}
						cityAddress = (Address)aBiz.findObjectById("from Address as a where a.name=?", new Object[]{city});
						if(null==cityAddress){
							cityAddress = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 420);
						}
						if(null!=cityAddress){
							addressId=cityAddress.getId();
						}
					}
					
					
					String name = new Date().getTime()+"";
					user = new User();
					user.setUserLevelId(1);
					user.setNicheng(wxUser.getNickname());
					user.setWxId(wxUser.getOpenid());
					user.setAddressId(addressId);
					if(null!=cityAddress){
						user.setAddressTreeIds(cityAddress.getTreeId());	
					}
					user.setName(name);
					user.setPassword("123456");
					user.setTelPhone("");
					user.setTotalScore(0);
					user.setGrade(0);
					user.setZhuceDate(new Date());
					user.setTouxiang(wxUser.getOpenid()+".jpg");
					user.setStatus(User.STATUS.OK.toString());
					userBiz.registUser(user, cityAddress);
					req.getSession().setAttribute("myUser", user);
					
				}else{
					//登录
					String ife = user.getIfNosubscribe_subscribeEvent();
					if(null!=ife && !ife.equals("")){
						user.setNicheng(wxUser.getNickname());
						user.setIfNosubscribe_subscribeEvent(null);
						userBiz.modifyObject(user);
						req.getSession().setAttribute("myUser", user);
						/**
						 * 执行在关注的时候需要执行的代码
						 */
						IfNosubscribe_subscribeEvent ifnse = JSONUtils.toBean(ife, IfNosubscribe_subscribeEvent.class);
						if(null!=ifnse){
							String eventType = ifnse.getEventType();
							HashMap<String,String> args = (HashMap<String, String>) ifnse.getArgs();
							if(null!=eventType && !eventType.equals("")){
								if(EnumUtil.equalsE(IfNosubscribe_subscribeEvent.EVENTTYP.BeHeFensi, eventType)){
									if(null!=args){
										String tjrId = args.get("heId");
										if(null!=tjrId && !tjrId.equals("")){
											int tjrIdInt = new Integer(tjrId);
											if(tjrIdInt!=0){
												HHRUserI hhrUserBiz=(HHRUserI)applicationContext.getBean("hhrUserBiz");
												AccountBizI accountBiz=(AccountBizI)applicationContext.getBean("accountBiz");
												if(null!=hhrUserBiz && null!=accountBiz){
													User he=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{tjrIdInt});
													if(null!=he){
														int he_levelId=he.getUserLevelId();
														if(he_levelId==1){
															throw new MyStrException("抱歉，该用户不是合伙人，您不能指定他为您的推荐人");
														}else{
															//正式和试用期的都算
															int mytjrId = user.getTuijianRenId();
															if(mytjrId==0){
																hhrUserBiz.beFensi(user, he, uICacheManager, accountBiz);
																user = (User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{user.getId()});
																req.getSession().setAttribute("myUser", user);
																
																System.out.println("关注的时候，成功执行了成为某人的合伙人了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
																
																
															}else{
																throw new MyStrException("抱歉，您已经有了自己的推荐人了，不可再指定");
															}
														}
													}
												}
												
											}
										}
									}
								}else{
									//其他的关注的时候的执行代码。。。。。。。
								}
							}
						}
					}else{
						
					}
					req.getSession().setAttribute("myUser", user);
				}
			}
			
			
		}else{
			reply_event_subscribe(rxe, req, response, uICacheManager,aBiz);
			return;
		}
		
		
		
		//第二步：给关注者一些欢迎信息
		String subscribeTxt=myFrameU.weixin.init.InitMavenImpl.ic.getSubscribeTxt();
		String result = new FormatXmlProcess().formatXmlAnswer(fromUser, toUser,subscribeTxt);  
		OutputStream os = response.getOutputStream();
		os.write(result.getBytes("UTF-8"));
		os.flush();
		os.close();
	}

	@Override
	public void reply_event_click(ReceiveXmlEntity rxe,HttpServletRequest req,
			HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		//ApplicationContext applicationContext = (ApplicationContext) req.getSession().getServletContext().getAttribute("applicationContext");
		//UICacheManager uICacheManager=(UICacheManager)applicationContext.getBean("uICacheManager");
		String fromUser=rxe.getFromUserName();
		String toUser=rxe.getToUserName();
		String key=rxe.getEventKey();
		
		System.out.println(key+"============================="+key);
		
		if(key.equals("paimaiHelp")){
			
			String basePath  = PathUtil.getBasePath(req);
			
			System.out.println(basePath+"wrap/fg/image/help.jpg");
			
			
		
			List<NewsTuwen> ntList = new ArrayList<NewsTuwen>();
			NewsTuwen nt1=new NewsTuwen();
			nt1.setTitle("LNG重卡维修服务点");
			nt1.setDescription("LNG重卡维修服务点");
			nt1.setPicurl(basePath+"wrap/fg/image/help.jpg");
			nt1.setUrl(basePath+"wrap/fg/help/help-hhr.jsp");
			ntList.add(nt1);
			/*NewsTuwen nt2=new NewsTuwen();
			nt2.setTitle("订单帮助");
			nt2.setDescription("讲解订单流程");
			nt2.setPicurl(basePath+"wrap/fg/image/help-nav-order.jpg");
			nt2.setUrl(basePath+"wrap/fg/help/help-order.jsp");
			ntList.add(nt2);
			
			
			
			NewsTuwen nt4=new NewsTuwen();
			nt4.setTitle("财务帮助");
			nt4.setDescription("财务如何处理？");
			nt4.setPicurl(basePath+"wrap/fg/image/help-nav-ac.jpg");
			nt4.setUrl(basePath+"wrap/fg/help/help-account.jsp");
			ntList.add(nt4);
			
			

			Global g21=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 21);
			if(null!=g21){
				String kefu = g21.getMyValue();
				NewsTuwen nt5=new NewsTuwen();
				nt5.setTitle("客服电话:"+kefu);
				nt5.setDescription("客服电话");
				nt5.setPicurl(basePath+"wrap/fg/image/help.jpg");
				nt5.setUrl(basePath+"wrap/fg/help/help-lxwm.jsp");
				ntList.add(nt5);
			}
			*/
			
			String result = new FormatXmlProcess().formatXmlAnswer(fromUser, toUser, ntList);  
			System.out.println("66666666666666666666666666"+result);
			OutputStream os = response.getOutputStream();
			//os.write(result.getBytes());
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			
		}else if(key.equals("zaixianzixun")){
			String zxzxTxt="请直接点击左下角键盘，直接输入你要咨询的问题，我们会有专业人员及时为您解答...";
			String result = new FormatXmlProcess().formatXmlAnswer(fromUser, toUser,zxzxTxt);  
			OutputStream os = response.getOutputStream();
			os.write(result.getBytes("UTF-8"));
			os.flush();
			os.close();
		}
	}

	@Override
	public void reply_text(ReceiveXmlEntity rxe,HttpServletRequest req, 
			HttpServletResponse response,
			UICacheManager uICacheManager,AbstractBizI aBiz)
			throws Exception {
		String fromUser=rxe.getFromUserName();
		String toUser=rxe.getToUserName();
		String subscribeTxt=myFrameU.weixin.init.InitMavenImpl.ic.getSubscribeTxt();
		String result = new FormatXmlProcess().formatXmlAnswer(fromUser, toUser,subscribeTxt);  
		OutputStream os = response.getOutputStream();
		os.write(result.getBytes("UTF-8"));
		os.flush();
		os.close();
	}

/*	@Override
	public void sendTemplate_setIndustry(String industry_id1,String industry_id2,UICacheManager uICacheManager)
			throws Exception {
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=")
		.append(accessToken);
		*//**
		 *  {
          "industry_id1":"1",
          "industry_id2":"4"
       		}
		 *//*
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();  
		paramsList.add(new BasicNameValuePair("industry_id1", industry_id1));  
		paramsList.add(new BasicNameValuePair("industry_id2", industry_id2));  
		HttpClientUtil.post(sb.toString(), paramsList);
	}

	@Override
	public String sendTemplate_getTemplateId(String template_id_short,UICacheManager uICacheManager)
			throws Exception {
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=")
		.append(accessToken);
		
		
		
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();  
		paramsList.add(new BasicNameValuePair("template_id_short", template_id_short));  
		String result = HttpClientUtil.post(sb.toString(), paramsList);
		System.out.println(result);
		
		return null;
	}
*/
	@Override
	public String sendTemplate(TemplateMsg tm,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception {
		//String toUser, String template_id, List<TemplateData> dataList
		String toUser = tm.getOpenId();
		String template_id=tm.getTemplate_id();
		List<TemplateData> dataList = tm.getDataList();
		System.out.println("222222222222222222222222222222222222222222");
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		StringBuffer sb = new StringBuffer("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=")
		.append(accessToken);
		
		
		String params=SendTemplateMsgArgsUtils.getTemplateMsgArgs(tm);
		System.out.println("333333333333333333333333333333333"+params);
		if(null!=params && !params.equals("")){
			System.out.println("444444444444444444444444444444444444444");
			HttpPost httpost = HttpClientConnectionManager.getPostMethod(sb.toString());    
	        httpost.setEntity(new StringEntity(params, "UTF-8"));    
	        HttpResponse response = httpclient.execute(httpost);    
	        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
	        //JSONObject a = new JSONObject(jsonStr);
	        System.out.println(jsonStr);
		}
		return null;
	}


}
