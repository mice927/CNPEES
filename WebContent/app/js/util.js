/**
 * 获取选中行某列的值，返回拼接的字符串
 */
function getTableSelected(tableId,colmunNum){
	colmunNum=colmunNum+1;
	var arr = [], trs = $('#'+tableId).data('selectedTrs');
	if(trs==null||trs==undefined){
		return "";
	}
	else{
		for(var i=0,j=trs.length;i<j;i++) {
			arr.push(trs[i].childNodes[colmunNum].innerText);
		}
		var str = arr.join(',');
		return str;
	}
}

/**
 * 获取选中行数量
 */
function getTableSelectedLen(tableId){
	var trs = $('#'+tableId).data('selectedTrs');
	if(trs==null||trs==undefined){
		return 0;
	}
	else{
		return trs.length;
	}
}

//选择选中的项
function S_NodeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getCheckedNodes(true);
    var ids = '', names = '';
    
    for (var i = 0; i < nodes.length; i++) {
    	if(nodes[i].pid!="0"){
    		ids   += ','+ nodes[i].id;
            names += ','+ nodes[i].name;
    	}
    }
    if (ids.length > 0) {
        ids = ids.substr(1), names = names.substr(1);
    }
    
    var $from = $('#'+ treeId).data('fromObj');
    
    if ($from && $from.length) $from.val(ids);
}
//选择项
function S_NodeClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    
    zTree.checkNode(treeNode, !treeNode.checked, true, true);
    event.preventDefault();
}

/**
 * 删除table所有行
 */
function deleteTableRows(tableId){
	var choose_table =document.getElementById(tableId);
	var rows = choose_table.rows.length;
	for (var i=1;i<rows;i++){
		 choose_table.deleteRow(i);
         rows=rows-1;
         i=i-1;
    }
}

//获取当前时间的信息
function getCurrentTime(){
	var d = new Date();
	var year  = d.getFullYear();
	var month  = d.getMonth()+ 1;
	var date  = d.getDate();
	var currentdate= "";
	currentdate = year.toString();
	if(month>=10){
		currentdate = currentdate+month.toString();
	}else{
		currentdate = currentdate + "0" + month.toString();
	}
	
	if(date>=10){
		currentdate = currentdate + date.toString();
	}else{
		currentdate = currentdate + "0" + date.toString();
	}
	return currentdate;
}


