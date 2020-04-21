package com.amdocs.media.assignement.authorization.dto;

public class ProfileDTO {

	private String address;
	private String mobile;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "ProfileDTO [address=" + address + ", mobile=" + mobile + "]";
	}

}
