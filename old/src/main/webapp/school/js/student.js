var start = 0;

var size = 15;

var total_size;

$(document).ready(function(){
	total_size= $('#total_students').val();
})
function pagination(button) {
	
	
	$('#pagination').show();

	if (button === "N") {

		$('#F,#P').show(1000);
		$('#N,#L').show(1000);
		$('#F,#L,#P').attr('disabled', false);

		if (start < total_size)
			start += size;
		else{
			$('#N').attr('disabled', true);
			$('#L').hide(1000);
		}
			
	} else if (button === "L") {
		$('#F,#P').show(1000);
		$('#N').hide(1000);
		$('#L').attr('disabled', true);
		$('#F,#P').attr('disabled', false);

		start = total_size-15;

	} else if (button === "F") {
		$('#N,#L').show(1000);
		$('#P').hide(1000);
		$('#F').attr('disabled', true);
		$('#L,#N').attr('disabled', false);
		start = 0;

	} else if (button === "P") {
		$('#F,#P,#N,#L').show(1000);
		$('#F,#L,#N,#P').attr('disabled', false);

		if (start > 0)
			start -= size;
		else{
			$('#P').attr('disabled', true);
			$('#F').hide(1000);
			
		}
			

	}

	varying_url = "student/" + $('#accountId').val() + "?start=" + start
			+ "&size=" + size;

	fetchStudents(false);

}

$(document).ready(
		function() {

			varying_url = "student/" + $('#accountId').val() + "?start="
					+ start + "&size=" + size;

			fetchStudents(false);

		});

function delayInput() {

	console.log('call successful');
	
	$('#pagination').hide(1000);

	setTimeout(function() {
		varying_url = "student/" + $('#accountId').val() + "?query="
				+ $('#query').val();
		
		if($('#query').val().length <= 0)
			$('#pagination').show(1000);
			
		fetchStudents(true);

	}, 1000)
}

var table;

function fetchStudents(paginate) {

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching students');

		console.log(varying_url);

		var cols = [];
		
		if(data.length <= 0){
			
			table.clear();
			
			$('#studentsList').DataTable({
				destroy : true,
				searching : false,
				"bLengthChange" : false,
			});
		}else{

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
										"targets" : [ 7 ],
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
										"targets" : [ 24],
										"visible" : false
									},
									{
										"targets" : [ 25 ],
										"visible" : false
									},
									{
										"targets" : [ 26],
										"visible" : false
									},
									{
										"targets" : [ 27 ],
										"visible" : false
									},
									{
										"targets" : [ 28 ],
										"visible" : false
									},
									{
										"targets" : [ 29 ],
										"data" : null,
										"defaultContent" : '<button class="btn btn-info ">'
												+ 'Profile   <span class="fa fa-info"></span></button>'
									} ],

							"order" : [ [ 8, "asc" ] ],
						/* "iDisplayLength": 100 */

						});
		}

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
									+ data['uuid']+"&name="+data['firstname']+' '+data['lastname'];

						});

	});
}
