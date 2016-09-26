package com.qixingjun.test;

import com.qixingjun.pojo.AccessToken;
import com.qixingjun.pojo.WeixinUserInfo;
import com.qixingjun.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WeixinTest {
	
	public static void main(String[] args) {
		AccessToken accessToken = WeixinUtil.getAccessToken();
		
		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(accessToken.getToken(), menu);
		if (result==0) {
			System.out.println("cg");
		}else {
			System.out.println("shibai+"+result);
		}
		
//		System.out.println("WeixinUtil.getUserOpenId(accessToken.getToken())"+WeixinUtil.getOauth2AccessToken(appId, appSecret, code));
//		
//		WeixinUserInfo weixinUserInfo = WeixinUtil.getUserInfo(accessToken.getToken(), WeixinUtil.getUserOpenId(accessToken.getToken()));
//		System.out.println("username:"+weixinUserInfo.getNickname());
//		System.out.println("city:"+weixinUserInfo.getCity());
//		System.out.println("touxiang:"+weixinUserInfo.getHeadImgUrl());
	}
}
