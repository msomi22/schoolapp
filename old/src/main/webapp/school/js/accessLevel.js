$(document).ready(function() {

	fetchAccessLevelsFilter();
	

	/*var message = "function disabled";

	function rtclickcheck(keyp){ if (navigator.appName == "Netscape" && keyp.which == 3){  console.log(message); return false; }

	if (navigator.appVersion.indexOf("MSIE") != -1 && event.button == 2) { console.log(message); return false; } }

	document.onmousedown = rtclickcheck;*/
})

function fetchAccessLevelsFilter() {

	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel === '100') {

		console.log('Principal logged in')
		$('#addNewStudentMenu').show(2000);
		

		if ($('#termYearConfig').length) {
			$('#termYearConfig').show(2000);
			
		}
	}

	else if (curentAcessLevel === '200') {
		console.log('Deputy Principal logged in')
		$('#financeMenu').remove();
		

	}

	else if (curentAcessLevel === '300') {
		console.log('CM logged in')
		$('#financeMenu').remove();
		$('#studentMenu').remove();
		// $('#finance').remove();

	} else if (curentAcessLevel === '400') {
		console.log('HOD logged in')
		$('#financeMenu').remove();
		$('#studentMenu').remove();
		
		// $('#finance').remove();

	} else if (curentAcessLevel === '500') {
		console.log('Teacher logged in')
		$('#financeMenu').remove();
		$('#studentListMenu').remove();
		$('#studentNewMenu').remove();
		$('#addNewStudentMenu').html('');
		$('#controlMenu').remove();
		$('#staffMenu').remove();

	} else if (curentAcessLevel === '600') {
		console.log('Secretary logged in')
		$('#financeMenu').remove();
		$('#controlMenu').remove();
		$('#staffMenu').remove();
		$('#academicsMenu').remove();

	} else if (curentAcessLevel === '700') {
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