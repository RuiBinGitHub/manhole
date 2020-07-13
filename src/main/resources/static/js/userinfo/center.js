$(document).ready(function() {

	var date = $("#table1 tr:eq(2) td:eq(1)").text();
	var term = $("#table1 tr:eq(2) td:eq(3)").text();
	$("#table1 tr:eq(2) td:eq(3)").text(getDate(date, term));
	/** *************************************************************** */
	$(".line:eq(0) img").click(function() {
		if ($("#reset1").is(":hidden")) {
			$(this).attr("src", "/survey/img/减号.png");
			$("#reset1").show();
		} else {
			$(this).attr("src", "/survey/img/加号.png");
			$("#reset1").hide();
		}
	});
	
	$(".line:eq(1) img").click(function() {
		showTips("系统维护中...");
	});
	$(".line:eq(2) img").click(function() {
		showTips("系统维护中...");
	});
	
	$("#reset1 input[type=button]").click(function() {
		var pass1 = $("#reset1 input:eq(0)").val();
		var pass2 = $("#reset1 input:eq(1)").val();
		var pass3 = $("#reset1 input:eq(2)").val();
		if (pass1.length < 6 || pass1.length > 16) {
			$("#reset1 input:eq(0)").css("border-color", "#f00");
			showTips("原密码格式不正确，请重新输入！");
			return false;
		}
		if (pass2.length < 6 || pass2.length > 16) {
			$("#reset1 input:eq(1)").css("border-color", "#f00");
			showTips("新密码格式不正确，请重新输入！");
			return false;
		}
		if (pass2 != pass3) {
			$("#reset1 input:eq(1)").css("border-color", "#f00");
			$("#reset1 input:eq(2)").css("border-color", "#f00");
			showTips("两次密码输入不一致！");
			return false;
		}
		if (Ajax("resetpass", {name: pass1, pass: pass2})) {
			showTips("操作完成，修改密码成功！");
			setTimeout("location.reload()", 2000);
		} else {
			$("#reset1 input:eq(0)").css("border-color", "#f00");
			showTips("原密码输入错误，请重新输入！");
		}
	});
		
	 $("#reset1 input:eq(0)").on("input", function() {
		 $(this).css("border-color", "#ccc");
	 });
	 $("#reset1 input:eq(1), #reset1 input:eq(2)").on("input", function() {
		 $("#reset1 input:eq(1)").css("border-color", "#ccc");
		 $("#reset1 input:eq(2)").css("border-color", "#ccc");
	 });
	
	function getDate(date, term) {   // 计算使用期限
        var date = new Date(date);
        date.setFullYear(date.getFullYear() + Number(term));
        var y = date.getFullYear();
        var m = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return y + "-" + m + "-" + d;
    }
	/** *************************************************************** */
	function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips   ").text(text);
    }
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