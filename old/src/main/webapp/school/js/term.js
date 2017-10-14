$(document).ready(function() {

	$('.c_year').val($('#currentYear').val());
	$('#term').val($('#currentTerm').val());

	feeTermFeeList();
	
	otherFeeTermFeeList();

})

var table;

function feeTermFeeList() {

	table = $('#yearlyTermFeeList')
			.DataTable(
					{

						destroy : true,

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
									"targets" : [ 6 ],
									"data" : null,
									"defaultContent" : '<button class="btn btn-warning" id="edit_termFee" onclick="streamModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" onclick="delTermFee()">Delete <span class="fa fa-trash"></span></button>'
								} ],
						searching : false,
						"bPaginate" : false,
						"bLengthChange" : false,

						"order" : [ [ 0, "desc" ] ]

					});
	/*
	 * table.clear();
	 */
/*
	table.fnClearTable();
	table.fnDraw();
	table.fnDestroy();*/
	
	//clear datatable
	table.clear().draw();

	//destroy datatable
	table.destroy();

	console.log('request called');

	if (rootCheckFormValidation($('#yearFee'))) {

		varying_url = "finance/termfee/" + $('#accountId').val() + "/"
				+ $('#inityear').val();

		global_data_passed = {};

		global_request_type = 'GET';

		globalApiCall(function(data) {

			console.log('Code for fetching term fee list per year');

			console.log(data);

			console.log(data.length);

			if (data["message"] != "error" && data.length > 0) {

				$('#yearlyFee').addClass('alert-info secondary-assent');
				$('#yearlyFee').removeClass('alert-danger');
				$('#yearlyFee').html(
						'<b> The yearly fee record retrieved successfully</b>');

				var cols = [];

				var getCol = data[0];

				console.log(getCol);

				var keys = Object.keys(getCol);

				keys.forEach(function(k) {

					cols.push({
						title : k,
						data : k,
					});

				});

				if (table)
					table.clear();

				table = $('#yearlyTermFeeList')
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
												"targets" : [ 1 ],
												"visible" : false
											},
											{
												"targets" : [ 6 ],
												"data" : null,
												"defaultContent" : '<button class="btn btn-warning" id="edit_termFee" onclick="streamModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" onclick="delTermFee()">Delete <span class="fa fa-trash"></span></button>'
											} ],
									searching : false,
									"bPaginate" : false,
									"bLengthChange" : false,

									"order" : [ [ 0, "desc" ] ]

								});

				table.rows.add(data).draw();

				$('#yearlyTermFeeList tbody').on('click', 'button', function() {
					var data = table.row($(this).parents('tr')).data();

					console.log(data);

					// console.log($("#desc").val(data[3]));

					$("#classId_edit").val(data['classRoomId']);

					$("#uuid").val(data['uuid']);

					$("#del_uuid").val(data['uuid']);

					console.log(data['uuid']);

					currentClassId = data['classRoomId'];

					$("#accountId_add").val(data['accountId']);

					$("#edit_accountId").val(data['accountId']);

				});

			} else {
				// if (table)
				table.clear();

				$('#yearlyFee').removeClass('alert-info secondary-assent ');
				$('#yearlyFee').addClass('alert-danger');
				$('#yearlyFee').html(
						'<b> The yearly fee record is not available, for year :'
								+ $('#inityear').val() +'</b>');
			}

		});
	}
}


function otherFeeTermFeeList() {

	table = $('#otherFeeList')
			.DataTable(
					{

						destroy : true,

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
									"targets" : [ 6 ],
									"data" : null,
									"defaultContent" : '<button class="btn btn-warning" id="edit_termFee" onclick="streamModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" onclick="delTermFee()">Delete <span class="fa fa-trash"></span></button>'
								} ],
						searching : false,
						"bPaginate" : false,
						"bLengthChange" : false,

						"order" : [ [ 0, "desc" ] ]

					});
	/*
	 * table.clear();
	 */
/*
	table.fnClearTable();
	table.fnDraw();
	table.fnDestroy();*/
	
	//clear datatable
	table.clear().draw();

	//destroy datatable
	table.destroy();

	console.log('request called');

	if (rootCheckFormValidation($('#otherFeeQuery'))) {

		varying_url = "finance/fee/other/" + $('#accountId').val() + "/"
				+$('#term').val()+"/"+ $('#termyear').val();

		global_data_passed = {};

		global_request_type = 'GET';

		globalApiCall(function(data) {

			console.log('Code for fetching other fee list per term');

			console.log(data);

			console.log(data.length);

			if (data["message"] != "error" && data.length > 0) {

				$('#otherFeeSms').addClass('alert-info secondary-assent');
				$('#otherFeeSms').removeClass('alert-danger');
				$('#otherFeeSms').html(
						'<b> The yearly fee record retrieved successfully</b>');

				var cols = [];

				var getCol = data[0];

				console.log(getCol);

				var keys = Object.keys(getCol);

				keys.forEach(function(k) {

					cols.push({
						title : k,
						data : k,
					});

				});

				if (table)
					table.clear();

				table = $('#otherFeeList')
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
												"targets" : [ 1 ],
												"visible" : false
											},
											{
												"targets" : [ 6 ],
												"data" : null,
												"defaultContent" : '<button class="btn btn-warning" id="edit_termFee" onclick="streamModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" onclick="delTermFee()">Delete <span class="fa fa-trash"></span></button>'
											} ],
									searching : false,
									"bPaginate" : false,
									"bLengthChange" : false,

									"order" : [ [ 0, "desc" ] ]

								});

				table.rows.add(data).draw();

				$('#otherFeeList tbody').on('click', 'button', function() {
					var data = table.row($(this).parents('tr')).data();

					console.log(data);

					// console.log($("#desc").val(data[3]));

					$("#classId_edit").val(data['classRoomId']);

					$("#uuid").val(data['uuid']);

					$("#del_uuid").val(data['uuid']);

					console.log(data['uuid']);

					currentClassId = data['classRoomId'];

					$("#accountId_add").val(data['accountId']);

					$("#edit_accountId").val(data['accountId']);

				});

			} else {
				// if (table)
				table.clear();

				$('#otherFeeSms').removeClass('alert-info secondary-assent ');
				$('#otherFeeSms').addClass('alert-danger');
				$('#otherFeeSms').html(
						'<b> The other fee records is not available, for year '
								+ $('#termyear').val() +' Term : '+$('#term').val() +'</b>' );
			}

		});
	}
}
