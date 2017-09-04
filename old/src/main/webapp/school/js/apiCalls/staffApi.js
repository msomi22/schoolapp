function StaffApiCall() {

	$("#staffForm")
			.submit(
					function(e) {
						e.preventDefault();

						var dataTxt = ' {  "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265","staffNo":"3060","firstname":"Peter","middlename":"Mwenda","lastname":"Njeru","gender":"M","mobile":"718953974","email":"peter.mwenda@adcea.com","username":"msomi22","password":"12345667890" } ';

						var dataObj = JSON.parse(dataTxt);
						var uName = "demo";
						var passwrd = "12345678";

					//	alert(JSON.stringify($('#staffForm').serializeJSON()));

						$
								.ajax(
										{
											url : "http://localhost:8080/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
											type : 'POST',
											dataType : 'json',
											data : JSON.stringify($(
													'#staffForm')
													.serializeJSON()),
											contentType : 'application/json',
											accept : 'application/json',
											beforeSend : function(xhr) {
												xhr
														.setRequestHeader(
																'Authorization',
																'Basic '
																		+ btoa(uName
																				+ ":"
																				+ passwrd));
											}
										})
								.done(
										function(data) {

										//	alert(data.description);

											if (data.description
													.includes("successfully")) {

												$('#success').modal('show');

												$('#successTitle').text(
														data.description);

												setTimeout(function() {

													$('#staffForm').get(0)
															.reset();
													$('#staff').modal('hide');
												}, 2500);

												setTimeout(
														function() {

															$('#success')
																	.modal(
																			'hide');
														}, 3000);

											} else if (data.description
													.includes("exist")) {

												$('#error').modal('show');

												$('#errorTitle').text(
														"Staff Exists");

												$('#errorSms').text(
														data.description);

												setTimeout(function() {

													$('#error').modal('hide');
												}, 2500);

											} else {

												$('#warning').modal('show');

												$('#warningTitle').text(
														"Details Input Error");

												$('#warningSms').text(
														data.description);

												setTimeout(function() {

													$('#warning').modal('hide');
												}, 2500);

											}

										}).fail(function(jqXHR, textStatus) {
											
											
											
									//alert("Error: " + textStatus);
											
											$('#error').modal('show');

											$('errorTitle').text(
													"Fatal Error");

											$('errorSms').text(textStatus);

											setTimeout(function() {

												$('#error').modal('hide');
											}, 2500);
								})

					});

}