<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge; charset=UTF-8">
<title>培训测试系统</title>
<link rel = "Shortcut Icon" href="images/CNPE.ico">
<!-- bootstrap - css -->
<link href="BJUI/themes/css/bootstrap.css" rel="stylesheet">
<!-- core - css -->
<link href="BJUI/themes/css/style.css" rel="stylesheet">
<link href="BJUI/themes/blue/core.css" id="bjui-link-theme" rel="stylesheet">
<!-- plug - css -->
<link href="BJUI/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
<link href="BJUI/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="BJUI/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
<link href="BJUI/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
<link href="BJUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<script src="BJUI/other/html5shiv.min.js"></script>
<script src="BJUI/other/respond.min.js"></script>
<!-- jquery -->

<!-- jquery -->
<script src="BJUI/js/jquery-1.7.2.min.js"></script>
<script src="BJUI/js/jquery.cookie.js"></script>
<script src="BJUI/other/jquery.iframe-transport.js"></script> 

<!-- BJUI核心js -->
<script src="BJUI/js/bjui-all.js"></script>

<!-- plugins -->
<!-- swfupload for uploadify && kindeditor -->
<script src="BJUI/plugins/swfupload/swfupload.js"></script>
<!-- kindeditor -->
<script src="BJUI/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
<script src="BJUI/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
<!-- colorpicker -->
<script src="BJUI/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
<!-- ztree -->
<script src="BJUI/plugins/ztree/jquery.ztree.all-3.5.js"></script>
<!-- nice validate -->
<script src="BJUI/plugins/niceValidator/jquery.validator.js"></script>
<script src="BJUI/plugins/niceValidator/jquery.validator.themes.js"></script>
<!-- bootstrap plugins -->
<script src="BJUI/plugins/bootstrap.min.js"></script>
<script src="BJUI/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
<script src="BJUI/plugins/bootstrapSelect/defaults-zh_CN.min.js"></script>
<!-- icheck -->
<script src="BJUI/plugins/icheck/icheck.min.js"></script>
<!-- dragsort -->
<script src="BJUI/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
<!-- HighCharts -->
<script src="BJUI/plugins/highcharts/highcharts.js"></script>
<script src="BJUI/plugins/highcharts/highcharts-3d.js"></script>
<script src="BJUI/plugins/highcharts/themes/gray.js"></script>
<!-- ECharts -->
<script src="BJUI/plugins/echarts/echarts.js"></script>
<!-- other plugins -->
<script src="BJUI/plugins/other/jquery.autosize.js"></script>
<link href="BJUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<script src="BJUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="BJUI/plugins/download/jquery.fileDownload.js"></script>

<script src="app/js/util.js"></script>
<script src="app/js/json.js"></script>
<!-- init -->
<script type="text/javascript">
$(function() {
    BJUI.init({
        JSPATH       : 'BJUI/',         //[可选]框架路径
        PLUGINPATH   : 'BJUI/plugins/', //[可选]插件路径
        loginInfo    : {url:'index.jsp', title:'登录', width:400, height:200}, // 会话超时后弹出登录对话框
        statusCode   : {ok:200, error:300, timeout:301}, //[可选]
        ajaxTimeout  : 50000, //[可选]全局Ajax请求超时时间(毫秒)
        pageInfo     : {total:'total', pageCurrent:'pageCurrent', pageSize:'pageSize', orderField:'orderField', orderDirection:'orderDirection'}, //[可选]分页参数
        alertMsg     : {displayPosition:'topcenter', displayMode:'slide', alertTimeout:3000}, //[可选]信息提示的显示位置，显隐方式，及[info/correct]方式时自动关闭延时(毫秒)
        keys         : {statusCode:'statusCode', message:'message'}, //[可选]
        ui           : {
                         windowWidth      : 0,    //框架可视宽度，0=100%宽，> 600为则居中显示
                         showSlidebar     : true, //[可选]左侧导航栏锁定/隐藏
                         clientPaging     : true, //[可选]是否在客户端响应分页及排序参数
                         overwriteHomeTab : false //[可选]当打开一个未定义id的navtab时，是否可以覆盖主navtab(我的主页)
                       },
        debug        : true,    // [可选]调试模式 [true|false，默认false]
        theme        : 'sky' // 若有Cookie['bjui_theme'],优先选择Cookie['bjui_theme']。皮肤[五种皮肤:default, orange, purple, blue, red, green]
    })
    
    // main - menu
    $('#bjui-accordionmenu')
        .collapse()
        .on('hidden.bs.collapse', function(e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if ($a.hasClass('collapsed')) $heading.removeClass('active')
            })
        })
        .on('shown.bs.collapse', function (e) {
            $(this).find('> .panel > .panel-heading').each(function() {
                var $heading = $(this), $a = $heading.find('> h4 > a')
                
                if (!$a.hasClass('collapsed')) $heading.addClass('active')
            })
        })
    
    $(document).on('click', 'ul.menu-items > li > a', function(e) {
        var $a = $(this), $li = $a.parent(), options = $a.data('options').toObj()
        var onClose = function() {
            $li.removeClass('active')
        }
        var onSwitch = function() {
            $('#bjui-accordionmenu').find('ul.menu-items > li').removeClass('switch')
            $li.addClass('switch')
        }
        
        $li.addClass('active')
        if (options) {
            options.url      = $a.attr('href')
            options.onClose  = onClose
            options.onSwitch = onSwitch
            if (!options.title) options.title = $a.text()
            
            if (!options.target)
                $a.navtab(options)
            else
                $a.dialog(options)
        }
        
        e.preventDefault()
    })
    
    //时钟
    var today = new Date(), time = today.getTime()
    $('#bjui-date').html(today.formatDate('yyyy/MM/dd'))
    setInterval(function() {
        today = new Date(today.setSeconds(today.getSeconds() + 1))
        $('#bjui-clock').html(today.formatDate('HH:mm:ss'))
    }, 1000)
})

//菜单-事件
function MainMenuClick(event, treeId, treeNode) {
    event.preventDefault()
    
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj(treeId)
        
        zTree.expandNode(treeNode, !treeNode.open, false, true, true)
        return
    }
    
    if (treeNode.target && treeNode.target == 'dialog')
        $(event.target).dialog({id:treeNode.tabid, url:treeNode.url, title:treeNode.name})
    else
        $(event.target).navtab({id:treeNode.tabid, url:treeNode.url, title:treeNode.name, fresh:treeNode.fresh, external:treeNode.external})
}

</script>
<!-- link type="text/css" rel="stylesheet" href="BJUI/other/shCore.css"/ -->
<!-- link type="text/css" rel="stylesheet" href="BJUI/other/shThemeEclipse.css"/ -->
<!-- script type="text/javascript" src="BJUI/other/brush.js"></script -->
<!-- link href="BJUI/other/doc.css" rel="stylesheet" -->

</head>
<body>
    <!-- 主体框架 -->
	<div id="bjui-window">
		<!-- 页头框架 -->
		<header id="bjui-header">
			<!-- 左侧logo -->
			<div class="bjui-navbar-header">
				<button type="button" class="bjui-navbar-toggle btn-default" data-toggle="collapse" data-target="#bjui-navbar-collapse">
                	<i class="fa fa-bars"></i>
            	</button>
            	<a class="bjui-navbar-logo" href="#"><img src="images/logo1.png"></a>
			</div>
			<!-- 右侧功能列表 -->
			<nav id="bjui-navbar-collapse">
				<ul class="bjui-navbar-right">
					<!-- 日期时间信息 -->
					<li class="datetime"><div><span id="bjui-date"></span>&nbsp;&nbsp;<span id="bjui-clock"></span></div></li>
					<!-- 账户信息 -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${username} <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="changepwd.html" data-toggle="dialog" data-id="changepwd_page" data-mask="true" data-width="400" data-height="260">&nbsp;<span class="glyphicon glyphicon-lock"></span> 修改密码&nbsp;</a></li>
							<li><a href="#">&nbsp;<span class="glyphicon glyphicon-user"></span> 我的资料</a></li>
							<li class="divider"></li>
							<li><a href="index.jsp" class="red">&nbsp;<span class="glyphicon glyphicon-off"></span> 注销登陆</a></li>
						</ul>
					</li>
					<!-- 皮肤信息 -->
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">切换皮肤<span class="caret"></span></a>
                    	<ul class="dropdown-menu" role="menu">
                        	<li><a href="javascript:;" class="theme_default" data-toggle="theme" data-theme="default">&nbsp;<i class="fa fa-tree"></i> 黑白分明&nbsp;&nbsp;</a></li>
                        	<li><a href="javascript:;" class="theme_orange" data-toggle="theme" data-theme="orange">&nbsp;<i class="fa fa-tree"></i> 橘子红了</a></li>
                        	<li><a href="javascript:;" class="theme_purple" data-toggle="theme" data-theme="purple">&nbsp;<i class="fa fa-tree"></i> 紫罗兰</a></li>
                        	<li class="active"><a href="javascript:;" class="theme_blue" data-toggle="theme" data-theme="blue">&nbsp;<i class="fa fa-tree"></i> 天空蓝</a></li>
                        	<li><a href="javascript:;" class="theme_green" data-toggle="theme" data-theme="green">&nbsp;<i class="fa fa-tree"></i> 绿草如茵</a></li>
                   		</ul>
                	</li>
				</ul>
			</nav>
			<!-- 菜单栏 -->
			<div id="bjui-hnav">
				<button type="button" class="btn-default bjui-hnav-more-left" title="导航菜单左移"><i class="fa fa-angle-double-left"></i></button>
				<div id="bjui-hnav-navbar-box">
					<ul id="bjui-hnav-navbar">${showL}</ul>
				</div>
			</div>
		</header>
		
		<!-- 中间框架 -->
		<div id="bjui-container">
			<!-- 导航栏 -->
			<div id="bjui-leftside">
				<!-- 隐藏的导航栏 -->
				<div id="bjui-sidebar-s">
					<div class="collapse"></div>
				</div>
				<!-- 导航栏 -->
				<div id="bjui-sidebar">
					<div class="toggleCollapse"><h2><i class="fa fa-bars"></i> 菜单栏 <i class="fa fa-bars"></i></h2><a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a></div>
					<div class="panel-group panel-main" data-toggle="accordion" id="bjui-accordionmenu" data-heightbox="#bjui-sidebar" data-offsety="26">
					</div>
				</div>
			</div>
			
			<!-- 工作区 -->
			<div id="bjui-navtab" class="tabsPage">
				<!-- 工作区tab表头信息 -->
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navtab-tab nav nav-tabs">
                        	<li data-url="app/myHome/myHome.jsp"><a href="javascript:;"><span><i class="fa fa-home"></i> #maintab#</span></a></li>
                    	</ul>
					</div>
					<div class="tabsLeft"><i class="fa fa-angle-double-left"></i></div>
                	<div class="tabsRight"><i class="fa fa-angle-double-right"></i></div>
                	<div class="tabsMore"><i class="fa fa-angle-double-down"></i></div>
				</div>
				<ul class="tabsMoreList">
                	<li><a href="javascript:;">#maintab#</a></li>
            	</ul>
            	
				<!-- 工作区页面信息 -->
				<div class="navtab-panel tabsPageContent">
					<div class="navtabPage unitBox">
						<div class="bjui-pageContent" style="background:#FFF;">
							Loading...
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 页脚 -->
		<footer id="bjui-footer">Copyright &copy;　小白鼠团队　
		</footer>
	</div>

</body>
</html>