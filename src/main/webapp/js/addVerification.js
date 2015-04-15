var isDayInMonthAndYear = function(date) {
	pattern = /^\d{1,2}-\d{1,2}-\d{1,4}$/;
	return pattern.test(date);
}

$("#computerName").keyup(function() {
	if ($(this).val() !== "") {
		$("#computerNameMessage").css("color", "green");
		$("#computerNameMessage").text("");
	} else {
		$("#computerNameMessage").css("color", "red");
		$("#computerNameMessage").text(message_validation_wrong_name_format);
	}
	
});

$("#introduced").keyup(function() {
	if (isDayInMonthAndYear($(this).val())) {
		$("#introducedMessage").css("color", "green");
		$("#introducedMessage").text(message_validation_right_date_format);
	} else {
		$("#introducedMessage").css("color", "red");
		$("#introducedMessage").text(message_validation_wrong_date_format);
	}
});


$("#discontinued").keyup(function() {
	if (isDayInMonthAndYear($(this).val())) {
		$("#discontinuedMessage").css("color", "green");
		$("#discontinuedMessage").text(message_validation_right_date_format);
	} else {
		$("#discontinuedMessage").css("color", "red");
		$("#discontinuedMessage").text(message_validation_wrong_date_format);
	}
});