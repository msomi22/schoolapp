var table;

var uName = "demo";
var passwrd = "12345678";

var currentClassId = "";

var currentAccId = "";

var base_url = location.protocol + "//" + window.location.host
		+ "/school/webapi/student/";

$(document).ready(
		function() {
			// init update :)

			console.log($('#passedParam').val());

			$.ajax(
					{
						url : base_url + "one/" + $('#passedParam').val(),
						type : 'GET',
						contentType : 'application/json',
						accept : 'application/json',
						beforeSend : function(xhr) {
							xhr.setRequestHeader('Authorization', 'Basic '
									+ btoa(uName + ":" + passwrd));
						}
					}).done(
					function(data) {

						console.log(data);

						// registry.byId("updateStudentInfo").setValues(data);

						/*
						 * $( '#updateStudentInfo input, #updateStudentInfo
						 * select') .each( function(index) { var input =
						 * $(this); console .log('Type: ' + input .attr('type') +
						 * 'Name: ' + input .attr('name') + 'Value: ' + input
						 * .val()); });
						 */

						$.each(data, function(key, value) {
							$("#updateStudentInfo").find(
									"input[name='" + key + "']").val(value);

							if (key === "apiParentPrimary") {

								$.each(data['apiParentPrimary'], function(key,
										value) {
									$("#updateStudentInfo").find(
											"input[name='" + key + "']").val(
											value);
								});

							}

							if (key === "currentStream") {
								$('#currentStream').val(value.trim());

								console.log(key + " value:" + value.trim());

							}

							if (key === "gender")
								$('#gender').val(value.trim());

							if (key === "isBoarding")
								$('#isBoarding').val(value.trim());

							if (key === "county")
								$('#county').val(value.trim());
						});

					}).fail(function(jqXHR, textStatus) {

				// alert("Error: " + textStatus);
				console.log(textStatus);

			});

		});