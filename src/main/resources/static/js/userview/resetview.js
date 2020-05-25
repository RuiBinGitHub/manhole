$(document).ready(function() {

	var code = null;
	
	$(".textbox:eq(0)").attr("placeholder", "请输入登录账号");
	$(".textbox:eq(1)").attr("placeholder", "请输入验证邮箱");
	$(".textbox:eq(2)").attr("placeholder", "请输入新密码");
	$(".textbox:eq(3)").attr("placeholder", "请确认新密码");
	$(".code").attr("placeholder", "请输入验证码");
	$(".textbox:eq(1)").attr("list", "list");
	$(".textbox:eq(2)").attr("name", "pass");
	$(".btn1").addClass("near");
	/** *************************************************************** */
	$("#tab1 .textbox:eq(1)").on("input", function(){
    	var value = $(this).val();
    	if(value.indexOf("@") != -1)
    		value = value.substring(0 ,value.indexOf("@"));
    	var context = "";
    	context += "<option>" + value + "@qq.com</option>";
    	context += "<option>" + value + "@126.com</option>";
    	context += "<option>" + value + "@163.com</option>";
    	context += "<option>" + value + "@sina.com</option>";
    	context += "<option>" + value + "@sohu.com</option>";
    	context += "<option>" + value + "@gmail.com</option>";
    	context += "<option>" + value + "@outlook.com</option>";
    	$("#list").html(context);
    });
	
	$(".commit").click(function() {
        if (!checkUserName())
            return false;
        if (!checkMail() || !checkCode())
            return false;
        if (!checkPassWord())
        	return false;
        $(this).css("background-color", "#999");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });
	
	/** 检测账号 */
    function checkUserName() {
        var name = $("#tab1 .textbox:eq(0)").val();
        if (name.length < 6 || name.length > 16) {
            $("#tab1 .textbox:eq(0)").css("border-color", "#f00");
            showTips("请输入正确登录账号！");
            return false;
        }
        return true;
    }
    
    /** 检测密码 */
    function checkPassWord() {
        var password1 = $("#tab1 input[type=password]:eq(0)").val();
        var password2 = $("#tab1 input[type=password]:eq(1)").val();
        if (password1.length < 6 || password1.length > 16) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            showTips("新密码格式输入不正确！");
            return false;
        }
        if (password1 != password2) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tab1 input[type=password]:eq(1)").css("border-color", "#f00");
            showTips("两次密码输入不一致！");
            return false;
        }
        return true;
    }
    
    /** 检测邮箱 */
    function checkMail() {
        if (!checkUserName())
            return false;
        var name = $("#tab1 .textbox:eq(0)").val();
        var mail = $("#tab1 .textbox:eq(1)").val();
        var expr = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (mail == "" || !mail.match(expr)) {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            showTips("请输入正确邮箱地址！");
            return false;
        }
        if (!Ajax("checknamemail", {username: name, mail: mail})) {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            showTips("登录账号与邮箱地址不匹配！");
            return false;
        }
        return true;
    }
    
    /** 检测验证码 */
    function checkCode() {
        var temp = $("#tab1 .code").val();
        if (temp != null && temp == code)
        	return true;
        $("#tab1 .code").css("border-color", "#f00");
        showTips("请输入正确的验证码！");
        return false;
    }
    
    var time = 0;
    $(".btn1").click(function() {
        if (!checkMail())
            return false;
        var mail = $("#tab1 .textbox:eq(1)").val();
        if ((code = Ajax("sendmail", {"mail": mail})) == "") {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            $("#tips").text("*Please check the input E-Mail!");
            return false;
        }
        time = 60;
        $(this).attr("disabled", true);
        $(".btn1").removeClass("near");
        changeTime();
    });

    function changeTime() {
        if (time-- == 0) {
        	$(".btn1").addClass("near");
        	$(".btn1").attr("disabled", false);
            $(".btn1").attr("value", "获取验证码");
        } else {
            $(".btn1").attr("value", time + " 秒后重新获取");
            setTimeout(changeTime, 1000);
        }
    }
    $("#tab1 .textbox").bind("input", function() {
        $(this).css("border-color", "#00BCDD");
        $("#tips").text("");
    });
    
    /** 执行AJAX操作 */
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            dataType: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
	/** *************************************************************** */
	function showTips(text) {
		$("#tips").show().delay(1800).hide(200);
		$("#tips").text(text);
	}
	
});