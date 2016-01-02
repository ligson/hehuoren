package myFrameU.weixin.init;import java.io.File;import java.util.HashMap;import java.util.Iterator;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;

public class InitMavenImpl implements InitMavenI {
	private String xml = null;
	public static InitConfig ic = new InitConfig();
	public String initMavenXml(String xml) {
		this.xml = xml;
		return xml;
	}

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
							}else if(eName.equals("weixinConfigs")){								Iterator<Element> iterator1 = e.elementIterator();								while (iterator1.hasNext()) {									Element e1 = iterator1.next();									if(e1.getName().equals("token")){										ic.setToken(e1.getTextTrim());									}else if(e1.getName().equals("appId")){										ic.setAppId(e1.getTextTrim());									}else if(e1.getName().equals("secret")){										ic.setSecret(e1.getTextTrim());									}else if(e1.getName().equals("accessTokenUrl")){										ic.setAccessTokenUrl(e1.getTextTrim());									}else if(e1.getName().equals("subscribeTxt")){										ic.setSubscribeTxt(e1.getTextTrim());									}else if(e1.getName().equals("menuTxtUrl")){										ic.setMenuTxtUrl(e1.getTextTrim());									}else if(e1.getName().equals("getOpenIdUrl")){										ic.setGetOpenIdUrl(e1.getTextTrim());									}else if(e1.getName().equals("pay-mch_id")){										ic.setPayMchId(e1.getTextTrim());									}else if(e1.getName().equals("pay-spbill_create_ip")){										ic.setPaySpbillCreateIp(e1.getTextTrim());									}else if(e1.getName().equals("pay-notify_url")){										ic.setPayNotifyUrl(e1.getTextTrim());									}else if(e1.getName().equals("appKey")){										ic.setAppKey(e1.getTextTrim());									}else if(e1.getName().equals("adminWxid_2wmUrl")){										ic.setAdminWxid_2wmUrl(e1.getTextTrim());									}								}							}else if(eName.equals("templateMsgId_myAndWxs")){								Iterator<Element> iterator1 = e.elementIterator();								HashMap<String,String> templateMsgId_myAndWx=new HashMap<String,String>();								while (iterator1.hasNext()) {									Element e1 = iterator1.next();									String e1Name = e1.getName();									String txt = e1.getTextTrim();									String myTemplateId=e1.attributeValue("myTemplateId");									String wxTemplateId=e1.attributeValue("wxTemplateId");									templateMsgId_myAndWx.put(myTemplateId, wxTemplateId);								}								ic.setTemplateMsgId_myAndWx(templateMsgId_myAndWx);							}
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
