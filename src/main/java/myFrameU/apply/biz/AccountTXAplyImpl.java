package myFrameU.apply.biz;

import hhr.order.entity.Order;
import hhr.user.biz.HHRUserI;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import myFrame.account.AccountConstant;
import myFrame.cache.CacheKey;
import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.account.entity.AccountItem;
import myFrameU.account.util.AccountUtil;
import myFrameU.account.util.AccountUtilHHR;
import myFrameU.apply.entity.Apply;
import myFrameU.apply.util.ApplyConstant;
import myFrameU.biz.AbstractBizImpl;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.enumU.EnumUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
@Service("accountTXApplyBiz")
public class AccountTXAplyImpl extends AbstractBizImpl implements ApplyBizI {
	@Autowired
	private AccountBizI accountBiz;
	
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}

	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}
	
	@Autowired
	@Qualifier("hhrUserBiz")
	private HHRUserI hhrUserBiz;
	
	

	public HHRUserI getHhrUserBiz() {
		return hhrUserBiz;
	}

	public void setHhrUserBiz(HHRUserI hhrUserBiz) {
		this.hhrUserBiz = hhrUserBiz;
	}

	@Override
	public Apply applyOK(Apply apply,UICacheManager uICacheManager) throws Exception {
		/**
		 * 审批通过：
		 */
		HashMap<String,String> extraMap = apply.getExtraDataMap();
		if(null!=extraMap){
			int roleId = new Integer(extraMap.get("roleId")).intValue();
			int accountId=new Integer(extraMap.get("accountId")).intValue();
			int accountItemId = new Integer(extraMap.get("accountItemId")).intValue();
			if(roleId!=0 && accountId!=0 && accountItemId !=0){
				AccountItem ai = accountBiz.findAccountItemById(accountItemId);
				if(null!=ai){
					/*ai.setStatus(AccountItem.STATUS.CLOSE.toString());
					aBiz.modifyObject(ai);*/
					
					float txPrice = ai.getPrice();
					/**
					 *  可用			冻结			总额			销售			宣传			可用宣传
					 * 	25.74		7.66		33.4		23.4		10			10-7.66=23.4x0.1
					 * 
					 * 
					 * 0			33.4		33.4		23.4		10
					 * 
					 * 
					 * ............在提交申请--与--审核之间所做的事情，依然参考的是23.4和10，因为你还没有审核呢，这俩数据还起作用
					 * 
					 * 
					 * 0			不用管		不用管	最新的-applyXS	最新的-可用applyXC
					 * 													30-(getAvailable_xcPrice(applyXC,applyXS))
					 * 														||
					 * 													30-(txPrice-applyXS)
					 * 				
					 * 
					 */
					//如果审核通过，则需要先处理xcPrice和xsPrice
					String whoClassName = ai.getWhoclassName();
					if(null!=whoClassName && whoClassName.equals(User.class.getName())){
						int whoId = ai.getWhoId();
						//原来一直增长的xcPrice-申请提现那时候的xcPrice=现在新的xcPrice(error)
						float theTimeApply_xcPrice = new Float(extraMap.get("xcPrice")).floatValue();
						float theTimeApply_xsPrice = new Float(extraMap.get("xsPrice")).floatValue();
						if(theTimeApply_xcPrice!=0 || theTimeApply_xsPrice!=0){
							Account account = accountBiz.account_findAccount(whoClassName, whoId);
							System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaacount=="+account.getFrozenPrice());//okkk
							float nowXcPrice = account.getXcPrice();//10
							float nowXsPrice = account.getXsPrice();//23.4
							float newXsPrice = nowXsPrice-theTimeApply_xsPrice;//最新的-applyXS  23.4-23.4=0
							//float newXcPrice = nowXcPrice-theTimeApply_xcPrice;
							float newXcPrice = nowXcPrice-(txPrice-theTimeApply_xsPrice);//10-(25.74-23.4)=7.66
							account.setXcPrice(newXcPrice);
							account.setXsPrice(newXsPrice);
							aDao.updateObject(account);
						}
					}
					accountBiz.account_withDrawals(ai);
					/**
					 * 短信通知
					 */
					aDao.updateObject(apply);
				}
			}
		}
		
		return apply;
	}

	@Override
	public Apply applyERROR(Apply apply,UICacheManager uICacheManager) throws Exception {
		System.out.println("管理员审批不通过 .......................");
		/**
		 * 审批不通过
		 * 		解冻那条因为提出提现申请而冻结的资金
		 */
		HashMap<String,String> extraMap = apply.getExtraDataMap();
		if(null!=extraMap){
			int roleId = new Integer(extraMap.get("roleId")).intValue();
			int accountId=new Integer(extraMap.get("accountId")).intValue();
			int accountItemId = new Integer(extraMap.get("accountItemId")).intValue();
			if(roleId!=0 && accountId!=0 && accountItemId !=0){
				AccountItem ai = accountBiz.findAccountItemById(accountItemId);
				if(null!=ai){
					accountBiz.closeAccountItem(ai);
					/**
					 * 短信通知
					 */
					aDao.updateObject(apply);
				}
			}
		}
		return apply;
	}

	@Override
	public void findById(Apply apply, HttpServletRequest req,UICacheManager uICacheManager) throws Exception {
		HashMap<String,String> extraDataMap=apply.getExtraDataMap();
		if(null!=extraDataMap){
			int userId = apply.getSponsorId();
			User user = (User)aDao.queryObjectById("from User as u where u.id=?", new Object[]{userId});
			if(null!=user){
				String accountItemIdStr=extraDataMap.get("accountItemId");
				String accountIdStr = extraDataMap.get("accountId");
				if(null!=accountItemIdStr && null!=accountIdStr && !accountIdStr.equals("") && !accountItemIdStr.equals("")){
					Account account = accountBiz.account_findAccount(apply.getSponsorClassName(), apply.getSponsorId());
					AccountItem accountItem = accountBiz.findAccountItemById(new Integer(accountItemIdStr).intValue());
					req.setAttribute("account", account);
					req.setAttribute("accountItem", accountItem);
					
					
					/**
					 * 这个用户的粉丝明细及粉丝的购买订单及提货日期明细等内容，方便审核
					 */
					
					HashMap<String,Object> map = hhrUserBiz.getUser_fs_and_fsOrders(user);
					if(null!=map){
						List<User> fensiList = (List<User>)map.get("fensiList");
						List<Order> fensiOrderList = (List<Order>)map.get("fensiOrderList");
						req.setAttribute("fensiList", fensiList);
						req.setAttribute("fensiOrderList", fensiOrderList);
						
					}
					
				}
			}
		}
	}

	@Override
	public Apply createApply(HttpServletRequest req,UICacheManager uICacheManager) throws Exception {
		Apply ap = null;
		String roleName=WebAop.getCurrentRoleName(req, this);
		String roleClassName=(String) req.getAttribute(CommonField.roleClassName);
		Integer roleId=(Integer) req.getAttribute(CommonField.roleId);
		String priceS = req.getParameter("price");
		/*String password = req.getParameter("password");
		
		if(null==password || password.equals("")){
			throw new MyStrException("请输入财务密码!");
		}*/
		User user = null;
		if(roleClassName.equals(User.class.getName())){
			user = (User)req.getSession().getAttribute("myUser");
		}
		
		if(null!=roleClassName && !roleClassName.equals("") && null!=priceS && !priceS.equals("") && !priceS.equals("0")){
			float price = new Float(priceS).floatValue();
			
			if(null!=roleId && roleId.intValue()!=0){
				
				System.out.println(roleClassName+"提出一个提现申请........................");
				String applyTypeKey = req.getParameter("applyTypeKey");//申请类型
				if(null!=applyTypeKey && !applyTypeKey.equals("")){
					Account account = accountBiz.account_findAccount(roleClassName, roleId);
					System.out.println(account.getXcPrice()+"==="+account.getXsPrice()+"--------------@@@@@@&&&&&&&&&&&&*********************");
					if(null!=account){
						
						/**
						 * 合伙人项目中，每次提现的price必须最少将销售佣金+可用宣传佣金全部提现出去
						 */
						
						if(roleClassName.equals(User.class.getName()) && null!=user){
							/*float xsPrice = account.getXsPrice();
							float available_xcPrice=AccountUtilHHR.getAvailable_xcPrice(uICacheManager, account);
							float leastPrice = xsPrice+available_xcPrice;*/
							float leastPrice=account.getAvailablePrice();
							if(leastPrice>price){
								throw new MyStrException("必须最少提现"+leastPrice+"元");
							}
						}
						
						
						Apply lastApply = (Apply)aDao.queryObjectById
						("from Apply as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=?", 
						new Object[]{User.class.getName(),user.getId(),ApplyConstant.APPLYTYPEKEY.AccountTXApply.toString()});
						boolean canTX=false;
						if(null==lastApply){
							canTX=true;
						}else{
							String speed=lastApply.getSpeed();
							if(EnumUtil.equalsE(Apply.SPEED.WAIT, speed)||EnumUtil.equalsE(Apply.SPEED.ING, speed)){
								canTX=false;
								throw new MyStrException("抱歉，您的上一条提现申请等待审核，请耐心等待。");
							}else if(EnumUtil.equalsE(Apply.SPEED.FINISH, speed)){
								canTX=true;
							}
						}
						if(canTX){

							/**
							 * 一个月最多可申请提现多少次
							 */
							Global g8=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 8);
							if(null!=g8){
								int cishu=new Integer(g8.getMyValue());
								int count = AccountUtil.getCount_tx_month(this, account.getId());
								if(count<cishu){
									float availablePrice = account.getAvailablePrice();
									if(price<=availablePrice){
										//暂时来看，可用余额是够了
										/**
										 * 提出申请的时候
										 * 	1、生成一个accountItem，是冻结的finish的
										 */
										HashMap<String,String> argsMap1 = new HashMap<String,String>();
										argsMap1.put("accountId", account.getId()+"");
										argsMap1.put("roleId", roleId+"");
										
										
										
										String extraData = AccountConstant.getExtraData(AccountConstant.ACCOUNTITEM_EXTRA_KEY.WITHDRAWALS_FORZEN.toString(), argsMap1);
										//不需要转义，因为现在不是查询
										AccountItem ai_forzen = accountBiz.forzen(account, price, extraData, "申请提现，冻结提现资金");
										if(null!=ai_forzen){
											ap = new Apply();
											ap.setApplyContent(roleName+"提出提现申请,提现金额"+price+"元，已将欲提现的资金冻结，请管理员审核");
											ap.setApplyTitle("申请提现"+price+"元");
											ap.setApplyTypeKey(applyTypeKey);
											
											HashMap<String,String> argsMap = new HashMap<String,String>();
											argsMap.put("accountId", account.getId()+"");
											argsMap.put("accountItemId", ai_forzen.getId()+"");
											argsMap.put("roleId", roleId+"");
											argsMap.put("xcPrice", account.getXcPrice()+"");
											argsMap.put("xsPrice", account.getXsPrice()+"");
											
											
											
											String extra=ApplyConstant.getExtraData(ApplyConstant.APPLYTYPEKEY.AccountTXApply.toString(), argsMap);
											//不需要转义，现在不是查询
											ap.setExtraData(extra);
											ap.setRelDate(new Date());
											ap.setSpeed(Apply.SPEED.WAIT.toString());
											ap.setSponsorClassName(roleClassName);
											ap.setSponsorId(roleId);
											ap.setSponsorName(roleName);
											aDao.insertObject(ap);
											
											/*if(ap.getId()!=0){
												throw new MyStrException("测试异常....................");
											}*/
											
											
											return ap;
											
											
										}
										

									}else{
										throw new MyStrException("可用余额不足");
									}
								}else{
									throw new MyStrException("抱歉，您本月已经用完提现申请次数！");
								}
							}
						}
						/*AccountItem forzenAI_TX=AccountUtil.findUniqueAI_forzen_forTX(aBiz, roleId, account.getId());
						if(null==forzenAI_TX){
							
						}else{
							throw new MyStrException("抱歉，您现在还有一条提现申请尚未做审批，请等待");
						}*/
					}
				}
			}
		}
		return ap;
	}

}
