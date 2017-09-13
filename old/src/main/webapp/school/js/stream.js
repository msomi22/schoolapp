 $('#streams').DataTable({
		 searching:false,
		 "bPaginate": false,
		 "bLengthChange": false
	 });
	 
	 function streamModal(id){
		 
		 if(id =="edit_stream"){
			 
			// alert(id);
			 $('#streamTiltle').text("Edit Stream Details");
			 
			 $('#stream_btn').text("Save Changes");
			 

			 
		 }else if(id == "add"){
			 
			 $('#streamTiltle').text("Add a new Stream");
			 $('#stream_btn').text("Submit");
			 
			 $('#streamForm').get(0).reset();
			 
			 
		 }
		 
		 
		 $('#stream').modal('show');
		 
		 }
	 
	 
	 function delStream(stream){
		 
		 $('#disableTitle').text("Delete Stream");
		 
		 $('#disableSms').text("Are you sure you want to Delete stream,"+ stream+"?");
		 $('#Dis_modal').modal('show');
	 }
	 
	 
	 
	 
	 $(".editStream")
		.click(
				function() {
					console.log('clicked');
					
					

					

						}
						);





