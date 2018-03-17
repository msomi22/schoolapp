//var default_streamID;

function fetchStreams(classIdVal, state="not_set") {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	console.log(classIdVal);
	
	$('.populateOptions').val(classIdVal);

	varying_url = "general/streams/" + $('#accountId').val() +"/"+ classIdVal + "/";

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching streams');

		console.log(data);

		var stremSelect = $('.populateStreamOptions');
		stremSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			stremSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}
		//default_streamID=classId.options[classId.selectedIndex].value;
		
		if(state == "house")
			fetchStreamStudents();
		
		
		//$('.DefaultStream').val(default_streamID);
		
		//console.log('Current stream: '+default_streamID);

	});

}


function fetchStreamsStudent() {


	varying_url = "general/stream/" + $('#globalAccountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching streams');

		console.log(data);

		var stremSelect = $('.populateStreamOptionsStudent');
		stremSelect.empty();
		

		for (var i = 0; i < data.length; i++) {
			stremSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			
		}
		

	});

}

function fetchClasses() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "general/class/" + $('#accountId').val();//url;

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching classes');

		console.log(data);

		var classSelect = $('.populateOptions');
		classSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			classSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

		console.log(data);

		//var classId = document.getElementById('classList');
		var classIdVal = $('.populateOptions').val();//classId.options[classId.selectedIndex].value;
		
		

		// var classId= $('#classesList').val();
		console.log(classIdVal);

		fetchStreams(classIdVal);

	});

}




function fetchHouses(state='not_set') {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "general/house/" + $('#accountId').val();//url;

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching Houses');

		console.log(data);

		var houseSelect = $('.populateHouses');
		houseSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			
			if(state== 'twerk' && i == 0){
				houseSelect.append('<option>...</option>');
				houseSelect.append('<option id=' + data[i].uuid + ' value='
						+ data[i].uuid + '>' + data[i].houseName + '</option>');
				
			}else
			
			
			houseSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].houseName + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
			
			
		}

		

	});

}



function fetchExams() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));
	
	

	varying_url = "general/exam/" + $('#accountId').val();//url;

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching exams');

		console.log(data);

		var examSelect = $('.populateExams');
		examSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			examSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

		

	});

}