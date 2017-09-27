var table;

var uName = "demo";
var passwrd = "12345678";

var currentClassId = "";

var currentAccId = "";

var base_url = location.protocol + "//" + window.location.host
		+ "/school/webapi/general/";

$(document)
		.ready(
				function() {

					fetchExams();

				});

function fetchExams() {

	$
			.ajax(
					{
						url : base_url
								+ "exam/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
						type : 'GET',
						dataType : 'json',
						contentType : 'application/json',
						accept : 'application/json',
						beforeSend : function(xhr) {
							xhr.setRequestHeader('Authorization', 'Basic '
									+ btoa(uName + ":" + passwrd));
						}
					})
			.done(
					function(data) {

						// alert(data);
						console.log(data);

						if (data["message"] != "error") {

							var cols = [];

							var getCol = data[0];

							

							var keys = Object.keys(getCol);

							keys.forEach(function(k) {

								if (k == "description") {

									cols.push({
										title : "Description",
										data : k,
									// optionally do some type detection here
									// for
									// render function

									});
								} else {

									cols.push({
										title : k,
										data : k,
									});

								}

							});

							if (table)
								table.clear();

							table = $('#exams')
									.DataTable(
											{

												destroy : true,
												columns : cols,
												"columnDefs" : [
														{
															"targets" : [ 0 ],
															"visible" : false,
															"searchable" : false
														},
														{
															"targets" : [ 1 ],
															"visible" : false
														},
														{
															"targets" : [ 5 ],
															"data" : null,
															"defaultContent" : '<button class="btn btn-warning editExam" id="edit" onclick="examModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" id="Form 1N" onclick="#">Disable <span class="fa fa-chain-broken"></span></button>'
														} ],
												

												"order" : [ [ 0, "desc" ] ]

											});

							table.rows.add(data).draw();

							$('#exams tbody').on(
									'click',
									'button',
									function() {
										var data = table.row(
												$(this).parents('tr')).data();

										$("#cname").val(data['code']);

										$("#uuid").val(data['uuid']);

										$("#dname").val(data['description']);

										$("#score").val(data['outOf']);

										console.log(data['uuid']);

										$("#accountId").val(data['accountId']);

									});

						}

					}).fail(function(jqXHR, textStatus) {

				console.log(textStatus);
			});

}

function addExam(form) {

	// $('#stream_btn_add').prop("type", "button");

	// console.log(JSON.stringify($('#addStreamForm').serializeJSON()));

	/*
	 * $("#addStreamForm") .submit( function(e) { // e.preventDefault();
	 * 
	 * 
	 * });
	 */

	var myform = $(form)[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {

		// console.log(JSON.stringify($('#addStreamForm').serializeJSON()));
		
		
		

		console.log(currentClassId);

		$.ajax(
				{
					url : base_url
							+ "exam/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
					type : 'POST',
					dataType : 'json',
					data : JSON.stringify($('#examForm').serializeJSON()),
					contentType : 'application/json',
					accept : 'application/json',
					beforeSend : function(xhr) {
						xhr.setRequestHeader('Authorization', 'Basic '
								+ btoa(uName + ":" + passwrd));
					}
				}).done(function(data) {

		


			if (data.description.includes("successfully")) {
				
				
			
				
				

				$('#success').modal('show');

				$('#successTitle').text(data.description);
				$('#successSms').text(data.description);

				$('#classesList').val($('#classId_add').val());

				$('#examForm').get(0).reset();

				setTimeout(function() {

					$('#exam').modal('hide');
				}, 2500);

				setTimeout(function() {

					$('#success').modal('hide');
				}, 3000);

				fetchExams();

			} else if (data.description.includes("exist")) {

				$('#error').modal('show');

				$('#errorTitle').text("Exam Exists");

				$('#errorSms').text(data.description);

				setTimeout(function() {

					$('#error').modal('hide');
				}, 2500);

			} else {

				$('#warning').modal('show');

				$('#warningTitle').text("Details Input Error");

				$('#warningSms').text(data.description);

				setTimeout(function() {

					$('#warning').modal('hide');
				}, 2500);

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

function updateExam(form) {

	// alert ("Swapp worked");
	
	
	var myform = $(form)[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {



						$
								.ajax(
										{
											url : base_url
													+ "exam/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
											type : 'PUT',
											dataType : 'json',
											data : JSON.stringify($(
													'#examForm')
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

											// alert(data.description);

											if (data.description
													.includes("successfully")) {
												
												
												
												

												$('#success').modal('show');

												$('#successTitle').text(
														data.description);

												$('#successSms').text(
														data.description);

												setTimeout(function() {

													$('#examForm').get(0)
															.reset();
													$('#exam')
															.modal('hide');
												}, 2500);

												setTimeout(
														function() {

															$('#success')
																	.modal(
																			'hide');
														}, 3000);

												fetchExams();

											} else if (data.description
													.includes("wrong")) {

												$('#error').modal('show');

												$('#errorTitle').text(
														"Fatal Error");

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

												setTimeout(
														function() {

															$('#warning')
																	.modal(
																			'hide');
														}, 2500);

											}

										}).fail(function(jqXHR, textStatus) {

									// alert("Error: " +
									// textStatus);

									$('#error').modal('show');

									$('#errorTitle').text("Fatal Error");

									$('#errorSms').text(textStatus);

									setTimeout(function() {

										$('#error').modal('hide');
									}, 2500);
								})
								
								

	}
					


}


