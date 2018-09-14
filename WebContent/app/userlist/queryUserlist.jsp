<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String yes_or_no=cache.getDictItemJson("yes_or_no");
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
	 * 页面初始化
	 */
	 $(function() {
		 document.getElementById("checkUserB").style.display="none";
		 var check=queryUserlist.check.value;
		 if(check=="userRole"){
			 document.getElementById("tableB").style.display="none";
			 document.getElementById("checkUserB").style.display="";
		 }
	 });
	
	/**
	 * 新增用户信息
	 */
	function insertUserList(){
		$(this).dialog({id:'editUserList', 
			url:"app/userlist/editUserList.jsp", 
			title:'新增用户信息',
			max:true});
	}
	/**
	 * 修改用户信息
	 */
	function updateUserList(){
		var selectLen=getTableSelectedLen('userlistT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要修改的行！");
		}
		else if(selectLen>1){
			$(this).alertmsg("error", "修改时只能选择一个！");
		}
		else{
			var userid=getTableSelected('userlistT',1);
			if(userid!=""&&userid!=null){
				$(this).dialog({id:'mydialog', 
							url:"txn99993.do?userid="+userid, 
							title:'修改用户信息',
							max:true,
							mask:true});
			}
		}
	}
	/**
	 * 删除用户信息
	 */
	function deleteUserList(){
		var selectLen=getTableSelectedLen('userlistT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var userid=getTableSelected('userlistT',1);
			$(this).bjuiajax('doAjax', {
				url:"txn99994.do?userid="+userid,
				confirmMsg:"确认删除？"
			});
		}
	}
	
	/**
	 * 选择用户信息
	 */
	function checkUserlist(){
		var selectLen=getTableSelectedLen('userlistT');
		if(selectLen==0){
			$(this).alertmsg("error", "请选择要删除的行！");
		}
		else{
			var userid=getTableSelected('userlistT',1);
			var role_authority_id=queryUserlist.role_authority_id.value;
			$(this).bjuiajax('doAjax', {
				url:"txnra00006.do?userid="+userid+"&role_authority_id="+role_authority_id,
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

	<form action="txn99990.do" data-toggle="ajaxsearch" id="queryUserlist" data-reload="false">
		<div class="bjui-searchBar">
			<input type="hidden" id="check" name="check" value="${check}">
			<input type="hidden" id="role_authority_id" name="role_authority_id" value="${role_authority_id}">
			<label>用户代码：</label><input type="text" id="usercode" name="usercode" class="form-control" size="10">&nbsp;
			<label>用户姓名：</label><input type="text" id="username" name="username" class="form-control" size="10">&nbsp;
			<label>部门：</label>
			<select name="office_one" data-toggle="selectpicker" data-nextselect="#user_office_two_s" data-refurl="getCodeValue.do?codetype=office_two&codevalue={value}" >
				<option value="">无</option>
				<%
					List<String[]> office_oneL=cache.getDictItem("office_one");
				%>
				<c:forEach items="<%=office_oneL%>" var="itemA">
					<option value="${itemA[0]}">${itemA[1]}</option>
    			</c:forEach>
            </select>
            <select name="office_two" id="user_office_two_s" data-width="100" data-toggle="selectpicker" data-emptytxt="" data-nextselect="#user_office_thr_s" data-refurl="getCodeValue.do?codetype=office_thr&codevalue={value}">
            </select>
            <select name="office_thr" id="user_office_thr_s" data-width="100" data-toggle="selectpicker" data-emptytxt="">
            </select>
            <button type="submit" class="btn-default" data-icon="search">查找</button>&nbsp;
            
            <div class="pull-right" id="tableB">
        		<button type="button" class="btn-blue" data-icon="plus" title="增加" onclick="insertUserList()">增加</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="edit" title="修改" onclick="updateUserList()">修改</button>&nbsp;
        		<button type="button" class="btn-blue" data-icon="remove" title="删除" onclick="deleteUserList()">删除</button>&nbsp;
        	</div>
        	<div class="pull-right" id="checkUserB">
        		<button type="button" class="btn-blue" onclick="checkUserlist()">选择</button>&nbsp;
        	</div>
		</div>
	</form>
	<table  data-toggle="datagrid" 
		data-options="{filterThead:false,showCheckboxcol:true}"
		data-data=${dataList}
		id="userlistT"
	>
		
		<thead>
    		<tr>
            	<th data-options="{name:'userid', width:200,hide:true}">userid</th>
            	<th data-options="{name:'usercode', width:200}">用户代码</th>
            	<th data-options="{name:'username', width:200}">用户姓名</th>
            	<th data-options="{name:'office_phone', width:200}">办公电话</th>
            	<th data-options="{name:'email', width:200}">电子邮箱</th>
            	<th data-options="{name:'office_one', width:200,items:<%=office_one%>}">所属部门</th>
            	<th data-options="{name:'grade', width:200}">资格等级</th>
            	<th data-options="{name:'status', width:200,items:<%=yes_or_no%>}">在职状态</th>
        	</tr>
		</thead>
	</table>

</body>
</html>