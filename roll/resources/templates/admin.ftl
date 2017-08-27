<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>R O L L</title>
<script src="js/jquery.js"></script>
</head>
<body>
<div>
Paste epgp json here:<input id="epgp_json" name="epgp_json" type="text">
<button onClick="submit()">Submit</button>
</div>
</body>
<script>
function submit(){
	var epgp_json = $("#epgp_json").val();
	$.ajax({
        type: "POST",
        url: "/roll/admin/uploadEpgp",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({"epgp":epgp_json}),
        dataType: "json",
        async:false,
        success: function (rs) {
        	alert(rs.message);
        },
        error: function (rs) {
        	alert("Server error, try again!");
        }
    });
}
</script>

</html>