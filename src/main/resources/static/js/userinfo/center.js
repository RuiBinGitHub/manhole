$(document).ready(function() {

	var date = $("#table1 tr:eq(2) td:eq(1)").text();
	var term = $("#table1 tr:eq(2) td:eq(3)").text();
	$("#table1 tr:eq(2) td:eq(3)").text(getDate(date, term));
	
	$(".line:eq(0) img").click(function() {
		
		if ($("#reset1").is(":hidden")) {
			$(this).attr("src", "/survey/img/减号.png");
			$("#reset1").show();
		} else {
			$(this).attr("src", "/survey/img/加号.png");
			$("#reset1").hide();
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	function getDate(date, term) {   // 计算使用期限
        var date = new Date(date);
        date.setFullYear(date.getFullYear() + Number(term));
        var y = date.getFullYear();
        var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return y + "-" + m + "-" + d;
    }
});