package myFrame.myFun;


public class MyFunctions{
	public static void main(String[] args){
		//String txt = replace("pt-[18]_sl-1","pt-\\[.*\\]_*","");
		/*String txt = replace("sl-15_pt-[18]","sl-.*_*","");
		System.out.println(txt);*/
		String txt = clearQueryArgs("","pt");
		System.out.println(txt);
	}
	public static String replace(String txt,String oldTxt,String newTxt){
		if(null!=txt && !txt.equals("")){
			txt=txt.replaceAll(oldTxt, newTxt);
		}
		return txt;
	}
	
	/**
	 * 
	 * @param queryArgs1 	pt-[18]_sl-1		sl-15_pt-[18]
	 * @param item			pt
	 * @return
	 * 重新遍历
	 */
	public static String clearQueryArgs(String queryArgs1,String item){
		StringBuffer sb = new StringBuffer();
		if(null!=queryArgs1 && !queryArgs1.equals("")){
			String[] array=queryArgs1.split("_");
			int len = array.length;
			String queryItem = null;
			for(int i=0;i<len;i++){
				queryItem=array[i];
				if(!queryItem.equals("")){
					if(!queryItem.startsWith(item)){
						sb.append(queryItem).append("_");
					}
				}
			}
			String sbs = sb.toString();
			if(null!=sbs && !sbs.equals("")){
				sbs=sbs.substring(0,sbs.length()-1);
			}
			if(!sbs.equals("")){
				sbs=sbs+"_";
			}
			return sbs;
		}
		return "";
	}
	
	//找出一个queryArgs1中的key的值
	public static String getValue(String queryArgs1,String itemKey){
		if(null!=queryArgs1 && !queryArgs1.equals("")){
			String[] array=queryArgs1.split("_");
			int len = array.length;
			String queryItem = null;
			String value = null;
			for(int i=0;i<len;i++){
				queryItem=array[i];
				if(null!=queryItem && !queryItem.equals("")){
					if(queryItem.startsWith(itemKey)){
						String[] qiArray=queryItem.split("-");
						if(null!=qiArray){
							int qiLen = qiArray.length;
							if(qiLen==2){
								value=qiArray[1];
								if(value.startsWith("[") && value.endsWith("]")){
									value=value.substring(1,value.length()-1);
								}
								return value;
							}
						}
					}
				}
			}
		}
		return "";
	}
	
}
