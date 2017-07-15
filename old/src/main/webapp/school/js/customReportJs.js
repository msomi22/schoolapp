$("#exam").change(function () {
      if($("#exam option:selected").length > 3 ) {
         
    	  $('#examWarning').modal('show');
          
    	 /* setTimeout(function(){
    		  $('#examWarning').modal('hide');
    	  }, 4000);*/
    	  
          $("#exam").focus();
          $("#exam").val("");
      }
  });

function redirect(reportType){
	if (reportType == "reportcard"){
		
		$('#generateReport').attr('action', 'studentReportCard');
		
		//alert('reporcard selected');
	}else{
		
		$('#generateReport').attr('action', 'classRankingList');
		//alert('rank list selected');
	}
	
}


function scopeSwap(scopeType){
	
	if(scopeType == "class"){
		$('#streamScope').hide('2000');
		$('#classScope').show('2000');
		
		
	}else{
		$('#streamScope').show('2000');
		$('#classScope').hide('2000');
		
		
	}
}