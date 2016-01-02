package myFrameU.shop.entity;

import java.io.Serializable;

/**
 * 
 * 引入该类的目的:使用最简单的店铺简单装修,抛弃之前的装修系统
 *	包括：
 *		banner
 *		小轮播图
 *		大背景图
 *		底部背景色
 *		导航颜色
 *		主色调（专指title背景图或者背景色）
 */
public class ShopTemplateSimple  implements Serializable{
	private int id;
	private int shopId;
	private String bannerBackground;//百分之百的banner的底色或者背景图
	private String banner;//banner
	private String smallFocus1;//小轮播图
	private String smallFocus2;
	private String smallFocus3;
	private String smallFocus4;
	private String smallFocus5;
	
	
	private String bigBackground;
	private String footerBackgroundColor;
	
	
	private String menuBg;
	private String menuHoverBg;
	private String titleBg;
	private String titleHeight;
	private String titleFontColor;
	private String divBorderColor;
	private String divBorderWidth;
	
	private String css;
	
	public ShopTemplateSimple(){
		this.smallFocus1="img/shop/smallFocus1.jpg";
		this.smallFocus2="img/shop/smallFocus2.jpg";
		this.smallFocus3="img/shop/smallFocus3.jpg";
		this.smallFocus4="img/shop/smallFocus4.jpg";
		this.smallFocus5="img/shop/smallFocus5.jpg";
		
		this.banner="img/shop/banner.jpg";
		this.bannerBackground="img/shop/bannerBg.jpg";
		this.bigBackground="img/shop/defalutBigBk1.jpg";
		this.footerBackgroundColor="#222";
		this.menuBg="#88c248";
		this.menuHoverBg="#000000";
		//this.titleBg="everyDiv_title_outer_4.png";
		this.titleBg="#88c248";
		this.titleFontColor="#fff";
		this.titleHeight="35px";
		this.divBorderColor="#4bae36";
		this.divBorderWidth="1px ";
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getSmallFocus1() {
		return smallFocus1;
	}
	public void setSmallFocus1(String smallFocus1) {
		this.smallFocus1 = smallFocus1;
	}
	public String getSmallFocus2() {
		return smallFocus2;
	}
	public void setSmallFocus2(String smallFocus2) {
		this.smallFocus2 = smallFocus2;
	}
	public String getSmallFocus3() {
		return smallFocus3;
	}
	public void setSmallFocus3(String smallFocus3) {
		this.smallFocus3 = smallFocus3;
	}
	public String getSmallFocus4() {
		return smallFocus4;
	}
	public void setSmallFocus4(String smallFocus4) {
		this.smallFocus4 = smallFocus4;
	}
	public String getSmallFocus5() {
		return smallFocus5;
	}
	public void setSmallFocus5(String smallFocus5) {
		this.smallFocus5 = smallFocus5;
	}
	public String getBigBackground() {
		return bigBackground;
	}
	public void setBigBackground(String bigBackground) {
		this.bigBackground = bigBackground;
	}
	public String getFooterBackgroundColor() {
		return footerBackgroundColor;
	}
	public void setFooterBackgroundColor(String footerBackgroundColor) {
		this.footerBackgroundColor = footerBackgroundColor;
	}
	public String getBannerBackground() {
		return bannerBackground;
	}
	public void setBannerBackground(String bannerBackground) {
		this.bannerBackground = bannerBackground;
	}
	public String getMenuBg() {
		return menuBg;
	}
	public void setMenuBg(String menuBg) {
		this.menuBg = menuBg;
	}
	public String getMenuHoverBg() {
		return menuHoverBg;
	}
	public void setMenuHoverBg(String menuHoverBg) {
		this.menuHoverBg = menuHoverBg;
	}
	public String getTitleBg() {
		return titleBg;
	}
	public void setTitleBg(String titleBg) {
		this.titleBg = titleBg;
	}
	public String getTitleHeight() {
		return titleHeight;
	}
	public void setTitleHeight(String titleHeight) {
		this.titleHeight = titleHeight;
	}
	public String getTitleFontColor() {
		return titleFontColor;
	}
	public void setTitleFontColor(String titleFontColor) {
		this.titleFontColor = titleFontColor;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getDivBorderColor() {
		return divBorderColor;
	}
	public void setDivBorderColor(String divBorderColor) {
		this.divBorderColor = divBorderColor;
	}
	public String getDivBorderWidth() {
		return divBorderWidth;
	}
	public void setDivBorderWidth(String divBorderWidth) {
		this.divBorderWidth = divBorderWidth;
	}
	
}
