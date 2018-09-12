// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.22/esri/copyright.txt for details.
//>>built
define("esri/dijit/geoenrichment/ReportPlayer/core/grid/ValueField","dojo/_base/declare dojo/_base/lang dojo/on dojo/dom-construct dojo/dom-class dojo/dom-geometry dojo/dom-style dijit/Destroyable ./_ValueFieldTrimSupport ./_ValueFieldTooltipSupport esri/dijit/geoenrichment/utils/DomUtil esri/dijit/geoenrichment/utils/UrlUtil".split(" "),function(k,l,m,d,f,q,c,g,r,t,h,n){function u(a){a.domNode=d.toDom("\x3cdiv class\x3d'esriGEAdjustableGridValueField esriGENonSelectable'\x3e\x3cdiv class\x3d'valueField_contentBlock' data-dojo-attach-point\x3d'contentBlock'\x3e\x3cdiv data-dojo-attach-point\x3d'valueContainer' class\x3d'valueField_valueBlock'\x3e\x3cdiv data-dojo-attach-point\x3d'valueLabel' class\x3d'valueField_valueLabel'\x3e\x3c/div\x3e\x3c/div\x3e\x3c/div\x3e\x3c/div\x3e");
a.contentBlock=a.domNode.children[0];a.valueContainer=a.contentBlock.children[0];a.valueLabel=a.valueContainer.children[0]}g=k(g,{_buildLayoutFunc:null,_parentNode:null,constructor:function(a,b){l.mixin(this,a);this._parentNode=b;this.postCreate()},postCreate:function(){this._buildLayoutFunc(this);this["class"]&&f.add(this.domNode,this["class"]);this._parentNode&&this.placeAt(this._parentNode);this.value&&this.set("value",this.value)},get:function(a){return"value"==a?this._getValueAttr?this._getValueAttr():
this.value:this[a]},set:function(a,b){"value"==a?this._setValueAttr?this._setValueAttr(b):this.value=b:this[a]=b},placeAt:function(a){d.place(this.domNode,a);return this},on:function(a,b){this.own(m(this.domNode,a,b))},isLeftToRight:function(){return q.isBodyLtr(document)},destroy:function(){d.destroy(this.domNode);this.domNode=null}});return k([g,r,t],{valueLabel:null,fieldCellClass:null,_defaultStyles:{verticalAlign:"top",horizontalAlign:"left",horizontalPadding:0},fieldStyle:null,trimTextIfOverflows:!1,
content:null,postCreate:function(){this._buildLayoutFunc=this._buildLayoutFunc||u;this.inherited(arguments);this.setStyle(this.fieldStyle);this.setContent(this.content);this.fieldCellClass&&(f.add(this.domNode,this.fieldCellClass),f.add(this.contentBlock,"contentBlock_"+this.fieldCellClass))},_destroyExistingContent:function(){this.content&&(this.content.destroy&&this.content.destroy(),this.content=null,this.contentContainer&&d.empty(this.contentContainer))},getContentContainerNode:function(a){a||
this._destroyExistingContent();this.contentContainer||(this.contentContainer=d.create("div",{"class":"valueField_valueBlock"},this.contentBlock,"first"));h.show(this.contentContainer);return this.contentContainer},setContent:function(a){this._destroyExistingContent();if(this.content=a)this.getContentContainerNode(!0),(a=this.content.domNode||this.content)&&a.parentNode!==this.contentContainer&&d.place(a,this.contentContainer);h[this.content?"show":"hide"](this.contentContainer);h[this.content?"hide":
"show"](this.valueContainer);this.content||this.set("value",this._value)},_value:null,_getValueAttr:function(){return this._value},_setValueAttr:function(a){a=a||"";this._setValueLabelText(a);this._value=a},_setValueLabelText:function(a){this.valueLabel&&(this._checkValueLabelOverflow(a),this._setValueLabelTooltip(a));return a},_currentNumberValue:null,setNumberValue:function(a){this._currentNumberValue=a},setStyle:function(a){this.fieldStyle=this.fieldStyle||{};l.mixin(this.fieldStyle,a);this.fieldStyle.width&&
this.setWidth(this.fieldStyle.width);this.fieldStyle.height&&this.setHeight(this.fieldStyle.height);this.fieldStyle.horizontalAlign&&c.set(this.domNode,"textAlign",this.fieldStyle.horizontalAlign);this.fieldStyle.horizontalPadding&&c.set(this.valueLabel,"paddingLeft",this.fieldStyle.horizontalPadding+"px");c.set(this.valueContainer,"verticalAlign",this.fieldStyle.verticalAlign||"");this.contentContainer&&c.set(this.contentContainer,"verticalAlign",this.fieldStyle.verticalAlign||"");this._applyTextStyleToDom(this.domNode)},
_supportedTextStyles:"color fontSize fontFamily fontWeight fontStyle textDecoration backgroundColor".split(" "),_supportedAdditionalStyles:["horizontalAlign","verticalAlign","horizontalPadding"],_applyTextStyleToDom:function(a,b){var p=this;this._supportedTextStyles.forEach(function(e){if(p.fieldStyle[e]){var d=p.fieldStyle[e]+("fontSize"==e?"px":"");b&&(a.style[e]=d);c.set(a,e,d)}})},getFullStyle:function(){var a=this,b=this.getTextStyle();this._supportedAdditionalStyles.forEach(function(c){b[c]=
a.fieldStyle[c]});for(var c in this._defaultStyles)void 0!==b[c]&&b.hasOwnProperty(c)||(b[c]=this._defaultStyles[c]);return b},getTextStyle:function(){var a=this,b={};this.domNode&&this._supportedTextStyles.forEach(function(d){b[d]=a.fieldStyle[d]||("fontSize"==d?c.toPixelValue(a.domNode,c.get(a.domNode,d)):c.get(a.domNode,d))});return b},getWidth:function(){return c.get(this.contentBlock,"width")+this._getHorizontalPaddings(!0)},setWidth:function(a){if(this.contentBlock&&this.domNode){var b=a-this._getHorizontalPaddings(!0);
c.set(this.contentBlock,"width",b+"px");c.set(this.valueContainer,"width",b+"px");c.set(this.valueLabel,"width",b-(this.fieldStyle.horizontalPadding||0)+"px");this.contentContainer&&c.set(this.contentContainer,"width",b+"px");c.set(this.domNode,"width",a-this._getHorizontalPaddings(!1)+"px");this.fieldStyle.width=a;this._checkValueLabelOverflow()}},getHeight:function(){return c.get(this.contentBlock,"height")+this._getVerticalPaddings(!0)},setHeight:function(a){var b=Math.max(0,a-this._getVerticalPaddings(!0));
c.set(this.contentBlock,"height",b+"px");c.set(this.valueContainer,"height",b+"px");this.contentContainer&&c.set(this.contentContainer,"height",b+"px");c.set(this.domNode,"height",Math.max(0,a-this._getVerticalPaddings(!1))+"px");this.fieldStyle.height=a;this._checkValueLabelOverflow()},setMinHeight:function(a){var b=a+this._getVerticalPaddings(!0);c.set(this.contentBlock,"minHeight",b+"px");c.set(this.valueContainer,"minHeight",b+"px");this.contentContainer&&c.set(this.contentContainer,"minHeight",
b+"px");c.set(this.domNode,"minHeight",a+this._getVerticalPaddings(!1)+"px");this._checkValueLabelOverflow()},_getHorizontalPaddings:function(a){var b=this._getBorders();return b.outer.l+b.outer.r+(a?b.inner.l+b.inner.r:0)},_getVerticalPaddings:function(a){var b=this._getBorders();return b.outer.t+b.outer.b+(a?b.inner.t+b.inner.b:0)},_borders:null,_getBorders:function(){return this._borders=this._borders||this.getBorders()},getBorders:function(){return{outer:{t:0,b:0,l:0,r:0},inner:{t:0,b:0,l:0,r:0}}},
_urlClickHandle:null,setUrl:function(a){this._urlClickHandle&&this._urlClickHandle.remove();this.valueLabel&&(f[a?"add":"remove"](this.valueLabel,"esriGEAdjustableGridValueFieldHyperLink"),a&&(this._urlClickHandle=m(this.valueLabel,"click",function(){n.openUrl(n.toHttpUrl(a))})))},destroy:function(){this._destroyExistingContent();this.inherited(arguments)}})});