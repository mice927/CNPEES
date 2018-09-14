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
		 var codetype=codeindex_form.codetype.value;
		 
		 if(codetype!=""&&codetype!=null){
			 codeindex_form.codetype.setAttribute("readonly");
		 }
		 codeindex_form.onload.value=codeindex_form.onload_s.value;
		 codeindex_form.father_code.value=codeindex_form.father_code_s.value;
	 });
	
	</script>
	<div class="bjui-pageContent">
		<form action="txncd00002.do" id="codeindex_form" name="codeindex_form" data-toggle="validate">
			<input type="hidden" name="check" value="${check }">
			<input type="hidden" name="status" value="01">
			<input type="hidden" name="onload_s" value="${onload }">
			<input type="hidden" name="father_code_s" value="${father_code }">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label for="codetype" class="control-label x85">代码名称：</label>
							<input type="text" name="codetype" data-rule="required" value="${codetype}" size="20"/>
						</td>
						<td>
							<label for="description" class="control-label x85">代码描述：</label>
							<input type="text" name="description" data-rule="required" value="${description}" size="20"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label x85">是否初始化：</label>
							<select name="onload" data-toggle="selectpicker" data-width="200">
								<%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									List<String[]> onloadL=cache.getDictItem("yes_or_no");
								%>
								<c:forEach items="<%=onloadL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
						</td>
						<td>
							<label class="control-label x85">父代码集：</label>
							<select name="father_code" data-toggle="selectpicker" data-width="200">
								<option value="">无</option>
								<%
									List<String[]> codeindexL=cache.getDictItem("codeindex");
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