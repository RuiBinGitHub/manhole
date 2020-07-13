$(document).ready(function() {

	$("input[type=text]:eq(0)").attr("placeholder", "请输入项目编号");
	$("input[type=text]:eq(1)").attr("placeholder", "请输入/选择调查人员");
	$("input[type=text]:eq(2)").attr("placeholder", "请选择开始调查日期");
	$("input[type=text]:eq(3)").attr("placeholder", "请选择结束调查日期");
	$("textarea").attr("placeholder", "请输入任务内容");
	$("input[type=text]:eq(2)").attr("readonly", true);
	$("input[type=text]:eq(3)").attr("readonly", true);
	/** *************************************************************** */
	$("input[name=operator]").attr("list", "names");
	$("input[name*=date]").css("cursor", "pointer");
	laydate.render({
		elem : "input[name=datetime1]",
		format : "dd/MM/yyyy"
	});
	laydate.render({
		elem : "input[name=datetime2]",
		format : "dd/MM/yyyy"
	});

	$(".combtn").click(function() {
		if ($("input[type=text]:eq(0)").val() == "") {
			$("input[type=text]:eq(0)").css("border-color", "#f00");
			showTips("请输入项目编号！");
			return false;
		}
		if ($("input[type=text]:eq(0)").val().indexOf("/") != -1) {
			$("input[type=text]:eq(0)").css("border-color", "#f00");
			showTips("项目编号不能包含'/'字符");
			return false;
		}
		if ($("input[type=text]:eq(0)").val().indexOf("\\") != -1) {
			$("input[type=text]:eq(0)").css("border-color", "#f00");
			showTips("项目编号不能包含'\\'字符");
			return false;
		}
		if ($("input[type=text]:eq(1)").val() == "") {
			$("input[type=text]:eq(1)").css("border-color", "#f00");
			showTips("请输入调查人员！");
			return false;
		}
		if ($("input[type=text]:eq(2)").val() == "") {
			$("input[type=text]:eq(2)").css("border-color", "#f00");
			showTips("请选择开始调查时间！");
			return false;
		}
		if ($("input[type=text]:eq(3)").val() == "") {
			$("input[type=text]:eq(3)").css("border-color", "#f00");
			showTips("请选择结束调查时间！");
			return false;
		}
		var value1 = $("input[type=text]:eq(2)").val();
		var value2 = $("input[type=text]:eq(3)").val();
		var time1 = value1.split("/").reverse().join("/");
		var time2 = value2.split("/").reverse().join("/");
		if (time1 != "" && time2 != "" && time1 > time2) {
			$("input[type=text]:eq(2)").css("border-color", "#f00");
			$("input[type=text]:eq(3)").css("border-color", "#f00");
			showTips("开始时间不能大于结束时间！");
			return false;
		}
		$(this).css("background-color", "#ccc");
		$(this).attr("disabled", true);
		$("#form1").submit();
	});
	$("input[type=text]:eq(0)").on("input", function() {
		$(this).css("border-color", "#999");
	});
	$("input[name*=date]").on("input", function() {
		$("input[name*=date]").css("border-color", "#999");
	});
	/** ***************************************************************** */
	function showTips(text) {
		$("#tips").show().delay(1800).hide(200);
		$("#tips").text(text);
	}
});
