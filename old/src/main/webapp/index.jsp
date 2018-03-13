<!DOCTYPE html>
<%@page import="ke.co.qubintel.school.server.session.SessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.*"%>
<%@page import="java.util.Calendar"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>SchoolApp</title>

<!-- Bootstrap -->
<link href="vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link href="vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="vendors/nprogress/nprogress.css" rel="stylesheet">
<!-- Animate.css -->
<link href="vendors/animate.css/animate.min.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="build/css/custom.css" rel="stylesheet">

<!-- Styling -->


<link rel="stylesheet" href="school/css/ui-styling2.css">

<link rel="stylesheet" href="school/css/formelementBorder.css">

<link rel="icon" href="resources/favicon.ico">
</head>

<body class="login" onload="disableBackButton()">
	<div>

		<div class="container">

			<div class="">

				<div class="row">

					<div
						class="col-md-offset-2 col-md-2 col-sm-offset-2 col-sm-4 col-xs-offset-1 col-xs-5 ">

						<img class="img-responsive cards logoPlace" width="80%"
							src="school/images/logo.png">

					</div>



					<div
						class="col-md-offset-5 col-md-3 col-sm-offset-2 col-sm-4 col-xs-6 ">

						<div class="logoPlace">

							<h4>
								<b>Email : info@qubintel.co.ke</b>
							</h4>
							<h4>
								<b>Tel 1 : +254 718 953974</b>
							</h4>
							<h4>
								<b>Tel 2 : +254 706 975801</b>
							</h4>


						</div>

					</div>



				</div>



			</div>


		</div>
		<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
			id="signin"></a>



		<div class="login_wrapper">

			<div class="animate form login_form">
				<section class="login_content">
					<%
						String loginErrStr = "";

						if (session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY) != null | session != null) {
							loginErrStr = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR);

							//  session.invalidate();
							// session.invalidate(); 

							//   response.sendRedirect("index.jsp");

						}

						if (StringUtils.isNotEmpty(loginErrStr)) {
					%>
					<div class="alert alert-warning">
						<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Warning!</strong>
						<%
							out.println("Login error: " + loginErrStr);
						%>
					</div>

					<%
						session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, null);
						}
					%>
					<form action="schoolLogin" method="post">
						<h1>Login Form</h1>

						<div>
							<input type="text" class="form-control formelement"
								placeholder="School Username" required name="schoolUsername" />
						</div>

						<div>
							<input type="text" class="form-control formelement"
								placeholder="Staff Username" required name="staffUsername" />
						</div>

						<div>
							<input type="password" class="form-control formelement"
								placeholder="Password" required name="staffPassword" />
						</div>

						<div>
							<button type="submit"
								class="btn btn-primary btn-block btn-flat cards">Log in</button>

							<a class="reset_pass" onclick="forgotPassword()" href="#">Lost
								your password?</a>
						</div>

						<div class="clearfix"></div>

						<div class="separator">
							<p class="change_link">
								New to site? <a href="#signup" class="to_register"> Create
									Account </a>
							</p>

							<div class="clearfix"></div>
							<br />

							<div>
								<h1>
									<i class="fa fa-graduation-cap"></i> F.M.S
								</h1>
								<p>&copy;2017 All Rights Reserved.</p>
								<!-- <a class="btn btn-primary secondary-assent cards"
									data-toggle="modal" data-target="#contact">Contact Us...</a> -->
							</div>
						</div>
					</form>
				</section>
			</div>





			<div id="register" class="animate form registration_form">
				<section class="login_content">
					<form>
						<h1>Create Account</h1>
						<div>
							<input type="text" class="form-control" placeholder="Username"
								required="" />
						</div>
						<div>
							<input type="email" class="form-control" placeholder="Email"
								required />
						</div>
						<div>
							<input type="password" class="form-control"
								placeholder="Password" required />
						</div>
						<div>
							<button type="submit" class="btn btn-primary btn-block btn-flat">Submit
							</button>
						</div>

						<div class="clearfix"></div>

						<div class="separator">
							<p class="change_link">
								Already a member ? <a href="#signin" class="to_register">
									Log in </a>
							</p>

							<div class="clearfix"></div>
							<br />

							<div>
								<h1>
									<i class="fa fa-graduation-cap"></i> F.M.S
								</h1>
								<p>&copy;2017 All Rights Reserved.</p>
								<!-- <a class="btn btn-primary secondary-assent cards"
									data-toggle="modal" data-target="#contact">Contact Us...</a> -->
							</div>
						</div>
					</form>
				</section>
			</div>


			<div id="forgotPassword" class="animate form" style="display: none">
				<section class="login_content">
					<form action="#" method="post" id="forgotPasswordForm">
						<h3>Forgot Password</h3>

						<div class="row alert alert-info">


							<h4>Please enter the following to get your password:</h4>
							<h6>School account name or email for the first field</h6>

							<h6>Your Email Address or Phone number for the second input
								field</h6>




						</div>
						<div>
							<input type="text" class="form-control" id="account_name"
								placeholder="Enter School Account Name or Email"
								required="required" />
						</div>


						<div>
							<input type="text" class="form-control"
								placeholder="Your Phone number or  email" id="query"
								required="required" />
						</div>

						<div>
							<button type="button" onclick="requestPassword()"
								class="btn btn-primary btn-block btn-flat">Submit</button>
						</div>

						<div class="clearfix"></div>

						<div class="separator">
							<p class="change_link">
								Already a member ? <a href="index.jsp" class="to_register">
									Log in </a>
							</p>

							<div class="clearfix"></div>
							<br />

							<div>
								<h1>
									<i class="fa fa-graduation-cap"></i>F.M.S
								</h1>
								<p>&copy;2017 All Rights Reserved.</p>
							<!-- 	<a class="btn btn-primary secondary-assent cards"
									data-toggle="modal" data-target="#contact">Contact Us...</a> -->
							</div>
						</div>
					</form>
				</section>
			</div>



		</div>


	</div>

	<!-- jQuery -->
	<script src="vendors/jquery/dist/jquery.min.js"></script>
	<!-- <script src="../vendors/jquery-ui/jquery-ui.min.js"></script> -->


	<!-- Bootstrap -->
	<script src="vendors/bootstrap/dist/js/bootstrap.min.js"></script>


	<!-- Json conversion -->

	<script src="school/js/json/jquery.serializejson.js"></script>


	<!-- RootApiCall js -->
	<script src="school/js/apiCalls/rootApiCall.js"></script>



	<!-- RootApiCall Response Parser js -->
	<script src="school/js/apiCalls/rootApisResponseParser.js"></script>


	<!-- RootForm validator js -->
	<script src="school/js/rootFormValidator.js"></script>


	<script src="school/js/passwordUpdate.js"></script>


	<script type="text/javascript">
		$(document).ready(function() {
			console.log('Heheheh');
			// sessionStorage.clear();

			disableBackButton();

			/* window.location = location.protocol + "//" + window.location.host
			+ "/school/schoolLogout"; 
			window.onhashchange = function() {
			//blah blah blah
			}*/

		})

		window.onhashchange = function() {
			//blah blah blah

			console.log(window.location);
		}

		function disableBackButton() {
			window.history.forward();
		}
		setInterval("disableBackButton()", 10);
	</script>


	<!-- State Modal -->
	<jsp:include page="school/modals/initModals.html" />

</body>
</html>
