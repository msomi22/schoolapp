function govtCategoryModal(){
	
	
	$('#govtCatForm').get(0).reset();
	
	$('#govtCategoryModal').modal('show');
}

function editGovtCat(id){
	
	$('#govtCatForm').get(0).reset();
	
	$('.'+id).each(function(){
		
		if($(this).attr('name') === "catName")
			$('#catname').val($(this).val());
		
		if($(this).attr('name') === "catAmount")
			$('#amount').val($(this).val());
		if($(this).attr('name') === "catuuid")
			$('#uuid').val($(this).val());
		
		
		
			
		
		
		
		
		
		
		console.log($(this).val());
		
	});
	
	$('#govtCatTiltle').html('<b> Edit the Category Details</b>');
	
	$('#govtCatbtn').attr('onclick', 'updatetGovtCat(this.form)');
	
	
	
	$('#').val();
	
	$('#govtCategoryModal').modal('show');
	
}


function updatetGovtCat(form){
	
	
	
}


function addGovtCategory(form){
	
	
}