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
			
			if(data["passport"]){
				$('#student_profile_pic').attr("src",'data:image/jpg;base64,'+data["passport"]);
				
				$('#profile_url').val(data["regNo"]+'.png');
				
			}
				

			if (key === "apiParentPrimary") {

				$.each(data['apiParentPrimary'], function(key, value) {
					$("#updateStudentInfo").find("input[name='" + key + "']")
							.val(value);
				});

			}

			if (key === "currentStream") {
				$('#currentStream').val(value.trim());
				
				$('#')

				console.log(key + " value:" + value.trim());

			}
			
			if(key === "regStream")
				$('#regStream').val(value);

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
				data : JSON.stringify(data_passed),
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
	
	
	
	
	//if(!checkFormValidation($('#updateStudentInfo'))){
		
		request_type = 'PUT';
		
		url=$('#accountId').val();
		
		data_passed= $('#updateStudentInfo').serializeJSON();
		
		console.log(data_passed);
		
		
		apiCall(function(data) {
			
			
			console.log('Smart Code');
			
			console.log(data);
			
			parseData(data)

		});
		
		
//	}
	
	
	/*data_passed= $('#updateStudentInfo').serializeJSON();
	
	console.log(JSON.stringify(data_passed));
	
	$
	.ajax(
			{
				url : location.protocol + "//"
						+ window.location.host
						+ "/school/webapi/student/"
						+ $('#accountId').val(),
				type : 'PUT',
				dataType : 'json',
				data : JSON.stringify($('#updateStudentInfo')
						.serializeJSON()),
				contentType : 'application/json',
				accept : 'application/json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization',
							'Basic ZGVtbzoxMjM0NTY3OA==');
				}
			}).done(function(data) {
				
				
				console.log(data);
				
			
			}
			);
	*/
	
	
	
	
	
	
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
	$('#avata_show').html('<b>Not Possible to change the profile pic</b>');
	
}

function activateEditing(){
	
	$('#updateStudentInfo input,#updateStudentInfo select,#updateStudentInfo button,#btn_deactivate').attr('disabled', false);
	
	$('#avata_show').show();
	$('#not_possible').hide();
	
	
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

function parseData(data){
	if (data.description.includes("success")) {

		$('#success').modal('show');

		$('#successTitle').text(data.description);
		$('#successSms').text(data.description);

		$('#updateStudentInfo input,#updateStudentInfo select,#updateStudentInfo button,#btn_deactivate,#crop-avatar').attr('disabled', true);
		$('#not_possible').show();
		$('#not_possible').html('<b>Click on the edit button to be able to edit the avatar</b>');
		$('#avata_show').hide();
		
		


		setTimeout(function() {

			$('#success').modal('hide');
		}, 2500);

	} else if (data.message.includes("error")) {

		$('#error').modal('show');

		$('#errorTitle').text(data.description);

		$('#errorSms').text(data.description);

		setTimeout(function() {

			$('#error').modal('hide');
		}, 3500);

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



/*
"accountId"
:
"E3CDC578-37BA-4CDB-B150-DAB0409270CD",
"bcertNo"
:
"96563",
"classroom"
:
"C143978A-E021-4015-BC67-5A00D6C910D1",
"county"
:
"Narok",
"currentStream"
:
"37D3223A-547E-4BA9-BD0C-28F6187BB5D4",
"dob"
:
"2018-07-03",
"firstname"
:
"Dominic",
"gender"
:
"M",
"hasParent"
:
"false",
"hasPrimary"
:
"false",
"index"
:
"030830597",
"isActive"
:
"1",
"isBoarding"
:
"1",
"kcpemark"
:
"342",
"kcpeyear"
:
"2008",
"lastname"
:
"Gabriel",
"middlename"
:
"Keefe",
"parentEmail"
:
"",
"parentMobile"
:
"",
"parentName"
:
"",
"passport"
:
"",
"regNo"
:
"0998",
"schoolName"
:
"Kathitun",
"uuid"
:
"CC6D62A0-5AA0-46CA-A0AA-C0A651021BDA"*/


