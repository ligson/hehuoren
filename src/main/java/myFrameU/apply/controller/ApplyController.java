package myFrameU.apply.controller;

import hhr.message.MessageUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.admin.entity.Admin;
import myFrameU.apply.biz.ApplyBizI;
import myFrameU.apply.entity.Apply;
import myFrameU.apply.entity.ApplyType;
import myFrameU.apply.util.ApplyConstant;
import myFrameU.queryArgs.util.QueryArgsUtil;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ApplyController extends FatherController {
	@Autowired
	@Qualifier("accountTXApplyBiz")
	private ApplyBizI accountTXApplyBiz;
	@Autowired
	@Qualifier("userHHRApplyBiz")
	private ApplyBizI userHHRApplyBiz;
	
	
	
	
	public ApplyBizI getAccountTXApplyBiz() {
		return accountTXApplyBiz;
	}

	public void setAccountTXApplyBiz(ApplyBizI accountTXApplyBiz) {
		this.accountTXApplyBiz = accountTXApplyBiz;
	}

	public ApplyBizI getUserHHRApplyBiz() {
		return userHHRApplyBiz;
	}

	public void setUserHHRApplyBiz(ApplyBizI userHHRApplyBiz) {
		this.userHHRApplyBiz = userHHRApplyBiz;
	}

	@RequestMapping({"/user/apply/addApplySimple","/shop/apply/addApplySimple"})
	public void addApplySimple(HttpServletRequest req,HttpServletResponse res) throws Exception{
		
		SDynaActionForm form = getSDynaActionForm(req);
		String applyTypeKey = form.getString("applyTypeKey");//申请类型
		if(null!=applyTypeKey && !applyTypeKey.equals("")){
			if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
				accountTXApplyBiz.createApply(req, uICacheManager);
			}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
				userHHRApplyBiz.createApply(req, uICacheManager);
			}
			
			/*InitConfig initConfig = myFrameU.apply.init.InitMavenImpl.ic;
			Map<String,ApplyType> applyTypeMap=initConfig.getApplyTypeMap();
			ApplyType applyType= applyTypeMap.get(applyTypeKey);
			if(null!=applyType){
				req.setAttribute("uICacheManager", uICacheManager);
				String className=applyType.getDealWithApplyClassName();
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod("createApply",AbstractBizI.class,HttpServletRequest.class);
				Apply apply = (Apply)m.invoke(c.newInstance(), aBiz,req);
				if(null!=apply){
					//aBiz.addObject(apply);
					//SendPushService.sendToAdmin("管理员后台有新的申请待审批，请前去审批");
				}
			}*/
			
		}
	}
	
	@RequestMapping({"/admin/apply/findApplys","/shop/apply/findApplys","/user/apply/findApplys","/wrap/user/apply/findApplys","/wrap/shop/apply/findApplys"})
	public String findApplys(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String orderBy=form.getString("orderBy");
		String queryArgs=form.getString("queryArgs");
		String roleClassName = (String) req.getAttribute(CommonField.roleClassName);
		if(!roleClassName.equals(Admin.class.getName())){
			if(null!=queryArgs && !queryArgs.equals("")){
				queryArgs=queryArgs.substring(0,queryArgs.length()-1);
				queryArgs=queryArgs+",{'key':'sponsorClassName','value':'"+roleClassName+"'}]";
			}
			queryArgs=QueryArgsUtil.getRoleQueryArgs("sponsorId", "sponsorId", "", req);
		}
		
		String searchStr=form.getString("searchStr");
		Integer returnListSize=form.getInteger("returnListSize");
		String isPage=form.getString("isPage");
		boolean isPager=false;
		if(null==isPage){
			isPager=true;
		}else{
			if(isPage.equals("true")){
				isPager=true;
			}
		}
		
		
		System.out.println(isPager);
		aBiz.findEntitysByArgs(Apply.class, EntityTableUtil.tableName(Apply.class.getName()),
				queryArgs, orderBy, null, isPager, 0, "applyList", req);
		return getForward("apply/applyList", req);
	}
	
	
	
	
	@RequestMapping({"/admin/apply/findById","/shop/apply/findById","/user/apply/findById"})
	public String findById(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		if(null!=id){
			Apply a=(Apply)aBiz.findObjectById("from Apply as a where a.id=?", new Object[]{id});
			req.setAttribute("apply", a);
			if(null!=a){
				boolean myself=false;
				String prefix=WebAop.getReqPrefix(req);
				if(prefix.equals("/admin/")){
					myself=true;
				}else{
					String className1 = a.getSponsorClassName();
					if(className1.equals(Shop.class.getName())){
						Shop shop = (Shop)req.getSession().getAttribute("myShop");
						if(null!=shop){
							if(a.getSponsorId()==shop.getId()){
								myself=true;
							}
						}
					}else if(className1.equals(User.class.getName())){
						User user = (User)req.getSession().getAttribute("myUser");
						if(null!=user){
							if(a.getSponsorId()==user.getId()){
								myself=true;
							}
						}
					}
				}
				
				
				
				if(myself){
					String applyTypeKey=a.getApplyTypeKey();
					if(null!=applyTypeKey && !applyTypeKey.equals("")){
						if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
							accountTXApplyBiz.findById(a, req, uICacheManager);
						}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
							userHHRApplyBiz.findById(a, req, uICacheManager);
						}
					}
					/*InitConfig initConfig = myFrameU.apply.init.InitMavenImpl.ic;
					Map<String,ApplyType> applyTypeMap=initConfig.getApplyTypeMap();
					ApplyType applyType= applyTypeMap.get(applyTypeKey);
					if(null!=applyType){
						String className=applyType.getDealWithApplyClassName();
						Class c = Class.forName(className);
						Method m = c.getDeclaredMethod("findById",Apply.class,AbstractBizI.class,HttpServletRequest.class);
						m.invoke(c.newInstance(),a,aBiz,req);
					}*/
				}
			}
		}
		return getForward("apply/apply", req);
	}
	
	
	
	@RequestMapping({"/admin/apply/findLast","/shop/apply/findLast","/user/apply/findLast"})
	public String findLast(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String applyTypeKey = form.getString("applyTypeKey");//申请类型
		if(null!=applyTypeKey && !applyTypeKey.equals("")){
			if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
			}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
			}
			/*InitConfig initConfig = myFrameU.apply.init.InitMavenImpl.ic;
			Map<String,ApplyType> applyTypeMap=initConfig.getApplyTypeMap();
			ApplyType applyType= applyTypeMap.get(applyTypeKey);
			if(null!=applyType){
				String className=applyType.getDealWithApplyClassName();
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod("findByLast",String.class,AbstractBizI.class,HttpServletRequest.class);
				m.invoke(c.newInstance(),applyTypeKey,aBiz,req);
			}*/
		}
		return getForward("apply/apply", req);
	}
	
	
	
	
	
	@RequestMapping({"/admin/apply/approval"})
	public void approval(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer id = form.getInteger("id");
		String result = form.getString("result");
		String remarks=form.getString("remarks");
		if(null!=id && null!=result){
			Apply a=(Apply)aBiz.findObjectById("from Apply as a where a.id=?", new Object[]{id});
			if(null!=a){
				String applyTypeKey=a.getApplyTypeKey();
				if(null!=applyTypeKey && !applyTypeKey.equals("")){
					
					
					a.setDealDate(new Date());
					a.setRemarks(remarks);
					a.setSpeed(Apply.SPEED.FINISH.toString());
					a.setDealWithClassName(Admin.class.getName());
					a.setDealWithId(1);
					//aBiz.modifyObject(apply);
					
					
					
					if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
						if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
							a.setResult(Apply.RESULT.APPLYERROR.toString());
							accountTXApplyBiz.applyERROR(a, uICacheManager);
							
						}else if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
							a.setResult(Apply.RESULT.APPLYOK.toString());
							accountTXApplyBiz.applyOK(a, uICacheManager);
							
						}
					}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
						if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
							a.setResult(Apply.RESULT.APPLYERROR.toString());
							userHHRApplyBiz.applyERROR(a, uICacheManager);
							
						}else if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
							a.setResult(Apply.RESULT.APPLYOK.toString());
							userHHRApplyBiz.applyOK(a, uICacheManager);
							
						}
					}
					
					
					HashMap<String,Object> args = new HashMap<String,Object>();
					args.put("apply", a);
					MessageUtil.sendMssage(MessageUtil.MssageTYPE.APPLY.toString(), MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(),
							args, aBiz);
					
					/*
					
					String okOrError="";
					String className=applyType.getDealWithApplyClassName();
					Class c = Class.forName(className);
					Method m = null;
					if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
						m = c.getDeclaredMethod("applyOK",Apply.class,AbstractBizI.class);
						okOrError="ok";
					}else if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
						m = c.getDeclaredMethod("applyERROR",Apply.class,AbstractBizI.class);
						okOrError="error";
					}
					Apply apply =(Apply)m.invoke(c.newInstance(),a,aBiz);
					
					if(null!=apply){
						apply.setDealDate(new Date());
						if(okOrError.equals("ok")){
							apply.setResult(Apply.RESULT.APPLYOK.toString());
						}else if(okOrError.equals("error")){
							apply.setResult(Apply.RESULT.APPLYERROR.toString());
						}
						apply.setRemarks(remarks);
						apply.setSpeed(Apply.SPEED.FINISH.toString());
						
						apply.setDealWithClassName(Admin.class.getName());
						apply.setDealWithId(1);
						aBiz.modifyObject(apply);
						
						//SendPushService.sendToShop("管理员审批结束，请前去查看结果");
						
						//短信通知
						String status = myFrameU.sms.init.InitMavenImpl.ic.getStatus();
						if(null!=status && status.equals("open")){
							myFrameU.apply.util.ApplyUtil.smsSendApplyFinished(aBiz, apply);
						}
						HashMap<String,Object> args = new HashMap<String,Object>();
						args.put("apply", apply);
						MessageUtil.sendMssage(MessageUtil.MssageTYPE.APPLY.toString(), MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(),
								args, aBiz);
						
					}*/
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
