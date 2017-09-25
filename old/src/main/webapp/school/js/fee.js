var preserveUrl = global_url;

$(document).ready(
		function() {

			global_url = global_url + "finance/termfee/"
					+ $('#accountId').val() + '/' + $('#term').val() + '/'
					+ $('#year').val();

			globalApiCall(function(data) {

				console.log('Async call of the global api term fee');
				console.log(data);

				$('#boarder').val(data['boaderAmount']);
				$('#day').val(data['dayAmount']);

				global_url = preserveUrl;
			});

		});

function initPayment() {

	$('#feePayment').modal("show");
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

		$('#preRegno').html('<b> Reg No_ : ' + $('#p_regNo').val() + '</b>');
		$('#prePaymentMode').html('<b>Payment Mode : ' + $('#p_mode').val() + '</b>');
		$('#preTransId').html('<b> Transaction ID : ' + $('#transactionId').val() + '</b>');
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

	var myform = $("#feePaymentForm")[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {

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

					if (data.description.includes("success")) {

						$('#success').modal('show');

						$('#successTitle').text(data.description);
						$('#successSms').text(data.description);

						$('#feePaymentForm').get(0).reset();

						setTimeout(function() {

							$('#feePayment').modal('hide');
						}, 2000);

						setTimeout(function() {

							$('#success').modal('hide');
						}, 3000);

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
			+ $('#studentId').val(), "_blank");
}

function showHistory(state) {

	if (state === 'history') {

		$('#history').toggle(1000);

		$('#showHistory').toggle(2000);

	} else if (state === 'other') {

		$('#showOtherHistory').toggle(1000);

		$('#OtherHistory').toggle(2000);

	}
}

$('#history').click(function() {

	$('#history').toggle(1000);

	$('#showHistory').toggle(2000);

});

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
	}, 3000)

}

function isReg(regNo) {
	var regxReg = /[0-9]{3,4}/;

	if (regNo.Lenght < 2 | !regNo.match(regxReg)) {

		return false;
	} else {

		return true;
	}

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
						global_url = preserveUrl;

						var fetchfeeURL = global_url
								+ 'student/fee/E3CDC578-37BA-4CDB-B150-DAB0409270CD/'
								+ $('#p_regNo').val();
						global_url = fetchfeeURL;

						globalApiCall(function(data) {

							console
									.log('Async call of the global api before fee payment');
							console.log(data);

							global_url = preserveUrl;

							if ((data.message)) {

								$('#PreRegNoInfo').addClass('alert-danger');
								$('#PreRegNoInfo').removeClass(
										'alert-warning alert-success');

								$('#regNoVerSms').html(
										'<b>' + data.description + '</b>');

							} else {

								$('#p_mode').prop('disabled', false);

								$('#transactionId').prop('disabled', false);

								$('#amount').prop('disabled', false);
								var fee = "";
								
								
								$('#studentId').val(data['studentFeeAPI']['studentId']);
								
								$('#p_staffId').val($('#staffId').val());
								
								$('#p_accountId').val($('#accountId').val());
								
								console.log('Staff ID: '+$('#p_staffId').val());
								
								

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
					}, 2000);

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

				global_url = preserveUrl;
				makeFetchCall()
			}, 1000);

		}
	}
}

function makeFetchCall() {

	var fetchfeeURL = global_url
			+ 'student/fee/E3CDC578-37BA-4CDB-B150-DAB0409270CD/'
			+ $('#regno').val();
	global_url = fetchfeeURL;

	console.log(global_url);

	globalApiCall(function(data) {

		console.log('Async call of the global api');
		console.log(data);

		global_url = preserveUrl;

		console.log("Second" + global_url);

		if ((data.message)) {

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);

			// $('#regNoState').html("<b>" + + "</b>");

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

		}

		else {

			// $("#regNoState").slideUp(300).delay(200).fadeIn(400);
			/*
			 * $('#regNoState').removeClass('alert alert-danger');
			 * $('#regNoState').addClass('alert alert-success');
			 * $('#regNoState') .html( "<b>Successfully retrieved student's
			 * school fees info. </b>");
			 */

			$('#regNoInfo').addClass('alert-success');
			$('#regNoInfo').removeClass('alert-info');

			$('#regNoInfo').show(1000);
			$('#regNoError').hide(2000);

			$('#regNoInfoSms')
					.html(
							"<b>Successfully retrieved student's school fees info. </b>");

			var basicInfo = data['studentFeeAPI'];

			$('#studentsInfo').show(3000);

			$('#btn_otherHistory').prop('disabled', false);
			$('#btn_history').prop('disabled', false);

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

									$('#studentId')
											.val(
													basicInfo['feeHistory'][0]['studentId']);
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
														'<div class="col-md-4 col-md-offset-1"> <h6>Amount Paid: '
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
																+ '</h6> <hr class="hr_list"></div>');

										// });
									}
								}

								if (key === "otherfeeHistory") {

									for (var i = 0; i < basicInfo['otherfeeHistory'].length; i++)

										$('#otherfeeHistory')
												.append(
														'<div class="row"> <div class="col-md-11 col-md-offset-1"> <h6>Description: '
																+ basicInfo['otherfeeHistory'][i]['otherFeeId']
																+ '</h6><h6>Amount Paid: '
																+ basicInfo['otherfeeHistory'][i]['amount']
																+ '</h6> <h6>Term Paid: '
																+ basicInfo['otherfeeHistory'][i]['termPiad']
																+ '</h6> <h6>Date Paid: '
																+ basicInfo['otherfeeHistory'][i]['dateAllocated']
																+ '</h6> <hr class="hr_list"></div> </div>');
								}

								if (key === "revertedFeeList") {

									for (var i = 0; i < basicInfo['revertedFeeList'].length; i++)

										$('#revertedFeeList')
												.append(
														'<div class="row"> <div class="col-md-11 col-md-offset-1"> <h6>Description: '
																+ basicInfo['revertedFeeList'][i]['otherFeeId']
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
