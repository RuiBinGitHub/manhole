$(document).ready(function() {

	var height = $(document).height();
	$(document).scroll(function(e) {
		if ($(window).scrollTop() + $(window).height() > height - 160) {
			$("#mark").removeClass("mark1");
			$("#mark").addClass("mark2");
		} else {
			$("#mark").removeClass("mark2");
			$("#mark").addClass("mark1");
		}
	});

	$("input[name=score]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });
	$("input[name=score]").on("input", function() {
		var value = $(this).val();
		if (value == "" || isNaN(value) || value < 0 || value > 100)
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#ddd");
    });
	
	$(".commit").click(function() {
		var value = $("input[name=score]").val();
		if (value == "" || isNaN(value) || value < 0 || value > 100) {
			$("input[name=score]").css("background-color", "#f00");
			return false;
		}
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("上传中...");
		$("#form1").submit();
	});
});