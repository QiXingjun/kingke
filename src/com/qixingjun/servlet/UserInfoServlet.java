package com.qixingjun.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qixingjun.pojo.AccessToken;
import com.qixingjun.pojo.WeixinUserInfo;
import com.qixingjun.util.CheckUtil;
import com.qixingjun.util.WeixinUtil;


@WebServlet("/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AccessToken accessToken = WeixinUtil.getAccessToken();
		String code = request.getParameter("code");
		WeixinUserInfo weixinUserInfo = WeixinUtil.getUserInfo(accessToken.getToken(), WeixinUtil.getOauth2AccessToken("wx06eaf5668141d249", "efafc6e11f9ed931065859548ec58e75", code));
		System.out.println("weixinUserInfo:"+weixinUserInfo);
		String username = weixinUserInfo.getNickname();
		String city = weixinUserInfo.getCity();
		String country = weixinUserInfo.getCountry();
		String touxiang = weixinUserInfo.getHeadImgUrl();
		
		request.setAttribute("username",username);
		request.setAttribute("city",city);
		request.setAttribute("country",country);
		request.setAttribute("touxiang",touxiang);
		
		request.getRequestDispatcher("/MyInfo.jsp").forward(request,response);
//		response.setContentType("text/html;charset=GB2312");
//		PrintWriter out = response.getWriter();
//		out.println("国家："+country+"     这是一个美丽的国家！");
//		out.println("<br/>");  
//		out.println("城市："+city+"     这是一个美丽的城市！");
//		out.println("<br/>");  
//		out.println("昵称："+username+"     这是一个美丽的昵称！");
//		out.println("<br/>");
//		out.println("头像："+"     这是一个美丽的头像！");
//		out.println("<br/>");
//		out.println("<img src="+touxiang+"><br>");  
	}
}
