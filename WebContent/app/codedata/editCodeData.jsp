<%@ page language="java" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String codetype=request.getParameter("codetype");
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
		 var codevalue=codedata_edit.codevalue.value;
		 
		 if(codevalue!=""&&codevalue!=null){
			 codedata_edit.codevalue.setAttribute("readonly");
		 }
		 codedata_edit.father_codevalue.value=codedata_edit.father_codevalue_s.value;
	 });
	
	</script>
	
	<div class="bjui-pageContent">
		<form action="txncd00006.do" id="codedata_edit" name="codedata_edit" data-toggle="validate">
			<input type="hidden" name="check" value="${check }">
			<input type="hidden" name="father_codevalue_s" value="${father_codevalue }">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label for="codetype" class="control-label x85">代码名称：</label>
							<input type="text" name="codetype" data-rule="required" value="<%=codetype%>" size="20" readonly/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="codevalue" class="control-label x85">代码值：</label>
							<input type="text" name="codevalue" data-rule="required" value="${codevalue}" size="20"/>
						</td>
					</tr>
					<tr>
						<td>
							<label for="codename" class="control-label x85">代码名：</label>
							<input type="text" name="codename" data-rule="required" value="${codename}" size="20"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label x85">父代码集：</label>
							<select name="father_codevalue" data-toggle="selectpicker" data-width="200">
								<option value="">无</option>
								<%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									String father_code=request.getParameter("father_code");
									
									List<String[]> codeindexL=cache.getDictItem(father_code);
								%>
								<c:forEach items="<%=codeindexL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[0]} - ${itemA[1]}</option>
    							</c:forEach>
                            </select>
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