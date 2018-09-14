package com.springmvc.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class DaoUtil {
	
	/** 
     * 生成32位编码 
     * @return string 
     */   
    public static String getUUID(){   
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");   
        return uuid;   
    } 
	
	
	/**
	 * 解析查出的结果，拼接成数组字符串
	 * @param tableTitle
	 * @param sqlList
	 * @return
	 */
	public static String getDataList(String tableColmuns,List<Map<String, Object>> sqlList){
		StringBuffer dataList=new StringBuffer();
		
		if(StringUtil.isEmpty(tableColmuns)){
			return "";
		}
		
		dataList.append("[");
		for(int i=0;i<sqlList.size();i++){
			String data="{";
			String tableColmunsA[]=tableColmuns.split(",");
			for(int j=0;j<tableColmunsA.length;j++){
				String value=(String) sqlList.get(i).get(tableColmunsA[j]);
				if(StringUtil.isEmpty(value)){
					value="";
				}
				data=data+tableColmunsA[j]+":'"+value.replace(" ", "&nbsp;")+"',";
			}
			data=data.substring(0, data.length()-1)+"}";
			
			dataList.append(data).append(",");
		}
		String dataListS=dataList.substring(0, dataList.length()-1).toString()+"]";
		return dataListS;
	}
	
	/**
	 * 获取数据组成map结构
	 * 主要用于显示
	 * @param tableTitle
	 * @param sqlList
	 * @return
	 */
	public static HashMap<String, Object> getDataMap(String tableColmuns,List<Map<String, Object>> sqlList){
		HashMap<String, Object> map=new HashMap<String, Object>();
		
		if(sqlList!=null&&sqlList.size()>0){
			String tableColmunsA[]=tableColmuns.split(",");
			for(int i=0;i<tableColmunsA.length;i++){
				String value=(String) sqlList.get(0).get(tableColmunsA[i]);
				
				map.put(tableColmunsA[i], value);
			}
		}
		
		return map;
	}
	
	/**
	 * insertTableValues
	 * 拼接插入的values
	 * @param tableColmuns
	 * @param request
	 */
	public static String insertTableValues(String tableColmuns,String id,HttpServletRequest request){
		if(StringUtil.isEmpty(id)){
			id=DaoUtil.getUUID();
		}
		String values="'"+id+"',";
		
		String tableColmunsA[]=tableColmuns.split(",");
		for(int i=1;i<tableColmunsA.length;i++){
			String value=request.getParameter(tableColmunsA[i]);
			if(StringUtil.isEmpty(value)){
				value="";
			}
			values = values+"'"+value+"',";
		}
		values=values.substring(0, values.length()-1);
		return values;
	}
	
	/**
	 * updateTableSet
	 * 拼接修改的信息
	 * @param tableColmuns
	 * @param request
	 */
	public static String updateTableSet(String tableColmuns,HttpServletRequest request){
		String values="";
		
		String tableColmunsA[]=tableColmuns.split(",");
		for(int i=0;i<tableColmunsA.length;i++){
			String value=request.getParameter(tableColmunsA[i]);
			if(StringUtil.isEmpty(value)){
				value="";
			}
			values = values+tableColmunsA[i]+"='"+value+"',";
		}
		values=values.substring(0, values.length()-1);
		return values;
	}
	
	/**
	 * 解析查出的结果，拼接成二级树
	 * @param tableTitle
	 * @param sqlList
	 * @return
	 */
	public static String getDataListForTree(List<Map<String, Object>> sqlList,String url,String divid){
		StringBuffer dataList=new StringBuffer();
		if(sqlList!=null&&sqlList.size()>0){
			String father_value_old=(String) sqlList.get(0).get("father_value");
			String father_name_old=(String) sqlList.get(0).get("father_name");
			String child_id_old="";
			dataList.append("<li data-id=\""+father_value_old+"\" data-pid=\"0\">"+father_name_old+"</li>");
			for(int i=0;i<sqlList.size();i++){
				String father_value=(String) sqlList.get(i).get("father_value");
				String father_name=(String) sqlList.get(i).get("father_name");
				String child_name=(String) sqlList.get(i).get("child_name");
				String child_id=(String) sqlList.get(i).get("child_id");
				String grandchild_name=(String) sqlList.get(i).get("grandchild_name");
				String grandchild_id=(String) sqlList.get(i).get("grandchild_id");
				if(!father_value.equals(father_value_old)){
					dataList.append("<li data-id=\""+father_value+"\" data-pid=\"0\">"+father_name+"</li> ");
					father_value_old=father_value;
				}
				if(!StringUtil.isEmpty(child_id)&&StringUtil.isEmpty(grandchild_id)){
					dataList.append("<li data-id=\""+child_id+"\" data-pid=\""+father_value+"\" data-url=\""+url+"\" data-divid=\"#"+divid+"\">"+child_name+"</li>");
				}
				if(!StringUtil.isEmpty(grandchild_id)){
					if(StringUtil.isEmpty(child_id_old)||!child_id_old.equals(child_id)){
						dataList.append("<li data-id=\""+child_id+"\" data-pid=\""+father_value+"\">"+child_name+"</li> ");
						child_id_old=child_id;
					}
					dataList.append("<li data-id=\""+grandchild_id+"\" data-pid=\""+child_id+"\" data-url=\""+url+"\" data-divid=\"#"+divid+"\">"+grandchild_name+"</li>");
				}
			}
		}
		return dataList.toString();
	}
	
	/**
	 * 从查询结果中获取随机列，把ID拼成字符串
	 * @param sqlList
	 * @return
	 */
	public static String getRandomDataList(List<Map<String, Object>> sqlList,String id,int total,int num){
//		System.out.println("total=="+total+"==num=="+num);
		String randomDataList="";
		int[] array = new int[total];
		for(int i=0;i<total;i++){
			array[i]=i;
		}
		
		Random rand = new Random();
		for (int i = 0; i <num; i++) {
		    int index = rand.nextInt(total);
		    int tmp = array[index];
		    array[index] = array[i];
		    array[i] = tmp;
		}
		
		for(int j=0;j<num;j++){
			randomDataList=randomDataList+sqlList.get(array[j]).get(id)+",";
		}
		
		return randomDataList.substring(0, randomDataList.length()-1);
	}
	
	/**
	 * jointPaper
	 * @param sqlList
	 * @return
	 */
	public static String jointPaper(List<Map<String, Object>> sqlList){
		StringBuffer paperS=new StringBuffer();
		String exam_paper_userlist_idT="";
		String username=(String) sqlList.get(0).get("username");
		String score=(String) sqlList.get(0).get("score");
		String office=(String) sqlList.get(0).get("office");
		String paper_status=(String) sqlList.get(0).get("paper_status");
		
		paperS.append("<table class=\"table table-condensed table-hover\" width=\"100%\" id=\"paper_t\"><tbody>");
		//抬头用户信息
		paperS.append("<tr><td><label for=\"username\" class=\"control-label x85\" style=\"font-size:14px;\">姓名：</label><span id=\"username_view\" style=\"font-size:14px;color:red;\">"+username+"</span></td>");
		paperS.append("<td><label for=\"office\" class=\"control-label x85\" style=\"font-size:14px;\">部门：</label><span id=\"office_view\" style=\"font-size:14px;color:red;\">"+office+"</span></td>");
		paperS.append("<td><label for=\"score\" class=\"control-label x85\" style=\"font-size:14px;\">总分：</label><span id=\"score_view\" style=\"font-size:14px;color:red;\">"+score+"</span></td></tr>");
		
		//拼接题
		for(int i=0;i<sqlList.size();i++){
			String exam_paper_userlist_id=(String) sqlList.get(i).get("exam_paper_userlist_id");
			String exam_paper_type=(String) sqlList.get(i).get("exam_paper_type");
			String exam_paper_name=(String) sqlList.get(i).get("exam_paper_name");
			String exam_paper_tab=(String) sqlList.get(i).get("exam_paper_tab");
			String exam_paper_solution=(String) sqlList.get(i).get("exam_paper_solution");
			String right_wrong=(String) sqlList.get(i).get("right_wrong");
			String test_score=(String) sqlList.get(i).get("test_score");
			String background_color="";
			String font_color="blue";
			
			if(!StringUtil.isEmpty(exam_paper_solution)&&paper_status.equals(Constants.PAPERINFO_STATUS_EDIT)){
				background_color="style=\"background-color:#eeeeee;\"";
			}
			if(!StringUtil.isEmpty(right_wrong)){
				if(right_wrong.equals(Constants.PAPER_RIGHT)){
					font_color="green";
				}
				else if(right_wrong.equals(Constants.PAPER_WRONG)){
					font_color="red";
				}
			}
			
			if(exam_paper_type.equals(Constants.PAPER_TYPE_CHOOSE)&&!StringUtil.isEmpty(exam_paper_tab)){//选择题
				String exam_paper_tabA[]=exam_paper_tab.split("#;");
				paperS.append("<tr><td colspan=\"3\"><table class=\"table table-condensed table-hover\" width=\"100%\" "+background_color+" id=\""+exam_paper_userlist_id+"_t\"><tbody>");
				paperS.append("<tr><td colspan=\""+exam_paper_tabA.length+"\"><label for=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";\">"+(i+1)+"、</label><span id=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";font-weight:bold\">"+exam_paper_name+"</span></td></tr>");
				
				paperS.append("<tr>");
				for(int j=0;j<exam_paper_tabA.length;j++){
					if(!StringUtil.isEmpty(exam_paper_tabA[j])&&exam_paper_tabA[j].split("#:").length>1){
						String exam_paper_tabAA[]=exam_paper_tabA[j].split("#:");
						String checked="";
						if(exam_paper_solution.contains(exam_paper_tabAA[0])){
							checked="CHECKED=\"\"";
						}
						paperS.append("<td><input id=\""+exam_paper_userlist_id+"_"+exam_paper_tabAA[0]+"\" name=\""+exam_paper_userlist_id+"\" value=\""+exam_paper_tabAA[0]+"\" type=\"checkbox\" "+checked+" data-toggle=\"icheck\">&nbsp;&nbsp;"+exam_paper_tabAA[1]+"</td>");
						
					}
				}
				paperS.append("</tr>");
				paperS.append("</tbody></table></td></tr>");
			}
			else if(exam_paper_type.equals(Constants.PAPER_TYPE_TOF)){//判断题
				String checkedT="";
				String checkedF="";
				if(!StringUtil.isEmpty(exam_paper_solution)){
					if(exam_paper_solution.equals("T")){
						checkedT="CHECKED=\"\"";
					}
					else{
						checkedF="CHECKED=\"\"";
					}
				}
				paperS.append("<tr><td colspan=\"3\"><table class=\"table table-condensed table-hover\" width=\"100%\" "+background_color+" id=\""+exam_paper_userlist_id+"_t\"><tbody>");
				paperS.append("<tr><td colspan=\"3\"><label for=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";\">"+(i+1)+"、</label><span id=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";font-weight:bold\">"+exam_paper_name+"</span></td>");
				paperS.append("<td><input id=\""+exam_paper_userlist_id+"_T\" name=\""+exam_paper_userlist_id+"\" "+checkedT+" value=\"T\" type=\"radio\" data-toggle=\"icheck\">&nbsp;对&nbsp;&nbsp;");
				paperS.append("<input id=\""+exam_paper_userlist_id+"_F\" name=\""+exam_paper_userlist_id+"\" "+checkedF+" value=\"T\" type=\"radio\" data-toggle=\"icheck\">&nbsp;错&nbsp;&nbsp;</td></tr>");
				paperS.append("</tbody></table></td></tr>");
			}
			else if(exam_paper_type.equals(Constants.PAPER_TYPE_ANSWER)){//简答题
				paperS.append("<tr><td colspan=\"3\"><table class=\"table table-condensed table-hover\" width=\"100%\" "+background_color+" id=\""+exam_paper_userlist_id+"_t\"><tbody>");
				paperS.append("<tr><td><label for=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";\">"+(i+1)+"、</label><span id=\""+exam_paper_userlist_id+"_name\" style=\"font-size:14px;color:"+font_color+";font-weight:bold\">"+exam_paper_name+"</span></td></tr>");
				paperS.append("<tr><td><textarea type=\"text\" name=\""+exam_paper_userlist_id+"\" id=\""+exam_paper_userlist_id+"\" data-rule=\"required\" data-toggle=\"autoheight\" cols=\"120\" rows=\"2\">"+exam_paper_solution+"</textarea></td></tr>");
				paperS.append("</tbody></table></td></tr>");
			}
			
			exam_paper_userlist_idT=exam_paper_userlist_idT+exam_paper_userlist_id+",";
		}
		paperS.append("</tbody></table>");
		
		if(paper_status.equals(Constants.PAPERINFO_STATUS_EDIT)){   //已提交的试卷不允许修改
			paperS.append("<table class=\"table table-condensed table-hover\" width=\"100%\" id=\"button_t\"><tbody><tr>");
			paperS.append("<td></td>");
			paperS.append("<td></td>");
			paperS.append("<td></td>");
			paperS.append("<td><input type=\"hidden\" name=\"exam_paper_userlist_id\" id=\"exam_paper_userlist_id\" value=\""+exam_paper_userlist_idT.substring(0,exam_paper_userlist_idT.length()-1)+"\"/>");
			paperS.append("<button type=\"button\" class=\"btn-default btn-nm\" onclick=\"submitExamPaper(0)\" style=\"margin:0 auto;\" id=\"creat_examinfo\"> 暂  存 </button></td>");
			paperS.append("<td><button type=\"button\" class=\"btn-default btn-nm\" onclick=\"submitExamPaper(1)\" id=\"creat_examinfo\"> 提  交 </button></td>");
			paperS.append("<td></td>");
			paperS.append("<td></td>");
			paperS.append("</tr></tbody></table>");
		}
		
		return paperS.toString();
	}

}
