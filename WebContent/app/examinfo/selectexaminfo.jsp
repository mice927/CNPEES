<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%
	com.springmvc.util.DictItemCache cache = com.springmvc.util.DictItemCache.getInstance();
	String item_bank_type=cache.getDictItemJson("item_bank_type");
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
		 
	 });
	
	 	/**
		 * 查看试题
		 */
		function viewExampaper(){
			var selectLen=getTableSelectedLen('examinfo_user_listT');
			if(selectLen==0){
				$(this).alertmsg("error", "请选择要查看的行！");
			}
			else{
				var exam_info_userlist_id=getTableSelected('examinfo_user_listT',1);
				$(this).dialog({id:'viewexaminfo', 
					url:"txnea00008.do?exam_info_userlist_id="+exam_info_userlist_id,
					title:'测试详情',
					max:true});
			}
		}
	
	</script>

	<div class="pull-left">
		<button type="button" class="btn-blue" title="查看试题" onclick="viewExampaper()">查看试题</button>&nbsp;
     	<input type="hidden" name="exam_info_id" id="exam_info_id" value="${office_id}">
		<input type="text" name="exam_info_code" value="${exam_info_code}" size="100" readonly>
    </div>
	<div style="width:100%; height:98%; overflow:auto;">
		<table  data-toggle="datagrid" 
			data-options="{filterThead:false,showCheckboxcol:true,paging:{pageSize:${total},selectPageSize:'' }}"
			data-data=${dataList}
			id="examinfo_user_listT"
		>
		
			<thead>
    			<tr>
	    			<th data-options="{name:'exam_info_userlist_id', width:200,hide:true}">exam_info_userlist_id</th>
	            	<th data-options="{name:'usercode', width:200}">用户代码</th>
	            	<th data-options="{name:'username', width:200}">用户姓名</th>
	            	<th data-options="{name:'score', width:200}">分数</th>
	            	<th data-options="{name:'paper_status', width:200,hide:true}">状态</th>
        		</tr>
			</thead>
		</table>
	</div>

</body>
</html>