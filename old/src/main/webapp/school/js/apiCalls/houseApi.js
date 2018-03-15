var table;


$(document).ready(function() {

	if (checkAccessControl())
		fetchHouses();

});

function fetchHouses() {

	varying_url = "general/house/" + $('#accountId').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for house fetching');

		console.log(data);

		if (data["message"] != "error" && data.length >= 1) {

			var cols = [];

			var getCol = data[0];

			var keys = Object.keys(getCol);

			keys.forEach(function(k) {

				if (k == "description") {

					cols.push({
						title : "Description",
						data : k,
					// optionally do some type detection here
					// for
					// render function

					});
				} else {

					cols.push({
						title : k,
						data : k,
					});

				}

			});

			if (table)
				table.clear();

			table = $('#houses')
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
											"targets" : [ 4 ],
											"data" : null,
											"defaultContent" : '<button class="btn btn-warning editExam" id="edit" onclick="houseModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" id="house" onclick="disableHouse(this.id)">Delete <span class="fa fa-trash"></span></button>'
										} ],

								"order" : [ [ 0, "desc" ] ]

							});

			table.rows.add(data).draw();

		}

	});

}

function addHouse(form) {
	
	
	

	if (rootCheckFormValidation($(form))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "general/house/new";

		global_data_passed = $(form).serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Code for house adding');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#houseModal').modal('hide');
				fetchHouses();
			}

		});

	}

}


function updateHouse(form) {
	
	
	

	if (rootCheckFormValidation($(form))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "general/house/update";

		global_data_passed = $(form).serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Code for house updating');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#houseModal').modal('hide');
				fetchHouses();
			}

		});

	}

}


function deleteHouse() {
	
		varying_url = "general/house/delete/" + $('#accountId').val()+"/" + $('#uuid').val();;

		global_data_passed = {};
		global_request_type = 'DELETE';

		globalApiCall(function(data) {

			if(rootParseApiResponseData(data)){
		
				$('#Dis_modal').modal('hide');
				fetchHouses();
			}

		});


}
