$(document).ready(function() {
	$('#studentsPerClass').DataTable({

		"bPaginate" : false,
		"scrollY" : "400px",
		"scrollCollapse" : true
	});
	$('#inactiveStudents').DataTable({
		"bPaginate" : false,
		"scrollY" : "400px",
		"scrollCollapse" : true
	});

	fetchClassesStudents();
	fetchClasses();

	/*
	 * setTimeout(function (){ fetchStudents($('#streamId').val()); },500)
	 */

});

function fetchStreamsStudents(classIdVal) {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	console.log(classIdVal);

	$('.populateOptionsStudents').val(classIdVal);

	varying_url = "general/streams/" + $('#accountId').val() + "/" + classIdVal
			+ "/";

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching streams');

		console.log(data);

		var stremSelect = $('.populateStreamOptionsStudents');
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
		// default_streamID=classId.options[classId.selectedIndex].value;

		// $('.DefaultStream').val(default_streamID);
		fetchStudents($('#streamId').val());

		// console.log('Current stream: '+default_streamID);

	});

}

function fetchClassesStudents() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "general/class/" + $('#accountId').val();// url;

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching classes');

		console.log(data);

		var classSelect = $('.populateOptionsStudents');
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

		var classId = $('.populateOptionsStudents');
		var classIdVal = $('.populateOptionsStudents').val();// classId.options[classId.selectedIndex].value;

		// var classId= $('#classesList').val();
		console.log(classIdVal);

		fetchStreamsStudents(classIdVal);

	});

}

function shiftStudents() {

	$('.students').each(function() {

		console.log($(this).val())

	})
}

var table;

var studentsHolder = [];


function fetchStudents(streamId) {

	varying_url = "student/" + $('#accountId').val() + "/" + streamId;

	table = $('#studentsPerClass').DataTable({
		destroy : true,
		"bPaginate" : false,
		"scrollY" : "400px",
		"scrollCollapse" : true
	});

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching students');

		console.log("Fetch students url :" + varying_url);

		console.log(data);

		var cols = [];

		if (data.length <= 0) {

			table.clear();

			$('#studentsPerClass').DataTable({
				destroy : true,
				"bPaginate" : false,
				"scrollY" : "400px",
				"scrollCollapse" : true
			});
		} else {

			var getCol = data[0];

			var keys = Object.keys(getCol);

			keys.some(function(k) {

				// return k=="dob";

				cols.push({
					title : k,
					data : k
				// optionally do some type detection here for render
				// function

				});

			});

			if (table)
				table.clear();

			table = $('#studentsPerClass')
					.DataTable(
							{

								destroy : true,
								"bPaginate" : false,
								"scrollY" : "400px",
								"scrollCollapse" : true,
								columns : cols,
								"columnDefs" : [
										{
											"targets" : [ 0 ],
											"visible" : false,
											"searchable" : false
										},
										{
											"targets" : [ 1 ],
											"visible" : false
										},
										{
											"targets" : [ 2 ],
											"visible" : false
										},
										{
											"targets" : [ 3 ],
											"visible" : false
										},
										{
											"targets" : [ 4 ],
											"visible" : false
										},
										{
											"targets" : [ 5 ],
											"visible" : false
										},
										{
											"targets" : [ 11 ],
											"visible" : false
										},
										{
											"targets" : [ 12 ],
											"visible" : false
										},
										{
											"targets" : [ 13 ],
											"visible" : false
										},
										{
											"targets" : [ 14 ],
											"visible" : false
										},
										{
											"targets" : [ 15 ],
											"visible" : false
										},
										{
											"targets" : [ 16 ],
											"visible" : false
										},
										{
											"targets" : [ 17 ],
											"visible" : false
										},
										{
											"targets" : [ 18 ],
											"visible" : false
										},
										{
											"targets" : [ 19 ],
											"visible" : false
										},
										{
											"targets" : [ 20 ],
											"visible" : false
										},
										{
											"targets" : [ 21 ],
											"visible" : false
										},
										{
											"targets" : [ 22 ],
											"visible" : false
										},
										{
											"targets" : [ 23 ],
											"data" : null,
											"defaultContent" : '<input type="checkbox" class="form-control students">'
										} ],

								"order" : [ [ 0, "desc" ] ],
							/* "iDisplayLength": 100 */

							});

			table.rows.add(data).draw();

			$('#studentsPerClass tbody').on('click', 'input', function() {
				var data = table.row($(this).parents('tr')).data();

				console.log(data);
				
				var single_student = {
						studentId :data['uuid'],
						oldClassId : data['currentStream'],
						newClassId : $('#movestreamId').val()
					};

				console.log(single_student);

				// $.inArray(single_student, studentsHolder) == -1
				//if(studentsHolder.length >0){
					
					var index= checkHolder(studentsHolder,single_student);
					console.log(index);
					
					if(index == -1 )
						studentsHolder.splice(0,0,single_student);
					else
						studentsHolder.splice(index,1);
					
					console.log(studentsHolder);
				
				//}else{
				//	studentsHolder.push(single_student);
				//}

				

				/*
				 * window .open( location.protocol + "//" + window.location.host +
				 * "/school/school/staffProfile.jsp?uuid=" + data['uuid'],
				 * "_blank");
				 */

			});
		}

	});

}

function checkHolder(studentsHolder,single_student){
	
	
	var index=-1;
	
	for (var i = 0; i < studentsHolder.length; i++) {

		if (studentsHolder[i]["studentId"] === single_student.studentId)
			{
			
			console.log(studentsHolder[i]);
			
			index=i;
			break
			
			}

			//studentsHolder.splice(i,1);
	//	else
		//	studentsHolder.push(single_student);

	}
	
	return index;
	
}


function initShift(){
	if(studentsHolder.length <=0){
		$('#warningTitle').html('Selected Students');
		$('#warningSms').html('No students selected, please select at least one student');
		
		$('#warning').modal('show');
		
		setTimeout(function(){
			$('#warning').modal('hide');
		},2000);
		
		
	}
	else if($('#movestreamId').val() == $('#streamId').val()){
		$('#warningTitle').html('Shift Students Error');
		$('#warningSms').html('The Streams are similar, select a differrent stream');
		
		$('#warning').modal('show');
		
		setTimeout(function(){
			$('#warning').modal('hide');
		},2000);
		
	}
	else if(rootCheckFormValidation($('#shiftForm'))){
		
		if(updateNewStream(studentsHolder)){
			varying_url = "student/changeclass/" + $('#accountId').val();

			global_data_passed =studentsHolder;

			console.log(JSON.stringify(global_data_passed));

			global_request_type = 'PUT';

			globalApiCall(function(data) {

				console.log(' Code for student class change');

				console.log(data);

				if (rootParseApiResponseData(data)) {

					studentsHolder=[];
					//fetchClassesStudents();
					fetchStudents($('#streamId').val());
					fetchClasses();

				//	fetchStudents($('#movestreamId').val());
					
				/*	$('#populateOptionsStudents').val($('#classList').val());
					fetchStreamsStudents($('#populateOptionsStudents').val());
					
					setTimeout(function(){
						$('#streamId').val($('#movestreamId').val());
					},500);*/
					
				}

			});
			
			
		}
		
	}
}

function updateNewStream(studentsHolder){
	
	for(var i=0; i<studentsHolder.length;i++){
		
		studentsHolder[i]["newClassId"]=$('#movestreamId').val();
	}
	
	return true;
}

function scopeSwapStudentsList(scopeType) {

	if (scopeType == "class_StudentsList") {
		$('#class_option').show('2000');
		$('#stream_option').hide('2000');

	} else {
		$('#class_option').hide('2000');
		$('#stream_option').show('2000');

	}
}

function studentsListModa() {

	$('#accountId_StudentsList').val($('#accountId').val());

	$('#studentsListModal').modal('show');
}
