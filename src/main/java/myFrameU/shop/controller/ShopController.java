package myFrameU.shop.controller;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myFrame.cache.CacheKey;
import myFrameU.account.entity.Account;
import myFrameU.address.entity.Address;
import myFrameU.biz.AbstractBizI;
import myFrameU.ehcache.util.UICacheManager;
import myFrameU.exception.exception.MyRefererException;
import myFrameU.exception.exception.MyStrException;
import myFrameU.global.entity.Global;
import myFrameU.product.entity.ProductType;
import myFrameU.queryArgs.util.SwitchFGQuery;
import myFrameU.shop.biz.ShopBizI;
import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopContent;
import myFrameU.shop.entity.ShopLevel;
import myFrameU.shop.entity.ShopUser;
import myFrameU.shop.init.InitConfig;
import myFrameU.shop.util.ShopMainProductTypeUtil;
import myFrameU.spring.aop.web.WebAop;
import myFrameU.spring.mvc.CommonField;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
import myFrameU.util.commonUtil.enumU.EnumUtil;
import myFrameU.util.commonUtil.text.PasswordUtil;
import myFrameU.util.commonUtil.text.PhoneUtil;
import myFrameU.util.commonUtil.text.TextUtil;
import myFrameU.util.commonUtil.text.WebFormatter;
import myFrameU.util.commonUtil.twoDimensional.TwoDimensionalCodeSwetakeQRCodeUtil;
import myFrameU.util.httpUtil.httpclient.HttpClientUtil;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.util.sshUtil.hibernate.EntityTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ShopController extends FatherController {
	@Autowired
	private ShopBizI shopBizI;
	public ShopBizI getShopBizI() {
		return shopBizI;
	}
	public void setShopBizI(ShopBizI shopBizI) {
		this.shopBizI = shopBizI;
	}








	@RequestMapping(value={"/wrap/fgshop/q{queryArgs1:.*}-o{orderBy1:.*}-pager{currentPage:[0-9]+}.htm" })
	public String wrapfgfinds( HttpServletRequest req,HttpServletResponse res,@PathVariable String queryArgs1,@PathVariable String orderBy1,@PathVariable Integer currentPage) throws Exception{
		req.setAttribute("currentPage", currentPage+"");
		SDynaActionForm form = getSDynaActionForm(req);
		//String queryArgs = form.getString(CommonField.queryArgs);
		String queryArgs=queryArgs1;
		if(null!=queryArgs && !queryArgs.equals("") && !queryArgs.contains("'key':")){
    		queryArgs=SwitchFGQuery.switchFGQuery(Shop.class.getName(),queryArgs);
    	}
		if(null!=queryArgs && !queryArgs.equals("")){
			queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			queryArgs=queryArgs+",{'key':'status','value':'OK'}]";
		}else{
			queryArgs="[{'key':'status','value':'OK'}]";
		}
		
		String orderBy = orderBy1;
		if(null!=orderBy && !orderBy.equals("") && !orderBy.contains("'field':")){
			orderBy=SwitchFGQuery.switchFGOrder(orderBy);
    	}
		
		aBiz.findEntitysByArgs(Shop.class, EntityTableUtil.tableNameC(Shop.class), queryArgs, orderBy, null, true, 0, "shopList", req);
		
		return "wrap/fg/shopList";
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value={"/fgshop/q{queryArgs1:.*}-o{orderBy1:.*}-pager{currentPage:[0-9]+}.htm" })
	public String finds( HttpServletRequest req,HttpServletResponse res,@PathVariable String queryArgs1,@PathVariable String orderBy1,@PathVariable Integer currentPage) throws Exception{
		req.setAttribute("currentPage", currentPage+"");
		SDynaActionForm form = getSDynaActionForm(req);
		//String queryArgs = form.getString(CommonField.queryArgs);
		String queryArgs=queryArgs1;
		if(null!=queryArgs && !queryArgs.equals("") && !queryArgs.contains("'key':")){
    		queryArgs=SwitchFGQuery.switchFGQuery(Shop.class.getName(),queryArgs);
    	}
		if(null!=queryArgs && !queryArgs.equals("")){
			queryArgs=queryArgs.substring(0,queryArgs.length()-1);
			queryArgs=queryArgs+",{'key':'status','value':'OK'}]";
		}else{
			queryArgs="[{'key':'status','value':'OK'}]";
		}
		
		String orderBy = orderBy1;
		if(null!=orderBy && !orderBy.equals("") && !orderBy.contains("'field':")){
			orderBy=SwitchFGQuery.switchFGOrder(orderBy);
    	}
		
		aBiz.findEntitysByArgs(Shop.class, EntityTableUtil.tableNameC(Shop.class), queryArgs, orderBy, null, true, 0, "shopList", req);
		//循环处理主营类目
		List<Shop> shopList = (List<Shop>) req.getAttribute("shopList");
		StringBuffer shopIdSB = new StringBuffer();
		if(null!=shopList){
			int size = shopList.size();
			Shop shop = null;
			for(int i=0;i<size;i++){
				shop = shopList.get(i);
				shop.setMainProductTypeList(uICacheManager);
				if(i==(size-1)){
					shopIdSB.append(shop.getId());
				}else{
					shopIdSB.append(shop.getId()).append(",");
				}
			}
		}
		/*Pager pager = (Pager) req.getAttribute("pager");
		pager.setTotalRows(80);
		pager.setTotalPages(40);
		req.setAttribute("pager", pager);*/
		
		
		
		List<ProductType> productTypeList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", productTypeList);
		
		List<ShopLevel> shopLevelList = CacheKey.CKShopLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("shopLevelList", shopLevelList);
		
		
		//获取每个店铺最新的最VIP的拍品。6个
		String shopIds = shopIdSB.toString();
		if(null!=shopIds && !shopIds.equals("")){
			
		}
		
		
		
		
		
		
		return "pcjsp/shopList";
	}
	
	@RequestMapping(value={"admin/shop/finds","user/shop/finds","shop/finds"})
	public String finds( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String queryArgs = form.getString(CommonField.queryArgs);
		aBiz.findEntitysByArgs(Shop.class, EntityTableUtil.tableNameC(Shop.class), queryArgs, null, null, true, 0, "shopList", req);
		//循环处理主营类目
		List<Shop> shopList = (List<Shop>) req.getAttribute("shopList");
		if(null!=shopList){
			int size = shopList.size();
			Shop shop = null;
			for(int i=0;i<size;i++){
				shop = shopList.get(i);
				shop.setMainProductTypeList(uICacheManager);
			}
		}
		List<ProductType> productTypeList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", productTypeList);
		
		List<ShopLevel> shopLevelList = CacheKey.CKShopLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("shopLevelList", shopLevelList);
		
		return getForward("shop/shopList", req);
	}
	
	@RequestMapping(value={"admin/shop/findShopUsers"})
	public String findShopUsers( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String queryArgs = form.getString(CommonField.queryArgs);
		aBiz.findEntitysByArgs(ShopUser.class, EntityTableUtil.tableNameC(ShopUser.class), queryArgs, null, null, true, 0, "shopUserList", req);
		return getForward("shop/shopUserList", req);
	}
	
	@RequestMapping(value={"admin/shop/toRegist"})
	public String toRegist( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		List<ProductType> productTypeList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", productTypeList);
		List<ShopLevel> shopLevelList = CacheKey.CKShopLevel.ALLMAP.getList(uICacheManager);
		req.setAttribute("shopLevelList", shopLevelList);
		String reqPrefix = WebAop.getReqPrefix(req);
		if(null!=reqPrefix && !reqPrefix.equals("")){
			if(reqPrefix.equals("/admin/")){
				return "manage/admin/shop/regist";
			}else{
				return "pcjsp/shopRegist";
			}
		}
		return getForward("shop/regist", req);
	}
	
	@RequestMapping(value={"/shopVerUserName"})
	public void verUserName( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String shopUserName=form.getString("shopUserName");
		if(null!=shopUserName && !shopUserName.equals("")){
			int len = shopUserName.length();
			if(len>=6){
				boolean b = TextUtil.isEnglish(shopUserName);
				if(!b){
					throw new MyStrException("登录账号必须为英文字符");
				}
			}else{
				throw new MyStrException("登录账号必须大于等于6位");
			}
		}else{
			throw new MyStrException("请输入登录账号");
		}
	}
	
	@RequestMapping(value={"/shopVerPassword"})
	public void verPassword( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String shopUserPassword=form.getString("shopUserPassword");
		if(null!=shopUserPassword && !shopUserPassword.equals("")){
			PasswordUtil.verPassword(shopUserPassword);
		}else{
			throw new MyStrException("请输入密码");
		}
	}
	
	
	
	@RequestMapping(value={"admin/shop/regist","/shopRegist"})
	public String regist( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		String name = form.getString("name");
		//String smallName = form.getString("smallName");
		String logo = form.getString("logo");
		String phone = form.getString("phone");
		String[] mainProductTypeIds = req.getParameterValues("mainProductTypeIds");
		Integer addressId = form.getInteger("addressId");
		String specificAddress = form.getString("specificAddress");
		
		String shopUserName=form.getString("shopUserName");
		String shopUserPassword = form.getString("shopUserPassword");
		
		if(null==name || name.equals("")){
			throw new MyRefererException("抱歉，必须填写店铺名称");
		}
		/*if(null==smallName || smallName.equals("")){
			throw new MyStrException("抱歉，必须填写店铺短名称");
		}*/
		if(null==logo || logo.equals("")){
			throw new MyRefererException("抱歉，必须上传店铺LOGO");
		}
		if(null==phone || phone.equals("")){
			throw new MyRefererException("抱歉，必须填写电话");
		}else{
			String result = PhoneUtil.vailterTelPhone(phone);
			if(null!=result){
				throw new MyRefererException(result);
			}else{
				//手机号唯一性
				int phoneShopCount_int = 0;
				BigInteger phoneShopCount = (BigInteger)aBiz.j_queryObject("select count(id) from shop as s where s.phone=?", new Object[]{phone});
				if(null!=phoneShopCount){
					phoneShopCount_int=phoneShopCount.intValue();
				}
				if(phoneShopCount_int!=0){
					throw new MyRefererException("抱歉，该手机已经注册");
				}
			}
		}
		
		if(null==mainProductTypeIds || mainProductTypeIds.length==0){
			throw new MyRefererException("抱歉，必须选择主营范围");
		}else{
			//shopLevel2的普通店铺最大的选择大类是
		}
		if(null==addressId || addressId.intValue()==0){
			throw new MyRefererException("抱歉，必须填写地址");
		}
		
		if(null==shopUserName || shopUserName.equals("")){
			throw new MyRefererException("抱歉，必须填写店铺登录账号");
		}else{
			int len = shopUserName.length();
			if(len>=6){
				boolean b = TextUtil.isEnglish(shopUserName);
				if(!b){
					throw new MyStrException("登录账号必须为英文字符");
				}
			}else{
				throw new MyStrException("登录账号必须大于等于6位");
			}
		}
		if(null==shopUserPassword || shopUserPassword.equals("")){
			throw new MyRefererException("抱歉，必须填写店铺登录密码");
		}else{
			PasswordUtil.verPassword(shopUserPassword);
		}
		
		Shop shop = new Shop();
		shop.setSpecificAddress(specificAddress);
		shop.setName(name);
		shop.setSmallName(name);
		shop.setAddressId(addressId);
		Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
		if(null!=a){
			shop.setAddressTreeIds(a.getTreeId());
		}
		shop.setCertiStatus(Shop.CERTISTATUS.WAIT.toString());
		shop.setClinchCount(0);
		shop.setClinchPrice(0);
		shop.setGrade(0);
		shop.setLogo(logo);
		shop.setInfo("");
		
		HashMap<String,ProductType> ptMap = CacheKey.CKProductType.ALLMAP.getMap(uICacheManager);
		//处理主营类目，处理成[][][]
		//因为选择的都是子类，那么得自动加上父亲
		int mptSize = mainProductTypeIds.length;
		if(mptSize==1){
			int ptId = new Integer(mainProductTypeIds[0]).intValue();
			ProductType pt = ptMap.get("productTypeId_"+ptId);
			if(null!=pt){
				shop.setMainProductTypeIds(pt.getTreeId());
			}
		}else{
			StringBuffer mptSB = new StringBuffer();
			String mpt = null;
			int ptId = 0;
			ProductType pt =  null;
			String fatherIdStr=null;
			for(int i=0;i<mptSize;i++){
				mpt=mainProductTypeIds[i];
				ptId = new Integer(mpt).intValue();
				if(ptId!=0){
					pt = ptMap.get("productTypeId_"+ptId);
					if(null!=pt){
						int fatherId=pt.getFatherId();
						if(fatherId!=0){
							fatherIdStr="["+fatherId+"]";
							if(!mptSB.toString().contains(fatherIdStr)){
								mptSB.append(fatherIdStr);
							}
						}
						mptSB.append("[").append(mpt).append("]");
					}
				}
			}
			shop.setMainProductTypeIds(mptSB.toString());
		}
		
		shop.setShopLevelId(2);//默认的是普通shop
		boolean ptOk=ShopMainProductTypeUtil.verCount_shopMainPts(shop.getMainProductTypeIds(), uICacheManager, shop.getShopLevelId());
		if(!ptOk){
			ShopLevel sl = CacheKey.CKShopLevel.ALLMAP.getObject(uICacheManager, shop.getShopLevelId());
			if(null!=sl){
				throw new MyRefererException("抱歉，您最多可以选择"+sl.getShopMainBigProductTypeCount()+"个大类");
			}
		}
		shop.setPhone(phone);
		shop.setProductCount(0);
		shop.setRuzhuDate(new Date());
		
		
		
		shop.setShoucangCount(0);
		shop.setSpecialCount(0);
		Global g12=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 12);
		if(null!=g12){
			String g12v=g12.getMyValue();
			float shopRegistPrice = new Float(g12v).intValue();
			if(shopRegistPrice==0){
				shop.setStatus(Shop.STATUS.OK.toString());//
			}else{
				shop.setStatus(Shop.STATUS.WAIT.toString());
			}
		}
		shop.setTotalScore(0);
		
		ShopUser suold = (ShopUser)aBiz.findObjectById("from ShopUser as su where su.name=?", new Object[]{shopUserName});
		if(null!=suold){
			throw new MyRefererException("抱歉，该登录账号已经存在");
		}
		
		ShopUser su = new ShopUser();
		su.setName(shopUserName);
		su.setPassword(shopUserPassword);
		
		shopBizI.registShop(shop, su);
		
		/**
		 * 生成二维码
		 */
		TwoDimensionalCodeSwetakeQRCodeUtil.createImg(PathUtil.getPhysicsPath()+"/img/shop/shop2weima"+shop.getId()+".png",
				PathUtil.getBasePath(req)+"/wrap/fgshop/id"+shop.getId()+"-auctions-s-o-pager1.htm");
		
		return redirect;
	}
	

	@RequestMapping(value={"/shop/shop/modify"})
	public String modify( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String redirect = form.getString(CommonField.redirect);
		String name = form.getString("name");
		String smallName = form.getString("smallName");
		String logo = form.getString("logo");
		Integer addressId = form.getInteger("addressId");
		String specificAddress = form.getString("specificAddress");
		String content=form.getString("content");
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			if(null==name || name.equals("")){
				throw new MyRefererException("抱歉，必须填写店铺名称");
			}
			if(null==smallName || smallName.equals("")){
				throw new MyRefererException("抱歉，必须填写店铺短名称");
			}
			if(null==logo || logo.equals("")){
				throw new MyRefererException("抱歉，必须上传店铺LOGO");
			}
			if(null==addressId || addressId.intValue()==0){
				throw new MyRefererException("抱歉，必须填写地址");
			}
			shop.setName(name);
			shop.setSmallName(smallName);
			shop.setAddressId(addressId);
			Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
			if(null!=a){
				shop.setAddressTreeIds(a.getTreeId());
			}
			shop.setSpecificAddress(specificAddress);
			shop.setLogo(logo);
			
			String info = WebFormatter.html2text(content);
			if(null==info){
				info="";
			}
			int lenInfo = info.length();
			if(lenInfo>99){
				info=info.substring(0,98);
			}
			shop.setInfo(info);
			aBiz.modifyObject(shop);
			req.getSession().setAttribute("myShop", shop);
			
			
			ShopContent sc = (ShopContent)aBiz.findObjectById("from ShopContent as sc where sc.shopId=?", new Object[]{shop.getId()});
			if(null!=sc){
				String contentOld = sc.getContent();
				if(null==content){
					content="";
				}
				if(!content.equals(contentOld)){
					sc.setContent(content);
					aBiz.modifyObject(sc);
				}
			}
			/**
			 * 生成二维码
			 */
			/*TwoDimensionalCodeSwetakeQRCodeUtil.createImg(PathUtil.getPhysicsPath()+"/img/shop/shop2weima"+shop.getId()+".png",
					PathUtil.getBasePath(req)+"/wrap/fgshop/id"+shop.getId()+"-auctions-s-o-pager1.htm");*/
			
			//wrap/fgshop/id${s.id}-auctions-s-o-pager1.htm
			
		}
		return redirect;
		
	}

	@RequestMapping(value={"/wrap/shop/shop/modify"})
	public void modifyWrap( HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		String smallName = form.getString("smallName");
		Integer addressId = form.getInteger("addressId");
		String specificAddress = form.getString("specificAddress");
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			if(null==name || name.equals("")){
				throw new MyStrException("抱歉，必须填写店铺名称");
			}
			if(null==smallName || smallName.equals("")){
				throw new MyStrException("抱歉，必须填写店铺短名称");
			}
			
			if(null==addressId || addressId.intValue()==0){
				throw new MyStrException("抱歉，必须填写地址");
			}
			shop.setName(name);
			shop.setSmallName(smallName);
			shop.setAddressId(addressId);
			Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
			if(null!=a){
				shop.setAddressTreeIds(a.getTreeId());
			}
			shop.setSpecificAddress(specificAddress);
			aBiz.modifyObject(shop);
			req.getSession().setAttribute("myShop", shop);
		}
		
		
	}
	
	
	
	@RequestMapping(value={"/toShopLogin","wrap/toShopLogin"})
	public String toLogin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			InitConfig initConfig = myFrameU.shop.init.InitMavenImpl.ic;
			req.getSession().setAttribute("myShop", shop);
			String className = initConfig.getAfterClass();
			HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
			String method=afterMethodMap.get("afterLogin");
			if(null!=className && !className.equals("") && null!=method && !method.equals("")){
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod(method,Shop.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
				m.invoke(c.newInstance(),shop, req,uICacheManager,aBiz);
			}
			if(isWrap(req)){
				return "wrap/manage/shop/index";
			}else{
				return "manage/shop/index";
			}
		}
		if(isWrap(req)){
			return "wrap/fg/shopLogin";
		}else{
			return "manage/shop/login";
		}
		
	}
	
	
	
	@RequestMapping(value={"/shopLogin","wrap/shopLogin"})
	public String shopLogin(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String name = form.getString("name");
		String password = form.getString("password");
		Shop shop1 = (Shop)req.getSession().getAttribute("myShop");
		if(null==shop1){
			if(null!=name && null!=password && !name.equals("") && !password.equals("")){
				ShopUser su = (ShopUser)aBiz.findObjectById("from ShopUser as su where su.name=?", new Object[]{name});
				if(null!=su){
					String dbPWD=su.getPassword();
					if(dbPWD.equals(password)){
						//登录成功 
						int shopId=su.getShopId();
						if(shopId!=0){
							Shop shop = (Shop)aBiz.findObjectById("from Shop as s where s.id=?", new Object[]{shopId});
							if(null!=shop){
								String status = shop.getStatus();
								if(EnumUtil.equalsE(Shop.STATUS.FROZEN, status)){
									throw new MyRefererException("该店铺被冻结");
								}else if(EnumUtil.equalsE(Shop.STATUS.OK, status) || EnumUtil.equalsE(Shop.STATUS.WAIT, status)){
									
									InitConfig initConfig = myFrameU.shop.init.InitMavenImpl.ic;
									req.getSession().setAttribute("myShop", shop);
									String className = initConfig.getAfterClass();
									HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
									String method=afterMethodMap.get("afterLogin");
									if(null!=className && !className.equals("") && null!=method && !method.equals("")){
										Class c = Class.forName(className);
										Method m = c.getDeclaredMethod(method,Shop.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
										m.invoke(c.newInstance(),shop, req,uICacheManager,aBiz);
									}
									
									HttpClientUtil.get(PathUtil.getBasePath(req)+"history/add?roleClassName=myFrameU.shop.entity.Shop&roleId="+shop.getId()+"&roleName="+shop.getName(), null);
									if(isWrap(req)){
										return "redirect:/wrap/shop/index";
									}else{
										return "redirect:/shop/index";
									}
								}
							}
						}
					}else{
						throw new MyRefererException("密码错误");	
					}
				}else{
					throw new MyRefererException("账号不存在");	
				}
			}else{
				throw new MyRefererException("请输入账户密码");
			}
		}else{
			if(isWrap(req)){
				return "redirect:/wrap/shop/index";
			}else{
				return "redirect:/shop/index";
			}
		}
		if(isWrap(req)){
			return "wrap/fg/shopLogin";
		}else{
			return "manage/shop/login";
		}
	}
	
	
	
	@RequestMapping(value={"shop/logout","wrap/shop/logout"})
	public String logout(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		req.getSession().removeAttribute("myShop");
		if(isWrap(req)){
			return "wrap/fg/shopLogin";
		}else{
			return "manage/shop/login";
		}
		
	}
	
	
	
	@RequestMapping(value={"/shop/index","wrap/shop/index"})
	public String index(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			String status = shop.getStatus();
			InitConfig initConfig = myFrameU.shop.init.InitMavenImpl.ic;
			req.getSession().setAttribute("myShop", shop);
			String className = initConfig.getAfterClass();
			HashMap<String,String> afterMethodMap = initConfig.getAfterMethodMap();
			String method=afterMethodMap.get("afterLogin");
			if(null!=className && !className.equals("") && null!=method && !method.equals("")){
				Class c = Class.forName(className);
				Method m = c.getDeclaredMethod(method,Shop.class, HttpServletRequest.class,UICacheManager.class,AbstractBizI.class); 
				m.invoke(c.newInstance(),shop, req,uICacheManager,aBiz);
			}
		}
		if(isWrap(req)){
			return "wrap/manage/shop/index";
		}else{
			return "manage/shop/index";
		}
	}
	//=============================================================================================================================
	@RequestMapping(value={"shop/myShop","wrap/shop/myShop"})
	public String findMyShop(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		req.setAttribute("myShop", shop);
		if(null!=shop){
			Address add = (Address)CacheKey.CKAddress.ALLMAP.getObject(uICacheManager,shop.getAddressId());
			req.setAttribute("currentAddress", add);
			
			
			List<ProductType> productTypeList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
			req.setAttribute("productTypeList", productTypeList);
			List<ShopLevel> shopLevelList = CacheKey.CKShopLevel.ALLMAP.getList(uICacheManager);
			req.setAttribute("shopLevelList", shopLevelList);
			
			ShopContent sc = (ShopContent)aBiz.findObjectById("from ShopContent as sc where sc.shopId=?", new Object[]{shop.getId()});
			req.setAttribute("shopContent", sc);
		}
		if(isWrap(req)){
			return "wrap/manage/shop/shop/shop";
		}else{
			return "manage/shop/shop/shop";
		}
		
	}
	
	@RequestMapping(value={"shop/myShopLevel","wrap/shop/myShopLevel"})
	public String findMyShopLevel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			List<ShopLevel> shopLevelList = CacheKey.CKShopLevel.ALLMAP.getList(uICacheManager);
			req.setAttribute("shopLevelList", shopLevelList);
			int level=shop.getShopLevelId();
			ShopLevel sl = CacheKey.CKShopLevel.ALLMAP.getObject(uICacheManager, level);
			req.setAttribute("currentLevel", sl);
			
			Global g3=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 3);
			Global g4=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 4);
			Global g9=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 9);
			Global g11=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 11);
			req.setAttribute("g3", g3);
			req.setAttribute("g4", g4);
			req.setAttribute("g9", g9);
			req.setAttribute("g11", g11);
		}
		if(isWrap(req)){
			return "wrap/manage/shop/shop/shopLevel";
		}else{
			return "manage/shop/shop/shopLevel";
		}
		
	}
	
	
	@RequestMapping(value={"shop/myMainPts","wrap/shop/myMainPts"})
	public String findMyMainPts(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		req.setAttribute("myShop", shop);
		ShopLevel sl = CacheKey.CKShopLevel.ALLMAP.getObject(uICacheManager, shop.getShopLevelId());
		req.setAttribute("shopLevel", sl);
		List<ProductType> productTypeList = CacheKey.CKProductType.ALLMAP.getList(uICacheManager);
		req.setAttribute("productTypeList", productTypeList);
		
		if(isWrap(req)){
			return "wrap/manage/shop/shop/mainProductType";
		}else{
			return "manage/shop/shop/mainProductType";
		}
		
	}
	
	//==========================================================================================================
	@RequestMapping(value="shop/security")
	public String security(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		req.setAttribute("myShop", shop);
		
		/**
		 * 1、shopUser的密码，肯定设置了，这里就不用查询了
		 * 2、手机号绑定
		 * 3、支付宝绑定
		 * 4、支付密码设置
		 */
		Account a = (Account)aBiz.findObjectById("from Account as a where a.whoclassName=? and a.whoId=?", new Object[]{Shop.class.getName(),shop.getId()});
		req.setAttribute("account", a);
		return "manage/shop/security/index";
	}
	@RequestMapping(value={"shop/security/toPassword","wrap/shop/security/toPassword"})
	public String security2Password(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			int shopUserId = shop.getShopUserId();
			if(shopUserId!=0){
				ShopUser su = (ShopUser)aBiz.findObjectById("from ShopUser as su ", new Object[]{});
				req.setAttribute("shopUser", su);
			}
		}
		req.setAttribute("myShop", shop);
		if(isWrap(req)){
			return "wrap/manage/shop/security/password";
		}else{
			return "manage/shop/security/password";
		}
		
	}
	@RequestMapping(value={"shop/security/password","wrap/shop/security/password"})
	public void securityModifyPassword(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		String password = form.getString("password");
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			PasswordUtil.verPassword(password);
			myFrameU.sms.util.Util.verYZM(req);
			int shopUserId = shop.getShopUserId();
			if(shopUserId!=0){
				ShopUser su = (ShopUser)aBiz.findObjectById("from ShopUser as su ", new Object[]{});
				su.setPassword(password);
				aBiz.modifyObject(su);
				req.getSession().setAttribute("myShopUser", su);
			}
		}
	}
	@RequestMapping(value={"shop/security/toTelphone","wrap/shop/security/toTelphone"})
	public String security2Telphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		req.setAttribute("myShop", shop);
		if(isWrap(req)){
			return "wrap/manage/shop/security/telPhone";
		}else{
			return "manage/shop/security/telPhone";
		}
		
	}
	@RequestMapping(value={"shop/security/telphone","wrap/shop/security/telphone"})
	public void securityModifyTelphone(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = getSDynaActionForm(req);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		String telPhone = form.getString("telPhones");
		String verTEL=PhoneUtil.vailterTelPhone(telPhone);
		if(null==verTEL){
			myFrameU.sms.util.Util.verYZM(req);
			
			int phoneShopCount_int = 0;
			BigInteger phoneShopCount = (BigInteger)aBiz.j_queryObject("select count(id) from shop as s where s.phone=?", new Object[]{telPhone});
			if(null!=phoneShopCount){
				phoneShopCount_int=phoneShopCount.intValue();
			}
			if(phoneShopCount_int!=0){
				throw new MyStrException("抱歉，该手机已经注册");
			}
			
			shop.setPhone(telPhone);
			aBiz.modifyObject(shop);
			req.getSession().setAttribute("myShop", shop);
		}else{
			throw new MyStrException(verTEL);
		}
	}
	
	//==========================================================================================
	@RequestMapping(value={"shop/toOpen","wrap/shop/toOpen"})
	public String toOpen(HttpServletRequest req,HttpServletResponse res) throws Exception{
		/*SDynaActionForm form = getSDynaActionForm(req);
		Global g12=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 12);
		req.setAttribute("g12", g12);
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		req.setAttribute("myShop", shop);
		
		//查找有没有申请过，结果如何
		//最后一个
		HashMap<String,String> argsMap = new HashMap<String,String>();
		argsMap.put("accountId", ".*");
		argsMap.put("accountItemId", ".*");
		String extraData = ApplyConstant.getExtraData(ApplyConstant.APPLYTYPEKEY.SHOPSTATUSWAIT2OK.toString(), argsMap);
		Apply apply = ApplyUtil.getLastApply(Shop.class.getName(), shop.getId(), extraData, ApplyConstant.APPLYTYPEKEY.SHOPSTATUSWAIT2OK.toString(), shopBizI);
		req.setAttribute("apply", apply);
		
		if(isWrap(req)){
			return "wrap/manage/shop/shop/open";
		}else{
			return "manage/shop/shop/open";
		}*/
		return null;
		
	}
	//========================================================================================================
		@RequestMapping(value="admin/shopPtVip")
		public String shopPtVip(HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			Integer id=form.getInteger("id");
			if(null!=id){
				Shop shop = (Shop)aBiz.findObjectById("from Shop as s where s.id=?", new Object[]{id});
				if(null!=shop){
					
				}
			}
			return "manage/shop/shop/open";
		}
	
		
	//=============================================================================================================================
		@RequestMapping(value={"toShopRegist","wrap/toShopRegist"})
		public String toShopRegist( HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			Address currentAddress=CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, 165);
			req.setAttribute("currentAddress", currentAddress);
			
			if(isWrap(req)){
				return "wrap/fg/shopRegist";
			}else{
				return "pcjsp/shopRegist";
			}
			
		}
		
		
		@RequestMapping(value={"/shopRegist_step_1"})
		public void shopRegist_step_1( HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			//String redirect = form.getString(CommonField.redirect);
			String name = form.getString("name");
			//String smallName = form.getString("smallName");
			//String logo = form.getString("logo");
			String phone = form.getString("phone");
			//String[] mainProductTypeIds = req.getParameterValues("mainProductTypeIds");
			Integer addressId = form.getInteger("addressId");
			//String specificAddress = form.getString("specificAddress");
			
			String shopUserName=form.getString("shopUserName");
			String shopUserPassword = form.getString("shopUserPassword");
			
			if(null==name || name.equals("")){
				throw new MyStrException("抱歉，必须填写店铺名称");
			}
			/*if(null==smallName || smallName.equals("")){
				throw new MyStrException("抱歉，必须填写店铺短名称");
			}*/
			/*if(null==logo || logo.equals("")){
				throw new MyRefererException("抱歉，必须上传店铺LOGO");
			}*/
			if(null==phone || phone.equals("")){
				throw new MyStrException("抱歉，必须填写电话");
			}else{
				String result = PhoneUtil.vailterTelPhone(phone);
				if(null!=result){
					throw new MyStrException(result);
				}else{
					int phoneShopCount_int = 0;
					BigInteger phoneShopCount = (BigInteger)aBiz.j_queryObject("select count(id) from shop as s where s.phone=?", new Object[]{phone});
					if(null!=phoneShopCount){
						phoneShopCount_int=phoneShopCount.intValue();
					}
					if(phoneShopCount_int!=0){
						throw new MyStrException("抱歉，该手机已经注册");
					}
				}
			}
			
			if(null==addressId || addressId.intValue()==0){
				throw new MyStrException("抱歉，必须填写地址");
			}
			
			if(null==shopUserName || shopUserName.equals("")){
				throw new MyStrException("抱歉，必须填写店铺登录账号");
			}else{
				int len = shopUserName.length();
				if(len>=6){
					boolean b = TextUtil.isEnglish(shopUserName);
					if(!b){
						throw new MyStrException("登录账号必须为英文字符");
					}
				}else{
					throw new MyStrException("登录账号必须大于等于6位");
				}
			}
			if(null==shopUserPassword || shopUserPassword.equals("")){
				throw new MyStrException("抱歉，必须填写店铺登录密码");
			}else{
				PasswordUtil.verPassword(shopUserPassword);
			}
			
			Shop shop = new Shop();
			shop.setSpecificAddress("");
			shop.setName(name);
			shop.setSmallName(name);
			shop.setAddressId(addressId);
			Address a = CacheKey.CKAddress.ALLMAP.getObject(uICacheManager, addressId);
			if(null!=a){
				shop.setAddressTreeIds(a.getTreeId());
			}
			shop.setCertiStatus(Shop.CERTISTATUS.WAIT.toString());
			shop.setClinchCount(0);
			shop.setClinchPrice(0);
			shop.setGrade(0);
			shop.setLogo("img/shop/shopLogo.jpg");
			shop.setInfo("");
			
			shop.setShopLevelId(2);//默认的是普通shop
			
			shop.setPhone(phone);
			shop.setProductCount(0);
			shop.setRuzhuDate(new Date());
			
			
			
			shop.setShoucangCount(0);
			shop.setSpecialCount(0);
			Global g12=CacheKey.CKGolbal.ALLMAP.getObjectTree(uICacheManager, 12);
			if(null!=g12){
				String g12v=g12.getMyValue();
				float shopRegistPrice = new Float(g12v).intValue();
				if(shopRegistPrice==0){
					shop.setStatus(Shop.STATUS.OK.toString());//
				}else{
					shop.setStatus(Shop.STATUS.WAIT.toString());
				}
			}
			shop.setTotalScore(0);
			
			ShopUser suold = (ShopUser)aBiz.findObjectById("from ShopUser as su where su.name=?", new Object[]{shopUserName});
			if(null!=suold){
				throw new MyStrException("抱歉，该登录账号已经存在");
			}
			
			ShopUser su = new ShopUser();
			su.setName(shopUserName);
			su.setPassword(shopUserPassword);
			
			
			req.getSession().setAttribute("registShop", shop);
			req.getSession().setAttribute("registShopUser", su);
			//shopBizI.registShop(shop, su);
			
			/**
			 * 生成二维码
			 */
			TwoDimensionalCodeSwetakeQRCodeUtil.createImg(PathUtil.getPhysicsPath()+"/img/shop/shop2weima"+shop.getId()+".png",
					PathUtil.getBasePath(req)+"/shop"+shop.getId()+"/");
			
			//return "pcjsp/shopRegist2";
		}
		@RequestMapping(value={"toShopRegist_step_2"})
		public String toShopRegist_step_2(HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			return "pcjsp/shopRegist2";
		}
		
		@RequestMapping(value={"shopRegist_step_2"})
		public String shopRegist_step_2(HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			Shop shop=(Shop)req.getSession().getAttribute("registShop");
			ShopUser shopUser=(ShopUser)req.getSession().getAttribute("registShopUser");
			if(null!=shop && null!=shopUser){
				myFrameU.sms.util.Util.verYZM(req);
				shopBizI.registShop(shop, shopUser);
				
				req.getSession().removeAttribute("registShop");
				req.getSession().removeAttribute("registShopUser");
				
				req.getSession().setAttribute("myShop", shop);
				req.getSession().setAttribute("myShopUser", shopUser);
			}else{
			}
			return "pcjsp/shopRegist3";
		}
		
		
		//=======================================
		@RequestMapping(value={"wrap/shop/shopInfoIndex"})
		public String shopInfoIndex(HttpServletRequest req,HttpServletResponse res) throws Exception{
			SDynaActionForm form = getSDynaActionForm(req);
			Shop shop = (Shop)req.getSession().getAttribute("myShop");
			return "wrap/manage/shop/shop/index";
		}
	
}
