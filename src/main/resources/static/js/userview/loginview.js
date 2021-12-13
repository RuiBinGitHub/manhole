$(document).ready(function () {

    $("input[name=username]").attr("placeholder", "请输入登录账号");
    $("input[name=password]").attr("placeholder", "请输入登录密码");
    $("input[name=password]").attr("type", "password");
    /** *************************************************************** */
    $("input").on("keydown", function () {
        if (event.which === 13)
            $(".commit").click();
    });

    $(".commit").on("click", function () {
        if ($("input[name=username]").val().length === 0) {
            $("input[name=username]").css("border-color", "#f00");
            $("#tips").text("*请输入登录账号！");
            return false;
        }
        if ($("input[name=password]").val().length === 0) {
            $("input[name=password]").css("border-color", "#f00");
            $("#tips").text("*请输入登录密码！");
            return false;
        }
        // 提交数据
        $(this).css("background-color", "#CCC");
        $(this).attr("disable", true);
        $(this).val("登录中...");
        $("#form1").submit();
    });

    $(".textbox").on("input", function () {
        $(this).css("border-color", "#00D3F8");
        $("#tips").text("");
    });

});