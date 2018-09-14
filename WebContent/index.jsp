<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登陆</title>
<link rel = "Shortcut Icon" href="images/CNPE.ico"> 
<script src="BJUI/js/jquery-1.11.3.min.js"></script>
<script src="BJUI/js/jquery.cookie.js"></script>
<link href="BJUI/themes/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
* {font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
.login_bg{position:absolute; float:left; z-index:1; margin:0; padding:0; }
a:link {color: #285e8e;}
.main_box {
    position: absolute; top:50%; left:50%; margin-top:-260px; margin-left: -300px; padding: 30px; width:600px; height:460px;
    background: #FAFAFA; background: rgba(255,255,255,0.5); border: 1px #DDD solid;
    border-radius: 5px;
    -webkit-box-shadow: 1px 5px 8px #888888; -moz-box-shadow: 1px 5px 8px #888888; box-shadow: 1px 5px 8px #888888;
}
.main_box .setting {position: absolute; top: 5px; right: 10px; width: 10px; height: 10px;}
.main_box .setting a {color: #FF6600;}
.main_box .setting a:hover {color: #555;}
.login_logo {text-align: center;}
.login_form {padding-top: 20px; font-size: 16px;}
.login_box .form-control {display: inline-block; *display: inline; zoom: 1; width: auto; font-size: 18px;}
.login_box .form-control.x319 {width: 319px;}
.login_box .form-control.x164 {width: 164px;}
.login_box .form-group {margin-bottom: 20px;}
.login_box .form-group label.t {width: 120px; text-align: right; cursor: pointer;}
.login_box .form-group.space {padding-top: 15px; border-top: 1px #FFF dotted;}
.login_box .form-group img {margin-top: 1px; height: 32px; vertical-align: top;}
.login_box .m {cursor: pointer;}
.bottom {text-align: center; font-size: 12px;}
</style>
<script type="text/javascript">

function addCookie(name,value,days,path){   /**添加设置cookie**/  
  var name = escape(name);  
  var value = escape(value);  
  var expires = new Date();  
  expires.setTime(expires.getTime() + days * 3600000 * 24);  
  //path=/，表示cookie能在整个网站下使用，path=/temp，表示cookie只能在temp目录下使用  
  path = path == "" ? "" : ";path=" + path;  
  //GMT(Greenwich Mean Time)是格林尼治平时，现在的标准时间，协调世界时是UTC  
  //参数days只能是数字型  
  var _expires = (typeof days) == "string" ? "" : ";expires=" + expires.toUTCString();  
  document.cookie = name + "=" + value + _expires + path;  
}  
function getCookieValue(name){  /**获取cookie的值，根据cookie的键获取值**/  
  //用处理字符串的方式查找到key对应value  
  var name = escape(name);  
  //读cookie属性，这将返回文档的所有cookie  
  var allcookies = document.cookie;         
  //查找名为name的cookie的开始位置  
  name += "=";  
  var pos = allcookies.indexOf(name);      
  //如果找到了具有该名字的cookie，那么提取并使用它的值  
  if (pos != -1){                                             //如果pos值为-1则说明搜索"version="失败  
      var start = pos + name.length;                  //cookie值开始的位置  
      var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置  
      if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie  
      var value = allcookies.substring(start,end); //提取cookie的值  
      return (value);                           //对它解码        
  }else{  //搜索失败，返回空字符串  
      return "";  
  }  
}  
function deleteCookie(name,path){   /**根据cookie的键，删除cookie，其实就是设置其失效**/  
  var name = escape(name);  
  var expires = new Date(0);  
  path = path == "" ? "" : ";path=" + path;  
  document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;  
}

/**实现功能，保存用户的登录信息到cookie中。当登录页面被打开时，就查询cookie**/  
function getCookie(){
  var usercodeValue = getCookieValue("usercode");
  document.getElementById("usercode").value = usercodeValue;  
  var userPassValue = getCookieValue("password");
  document.getElementById("password").value = unescape(userPassValue);  
  if(usercodeValue!=""&&usercodeValue!=null&&userPassValue!=""&&userPassValue!=null){
	  document.getElementById("j_remember").checked=true;
  }
}  

//登录
function login()
{
	var usercode=document.getElementById("usercode").value;
	var password=document.getElementById("password").value;
	if(usercode==""||usercode==null){
		alert("请输入用户名");
	}
	else{
		var objChk = document.getElementById("j_remember");
		if(objChk.checked){  
	        //添加cookie  
	        addCookie("usercode",usercode,7,"/");  
	        addCookie("password",password,7,"/");
	    }else{
	    	//删除cookie  
	        deleteCookie("usercode","/");  
	        deleteCookie("password","/");
	    }  
		
		document.forms[0].submit();
	}
}
//回车登录
function funcSubmitKeydown(){
	getCookie();
	
	var backgRoundImg = document.getElementById('backgRoundImg');
	backgRoundImg.style.width = document.documentElement.clientWidth + "px";
	backgRoundImg.style.height = document.documentElement.clientHeight + "px";
	
	document.getElementById("error").style.display="none";
	var error=document.getElementById("error").innerHTML;
	if(error!=""&&error!=null){
		alert(document.getElementById("error").innerHTML);
	}
}
</script>
</head>

<body onload="funcSubmitKeydown()">
<div class="login_bg"><img src="images/loginbg.jpg" name="backgRoundImg" id='backgRoundImg' /></div>
<div class="main_box" style="filter:alpha(opacity=80);position: absolute; z-index:3; top:45%; left:55%;">
	<div class="login_box">
        <div class="login_logo">
            <img src="images/logo.png">
        </div>
        <div class="login_form">
    		<form action="txn99999.do" id="login_form" method="post">
    			<div class="form-group">
    				<label for="usercode" class="t">用户名：</label> <input id="usercode" value="" name="usercode" type="text" class="form-control x319 in" autocomplete="off">
    			</div>
    			<div class="form-group">
    				<label for="password" class="t">密　码：</label> <input id="password" value="" name="password" type="password" class="form-control x319 in">
    			</div>
    			<div class="form-group">
                    <label class="t"></label>
                    <label for="j_remember" class="m"><input id="j_remember" type="checkbox" value="true">&nbsp;记住登陆账号!</label>
    			</div>
    			<div class="form-group space">
    				<span id="error">${error}</span>
                    <label class="t"></label>　　　
    				<input type="button" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;" class="btn btn-primary btn-lg" onclick="login()">&nbsp;&nbsp;&nbsp;&nbsp;
    				<input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
    			</div>
    		</form>
        </div>
	</div>
</div>

</body>
</html>