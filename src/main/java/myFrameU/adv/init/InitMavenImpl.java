package myFrameU.adv.init;import java.io.File;import java.util.ArrayList;import java.util.Iterator;import java.util.List;import myFrameU.adv.entity.AdvertingPage;import myFrameU.adv.entity.Advertisement;import myFrameU.adv.entity.Advertising;import myFrameU.mavenInit.InitMavenI;import org.dom4j.Document;import org.dom4j.Element;import org.dom4j.io.SAXReader;

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
							}else if(eName.equals("advertingPages")){								List<AdvertingPage> apList = new ArrayList<AdvertingPage>();								String isInsert=e.attributeValue("insert");								ic.setPageInsert(isInsert);								if(null!=isInsert && isInsert.equals("database")){									Iterator<Element> iterator1 = e.elementIterator();									while (iterator1.hasNext()) {										Element e1 = iterator1.next();										if(e1.getName().equals("advertingPage")){											AdvertingPage ap = new AdvertingPage();											ap.setName(e1.getText());											ap.setNameKey(e1.attributeValue("nameKey"));											apList.add(ap);										}									}									ic.setPageList(apList);								}							}else if(eName.equals("advertisings")){								List<Advertising> aList = new ArrayList<Advertising>();								String isInsert=e.attributeValue("insert");								ic.setAdvertingInsert(isInsert);								if(null!=isInsert && isInsert.equals("database")){									Iterator<Element> iterator1 = e.elementIterator();									while (iterator1.hasNext()) {										Element e1 = iterator1.next();										if(e1.getName().equals("advertising")){											Advertising a = new Advertising();											String haveSon = e1.attributeValue("haveSon");											if(null!=haveSon && haveSon.equals("YES")){												a.setHaveSon("YES");											}else{												a.setHaveSon("NO");											}											a.setWidth100(new Integer(e1.attributeValue("width100")));											a.setMarkedNum(e1.attributeValue("markedNum"));											a.setInfo(e1.attributeValue("info"));											String widthS = e1.attributeValue("width");											String heightS = e1.attributeValue("height");											if(null!=widthS && null!=heightS && !widthS.equals("") && !heightS.equals("")){												a.setWidth(new Integer(widthS));												a.setHeight(new Integer(heightS));											}																						a.setAdvType(e1.attributeValue("advType"));											a.setStatus(e1.attributeValue("status"));											a.setAdvertingPageNameKey(e1.attributeValue("advertingPageNameKey"));											String picNumberS = e1.attributeValue("picNumber");											if(null!=picNumberS && !picNumberS.equals("")){												a.setPicNumber(new Integer(picNumberS));											}																																	aList.add(a);										}									}									ic.setaList(aList);								}							}else if(eName.equals("advertisements")){								List<Advertisement> aList = new ArrayList<Advertisement>();								String isInsert=e.attributeValue("insert");								ic.setAdvermentInsert(isInsert);								if(null!=isInsert && isInsert.equals("database")){									Iterator<Element> iterator1 = e.elementIterator();									while (iterator1.hasNext()) {										Element e1 = iterator1.next();										if(e1.getName().equals("advertisement")){											Advertisement a = new Advertisement();											a.setAdvertisingMarkedNum(e1.attributeValue("advertisingMarkedNum"));											a.setPicUrl(e1.attributeValue("picUrl"));											a.setPicTitle(e1.attributeValue("picTitle"));											a.setPicA(e1.attributeValue("picA"));											a.setStatus(e1.attributeValue("status"));											String indexNumS = e1.attributeValue("indexNum");											if(null!=indexNumS && !indexNumS.equals("")){												a.setIndexNum(new Integer(indexNumS).intValue());											}											aList.add(a);										}									}									ic.setAdvertisementList(aList);								}															}
						}
					}
				}
			}															
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	public void testPrintInitConfig() {
		
		System.out.println("简易验证框架初始化实体类："+ic.getInitMavenClass().getName());
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
