package myFrameU.weixin.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import myFrameU.weixin.base.entity.ReceiveXmlEntity;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

//解析接收到的微信的xml，返回消息对象
public class ReceiveXmlProcess {
	public ReceiveXmlEntity getMsgEntity(String strXml){
		ReceiveXmlEntity msg = null;
		try{
			if(null== strXml || strXml.length()<=0){
				return null;
			}else{
				//将字符串转换为xml对象
				Document document = DocumentHelper.parseText(strXml);
				//获得根节点
				Element root = document.getRootElement();
				//便利所有的子节点
				Iterator<?> iter=root.elementIterator();
				msg=new ReceiveXmlEntity();
				Class<?> c = Class.forName("myFrameU.weixin.base.entity.ReceiveXmlEntity");
				msg = (ReceiveXmlEntity)c.newInstance();
				
				while(iter.hasNext()){
					Element ele=(Element)iter.next();
					Field field = c.getDeclaredField(ele.getName());
					Method method=c.getDeclaredMethod("set"+ele.getName(), field.getType());
					method.invoke(msg, ele.getText());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg;
	}
}
