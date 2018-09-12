<%@page import="com.scwe.dss.pagebean.designevaluation.DesignEvaluationPageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="true" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% DesignEvaluationPageBean pageBean = (DesignEvaluationPageBean) session.getAttribute("PageBean"); 
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>海绵城市规划</title>
	 	<link rel="stylesheet" href="arcgis_js/dijit/themes/claro/claro.css">
	<link rel="stylesheet" href="arcgis_js/esri/css/esri.css" />
	<link rel="stylesheet" href="arcgis_js/dojox/grid/resources/claroGrid.css">	
  <style type="text/css">
      body, html { font-family:helvetica,arial,sans-serif; font-size:90%; }
  </style>
  <script src="js/javascript.js" type="text/javascript" language="JavaScript"></script>  
  <script src="arcgis_js/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
        
  <style>
    html, body, #BMapToggle {  
      position: absolute;  
      right: 0px;  
      top: 0px;  
      z-index: 50;
 			opacity: 0.8;
    }  
    #PageHeader {  
      position: absolute;  
      left: 70px;  
      top: 0px;  
      z-index: 50;
      opacity: 0.8;
    }
		#info {
      position: absolute;
      left: 0;
      bottom: 0;
      padding: 4px 0 0 4px;
      background-color: #ddd;
      font: 10px Segoe UI;
      text-align: center;
      border-radius: 0 10px 0 0;
    } 
		#RainDataDiv {
      position: absolute;
      right: 0px;
      bottom: 0px;
      width: 300px;
      height:200px;
      z-index: 50;      
      opacity: 0.8;
    }    
  </style>

  <script>
	  var visible = [];
		var myMap;	  
    var xStr;
    var xRain;    
    var cTitle;
    var totalVolumn;
    require([
			"dojox/charting/plot2d/Grid",
      "dojox/charting/Chart2D",
      "dojox/charting/plot2d/Columns",
      "dojox/charting/themes/Wetland",  
      "dojox/charting/axis2d/Default",
    	"esri/basemaps",
      "esri/IdentityManager",
      "esri/layers/GraphicsLayer",
      "esri/layers/FeatureLayer",
      "esri/dijit/FeatureTable",
      "esri/geometry/webMercatorUtils",
      "esri/map",
      "dojo/dom-construct",
      "dojo/dom",
      "dojo/ready",
      "dojo/on", 
      "dojo/parser",      
      "dojo/_base/lang",
      "dijit/registry",
      "dijit/form/Button",
      "dijit/layout/ContentPane",
      "dijit/layout/BorderContainer",
      "esri/dijit/BasemapToggle",
      "esri/layers/ArcGISDynamicMapServiceLayer",
      "dojo/colors",
      "esri/symbols/SimpleFillSymbol",
      "esri/symbols/SimpleLineSymbol",
      "esri/dijit/Legend",
      "esri/renderers/SimpleRenderer",      
      "esri/renderers/ClassBreaksRenderer",  
      "esri/renderers/UniqueValueRenderer",
      "esri/layers/LayerDrawingOptions",      
      "esri/tasks/query",
      "esri/tasks/QueryTask",
      "dojox/grid/DataGrid",
      "dojo/data/ItemFileWriteStore",
      "dojo/domReady!"
    ], function (
    	Grid, Chart2D, Columns, Wetland, Default, esriBasemaps,IdentityManager, GraphicsLayer, FeatureLayer, FeatureTable, webMercatorUtils, Map, domConstruct, dom, ready, on, parser, lang,registry, Button, ContentPane, 
    	BorderContainer, BasemapToggle, ArcGISDynamicMapServiceLayer,Color,SimpleFillSymbol, SimpleLineSymbol, Legend, SimpleRenderer, ClassBreaksRenderer, 
    	UniqueValueRenderer,LayerDrawingOptions, Query, QueryTask, DataGrid, ItemFileWriteStore
    ) {
      ready(function(){
		 		esriBasemaps.delorme = {
          baseMapLayers: [
              //中国矢量地图服务
              { url: "http://cache1.arcgisonline.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer" }
          ],
          //缩略图
          thumbnailUrl: "images/mapIcon.jpg",
          title: "矢量图"
		    };		

		 		var myRenderer;
		 		var myInfoTemplate;
        var tabContainer = dijit.byId("MyTab");	 
        var mapImpervTab, mapRunoffCoefTab, mapCapDepthTab;  
        
        var IsRunoffCoefTabStarted = false;
        var IsImpervTabStarted = false;        
        var IsRunoffCoefColorTabStarted = false;
        var IsImpervColorTabStarted = false;        
        var IsCapDepthTabStarted = false;
        var IsMaxFlowTabStarted = false;
        var IsMajSysCapDepthTabStarted = false;

        //var xStr = ["0:05","0:10","0:15","0:20","0:25","0:30","0:35","0:40","0:45","0:50","0:55","1:00","1:05","1:10","1:15","1:20","1:25","1:30","1:35","1:40","1:45","1:50","1:55","2:00"];
        //var xRain = [100,123,145,154,178,184,194,185,150,130,120,110,100,90,80,70,60,50,40,35,34,17,13,3];    
        xStr = <%=pageBean.xAxisStr%>;
        xRain = <%=pageBean.xAxisData%>;    
        cTitle = "<%=pageBean.defaultRainDesc%>";
        totalVolumn = "<%=pageBean.totalVolumnStr%>";

        on(dom.byId("SRunoffUp"),"click",function(){getTableData("sRunoffDataPrev", "sRunoffNode")});             
        on(dom.byId("SRunoffDown"),"click",function(){getTableData("sRunoffDataNext", "sRunoffNode")});                     
  			
  			dojo.connect(tabContainer,"_transition", function(newTab, oldTab){
        	if (newTab.id == "SRunoffTab"){
	      	} else if (newTab.id == "ImpervTab"){
	      		if(! IsImpervTabStarted){
		      		myRenderer = getImpervTabRenderer("IMPERV");
		      		myInfoTemplate = new esri.InfoTemplate("汇水区:${FID} / IMPERV:${IMPERV}", "${*}");
		      		mapImpervTab = tabRenderer("ImpervNode", mapImpervTab, "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer,"ImpervLegend", "汇水区不透水率",3);
		      		IsImpervTabStarted = true;
	      		}
	      	} else if (newTab.id == "ImpervColorTab"){
	      		if(! IsImpervColorTabStarted){
		      		myRenderer = getColorTabRenderer("IMPERV", 0, 100);
		      		myInfoTemplate = new esri.InfoTemplate("汇水区:${FID} / IMPERV:${IMPERV}", "${*}");
		      		mapImpervTab = colorRenderer("ImpervColorNode", mapImpervTab, "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer,"ImpervColorLegend", "汇水区不透水率", 3);
		      		IsImpervColorTabStarted = true;
	      		}	      		
	      	}	else if (newTab.id == "RunoffCoefTab"){
	      		if(! IsRunoffCoefTabStarted){
		      		myRenderer = getRunoffCoefTabRenderer("RUNOFFCOEF");
		      		myInfoTemplate = new esri.InfoTemplate("汇水区:${FID} / RUNOFFCOEF:${RUNOFFCOEF}", "${*}");
		      		mapRunoffCoefTab = tabRenderer("RunoffCoefNode", mapRunoffCoefTab, "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer,"RunoffCoefLegend", "汇水区综合雨量径流系数",3);
		      		IsRunoffCoefTabStarted = true;
	      		}
	      	}	else if (newTab.id == "RunoffCoefColorTab"){
	      		if(! IsRunoffCoefColorTabStarted){
		      		myRenderer = getColorTabRenderer("RUNOFFCOEF", 0, 1);
		      		myInfoTemplate = new esri.InfoTemplate("汇水区:${FID} / RUNOFFCOEF:${RUNOFFCOEF}", "${*}");
		      		mapRunoffCoefTab = colorRenderer("RunoffCoefColorNode", mapRunoffCoefTab, "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer,"RunoffCoefColorLegend", "汇水区综合雨量径流系数", 3);
		      		IsRunoffCoefColorTabStarted = true;
	      		}
	      	} else if (newTab.id == "CapDepthTab"){ 
	      		if(! IsCapDepthTabStarted){
		      		myRenderer = getUVRenderer("CAPDEPTH", "1", "TAG=''&CAPDEPTH=1");
		      		myInfoTemplate = new esri.InfoTemplate("排水管:${FID} / CAPDEPTH:${CAPDEPTH}", "${*}");
		      	  capDepthTabRenderer("CapDepthNode", "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer, "TAG = ''","CapDepthLegend", "排水能力评估(满管)", 2);   
		      		IsCapDepthTabStarted = true;
	      		}
	      	} else if (newTab.id == "MaxFlowTab") {
	      		if(! IsMaxFlowTabStarted){
		      		myRenderer = getUVRenderer("TAG", "Major_System", "Major_System&MAXFLOW>1");
		      		myInfoTemplate = new esri.InfoTemplate("排水管:${FID} / MAXFLOW:${MAXFLOW}", "${*}");
		      	  capDepthTabRenderer("MaxFlowNode", "http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer, "MAXFLOW > 1","MaxFlowLegend", "排水能力评估(检查井)", 2);   
			      	IsMaxFlowTabStarted = true;
	      		}
	      	}	else if (newTab.id == "MajSysCapDepthTab"){
	      		if(! IsMajSysCapDepthTabStarted){
	      			myRenderer = getMajSysCapDepthTabRenderer("CAPDEPTH");
		      		myInfoTemplate = new esri.InfoTemplate("排水管:${FID} / CAPDEPTH:${CAPDEPTH}", "${*}");
		      		capDepthTabRenderer("MajSysCapDepthNode","http://123.206.254.127:6080/arcgis/rest/services/hebi_pre_shape_1016/MapServer", myInfoTemplate, myRenderer,"TAG='Major_System'","MajSysCapDepthLegend", "内涝风险区划", 2);
		      		IsMajSysCapDepthTabStarted = true; 
	      		}
        	}
        });

      });      
    
    function tabRenderer(nodeName, mapTab, sUrlService, myInfoTemplate, cbRenderer, legendNode, legendTitle, layerNum) {
    	mapTab = new Map(nodeName, {
	        center: [114.2992, 35.745091],	// 117.1564,39.094
	        zoom: 11,
	        basemap: "delorme",
	        opacity: 0	        
	      });

	    var sUrl = new ArcGISDynamicMapServiceLayer(sUrlService, {
        opacity: 0.7
      });
      //定义一个要素图层
      var myLayer = sUrlService + "/" + layerNum;
      var featureLayer = new FeatureLayer(
    		myLayer,
       {
          outFields: ["*"],
          infoTemplate: myInfoTemplate,
          opacity: 0
       });
      mapTab.addLayer(featureLayer);                      
      mapTab.addLayer(sUrl);      
		
		  var arrayLayerDrawingOptions = [];
      var layerDrawingOptions = new LayerDrawingOptions();
      layerDrawingOptions.renderer = cbRenderer;
      arrayLayerDrawingOptions[layerNum] = layerDrawingOptions;
      sUrl.setLayerDrawingOptions(arrayLayerDrawingOptions);
      
      mapTab.on("load",function (){
    	  mapTab.hideZoomSlider();
				getLegend(mapTab, sUrl, legendNode, legendTitle).startup();
      }); 
      
      return mapTab;
    }
    
    function colorRenderer(nodeName, mapTab, sUrlService, myInfoTemplate, cbRenderer, legendNode, legendTitle, layerNum){  
    	mapTab = new Map(nodeName, {
	        center: [114.2992, 35.745091],	// 117.1564,39.094
	        zoom: 11,
	        basemap: "delorme",
	        opacity: 0	        
	      });
        var myLayer = sUrlService + "/" + layerNum;        
        var featureLayer = new FeatureLayer(myLayer, {
          outFields: ["*"],
          infoTemplate: myInfoTemplate
        });
            
        featureLayer.setRenderer(cbRenderer);
        mapTab.addLayer(featureLayer);
        mapTab.on("load",function (){
      	  mapTab.hideZoomSlider();
		  		getLegend(mapTab, featureLayer, legendNode, legendTitle).startup();
        });         
	  		
        return mapTab;
    }

    function getLegend(mapTab, featureLayer, legendNode, myTitle){
      var myLegend = new Legend({
          map: mapTab,
          layerInfos: [{ title: myTitle, layer: featureLayer }]
        }, legendNode);
      return myLegend;      	
    }
    
    function getImpervTabRenderer(nodeName){
      var symDefault = new SimpleFillSymbol().setColor(new Color([255, 255, 0]));    	
      var cbRenderer = new ClassBreaksRenderer(symDefault, nodeName);  // "IMPERV"
      cbRenderer.addBreak({
       	 label: "0~20%",
         minValue: 0,
         maxValue: 20,
         symbol: new SimpleFillSymbol().setColor(new Color([0, 127, 255]))
      });
      cbRenderer.addBreak({
       	 label: "20~40%",
         minValue: 20,
         maxValue: 40,
         symbol: new SimpleFillSymbol().setColor(new Color([0, 127, 0]))
      });
      cbRenderer.addBreak({
       	 label: "40~60%",
         minValue: 40,
         maxValue: 60,
         symbol: new SimpleFillSymbol().setColor(new Color([255, 255, 0]))
      });
      cbRenderer.addBreak({
         label: "60~80%",
         minValue: 60,
         maxValue: 80,
         symbol: new SimpleFillSymbol().setColor(new Color([255, 127, 0]))
      });
      cbRenderer.addBreak({
         label: "80~100%",
         minValue: 80,
         maxValue: 100,
         symbol: new SimpleFillSymbol().setColor(new Color([255, 0, 0]))
      });
      return cbRenderer;
    }
    function getColorTabRenderer(nodeName, minValue, maxValue){
        var renderer = new SimpleRenderer(new SimpleFillSymbol().setOutline(new SimpleLineSymbol().setWidth(0.2).setColor(new Color([0,0,0]))));
        renderer.setColorInfo({
          field: nodeName,
          minDataValue: minValue,
          maxDataValue: maxValue,
          colors: [
            new Color([255, 255, 255]),
            new Color([255, 0, 0])
          ]
        }); 
        return renderer;
    }
    function getRunoffCoefTabRenderer(nodeName){
        var symDefault = new SimpleFillSymbol().setColor(new Color([255, 255, 0]));    	
        var cbRenderer = new ClassBreaksRenderer(symDefault, nodeName);  
        cbRenderer.addBreak({
       	   label: "0.0~0.2mm",
        	 minValue: 0,
           maxValue: 0.2,
           symbol: new SimpleFillSymbol().setColor(new Color([0, 127, 255]))
        });
        cbRenderer.addBreak({
         	 label: "0.2~0.4mm",
           minValue: 0.2,
           maxValue: 0.4,
           symbol: new SimpleFillSymbol().setColor(new Color([0, 127, 0]))
        });
        cbRenderer.addBreak({
         	 label: "0.4~0.6mm",
           minValue: 0.4,
           maxValue: 0.6,
           symbol: new SimpleFillSymbol().setColor(new Color([255, 255, 0]))
        });
        cbRenderer.addBreak({
         	 label: "0.6~0.8mm",
           minValue: 0.6,
           maxValue: 0.8,
           symbol: new SimpleFillSymbol().setColor(new Color([255, 127, 0]))
        });
        cbRenderer.addBreak({
         	 label: "0.8~1.0mm",
           minValue: 0.8,
           maxValue: 1.0,
           symbol: new SimpleFillSymbol().setColor(new Color([255, 0, 0]))
        });
        return cbRenderer;
      }

    function getMajSysCapDepthTabRenderer(nodeName){
        var cbRenderer = new ClassBreaksRenderer(null, nodeName);  
        cbRenderer.addBreak({
           label: "0.6~0.8",
           minValue: 0.6,
           maxValue: 0.8,
           symbol: new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([0, 127, 0]), 3)
        });
        cbRenderer.addBreak({
         	 label: "0.8~1.0",
           minValue: 0.8,
           maxValue: 1.0,
           symbol: new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255,255, 0]), 3)
        });
        cbRenderer.addBreak({
         	 label: "1.0+",
           minValue: 1.0,
           maxValue: Infinity,
		       symbol: new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0]), 3)
        });
        return cbRenderer;
      }

    function getUVRenderer(nodeName, nValue, nTitle){
    	var uvRenderer = new UniqueValueRenderer(null, nodeName);
	    uvRenderer.addValue({
	        value: nValue,
	        symbol: new SimpleLineSymbol(SimpleLineSymbol.STYLE_SOLID, new Color([255, 0, 0]), 3),
	        label: nTitle,
	        description: "Description"
	      });	    
	    return uvRenderer;
    }
    
    function capDepthTabRenderer(tabName, sUrlService, myInfoTemplate, uvRenderer, whereStr, legendNode, legendTitle, layerNum){     
    	mapTab = new Map(tabName, {
	        center: [114.2992, 35.745091],	// 117.1564,39.094
	        zoom: 11,      
	        basemap: "delorme",
	        opacity: 0	        
	      });
	    var sUrl = new ArcGISDynamicMapServiceLayer(sUrlService, {
	        opacity: 0.7
	      });        
	    var myLayer = sUrlService + "/" + layerNum;
    	var featureLayer = new FeatureLayer(
    				myLayer, 
      		{
            infoTemplate: myInfoTemplate,
            outFields: ["*"]
          });
    	featureLayer.setDefinitionExpression(whereStr);
      featureLayer.setRenderer(uvRenderer);
      mapTab.addLayer(featureLayer);   
      mapTab.addLayer(sUrl);   

      mapTab.on("load",function () {
    	  mapTab.hideZoomSlider();
	  		getLegend(mapTab, featureLayer, legendNode, legendTitle).startup();
	      dojo.byId("RainDataDiv").innerHTML = getRainDataChart("RainDataDiv", cTitle, xStr, xRain, totalVolumn).innerHTML;	  
      }); 
    }
		function getRainDataChart(nodeName, cTitle, xStr, xRain, totalVolumn){
    		var cFontSize = "normal normal normal 8pt Arial";
    		var xyFontSize = "normal normal normal 6pt Arial";
    		var xTitle = "时间序列(min)";
    		var yTitle = "降雨量(mm) / 总量:" + totalVolumn + "mm";
        
        var chart = new Chart2D(nodeName,{	title: cTitle, titleFont: cFontSize, titleFontColor: "black" });        
        chart.addPlot("default", { type: Columns, gap: 3 });
        // Add axes  
        var myXLabelFunc = function(text, value, precision){ return xStr[text-1]; };             
        chart.addAxis("x", { title: xTitle, titleFont: xyFontSize, titleOrientation:"away",rotation:-90, majorTick:{length:3},minorTick:{length:3}, labelFunc: myXLabelFunc });
        chart.addAxis("y", { title: yTitle, titleFont: xyFontSize, vertical:true, fixLower: "major", fixUpper: "major", min:0,
        	  font: xyFontSize, //            	  fontColor:"red",
        	  majorTick: {length:3},
        	  minorTick: {length:3}
        });              
        chart.setTheme(Wetland);
        chart.addSeries("降雨序列", xRain);

        chart.addPlot("Grid", { type: Grid,
					         hMajorLines: true,
					         hMinorLines: true,
					         vMajorLines: false,
					         majorHLine: {width: 0.2},
         					 minorHLine: {width: 0.1}
					});
        chart.render(); 
        return chart.node;
		}    
  });    
    
  </script>
      
</head>

<body class="claro">
  <div style="width: 100%; height: 100%;">   
  	<div id="BMapToggle"></div> 
    <div id="LayersDiv"></div>
		<div id="RainDataDiv"></div>    
    <div id="MapBorder" dojoType="dijit.layout.BorderContainer" style="width: 100%; height: 100%">   
      <div id="Title" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'" align="center">设计方案结果展示 (<%=pageBean.getDefaultPrjName()%>/<%=pageBean.getDefaultDsnName()%>/<%=pageBean.getDefaultINPName()%>/<%=pageBean.getDefaultRPTName()%>)</div>
	    <div id="MyTab" data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center'" style="height:100%">
        <div data-dojo-type="dijit/layout/ContentPane" id="SRunoffTab" title="年径流总量控制率评估" splitter="true" style="height:100%">
        	<div align="center"><button id="SRunoffUp" type="button">上一页</button>
			         <img src="images/clear.gif" width="5" height="1" border="0" alt=""/><button id="SRFPageStr" type="button" style="border:0;background:none"><%=pageBean.getSRFPageStr()%></button><img src="images/clear.gif" width="5" height="1" border="0" alt=""/> 
        			 <button id="SRunoffDown" type="button">下一页</button>
        	</div>
        	<div id="sRunoffNode" style="width: 100%; height: 100%"></div>
        </div>        
        <div data-dojo-type="dijit/layout/ContentPane" id="ImpervTab" title="不透水率参数图"><div id="ImpervNode" style="width:100%;height:100%;"></div><div id="info"><div id="ImpervLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="ImpervColorTab" title="不透水率参数渐变色图"><div id="ImpervColorNode" style="width:100%;height:100%;"></div><div id="info"><div id="ImpervColorLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="RunoffCoefTab" title="综合雨量径流系数图"><div id="RunoffCoefNode" style="width:100%;height:100%;"></div><div id="info"><div id="RunoffCoefLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="RunoffCoefColorTab" title="综合雨量径流系数渐变色图"><div id="RunoffCoefColorNode" style="width:100%;height:100%;"></div><div id="info"><div id="RunoffCoefColorLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="CapDepthTab" title="排水能力评估图(满管状态)"><div id="CapDepthNode" style="width:100%;height:100%;"></div><div id="info"><div id="CapDepthLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="MaxFlowTab" title="排水能力评估(检查井溢流)"><div id="MaxFlowNode" align="center" style="width:100%;height:100%;"></div><div id="info"><div id="MaxFlowLegend"></div></div></div>
        <div data-dojo-type="dijit/layout/ContentPane" id="MajSysCapDepthTab" title="内涝风险区划图"><div id="MajSysCapDepthNode" align="center" style="width:100%;height:100%;"></div><div id="info"><div id="MajSysCapDepthLegend"></div></div></div>
  	  </div>	    
		</div>
	</div>	
</body>            

</html>