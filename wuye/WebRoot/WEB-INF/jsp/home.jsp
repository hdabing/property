﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.wuye.model.Managers"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>小区物业后台系统</title>
	<link href="style/authority/main_css.css" rel="stylesheet" type="text/css" />
	<link href="style/authority/zTreeStyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="scripts/zTree/jquery.ztree.core-3.2.js"></script>
	<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
	<script type="text/javascript">
		/**退出系统**/
		function logout(){
			if(confirm("您确定要退出本系统吗？")){
				window.location.href = "log_logout.action";
			}
		}
		
		/**获得当前日期**/
		function  getDate01(){
			//var time = new Date();
			//var myYear = time.getFullYear();
			//var myMonth = time.getMonth()+1;
			//var myDay = time.getDate();
			//var myTime = time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
			//if(myMonth < 10){
			//	myMonth = "0" + myMonth;
			//}
			//if(time.getHours() < 10){
			//	myTime = "0" + time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
			//}
			//document.getElementById("yue_fen").innerHTML =  myYear + "." + myMonth + "." + myDay;
			//document.getElementById("day_day").innerHTML =  myTime;

			var today,year,month,day,hour,minute,second,weekday,strDate;
            today = new Date();
            weekday = today.getDay();
            switch(weekday) {
                case 0:{
                    strDate = "星期日";
                } break;
                case 1:{
                    strDate = "星期一";
                } break;
                case 2:{
                    strDate = "星期二";
                } break;
                case 3:{
                    strDate = "星期三";
                } break;
                case 4:{
                    strDate = "星期四";
                } break;
                case 5:{
                    strDate = "星期五";
                } break;
                case 6:{
                    strDate = "星期六";
                } break;
            }
            year = today.getFullYear();
            month = today.getMonth() + 1;
            day = today.getDate();
            hour = today.getHours();
            minute = today.getMinutes();
            second = today.getSeconds();
            if ( (""+month).length < 2 ) month = "0" + month;
            if ( (""+day).length < 2 ) day = "0" + day;
            if ( (""+hour).length < 2 ) hour = "0" + hour;
            if ( (""+minute).length < 2 ) minute = "0" + minute;
            if ( (""+second).length < 2 ) second = "0" + second;
            //window.status = strDate + " " + year + "-"+ month + "-"+ day + " "+ hour + ":"+ minute + ":" + second;//显示在浏览器窗口状态栏  <body onload="showtime();">
            //document.getElementById('time').innerHTML = year + "年" + month + "月" + day + "日" +"  "+ strDate +"  " + hour + ":" + minute + ":" + second; //显示时间
            document.getElementById("yue_fen").innerHTML = year + "." + month + "." + day;
            document.getElementById("day_day").innerHTML =  hour + ":" + minute + ":" + second;
        	setTimeout("getDate01()","1000");
		}
		
		/**加入收藏夹**/
		function addfavorite(){
			var ua = navigator.userAgent.toLowerCase();
			 if (ua.indexOf("360se") > -1){
			  	art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'由于360浏览器功能限制，加入收藏夹功能失效', ok:true,});
			 }else if (ua.indexOf("msie 8") > -1){
			  	window.external.AddToFavoritesBar('${dynamicURL}/authority/loginInit.action','西宁市公共租赁住房信息管理系统管理');//IE8
			 }else if (document.all){
			  	window.external.addFavorite('${dynamicURL}/authority/loginInit.action','西宁市公共租赁住房信息管理系统管理');
			 }else{
			  	art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'添加失败，请用ctrl+D进行添加', ok:true,});
			 }
		} 
	</script>
	<script type="text/javascript">
		/* zTree插件加载目录的处理  */
		var zTree;
		
		var setting = {
				view: {
					dblClickExpand: false,
					showLine: false,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				data: {
					key: {
						name: "resourceName"
					},
					simpleData: {
						enable:true,
						idKey: "resourceID",
						pIdKey: "parentID",
						rootPId: ""
					}
				},
				callback: {
	// 				beforeExpand: beforeExpand,
	// 				onExpand: onExpand,
					onClick: zTreeOnClick			
				}
		};
		 
		var curExpandNode = null;
		function beforeExpand(treeId, treeNode) {
			var pNode = curExpandNode ? curExpandNode.getParentNode():null;
			var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
			for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
				if (treeNode !== treeNodeP.children[i]) {
					zTree.expandNode(treeNodeP.children[i], false);
				}
			}
			while (pNode) {
				if (pNode === treeNode) {
					break;
				}
				pNode = pNode.getParentNode();
			}
			if (!pNode) {
				singlePath(treeNode);
			}
	
		}
		function singlePath(newNode) {
			if (newNode === curExpandNode) return;
			if (curExpandNode && curExpandNode.open==true) {
				if (newNode.parentTId === curExpandNode.parentTId) {
					zTree.expandNode(curExpandNode, false);
				} else {
					var newParents = [];
					while (newNode) {
						newNode = newNode.getParentNode();
						if (newNode === curExpandNode) {
							newParents = null;
							break;
						} else if (newNode) {
							newParents.push(newNode);
						}
					}
					if (newParents!=null) {
						var oldNode = curExpandNode;
						var oldParents = [];
						while (oldNode) {
							oldNode = oldNode.getParentNode();
							if (oldNode) {
								oldParents.push(oldNode);
							}
						}
						if (newParents.length>0) {
							for (var i = Math.min(newParents.length, oldParents.length)-1; i>=0; i--) {
								if (newParents[i] !== oldParents[i]) {
									zTree.expandNode(oldParents[i], false);
									break;
								}
							}
						}else {
							zTree.expandNode(oldParents[oldParents.length-1], false);
						}
					}
				}
			}
			curExpandNode = newNode;
		}
	
		function onExpand(event, treeId, treeNode) {
			curExpandNode = treeNode;
		}
		
		/** 用于捕获节点被点击的事件回调函数  **/
		function zTreeOnClick(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("dleft_tab1");
			zTree.expandNode(treeNode, null, null, null, true);
	// 		zTree.expandNode(treeNode);
			// 规定：如果是父类节点，不允许单击操作
			if(treeNode.isParent){
	// 			alert("父类节点无法点击哦...");
				return false;
			}
			// 如果节点路径为空或者为"#"，不允许单击操作
			if(treeNode.accessPath=="" || treeNode.accessPath=="#"){
				//alert("节点路径为空或者为'#'哦...");
				return false;
			}
		    // 跳到该节点下对应的路径, 把当前资源ID(resourceID)传到后台，写进Session
		    rightMain(treeNode.accessPath);
		    
		    if( treeNode.isParent ){
			    $('#here_area').html('当前位置：'+treeNode.getParentNode().resourceName+'&nbsp;>&nbsp;<span style="color:#1A5CC6">'+treeNode.resourceName+'</span>');
		    }else{
			    $('#here_area').html('当前位置：系统&nbsp;>&nbsp;<span style="color:#1A5CC6">'+treeNode.resourceName+'</span>');
		    }
		};
		
		/* 上方菜单 */
		function switchTab(tabpage,tabid){
		var oItem = document.getElementById(tabpage).getElementsByTagName("li"); 
		    for(var i=0; i<oItem.length; i++){
		        var x = oItem[i];    
		        x.className = "";
			}
			if('left_tab1' == tabid){
				$(document).ajaxStart(onStart).ajaxSuccess(onStop);
				// 异步加载"业务模块"下的菜单
			  	loadMenu('YEWUMOKUAI', 'dleft_tab1');
			}else  if('left_tab2' == tabid){
				$(document).ajaxStart(onStart).ajaxSuccess(onStop);
				// 异步加载"系统管理"下的菜单
				loadMenu('XITONGMOKUAI', 'dleft_tab1');
			}else  if('left_tab3' == tabid){
				$(document).ajaxStart(onStart).ajaxSuccess(onStop);
				// 异步加载"其他"下的菜单
				loadMenu('QITAMOKUAI', 'dleft_tab1');
			} 
		}
		
		
		$(document).ready(function(){
			$(document).ajaxStart(onStart).ajaxSuccess(onStop);
			/** 默认异步加载"业务模块"目录  **/
			loadMenu('YEWUMOKUAI', "dleft_tab1");
			// 默认展开所有节点
			if( zTree ){
				// 默认展开所有节点
				zTree.expandAll(false);
			}
		});
		var data = null;
		function loadMenu(resourceType, treeObj){
			$.ajax({
				type:"POST",
				url:"${dynamicURL}menu_queryAllMenu.action?type=" + resourceType,
				dataType : "json",
				success:function(data){
					// 如果返回数据不为空，加载"业务模块"目录
					if(data != null){
						// 将返回的数据赋给zTree
						$.fn.zTree.init($("#"+treeObj), setting, data);
 						//alert(treeObj);
						zTree = $.fn.zTree.getZTreeObj(treeObj);
						if( zTree ){
							// 默认展开所有节点
							zTree.expandAll(false);
						}
					}
				}
			});
            
            // 如果返回数据不为空，加载"业务模块"目录
            if(data != null){
                // 将返回的数据赋给zTree
                $.fn.zTree.init($("#"+treeObj), setting, data);
//              alert(treeObj);
                zTree = $.fn.zTree.getZTreeObj(treeObj);
                if( zTree ){
                    // 默认展开所有节点
                    zTree.expandAll(false);
                }
            }
		}
		
		//ajax start function
		function onStart(){
			$("#ajaxDialog").show();
		}
		
		//ajax stop function
		function onStop(){
	// 		$("#ajaxDialog").dialog("close");
			$("#ajaxDialog").hide();
		}
</script>
</head>
<body onload="getDate01()">
    <div id="top">
		<div id="top_logo">
			<img alt="logo" src="images/common/logo.jpg" width="274" height="49" style="vertical-align:middle;" />
		</div>
		<div id="top_links">
			<div id="top_op">
				<ul>
					<li>
						<img alt="当前用户" src="images/common/user.jpg" />：
						<%
							HttpSession s = request.getSession();
							Managers m = (Managers)s.getAttribute("manager");
						%>
						<span><%=m.getName() %></span>
					</li>
					<li>
						<img alt="事务月份" src="images/common/month.jpg" />：
						<span id="yue_fen"></span>
					</li>
					<li>
						<img alt="今天是" src="images/common/date.jpg" />：
						<span id="day_day"></span>
					</li>
				</ul> 
			</div>
			<div id="top_close">
				<a href="javascript:void(0);" onclick="logout();" target="_parent">
					<img alt="退出系统" title="退出系统" src="images/common/close.jpg" style="position: relative; top: 10px; left: 25px;" />
				</a>
			</div>
		</div>
	</div>
    <!-- side menu start -->
	<div id="side">
		<div id="left_menu">
		 	<ul id="TabPage2" style="height:200px; margin-top:50px;">
				<li id="left_tab1" class="selected" onclick="javascript:switchTab('TabPage2','left_tab1');" title="业务模块">
					<img alt="业务模块" title="业务模块" src="images/common/1_hover.jpg" width="33" height="31" />
				</li>
				<li id="left_tab2" onclick="javascript:switchTab('TabPage2','left_tab2');" title="系统管理">
					<img alt="系统管理" title="系统管理" src="images/common/2.jpg" width="33" height="31" />
				</li>		
				<li id="left_tab3" onclick="javascript:switchTab('TabPage2','left_tab3');" title="其他">
					<img alt="其他" title="其他" src="images/common/3.jpg" width="33" height="31" />
				</li>
			</ul>
			
			
			<div id="nav_show" style="position:absolute; bottom:0px; padding:10px;">
				<a href="javascript:;" id="show_hide_btn">
					<img alt="显示/隐藏" title="显示/隐藏" src="images/common/nav_hide.png" width="35" height="35" />
				</a>
			</div>
		 </div>
		 <div id="left_menu_cnt">
		 	<div id="nav_module">
		 		<img src="images/common/module_1.png" width="210" height="58"/>
		 	</div>
		 	<div id="nav_resource">
		 		<ul id="dleft_tab1" class="ztree"></ul>
		 	</div>
		 </div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#TabPage2 li').click(function(){
				var index = $(this).index();
				$(this).find('img').attr('src', 'images/common/'+ (index+1) +'_hover.jpg');
				$(this).css({background:'#fff'});
				$('#nav_module').find('img').attr('src', 'images/common/module_'+ (index+1) +'.png');
				$('#TabPage2 li').each(function(i, ele){
					if( i!=index ){
						$(ele).find('img').attr('src', 'images/common/'+ (i+1) +'.jpg');
						$(ele).css({background:'#044599'});
					}
				});
				// 显示侧边栏
				switchSysBar(true);
			});
			
			// 显示隐藏侧边栏
			$("#show_hide_btn").click(function() {
		        switchSysBar();
		    });
		});
		
		/**隐藏或者显示侧边栏**/
		function switchSysBar(flag){
			var side = $('#side');
	        var left_menu_cnt = $('#left_menu_cnt');
			if( flag==true ){	// flag==true
				left_menu_cnt.show(500, 'linear');
				side.css({width:'280px'});
				$('#top_nav').css({width:'77%', left:'304px'});
	        	$('#main').css({left:'280px'});
			}else{
		        if ( left_menu_cnt.is(":visible") ) {
					left_menu_cnt.hide(10, 'linear');
					side.css({width:'60px'});
		        	$('#top_nav').css({width:'100%', left:'60px', 'padding-left':'28px'});
		        	$('#main').css({left:'60px'});
		        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
		        } else {
					left_menu_cnt.show(500, 'linear');
					side.css({width:'280px'});
					$('#top_nav').css({width:'77%', left:'304px', 'padding-left':'0px'});
		        	$('#main').css({left:'280px'});
		        	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
		        }
			}
		}
	</script>
    <!-- side menu start -->
    <div id="top_nav">
	 	<span id="here_area">当前位置：系统&nbsp;>&nbsp;系统介绍</span>
	</div>
    <div id="main">
      	<iframe name="right" id="rightMain" src="introduce.jsp" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true" />
    </div>

</body>
</html>
   
 