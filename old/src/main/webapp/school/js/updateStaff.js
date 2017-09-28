function updateStaffDetails() {

	if (rootCheckFormValidation($('#updateStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

		global_data_passed = $('#updateStaffRolesForm').serializeJSON();

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

});

function addStaffRoles() {

	if (rootCheckFormValidation($('#editStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

		global_data_passed = $('#updateStaffRolesForm').serializeJSON();

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Genius Code for staff roles adding');

			console.log(data);

			rootParseApiResponseData(data)

		});

	}

}

function alterStaffRoles() {

	if (rootCheckFormValidation($('#editStaffRolesForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

		global_data_passed = $('#updateStaffRolesForm').serializeJSON();

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Genius Code for staff roles altering');

			console.log(data);

			rootParseApiResponseData(data)

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
function fetchStaffRoles() {

	varying_url = "staff/" + $('#uuid').val() + "/subjects/"
			+ $('#accountId').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for specific staff roles fetch');

		console.log(data);

		var cols = [];

		var getCol = data[0]['apiSubjectClasss'];

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
							columns : cols,
							"columnDefs" : [
									{
										"targets" : [ 0 ],
										"visible" : false,
										"searchable" : false
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
										"targets" : [ 6 ],
										"data" : null,
										"defaultContent" : '<button class="btn btn-warning ">'
												+ 'Edit  <span class="fa fa-edit"></span></button>'
												+ '<button class="btn btn-warning ">'
												+ 'Delete  <span class="fa fa-trash"></span></button>'
									} ],

							"order" : [ [ 0, "desc" ] ],
						/* "iDisplayLength": 100 */

						});

		table.rows.add(data).draw();

		$('#staffRoles tbody').on('click', 'button', function() {
			var data = table.row($(this).parents('tr')).data();

			console.log(data['uuid']);

			$('#').val(data['uuid']);
			$('#').val(data['subjectId']);
			$('#').val(data['streamId']);

			$('#staffSujectModal').modal('show');
			$('#staffSCTiltle').text("Edit Staff Assignment/Roles Details");

			$('#btn_editStaffRoles').text("Save Changes");

		});

	});

}
