$(document).keypress(function(e) {
    if(e.which == 13) {

            // Do something here if the popup is open
            //alert("dd")
            var index = $('.ui-dform-text').index(document.activeElement) + 1;
            $('.ui-dform-text').eq(index).focus();

    }
});


function trimVar(x) {
    return x.replace(/^\s+|\s+$/gm,'');
}

function validateScore(id){
	
	//define a regex of the score
	var scoreRegx=/[0-9]{1}|[0-9]{1}[0-9]{1}/i;
	
	///get the cell value
	var Row = document.getElementById(id);
	var Cells = Row.getElementsByTagName("td");
	
	var score= trimVar(Cells[5].innerText);
	
	
	if(score.length>0){
	
	if(!score.match(scoreRegx) || score.length>2){
		
		
		
		$('#scorewarning').modal('show');
		
		 Cells[5].innerText="";
		 


		setTimeout(function(){
	       $('#scorewarning').modal('hide');
	   }, 2500);
	}
	
	}
}