function rootParseApiResponseData(data) {
	if (data.description.includes("success") || data.message.includes("success")) {

		$('#success').modal('show');

		$('#successTitle').text(data.description);
		$('#successSms').text(data.description);
		
		

		setTimeout(function() {

			$('#success').modal('hide');
		}, 2500);
		
		return true;

	} else if (data.message.includes("error")) {

		$('#error').modal('show');

		$('#errorTitle').text(data.description);

		$('#errorSms').text(data.description);

		setTimeout(function() {

			$('#error').modal('hide');
		}, 3500);
		
		return false;

	}else{
		
		$('#error').modal('show');

		$('#errorTitle').text("Response not recognized");

		$('#errorSms').text("Please check with system maintenace team");

		setTimeout(function() {

			$('#error').modal('hide');
		}, 3500);
		
		return false;

		
	}
}