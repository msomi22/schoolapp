 $('#streams').DataTable({
		 searching:false,
		 "bPaginate": false,
		 "bLengthChange": false
	 });
	 
	 function streamModal(id){
		 
		 if(id =="edit"){
			 
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
					
					$("#desc")
							.val(
									$(this).closest('tr')
											.children()[2].textContent);
					$("#classId")
							.val(
									$(this).closest('tr')
											.children()[1].textContent);

					

						}
						);





