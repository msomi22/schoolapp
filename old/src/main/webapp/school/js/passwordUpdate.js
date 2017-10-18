function changePasswordModal(){
	
	$('#changePasswordModal').modal('show');
}


function updatePassword() {
	
	if (rootCheckFormValidation($('#changePasswordForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "config/"+$('#accountId').val();

		global_data_passed = $('#changePasswordForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Code for password updating');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
			
				$('#changePasswordModal').modal('hide');
			//();
			}

		});

	}

}