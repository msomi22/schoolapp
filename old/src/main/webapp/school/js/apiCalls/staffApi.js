var table;

$(document)
		.ready(
				function() {

					varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

					global_data_passed = {};

					global_request_type = 'GET';

					globalApiCall(function(data) {

						console.log('Genius Code for staff altering');

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

						table = $('#staffs')
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
														"targets" : [ 3 ],
														"visible" : false
													},
													{
														"targets" : [ 4 ],
														"visible" : false
													},
													{
														"targets" : [ 6 ],
														"visible" : false
													},
													{
														"targets" : [ 9 ],
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
														"data" : null,
														"defaultContent" : '<button class="btn btn-info ">'
																+ 'Profile   <span class="fa fa-info"></span></button>'
													} ],

											"order" : [ [ 0, "desc" ] ],
										/* "iDisplayLength": 100 */

										});

						table.rows.add(data).draw();

						$('#staffs tbody')
								.on(
										'click',
										'button',
										function() {
											var data = table.row(
													$(this).parents('tr'))
													.data();

											console.log(data['uuid']);

											/*
											 * window .open( location.protocol +
											 * "//" + window.location.host +
											 * "/school/school/staffProfile.jsp?uuid=" +
											 * data['uuid'], "_blank");
											 */

											window.location = location.protocol
													+ "//"
													+ window.location.host
													+ "/school/school/staffProfile.jsp?uuid="
													+ data['uuid'];

										});

					});
					
					
					

				});

function addStaffApiCall() {

	if (rootCheckFormValidation($('#staffForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/";

		global_data_passed = $('#staffForm').serializeJSON();

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Genius Code for staff adding');

			console.log(data);

			rootParseApiResponseData(data)

		});

	}

}







