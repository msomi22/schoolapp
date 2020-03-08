function fetchSubjects() {
	
	global_data_passed = {};
	global_request_type = 'GET';

	varying_url = "student/subjects/" + $('#globalAccountId').val();
	
	globalApiCall(function(data) {

		console.log('Code for fetching subjects');

		console.log(data);

		var subSelect = $('.populateSubjects');
		subSelect.empty();
		

		for (var i = 0; i < data.length; i++) {
			subSelect.append('<option id=' + data[i]["subjectId"] + ' value='
					+ data[i]["subjectId"]+ '>' + data[i].description + '</option>');
			
		}
		

	});

	
		

}

function fetchExams() {
	
	global_data_passed = {};
	global_request_type = 'GET';

	varying_url = "general/exam/" + $('#globalAccountId').val();
	
	globalApiCall(function(data) {

		console.log('Code for fetching Exams');

		console.log(data);

		var examSelect = $('.populateExams');
		examSelect.empty();
		

		for (var i = 0; i < data.length; i++) {
			examSelect.append('<option id=' + data[i]["uuid"] + ' value='
					+ data[i]["uuid"]+ '>' + data[i].description +', OutOf'+data[i].outOf + '</option>');
			
		}
		

	});


}


var table;

var editor = new $.fn.dataTable.Editor( {
    
} );

function initExamEntry(form){
	
	global_data_passed = {};
	global_request_type = 'GET';

	varying_url = "exam/student/" + $('#globalAccountId').val()+"/"+$('#streamId').val()+
	"/"+$('#subjectId').val()+"/"+$('#examId').val();
	
	globalApiCall(function(data) {

		console.log('Code for getting student list for exam entry');

		console.log(data);
		
		
		if(data["message"] != "error" && data.length > 0)
		{
		

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

		table = $('#submitExamScore')
				.DataTable(
						{

							destroy : true,
							
							"bPaginate" : false,
							"bLengthChange" : false,
							"scrollY" : "350px",
							"scrollCollapse" : true,
							columns : cols,
							"columnDefs" : [
									{
										"targets" : [ 0 ],
										"visible" : false,
										"searchable" : false
									},
									
									{
										"targets" : [ 7 ],
										"data" : null,
										className: 'editable',
										"defaultContent" : '0'
									} ],

							"order" : [ [ 5, "desc" ] ],
							
							select: {
					            style:    'os',
					            selector: 'td:first-child'
					        },
					        buttons: [
					            { extend: 'create', editor: editor },
					            { extend: 'edit',   editor: editor },
					            { extend: 'remove', editor: editor }
					        ]
						/* "iDisplayLength": 100 */

						});
		
	

		table.rows.add(data).draw();
		
		
		
		}else{
			
			 table.clear().draw();
		}

		
		

	});
	
	
	
}













