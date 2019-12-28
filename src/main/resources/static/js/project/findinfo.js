$(document).ready(function() {

	$("textarea").attr("readonly", true);
	/** ***************************************************************** */
	$("#table2 tr").each(function(i) {
		var id = $(this).attr("id");
		$(this).find("input:eq(0)").click(function() {
			window.open("/survey/manhole/findinfo?id=" + id);
		});
	});
});
