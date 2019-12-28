$(document).ready(function() {
	
	$(".textbox:eq(1)").attr("type", "password");
	$(".textbox:eq(0)").attr("placeholder", "请输入登录账号");
	$(".textbox:eq(1)").attr("placeholder", "请输入登录密码");
	/** *************************************************************** */
	$(".commit").click(function() {
		var name = $(".textbox:eq(0)").val();
		var pass = $(".textbox:eq(1)").val();
		if (name == null || name == "") {
			$(".textbox:eq(0)").css("border-color", "#f00");
			showTips("请输入登录账号！");
			return false;
		}
		if (pass == null || pass == "") {
			$(".textbox:eq(1)").css("border-color", "#f00");
			showTips("请输入登录密码！");
			return false;
		}
		// 提交数据
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("登录中...");
		$("#form1").submit();
	});

	$(".textbox").bind("input", function() {
		$(this).css("border-color", "#00D3F8");
	});
	/** *************************************************************** */
	function showTips(text) {
		$("#tips").show().delay(1800).hide(200);
		$("#tips span").text(text);
	}
});