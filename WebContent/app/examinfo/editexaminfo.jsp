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
		 setButtonRole();
		 
		 checkItemBankCode();
		 var paper_str=document.getElementById("paper_str").value;
		 $("#paper").html(paper_str);
	 });
	
	//设置按钮权限
	 function setButtonRole(){
	 	document.getElementById("txnea00002").style.display="none";
	 	document.getElementById("txnea00003").style.display="none";
	 	var button_role=examinfo_form.button_role.value;
	 	if(button_role=="true"){
	 		document.getElementById("txnea00002").style.display="";
		 	document.getElementById("txnea00003").style.display="";
	 	}
	 }
	
	/**
	 * 页面显示控制
	 */
	function checkItemBankCode(){
		 document.getElementById("add").style.display="none";
		 document.getElementById("view").style.display="none";
		 document.getElementById("tab_t").style.display="none";
		 
		 var exam_info_id=examinfo_form.exam_info_id.value;
		 if(exam_info_id!=""&&exam_info_id!=null){
			 document.getElementById("view").style.display="";
			 document.getElementById("tab_t").style.display="";
			 checkSettings();
		 }
		 else{
			 document.getElementById("add").style.display="";
			 examinfo_form.start_time.value=getCurrentTime();
		 }
	}
	
	/**
	 * 判断是否加载试题
	 */
	function checkSettings(){
		 document.getElementById("creat_examinfo").style.display="none";
		 
		 var exam_info_userlist_id=document.getElementById("exam_info_userlist_id").value;
		 if(exam_info_userlist_id==""||exam_info_userlist_id==null){
			 document.getElementById("creat_examinfo").style.display="";
		 }
	}

	/**
	 * 保存用户回调
	 */
	function saveCallback(json){
		var exam_info_id=json.exam_info_id;
		if(exam_info_id!=""&&exam_info_id!=null){
			$(this).alertmsg("ok", "保存成功！");
			examinfo_form.exam_info_id.value=exam_info_id;
			
			document.getElementById("exam_info_code_view").innerHTML=examinfo_form.exam_info_code.value;
			document.getElementById("start_time_view").innerHTML=examinfo_form.start_time.value;
			document.getElementById("end_time_view").innerHTML=examinfo_form.end_time.value;
			document.getElementById("exam_info_note_view").innerHTML=examinfo_form.exam_info_note.innerHTML;
			
			checkItemBankCode();
		}
	}
	
	/**
	 * 结束本次测试
	 */
	function endExaminfo(){
		var exam_info_id=examinfo_form.exam_info_id.value;
		$(this).bjuiajax('doAjax', {
			url:"txnea00003.do?exam_info_id="+exam_info_id,
			confirmMsg:"确认提前结束？"
		});
	}
	
	/**
	 * 产生试题
	 */
	function creatExaminfo(){
		document.getElementById("creat_examinfo").style.disabled="disabled";
		
		var exam_info_id=examinfo_form.exam_info_id.value;
		var userid=document.getElementById("userid").value;
		var paper_status=document.getElementById("paper_status").value;
		
		if(exam_info_id!=""&&exam_info_id!=null){
			$(this).bjuiajax('doAjax', {
				url:"txnea00005.do?exam_info_id="+exam_info_id+"&userid="+userid+"&paper_status="+paper_status,
				callback:checkCallback
			});
		}
	}
	
	/**
	 * 判断用户回调
	 */
	function checkCallback(json){
		var message=json.message;
		var exam_info_userlist_id=json.exam_info_userlist_id;
		var paper_str=json.paper_str;
		if(exam_info_userlist_id!=""&&exam_info_userlist_id!=null){
			$(this).alertmsg("ok", message);
			document.getElementById("exam_info_userlist_id").value=exam_info_userlist_id;
			$("#paper").html(paper_str);
			checkSettings();
		}
		else{
			$(this).alertmsg("error", message);
			document.getElementById("creat_examinfo").style.disabled="";
		}
	}
	
	/**
	 * 保存/提交
	 */
	function submitExamPaper(type){
		var exam_info_userlist_id=document.getElementById("exam_info_userlist_id").value;
		var exam_paper_userlist_id=document.getElementById("exam_paper_userlist_id").value;
		var exam_paper_userlist_idA=exam_paper_userlist_id.split(",");
		var paperValue="";
		var total=0;
		if((type==1&&confirm("确认提交？"))||type==0){
			for(var i=0;i<exam_paper_userlist_idA.length;i++){
				var text = document.getElementsByName(exam_paper_userlist_idA[i])[0];
			    var textTagName = text.tagName;//获取标签类型
			    if(textTagName == 'INPUT'){//如果是input,判断是何种INPUT,checkbox、text等
			    	if(text.type=="checkbox"||text.type=="radio"){
			    		var checkedValue="";
						$("input[name='"+exam_paper_userlist_idA[i]+"']:checked").each(function(){checkedValue+=this.value+','});
						if(checkedValue!=""){
							checkedValue=checkedValue.replaceAll(",","");
							paperValue=paperValue+exam_paper_userlist_idA[i]+":"+checkedValue+";";
							total++;
						}
			    	}
			    }
			    else if(textTagName == 'TEXTAREA'){
			    	if(text.value!=null&&text.value!=""){
			    		paperValue=paperValue+exam_paper_userlist_idA[i]+":"+text.value+";";
			    		total++;
			    	}
			    }
			}
			if(type==1&&total!=exam_paper_userlist_idA.length&&!confirm("还有未作答的试题，确认提交？")){
				return;
			}
			if(paperValue!=""&&paperValue!=null){
				$(this).bjuiajax('doAjax', {
					url:"txnea00006.do?paperValue="+paperValue+"&type="+type+"&exam_info_userlist_id="+exam_info_userlist_id,
					callback:savePaperCallback
				});
			}
		}
	}
	
	/**
	 * 判断用户回调
	 */
	function savePaperCallback(json){
		var message=json.message;
		var paper_str=json.paper_str;
		$(this).alertmsg("ok", message);
		if(paper_str!=""&&paper_str!=null){
			$("#paper").html(paper_str);
		}
	}
	
	</script>
	<div class="bjui-pageContent">
		<ul class="nav nav-tabs" role="tablist">
                    <li class="active"><a href="#home" role="tab" data-toggle="tab">本次测试信息</a></li>
                    <li id="tab_t"><a href="#settings" role="tab" data-toggle="tab">测试试卷</a></li>
        </ul>
        <div class="tab-content">
        	<div class="tab-pane fade active in" id="home">
        		<form action="txnea00002.do" id="examinfo_form" name="examinfo_form" data-toggle="validate" data-callback="saveCallback">
					<input type="hidden" name="exam_info_id" value="${exam_info_id}">
					<input type="hidden" name="button_role" value="${button_role}">
					<input type="hidden" name="exam_info_status" value="01">
					<table class="table table-condensed table-hover" width="100%" id="add">
						<tbody>
							<tr>
								<td>
									<label for="exam_info_code" class="control-label x85">测试编码：</label>
									<input type="text" name="exam_info_code" data-rule="required" value="${exam_info_code}" size="20" readonly/>
								</td>
								<td>
									<label for="start_time" class="control-label x85">起始时间：</label>
									<input type="text" name="start_time" data-rule="required" value="" data-toggle="datepicker" size="20" data-pattern="yyyyMMdd"/>
								</td>
								<td>
									<label for="end_time" class="control-label x85">结束时间：</label>
									<input type="text" name="end_time" data-rule="required" value="" data-toggle="datepicker" size="20" data-pattern="yyyyMMdd"/>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<label for="exam_info_note" class="control-label x85">备注：</label>
									<textarea type="text" name="exam_info_note" data-toggle="autoheight" cols="120" rows="2"></textarea>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<button type="submit" id="txnea00002" class="btn-blue btn-lg" data-icon="save">产生新一轮的测试</button>
								</td>
								<td></td>
							</tr>
						</tbody>
					</table>
					
					<table class="table table-condensed table-hover" width="100%" id="view">
						<tbody>
							<tr>
								<td>
									<label for="exam_info_code" class="control-label x85">测试编码：</label><span id="exam_info_code_view">${exam_info_code}</span>
								</td>
								<td>
									<label for="start_time" class="control-label x85">起始时间：</label><span id="start_time_view">${start_time}</span>
								</td>
								<td>
									<label for="end_time" class="control-label x85">结束时间：</label><span id="end_time_view">${end_time}</span>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<label for="exam_info_note" class="control-label x85">备注：</label><span id="exam_info_note_view">${exam_info_note}</span>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<button type="button" class="btn-blue btn-lg" onclick="endExaminfo()" id="txnea00003">结束本次测试</button>
								</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</form>
        	</div>
        	<div class="tab-pane fade" id="settings">
        		<input type="hidden" name="exam_info_userlist_id" id="exam_info_userlist_id" value="${exam_info_userlist_id}"/>
        		<input type="hidden" name="userid" id="userid" value="${userid}"/>
        		<input type="hidden" id="paper_str" value='${paper_str}'/>
        		<input type="hidden" name="paper_status" id="paper_status" value="01"/>
        		<button type="button" class="btn-blue btn-lg" onclick="creatExaminfo()" id="creat_examinfo">获取测试试卷</button>
        		
        		<div id="paper"></div>
        	</div>
        </div>
		
	</div>
</body>
</html>