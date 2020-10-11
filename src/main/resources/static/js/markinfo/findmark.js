$(document).ready(function() {

	$("textarea").attr("readonly", true);
	/** ***************************************************************** */
	$("#table2 tr").each(function(i) {
		var id = $(this).attr("id");
		var score = $(this).find("td:eq(5)").text();
		$(this).find("td:eq(5)").text(Number(score).toFixed(1));
		if (score < 90)
			$(this).find("td:eq(5)").css("color", "#FF1000");
		else
			$(this).find("td:eq(5)").css("color", "#479911");
		$(this).find("input:eq(0)").click(function() {
			window.open("findinfo?id=" + id);
		});
	});
	/** ***************************************************************** */
});
