//>>built
(function(a,b){"object"===typeof exports&&"undefined"!==typeof module&&"function"===typeof require?b(require("../moment")):"function"===typeof define&&define.amd?define("moment/locale/es",["../moment"],b):b(a.moment)})(this,function(a){var b="ene. feb. mar. abr. may. jun. jul. ago. sep. oct. nov. dic.".split(" "),c="ene feb mar abr may jun jul ago sep oct nov dic".split(" ");return a.defineLocale("es",{months:"enero febrero marzo abril mayo junio julio agosto septiembre octubre noviembre diciembre".split(" "),
monthsShort:function(a,d){return a?/-MMM-/.test(d)?c[a.month()]:b[a.month()]:b},monthsParseExact:!0,weekdays:"domingo lunes martes mi\u00e9rcoles jueves viernes s\u00e1bado".split(" "),weekdaysShort:"dom. lun. mar. mi\u00e9. jue. vie. s\u00e1b.".split(" "),weekdaysMin:"do lu ma mi ju vi s\u00e1".split(" "),weekdaysParseExact:!0,longDateFormat:{LT:"H:mm",LTS:"H:mm:ss",L:"DD/MM/YYYY",LL:"D [de] MMMM [de] YYYY",LLL:"D [de] MMMM [de] YYYY H:mm",LLLL:"dddd, D [de] MMMM [de] YYYY H:mm"},calendar:{sameDay:function(){return"[hoy a la"+
(1!==this.hours()?"s":"")+"] LT"},nextDay:function(){return"[ma\u00f1ana a la"+(1!==this.hours()?"s":"")+"] LT"},nextWeek:function(){return"dddd [a la"+(1!==this.hours()?"s":"")+"] LT"},lastDay:function(){return"[ayer a la"+(1!==this.hours()?"s":"")+"] LT"},lastWeek:function(){return"[el] dddd [pasado a la"+(1!==this.hours()?"s":"")+"] LT"},sameElse:"L"},relativeTime:{future:"en %s",past:"hace %s",s:"unos segundos",m:"un minuto",mm:"%d minutos",h:"una hora",hh:"%d horas",d:"un d\u00eda",dd:"%d d\u00edas",
M:"un mes",MM:"%d meses",y:"un a\u00f1o",yy:"%d a\u00f1os"},dayOfMonthOrdinalParse:/\d{1,2}\u00ba/,ordinal:"%d\u00ba",week:{dow:1,doy:4}})});