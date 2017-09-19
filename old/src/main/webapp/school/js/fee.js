
var preserveUrl=global_url;
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


function delayInput(){
	
	setTimeout(function(){
		
		fetchFeeDetails()},3000)
	
}



function fetchFeeDetails() {
	
	var regNo= $('#regno').val();
	
	
	
	$('#history').hide();

	$('#showHistory').show();
	
	$('#showOtherHistory').show();

	$('#OtherHistory').hide();
	
	
	
	
	$('#feeHistory').html('');
	$('#otherfeeHistory').html('');
	$('#revertedFeeList').html('');

	var regxReg = /[0-9]{3,4}/;
	
	//$('#studentsInfo').hide(2000);
	$('#name').text('Name : ##');
	$('#regNO').text('Reg No: ##');
	$('#stream').text('Stream : ##');
	$('#isBoarding').text('Type : ##');
	$('#balance').html('<b>Balance : ##</b>');
	
	
	
	$('#btn_otherHistory').prop('disabled',true);
	$('#btn_history').prop('disabled',true);

	if (regNo.Lenght < 2 | !regNo.match(regxReg)) {

		//$("#regNoState").slideUp(300).fadeIn(400);
		
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


		//$("#regNoState").slideUp(300).delay(200).fadeIn(400);
		$('#regNoInfo').removeClass('alert-info');
		$('#regNoInfo').addClass('alert-success');
		$('#regNoInfoSms')
				.html(
						"<b>Retriving student's school fees info.... Loading .....</b>")

		setTimeout(function() {
			
			
			global_url=preserveUrl;
			makeFetchCall()}, 1000
		);

	}
}


function makeFetchCall() {
	
	
	var fetchfeeURL = global_url
	+ 'student/fee/E3CDC578-37BA-4CDB-B150-DAB0409270CD/'+$('#regno').val();
	global_url = fetchfeeURL;
	
	console.log(global_url);

	globalApiCall(function(data) {

		console.log('Async call of the global api');
		console.log(data);
		
		global_url=preserveUrl;
		
		console.log("Second"+global_url);

		if ((data.message) ) {

			//$("#regNoState").slideUp(300).delay(200).fadeIn(400);
			
			//$('#regNoState').html("<b>" +  + "</b>");
			
			$('#regNoError').addClass('alert-danger');
			$('#regNoError').removeClass('alert-warning');
			
			$('#regNoInfo').hide(1000);
			$('#regNoError').show(2000);
			
			
			$('#regNoErrorSms')
					.html(
							'<b>'+ data.description+' </b>');

		}else if( data.length <1){
			
			//$("#regNoState").slideUp(300).delay(200).fadeIn(400);
			/*$('#regNoState').removeClass('alert alert-success');
			$('#regNoState').addClass('alert alert-danger');
			$('#regNoState').html("<b>RegNo </b>");	*/
			
			$('#regNoError').addClass('alert-danger');
			$('#regNoError').removeClass('alert-warning');
			
			$('#regNoInfo').hide(1000);
			$('#regNoError').show(2000);
			
			
			$('#regNoErrorSms')
					.html(
							'<b>No data available for this registration number </b>');
			
		}
		
		
		
		else {

			//$("#regNoState").slideUp(300).delay(200).fadeIn(400);
			/*$('#regNoState').removeClass('alert alert-danger');
			$('#regNoState').addClass('alert alert-success');
			$('#regNoState')
					.html(
							"<b>Successfully retrieved student's school fees info. </b>");*/
			
			$('#regNoInfo').addClass('alert-success');
			$('#regNoInfo').removeClass('alert-info');
			
			$('#regNoInfo').show(1000);
			$('#regNoError').hide(2000);
			
			
			$('#regNoInfoSms')
					.html(
							"<b>Successfully retrieved student's school fees info. </b>");

			var basicInfo = data['studentFeeAPI'];
			
			$('#studentsInfo').show(3000);
			
			$('#btn_otherHistory').prop('disabled',false);
			$('#btn_history').prop('disabled',false);
			
			var name;
			

			$
					.each(
							basicInfo,
							function(key, value) {

								if (key === 'regNo')
									$('#regNo').text('Reg No : ' + value);

								if (key === 'firstname')
									name='Name: '+value;
									//$('#name').text('Name : ' + value);

								/*if (key === 'middlename')
									$('#middlename').text(
											'Middle Name : ' + value);*/

								if (key === 'lastname'){
									name = name +" "+value;$('#name').text(name);
								}
									//$('#lastname').text('Last Name : ' + value);

								if (key === 'stream')
									$('#stream').text('Stream : ' + value);

								if (key === 'isBoarding') {

									if (value == '1') {
										$('#isBoarding')
												.text('Type : Boarding');
									} else {
										$('#isBoarding').text('Type : Day');
									}

								}

								if (key === 'balance')
									$('#balance').html(
											'<b>Balance : ' + value + '</b>');

								if (key === 'feeHistory') {
									$('#feeHistory').html('');
									$('#otherfeeHistory').html('');
									$('#revertedFeeList').html('');
									// $('#feeHistory').html("Loading");

									// $.each(basicInfo['feeHistory'],
									// function(key,
									// value){

									for (var i = 0; i < basicInfo['feeHistory'].length; i++){
										
										

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
