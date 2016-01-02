package myFrameU.util.commonUtil.image;

public class ImageName {
	//从大图中获取小图名
		public static String getSmallImgFromBigImg(String bigImg){
			String smallImgName=null;
			if(null!=bigImg){
				String ext=null;
				String imgName=null;
				String[] arry=bigImg.split("\\.");
				if(arry.length==2){
					imgName=arry[0];
					ext=arry[1];
					smallImgName=imgName+"_min."+ext;
				}
			}
			return smallImgName;
		}
}
