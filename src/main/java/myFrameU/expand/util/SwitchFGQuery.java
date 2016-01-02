package myFrameU.expand.util;


public class SwitchFGQuery {
	/**
	 * 		后台需要的格式是：[{'pId':'23','pvId':'116'},{'pId':'24','pvId':'122'},{'pId':'13','pvId':'53'},{'pId':'14','pvId':'65'},{'pId':'20','pvId':'102'},{'pId':'21','pvId':'107'}]
	 * 		前台传过来格式是：23-116_24-122_13-53_14-65_20-102_21-107
	 */
	public static String switchFGQuery(String queryArgs){
		StringBuffer sb = new StringBuffer("[");
		if(null!=queryArgs && !queryArgs.equals("")){
			if(queryArgs.endsWith("_")){
				queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			}
			if(queryArgs.startsWith("_")){
				queryArgs=queryArgs.substring(1,queryArgs.length());
			}
			if(queryArgs.contains("_")){
				String[] array=queryArgs.split("_");
				int len = array.length;
				String item = null;
				for(int i=0;i<len;i++){
					item=array[i];
					if(null!=item && !item.equals("")){
						if(item.contains("-")){
							String[] array_=item.split("-");
							if(null!=array){
								int len_=array_.length;
								if(len_==2){
									sb.append("{'pId':'").append(array_[0]).append("','pvId':'").append(array_[1]).append("'},");
								}
							}
						}
					}
				}
			}else{
				String[] array=queryArgs.split("-");
				sb.append("{'pId':'").append(array[0]).append("','pvId':'").append(array[1]).append("'}");
			}
		}
		String sbs = sb.toString();
		if(sbs.endsWith(",")){
			sbs=sbs.substring(0,sbs.length()-1);
		}
		//sb.append("]");
		sbs=sbs+"]";
		System.out.println("将前台传递过来的expandIds参数转为："+sbs);
		return sbs;
	}
	
	
	
}
