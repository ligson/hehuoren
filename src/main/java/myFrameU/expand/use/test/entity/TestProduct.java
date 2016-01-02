package myFrameU.expand.use.test.entity;

import myFrameU.expand.use.entity.AbstractExpandEntity;

/**
 * 
 * 测试类
 *
 */
public class TestProduct extends AbstractExpandEntity{
	private int id;
	private String name;
	private int productTypeId;
	private String productTypeTreeIds;
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
	public int getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getProductTypeTreeIds() {
		return productTypeTreeIds;
	}
	public void setProductTypeTreeIds(String productTypeTreeIds) {
		this.productTypeTreeIds = productTypeTreeIds;
	}


	
}
