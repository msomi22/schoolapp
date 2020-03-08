$(document).ready(function() {
	
	
	$('#avata_show').show();
	$('#not_possible').hide();
	
	//alert("loaded");
	$("#registerNewStudent").submit(function(e) {
		e.preventDefault();

		var form = $('#registerNewStudent');
		
		
		jQuery.ajax({
			url : 'studentAjax',
			data : $(this).serialize(),
			cache : false,
			contentType : false,
			processData : false,
			type : 'GET',
			success : function(data) {

				if (data.responseMessage === "OK") {

					// alert("SUCCESS");
					$('#success').modal('show');

					setTimeout(function() {
						$('#success').modal('hide');
					}, 2500);

					// reset form to allow next entry
					
					$(form)[0].reset();

				} else {

					$('#error').modal('show');

					setTimeout(function() {
						$('#error').modal('hide');
					}, 2500);
				}

				console.log(data);

			}

		});
	});
	
	
});

function submitStudentData(form) {

	// validation of the fields

	// prevent default

	// submit if validaion is ok

	jQuery.ajax({
		url : 'studentAjax',
		data : new FormData( this ),
		cache : false,
		contentType : false,
		processData : false,
		type : 'GET',
		success : function(data) {

			if (data.responseMessage === "OK") {

				// alert("SUCCESS");
				$('#success').modal('show');

				setTimeout(function() {
					$('#success').modal('hide');
				}, 2500);

				// reset form to allow next entry

			} else {

				$('#error').modal('show');

				setTimeout(function() {
					$('#error').modal('hide');
				}, 2500);
			}

			console.log(data);

		}

	});
}


function talkToMe(){
//	alert ("Me here");
	
}

function primarySwap(state) {

	if (state == "yes") {

		$('#primarySchoolDetails').show('2000');

	} else if (state == "no") {
		
		$('#primarySchoolDetails').hide('2000');
		
		

	}

	else if (state == "noParent") {
		
		$('#parentDetails').hide('2000');

	}

	else if(state == "yesParent") {

		$('#parentDetails').show('2000');

	}else{
		
	}
}




function popImport(){
	
	$('#import_show').modal('show');
	
}
