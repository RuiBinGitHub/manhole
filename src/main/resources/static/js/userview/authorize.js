$(document).ready(function() {
	
	$("#link1").click(function() {
		window.history.back();
	});
	$("#link2").click(function() {
		window.location.href = "/survey/manhole/showlist";
	});
	
});