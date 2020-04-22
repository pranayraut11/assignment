package com.amdocs.media.assignement.profile.dto;

import java.io.Serializable;

public class ProfileDTO implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2898542330106772174L;
	private Integer userId;
	private String address;
	private String mobile;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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
