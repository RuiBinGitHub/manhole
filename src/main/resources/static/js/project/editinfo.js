$(document).ready(function() {

    // 获取当前语言
    var language = $("#mainTop").text().length < 5 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "确定删除该数据吗？" : "Are you sure you want to delete this data?";
    var tipsText2 = language == "zh" ? "数据删除成功！" : "Operating successfully!";
    /** ***************************************************************** */
    $("textarea").attr("readonly", true);
    var p = $("#index").val();
    $(".infoTop:eq(0) input").click(function() {
    	window.open("/survey/downfile?id=" + p);
    });
    $(".infoTop:eq(1) input").click(function() {
        window.open("/survey/manhole/insert?id=" + p);
    });
    /** ***************************************************************** */
    $("#table2 tr").each(function(i) {
        var id = $(this).attr("id");
        $(this).find("input:eq(0)").click(function() {
            window.open("/survey/manhole/editinfo?id=" + id);
        });
        $(this).find("input:eq(1)").click(function() {
            if (!confirm(tipsText1))
                return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("/survey/manhole/delete", {id: id}))
                showTips(tipsText2);
            setTimeout("location.reload()", 2000);
        });
    });
    /** ***************************************************************** */
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips").text(text);
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
