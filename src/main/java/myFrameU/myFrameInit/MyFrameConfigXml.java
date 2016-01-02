package myFrameU.myFrameInit;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import myFrameU.util.httpUtil.path.PathUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MyFrameConfigXml {
	public static Map<String,String> getConfigMap(){
		Map<String,String> map = new HashMap<String,String>();
		try{
			SAXReader reader = new SAXReader();
			// 读取文件 转换成Document
			String path=PathUtil.getWebinf()+"myFrameUXml/myFrameInit.xml";
			Document document=reader.read(new File(path));
			
			Element root = document.getRootElement();
			String rootName = root.getName();
			if (rootName.equals("myFrameInits")) {
				Iterator<Element> iterator = root.elementIterator();
				String eName = null;
				String eText=null;
				while (iterator.hasNext()) {
					Element e = iterator.next();
					eName = e.getName();
					if(eName.equals("spring-listener")){
						
						
						Iterator<Element> iterator1 = e.elementIterator();
						String eName1 = null;
						String eText1=null;
						while (iterator1.hasNext()) {
							Element e1 = iterator1.next();
							eName1 = e1.getName();
							eText1=e1.getTextTrim();
							if(eName1.equals("initMaven-xmlPath")){
								map.put("initMaven-xmlPath", eText1);
							}else if(eName1.equals("listener-myClass")){
								map.put("listener-myClass", eText1);
								map.put("listener-method", e1.attributeValue("method"));
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
