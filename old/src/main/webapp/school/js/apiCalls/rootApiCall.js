var global_url = location.protocol + "//" + window.location.host
		+ "/school/webapi/";

var varying_url="";

var global_request_type = 'GET';

var global_data_passed = {};

function globalApiCall(handleData) {

	$.ajax(
			{
				url : global_url+varying_url,
				type : global_request_type,
				dataType : 'json',
				data : JSON.stringify(global_data_passed),
				contentType : 'application/json',
				accept : 'application/json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Authorization',
							'Basic ZGVtbzoxMjM0NTY3OA==');
				}
			}).done(function(data) {

		console.log(data);
		handleData(data);

	}).fail(function(jqXHR, textStatus) {

		console.log(textStatus);

	});
}