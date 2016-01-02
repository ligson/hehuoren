package myFrame.account;

import java.util.HashMap;

import myFrameU.exception.exception.MyApplyException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.util.commonUtil.enumU.EnumUtil;

public class AccountConstant {
	public enum ACCOUNTITEM_EXTRA_KEY{
		//申请提现引起的冻结资金AI，申请加入参加专场而冻结的，申请举办专场而冻结的,不管是参展还是举办展会最后保证金的支出
		//申请升级为VIP店铺的冻结的费用
		WITHDRAWALS_FORZEN,SHOPINSPECIAL_FORZEN,SHOPSPECIAL_FORZEN,SPECIAL_BZJ_SPENDING
		,UPDATEVIPSHOP_FORZEN,UPDATEVIPUSER_FORZEN,SHOPSTATUSWAIT2OK_FORZEN
		,ORDERPAY;
		//支付订单
		
	}
	public static String getExtraData(String applyTypeKey,HashMap<String, String> argsMap) throws Exception{
		String extra = null;
		if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.WITHDRAWALS_FORZEN, applyTypeKey)){
			//获取申请提现而冻结的accountItem的extraData
			//{'accountId':'5','roleId':'2','unique':'applyWITHDRAWALS'}
			String accountIdStr = argsMap.get("accountId");
			String roleIdStr=argsMap.get("roleId");
			/*String xcPrice = argsMap.get("xcPrice");
			String xsPrice = argsMap.get("xsPrice");
			if(null==xcPrice || xcPrice.equals("")){
				throw new MyApplyException("xcPrice必须传递");
			}
			if(null==xsPrice || xsPrice.equals("")){
				throw new MyApplyException("xsPrice必须传递");
			}*/
			if(null==accountIdStr || accountIdStr.equals("")){
				throw new MyStrException("请传入accountId值");
			}
			if(null==roleIdStr || roleIdStr.equals("")){
				throw new MyStrException("请传入roleId值");
			}
			//extra="{'accountId':'"+accountIdStr+"','roleId':'"+roleIdStr+"','unique':'applyWITHDRAWALS','xcPrice':'"+xcPrice+"','xsPrice':'"+xcPrice+"'}";
			extra="{'accountId':'"+accountIdStr+"','roleId':'"+roleIdStr+"','unique':'applyWITHDRAWALS'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.SHOPINSPECIAL_FORZEN, applyTypeKey)){
			//{'specialId':'60','unique':'SHOPINSPECIAL_FORZEN'}
			String specialId = argsMap.get("specialId");
			if(null==specialId || specialId.equals("")){
				throw new MyStrException("请传入specialId值");
			}
			extra="{'specialId':'"+specialId+"','unique':'SHOPINSPECIAL_FORZEN'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.SHOPSPECIAL_FORZEN, applyTypeKey)){
			String specialId = argsMap.get("specialId");
			if(null==specialId || specialId.equals("")){
				throw new MyStrException("请传入specialId值");
			}
			extra="{'specialId':'"+specialId+"','unique':'SHOPSPECIAL_FORZEN'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.SPECIAL_BZJ_SPENDING, applyTypeKey)){
			//extraDataSB.append("{'specialId':'").append(s.getId()).append("','shopId':'").append(shopId).append("','unique':'special_bzj_spending'}");
			//{'specialId':'4','unique':'SPECIAL_BZJ_SPENDING'}
			String specialId = argsMap.get("specialId");
			if(null==specialId || specialId.equals("")){
				throw new MyStrException("请传入specialId值");
			}
			extra="{'specialId':'"+specialId+"','unique':'SPECIAL_BZJ_SPENDING'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.UPDATEVIPSHOP_FORZEN, applyTypeKey)){
			extra="{'unique':'UPDATEVIPSHOP_FORZEN'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.UPDATEVIPUSER_FORZEN, applyTypeKey)){
			extra="{'unique':'UPDATEVIPUSER_FORZEN'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.SHOPSTATUSWAIT2OK_FORZEN, applyTypeKey)){
			extra="{'unique':'SHOPSTATUSWAIT2OK_FORZEN'}";
		}else if(EnumUtil.equalsE(AccountConstant.ACCOUNTITEM_EXTRA_KEY.ORDERPAY, applyTypeKey)){
			String orderId = argsMap.get("orderId");
			if(null==orderId || orderId.equals("")){
				throw new MyStrException("请传入orderId值");
			}
			extra="{'orderId':'"+orderId+"'}";
		}
		return extra;
	}
}
