/**
 * Common Utility functions
 */
// jQuery plugin to prevent double submission of forms
jQuery.fn.preventDoubleSubmission = function() {
	$(this).bind('submit', function(e) {

		if ($(this).data('submitted') === true) {
			// Previously submitted - don't submit again
			e.preventDefault();
		} else {
			// Mark this so that the next submit can be ignored
			$(this).data('submitted', true);
		}
	});

	// Keep chainability
	return $(this);
};

