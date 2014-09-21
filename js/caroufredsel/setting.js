(function($) {
	'use strict';	
			$('#foo4').carouFredSel({
					prev: '#prev4',
					next: '#next4',
					auto: false,
					responsive: true,
					width: '100%',
					scroll: 1,
					items: {
						width: 400,
					height: 'auto',	//	optionally resize item-height
						visible: {
							min: 1,
							max: 8
						}
					}
				});
				
			$('#foo5').carouFredSel({
					prev: '#prev5',
					next: '#next5',
					auto: false,
					responsive: true,
					width: '100%',
					scroll: 1,
					items: {
						width: 400,
					height: 'auto',	//	optionally resize item-height
						visible: {
							min: 1,
							max: 8
						}
					}
				});
})(jQuery);