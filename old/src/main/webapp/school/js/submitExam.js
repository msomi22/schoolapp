function fetchSubjects() {
	
	global_data_passed = {};
	global_request_type = 'GET';

	varying_url = "student/subjects/" + $('#globalAccountId').val();
	
	globalApiCall(function(data) {

		console.log('Code for fetching subjects');

		console.log(data);

		var subSelect = $('.populateSubjects');
		subSelect.empty();
		

		for (var i = 0; i < data.length; i++) {
			subSelect.append('<option id=' + data[i]["subjectId"] + ' value='
					+ data[i]["subjectId"]+ '>' + data[i].description + '</option>');
			
		}
		

	});

	
		

}

function fetchExams() {
	
	global_data_passed = {};
	global_request_type = 'GET';

	varying_url = "general/exam/" + $('#globalAccountId').val();
	
	globalApiCall(function(data) {

		console.log('Code for fetching Exams');

		console.log(data);

		var examSelect = $('.populateExams');
		examSelect.empty();
		

		for (var i = 0; i < data.length; i++) {
			examSelect.append('<option id=' + data[i]["uuid"] + ' value='
					+ data[i]["uuid"]+ '>' + data[i].description +', OutOf'+data[i].outOf + '</option>');
			
		}
		

	});

	
		

}