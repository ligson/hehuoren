package myFrameU.queryArgs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.myFun.MyFunctions;
import myFrameU.spring.mvc.FatherController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class QueryArgsController extends FatherController {
	@RequestMapping("queryArgs1/getNewCurrentUrl")
	public void clearQueryArgs_getCurrentUrl(HttpServletRequest req,HttpServletResponse response,String queryArgs1,String key,String currentUrl){
		//fgshop/q${clearQueryArgs1}-o${requestScope.orderBy1}-pager${requestScope.pager.currentPage}.htm
		String newQueryArgs1=MyFunctions.clearQueryArgs(queryArgs1, key);
		if(null!=newQueryArgs1 && !newQueryArgs1.equals("")){
			if(newQueryArgs1.endsWith("_")){
				newQueryArgs1=newQueryArgs1.substring(0,newQueryArgs1.length()-1);
			}
		}
		currentUrl=currentUrl.replace(queryArgs1, newQueryArgs1);
		renderData(response, currentUrl);
	}
	
	
	@RequestMapping("queryArgsProperty1/getNewCurrentUrl")
	public void queryArgsProperty1_getCurrentUrl(HttpServletRequest req,HttpServletResponse response,String oldqueryArgsProperty1,String newVal,String currentUrl){
		/**
		 * 第一种情况，替换老的
		 * 第二种情况，原来就没有，要追加
		 */
		if(null!=currentUrl && !currentUrl.equals("")){
			String splitStr="expand"+oldqueryArgsProperty1+"-o";
			String[] urlArray=currentUrl.split(splitStr);
			if(null!=urlArray){
				String url_pre=urlArray[0];
				String url_suf=urlArray[1];
				if(null!=url_pre && !url_pre.equals("") && null!=url_suf && !url_suf.equals("")){
					StringBuffer sb = new StringBuffer(url_pre);
					if(null!=oldqueryArgsProperty1 && !oldqueryArgsProperty1.equals("")){
						if(oldqueryArgsProperty1.contains("_")){
							//说明之前有N多个扩展属性
							boolean replaceMa=false;
							String replaceOldVal=null;
							String[] expandArray=oldqueryArgsProperty1.split("_");
							int len = expandArray.length;
							String expandItem = null;
							for(int i=0;i<len;i++){
								expandItem=expandArray[i];
								String[] expandItemArray=expandItem.split("-");
								if(null!=expandItemArray){
									String expandItemKey=expandItemArray[0]+"-";
									if(newVal.startsWith(expandItemKey)){
										//说明要替换
										replaceMa=true;
										replaceOldVal=expandItem;
										break;
									}else{
										
									}
								}
							}
							if(replaceMa){
								//说明要替换
								if(null!=replaceOldVal && !replaceOldVal.equals("")){
									String oldqueryArgsProperty1_new=oldqueryArgsProperty1.replace(replaceOldVal, newVal);
									sb.append("expand").append(oldqueryArgsProperty1_new).append("-o").append(url_suf);
								}
							}else{
								//说明要追加
								sb.append("expand").append(oldqueryArgsProperty1).append("_").append(newVal).append("-o").append(url_suf);
							}
						}else{
							//说明只有一个扩展属性
							String[] expandItemArray=oldqueryArgsProperty1.split("-");
							if(null!=expandItemArray){
								String expandItemKey=expandItemArray[0]+"-";
								if(newVal.startsWith(expandItemKey)){
									//说明要替换的
									sb.append("expand").append(newVal).append("-o").append(url_suf);
								}else{
									//说明要追加的
									sb.append("expand").append(oldqueryArgsProperty1).append("_").append(newVal).append("-o").append(url_suf);
								}
							}
						}
					}else{
						//原来一个扩展属性都没有，这好办，肯定就是追加
						sb.append("expand").append(newVal).append("-o").append(url_suf);
					}
					
					
					System.out.println("扩展属性获得的新的curUrl="+sb.toString());
					renderData(response, sb.toString());
				}
			}
		}
	}
	
	
}
