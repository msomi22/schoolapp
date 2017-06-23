<!DOCTYPE html>
<html>
<head>
	<title> Login Page </title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap-theme.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap-theme.min.css">
    
	<script src="js/bootstrap/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap/bootstrap-modal.js"></script>
	
     <!-- Mobile first -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="icon" href="img/favicon.ico">

	<style type="text/css">

        .colorgraph {
          height: 5px;
          border-top: 0;
          background: #c4e17f;
          border-radius: 5px;
          background-image: -webkit-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: -moz-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: -o-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: linear-gradient(to right, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
        }
          </style>
</head>

<body>


        <div class="container">
          <div class="row"> 
            <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">  
              <form class="form-horizontal"  role="form" method="POST" action="index.jsp">
                <h2>Please Sign Up <small>It's free and always will be.</small></h2>

                    <hr class="colorgraph">

		               <div class="form-group">
		                    <label for="Firstname" class="col-sm-3 control-label">First Name</label>
		                    <div class="col-sm-9">
		                        <input type="text" name="Firstname" placeholder="First Name" class="form-control" autofocus required autocomplete="off">
		                    </div>
		                </div>

		                 <div class="form-group">
		                    <label for="firstName" class="col-sm-3 control-label">Last Name</label>
		                    <div class="col-sm-9">
		                        <input type="text" name="Lastname" placeholder="Last Name" class="form-control" autofocus required autocomplete="off">
		                    </div>
		                </div>

		                 <div class="form-group">
		                    <label for="firstName" class="col-sm-3 control-label">Username</label>
		                    <div class="col-sm-9">
		                        <input type="text" name="Username" placeholder="Username" class="form-control" autofocus required autocomplete="off">
		                    </div>
		                </div>

		                <div class="form-group">
		                    <label for="email" class="col-sm-3 control-label">Email</label>
		                    <div class="col-sm-9">
		                        <input type="email" name="email" placeholder="Email" class="form-control" required autocomplete="off">
		                    </div>
		                </div>
                        
		                <div class="form-group">
		                    <label for="password" class="col-sm-3 control-label">Password</label>
		                    <div class="col-sm-9">
		                        <input type="password" name="password" id="password" placeholder="Password" class="form-control" required autocomplete="off">
		                    </div>
		                </div>


		                <div class="form-group">
		                    <p class="text-center" id="validate-status" style="color:red;"> </p>
		                    <label for="password" class="col-sm-3 control-label">Confirm Password</label>
		                    <div class="col-sm-9">
		                        <input type="password" name="cpassword" id="cpassword" placeholder="Confirm Password" class="form-control" required autocomplete="off">
		                    </div>
		                </div>

		                <div class="form-group">
		                    <label for="birthDate" class="col-sm-3 control-label">Date of Birth</label>
		                    <div class="col-sm-9">
		                        <input type="date" name="birthDate" class="form-control" required autocomplete="off">
		                    </div>
		                </div>

		                <div class="form-group">
		                    <label for="country" class="col-sm-3 control-label">Category</label>
		                    <div class="col-sm-9">
		                        <select name="Category" class="form-control"  required>
		                            <option>Principal</option>
		                            <option>Deputy Principal</option>
		                            <option>CM/CC</option>
		                            <option>HOD</option>
		                            <option>Barsur</option>
		                            <option>Secretary</option>
		                        </select>
		                     </div>
                         </div>


                         <div class="form-group">
		                    <label for="country" class="col-sm-3 control-label">Gender</label>
		                    <div class="col-sm-9">
		                        <select name="Gender" class="form-control" required>
		                            <option>Male</option>
		                            <option>Female</option>
		                        </select>
		                     </div>
                         </div>

            
			            <div class="row">
			                <div class="col-xs-8 col-sm-9 col-md-9">
			                     By clicking <strong class="label label-primary">Register</strong>, you agree to our <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a>.
			                </div>
			            </div>
            
                        <hr class="colorgraph">

			            <div class="row">
			                <div class="col-xs-12 col-md-6">  
			                     <button type="submit" class="btn btn-success btn-block btn-lg">Register</button>  
			                </div>
			            </div>

              </form>
      </div>
 </div>

<!-- Modal -->
<div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
            </div>
            <div class="modal-body">
                <p>Permission is hereby granted, to a school/person obtaining a copy
	            of this software and associated documentation files (the "Software"), to
	            use the Software, subject to the following conditions:</p>

	            <p>The school/person pays a fee not less than KSH 20,000 to use the 
	            software for a period of not more than 20 years, OR buys the software
	            permanently for a cost of KSH 120,000.</p>

	            <p>Service fee not less than KSH 1,000 will be required for fixing bugs and
	                for any other kind of maintainace or upgraderelating to this product.</p>

	            <p>You can not ligally use this software, sell the software or part of
	            the software without Licensor's permission.</p>

	            <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
	            FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
	            IN THE SOFTWARE.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</div>

<script type="text/javascript">
	$(document).ready(function() {
      $("#cpassword").keyup(validate);
    });


function validate() {
  var password1 = $("#password").val();
  var password2 = $("#cpassword").val();

    
 
    if(password1 != password2) {
       $("#validate-status").text("Password doesn't match!");        
    }else{
    	$("#validate-status").text(" ");
    }
}


</script>
</body>
</html>