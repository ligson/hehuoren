package myFrameU.mavenInit;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * 
 * 该类配置在spring的监听器中，监听项目刚刚启动之时，调用这个init，就能配置好各个maven模块的初始化数据了。
 * 方案：
 * 		所有的init配置文件均遵守以下规则，这个init猜可成立
 * 		1）必须直接放在web-inf下
 * 		2）命名规则必须是initMaven-开头，.xml结尾，如initMaven-ehcache.xml
 * 
 * 获得该项目web-inf下所有init.xml文件，然后分别解析，组成一个List<EveryMavenConfig>
 *
 */
public class AutoInit {
	//private static final String webPath="src/main/webapp/WEB-INF/initMaven/";
	private static String getInitMavenClassFromXml(String path,String fname){
		Document document = Dom4jXmlUtil.getDocumentFromXmlPath(path+fname);
		
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
								return eText;
							}
						}
					}
				}
			}
		}
		return rootName;
	}
	public static void autoInit(String path){
		try{
			File webInfoFile=new File(path);
			
			if(null!=webInfoFile){
				if(webInfoFile.exists()){
					File[] files = webInfoFile.listFiles();
					int size = files.length;
					File f = null;
					String fname = null;
					for(int i=0;i<size;i++){
						f=files[i];
						fname=f.getName();
						if(fname.endsWith(".xml") && fname.startsWith("initMaven-")){
							String xmlPath=path+fname;
							String initMavenClass=getInitMavenClassFromXml(path,fname);
							Class c = Class.forName(initMavenClass);
							
							Object o=c.newInstance();
							
							
							Method m0 = c.getDeclaredMethod("initMavenXml",String.class);
							m0.invoke(o,xmlPath); 
							
							
							Method m1 = c.getDeclaredMethod("init",null);
							m1.invoke(o); 
							
							
							
							System.out.println("以下是自动初始化--"+fname+"的结果，请查看");
							
							Method m2 = c.getDeclaredMethod("testPrintInitConfig",null);
							m2.invoke(o); 
							
							
							
							
							
							
							
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
