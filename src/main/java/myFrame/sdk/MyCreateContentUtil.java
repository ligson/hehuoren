package myFrame.sdk;

import javax.servlet.http.HttpServletRequest;

import myFrameU.apply.entity.Apply;
import myFrameU.sms.util.CreateContentUtil;
import myFrameU.util.commonUtil.enumU.EnumUtil;

public class MyCreateContentUtil extends CreateContentUtil {
	public enum SDKMTTYPE{
		TOUser_AUCTION_END_BID,TOShop_AUCTION_END_BID_NOPAY,
		TOUser_RS_SPECIAL_BEGIN,TOUser_RS_SPECIAL_WILLEND,TOUser_RS_AUCTION_BEGIN,TOUser_RS_AUCTION_WILLEND,
		RENGONGSEND
	};
	
	//人工手动发送
		public String RENGONGSEND(HttpServletRequest req){
			String content = req.getParameter("content");
			return content;
		}
		
		
	//autionPeriod结束后，如果中标则短信通知用户
	public String TOUser_AUCTION_END_BID(HttpServletRequest req){
		String auctionPeriod_title = req.getParameter("auctionPeriod_title");
		String price = req.getParameter("bidPrice");
		int len = auctionPeriod_title.length();
		if(len>=40){
			auctionPeriod_title=auctionPeriod_title.substring(0,39);
		}
		String msg = "您已中标拍品["+auctionPeriod_title+"]"+"中标价为"+price+"元,请您尽快付款";
		return msg;
	}
	
	//用户中标之后，过了15天，拒绝付款，那么这时候给商家发一个短信，告诉他会在另外15天时候会将冻结资金给你
	public String TOShop_AUCTION_END_BID_NOPAY(HttpServletRequest req){
		String auctionPeriod_title = req.getParameter("auctionPeriod_title");
		String userName=req.getParameter("bidUserName");
		String g7=req.getParameter("g7");
		int len = auctionPeriod_title.length();
		if(len>=30){
			auctionPeriod_title=auctionPeriod_title.substring(0,29);
		}
		String msg ="["+auctionPeriod_title+"]被用户["+userName+"]中标，已过付款期未付款，系统在"+g7+"天将他的保证金付给您";
		return msg;
	}
	

	//================================================remindSettings开始==============================================
	public String TOUser_RS_SPECIAL_BEGIN(HttpServletRequest req){
		String specialName = req.getParameter("specialName");
		StringBuffer sb = new StringBuffer();
		if(null!=specialName && !specialName.equals("")){
			int len = specialName.length();
			if(len>50){
				specialName=specialName.substring(0,49);
			}
			sb.append("专场[").append(specialName).append("]开始了，您可前往竞拍!");
		}
		return sb.toString();
	}
	public String TOUser_RS_SPECIAL_WILLEND(HttpServletRequest req){
		String specialName = req.getParameter("specialName");
		StringBuffer sb = new StringBuffer();
		if(null!=specialName && !specialName.equals("")){
			int len = specialName.length();
			if(len>50){
				specialName=specialName.substring(0,49);
			}
			sb.append("专场[").append(specialName).append("]马上结束了，您抓紧时间前去往竞拍!");
		}
		return sb.toString();
	}
	public String TOUser_RS_AUCTION_BEGIN(HttpServletRequest req){
		String specialName = req.getParameter("auctionName");
		StringBuffer sb = new StringBuffer();
		if(null!=specialName && !specialName.equals("")){
			int len = specialName.length();
			if(len>50){
				specialName=specialName.substring(0,49);
			}
			sb.append("拍品[").append(specialName).append("]开始拍卖了，您可前往竞拍!");
		}
		return sb.toString();
	}
	public String TOUser_RS_AUCTION_WILLEND(HttpServletRequest req){
		String specialName = req.getParameter("auctionName");
		StringBuffer sb = new StringBuffer();
		if(null!=specialName && !specialName.equals("")){
			int len = specialName.length();
			if(len>50){
				specialName=specialName.substring(0,49);
			}
			sb.append("拍品[").append(specialName).append("]拍卖马上结束了，您可抓紧前往竞拍!");
		}
		return sb.toString();
	}
	//================================================remindSettings结束==============================================
	
	
/*	//审批结束之后的通知--提现
	public String APPLYFINISH_AccountTXApply(HttpServletRequest req){
		String result = req.getParameter("result");
		if(null!=result && !result.equals("")){
			if(EnumUtil.equalsE(Apply.RESULT.APPLYOK, result)){
				
			}else if(EnumUtil.equalsE(Apply.RESULT.APPLYERROR, result)){
				
			}
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
