package myFrameU.news.util;


public class NewsTypeUtil {
	//处理allName，当name修改了之后，要级联修改自己的allName
	//allName之间都是用-隔开的。玉石-好玉石-特级玉石
	/**
	 * @param oldAllName  原来老的allName
	 * @param oldName		原来老的name
	 * @param newName		现在新的name
	 * @return
	 */
	public static String getNewAllName(String oldAllName,String oldName,String newName){
		StringBuffer sb = new StringBuffer("");
		String[] oldAllNameArray=oldAllName.split("-");
		int len=oldAllNameArray.length;
		String everyName=null;
		for(int i=0;i<len;i++){
			everyName=oldAllNameArray[i];
			if(everyName.equals(oldName)){
				sb.append(newName).append("-");
			}else{
				sb.append(everyName).append("-");
			}
		}
		String s = sb.toString();
		if(null!=s && !s.equals("")){
			if(s.endsWith("-")){
				s=s.substring(0,s.length()-1);
			}
		}
		return s;
	}
	
	
	
	
	
	
	
}
