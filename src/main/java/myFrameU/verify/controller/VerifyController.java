package myFrameU.verify.controller;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.commonUtil.text.TextUtil;
import myFrameU.verify.init.InitMavenImpl;
import myFrameU.verify.util.VerifyUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value="/verify")
public class VerifyController extends FatherController {
	
	//格式验证
	@RequestMapping(value="/geshi")
	public void geshi(HttpServletRequest req,HttpServletResponse res) throws Exception{
		PrintWriter out = res.getWriter();
		SDynaActionForm form =getSDynaActionForm(req);
		String geshi=form.getString("geshi");
		String value=form.getString("value");
		String error=null;
		if(null!=geshi && !geshi.equals("")){
			if(null!=value && !value.equals("")){
				boolean b=false;
				if(EnumUtil.equalsE(VerifyUtil.GESHI.NUMBER, geshi)){
					b = TextUtil.isNumber(value);
					error="输入的值格式不正确";
				}else if(EnumUtil.equalsE(VerifyUtil.GESHI.TELPHONE, geshi)){
					String verPhone = PhoneUtil.vailterTelPhone(value);
					if(null==verPhone){
						b=true;
					}else{
						error=verPhone;
					}
				}
				if(!b){
					throw new MyStrException(error);
				}
			}else{
				throw new MyStrException("请输入值");
			}
		}
	}
	
	@RequestMapping(value="/unique")
	public void unique(HttpServletRequest req,HttpServletResponse res) throws Exception{
		PrintWriter out = res.getWriter();
		SDynaActionForm form =getSDynaActionForm(req);
		String name=form.getString("name");
		String values=form.getString("values");
		if(null!=name && null!=values ){
			if(!name.equals("") && !values.equals("")){
				HashMap<String,String> map = InitMavenImpl.ic.getNamesqlMap();
				String sql=map.get(name);
				if(null!=sql && !sql.equals("")){
					//select count(id) from shopUser where name=?
					Object[] os=null;
					if(values.contains(",")){
						os=values.split(",");
					}else{
						//只有一个值
						os=new Object[]{values};
					}
					java.math.BigInteger countIn=(java.math.BigInteger)aBiz.j_queryObject(sql,os);
					if(null!=countIn && countIn.intValue()>0){
						out.print("noUnique");
					}else{
						out.print("unique");
					}
				}
			}
		}
	}
}
