package myFrameU.weixin.base.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;

public interface ReceiveMessageInterface {
	public ReceiveXmlEntity receiveMessage(HttpServletRequest request, HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz);
	
}
