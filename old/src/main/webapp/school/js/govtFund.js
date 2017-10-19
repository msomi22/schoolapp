var preserveUrl = global_url;

var feeCatId = "99AC5A73-A3A2-4EEB-AC12-9624266122C2";

var accoutId = "";
var uuid="";

$(document).ready(function() {

	fetCategories();
	fetchConfig();

});

function fetCategories() {

	$('#govtCatList').html('');

	accountId = $('#loggedId').val();
	
	$('#feeBreakdownId').val(feeCatId);
	
	$('#accountId').val(accountId);

	//global_url = global_url + 
	varying_url="finance/goke/" + accountId + '/' + feeCatId;

	globalApiCall(function(data) {

		console.log('Async call of the global api govt category breakdown');
		console.log(data);

		for (var i = 0; i < data.length; i++) {

			$('#govtCatList')
					.append(
							'<div class="row">' + '<div class="col-md-3">'
									+ '<input type="text" '
									+ 'placeholder="Enter Category Name"'
									+ 'class="form-control formelement '
									+ data[i]['uuid']
									+ '" name="catName" value="'
									+ data[i]['feeDescription']
									+ '"'
									+ 'readonly="readonly">'
									+ '</div>'
									+ '<input type="hidden" name="catuuid" class="'
									+ data[i]['uuid']
									+ '" value="'
									+ data[i]['uuid']
									+ '">'

									+ '<div class="col-md-3">'
									+ '<input type="text" '
									+ 'placeholder="Enter Category Amount"'
									+ 'class="form-control formelement '
									+ data[i]['uuid']
									+ '" name="catAmount" value="'
									+ data[i]['amount']
									+ '"'
									+ 'readonly="readonly">'
									+ '</div>'

									+ '<div class="col-md-1">'

									+ '	<button type="button" class="btn btn-primary my_btn" id="'
									+ data[i]['uuid']
									+ '" onclick="editGovtCat(this.id)"'
									+ '	style="border-radius: 90%">'
									+ '	<span class="fa fa-pencil-square-o "></span>'
									+ '</button>'

									+ '	</div>'
									+ '<div class="col-md-1">'

									+ '	<button type="button" class="btn btn-primary my_btn"'
									+'id="'+data[i]['uuid']+'"onclick="deGovtModal(this.id)"'
									+ 'style="border-radius: 90%">'
									+ '	<span class="fa fa-trash del"></span>'
									+ '	</button>'

									+ '</div>'

									+ '</div>');

		}
		/*
		 * "E3CDC578-37BA-4CDB-B150-DAB0409270CD" amount : 90 feeBreakdownId :
		 * "99AC5A73-A3A2-4EEB-AC12-9624266122C2" feeCode : "100" feeDescription :
		 * "R.M.I" uuid : "19FF7382-77E0-4789-BBC2-3EE1261722C2"
		 */

	//	global_url = preserveUrl; heheh!
	});
}

function govtCategoryModal() {

	$('#govtCatForm').get(0).reset();

	$('#govtCategoryModal').modal('show');
}

function editGovtCat(id) {

	$('#govtCatForm').get(0).reset();

	$('.' + id).each(function() {

		if ($(this).attr('name') === "catName")
			$('#catname').val($(this).val());

		if ($(this).attr('name') === "catAmount")
			$('#amount').val($(this).val());
		if ($(this).attr('name') === "catuuid")
			$('#uuid').val($(this).val());

		console.log($(this).val());

	});

	$('#govtCatTiltle').html('<b> Edit the Category Details</b>');

	$('#govtCatbtn').attr('onclick', 'updatetGovtCat(this.form)');

	$('#').val();

	$('#govtCategoryModal').modal('show');

}

function updatetGovtCat(form) {
	
	submitCatData('PUT');
	
	

}

function addGovtCategory(form) {
	
	submitCatData('POST');
	
	

}


function deGovtModal(id){
	
	uuid= id;
	
	$('#del_modal').modal('show');
	
}


function parseData(data){
	if (data.description.includes("success")) {

		$('#success').modal('show');

		$('#successTitle').text(data.description);
		$('#successSms').text(data.description);

		$('#govtCatForm').get(0).reset();
		
		fetCategories();

		setTimeout(function() {

			$('#govtCategoryModal').modal('hide');
			$('#del_modal').modal('hide');
		}, 2000);

		setTimeout(function() {

			$('#success').modal('hide');
		}, 3000);

	} else if (data.message.includes("error")) {

		$('#error').modal('show');

		$('#errorTitle').text(data.description);

		$('#errorSms').text(data.description);

		setTimeout(function() {

			$('#error').modal('hide');
		}, 3500);

	}
}


function submitCatData(req_type) {

	var myform = $("#govtCatForm")[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {


		$
				.ajax(
						{
							url : location.protocol + "//"
									+ window.location.host
									+ "/school/webapi/finance/goke/"
									+ accountId,
							type : req_type,
							dataType : 'json',
							data : JSON.stringify($('#govtCatForm')
									.serializeJSON()),
							contentType : 'application/json',
							accept : 'application/json',
							beforeSend : function(xhr) {
								xhr.setRequestHeader('Authorization',
										'Basic ZGVtbzoxMjM0NTY3OA==');
							}
						}).done(function(data) {

							parseData(data);

				

				}).fail(function(jqXHR, textStatus) {

					// alert("Error: " + textStatus);

					$('#error').modal('show');

					$('#errorTitle').text("Fatal Error");

					$('#errorSms').text(textStatus);

					setTimeout(function() {

						$('#error').modal('hide');
					}, 2500);
				})

	}
}





function delGovtCat() {

	//uuid = $('#del_uuid').val();


	$.ajax(
			{

				url : location.protocol + "//"
						+ window.location.host
						+ "/school/webapi/finance/goke/"+accountId+ "/"+uuid,
				type : 'DELETE',
				dataType : 'json',
				contentType : 'application/json',
				accept : 'application/json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization', 'Basic ZGVtbzoxMjM0NTY3OA==');
				}
			}).done(function(data) {
				
				parseData(data);

		

		

	}).fail(function(jqXHR, textStatus) {

		// alert("Error: " +
		// textStatus);

		$('#error').modal('show');

		$('#errorTitle').text("Fatal Error");

		$('#errorSms').text(textStatus);

		setTimeout(function() {

			$('#error').modal('hide');
		}, 2500);
	})

}

function modalTimeout(modal){
	
	
	setTimeout(function(){
		
		$('#'+modal).modal('hide');
	}, 2000);
}


function processTotalAmount(){
	
	
	var amounReg= /[0-9]{4,7}/;
	
	var value= $('#totalAmount').val();
	
	
	
	if(!value.match(amounReg)){
		
		console.log("Passsed value:"+value);
		
		$('#warningTitle').html('<b> Total Amount Error</b>');
		
		$('#warningSms').html('<b> Invalid input for total amount, the amount should onlt be digits and more than 3 digits e.g 10000, 90000</b>');
		
		
		
		
		$('#warning').modal('show');
		
		modalTimeout('warning');
		
		
		
		
		
		
		
	}else{
	
	
	$('#totalAmount').attr('disabled', true);
	
	varying_url = "finance/fee/gokcheck/" +accountId+"/"+value;

	global_data_passed = {};
	global_request_type = 'GET';
	
	$('#govtCheckResponse').html('');

	globalApiCall(function(data) {

		console.log('Code for checking govt amount Distri');

		console.log(data);
		
		$('#govtCheckResponse').append('<br> Number of Students : '+data['numberOfStudents']
		+'<br> Total Amount : '+data['totalAmount']
		+'<br> Expected Amount :'+data['expectedAmount']
		+'<br> Amount per Student :'+data['amountPerStudent']
		+'<br> Balance : '+data['balance']);
		
		$('#amountPerStudent').html("The Amount to be allocated to each student is, "+data['amountPerStudent']);
		
		
		if(parseInt(data['balance'])<0){
			
			$('#errorTitle').html('<b> Amount Insufficient </b>');
			$('#errorSms').html('<b> Total Amount supplied is insufficient </b>');
			$('#error').modal('show');
			
			$('#totalAmount').attr('disabled', false);
			
			modalTimeout('error');
		}else{
			
			$('#success').modal('show');
			
			$('#successTitle').html('<b> Amount well equated </b>');
			$('#successSms').html('<b> Total Amount supplied is okay, Proceed to allocation </b>');
			
			
			modalTimeout('success');
			
			$('#totalAmount').attr('disabled', false);
			
			$('#allocateGovtMoney').attr('disabled',false);
			
		}

		
		

		
	});
	
	}
}

function monitorTotalAmount(){
	
	$('#allocateGovtMoney').attr('disabled',true);
	
	
}

function allocateGovtCash(){
	
	$('#allocating').modal({
	    backdrop: 'static',
	    keyboard: false
	    
	});
	
	
	$('#allocating').modal('show');
	
	
	
	setTimeout(function(){
		
		$('#allocating').modal('hide');
		$('#allocateGovtMoney').attr('disabled',true);
		$('#totalAmount').val('');
		$('#govtCheckResponse').html('Display the amount check here');
		
		
	},7000);
}

$(document).ready(function(){
	
})

function govtCategoryTemplateModal(){
	
	$('#govtCategoryTemplateModal').modal('show');
}

function addGovtCategoryTemplate(form){
	console.log('submit/ activate default template');
}







