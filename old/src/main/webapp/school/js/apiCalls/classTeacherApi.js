$(document).ready(function (){
	
	
	//var url= "general/class/" + $('#accountId').val();
	
	fetchClasses();
	
	

	
	
});





function ctModal(id){
	
	if(id=="add"){
		
		$('#ctTitle').html("<b> Add a new Class Teacher");
		
		$('#btn_ct').prop('onclick', 'addCTAlloc()');
		
		
	}else if(id == "edit"){
		
		$('#ctTitle').html("<b> Edit Class Teacher Allocations");
		$('#btn_ct').prop('onclick', 'alterCTAlloc()');
		
		
		
	}
	
	
	$('#ctModal').modal('show');
	
	
}


function alterCTAlloc(){
	
	if (rootCheckFormValidation($('#staffForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "staff/"+$('#accountId').val();

		global_data_passed = $('#staffForm').serializeJSON();
		
		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console.log('Genius Code for staff adding');

			console.log(data);

			if(rootParseApiResponseData(data)){
				
				
				$('#staff').modal('hide');
			}

		});

	}
	
}


function addCTAlloc(){
	
}