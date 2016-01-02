package myFrameU.product.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductPricePropertyValue implements Serializable{
	private int id;
	private int productId;
	private String keyPy;
	private String keyName;
	private String keyValues;//,,,,,逗号隔开
	
	//======================
	private List<String> noSave_keyValues;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getKeyValues() {
		return keyValues;
	}
	public void setKeyValues(String keyValues) {
		this.keyValues = keyValues;
	}
	public String getKeyPy() {
		return keyPy;
	}
	public void setKeyPy(String keyPy) {
		this.keyPy = keyPy;
	}
	public List<String> getNoSave_keyValues() {
		return noSave_keyValues;
	}
	public void setNoSave_keyValues(List<String> noSave_keyValues) {
		this.noSave_keyValues = noSave_keyValues;
	}
	
	
	
	
}
