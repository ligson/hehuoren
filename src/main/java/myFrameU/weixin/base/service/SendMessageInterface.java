package myFrameU.weixin.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;
import myFrameU.weixin.base.entity.TemplateData;
import myFrameU.weixin.base.entity.TemplateMsg;

public interface SendMessageInterface {
	public void reply(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception;
	
	
	
	//public void sendTemplate_setIndustry(String industry_id1,String industry_id2,UICacheManager uICacheManager) throws Exception;
	//public String sendTemplate_getTemplateId(String template_id_short,UICacheManager uICacheManager) throws Exception;
	public String sendTemplate(TemplateMsg tm,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception;
	
	
	//====================================================================
	public void reply_event_subscribe(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz)  throws Exception;
	public void reply_event_click(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception;
	public void reply_text(ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) throws Exception;
	
	
	
	
	
	
	
	
	
	
}
