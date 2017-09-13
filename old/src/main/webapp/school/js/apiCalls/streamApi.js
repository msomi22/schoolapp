var table;

var uName = "demo";
var passwrd = "12345678";

$(document)
		.ready(
				function() {

					$
							.ajax(
									{
										url : "http://localhost:8080/school/webapi/general/class/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
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
										
										var classId = $("#classesList");
										var classIdVal = classId.options[classId.selectedIndex].value;
										
										//var classId= $('#classesList').val();
										console.log(classIdVal);
										
										fetchStreams(classIdVal);


									}).fail(function(jqXHR, textStatus) {

								// alert("Error: " + textStatus);
								console.log(textStatus);

							});
					
										

				});





function fetchStreams(classID) {

	$
			.ajax(
					{
						url : location.protocol
								+ "//"
								+ window.location.host
								+ "/school/webapi/general/stream/E3CDC578-37BA-4CDB-B150-DAB0409270CD/classID/",
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
						
						if(data["message"] != "error"){

						var cols = [];

						var getCol = data[0];

						var keys = Object.keys(getCol);

						keys.forEach(function(k) {

							if (k == "description") {

								cols.push({
									title : "Description",
									data : k,
								// optionally do some type detection here for
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
									var data = table.row($(this).parents('tr'))
											.data();
									
									console.log(data);
									
									
									//console.log($("#desc").val(data[3]));
									$("#desc").val(data['description']);
									console.log(data['description']);
									$("#classId").val(data['classRoomId']);
									
									
								});
						
						}

					}).fail(function(jqXHR, textStatus) {

				console.log(textStatus);
			});

}

function StaffApiCall() {

	$("#staffForm")
			.submit(
					function(e) {
						e.preventDefault();

						var dataTxt = ' {  "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265","staffNo":"3060","firstname":"Peter","middlename":"Mwenda","lastname":"Njeru","gender":"M","mobile":"718953974","email":"peter.mwenda@adcea.com","username":"msomi22","password":"12345667890" } ';

						var dataObj = JSON.parse(dataTxt);
						var uName = "demo";
						var passwrd = "12345678";

						// alert(JSON.stringify($('#staffForm').serializeJSON()));

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

											// alert(data.description);

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

												setTimeout(
														function() {

															$('#warning')
																	.modal(
																			'hide');
														}, 2500);

											}

										}).fail(function(jqXHR, textStatus) {

									// alert("Error: " + textStatus);

									$('#error').modal('show');

									$('errorTitle').text("Fatal Error");

									$('errorSms').text(textStatus);

									setTimeout(function() {

										$('#error').modal('hide');
									}, 2500);
								})

					});

}

function updateStaffApiCall(state) {

	// alert ("Swapp worked");

	if (state == 'disable') {

	} else if (state == 'update') {

		$("#staffForm")
				.submit(
						function(e) {
							e.preventDefault();

							var dataTxt = ' {  "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265","staffNo":"3060","firstname":"Peter","middlename":"Mwenda","lastname":"Njeru","gender":"M","mobile":"718953974","email":"peter.mwenda@adcea.com","username":"msomi22","password":"12345667890" } ';

							var dataObj = JSON.parse(dataTxt);
							var uName = "demo";
							var passwrd = "12345678";

							// alert(JSON.stringify($('#staffForm').serializeJSON()));

							$
									.ajax(
											{
												url : "http://localhost:8080/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
												type : 'PUT',
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

												// alert(data.description);

												if (data.description
														.includes("successfully")) {

													$('#success').modal('show');

													$('#successTitle').text(
															data.description);

													setTimeout(function() {

														$('#staffForm').get(0)
																.reset();
														$('#staff').modal(
																'hide');
													}, 2500);

													setTimeout(function() {

														$('#success').modal(
																'hide');
													}, 3000);

												} else if (data.description
														.includes("wrong")) {

													$('#error').modal('show');

													$('#errorTitle').text(
															"Fatal Error");

													$('#errorSms').text(
															data.description);

													setTimeout(function() {

														$('#error').modal(
																'hide');
													}, 2500);

												} else {

													$('#warning').modal('show');

													$('#warningTitle')
															.text(
																	"Details Input Error");

													$('#warningSms').text(
															data.description);

													setTimeout(function() {

														$('#warning').modal(
																'hide');
													}, 2500);

												}

											}).fail(
											function(jqXHR, textStatus) {

												// alert("Error: " +
												// textStatus);

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
}