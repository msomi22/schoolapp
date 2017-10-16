<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>






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
	
	SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();

	SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

	int currentYear = Integer.parseInt(sysConfig.getYear());

	int currentTerm = Integer.parseInt(sysConfig.getTerm());

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
				<h3>Term and Misc Fee</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">


						<div class="col-md-10 col-sm-10 col-xs-12">

							<input type="hidden" id="passed_log_id" value="<%=loggedUserId%>">
							<input type="hidden" name="passedLogId" id="passedLogId"
								value="<%=loggedUserId%>"> <input type="hidden"
								name="passedLogAcessId" id="passedLogAcessId"
								value="<%=loggedUserAccessId%>"> <input type="hidden"
								name="accountId" id="accountId" value="<%=accountId%>">
								
								<input type="hidden" id="currentYear" value="<%= currentYear%>">
								<input type="hidden" id="currentTerm" value="<%= currentTerm%>">

							<div class="" role="tabpanel" data-example-id="togglable-tabs">
								<ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
									<li role="presentation" class="active"><a
										href="#tab_content1" id="home-tab" role="tab"
										data-toggle="tab" aria-expanded="true">Term Fee</a></li>
									<li role="presentation" class=""><a href="#tab_content2"
										role="tab" id="profile-tab" data-toggle="tab"
										aria-expanded="false">Damage and Other fee</a></li>
								</ul>
								<div id="myTabContent" class="tab-content">
									<div role="tabpanel" class="tab-pane fade active in"
										id="tab_content1" aria-labelledby="home-tab">

									

											<!-- start term fee-->
											<div class="messages">


												<div class="row">

													<div
														class="col-md-6 col-md-offset-3 alert alert-info secondary-assent" id="yearlyFee">
														Alter Term fee</div>



												</div>

												<div class="row ">
													<div class="col-md-3 pull-right"></div>

													<div class="col-md-4 pull-right">
														<h3 class="pull-right">
															Add new Term Fee
															<button class="btn btn-primary"
																style="border-radius: 90%" id="add"
																onclick="termModal(this.id)">
																<i class="fa fa-plus-circle fa-2x"></i>
															</button>

														</h3>

													</div>

												</div>
												<div class="row ">
													<div class="col-md-6 col-md-offset-3">

														<div class="row">
														
														<form action="post" action="#" id="yearFee">

															<div class="col-md-1 col-md-offset-2">
																<label for="year">Year:</label>
															</div>
															<div class="col-md-4">

																<div class="input-group">
																	<input type="text" id="inityear" name="inityear"
																		class="form-control formelement cards c_year yearConfig"
																		placeholder="Enter the Year" pattern="[0-9]{4}"
																		maxlength="4" onchange=""
																		title="Year, should contain numerics only and should be 4 numbers only"required="required">
																	<div class="input-group-btn">
																		<button class="btn btn-primary" type="button" onclick="feeTermFeeList()">
																			<i class="glyphicon glyphicon-search"></i>
																		</button>
																	</div>

																</div>
															</div>
															
															</form>
															
															
														</div>

													</div>




													<div class="row">
														<br>

														<div class="table-responsive col-md-6 col-md-offset-3">
															<table
																class="table table-striped jambo_table bulk_action"
																id="yearlyTermFeeList">
																<thead>
																	<tr class="headings secondary-assent">

																		<th class="column-title hidden">uuid</th>
																		<th class="column-title hidden">accountId</th>
																		<th class="column-title">boaderAmount</th>
																		<th class="column-title">dayAmount</th>
																		<th class="column-title">term</th>
																		<th class="column-title">year</th>
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

												</div>
												<!-- end general info -->
									

									</div>
									
									</div>
									<div role="tabpanel" class="tab-pane fade" id="tab_content2"
										aria-labelledby="profile-tab">

										<!-- start subjects -->

										<br>



										<div class="row">

											<div id="otherFeeSms"
												class="col-md-6 col-md-offset-3 alert alert-info secondary-assent">
												Alter Other fee</div>



										</div>

										<div class="row ">
											<div class="col-md-3 pull-right"></div>

											<div class="col-md-4 pull-right">
												<h3 class="pull-right">
													Add another Fee
													<button class="btn btn-primary cards"
														style="border-radius: 90%" id="add_other"
														onclick="othertermModal(this.id)">
														<i class="fa fa-plus-circle fa-2x"></i>
													</button>

												</h3>

											</div>

										</div>

										<div class="col-md-6 col-md-offset-3">

											<form method="post" id="otherFeeQuery" action="#">

												<div class="row">

													<div class="col-md-1">
														<label for="regno">Year:</label>
													</div>
													<div class="col-md-3">
														<input type="text" id="termyear" name="termyear"
															class="form-control formelement cards c_year yearConfig"
															placeholder="Enter the Year" pattern="[0-9]{4}"
															maxlength="4"
															title="Year, should contain numerics only and should be 4 numbers only"
															required>
													</div>

													<div class="col-md-1 ">
														<label for="term">Term:</label>


													</div>
													<div class="col-md-3">
														<select class="form-control formelement cards" id="term" name="term" required>
															<option>1</option>
															<option>2</option>

															<option>3</option>
														</select>
													</div>

													<div class="col-md-2">
														<input type="button"
															class=" form-control btn btn-primary cards" onclick="otherFeeTermFeeList()"
															value="Submit">
													</div>


												</div>
											</form>

											<br>
										</div>



										<div class="row">
											<br> <br>

											<div class="table-responsive col-md-6 col-md-offset-3">
												<table class="table table-striped jambo_table bulk_action"
													id="otherFeeList">
													<thead>
														<tr class="headings secondary-assent">

															<th class="column-title hidden">uuid</th>
															<th class="column-title hidden">accountId</th>
															<th class="column-title">description</th>
															<th class="column-title">amount</th>
															<th class="column-title">term</th>
															<th class="column-title">year</th>
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


<!-- term fee Modal -->
<jsp:include page="modals/termFeeModals.html" />

<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />

<!-- term js -->

<script src="js/term.js"></script>







