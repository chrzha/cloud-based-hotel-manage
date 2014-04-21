<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>
      云中心管理员界面
    </title>
    <%
    String path = request.getContextPath();
    %>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">


    <link 
        rel="stylesheet"
        type="text/css"
        href="${pageContext.request.contextPath}/webresource/jquery-easy-ui/themes/default/easyui.css">

  </link>
  <link 
      rel="stylesheet"
      type="text/css"
      href="${pageContext.request.contextPath}/webresource/jquery-easy-ui/themes/icon.css">

</link>
<link 
    rel="stylesheet"
    type="text/css"
    href="${pageContext.request.contextPath}/webresource/jquery-easy-ui/themes/tree_themes/SimpleTree.css">

</link>

<script src="${pageContext.request.contextPath}/webresource/hotel-js/jquery-1.7.2.min.js">
</script>
<script src="${pageContext.request.contextPath}/webresource/jquery-easy-ui/jquery.easyui.min.js">
</script>
<script src="${pageContext.request.contextPath}/webresource/jquery-easy-ui/tabs.js">
</script>
<script src="${pageContext.request.contextPath}/webresource/jquery-easy-ui/themes/SimpleTree.js">
</script>
<script type="text/javascript">
 $(document).ready(function(){
	$("#exit").click(function(){
	
		 $.messager.confirm('确认', '确定要退出系统吗?', function(r){  
		 	if(r){
		 	$.post("/hotel/logout",null,function(data){
			if(data=="true"){
				window.location="/hotel/login";
				}
			});
		 }
                
       });  
		
	});
	
	$("#adminValidate").click(function(){
		if($("#tabs").tabs('exists','管理员审核')){
			$("#tabs").tabs('select','管理员审核');
			}else{
				$('#tabs').tabs('add',{
				title: '管理员审核',
				content: '<iframe style="width:100%;height:480px;" src="/hotel/adminValidate"></iframe>',
				closable: true
			});
		}
	});
	
	$("#hotelsValidate").click(function(){
		if($("#tabs").tabs('exists','酒店审核')){
			$("#tabs").tabs('select','酒店审核');
		}else{
			$('#tabs').tabs('add',{
				title: '酒店审核',
				content: '<iframe style="width:100%;height:480px;" src="/hotel/hotelsValidate"></iframe>',
				closable: true
			});
		}
	});
	
	
	$("#changePwd").click(function(){
		if($("#tabs").tabs('exists','修改密码')){
			$("#tabs").tabs('select','修改密码');
			}else{
			$('#tabs').tabs('add',{
				title: '修改密码',
				content: '<iframe style="width:100%;height:480px;" src="/hotel/changePwd"></iframe>',
				closable: true
			});
		}
	});
	
});
 
</script>
<style>
#userInfo {
	margin:5px;padding:5px; border:solid 1px #ccc;line-height:150%;
}

.tree{
	margin:5px;padding:5px; border:solid 1px #ccc;line-height:150%;height:380px;
}
</style>
</head>
<body class="easyui-layout" >
  <div region="north" style="height:70px;background-color:#99ccff;">
    <div style="width:500px;height:30px;margin-left:30%;"><h1>基于云模式的酒店服务信息后台管理系统</h1></div>
  </div>

<div region="west" split="true" style="width:220px;" title="导航菜单">
    <div id="userInfo">
        <div style="background-color:#99ccff;padding:2px 5px;font-weight:bold;">登录信息</div>
        用户名：${user.userName}<br />
        邮&nbsp;&nbsp;箱：${user.email}<br />
        电&nbsp;&nbsp;话：${user.phone}<br />
    </div>

    <div class="">
   
    <ul class="easyui-tree" data-options="animate:true,lines:true">
		<li data-options="state:'closed'">
			<span>管理员审核与管理</span>
			<ul>
				<li>
					<a id="adminValidate">管理员审核</a>
				</li>
				<li>
					<span>管理员管理</span>
				</li>
			</ul>
		</li>
		<li data-options="state:'closed'">
			<span>酒店审核与管理</span>
			<ul>
				<li>
					<a id="hotelsValidate">酒店审核</a>
				</li>
				<li>
					<span>酒店管理</span>
				</li>
			</ul>
		</li>
		<li data-options="state:'closed'">
			<span>公共信息审核与管理</span>
			<ul>
				<li>
					<span>公共信息审核</span>
				</li>
				<li>
					<span>公共信息管理</span>
				</li>
			</ul>
		</li>
		<li>
			<a id="changePwd">修改密码</a>
		</li>
		<li>
			<a id="exit">注销登录</a>
		</li>
	</ul>
    
    
    </div>
</div>

<div region="center">
  <div id="tabs" class="easyui-tabs" fit="true" border="false">
    <div title="系统首页">
     <p style="text-align:center;font-size:medium;color:#666;border-bottom:#ccc 1px solid;"><b>基于云模式的酒店服务信息后台管理系统</b></p>
        <p style="padding-left:2em;font-weight:bold;">基于云模式的酒店服务信息后台管理系统须知：</p>
        <p style="padding-left:2em;">
            <span style="color:#ff0000;font-weight:bold;">1. 第一次登录后务必修改自己的密码，以确保操作安全。</span><br />
        <span id="conLabel"></span>
    
    
    </div>
  </div>
</div>
<div region="south" style="height:40px;">
<jsp:include page="copyright.jsp"></jsp:include>
</div>


</body>

</html>