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