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
		$(this).css("background-color", "#ccc");
        $(this).attr("disabled", true);
        $(this).val("上传中...");
        if (Ajax("update", $("#form1").serialize()))
            showTips("操作成功！");
        setTimeout("location.reload()", 2000);
	});
	
	/********************************************************************/
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips span").text(text);
    }
    /** 执行AJAX操作 */
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
	
});