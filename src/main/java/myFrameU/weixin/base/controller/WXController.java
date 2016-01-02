package myFrameU.weixin.base.controller;

import hhr.message.MessageUtil;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.global.entity.Global;
import myFrameU.product.entity.Product;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.user.entity.User;
import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.commonUtil.twoDimensional.QRCodeEventsZXING;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.base.entity.TemplateMsg;
import myFrameU.weixin.base.service.impl.MenuImpl;
import myFrameU.weixin.base.service.impl.SendMessageImpl;
import myFrameU.weixin.base.util.SendTemplateMsgArgsUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/weixin/")
public class WXController extends FatherController {
	@RequestMapping("createMenu")
	public void createMenu(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String menuTxtUrl=myFrameU.weixin.init.InitMavenImpl.ic.getMenuTxtUrl();
		String s=MyFileUtil.readFile(menuTxtUrl);
		s=s.replaceAll("	", "").replaceAll(" ", "").replaceAll("\r\n", "");
		String appId = myFrameU.weixin.init.InitMavenImpl.ic.getAppId();
		s=s.replaceAll("@APPID@", appId);
		Global g23= CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 23);
		if(null!=g23){
			s=s.replaceAll("@WEBURL@", g23.getMyValue());
		}
		System.out.println("导航========s="+s);
		if(s.startsWith("?")){
			s=s.substring(1,s.length());
		}
		s=s.substring(1,s.length());
		System.out.println("导航-------s="+s);
		MenuImpl mi = new MenuImpl();
		String sS = mi.createMenu(s, uICacheManager);
		
		System.out.println(sS);
		
	}
	@RequestMapping("removeMenu")
	public void removeMenu(HttpServletRequest req,HttpServletResponse res) throws Exception{
		MenuImpl mi = new MenuImpl();
		String sS = mi.deleteMenu(uICacheManager);
		System.out.println(sS);
	}
	
	
	//test测试方法---给用户发
	@RequestMapping("sendMsg")
	public void sendMsg(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		Integer userId = form.getInteger("userId");
		if(null!=userId && userId.intValue()!=0){
			User u=(User)aBiz.findObjectById("from User as u where u.id=?", new Object[]{userId});
			if(null!=u){
				String openId=u.getWxId();
				if(null!=openId && !openId.equals("")){
					/**
					 * {
					 * 		"touser":"o9WE8uGHLCF15kQaFAjBKSdiNsyg",
					 * 		"template_id":"2uBR_vAHoy-9qqDDleUiSRP6QNZVmQ2cYPBgV_-8bbg",
					 * 		"topcolor":"#FF0000",
					 * 		"data":
					 * 			{
					 * 				"first":{"value":"您微信充值成功！","color":"#173177"},
					 * 				"accountType":{"value":"用户名","color":"#173177"},
					 * 				"account":{"value":"宝汇鲜果速递-小徐","color":"#173177"},
					 * 				"amount":{"value":"0.01元","color":"#173177"},
					 * 				"result":{"value":"充值成功","color":"#173177"},
					 * 				"remark":{"value":"感谢您支持艺藏家官网","color":"#173177"}
					 * 			}
					 * }
					 */
					HashMap<String,String> valuesMap = new HashMap<String,String>();
					valuesMap.put("first", "您微信充值成功！");
					valuesMap.put("accountType", "用户名");
					valuesMap.put("account", "宝汇鲜果速度-小徐");
					valuesMap.put("amount", "500元");
					valuesMap.put("result", "充值成功");
					valuesMap.put("remark", "感谢您支持艺藏家官网");
					
					TemplateMsg tm = SendTemplateMsgArgsUtils.getTemplateMsg(openId, SendTemplateMsgArgsUtils.MyTemplate.RECHARGE_SUCCESS.toString(),
							valuesMap);
					if(null!=tm){
						SendMessageImpl si = new SendMessageImpl();
						si.sendTemplate(tm, uICacheManager,aBiz);
					}
					
					
				}
			}
		}
	}
	//test测试方法---给公共平台发
	@RequestMapping("sendMsgGGPT")
	public void sendMsgGGPT(HttpServletRequest req,HttpServletResponse res) throws Exception{
		Global g22 = CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 22);
		if(null!=g22){
			String wxggptOpenId = g22.getMyValue();
			if(null!=wxggptOpenId && !wxggptOpenId.equals("")){
				Product p = (Product)aBiz.findObjectById("from Product as p where p.id=3", null);
				if(null!=p){
					HashMap<String,Object> argswx = new HashMap<String,Object>();
					argswx.put("product", p);
					argswx.put("wxId", wxggptOpenId);
					String adminPhone=null;
					Global g21=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 21);
					if(null!=g21){
						adminPhone=g21.getMyValue();
					}
					argswx.put("adminPhone", adminPhone);
					MessageUtil.sendMssage(MessageUtil.MssageTYPE.ProductCOUNT.toString(),
							MessageUtil.MssageWay.WEIXIN_DUANXIN.toString(), argswx, aBiz);
					
				}
			}
		}
	}
		
	
	
	//=================================
	/*@RequestMapping("createAdminWX2WM")
	public void createAdminWX2WM(HttpServletRequest req,HttpServletResponse res) throws Exception{
		String imagePathName = "/img/user/admin2weima1.png";
		//String path = "E:/合伙人/application/hehuoren/WebContent";
		String path = PathUtil.getPhysicsPath();
		System.out.println(path+"@@@");
		Global g23 = CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 23);
		if(null!=g23){
			QRCodeEventsZXING.createImg(path+imagePathName,
					g23.getMyValue()+"admin/adminWxId", 400);
			
			System.out.println(g23.getMyValue());
		}
		
	}*/
	
	//完善角色的openId也就是wxId
	
	
	
	
}
