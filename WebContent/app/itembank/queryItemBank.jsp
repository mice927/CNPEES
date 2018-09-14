<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String item_bank_type=cache.getDictItemJson("item_bank_type");
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
		 document.getElementById("checkItemB").style.display="none";
		 var check=queryItemBank.check.value;
		 if(check=="office"){
			 document.getElementById("tableB").style.display="none";
			 document.getElementById("checkItemB").style.display="";
		 }
	 });
	
	/**
	 * 新增题库信息
	 */
	function insertItemBank(){
		$(this).dialog({id:'editItemBank', 
			url:"app/itembank/editItemBank.jsp", 
			title:'新增题库信息',
			max:true});
	}
	/**
	 * 修改题库信息
	 */
	function updateItemBank(){
		var selectLen=getTableSelectedLen('itemBank_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要修改的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "修改时只能选择一个！");
		}
		else{
			var item_bank_id=getTableSelected('itemBank_listT',1);
			if(item_bank_id!=""&&item_bank_id!=null){
				$(this).dialog({id:'editItemBank', 
							url:"txnib00004.do?item_bank_id="+item_bank_id, 
							title:'修改题库信息',
							max:true,
							mask:true});
			}
		}
	}
	/**
	 * 删除题库信息
	 */
	function deleteItemBank(){
		var selectLen=getTableSelectedLen('itemBank_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var item_bank_id=getTableSelected('itemBank_listT',1);
			$(this).bjuiajax('doAjax', {
				url:"txnib00005.do?item_bank_id="+item_bank_id,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	/**
	 * 考题管理
	 */
	function queryExamPaper(){
		var selectLen=getTableSelectedLen('itemBank_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要添加的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "只能选择一个！");
		}
		else{
			var item_bank_id=getTableSelected('itemBank_listT',1);
			if(item_bank_id!=""&&item_bank_id!=null){
				$(this).dialog({id:'queryExamPaper', 
							url:"txnep00001.do?item_bank_id="+item_bank_id, 
							title:'考题管理',
							max:true});
			}
		}
	}
	
	/**
	 * 选择题库信息
	 */
	function checkItemBank(){
		var selectLen=getTableSelectedLen('itemBank_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var item_bank_id=getTableSelected('itemBank_listT',1);
			var office_id=queryItemBank.office_id.value;
			$(this).bjuiajax('doAjax', {
				url:"txnep00007.do?item_bank_id="+item_bank_id+"&office_id="+office_id,
				callback:checkCallback
			});
		}
	}
	/**
	 * 判断用户回调
	 */
	function checkCallback(json){
		var statusCode=json.statusCode;
		if(statusCode=="200"){
			$(this).alertmsg("ok", "添加成功！");
		}
	}
	
	</script>

	<form action="txnib00001.do" data-toggle="ajaxsearch" id="queryItemBank" data-reload="false">
		<div class="bjui-searchBar">
			<input type="hidden" name="office_id" id="office_id" value="${office_id}">
			<input type="hidden" name="check" id="check" value="${check}">
			<label>题库编码：</label><input type="text" id="item_bank_code" name="item_bank_code" class="form-control" size="10">&nbsp;
			<label>题库名称：</label><input type="text" id="item_bank_name" name="item_bank_name" class="form-control" size="10">&nbsp;
			<label>题库说明：</label><input type="text" id="item_bank_description" name="item_bank_description" class="form-control" size="10">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查找</button>&nbsp;
            
            <div class="pull-right" id="tableB">
        		<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="insertItemBank()">增加题库</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="edit" title="修改" onclick="updateItemBank()">修改题库</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteItemBank()">删除题库</button>&nbsp;
        	</div>
        	<div class="pull-right" id="checkItemB">
        		<button type="button" class="btn-blue" onclick="checkItemBank()">选择题库</button>&nbsp;
        	</div>
		</div>
	</form>
	<div style="width:100%; height:98%; overflow:auto;">
		<table  data-toggle="datagrid" 
			data-options="{filterThead:false,showCheckboxcol:true,paging:{pageSize:${total},selectPageSize:'' }}"
			data-data=${dataList}
			id="itemBank_listT"
		>
		
			<thead>
    			<tr>
    			<th data-options="{name:'item_bank_id', width:200,hide:true}">item_bank_id</th>
            	<th data-options="{name:'item_bank_code', width:200}">题库编码</th>
            	<th data-options="{name:'item_bank_name', width:200}">题库名称</th>
            	<th data-options="{name:'item_bank_type', width:200, items:<%=item_bank_type%>}">题库所属类别</th>
        		</tr>
			</thead>
		</table>
	</div>

</body>
</html>