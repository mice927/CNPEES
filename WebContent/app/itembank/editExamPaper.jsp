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
		 var exam_paper_id=exampaper_form.exam_paper_id.value;
		 if(exam_paper_id!=""&&exam_paper_id!=null){
			 exampaper_form.exam_paper_type.value=exampaper_form.exam_paper_type_s.value;
			 
			 var exam_paper_type=exampaper_form.exam_paper_type.value;
			 document.getElementById("choose").style.display="none";
			 document.getElementById("torf").style.display="none";
			 document.getElementById("solution").style.display="none";
			 if(exam_paper_type==EXAM_PAPER_TYPE.RADIO||exam_paper_type==EXAM_PAPER_TYPE.CHECKBOX){
					document.getElementById("choose").style.display="";
					changeChoose();
			 }
			 else if(exam_paper_type==EXAM_PAPER_TYPE.TORF){
					document.getElementById("torf").style.display="";
					exampaper_form.torf_solution.value=exampaper_form.exam_paper_solution.value;
			 }
			 else if(exam_paper_type==EXAM_PAPER_TYPE.INPUT||exam_paper_type==EXAM_PAPER_TYPE.TEXTAREA){
					document.getElementById("solution").style.display="";
			 }
		 }
		 else{
			 checkPaperType();
		 }
		 
	 });
	
	/**
	 * 保存用户回调
	 */
	function saveCallback(json){
		var exam_paper_id=json.exam_paper_id;
		if(exam_paper_id!=""&&exam_paper_id!=null){
			$(this).alertmsg("ok", "保存成功！");
			exampaper_form.exam_paper_id.value=exam_paper_id;
		}
	}
	
	/**
	 * 判断选择类型
	 */
	function checkPaperType(){
		var exam_paper_type=exampaper_form.exam_paper_type.value;
		document.getElementById("choose").style.display="none";
		document.getElementById("torf").style.display="none";
		document.getElementById("solution").style.display="none";
		
		exampaper_form.exam_paper_tab.value="";
		exampaper_form.exam_paper_solution.value="";
		
		if(exam_paper_type==EXAM_PAPER_TYPE.RADIO||exam_paper_type==EXAM_PAPER_TYPE.CHECKBOX){
			document.getElementById("choose").style.display="";
			changeChoose();
		}
		else if(exam_paper_type==EXAM_PAPER_TYPE.TORF){
			document.getElementById("torf").style.display="";
			changeTorfSolution();
		}
		else if(exam_paper_type==EXAM_PAPER_TYPE.INPUT||exam_paper_type==EXAM_PAPER_TYPE.TEXTAREA){
			document.getElementById("solution").style.display="";
		}
	}
	
	/**
	 * 回填选项内容
	 */
	function changeChoose(){
		var choose_table =document.getElementById("choose_table");
		var rows = choose_table.rows.length;
		
		var code_str="A";
		var code = code_str.charCodeAt(); 
		var exam_paper_tab="";
		
		for(var i=0;i<rows-1;i++){
			document.getElementById("choose_code["+i+"]").value=String.fromCharCode(code+i);
			
			var choose_code = document.getElementById("choose_code["+i+"]").value;
			var choose_name = document.getElementById("choose_name["+i+"]").value;
			exam_paper_tab=exam_paper_tab+choose_code+"#:"+choose_name+"#;";
		}
		exampaper_form.exam_paper_tab.value=exam_paper_tab.substring(0,exam_paper_tab.length-2);
		
		//回填选项答案
		changeChooseSolution();
	}
	
	/**
	 * 回填选项答案
	 */
	function changeChooseSolution(){
		var choose_table =document.getElementById("choose_table");
		var rows = choose_table.rows.length;
		var exam_paper_type=exampaper_form.exam_paper_type.value;
		
		var exam_paper_solution="";
		for(var i=0;i<rows-1;i++){
			var choose_solution = document.getElementById("choose_solution["+i+"]").value;
			var choose_code = document.getElementById("choose_code["+i+"]").value;
			if(choose_solution==GLOBAL_YES_NO.YES){   //判断是否为答案
				if(choose_code!=""&&choose_code!=null){
					if(exam_paper_type==EXAM_PAPER_TYPE.RADIO&&exam_paper_solution!=""){
						//document.getElementById("choose_code["+i+"]").value="02";
						//document.getElementById("choose_code["+i+"]").options[1].selected=true;
						$(this).alertmsg("error", "该题为单选题不能选择多个答案！");
						return;
					}
					exam_paper_solution=exam_paper_solution+choose_code;
				}
				else{
					$(this).alertmsg("error", "请先输入选项内容，再选择答案！");
					return;
				}
			}
		}
		
		exampaper_form.exam_paper_solution.value=exam_paper_solution;
	}
	
	/**
	 * 回填判断答案
	 */
	function changeTorfSolution(){
		exampaper_form.exam_paper_solution.value=exampaper_form.torf_solution.value;
	}
	</script>
	<div class="bjui-pageContent">
		<form action="txnep00003.do" id="exampaper_form" name="exampaper_form" data-toggle="validate" data-callback="saveCallback">
			<input type="hidden" name="exam_paper_id" value="${exam_paper_id}">
			<input type="hidden" name="item_bank_id" value="${item_bank_id}">
			<input type="hidden" name="exam_paper_type_s" value="${exam_paper_type}">
			<input type="hidden" name="exam_paper_tab" value="${exam_paper_tab}">
			<input type="hidden" name="exam_paper_status" value="01">
			<table class="table table-condensed table-hover" width="100%">
				<tbody>
					<tr>
						<td>
							<label class="control-label x85">考题类型：</label>
							<select name="exam_paper_type" data-toggle="selectpicker" data-val="${exam_paper_type}"  data-width="200" onchange="checkPaperType()">
								<%
									com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
									List<String[]> exam_paper_typeL=cache.getDictItem("exam_paper_type");
								%>
								<c:forEach items="<%=exam_paper_typeL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
						</td>
						<td>
							<label for="exam_paper_code" class="control-label x85">考题编码：</label>
							<input type="text" name="exam_paper_code" value="${exam_paper_code}" size="20" readonly/>
						</td>
						<td>
							<label for="exam_paper_score" class="control-label x85">分值：</label>
							<input type="text" name="exam_paper_score" value="${exam_paper_score}" size="20"/>
						</td>
					</tr>
					
					<tr>
						<td colspan="3">
							<label for="exam_paper_name" class="control-label x85">题目：</label>
							<textarea type="text" name="exam_paper_name" data-rule="required" data-toggle="autoheight" cols="120" rows="2">${exam_paper_name}</textarea>
						</td>
					</tr>
					
					<tr id="solution">
						<td colspan="3">
							<label for="exam_paper_solution" class="control-label x85">答案：</label>
							<textarea type="text" name="exam_paper_solution" data-rule="required" data-toggle="autoheight" cols="120" rows="2">${exam_paper_solution}</textarea>
						</td>
					</tr>
					
					<tr id="torf">
						<td>
							<label class="control-label x85">是否正确：</label>
							<select name="torf_solution" data-toggle="selectpicker" data-width="200" onchange="changeTorfSolution()">
								<%
									List<String[]> yesOrNoL=cache.getDictItem("yes_or_no");
								%>
								<c:forEach items="<%=yesOrNoL%>" var="itemA">
									<option value="${itemA[0]}">${itemA[1]}</option>
    							</c:forEach>
                            </select>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div id="choose">
				<table class="table table-bordered table-hover table-striped" data-toggle="tabledit" width="70%" id="choose_table">
					<thead>
						<tr>
							<th title="选项代码" width="5%"><input type="text" id="choose_code[#index#]" placeholder="文本框" readonly></th>
							<th title="选项描述" width="50%"><input type="text" id="choose_name[#index#]" placeholder="文本框" data-rule="required" onchange="changeChoose()"></th>
							 <th title="是否为答案" width="5%">
							 	<select id="choose_solution[#index#]" data-toggle="selectpicker" data-width="50" onchange="changeChooseSolution()">
									<c:forEach items="<%=yesOrNoL%>" var="itemA">
										<c:choose>
											<c:when test="${itemA[0]=='02'}">
												<option value="${itemA[0]}" selected>${itemA[1]}</option>
											</c:when>
											<c:otherwise>
												<option value="${itemA[0]}">${itemA[1]}</option>
											</c:otherwise>
										</c:choose>
    								</c:forEach>
							 	</select>
							 </th>
							<th title="" data-addtool="true" width="5%">
                				<a href="#" class="btn btn-red row-del" data-confirm-msg="确定要删除该行信息吗？">删</a>
            				</th>
						</tr>
					</thead>
				</table>
			</div>
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