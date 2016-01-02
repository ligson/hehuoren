package myFrameU.ehcache.init;import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import myFrameU.mavenInit.Dom4jXmlUtil;
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
							}else if(eName.equals("initDataClasses")){
								eText=e.getTextTrim();
									List<Class> initDataClasses=new ArrayList<Class>();
									Iterator<Element> iterator1 = e.elementIterator();
									String e1Name=null;
									String e1Text=null;
									
									while(iterator1.hasNext()){  
							            Element e1 = iterator1.next();  
							            e1Name=e1.getName();
							            e1Text=e1.getTextTrim();
							            if(null!=e1Name && null!=e1Text){
							            	if(e1Name.equals("initDataClass")&&!e1Text.equals("")){
							            		initDataClasses.add(Class.forName(e1Text));
							            	}
							            }
							        }  
									ic.setInitDataClasses(initDataClasses);
							}else if(eName.equals("initCleans")){
								eText=e.getTextTrim();
								List<InitCleanEntity> initCleans = new ArrayList<InitCleanEntity>();
								Iterator<Element> iterator2 = e.elementIterator();
								String e2Name=null;
								String e2Text=null;
								while(iterator2.hasNext()){  
						            Element e2 = iterator2.next();  
						            e2Name=e2.getName();
						            e2Text=e2.getTextTrim();
						            if(null!=e2Name && null!=e2Text){
						            	if(e2Name.equals("initClean")){
						            		InitCleanEntity ice = new InitCleanEntity();						            		ice.setClassName(e2.attributeValue("class"));
						            		ice.setMethod(Dom4jXmlUtil.getElementAttribute(e2, "method"));
						            		List<EhcacheEntity> clears = new ArrayList<EhcacheEntity>();
						            		Iterator<Element> iterator3 = e2.elementIterator();  
						                    while(iterator3.hasNext()){  
						                        Element e3 = iterator3.next();  
						                        EhcacheEntity ee = new EhcacheEntity();
						                        ee.setCacheName(Dom4jXmlUtil.getElementAttribute(e3, "cacheName"));
						                        ee.setCacheKey(Dom4jXmlUtil.getElementAttribute(e3, "cacheKey"));						                        String againLoadStr= e3.attributeValue("againLoad");						                        if(null!=againLoadStr && !againLoadStr.equals("")){						                        	 ee.setAgainLoad(new Boolean(againLoadStr));						                        	 String loadClassStr= e3.attributeValue("loadClass");								                     if(null!=loadClassStr && !loadClassStr.equals("")){								                         ee.setLoadClass(loadClassStr);								                     }						                        }						                       
						                        clears.add(ee);
						                    }  
						                    ice.setClears(clears);
						            		
						            		initCleans.add(ice);
						            	}
						            }
						        }  
								ic.setInitCleans(initCleans);
								
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
		
		System.out.println("ehcache初始化实体类："+ic.getInitMavenClass().getName());
		List<Class> initDataClasses=ic.getInitDataClasses();
		int size = initDataClasses.size();
		Class c1 = null;
		for(int i=0;i<size;i++){
			c1=initDataClasses.get(i);
			System.out.println("初始化数据"+i+":="+c1.getName());
		}
		
		List<InitCleanEntity> initCleans=ic.getInitCleans();
		int size0=initCleans.size();
		InitCleanEntity ice = null;
		for(int i=0;i<size0;i++){
			ice=initCleans.get(i);
			List<EhcacheEntity> ceList =ice.getClears();
			int si=ceList.size();
			EhcacheEntity ce=null;
			StringBuffer sb = new StringBuffer(";要清空的缓存位置是：");
			for(int j=0;j<si;j++){
				ce=ceList.get(j);
				sb.append("[name=").append(ce.getCacheName()).append(",key=").append(ce.getCacheKey()).append("]");
			}
			System.out.println("清理缓存请求"+i+":class="+ice.getClassName()+";method="+ice.getMethod()+sb.toString());
		}
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
