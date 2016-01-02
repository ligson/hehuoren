package myFrameU.weixin.base.util;

import java.util.Date;
import java.util.List;

import myFrameU.weixin.base.entity.NewsTuwen;


public class FormatXmlProcess {
	//封装文字类的返回消息 
	/**
	 * <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[FromUser]]></FromUserName>
<CreateTime>123456789</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[subscribe]]></Event>
</xml>
	 */
	
	
	public String formatXmlAnswer(String to,String from){
		/**
		 * <xml>
<ToUserName><![CDATA[toUser]]></ToUserName>
<FromUserName><![CDATA[fromUser]]></FromUserName>
<CreateTime>12345678</CreateTime>
<MsgType><![CDATA[image]]></MsgType>
<Image>
<MediaId><![CDATA[media_id]]></MediaId>
</Image>
</xml>
		 */
		return null;
	}
	
	
	
	
	public String formatXmlAnswer(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content></xml>");
		return sb.toString();
	}
	
	
	public static String formatXmlAnswer(String to, String from,List<NewsTuwen> list){
		if(null==list){
			return null;
		}
		int size = list.size();
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[news]]></MsgType>");
		sb.append("<ArticleCount>").append(size).append("</ArticleCount>");
		sb.append("<Articles>");
		
		NewsTuwen nt  = null;
		for(int i=0;i<size;i++){
			nt=list.get(i);
			sb.append("<item>");
			sb.append("<Title><![CDATA[").append(nt.getTitle()).append("]]></Title> ");
			sb.append("<Description><![CDATA[").append(nt.getDescription()).append("]]></Description>");
			sb.append("<PicUrl><![CDATA[").append(nt.getPicurl()).append("]]></PicUrl>");
			sb.append("<Url><![CDATA[").append(nt.getUrl()).append("]]></Url>");
			sb.append("</item>");
		}
		sb.append("</Articles>");
		sb.append("</xml>");

		return sb.toString();
	}
	
	
	
	
	
}
