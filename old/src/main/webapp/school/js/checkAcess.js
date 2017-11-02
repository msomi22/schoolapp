function checkAccessFee() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== 'C3915245-00EE-4EF4-9898-ACE59683DD60'
			&& curentAcessLevel !== '0DE968C9-7309-C481-58F7-AB6CDB1011EF') {
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessStudents() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== 'C3915245-00EE-4EF4-9898-ACE59683DD60'
			&& curentAcessLevel !== '615F04C1-00BF-499C-AC7A-B46B69243AAA'
			&& curentAcessLevel !== 'BDF7F33D-1936-43F3-B14B-8FC3EA3A1265'
			&& curentAcessLevel !== '64553348-3229-4869-A13D-CADFC1D3AF46') {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessAcademics() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== 'C3915245-00EE-4EF4-9898-ACE59683DD60'
			&& curentAcessLevel !== '615F04C1-00BF-499C-AC7A-B46B69243AAA'
			&& curentAcessLevel !== 'BDF7F33D-1936-43F3-B14B-8FC3EA3A1265'
			&& curentAcessLevel !== '0DE968C9-7309-C481-58F7-AB6CDB1011EH'
			&& curentAcessLevel !== '1CC7F06E-9938-4850-81FB-9CC249C7CFA2') {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessStaff() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== 'C3915245-00EE-4EF4-9898-ACE59683DD60'
			&& curentAcessLevel !== '615F04C1-00BF-499C-AC7A-B46B69243AAA'
			&& curentAcessLevel !== '0DE968C9-7309-C481-58F7-AB6CDB1011EH'
			&& curentAcessLevel !== '1CC7F06E-9938-4850-81FB-9CC249C7CFA2') {
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}




function checkAccessControl() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== 'C3915245-00EE-4EF4-9898-ACE59683DD60'
			&& curentAcessLevel !== '615F04C1-00BF-499C-AC7A-B46B69243AAA'
			&& curentAcessLevel !== '0DE968C9-7309-C481-58F7-AB6CDB1011EH'
			&& curentAcessLevel !== '1CC7F06E-9938-4850-81FB-9CC249C7CFA2') {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}