package myFrameU.util.commonUtil.xml;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLFormat {
	public static Object xml2Object(String xmlStr,Class c){
		Object o = null;
		try {
			o = c.newInstance();
			//SAXReader reader = new SAXReader();
			// 读取文件 转换成Document
			//Document document = reader.read(xmlStr);
			Document document  = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
			
			Element root = document.getRootElement();
			String rootName = root.getName();
			
			if(rootName.equals("returnsms")){
				Iterator<Element> iterator = root.elementIterator();
				String eName = null;
				String eText=null;
				while (iterator.hasNext()) {
					Element e = iterator.next();
					if (null != e) {
						eName = e.getName();
						eText=e.getTextTrim();
						if (null != eName) {
							String firstZM=eName.charAt(0)+"";
							firstZM=firstZM.toUpperCase();
							String noFirst=eName.substring(1,eName.length());
							String methodName="set"+firstZM+noFirst;
							Method m = c.getMethod(methodName, String.class);
							if(null!=m){
								m.invoke(o, eText);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
}
