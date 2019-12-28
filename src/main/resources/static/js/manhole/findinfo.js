$(document).ready(function() {

	$(".ycell").css("text-align", "center");
	$("#item1").click(function() {
		var id = $(this).data("id");
		window.location.href = "/survey/item/findinfo?id=" + id;
	});
	$("#item2").click(function() {
		$("body,html").animate({scrollTop: 0}, 100);
	});
});
