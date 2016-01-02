package myFrameU.address.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import myFrameU.tree.entity.AbstractTreeEntity;


public class Address extends AbstractTreeEntity implements Serializable{
	public Address(){}
	private String firstZm;
	private int diqu;//地区。1：东北地区。2华北地区。3华东地区。4华南地区。5华中。6西北。7西南。8青藏
	
	
	private int isOpen;//1代表已经开通了，0代表已经没有开通
	private int isHot;
	
	public Address(int id){
		super.id=id;
	}
	
	private Set<Address> childs=new HashSet<Address>();

	public Set<Address> getChilds() {
		return childs;
	}


	public void setChilds(Set<Address> childs) {
		this.childs = childs;
	}


	public String getFirstZm() {
		return firstZm;
	}


	public void setFirstZm(String firstZm) {
		this.firstZm = firstZm;
	}


	public int getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}


	public int getDiqu() {
		return diqu;
	}


	public void setDiqu(int diqu) {
		this.diqu = diqu;
	}


	public int getIsHot() {
		return isHot;
	}


	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}


	
	
	
	
	
	
	
	
	
}
