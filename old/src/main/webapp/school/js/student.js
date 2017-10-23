var start = 0;

var size = 15;

var total_size=0;

 var page_size=0;
 
 var current_page=1;

var count = 0;


function pagination(button) {

	$('#pagination').show();

	if (button === "N") {

		$('#F,#P').show(1000);
		$('#N,#L').show(1000);
		$('#F,#L,#P').attr('disabled', false);

		if (start < total_size)
			{
			start += size;
		current_page+= 1;
			}
		
		else if (start >= total_size){
			start=total_size;
			count=total_size;
			current_page= page_size;
			$('#N').attr('disabled', true);
			$('#L').attr('disabled', true);
			
		}
		else {
			$('#N').attr('disabled', true);
			$('#L').hide(1000);
		}

	} else if (button === "L") {
		$('#F,#P').show(1000);
		$('#N').hide(1000);
		$('#L').attr('disabled', true);
		$('#F,#P').attr('disabled', false);

		start = total_size;
		count= total_size;
		current_page= page_size;

		
	} else if (button === "F") {
		$('#N,#L').show(1000);
		$('#P').hide(1000);
		$('#F').attr('disabled', true);
		$('#L,#N').attr('disabled', false);
		start = 0;
		count= 0;
		current_page= 1;

	} else if (button === "P") {
		$('#F,#P,#N,#L').show(1000);
		$('#F,#L,#N,#P').attr('disabled', false);
		
		count-=(size*2);
		current_page -=1;

		if (start > 0)
			start -= size;
		else if(start <=0){
			start=0;
			count=0;
			current_page= 0;
			$('#P').attr('disabled', true);
			$('#F').attr('disabled', true);
		}
		else {
			$('#P').attr('disabled', true);
			$('#F').hide(1000);

		}

	}
	
	if(start >= total_size){
		start=total_size;
		count=total_size;
	}else if(start <=0){
		start=0;
		count=0;
	}

	varying_url = "student/" + $('#accountId').val() + "?limit=" + size
			+ "&offset=" + start;

	fetchStudents(false);

}

$(document).ready(
		function() {
			
			
			count = 0;
			
			
			total_size = parseInt($('#total_students').val());
			
			page_size= Math.ceil(total_size/size);
			
			$('#pageSize').html(page_size);
			$('.currentPage').html(current_page);

			console.log(total_size + "page Size:"+page_size +"total :"+total_size);
			total_size = total_size - 15;
			
			
			varying_url = "student/" + $('#accountId').val() + "?limit=" + size
					+ "&offset=" + start;

			fetchStudents(false);

		});

function delayInput() {

	console.log('call successful');

	$('#pagination').hide(1000);

	setTimeout(function() {
		varying_url = "student/" + $('#accountId').val() + "?query="
				+ $('#query').val();

		if ($('#query').val().length <= 0)
			$('#pagination').show(1000);
		
		count=0;

		fetchStudents(true);

	}, 1000)
}

var table;

function fetchStudents(paginate) {
	
	//console.log(page_size);
	if(start >= total_size){
		start=total_size;
		count=total_size;
		current_page=page_size;
	}else if(start <=0){
		start=0;
		count=0;
		current_page=1;
	}
	
	$('#pageSize').html(page_size);
	$('.currentPage').html(current_page);
	console.log(current_page + "of "+page_size);

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Genius Code for fetching students');

		console.log(varying_url);

		var cols = [];

		if (data.length <= 0) {

			table.clear();

			$('#studentsList').DataTable({
				destroy : true,
				searching : false,
				"bLengthChange" : false,
			});
		} else {

			/*$.each(data, function(key, value) {
				if (key == "studentCount") {

					count += 1;
					value = count;

					console.log(count);

				}
			});*/
			
			console.log(count);
			
			if(count <0)
				count=0;
			

			for (var i = 0; i < data.length; i++) {

				count += 1;
				data[i]["studentCount"] = count;

				console.log(count);

			}

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
								"bInfo" : false,
								columns : cols,
								"scrollY" : "400px",
								"scrollCollapse" : true,
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
											"visible" : false
										},
										{
											"targets" : [ 29 ],
											"visible" : false
										},
										{
											"targets" : [ 30 ],
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

		$('#studentsList tbody').on(
				'click',
				'button',
				function() {
					var data = table.row($(this).parents('tr')).data();

					console.log(data['uuid']);

					/*
					 * window .open( location.protocol + "//" +
					 * window.location.host +
					 * "/school/school/staffProfile.jsp?uuid=" + data['uuid'],
					 * "_blank");
					 */

					window.location = location.protocol + "//"
							+ window.location.host
							+ "/school/school/profile.jsp?uuid=" + data['uuid']
							+ "&name=" + data['firstname'] + ' '
							+ data['lastname'];

				});

	});
}
