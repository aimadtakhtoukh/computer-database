var doesDateExist = function(day, month, year) {
	if (month < 1 || month > 12) {
		return false;
	}		
	if (day >= 1 && day <= 28) {
		return true;
	}
	if (month != 2) {
		return (day == 29) || (day == 30) || (((day == 31) && !(month == 4 || month == 6 || month == 9 || month == 11)));
	} else {
		if (day == 29) {
			return ((year % 4 == 0) && !(year % 100 == 0)) || ((year % 400) == 0);
		}
	}
	return false;
}

var isDayInMonthAndYear = function(date) {
	pattern = /^\d{1,2}-\d{1,2}-\d{1,4}$/;
	if(!pattern.test(date)) {
		return false;
	}
	subPattern = /\d+/g;
	matches = date.match(subPattern);
	day = matches[0];
	month = matches[1];
	year = matches[2];
	return doesDateExist(day, month, year);
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