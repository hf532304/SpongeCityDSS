// All material copyright ESRI, All Rights Reserved, unless otherwise specified.
// See http://js.arcgis.com/3.22/esri/copyright.txt for details.
//>>built
define("esri/dijit/geoenrichment/ReportPlayer/core/reportContainers/containerUtils/QueryUtil",[],function(){var e={getReportElements:function(a,d){for(var b=[],c=0;c<a.stackContainer.children.length;c++){var e=a.stackContainer.children[c];a.getElementParams(e,"isReportElement")&&b.push(e)}return d?b.filter(function(b){return(b=a.getElementSection(b))&&!!b[d]}):b},collectFieldInfos:function(a,d){var b=[];e.getReportElements(a,"collectFieldInfos").some(function(c){(c=a.getElementSection(c).collectFieldInfos(d))&&
(b=b.concat(c))});return b},canInsertElementInCell:function(a){return!!e.getElementWithSelectedTableCells(a)},canCreateChartFromSelected:function(a){return!!e.getElementWithSelectedTableCells(a,!0)},getElementWithSelectedTableCells:function(a,d){var b;e.getReportElements(a,"hasSelectedCells").some(function(c){if(a.getElementSection(c).hasSelectedCells(d))return b=c,!0});return b},findLastContentElementBeforeFooter:function(a){var d;e.getReportElements(a).forEach(function(b){var c=a.getElementSection(b);
!c||c.isEmpty()||c.isPageFooter()||(d=b)});return d},getSectionPositionInfo:function(a,d){var b=0,c;e.getReportElements(a).some(function(e){if(a.getElementSection(e)===d)return c=e,!0;b++});return{reportElement:c,index:c&&b}},getElementIndex:function(a,d){return e.getReportElements(a).indexOf(d)},getReportElementBySectionIndex:function(a,d){return e.getReportElements(a)[d]},getSectionsByType:function(a,d){var b=[];e.getReportElements(a,"getSectionType").forEach(function(c){(c=a.getElementSection(c))&&
c.getSectionType()==d&&b.push(c)});return b},getReportElementTable:function(a,d){var b=a.getElementSection(d);return b&&b.getLastTable&&b.getLastTable()},getReportElementByTable:function(a,d){var b;e.getReportElements(a).some(function(c){if(e.getReportElementTable(a,c)===d)return b=c,!0});return b},getTableAbove:function(a,d){var b,c;e.getReportElements(a).some(function(f){if(e.getReportElementTable(a,f)===d)return c=e.getReportElementTable(a,b),!0;b=f});return c}};return e});