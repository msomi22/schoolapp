$(document).keypress(function(e) {
    if(e.which == 13) {

            // Do something here if the popup is open
            //alert("dd")
            var index = $('.ui-dform-text').index(document.activeElement) + 1;
            $('.ui-dform-text').eq(index).focus();

    }
});


function trimVar(x) {
    return x.replace(/^\s+|\s+$/gm,'');
}

function validateScore(id){
	
	//define a regex of the score
	var scoreRegx=/[0-9]{1}|[0-9]{1}[0-9]{1}/i;
	
	///get the cell value
	var Row = document.getElementById(id);
	var Cells = Row.getElementsByTagName("td");
	
	var score= trimVar(Cells[5].innerText);
	
	
	if(score.length>0){
	
	if(!score.match(scoreRegx) || score.length>2){
		
		
		$('#edit'+id).removeClass('glyphicon glyphicon-ok');
		$('#scorewarning').modal('show');
		
		$('#edit'+id).innerText="";
		 


		setTimeout(function(){
	       $('#scorewarning').modal('hide');
	   }, 2500);
	}else{
		
		
		
		var studentId=Cells[6].innerText ;
		var subjectId= $("#subjectId").val();
		var examId= $("#examId").val();
		var streamId= $("#streamId").val();
		
		//setting the hidden values
		
		$("#score").val(score);
		$("#studentId").val(studentId);
		
		
		
		
		
		//log values
		console.log("Student Id: "+studentId);
		
		console.log("Subject Id: "+subjectId);
		console.log("Exam Id: "+examId);
		console.log("Stream ID:"+streamId);
		
		console.log("Score "+score);
		
		
		
		//submit
		$('#submitExam').attr('action', 'examAjax');
		
	//	$("#submitExam").submit();
		
		
		var url= $('#submitExam').attr("action");

		var form= $('#submitExam');

		jQuery.ajax({
		    url: 'examAjax',
		    data: form.serialize(),
		    cache: false,
		    contentType: false,
		    processData: false,
		    type: 'GET',
		    success: function(data){
		    	
		    	$('#edit'+id).removeClass('glyphicon glyphicon-ok')
		    	
		    	// $('#edit'+id).remove('.glyphicon');
		    	
		    	//var obj = jQuery.parseJSON(data);
		    	 if(data.responseMessage ==="OK"){
		    		 
		    		 $('#edit'+id).addClass('glyphicon glyphicon-ok');
		    		 
		    		// $('#edit'+id).append('<span class="glyphicon glyphicon-ok"></span>');
		    	 }
		    	
		    	console.log(data);

		    }
		
		});
		
	}
	
	}
}