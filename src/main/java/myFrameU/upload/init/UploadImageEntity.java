package myFrameU.upload.init;

public class UploadImageEntity {
	private String saveType;//存储类型
	private String savePath;//存储路径
	private String savePathMin;//小图片存储路径
	
	private String getMinWay;//获取小图片的方式，只有narrow和cut
	private int minWidth;
	private int minHeight;
	
	
	
	private int maxWidth;
	private int maxHeight;
	
	private boolean water;
	private String waterImage;
	
	
	/*//返回方式，有三种，1）返回直接单个的图片（分为返回min还是big）  2）返回jsonArray，到了页面之后，用js去创建DOM树.3)返回ajax jspPage。
	private String returnType;//返回方式
	private String RT3returnPage;*/
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	
	public int getMaxWidth() {
		return maxWidth;
	}
	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
	}
	public int getMaxHeight() {
		return maxHeight;
	}
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	public boolean getWater() {
		return water;
	}
	public void setWater(boolean water) {
		this.water = water;
	}
	
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getSavePathMin() {
		return savePathMin;
	}
	public void setSavePathMin(String savePathMin) {
		this.savePathMin = savePathMin;
	}
	public String getWaterImage() {
		return waterImage;
	}
	public void setWaterImage(String waterImage) {
		this.waterImage = waterImage;
	}
	public String getGetMinWay() {
		return getMinWay;
	}
	public void setGetMinWay(String getMinWay) {
		this.getMinWay = getMinWay;
	}
	public int getMinWidth() {
		return minWidth;
	}
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}
	public int getMinHeight() {
		return minHeight;
	}
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}
	
	
	
	
	
	
}
