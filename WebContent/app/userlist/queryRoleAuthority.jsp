<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String role_type=cache.getDictItemJson("role_type");
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
	 * 新增角色信息
	 */
	function insertRoleAuthority(){
		$(this).dialog({id:'editRoleAuthority', 
			url:"app/userlist/editRoleAuthority.jsp", 
			width:"800",
			height:"150",
			title:'新增角色信息'});
	}
	/**
	 * 修改角色信息
	 */
	function updateRoleAuthority(){
		var selectLen=getTableSelectedLen('role_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要修改的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "修改时只能选择一个！");
		}
		else{
			var role_authority_id=getTableSelected('role_listT',1);
			if(role_authority_id!=""&&role_authority_id!=null){
				$(this).dialog({id:'mydialog', 
							url:"txnra00003.do?role_authority_id="+role_authority_id, 
							title:'修改角色信息',
							width:"800",
							height:"150",});
			}
		}
	}
	/**
	 * 删除角色信息
	 */
	function deleteRoleAuthority(){
		var selectLen=getTableSelectedLen('role_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var role_authority_id=getTableSelected('role_listT',1);
			$(this).bjuiajax('doAjax', {
				url:"txnra00004.do?role_authority_id="+role_authority_id,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	/**
	 * 授权维护
	 */
	function queryUserRole(){
		var selectLen=getTableSelectedLen('role_listT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要维护的角色！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "维护时只能选择一个！");
		}
		else{
			var role_authority_id=getTableSelected('role_listT',1);
			if(role_authority_id!=""&&role_authority_id!=null){
				$(this).dialog({id:'queryUserRole', 
							url:"txnra00005.do?role_authority_id="+role_authority_id, 
							title:'授权维护',
							max:true});
			}
		}
	}
	
	</script>

	<form action="txnra00001.do" data-toggle="ajaxsearch" data-reload="false">
		<div class="bjui-searchBar">
			<label>角色描述：</label><input type="text" id="role_description" name="role_description" class="form-control" size="10">&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查找</button>&nbsp;
            
            <div class="pull-right">
        		<button type="button" class="btn-blue" title="授权维护" onclick="queryUserRole()">授权维护</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="insertRoleAuthority()">增加</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="edit" title="修改" onclick="updateRoleAuthority()">修改</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteRoleAuthority()">删除</button>&nbsp;
        	</div>
		</div>
	</form>
	<table  data-toggle="datagrid" 
		data-options="{filterThead:false,showCheckboxcol:true}"
		data-data=${dataList}
		id="role_listT"
	>
		
		<thead>
    		<tr>
            	<th data-options="{name:'role_authority_id', width:200,hide:true}">role_authority_id</th>
            	<th data-options="{name:'role_description', width:200}">角色描述</th>
            	<th data-options="{name:'role_type', width:200,items:<%=role_type%>}">角色类型</th>
            	<th data-options="{name:'authority_list', width:200}">功能权限</th>
        	</tr>
		</thead>
	</table>

</body>
</html>