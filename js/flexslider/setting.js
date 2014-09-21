(function($) {
	'use strict';
      $('.flexslider').flexslider({
        animation: "fade",
		directionNav: false,
		controlNav: true,
		slideshow: true, 
      });
	  
      $('.tablet-device').flexslider({
        animation: "slide",
		directionNav: true,
		controlNav: false,
		slideshow: true, 
      });
})(jQuery);