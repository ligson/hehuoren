package myFrameU.product.util;

import myFrameU.product.entity.Product;

public class ProductUtil {
	
	private static String getSmallImage(String bigImage){
		String minImageName="";
		if(null!=bigImage && !bigImage.equals("")){
//		    img/product/1434701594624.jpg
			String imageName=bigImage.replace("img/product/", "");
			if(null!=imageName && !imageName.equals("")){
				String[] nameArray=imageName.split("\\.");
				if(null!=nameArray){
					int len = nameArray.length;
					if(len==2){
						minImageName=nameArray[0]+"_min."+nameArray[1];
					}
				}
			}
		}
		if(minImageName.equals("")){
			return "";
		}else{
			return "img/product/min/"+minImageName;
		}
	}
}
