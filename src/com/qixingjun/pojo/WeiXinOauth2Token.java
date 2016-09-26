package com.qixingjun.pojo;

public class WeiXinOauth2Token {
	private String accessToken;
	private int expiresIn;
	private String refeshToken;
	private String openId;
	private String scope;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefeshToken() {
		return refeshToken;
	}
	public void setRefeshToken(String refeshToken) {
		this.refeshToken = refeshToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
