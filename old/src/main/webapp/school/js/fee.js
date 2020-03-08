var preserveUrl = global_url;

var regno;

var studentId;

var search_state = false;

$(document).ready(
		function() {
			
			
			if(checkAccessFee()){

			$('.accountId').val($('#accountId').val());

			varying_url = "finance/termfee/" + $('#accountId').val() + '/'
					+ $('#term').val() + '/' + $('#year').val();

			globalApiCall(function(data) {

				console.log('Async call of the global api term fee');
				console.log(data);

				$('#boarder').val(data['boaderAmount']);
				$('#day').val(data['dayAmount']);

			});

			otherFeeTermFeeList();
			
			}

		});




function initPayment() {

	$('#btn_revertPaymentInfo').hide();
	$('#btn_closepayment').show();

	$('#paymentSpace').show();
	$('#PreSubmitInfo').hide();

	$('#btn_feePayment').text('Submit');
	$('#btn_feePayment').attr('onclick', "preSubmitVerify('init')");

	$('#feePayment').modal("show");

	$('#feePaymentForm').get(0).reset();

	checkRegnoState();

	setTimeout(function() {
		checkRegnoState();

	}, 2000)

}

function OtherFeeModal(id) {

	if (id == "revert_payment_btn") {

		/*
		 * $('#otherTermFeeTiltle').text("Revert Other Term Fee Details");
		 * 
		 * $('#othertermFee_btn').text("Revert");
		 * 
		 * $("#othertermFee_btn").attr("onclick", "revertOtherTermFee()");
		 */

		$('#revertOtherTermFeeModal').modal('show');

	}

	else if (id == "addOtherFee_btn") {

		/*
		 * $('#otherTermFeeTiltle').text("Add Other Term Fee Details");
		 * 
		 * $('#othertermFee_btn').text("Submit");
		 * 
		 * $("#othertermFee_btn").attr("onclick", "addOtherFee()");
		 */

		$('#otherTermFeeModal').modal('show');

	}

}

function addOtherFee() {

	if (rootCheckFormValidation($('#otherTermFeeForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "student/other/fee/" + $('#accountId').val();

		global_data_passed = $('#otherTermFeeForm').serializeJSON();

		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'POST';

		globalApiCall(function(data) {

			console
					.log('Code for new other term fee adding for a specific student');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#otherTermFeeModal').modal('hide');
				fetchFeeDetails('info');
			}

		});

	}

}

function revertOtherTermFee() {
	if (rootCheckFormValidation($('#revertOtherTermFeeForm'))) {

		// alert(JSON.stringify($('#staffForm').serializeJSON()));

		varying_url = "student/other/fee/revert/" + $('#accountId').val();

		global_data_passed = $('#revertOtherTermFeeForm').serializeJSON();

		console.log(varying_url);

		console.log(JSON.stringify(global_data_passed));

		global_request_type = 'PUT';

		globalApiCall(function(data) {

			console
					.log('Code for reverting other term fee for a specific student');

			console.log(data);

			if (rootParseApiResponseData(data)) {

				$('#revertOtherTermFeeModal').modal('hide');
				fetchFeeDetails('info');
			}

		});

	}

}

function isReg(regNo) {
	var regxReg = /[0-9]{3,5}/;

	if (regNo.Lenght < 2 | !regNo.match(regxReg)) {

		return false;
	} else {

		return true;
	}

}

function checkRegnoState() {
	regno = $('#regno').val();

	if (isReg(regno) && search_state) {

		$('#p_regNo').val(regno);

		$('#regNoVerSms').html(
				'<h4> ' + $('#p_regNo').html() + '</h4>' + '<h4>'
						+ $('#name').html() + '</h4>' + '<h4>'
						+ $('#stream').html() + '</h4>' + '<h4> '
						+ $('#termfee').html() + '</h4>');
		$('#p_mode').prop('disabled', false);

		$('#transactionId').prop('disabled', false);

		$('#amount').prop('disabled', false);
		$('#p_staffId').val($('#staffId').val());

		$('#studentId').val(studentId);

		$('#p_accountId').val($('#accountId').val());

	} else {

		$('#regNoVerSms')
				.html(
						"Enter a student's registratio number to view school fees details.");
		$('#p_mode').prop('disabled', true);

		$('#transactionId').prop('disabled', true);

		$('#amount').prop('disabled', true);
		$('#p_staffId').val("");

		$('#p_accountId').val("");
	}
}

function checkFormValidation(form) {

	var myform = $(form)[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
			return false;
		} else {
			// warn IE users somehow :)
		}
	} else {

		return true;
	}
}

function preSubmitVerify(state) {

	if (checkFormValidation($("#feePaymentForm"))) {
		regno = $('#p_regNo').val();

		$('#preRegno').html('<b> Reg No_ : ' + $('#p_regNo').val() + '</b>');
		$('#prePaymentMode').html(
				'<b>Payment Mode : ' + $('#p_mode').val() + '</b>');
		$('#preTransId').html(
				'<b> Transaction ID : ' + $('#transactionId').val() + '</b>');
		$('#preAmount').html('<b>Amount : ' + $('#amount').val() + '</b>');

		$('#paymentSpace').toggle(2000);
		$('#PreSubmitInfo').toggle(1000);

		$('#btn_revertPaymentInfo').toggle(1000);
		$('#btn_closepayment').toggle(2000);

		if (state === 'init') {

			$('#btn_feePayment').text('OK');

			$('#btn_feePayment').attr('onclick', "feePayment()");

		} else if (state === 'back') {

			$('#btn_feePayment').text('Submit');
			$('#btn_feePayment').attr('onclick', "preSubmitVerify('init')");

		}

	}
}
function regVerify() {

	$('#p_mode').prop('disabled', true);

	$('#transactionId').prop('disabled', true);

	$('#amount').prop('disabled', true);

	$('#transactionId').val("");

	$('#amount').val("");

	setTimeout(function() {

		fetchFeeDetails('fee')
	}, 3000)

}

function feePayment() {

	console.log("Form data");

	console.log($('#feePaymentForm').serializeJSON());
	var myform = $("#feePaymentForm")[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {
		
		$('#btn_feePayment').attr('disabled', true);

		console.log($('#accountId').val() + " Regno" + $('#p_regNo').val());

		$
				.ajax(
						{
							url : location.protocol + "//"
									+ window.location.host
									+ "/school/webapi/student/fee/"
									+ $('#accountId').val() + "/"
									+ $('#p_regNo').val(),
							type : 'POST',
							dataType : 'json',
							data : JSON.stringify($('#feePaymentForm')
									.serializeJSON()),
							contentType : 'application/json',
							accept : 'application/json',
							beforeSend : function(xhr) {
								xhr.setRequestHeader('Authorization',
										'Basic ZGVtbzoxMjM0NTY3OA==');
							}
						}).done(function(data) {

					// $('#addStreamForm').get(0).reset();

					// console.log(JSON.stringify($('#addStreamForm').serializeJSON()));
							
							$('#btn_feePayment').attr('disabled', false);

					if (data.description.includes("success")) {

						$('#success').modal('show');

						$('#successTitle').text(data.description);
						$('#successSms').text(data.description);

						$('#feePaymentForm').get(0).reset();

						setTimeout(function() {

							$('#feePayment').modal('hide');
						}, 500);

						setTimeout(function() {

							$('#success').modal('hide');
						}, 3000);

						$('#regno').val(regno);
						$('#paymentSpace').toggle(2000);
						$('#PreSubmitInfo').toggle(1000);
						fetchFeeDetails('info');

					} else if (data.message.includes("error")) {

						$('#error').modal('show');

						$('#errorTitle').text(data.description);

						$('#errorSms').text(data.description);

						setTimeout(function() {

							$('#error').modal('hide');
						}, 3500);

					}

				}).fail(function(jqXHR, textStatus) {

					// alert("Error: " + textStatus);

					$('#error').modal('show');

					$('#errorTitle').text("Fatal Error");

					$('#errorSms').text(textStatus);

					setTimeout(function() {

						$('#error').modal('hide');
					}, 2500);
				})

	}
}

function generateReceipt() {

	// window.location="feeReceipt?accountId="+$('#accountId').val()+"&studentId="+$('#studentId').val();
	window.open("feeReceipt?accountId=" + $('#accountId').val() + "&studentId="
			+ studentId, "_blank");
}

function showHistory(state) {

	if (state === 'history') {

		$('#history').show(1000);

		$('#showHistory').hide(2000);

	} else if (state === 'other') {

		$('#showOtherHistory').toggle(1000);

		$('#OtherHistory').toggle(2000);

	}
}

/*
 * $('#history').click(function() {
 * 
 * $('#history').toggle(1000);
 * 
 * $('#showHistory').toggle(2000);
 * 
 * });
 */
$('#OtherHistory').click(function() {

	$('#showOtherHistory').toggle(1000);

	$('#OtherHistory').toggle(2000);

});

function delayInput() {

	$('#genReceipt').removeClass('btn-success');
	$('#genReceipt').addClass('btn-info');
	$('#genReceipt').prop('disabled', true);

	$('#history').hide();

	$('#showHistory').show();

	$('#showOtherHistory').show();

	$('#OtherHistory').hide();

	$('#feeHistory').html('');
	$('#otherfeeHistory').html('');
	$('#revertedFeeList').html('');

	// $('#studentsInfo').hide(2000);
	$('#name').text('Name : ##');
	$('#regNo').text('Reg No: ##');
	$('#stream').text('Stream : ##');
	$('#isBoarding').text('Type : ##');
	$('#balance').html('<b>Balance : ##</b>');

	$('#btn_otherHistory').prop('disabled', true);
	$('#btn_history').prop('disabled', true);

	setTimeout(function() {

		fetchFeeDetails('info')
	}, 2000)

}

function fetchFeeDetails(state) {

	if (state === "fee") {

		var regNo = $('#p_regNo').val();

		if (!isReg(regNo)) {

			// $("#regNoState").slideUp(300).fadeIn(400);

			$('#PreRegNoInfo').addClass('alert-danger');
			$('#PreRegNoInfo').removeClass('alert-warning');

			$('#regNoVerSms')
					.html(
							'<b>Please input a valid registration number e.g 900, 956 e.t.c </b>');
		} else {

			$('#PreRegNoInfo').addClass('alert-success');
			$('#PreRegNoInfo').removeClass('alert-warning alert-danger');

			$('#regNoVerSms').html(
					'<b>Fetching student details, please wait...</b>');

			setTimeout(
					function() {
						// global_url = preserveUrl;

						// var
						varying_url = 'student/fee/' + $('#accountId').val()
								+ '/' + $('#p_regNo').val();
						// global_url = fetchfeeURL; Heheh!:)

						globalApiCall(function(data) {

							console
									.log('Async call of the global api before fee payment');
							console.log(data);

							// global_url = preserveUrl; heheeh! :)

							if ((data.message)) {

								$('#PreRegNoInfo').addClass('alert-danger');
								$('#PreRegNoInfo').removeClass(
										'alert-warning alert-success');

								$('#regNoVerSms').html(
										'<b>' + data.description + '</b>');

								search_state = false;

							} else {

								search_state = true;

								$('#p_mode').prop('disabled', false);

								$('#transactionId').prop('disabled', false);

								$('#amount').prop('disabled', false);
								var fee = "";

								$('#other_studentId').val(
										data['studentFeeAPI']['studentId']);

								$('#studentId').val(
										data['studentFeeAPI']['studentId']);
								studentId = data['studentFeeAPI']['studentId'];

								$('#p_staffId').val($('#staffId').val());

								$('#p_accountId').val($('#accountId').val());

								console.log('Student ID: '
										+ $('#studentId').val());

								if (data['studentFeeAPI']['isBoarding'] == '1') {

									fee = "Boarding " + $('#boarder').val();

								} else {
									fee = "Day " + $('#day').val();
								}

								$('#PreRegNoInfo').addClass('alert-success');
								$('#PreRegNoInfo').removeClass(
										'alert-danger alert-warning');

								$('#regNoVerSms')
										.html(
												'<h4> Reg No : '
														+ data['studentFeeAPI']['regNo']
														+ '</h4>'
														+ '<h4> First Name : '
														+ data['studentFeeAPI']['firstname']
														+ '</h4>'
														+ '<h4> Last Name : '
														+ data['studentFeeAPI']['lastname']
														+ '</h4>'
														+ '<h4> Stream : '
														+ data['studentFeeAPI']['stream']
														+ '</h4>'
														+ '<h4> Term Fee : '
														+ fee + '</h4>');

							}

						});
					}, 1000);

		}

	} else if (state === "info") {

		var regNo = $('#regno').val();

		if (!isReg(regNo)) {

			// $("#regNoState").slideUp(300).fadeIn(400);

			$('#regNoError').addClass('alert-warning');
			$('#regNoError').removeClass('alert-danger');

			$('#regNoInfo').addClass('alert-info');
			$('#regNoInfo').removeClass('alert-success');

			$('#regNoInfo').hide(1000);
			$('#regNoError').show(2000);
			$('#regNoErrorSms')
					.html(
							'<b>Please input a valid registration number e.g 900, 956 e.t.c </b>');
		} else {

			$('#regNoInfo').show(1000);
			$('#regNoError').hide(2000);

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);
			$('#regNoInfo').removeClass('alert-info');
			$('#regNoInfo').addClass('alert-success');
			$('#regNoInfoSms')
					.html(
							"<b>Retriving student's school fees info.... Loading .....</b>")

			setTimeout(function() {

				// global_url = preserveUrl; Hahahah!
				makeFetchCall()
			}, 1000);

		}
	}
}

function makeFetchCall() {

	varying_url = 'student/fee/' + $('#accountId').val() + '/'
			+ $('#regno').val();
	// global_url = fetchfeeURL; one day i will laugh at this line of code like
	// i am doing right now. Heheh! :)

	global_request_type = 'GET';

	global_data_passed = {};

	globalApiCall(function(data) {

		console.log('Async call of the make fetchcall api');
		console.log(data);

		// global_url = preserveUrl;

		// console.log("Second" + global_url);

		if ((data.message)) {

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);

			// $('#regNoState').html("<b>" + + "</b>");
			search_state = false;

			$('#regNoError').addClass('alert-danger');
			$('#regNoError').removeClass('alert-warning');

			$('#regNoInfo').hide(1000);
			$('#regNoError').show(2000);

			$('#regNoErrorSms').html('<b>' + data.description + ' </b>');

		} else if (data.length < 1) {

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);
			/*
			 * $('#regNoState').removeClass('alert alert-success');
			 * $('#regNoState').addClass('alert alert-danger');
			 * $('#regNoState').html("<b>RegNo </b>");
			 */

			$('#regNoError').addClass('alert-danger');
			$('#regNoError').removeClass('alert-warning');

			$('#regNoInfo').hide(1000);
			$('#regNoError').show(2000);

			$('#regNoErrorSms').html(
					'<b>No data available for this registration number </b>');

			search_state = false;

		}

		else {

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);
			/*
			 * $('#regNoState').removeClass('alert alert-danger');
			 * $('#regNoState').addClass('alert alert-success');
			 * $('#regNoState') .html( "<b>Successfully retrieved student's
			 * school fees info. </b>");
			 */

			search_state = true;

			$('#regNoInfo').addClass('alert-success');
			$('#regNoInfo').removeClass('alert-info');

			$('#regNoInfo').show(1000);
			$('#regNoError').hide(2000);

			$('#regNoInfoSms')
					.html(
							"<b>Successfully retrieved student's school fees info. </b>");

			var basicInfo = data['studentFeeAPI'];
			studentId = data['studentFeeAPI']['studentId'];

			$('#studentsInfo').show(3000);

			$('#btn_otherHistory').prop('disabled', false);
			$('#btn_history').prop('disabled', false);
			$('#revert_payment_btn').prop('disabled', false);
			$('#addOtherFee_btn').prop('disabled', false);
			$('#other_studentId, #revertOther_studentId').val(data['studentFeeAPI']['studentId']);
			

			var name;

			$
					.each(
							basicInfo,
							function(key, value) {
								if (key === 'regNo')
									$('#regNo').text('Reg No : ' + value);

								if (key === 'firstname')
									name = 'Name: ' + value;
								// $('#name').text('Name : ' + value);

								/*
								 * if (key === 'middlename')
								 * $('#middlename').text( 'Middle Name : ' +
								 * value);
								 */

								if (key === 'lastname') {
									name = name + " " + value;
									$('#name').text(name);
								}
								// $('#lastname').text('Last Name : ' + value);

								if (key === 'stream')
									$('#stream').text('Stream : ' + value);

								if (key === 'isBoarding') {

									if (value == '1') {
										$('#isBoarding')
												.text('Type : Boarding');

										$('#termfee').html(
												'<b> Term Fee:'
														+ $('#boarder').val()
														+ '</b>');
									} else {
										$('#isBoarding').text('Type : Day');
										$('#termfee').html(
												'<b> Term Fee:'
														+ $('#day').val()
														+ '</b>');
									}

								}

								if (key === 'balance')
									$('#balance').html(
											'<b>Balance : ' + value + '</b>');

								if (key === 'feeHistory') {

									/*
									 * $('#studentId') .val(
									 * basicInfo['feeHistory'][0]['studentId']);
									 */
									$('#genReceipt').removeClass('btn-info');
									$('#genReceipt').addClass('btn-success');
									$('#genReceipt').prop('disabled', false);

									$('#feeHistory').html('');
									$('#otherfeeHistory').html('');
									$('#revertedFeeList').html('');
									// $('#feeHistory').html("Loading");

									// $.each(basicInfo['feeHistory'],
									// function(key,
									// value){

									for (var i = 0; i < basicInfo['feeHistory'].length; i++) {

										$('#feeHistory')
												.append(
														'<div class="col-md-4 col-md-offset-1" id="receipt'
																+ basicInfo['feeHistory'][i]['datePaid']
																+ '"> <h6>Amount Paid: '
																+ basicInfo['feeHistory'][i]['amountPaid']
																+ '</h6><h6>Payment Mode: '
																+ basicInfo['feeHistory'][i]['payMode']
																+ '</h6> <h6>Transaction ID: '
																+ basicInfo['feeHistory'][i]['transactionId']
																+ '</h6> <h6>Term Paid: '
																+ basicInfo['feeHistory'][i]['termPiad']
																+ '</h6> <h6>Year Paid: '
																+ basicInfo['feeHistory'][i]['yearPaid']
																+ '</h6> <h6>Date Paid: '
																+ basicInfo['feeHistory'][i]['datePaid']
																+ '<span class="btn btn-pull-right hand" id="'
																+ basicInfo['feeHistory'][i]['datePaid']
																+ '" onclick="printReceipt(this.id)" > <i class="fa fa-print fa-2x print"></i></span>'
																+ '</h6> <hr class="hr_list"></div>');

										// });
									}
								}

								if (key === "otherfeeHistory") {
									var revertOtherFeeSelect = $('.revertOtherFeeList');
									revertOtherFeeSelect.empty();

									for (var i = 0; i < basicInfo['otherfeeHistory'].length; i++) {
										
										
										revertOtherFeeSelect.append('<option id=' + basicInfo['otherfeeHistory'][i]['otherFeeId'] + ' value='
												+ basicInfo['otherfeeHistory'][i]['otherFeeId'] + '>' +basicInfo['otherfeeHistory'][i]['description'] + ' : '
												+ basicInfo['otherfeeHistory'][i]['amount'] + '</option>');

										$('#otherfeeHistory')
												.append(
														'<div class="row"> <div class="col-md-11 col-md-offset-1"> <h6>Description: '
																+ basicInfo['otherfeeHistory'][i]['description']
																+ '</h6><h6>Amount Paid: '
																+ basicInfo['otherfeeHistory'][i]['amount']
																+ '</h6> <h6>Term Paid: '
																+ basicInfo['otherfeeHistory'][i]['termPiad']
																+ '</h6> <h6>Date Paid: '
																+ basicInfo['otherfeeHistory'][i]['dateAllocated']
																+ '</h6> <hr class="hr_list"></div> </div>');
									}
								}

								if (key === "revertedFeeList") {

									for (var i = 0; i < basicInfo['revertedFeeList'].length; i++)

										$('#revertedFeeList')
												.append(
														'<div class="row"> <div class="col-md-11 col-md-offset-1"> <h6>Description: '
																+ basicInfo['revertedFeeList'][i]['description']
																+ '</h6><h6>Amount: '
																+ basicInfo['revertedFeeList'][i]['amount']
																+ '</h6> <h6>Date: '
																+ basicInfo['revertedFeeList'][i]['dateReverted']
																+ '</h6> <hr class="hr_list"></div> </div>');
								}

							});
		}

	});

}

function otherFeeTermFeeList() {

	varying_url = "finance/fee/other/" + $('#accountId').val() + "/"
			+ $('#term').val() + "/" + $('#year').val();

	global_data_passed = {};

	global_request_type = 'GET';

	globalApiCall(function(data) {

		console.log('Code for fetching other fee list per term');

		console.log(data);

		console.log(data.length);

		if (data["message"] != "error" && data.length > 0) {

			var otherFeeSelect = $('.otherFeeList');
			otherFeeSelect.empty();
			// classSelect.options[classSelect.options.length]
			// = new Option('Form 1', 'Value1');

			for (var i = 0; i < data.length; i++) {
				otherFeeSelect.append('<option id=' + data[i].uuid + ' value='
						+ data[i].uuid + '>' + data[i].description + ' : '
						+ data[i].amount + '</option>');
				// classSelect.options[classSelect.options.length]
				// = new Option(data[i].description,
				// data[i].uuid);
			}

		}

	});

}

function printReceipt(id) {
	console.log('Print init' + id + 'Heheheh');
	var mywindow = window.open('', 'PRINT', 'height=800,width=1000');

	mywindow.document
			.write('<html><head><title>School Fees Receipt + </title>');
	mywindow.document.write('</head><body >');
	mywindow.document.write('<h1> Fees Receipt</h1>');
	mywindow.document.write(document.getElementById('receipt' + id).innerHTML);
	mywindow.document.write('</body></html>');

	mywindow.document.close(); // necessary for IE >= 10
	mywindow.focus(); // necessary for IE >= 10*/

	mywindow.print();
	mywindow.close();

	return true;

}
