$(document).ready(function() {

	
	$("#houses").DataTable({
		
		destroy: true

	});

	$('#houses tbody').on('click', 'button', function() {
		var data = table.row($(this).parents('tr')).data();

		$("#hsename").val(data['houseName']);

		$("#uuid").val(data['uuid']);
		
		$("#dname").val(data['description']);

		$("#hse_accountId").val(data['accountId']);

	});

})

function houseModal(id) {

	if (id == "edit") {

		// alert(id);
		$('#houseTiltle').text("Edit House Details");

		$('#house_btn').text("Save Changes");

		$('#house_btn').attr('onclick', 'updateHouse(this.form)');

	} else if (id == "add") {

		$('#houseTiltle').text("Add a new House");
		$('#house_btn').text("Submit");

		$('#house_btn').attr('onclick', 'addHouse(this.form)');
		$('#houseForm').get(0).reset();

		$('#hse_accountId').val($('#accountId').val());

	}

	$('#houseModal').modal('show');

}

function disableHouse(staff) {

	$('#disableTitle').text("Delete House");

	$('#disableSms').text("Are you sure you want to Delete this House?");
	$('#Dis_modal').modal('show');
	
	$('#btn_dis').attr('onclick', 'deleteHouse()');
	
	
}
