package myFrameU.freemarker.init;

import java.io.File;
import java.util.Iterator;

import myFrameU.mavenInit.InitMavenI;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class InitMavenImpl implements InitMavenI {
	private String xml = null;
	public static InitConfig ic = new InitConfig();
	public String initMavenXml(String xml) {
		this.xml = xml;
		return xml;
	}

	/**
	 * 初始化freemarkerMaven这个模块
	 * 		1、生成InitConfig参数对象。
	 * 		2、
	 */
	public void init() {
		createInitConfig();
		
	}

	
	
	
	public void createInitConfig(){
		try {
			SAXReader reader = new SAXReader();
			// 读取文件 转换成Document
			Document document = reader.read(new File(xml));
			Element root = document.getRootElement();
			String rootName = root.getName();
			if (rootName.equals("initMaven")) {
				Iterator<Element> iterator = root.elementIterator();
				String eName = null;
				String eText=null;
				while (iterator.hasNext()) {
					Element e = iterator.next();
					if (null != e) {
						eName = e.getName();
						if (null != eName) {
							
							if(eName.equals("initMavenClass")){
								eText=e.getTextTrim();
								if(null!=eText && !eText.equals("")){
									ic.setInitMavenClass(Class.forName(eText));
								}
							}else if(eName.equals("ftlPackage")){
								eText=e.getTextTrim();
								if(null!=eText && !eText.equals("")){
									ic.setFtlPackage(eText);
								}
							}else if(eName.equals("ftlToPagePath")){
								eText=e.getTextTrim();
								if(null!=eText && !eText.equals("")){
									ic.setFtlToPagePath(eText);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	public void testPrintInitConfig() {
		System.out.println("freemarker初始化实体类："+ic.getInitMavenClass().getName());
		System.out.println("freemarker ftlPackage："+ic.getFtlPackage());
		System.out.println("freemarker ftlToPagePath："+ic.getFtlToPagePath());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
