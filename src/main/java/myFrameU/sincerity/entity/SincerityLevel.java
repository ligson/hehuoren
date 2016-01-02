package myFrameU.sincerity.entity;



public class SincerityLevel {
	private int id;
	private String levelName;
	
	//不包含
	private float lowScore;
	//包含
	private float heighScore;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public float getLowScore() {
		return lowScore;
	}
	public void setLowScore(float lowScore) {
		this.lowScore = lowScore;
	}
	public float getHeighScore() {
		return heighScore;
	}
	public void setHeighScore(float heighScore) {
		this.heighScore = heighScore;
	}
	
	
	
}
