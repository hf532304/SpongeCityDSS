//pop-up window for FAQ,and other screens requiring limited browser features
function openWindow(URL, x, y) {
  if (x == null) {
    x = 620;
  }
  if (y == null) {
    y = 450;
  }

  var params = "width=" + x + ",height=" + y + ",scrollbars=yes,location=no,staus=no,resizable=yes,top=30,left=10,scrollX=0,scrollY=0";
  myWindow=window.open(URL, "popWindow",  params );
  setTimeout("myWindow.focus()", 500);
}

//pop-up window for FAQ,and other screens requiring limited browser features
function openPageW(nextPageName, currentPageName, x, y) {
  if (x == null) {
	    x = 620;
  }
  if (currentPageName == null) {
	  currentPageName = "com.scwe.dss.pagebean.MyMainPageBean";
  }
  if (y == null) {
    y = 450;
  }
  var URL = "Page?Name="+nextPageName+"&CurrentPageBean="+currentPageName;
  var params = "width=" + x + ",height=" + y + ",toolbar=no,menubar=no,scrollbars=yes,location=no,staus=no,resizable=yes,top=30,left=10,scrollX=0,scrollY=0";
  myWindow=window.open(URL, "popWindow",  params );
  setTimeout("myWindow.focus()", 500);
}

//pop-up window with custom name and limited browser features
function openWindowName(URL, x, y, window_name) {
  if (x == null) {
    x = 620;
  }
  if (y == null) {
    y = 450;
  }

  var params = "width=" + x + ",height=" + y + ",scrollbars=yes,location=no,staus=no,resizable=yes,top=30,left=10,scrollX=0,scrollY=0";
  myWindow=window.open(URL, window_name,  params );
  setTimeout("myWindow.focus()", 500);
}

function closepopup()
{
	if(false == myWindow.closed)
	{
	myWindow.close();
	}
	
}
//pop-up window with full browser features
function openWindow2(URL, x, y) {
  if (x == null) {
    x = 700;
  }
  if (y == null) {
    y = 500;
  }

  var params = "width=" + x + ",height=" + y + ",scrollbars=yes,resizable=yes,top=30,left=10,status=yes,toolbar=yes,location=yes,menubar=yes,scrollX=0,scrollY=0";
  myWindow2=window.open(URL, "popWindow2",  params );
  setTimeout("myWindow2.focus()", 500);
}

//pop-up window for Printing
function openPrintWindow(URL, x, y) {
  if (x == null) {
    x = 700;
  }
  if (y == null) {
    y = 500;
  }

  var params = "width=" + x + ",height=" + y + ",scrollbars=yes,resizable=yes,top=30,left=10,status=yes,toolbar=no,location=no,menubar=yes,scrollX=0,scrollY=0";
  myWindow3=window.open(URL, "popWindow3",  params );
  setTimeout("myWindow3.focus()", 500);
}

//pop-up window on open file with custom name and limited browser features
function openFileWindow(URL, x, y, windowName) {
	if (x == null) {
		x = 620;
	}
	if (y == null) {
		y = 450;
	}
	// ie: window name can not contain space & hyphen
	if (navigator.appName == "Microsoft Internet Explorer" && ((windowName.search(" ") == 1) || (windowName.search("-") == 1))) {
		windowName=windowName.replace(" ","_");
		windowName=windowName.replace("-","_");// ie: window name can not contain 
	}
	var winFeatures = "width=" + x + ",height=" + y + "," + windowFeatures;
	myFileWindow=window.open('about:blank', windowName, winFeatures);
	try {
		myFileWindow.document.location.href = URL;
	} catch (e) {
		myFileWindow.close();
		myFileWindow=window.open(URL, windowName, winFeatures);
	}
	setTimeout("myFileWindow.focus()", 500);
}

function conduitsRenderer(myMap, layerUrl) {
  var queryTask = new esri.tasks.QueryTask(layerUrl);
  var query = new esri.tasks.Query();
  query.outFields = ["*"];
  query.returnGeometry = true;
  query.outSpatialReference = myMap.spatialReference;
  query.where = "TAG='Major_System'";
  queryTask.execute(query, function (featureSet) {
	var lineSymbol_red=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("red"), 3);
	var lineSymbol_yellow=new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new dojo.Color("yellow"), 3);
	var fill1=new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, null,null);
    var renderer = new esri.renderer.ClassBreaksRenderer(fill1, "MAXFLOW");
    renderer.addBreak(0.0001,0.2, new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, lineSymbol_yellow,null));
    renderer.addBreak(0.2,Infinity, new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID, lineSymbol_red,null));
              
    myMap.graphics.setRenderer(renderer);		
    var infoTemplate = new esri.InfoTemplate("${FID}","TAG=${TAG} MaxFlow=${MAXFLOW}");
    dojo.forEach(featureSet.features, function(feature) {
	  myMap.graphics.add(feature.setInfoTemplate(infoTemplate));
    });  
  });  
}

function cancelConduitsRenderer(myMap) {
  myMap.graphics.setRenderer(null);
  myMap.graphics.redraw();
}

function disableFeatureTable(MapBorder, TableBorder) {
	var borderContainer = dijit.byId(MapBorder);
	dojo.setStyle(borderContainer.domNode, 'height','100%');
	borderContainer.resize();        	
	borderContainer = dijit.byId(TableBorder);
	dojo.setStyle(borderContainer.domNode, 'height','0%');
	borderContainer.resize();
}

function loadFeatureTableTab(myMap, layerUrl, nodeName) {	
  if(! dijit.byId(nodeName)){
	var myFeatureLayer = new esri.layers.FeatureLayer(layerUrl, {
		mode: esri.layers.FeatureLayer.MODE_ONDEMAND,
		outFields:  "*",
		visible: true,
		id: "fLayer"
	});
    var myTable = new esri.dijit.FeatureTable({
        "featureLayer" : myFeatureLayer,
        "hiddenFields": ["FID"],  // field that end-user can show but is hidden on startup
        "map" : myMap
    }, nodeName);
    myTable.startup();  	
  }	
}

function loadDataGridTab(myMap, lang, ItemFileWriteStore, DataGrid, myDataList, myLayout, nodeName) {	
  var nName = nodeName + "DataGrid";
  if(! dijit.byId(nName)){
    var myData = {
      identifier: "id",
      items: []
    };
    var rows = 60;
    for(var i = 0; i < myDataList.length; i++){
    	myData.items.push(lang.mixin({ id: i+1 }, myDataList[i]));
    }
    var myStore = new ItemFileWriteStore({data: myData});
    var myGrid = new DataGrid({
        id: nName,
    	store: myStore,
        structure: myLayout,
        autoHeight: true,
        rowsPerPage:20,
        rowSelector: '20px'
    	});
	myGrid.placeAt(nodeName);
	//myGrid.startup();
	myGrid.render();
  }
}
function loadFeatureTable(myMap, layerUrl, MapBorder, TableBorder) {
	var borderContainer = dijit.byId(MapBorder);
	dojo.setStyle(borderContainer.domNode, 'height','76%');
	borderContainer.resize();        	
	borderContainer = dijit.byId(TableBorder);
	dojo.setStyle(borderContainer.domNode, 'height','24%');
	borderContainer.resize();
		
	var myFeatureLayer = new esri.layers.FeatureLayer(layerUrl, {
		mode: esri.layers.FeatureLayer.MODE_ONDEMAND,
		outFields:  ["FID","NAME","X","Y","RAINGAGE","WIDTH","SLOPE","ROUTING","RUNOFFCOEF"],
		visible: true,
		id: "fLayer"
	});
	
    var myTable = new esri.dijit.FeatureTable({
        "featureLayer" : myFeatureLayer,
        "hiddenFields": ["FID"],  // field that end-user can show but is hidden on startup
        "map" : myMap
    }, 'myTableNode');
    myTable.startup();  	
}

function setLayerVisibility(layer, name) {
  //用dojo.query获取css为listCss的元素数组
  var inputs = dojo.query(name);
  visible = [];
  for (var i = 0; i < inputs.length; i++) {
    if (inputs[i].checked) {
      visible.push(inputs[i].id);
    }
  }    //设置可视图层
  layer.setVisibleLayers(visible);
}

function addLayerAndInfoTemplate(myMap, layerUrl){
    var _outfallsInfoTemplate = new esri.InfoTemplate();
    _outfallsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");			
    var _junctionsInfoTemplate = new esri.InfoTemplate();
    _junctionsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");
    var _conduitsInfoTemplate = new esri.InfoTemplate();
    _conduitsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");
    var _subcatchmentsInfoTemplate = new esri.InfoTemplate();
    _subcatchmentsInfoTemplate.setTitle("<b>FID: ${FID}, NAME: ${NAME}</b>");		    
    var _outfallsInfoContent =
        "<div class='demographicInfoContent'>" +
        "RIMELEV: ${RIMELEV}<br/>" + 
        "INVERTELEV: ${INVERTELEV}<br/>" +  
        "MAXDEPTH: ${MAXDEPTH}<br/>" +
        "MAXHGL: ${MAXHGL}<br/>" +
        "TIMEMAXHGL: ${TIMEMAXHGL}" +
        "</div>";
    var _InfoContent =
      "<div class='demographicInfoContent'>" +
      "Tag: ${TAG}<br/>" + 
      "DEPTH: ${DEPTH}<br/>" +  
      "INVERT ELEV: ${INVERTELEV}<br/>" +
      "RIM ELEV: ${RIMELEV}<br/>" +
      "TIMEMAXHGL: ${TIMEMAXHGL}" +
      "</div>";
      var _conduitsInfoContent =
          "<div class='demographicInfoContent'>" +
          "Tag: ${TAG}<br/>" + 
          "LENGTH: ${LENGTH}<br/>" +  
          "SLOPE: ${SLOPE}<br/>" +
          "MAXFLOW: ${MAXFLOW}<br/>" +
          "TIMEMAXFLW: ${TIMEMAXFLW}" +
          "</div>";
      var _subcatchmentsInfoContent =
          "<div class='demographicInfoContent'>" +
          "RAINGAGE: ${RAINGAGE}<br/>" + 
          "WIDTH: ${WIDTH}<br/>" +  
          "SLOPE: ${SLOPE}<br/>" +
          "ROUTING: ${ROUTING}<br/>" +
          "RUNOFF COEF: ${RUNOFFCOEF}" +
          "</div>";
      
      _outfallsInfoTemplate.setContent("Outfalls for:<br>${X} ${Y}<br/>" + _outfallsInfoContent);
      _junctionsInfoTemplate.setContent("Junctions for:<br>${X} ${Y}<br/>" + _InfoContent);
      _conduitsInfoTemplate.setContent("Conduits for:<br>${INLETNODE} ${OUTLETNODE}<br/>" + _conduitsInfoContent);
      _subcatchmentsInfoTemplate.setContent("Subcatchments for:<br>${X} ${Y}<br/>" + _subcatchmentsInfoContent);
      
    var demographicsLayerURL = layerUrl; //"http://123.206.254.127:6080/arcgis/rest/services/Pre_Hebi__30y_24h/MapServer"; 
    var demographicsLayerOptions = {
      "id": "demographicsLayer",
      "opacity": 0.7,
      "showAttribution": false
    };

    var demographicsLayer = new esri.layers.ArcGISDynamicMapServiceLayer(demographicsLayerURL, demographicsLayerOptions);
    demographicsLayer.setInfoTemplates({
    	0: { infoTemplate: _outfallsInfoTemplate },
    	1: { infoTemplate: _junctionsInfoTemplate },
    	2: { infoTemplate: _conduitsInfoTemplate },     	
    	3: { infoTemplate: _subcatchmentsInfoTemplate }
    });
    demographicsLayer.setVisibleLayers([0,1,2,3]);
    myMap.addLayer(demographicsLayer);
    dojo.connect(demographicsLayer, "onLoad", loadLayerList);
    function loadLayerList(layers) {

        var html = "";
        var infos = layers.layerInfos;
        var length = infos.length; 
        for (i = 0; i < length; i++) {
            var info = infos[i];
            //图层默认显示的话就把图层id添加到visible
            if (info.defaultVisibility) {
                visible.push(info.id);
            }
            //输出图层列表的html
            html = html + "<div><input id='" + info.id + "' name='layerList' class='listCss' type='checkbox' value='checkbox' onclick='setVisibility();' " + (info.defaultVisibility ? "checked" : "") + " />" + info.name + "</div>";
        }       
        //设置可视图层
        demographicsLayer.setVisibleLayers(visible);
        //在右边显示图层名列表
        dojo.byId("LayersDiv").innerHTML = html;
    }
    setVisibility = function () {
    	setLayerVisibility(demographicsLayer, ".listCss");
    }		
}


/**  
 * 得到XMLHttpRequest对象  
 */  
function getXHR(){  
    var xmlHttp;  
    try {  
        xmlHttp=new XMLHttpRequest();  
    }catch(e)  
    {  
        try{  
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");  
        }  
        catch(e)  
        {  
            try{  
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");  
            }  
            catch(e)  
            {  
                alert("浏览器不支持Ajax");  
                return false;  
            }  
              
        }  
          
    }  
    return xmlHttp;  
}     