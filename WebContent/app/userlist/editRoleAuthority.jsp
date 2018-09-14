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
		 roleauthority_form.role_type.value=roleauthority_form.role_type_s.value;
		 var authority_list=roleauthority_form.authority_list.value;
		 
		 if(authority_list!=""&&authority_list!=null){
			 var authority_listA=authority_list.split(",");
			 var authorityTree = $.fn.zTree.getZTreeObj("authority_tree");
			 if(authorityTree!=null){
				 var authorityNodes=authorityTree.getCheckedNodes(false);
				 for(var j=0;j<authority_listA.length;j++){
					 for(var i=0; i<authorityNodes.length; i++){
						 if(authority_listA[j]==authorityNodes[i].id){
							 
							 authorityTree.checkNode(authorityNodes[i], !authorityNodes[i].checked, true, true);
						 }
					 }
				 }
				 
			 }
		 }
	 });
	
	</script>
	<div class="bjui-pageContent">
		<form action="txnra00002.do" id="roleauthority_form" name="roleauthority_form" data-toggle="validate" >
			<input type="hidden" name="role_authority_id" value="${role_authority_id}">
			<input type="hidden" name="role_type_s" value="${role_type}">
			<input type="hidden" name="status" value="01">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label for="usercode" class="control-label x85">角色描述：</label>
							<input type="text" name="role_description" data-rule="required" value="${role_description}" size="20"/>
						</td>
						<td>
							<label class="control-label x85">角色类型：</label>
							<select name="role_type" data-toggle="selectpicker" data-width="200">
                                <%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									List<String[]> role_typeL=cache.getDictItem("role_type");
								%>
								<c:forEach items="<%=role_typeL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<label class="control-label x85">应用权限：</label>
							<input type="text" name="authority_list" value="${authority_list}" id="authority_list" data-toggle="selectztree" size="59" data-tree="#authority_tree" readonly>
                            <ul id="authority_tree" class="ztree hide" data-toggle="ztree" data-expand-all="true" data-check-enable="true" data-on-check="S_NodeCheck" data-on-click="S_NodeClick">
                                <%
									String allmenu=com.springmvc.util.JointMainList.getAllMainListInRole();
								%>
								<%=allmenu %>
                            </ul>
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
    	</ul>
	</div>
</body>
</html>