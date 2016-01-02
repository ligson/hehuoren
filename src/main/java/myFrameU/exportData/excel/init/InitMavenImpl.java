package myFrameU.exportData.excel.init;import java.io.File;import java.util.HashMap;import java.util.Iterator;import java.util.Map;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;


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
							}else if(eName.equals("export-class-instructions")){
								eText=e.getTextTrim();								Map<String,ExcelDataEntity> edeMap = new HashMap<String,ExcelDataEntity>();																Iterator<Element> iterator1 = e.elementIterator();								String e1Name=null;								String e1Text=null;																while(iterator1.hasNext()){  						            Element e1 = iterator1.next();  						            e1Name=e1.getName();						            e1Text=e1.getTextTrim();						            if(null!=e1Name && null!=e1Text){						            	if(e1Name.equals("exportClassIns")){						            		ExcelDataEntity ede = new  ExcelDataEntity();						            		ede.setClassName(e1.attributeValue("className"));						            		ede.setFields(e1.attributeValue("fields"));						            		ede.setFieldNames(e1.attributeValue("fieldNames"));						            		ede.setSimpleClassName(e1.attributeValue("simpleClassName"));						            		ede.setClassNameChinese(e1.attributeValue("classNameChinese"));						            		edeMap.put(e1.attributeValue("simpleClassName"), ede);						            	}						            }						        }  								ic.setEdeMap(edeMap);
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
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
