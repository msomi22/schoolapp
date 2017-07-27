$(document).ready(function() {
	
	alert("loaded");
	$("#registerStudent").submit(function(e) {
		e.preventDefault();

		var form = $('#registerStudent');
		
		alert(form);
		
		

		// submit with ajax
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
	
	$('#btn_avatar').click(function(){
		
		alert($('#avatarInput').val() +'other field'+ $('.avatar-data').val() );
		
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
	alert ("Me here");
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
