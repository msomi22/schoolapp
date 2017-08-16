function examWarningModal() {

	$('#examWarning').modal('show');

	setTimeout(function() {
		$('#examWarning').modal('hide');
	}, 5000);
	
	/*$('.SlectBox').html('');
	$('.SlectBox')[0].sumo.reload();*/
	
	
	$("#exam").focus();
	$("#exam").val("");
	
	
}

$("#exam")
		.change(
				function() {
					if ($("#exam option:selected").length > 3) {

						$('#titleWarning').text('Number of exams allowed');

						$('#smsWarning')
								.text(
										'At least one exam and not more than three exams are allowed.');
						
						
						
						/*var num = $('option').length;
					    for(var i=0; i<num-1; i++){
					      $('.SlectBox')[0].sumo.unSelectItem(i);
					    }
*/
						var obj = [];
					    $('option:selected').each(function () {
					        obj.push($(this).index());
					    });

					    for (var i = 0; i < obj.length; i++) {
					        $('.SlectBox')[0].sumo.unSelectItem(obj[i]);
					    }
						examWarningModal();
						
						}

				
				});

function validateExamSelected(){
	
	
	if ($("#exam option:selected").length >0) {

		var selectedExams = $('#exam option:selected').text();
		
		//alert(selectedExams);

		if (selectedExams.includes('Paper 1')
				| selectedExams.includes('Paper 2')
				| selectedExams.includes('Paper 3')) {

			if (!(selectedExams.includes('Paper 1')
					&& selectedExams.includes('Paper 2') 
					&& selectedExams.includes('Paper 3'))) 
			{

				$('#titleWarning').text(
						'Exam Papers Number Warning');

				$('#smsWarning')
						.text(
								'Exam Papers selected must be three i.e Paper 1, Paper 2 and Paper 3');
				
				
				var obj = [];
			    $('option:selected').each(function () {
			        obj.push($(this).index());
			    });

			    for (var i = 0; i < obj.length; i++) {
			        $('.SlectBox')[0].sumo.unSelectItem(obj[i]);
			    }
			    

				examWarningModal();
				
				

			}else if(selectedExams.includes('Paper 1')
					&& selectedExams.includes('Paper 2') 
					&& selectedExams.includes('Paper 3')){
				
				$('#examType').val('P123');
			}
			else{
				
				$('#examType').val('others');
			}

		}
		else{
			
			$('#examType').val('others');
		}

	}

	
	
}

function redirect(reportType) {
	if (reportType == "reportcard") {

		$('#generateReport').attr('action', 'studentReportCard');

		// alert('reporcard selected');
	} else {

		$('#generateReport').attr('action', 'classRankingList');
		// alert('rank list selected');
	}

}

function scopeSwap(scopeType) {

	if (scopeType == "class") {
		$('#streamScope').hide('2000');
		$('#classScope').show('2000');

	} else {
		$('#streamScope').show('2000');
		$('#classScope').hide('2000');

	}
}