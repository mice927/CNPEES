package com.springmvc.login;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO �Զ����ɵķ������
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO �Զ����ɵķ������
		HttpSession session = event.getSession();  
        ServletContext application = session.getServletContext();  
        // ȡ�õ�¼���û���  
        String usercode = (String) session.getAttribute("usercode");  
        // �������б���ɾ���û���  
        List onlineUserList = (List) application.getAttribute("onlineUserList");  
        onlineUserList.remove(usercode);  
        System.out.println(usercode + "��ʱ�˳���");
		
	}

}
