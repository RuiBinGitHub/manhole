layui.use(["layer", "form"], function () {
    const layer = layui.layer;

    // 获取当前语言
    const language = $("#mainTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "确定删除该数据吗？" : "Are you sure you want to delete this data?";
    const tipsText2 = language === "zh" ? "数据删除成功！" : "Operating successfully!";
    /** *************************************************************** */
    const id = $("input[name=id]").val();
    $("textarea").attr("readonly", true);

    $(".infoTop:eq(0) input").on("click", function () {
        window.open("/survey/project/downfile?id=" + id);
    });
    $(".infoTop:eq(1) input").on("click", function () {
        window.open("/survey/manhole/insert?id=" + id);
    });
    /** *************************************************************** */
    $("#table2 tbody tr").each(function () {
        const id = $(this).attr("id");
        $(this).find("a").attr("target", "_blank");
        $(this).find("input:eq(0)").click(function () {
            window.open("/survey/manhole/downfile?id=" + id);
        });
        $(this).find("input:eq(1)").click(function () {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("/survey/manhole/delete", {id: id}))
                layer.msg(tipsText2, {icon: 1});
            setTimeout("location.reload()", 2000);
        });
    });

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
