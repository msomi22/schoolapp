function fetchConfig() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	

	varying_url = "config/config/" + $('#accountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching config');

		console.log(data);
		
		$('.year').html('&nbsp; Year : '+data['year']);
		$('.term').html('&nbsp; Term : '+data['term']);

	});

}

$(document).ready(function (){
	
	fetchConfig();
	
})
