/**
 * @param {String} str (y-m-d h:i:s) y:年 m:月 d:日 h:时 i:分 s:秒
 */
 function dateTimeStr(str){
	var date = new Date(),
	year = date.getFullYear(), //年
	month = date.getMonth() + 1, //月
	day = date.getDate(), //日
	hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours(), //时
	minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes(), //分
	second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds(); //秒
	month >= 1 && month <= 9 ? (month = "0" + month) : "";
	day >= 0 && day <= 9 ? (day = "0" + day) : "";
	hour >= 0 && hour <= 9 ? (hour = "0" + hour) : "";
	minute >= 0 && minute <= 9 ? (minute = "0" + minute) : "";
	second >= 0 && second <= 9 ? (second = "0" + second) : "";
	if(str.indexOf('y') != -1){
		str = str.replace('y', year)
	}
	if(str.indexOf('m') != -1){
		str = str.replace('m', month)
	}
	if(str.indexOf('d') != -1){
		str = str.replace('d', day)
	}
	if(str.indexOf('h') != -1){
		str = str.replace('h', hour)
	}
	if(str.indexOf('i') != -1){
		str = str.replace('i', minute)
	}
	if(str.indexOf('s') != -1){
		str = str.replace('s', second)
	}
	return str;
}

export default {
	dateTimeStr
} 