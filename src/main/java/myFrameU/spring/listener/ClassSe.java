package myFrameU.spring.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.commonUtil.text.MD5;
import myFrameU.util.httpUtil.path.PathUtil;

import com.mysql.jdbc.util.ResultSetUtil;

public class ClassSe {
	
	public static boolean canInit(HttpServletRequest req) throws Exception{
		/*boolean b1 = init_mysql(req);
		System.out.println(b1+"----------");
		boolean b2 = init_file(req);
		System.out.println(b2+"----------");
		if(b1 && b2){
		}else{
			String webINpath=PathUtil.getWebinf();
			if(null!=webINpath){
				File f = new File(webINpath+"/classes/");
				if(f.isDirectory()){
					MyFileUtil.deleteDirectory(f.getAbsolutePath());
				}
			}
			Thread.sleep(1000*10);
			System.exit(0);
		}
		return b1 && b2;*/
		return true;
	}
	
	public static String readFile(String filePath) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePath), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			sb.append(line);
		}
		br.close();
		return sb.toString();
	}

	public static boolean init_file(HttpServletRequest req) throws Exception{
		File f = new File("C:/WINDOWS/system32/wins/class/m.txt");
		//System.out.println("111111111111111");
		if(null!=f){
			//System.out.println("222222222222222");
			if(f.exists()){
				//System.out.println("33333333333333");
				String s = readFile("C:/WINDOWS/system32/wins/class/m.txt");
				if(null!=s){
					//System.out.println("444444444444444");
					if(s.contains("@")){
						//System.out.println("555555555555555555");
						String[] array= s.split("@");
						if(null!=array){
							//System.out.println("6666666666666666");
							int len = array.length;
							if(len==4){
								//System.out.println("7777777777777777");
								String s1_f=array[0];
								String s2_f=array[1];
								String s3_f=array[2];
								String s4_f=array[3];
								
								
								
								
								
								MD5 md = new MD5();
								InetAddress myIPaddress;
								myIPaddress = InetAddress.getLocalHost();
								String mi = myIPaddress.getHostAddress();
								
								String s1=md.getMD5ofStr(mi);
								if(null!=req){
									String s2=PathUtil.getBasePath(req);
									s2=md.getMD5ofStr(s2);
									if(!s2_f.equals(s2)){
										return false;
									}
									System.out.println(s2_f+"===s2_f"+"====="+s2);	
								}
								String s3=myFrameU.sms.init.InitMavenImpl.ic.getUserId();
								s3=md.getMD5ofStr(s3);
								String s4=myFrameU.weixin.init.InitMavenImpl.ic.getAppId();
								s4=md.getMD5ofStr(s4);
								
								
								
								
								//System.out.println(s1_f+"===s1_f===="+s1);
								//System.out.println(s3_f+"===s3_f===="+s3);
								//System.out.println(s4_f+"===s4_f==="+s4);
								
								
								
								
								if(!s1_f.equals(s1)){
									//System.out.println("8888888888888888888888");
									return false;
								}
								
								if(!s3_f.equals(s3)){
									//System.out.println("99999999999999999");
									return false;
								}
								
								if(!s4_f.equals(s4)){
									//System.out.println("10101010101010");
									return false;
								}
								
								
							}
						}
					}
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		
		return true;
	}
	
	public static boolean init_mysql(HttpServletRequest req) throws Exception{
		
		InetAddress myIPaddress;
		myIPaddress = InetAddress.getLocalHost();
		String mi = myIPaddress.getHostAddress();
		System.out.println(mi);
		
		//System.out.println(ResultSetUtil.resultSet_2);
		
		MD5 md=new MD5();
		String s1=md.getMD5ofStr(mi);
		if(!ResultSetUtil.resultSet_1.equals(s1)){
			return false;
		}
		
		String s3=myFrameU.sms.init.InitMavenImpl.ic.getUserId();
		s3=md.getMD5ofStr(s3);
		if(!ResultSetUtil.resultSet_3.equals(s3)){
			return false;
		}
		
		String s4=myFrameU.weixin.init.InitMavenImpl.ic.getAppId();
		s4=md.getMD5ofStr(s4);
		if(!ResultSetUtil.resultSet_4.equals(s4)){
			return false;
		}
		
		if(null!=req){
			String s2=PathUtil.getBasePath(req);
			s2=md.getMD5ofStr(s2);
			if(!ResultSetUtil.resultSet_2.equals(s2)){
				return false;
			}
		}
		
		return true;
	}
	
	
	
	
	public static void main(String[] args){
		
        
	}
}
