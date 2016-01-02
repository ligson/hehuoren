package myFrameU.adv.entity;

import java.io.Serializable;

public class AdvertingPage  implements Serializable{
	private int id;
	private String nameKey;
	private String name;
/*	
	
	private String haveSon;
	public enum HAVESON{YES,NO}
	private String sonBy;
	public enum SONBY{PRODUCTTYPE}
	
	*/
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
	public String getNameKey() {
		return nameKey;
	}
	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}
	
}
