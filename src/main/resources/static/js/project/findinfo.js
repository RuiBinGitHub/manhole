$(document).ready(function () {

    $("textarea").attr("readonly", true);
    /** ***************************************************************** */
    $("#table2 tr").each(function () {
        const id = $(this).attr("id");
        $(this).find("a").attr("target", "_blank");
        $(this).find("input").click(function () {
            window.open("/survey/manhole/downfile?id=" + id);
        });
    });

});
