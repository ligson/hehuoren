package myFrame.admin;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import myFrameU.account.biz.AccountBizI;
import myFrameU.account.entity.Account;
import myFrameU.admin.entity.Admin;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.sms.sdkN.MySMSService;
import myFrameU.sms.sdkN.entity.YEEntity;

import org.springframework.context.ApplicationContext;

public class AfterAdmin {
	
	public void afterLogin(Admin admin,HttpServletRequest req,UICacheManager cache,AbstractBizI aBiz) throws Exception{
		System.out.println("管理员登录成功了，，我要做一些事情");
		ApplicationContext applicationContext = (ApplicationContext) req.getSession().getServletContext().getAttribute("applicationContext");
		AccountBizI accountBiz=(AccountBizI)applicationContext.getBean("accountBiz");
		if(null!=admin){
			
			int adminId = admin.getId();
			admin = (Admin)aBiz.findObjectById("from Admin as a where a.id=?", new Object[]{adminId});
			req.getSession().setAttribute("myAdmin", admin);
			
		
			/**
			 * 1、获取account
			 * 2、
			 */
			Account account = accountBiz.account_findAccount(Admin.class.getName(), admin.getId());
			req.setAttribute("account", account);
			
			/**
			 * 2、获取订单信息
			 * 		1、待用户支付订单数量
			 * 		2、用户拒绝支付，待付保证金给商家的订单数量
			 * 		3、待发货数量
			 * 3、待审批申请数量
			 * 4、
			 */
			BigInteger orderCount_WAITPAY=(BigInteger)aBiz.j_queryObject("select count(id) from order_order where status='WAITPAY'" , null);
			req.setAttribute("orderCount_WAITPAY", orderCount_WAITPAY);
			
			BigInteger orderCount_PAYED=(BigInteger)aBiz.j_queryObject
					("select count(id) from order_order where status='PAYED' or status='PAYING'" , null);
			req.setAttribute("orderCount_PAYED", orderCount_PAYED);
			
			//成功付款
			BigInteger orderCount_PICKUPED=(BigInteger)aBiz.j_queryObject("select count(id) from order_order where status='PICKUPED'" , null);
			req.setAttribute("orderCount_PICKUPED", orderCount_PICKUPED);
			
			BigInteger orderCount_CLOSE=(BigInteger)aBiz.j_queryObject("select count(id) from order_order where status='CLOSE'" , null);
			req.setAttribute("orderCount_CLOSE", orderCount_CLOSE);
			
			
			HashMap<String,String> applyKey_count_map = new HashMap<String,String>();
			List<String> applyWAITINGCountList = (List<String>)aBiz.j_queryObjectList("select concat(applyTypeKey,'-',convert(count(id),char)) from apply_apply where (speed='WAIT' or speed='ING') group by applyTypeKey", null);
			if(null!=applyWAITINGCountList){
				int size = applyWAITINGCountList.size();
				String s = null;
				String[] array=null;
				String key = null;
				String count = null;
				for(int i=0;i<size;i++){
					s=applyWAITINGCountList.get(i);
					array=s.split("-");
					if(null!=array){
						if(array.length==2){
							key=array[0];
							count=array[1];
							applyKey_count_map.put(key, count);
						}
					}
				}
				req.setAttribute("applyKey_count_map", applyKey_count_map);
			}
			
			
			
			
			YEEntity ye=MySMSService.getYE();
			req.setAttribute("duanxin", ye);
			
			
			
			
			
			
			
			
			
			
			
			
		}
	}
}
