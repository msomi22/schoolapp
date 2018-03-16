var studentsHolder =[];
var table;

$(document).ready(function() {

	fetchClasses();
	fetchHouses();

	setTimeout(function() {

		fetchStudents();

	
	
	
	
	
	$('#studentsPerHouse tbody').on('click', 'input', function() {
		var data = table.row($(this).parents('tr')).data();

		console.log(data);

		var single_student = data;

		console.log(single_student);

		// $.inArray(single_student, studentsHolder) == -1
		// if(studentsHolder.length >0){
		$('#selectCurrentHouse').prop('checked', false);

		var index = checkHolder(studentsHolder, single_student);
		console.log(index);

		if (index == -1)
			studentsHolder.splice(0, 0, single_student);
		else
			studentsHolder.splice(index, 1);

		console.log(studentsHolder);

		// }else{
		// studentsHolder.push(single_student);
		// }

		/*
		 * window .open( location.protocol + "//" + window.location.host +
		 * "/school/school/staffProfile.jsp?uuid=" + data['uuid'], "_blank");
		 */

	});
	
	}, 100);

})


function checkHolder(studentsHolder, single_student) {

	var index = -1;

	for (var i = 0; i < studentsHolder.length; i++) {

		if (studentsHolder[i]["studentId"] === single_student.studentId) {

			console.log(studentsHolder[i]);

			index = i;
			break

		}

		// studentsHolder.splice(i,1);
		// else
		// studentsHolder.push(single_student);

	}

	return index;

}

function updateNewHouse(studentsHolder) {

	for (var i = 0; i < studentsHolder.length; i++) {

		studentsHolder[i]["houseId"] = $('#moveHouseID').val();
		studentsHolder[i]["houseName"] = $( "#moveHouseID option:selected" ).text();
	}

	return true;
}


function selectCurrentHouse() {

	
	console.log($('#selectCurrentHouse').is(':checked'));
	if ($('#selectCurrentHouse').is(':checked'))
		$('.students').each(function() {
			$(this).prop('checked', true);
			
		})
	else
		$('.students').each(function() {
			$(this).prop('checked', false);
			studentsHolder = [];
		})

}


function initShiftHouse() {
	if (studentsHolder.length <= 0) {
		$('#warningTitle').html('Selected Students');
		$('#warningSms').html(
				'No students selected, please select at least one student');

		$('#warning').modal('show');

		setTimeout(function() {
			$('#warning').modal('hide');
		}, 2000);

	} else if ($('#moveHouseID').val() == $('#houseId').val()) {
		$('#warningTitle').html('Shift Students Error');
		$('#warningSms').html(
				'The Houses are similar, select a differrent House');

		$('#warning').modal('show');

		setTimeout(function() {
			$('#warning').modal('hide');
		}, 2000);

	} else if (rootCheckFormValidation($('#shiftForm'))) {

		if (updateNewHouse(studentsHolder)) {
			varying_url = "general/house/student/new/" + $('#accountId').val();

			global_data_passed = studentsHolder;

			console.log(JSON.stringify(global_data_passed));

			global_request_type = 'POST';

			globalApiCall(function(data) {

				console.log(' Code for student house class change');

				console.log(data);

				if (rootParseApiResponseData(data)) {

					studentsHolder = [];
					// fetchClassesStudents();
					
					
					//fetchClasses();
					//fetchHouses();
					
					$('#houseId').val($('#moveHouseID').val())

					setTimeout(function() {

						fetchStudents();
						
					},100);
					
					
					$('#selectCurrentHouse').prop('checked', false);

					// fetchStudents($('#movestreamId').val());

					/*
					 * $('#populateOptionsStudents').val($('#classList').val());
					 * fetchStreamsStudents($('#populateOptionsStudents').val());
					 * 
					 * setTimeout(function(){
					 * $('#streamId').val($('#movestreamId').val()); },500);
					 */

				}

			});

		}

	}
}




function fetchStudents() {

	setTimeout(
			function() {

				varying_url = "general/house/studentlist/"
						+ $('#accountId').val() + "/" + $('#houseId').val()
						+ "/" + $('#streamId').val();

				/*
				 * table = $('#studentsPerClass').DataTable({ destroy : true,
				 * "bPaginate" : false, "scrollY" : "400px", "scrollCollapse" :
				 * true });
				 */

				global_data_passed = {};

				global_request_type = 'GET';

				globalApiCall(function(data) {

					console.log('Code for fetching students');

					

					console.log(data);

					var cols = [];

					if (table)
						table.clear().draw();

					if (data.length > 0) {

						var getCol = data[0];

						var keys = Object.keys(getCol);
						
						studentsHolder = data;

						keys.some(function(k) {

							// return k=="dob";

							cols.push({
								title : k,
								data : k
							// optionally do some type detection here for render
							// function

							});

						});

						table = $('#studentsPerHouse')
								.DataTable(
										{

											destroy : true,
											"bPaginate" : false,
											"scrollY" : "400px",
											/* "scrollX": "100%", */

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
														"targets" : [ 5 ],
														"visible" : false
													},
													{
														"targets" : [ 7 ],
														"visible" : false
													},
													{
														"targets" : [ 8 ],
														"visible" : false
													},
													{
														"targets" : [ 9 ],
														"data" : null,

														"defaultContent" : '<input type="checkbox" class="students">'
													} ],

											"order" : [ [ 6, "desc" ] ]
										/* "iDisplayLength": 100 */

										});

						table.rows.add(data).draw();

					}

				})

			}, 100)

}