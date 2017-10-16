function fetchStreams(classIdVal) {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	console.log(classIdVal);

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

		var classId = document.getElementById('classList');
		var classIdVal = classId.options[classId.selectedIndex].value;

		// var classId= $('#classesList').val();
		console.log(classIdVal);

		fetchStreams(classIdVal);

	});

}