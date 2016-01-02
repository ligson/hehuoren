package myFrameU.entity;

import java.io.Serializable;

public class AddFatherEntity  implements Serializable{
	private int addressId;
	private String addressTreeIds;
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddressTreeIds() {
		return addressTreeIds;
	}
	public void setAddressTreeIds(String addressTreeIds) {
		this.addressTreeIds = addressTreeIds;
	}
	
}
