package myFrameU.sincerity.entity;
/**
 * 
 * 诚信档案
 *
 */
public class SincerityArchives {
	private int id;
	private String whoclassName;
	private String whopName;
	private int whoId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWhoclassName() {
		return whoclassName;
	}
	public void setWhoclassName(String whoclassName) {
		this.whoclassName = whoclassName;
	}
	public String getWhopName() {
		return whopName;
	}
	public void setWhopName(String whopName) {
		this.whopName = whopName;
	}
	public int getWhoId() {
		return whoId;
	}
	public void setWhoId(int whoId) {
		this.whoId = whoId;
	}
	
	private float score;	
	private int sincerityLevelId;
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getSincerityLevelId() {
		return sincerityLevelId;
	}
	public void setSincerityLevelId(int sincerityLevelId) {
		this.sincerityLevelId = sincerityLevelId;
	}	
		
}
