$(document).ready(function() {

	var language = $("#infoTop").text().length < 5 ? "zh" : "en";
    var tipsText1 = language == "zh" ? "确定要移除该数据吗？" : "Are you sure you want to remove this data?";
    var tipsText2 = language == "zh" ? "数据移除成功！" : "Operating successfully!";
    /** *************************************************************** */
    if ($("#menuText").val() == "") {
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
    /** *************************************************************** */
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("id");
    	var date = $(this).find("a").attr("id");
        $(this).find("a").attr("target", "_blank");
        $(this).find("a").attr("href", "/survey/project/findinfo?id=" + date);
        /** *********************************************************** */
        if (name.trim() != "") {
        	var text = $(this).find("td:eq(1) a").text();
        	var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /** *********************************************************** */
        var score = $(this).find("td:eq(6)").text();
        $(this).find("td:eq(6)").text(Number(score).toFixed(2));
        if (score < 95)
            $(this).find("td:eq(6)").css("color", "#FF1000");
        else
            $(this).find("td:eq(6)").css("color", "#479911");
        /** *********************************************************** */
        $(this).find("input:eq(0)").click(function() {
            window.open("findmark?id=" + id);
        });
        $(this).find("input:eq(1)").click(function() {
            if (!confirm(tipsText1))
            	return false;
            $(this).css("background-color", "#ccc");
            $(this).attr("disabled", true);
            if (Ajax("remove", {id: id}))
                showTips(tipsText2);
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
        var name = $("#menuText").val();
        var page = Number($("#page1").text()) - 1;
        window.location.href = "findlist?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "findlist?name=" + name + "&page=" + page;
    });
    $(".pagebtn:eq(0)").attr("disabled", false);
    $(".pagebtn:eq(1)").attr("disabled", false);
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
