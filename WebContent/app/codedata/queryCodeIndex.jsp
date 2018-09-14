<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String yes_or_no=cache.getDictItemJson("yes_or_no");
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
	 * 新增代码信息
	 */
	function insertCodeIndex(){
		$(this).dialog({id:'editCodeIndex', 
			url:"app/codedata/editCodeIndex.jsp", 
			width:"800",
			height:"200",
			title:'新增代码信息'});
	}
	/**
	 * 修改代码信息
	 */
	function updateCodeIndex(){
		var selectLen=getTableSelectedLen('codeIndex_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要修改的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "修改时只能选择一个！");
		}
		else{
			var codetype=getTableSelected('codeIndex_listT',1);
			if(codetype!=""&&codetype!=null){
				$(this).dialog({id:'editCodeIndex', 
							url:"txncd00003.do?codetype="+codetype, 
							title:'修改代码信息',
							width:"800",
							height:"150"});
			}
		}
	}
	/**
	 * 删除代码信息
	 */
	function deleteCodeIndex(){
		var selectLen=getTableSelectedLen('codeIndex_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var codetype=getTableSelected('codeIndex_listT',1);
			$(this).bjuiajax('doAjax', {
				url:"txncd00004.do?codetype="+codetype,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	/**
	 * 维护代码集信息
	 */
	function queryCodeData(){
		var selectLen=getTableSelectedLen('codeIndex_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要维护的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "维护时只能选择一个！");
		}
		else{
			var codetype=getTableSelected('codeIndex_listT',1);
			if(codetype!=""&&codetype!=null){
				$(this).dialog({id:'queryCodeData', 
							url:"txncd00005.do?codetype="+codetype, 
							title:'维护代码集',
							max:true});
			}
		}
	}
	
	</script>

	<form action="txncd00001.do" data-toggle="ajaxsearch" data-reload="false">
		<div class="bjui-searchBar">
			<label>代码名称：</label><input type="text" id="codetype" name="codetype" class="form-control" size="10">&nbsp;
			<label>代码描述：</label><input type="text" id="description" name="description" class="form-control" size="10">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查找</button>&nbsp;
            
            <div class="pull-right">
        		<button type="button" class="btn-blue" title="代码集维护" onclick="queryCodeData()">代码集维护</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="insertCodeIndex()">增加</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="edit" title="修改" onclick="updateCodeIndex()">修改</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteCodeIndex()">删除</button>&nbsp;
        	</div>
		</div>
	</form>
	<table  data-toggle="datagrid" 
		data-options="{filterThead:false,showCheckboxcol:true}"
		data-data=${dataList}
		id="codeIndex_listT"
	>
		
		<thead>
    		<tr>
            	<th data-options="{name:'codetype', width:200}">代码名称</th>
            	<th data-options="{name:'description', width:200}">代码描述</th>
            	<th data-options="{name:'onload', width:200, items:<%=yes_or_no%>}">是否初始化</th>
            	<th data-options="{name:'father_code', width:200}">父代码集</th>
        	</tr>
		</thead>
	</table>

</body>
</html>