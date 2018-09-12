// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.22/esri/copyright.txt for details.
//>>built
define("esri/layers/clustering/GeohashAggregation","dojo/has dojo/_base/declare dojo/_base/lang dojo/_base/array ../../kernel ../../Evented ../../geometry/Point ../../geometry/Extent ../../geometry/mathUtils ../../renderers/arcadeUtils ../../core/timerUtils ../support/attributeUtils ./geohashUtils ./statUtils".split(" "),function(q,m,h,f,r,t,u,v,w,x,n,y,l,p){m=m(t,{loaded:!1,map:null,layer:null,lod:null,tolerance:null,clusterMode:null,clusterRadius:null,sortEnabled:!0,filterEnabled:!0,bufferEnabled:!0,
updateEnabled:!0,clusters:null,clustersEnabled:!0,statisticInfos:null,defaults:{lod:1,tolerance:0,clusterMode:"auto",clusterRadius:80,sortEnabled:!0,filterEnabled:!0,bufferEnabled:!0,updateEnabled:!0},_eventHandles:null,_updateHandle:null,_cellIndex:null,_globalIndex:null,_perfProfile:null,_cellSizeScaleFactor:1.5,_extentScaleFactor:1.25,_maxGeohashLength:12,_minClusterRadius:15,_levelChange:!0,_clusterFieldPrefix:"cluster_",_mapLevelChange:!1,constructor:function(a){this._update=h.hitch(this,this._update);
this._eventHandles=[];this._globalIndex={numFeatures:null,fullExtent:null,lodStats:null};this._perfProfile={lastIndex:{total:null,numFeatures:0},lastUpdate:{cells:null,clusters:null,total:null}};this.clusters=[];this.map=a.map;this.layer=a.layer;this.setLod(a.lod);this.setTolerance(a.tolerance);this.setClusterMode(a.clusterMode);this.setClusterRadius(a.clusterRadius);this.setSortEnabled(a.sortEnabled);this.setFilterEnabled(a.filterEnabled);this.setBufferEnabled(a.bufferEnabled);this.setUpdateEnabled(a.updateEnabled);
this.setStatisticInfos(a.statisticInfos);this._load();this.loaded?this._startup():this._eventHandles.push(this.on("load",h.hitch(this,this._startup)))},destroy:function(a){this._displayFeatures(!0,a);f.forEach(this._eventHandles,function(a){a.remove()});n.clearTimeout(this._updateHandle);this._cellIndex=this._globalIndex=this._eventHandles=this._updateHandle=this.map=this.layer=this.clusters=null},setLod:function(a){var b=this.lod;this.lod=a||this.defaults.lod;b!==this.lod&&this.update()},setTolerance:function(a){var b=
this.tolerance;this.tolerance=a||this.defaults.tolerance;b!==this.tolerance&&this.update()},setClusterMode:function(a){var b=this.clusterMode;this.clusterMode=a||this.defaults.clusterMode;this._evalClusterParams();b!==this.clusterMode&&this.update()},setClusterRadius:function(a){var b=this.clusterRadius;this.clusterRadius=null!=a?a:this.defaults.clusterRadius;this._evalClusterParams();b!==this.clusterRadius&&this.update()},setSortEnabled:function(a){var b=this.sortEnabled;this.sortEnabled=null!=a?
!!a:this.defaults.sortEnabled;b!==this.sortEnabled&&this.update()},setFilterEnabled:function(a){var b=this.filterEnabled;this.filterEnabled=null!=a?!!a:this.defaults.filterEnabled;b!==this.filterEnabled&&this.update()},setBufferEnabled:function(a){var b=this.bufferEnabled;this.bufferEnabled=null!=a?!!a:this.defaults.bufferEnabled;b!==this.bufferEnabled&&this.update()},setUpdateEnabled:function(a){var b=this.updateEnabled;(this.updateEnabled=null!=a?!!a:this.defaults.updateEnabled)&&b!==this.updateEnabled&&
(this._mapLevelChange=!0,this.update())},setStatisticInfos:function(a){this.statisticInfos=a||[];this.loaded&&this._applyStatInfos(this.statisticInfos)},update:function(){this.loaded&&null==this._updateHandle&&(this._updateHandle=n.setTimeout(this._update,n.priority.HIGH))},isUpdateScheduled:function(){return null!=this._updateHandle},getCell:function(a){return this._cellIndex[a.length][a]},getCluster:function(a){var b;f.some(this.clusters,function(c){-1<f.indexOf(c.geohashes,a)&&(b=c);return!!b});
return b},getCellsInCluster:function(a){var b=[];f.forEach(a&&a.geohashes,function(a){(a=this.getCell(a))&&b.push(a)},this);return b},getFeaturesInCluster:function(a){var b=[];a=this.getCellsInCluster(a);f.forEach(a,function(a){Array.prototype.push.apply(b,a.features)});return b},getCurrentLodStats:function(){var a=this._globalIndex.lodStats;return a&&a[this.lod]},getNumFeatures:function(){return this._globalIndex.numFeatures},getFullExtent:function(){var a=this._globalIndex.fullExtent;return a&&
Infinity!==a.xmin?new v(a):null},_load:function(){this._displayFeatures(!1);this._checkLoadStatus();this.map.loaded||this._eventHandles.push(this.map.on("load",h.hitch(this,this._checkLoadStatus)));this.layer.loaded||this._eventHandles.push(this.layer.on("load",h.hitch(this,this._checkLoadStatus)))},_checkLoadStatus:function(){if(this.map.loaded&&this.layer.loaded){var a;if("esriGeometryPoint"!==this.layer.geometryType)a=Error("GeohashAggregation is supported only for points");else{var b=this.map.spatialReference;
b.isWebMercator()||4326===b.wkid||(a=Error("GeohashAggregation is supported only when map spatial reference is WGS84 or WebMercator"))}a?(this.loadError=a,this.emit("load-error",{error:a})):(this.loaded=!0,this.emit("load"))}},_startup:function(){this._evalUpdateStatus();this._processFeatures();this._processExtentChange();this._eventHandles.push(this.layer.on("update-end",h.hitch(this,this._processFeatures)),this.layer.on("graphics-clear",h.hitch(this,this._processFeatures)),this.layer.on("suspend",
h.hitch(this,this._evalUpdateStatus)),this.layer.on("resume",h.hitch(this,this._evalUpdateStatus)),this.map.on("extent-change",h.hitch(this,this._processExtentChange)))},_processFeatures:function(){this._indexFeatures();this.update()},_evalUpdateStatus:function(){this.setUpdateEnabled(!this.layer.suspended)},_processExtentChange:function(a){this._mapLevelChange=!(!a||!a.levelChange);this._evalClusterParams();this.update()},_update:function(){this._updateHandle=null;if(this.updateEnabled){this.emit("update-start");
if(this.clustersEnabled){var a=this.map.geographicExtent;if(a){var b=this._getIntersectingCells(a),a=this._getClusters(b.cells,a);this.clusters=a.clusters;this._applyStatInfosToClusters();var b=b.profile,a=a.profile,c=b.total+a.total;b.total=this._getElapsedTime(b.total);a.total=this._getElapsedTime(a.total);var d=this._perfProfile.lastUpdate={};d.cells=b;d.clusters=a;d.total=this._getElapsedTime(c)}}else this.clusters=[],this._perfProfile.lastUpdate=null;b=this._mapLevelChange;this._mapLevelChange=
!1;this.emit("update-end",{levelChange:this._levelChange,mapLevelChange:b})}},_displayFeatures:function(a,b){var c=this.layer,d=!a;c.suspendGraphics(d);!1!==b&&(d?c.clearNodes():c.redraw())},_indexFeatures:function(){var a=this._getTime();this._initializeIndexing();var b=0,c=this._globalIndex.fullExtent;f.forEach(this.layer.graphics,function(a){if(a.visible){var d=a.geometry,g=this._getLngLat(d),e;g&&(e=d.getCacheValue("_geohash"),void 0===e&&(e=l.pointToGeohash(g),d.setCacheValue("_geohash",e||null)));
e&&(this._addGeohashToIndex(e,a,g),b++,this._updateExtent(c,g))}},this);this._applyStatInfosToIndex();this._globalIndex.numFeatures=b;for(var d=this._globalIndex.lodStats,g=1;g<=this._maxGeohashLength;g++)d[g]=this._getLODStats(g,b);this._perfProfile.lastIndex.total=this._getElapsedTime(a,this._getTime());this._perfProfile.lastIndex.numFeatures=b;this.emit("index-complete")},_initializeIndexing:function(){this._globalIndex={numFeatures:0,fullExtent:{xmin:Infinity,ymin:Infinity,xmax:-Infinity,ymax:-Infinity},
lodStats:{}};for(var a=this._cellIndex=[],b=1;b<=this._maxGeohashLength;b++)a[b]={}},_getLngLat:function(a){if(a){var b=a.getLongitude();a=a.getLatitude();a=null!=b&&null!=a?{x:b,y:a}:null}return a},_addGeohashToIndex:function(a,b,c){for(var d=this._cellIndex,g="",e=0;e<this._maxGeohashLength;e++){var g=g+a[e],k=d[g.length],f=k[g];f||(f=k[g]={count:0,centroid:{x:null,y:null},extent:{xmin:Infinity,ymin:Infinity,xmax:-Infinity,ymax:-Infinity},features:[],geohash:g,statistics:null});this._updateItem(f,
1,c,!0);f.features.push(b)}},_getLODStats:function(a,b){var c=this._cellIndex[a],d=0,g=Infinity,e=-Infinity,k=null,f;for(f in c){var h=c[f];d++;h.count<g&&(g=h.count);h.count>e&&(e=h.count)}0<d&&(k=Number((b/d).toFixed(2)));return{lod:a,count:d,min:Infinity===g?null:g,max:-Infinity===e?null:e,avg:k}},_evalClusterParams:function(){if(this.loaded&&"auto"===this.clusterMode){var a=this._getClusterParams(this.map.getResolutionInMeters(),this.clusterRadius,this._minClusterRadius);this._levelChange=this.lod!==
a.lod;this.lod=a.lod;this.tolerance=a.tolerance}},_getClusterParams:function(a,b,c){b<c&&(b=c);a=Math.ceil(a*b);b=this._getClosestLODRange(a).max;do c=this._getCellSize(b),(c=a>=this._cellSizeScaleFactor*c||1===c)||(b+=1);while(!c);c=a/this._getCellSize(b);return{lod:b,tolerance:a,multiplier:Number(c.toFixed(2))}},_getClosestLODRange:function(a){for(var b,c=this._maxGeohashLength;1<=c;c--)if(this._getCellSize(c)>=a){b=c;break}null==b&&(b=1);a=b+1;a>this._maxGeohashLength&&(a=this._maxGeohashLength);
return{min:b,max:a}},_getCellSize:function(a){a=l.getCellSizeInMeters(a);return Math.ceil(Math.min(a.width,a.height))},_sorter:function(a,b){var c=a.centroid,d=b.centroid;return a.count>b.count?-1:a.count<b.count?1:c.x>d.x?-1:c.x<d.x?1:0},_getIntersectingCells:function(a){var b=this._getTime();a=a.expand(this._extentScaleFactor);var c=l.getIntersecting(a,this.lod,this.bufferEnabled?this.tolerance:0);a=this._getTime();var d=[],g=this.tolerance,e=this.sortEnabled;f.forEach(c,function(a){(a=this.getCell(a))&&
d.push(a)},this);g&&e&&d.sort(this._sorter);c=this._getTime();return{cells:d,profile:{findCells:this._getElapsedTime(b,a),scanAndSortCells:this._getElapsedTime(a,c),total:c-b}}},_getClusters:function(a,b){var c=this._getTime(),d=[],g={},e={findCells:0};f.forEach(a,function(a,b){var c=this._createCluster(a,g,e);c&&d.push(c)},this);this._markIntersecting(d,b);this.filterEnabled&&(d=this._getIntersectingClusters(d));var k=this._getTime();return{clusters:d,profile:{findCellsInCluster:this._getElapsedTime(e.findCells),
total:k-c}}},_markIntersecting:function(a,b){var c=b.normalize();f.forEach(a,function(a){var b=a.centroid.x,d=a.centroid.y;a.isIntersecting=f.some(c,function(a){return b>=a.xmin&&b<=a.xmax&&d>=a.ymin&&d<=a.ymax})})},_getIntersectingClusters:function(a){return f.filter(a,function(a){return a.isIntersecting})},_createCluster:function(a,b,c){if(!b[a.geohash]){var d=[{cell:a,distance:0}];if(this.tolerance){var g=this._getTime(),e=l.getNeighborsWithinDistance(a.centroid,this.lod,this.tolerance);c.findCells+=
this._getTime()-g;f.forEach(e,function(b){if(b!==a.geohash&&(b=this.getCell(b))){var c=this._calculateDistance(a.centroid,b.centroid);c<=this.tolerance&&d.push({cell:b,distance:c})}},this)}return this._mergeCells(d,b)}},_calculateDistance:function(a,b){return w.getLength(l.geographicToWebMercator(a),l.geographicToWebMercator(b))},_mergeCells:function(a,b){var c=this._initializeCluster({},a[0].cell.geohash);f.forEach(a,function(a){var d=a.cell,e=d.geohash;a=a.distance;var f=b[e];if(f)if(a<f.distance)this._removeCellFromCluster(e,
b);else return;b[e]={cluster:c,distance:a};this._updateItem(c,d.count,d.centroid);c.geohashes.push(e)},this);return c},_removeCellFromCluster:function(a,b){var c=b[a].cluster;delete b[a];var d=f.indexOf(c.geohashes,a);-1<d&&c.geohashes.splice(d,1);this._reevaluateCluster(c)},_reevaluateCluster:function(a){var b=a.geohashes;a=this._initializeCluster(a,a.primary);f.forEach(b,function(b){var c=this.getCell(b);c&&(this._updateItem(a,c.count,c.centroid),a.geohashes.push(b))},this)},_initializeCluster:function(a,
b){a.count=0;a.centroid=new u(null,null);a.geohashes=[];a.primary=b;a.statistics=null;return a},_applyStatInfos:function(a){a=this._getValidStatInfos(a);this._applyStatInfosToIndex(a);this._applyStatInfosToClusters(a)},_getValidStatInfos:function(a){var b=[];f.forEach(a,function(a){var c=a.attributeInfo;a=a.statisticType;var g="angle"===c.attributeType;if(p.isSupportedStatisticType(a)&&(!g||"avg"===a)){var e="arithmetic"===c.rotationType;b.push({attributeCache:y.createAttributeCache(c,"type"===a),
identifier:p.getStatisticId(c,a),statFunctions:p.getStatisticFunctions(a),isAngular:g,isDate:"date"===c.attributeType,isArithmetic:e})}});return b},_applyStatInfosToIndex:function(a){a=a||this._getValidStatInfos(this.statisticInfos);var b=this._cellIndex;if(b)for(var c=1;c<=this._maxGeohashLength;c++){var d=b[c],g;for(g in d)this._applyStatInfosToCell(d[g],a)}},_applyStatInfosToCell:function(a,b){var c=this._initializeStats(a,b);f.forEach(a.features,function(a){this._calcFeatureStats(a,b,c)},this);
this._summarizeStats(a,c,b)},_calcFeatureStats:function(a,b,c){a.attributes&&f.forEach(b,function(b){var d=c[b.identifier],e=b.attributeCache,e=e?a._getDataValue(e.attributeInfo,e,x):null;b.statFunctions.updateCellStat(d,e,b)})},_applyStatInfosToClusters:function(a){a=a||this._getValidStatInfos(this.statisticInfos);var b=this._clusterFieldPrefix;f.forEach(this.clusters,function(c){var d=this._initializeStats(c,a);f.forEach(this.getCellsInCluster(c),function(b){this._calcCellStats(b,a,d)},this);this._summarizeStats(c,
d,a,b);c.attributes[b+"id"]=c.primary},this)},_calcCellStats:function(a,b,c){var d=a.statistics;f.forEach(b,function(a){var b=a.identifier;a.statFunctions.updateClusterStat(c[b],d[b],a)})},_initializeStats:function(a,b){var c=a.statistics={};f.forEach(b,function(a){c[a.identifier]=a.statFunctions.initialize(a)});return c},_summarizeStats:function(a,b,c,d){var g=a.attributes={};d=d||"";g[d+"count"]=a.count;f.forEach(c,function(a){var c=this._getStatFieldName(a,d);g[c]=a.statFunctions.summarize(b[a.identifier],
a)},this)},_getStatFieldName:function(a,b){return(b||"")+a.identifier},_updateItem:function(a,b,c,d){var g=c.y,e=a.centroid,f=a.count;e.x=(f*e.x+b*c.x)/(f+b);e.y=(f*e.y+b*g)/(f+b);a.count+=b;d&&this._updateExtent(a.extent,c)},_updateExtent:function(a,b){var c=b.x,d=b.y;c<a.xmin&&(a.xmin=c);c>a.xmax&&(a.xmax=c);d<a.ymin&&(a.ymin=d);d>a.ymax&&(a.ymax=d)},_getTime:function(){return window.performance?window.performance.now():(new Date).getTime()},_getElapsedTime:function(a,b){var c,d;d=null!=a&&null!=
b?b-a:a;null!=d&&(c="millisecond",1E3<=d&&(d/=1E3,c="second",60<=d&&(d/=60,c="minute")),c={value:Number(d.toFixed(2)),unit:c});return c}});q("extend-esri")&&h.setObject("layers.clustering.GeohashAggregation",m,r);return m});