<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


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
%>
<jsp:include page="header.jsp" />


<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Settings</h3>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">

						<input type="hidden" id="accountId" value="<%=accountId%>">



						<div class="row">



							<div class="col-md-8 col-md-offset-2">
								<br>
								<form action="#" method="post" id="yearTerm">


									<div class="row">

										<div class="col-md-1 col-md-offset-1">
											<label for="term">Term:</label>
										</div>

										<div class="col-md-3">

											<select class="form-control formelement cards" id="term"
												name="term" required>
												<option>1</option>
												<option>2</option>

												<option>3</option>
											</select>
										</div>



										<div class="col-md-1">
											<label for="desc">Year:</label>

										</div>

										<div class="col-md-3">

											<input type="text" name="year" id="year"
												class="form-control formelement cards c_year yearConfig"
												placeholder="Enter the Year" pattern="[0-9]{4}"
												maxlength="4"
												title="Year, should contain numerics only and should be 4 numbers only"
												required> <br>
										</div>

										<div class="col-md-2">

											<input type="hidden" name="uuid"> <input
												type="hidden" name="examId"> <input type="hidden"
												name="cansendSMS" value="0"> <input type="hidden"
												name="accountId" value="<%=accountId%>"> <input
												type="button" name="set_btn" id="set_btn"
												onclick="updateYearTerm()"
												class="btn btn-primary btn-block cards" value="Submit">
											<br>
										</div>

									</div>

								</form>

							</div>

						</div>


						<div class="row">

							<div class="col-md-8 col-md-offset-2">
								<br>

								<hr class="hr_list">
								<br>

								<div class="row">
									<div class="col-md-4 col-md-offset-4">

										<h3 class="centerMe">Grading System</h3>

										<hr class="hr_list">
										<br>

									</div>
								</div>

								<div class="row ">

									<div class="col-md-4 pull-right">


										<button class="btn btn-primary pull-right cards" id="add"
											onclick="gradingModal(this.id)">
											Add a new Scale <i class="fa fa-plus-circle"></i>
										</button>

									</div>







									<div class="col-md-2 col-md-offset-2">


										<label for="category">Category:</label>
									</div>

									<div class="col-md-4">

										<select class="form-control formelement cards" id="category"
											name="category" onchange="fetchGradingScale(this.value)"
											required>
											<option>General</option>
											<option>...</option>
											<option>...</option>
										</select>
									</div>




								</div>
							</div>
						</div>



						<div class="row">
							<br>

							<div class="col-md-8 col-md-offset-2" id="">
								<div class="table-responsive">
									<table class="table table-striped jambo_table bulk_action"
										id="gradingSystem">
										<thead>
											<tr class="headings secondary-assent">

												<th class="column-title hidden">uuid</th>
												<th class="column-title hidden">categoryId</th>
												<th class="column-title ">lowerLimit</th>
												<th class="column-title">upperLimit</th>
												<th class="column-title">description</th>
												<th class="column-title">points</th>
												<th class="column-title">Modify</th>

											</tr>
										</thead>

										<tbody class='tablebody'>

										</tbody>


									</table>

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
<jsp:include page="modals/gradingModal.html" />

<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />


<!-- footer -->
<jsp:include page="footer.jsp" />


<script src="js/settings.js"></script>
