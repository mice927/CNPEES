<%@ page language="java" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		 var userid=userlist_form.userid.value;
		 
		 if(userid!=""&&userid!=null){
			 userlist_form.usercode.setAttribute("readonly");
		 }
		 
		 userlist_form.office_one.value=userlist_form.office_one_s.value;
	 });
	/**
	 * 设置用户初始密码
	 */ 
	function setUserPassword(){
		var usercode=userlist_form.usercode.value;
		userlist_form.password.value=usercode;
	}
	/**
	 * 判断用户代码是否重复
	 */
	function checkUserCode(){
		var userid=userlist_form.userid.value;
		if(userid==""||userid==null){
			var usercode=userlist_form.usercode.value;
			if(usercode==""||usercode==null){
				setUserPassword();
				return;
			}
			$(this).bjuiajax('doAjax', {
				url:"txn99992.do?usercode="+usercode,
				callback:checkCallback
			});
		}
	}
	/**
	 * 判断用户回调
	 */
	function checkCallback(json){
		var statusCode=json.statusCode;
		var message=json.message;
		if(message==""||message==null){
			setUserPassword();
		}
		else{
			$(this).alertmsg("error", json.message);
			userlist_form.usercode.value="";
			userlist_form.password.value="";
		}
	}
	/**
	 * 保存用户回调
	 */
	function saveCallback(json){
		var userid=json.userid;
		if(userid!=""&&userid!=null){
			$(this).alertmsg("ok", "保存成功！");
			userlist_form.userid.value=userid;
			userlist_form.usercode.setAttribute("readonly");
		}
	}
	</script>
	<div class="bjui-pageContent">
		<form action="txn99991.do" id="userlist_form" name="userlist_form" data-toggle="validate" data-callback="saveCallback">
			<input type="hidden" name="userid" value="${userid}">
			<input type="hidden" name="password" value="${password}"/>
			<input type="hidden" name="office_one_s" value="${office_one}">
			<input type="hidden" name="status" value="01">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label for="usercode" class="control-label x85">用户代码：</label>
							<input type="text" name="usercode" data-rule="required" value="${usercode}" size="20" onchange="checkUserCode()"/>
						</td>
						<td>
							<label for="username" class="control-label x85">用户姓名：</label>
							<input type="text" name="username" data-rule="required" value="${username}" size="20"/>
						</td>
						<td>
							<label class="control-label x85">部门：</label>
							<select name="office_one" data-toggle="selectpicker" data-val="${office_one}" data-nextselect="#user_office_two" data-refurl="getCodeValue.do?codetype=office_two&codevalue={value}" >
								<option value="">无</option>
								<%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									List<String[]> office_oneL=cache.getDictItem("office_one");
								%>
								<c:forEach items="<%=office_oneL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
                            <select name="office_two" id="user_office_two" data-width="100" data-toggle="selectpicker" data-val="${office_two}" data-emptytxt="" data-nextselect="#user_office_thr" data-refurl="getCodeValue.do?codetype=office_thr&codevalue={value}">
                            	<option value="">无</option>
                            </select>
                            <select name="office_thr" id="user_office_thr" data-width="100" data-toggle="selectpicker" data-val="${office_thr}" data-emptytxt="">
                            	<option value="">无</option>
                            </select>
						</td>
					</tr>
					<tr>
						<td>
							<label for="office_phone" class="control-label x85">办公电话：</label>
							<input type="text" name="office_phone" value="${office_phone}" size="20"/>
						</td>
						<td>
							<label for="email" class="control-label x85">邮箱：</label>
							<input type="text" name="email" size="20" value="${email}" data-rule="email"/>
						</td>
						<td>
							<label for="grade" class="control-label x85">资格等级：</label>
							<input type="text" name="grade" value="${grade}" size="20" data-rule="integer">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="bjui-pageFooter">
    	<ul>
        	<li><button type="button" class="btn-close" data-icon="close">取消</button></li>
        	<li><button type="submit" class="btn-default" data-icon="save">提交</button></li>
        	<!-- li><button type="button" class="btn-default" data-icon="save" onclick="">提交并继续</button></li -->
    	</ul>
	</div>
</body>
</html>