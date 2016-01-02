package myFrameU.admin.init;import java.io.File;import java.util.HashMap;import java.util.Iterator;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;


public class InitMavenImpl implements InitMavenI {
	private String xml = null;
	public static InitConfig ic = new InitConfig();
	public String initMavenXml(String xml) {
		this.xml = xml;
		return xml;
	}

	/**
	 * 初始化ehcacheMaven这个模块
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
							}else if(eName.equals("adminLoginErrorPage")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setLoginErrorPage(eText);								}							}else if(eName.equals("adminAfter")){								eText=e.getTextTrim();								ic.setAfterClass(e.attributeValue("className"));								HashMap<String,String> afterMethodMap=new HashMap<String,String>();								Iterator<Element> iterator1 = e.elementIterator();								while (iterator1.hasNext()) {									Element e1 = iterator1.next();									if(e1.getName().equals("method")){										afterMethodMap.put(e1.attributeValue("methodKey"), e1.getText().trim());									}								}								ic.setAfterMethodMap(afterMethodMap);															}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	public void testPrintInitConfig() {
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
