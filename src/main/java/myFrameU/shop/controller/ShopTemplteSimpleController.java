package myFrameU.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import myFrameU.shop.entity.Shop;
import myFrameU.shop.entity.ShopTemplateSimple;
import myFrameU.spring.mvc.FatherController;
import myFrameU.spring.mvc.SDynaActionForm;
@Controller
public class ShopTemplteSimpleController extends FatherController {
	@RequestMapping(value={"shop/temple/find"})
	public String findByShopId(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		//Integer shopId = form.getInteger("shopId");
		String templeKey=form.getString("templeKey");
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			int shopId = shop.getId();
			ShopTemplateSimple ss = (ShopTemplateSimple)aBiz.findObjectById("from ShopTemplateSimple as ss where ss.shopId=?", new Object[]{shopId});
			/*if(null==ss){
				ss=new ShopTemplateSimple();
				ss.setShopId(shop.getId());
				aBiz.addObject(ss);
			}*/
			req.setAttribute("shopTemplateSimple", ss);
		}
		
		if(null!=templeKey && !templeKey.equals("")){
			if(templeKey.equals("indexFocus")){
				return "manage/shop/temple/indexFocus";
			}else if(templeKey.equals("banner")){
				return "manage/shop/temple/banner";
			}else if(templeKey.equals("maincolor")){
				return "manage/shop/temple/maincolor";
			}
		}
		
		return "manage/shop/temple/";
	}
	
	
	
	@RequestMapping(value={"shop/temple/modfiy"})
	public void modify(HttpServletRequest req,HttpServletResponse res) throws Exception{
		SDynaActionForm form = this.getSDynaActionForm(req);
		//Integer shopId = form.getInteger("shopId");
		String templeKey=form.getString("templeKey");
		Shop shop = (Shop)req.getSession().getAttribute("myShop");
		if(null!=shop){
			int shopId = shop.getId();
			ShopTemplateSimple ss = (ShopTemplateSimple)aBiz.findObjectById("from ShopTemplateSimple as ss where ss.shopId=?", new Object[]{shopId});
			if(null!=ss){
				if(templeKey.equals("indexFocus")){
					String smallFocus1=form.getString("smallFocus1");
					String smallFocus2=form.getString("smallFocus2");
					String smallFocus3=form.getString("smallFocus3");
					String smallFocus4=form.getString("smallFocus4");
					String smallFocus5=form.getString("smallFocus5");
					if(null!=smallFocus1 && !smallFocus1.equals("")){
						ss.setSmallFocus1(smallFocus1);
					}
					if(null!=smallFocus2 && !smallFocus2.equals("")){
						ss.setSmallFocus2(smallFocus2);
					}
					if(null!=smallFocus3 && !smallFocus3.equals("")){
						ss.setSmallFocus3(smallFocus3);
					}
					if(null!=smallFocus4 && !smallFocus4.equals("")){
						ss.setSmallFocus4(smallFocus4);
					}
					if(null!=smallFocus5 && !smallFocus5.equals("")){
						ss.setSmallFocus5(smallFocus5);
					}
					aBiz.modifyObject(ss);
				}else if(templeKey.equals("banner")){
					String bannerBackground=form.getString("bannerBackground");
					String banner=form.getString("banner");
					if(null!=bannerBackground && !bannerBackground.equals("")){
						ss.setBannerBackground(bannerBackground);
					}
					if(null!=banner && !banner.equals("")){
						ss.setBanner(banner);
					}
					aBiz.modifyObject(ss);
				}else if(templeKey.equals("maincolor")){
					String bigBackground=form.getString("bigBackground");
					String footerBackgroundColor=form.getString("footerBackgroundColor");
					String menuBg=form.getString("menuBg");
					String menuHoverBg=form.getString("menuHoverBg");
					String titleBg=form.getString("titleBg");
					String titleHeight=form.getString("titleHeight");
					String titleFontColor=form.getString("titleFontColor");
					String divBorderColor=form.getString("divBorderColor");
					String divBorderWidth=form.getString("divBorderWidth");
					
					if(null!=bigBackground && !bigBackground.equals("")){
						ss.setBigBackground(bigBackground);
					}
					if(null!=footerBackgroundColor && !footerBackgroundColor.equals("")){
						ss.setFooterBackgroundColor(footerBackgroundColor);
					}
					if(null!=menuBg && !menuBg.equals("")){
						ss.setMenuBg(menuBg);
					}
					if(null!=menuHoverBg && !menuHoverBg.equals("")){
						ss.setMenuHoverBg(menuHoverBg);
					}
					if(null!=titleBg && !titleBg.equals("")){
						ss.setTitleBg(titleBg);
					}
					if(null!=titleHeight && !titleHeight.equals("")){
						ss.setTitleHeight(titleHeight);
					}
					if(null!=titleFontColor && !titleFontColor.equals("")){
						ss.setTitleFontColor(titleFontColor);
					}
					if(null!=divBorderColor && !divBorderColor.equals("")){
						ss.setDivBorderColor(divBorderColor);
					}
					if(null!=divBorderWidth && !divBorderWidth.equals("")){
						ss.setDivBorderWidth(divBorderWidth);
					}
					aBiz.modifyObject(ss);
					
				}
			}
			req.getSession().setAttribute("myShopTemplateSimple", ss);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
