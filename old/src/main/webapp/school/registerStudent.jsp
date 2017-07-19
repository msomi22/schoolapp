<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Stream"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.ClassDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>



<!-- Config -->
<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!-- Import calendar -->
<%@page import="java.util.Calendar"%>
<%@page import="java.util.GregorianCalendar"%>
   





<%

  if (session == null) {
       response.sendRedirect("../index.jsp");
       //return;
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
        //return;
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
    
    String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 
    
    
     //get the current year
    /*  int current= 0;
     
      GregorianCalendar cal = new GregorianCalendar();
      current=cal.get(Calendar.YEAR); */
      
      SysConfigDAO sysConfigDAO= SysConfigDAO.getInstance();
      
      SysConfig sysConfig= sysConfigDAO.getSysConfig(accountId);
      
      int currentYear= Integer.parseInt(sysConfig.getYear());
      
      int currentTerm= Integer.parseInt(sysConfig.getTerm());
      
      
      
      //get class list
      ClassDAO classDAO = ClassDAO.getInstance();
      
      List<ClassRoom> classroomList = new ArrayList<>();
      
      classroomList = classDAO.getClassRooms(accountId);
      
      
      
      //get stream list
      StreamDAO streamDAO = StreamDAO.getInstance();
      
      List<Stream> streamList= new ArrayList<>();
      
      streamList= streamDAO.getStreamList(accountId);
     
    
     
     
     
   
%>
<jsp:include page="header.jsp" />
<!-- Custom report style -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

<link rel='stylesheet prefetch'
	href='https://fonts.googleapis.com/css?family=Roboto:400,700'>
<link rel='stylesheet prefetch'
	href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css'>
<link rel="stylesheet" href="css/customReportStyle.css">

<link rel="stylesheet" href="css/pikaday.css">




<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Student Registration window</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">






						<div class="row">

							<div class="col-md-12 col-sm-12 col-xs-12">
								<div class="x_panel">
									<div class="x_title">
										<h1>
											Register a new student<small> Click register
												when done</small>
										</h1>
										<ul class="nav navbar-right panel_toolbox">
											<li><a class="collapse-link"><i
													class="fa fa-chevron-up"></i></a></li>
										</ul>
										<div class="clearfix"></div>
									</div>
									<div class="x_content">
										<br />


										<form action="studentRegistration" id="registerStudent"
											class="col-md-6 col-md-offset-3" method="post" target="_blank">


								<!-- names  -->
											
											<div class="row">
											

												<div class="col-md-3 col-md-offset-1">
												<h4>First Name</h4>
												
													<input type="text" id="fname" class="form-control formelement"
														name="fname" placeholder="First Name">
												</div>

												<div class="col-md-3 col-md-offset-1">
												<h4>Middle Name</h4>
													<input type="text" id="mname" name="mname"   class="form-control formelement"
													placeholder="Middle Name" >
												</div>
												
												
												<div class="col-md-3 col-md-offset-1">
												<h4>Last Name</h4>
													<input type="text" id="lname" name="lname" class="form-control formelement" 
													placeholder="Last Name"   >
												</div>
												
												
												
											</div>
											
											<br>


								<!-- sex and county  -->
											
											<div class="row">

												<div class="col-md-5 col-md-offset-1">
												<h4>Gender</h4>
													<select name="gender" class="form-control formelement" >
													<option value="male">Male</option>
													<option value="female">Female</option>
													</select>
												</div>

												<div class="col-md-5 col-md-offset-1">
												<h4>County</h4>
													<select name="county" class="form-control formelement">
														<option value='Baringo'>Baringo</option>
														<option value='Bomet'>Bomet</option>
														<option value='Bungoma'>Bungoma</option>
														<option value='Busia'>Busia</option>
														<option value='Elgeyo-Marakwet'>Elgeyo-Marakwet</option>
														<option value='Embu'>Embu</option>
														<option value='Garissa'>Garissa</option>
														<option value='Homa Bay'>Homa Bay</option>
														<option value='Isiolo'>Isiolo</option>
														<option value='Kajiado'>Kajiado</option>
														<option value='Kakamega'>Kakamega</option>
														<option value='Kericho'>Kericho</option>
														<option value='Kiambu'>Kiambu</option>
														<option value='Kilifi'>Kilifi</option>
														<option value='Kirinyaga'>Kirinyaga</option>
														<option value='Kisii'>Kisii</option>
														<option value='Kisumu'>Kisumu</option>
														<option value='Kitui'>Kitui</option>
														<option value='Kwale'>Kwale</option>
														<option value='Laikipia'>Laikipia</option>
														<option value='Lamu'>Lamu</option>
														<option value='Machakos'>Machakos</option>
														<option value='Makueni'>Makueni</option>
														<option value='Mandera'>Mandera</option>
														<option value='Marsabit'>Marsabit</option>
														<option value='Meru'>Meru</option>
														<option value='Migori'>Migori</option>
														<option value='Mombasa'>Mombasa</option>
														<option value='Murang'a'>Murang'a</option>
														<option value='Nairobi City'>Nairobi City</option>
														<option value='Nakuru'>Nakuru</option>
														<option value='Nandi'>Nandi</option>
														<option value='Narok'>Narok</option>
														<option value='Nyamira'>Nyamira</option>
														<option value='Nyandarua'>Nyandarua</option>
														<option value='Nyeri'>Nyeri</option>
														<option value='Samburu'>Samburu</option>
														<option value='Siaya'>Siaya</option>
														<option value='Taita-Taveta'>Taita-Taveta</option>
														<option value='Tana River'>Tana River</option>
														<option value='Tharaka-Nithi'>Tharaka-Nithi</option>
														<option value='Trans Nzoia'>Trans Nzoia</option>
														<option value='Turkana'>Turkana</option>
														<option value='Uasin Gishu'>Uasin Gishu</option>
														<option value='Vihiga'>Vihiga</option>
														<option value='West Pokot'>West Pokot</option>
														<option value='wajir'>wajir</option>
													</select>
												</div>

												
												
												
											</div>
											
											
											<br>
											
											
											
											
											<!-- dob and bcertno -->
											
											<div class="row">

												<div class="col-md-5 col-md-offset-1">
												<h4>Date of Birth</h4>
													<input type="date" id="dob" class="form-control formelement"
														name="dob" placeholder="Date of Birth"  >

												
												</div>

												<div class="col-md-5 col-md-offset-1">
												<h4>Birth Cert N0_</h4>
													<input type="text" id="bcertno" name="bcertno"  class="form-control formelement"
													placeholder="Birth Cert No_" >
												</div>

												
												
												
											</div>
											
											<br>
											
											
											<br>
											<br>
											
											
											
											<!-- Schoool info -->


											<div class="row">

												<div class="col-md-5 col-md-offset-1">
												<h4>School's Name</h4>
													<input type="text" id="schoolname" class="form-control formelement"
														name="schoolname" placeholder="School name" >
												</div>

												<div class="col-md-5 col-md-offset-1">
												<h4>Registration No_</h4>
												
													<input type="text" id="regno" name="regno" class="form-control formelement"
													placeholder="Registration number" >
												</div>




											</div>
											
											
											
											
											<div class="row">
											
											

												<div class="col-md-5 col-md-offset-1">
												<h4>Type</h4>
													<select name="boarding" class="form-control formelement" >
													<option value="1">Boarding</option>
													<option value="0">Day</option>
													</select>
												</div>
												
												

												<div class="col-md-5 col-md-offset-1">
												<h4>Term</h4>
													<select name="term" class="form-control formelement">
														<option value="1">Term 1</option>
														<option value="2">Term 2</option>
														<option value="3">Term 3</option>
														
													</select>
												</div>

												
												
												
											</div>



											<div class="row">
												<div class="col-md-5 col-md-offset-1">


													<h4>Class</h4>

													<select class="form-control formelement" name="classroom">
														<%
															if (classroomList != null) {
																for (ClassRoom classroom : classroomList) {
														%>

														<option value="<%=classroom.getUuid()%>">
															<%=classroom.getDescription()%></option>

														<%
															}
															} else {
														%>
														<option value="">...</option>
														<%
															}
														%>
													</select>



												</div>
												
												
												
												
												<div class="col-md-5 col-md-offset-1">

													<h4>Stream</h4>

													<select class="form-control formelement" name="stream">

														<%
															if (streamList != null) {
																for (Stream stream : streamList) {
														%>

														<option value="<%=stream.getUuid()%>">
															<%=stream.getDescription()%></option>

														<%
															}
															}

															else {
														%>
														<option value="">...</option>

														<%
															}
														%>


													</select>

												</div>

											</div>
											
											
											
											<div class="row">
											
											

												<div class="col-md-5 col-md-offset-1">
												<h4>Active</h4>
													<select name="active" class="form-control formelement" >
													<option value="1">Yes</option>
													<option value="0">No</option>
													</select>
												</div>
												
												

												<div class="col-md-5 col-md-offset-1">
												<h4>Alumni</h4>
													<select name="alumni" class="form-control formelement">
														<option value="0">No</option>
														<option value="1">Yes</option>
														
													</select>
												</div>

												
												
												
											</div>








											<br>
											<br>
											
											<input type="hidden" name="action" value="add">
											
											
										<!-- 	<input type="hidden" name="action" value="edit"> -->

											<!-- footer of the form elements: Back,Reset and generate -->

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<button class="btn btn-primary">Back</button>
													<button type="reset" class="btn btn-primary">Reset</button>
												</div>



												<div class="col-md-2 pull-right">
													<button type="button" onclick="submitStudentData(this.form)" class="btn btn-lg btn-primary">Register</button>
												</div>


											</div>


										</form>



									</div>
									<!-- ./content -->
								</div>
							</div>

						</div>


					</div>
				</div>
			</div>
		</div>
	</div>
</div>





<!-- /page content -->
<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->


<jsp:include page="footer.jsp" />

<script src="js/registerStudent.js"></script>
<script src="js/datepicker/moment.min.js"></script>
<script src="js/datepicker/pikaday.js"></script>
<script src="js/datepicker/pikaday.jquery.js"></script>

	<script>
														var timepicker = new Pikaday(
																{
																	field : document
																			.getElementById('dob'),
																	firstDay : 1,
																	minDate : new Date(
																			1990,
																			0,
																			1),
																	maxDate : new Date(
																			2006,
																			12,
																			31),
																	yearRange : [
																			1990,
																			2006 ],
																	showTime : true,
																	autoClose : false,
																	use24hour : false,
																	format : 'YYYY-MM-DD'
																});
													</script>


