 $("#exams").DataTable({
		 
	 });
	 
	 
	 function examModal(id){
		 
		 if(id =="edit"){
			 
			// alert(id);
			 $('#examTiltle').text("Edit Exam Details");
			 
			 $('#exam_btn').text("Save Changes");
			 

			 
		 }else if(id == "add"){
			 
			 $('#examTiltle').text("Add a new Exam");
			 $('#exam_btn').text("Submit");
			 
			 $('#examForm').get(0).reset();
			 
			 
		 }
		 
		 
		 $('#exam').modal('show');
		 
		 }
	 
	 
	 function disableExam(staff){
		 
		 $('#disableTitle').text("Disable Exam");
		 
		 $('#disableSms').text("Are you sure you want to Disable exam,"+ staff+"?");
		 $('#Dis_modal').modal('show');
	 }
	 
	 
	 
	 
	 $(".editExam")
		.click(
				function() {
					
					$("#cname")
							.val(
									$(this).closest('tr')
											.children()[1].textContent);
					$("#dname")
							.val(
									$(this).closest('tr')
											.children()[2].textContent);

					$("#score")
							.val(
									$(this).closest('tr')
											.children()[3].textContent);
					

						}
						);





