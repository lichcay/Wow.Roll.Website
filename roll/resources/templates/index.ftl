<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>R O L L</title>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.css" rel="stylesheet">
<!-- Add custom CSS here -->
<link href="css/slidefolio.css" rel="stylesheet">
<!-- Font Awesome -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<script type="text/javascript" src="//wow.zamimg.com/widgets/power.js"></script>
<script>
	var wowhead_tooltips = {
		"colorlinks" : true,
		"iconizelinks" : true,
		"renamelinks" : true
	}
	var pageno=1;
	
	function timeStamp2String (time){
        var datetime = new Date();
         datetime.setTime(time);
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1;
         var date = datetime.getDate();
         var hour = datetime.getHours();
         var minute = datetime.getMinutes();
         var second = datetime.getSeconds();
         var mseconds = datetime.getMilliseconds();
         return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;
};
	
	function getLootdata(pageno){
		var rs;
		$.ajax({
	        type: "POST",
	        url: "/roll/pageloot",
	        contentType: "application/json; charset=utf-8",
	        data: JSON.stringify({"pageno":pageno}),
	        dataType: "json",
	        async:false,
	        success: function (message) {
	        	//alert("success");
	           rs=message;
	        },
	        error: function (message) {
	        	pageno--;
	        	//alert("error");
	        }
	    });
		return rs;
	}
	
	function loadLoot(){
		pageno++;
		var data = getLootdata(pageno);
		$.each(
				data,function(i,n){
					var cname=n["charactername"];
					var ltime=n["loottimestamp"];
					ltime = timeStamp2String(ltime);
					var itemid=n["itemid"];
					var bonuslists=n["bonuslists"];
					var blist = bonuslists.substring(1,bonuslists.length-1).split(",");
					var bliststr="";
					for(var i=0;i<blist.length;i++){
						bliststr=bliststr+blist[i]+":";
					}
					bliststr=bliststr.substr(0,bliststr.length);
					var txt = "<tr><td>"+cname+"</td><td>"+ltime+"</td><td><a href='#'rel='item="+itemid+" transmog="+itemid+" bonus="+bliststr+"'></a></td></tr>"
					$("#t1 tr:last").before(txt);
					});
		$WowheadPower.init();
	}
</script>
<style type="text/css">
.wechat {
	height: 50px;
	cursor: pointer;
	border-radius:5px;
}

.discord {
	height: 50px;
	cursor: pointer;
	border-radius:5px;
}
.wechat:hover {
	background:rgba(0, 0, 0, 0.50);
}
.discord:hover {
	background:rgba(0, 0, 0, 0.50);
}
.about_us{
	background:none;
}
</style>
</head>
<body>
	<!-- Header Area -->
	<div id="top" class="header">
		<div class="vert-text">
			<img class="img-rounded" alt="Company Logo"
				src="./img/roll_300_430.png" />
			<ul class="list-inline">
				<li><i><img class="wechat" src="./img/wechat.png" /></i></li>
				<li><i><img class="discord" src="./img/discord.png" onClick = "javascript:window.open('https://discord.gg/ceCreXY');"/></i></li>
			</ul>
		</div>
	</div>
	<!-- /Header Area -->
	<div id="nav">
		<!-- Navigation -->
		<nav class="navbar navbar-new" role="navigation">
			<div class="container">
				<!-- Brand and toggle get grouped for better mobile display -->
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#mobilemenu">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<div class="collapse navbar-collapse" id="mobilemenu">

					<ul class="nav navbar-nav navbar-right text-center">
						<li><a href="#top"><i class="service-icon fa fa-home"></i>&nbsp;Home</a></li>
						<li><a href="#about"><i class="service-icon fa fa-info"></i>&nbsp;About</a></li>
						<li><a href="#contact"><i
								class="service-icon fa fa-envelope"></i>&nbsp;Contact</a></li>
					</ul>
				</div>
				<!-- /.navbar-collapse -->
			</div>
		</nav>
		<!-- /Navigation -->
	</div>
	<!-- About -->
	<div id="about" class="about_us">
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2 text-center">
					<div class="vert-text">
						<table id="t1">
							<tr>
							<td width="150px">人物</td>
							<td>掉落时间</td>
							<td>掉落</td>
							</tr>
							<#list loots as loot>
							<tr>
								<td>${loot.charactername}</td>
								<td>${loot.loottimestamp?number?number_to_datetime}</td>
								<td><a href="#"
									rel="item=${loot.itemid} transmog=${loot.itemid} bonus=<#list (loot.bonuslists)?eval as bl>${bl?c}<#sep>:</#sep></#list>"></a>
								</td>
							</tr>
							</#list>
							<tr>
							<td colspan=3><div style="length:150;cursor:pointer;" onClick=loadLoot()>显示更多</div></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /About -->
	<!-- Services -->
	<div id="services" class="services">
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4 text-center"></div>
			</div>
		</div>
	</div>
	<!-- /Services -->
	<!-- /Footer -->
	<!-- Bootstrap core JavaScript -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery.js"></script>
	<script src="js/jquery-scrolltofixed-min.js"></script>
	<script src="js/jquery.vegas.js"></script>
	<script src="js/jquery.mixitup.min.js"></script>
	<script src="js/jquery.validate.min.js"></script>
	<script src="js/script.js"></script>
	<script src="js/bootstrap.js"></script>

	<!-- Slideshow Background  -->
	<script>
		$.vegas('slideshow', {
			delay : 5000,
			backgrounds : [ {
				src : './img/alexstrasza-1600x1200.jpg',
				fade : 2000
			}, {
				src : './img/silvermoon-1600x900.jpg',
				fade : 2000
			}, {
				src : './img/wallpaper26-1600x900.jpg',
				fade : 2000
			}, {
				src : './img/wallpaper27-1600x900.jpg',
				fade : 2000
			}, {
				src : './img/lich-king-1600x900.jpg',
				fade : 2000
			}

			]
		})('overlay', {
			src : './img/overlay.png'
		});
	</script>
	<!-- /Slideshow Background -->

	<!-- Mixitup : Grid -->
	<script>
		$(function() {
			$('#Grid').mixitup();
		});
	</script>
	<!-- /Mixitup : Grid -->

	<!-- Custom JavaScript for Smooth Scrolling - Put in a custom JavaScript file to clean this up -->
	<script>
		$(function() {
			$('a[href*=#]:not([href=#])')
					.click(
							function() {
								if (location.pathname.replace(/^\//, '') == this.pathname
										.replace(/^\//, '')
										|| location.hostname == this.hostname) {

									var target = $(this.hash);
									target = target.length ? target
											: $('[name=' + this.hash.slice(1)
													+ ']');
									if (target.length) {
										$('html,body').animate({
											scrollTop : target.offset().top
										}, 1000);
										return false;
									}
								}
							});
		});
	</script>
	<!-- Navbar -->
	<script type="text/javascript">
		$(document).ready(function() {
			$('#nav').scrollToFixed();
		});
	</script>
	<!-- /Navbar-->

</body>

</html>