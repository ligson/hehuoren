package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.commonUtil.json.JsonValidator;
import myFrameU.util.httpUtil.httpclient.NameValuePair2String;

public class Test4 {

	public static void main(String[] args) {
		try {
			String s=MyFileUtil.readFile("E:/艺术拍拍/application/yishupaipai/src/myFrameU/weixin/base/util/menu.txt");
			s=s.replaceAll("	", "").replaceAll(" ", "").replaceAll("\r\n", "");
			System.out.println(s);
			boolean jsonHege=JsonValidator.validate(s);
			if(!jsonHege){
				System.out.println("传进来的queryArgs非json格式出错");
			}
			
			List<NameValuePair> paramsList = new ArrayList<NameValuePair>();  
			NameValuePair nvp = new BasicNameValuePair("data", s);
			NameValuePair nvp12 = new BasicNameValuePair("data12", s);
			System.out.println(nvp.getValue());
			paramsList.add(nvp); 
			paramsList.add(nvp12); 
			
			String formatParams=NameValuePair2String.nvp2String(paramsList);
			System.out.println(formatParams+"--------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
