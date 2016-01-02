package myFrameU.sincerity.entity;



public class SincerityType {
	private int id;
	private String typeName;
	private String typeKey;
	private String addOrMis;//加还是减分。默认的是减，本系统只有减
	public enum ADDORMIS{ADD,MIS};
	
	private float score;//加/减的分数

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAddOrMis() {
		return addOrMis;
	}

	public void setAddOrMis(String addOrMis) {
		this.addOrMis = addOrMis;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}
	
	
	
	
}
