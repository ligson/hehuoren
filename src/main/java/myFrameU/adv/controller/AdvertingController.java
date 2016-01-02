package myFrameU.adv.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.adv.entity.Advertising;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/adv/")
public class AdvertingController extends FatherController{
	@RequestMapping("closeAdverting")
	public void closeAdverting(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Integer advertingId = form.getInteger("advertingId");
		if(null!=advertingId && advertingId.intValue()!=0){
			Advertising a = (Advertising)aBiz.findObjectById("from Advertising as a where a.id=?", new Object[]{advertingId});
			if(null!=a){
				a.setStatus(Advertising.STATUS.CLOSE.toString());
				aBiz.modifyObject(a);
			}
		}
	}
}
