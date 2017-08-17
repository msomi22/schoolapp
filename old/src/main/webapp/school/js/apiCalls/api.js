function StaffApiCall() {

		var dataTxt = ' {  "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265","staffNo":"3060","firstname":"Peter","middlename":"Mwenda","lastname":"Njeru","gender":"M","mobile":"718953974","email":"peter.mwenda@adcea.com","username":"msomi22","password":"12345667890" } ';

		var dataObj = JSON.parse(dataTxt);
		var uName = "demo";
		var passwrd = "12345678";

		$
				.ajax(
						{
							url : "http://localhost:8080/school/webapi/staff/E3CDC578-37BA-4CDB-B150-DAB0409270CD/",
							type : 'POST',
							dataType : 'json',
							data : JSON.stringify({  
								   "acessLevelId":"BDF7F33D-1936-43F3-B14B-8FC3EA3A1265",
								   "staffNo":"3062",
								   "firstname":"Peter",
								   "middlename":"Mwenda",
								   "lastname":"Njeru",
								   "gender":"M",
								   "mobile":"718953974",
								   "email":"peter.mwenda@adcea.com",
								   "username":"msomi22",
								   "password":"12345667890"
								}),
							contentType: 'application/json',
							accept: 'application/json',
							beforeSend : function(xhr) {
								xhr.setRequestHeader('Authorization', 'Basic '+ btoa(uName + ":" + passwrd));
							}
						}).done(function(data) {
							alert(data.description);
					
				}).fail(function(jqXHR, textStatus) {
					alert("Error: " + textStatus);
				})
	}