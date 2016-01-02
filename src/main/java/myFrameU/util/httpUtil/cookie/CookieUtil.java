package myFrameU.util.httpUtil.cookie;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CookieUtil {
	/**
	 * 设置cookie
	 * @param response
	 * @param name  cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
		try{
			String cookieValueBase64 = new String(Base64.encode(value.getBytes("UTF-8")));
			cookieValueBase64 = cookieValueBase64.replace("\"r\"n", "");              
			cookieValueBase64 = cookieValueBase64.replace("\"n", "");     
			String cookieValueUrlEncode = URLEncoder.encode(cookieValueBase64, "UTF-8");
			
		    Cookie cookie = new Cookie(name,cookieValueUrlEncode);
		    cookie.setPath("/");
		    if(maxAge>0)  cookie.setMaxAge(maxAge);
		    response.addCookie(cookie);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	/**
	 根据名字删除cookie
	 */
	public static void removeCookieByName(HttpServletRequest request,String name,HttpServletResponse res){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        if(null!=cookie){
	        	 cookie.setMaxAge(0);
	 	        cookie.setPath("/");
	 	        res.addCookie(cookie);
	        }
	    }else{
	    }   
	}
	/**
	 * 根据名字获取cookieValue
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request,String name){
		try{
			Map<String,Cookie> cookieMap = ReadCookieMap(request);
		    if(cookieMap.containsKey(name)){
		        Cookie cookie = (Cookie)cookieMap.get(name);
		        String value = cookie.getValue();
		        value=new String(Base64.decode(URLDecoder.decode(value, "UTF-8")),"UTF-8");
		        return value;
		    }else{
		        return null;
		    }   
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	
	
}
