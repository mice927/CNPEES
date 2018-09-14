package com.springmvc.login;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetEncodingFilter implements Filter {
	protected String encoding = null;// ����ȱʡ�ַ����뷽ʽ
	protected boolean ignore = true;// ����ͻ���ָ���ı��뷽ʽ�Ƿ�Ӧ������
	protected FilterConfig filterConfig = null;// ������������ö���,��Ϊnull,��˵��������δ����

	public void destroy()// ֹͣ�������Ĺ���
	{
		this.encoding = null;
		this.filterConfig = null;
	}

	// �����ַ�����
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (ignore || (req.getCharacterEncoding() == null)) {
			req.setCharacterEncoding(selectEncoding(req));
		}
		chain.doFilter(req, res);
	}

	// ����������
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true")
				|| value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	// ѡ����ʵ��ַ����뷽ʽ
	protected String selectEncoding(ServletRequest req) {
		return this.encoding;
	}

	// ����filterConfig����
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	// ����filterConfig����
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	@Override
	public boolean isLoggable(LogRecord arg0) {
		// TODO �Զ����ɵķ������
		return false;
	}
}
