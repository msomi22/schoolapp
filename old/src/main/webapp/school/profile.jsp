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


<!-- testing -->


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

	//get student's details
	//int reg= Integer.parseInt( request.getParameter("uuid"));

	String uuid = request.getParameter("uuid");

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

<!-- Styled checkbox -->

<link href="css/styledCheckbox/style.css" rel="stylesheet" />

<!-- Cropper -->

<link rel="stylesheet" href="css/cropper/cropper.min.css">
<link rel="stylesheet" href="css/cropper/main.css">
<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Student's Profile</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">



						<div class="col-md-3 col-sm-3 col-xs-12 profile_left">
							<div class="profile_img" id="crop-avatar">

								<!-- Current avatar -->
								<div class="avatar-view" title="Change the avatar"
									id="crop-avatarState">
									<img src="images/user.png" alt="Avatar"
										id="student_profile_pic">
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

								<%-- <div class="col-md-2 col-sm-2" >

											<!-- Current avatar -->
											<div class="avatar-view" title="Change the avatar">
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
										</div> --%>
							</div>
							<h3>
								Student's Name
								<%=uuid%></h3>

							<input type="hidden" id="passedParam" name="regno"
								value="<%=accountId%>/<%=uuid%>">


							<ul class="list-unstyled user_data">
								<li><i class="fa fa-map-marker user-profile-icon"></i>
									School Name</li>



								<li class="m-top-xs">
									<!--  <i class="fa fa-external-link user-profile-icon"></i> -->

									<button class="form-control btn btn-primary" type="button"
										id="btn_deactivate" onclick="deactivateModal()">
										<i class="fa fa-remove"></i> Deactivate
									</button>

								</li>
							</ul>

							<button class=" form-control btn btn-success"
								onclick="activateEditing()" id="btn_editState">
								<i class="fa fa-edit m-right-xs"></i> Edit Details
							</button>
							<br />

							<!-- start skills -->
							<!--  <h4>More</h4> -->
							<ul class="list-unstyled user_data">
								<li>
									<p>Ranking</p>
									<div class="progress progress_sm">
										<div class="form-control progress-bar bg-green"
											role="progressbar" data-transitiongoal="50"></div>
									</div>
								</li>
							</ul>
							<!-- end of skills -->
						</div>


						<div class="col-md-9 col-sm-9 col-xs-12">

							<form action="#" method="POST" id="updateStudentInfo">

								<input type="hidden" name="passport" id="profile_url">

								<div class="" role="tabpanel" data-example-id="togglable-tabs">
									<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
										<li role="presentation" class="active"><a
											href="#tab_content1" id="home-tab" role="tab"
											data-toggle="tab" aria-expanded="true">General Info</a></li>
										<li role="presentation" class=""><a href="#tab_content2"
											role="tab" id="profile-tab" data-toggle="tab"
											aria-expanded="false">Subjects Info</a></li>
										<li role="presentation" class=""><a href="#tab_content3"
											role="tab" id="profile-tab2" data-toggle="tab"
											aria-expanded="false">Primary Info</a></li>

										<li role="presentation" class=""><a href="#tab_content4"
											role="tab" id="profile-tab2" data-toggle="tab"
											aria-expanded="false">Parent's Info</a></li>
									</ul>
									<div id="myTabContent" class="tab-content">
										<div role="tabpanel" class="tab-pane fade active in"
											id="tab_content1" aria-labelledby="home-tab">

											<!-- start general info-->
											<div class="messages">

												<div class="row">
													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<h4>First Name</h4>

														<input type="text" id="fname"
															class="form-control formelement" name="firstname"
															onblur="talkToMe()" placeholder="First Name"
															pattern="[A-Za-z]{3,20}"
															title="First Name,Only characters are allowed and should be more than two and less than 20 characters"
															required>
													</div>

													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<h4>Middle Name</h4>
														<input type="text" id="mname" name="middlename"
															class="form-control formelement"
															placeholder="Middle Name" pattern="[A-Za-z]{3,20}"
															title="Middle Name,Only characters are allowed and should be less than 20 characters ">
													</div>


													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<h4>Last Name</h4>
														<input type="text" id="lname" name="lastname"
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
														<select name="gender" id="gender"
															class="form-control formelement">
															<option value="M">Male</option>
															<option value="F">Female</option>
														</select>
													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>County</h4>
														<select name="county" id="county"
															class="form-control formelement">
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

													<input type="hidden" name="uuid" id="uuid"
														value="<%=uuid%>"> <input type="hidden"
														name="accountId" id="accountId" value="<%=accountId%>">

													<input type="hidden" name="isActive" id="isActive">


													<input type="hidden" name="hasParent" id="hasParent"
														value="false"> <input type="hidden"
														name="hasPrimary" id="hasPrimary" value="false">
													<input type="hidden" name="regStream" id="regStream">
													<input type="hidden" name="isAlumni" id="isAlumni"
														value="0">



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
														<input type="text" id="bcertNo" name="bcertNo"
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
														<select name="isBoarding" id="isBoarding"
															class="form-control formelement">
															<option value="1">Boarding</option>
															<option value="0">Day</option>
														</select>
													</div>



													<div class="col-md-5 col-md-offset-1">
														<h4>Registration No_</h4>

														<input type="text" id="regNo" name="regNo"
															class="form-control formelement"
															placeholder="Registration number" pattern="[0-9]{4}"
															title="Registration number, should contain numerics only and should be 4 numbers only"
															required>
													</div>




												</div>



												<div class="row">
													<div class="col-md-5 col-md-offset-1">


														<h4>Class</h4>

														<select class="form-control formelement" id="classroom">
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

														<select class="form-control formelement"
															name="currentStream" id="currentStream">

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


												<br> <br>

												<div class="row">

													<div class="col-md-3 col-md-offset-5">

														<button type="button" id="submit_gen"
															class="btn btn-primary form-control"
															onclick="updateStudent()">Apply Changes</button>

													</div>


												</div>















												<br> <br> <br>

											</div>
											<!-- end general info -->

										</div>
										<div role="tabpanel" class="tab-pane fade" id="tab_content2"
											aria-labelledby="profile-tab">

											<!-- start subjects -->

											<br>



											<div class="row">

												<div class="col-md-6 col-md-offset-3 alert alert-info">
													Checked represents subjects assigned to the student</div>



											</div>

											<br> <br> <br>

											<div class="row">


												<div id="subjectList"></div>

											</div>






											<!-- end subjects -->

											<br> <br> <br>

										</div>
										<div role="tabpanel" class="tab-pane fade" id="tab_content3"
											aria-labelledby="profile-tab">


											<!-- Primary's info -->

											<div id="primarySchoolDetails">

												<div class="row">


													<div class="col-md-5 col-md-offset-1">
														<h4>School's Name</h4>
														<input type="text" id="schoolName"
															class="form-control formelement" name="schoolName"
															placeholder="School name" pattern="[A-Za-z\s]{3,30}"
															title="School Name,Only characters are allowed and should be less than 20 characters">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>Index Number</h4>
														<input type="text" id="index"
															class="form-control formelement" name="index"
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
														<input type="text" id="kcpemark"
															class="form-control formelement" name="kcpemark"
															placeholder="KCPE marks" pattern="[0-9]{1,3}"
															title="KCPE mark,Only numbers are allowed and should be less than 3 numbers .e.g 234,345,467 e.t.c">

													</div>





												</div>

											</div>



											<br> <br>

											<div class="row">

												<div class="col-md-3 col-md-offset-5">

													<button type="button" id="submit_pri"
														class="btn btn-primary form-control"
														onclick="updateStudent()">Apply Changes</button>

												</div>


											</div>




											<!-- ./end primary info -->
											<br> <br> <br>

										</div>


										<div role="tabpanel" class="tab-pane fade" id="tab_content4"
											aria-labelledby="profile-tab">


											<!-- Parent's info -->

											<div id="parentDetails">

												<div class="row">




													<div class="col-md-8 col-md-offset-2">
														<h4>Name</h4>
														<input type="text" id="parentName"
															class="form-control formelement" name="parentName"
															placeholder="Parent's First name"
															pattern="[A-Za-z]{3,20}"
															title="First Name,Only characters are allowed and should be less than 20 characters">

													</div>

													<!-- <div class="col-md-5 col-md-offset-1">
													<h4>Last Name</h4>
													<input type="text" id="plname"
														class="form-control formelement" name="plname"
														placeholder="Parent's Last name" pattern="[A-Za-z]{3,20}"
														title="Last Name,Only characters are allowed and should be less than 20 characters">

												</div> -->





												</div>


												<div class="row">




													<div class="col-md-5 col-md-offset-1">
														<h4>Phone Number</h4>
														<input type="text" id="parentMobile"
															class="form-control formelement" name="parentMobile"
															placeholder="Phone number" pattern="[0-9]{10}"
															title="Phone,enter a valid number e.g 0712345678">

													</div>

													<div class="col-md-5 col-md-offset-1">
														<h4>Email</h4>
														<input type="email" id="parentEmail"
															class="form-control formelement" name="parentEmail">

													</div>





												</div>

											</div>


											<br> <br>

											<div class="row">

												<div class="col-md-3 col-md-offset-5">

													<button type="button" id="submit_pri"
														class="btn btn-primary form-control"
														onclick="updateStudent()">Apply Changes</button>

												</div>


											</div>



											<!-- ./end parent's info -->
											<br> <br> <br>




										</div>



									</div>
								</div>

							</form>
							<form action="#" method="post" id="alterSujectForm">
								<input type="hidden" name="accountId" id="sub_accountId">
								<input type="hidden" name="uuid" id="subUuid">
								<input type="hidden" name="studentId" id="sub_studentId">
								<input type="hidden" name="subjectId" id="sub_subjectId">
								<input type="hidden" name="description" id="sub_description">

							</form>
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




<script src="js/updateStudent.js"></script>


