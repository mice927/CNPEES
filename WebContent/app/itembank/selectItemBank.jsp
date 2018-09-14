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
		 
	 });
	
	/**
	 * 增加题库信息
	 */
	function insertItemBank(){
		var office_id=document.getElementById("office_id").value;
		$(this).dialog({id:'queryItemBankOf', 
			url:"txnib00001.do?check=office&office_id="+office_id, 
			title:'增加授权',
			max:true,
			mask:true});
	}
	
	/**
	 * 删除题库信息
	 */
	function deleteItemBank(){
		var selectLen=getTableSelectedLen('office_itemBank_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var exam_office_id=getTableSelected('office_itemBank_listT',1);
			$(this).bjuiajax('doAjax', {
				url:"txnep00008.do?exam_office_id="+exam_office_id,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	</script>

	<div class="pull-left">
     	<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="insertItemBank()">增加题库</button>&nbsp;
     	<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteItemBank()">删除题库</button>&nbsp;
     	<input type="hidden" name="office_id" id="office_id" value="${office_id}">
		<input type="text" name="office_name" value="${office_name}" size="100" readonly>
     </div>
	<div style="width:100%; height:98%; overflow:auto;">
		<table  data-toggle="datagrid" 
			data-options="{filterThead:false,showCheckboxcol:true,paging:{pageSize:${total},selectPageSize:'' }}"
			data-data=${dataList}
			id="office_itemBank_listT"
		>
		
			<thead>
    			<tr>
	    			<th data-options="{name:'exam_office_id', width:200,hide:true}">exam_office_id</th>
	            	<th data-options="{name:'item_bank_code', width:200}">题库编码</th>
	            	<th data-options="{name:'item_bank_name', width:200}">题库名称</th>
	            	<th data-options="{name:'item_bank_type', width:200, items:<%=item_bank_type%>}">题库所属类别</th>
	            	<th data-options="{name:'percentage', width:200}">分配比例</th>
        		</tr>
			</thead>
		</table>
	</div>

</body>
</html>