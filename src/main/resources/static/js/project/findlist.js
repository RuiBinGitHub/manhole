$(document).ready(function() {
    // 获取当前语言
    var language = $("#infoTop").text().length < 5 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "确定撤回该数据吗？" : "Are you sure you want to submit this data?";
    var tipsText2 = language == "zh" ? "数据撤回成功！" : "Operating successfully!";
    var tipsText3 = language == "zh" ? "确定移除该数据吗？" : "Are you sure you want to delete this data?";
    var tipsText4 = language == "zh" ? "数据移除成功！" : "Operating successfully!";
    /********************************************************************/
    var width = $("#infoMenu span:eq(0)").css("width");
    var length = width.substring(0, width.length - 2);
    $("#infoMenu div:eq(0)").css("width", 570 - length);
    /********************************************************************/
    if ($("#menuText").val().trim() == "") {
    	$("#menuBtn1").css("color", "#AAAAAA");
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
        window.location.href = "findlist";
    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            window.location.href = "findlist?name=" + name;
    });
    /** 地图展示 */
    $("#showmap").click(function() {
        window.open("/survey/geominfo/showlist");
    });
    /********************************************************************/
    /** 初始化表格 */
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("id");
        $(this).find("td:eq(1) a").attr("target", "_blank");
        /*********************************************/
        if (name.trim() != "") {
        	var text = $(this).find("td:eq(1) a").text();
        	var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /*********************************************/
        $(this).find("input[type=button]:eq(0)").click(function() {
            window.open("/survey/downfile?id=" + id);
        });
        $(this).find("input[type=button]:eq(1)").click(function() {
        	if (!confirm(tipsText1))
        		return false;
        	$(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("revoke", {id: id}))
                showTips(tipsText2);
            setTimeout("location.reload()", 2000);
        });
        $(this).find("input[type=button]:eq(2)").click(function() {
        	if (!confirm(tipsText3))
        		return false;
        	$(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("remove", {id: id}))
                showTips(tipsText4);
            setTimeout("location.reload()", 2000);
        });
        $(this).click(function() {
            $("#tab1 tbody tr:even").find("td:eq(0)").css("background-color", "#FAFAFA");
            $("#tab1 tbody tr:odd").find("td:eq(0)").css("background-color", "#EEEEEE");
            $(this).find("td:eq(0)").css("background-color", "#FFD58D");
        });
    });
    /********************************************************************/
    /** 上一页 */
    $(".pagebtn:eq(0)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) - 1;
        window.location.href = "findlist?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val().trim();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "findlist?name=" + name + "&page=" + page;
    });
    /********************************************************************/
    var page1 = $("#page1").text();
    var page2 = $("#page2").text();
    if (page1 <= 1) {
        $(".pagebtn:eq(0)").attr("disabled", true);
        $(".pagebtn:eq(0)").css("color", "#999");
    }
    if (page1 == page2) {
        $(".pagebtn:eq(1)").attr("disabled", true);
        $(".pagebtn:eq(1)").css("color", "#999");
    }
    /********************************************************************/
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips span").text(text);
    }
    /********************************************************************/
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
