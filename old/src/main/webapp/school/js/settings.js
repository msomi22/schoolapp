$(document).ready(function (){
	
	fetchCategories();
	
})


function fetchCategories() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	

	varying_url = "config/category/" + $('#accountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching categories');

		console.log(data);

		var categorySelect = $('#category');
		categorySelect.empty();
		// classSelect.options[classSelect.options.length]
		// = new Option('Form 1', 'Value1');

		for (var i = data.length-1; i >=0; i--) {
			categorySelect.append('<option id=' + data[i].uuid + ' value='
					+ data[i].uuid + '>' + data[i].description + '</option>');
			// classSelect.options[classSelect.options.length]
			// = new Option(data[i].description,
			// data[i].uuid);
		}
		
		fetchGradingScale($('#category').val());

	});

}

var table_grade;

function fetchGradingScale(category){
	varying_url = "config/scale/cat/" + $('#accountId').val() + "/"
	+category;

global_data_passed = {};

global_request_type = 'GET';

globalApiCall(function(data) {

console.log('Code for fetching grading scale per catregory');

console.log(data);

console.log(data.length);

if (data["message"] != "error" && data.length > 0) {

	

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

	if (table_grade) {

		// clear datatable
		table_grade.clear().draw();

		// destroy datatable
		//table_grade.destroy();
	}

	table_grade = $('#gradingSystem')
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
									"targets" : [ 6 ],
									"data" : null,
									"defaultContent" : '<button class="btn btn-warning" id="edit_grade" onclick="gradingModal(this.id)">Edit <span class="fa fa-edit"></span></button>'
								} ],
						searching : false,
						"bPaginate" : false,
						"bLengthChange" : false,

						"order" : [ [ 0, "desc" ] ]

					});

	table_grade.rows.add(data).draw();

	$('#gradingSystem tbody').on('click', 'button', function() {
		var data = table_grade.row($(this).parents('tr')).data();

		console.log(data);

		// console.log($("#desc").val(data[3]));

		$("#description").val(data['description']);
		$("#amount").val(data['amount']);

		$("#edit_otherterm").val(data['term']);
		$("#edit_otheryear").val(data['year']);

		$("#term_otheruuid").val(data['uuid']);
		$("#term_otheraccountId").val(data['accountId']);

		del_otheruuid = (data['uuid']);

	});

} else {
	// if (table)
	if (table_grade) {

		// clear datatable
		table_grade.clear().draw();

		// destroy datatable
		//table_grade.destroy();
	}

	
}

});
}