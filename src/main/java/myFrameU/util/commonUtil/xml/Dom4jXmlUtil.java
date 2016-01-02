package myFrameU.util.commonUtil.xml;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jXmlUtil {
	/**
	 * 创建新的document
	 */
	public Document createDocument(){
		return DocumentHelper.createDocument(); 
	}
	
	/**
	 * 根据文件名字和路径获取xml文件的document
	 * @param xmlPath
	 * @return
	 */
	public Document getDocumentFromXmlPath(String xmlPath){
		Document document = null;
		try{
			//创建SAXReader对象  
	        SAXReader reader = new SAXReader();  
	        //读取文件 转换成Document  
	        document = reader.read(xmlPath); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return document;
	}
	/**
	 * 从xml字符串中解析出document来
	 * @param xmlStr
	 * @return
	 */
	public Document getDocumentFromXmlContent(String xmlStr){
       try {
		return  DocumentHelper.parseText(xmlStr);
	} catch (DocumentException e) {
		e.printStackTrace();
	}
	return null;  
	}
	
	
	public Element getRoot(Document document){
		return document.getRootElement();  
	}
	
	
	
	public String getElementAttribute(Element e,String attributeName){
		List<Attribute> list = e.attributes(); 
		for(Attribute attribute : list){  
			if(attribute.getName().equals(attributeName)){
				return attribute.getValue();
			}
        }
		return attributeName;  
	}
	
	/*public void listNodes(Element node){  
        System.out.println("当前节点的名称：" + node.getName());  
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        //遍历属性节点  
        for(Attribute attribute : list){  
            System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
        }  
        //如果当前节点内容不为空，则输出  
        if(!(node.getTextTrim().equals(""))){  
             System.out.println( node.getName() + "：" + node.getText());    
        }  
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();  
        while(iterator.hasNext()){  
            Element e = iterator.next();  
            listNodes(e);  
        }  
    }  */
	
	
	
	
	
	
	
	
	
	
	
	
}
