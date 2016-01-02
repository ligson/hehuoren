package myFrameU.adv.init;

import java.util.List;

import myFrameU.adv.entity.AdvertingPage;
import myFrameU.adv.entity.Advertisement;
import myFrameU.adv.entity.Advertising;



public class InitConfig{
	private Class initMavenClass;
	
	private String pageInsert;
	private List<AdvertingPage> pageList;
	
	
	private String advertingInsert;
	private List<Advertising> aList;
	
	
	private String advermentInsert;
	private List<Advertisement> advertisementList;
	
	
	public Class getInitMavenClass() {
		return initMavenClass;
	}
	public void setInitMavenClass(Class initMavenClass) {
		this.initMavenClass = initMavenClass;
	}
	public String getPageInsert() {
		return pageInsert;
	}
	public void setPageInsert(String pageInsert) {
		this.pageInsert = pageInsert;
	}
	public List<AdvertingPage> getPageList() {
		return pageList;
	}
	public void setPageList(List<AdvertingPage> pageList) {
		this.pageList = pageList;
	}
	public String getAdvertingInsert() {
		return advertingInsert;
	}
	public void setAdvertingInsert(String advertingInsert) {
		this.advertingInsert = advertingInsert;
	}
	public List<Advertising> getaList() {
		return aList;
	}
	public void setaList(List<Advertising> aList) {
		this.aList = aList;
	}
	public String getAdvermentInsert() {
		return advermentInsert;
	}
	public void setAdvermentInsert(String advermentInsert) {
		this.advermentInsert = advermentInsert;
	}
	public List<Advertisement> getAdvertisementList() {
		return advertisementList;
	}
	public void setAdvertisementList(List<Advertisement> advertisementList) {
		this.advertisementList = advertisementList;
	}
	
	
	
}
