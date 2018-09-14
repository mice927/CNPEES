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
		 var item_bank_id=itembank_form.item_bank_id.value;
		 if(item_bank_id!=""&&item_bank_id!=null){
			 itembank_form.item_bank_code.setAttribute("readonly");
		 }
		 
		 itembank_form.item_bank_type.value=itembank_form.item_bank_type_s.value;
	 });
	
	/**
	 * 判断题库编码是否重复
	 */
	function checkItemBankCode(){
		var item_bank_id=itembank_form.item_bank_id.value;
		if(item_bank_id==""||item_bank_id==null){
			var item_bank_code=itembank_form.item_bank_code.value;
			if(item_bank_code==""||item_bank_code==null){
				return;
			}
			$(this).bjuiajax('doAjax', {
				url:"txnib00002.do?item_bank_codes="+item_bank_code,
				callback:checkCallback
			});
		}
	}
	/**
	 * 判断题库编码回调
	 */
	function checkCallback(json){
		var statusCode=json.statusCode;
		var message=json.message;
		if(message==""||message==null){
			
		}
		else{
			$(this).alertmsg("error", json.message);
			itembank_form.item_bank_code.value="";
		}
	}
	/**
	 * 保存用户回调
	 */
	function saveCallback(json){
		var item_bank_id=json.item_bank_id;
		if(item_bank_id!=""&&item_bank_id!=null){
			$(this).alertmsg("ok", "保存成功！");
			itembank_form.item_bank_id.value=item_bank_id;
			itembank_form.item_bank_code.setAttribute("readonly");
		}
	}
	</script>
	<div class="bjui-pageContent">
		<form action="txnib00003.do" id="itembank_form" name="itembank_form" data-toggle="validate" data-callback="saveCallback">
			<input type="hidden" name="item_bank_id" value="${item_bank_id}">
			<input type="hidden" name="item_bank_type_s" value="${item_bank_type}">
			<input type="hidden" name="item_bank_status" value="01">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label for="item_bank_code" class="control-label x85">题库编码：</label>
							<input type="text" name="item_bank_code" data-rule="required" value="${item_bank_code}" size="20" onchange="checkItemBankCode()"/>
						</td>
						<td>
							<label for="item_bank_name" class="control-label x85">题库名称：</label>
							<input type="text" name="item_bank_name" data-rule="required" value="${item_bank_name}" size="20"/>
						</td>
						<td>
							<label class="control-label x85">题库类别：</label>
							<select name="item_bank_type" data-toggle="selectpicker" data-val="${item_bank_type}">
								<option value="">无</option>
								<%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									List<String[]> item_bank_typeL=cache.getDictItem("item_bank_type");
								%>
								<c:forEach items="<%=item_bank_typeL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
						</td>
						<td>
							<label for="item_bank_percent" class="control-label x85">比例：</label>
							<input type="text" name="item_bank_percent" data-rule="required" value="${item_bank_percent}" size="20"/>%
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<label for="item_bank_description" class="control-label x85">题库说明：</label>
							<textarea type="text" name="item_bank_description" data-toggle="autoheight" cols="120" rows="2">${item_bank_description}</textarea>
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