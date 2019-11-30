$(document).ready(function() {
	
	var height = $(document).height();
	$(document).scroll(function(e) {
        if ($(window).scrollTop() + $(window).height() > height - 420) {
        	$("#mark").removeClass("mark1");
        	$("#mark").addClass("mark2");
        } else {
        	$("#mark").removeClass("mark2");
        	$("#mark").addClass("mark1");
        }
        	
    });
});