package com.qixingjun.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.slf4j.*;
import com.qixingjun.menu.Button;
import com.qixingjun.menu.Menu;
import com.qixingjun.menu.ViewButton;
import com.qixingjun.pojo.AccessToken;
import com.qixingjun.pojo.WeiXinOauth2Token;
import com.qixingjun.pojo.WeixinUserInfo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/** 
 * 公众平台通用接口工具类 
 */  
public class WeixinUtil {  
	
	private static final String CREATE_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//private static final String APPID="wx7948cfb26b000432";
	//private static final String APPSECRET="2367d444a53519a9d375fe67fcb89a6c";
	private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String OPENID_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	private static final String APPID="wx06eaf5668141d249";//test
	private static final String APPSECRET="efafc6e11f9ed931065859548ec58e75";//test
	private static final String USERINFO_URL="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static final String OAYTH_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);  
  
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
    
    public static Menu initMenu(){
    	Menu menu = new Menu();
//    	ClickButton clickButton = new ClickButton();
//    	clickButton.setName("Click菜单");
//    	clickButton.setType("click");
//    	clickButton.setKey("1");
    	
//    	ViewButton viewButton = new ViewButton();
//    	viewButton.setName("查看我的信息");
//    	viewButton.setType("view");
//    	viewButton.setUrl("http://ustckingke.duapp.com/ustckingke/UserInfoServlet");
//    	viewButton.setUrl("/UserInfoServlet");
    	
    	ViewButton viewButton = new ViewButton();
    	viewButton.setName("查看我的信息");
    	viewButton.setType("view");
    	viewButton.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=http%3A%2F%2Fustckingke.duapp.com%2Fustckingke%2FUserInfoServlet&response_type=code&scope=snsapi_base&state=1#wechat_redirect".replace("APPID", APPID));
    	
//    	Button button = new Button();
//    	button.setName("查看我的信息");
    	menu.setButton(new Button[]{viewButton});
    	return menu;
    }
    
    public static int createMenu(String token,String menu){
    	int result = 0;
    	String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
    	String jsonMenu = JSONObject.fromObject(menu).toString(); 
    	JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
    	System.out.println("jsonObject:"+jsonObject);
    	 if (null != jsonObject) {  
    	        if (0 != jsonObject.getInt("errcode")) {  
    	            result = jsonObject.getInt("errcode");  
    	            System.out.println("创建菜单失败 errcode:{} errmsg:{}"+ jsonObject.getInt("errcode")+ jsonObject.getString("errmsg"));  
    	        }  
    	    }  
    	System.out.println("result123456789:"+result);
    	return result;
    }
    
    public static AccessToken getAccessToken(){
    	AccessToken accessToken = new AccessToken();
    	String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
    	JSONObject jsonObject = httpRequest(url, "GET", null);
    	// 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                System.out.println("获取token失败 errcode:{} errmsg:{}"+jsonObject.getInt("errcode")+ jsonObject.getString("errmsg"));  
            }  
        }  
    	return accessToken;
    }
    
//    public static String getUserOpenId(String accessToken){  
//    	String result = null;  
//        String requestUrl = OPENID_URL.replace("ACCESS_TOKEN", accessToken);  
//        JSONObject jsonObject = WeixinUtil.httpRequest(requestUrl, "GET", null);  
//        if(null != jsonObject){  
//            System.out.println(jsonObject);  
//            System.out.println(jsonObject.get("total"));  
//            System.out.println(jsonObject.get("data"));  
//            result = jsonObject.get("data")+"";  
//        }  
//        System.out.println("resultttttt:"+result);
//        return (String) jsonObject.get("next_openid");
//        //return result;
//    }  
    public static WeiXinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeiXinOauth2Token wat = new WeiXinOauth2Token();
        String requestUrl = OAYTH_URL.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
                try {
                        wat = new WeiXinOauth2Token();
                        wat.setAccessToken(jsonObject.getString("access_token"));
                        wat.setExpiresIn(jsonObject.getInt("expires_in"));
                        wat.setRefeshToken(jsonObject.getString("refresh_token"));
                        wat.setOpenId(jsonObject.getString("openid"));
                        wat.setScope(jsonObject.getString("scope"));
                } catch (Exception e) {
                        wat = null;
                        String errorCode = jsonObject.getString("errcode");
                        String errorMsg = jsonObject.getString("errmsg");
                        log.error("获取网页授权凭证失败 errcode{},errMsg", errorCode, errorMsg);
                }

        }
        return wat;
}
    
    
    public static WeixinUserInfo getUserInfo(String accessToken, WeiXinOauth2Token weiXinOauth2Token) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", weiXinOauth2Token.getOpenId());
        // 获取用户信息
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        
        System.out.println("requestUrl:"+requestUrl);
        System.out.println("openId:"+weiXinOauth2Token.getOpenId());
        System.out.println("jsonObject:"+jsonObject);
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    System.out.println("用户{}已取消关注"+ weixinUserInfo.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    System.out.println("获取用户信息失败 errcode:{} errmsg:{}"+ errorCode+errorMsg);
                }
            }
        }
        System.out.println(weixinUserInfo.toString());
        return weixinUserInfo;
    }
    
}  
