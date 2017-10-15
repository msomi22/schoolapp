$(document).ready(function() {

	$('.c_year').val($('#currentYear').val());
	$('#term').val($('#currentTerm').val());
	
	

	setTimeout(function(){
		feeTermFeeList();
		otherFeeTermFeeList();
		
	},500);
	
	
	$('#term_accountId').val($('#accountId').val());

	

})

function termModal(id) {

	if (id == "edit") {

		// alert(id);
		$('#termFeeTiltle').text("Edit Term Fee Details");

		$('#termFee_btn').text("Save Changes");

		$("#termFee_btn").attr("onclick", "updateTermFee()");

	} else if (id == "add") {

		$('#termFeeTiltle').text("Add a new Term Fee");
		$('#termFee_btn').text("Submit");
		$("#termFee_btn").attr("onclick", "addNewTermFee()");

		$('#TermFeeForm').get(0).reset();

	}

	$('#TermFeeModal').modal('show');

}

function addNewTermFee() {
	
	if (rootCheckFormValidation($('#TermFeeForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "finance/termfee/"+$('#accountId').val();

		global_data_passed = $('#TermFeeForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Code for new term fee adding');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#TermFeeModal').modal('hide');
				feeTermFeeList();
			}

		});

	}

}

function updateTermFee() {
	
	if (rootCheckFormValidation($('#TermFeeForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "finance/termfee/"+$('#accountId').val();

		global_data_passed = $('#TermFeeForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Code for  term fee updating');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#TermFeeModal').modal('hide');
				feeTermFeeList();
			}

		});

	}

}

function delTermFee() {

}

var table_term;

var table_other;

var del_uuid;

function feeTermFeeList() {

	
	/*
	 * table.clear();
	 */
	/*
	 * table.fnClearTable(); table.fnDraw(); table.fnDestroy();
	 */

	

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

				if (table_term)
					{
					// clear datatable
					table_term.clear().draw();
					
					//table.clear();
					

					// destroy datatable
					table_term.destroy();
					}

				table_term = $('#yearlyTermFeeList')
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
												"defaultContent" : '<button class="btn btn-warning" id="edit" onclick="termModal(this.id)">Edit <span class="fa fa-edit"></span></button>'
											} ],
									searching : false,
									"bPaginate" : false,
									"bLengthChange" : false,

									"order" : [ [ 0, "desc" ] ]

								});

				table_term.rows.add(data).draw();

				$('#yearlyTermFeeList tbody').on('click', 'button', function() {
					var data = table_term.row($(this).parents('tr')).data();

					console.log(data);

					// console.log($("#desc").val(data[3]));

					$("#boarder").val(data['boaderAmount']);
					$("#day").val(data['dayAmount']);

					$("#edit_term").val(data['term']);
					$("#edit_year").val(data['year']);

					$("#term_uuid").val(data['uuid']);
					$("#term_accountId").val(data['accountId']);

					del_uuid = (data['uuid']);

				});

			} else {
				 if (table_term)
				{
					// clear datatable
					 table_term.clear().draw();

						// destroy datatable
						table.destroy();
				}

				$('#yearlyFee').removeClass('alert-info secondary-assent ');
				$('#yearlyFee').addClass('alert-danger');
				$('#yearlyFee').html(
						'<b> The yearly fee record is not available, for year :'
								+ $('#inityear').val() + '</b>');
			}

		});
	}
}

function otherFeeTermFeeList() {

	
	/*
	 * table.clear();
	 */
	/*
	 * table.fnClearTable(); table.fnDraw(); table.fnDestroy();
	 */

	console.log('request called');

	if (rootCheckFormValidation($('#otherFeeQuery'))) {

		varying_url = "finance/fee/other/" + $('#accountId').val() + "/"
				+ $('#term').val() + "/" + $('#termyear').val();

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

				if (table_other) {

					// clear datatable
					table_other.clear().draw();

					// destroy datatable
					table_other.destroy();
				}

				table_other = $('#otherFeeList')
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

				table_other.rows.add(data).draw();

				$('#otherFeeList tbody').on('click', 'button', function() {
					var data = table_other.row($(this).parents('tr')).data();

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
				if (table_other) {

					// clear datatable
					table_other.clear().draw();

					// destroy datatable
					table_other.destroy();
				}

				$('#otherFeeSms').removeClass('alert-info secondary-assent ');
				$('#otherFeeSms').addClass('alert-danger');
				$('#otherFeeSms').html(
						'<b> The other fee records is not available, for year '
								+ $('#termyear').val() + ' Term : '
								+ $('#term').val() + '</b>');
			}

		});
	}
}
