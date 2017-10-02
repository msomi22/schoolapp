var start = 0;

var size = 15;

function pagination(button) {

	if (button === "N") {

		$('#F,#P').show(1000);
		$('#N,#L').show(1000);
		$('#F,#L').attr('disabled',false);
		$('').attr('disabled',true);
	} else if (button === "L") {
		$('#F,#P').show(1000);
		$('#N').hide(1000);
		$('#L').attr('disabled',true);
		$('#F').attr('disabled',false);
		
		
		

	} else if (button === "F") {
		$('#N,#L').show(1000);
		$('#P').hide(1000);
		$('#F').attr('disabled',true);
		$('#L').attr('disabled',false);

	} else if (button === "P") {
		$('#F,#P,#N,#L').show(1000);
		$('#F,#L').attr('disabled',false);
		
		

	}

}

$(document).ready(
		function() {

			varying_url = "student/" + $('#accountId').val() + "?start="
					+ start + "&size=" + size;

			fetchStudents(false);

		});

function delayInput() {

	console.log('call successful');

	setTimeout(function() {
		varying_url = "student/" + $('#accountId').val() + "?query="
				+ $('#query').val();
		fetchStudents(true);

	}, 1000)
}

var table;

function fetchStudents(paginate) {

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching students');

		console.log(data);

		var cols = [];

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

		if (table)
			table.clear();

		table = $('#studentsList')
				.DataTable(
						{

							destroy : true,
							"bPaginate" : paginate,
							"bLengthChange" : paginate,
							searching : false,
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
										"targets" : [ 5 ],
										"visible" : false
									},
									{
										"targets" : [ 6 ],
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
										"visible" : false
									},
									{
										"targets" : [ 16 ],
										"visible" : false
									},
									{
										"targets" : [ 17 ],
										"visible" : false
									},
									{
										"targets" : [ 18 ],
										"visible" : false
									},
									{
										"targets" : [ 19 ],
										"visible" : false
									},
									{
										"targets" : [ 20 ],
										"visible" : false
									},
									{
										"targets" : [ 21 ],
										"visible" : false
									},
									{
										"targets" : [ 22 ],
										"visible" : false
									},
									{
										"targets" : [ 23 ],
										"visible" : false
									},
									{
										"targets" : [ 24 ],
										"visible" : false
									},
									{
										"targets" : [ 25 ],
										"visible" : false
									},
									{
										"targets" : [ 26 ],
										"visible" : false
									},
									{
										"targets" : [ 27 ],
										"visible" : false
									},
									{
										"targets" : [ 28 ],
										"data" : null,
										"defaultContent" : '<button class="btn btn-info ">'
												+ 'Profile   <span class="fa fa-info"></span></button>'
									} ],

							"order" : [ [ 0, "desc" ] ],
						/* "iDisplayLength": 100 */

						});

		// fetchAccessLevels();

		table.rows.add(data).draw();

		$('#studentsList tbody')
				.on(
						'click',
						'button',
						function() {
							var data = table.row($(this).parents('tr')).data();

							console.log(data['uuid']);

							/*
							 * window .open( location.protocol + "//" +
							 * window.location.host +
							 * "/school/school/staffProfile.jsp?uuid=" +
							 * data['uuid'], "_blank");
							 */

							window.location = location.protocol + "//"
									+ window.location.host
									+ "/school/school/profile.jsp?uuid="
									+ data['uuid'];

						});

	});
}
