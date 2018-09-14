package com.springmvc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StringUtil {
	
	static long DAY = 1000*60*60*24;

	/**
	 * 判断字符串是否为空
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
     * 指定日期格式获取当前日期
     * @param pattern String 时间显示格式
     * @return String
     */
    public static String getCurrentDate(String pattern) {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return(sf.format(date.getTime()));
    }
}
