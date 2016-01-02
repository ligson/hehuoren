package myFrameU.util.httpUtil.ip;

import javax.servlet.http.HttpServletRequest;

import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import net.sf.json.JSONObject;

public class GetIp {
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		ip = request.getHeader("Proxy-Client-IP");  
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		ip = request.getRemoteAddr();  
		}  
		return ip;  
	} 
	
	
	public static String ipLocation(String ip){
		if(null!=ip && !ip.equals("")){
			System.out.println("ip============="+ip);
			if(!ip.equals("0:0:0:0:0:0:0:1") && !ip.equals("127.0.0.1")){
				String content = HttpClientUtil.get("http://api.map.baidu.com/location/ip?ak=0B98f13881da051920fc9aef9249ec6d&coor=bd09ll&ip="+ip, null);
				//System.out.println(content);
				JSONObject json = JSONObject.fromObject(content);
				//System.out.println("address="+json.get("address"));
				System.out.println("content="+json.get("content"));
					JSONObject json_con = JSONObject.fromObject(json.get("content"));
					System.out.println("content.address="+json_con.getString("address"));
					System.out.println("content.address_detail="+json_con.getString("address_detail"));
					System.out.println("content.point="+json_con.getString("point"));//当前（或者某一个IP）的经纬度{"x":"123.43279092","y":"41.80864478"}
				//System.out.println("status="+json.get("status"));
					return json_con.getString("address");
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
