package myFrameU.weixin.base.entity;

public class WXUserListEntity {
	/**
	 * {
				  "total":23000,
				  "count":10000,
				  "data":{"
				     openid":[
				        "OPENID1",
				        "OPENID2",
				        ...,
				        "OPENID10000"
				     ]
				   },
				   "next_openid":"NEXT_OPENID1"
				}
	 */
	private int total;
	private int count;
	private String data;
	private String next_openid;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	
}
