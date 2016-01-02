package myFrameU.apply.util;

import java.util.HashMap;
import java.util.List;

import myFrameU.apply.entity.Apply;
import myFrameU.biz.AbstractBizI;
import myFrameU.exception.exception.MyStrException;
import myFrameU.shop.entity.Shop;
import myFrameU.sms.util.SendSms;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.date.DateUtils;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;
import myFrameU.weixin.base.util.SendTemplateMsgArgsUtils;

public class ApplyUtil {
	
	/**
	 * 查找某一个key，某一个extraData，最后一个申请
	 */
	public static Apply getLastApply(String sponsorClassName,int sponsorId,String extraData,String applyTypeKey,AbstractBizI aBiz) throws Exception{
		Apply apply = null;
		Integer lastId = null;
		if(null!=extraData && extraData.contains(".*")){
			//说明是{'mainProductTypeIds':'.*'}
			if(extraData.contains("'")){
				extraData=extraData.replaceAll("'", "''");
			}
			String sql="select id from apply_apply as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=? and a.extraData REGEXP '"+extraData+"' order by id desc limit 1";
			lastId = (Integer)aBiz.j_queryObject(sql, new Object[]{sponsorClassName,sponsorId,applyTypeKey});
		}else{
			String sql="select id from apply_apply as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=? and a.extraData=? order by id desc limit 1";
			lastId = (Integer)aBiz.j_queryObject(sql, new Object[]{sponsorClassName,sponsorId,applyTypeKey,extraData});
		}
		if(null!=lastId && lastId.intValue()>0){
			 apply = (Apply)aBiz.findObjectById("from Apply as a where a.id=?", new Object[]{lastId});
		}
		return apply;
	}
	
	
	/**
	 * 查找某一个key，某一个extraData，最后一个申请
	 */
	public static String getLastApplyIds(String sponsorClassName,int sponsorId,String extraData,String applyTypeKey,AbstractBizI aBiz) throws Exception{
		StringBuffer sb =new StringBuffer();
		sb.append("select id from (select * from apply_apply order by id desc) as a where a.sponsorClassName=? and a.sponsorId=? and a.applyTypeKey=?")
		.append(" and a.extraData REGEXP '").append(extraData).append("' group by extraData order by a.id ")
		;
		
		//String s = "select id from (select id,extraData from apply_apply order by id desc) as s group by extraData ;";
		StringBuffer idsb = new StringBuffer();
		
		List<Integer> ids = (List<Integer>)aBiz.j_queryObjectList(sb.toString(), new Object[]{sponsorClassName,sponsorId,applyTypeKey});
		if(null!=ids && !ids.equals("")){
			int size = ids.size();
			Integer id =null;
			for(int i=0;i<size;i++){
				if(i==(size-1)){
					idsb.append(ids.get(i));
				}else{
					idsb.append(ids.get(i)).append(",");
				}
			}
		}
		return idsb.toString();
	}
	
	
	
	/**
	 * 判断能否插入apply
	 * 		最后一条apply
	 * 			1、如果是wait、ing处理的，不可以
	 * 			2、如果是finish，且是applyOk的不可以
	 * 			3、如果是finish，且是applyOk的可以
	 * 			4、如果没有apply，可以
	 */
	public static boolean canInsert(String sponsorClassName,int sponsorId,String extraData,String applyTypeKey,AbstractBizI aBiz) throws Exception{
		Apply apply = getLastApply(sponsorClassName, sponsorId, extraData, applyTypeKey, aBiz);
		if(null==apply){
			return true;
		}else{
			String speed = apply.getSpeed();
			String result = apply.getResult();
			if(EnumUtil.equalsE(Apply.SPEED.FINISH, speed)){
				if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
					throw new MyStrException("抱歉，您的申请已经审批通过，不能重复申请");
				}else if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
					return true;
				}
			}else if(EnumUtil.equalsE(Apply.SPEED.WAIT, speed)||EnumUtil.equalsE(Apply.SPEED.ING, speed)){
				throw new MyStrException("抱歉，您的申请已经提交，等待审批，不能重复申请");
			}
		}
		return false;
	}
	
	

	private static String smsSendApplyFinished_getCon(AbstractBizI aBiz,Apply apply,String phone) throws Exception{
		String content = null;
		String speed = apply.getSpeed();
		if(null!=speed && !speed.equals("")){
			if(EnumUtil.equalsE(Apply.SPEED.FINISH, speed)){
				String result = apply.getResult();
				String applyTypeKey = apply.getApplyTypeKey();
				if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
					String remarks = apply.getRemarks();
					if(remarks.length()>40){
						remarks=remarks.substring(0,36)+"...";
					}
					if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
						content="您的提现申请审核不通过，原因是："+remarks;
					}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
						content="您的申请合伙人申请不通过，原因是："+remarks;
					}
				}else if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
					if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.AccountTXApply, applyTypeKey)){
						content="您的提现申请审核已通过，稍后请注意查收";
					}else if(EnumUtil.equalsE(ApplyConstant.APPLYTYPEKEY.UserLevelHHR, applyTypeKey)){
						content="您的申请合伙人已通过";
					}
				}
				
			}
		}
		return content;
		
	}
	
	public static void smsSendApplyFinished_dx(AbstractBizI aBiz,Apply apply) throws Exception{
		String className = apply.getSponsorClassName();
		int whoId = apply.getSponsorId();
		String phone=null;
		if(null!=className && !className.equals("") && whoId!=0){
			if(className.equals(Shop.class.getName())){
				phone=(String)aBiz.j_queryObject("select phone from "+EntityTableUtil.tableNameC(Shop.class)+" as s where s.id=?", new Object[]{whoId});
			}else if(className.equals(User.class.getName())){
				phone=(String)aBiz.j_queryObject("select telPhone from "+EntityTableUtil.tableNameC(User.class)+" as s where s.id=?", new Object[]{whoId});
			}
		}
		
		if(null!=phone && !phone.equals("")){
			String verPhone = PhoneUtil.vailterTelPhone(phone);
			if(null==verPhone){
				String content = smsSendApplyFinished_getCon(aBiz, apply, phone);
				System.out.println("发送审核完毕的通知短信................................................"+content);
				SendSms.sdk_mt(phone, content, aBiz, apply.getApplyTypeKey(), null);
			}
		}
		
	}
	
	
	
	public static void smsSendApplyFinished_wx(AbstractBizI aBiz,Apply apply) throws Exception{
		String className = apply.getSponsorClassName();
		int whoId = apply.getSponsorId();
		String phone=null;
		String wxId = null;
		if(null!=className && !className.equals("") && whoId!=0){
			if(className.equals(Shop.class.getName())){
				wxId=(String)aBiz.j_queryObject("select wxId from shop_shopUser where shopId=?", new Object[]{whoId});
			}else if(className.equals(User.class.getName())){
				wxId=(String)aBiz.j_queryObject("select wxId from user_user where id=?", new Object[]{whoId});
			}
		}
		if(null!=wxId && !wxId.equals("")){
			HashMap<String,String> valuesMap = new HashMap<String,String>();
			valuesMap.put("first", apply.getApplyTitle());
			valuesMap.put("keyword1", apply.getSponsorName());
			valuesMap.put("keyword2", DateUtils.format_all(apply.getRelDate()));
			String rs=apply.getResult();
			String result = null;
			if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, rs)){
				result="审核不通过";
			}else if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, rs)){
				result="审核通过";
			}
			valuesMap.put("keyword3", result);
			valuesMap.put("remark", apply.getRemarks());
			
			SendTemplateMsgArgsUtils.getTemplateMsg(wxId, SendTemplateMsgArgsUtils.MyTemplate.APPLY.toString(), valuesMap);
		}
		
	}
	
	
	
	
}
