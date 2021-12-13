$(document).ready(function () {

    let code = null;

    $("input[name=mail]").attr("list", "list");
    $("input[name=code]").attr("placeholder", "请输入验证码");
    $("#pass1").attr("name", "pass");
    $(".btn1").addClass("near");
    /** *************************************************************** */
    $("input[name=mail]").on("input", function () {
        let value = $(this).val();
        if (value.indexOf("@") !== -1)
            value = value.substring(0, value.indexOf("@"));
        let context = "";
        context += "<option>" + value + "@qq.com</option>";
        context += "<option>" + value + "@126.com</option>";
        context += "<option>" + value + "@163.com</option>";
        context += "<option>" + value + "@sina.com</option>";
        context += "<option>" + value + "@sohu.com</option>";
        context += "<option>" + value + "@gmail.com</option>";
        context += "<option>" + value + "@outlook.com</option>";
        $("#list").html(context);
    });

    $(".commit").on("click", function () {
        if (!checkName())
            return false;
        if (!checkMail() || !checkCode())
            return false;
        if (!checkPass())
            return false;
        $(this).css("background-color", "#999");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });

    /** 检测账号 */
    function checkName() {
        const name = $("input[name=name]").val();
        if (name.length < 6 || name.length > 16) {
            $("input[name=name]").css("border-color", "#f00");
            $("#tips").text("*请输入正确登录账号！");
            return false;
        }
        return true;
    }

    /** 检测密码 */
    function checkPass() {
        const password1 = $("#tab1 input[type=password]:eq(0)").val();
        const password2 = $("#tab1 input[type=password]:eq(1)").val();
        if (password1.length < 6 || password1.length > 16) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tips").text("*密码格式输入不正确！");
            return false;
        }
        if (password1 !== password2) {
            $("#tab1 input[type=password]:eq(0)").css("border-color", "#f00");
            $("#tab1 input[type=password]:eq(1)").css("border-color", "#f00");
            $("#tips").text("*两次密码输入不一致！");
            return false;
        }
        return true;
    }

    /** 检测邮箱 */
    function checkMail() {
        if (!checkName())
            return false;
        const name = $("input[name=name]").val();
        const mail = $("input[name=mail]").val();
        const expr = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        if (mail === "" || !mail.match(expr)) {
            $("input[name=mail]").css("border-color", "#f00");
            $("#tips").text("*请输入正确邮箱地址！");
            return false;
        }
        if (!Ajax("check", {username: name, mail: mail})) {
            $("#tab1 .textbox:eq(1)").css("border-color", "#f00");
            $("#tips").text("*登录账号与邮箱地址不匹配！");
            return false;
        }
        return true;
    }

    /** 检测验证码 */
    function checkCode() {
        const temp = $("input[name=code]").val();
        if (temp === "" || temp + "" !== code + "") {
            $("input[name=code]").css("border-color", "#f00");
            $("#tips").text("*验证码输入不正确！!");
            return false;
        }
        return true;
    }

    $(".btn1").on("click", function () {
        if (!checkName() || !checkMail())
            return false;
        const mail = $("input[name=mail]").val();
        if ((code = Ajax("sendmail", {"mail": mail})) === "") {
            $("input[name=mail]").css("border-color", "#f00");
            $("#tips").text("*电子邮箱格式不正确");
            return false;
        }
        $(this).attr("disabled", true);
        $(".btn1").removeClass("near");
        changeTime(60);
    });

    function changeTime(time) {
        if (time-- === 0) {
            $(".btn1").addClass("near");
            $(".btn1").attr("disabled", false);
            $(".btn1").attr("value", "获取验证码");
        } else {
            $(".btn1").attr("value", time + " 秒后重新获取");
            setTimeout(changeTime, 1000, time);
        }
    }

    $("#tab1 .textbox").bind("input", function () {
        $(this).css("border-color", "#00BCDD");
    });

    /** 执行AJAX操作 */
    function Ajax(url, data) {
        let result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            dataType: "json",
            success: function (data) {
                result = data;
            }
        });
        return result;
    }

});