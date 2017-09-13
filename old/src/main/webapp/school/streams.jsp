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
	
	
%>
<jsp:include page="header.jsp" />


<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>Streams List</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">







						<div class="row ">

							<div class="col-md-4 pull-right">
								<h3 class="pull-right">
									Add a new Stream
									<button class="btn btn-primary" style="border-radius: 90%"
										id="add" onclick="streamModal(this.id)">
										<i class="fa fa-plus-circle fa-2x"></i>
									</button>

								</h3>

							</div>

						</div>




						<div class="row">
						<div class="col-md-4 col-md-offset-4">
						<h2><select class="form-control formelement populateOptions" onchange="fetchStreams(this.value)" id="classesList">
						
						<option>Form 1</option>
						
						<option>Form 2</option>
						
						<option>Form 3</option>
						
						<option>Form 4</option>
						
						
						</select></h2>
						</div>
						</div>

						<div class="col-md-6 col-md-offset-3 table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="streams">
								<thead>
									<tr class="headings secondary-assent">

										<th class="column-title">UUID</th>
										<th class="column-title">Class ID</th>
										<th class="column-title">Account ID</th>
										<th class="column-title">Description</th>
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
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->

<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />




<!-- stream Modal -->
<jsp:include page="modals/streamModals.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />
