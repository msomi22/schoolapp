$(document).ready(
		function() {
			$('#studentsPerClass').DataTable({
				
				"bPaginate":false,
				"scrollY" : "400px",
				"scrollCollapse" : true
			});
			$('#inactiveStudents').DataTable({
				"bPaginate":false,
				"scrollY" : "400px",
				"scrollCollapse" : true
			});

			fetchClasses();
			fetchMoveClasses();

			setTimeout(function (){
				fetchStudents($('#streamId').val());
			},500)
			

		})
		
		

function shiftStudents(){
	
	$('.students').each(function(){
		
		console.log($(this).val())
		
	})
}

function fetchMoveStreams(classIdVal) {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	console.log(classIdVal);

	varying_url = "general/streams/" + $('#accountId').val() + "/" + classIdVal
			+ "/";

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching move to streams');

		console.log(data);

		var stremSelect = $('.populateMoveStreamOptions');
		stremSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			stremSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

	});

}

function fetchMoveClasses() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	varying_url = "general/class/" + $('#accountId').val();// url;

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching  move to classes');

		console.log(data);

		var classSelect = $('.populateMoveOptions');
		classSelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = 0; i < data.length; i++) {
			classSelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}

		console.log(data);

		var classId = document.getElementById('classMoveList');
		var classIdVal = classId.options[classId.selectedIndex].value;

		// var classId= $('#classesList').val();
		console.log(classIdVal);

		fetchMoveStreams(classIdVal);

	});

}
var table;


function fetchStudents(streamId) {
	
	varying_url = "student/" + $('#accountId').val() + "/"+streamId;
	
	
	
	table= $('#studentsPerClass').DataTable({
		destroy : true,
		"bPaginate":false,
		"scrollY" : "400px",
		"scrollCollapse" : true
	});


	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching students');

		console.log("Fetch students url :"+varying_url);
		
		console.log(data);

		var cols = [];
		
		if(data.length <= 0){
			
			table.clear();
			
			$('#studentsPerClass').DataTable({
				destroy : true,
				"bPaginate":false,
				"scrollY" : "400px",
				"scrollCollapse" : true
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

		table = $('#studentsPerClass')
				.DataTable(
						{

							destroy : true,
							"bPaginate":false,
							"scrollY" : "400px",
							"scrollCollapse" : true,
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
										"targets" : [ 10],
										"visible" : false
									},
									{
										"targets" : [ 11],
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
										"data" : null,
										"defaultContent" : '<input type="checkbox" class="form-control students">'
									} ],

							"order" : [ [ 0, "desc" ] ],
						/* "iDisplayLength": 100 */

						});
		
		
		
		table.rows.add(data).draw();
		
		$('#studentsPerClass tbody')
		.on(
				'click',
				'input',
				function() {
					var data = table.row($(this).parents('tr')).data();

					console.log(data);

					/*
					 * window .open( location.protocol + "//" +
					 * window.location.host +
					 * "/school/school/staffProfile.jsp?uuid=" +
					 * data['uuid'], "_blank");
					 */

				

				});
		}
		
	});
	
}



