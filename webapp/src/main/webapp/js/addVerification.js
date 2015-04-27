var isDayInMonthAndYear = function(date) {
	pattern = /^\d{1,2}-\d{1,2}-\d{1,4}$/;
	return pattern.test(date);
}

if($("#computerNameMessage").val() == "") {
	$("#computerNameMessageDiv").css("display", "none");	
}
if ($("#introducedMessage").val() == "") {
	$("#introducedMessageDiv").css("display", "none");	
}
if ($("#discontinuedMessage").val() == "") {
	$("#discontinuedMessageDiv").css("display", "none")
}

$("#computerName").keyup(function() {
	if ($(this).val() !== "") {
		$("#computerNameMessageDiv").css("display", "none");
		$("#computerNameMessage").text("");
	} else {
		$("#computerNameMessageDiv").css("display", "flex");
		$("#computerNameMessageDiv").addClass("alert alert-danger");
		$("#computerNameMessage").text(message_validation_wrong_name_format);
	}
	
});

$("#introduced").keyup(function() {
	if ($(this).val() == "") {
		$("#introducedMessageDiv").css("display", "none");	
	} else {
		if (isDayInMonthAndYear($(this).val())) {
			$("#introducedMessageDiv").css("display", "flex");	
			$("#introducedMessageDiv").removeClass("alert alert-danger");
			$("#introducedMessageDiv").addClass("alert alert-success");
			$("#introducedMessage").text(message_validation_right_date_format);
		} else {
			$("#introducedMessageDiv").css("display", "flex");	
			$("#introducedMessageDiv").removeClass("alert alert-success");
			$("#introducedMessageDiv").addClass("alert alert-danger");
			$("#introducedMessage").text(message_validation_wrong_date_format);
		}
	}
});


$("#discontinued").keyup(function() {
	if ($(this).val() == "") {
		$("#discontinuedMessageDiv").css("display", "none");
	} else {	
		if (isDayInMonthAndYear($(this).val())) {
			$("#discontinuedMessageDiv").css("display", "flex");
			$("#discontinuedMessageDiv").removeClass("alert alert-danger");
			$("#discontinuedMessageDiv").addClass("alert alert-success");
			$("#discontinuedMessage").text(message_validation_right_date_format);
		} else {
			$("#discontinuedMessageDiv").css("display", "flex");
			$("#discontinuedMessageDiv").removeClass("alert alert-success");
			$("#discontinuedMessageDiv").addClass("alert alert-danger");
			$("#discontinuedMessage").text(message_validation_wrong_date_format);
		}
	}
});