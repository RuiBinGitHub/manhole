$(document).ready(function () {

    var index = -1;
    const drainage = $("#drainage").text();
    const localtion = $("#localtion").text();
    /** *************************************************************** */
    $("#form1").on("click focus", ".table", function () {
        $(".table").css("border-color", "#000000");
        $(this).css("border-color", "#FF910C");
        index = $(this).index() - 1;
    });

    $("#form2 input[name=files]").attr("webkitdirectory", true);

    $("#item0").click(function () {
        $("#form2 input[name=files]").click();
    });
    $("#form2 input[name=files]").change(function () {
        if (this.files.length == 0)
            return false;
//        for (var i = 0; i < this.files.length; i++) {
//            var name = this.files[i].name;
//            var loca = name.lastIndexOf(".");
//            var type = name.substr(loca).toLowerCase();
//            if (type == null || type != ".xlsx")
//            	continue;
//            console.log("000" + i + name);
//            $("#form2").submit();
//            return true;
//        }
//        showTips("请选择正确的文件夹！");
        $("#form2").submit();
    });

    /** 删除一个表格 */
    $("#item1").click(function () {
        if (index != null && index == -1) {
            showTips("请选择需要删除的表格！");
            return false;
        }
        if ($(".table").length == 1) {
            showTips("最后一个表格无法删除！");
            return false;
        }
        var id = $(".table").eq(index).find("input[type=hidden]:eq(0)").val();
        if (!confirm("确定删除该表格吗?"))
            return false;
        if (id != "" && id != 0)
            Ajax("delete", {id: id});
        $(".table").eq(index).remove();
    });

    /** 提交数据 */
    $("#item2").click(function () {
        if (!checkInput())
            return false;
        setControlName();
        $(this).css("background-color", "#ccc");
        $(this).attr("disable", true);
        $(this).val("上传中...");
        $("#form1").submit();
    });

    $("#item3").click(function () {
        var id = $("input[name=id]").val();
        location.href = "/survey/manhole/editinfo?id=" + id;
    });
    $("#item4").click(function () {
        $("body,html").animate({scrollTop: 0}, 100);
    });

    function checkInput() {
        var result = true;
        $("#mainInfo .table").each(function (i) {
            var control1 = $(this).find("input[type=text]:eq(0)");
            var control2 = $(this).find("input[type=text]:eq(1)");
            if (control1.val() == null || control1.val() == "") {
                control1.css("background-color", "#ff4400");
                showTips("请输入完整数据！");
                result = false;
            }
            if (control2.val() == null || control2.val() == "") {
                control2.css("background-color", "#ff4400");
                showTips("请输入完整数据！");
                result = false;
            }
        });
        return result;
    }

    function setControlName() {
        $("#mainInfo .table").each(function (i) {
            $(this).find("input[type=file]").attr("name", "files");
            $(this).find("input[type=hidden]:eq(0)").attr("name", "items[" + i + "].id");
            $(this).find("input[type=hidden]:eq(1)").attr("name", "items[" + i + "].path1");
            $(this).find("input[type=hidden]:eq(2)").attr("name", "items[" + i + "].path2");
            $(this).find("input[type=text]:eq(0)").attr("name", "items[" + i + "].photo1");
            $(this).find("input[type=text]:eq(1)").attr("name", "items[" + i + "].location1");
            $(this).find("input[type=text]:eq(2)").attr("name", "items[" + i + "].explain1");
            $(this).find("input[type=text]:eq(3)").attr("name", "items[" + i + "].remark1");
            $(this).find("input[type=text]:eq(4)").attr("name", "items[" + i + "].photo2");
            $(this).find("input[type=text]:eq(5)").attr("name", "items[" + i + "].location2");
            $(this).find("input[type=text]:eq(6)").attr("name", "items[" + i + "].explain2");
            $(this).find("input[type=text]:eq(7)").attr("name", "items[" + i + "].remark2");
        });
    }

    /** 添加一个表格 */
    $("#append div").click(function () {
        const table = $(".table:eq(0)").clone();
        const src = "/survey/img/appimg.png";
        let length = $("#form1 .table").length * 2;
        table.find("img:eq(1)").attr("src", src);
        table.find("img:eq(2)").attr("src", src);
        table.find("input[type=hidden]:eq(0)").val(0);
        table.find("input[type=hidden]:eq(1)").val("");
        table.find("input[type=hidden]:eq(2)").val("");
        table.find("input[type=text]").val("");
        table.find("input[type=file]").val("");

        table.find("input[type=text]:eq(0)").val(getPhotoNo(++length));
        table.find("input[type=text]:eq(1)").val(localtion);
        table.find("input[type=text]:eq(4)").val(getPhotoNo(++length));
        table.find("input[type=text]:eq(5)").val(localtion);
        $("#form1").append(table);
    });

    /** 获取图片编号 */
    function getPhotoNo(no) {
        no = no < 10 ? "0" + no : no;
        return drainage + " - P" + no;
    }

    /** 表格内图片点击事件 */
    $("#mainInfo").on("dblclick", "table img", function () {
        $(this).next("input").click();
    });

    $("#mainInfo").on("focus", "table input", function () {
        $(this).css("background-color", "#FFFFFF");
    });

    /** 表格内图片点击事件 */
    $("#mainInfo").on("contextmenu", "table img", function (e) {
        e.preventDefault(); // 禁止浏览器默认右击事件
        $(this).attr("src", "/survey/img/appimg.png");
        var parent = $(this).parents("table");
        var i = parent.find("img").index($(this));
        parent.find("input[type=file]").eq(i - 1).val("");
        parent.find("input[type=hidden]").eq(i).val("");
    });

    /** 表格文件框改变事件 */
    $("#mainInfo").on("change", "input[type=file]", function (i) {
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
            success: function (data) {
                result = data;
            }
        });
        return result;
    }
});