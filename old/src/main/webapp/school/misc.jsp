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
				<h2>Misc List</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">
					
					<input type="hidden" name="accountId" id="accountId" value="<%=accountId%>">







						






						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="miscList">
								<thead>
									<tr class="headings secondary-assent">

										<th class="column-title">uuid</th>
										<th class="column-title">accountId</th>
										<th class="column-title">key</th>
										<th class="column-title">value</th>
										
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
<jsp:include page="modals/miscModals.html" />





<!-- footer -->
<jsp:include page="footer.jsp" />


<script src="js/apiCalls/miscApi.js"></script>


