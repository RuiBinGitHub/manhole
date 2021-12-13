layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    // 获取当前语言
    const tipsText1 = "確定要刪除該數據嗎？";
    const tipsText2 = "數據刪除成功！";
    /** *************************************************************** */
    if ($("#menuText").val().trim() === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.keyCode === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "showlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            window.location.href = "showlist?name=" + name;
    });
    /** 新建项目 */
    $("#append").on("click", function () {
        window.open("insertview");
    });
    /** *************************************************************** */
    /** 初始化表格 */
    const name = $("#menuText").val();
    $("#tab1 tbody tr").each(function () {
        const id = $(this).attr("id");
        /** *********************************************************** */
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        $(this).find("input[type=button]:eq(0)").click(function () {
            window.open("updateview?id=" + id);
        });
        $(this).find("input[type=button]:eq(1)").click(function () {
            layer.confirm(tipsText1, {
                btn: ["确定", "取消"]
            }, function () {
                if (Ajax("delete", {id: id}))
                    layer.msg(tipsText2, {icon: 1});
                setTimeout("location.reload()", 2000);
            });
        });
    });
    /********************************************************************/
    laypage.render({
        elem: "page",
        curr: $("#page").data("p1"),
        count: $("#page").data("p2"),
        limit: 15,
    });
    $("#page a").on("click", function () {
        let page = $(this).text();
        if (page === "上一页")
            page = Number($("#page").data("p1") - 1);
        if (page === "下一页")
            page = Number($("#page").data("p1") + 1);
        location.href = "showlist?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");

    /********************************************************************/
    function Ajax(url, data) {
        let result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function (data) {
                result = data;
            }
        });
        return result;
    }
});
