$(document).ready(function() {
	
	var table = $("#list .table:eq(0)").clone();
	$("#mainCenter").html(table);
	
	$("#main1 div").click(function() {
		var page1 = Number($("#page1").text());
		var page2 = Number($("#page2").text());
		if (page1 == 1) {
			showTips("已经是第一个！");
		} else {
			var table = $("#list .table").eq(page1 - 2).clone();
			$("#mainCenter").html(table);
			$("#page1").text(page1 - 1);
		}
	});
	
	$("#main2 div").click(function() {
		var page1 = Number($("#page1").text());
		var page2 = Number($("#page2").text());
		if (page1 == page2) {
			showTips("已经是最后一个！");
		} else {
			var table = $("#list .table").eq(page1).clone();
			$("#mainCenter").html(table);
			$("#page1").text(page1 + 1);
		}
	});
	
	function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips span").text(text);
    }
});