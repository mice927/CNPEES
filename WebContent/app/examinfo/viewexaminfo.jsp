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
		 var paper_str=document.getElementById("paper_str").value;
		 $("#paper").html(paper_str);
	 });
	
	</script>
	<div class="bjui-pageContent">
		<div class="tab-pane fade" id="settings">
       		<input type="hidden" id="paper_str" value='${paper_str}'/>
       		
       		<div id="paper"></div>
        </div>
	</div>
</body>
</html>