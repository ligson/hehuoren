package myFrameU.news.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import myFrameU.tree.entity.AbstractTreeEntity;


public class NewsType extends AbstractTreeEntity implements Serializable{
	public NewsType(){}
	private String pinyin;//拼音
	
	public NewsType(int id){
		super.id=id;
	}
	
	private Set<NewsType> childs=new HashSet<NewsType>();
	
	//private Set<News> newsSet;
	
	
	private String image1;
	private String image2;
	private String returnPage;

	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public Set<NewsType> getChilds() {
		return childs;
	}
	public void setChilds(Set<NewsType> childs) {
		this.childs = childs;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getReturnPage() {
		return returnPage;
	}
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	
	
	
}
