<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- jsp:useBean id="ecust" type="com.hydroone.ecustomer.web.servlet.ECSessionContext" scope="session" -->
<!-- % com.hydroone.ecustomer.web.contractor.ECContractorCableLocatePage pageBean = (com.hydroone.ecustomer.web.contractor.ECContractorCableLocatePage) ecust.getPageBean(); % -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
		<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<title>iWorker</title>
	<!-- %@ include file="../includes/javascript.jsp" % -->
	<!-- %@ include file="section_heading_contractor.jsp" % -->
<style type="text/css">
body {background-image:url(/60.jpg);}
</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=UVLH0gCGD5ePoZqcqSZmlQbgoUkoaCug"></script>
</head>
<body>
<%
String lData =(String)request.getAttribute( "LocationData"); //LocationData.java
%>
<form method="post" action="ECAction" name="cable_locate">
<!-- input type="hidden" name="Page" value="" -->
</form>
<div id="allmap"></div>
</body>
</html>

<script type="text/javascript">
	// 百度地图API功能
	var locationData = <%=lData%>
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(locationData[0].longitude, locationData[0].latitude), 16);  // 初始化地图,设置中心点坐标和地图级别  new BMap.Point(116.404, 39.915)
	
	var opts = {
			width : 250,     // 信息窗口宽度
			height: 80,     // 信息窗口高度
			enableMessage:true//设置允许信息窗发送短息
  };

	var dotArray=new Array();
	var dot;
	var label;
	var content;
	var marker;
	for (var i=0;i<locationData.length;i++)	{
		dot = new BMap.Point(locationData[i].longitude, locationData[i].latitude);
		title = locationData[i].id + " " + locationData[i].time;
		content = "纬度: " + locationData[i].latitude + "<br/>经度: " + locationData[i].longitude + " <img style='float:right;margin:4px' id='imgDemo' src='" + locationData[i].description + "' width='30' height='30' title='天安门'/>";
		marker = new BMap.Marker(dot);
		
		map.addOverlay(marker);  
		addClickHandler(title,content,marker);
	
//		dotArray.push(dot);
	}
	
	function addClickHandler(title, content,marker){
		marker.addEventListener("mouseover",function(e){
			openInfo(title,content,e)}
		);
	}
	function openInfo(title, content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		opts.title=title;
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	
	//var tracing = new BMap.Polyline(dotArray, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
	//map.addOverlay(tracing);
	
	function getAttr(){
		var p = marker.getPosition();       //获取marker的位置
		var msg = marker.getLabel();       //获取marker的位置
		alert(msg.content + " " + p.lng + " " + p.lat);   
	}
	
	map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
	map.enableScrollWheelZoom(true);   //开启鼠标滚轮缩放
</script>
