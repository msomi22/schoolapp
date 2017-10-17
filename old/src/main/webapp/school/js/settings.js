$(document).ready(function (){
	
	fetchCategories();
	
	fetchConfig();
	
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
		
		$('#categoryId').val($('#category').val());

	});

}

function fetchConfig() {

	// alert(JSON.stringify($('#staffForm').serializeJSON()));

	

	varying_url = "config/config/" + $('#accountId').val();

	global_data_passed = {};
	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching config');

		console.log(data);
		
		$('#year').val(data['year']);
		$('#term').val(data['term']);

	});

}


function updateYearTerm() {
	
	if (rootCheckFormValidation($('#yearTerm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "config/"+$('#accountId').val();

		global_data_passed = $('#yearTerm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console.log('Code for year and term upodating');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				fetchConfig();
				
			//();
			}

		});

	}

}

function addNewGradeScale() {
	
	
	
	global_request_type = 'POST';
	alterGradeScale();
	
}

function updateGradeScale() {
	
	global_request_type = 'PUT';
	alterGradeScale();
	
}

function alterGradeScale(){
	
	if (rootCheckFormValidation($('#gradingScaleForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "config/scale/"+$('#accountId').val();

		global_data_passed = $('#gradingScaleForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		

		globalApiCall(function(data) {

			console.log('Code for year altering grade scale edit/add');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#gradingScaleModal').modal('hide');
				fetchGradingScale($('#category').val());
				
				
			//();
			}

		});

	}

}

function gradingModal(id) {

	if (id == "edit") {

		// alert(id);
		$('#gradingScaleTiltle').text("Edit Grade Scale Details");

		$('#gradeScale_btn').text("Save Changes");

		$("#gradeScale_btn").attr("onclick", "updateGradeScale()");

	} else if (id == "add") {
		
		

		$('#gradingScaleTiltle').text("Add a  Grade Scale");
		$('#gradeScale_btn').text("Submit");
		$("#gradeScale_btn").attr("onclick", "addNewGradeScale()");
		$('#gradingScaleForm').get(0).reset();
		
		$('#categoryId').val($('#category').val());

	}

	$('#gradingScaleModal').modal('show');

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
									"targets" : [ 6 ],
									"data" : null,
									"defaultContent" : '<button class="btn btn-warning" id="edit" onclick="gradingModal(this.id)">Edit <span class="fa fa-edit"></span></button>'
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
		$("#lowerLimit").val(data['lowerLimit']);

		$("#upperLimit").val(data['upperLimit']);
		$("#points").val(data['points']);

		$("#cat_uuid").val(data['uuid']);
		$("#categoryId").val(data['categoryId']);

		//del_otheruuid = (data['uuid']);

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