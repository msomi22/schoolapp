function newStudent() {
	
	$('#currentStream').val($('#regStream').val())

	

	if (rootCheckFormValidation($('#registerNewStudent'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "student/"+$('#accountId').val();
		console.log($('#accountId').val());

		global_data_passed = $('#registerNewStudent').serializeJSON();

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Genius Code for adding student');

			console.log(data);

			if(rootParseApiResponseData(data)){
				$('#registerNewStudent').get(0).reset();
				
			}

		});

	}

}

$(document).ready(function (){
	fetchClasses();
})