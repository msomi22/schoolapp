function showAntennae(state) {

	if (state == "refresh") {

		$('#showAntennae').modal('show');

		$('#newAntenna').modal('hide');
		
		$('#preload').show(1000);
		$('#antennaList').hide(1000)
		
		setTimeout(function() {

			$('#preload').hide(1000);
			
			$('#antennaList').show(1000)
			
		}, 3500);
		
		

	} else if (state == "norefresh") {

		$('#showAntennae').modal('show');
		$('#newAntenna').modal('hide');

	} else {
		$('#newAntenna').modal('show');
		$('#showAntennae').modal('hide');

	}

}

var table;
function accelApiCall() {

	$('#preload').hide(1000);

	$('#loading').modal('show');

	// alert( location.protocol + "//"+window.location.host);

	$.ajax(
			{
				url : location.protocol + "//" + window.location.host
						+ "/school/webapi/admin/data",
				type : 'GET',
				dataType : 'json',
				contentType : 'application/json',
				accept : 'application/json',
			}).done(function(data) {

		// alert(data);

		$('#loading').modal('hide');

		var cols = [];

		var getCol = data[0];

		var keys = Object.keys(getCol);

		keys.forEach(function(k) {

			if (k != "uuid") {
				cols.push({
					title : k,
					data : k
				// optionally do some type detection here for render function
				});
			}
		});

		if (table)
			table.clear();

		table = $('#accel').DataTable({

			destroy : true,
			columns : cols,
			"order" : [ [ 0, "desc" ] ],
			"iDisplayLength" : 100
		});

		table.rows.add(data).draw();

	}).fail(function(jqXHR, textStatus) {

		$('#loading').modal('hide');

		// alert("Error: " + textStatus);

		$('#error').modal('show');

		// $('errorTitle').text("Fatal Error");

		// $('errorSms').text(textStatus);

		setTimeout(function() {

			$('#error').modal('hide');
		}, 2500);
	});

}

/*
 * var data = [{ "first_name": "Airi", "last_name": "Satou", "position":
 * "Accountant", "office": "Tokyo", "start_date": "28th Nov 08", "salary":
 * "$162,700" }, { "first_name": "Angelica", "last_name": "Ramos", "position":
 * "Chief Executive Officer (CEO)", "office": "London", "start_date": "9th Oct
 * 09", "salary": "$1,200,000" }];
 * 
 * $(document).ready( function () { var cols = [];
 * 
 * var exampleRecord = data[0];
 * 
 * //get keys in object. This will only work if your statement remains true that
 * all objects have identical keys var keys = Object.keys(exampleRecord);
 * 
 * //for each key, add a column definition keys.forEach(function(k) {
 * cols.push({ title: k, data: k //optionally do some type detection here for
 * render function }); });
 * 
 * //initialize DataTables var table = $('#example').DataTable({ columns: cols
 * });
 * 
 * //add data and draw table.rows.add(data).draw(); });
 */

$(document).ready(function() {

	/*
	 * setInterval(function() { accelApiCall(); }, 7000)
	 */

	$('#sites').DataTable({});

	$('#antennae').DataTable({});

}

);

$(document).ready(function() {

	// callback function that configures and initializes DataTables
	function renderTable(xhrdata) {
		var cols = [];

		var exampleRecord = xhrdata[0];

		var keys = Object.keys(exampleRecord);

		keys.forEach(function(k) {
			cols.push({
				title : k,
				data : k
			// optionally do some type detection here for render function
			});
		});

		var table = $('#example').DataTable({
			columns : cols
		});

		table.rows.add(xhrdata).draw();
	}

	// xhr call to retrieve data
	var xhrcall = $.ajax('http://localhost:8080/school/webapi/admin/data');

	// promise syntax to render after xhr completes
	xhrcall.done(renderTable);
});

var map;

var adc = {
		lat : -1.272819,
		lng : 36.813288
	};

// google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {

	
	/*var mapCanvas = document.getElementById('map');
	var mapOptions = {
		center : new google.maps.LatLng(adc),
		zoom : 8,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}

	
	map = new google.maps.Map(mapCanvas, mapOptions);
	
	var marker = new google.maps.Marker({
		position : adc,
		map : map
	});*/
	
	
     map = new google.maps.Map(document.getElementById('map'), {
       zoom: 15,
       center: adc,
       mapTypeId : google.maps.MapTypeId.ROADMAP
     });
     
     var marker = new google.maps.Marker({
	       position: adc,
	       map: map
	     });
     
}

$('#showAntennae').on('shown.bs.modal', function() {
	google.maps.event.trigger(map, "resize");
	
	map.setCenter(adc);
	
});