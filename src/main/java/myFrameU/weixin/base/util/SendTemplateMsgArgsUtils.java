package myFrameU.weixin.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import myFrameU.weixin.base.entity.TemplateData;
import myFrameU.weixin.base.entity.TemplateMsg;
import myFrameU.weixin.init.InitConfig;

public class SendTemplateMsgArgsUtils {

	public static String getTemplateMsgArgs(TemplateMsg tm){
		//String toUserOpenId,String template_id,List<TemplateData> dataList
		String toUserOpenId=tm.getOpenId();
		String template_id=tm.getTemplate_id();
		List<TemplateData> dataList=tm.getDataList();
		String url=tm.getUrl();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(toUserOpenId).append("\",");
		sb.append("\"template_id\":\"").append(template_id).append("\",");
		if(null!=url && !url.equals("")){
			sb.append("\"url\":\"").append(url).append("\",");
		}
		
		sb.append("\"topcolor\":\"").append("#FF0000").append("\",");
		sb.append("\"data\":{");
		
		String dataStr = getDataStr(dataList);
		sb.append(dataStr);
		
		
		sb.append("}");
		sb.append("}");
		/**
		 *  {
           "touser":"OPENID",
           "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
           "url":"http://weixin.qq.com/download",
           "topcolor":"#FF0000",
           "data":{
                   "first": {
                       "value":"恭喜你购买成功！",
                       "color":"#173177"
                   },
                   "keynote1":{
                       "value":"巧克力",
                       "color":"#173177"
                   },
                   "keynote2": {
                       "value":"39.8元",
                       "color":"#173177"
                   },
                   "keynote3": {
                       "value":"2014年9月16日",
                       "color":"#173177"
                   },
                   "remark":{
                       "value":"欢迎再次购买！",
                       "color":"#173177"
                   }
           }
       }
		 */
		return sb.toString();
	}
	
	
	
	private static String getDataStr(List<TemplateData> dataList){
		StringBuffer sb = new StringBuffer();
		if(null!=dataList){
			int size = dataList.size();
			TemplateData d = null;
			for(int i=0;i<size;i++){
				d=dataList.get(i);
				if(i==(size-1)){
					sb.append("\"").append(d.getKey()).append("\":{").append("\"value\":\"").append(d.getValue()).append("\",");
					sb.append("\"color\":\"").append(d.getColor()).append("\"").append("}");
				}else{
					sb.append("\"").append(d.getKey()).append("\":{").append("\"value\":\"").append(d.getValue()).append("\",");
					sb.append("\"color\":\"").append(d.getColor()).append("\"").append("},");
				}
			}
		}
		return sb.toString();
	}
	
	
	
	
	//=======================================================================
	public enum MyTemplate{
		RECHARGE_SUCCESS,Order_Add_SUC,Order_Payed_SUC,Order_Payed_SUC_QH,APPLY
		,TOTuijianren_icome_order,HHR_SYQOVER_OK,ProductCOUNT,Order_Payed_SUC_TOADMIN
		,RECHARGE_SUCCESS_TOADMIN
	}
	/**
	 * 以下方法将常用的模板信息进行封装
	 * 返回一个TemplateMsg
	 * 
	 * 		财务：
	 * 				充值成功、提现成功
	 * 		订单：
	 * 				下单成功、订单完成支付、订单发货成功
	 */
	public static TemplateMsg getTemplateMsg(String openId,String myTemplateId,HashMap<String,String> valuesMap){
		TemplateMsg tm = new TemplateMsg();
		tm.setOpenId(openId);
		List<TemplateData> dataList = new ArrayList<TemplateData>();
		tm.setTemplate_id(getWXTemplateId(myTemplateId));
		
		if(null!=myTemplateId && !myTemplateId.equals("")){
			if(myTemplateId.equals("RECHARGE_SUCCESS")){
				/**
				 * 会员充值通知
				 * 消费品 - 消费品
				 * 
				 * 
				 *  {{first.DATA}}
					{{accountType.DATA}}：{{account.DATA}}
					充值金额：{{amount.DATA}}
					充值状态：{{result.DATA}}
					{{remark.DATA}}
					
					
					这个模板可以用来给用户发，也可以给管理员发
					
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("accountType",valuesMap));
				dataList.add(getTemplateData("account",valuesMap));
				dataList.add(getTemplateData("amount",valuesMap));
				dataList.add(getTemplateData("result",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("Order_Add_SUC")){
				/**
				 * 下单通知
				 * OPENTM206166377
				 * 消费品 - 消费品
				 * 
				 * 
				 * 
				 *{{first.DATA}}
					订单号：{{keyword1.DATA}}
					时间：{{keyword2.DATA}}
					{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("Order_Payed_SUC")){
				/**
				 * 订单付款成功通知
				 * OPENTM200444326
				 * 消费品 - 消费品
				 * 
				 * 
				 *  {{first.DATA}}
					订单号：{{keyword1.DATA}}
					支付时间：{{keyword2.DATA}}
					支付金额：{{keyword3.DATA}}
					支付方式：{{keyword4.DATA}}
					{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("keyword4",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
			}else if(myTemplateId.equals("Order_Payed_SUC_QH")){
				/**
				 * 订单取货提醒
				 * 消费品 - 消费品
				 * OPENTM200936319
				 * 
				 * 
				 * {{first.DATA}}
					订单号：{{keyword1.DATA}}
					订单金额：{{keyword2.DATA}}
					消费门店：{{keyword3.DATA}}
					{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("APPLY")){
				/**
				 * OPENTM203234747
				 * 合伙人申请结果通知
				 * 消费品 - 消费品
				 * 
				 {{first.DATA}}
				申请人：{{keyword1.DATA}}
				申请时间：{{keyword2.DATA}}
				审核状态：{{keyword3.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("TOTuijianren_icome_order")){
				/**
				 * 发送给推荐人，当有他的粉丝下单付款成功了，则发送恭喜信息
				 * 
				 * 
				 * 
				{{first.DATA}}
				订单号：{{keyword1.DATA}}
				订单金额：{{keyword2.DATA}}
				分成金额：{{keyword3.DATA}}
				时间：{{keyword4.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("keyword4",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("HHR_SYQOVER_OK")){
				/**
				 * 过了试用期之后，发现该合伙人可以成为正式的合伙人了发信息
				 * 
				 * 
				 * 
				{{first.DATA}}
				申请人：{{keyword1.DATA}}
				申请时间：{{keyword2.DATA}}
				审核状态：{{keyword3.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("ProductCOUNT")){
				/**
				 * 订单支付之后，判断是否要发送产品库存预警
				 * OPENTM206773265
				 * 库存预警通知
				 * 消费品 - 消费品
				 * 
				 * 
				{{first.DATA}}
				仓库：{{keyword1.DATA}}
				商品：{{keyword2.DATA}}
				库存：{{keyword3.DATA}}
				时间：{{keyword4.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("keyword4",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("Order_Payed_SUC_TOADMIN")){
				/**
				 * 
				{{first.DATA}}
				订单号：{{keyword1.DATA}}
				时间：{{keyword2.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}else if(myTemplateId.equals("RECHARGE_SUCCESS_TOADMIN")){
				/**
				 * 
				{{first.DATA}}
				银行：{{keyword1.DATA}}
				金额：{{keyword2.DATA}}
				到帐时间：{{keyword3.DATA}}
				{{remark.DATA}}
				 */
				dataList.add(getTemplateData("first",valuesMap));
				dataList.add(getTemplateData("keyword1",valuesMap));
				dataList.add(getTemplateData("keyword2",valuesMap));
				dataList.add(getTemplateData("keyword3",valuesMap));
				dataList.add(getTemplateData("remark",valuesMap));
				
			}
		}
		tm.setDataList(dataList);
		return tm;
	}
	
	
	private static TemplateData getTemplateData(String name,HashMap<String,String> valuesMap){
		TemplateData data6 = new TemplateData();
		data6.setKey(name);
		data6.setValue(valuesMap.get(name));
		data6.setColor("#173177");
		return data6;
	}
	
	private static String getWXTemplateId(String myTemplateId){
		String wxTemplateId = null;
		InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
		HashMap<String,String> map = ic.getTemplateMsgId_myAndWx();
		if(null!=map){
			wxTemplateId=map.get(myTemplateId);
		}
		return wxTemplateId;
	}
	
	
	
}
