package myFrameU.weixin.base.entity;

//接收到到微信xml实体类
public class ReceiveXmlEntity{
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String MsgId;
	private String MsgID;
	private String Status;
	//以上五个属性是所有消息都有都
	
	//文本消息特有
	private String Content;//文本内容
	
	
	//图片消息特有
	private String PicUrl;
	
	//图片、语音、视频、小视频都有都
	private String MediaId;
	
	//语音特有都
	private String Format;
	
	//视频、小视频都
	private String ThumbMediaId;//视频消息缩略图的媒体id
	
	//地理位置特有的
	private String Location_x;
	private String Location_y;
	private String Scale;
	private String Label;
	
	//链接特有的
	private String Title;
	private String Description;
	private String Url;
	
	private String Encrypt;
	
	//事件消息特有的
	private String Event;//事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	private String EventKey;//qrscene_123123，事件KEY值，qrscene_为前缀，后面为二维码的参数值
	private String Ticket;//二维码的ticket，可用来换取二维码图片
	private String Latitude;
	private String Longitude;
	private String Precision;
	
	
	
	
	
	
	private String Recognition;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getLocation_x() {
		return Location_x;
	}
	public void setLocation_x(String location_x) {
		Location_x = location_x;
	}
	public String getLocation_y() {
		return Location_y;
	}
	public void setLocation_y(String location_y) {
		Location_y = location_y;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
	public String getMsgID() {
		return MsgID;
	}
	public void setMsgID(String msgID) {
		MsgID = msgID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getEncrypt() {
		return Encrypt;
	}
	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}
	
}
