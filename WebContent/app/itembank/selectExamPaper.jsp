<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" import="java.util.*"%>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String exam_paper_type=cache.getDictItemJson("exam_paper_type");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
	
	/**
	 * 页面初始化
	 */
	 $(function() {
		 
	 });
	
	/**
	 * 删除考题
	 */
	function deleteExamPaper(){
		var selectLen=getTableSelectedLen('examPaper_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var exam_paper_id=getTableSelected('examPaper_listT',1);
			$(this).bjuiajax('doAjax', {
				url:"txnep00009.do?exam_paper_id="+exam_paper_id,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	/**
	 * 添加考题
	 */
	function editExamPaper(flag){
		var item_bank_id=exam_paper_form.item_bank_id.value;
		var exam_paper_id="";
		if(flag=="update"){
			var selectLen=getTableSelectedLen('examPaper_listT');
			if(selectLen==0){
				$(this).alertmsg("error", "请选择要修改的行！");
			}
			else if(selectLen>1){
				$(this).alertmsg("error", "修改时只能选择一个！");
			}
			else{
				exam_paper_id=getTableSelected('examPaper_listT',1);
			}
		}
		if(item_bank_id!=""&&item_bank_id!=null&&(flag=="insert"||(flag=="update"&&exam_paper_id!=""&&exam_paper_id!=null))){
			$(this).dialog({id:'editExamPaper', 
						url:"txnep00002.do?item_bank_id="+item_bank_id+"&exam_paper_id="+exam_paper_id, 
						title:'添加考题',
						max:true,
						mask:true});
		}
	}
	
	</script>
        
	<div class="bjui-searchBar">
		<form action="txnep00001.do" data-toggle="ajaxsearch" id="exam_paper_form" name="exam_paper_form" data-reload="false">
			<input type="hidden" name="item_bank_id" value="${item_bank_id}">
			<input type="text" name="item_bank_name" value="${item_bank_name}" size="100" readonly>
			<br/>
			<label>考题编码：</label><input type="text" id="exam_paper_code" name="exam_paper_code" class="form-control" size="10">&nbsp;
			<label>题目：</label><input type="text" id="exam_paper_name" name="exam_paper_name" class="form-control" size="30">&nbsp;
           	<label class="control-label x85">考题类型：</label>
			<select name="exam_paper_type" data-toggle="selectpicker" data-width="200">
				<%
					List<String[]> exam_paper_typeL=cache.getDictItem("exam_paper_type");
				%>
				<option value=""></option>
				<c:forEach items="<%=exam_paper_typeL%>" var="itemA">
					<option value="${itemA[0]}">${itemA[1]}</option>
   				</c:forEach>
               </select>
           	<button type="submit" class="btn-default" data-icon="search">查找</button>&nbsp;
          
           	<div class="pull-right">
       			<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="editExamPaper('insert')">增加考题</button>&nbsp;
       			<button type="button" class="btn-blue" data-icon="edit" title="修改" onclick="editExamPaper('update')">修改考题</button>&nbsp;
       			<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteExamPaper()">删除考题</button>&nbsp;
       		</div>
      	</form>
	</div>
		
		<div style="width:100%; height:98%; overflow:auto;">
			<table  data-toggle="datagrid" 
				data-options="{filterThead:false,showCheckboxcol:true,paging:{pageSize:${total},selectPageSize:'' }}"
				data-data=${dataList}
				id="examPaper_listT"
			>
			
				<thead>
	    			<tr>
	    			<th data-options="{name:'exam_paper_id', width:200,hide:true}">exam_paper_id</th>
	            	<th data-options="{name:'exam_paper_code', width:200}">考题编码</th>
	            	<th data-options="{name:'exam_paper_name', width:200}">题目</th>
	            	<th data-options="{name:'exam_paper_type', width:200, items:<%=exam_paper_type%>}">题型</th>
	            	<th data-options="{name:'exam_paper_score', width:200}">分值</th>
	        		</tr>
				</thead>
			</table>
		</div>
</body>
</html>