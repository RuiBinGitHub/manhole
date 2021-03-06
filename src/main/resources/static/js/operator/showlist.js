$(document).ready(function() {
    // 获取当前语言
    var tipsText1 = "確定要刪除該數據嗎？";
    var tipsText2 = "數據刪除成功！";
    /** *************************************************************** */
    var width = $("#infoMenu span:eq(0)").css("width");
    var length = width.substring(0, width.length - 2);
    $("#infoMenu div:eq(0)").css("width", 588 - length);
    /** *************************************************************** */
    if ($("#menuText").val().trim() == "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").keydown(function() {
        if (event.keyCode == 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").click(function() {
        window.location.href = "showlist";
    });
    $("#menuBtn2").click(function() {
        var name = $("#menuText").val();
        if (name.trim() != "")
            window.location.href = "showlist?name=" + name;
    });
    /** 新建项目 */
    $("#append").click(function() {
        window.open("insertview");
    });
    /** *************************************************************** */
    /** 初始化表格 */
    var name = $("#menuText").val();
    $("#tab1 tbody tr").each(function(i) {
    	var id = $(this).attr("id");
    	/** *********************************************************** */
        if (name.trim() != "") {
        	var text = $(this).find("td:eq(1)").text();
        	var font = "<font color='#f00'>" + name + "</font>";
            var expr = new RegExp(name,"gm");
            var cont = text.replace(expr, font);
            $(this).find("td:eq(1)").html(cont);
        }
        /** *********************************************************** */
        $(this).find("input[type=button]:eq(0)").click(function() {
            window.open("updateview?id=" + id);
        });
        $(this).find("input[type=button]:eq(1)").click(function() {
            if (!confirm("确定删除该数据吗？")) 
            	return false;
            $(this).css("background-color", "#CCC");
            $(this).attr("disabled", true);
            if (Ajax("delete", {id: id}))
                showTips("数据删除成功！");
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
        window.location.href = "showlist?name=" + name + "&page=" + page;
    });
    /** 下一页 */
    $(".pagebtn:eq(1)").click(function() {
        var name = $("#menuText").val();
        var page = Number($("#page1").text()) + 1;
        window.location.href = "showlist?name=" + name + "&page=" + page;
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
