function submitStudentData(form){
	
	//validation of the fields
	
	
	//prevent default
	
	
	//submit if validaion is ok
	
	
	
	
	jQuery.ajax({
	    url: 'studentAjax',
	    data: $(form).serialize(),
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'GET',
	    success: function(data){
	    	
	    	
	    	 if(data.responseMessage ==="OK"){
	    		 
	    		//alert("SUCCESS");
	    		 $('#success').modal('show');
	    		 
	    		 setTimeout(function(){
	    		       $('#success').modal('hide');
	    		   }, 2500);
	    		
	    		//reset form to allow next entry
	    		
	    	 }else{
	    		 

				 $('#error').modal('show');

				setTimeout(function() {
					$('#error').modal('hide');
				}, 2500);
	    	 }
	    	
	    	console.log(data);

	    }
	
	});
}