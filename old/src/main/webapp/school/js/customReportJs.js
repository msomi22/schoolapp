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


$(document).ready(function(){
	$('#accountId_tbid').val($('#accountId').val());
	
	
	if(checkAccessAcademics())
		fetchClasses();
})


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

		if (selectedExams.includes('PAPER 1')
				| selectedExams.includes('PAPER 2')
				| selectedExams.includes('PAPER 3')) {

			if (!(selectedExams.includes('PAPER 1')
					&& selectedExams.includes('PAPER 2') 
					&& selectedExams.includes('PAPER 3'))) 
			{

				$('#titleWarning').text(
						'Exam Papers Number Warning');

				$('#smsWarning')
						.text(
								'Exam Papers selected must be three i.e PAPER 1, PAPER 2 and PAPER 3');
				
				
				var obj = [];
			    $('option:selected').each(function () {
			        obj.push($(this).index());
			    });

			    for (var i = 0; i < obj.length; i++) {
			        $('.SlectBox')[0].sumo.unSelectItem(obj[i]);
			    }
			    

				examWarningModal();
				
				

			}else if(selectedExams.includes('PAPER 1')
					&& selectedExams.includes('PAPER 2') 
					&& selectedExams.includes('PAPER 3')){
				
				$('#examType').val('P123');
				$('#paper123Id').val('C3915245-00EE-4EF4-9898-ACE59683DD60');
				$('#no_subjects_show').hide(1000);
			}
			else{
				
				$('#examType').val('others');
				$('#paper123Id').val('');
				$('#no_subjects_show').show(1000);
			}

		}
		else{
			
			$('#examType').val('others');
			$('#paper123Id').val('');
			$('#no_subjects_show').show(1000);
		}

	}

	
	
}

function hideSubjects(){
	if ($("#exam option:selected").length >0) {

		var selectedExams = $('#exam option:selected').text();
		
		
		
		if(selectedExams.includes('PAPER 1')
				&& selectedExams.includes('PAPER 2') 
				&& selectedExams.includes('PAPER 3')){
			
		//	$('#examType').val('P123');
			$('#no_subjects_show').hide(1000);
		}else{
			
			
			$('#no_subjects_show').show(1000);
			
			
			
			
			
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


function scopeSwapTBID(scopeType) {

	if (scopeType == "class_tbid") {
		$('#class_option').show('2000');
		$('#stream_option').hide('2000');

	} else {
		$('#class_option').hide('2000');
		$('#stream_option').show('2000');

	}
}


function showTBIDModal(){
	
	
	scopeSwapTBID('class_tbid');
	scopeSwap('class');
	
	$('#generateReport').get(0).reset();
	$('#TBIDForm').get(0).reset();
	$('#TBIDModal').modal('show');
}

function trace(){
	
	console.log(JSON.stringify($('#generateReport').serializeJSON()));
}



