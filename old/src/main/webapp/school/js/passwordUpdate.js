function changePasswordModal(){
	
	
	$('#staffId').val($('#userId').val());
	$('#accountId_profile').val($('#globalAccountId').val());
	$('#username').val($('#user').val());
	
	$('#changePasswordModal').modal('show');
}


function updatePassword() {
	
	if (rootCheckFormValidation($('#changePasswordForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/profile/update/"+$('#globalAccountId').val();

		global_data_passed = $('#changePasswordForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';
		
		

		globalApiCall(function(data) {

			console.log('Code for password updating');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
			
				$('#changePasswordModal').modal('hide');
				
				setTimeout(function(){
					
					window.location=location.protocol + "//" + window.location.host+'/school';
				},2000)
			//();
			}

		});

	}

}


function forgotPassword(){
	
	$('#forgotPassword').show(2000);
	
	$('#register').hide();
	$('.login_form').hide(2000);
	
	
	
}

function requestPassword(){
	
	if (rootCheckFormValidation($('#forgotPasswordForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/reset/password/"+$('#account_name').val()+"/"+$('#query').val();

		global_data_passed ={};
		
		console.log(varying_url);

		console.log($('#account_name').val()+"/"+$('#query').val());

		global_request_type = 'GET';
		
		global_auth= "Y29tUGxleDpyZVN0KkAhQXBp";

		globalApiCall(function(data) {

			console.log('Code for password password reset');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
			
			//	$('#changePasswordModal').modal('hide');
			//();
			}

		});

	}
	
}




