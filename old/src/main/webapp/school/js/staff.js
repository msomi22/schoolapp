 $("#staffs").DataTable({
	 
	 "scrollY":        "550px",
     "scrollCollapse": true,
     "paging":         false
		 
     
     
	 });

	 
	 
	 function StaffModal(id){
		 
		 if(id =="edit"){
			 
			// alert(id);
			 $('#staffTiltle').text("Edit Staff Details");
			 
			 $('#staff_btn').text("Save Changes");
			 
			 $("#staff_btn").attr("onclick","updateStaffApiCall()");
			 

			 
		 }else if(id == "add"){
			 
			 $('#staffTiltle').text("Add a new Staff");
			 $('#staff_btn').text("Submit");
			 $("#staff_btn").attr("onclick","StaffApiCall()");
			 
			 $('#staffForm').get(0).reset();
			 
			 
		 }
		 
		 
		 $('#staff').modal('show');
		 
		 }
	 
	 
	 function disableStaff(staff){
		 
		 $('#disableTitle').text("Disable staff");
		 
		 $('#disableSms').text("Are you sure you want to Disable staff, "+ staff+"?");
		 $('#Dis_modal').modal('show');
	 }
	 
	 
	 
	 
	 $(".editStaff")
		.click(
				function() {
					
					$("#staffNo")
							.val(
									$(this).closest('tr')
											.children()[1].textContent);
					$("#fname")
							.val(
									$(this).closest('tr')
											.children()[2].textContent);

					$("#mname")
							.val(
									$(this).closest('tr')
											.children()[3].textContent);
					
					$("#lname")
					.val(
							$(this).closest('tr')
									.children()[4].textContent);
					
					$("#gender")
					.val(
							$(this).closest('tr')
									.children()[5].textContent);
					
					$("#phone")
					.val(
							$(this).closest('tr')
									.children()[6].textContent);
					
					$("#email")
					.val(
							$(this).closest('tr')
									.children()[7].textContent);
					
					
					$("#username")
					.val(
							$(this).closest('tr')
									.children()[8].textContent);
					
					
					

						}
						);





