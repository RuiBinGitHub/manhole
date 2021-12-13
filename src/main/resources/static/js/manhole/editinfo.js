$(document).ready(function () {

    /** 显示图片 */
    const showImage = function (item, name, tagr) {
        const file = item.getAsFile();
        const reader = new FileReader();
        reader.onload = function (e) {
            const text = "<img src='" + e.target.result + "' alt=''>";
            $(name).val(e.target.result);
            $(tagr).html(text);
        };
        reader.readAsDataURL(file);
    };

    $("#logo").attr("title", "编辑图标，Size：700px * 112px");
    /** ***************************************************** */
    $("#logo").on("dblclick", function () {
        $("input[name=file]").click();
    });
    $("input[name=file]").change(function () {
        if (this.files.length === 0)
            return false;
        $("#logo").attr("src", getURL(this.files[0]));
        $("#edit img").show();
    });
    $("#edit img").on("click", function () {
        if ($("input[name=file]").length === 0)
            return false;
        $("#iform").attr("action", "/survey/project/editlogo");
        $("#iform input[name=path]").val(window.location.href);
        $("#iform").submit();
    });

    /** ***************************************************** */
    if ($("input[name=id]").val() === "")
        $("input[name=id]").val(0);

    /** 设置select的值 */
    const option = "<option value='N'>N</option><option value='Y'>Y</option>";
    $("select").html(option);
    $("select").each(function () {
        $(this).val($(this).attr("value"));
    });

    /** 输入框获取焦点事件 */
    $("input[type=text]").on("focus", function () {
        // $(this).select();
    });

    /** 下拉列表获取焦点事件 */
    $("select").on("focus", function () {
        $(this).parent().css("background-color", "#1E90FF");
        $(this).parent().css("border-color", "#1E90FF");
    });

    /** 下拉列表失去焦点事件 */
    $("select").on("blur", function () {
        $(this).parent().css("background-color", "#FFFFFF");
        $(this).parent().css("border-color", "#000000");
    });

    /** 输入框内容修改事件 */
    $("input[type=text]").on("input", function () {
        $(this).css("background-color", "#fff");
    });

    /** 输入框只能输入数字和小数点 */
    $(".num1").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });

    /** 输入框只能输入数字 */
    $(".num2").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57;
    });

    /** 输入框输入非法数值 */
    $(".num1, .num2").on("input", function () {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });

    /** node输入框内容修改事件 */
    $("input[name=drainage]").on("input", function () {
        if ($(this).val() !== "") {
            $("input[name=photono1]").val($(this).val() + " - P01");
            $("input[name=photono2]").val($(this).val() + " - P02");
        } else {
            $("input[name=photono1]").val("");
            $("input[name=photono2]").val("");
        }
    });

    /** 坐标输入框输入限制 */
    $("input[name=gridx], input[name=gridy]").on("keypress", function (event) {
        return event.which >= 48 && event.which <= 57 || event.which === 46;
    });
    $("input[name=gridx], input[name=gridy]").on("input", function () {
        if (isNaN($(this).val()))
            $(this).css("background-color", "#f00");
        else
            $(this).css("background-color", "#fff");
    });

    /** survey date日期选择框 */
    laydate.render({
        elem: "input[name=surveydate]",
        format: "dd/MM/yyyy"
    });
    $("[name=rplan]").attr("placeholder", "(Y of attention required)");
    $("[name=rtype]").attr("placeholder", "Other(                  )");
    /** *************************************************************** */
    $(".ptab").each(function () {
        const select = $(this).find("select");
        const textbox = $(this).find("input");
        if (textbox.val() === "" && select.val() === "N")
            textbox.val("N");
        $(this).find("select").change(function () {
            if ($(this).val() === "Y") {
                textbox.focus();
                textbox.val("");
            } else
                textbox.val("N");
        });

    });
    /** *************************************************************** */
    $("#table5 td[class*=ctype]").each(function (i) {
        $(this).on("click", function () {
            if ($(this).attr("class") === "ctype1") {
                $(this).addClass("ctype2");
                $(this).removeClass("ctype1");
                $("input[name=ctype]").eq(i).val("N");
            } else {
                $(this).addClass("ctype1");
                $(this).removeClass("ctype2");
                $("input[name=ctype]").eq(i).val("Y");
            }
        });
    });
    /** *************************************************************** */
    $("input[name=mcover]").on("input", function () {
        const level = Number($(this).val());
        $("#table2 tbody tr").each(function () {
            const value = $(this).find("input[type=text]:eq(7)").val();
            if (value !== "" && !isNaN(value))
                $(this).find("input[type=text]:eq(8)").val((level - value).toFixed(2));
        });
        $("#table3 tbody tr").each(function () {
            const value = $(this).find("input[type=text]:eq(7)").val();
            if (value !== "" && !isNaN(value))
                $(this).find("input[type=text]:eq(8)").val((level - value).toFixed(2));
        });
    });
    $("#table2 tbody tr, #table3 tbody tr").each(function () {
        const textbox1 = $(this).find("input[type=text]:eq(7)");
        const textbox2 = $(this).find("input[type=text]:eq(8)");
        $(this).find("input[type=text]:eq(7)").on("input", function () {
            const level = $("input[name=mcover]").val();
            if ($(this).val() !== "" && level !== "")
                textbox2.val((level - $(this).val()).toFixed(2));
            if ($(this).val() === "" || isNaN($(this).val()))
                textbox2.val("");
        });
        $(this).find("input[type=text]:eq(8)").on("input", function () {
            const level = $("input[name=mcover]").val();
            if ($(this).val() !== "" && level !== "")
                textbox1.val((level - $(this).val()).toFixed(2));
            if ($(this).val() === "" || isNaN($(this).val()))
                textbox1.val("");
        });
    });
    /** *************************************************************** */
    $("#item1").on("click", function () {
        if (!checkInput() || !setControlName())
            return false;
        $("input[name=type]").val("save");
        $(this).css("background-color", "#ccc");
        $(this).attr("disable", true);
        $(this).val("上传中...");
        $("#form1").submit();
    });
    $("#item2").on("click", function () {
        if (!checkInput() || !setControlName())
            return false;
        $("input[name=type]").val("next");
        $(this).css("background-color", "#ccc");
        $(this).attr("disable", true);
        $(this).val("上传中...");
        $("#form1").submit();
    });
    $("#item3").on("click", function () {
        $("body,html").animate({scrollTop: 0}, 100);
    });

    /** *************************************************************** */
    function checkInput() {
        if ($("input[name=node]").val() === "") {
            $("input[name=node]").css("background-color", "#f00");
            $("input[name=node]").focus();
            return false;
        }
        if ($("input[name=areacode]").val() === "") {
            $("input[name=areacode]").css("background-color", "#f00");
            $("input[name=areacode]").focus();
            return false;
        }
        if ($("input[name=surveyname]").val() === "") {
            $("input[name=surveyname]").css("background-color", "#f00");
            $("input[name=surveyname]").focus();
            return false;
        }
        if ($("input[name=surveydate]").val() === "") {
            $("input[name=surveydate]").css("background-color", "#f00");
            $("input[name=surveydate]").focus();
            return false;
        }
        if ($("input[name=projectno]").val() === "") {
            $("input[name=projectno]").css("background-color", "#f00");
            $("input[name=projectno]").focus();
            return false;
        }
        if ($("input[name=workorder]").val() === "") {
            $("input[name=workorder]").css("background-color", "#f00");
            $("input[name=workorder]").focus();
            return false;
        }
        if ($("input[name=location]").val() === "") {
            $("input[name=location]").css("background-color", "#f00");
            $("input[name=location]").focus();
            return false;
        }
        return true;
    }

    function setControlName() {
        $("#table2 tbody tr").each(function (i) {
            $(this).find("input[type=hidden]:eq(0)").attr("name", "pipes[" + i + "].id");
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
        $("#table3 tbody tr").each(function (i) {
            const index = i + 8;
            $(this).find("input[type=hidden]:eq(0)").attr("name", "pipes[" + index + "].id");
            $(this).find("input[type=text]:eq(0)").attr("name", "pipes[" + index + "].upstream");
            $(this).find("input[type=text]:eq(1)").attr("name", "pipes[" + index + "].shape");
            $(this).find("input[type=text]:eq(2)").attr("name", "pipes[" + index + "].size1");
            $(this).find("input[type=text]:eq(3)").attr("name", "pipes[" + index + "].size2");
            $(this).find("input[type=text]:eq(4)").attr("name", "pipes[" + index + "].backdrop");
            $(this).find("input[type=text]:eq(5)").attr("name", "pipes[" + index + "].material");
            $(this).find("input[type=text]:eq(6)").attr("name", "pipes[" + index + "].lining");
            $(this).find("input[type=text]:eq(7)").attr("name", "pipes[" + index + "].depth");
            $(this).find("input[type=text]:eq(8)").attr("name", "pipes[" + index + "].level");
            $(this).find("input[type=text]:eq(9)").attr("name", "pipes[" + index + "].photo");
            $(this).find("input[type=text]:eq(10)").attr("name", "pipes[" + index + "].office1");
            $(this).find("input[type=text]:eq(11)").attr("name", "pipes[" + index + "].office2");
        });
        return true;
    }

    /** *************************************************************** */
    // 输入框复制事件
    document.getElementById("picture1").addEventListener("paste", function (e) {
        const cdata = e.clipboardData;
        const items = cdata.items;
        if (e.clipboardData && items) {
            let item = items[0];
            const types = cdata.types || [];
            for (let i = 0; i < types.length; i++) {
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
    document.getElementById("picture2").addEventListener("paste", function (e) {
        const cdata = e.clipboardData;
        const items = cdata.items;
        if (e.clipboardData && items) {
            let item = items[0];
            const types = cdata.types || [];
            for (let i = 0; i < types.length; i++) {
                if (types[i] === "Files") {
                    item = items[i];
                    break;
                }
            }
            if (item && item.kind === "file" && item.type.match(/^image\//i))
                showImage(item, "#path2", "#picture2");
        }
    }, false);

    $("#picture1").on("input", function () {
        if ($(this).html() === "" || $(this).html().length === 28)
            $("#path1").val("");
    });
    $("#picture2").on("input", function () {
        if ($(this).html() === "" || $(this).html().length === 28)
            $("#path2").val("");
    });


    /** 根據文件獲取路徑 */
    function getURL(file) {
        let url = null;
        if (window.createObjectURL !== undefined)
            url = window.createObjectURL(file);
        else if (window.URL !== undefined)
            url = window.URL.createObjectURL(file);
        else if (window.webkitURL !== undefined)
            url = window.webkitURL.createObjectURL(file);
        return url;
    }
});
