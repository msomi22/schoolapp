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

	SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();

	SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

	int currentYear = Integer.parseInt(sysConfig.getYear());

	int currentTerm = Integer.parseInt(sysConfig.getTerm());

	//get class list
	ClassDAO classDAO = ClassDAO.getInstance();

	List<ClassRoom> classroomList = new ArrayList<>();

	classroomList = classDAO.getClassRooms(accountId);

	//get stream list
	StreamDAO streamDAO = StreamDAO.getInstance();

	List<Stream> streamList = new ArrayList<>();

	streamList = streamDAO.getStreamList(accountId);
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




<!-- Date and time picker -->

<link rel="stylesheet" href="css/pikaday.css">



<!-- Cropper -->

<link rel="stylesheet" href="css/cropper/cropper.min.css">
<link rel="stylesheet" href="css/cropper/main.css">




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
											Register a new student<small> Click register when
												done</small>
										</h1>
										<ul class="nav navbar-right panel_toolbox">
											<li><a class="collapse-link"><i
													class="fa fa-chevron-up"></i></a></li>
										</ul>
										<div class="clearfix"></div>
									</div>
									<div class="x_content">
										<br />

										<!-- 	<form action="studentRegistration" id="registerStudent"
											enctype="multipart/form-data" method="post"> -->
										<div class="col-md-2 col-sm-2" id="crop-avatar">

											<!-- Current avatar -->
											<div class="avatar-view works" title="Change the avatar">
												<img src="images/user.png" alt="Avatar">
												<!-- Since i can't get the dist dir need to create a preview here simiar 
												to the one in the cropping option, thus i will have to look at the code
												that previews that image before cropping it then i will have achieved my goal. setting a new input file wint work
												 -->


											</div>
											<!-- Cropper Modal -->
											<jsp:include page="modals/cropper.html" />

											<!-- Loading state -->
											<div class="loading" aria-label="Loading" role="img"
												tabindex="-1"></div>
										</div>


										<form action="studentRegistration" id="registerStudent"
											class="col-md-6 col-sm-6 col-md-offset-1"
											 method="post" target="_blank">
											<!-- <div class="col-md-6 col-sm-6 col-md-offset-1"> -->


											<input type="hidden" name="profile_url" id="profile_url"
												value="">
											<!-- names  -->


											<div class="row">


												<div class="col-md-3 col-md-offset-1">
													<h4>First Name</h4>

													<input type="text" id="fname"
														class="form-control formelement" name="fname"
														placeholder="First Name" pattern="[A-Za-z]{3,20}"
														title="First Name,Only characters are allowed and should be more than two and less than 20 characters"
														required>
												</div>

												<div class="col-md-3 col-md-offset-1">
													<h4>Middle Name</h4>
													<input type="text" id="mname" name="mname"
														class="form-control formelement" placeholder="Middle Name"
														pattern="[A-Za-z]{3,20}"
														title="Middle Name,Only characters are allowed and should be less than 20 characters ">
												</div>


												<div class="col-md-3 col-md-offset-1">
													<h4>Last Name</h4>
													<input type="text" id="lname" name="lname"
														class="form-control formelement" placeholder="Last Name"
														pattern="[A-Za-z]{3,20}"
														title="Last Name,Only characters are allowed and should be more than two and less than 20 characters"
														required>
												</div>



											</div>

											<br>


											<!-- sex and county  -->

											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<h4>Gender</h4>
													<select name="gender" class="form-control formelement">
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
													<input type="date" id="dob"
														class="form-control formelement" name="dob"
														placeholder="Date of Birth">


												</div>

												<div class="col-md-5 col-md-offset-1">
													<h4>Birth Cert N0_</h4>
													<input type="text" id="bcertno" name="bcertno"
														class="form-control formelement"
														placeholder="Birth Cert No_" pattern="[0-9]{5}"
														title="Birth cert no, should contain numerics only and should be 5 numbers only"
														required>
												</div>




											</div>

											<br> <br> <br>



											<!-- Schoool info -->


											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<h4>Type</h4>
													<select name="boarding" class="form-control formelement">
														<option value="1">Boarding</option>
														<option value="0">Day</option>
													</select>
												</div>



												<div class="col-md-5 col-md-offset-1">
													<h4>Registration No_</h4>

													<input type="text" id="regno" name="regno"
														class="form-control formelement"
														placeholder="Registration number" pattern="[0-9]{4}"
														title="Registration number, should contain numerics only and should be 4 numbers only"
														required>
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






											<!-- 	<div class="row">
											
											

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
 -->

											<br> <br>
											<!-- Primary school element -->
											<h4>Enter Primary school details:</h4>

											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="no" class="form-control"
														name="primaryschool" value="false"
														onclick="primarySwap(this.id)" checked> <label
														for="no">
														<h6>NO</h6>

													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="yes" value="true"
														name="primaryschool" onclick="primarySwap(this.id)">
													<label for="yes">
														<h6>Yes</h6>

													</label>
												</div>

											</div>




											<!-- Priamry school details -->
											<div id="primarySchoolDetails" style="display: none">

												<div class="row">




													<div class="col-md-5 col-md-offset-1">
														<h4>School's Name</h4>
														<input type="text" id="schoolname"
															class="form-control formelement" name="schoolname"
															placeholder="School name" pattern="[A-Za-z]{3,30}"
															title="School Name,Only characters are allowed and should be less than 20 characters">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>Index Number</h4>
														<input type="text" id="indexno"
															class="form-control formelement" name="indexno"
															placeholder="Index Number" pattern="[0-9]{9}"
															title="Index Number,Only numbers are allowed and should be 9 numbers">

													</div>





												</div>


												<div class="row">




													<div class="col-md-5 col-md-offset-1">
														<h4>KCPE YEAR</h4>
														<input type="text" id="kcpeyear"
															class="form-control formelement" name="kcpeyear"
															placeholder="KCPE year" pattern="[0-9]{4}"
															title="KCPE year,enter an year">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>KCPE MARKS</h4>
														<input type="text" id="kcpemarks"
															class="form-control formelement" name="kcpemarks"
															placeholder="KCPE marks" pattern="[0-9]{1,3}"
															title="KCPE mark,Only numbers are allowed and should be less than 3 numbers .e.g 234,345,467 e.t.c">

													</div>





												</div>

											</div>


											<br>
											<!-- Student's parent element -->
											<h4>Enter Parent's details:</h4>

											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="noParent" class="form-control"
														name="parent" value="false" onclick="primarySwap(this.id)"
														checked> <label for="noParent">
														<h6>NO</h6>

													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="yesParent" name="parent"
														value="true" onclick="primarySwap(this.id)"> <label
														for="yesParent">
														<h6>Yes</h6>

													</label>
												</div>

											</div>


											<!-- Parent's details -->
											<div id="parentDetails" style="display: none">

												<div class="row">




													<div class="col-md-5 col-md-offset-1">
														<h4>First Name</h4>
														<input type="text" id="pfname"
															class="form-control formelement" name="pfname"
															placeholder="Parent's First name"
															pattern="[A-Za-z]{3,20}"
															title="First Name,Only characters are allowed and should be less than 20 characters">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>Last Name</h4>
														<input type="text" id="plname"
															class="form-control formelement" name="plname"
															placeholder="Parent's Last name" pattern="[A-Za-z]{3,20}"
															title="Last Name,Only characters are allowed and should be less than 20 characters">

													</div>





												</div>


												<div class="row">




													<div class="col-md-5 col-md-offset-1">
														<h4>Phone Number</h4>
														<input type="text" id="phone"
															class="form-control formelement" name="phone"
															placeholder="Phone number" pattern="[0-9]{10}"
															title="Phone,enter a valid number e.g 0712345678">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>Email</h4>
														<input type="email" id="email"
															class="form-control formelement" name="email">

													</div>





												</div>

											</div>












											<br> <br> <input type="hidden" name="action"
												value="add">


											<!-- 	<input type="hidden" name="action" value="edit"> -->

											<!-- footer of the form elements: Back,Reset and generate -->

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<button class="btn btn-primary">Back</button>
													<button type="reset" class="btn btn-primary">Reset</button>
												</div>



												<div class="col-md-2 pull-right">

													<button type="submit" onclick="talkToMe()" class="btn btn-lg btn-primary">Register</button>
													<!--  <button type="submit" onclick="submitStudentData(this.form)" class="btn btn-lg btn-primary">Register</button> -->
												</div>


											</div>
										</form>


										<!-- </form> -->
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




