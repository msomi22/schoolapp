function updateStaffDetails() {

	$('#logedUserId').val($('#passedLogId').val());

	$('#logedUserAccessId').val($('#passedLogAcessId').val());

	if (rootCheckFormValidation($('#updateStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

		global_data_passed = $('#updateStaffRolesForm').serializeJSON();

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Genius Code for staff altering');

			console.log(data);

			rootParseApiResponseData(data)

		});

	}

}

$(document).ready(function() {

	fetchStaffDetails();

	setTimeout(function() {

		fetchStaffRoles();

	}, 1000)

	fetchClasses();

	fetchSubjects();

});

function addStaffRoles() {

	$('#teacherId').val($('#uuid').val());

	$('#alterStaffRole_accountId').val($('#accountId').val());

	// $('#passed_log_id').val()

	if (rootCheckFormValidation($('#editStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/" + $('#uuid').val() + "/subjects";

		global_data_passed = $('#editStaffRolesForm').serializeJSON();

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Genius Code for staff roles adding');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#staffSujectModal').modal('hide');

				fetchStaffRoles();

			}

		});

	}

}

function alterStaffRoles() {

	$('#teacherId').val($('#uuid').val());

	$('#alterStaffRole_accountId').val($('#accountId').val());

	if (rootCheckFormValidation($('#editStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/" + $('#uuid').val() + "/subjects/"
				+ $('#alterSR_uuid').val();

		console.log(varying_url)

		global_data_passed = $('#editStaffRolesForm').serializeJSON();

		console.log(global_data_passed)

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Genius Code for staff roles altering');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#staffSujectModal').modal('hide');

				fetchStaffRoles();

			}

		});

	}

}

function fetchStaffDetails() {

	varying_url = "staff/" + $('#accountId').val() + "/" + $('#uuid').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for specific staff fetch');

		console.log(data);

		$.each(data, function(key, value) {
			$("#updateStaffRolesForm").find("input[name='" + key + "']").val(
					value);

			if (key === "gender")
				$('#gender').val(value);
		});

	});
}

var table;

var del_StaffRole;
function fetchStaffRoles() {

	varying_url = "staff/" + $('#uuid').val() + "/subjects/"
			+ $('#accountId').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for specific staff roles fetch');

		console.log(data);

		var cols = [];

		var getCol = data[0];

		var keys = Object.keys(getCol);

		keys.forEach(function(k) {

			cols.push({
				title : k,
				data : k
			// optionally do some type detection here for render
			// function

			});

		});

		if (table)
			table.clear();

		table = $('#staffRoles')
				.DataTable(
						{

							destroy : true,
							"paging" : false,
							searching : false,
							"bPaginate" : false,
							"bLengthChange" : false,
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
										"targets" : [ 4 ],
										"visible" : false
									},

									{
										"targets" : [ 5 ],
										"visible" : false
									},
									{
										"targets" : [ 6 ],
										"visible" : false
									},
									{
										"targets" : [ 7 ],
										"data" : null,
										"defaultContent" : '<button class="btn btn-warning ">'
												+ 'Edit  <span class="fa fa-edit"></span></button>'
												+ '<a class="btn btn-danger ">'
												+ 'Delete  <span class="fa fa-trash"></span></a>'
									} ],

							"order" : [ [ 0, "desc" ] ]
						/* "iDisplayLength": 100 */

						});

		table.rows.add(data).draw();

		$('#staffRoles tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();

			console.log(data['uuid']);

			$('#alterSR_uuid').val(data['uuid']);
			$('#subjectId').val(data['subjectId']);
			$('#streamId').val(data['streamId']);

			$('#staffSujectModal').modal('show');
			$('#staffSCTiltle').text("Edit Staff Assignment/Roles Details");

			$('#btn_editStaffRoles').text("Save Changes");
			$('#btn_editStaffRoles').attr('onclick', 'alterStaffRoles()');

		});

		$('#staffRoles tbody')
				.on(
						'click',
						'a',
						function() {
							var data = table.row($(this).parents('tr')).data();

							console.log(data['uuid']);

							del_StaffRole = data['uuid'];

							$('#disableTitle').html(
									"<b> Delete Staff's Subject Asigned");
							$('#disableSms')
									.html(
											"<b>Are you sure you want to delete the staff's subject? </b>");
							$('#Dis_modal').modal('show');

						});

	});

}

function delStaffRole() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "staff/" + $('#uuid').val() + "/subjects/" + del_StaffRole
			+ "/" + $('#accountId').val();

	global_data_passed = {};

	global_request_type = 'DELETE';

	globalApiCall(function(data) {

		console.log('Genius Code for staff roles delete');

		console.log(data);

		if (rootParseApiResponseData(data)) {

			$('#Dis_modal').modal('hide');

			fetchStaffRoles();

		}

	});

}

function fetchSubjects() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "student/subjects/" + $('#accountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching subjects');

		console.log(data);

		var subjectSelect = $('.populateSubjectOptions');
		subjectSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			subjectSelect.append('<option id=' + data[i].subjectId + ' value='
					+ data[i].subjectId + '>' + data[i].description
					+ '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

	});

}

function fetchStreams(classIdVal) {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	console.log(classIdVal);

	varying_url = "general/streams/E3CDC578-37BA-4CDB-B150-DAB0409270CD/"
			+ classIdVal + "/";

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching streams');

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

	varying_url = "general/class/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching classes');

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
