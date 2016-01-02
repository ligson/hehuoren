package test;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.account.biz.AccountBizI;
import myFrameU.biz.AbstractBizI;
import myFrameU.spring.mvc.FatherController;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class Test  extends FatherController{
	@Autowired
	@Qualifier("accountBiz")
	private AccountBizI accountBiz;
	@Autowired
	private AbstractBizI aBiz;
	
	public AccountBizI getAccountBiz() {
		return accountBiz;
	}



	public AbstractBizI getaBiz() {
		return aBiz;
	}



	public void setAccountBiz(AccountBizI accountBiz) {
		this.accountBiz = accountBiz;
	}



	public void setaBiz(AbstractBizI aBiz) {
		this.aBiz = aBiz;
	}


	@RequestMapping("/admin/test/test/test")
	public  void test(HttpServletRequest req,HttpServletResponse res){
		String mi = null;
		InetAddress myIPaddress;
		try {
			myIPaddress = InetAddress.getLocalHost();
			mi = myIPaddress.getHostAddress();
			System.out.println(mi);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.renderData(res, mi);
	}


	@RequestMapping("/admin/test/mustclear")
	public void mustclear(HttpServletRequest req) throws Exception{
		String nojichu=req.getParameter("nojichu");
		/**
		 * 必须要删除的一些模块，主要是测试数据
		 */
		
		//1、财务模块
		aBiz.j_execute("delete from accountItem", null);
		aBiz.j_execute("delete from account where id>1", null);
		aBiz.j_execute("update account set totalPrice=0,frozenPrice=0,availablePrice=0,xsPrice=0,xcPrice=0", null);
		
		//apply
		aBiz.j_execute("delete from apply_apply", null);
		//order
		aBiz.j_execute("delete from order_orderitem", null);
		aBiz.j_execute("delete from order_order", null);
		
		//user
		aBiz.j_execute("delete from user_user", null);
		
		//history
		aBiz.j_execute("delete from history", null);
		
		//积分
		aBiz.j_execute("delete from integrationitem", null);
		aBiz.j_execute("delete from integration", null);
		
		//sms
		aBiz.j_execute("delete from smsrecord", null);
		
	}
	


	@RequestMapping("/admin/test/allclear")
	public void allclear(HttpServletRequest req) throws Exception{
		String nojichu=req.getParameter("nojichu");
		/**
		 * 必须要删除的一些模块，主要是测试数据
		 */
		
		//1、财务模块
		aBiz.j_execute("delete from accountItem", null);
		aBiz.j_execute("delete from account where id>1", null);
		aBiz.j_execute("update account set totalPrice=0,frozenPrice=0,availablePrice=0,xsPrice=0,xcPrice=0", null);
		
		//apply
		aBiz.j_execute("delete from apply_apply", null);
		//order
		aBiz.j_execute("delete from order_orderitem", null);
		aBiz.j_execute("delete from order_order", null);
		
		//product
		aBiz.j_execute("delete from product_product", null);
		aBiz.j_execute("delete from product_productcontent", null);
		
		
	
		//user
		aBiz.j_execute("delete from user_user", null);
		
		//history
		aBiz.j_execute("delete from history", null);
		
		//积分
		aBiz.j_execute("delete from integrationitem", null);
		aBiz.j_execute("delete from integration", null);
		
		//sms
		aBiz.j_execute("delete from smsrecord", null);
		
		//自提点
		aBiz.j_execute("delete from order_pickupaddress", null);
		
		//aBiz.j_execute("delete from adv_advertisement where advertising_id！=1", null);
	}
	
	

	//基础数据
	@RequestMapping("/admin/test/clear")
	public void testClearData(HttpServletRequest req) throws Exception{
		String clearType=req.getParameter("clearType");
		if(null!=clearType && !clearType.equals("")){
			if(clearType.equals("productType")){
				/**
				 * 清除pt，级联清除扩展属性、栏目广告
				 */
				//产品分类
				aBiz.j_execute("SET FOREIGN_KEY_CHECKS=0", null);
				aBiz.j_execute("delete from product_producttype", null);
				aBiz.j_execute("SET FOREIGN_KEY_CHECKS=1", null);
				
				
				//扩展属性
				aBiz.j_execute("delete from expand_systemlibrarypropertyvalue", null);
				aBiz.j_execute("delete from expand_propertydistribute", null);
				aBiz.j_execute("delete from expand_systemlibraryproperty", null);
				
				
				
				/*
				//广告--栏目广告
				aBiz.j_execute("delete from adv_advertisement where advertising_id=3", null);
				//aBiz.j_execute("delete from adv_advertising", null);
				*/
			}
		}
	}
	
	
	
	
	
	
	
		public static void main(String args[]){
			String content2=HttpClientUtil.get("http://localhost:8080/yishupaipai/sms/send?auctionPeriod_title=11&bidUserName=3232&sdkMtType=TOShop_AUCTION_END_BID_NOPAY&telPhones=3232&g7=32", null);
			System.out.println(content2);
			String content1=HttpClientUtil.get("http://localhost:8080/yishupaipai/history/add?historyKey=userLogin", null);
			System.out.println(content1);
			String content = HttpClientUtil.get("http://www.baidu.com", null);
			System.out.println(content);
		}
		
		public static List<String> mainPTIds2List(String mainPTIds){
			List<String> list =null;
			if(null!=mainPTIds){
				list = new ArrayList<String>();;
				//5][7][8
				mainPTIds=mainPTIds.substring(1,mainPTIds.length()-1);
				if(mainPTIds.contains("][")){
					String array[]=mainPTIds.split("]\\[");
					int len=array.length;
					String m = null;
					for(int i=0;i<len;i++){
						m=array[i];
						list.add(m);
					}
				}else{
					list.add(mainPTIds);
				}
			}
			return list;
		}

		public static void test(Object o ){
			System.out.println(o.getClass().getName());
			Class c=o.getClass();
			try {
				Method m = c.getDeclaredMethod("getId");
				int id=(Integer)m.invoke(o, null);
				System.out.println(id);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
}
