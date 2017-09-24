var preserveUrl = global_url;

var feeCatId = "99AC5A73-A3A2-4EEB-AC12-9624266122C2";

var accoutId = "";
var uuid="";

$(document).ready(function() {

	fetCategories();

});

function fetCategories() {

	$('#govtCatList').html('');

	accountId = $('#loggedId').val();
	
	$('#feeBreakdownId').val(feeCatId);
	
	$('#accountId').val(accountId);

	global_url = global_url + "finance/goke/" + accountId + '/' + feeCatId;

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

		global_url = preserveUrl;
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