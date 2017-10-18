<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>


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

	SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();

	SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

	int currentYear = Integer.parseInt(sysConfig.getYear());

	int currentTerm = Integer.parseInt(sysConfig.getTerm());
%>
<jsp:include page="header.jsp" />




<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h3>Result Generation Window</h3>
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
										<h2>
											Government Education Fund<small> Click allocate when
												done</small>
										</h2>
										<ul class="nav navbar-right panel_toolbox">
											<li><a class="collapse-link"><i
													class="fa fa-chevron-up"></i></a></li>
										</ul>
										<div class="clearfix"></div>
									</div>
									<div class="x_content">


										<div class="row ">
										
										<div class="col-md-3">
												<h3 class="pull-right">
												
													<button type="button" class="btn btn-primary cards"
														 id="govt_temp"
														onclick="govtCategoryTemplateModal()">Use a Template
														<i class="fa fa-file-o"></i>
													</button>

												</h3>

											</div>
										
										<div class="col-md-1 pull-right"></div>

											<div class="col-md-3 pull-right">
												<h3 class="pull-right">
													Add a new Category
													<button type="button" class="btn btn-primary cards"
														style="border-radius: 90%" id="add"
														onclick="govtCategoryModal()">
														<i class="fa fa-plus-circle fa-2x"></i>
													</button>

												</h3>

											</div>

										</div>


										<form action="#" id="generateGokeAllocation" method="post">






											<!-- Choose time span -->

											<input type="hidden" name="loggedId" id="loggedId"
												value="<%=accountId%>">







											<div class="row">


												<div class="col-md-7 col-md-offset-2">



													<div class="row">

														<div class="col-md-4 col-md-offset-2">

															<!-- Exam element -->



															<h4 class="centerMe">
																<b>Total Amount</b>
															</h4>

															<input type="text" class="form-control formelement"
																name="totalAmount" id="totalAmount" onkeyup="monitorTotalAmount()"
																placeholder="Enter Total Amount"> <br>


														</div>
														<div class="col-md-1">
															<br>
															<br>

															<button type="button" class="btn btn-primary my_btn"
																id="p_totalAmount"
																onclick="processTotalAmount()"
																style="border-radius: 90%">
																<span class="fa fa-floppy-o"></span>
															</button>

														</div>





													</div>

													<div id="govtCatList">

														<div class="row">




															<div class="col-md-2">

																<input type="text" placeholder="Enter Category Name"
																	class="form-control formelement cat1" name="catName"
																	value="R.M.I" readonly="readonly">
															</div>

															<input type="hidden" name="catuuid" class="cat1"
																value="memeUUID">

															<div class="col-md-4">
																<input type="text" placeholder="Enter Category Amount"
																	class="form-control formelement cat1" name="catAmount"
																	value="7777" readonly="readonly">
															</div>

															<div class="col-md-1">

																<button type="button" class="btn btn-primary my_btn"
																	id="cat1" onclick="editGovtCat(this.id)"
																	style="border-radius: 90%">
																	<span class="fa fa-pencil-square-o "></span>
																</button>

															</div>
															<div class="col-md-1">

																<button type="button" class="btn btn-primary my_btn"
																	style="border-radius: 90%">
																	<span class="fa fa-trash"></span>
																</button>

															</div>

														</div>


														<div class="row">




															<div class="col-md-2">

																<input type="text" id="catId"
																	placeholder="Enter Category Name"
																	class="form-control formelement cat2" name="catName"
																	value="Another" readonly="readonly">
															</div>

															<input type="hidden" name="catuuid" class="cat2"
																value="memeUUID111">

															<div class="col-md-4">
																<input type="text" id="catAmount"
																	placeholder="Enter R.M.I Amount"
																	class="form-control formelement cat2" name="catAmount"
																	value="111" readonly="readonly">
															</div>

															<div class="col-md-1">

																<button type="button" class="btn btn-primary my_btn"
																	id="cat2" onclick="editGovtCat(this.id)"
																	style="border-radius: 90%">
																	<span class="fa fa-pencil-square-o "></span>
																</button>

															</div>
															<div class="col-md-1">

																<button type="button" class="btn btn-primary my_btn"
																	style="border-radius: 90%">
																	<span class="fa fa-trash"></span>
																</button>

															</div>

														</div>
													</div>
												</div>


												<div class="col-md-2 ">
												<br>
												
												<br>
												<br>
												
												
												
												<h5 class="alert alert-info" id="govtCheckResponse">Display the amount check here</h5>
												
												
												
												</div>



											</div>




											<div class="row">

												<div class="col-md-8 col-md-offset-2">

													<br>

													<div class="row">




														<div class="col-md-4 col-md-offset-2 alert alert-info">
															<h4 id="amountPerStudent">The Amount to be allocated to each student is
																####</h4>
														</div>

													</div>


													<div class="row">
														<br> <br>

														<div class="col-md-4 col-md-offset-2">

															<button type="button"
																class="btn btn-primary form-control" id="allocateGovtMoney" onclick="allocateGovtCash()" disabled>Allocate</button>
														</div>

													</div>
												</div>


											</div>












											<!-- footer of the form elements: Back,Reset and generate -->






										</form>


									</div>





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


<!-- Govt fund modal -->

<jsp:include page="modals/govtCategoryModal.html" />

<!-- Govt fund template modal -->

<jsp:include page="modals/govtCategoryTemplateModal.html" />


<!-- footer -->


<jsp:include page="footer.jsp" />



<script src="js/govtFund.js"></script>




