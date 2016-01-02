package myFrameU.sms.init;import java.io.File;import java.util.Iterator;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;

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
							}else if(eName.equals("status")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setStatus(eText);								}							}else if(eName.equals("serviceURL")){
								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setServiceURL(eText);								}
							}else if(eName.equals("userId")){
								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setUserId(eText);								}
							}else if(eName.equals("account")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setAccount(eText);								}							}else if(eName.equals("password")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setPassword(eText);								}							}else if(eName.equals("suffix")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setSuffix(eText);								}							}else if(eName.equals("saveRecord")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setSaveRecord(new Boolean(eText));								}							}else if(eName.equals("myCreateContentUtil")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setMyCreateContentUtil(eText);								}							}else if(eName.equals("requestKeyName-sdkMtType")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setSdkMtType(eText);								}							}else if(eName.equals("requestKeyName-telPhones")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setTelPhones(eText);								}							}else if(eName.equals("requestKeyName-smsOthers")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setSmsOthers(eText);								}							}else if(eName.equals("requestKeyName-yanzhengma")){								eText=e.getTextTrim();								if(null!=eText && !eText.equals("")){									ic.setSmsYanzhengma(eText);								}							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	public void testPrintInitConfig() {
		System.out.println("sms初始化实体类："+ic.getInitMavenClass().getName());
		System.out.println("sms-serviceURL："+ic.getServiceURL());		System.out.println("sms-userId："+ic.getUserId());		System.out.println("sms-account："+ic.getAccount());		System.out.println("sms-password："+ic.getPassword());		System.out.println("sms-suffix："+ic.getSuffix());		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
