// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.22/esri/copyright.txt for details.
//>>built
require({cache:{"url:esri/dijit/geoenrichment/DataBrowser/templates/Breadcrumb.html":'\x3cdiv class\x3d"Breadcrumb BreadcrumbHeight"\x3e\r\n    \x3cdiv data-dojo-attach-point\x3d"connect" class\x3d"Breadcrumb_Line" style\x3d"display: none;"\x3e\x3c/div\x3e\r\n    \x3cdiv data-dojo-attach-point\x3d"animatedConnect" class\x3d"Breadcrumb_Line Breadcrumb_AnimatedConnect" style\x3d"display: none;"\x3e\x3c/div\x3e\r\n    \x3cdiv class\x3d"Breadcrumb_AllCategories DataCategoryImage_AllCategories" data-dojo-attach-event\x3d"click: _onCategoriesClick"\x3e\x26nbsp;\x3c/div\x3e\r\n    \x3cdiv data-dojo-attach-point\x3d"dcIcon" data-dojo-attach-event\x3d"click: _onDCsClick" style\x3d"display: none;"\x3e\x3c/div\x3e\r\n    \x3cdiv data-dojo-attach-point\x3d"varsNode" class\x3d"Breadcrumb_Variables DataCollectionButton TrimWithEllipses" style\x3d"display: none;"\x3e\r\n        \x3cspan data-dojo-attach-point\x3d"varsLabel"\x3e\x3c/span\x3e\r\n    \x3c/div\x3e\r\n\x3c/div\x3e'}});
define("esri/dijit/geoenrichment/DataBrowser/Breadcrumb","dojo/_base/declare dojo/_base/lang dojo/_base/fx dojo/dom-class dojo/dom-style dojo/dom-geometry dojo/when dojox/mvc/Templated dojo/text!./templates/Breadcrumb.html ./VariableUtil".split(" "),function(k,f,l,d,g,h,e,m,n,p){return k(m,{templateString:n,flyAnim:null,selectCategory:function(a){this._lastDataCollection?this._updateDataCollection():this.clearSelection();a&&this._selectCategory(a,f.hitch(this,this._updateCategory))},selectDataCollection:function(a,
b){b?this._updateConnectionLine("Breadcrumb_Connect"):this.clearSelection();var c=this;this._selectCategory(b,function(b){c._updateCategory(b,!0);e(a,f.hitch(c,c._updateDataCollection))})},_selectCategory:function(a,b){var c=this;e(a,function(a){e(c.flyAnim.progress,function(){b(a)})})},clearSelection:function(){this._updateCategory();this._updateDataCollection()},_categoryClickAllowed:!1,_lastCategory:null,_lastDataCollection:null,_updateCategory:function(a,b){this._categoryClickAllowed=b;if(this._lastCategory=
a)this.dcIcon.className="Breadcrumb_DataCollections "+this._getCategoryImageClass(a),d[b?"add":"remove"](this.dcIcon,"DataBrowser_Clickable"),b||this._updateConnectionLine("Breadcrumb_ConnectDC");this.dcIcon.style.display=a?"":"none"},_getCategoryImageClass:function(a){return p.getCategoryImageClass(a)},_updateDataCollection:function(a){if(this._lastDataCollection=a)d[this._lastCategory?"remove":"add"](this.varsNode,"Breadcrumb_VariablesNoDC"),this.varsLabel.innerHTML=a.title;this.varsNode.style.display=
a?"":"none";this._updateConnectionLine(a?this._lastCategory?"Breadcrumb_ConnectDCVars":"Breadcrumb_ConnectVars":this._lastCategory?"Breadcrumb_Connect":"")},_updateConnectionLine:function(a){this._stopAnimation();if(a){var b="Breadcrumb_Connect"==a;d.replace(this.connect,a,"Breadcrumb_Connect Breadcrumb_ConnectDC Breadcrumb_ConnectDCVars Breadcrumb_ConnectVars");this.connect.style.display="";this._animateConnect(h.getContentBox(this.connect),b)}else this.connect.style.display="none",this.animatedConnect.style.display=
"none"},_animateConnect:function(a,b){if(b)g.set(this.animatedConnect,{width:a.w+"px",height:a.h+"px"}),this.animatedConnect.style.display="";else{var c;this.animatedConnect.style.display?(c={w:0,h:0},g.set(this.animatedConnect,{width:"0",height:"0"}),this.animatedConnect.style.display=""):c=h.getContentBox(this.animatedConnect);this._doAnimation(c,a)}},_animation:null,_animationRate:700,_stopAnimation:function(){this._animation&&this._animation.stop()},_doAnimation:function(a,b){var c=this;this._playAnimation({node:this.animatedConnect,
duration:(b.w-a.w)/this._animationRate*1E3,properties:{width:b.w}},function(){c._playAnimation({node:c.animatedConnect,duration:(b.h-a.h)/c._animationRate*1E3,properties:{height:b.h}})})},_playAnimation:function(a,b){var c=this;a.onStop=function(){c._animation=null};a.onEnd=function(){c._animation=null;b&&b()};this._animation=l.animateProperty(a);this._animation.play()},_onCategoriesClick:function(){this.onCategoriesClick()},onCategoriesClick:function(){},_onDCsClick:function(){if(this._categoryClickAllowed)this.onDCsClick()},
onDCsClick:function(){}})});