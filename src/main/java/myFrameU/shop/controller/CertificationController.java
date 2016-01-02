package myFrameU.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.exception.exception.MyStrException;
import myFrameU.shop.entity.Certification;
import myFrameU.shop.entity.Shop;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.text.IDnumDistinguish;
import myFrameU.util.commonUtil.text.TextUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CertificationController extends FatherController {
	@RequestMapping(value={"shop/certi/certi","user/certi/certi"})
	public String finds( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String certificationType = form.getString("certificationType");
		String reqPrefix = WebAop.getReqPrefix(req);
		if(null!=reqPrefix && !reqPrefix.equals("") && null!=certificationType && !certificationType.equals("")){
			if(reqPrefix.equals("/shop/")){
				Shop shop = (Shop)req.getSession().getAttribute("myShop");
				if(null!=shop){
					int shopId = shop.getId();
					Certification c = (Certification)aBiz.findObjectById("from Certification as c where c.whoclassName=? and c.whoId=?", 
							new Object[]{Shop.class.getName(),shopId});
					if(null!=c){
						req.setAttribute("certification", c);
					}
				}
			}else if(reqPrefix.equals("/user/")){
				User user = (User)req.getSession().getAttribute("myUser");
				if(null!=user){
					int userId = user.getId();
					Certification c = (Certification)aBiz.findObjectById("from Certification as c where c.whoclassName=? and c.whoId=?", 
							new Object[]{User.class.getName(),userId});
					if(null!=c){
						req.setAttribute("certification", c);
					}
				}
			}
		}
		
		if(certificationType.equals("SHOPZIZHIRENZHENG")){
			return "manage/shop/certification/certification_zizhi";
		}else if(certificationType.equals("SHOPSHIMINGRENZHENG")){
			return "manage/shop/certification/certification_shiming";
		}else if(certificationType.equals("USERSHIMINGRENZHENG")){
			return "manage/user/certification/certification_shiming";
		}
		return null;
		
	}
	
	
	
	

	@RequestMapping(value={"admin/certi/finds"})
	public String adminFinds( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String queryArgs = form.getString(CommonField.queryArgs);
		String certificationType = form.getString("certificationType");
		req.setAttribute("certificationType", certificationType);
		aBiz.findEntitysByArgs(Certification.class, EntityTableUtil.tableNameC(Certification.class), queryArgs, null, null, true, 0, "certificationList", req);
		if(queryArgs.contains("myFrameU.shop.entity.Shop")){
			return "manage/admin/shop/certificationList";
		}else{
			return "manage/admin/user/certificationList";
		}
	}
	
	
	

	@RequestMapping(value={"shop/certi/verSfzhm","user/certi/verSfzhm"})
	public void verSfzhm( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String shenfenzhenghaoma = req.getParameter("shenfenzhenghaoma");//身份证号码
		if(null==shenfenzhenghaoma || shenfenzhenghaoma.equals("")){
			throw new MyStrException("抱歉，请填写身份证号码");
		}else{
			IDnumDistinguish.ver(shenfenzhenghaoma);
		}
	}
	
	@RequestMapping(value={"shop/certi/verTrueName","user/certi/verTrueName"})
	public void verTrueName( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String trueName = req.getParameter("trueName");
		if(null==trueName || trueName.equals("")){
			throw new MyStrException("抱歉，请填写真实姓名");
		}else{
			int len = trueName.length();
			if(len<=8){
				boolean b = TextUtil.isChinese(trueName);
				if(!b){
					throw new MyStrException("抱歉，请填写真实姓名");
				}
			}else{
				throw new MyStrException("抱歉，请填写真实姓名");
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
