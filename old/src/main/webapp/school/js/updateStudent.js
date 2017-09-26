var table;

var uName = "demo";
var passwrd = "12345678";

var currentClassId = "";

var currentAccId = "";

var returnData = {};

var data_passed={};

var url = "";
var request_type = 'GET';

var base_url = location.protocol + "//" + window.location.host
		+ "/school/webapi/student/";

$(document).ready(function() {
	// init update :)
	
	$('#updateStudentInfo input,#updateStudentInfo select,#updateStudentInfo button,#btn_deactivate,#crop-avatar').attr('disabled', true);

	console.log($('#passedParam').val());
	url = "one/" + $('#passedParam').val();

	fetchBasicInfo();

	fetchSubjects();

});

function fetchBasicInfo() {

	apiCall(function(data) {

		console.log('Async call');
		console.log(data);

		$.each(data, function(key, value) {
			$("#updateStudentInfo").find("input[name='" + key + "']")
					.val(value);

			if (key === "apiParentPrimary") {

				$.each(data['apiParentPrimary'], function(key, value) {
					$("#updateStudentInfo").find("input[name='" + key + "']")
							.val(value);
				});

			}

			if (key === "currentStream") {
				$('#currentStream').val(value.trim());

				console.log(key + " value:" + value.trim());

			}

			if (key === "gender")
				$('#gender').val(value.trim());

			if (key === "isBoarding")
				$('#isBoarding').val(value.trim());

			if (key === "county")
				$('#county').val(value.trim());

		});

	});

	// var m_data= apiCall();

	/*
	 * setTimeout(function() {
	 *  // console.log(returnData);
	 *  }, 2000);
	 */

}

function fetchSubjects() {

	url = "subject/" + $('#passedParam').val();

	apiCall(function(data) {

		console.log("Subjects loadded");

		for (var i = 0; i < data.length; i++) {

			console.log(data[i]);

			$('#subjectList')
					.append(
							'<div class="col-md-3 col-md-offset-1 col-sm-12 col-xs-12"><input id="'
									+ data[i]['subjectId']
									+ '" type="checkbox" class="form-control  chk" checked /><label for="math">'
									+ data[i]['description'] + '</label> </div>');

		}

	});

}

function apiCall(handleData) {

	$.ajax(
			{
				url : base_url + url,
				type : request_type,
				dataType : 'json',
				data : data_passed,
				contentType : 'application/json',
				accept : 'application/json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization', 'Basic '
							+ btoa(uName + ":" + passwrd));
				}
			}).done(function(data) {

		// console.log(data);

		returnData = data;

		// console.log(returnData);

		handleData(data);

		// returnData = callBack(data);
		// return returnData;

	}).fail(function(jqXHR, textStatus) {

		// alert("Error: " + textStatus);
		console.log(textStatus);

		returnData = textStatus;

	});

	return returnData;
}




function updateStudent(){
	
	
	
	
	if(checkFormValidation($('#updateStudentInfo'))){
		
		request_type = 'PUT';
		
		url=$('#accountId').val();
		
		data_passed= $('#updateStudentInfo').serializeJSON();
		
		console.log(data_passed);
		
		
		apiCall(function(data) {
			
			
			console.log('Smart Code');
			
			console.log(data);

		});
		
		
	}
	
	
	
	
	
	
	
}


function deactivateModal(){
	
	$('#warning').modal('show');
	
	$('#warningTitle').html('<b>Deactivate the Student</b>');
	
	$('#warningSms').html('<b>Are you sure you want to deactivate the student?</b>');
	
	
	
	
	
	$('#btn_warningState').attr('onclick','deactivateStudent()');
}

function deactivateStudent(){
	
	$('#isActive').val('0');
	console.log($('#updateStudentInfo').serializeJSON());
	
	$('#updateStudentInfo input,#updateStudentInfo select,#updateStudentInfo button,#btn_editState,#btn_deactivate').attr('disabled', true);
	//$('#crop-avatar').attr('id','tempID');
	//avatar-view
	//$('#crop-avatarState').removeClass('avatar-view');
	//$('#crop-avatar').removeClass('profile_img');
	$('#not_possible').html('<b>Not Possible to change the profile pic</b>');
	
}

function activateEditing(){
	
	$('#updateStudentInfo input,#updateStudentInfo select,#updateStudentInfo button,#btn_deactivate').attr('disabled', false);
	
	
}

function checkFormValidation(form) {

	var myform = $(form)[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
			return false;
		} else {
			// warn IE users somehow :)
		}
	} else {

		return true;
	}
}





/*
 * Just joking around with Async calls function callBack(data) {
 * 
 * returnData = data;
 *  // console.log(returnData);
 * 
 * return returnData;
 *  }
 */

/*
 * In case i see a need to alter the search box, please reference this guide
 * This is very simple. First you must hide the default search box :
 * 
 * .dataTables_filter { display: none; } Example of your own designed search
 * box, placed somewhere in the HTML :
 * 
 * <input type="text" id="searchbox"> script to search / filter when typing in
 * the search box
 * 
 * $("#searchbox").keyup(function() { dataTable.fnFilter(this.value); });
 * working demo -> http://jsfiddle.net/TbrtF/
 * 
 * If you are using DataTables 1.10 the JS should look like:
 * 
 * $("#searchbox").on("keyup search input paste cut", function() {
 * dataTable.search(this.value).draw(); });
 * 
 */