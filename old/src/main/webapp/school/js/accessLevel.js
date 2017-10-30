$(document).ready(function() {

	fetchAccessLevelsFilter()
})

function fetchAccessLevelsFilter() {

	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel == 'C3915245-00EE-4EF4-9898-ACE59683DD60') {

		console.log('Principal logged in')
		$('#addNewStudentMenu').show(2000);
		

		if ($('#termYearConfig').length) {
			$('#termYearConfig').show(2000);
			
		}
	}

	else if (curentAcessLevel == '615F04C1-00BF-499C-AC7A-B46B69243AAA') {
		console.log('Deputy Principal logged in')
		$('#financeMenu').remove();
		

	}

	else if (curentAcessLevel == '0DE968C9-7309-C481-58F7-AB6CDB1011EH') {
		console.log('CM logged in')
		$('#financeMenu').remove();
		$('#studentMenu').remove();
		// $('#finance').remove();

	} else if (curentAcessLevel == '1CC7F06E-9938-4850-81FB-9CC249C7CFA2') {
		console.log('HOD logged in')
		$('#financeMenu').remove();
		$('#studentMenu').remove();
		
		// $('#finance').remove();

	} else if (curentAcessLevel == 'BDF7F33D-1936-43F3-B14B-8FC3EA3A1265') {
		console.log('Teacher logged in')
		$('#financeMenu').remove();
		$('#studentListMenu').remove();
		$('#studentNewMenu').remove();
		$('#addNewStudentMenu').html('');
		$('#controlMenu').remove();
		$('#staffMenu').remove();

	} else if (curentAcessLevel == '64553348-3229-4869-A13D-CADFC1D3AF46') {
		console.log('Secretary logged in')
		$('#financeMenu').remove();
		$('#controlMenu').remove();
		$('#staffMenu').remove();
		$('#academicsMenu').remove();

	} else if (curentAcessLevel == '0DE968C9-7309-C481-58F7-AB6CDB1011EF') {
		console.log('Bursar logged in')
		$('#studentMenu').remove();
		$('#controlMenu').remove();
		$('#staffMenu').remove();
		$('#academicsMenu').remove();
		if ($('#termYearConfig').length) {
			$('#termYearConfig').show(2000);
		}

	} else {

		$('#studentMenu').remove();
		$('#controlMenu').remove();
		$('#staffMenu').remove();
		$('#academicsMenu').remove();
		$('#financeMenu').remove();

	}

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	/*
	 * varying_url = "config/accsslevel/" + $('#accountId').val();
	 * 
	 * global_data_passed = {}; global_request_type = 'GET';
	 * 
	 * globalApiCall(function(data) {
	 * 
	 * console.log('Genius Code for fetching access levels');
	 * 
	 * console.log(data);
	 * 
	 * if(data.length >0 && !data.message){ var curentAcessLevel=
	 * $('#accessLevel').val();
	 * 
	 * if(data[i].uuid == curentAcessLevel ){ } }
	 * 
	 * var accessSelect = $('.accessLevels'); accessSelect.empty(); //
	 * classSelect.options[classSelect.options.length] // = new Option('Form 1',
	 * 'Value1');
	 * 
	 * for (var i = 0; i < data.length; i++) { accessSelect.append('<option
	 * id=' + data[i].uuid + ' value=' + data[i].uuid + '>' +
	 * data[i].description + '</option>'); //
	 * classSelect.options[classSelect.options.length] // = new
	 * Option(data[i].description, // data[i].uuid); }
	 * 
	 * });
	 */

}