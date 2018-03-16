$(document).ready(function() {

	fetchClasses();
	fetchHouses();

	setTimeout(function() {

		fetchStudents();

	}, 1000)

})

var table;

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

					console.log("Fetch students url :" + varying_url);

					console.log(data);

					var cols = [];

					if (table)
						table.clear().draw();

					if (data.length > 0) {

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