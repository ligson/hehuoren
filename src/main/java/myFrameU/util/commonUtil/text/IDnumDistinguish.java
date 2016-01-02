package myFrameU.util.commonUtil.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myFrameU.exception.exception.MyStrException;

//身份证
public class IDnumDistinguish {
	public static void main(String args[]){
		try {
			ver("37078519870617255X");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ver(String idNum) throws Exception{
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）  
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");  
        //通过Pattern获得Matcher  
        Matcher idNumMatcher = idNumPattern.matcher(idNum);  
        //判断用户输入是否为身份证号  
        if(idNumMatcher.matches()){  
            System.out.println("您的出生年月日是：");  
            //如果是，定义正则表达式提取出身份证中的出生日期  
            Pattern birthDatePattern= Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");//身份证上的前6位以及出生年月日  
            //通过Pattern获得Matcher  
            Matcher birthDateMather= birthDatePattern.matcher(idNum);  
            //通过Matcher获得用户的出生年月日  
            if(birthDateMather.find()){  
                String year = birthDateMather.group(1);  
                String month = birthDateMather.group(2);  
                String date = birthDateMather.group(3);  
                //输出用户的出生年月日  
                System.out.println(year+"年"+month+"月"+date+"日");                  
            }     
        }else{  
            //如果不是，输出信息提示用户  
        	throw new MyStrException("您输入的并不是身份证号");
          //  System.out.println("您输入的并不是身份证号");  
        }  
	}
	
}
