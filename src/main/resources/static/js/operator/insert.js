$(document).ready(function() {

	/** *************************************************************** */
	var tipsText1 = "請輸入人員全名！";
	var tipsText2 = "人員名稱已經存在！";
	var tipsText3 = "請輸入人員姓氏！";
	var tipsText4 = "請輸入人員名字！";
	var tipsText5 = "請輸入會員等級！";
	var tipsText6 = "請輸入會員編號！";
	$(".textbox:eq(0)").attr("placeholder", "人員名稱，2-12位");
	$(".textbox:eq(1)").attr("placeholder", "人員姓氏，1-6位");
	$(".textbox:eq(2)").attr("placeholder", "人員名字，1-6位");
	$(".textbox:eq(5)").attr("placeholder", "操作人員認證等級");
	$(".textbox:eq(6)").attr("placeholder", "操作人員認證編號");
	/** *************************************************************** */
	/** 提交数据 */
	$(".combtn").click(function() {
		if ($(".textbox:eq(0)").val() == "") {
			$(".textbox:eq(0)").css("border-color", "#f00");
			showTips(tipsText1);
			return false;
		}
		if (Ajax("isexistname", {id : 0, name : $(".textbox:eq(0)").val()})) {
			$(".textbox:eq(0)").css("border-color", "#f00");
			showTips(tipsText2);
			return false;
		}
		if ($(".textbox:eq(1)").val() == "") {
			$(".textbox:eq(1)").css("border-color", "#f00");
			showTips(tipsText3);
			return false;
		}
		if ($(".textbox:eq(2)").val() == "") {
			$(".textbox:eq(2)").css("border-color", "#f00");
			showTips(tipsText4);
			return false;
		}
		if ($(".textbox:eq(5)").val() == "") {
			$(".textbox:eq(5)").css("border-color", "#f00");
			showTips(tipsText5);
			return false;
		}
		if ($(".textbox:eq(6)").val() == "") {
			$(".textbox:eq(6)").css("border-color", "#f00");
			showTips(tipsText5);
			return false;
		}
		/** 提交数据 */
		$(this).css("background-color", "#ccc");
		$(this).attr("disabled", true);
		$("#form1").submit();
	});
	/** 输入框获取焦点事件 */
	$("#tab1 input[type=text]").on("input", function() {
		$(this).css("border-color", "#999");
	});
	/** *************************************************************** */
	/** 显示提示信息 */
	function showTips(text) {
		$("#tips").show().delay(1800).hide(200);
		$("#tips span").text(text);
	}
	function Ajax(url, data) {
		var result = null;
		$.ajax({
			url : url,
			data : data,
			type : "post",
			async : false,
			datatype : "json",
			success : function(data) {
				result = data;
			}
		});
		return result;
	}
});
