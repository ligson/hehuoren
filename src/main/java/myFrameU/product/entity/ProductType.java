package myFrameU.product.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import myFrameU.tree.entity.AbstractTreeEntity;

public class ProductType extends AbstractTreeEntity implements Serializable{
	
	public ProductType(int id,String name){
		super.id=id;
		super.name=name;
	}
	
	
	
	private String fm;//首页的分类下拉每个大类前面的小图标
	private String smallImg;//栏目页面中的图片
	private String proTypeInfo;//类型简介
	

	private double webTicheng;//每种分类都不一样都提成。
	
	
	
	
	private Set<ProductType> childs=new HashSet<ProductType>();
	

	
	//======================================================
	private String noSave_shopMainPt;
	public enum NOSAVE_SHOPMAINPT{YES,NO};
	
	public Set<ProductType> getChilds() {
		return childs;
	}

	public void setChilds(Set<ProductType> childs) {
		this.childs = childs;
	}


	public String getProTypeInfo() {
		return proTypeInfo;
	}

	public void setProTypeInfo(String proTypeInfo) {
		this.proTypeInfo = proTypeInfo;
	}
	public ProductType(){}
	public ProductType(int id){
		super.id=id;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public String getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}

	public double getWebTicheng() {
		return webTicheng;
	}

	public void setWebTicheng(double webTicheng) {
		this.webTicheng = webTicheng;
	}

	public String getNoSave_shopMainPt() {
		return noSave_shopMainPt;
	}

	public void setNoSave_shopMainPt(String noSave_shopMainPt) {
		this.noSave_shopMainPt = noSave_shopMainPt;
	}

	
}
