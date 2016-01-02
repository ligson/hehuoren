package myFrameU.integration.init;import java.io.File;import java.util.HashMap;import java.util.Iterator;import java.util.Map;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;

public class InitMavenImpl implements InitMavenI {
	private String xml = null;
	public static InitConfig ic = new InitConfig();
	public String initMavenXml(String xml) {
		this.xml = xml;
		return xml;
	}

	/**
	 * 初始化这个模块
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
							}else if(eName.equals("integration-role-caseField")){
								eText=e.getTextTrim();								Map<String,RoleCaseFieldEntity> rcfeMap=new HashMap<String,RoleCaseFieldEntity>();									Iterator<Element> iterator1 = e.elementIterator();
									String e1Name=null;
									String e1Text=null;
									while(iterator1.hasNext()){  
							            Element e1 = iterator1.next();  
							            e1Name=e1.getName();
							            e1Text=e1.getTextTrim();
							            if(null!=e1Name && null!=e1Text){
							            	if(e1Name.equals("irc")){							            		RoleCaseFieldEntity rcfe = new RoleCaseFieldEntity();							            		rcfe.setRoleName(e1.attributeValue("roleName"));
							            		rcfe.setGradeField(e1.attributeValue("gradeField"));							            		rcfe.setTotalField(e1.attributeValue("totalField"));							            		rcfeMap.put(rcfe.getRoleName(), rcfe);							            	}
							            }
							        }  									ic.setRcfeMap(rcfeMap);							}else if(eName.equals("itemRules")){								eText=e.getTextTrim();																Map<String, HashMap<String, ItemRule>> itemRulesMap= new HashMap<String,HashMap<String,ItemRule>>();									Iterator<Element> iterator1 = e.elementIterator();									String e1Name=null;									String e1Text=null;									while(iterator1.hasNext()){  							            Element e1 = iterator1.next();  							            e1Name=e1.getName();							            e1Text=e1.getTextTrim();							            if(null!=e1Name && null!=e1Text){							            	if(e1Name.equals("itemRule")){							            									            		String roleName=e1.attributeValue("roleName");							            		HashMap<String, ItemRule> ruleMap=new HashMap<String,ItemRule>();							            									            		Iterator<Element> iterator2 = e1.elementIterator();												String e2Name=null;												String e2Text=null;												while(iterator2.hasNext()){  										            Element e2 = iterator2.next();  										            e2Name=e2.getName();										            e2Text=e2.getTextTrim();										            if(null!=e2Name && null!=e2Text){										            	if(e2Name.equals("rule")){										            		String actionType=e2.attributeValue("actionType");										            		Float fraction=new Float(e2.attributeValue("fraction"));										            		String itemEvent=e2.attributeValue("itemEvent");										            		ItemRule irule=new ItemRule();										            		irule.setActionType(actionType);										            		irule.setFraction(fraction);										            		irule.setItemEvent(itemEvent);										            		ruleMap.put(actionType, irule);										            	}										            }										        }  							            														itemRulesMap.put(roleName, ruleMap);							            									            	}							            }							        }  																		ic.setItemRulesMap(itemRulesMap);							}
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
