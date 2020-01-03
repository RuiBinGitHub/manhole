$(document).ready(function() {
	
	/** *************************************************************** */
	/** 删除一个表格 */
	$("#item1").click(function() {
		if ($(".table").length == 1)
			return false;
		var id = $(".table:last input[type=hidden]:eq(0)").val();
		if (id != 0 && confirm("确定删除该图片框吗?")) {
			Ajax("delete", {id: id});
			$(".table:last").remove();
		}
		if (id == 0 || id == "")
			$(".table:last").remove();
	});
	
	/** 提交数据 */
	$("#item2").click(function() {
		if (!checkInput())
			return false;
		setControlName();
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("上传中...");
		$("form").submit();
	});
	
	$("#item3").click(function() {
		var id = $("input[name=id]").val();
		location.href="/survey/manhole/editinfo?id=" + id;
	});
	$("#item4").click(function() {
		$("body,html").animate({scrollTop: 0}, 100);
	});
	
	function checkInput() {
		var result = true;
		$("#mainInfo .table").each(function(i) {
			var control1 = $(this).find("input[type=text]:eq(0)");
			var control2 = $(this).find("input[type=text]:eq(1)");
			if (control1.val() == null || control1.val() == "") {
				control1.css("background-color", "#FDDD66");
				showTips("请输入完整数据！");
				result = false;
			}
			if (control2.val() == null || control2.val() == "") {
				control2.css("background-color", "#FDDD66");
				showTips("请输入完整数据！");
				result = false;
			}
		});
		return result;
	}
	
	function setControlName() {
		$("#mainInfo .table").each(function(i) {
			$(this).find("input[type=file]").attr("name", "files");
			$(this).find("input[type=hidden]:eq(0)").attr("name", "items[" + i + "].id");
			$(this).find("input[type=hidden]:eq(1)").attr("name", "items[" + i + "].path1");
			$(this).find("input[type=hidden]:eq(2)").attr("name", "items[" + i + "].path2");
			$(this).find("input[type=text]:eq(0)").attr("name", "items[" + i + "].photo1");
			$(this).find("input[type=text]:eq(1)").attr("name", "items[" + i + "].explain1");
			$(this).find("input[type=text]:eq(2)").attr("name", "items[" + i + "].remark1");
			$(this).find("input[type=text]:eq(3)").attr("name", "items[" + i + "].photo2");
			$(this).find("input[type=text]:eq(4)").attr("name", "items[" + i + "].explain2");
			$(this).find("input[type=text]:eq(5)").attr("name", "items[" + i + "].remark2");
		});
	}
	
	/** 添加一个表格 */
	$("#append div").click(function() {
		var table = $(".table:eq(0)").clone();
		var src = "/survey/img/appimg.png";
		table.find("img:eq(1)").attr("src", src);
		table.find("img:eq(2)").attr("src", src);
		table.find("input[type=hidden]:eq(0)").val(0);
		table.find("input[type=hidden]:eq(1)").val("");
		table.find("input[type=hidden]:eq(2)").val("");
		table.find("input[type=text]").val("");
		$("form").append(table);
	});
	
	/** 表格内图片点击事件 */
	$("#mainInfo").on("click", "table img", function() {
		$(this).next("input").click();
	});
	
	$("#mainInfo").on("focus", "table input", function() {
		$(this).css("background-color", "#FFFFFF");
	});
	
	/** 表格内图片点击事件 */
	$("#mainInfo").on("contextmenu", "table img", function(e) {
		e.preventDefault(); // 禁止浏览器默认右击事件
		$(this).attr("src", "/survey/img/appimg.png");
		var parent = $(this).parents("table");
		var i = parent.find("img").index($(this));
		parent.find("input[type=file]").eq(i - 1).val("");
		parent.find("input[type=hidden]").eq(i).val("");
	});
	
	/** 表格文件框改变事件 */
	$("#mainInfo").on("change", "input[type=file]", function() {
		if (this.files.length == 0)
            return false;
		var url = getURL(this.files[0]);
        $(this).prev("img").attr("src", url);
	});
	
	/** 根據文件獲取路徑 */
    function getURL(file) {
        var url = null;
        if (window.createObjectURL != undefined)
            url = window.createObjectURL(file);
        else if (window.URL != undefined)
            url = window.URL.createObjectURL(file);
        else if (window.webkitURL != undefined)
            url = window.webkitURL.createObjectURL(file);
        return url;
    }
    
    /** *************************************************************** */
    function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips span").text(text);
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