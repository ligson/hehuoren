package myFrameU.weixin.pay.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import myFrameU.weixin.base.entity.ReceiveXmlEntity;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

//解析接收到的微信的xml，返回消息对象
public class ReceiveXmlProcess {
	public static <T> T getMsgEntity(String strXml,Class c){
		T msg = null;
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
				msg=(T) new ReceiveXmlEntity();
				//Class<?> c = Class.forName("myFrameU.weixin.base.entity.ReceiveXmlEntity");
				msg = (T)c.newInstance();
				String name = null;
				String name_first=null;
				String name_nofirst=null;
				String methodName = null;
				while(iter.hasNext()){
					Element ele=(Element)iter.next();
					name = ele.getName();
					name_first=name.charAt(0)+"";
					name_first=name_first.toUpperCase();
					name_nofirst=name.substring(1,name.length());
					methodName="set"+name_first+name_nofirst;
					Field field = c.getDeclaredField(name);
					Method method=c.getDeclaredMethod(methodName, field.getType());
					method.invoke(msg, ele.getText());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msg;
	}
}
