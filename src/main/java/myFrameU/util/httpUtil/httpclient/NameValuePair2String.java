package myFrameU.util.httpUtil.httpclient;

import java.util.List;

import org.apache.http.NameValuePair;

public class NameValuePair2String {
	public static String nvp2String(List<NameValuePair> paramsList){
		StringBuffer sb = new StringBuffer();
		if(null!=paramsList){
			int size = paramsList.size();
			NameValuePair nvp = null;
			for(int i=0;i<size;i++){
				nvp=paramsList.get(i);
				if(i==(size-1)){
					sb.append(nvp.getName()).append("=").append(nvp.getValue());
				}else{
					sb.append(nvp.getName()).append("=").append(nvp.getValue()).append("&");
				}
			}
		}
		return sb.toString();
	}
}
