package com.springmvc.login;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO 自动生成的方法存根
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO 自动生成的方法存根
		HttpSession session = event.getSession();  
        ServletContext application = session.getServletContext();  
        // 取得登录的用户名  
        String usercode = (String) session.getAttribute("usercode");  
        // 从在线列表中删除用户名  
        List onlineUserList = (List) application.getAttribute("onlineUserList");  
        onlineUserList.remove(usercode);  
        System.out.println(usercode + "超时退出。");
		
	}

}
