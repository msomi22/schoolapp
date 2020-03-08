<%@page import="ke.co.qubintel.school.server.session.SessionConstants"%>

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
	//response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
	response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");


	String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID);
%>
<jsp:include page="header.jsp" />


<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>Class Teacher List</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">
					
					<input type="hidden" name="accountId" id="accountId" value="<%=accountId%>">







						<div class="row ">

							<div class="col-md-4 pull-right">
								<h3 class="pull-right">
									Add a new Class Teacher
									<button class="btn btn-primary" style="border-radius: 90%"
										id="add" onclick="ctModal(this.id)">
										<i class="fa fa-plus-circle fa-2x"></i>
									</button>

								</h3>

							</div>

						</div>






						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="ctList">
								<thead>
									<tr class="headings secondary-assent">

										<th class="column-title">uuid</th>
										<th class="column-title">accountId</th>
										<th class="column-title">staffId</th>
										<th class="column-title">staffName</th>
										<th class="column-title">staffNo</th>
										<th class="column-title">streamId</th>
										<th class="column-title">streamDesc</th>
										<th class="column-title">Modify</th>

									</tr>
								</thead>

								<tbody class='tablebody'>



									<!-- <tr class="tabledit" style='color: black;'>

										<td width="5%">1</td>
										<td class="center">P1</td>
										<td class="center">Paper 1</td>
										<td class="center">80</td>

										<td>

											<button class="btn btn-warning editExam" id="edit"
												onclick="examModal(this.id)">
												Edit <span class="fa fa-edit"></span>
											</button>
											<button class="btn btn-danger" id="P1"
												onclick="disableExam(this.id)">
												Disable <span class="fa fa-chain-broken"></span>
											</button>
										</td>

									</tr> -->


								</tbody>


							</table>

						</div>


























					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->


<!-- exam Modal -->
<jsp:include page="modals/ctModals.html" />





<!-- footer -->
<jsp:include page="footer.jsp" />


<script src="js/apiCalls/classTeacherApi.js"></script>


