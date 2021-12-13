layui.use(["layer", "laypage"], function () {
    const layer = layui.layer;
    const laypage = layui.laypage;

    // 获取当前语言
    const language = $("#infoTop").text().length === 4 ? "zh" : "en";
    const tipsText1 = language === "zh" ? "确定提交该数据吗？" : "Are you sure you want to submit this data?";
    const tipsText2 = language === "zh" ? "数据提交成功！" : "Operating successfully!";
    const tipsText3 = language === "zh" ? "确定删除该数据吗？" : "Are you sure you want to delete this data?";
    const tipsText4 = language === "zh" ? "数据删除成功！" : "Operating successfully!";
    const btnText1 = language === "zh" ? "编辑" : "Edit";
    const btnText2 = language === "zh" ? "提交" : "Sub";
    const btnText3 = language === "zh" ? "删除" : "Del";
    /********************************************************************/
    if ($("#menuText").val().trim() === "") {
        $("#menuBtn1").attr("disabled", true);
    }
    $("#menuText").on("keydown", function (event) {
        if (event.which === 13)
            $("#menuBtn2").click();
    });
    $("#menuBtn1").on("click", function () {
        window.location.href = "showlist";
    });
    $("#menuBtn2").on("click", function () {
        const name = $("#menuText").val();
        if (name.trim() !== "")
            window.location.href = "showlist?name=" + name;
    });
    /** 新建项目 */
    $("#append").on("click", function () {
        window.open("insertview");
    });
    /********************************************************************/
    const name = $("input[name=name]").val();
    const sort = $("input[name=sort]").val();
    const type = $("input[name=type]").val();
    $("#tab1 thead th[data-name]").on("click", function () {
        const option = $(this).data("name");
        window.location.href = "showlist?name=" + name + "&sort=" + option;
    });

    $("#tab1 thead th[data-name]").each(function () {
        const option = $(this).data("name");
        if (sort === option && type === "") {
            $(this).text($(this).text() + "↓");
            $(this).off("click").on("click", function () {
                window.location.href = "showlist?name=" + name + "&sort=" + option + "&type=asc";
            });
        }
        if (sort === option && type !== "") {
            $(this).text($(this).text() + "↑");
        }
    });
    /********************************************************************/
    /** 初始化表格 */
    $("#tab1 tbody tr").each(function (n) {
        const id = $(this).attr("id");
        $(this).find("td:eq(1) a").attr("target", "_blank");
        /*********************************************/
        if (name.trim() !== "") {
            const text = $(this).find("td:eq(1) a").text();
            const font = "<span>" + name + "</span>";
            const expr = new RegExp(name, "gm");
            const cont = text.replace(expr, font);
            $(this).find("td:eq(1) a").html(cont);
        }
        /*********************************************/
        $(this).find("img").click(function () {
            const text = $(this).prev().text();
            if ($(this).attr("src") === "/survey/img/展开.png") {
                if ($(this).data("name") === undefined) {
                    $(this).parents("tr").after(getContext(text, id, n));
                    $(this).data("name", "已完成");
                } else
                    $("#tab1 tbody ." + n).show();
                $(this).attr("src", "/survey/img/收起.png");
            } else {
                $("#tab1 tbody ." + n).hide();
                $(this).attr("src", "/survey/img/展开.png");
            }
        });
        /*********************************************/
		/**
        $(this).find("input[type=button]:eq(0)").click(function () {
            window.open("updateview?id=" + id);
        });
        $(this).find("input[type=button]:eq(1)").click(function () {
            layer.confirm(tipsText1, {
                btn: ["确定", "取消"]
            }, function () {
                if (Ajax("submit", {id: id}))
                    layer.msg(tipsText2, {icon: 1});
                setTimeout("location.reload()", 2000);
            });
        });
        $(this).find("input[type=button]:eq(2)").click(function () {
            layer.confirm(tipsText3, {
                btn: ["确定", "取消"]
            }, function () {
                if (Ajax("delete", {id: id}))
                    layer.msg(tipsText4, {icon: 1});
                setTimeout("location.reload()", 2000);
            });
        });
		*/
    });
	
	/** 编辑项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(1)", function () {
        const id = $(this).parents("tr").attr("id");
        window.open("updateview?id=" + id);
    });

    /** 提交项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(2)", function () {
		const id = $(this).parents("tr").attr("id");
        layer.confirm(tipsText1, {
			btn: ["确定", "取消"]
		}, function () {
			if (Ajax("submit", {id: id}))
				layer.msg(tipsText2, {icon: 1});
			setTimeout("location.reload()", 2000);
		});
    });

    /** 删除项目 */
    $("#tab1 tbody").on("click", "tr td input:nth-child(3)", function () {
        const id = $(this).parents("tr").attr("id");
        layer.confirm(tipsText3, {
			btn: ["确定", "取消"]
		}, function () {
			if (Ajax("delete", {id: id}))
				layer.msg(tipsText4, {icon: 1});
			setTimeout("location.reload()", 2000);
		});
    });
	
    /********************************************************************/
    function getContext(name, id, no) {
        let context = "";
        const data = Ajax("showreal", {real: name});
        for (let i = 1; data != null && i < data.length; i++) {
            context += "<tr id='" + data[i].id + "' class='" + no + "'>";
            context += "    <td>-</td>";
            context += "    <td><a href='editinfo?id=" + data[i].id + "' target='_blank'>" + data[i].name + "</a></td>";
            context += "    <td>" + data[i].operator + "</td>";
            context += "    <td>" + data[i].datetime1 + "</td>";
            context += "    <td>" + data[i].datetime2 + "</td>";
            context += "    <td>正常</td>";
            context += "    <td>" + data[i].date + "</td>";
            context += "    <td>";
            context += "        <input type='button' class='layui-btn layui-btn-xs' value='" + btnText1 + "'/>";
            context += "        <input type='button' class='layui-btn layui-btn-xs layui-btn-normal' value='" + btnText2 + "'/>";
            context += "        <input type='button' class='layui-btn layui-btn-xs layui-btn-danger' value='" + btnText3 + "'/>";
            context += "    </td>";
            context += "</tr>";
        }
        if (data == null || data.length === 1) {
            context += "<tr class='" + no + "'>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "    <td>-</td>";
            context += "</tr>";
        }
        return context;
    }
    /********************************************************************/
    laypage.render({
        elem: "page",
        curr: $("#page").data("p1"),
        count: $("#page").data("p2"),
        limit: 15,
    });
    $("#page a").on("click", function () {
        let page = $(this).text();
        if (page === "上一页")
            page = Number($("#page").data("p1") - 1);
        if (page === "下一页")
            page = Number($("#page").data("p1") + 1);
        location.href = "showlist?name=" + name + "&page=" + page;
    });
    $(".layui-disabled").off("click");

    /********************************************************************/
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
