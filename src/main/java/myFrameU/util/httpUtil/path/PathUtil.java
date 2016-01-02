package myFrameU.util.httpUtil.path;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class PathUtil {
	
	
	
	public static String getWebinf() {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		//path = path.replace('/', '\\'); // 将/换成\
		path = path.replace("file:", ""); // 去掉file:
		path = path.replace("classes/", ""); // 去掉class\
		path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
		return path;
	}
	
	public static String getBasePath(HttpServletRequest request){
		String path = request.getContextPath();
		int port = request.getServerPort();
		String basePath=null;
		if(port==80){
			basePath=request.getScheme()+"://"+request.getServerName()+path+"/";
		}else{
			basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		}
		
		return basePath;
	}
	public static String getBasePath_no(HttpServletRequest request){
		String path = request.getContextPath();
		int port = request.getServerPort();
		String basePath=null;
		if(port==80){
			basePath=request.getScheme()+"://"+request.getServerName()+path;
		}else{
			basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		}
		
		return basePath;
	}
	//获取项目物理路径
	public static String getPhysicsPath(){
		String s = System.getProperty("webapp.root");
		s = s.replace("webAppRoot", "");
		return s;
	}
	
	
	
	
	
}
