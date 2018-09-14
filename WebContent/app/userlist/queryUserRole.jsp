<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String office_one=cache.getDictItemJson("office_one");
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
	 * 增加授权
	 */
	function insertUserRole(){
		var role_authority_id=queryUserrole.role_authority_id.value;
		
		$(this).dialog({id:'queryUserlistBr', 
			url:"txn99990.do?check=userRole&role_authority_id="+role_authority_id, 
			title:'增加授权',
			max:true,
			mask:true});
	}
	
	/**
	 * 取消授权
	 */
	function deleteUserRole(){
		var selectLen=getTableSelectedLen('userroleT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var user_roal_authority_id=getTableSelected('userroleT',1);
			var role_authority_id=getTableSelected('userroleT',2);
			var userid=getTableSelected('userroleT',3);
			$(this).bjuiajax('doAjax', {
				url:"txnra00007.do?user_roal_authority_id="+user_roal_authority_id+"&role_authority_id="+role_authority_id+"&userid="+userid,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	</script>

	<form action="txnra00005.do" data-toggle="ajaxsearch" id="queryUserrole" data-reload="false">
		<div class="bjui-searchBar">
			<input type="hidden" id="role_authority_id" name="role_authority_id" value="${role_authority_id}">
            <button type="submit" class="btn-default" data-icon="search">刷新</button>&nbsp;
            <button type="button" class="btn-blue" data-icon="plus" onclick="insertUserRole()">增加授权</button>&nbsp;
        	<button type="button" class="btn-blue" data-icon="remove" onclick="deleteUserRole()">删除授权</button>&nbsp;
		</div>
	</form>
	<table  data-toggle="datagrid" 
		data-options="{filterThead:false,showCheckboxcol:true}"
		data-data=${dataList}
		id="userroleT"
	>
		
		<thead>
    		<tr>
            	<th data-options="{name:'user_roal_authority_id', width:200,hide:true}">user_roal_authority_id</th>
            	<th data-options="{name:'role_authority_id', width:200,hide:true}">role_authority_id</th>
            	<th data-options="{name:'userid', width:200,hide:true}">userid</th>
            	<th data-options="{name:'usercode', width:200}">用户代码</th>
            	<th data-options="{name:'username', width:200}">用户姓名</th>
            	<th data-options="{name:'office_one', width:200,items:<%=office_one%>}">所属部门</th>
            	<th data-options="{name:'grade', width:200}">资格等级</th>
        	</tr>
		</thead>
	</table>

</body>
</html>