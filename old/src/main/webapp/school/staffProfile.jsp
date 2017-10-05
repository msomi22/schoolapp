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
	
	String loggedUserId = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
	
	String loggedUserAccessId = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_CATEGORY);

	//get student's details
	//int reg= Integer.parseInt( request.getParameter("uuid"));

	String uuid = request.getParameter("uuid");

	//get class list
	
%>
<jsp:include page="header.jsp" />

<!-- Styled checkbox -->

<link href="css/styledCheckbox/style.css" rel="stylesheet" />


<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Staff's Profile  </h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">
					
					<div class="col-md-1 col-sm-1 col-xs-1">
					<a class="btn btn-success round secondary-assent cards"
								href="staff.jsp" >
								<i class="fa fa-arrow-left m-right-xs"></i> &laquo;
							</a> 
					
					
					</div>
						<div class="col-md-9 col-sm-9 col-xs-12">
						
						<input type="hidden" id="passed_log_id" value="<%=loggedUserId %>">

							

								<input type="hidden" name="passedLogId" id="passedLogId" value="<%=loggedUserId %>">
								<input type="hidden" name="passedLogAcessId" id="passedLogAcessId" value="<%=loggedUserAccessId %>">

								<div class="" role="tabpanel" data-example-id="togglable-tabs">
									<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
										<li role="presentation" class="active"><a
											href="#tab_content1" id="home-tab" role="tab"
											data-toggle="tab" aria-expanded="true">General Info</a></li>
										<li role="presentation" class=""><a href="#tab_content2"
											role="tab" id="profile-tab" data-toggle="tab"
											aria-expanded="false"  onclick="fetchStaffRoles()">Subjects and Classes</a></li>
									</ul>
									<div id="myTabContent" class="tab-content">
										<div role="tabpanel" class="tab-pane fade active in"
											id="tab_content1" aria-labelledby="home-tab">
											
											<form action="#" method="POST" id="updateStaffRolesForm">

											<!-- start general info-->
											<div class="messages">

												<div class="row">
													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">

														<label for="fname">First Name</label> <input type="text"
															id="fname" class="form-control formelement"
															name="firstname" placeholder="First Name"
															pattern="[A-Za-z]{3,20}"
															title="First Name,Only characters are allowed and should be more than two and less than 20 characters"
															required>
													</div>

													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<label for="mname">Middle Name</label> <input type="text"
															id="mname" name="middlename"
															class="form-control formelement"
															placeholder="Middle Name" pattern="[A-Za-z]{3,20}"
															title="Middle Name,Only characters are allowed and should be less than 20 characters ">
													</div>


													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<label for="lname">Last Name</label> <input type="text"
															id="lname" name="lastname"
															class="form-control formelement" placeholder="Last Name"
															pattern="[A-Za-z]{3,20}"
															title="Last Name,Only characters are allowed and should be more than two and less than 20 characters"
															required>
													</div>
												</div>


												<br>


												<!-- sex and county  -->

												<div class="row">

													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<h4>Gender</h4>
														<select name="gender" id="gender"
															class="form-control formelement">
															<option value="M">Male</option>
															<option value="F">Female</option>
														</select>
													</div>

													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<label for="email">Email</label> <input type="email"
															id="email" class="form-control formelement" name="email">
													</div>


													<div
														class="col-md-3 col-md-offset-1 col-sm-10 col-sm-offset-2">
														<label for="phone">Phone Number</label> <input type="text"
															id="phone" class="form-control formelement" name="mobile"
															placeholder="Phone number" pattern="[0-9]{9}"
															title="Phone,enter a valid number e.g 712345678">
													</div>





													<input type="hidden" name="uuid" id="uuid"
														value="<%=uuid%>">
														
														<input type="hidden" name="logedUserId" id="logedUserId"
														value="<%=loggedUserId %>">
														
															<input type="hidden" name="logedUserAccessId" id="logedUserAccessId"
														value="<%=loggedUserAccessId %>">
														
														<input type="hidden" name="acessLevelId" id="acessLevelId"
														value="">
														
														 <input type="hidden"
														name="accountId" id="accountId" value="<%=accountId%>">

													<input type="hidden" name="isActive" id="isActive">
													
													
													<input type="hidden" name="staffNo" id="staffNo">






												</div>


												<br> <br>



												<!-- dob and bcertno -->

												<div class="row">

													<div class="col-md-5 col-md-offset-1">

														<label for="username">User name</label> <input type="text"
															id="username" class="form-control formelement"
															name="username" placeholder="Username"
															pattern="[A-Za-z0-9]{3,20}"
															title="Username, Alpha numeric characters are allowed and should be more than two and less than 20 characters"
															required>



													</div>

													<div class="col-md-5 col-md-offset-1">
														<label for="password">Password</label> <input
															type="password" id="password"
															class="form-control formelement" name="password"
															placeholder="Username" pattern="[A-Za-z0-9]{6,20}"
															title="Password, Alpha numeric characters are allowed and should be more than six characters"
															required>
													</div>




												</div>




												<br> <br> <br>




												<div class="row">

													<div class="col-md-3 col-md-offset-5">

														<button type="button" id="submit_gen"
															class="btn btn-primary form-control"
															onclick="updateStaffDetails()">Apply Changes</button>

													</div>


												</div>















												<br> <br> <br>

											</div>
											<!-- end general info -->
											
											
							</form>

										</div>
										<div role="tabpanel" class="tab-pane fade" id="tab_content2"
											aria-labelledby="profile-tab">
 
											<!-- start subjects -->

											<br>



											<div class="row">

												<div class="col-md-6 col-md-offset-3 alert alert-info secondary-assent">
													Alter Staff's subjects and classes</div>



											</div>

											<div class="row ">
											<div class="col-md-3 pull-right"></div>

												<div class="col-md-4 pull-right">
													<h3 class="pull-right">
														Assign new Subject
														<button class="btn btn-primary" style="border-radius: 90%"
															id="btn_addStaffRoles" onclick="staffSuject(this.id)">
															<i class="fa fa-plus-circle fa-2x"></i>
														</button>

													</h3>

												</div>

											</div>



											<div class="row">
											
											<div class="table-responsive col-md-6 col-md-offset-3">
												<table class="table table-striped jambo_table bulk_action"
													id="staffRoles">
													<thead>
														<tr class="headings secondary-assent">

															<th class="column-title">teacherId</th>
															<th class="column-title">subjectId</th>
															<th class="column-title">subjectDesc</th>
															<th class="column-title">streamId</th>
															<th class="column-title">uuid</th>
															<th class="column-title">accountId</th>
															<th class="column-title">allocationDate</th>
															<th class="column-title">Modify</th>

														</tr>
													</thead>

													<tbody class='tablebody'>



														<!-- <tr class="tabledit" style='color: black;'>

										<td width="5%">1</td>
										<td class="center hidden">### ###</td>
										<td class="center">Form 1N</td>
										<td>

											<button class="btn btn-warning editStream" id="edit"
												onclick="streamModal(this.id)">
												Edit <span class="fa fa-edit"></span>
											</button>
											<button class="btn btn-danger" id="Form 1N"
												onclick="delStream(this.id)">
												Delete <span class="fa fa-trash"></span>
											</button>
										</td>

									</tr> -->


													</tbody>


												</table>
												</div>

											</div>


											<br> <br> <br>

											<div class="row">


												<div id="subjectList"></div>

											</div>






											<!-- end subjects -->

											<br> <br> <br>

										</div>





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


<!-- Staff Modal -->
<jsp:include page="modals/staffModals.jsp" />

<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />




<script src="js/staff.js"></script>

<script src="js/updateStaff.js"></script>


