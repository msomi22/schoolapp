var table;

$(document)
		.ready(
				function() {

				
					
					
					fetchMisc();

				});

function updateMisc() {
	
	
	

	if (rootCheckFormValidation($('#miscForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "config/misc/"+$('#accountId').val();

		global_data_passed = $('#miscForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Code for updating Misc settings');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#miscModal').modal('hide');
				fetchMisc();
			}

		});

	}

}




function fetchMisc(){
	varying_url = "config/misc/"+ $('#accountId').val()+"/";

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for getting misc settings');

		console.log(data);
		
		if (data["message"] != "error" && data.length > 0) {

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

		table = $('#miscList')
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
										"defaultContent" : '<button class="btn btn-warning btn-block " onclick="editMiscModal()">'
												+ 'Edit   <span class="fa fa-edit"></span></button>'
									} ],

							"order" : [ [ 0, "desc" ] ],
						/* "iDisplayLength": 100 */

						});
		
		

		table.rows.add(data).draw();

		$('#miscList tbody')
				.on(
						'click',
						'button',
						function() {
							var data = table.row(
									$(this).parents('tr'))
									.data();

							
							$("#key").val(data['key']);
							$("#value").val(data['value']);

							$("#uuid").val(data['uuid']);
							


						});
		
		}

	});
}


function editMiscModal(){
	
	
	$('#miscModal').modal('show');
}









