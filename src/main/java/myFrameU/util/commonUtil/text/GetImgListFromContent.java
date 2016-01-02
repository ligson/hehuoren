package myFrameU.util.commonUtil.text;

import java.util.HashMap;
import java.util.Map;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

public class GetImgListFromContent {
	public static void main(String[] args){
		String body="<img src='ss.jpg'/>sssssssssaaaaaaaaa<b>sddddddd</b>";
		Map<String,String> imageMap=fetchImgs(body,"");
		for(String dataKey : imageMap.keySet()){
			System.out.println(dataKey+imageMap.get(dataKey));
		}
	}
	public static Map<String,String> fetchImgs(String content,String webApp){
		Parser parser = null;
		try {
			parser = new Parser(content);
			parser.setEncoding(parser.getEncoding());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map<String,String> imgs = new HashMap<String,String>();
		try{
		NodeFilter filterImg = new NodeClassFilter(ImageTag.class);
		NodeList listImg = parser.extractAllNodesThatMatch(filterImg);
		for(int i=0;i<listImg.size();i++ ){
			if(imgs.size()<=10){
				ImageTag imageTag = (ImageTag)listImg.elementAt(i);
				String imageSrc = imageTag.getImageURL();
				//http://localhost:8080/024pm/ newsImages/1345203110256.jpg
				System.out.println("GetImgListFromContent.imageSrc_old="+imageSrc);
				imageSrc=imageSrc.replace(webApp, "");
				System.out.println("GetImgListFromContent.imageSrc_new="+imageSrc);
				String imageTitle=imageTag.getAttribute("title");
				imgs.put(imageSrc, imageTitle);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		for(Map.Entry<String, String> entry: imgs.entrySet()) {
		}
		return imgs;
		
		
	}
}
