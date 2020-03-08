 $('#streams').DataTable({
		 searching:false,
		 "bPaginate": false,
		 "bLengthChange": false
	 });
 
 
 
	 
	 function streamModal(id){
		 
		 if(id =="edit_stream"){
			 
			// alert(id);
			/* $('#streamTiltle').text("Edit Stream Details");
			 
			 //$('#stream_btn').text("Save Changes");
			 
			// $('#stream_btn').text("Save Changes");
			 $("#stream_btn_add").hide();
			 $("#stream_btn_update").show(1000);*/
			 
			 $('#updateStreamModal').modal('show');
			 

			 
		 }else if(id == "add"){
			 
			 /*$('#streamForm').get(0).reset();
			 
			 $('#streamTiltle').text("Add a new Stream");
			// $('#stream_btn').text("Submit");
			 
			 $("#stream_btn_update").hide();
			 $("#stream_btn_add").show(1000);*/
			 
			 $('#addStreamForm').get(0)
				.reset();
			 
			 
			 console.log(JSON.stringify($('#addStreamForm').serializeJSON()));
			 
			 $('#addStreamModal').modal('show');
			 
			 
			 
			 
		 }
		 
		 
		/// $('#stream').modal('show');
		 
		 }
	 
	 
	 function delStream(stream){
		 
		 $('#delTitle').text("Delete Stream");
		 
		 $('#delSms').text("Are you sure you want to Delete this stream?");
		 $('#del_modal').modal('show');
	 }
	 
	 
	 
	 
	 $(".editStream")
		.click(
				function() {
					console.log('clicked');
					
					

					

						}
						);





