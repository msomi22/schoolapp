$(document).ready(function() {

	// var url= "general/class/" + $('#accountId').val();

	fetchClasses();

	fetchCTList();
	populateCTSelect();

});

function ctModal(id) {

	if (id == "add") {

		$('#ctTitle').html("<b> Add a new Class Teacher");

		$('#btn_classTeacher').attr('onclick', 'addCTAlloc()');
		
		$('#editCTForm').get(0).reset();
		
		$("#ct_accountId").val($('#accountId').val());

	} else if (id == "edit") {

		$('#ctTitle').html("<b> Edit Class Teacher Allocations");
		$('#btn_classTeacher').attr('onclick', 'alterCTAlloc()');

	}

	$('#ctModal').modal('show');

}

function alterCTAlloc() {
	
	console.log("Request triggered");

	if (rootCheckFormValidation($('#editCTForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/classteacher";

		global_data_passed = $('#editCTForm').serializeJSON();

		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Genius Code for alerting class teacher');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#ctModal').modal('hide');
				fetchCTList();
			}

		});

	}

}

var table;

var del_id;


function fetchCTList() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "staff/classteacher/" + $('#accountId').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching class teachers');

		console.log(data);

		if (data.length >= 1) {

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

			table = $('#ctList')
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
											"targets" : [ 2 ],
											"visible" : false
										},
										{
											"targets" : [ 5 ],
											"visible" : false
										},
										{
											"targets" : [ 7 ],
											"data" : null,
											"defaultContent" : '<button class="btn btn-warning" id="edit" onclick="ctModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" onclick="delModal()">Delete <span class="fa fa-trash"></span></button>'
										} ],

							});

			table.rows.add(data).draw();

			$('#ctList tbody').on('click', 'button', function() {
				var data = table.row($(this).parents('tr')).data();

				console.log(data);
				//console.log(data['accountId']);
				$("#streamId").val(data['streamId']);
				
				
				
				console.log(data['streamId']);
				

				$("#ct_uuid").val(data['uuid']);

				$("#staffId").val(data['staffId']);
				
				
				console.log($('#streamId').val() +'staff : '+$('#streamId').val());

				del_id=data['uuid'];
				
				console.log(del_id);

				
				$("#ct_accountId").val(data['accountId']);

			});

		}

	});

}

function addCTAlloc() {
	
	console.log("Request triggered");

	if (rootCheckFormValidation($('#editCTForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/classteacher";

		global_data_passed = $('#editCTForm').serializeJSON();

		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log(' Code for adding class teacher');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#ctModal').modal('hide');
				
				fetchCTList();
			}

		});

	}

}

function delCTAlloc() {
	
	console.log("Request triggered");

	

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/classteacher/"+$('#accountId').val()+"/"+del_id;

		global_data_passed = {};

		

		global_request_type = 'DELETE';

		globalApiCall(function(data) {

			console.log(' Code for deleting class teacher');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#Dis_modal').modal('hide');
				
				fetchCTList();
			}

		});

	

}


function delModal(){
	$('#Dis_modal').modal('show');
	
	$('#disableTitle').html('<b>Delete Class Teacher Allocation</b>');
	
	$('#disableSms').html('<b>Are you sure you want to delete the class teacher allocation?</b>');
	
	$('#btn_dis').attr('onclick','delCTAlloc()');
}


function populateCTSelect(){
	
	varying_url = "staff/" + $('#accountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for populating ct select list');

		console.log(data);

		var ctSelect = $('.populateStaffs');
		ctSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			ctSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].firstname+ ' '+data[i].lastname + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

	});
	
}



