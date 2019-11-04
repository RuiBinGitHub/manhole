$(document).ready(function() {
	
	/** 提交数据 */
	$("#mainTop input:eq(0)").click(function() {
		setcontrolName();
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("上传中...");
		$("form").submit();
	});
	
	function setcontrolName() {
		$("#mainInfo .table").each(function(i) {
			$(this).find("input[type=file]").attr("name", "files");
			$(this).find("input[type=hidden]:eq(0)").attr("name", "items[" + i + "].id");
			$(this).find("input[type=hidden]:eq(1)").attr("name", "items[" + i + "].path1");
			$(this).find("input[type=hidden]:eq(2)").attr("name", "items[" + i + "].path2");
			$(this).find("input[type=text]:eq(0)").attr("name", "items[" + i + "].remark1");
			$(this).find("input[type=text]:eq(1)").attr("name", "items[" + i + "].remark2");
		});
	}
	
	/** 添加一个表格 */
	$("#append div").click(function() {
		var table = $(".table:eq(0)").clone();
		var src = "/survey/img/appimg.png";
		table.find("img:eq(1)").attr("src", src);
		table.find("img:eq(2)").attr("src", src);
		table.find("input[type=hidden]").val(0);
		table.find("input[type=text]").val("");
		$("form").append(table);
	});
	
	/** 删除一个表格 */
	$("#mainTop input:eq(1)").click(function() {
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
	
	/** 表格内图片点击事件 */
	$("#mainInfo").on("click", "table img", function() {
		$(this).next("input").click();
	});
	
	/** 表格文件框改变事件 */
	$("#mainInfo").on("change", "table input[type=file]", function() {
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