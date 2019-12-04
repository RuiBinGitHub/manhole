$(document).ready(function() {

	$(".ycell").css("text-align", "center");
	$("#common").click(function() {
		var id = $(this).data("id");
		window.location.href = "/survey/item/findinfo?id=" + id;
	});
});
