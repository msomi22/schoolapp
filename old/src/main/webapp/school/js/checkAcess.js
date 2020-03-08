function checkAccessFee() {
	var curentAcessLevel = $('#accessLevel').val();
	
	/*
	 * P and B
	 */

	if (curentAcessLevel !== '100'
			&& curentAcessLevel !== '700') {
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

/*
 * 
 */

function checkAccessStudents() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== '100'
			&& curentAcessLevel !== '200'
			&& curentAcessLevel !== '500'
			&& curentAcessLevel !== '600'
			) {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessAcademics() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== '100'
			&& curentAcessLevel !== '200'
			&& curentAcessLevel !== '500'
			&& curentAcessLevel !== '300'
			&& curentAcessLevel !== '400') {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessStaff() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== '100'
			&& curentAcessLevel !== '200'
			&& curentAcessLevel !== '300'
			&& curentAcessLevel !== '400') {
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}

function checkAccessControl() {
	var curentAcessLevel = $('#accessLevel').val();

	if (curentAcessLevel !== '100'
			&& curentAcessLevel !== '200'
			&& curentAcessLevel !== '300'
			&& curentAcessLevel !== '400') {

		console.log(curentAcessLevel);
		window.location = location.protocol + "//" + window.location.host
				+ "/school/schoolLogout";

	} else
		return true
}