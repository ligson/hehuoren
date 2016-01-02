package myFrameU.weixin.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityKit {
	public static String sha1(String str){
		StringBuffer sb=null;
		try {
			sb = new StringBuffer();
			MessageDigest digest = MessageDigest.getInstance("sha1");
			digest.update(str.getBytes());
			byte[] diegestMsg=digest.digest();
			for(byte b:diegestMsg){
				sb.append(String.format("%02x", b));
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
