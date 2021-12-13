layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "确定要移除该数据吗？" : "Are you sure you want to remove this data?";
    const tipsText2 = language === "zh" ? "数据移除成功！" : "Operating successfully!";
    /** *************************************************************** */
    if ($("#menuText").val().trim() === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.keyCode === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "findlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            window.location.href = "findlist?name=" + name;
    });
    /** *************************************************************** */
    const name = $("#menuText").val();
    $("#tab1 tbody tr").each(function () {
        const id = $(this).attr("id");
        const date = $(this).find("a").attr("id");
        $(this).find("a").attr("target", "_blank");
        $(this).find("a").attr("href", "/survey/project/findinfo?id=" + date);
        /** *********************************************************** */
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        const score = $(this).find("td:eq(6)").text();
        $(this).find("td:eq(6)").text(Number(score).toFixed(2));
        if (score < 95)
            $(this).find("td:eq(6)").css("color", "#FF1000");
        else
            $(this).find("td:eq(6)").css("color", "#479911");
        /** *********************************************************** */
        $(this).find("input:eq(0)").click(function () {
            window.open("findmark?id=" + id);
        });

        $(this).find("input[type=button]:eq(1)").click(function () {
            layer.confirm(tipsText1, {
                btn: ["确定", "取消"]
            }, function () {
                if (Ajax("remove", {id: id}))
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
        location.href = "findlist?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");
    /** *************************************************************** */

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
