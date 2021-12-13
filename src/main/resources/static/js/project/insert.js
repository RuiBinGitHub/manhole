layui.use(["layer", "laydate"], function () {
    const layer = layui.layer;
    const laydate = layui.laydate;

    $("input[name=name]").attr("placeholder", "请输入项目编号");
    $("input[name=operator]").attr("placeholder", "请输入/选择调查人员");
    $("input[name=datetime1]").attr("placeholder", "请选择开始调查日期");
    $("input[name=datetime2]").attr("placeholder", "请选择结束调查日期");
    $("textarea").attr("placeholder", "请输入任务内容");
    $("input[name=name]").attr("maxlength", 32);
    /** *************************************************************** */
    $("input[name=operator]").attr("list", "names");
    $("input[name*=date]").css("cursor", "pointer");
    laydate.render({
        elem: "input[name=datetime1]",
        format: "dd/MM/yyyy",
        showBottom: false
    });
    laydate.render({
        elem: "input[name=datetime2]",
        format: "dd/MM/yyyy",
        showBottom: false
    });

    $(".combtn").on("click", function () {
        if ($("input[name=name]").val() === "") {
            $("input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg("请输入项目编号！", {icon: 2});
            return false;
        }
        if ($("input[name=name]").val().indexOf("/") !== -1) {
            $("input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg("项目编号不能包含'/'字符", {icon: 2});
            return false;
        }
        if ($("input[name=name]").val().indexOf("\\") !== -1) {
            $("input[type=text]:eq(0)").css("border-color", "#f00");
            layer.msg("项目编号不能包含'\\'字符", {icon: 2});
            return false;
        }
        if ($("input[name=operator]").val() === "") {
            $("input[type=text]:eq(1)").css("border-color", "#f00");
            layer.msg("请输入调查人员！", {icon: 2});
            return false;
        }
        if ($("input[name=datetime1]").val() === "") {
            $("input[type=text]:eq(2)").css("border-color", "#f00");
            layer.msg("请选择开始调查时间！", {icon: 2});
            return false;
        }
        if ($("input[name=datetime2]").val() === "") {
            $("input[type=text]:eq(3)").css("border-color", "#f00");
            layer.msg("请选择结束调查时间！", {icon: 2});
            return false;
        }
        const value1 = $("input[name=datetime1]").val();
        const value2 = $("input[name=datetime2]").val();
        const time1 = value1.split("/").reverse().join("/");
        const time2 = value2.split("/").reverse().join("/");
        if (time1 !== "" && time2 !== "" && time1 > time2) {
            $("input[name=datetime1]").css("border-color", "#f00");
            $("input[name=datetime2]").css("border-color", "#f00");
            layer.msg("开始时间不能大于结束时间！", {icon: 2});
            return false;
        }
        $(this).css("background-color", "#CCC");
        $(this).attr("disabled", true);
        $("#form1").submit();
    });

    $("input[type=text]:eq(0)").on("input", function () {
        $(this).css("border-color", "#999");
    });

    $("input[name*=date]").on("input", function () {
        $("input[name*=date]").css("border-color", "#999");
    });
});
