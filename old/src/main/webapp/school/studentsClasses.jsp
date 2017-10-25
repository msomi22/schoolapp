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

<link href="css/customReportStyle.css" rel="stylesheet" />


<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Students and Classes</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">
						<div class="col-md-9 col-sm-9 col-xs-12">



							<input type="hidden" name="passedLogId" id="passedLogId"
								value="<%=loggedUserId%>"> <input type="hidden"
								name="passedLogAcessId" id="passedLogAcessId"
								value="<%=loggedUserAccessId%>"> <input type="hidden"
								name="accountId" id="accountId" value="<%=accountId%>">

							<div class="" role="tabpanel" data-example-id="togglable-tabs">
								<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
									<li role="presentation" class="active"><a
										href="#tab_content1" id="home-tab" role="tab"
										data-toggle="tab" aria-expanded="true">Active Students</a></li>
									<li role="presentation" class=""><a href="#tab_content2"
										role="tab" id="profile-tab" data-toggle="tab"
										aria-expanded="false">Inactive Students</a></li>
								</ul>
								<div id="myTabContent" class="tab-content">
									<div role="tabpanel" class="tab-pane fade active in"
										id="tab_content1" aria-labelledby="home-tab">

										<br>

										<div class="row">


											<div class="col-md-2">


												<button class="btn btn-primary secondary-assent  cards"
													onclick="studentsListModa()">
													Export <i class="fa fa-file-pdf-o"> </i>
												</button>
											</div>

											<div class="col-md-6 col-md-offset-1 alert alert-info">
												Alter student's classes and state(Active or Inactive)</div>



										</div>


										<div class="row">



											<div class="col-md-2" id="classDiv">
												<h6 for="classId">Class/Form:</h6>
												<select
													class="form-control formelement populateOptionsStudents"
													id="populateOptionsStudents"
													onchange="fetchStreamsStudents(this.value)">


												</select>
											</div>

											<div class="col-md-2" id="classDiv">
												<h6 for="classId">Stream:</h6>
												<select
													class="form-control formelement populateStreamOptionsStudents"
													name="streamId" id="streamId"
													onchange="fetchStudents(this.value)" required>

												</select>
											</div>

											<div class="col-md-1 pull-right">
												<br>

												<button class="form-control btn btn-primary" id="btn_shift">Submit

												</button>
											</div>

											<div class="col-md-2 pull-right">
												<h6 for="movestreamId">Stream:</h6>
												<select
													class="form-control formelement populateStreamOptions"
													name="streamId" id="movestreamId" required>

												</select>
											</div>

											<div class="col-md-2 col-md-offset-1 pull-right">
												<h6 for="classMoveList">Move to Class/Form:</h6>
												<select class="form-control formelement populateOptions"
													id="classList" onchange="fetchStreams(this.value)">


												</select>
											</div>






										</div>

										

										<br>

										<div class="table-responsive">
											<table class="table table-striped jambo_table bulk_action"
												id="studentsPerClass">
												<thead>
													<tr class="headings secondary-assent">

														<th class="column-title hidden">regStream</th>
														<th class="column-title hidden">currentStream</th>
														<th class="column-title hidden">isActive</th>
														<th class="column-title hidden">isAlumni</th>
														<th class="column-title hidden">isBoarding</th>
														<th class="column-title hidden">isGoKFeeEligibe</th>

														<th class="column-title">regNo</th>
														<th class="column-title">firstname</th>
														<th class="column-title">middlename</th>
														<th class="column-title">lastname</th>
														<th class="column-title">gender</th>
														<th class="column-title hidden">dob</th>
														<th class="column-title hidden">bcertNo</th>
														<th class="column-title hidden">county</th>
														<th class="column-title hidden">regTerm</th>
														<th class="column-title hidden">finalYear</th>
														<th class="column-title hidden">finalTerm</th>
														<th class="column-title hidden">passport</th>
														<th class="column-title hidden">lastUpdated</th>
														<th class="column-title hidden">admissionDate</th>
														<th class="column-title hidden">apiParentPrimary</th>

														<th class="column-title hidden">uuid</th>
														<th class="column-title hidden">accountId</th>

														<th class="column-title"><input type="checkbox"
															name="selectedStudents" id="selectedStudents"></th>

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

										<br> <br>





									</div>
									<div role="tabpanel" class="tab-pane fade" id="tab_content2"
										aria-labelledby="profile-tab">

										<!-- start subjects -->

										<br>



										<div class="row">

											<div class="col-md-6 col-md-offset-3 alert alert-info">
												Alter student's state(Active or Inactive)</div>



										</div>





										<div class="row">

											<div class="table-responsive">
												<table class="table table-striped jambo_table bulk_action"
													id="inactiveStudents">
													<thead>
														<tr class="headings secondary-assent">



															<th class="column-title">firstname</th>
															<th class="column-title">lastname</th>
															<th class="column-title">regno</th>
															<th class="column-title">streamDesc</th>

															<th class="column-title"><input type="checkbox"
																name="selectedStudents" id="selectedInactiveStudents"></th>


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



<!-- Students List Modal -->
<jsp:include page="modals/studentsListModal.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />


<script src="js/studentClass.js"></script>





