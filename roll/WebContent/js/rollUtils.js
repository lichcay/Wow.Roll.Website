/**
 * 
 */
Date.prototype.Format = function(formatStr) {
	var str = formatStr;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];

	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/,
			(this.getYear() % 100) > 9 ? (this.getYear() % 100).toString()
					: '0' + (this.getYear() % 100));

	str = str.replace(/MM/, this.getMonth() > 8 ? this.getMonth().toString()+1
			: '0' + this.getMonth()+1);
	str = str.replace(/M/g, this.getMonth()+1);

	str = str.replace(/w|W/g, Week[this.getDay()]);

	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString()
			: '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());

	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString()
			: '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes()
			.toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());

	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds()
			.toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());

	return str;
}

function timeStamp2String(time) {
	var unixTimestamp = new Date(parseInt(time));
	commonTime = unixTimestamp.Format("yyyy-M-dd hh:mm:ss");
	return commonTime;
};

function classColorHelp(num){
	var rs;
	switch(num){
		case 1:
			rs={"class":"Warrior","color":"#C79C6E"};
			break;
		case 2:
			rs={"class":"Paladin","color":"#F58CBA"};
			break;
		case 3:
			rs={"class":"Hunter","color":"#ABD473"};
			break;
		case 4:
			rs={"class":"Rogue","color":"#FFF569"};
			break;
		case 5:
			rs={"class":"Priest","color":"#FFFFFF"};
			break;
		case 6:
			rs={"class":"DeathKnight","color":"#C79C6E"};
			break;
		case 7:
			rs={"class":"Shaman","color":"#0070DE"};
			break;
		case 8:
			rs={"class":"Mage","color":"#69CCF0"};
			break;
		case 9:
			rs={"class":"Warlock","color":"#9482C9"};
			break;
		case 10:
			rs={"class":"Monk","color":"#00FF96"};
			break;
		case 11:
			rs={"class":"Druid","color":"#FF7D0A"};
			break;
		case 12:
			rs={"class":"Demon Hunter","color":"#A330C9"};
			break;
	}
	return rs;
}