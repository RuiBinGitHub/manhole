$(document).ready(function() {

	$("#table1 .ycell").css("text-align", "center");
	
	$("#common").click(function() {
		var id = $("input[name=id]").val();
		window.location.href = "/survey/item/findinfo?id=" + id;
	});
});