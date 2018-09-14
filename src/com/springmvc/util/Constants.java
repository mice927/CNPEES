package com.springmvc.util;

/**
 * 常量类
 * @author chenhao
 *
 */
public class Constants {
	/**
	 * 状态 
	 */
	public static final String  STATUS_T = "01";        //有效
	public static final String  STATUS_F = "02";        //无效
	
	/**
	 * 题型
	 */
	public static final String  PAPER_TYPE_CHOOSE = "02";        //选择题
	public static final String  PAPER_TYPE_TOF = "03";        //判断题
	public static final String  PAPER_TYPE_ANSWER = "05";        //简答题
	
	/**
	 * 用户试卷状态 
	 */
	public static final String  PAPERINFO_STATUS_EDIT= "01";        //编辑中
	public static final String  PAPERINFO_STATUS_SUBMIT = "02";        //已提交
	public static final String  PAPERINFO_STATUS_FINISHED = "03";        //已评分
	
	/**
	 * 正确错误
	 */
	public static final String  PAPER_RIGHT= "R";        //正确
	public static final String  PAPER_WRONG = "W";        //错误
	
	
	/**
	 * 角色信息
	 */
	public static final String  ROLE_AUTHORITY_ZKG= "3df160bb3d554a1381b513e65bea463f";        //主考官
}
