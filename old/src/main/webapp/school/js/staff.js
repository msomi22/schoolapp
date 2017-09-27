/* $("#staffs").DataTable({
	 
	 "scrollY":        "550px",
     "scrollCollapse": true,
     "paging":         false
		 
     
     
	 });*/

function StaffModal(id) {

	if (id == "edit") {

		// alert(id);
		$('#staffTiltle').text("Edit Staff Details");

		$('#staff_btn').text("Save Changes");

		$("#staff_btn").attr("onclick", "updateStaffApiCall('update')");

	} else if (id == "add") {

		$('#staffTiltle').text("Add a new Staff");
		$('#staff_btn').text("Submit");
		$("#staff_btn").attr("onclick", "StaffApiCall()");

		$('#staffForm').get(0).reset();

	}

	$('#staff').modal('show');

}

function disableStaff(staff) {

	$('#disableTitle').text("Disable staff");

	$('#disableSms').text(
			"Are you sure you want to Disable staff, " + staff + "?");
	$('#Dis_modal').modal('show');
}


function staffSuject(){
	
	
	$('#staffSujectModal').modal('show');
}