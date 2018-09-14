<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
	String father_code=request.getParameter("father_code");
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String father_code_value=cache.getDictItemJson(father_code);
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
	function insertCodeData(){
		var codetype=codedata_form.codetype.value;
		var father_code=codedata_form.father_code.value;
		$(this).dialog({id:'editCodeData', 
			url:"app/codedata/editCodeData.jsp?codetype="+codetype+"&father_code="+father_code, 
			width:"400",
			height:"400",
			mask:true,
			title:'新增代码信息'});
	}
	/**
	 * 修改代码信息
	 */
	function updateCodeData(){
		var selectLen=getTableSelectedLen('listCodeDataT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要修改的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "修改时只能选择一个！");
		}
		else{
			var codetype=getTableSelected('listCodeDataT',1);
			var codevalue=getTableSelected('listCodeDataT',2);
			var father_code=codedata_form.father_code.value;
			if(codetype!=""&&codetype!=null){
				$(this).dialog({id:'editCodeData', 
							url:"txncd00007.do?codetype="+codetype+"&codevalue="+codevalue+"&father_code="+father_code, 
							title:'修改代码信息',
							width:"400",
							height:"400",
							mask:true});
			}
		}
	}
	/**
	 * 删除代码信息
	 */
	function deleteCodeData(){
		var selectLen=getTableSelectedLen('listCodeDataT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var codetype=getTableSelected('listCodeDataT',1);
			var codevalue=getTableSelected('listCodeDataT',2);
			$(this).bjuiajax('doAjax', {
				url:"txncd00008.do?codetype="+codetype+"&codevalue="+codevalue,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	</script>

	<form action="txncd00005.do" data-toggle="ajaxsearch" id="codedata_form" name="codedata_form" data-reload="false" id="">
		<div class="bjui-searchBar">
		    <input type="hidden" name="codetype" value="${codetype }">
		    <input type="hidden" name="father_code" value="${father_code }">
            <button type="submit" class="btn-default" data-icon="search">刷新</button>&nbsp;
            <button type="button" class="btn-blue" data-icon="plus" onclick="insertCodeData()">增加</button>&nbsp;
            <button type="button" class="btn-blue" data-icon="edit" onclick="updateCodeData()">修改</button>&nbsp;
        	<button type="button" class="btn-blue" data-icon="remove" onclick="deleteCodeData()">删除</button>&nbsp;
		</div>
	</form>
	<table  data-toggle="datagrid" 
		data-options="{filterThead:false,showCheckboxcol:true}"
		data-data=${dataList}
		id="listCodeDataT"
	>
		
		<thead>
    		<tr>
            	<th data-options="{name:'codetype', width:200}">代码名称</th>
            	<th data-options="{name:'codevalue', width:200}">代码值</th>
            	<th data-options="{name:'codename', width:200}">代码名</th>
            	<th data-options="{name:'father_codevalue', width:200, items:<%=father_code_value%>}">父代码值</th>
        	</tr>
		</thead>
	</table>

</body>
</html>