package myFrameU.spring.aop.init;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
							}else if(eName.equals("login")){
								eText=e.getTextTrim();
								if(null!=eText){
									System.out.println(e.attributeValue("status"));
									ic.setLogin_status(e.attributeValue("status"));
									HashMap<String,LoginEntity> loginRoleMap = new HashMap<String,LoginEntity>();
									Iterator<Element> iterator1 = e.elementIterator();
									while (iterator1.hasNext()) {
										Element e1 = iterator1.next();
										if(e1.getName().equals("login-role")){
											LoginEntity le = new LoginEntity();
											le.setRoleClass(e1.attributeValue("roleClass"));
											le.setPrefix(e1.attributeValue("prefix"));
											le.setSaveRoleSessionKey(e1.attributeValue("saveRoleSessionKey"));
											le.setIfNotLoginPath(e1.attributeValue("ifNotLoginPath"));
											loginRoleMap.put(le.getPrefix(), le);
										}
									}
									ic.setLoginEntityMap(loginRoleMap);
								}
							}else if(eName.equals("mavenAops")){
								List<MavenAopEntity> aopBeforeList= new ArrayList<MavenAopEntity>();
								List<MavenAopEntity> aopAfterList= new ArrayList<MavenAopEntity>();
								eText=e.getTextTrim();
								if(null!=eText){
									Iterator<Element> iterator1 = e.elementIterator();
									while (iterator1.hasNext()) {
										Element e1 = iterator1.next();
										if(e1.getName().equals("mavenAop")){
											String classStr=e1.attributeValue("class");
											String interceptPrefix=e1.attributeValue("interceptPrefix");
											Class c = Class.forName(classStr);
											String method=e1.attributeValue("method");
											if(null!=method){
												MavenAopEntity mae=new MavenAopEntity();
												mae.setAopClass(c);
												mae.setInterceptPrefix(interceptPrefix);
												mae.setMethod(method);
												if(method.equals("aopBefore")){
													aopBeforeList.add(mae);
												}else if(method.equals("aopAfter")){
													aopAfterList.add(mae);
												}else if(method.equals("aopBefore,aopAfter")){
													aopBeforeList.add(mae);
													aopAfterList.add(mae);
												}
											}
										}
									}
									ic.setAopAfterList(aopAfterList);
									ic.setAopBeforeList(aopBeforeList);
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
		System.out.println("初始化实体类========================："+ic.getInitMavenClass().getName());
		
		List<MavenAopEntity> beforeList = ic.getAopBeforeList();
		List<MavenAopEntity> afterList = ic.getAopAfterList();
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
