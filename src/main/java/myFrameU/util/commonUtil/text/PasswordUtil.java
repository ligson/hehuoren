package myFrameU.util.commonUtil.text;

import myFrameU.exception.exception.MyStrException;

public class PasswordUtil {
	public static void main(String[] args){
		try {
			verPassword("12333456papb");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void verPassword(String pwd) throws Exception{
		if(null!=pwd && !pwd.equals("")){
			/**
			 * 密码合格：
			 * 1、位大于等于6
			 * 2、数字和字母混合
			 * 3、不能有中文
			 */
			int len = pwd.length();
			if(len>=6){
				if(len<=20){
					boolean hasSymble = !pwd.matches("^[\\da-zA-Z]*$"); 
					//System.out.println(hasSymble);
					if(hasSymble){
						throw new MyStrException("新密码只能是数字和英文字母！");
					}
				}else{
					throw new MyStrException("新密码长度必须小于等于20位");
				}
			}else{
				throw new MyStrException("新密码长度必须大于等于6位");
			}
		}else{
			throw new MyStrException("请输入新密码");
		}
	}
}
