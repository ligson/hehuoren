package myFrameU.product.entity;

import java.io.Serializable;

public class ProductTesefuwu   implements Serializable{
	private int id;
	private String name;
	
	private String tsfwkey;
	
	private boolean addProductMust;//在添加产品的时候是否要必须填写的
	
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
	public boolean isAddProductMust() {
		return addProductMust;
	}
	public void setAddProductMust(boolean addProductMust) {
		this.addProductMust = addProductMust;
	}
	public String getTsfwkey() {
		return tsfwkey;
	}
	public void setTsfwkey(String tsfwkey) {
		this.tsfwkey = tsfwkey;
	}

	
}
