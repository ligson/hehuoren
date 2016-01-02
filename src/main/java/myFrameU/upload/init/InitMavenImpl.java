package myFrameU.upload.init;import java.io.File;
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
							}else if(eName.equals("upLoadImages")){								List<UploadImageEntity> list = new ArrayList<UploadImageEntity>();								Iterator<Element> iterator1 = e.elementIterator();								while (iterator1.hasNext()) {									Element e2 = iterator1.next();									if(e2.getName().equals("upLoadImage")){										UploadImageEntity uie=new UploadImageEntity();										Iterator<Element> iterator2 = e2.elementIterator();										String e3Name=null;										String e3Value=null;										while (iterator2.hasNext()) {											Element e3 = iterator2.next();											e3Name=e3.getName();											e3Value=e3.getTextTrim();											if(null!=e3Value){												if(e3Name.equals("saveType")){													uie.setSaveType(e3Value);												}else if(e3Name.equals("savePath")){													uie.setSavePath(e3Value);												}else if(e3Name.equals("savePathMin")){													uie.setSavePathMin(e3Value);												}else if(e3Name.equals("getMinWay")){													uie.setGetMinWay(e3Value);												}else if(e3Name.equals("minWidth")){													int width=new Integer(e3Value).intValue();													uie.setMinWidth(width);												}else if(e3Name.equals("minHeight")){													int height=new Integer(e3Value).intValue();													uie.setMinHeight(height);												}else if(e3Name.equals("maxWidth")){													int width=new Integer(e3Value).intValue();													uie.setMaxWidth(width);												}else if(e3Name.equals("maxHeight")){													int height=new Integer(e3Value).intValue();													uie.setMaxHeight(height);												}else if(e3Name.equals("water")){													if(e3Value.equals("true")){														uie.setWater(true);													}else{														uie.setWater(false);													}												}else if(e3Name.equals("waterImage")){													uie.setWaterImage(e3Value);												}											}										}										list.add(uie);									}								}								ic.setList(list);							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	public void testPrintInitConfig() {
		
		/*List<UploadImageEntity> list = ic.getList();		if(null!=list){			int size = list.size();			for(int i=0;i<size;i++){				UploadImageEntity uie = list.get(i);				System.out.println(uie.getSavePath()+"$$$$$$$$$###################################");			}		}else{			System.out.println("初四话的时候list就为空=====================================================");		}*/
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
