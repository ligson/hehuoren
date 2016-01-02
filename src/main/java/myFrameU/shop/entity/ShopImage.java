package myFrameU.shop.entity;

import java.io.Serializable;

/**
 * 
 * 图片空间
 *
 */
public class ShopImage   implements Serializable{
	private int id;
	private String name;
	private String image;
	private int shopId;
	
	private String imageType;//shopbanner,shop焦点轮播图，shop的product主图，product的简介图，shop的news图，shop的资质图
	public enum IMAGETYPE{SHOPBANNER,SHOPFOCUS,PRODUCTMAIN,PRODUCTCONTENT,SHOPNEWS,SHOPZIZHI};

	private int cy;//常用标签,1,0
	
	private String width;
	private String height;
	
	private String noInsert_qualifiedMessage;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}




	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}


	public String getNoInsert_qualifiedMessage() {
		return noInsert_qualifiedMessage;
	}

	public void setNoInsert_qualifiedMessage(String noInsertQualifiedMessage) {
		noInsert_qualifiedMessage = noInsertQualifiedMessage;
	}
	
}
