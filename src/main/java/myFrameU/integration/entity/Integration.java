package myFrameU.integration.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * 积分
 *		最简易的积分系统
 *		不可花销、不可转换、只有一种积分。
 */
public class Integration implements Serializable{
	private int id;
	private Set<IntegrationItem> inteItemSet;
	

	private float total;//
	
	
	private String who;//myFrame.....Admin
	private int whoId;//adminId
	private int addressId;
	private String addressTreeIds;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<IntegrationItem> getInteItemSet() {
		return inteItemSet;
	}
	public void setInteItemSet(Set<IntegrationItem> inteItemSet) {
		this.inteItemSet = inteItemSet;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public int getWhoId() {
		return whoId;
	}
	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddressTreeIds() {
		return addressTreeIds;
	}
	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	
	
	
	
}
