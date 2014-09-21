(function($) {
	'use strict';

	/*
	tooltip
	=========================== */
	$('.tooltips').tooltip({
		selector: "a[data-toggle^=tooltip]"
	})
	
	/*
	Team
	=========================== */		
	$(".team-caption").css({'opacity':'0','filter':'alpha(opacity=0)'});
	$('.team-wrapper').hover(
		function() {
			$(this).find('.team-caption').stop().fadeTo(800, 1);
		},
		function() {
			$(this).find('.team-caption').stop().fadeTo(800, 0);
		}
	)
	
	/* Client logo hover
	=========================== */	
	$(".logo-hover").css({'opacity':'0','filter':'alpha(opacity=0)'});	
	$('.client-link').hover(function(){
				$(this).find('.logo-hover').stop().fadeTo(900, 1);
				$(this).find('.client-logo').stop().fadeTo(900, 0);
	}, function() {
				$(this).find('.logo-hover').stop().fadeTo(900, 0);
				$(this).find('.client-logo').stop().fadeTo(900, 1);
	});	
	
	
	
})(jQuery);