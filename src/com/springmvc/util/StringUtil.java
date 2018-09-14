package com.springmvc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtil {
	
	static long DAY = 1000*60*60*24;

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||str.equals("")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
     * ָ�����ڸ�ʽ��ȡ��ǰ����
     * @param pattern String ʱ����ʾ��ʽ
     * @return String
     */
    public static String getCurrentDate(String pattern) {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return(sf.format(date.getTime()));
    }
}
