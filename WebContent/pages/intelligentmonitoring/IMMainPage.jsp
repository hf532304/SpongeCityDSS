<%@page import="com.scwe.dss.pagebean.IntelligentMonitoring.IMMainPageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% IMMainPageBean pageBean = (IMMainPageBean) session.getAttribute("PageBean"); 
String jsonSensorInfo = pageBean.getJsonSensorInfo();
%>
<html>
	<head>
	  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	  <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
	  <title>PictureMarkerSymbol</title>
	  <link rel="stylesheet" href="arcgis_js/dijit/themes/claro/claro.css" />
	  <link rel="stylesheet" href="arcgis_js/esri/css/esri.css">
	  <script src="http://js.arcgis.com/3.20/init.js"></script>     
    <script src="js/javascript.js" type="text/javascript" language="JavaScript"></script>
	  
    <style>
        html, body, #map {
            height: 100%;
            width: 100%;
            margin: 0;
            padding: 0;
        }
    </style>
		<script>
        var map;

        require(["esri/map", "esri/basemaps","esri/geometry/Point", "esri/SpatialReference","esri/symbols/PictureMarkerSymbol",
            "esri/graphic","dojo/dom-construct", "esri/dijit/InfoWindow"
        ], function(Map, esriBasemaps, Point, SpatialReference, PictureMarkerSymbol,Graphic,domConstruct, InfoWindow) {
            esriBasemaps.ChineseMap = {
	 		          baseMapLayers: [
	 		              //中国矢量地图服务
	 		              { url: "http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer" }
	 		          ],
	 		          //缩略图
	 		          thumbnailUrl: "images/mapIcon.jpg",
	 		          title: "矢量图"
	 				    };	
         map = new Map("map", {  
             basemap: "ChineseMap",  
             center: [117.1564,39.094],  // 117.1564,39.094
             zoom: 15
         });         	

         map.on("load", function() {
       	  var sensors = <%=jsonSensorInfo%>;
      		for(var i=0;i<sensors.length;i++){        			
		        addMarker(sensors[i].id,sensors[i].name,sensors[i].desc,sensors[i].lon,sensors[i].lat);    	
      		}
         	 map.graphics.on("click",function(e){  
   	         //1/得到xhr对象  
   	         var xhr=getXHR();  
   	         //2.注册状态变化监听器  
   	         xhr.onreadystatechange=function(){  
//                if(xhr.readyState==4) {  
//                  if(xhr.status==200) {
//                	  tmpStr = xhr.responseText;
                //  }  
                //}  
        		  	map.infoWindow.setTitle(e.graphic.attributes.SensorID + " / " + e.graphic.attributes.SensorName + "<br/>纬度：" + e.mapPoint.getLatitude() + "<br/>经度：" + e.mapPoint.getLongitude());
        		  	if (xhr.responseText==""){
                  map.infoWindow.setContent("没有更新的数据");        		  		
        		  	} else {
        		  		var jData = eval('(' + xhr.responseText + ')');
                	map.infoWindow.setContent(jData.time + "<br/>温度 = "+jData.parameter1+"<br/>PH值 = "+jData.parameter2+"<br/>COD = "+jData.parameter3+"<br/>叶绿素 = "+jData.parameter4);
   	         		}
                map.infoWindow.show(e.screenPoint, map.getInfoWindowAnchor(e.screenPoint));
   	         }  
   	         //3.建立与服务器的连接  
   	         xhr.open("GET","Page"+"?Name=Ajax&SensorId="+e.graphic.attributes.SensorID+"&CurrentPageBean=SensorData");  
   	         //4.向服务器发出请求  
   	         xhr.send();  

             e.stopPropagation();
         	 });
         	
         });
         
         map.on("click",function(e){
            map.infoWindow.setTitle("Map clicked");
            map.infoWindow.setContent(e.mapPoint.getLongitude() + " " + e.mapPoint.getLatitude());
            map.infoWindow.show(e.screenPoint, map.getInfoWindowAnchor(e.screenPoint));
         });
         
         function addMarker(sId, sName, sDesc, x, y) {
             var point = new esri.geometry.Point(x, y,new esri.SpatialReference({wkid:4326}));
             var symbol = new esri.symbol.PictureMarkerSymbol("images/icon_location.png", 21, 33);
             var attributes = {
                     "SensorID": sId,
                     "SensorName": sName,
                     "SensorDesc": sDesc,
                     "SensorLon":	x,
                     "SensorLat":	y,
                     "SensorLei":	0
             };
             var graphic = new Graphic(point, symbol, attributes, null);
             map.graphics.add(graphic);
         };      
         
       });
    </script>
</head>
<div id="map"></div>
</body>
</html>