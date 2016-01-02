package myFrameU.weixin.base.service.impl;

import java.io.File;

import myFrameU.ehcache.util.UICacheManager;
import myFrameU.util.commonUtil.file.MyFileUtil;
import myFrameU.util.commonUtil.json.JSONUtils;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.image.DownURLImage;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.base.entity.WXUser;
import myFrameU.weixin.base.entity.WXUserListEntity;
import myFrameU.weixin.base.service.UserInterface;
import myFrameU.weixin.init.InitConfig;

import org.json.JSONObject;

public class UserImpl implements UserInterface {
	private static final String url="https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN";
	private static final String listUrl="https://api.weixin.qq.com/cgi-bin/user/get";
	@Override
	public WXUser getWXUserByOpenId(String openId,UICacheManager uICacheManager,String shopOrUser)  throws Exception {
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		StringBuffer sb = new StringBuffer(url);
		sb.append("&access_token=").append(accessToken).append("&openid=").append(openId);
		String getUrl=sb.toString();
		String result = HttpClientUtil.get(getUrl, null);
		
		System.out.println("的getWXUserByOpenIdgetWXUserByOpenId===="+result);
		
		
		if(null!=result && !result.equals("")){
			//System.out.println("getWXUserByOpenIdgetWXUserByOpenId======result"+result);
			//解析返回的xml
			/**
			 * {
				    "subscribe": 1, 
				    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
				    "nickname": "Band", 
				    "sex": 1, 
				    "language": "zh_CN", 
				    "city": "广州", 
				    "province": "广东", 
				    "country": "中国", 
				    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
				   "subscribe_time": 1382694957,
				   "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
				   "remark": "",
				   "groupid": 0
				}
			 */
			WXUser user = JSONUtils.toBean(result, WXUser.class);
			
			
			
			//下载头像
			if(null!=user){
				String headImageUrl = user.getHeadimgurl();
				if(null!=headImageUrl && !headImageUrl.equals("")){
					if(null!=shopOrUser && !shopOrUser.equals("")){
						String filePath=null;
						if(shopOrUser.equals("user")){
							filePath=PathUtil.getPhysicsPath()+"/img/user/";
						}else if(shopOrUser.equals("shop")){
							filePath=PathUtil.getPhysicsPath()+"/img/shop/";
						}
						
						boolean canDown = false;
						String imageName=openId+".jpg";
						File oldFile = new File(filePath+imageName);
						if(null==oldFile){
							canDown=true;
						}else{
							if(!oldFile.exists()){
								canDown=true;
							}
						}
						if(canDown){
							DownURLImage.download(headImageUrl, imageName, filePath);
						}
						
					}
				}
			}
			return user;
		}
		return null;
	}

	@Override
	public String getUserList(UICacheManager uICacheManager)  throws Exception {
		String data = null;
		AccessTokenImpl ati = new AccessTokenImpl();
		String accessToken=ati.getAccess_token(uICacheManager);
		StringBuffer sb = new StringBuffer(listUrl);
		sb.append("&access_token=").append(accessToken);
		String getUrl=sb.toString();
		String result = HttpClientUtil.get(getUrl, null);
		System.out.println("getUserListgetUserListgetUserList=====result=="+result);
		if(null!=result && !result.equals("")){
			/**
			 * {
				  "total":23000,
				  "count":10000,
				  "data":{"
				     openid":[
				        "OPENID1",
				        "OPENID2",
				        ...,
				        "OPENID10000"
				     ]
				   },
				   "next_openid":"NEXT_OPENID1"
				}
			 */
			
			WXUserListEntity wxule = JSONUtils.toBean(result, WXUserListEntity.class);
			if(null!=wxule){
				data = wxule.getData();
				System.out.println(data);
			}
		}
		return data;
	}

	
	
	//=REDIRECT_URI&scope=snsapi_base#wechat_redirect
	@Override
	public String getUserOpenId(String code)
			throws Exception {
		
		
		
		InitConfig ic = myFrameU.weixin.init.InitMavenImpl.ic;
		String appId = ic.getAppId();
		String secret = ic.getSecret();
		
		StringBuffer getATSB = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		getATSB.append(appId).append("&secret=").append(secret).append("&code=").append(code).append("&grant_type=authorization_code");
		
		String getAT  = getATSB.toString();
		
		 String openId=null;
		String result = HttpClientUtil.get(getAT, null);
		if(null!=result && !result.equals("")){
			//{"access_token":"OezXcEiiBSKSxW0eoylIeEq7JuKWaRQWYQ2rOn2zmhJxQu5Piqs9DlM2Aa0AOc6qxhyElUc_ma6D9BvIyQ4NQF7ULNTrMrh589RhVNEXTloEkVDwL1sYVL_LO0YE_VaO0Her1Iscd10Old48OabUBQ","expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeEq7JuKWaRQWYQ2rOn2zmhJxQu5Piqs9DlM2Aa0AOc6qISKN9_3Mj_sdmv6aBJQrEBsKY-Hy0vNKff0o69yU4vzek7qltXRgaOXlds50dzoFr5Hjq9LYv7KhxUwv8Il55g","openid":"o9WE8uGHLCF15kQaFAjBKSdiNsyg","scope":"snsapi_userinfo"}
			if(result.contains("openid")){
				//System.out.println("resultresultresultresult==="+result);
				JSONObject a = new JSONObject(result);  
				//System.out.println("aaaaaaaaaa==="+a.toString());
				 openId = a.getString("openid");
			}else{
				//发生错误
				//再调用一次
				/*System.out.println("根据code找openId发生错误，再调用一次");
				openId = getUserOpenId(code);*/
				MyFileUtil.writeFile("D://weixin.shanxity.com/ROOT/test.txt", "根据code获取微信用户的wxId发生错误,result="+result);
			}
			
		}
		 return openId;
	}

}
