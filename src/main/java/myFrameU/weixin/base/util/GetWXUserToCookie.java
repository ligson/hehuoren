package myFrameU.weixin.base.util;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.util.httpUtil.cookie.CookieUtil;
import myFrameU.util.httpUtil.image.DownURLImage;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.base.entity.ReceiveXmlEntity;
import myFrameU.weixin.base.entity.WXUser;

public class GetWXUserToCookie {
	/*public static WXUser copyUserToCookie(UICacheManager uICacheManager,ReceiveXmlEntity rxe,HttpServletRequest req,HttpServletResponse response) throws Exception{
		String fromUser=rxe.getFromUserName();
		String toUser=rxe.getToUserName();
		myFrameU.weixin.base.service.impl.UserImpl ui = new myFrameU.weixin.base.service.impl.UserImpl();
		WXUser wxUser = ui.getWXUserByOpenId(fromUser, uICacheManager);
		req.getSession().setAttribute("wxUser", wxUser);
		String wxUserJSON=JSONUtils.toJSONString(wxUser);
		
		
		
		
		String headImageUrl=wxUser.getHeadimgurl();
		if(null!=headImageUrl && !headImageUrl.equals("")){
			boolean canDown = false;
			String imageName=fromUser+".jpg";
			File oldFile = new File(PathUtil.getPhysicsPath()+"/img/user/"+imageName);
			if(null==oldFile){
				canDown=true;
			}else{
				if(!oldFile.exists()){
					canDown=true;
				}
			}
			if(canDown){
				DownURLImage.download(headImageUrl, imageName, PathUtil.getPhysicsPath()+"/img/user/");
			}
		}
		
		
		if(null!=wxUser){
			System.out.println("得到的wxUser的信息为：---");
			System.out.println("nicheng="+wxUser.getNickname()+";openId="+wxUser.getOpenid()+";headImageUrl="+wxUser.getHeadimgurl()+";");
		}
		
		
		return wxUser;
	}*/
}
