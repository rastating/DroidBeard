(function($) {
  'use strict';
	//$('#nav').localScroll(800);

	//.parallax(xPosition, adjuster, inertia, outerHeight) options:
	//xPosition - Horizontal position of the element
	//adjuster - y position to start from
	//inertia - speed to move relative to vertical scroll. Example: 0.1 is one tenth the speed of scrolling, 2 is twice the speed of scrolling
	//outerHeight (true/false) - Whether or not jQuery should use it's outerHeight option to determine when a section is in the viewport
	$('.home-wrapper').parallax("50%", 0, 0.1, true);
	$('#testimoni').parallax("50%", 0, 0.1, true);
	$('#pricing').parallax("50%", 0, 0.1, true);
	$('#intro').parallax("50%", 0, 0.1, true);
})(jQuery);