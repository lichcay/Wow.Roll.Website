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
<link href="css/rollCSS.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
<script src="js/jquery.js"></script>
<script type="text/javascript" src="//wow.zamimg.com/widgets/power.js"></script>
<script type="text/javascript" src="js/rollUtils.js"></script>
<script>
	var wowhead_tooltips = {
		"colorlinks" : true,
		"iconizelinks" : true,
		"renamelinks" : true
	}
	var pageno=0;
	var epgppageno=0;
	
	$(function(){
		var isguest = ${isGuest};
		if(!isguest){
			$($("#mobilemenu ul li")[1]).prepend("<img src='http://render-eu.worldofwarcraft.com/character/${default_character_avatar}' style='height:50px;display:block;float:left'></img>");
			$($("#mobilemenu ul li")[1]).children().text("${bnaccount.battletag}");
			$($("#mobilemenu ul li")[1]).children().attr("href","")
			$("#loginbn").empty();
			
		}else{
			$("#epgp_total").empty();
			$("#epgp_details").empty();
		}
	})
	
	function getLootdata(pageno){
		var rs;
		$.ajax({
	        type: "POST",
	        url: "/pageloot",
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
	        	rs = "error";
	        	//alert("error");
	        }
	    });
		return rs;
	}
	
	function getEpgpdata(pageno){
		var rs;
		$.ajax({
	        type: "POST",
	        url: "/pageepgp",
	        contentType: "application/json; charset=utf-8",
	        data: JSON.stringify({"pageno":pageno}),
	        dataType: "json",
	        async:false,
	        success: function (message) {
	        	//alert("success");
	           rs=message;
	        },
	        error: function (message) {
	        	epgppageno--;
	        	rs = "error";
	        	//alert("error");
	        }
	    });
		return rs;
	}
	
	function loadLoot(){
		pageno++;
		var data = getLootdata(pageno);
		if (data == "error"){
			alert("server error");
		}else{
		$.each(
				data,function(i,n){
					var cname=n["charactername"];
					var ltime=n["loottimestamp"];
					var cclass="wowclass"+n["cclass"];
					ltime = timeStamp2String(ltime);
					var itemid=n["itemid"];
					var bonuslists=n["bonuslists"];
					var blist = bonuslists.substring(1,bonuslists.length-1).split(",");
					var bliststr="";
					for(var i=0;i<blist.length;i++){
						bliststr=bliststr+blist[i]+":";
					}
					bliststr=bliststr.substr(0,bliststr.length);
					var txt = "<tr><td class='"+cclass+"'>"+cname+"</td><td>"+ltime+"</td><td><a href='#'rel='item="+itemid+" transmog="+itemid+" bonus="+bliststr+"'></a></td></tr>"
					$("#t1 tr:last").before(txt);
					});
		$WowheadPower.init();
	}}
	
	function loadEpgp(){
		epgppageno++;
		var data = getEpgpdata(epgppageno);
		if (data == "error"){
			alert("server error");
		}else{
		$.each(
				data,function(i,n){
					var cname=n["charactername"];
					var type=n["type"];
					var reason=n["reason"];
					var amount=n["amount"];
					var timestamp=n["timestamp"]*1000;
					var cclass="wowclass"+n["cclass"];
					ltime = timeStamp2String(timestamp);
					var txt = "<tr><td class='"+cclass+"'>"+cname+"</td><td>"+type+"</td><td>"+reason+"</td>";
					if (type=="GP"){
					var itemid=n["itemid"];
					var bonusids=n["bonusids"];
					var bliststr="";
					if (bonusids!=""){
					var blist = bonusids.substring(1,bonusids.length-1).split(",");
					for(var i=0;i<blist.length;i++){
						bliststr=bliststr+blist[i]+":";
					}
					bliststr=bliststr.substr(0,bliststr.length);
					}
					
						txt = txt + "<td><a href='#'rel='item="+itemid+" transmog="+itemid+" bonus="+bliststr+"'></a></td>"
					}else{
						txt = txt + "<td></td>";
					}
					if ((amount<0)||(type=="GP")){
						txt = txt + "<td style='color:red'>"+amount+"</td><td>"+ltime+"</td></tr>"
					}else{
						txt = txt + "<td style='color:limegreen'>"+amount+"</td><td>"+ltime+"</td></tr>"
					}
					
					$("#t3 tr:last").before(txt);
					});
		$WowheadPower.init();
		}
	}
	
	function navi_to_bn(){
		window.location.replace("https://eu.battle.net/oauth/authorize?client_id=czbvpfc8pcpms7m22k53r2h8vxht2a3y&client_secret=GHyDDrGjeKzXUdF824cqBaCEhBYn6h3h&redirect_uri=https%3a%2f%2fwowshroll.online%2foauth2callback&response_type=code&scope=wow.profile");
	}
</script>
</head>
<body>
	<!-- Header Area -->
	<div id="top" class="header">
		<div class="vert-text">
			<img class="img-rounded" alt="Company Logo"
				src="./img/roll_300_430.png" />
			<ul class="list-inline">
				<li><i><img class="wechat" src="./img/wechat.png" /></i></li>
				<li><i><img class="discord" src="./img/discord.png"
						onClick="javascript:window.open('https://discord.gg/ceCreXY');" /></i></li>
			</ul>
			<ul class="list-inline" id="loginbn">
				<li style="cursor: pointer;" onClick="navi_to_bn()">Login with
					Battlenet Account</li>
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
						<li><a
							href="https://eu.battle.net/oauth/authorize?client_id=czbvpfc8pcpms7m22k53r2h8vxht2a3y&client_secret=GHyDDrGjeKzXUdF824cqBaCEhBYn6h3h&redirect_uri=https%3a%2f%2fwowshroll.online%2foauth2callback&response_type=code&scope=wow.profile"
							style="float: right;">Login</a></li>
					</ul>
				</div>
			</div>
		</nav>
		<!-- /Navigation -->
	</div>
	<!-- About -->
	<div id="about" class="about_us">
		<div class="container">
			<div class="row" id="epgp_total">
				<div class="col-md-8 col-md-offset-2 text-center">
					<div class="vert-text">
						<table id="t2" style="width: 470px;">
							<thead>
								<tr>
									<td width="150px">人物</td>
									<td width="120px">EP</td>
									<td width="100px">GP</td>
									<td width="100px">PR</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="150px"></td>
									<td width="120px"></td>
									<td width="100px"></td>
									<td width="100px"></td>
								</tr>
								<#list epgplist as epgp>
								<tr>
									<td class="wowclass${epgp.cclass}">${epgp.charactername}</td>
									<td>${epgp.ep}</td>
									<td>${epgp.gp}</td>
									<td>${epgp.pr}</td>
								</tr>
								</#list>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8 col-md-offset-2 text-center">
					<div class="vert-text">
						<table id="t1" style="width: 650px;">
							<thead>
								<tr>
									<td width="150px">人物</td>
									<td width="140px">掉落时间</td>
									<td width="350px">掉落</td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td width="150px"></td>
									<td width="140px"></td>
									<td width="350px"></td>
								</tr>
								<#list loots as loot>
								<tr>
									<td class="wowclass${loot.cclass}">${loot.charactername}</td>
									<td class='timestamp'>${loot.loottimestamp}</td>
									<td><a href="#"
										rel="item=${loot.itemid} transmog=${loot.itemid} bonus=<#list (loot.bonuslists)?eval as bl>${bl?c}<#sep>:</#sep></#list>"></a>
									</td>
								</tr>
								</#list>
								<tr>
									<td colspan=3><div style="width: 150; cursor: pointer;"
											onClick=loadLoot()>显示更多</div></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row" id="epgp_details">
			<div class="col-md-8 col-md-offset-2 text-center">
				<div class="vert-text">
					<table id="t3" style="width: 1020px;">
						<thead>
							<tr>
								<td width="150px">人物</td>
								<td width="80px">类型</td>
								<td width="200px">原因</td>
								<td width="350px">装备</td>
								<td width="100px">数量</td>
								<td width="140px">时间</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td width="150px"></td>
								<td width="80px"></td>
								<td width="200px"></td>
								<td width="350px"></td>
								<td width="100px"></td>
								<td width="140px"></td>
							</tr>
							<#list epgploglist as epgplog>
							<tr>
								<td class="wowclass${epgplog.cclass}">${epgplog.charactername}</td>
								<td>${epgplog.type}</td>
								<td>${epgplog.reason}</td>
								<td><#if epgplog.type=="GP"> <a href="#"
										rel="item=${epgplog.itemid} transmog=${epgplog.itemid} bonus=<#list (epgplog.bonusids)?eval as bl>${bl?c}<#sep>:</#sep></#list>"></a></#if>
								</td>
								<td <#if (((epgplog.amount?eval)<0)||(epgplog.type=="GP")) >style="color:red"<#else>style="color:limegreen"</#if>>${epgplog.amount}</td>
								<td class='timestamp'>${epgplog.timestamp}000</td>
							</tr>
							</#list>
							<tr>
								<td colspan=6><div style="width: 150; cursor: pointer;"
										onClick=loadEpgp()>显示更多</div></td>
							</tr>
						</tbody>
					</table>
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
			//$WowheadPower.init();
		});
		$(window).load(function(){
			$.each($(".timestamp"),function(i,n){
				var txt = $(n).html();
				$(n).html(timeStamp2String(txt));
			});
			$WowheadPower.init();
		});
	</script>
	<!-- /Navbar-->

</body>

</html>