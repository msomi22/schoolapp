$(document).keypress(function(e) {
	if (e.which == 13) {

		// Do something here if the popup is open
		// alert("dd")
		var index = $('.ui-dform-text').index(document.activeElement) + 1;
		$('.ui-dform-text').eq(index).focus();

	}
});

$(document).ready(function(){
	
	$('#submitExamScore').DataTable({
		
		searching : false,
		"bPaginate" : false,
		"bLengthChange" : false,
		"scrollY" : "500px",
		"scrollCollapse" : true
	});
})

function trimVar(x) {
	return x.replace(/^\s+|\s+$/gm, '');
}


function scroll(id){
	
	
		var selection = $( "#submitExamScore #"+id );
    console.log( selection );
    $(".dataTables_scrollBody").scrollTo(selection);
    $("tr[role='row']").removeClass("selectedRow");
    selection.addClass("selectedRow");
	
}

function validateScore(id) {

	// define a regex of the score
	///^[0-9]|[0-9][0-9]$/
	///[0-9]{1}|[0-9]{1}[0-9]{1}/i
	var scoreRegx = /^\d{1,2}$/;

	// /get the cell value
	var Row = document.getElementById(id);
	var Cells = Row.getElementsByTagName("td");

	var score = trimVar(Cells[6].innerText);

	if (score.length > 0) {

		if (!score.match(scoreRegx) || score.length > 2) {

			$('#edit' + id).removeClass('glyphicon glyphicon-ok secondary-assent');
			$('#scorewarning').modal('show');

			Cells[6].innerText = "";

			setTimeout(function() {
				$('#scorewarning').modal('hide');
			}, 2500);
		} else {

			var studentId = Cells[7].innerText;
			var subjectId = $("#subjectId").val();
			var examId = $("#examId").val();
			var streamId = $("#streamId").val();

			// setting the hidden values

			$("#score").val(score);
			$("#studentId").val(studentId);

			// log values
			console.log("Student Id: " + studentId);

			console.log("Subject Id: " + subjectId);
			console.log("Exam Id: " + examId);
			console.log("Stream ID:" + streamId);

			console.log("Score " + score);

			// submit
			// $('#submitExam').attr('action', 'examAjax');

			// $("#submitExam").submit();

			var url = $('#submitExam').attr("action");

			var form = $('#submitExam');

			jQuery.ajax({
				url : 'examAjax',
				data : form.serialize(),
				cache : false,
				contentType : false,
				processData : false,
				type : 'GET',
				success : function(data) {
					
					console.log(data);

					$('#edit' + id).removeClass('glyphicon glyphicon-ok secondary-assent');
					$('#edit' + id).removeClass('glyphicon glyphicon-remove error');

					// $('#edit'+id).remove('.glyphicon');

					// var obj = jQuery.parseJSON(data);
					if (data.responseMessage.includes("OK")) {

						$('#edit' + id).addClass('glyphicon glyphicon-ok secondary-assent');
						

						// $('#edit'+id).append('<span class="glyphicon
						// glyphicon-ok"></span>');
					}

					else if (data.responseMessage.includes("not valid")) {

						$('#edit' + id).addClass('glyphicon glyphicon-remove error');
						Cells[6].innerText = "";
						
						
						$('#errorTitle').text('Score Error');
						$('#errorSms').text(data.responseMessage);
						$('#error').modal('show');
						
						setTimeout(function() {
							$('#error').modal('hide');
						}, 2000);

						// $('#edit'+id).append('<span class="glyphicon
						// glyphicon-ok"></span>');
					}else{
						$('#edit' + id).addClass('glyphicon glyphicon-remove error');
						Cells[6].innerText = "";
						
						
						$('#errorTitle').text('Score Error');
						$('#errorSms').text(data.responseMessage);
						$('#error').modal('show');
						
						setTimeout(function() {
							$('#error').modal('hide');
						}, 2000);
						
						
					}

					console.log(data);

				}

			});

		}

	}
}