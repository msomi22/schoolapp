
/*
    Author: Migwi Ndung'u  <migwi@tawi.mobi>
*/
	function searchstudents(AdmissNo){

    var data = "admissNo=" + escape(AdmissNo);
    //console.log(data);

     //depending on a users browser a request object is created
     function getRequestObject() {
          if (window.XMLHttpRequest) {
             return(new XMLHttpRequest());//for modern browsers, i.e. Opera,Mozilla, chrome e.t.c.
             } 

             else if (window.ActiveXObject) {
               return(new ActiveXObject("Microsoft.XMLHTTP")); //for internet explorer
             } 
             else if(window.createRequest){             
              return(window.createRequest());// for crystal browser
             }
             else {
             return(null); 
             //alert("Your current browser failed, try Mozilla or chrome browsers");
             }
           }
    
      var request=getRequestObject();
      request.onreadystatechange =function() { handleResponse(request); };
       request.open("get", "../school/studentsearch.jsp?"+data, true);
       request.send(); 
  }
//end of onKeyu/Up function



function handleResponse(request) {

        if((request.readyState == 4) && (request.status == 200)) {
             $('.tabledit').remove();
             $('#pagination').hide();   
                 response=request.responseText;
                 //console.log(response);
              $('.tablebody').append(response);                               
        }
}
