<%@page import="ke.co.qubintel.school.server.session.SessionConstants"%>

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
	//response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
	response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");

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
				<h3>Students and Houses</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">
						<div class="col-md-12 col-sm-12 col-xs-12">



							<input type="hidden" name="passedLogId" id="passedLogId"
								value="<%=loggedUserId%>"> <input type="hidden"
								name="passedLogAcessId" id="passedLogAcessId"
								value="<%=loggedUserAccessId%>"> <input type="hidden"
								name="accountId" id="accountId" value="<%=accountId%>">

							<div class="">

								<div id="" class="">
									<div role="tabpanel" class="tab-pane fade active in"
										id="tab_content1" aria-labelledby="home-tab">

										<br>

										<div class="row">


											<div class="col-md-3">


												<button class="btn btn-primary secondary-assent  cards"
													onclick="studentsListModa('studentPerStream')" disabled>
													Export <i class="fa fa-file-pdf-o"> </i>
												</button>

												<button
													class="btn btn-primary secondary-assent  cards pull-right"
													onclick="studentsListModa('studentPerStreamExcel')"
													disabled>
													Export <i class="fa fa-file-excel-o"> </i>
												</button>
											</div>

											<div class="col-md-5 col-md-offset-1 alert alert-info">
												Alter student's houses</div>


											<div class="col-md-2 col-md-offset-1"></div>



										</div>


										<div class="row">

											<form action="#" method="post" id="shiftForm">



												<div class="col-md-4" id="classDiv">
													<h6 for="classId">House:</h6>
													<select class="form-control formelement populateHouses"
														onchange="fetchHouseStudents(this.value)"
														required="required">


													</select>
												</div>





												<div class="col-md-4 col-md-offset-2 pull-right">
													<h6 for="movestreamId">House:</h6>
													<select class="form-control formelement populateHouses"
														name="streamId" id="movestreamId" required>

													</select>
												</div>


												<div class="col-md-3">
													<br>

													<button class="form-control btn btn-primary" type="button"
														id="btn_shift" onclick="initShiftHouse()">Shift</button>
												</div>






											</form>

										</div>



										<br>

										<div class="row">

											<div class="table-responsive">
												<table class="table table-striped jambo_table bulk_action"
													id="studentsPerClass">
													<thead>
														<tr class="headings secondary-assent">

															<th class="column-title hidden">studentId</th>
															<th class="column-title hidden">houseId</th>
															<th class="column-title hidden">dateOut</th>
															<th class="column-title hidden">dateIn</th>

															<th class="column-title">studentName</th>
															
															<th class="column-title">regNo</th>
															<th class="column-title">houseName</th>
															<th class="column-title">lastname</th>
															

															<th class="column-title hidden">uuid</th>
															<th class="column-title hidden">accountId</th>

															<th class="column-title"><input type="checkbox"
																id="selectCurrentStream" onclick="selectCurrentStream()"></th>

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

										<br> <br>





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





