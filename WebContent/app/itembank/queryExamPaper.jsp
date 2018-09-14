<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	//局部更新
	function do_open_layout(event, treeId, treeNode) {
	    if (treeNode.isParent) {
	        var zTree = $.fn.zTree.getZTreeObj(treeId);
	        
	        zTree.expandNode(treeNode);
	        return;
	    }
	    $(event.target).bjuiajax('doLoad', {url:treeNode.url+"?item_bank_id="+treeNode.id+"&item_bank_name="+treeNode.name, target:treeNode.divid});
	    event.preventDefault();
	}
	
	</script>
        
	<div style="float:left; width:200px;height:99.9%;background:#ccffcc;">
		<ul id="item-tree" class="ztree" data-toggle="ztree" data-expand-all="false" data-on-click="do_open_layout">
			${treeStr}
		</ul>
	</div>
	<div style="margin-left:210px; height:99.9%; overflow:hidden;">
		<div style="height:100%; overflow:hidden;">
            <fieldset style="height:100%;">
                <legend></legend>
                <div id="layout-item" style="height:94%; overflow:hidden;">
                    
                </div>
            </fieldset>
        </div>
	</div>
</body>
</html>