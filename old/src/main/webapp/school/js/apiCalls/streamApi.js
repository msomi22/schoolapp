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

					$
							.ajax(
									{
										url : base_url
												+ "class/" + $('#accountId').val()+"/",
										type : 'GET',
										contentType : 'application/json',
										accept : 'application/json',
										beforeSend : function(xhr) {
											xhr.setRequestHeader(
													'Authorization', 'Basic '
															+ btoa(uName + ":"
																	+ passwrd));
										}
									})
							.done(
									function(data) {

										var classSelect = $('.populateOptions');
										classSelect.empty();
										// classSelect.options[classSelect.options.length]
										// = new Option('Form 1', 'Value1');

										for (var i = 0; i < data.length; i++) {
											classSelect.append('<option id='
													+ data[i].sysid + ' value='
													+ data[i].uuid + '>'
													+ data[i].description
													+ '</option>');
											// classSelect.options[classSelect.options.length]
											// = new Option(data[i].description,
											// data[i].uuid);
										}

										console.log(data);

										var classId = document
												.getElementById('classesList');
										var classIdVal = classId.options[classId.selectedIndex].value;

										// var classId= $('#classesList').val();
										console.log(classIdVal);

										fetchStreams(classIdVal);

									}).fail(function(jqXHR, textStatus) {

								// alert("Error: " + textStatus);
								console.log(textStatus);

							});

				});

function fetchStreams(classID) {

	currentAccId = classID;

	$
			.ajax(
					{
						url : base_url
								+ "streams/"+ $('#accountId').val()+"/"
								+ classID + "/",
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

							console.log(getCol);

							currentAccId = getCol["accountId"];
							$("#accountId").val(currentAccId);
							$("#accountId_add").val(currentAccId);

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

							table = $('#streams')
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
															"targets" : [ 2 ],
															"visible" : false
														},
														{
															"targets" : [ 4 ],
															"data" : null,
															"defaultContent" : '<button class="btn btn-warning editStream" id="edit_stream" onclick="streamModal(this.id)">Edit <span class="fa fa-edit"></span></button><button class="btn btn-danger" id="Form 1N"onclick="delStream(this.id)">Delete <span class="fa fa-trash"></span></button>'
														} ],
												searching : false,
												"bPaginate" : false,
												"bLengthChange" : false,

												"order" : [ [ 0, "desc" ] ]

											});

							table.rows.add(data).draw();

							$('#streams tbody').on(
									'click',
									'button',
									function() {
										var data = table.row(
												$(this).parents('tr')).data();

										console.log(data);

										// console.log($("#desc").val(data[3]));
										
										var desc= data['description'];
										$("#desc").val(desc.substr(7,1));
										console.log(data['description']);
										console.log(data['accountId']);
										$("#classId_edit").val(
												data['classRoomId']);

										$("#uuid").val(data['uuid']);

										$("#del_uuid").val(data['uuid']);

										console.log(data['uuid']);

										currentClassId = data['classRoomId'];
										$("#accountId").val(data['accountId']);

									});

						}

					}).fail(function(jqXHR, textStatus) {

				console.log(textStatus);
			});

}

function addNewStream() {

	// $('#stream_btn_add').prop("type", "button");

	// console.log(JSON.stringify($('#addStreamForm').serializeJSON()));

	/*
	 * $("#addStreamForm") .submit( function(e) { // e.preventDefault();
	 * 
	 * 
	 * });
	 */

	var myform = $("#addStreamForm")[0];
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
							+ "stream/"+ $('#accountId').val()+"/",
					type : 'POST',
					dataType : 'json',
					data : JSON.stringify($('#addStreamForm').serializeJSON()),
					contentType : 'application/json',
					accept : 'application/json',
					beforeSend : function(xhr) {
						xhr.setRequestHeader('Authorization', 'Basic '
								+ btoa(uName + ":" + passwrd));
					}
				}).done(function(data) {

		

		//	$('#addStreamForm').get(0).reset();

		//	console.log(JSON.stringify($('#addStreamForm').serializeJSON()));

			if (data.description.includes("successfully")) {
				
				
				currentClassId = $('#classId_add').val();
				
				$("#classesList").val(currentClassId);
				
				

				$('#success').modal('show');

				$('#successTitle').text(data.description);
				$('#successSms').text(data.description);

				$('#classesList').val($('#classId_add').val());

				$('#addStreamForm').get(0).reset();

				setTimeout(function() {

					$('#addStreamModal').modal('hide');
				}, 2500);

				setTimeout(function() {

					$('#success').modal('hide');
				}, 3000);

				fetchStreams(currentClassId);

			} else if (data.description.includes("exist")) {

				$('#error').modal('show');

				$('#errorTitle').text("Stream Exists");

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

function updateStreamApiCall() {

	// alert ("Swapp worked");
	
	

	
			var myform = $("#editStreamForm")[0];
	if (!myform.checkValidity()) {
		if (myform.reportValidity) {
			myform.reportValidity();
		} else {
			// warn IE users somehow :)
		}
	} else {
								$.ajax(
										{
											url : base_url
													+ "stream/"+ $('#accountId').val()+"/",
											type : 'PUT',
											dataType : 'json',
											data : JSON.stringify($(
													'#editStreamForm')
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
												
												
												currentClassId = $('#classId_edit').val();
												
												$("#classesList").val(currentClassId);
												
												
												

												$('#success').modal('show');

												$('#successTitle').text(
														data.description);

												$('#successSms').text(
														data.description);

												setTimeout(function() {

													$('#editStreamForm').get(0)
															.reset();
													$('#updateStreamModal')
															.modal('hide');
												}, 2500);

												setTimeout(
														function() {

															$('#success')
																	.modal(
																			'hide');
														}, 3000);

												fetchStreams(currentClassId);

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
								});
	}					

}

function delStreamApiCall() {

	var uuid = $('#del_uuid').val();

	console.log(uuid);

	$.ajax(
			{
				url : base_url
						+ "stream/delete/"+ $('#accountId').val()+"/"
						+ uuid + "/",
				type : 'DELETE',
				dataType : 'json',
				contentType : 'application/json',
				accept : 'application/json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization', 'Basic '
							+ btoa(uName + ":" + passwrd));
				}
			}).done(function(data) {

		// alert(data.description);

		if (data.description.includes("successfully")) {

			$('#success').modal('show');

			$('#successTitle').text("Deletion State");

			$('#successSms').text(data.description);

			setTimeout(function() {

				$('#delStream').get(0).reset();
				$('#del_modal').modal('hide');
			}, 2500);

			setTimeout(function() {

				$('#success').modal('hide');
			}, 3000);

			fetchStreams(currentClassId);

		} else if (data.description.includes("wrong")) {

			$('#error').modal('show');

			$('#errorTitle').text("Fatal Error");

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

/*
 * if(!this.checkValidity()) { event.preventDefault(); //
 * $('#stream_btn_add').prop("type", "submit"); }else{
 * 
 * event.preventDefault();
 * 
 * //$('#stream_btn_add').prop("type", "button");
 *  // alert(JSON.stringify($('#staffForm').serializeJSON())); currentClassId=
 * $('#classId_add').val();
 * 
 * 
 * console.log(currentClassId);
 *  $ .ajax( { url :
 * "http://localhost:8080/school/webapi/general/stream/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
 * type : 'POST', dataType : 'json', data : JSON.stringify($( '#addStreamForm')
 * .serializeJSON()), contentType : 'application/json', accept :
 * 'application/json', beforeSend : function(xhr) { xhr .setRequestHeader(
 * 'Authorization', 'Basic ' + btoa(uName + ":" + passwrd)); } }) .done(
 * function(data) {
 *  // alert(data.description);
 * 
 * $('#addStreamForm').get(0) .reset();
 * 
 * 
 * console.log(JSON.stringify($('#addStreamForm').serializeJSON()));
 * 
 * 
 * 
 * if (data.description .includes("successfully")) {
 * 
 * $('#success').modal('show');
 * 
 * $('#successTitle').text( data.description);
 * 
 * 
 * $('#classesList').val($('#classId_add').val());
 * 
 * $('#addStreamForm').get(0) .reset();
 * 
 * setTimeout(function() {
 * 
 * 
 * $('#addStreamModal').modal('hide'); }, 2500);
 * 
 * setTimeout( function() {
 * 
 * $('#success') .modal( 'hide'); }, 3000);
 * 
 * 
 * 
 * fetchStreams(currentClassId);
 * 
 *  } else if (data.description .includes("exist")) {
 * 
 * $('#error').modal('show');
 * 
 * $('#errorTitle').text( "Stream Exists");
 * 
 * $('#errorSms').text( data.description);
 * 
 * setTimeout(function() {
 * 
 * $('#error').modal('hide'); }, 2500);
 *  } else {
 * 
 * $('#warning').modal('show');
 * 
 * $('#warningTitle').text( "Details Input Error");
 * 
 * $('#warningSms').text( data.description);
 * 
 * setTimeout( function() {
 * 
 * $('#warning') .modal( 'hide'); }, 2500);
 *  }
 * 
 * }).fail(function(jqXHR, textStatus) {
 *  // alert("Error: " + textStatus);
 * 
 * $('#error').modal('show');
 * 
 * $('#errorTitle').text("Fatal Error");
 * 
 * $('#errorSms').text(textStatus);
 * 
 * setTimeout(function() {
 * 
 * $('#error').modal('hide'); }, 2500); })
 * 
 *  }
 */