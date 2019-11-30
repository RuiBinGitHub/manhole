$(document).ready(function() {

	// 下拉框选项
    var option = "<option>N</option><option>Y</option>";
	
    /** 输入框获取焦点事件 */
	$("input[type=text]").focus(function() {
		$(this).select();
	});
	
	/** 输入框内容修改事件 */
	$("input[type=text]").on("input", function() {
		$("input[type=text]").css("background-color", "#fff");
	});

	/** 下拉列表获取焦点事件 */
    $("select").focus(function() {
        $(this).parent().css("background-color", "#00A1D6");
        $(this).parent().css("border-color", "#00A1D6");
    });

    /** 下拉列表失去焦点事件 */
    $("select").blur(function() {
        $(this).parent().css("background-color", "#FFFFFF");
        $(this).parent().css("border-color", "#000000");
    });

    /** 输入框只能输入数字和小数点 */
    $(".num1").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });

    /** 输入框只能输入数字 */
    $(".num2").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57)
            return true;
        return false;
    });

    /** 输入框输入非法数值 */
    $(".num1, .num2").on("input", function() {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
    
	$("input[name=node]").on("input", function() {
		if ($(this).val() != "") {
			$("textarea[name=photono1]").val($(this).val() + "-P02");
			$("textarea[name=photono2]").val($(this).val() + "-P02");
		} else {
			$("textarea[name=photono1]").val("");
			$("textarea[name=photono2]").val("");
		}
	});

	/** node输入框内容修改事件 */
    $("input[name=node]").on("input", function() {
        if ($(this).val() != "") {
            $("textarea[name=photono1]").val($(this).val() + "-P01");
            $("textarea[name=photono2]").val($(this).val() + "-P02");
        } else {
            $("textarea[name=photono1]").val("");
            $("textarea[name=photono2]").val("");
        }
    });

    /** 坐标输入框输入限制 */
    $("input[name=gridx], input[name=gridy]").keypress(function(event) {
        if (event.which >= 48 && event.which <= 57 || event.which == 46)
            return true;
        return false;
    });
    $("input[name=gridx], input[name=gridy]").on("input", function() {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });
	
    /** survey date日期选择框 */
    laydate.render({
        elem: "input[name=surveydate]",
        format: "dd/MM/yyyy",
        value: "01/01/2019"
    });
    /** *************************************************************** */
	$(".ptab").each(function(i) {
		var textbox = $(this).find("input");
        $(this).find("select").html(option);
        $(this).find("select").change(function() {
            if ($(this).val() == "Y") {
                textbox.focus();
                textbox.val("");
            } else
                textbox.val("N");
        });
	});
	$("select[name=dplan]").html(option);
    $("#table4 select, #table7 select").html(option);
    /** *************************************************************** */
    $("input[name=mcover]").on("input", function() {
        var level = Number($(this).val());
        $("#table2 tbody tr").each(function() {
            var value = $(this).find("input[type=text]:eq(7)").val();
            if (value != "" && !isNaN(value))
                $(this).find("input[type=text]:eq(8)").val((level - value).toFixed(2));
        });
        $("#table3 tbody tr").each(function() {
            var value = $(this).find("input[type=text]:eq(7)").val();
            if (value != "" && !isNaN(value))
                $(this).find("input[type=text]:eq(8)").val((level - value).toFixed(2));
        });
    });
    $("#table2 tbody tr").each(function() {
        var textbox = $(this).find("input[type=text]:eq(8)");
        $(this).find("input[type=text]:eq(7)").on("input", function() {
            var level = $("input[name=mcover]").val();
            if ($(this).val() != "" && level != "")
                textbox.val((level - $(this).val()).toFixed(2));
            if ($(this).val() == "" || isNaN($(this).val()))
                textbox.val("");
        })
    });
    $("#table3 tbody tr").each(function() {
        var textbox = $(this).find("input[type=text]:eq(8)");
        $(this).find("input[type=text]:eq(7)").on("input", function() {
            var level = $("input[name=mcover]").val();
            if ($(this).val() != "" && level != "")
                textbox.val((level - $(this).val()).toFixed(2));
            if ($(this).val() == "" || isNaN($(this).val()))
                textbox.val("");
        })
    });
    /** *************************************************************** */
    $("#common1").click(function() {
		if (!checkInput() || !setControlName())
			return false;
		$("input[name=type]").val("0");
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("上传中...");
		$("#form1").submit();
	});
    $("#common2").click(function() {
		if (!checkInput() || !setControlName())
			return false;
		$("input[name=type]").val("1");
		$(this).css("background-color", "#ccc");
		$(this).attr("disable", true);
		$(this).val("上传中...");
		$("#form1").submit();
	});
	/** *************************************************************** */
	function checkInput() {
		if ($("input[name=node]").val() == "") {
            $("input[name=node]").css("background-color", "#f00");
            $("input[name=node]").focus();
            return false;
        }
        if ($("input[name=areacode]").val() == "") {
            $("input[name=areacode]").css("background-color", "#f00");
            $("input[name=areacode]").focus();
            return false;
        }
        if ($("input[name=surveyname]").val() == "") {
            $("input[name=surveyname]").css("background-color", "#f00");
            $("input[name=surveyname]").focus();
            return false;
        }
        if ($("input[name=surveydate]").val() == "") {
            $("input[name=surveydate]").css("background-color", "#f00");
            $("input[name=surveydate]").focus();
            return false;
        }
        if ($("input[name=projectno]").val() == "") {
            $("input[name=projectno]").css("background-color", "#f00");
            $("input[name=projectno]").focus();
            return false;
        }
        if ($("input[name=workorder]").val() == "") {
            $("input[name=workorder]").css("background-color", "#f00");
            $("input[name=workorder]").focus();
            return false;
        }
        if ($("input[name=location]").val() == "") {
            $("input[name=location]").css("background-color", "#f00");
            $("input[name=location]").focus();
            return false;
        }
        if ($("input[name=yearlaid]").val() == "") {
            $("input[name=yearlaid]").css("background-color", "#f00");
            $("input[name=yearlaid]").focus();
            return false;
        }
        return true;
	}
	function setControlName() {
		$("#table2 tbody tr").each(function(i) {
            $(this).find("input[type=text]:eq(0)").attr("name", "pipes[" + i + "].upstream");
            $(this).find("input[type=text]:eq(1)").attr("name", "pipes[" + i + "].shape");
            $(this).find("input[type=text]:eq(2)").attr("name", "pipes[" + i + "].size1");
            $(this).find("input[type=text]:eq(3)").attr("name", "pipes[" + i + "].size2");
            $(this).find("input[type=text]:eq(4)").attr("name", "pipes[" + i + "].backdrop");
            $(this).find("input[type=text]:eq(5)").attr("name", "pipes[" + i + "].material");
            $(this).find("input[type=text]:eq(6)").attr("name", "pipes[" + i + "].lining");
            $(this).find("input[type=text]:eq(7)").attr("name", "pipes[" + i + "].depth");
            $(this).find("input[type=text]:eq(8)").attr("name", "pipes[" + i + "].level");
            $(this).find("input[type=text]:eq(9)").attr("name", "pipes[" + i + "].photo");
            $(this).find("input[type=text]:eq(10)").attr("name", "pipes[" + i + "].office1");
            $(this).find("input[type=text]:eq(11)").attr("name", "pipes[" + i + "].office2");
        });
        $("#table3 tbody tr").each(function(i) {
            var i = i + 8;
            $(this).find("input[type=text]:eq(0)").attr("name", "pipes[" + i + "].upstream");
            $(this).find("input[type=text]:eq(1)").attr("name", "pipes[" + i + "].shape");
            $(this).find("input[type=text]:eq(2)").attr("name", "pipes[" + i + "].size1");
            $(this).find("input[type=text]:eq(3)").attr("name", "pipes[" + i + "].size2");
            $(this).find("input[type=text]:eq(4)").attr("name", "pipes[" + i + "].backdrop");
            $(this).find("input[type=text]:eq(5)").attr("name", "pipes[" + i + "].material");
            $(this).find("input[type=text]:eq(6)").attr("name", "pipes[" + i + "].lining");
            $(this).find("input[type=text]:eq(7)").attr("name", "pipes[" + i + "].depth");
            $(this).find("input[type=text]:eq(8)").attr("name", "pipes[" + i + "].level");
            $(this).find("input[type=text]:eq(9)").attr("name", "pipes[" + i + "].photo");
            $(this).find("input[type=text]:eq(10)").attr("name", "pipes[" + i + "].office1");
            $(this).find("input[type=text]:eq(11)").attr("name", "pipes[" + i + "].office2");
        });
        return true;
	}
	/** *************************************************************** */
	$("#picture1").on("input", function() {
		if ($(this).html() == "")
			$("#path1").val();
	});
	$("#picture2").on("input", function() {
		if ($(this).html() == "")
			$("#path2").val();
	});
	/** *************************************************************** */
	// 输入框复制事件
	document.getElementById("picture1").addEventListener("paste", function(e) {
		var cdata = e.clipboardData;
		var items = cdata.items;
		if (e.clipboardData && items) {
			var item = items[0];
			var types = cdata.types || [];
			for (var i = 0; i < types.length; i++) {
				if (types[i] === "Files") {
					item = items[i];
					break;
				}
			}
			if (item && item.kind === "file" && item.type.match(/^image\//i))
				showImage(item, "#path1", "#picture1");
		}
	}, false);
	// 输入框复制事件
	document.getElementById("picture2").addEventListener("paste", function(e) {
		var cdata = e.clipboardData;
		var items = cdata.items;
		if (e.clipboardData && items) {
			var item = items[0];
			var types = cdata.types || [];
			for (var i = 0; i < types.length; i++) {
				if (types[i] === "Files") {
					item = items[i];
					break;
				}
			}
			if (item && item.kind === "file" && item.type.match(/^image\//i))
				showImage(item, "#path2", "#picture2");
		}
	}, false);
	
	/** 显示图片 */
	var showImage = function(item, name, tagr) {
		var file = item.getAsFile();
		var reader = new FileReader();
		reader.onload = function(e) {
			var text = "<img src='" + e.target.result + "'>";
			$(name).val(e.target.result);
			$(tagr).html(text);
		};
		reader.readAsDataURL(file);
	};
	
	function showTips(text) {
        $("#tips").show().delay(1800).hide(200);
        $("#tips span").text(text);
    }
});