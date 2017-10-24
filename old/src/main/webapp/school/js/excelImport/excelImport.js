var base_url = location.protocol + "//" + window.location.host
		+ "/school/";
function swap() {

	$('#import_intro').hide(2000);

	$('#browse_excel').show(2000);
	
	$('.browse').show(2000);
	
	console.log("her");
	
	
	$('#import_btn').prop('disabled',false);
	$('#back_btn').prop('disabled',false);

}

function back(){
	
	$('#import_intro').show(2000);

	$('#browse_excel').hide(2000);
	
	$('.browse').hide(2000);
	
	
	$('#import_btn').prop('disabled',true);
	$('#back_btn').prop('disabled',true);
	
}


function importBegin(form){
	
	
	var myform = $(form)[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {
		
	
		
		var data = new FormData();
		jQuery.each(jQuery('#file')[0].files, function(i, file) {
		    data.append('file-'+i, file);
		});
		
		console.log(data);
	
	jQuery.ajax({
		url : "importStudent",
		data : data,
		cache : false,
		contentType : false,
		processData : false,
		type : 'POST',
		success : function(data) {

			if (data.responseMessage.includes("successfully")) {

				// alert("SUCCESS");
				$('#success').modal('show');
				
				$('#import_show').modal('hide');
				
				$('#successTitle').text(data.responseMessage);
				$('#successSms').text(data.responseMessage);
				

				setTimeout(function() {
					$('#success').modal('hide');
				}, 2500);

				// reset form to allow next entry
				window.location=base_url+"school/studentIndex.jsp";

			} else {
				
				$('#errorTitle').text("Upload Failed");
				
				$('#errorSms').text(data.responseMessage);

				$('#error').modal('show');

				setTimeout(function() {
					$('#error').modal('hide');
				}, 5000);
			}

			console.log(data);

		}

	});
	
	}
	
	
	
}