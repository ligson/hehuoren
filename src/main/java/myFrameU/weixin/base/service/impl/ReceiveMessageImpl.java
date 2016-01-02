package myFrameU.weixin.base.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;
import myFrameU.weixin.base.service.ReceiveMessageInterface;
import myFrameU.weixin.base.util.ReceiveXmlProcess;

public class ReceiveMessageImpl implements ReceiveMessageInterface {

	@Override
	public ReceiveXmlEntity receiveMessage(HttpServletRequest request,
			HttpServletResponse response,UICacheManager uICacheManager,AbstractBizI aBiz) {
		String xml =null;
		try{
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s= "";
			while((s=br.readLine())!=null){
				sb.append(s);
			}
			
			xml = sb.toString();
			System.out.println(xml+"******************************");
			//解析xml
			ReceiveXmlEntity xmlEntity=new ReceiveXmlProcess().getMsgEntity(xml);
			return xmlEntity;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
