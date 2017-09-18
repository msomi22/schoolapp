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
				<h2>Student's School Fee</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">





						<!-- <h1>Test the Api call via jquery</h1>


						<button class="btn btn-primary btn-block" onclick="StaffApiCall()">
							Test API call</button> -->


						<div class="row ">

							<div class="col-md-4 col-md-offset-4">


								<form method="get" action="">



									<div class="form-group has-feedback">
										<h3 class="">
											<label for="regno">Enter Reg N0_:</label> <input type="text"
												id="regno" name="regno"
												class="form-control formelement cards"
												placeholder="Registration number" pattern="[0-9]{3,4}"
												maxlength="4"
												title="Registration number, should contain numerics only and should be 4 numbers only"
												required> <span
												class="glyphicon glyphicon-search form-control-feedback"></span>
										</h3>
									</div>
								</form>



							</div>

						</div>


						<br> <br> <br> <br>

						<div class="row">


							<!-- 	private String studentId;
	private int amountPaid;
	private String payMode;
	private String transactionId;
	private String paidHas;//boarders = 1, day = 0
	private String termPiad;
	private String yearPaid;
	private Timestamp datePaid; -->

							<div class="col-md-11 col-md-offset-1 cards formelement">
								<div class="row secondary-assent">
									Studen't Info <br> <br>

								</div>
								<div class="row">
									<div class="col-md-4">
										<h4 id="regNo">Reg No: ######</h4>
									</div>

									<div class="col-md-4">
										<h4 id="firstname" >First Name: ######</h4>
									</div>
									<div class="col-md-4">
										<h4 id="middlename">Middle Name: ######</h4>
									</div>
								</div>


								<div class="row">
									


									<div class="col-md-4">
										<h4 id="lastname">Last Name: ######</h4>
									</div>
								
									<div class="col-md-4">
										<h4 id="stream">Stream: ######</h4>
									</div>


									<div class="col-md-4">
										<h4 id="isBoarding">Boarding: ######</h4>
									</div>

								</div>




								


								<div class="row">
									<div class="col-md-6">
										<h4 class="pull-right" id="balance">
											<b>Balance: ######</b>
										</h4>
									</div>

								</div>





								<br> <br>

							</div>

						</div>


						<div class="row">
							<br> <br>



							<div class="col-md-6 col-md-offset-1 cards formelement">
								<div class="row secondary-assent">
									Student's Fee History <br> <br>

								</div>
								<div class="scale">
									<div class="row">
										<div class="col-md-4 col-md-offset-1">
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Transaction ID: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Year Paid: ######</h6>
											<h6>Date Paid: ######</h6>
											<hr class="hr_list">
										</div>



										<div class="col-md-4 col-md-offset-1">
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Transaction ID: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Year Paid: ######</h6>
											<h6>Date Paid: ######</h6>

											<hr class="hr_list">
										</div>



										<div class="col-md-4 col-md-offset-1">
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Transaction ID: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Year Paid: ######</h6>
											<h6>Date Paid: ######</h6>

											<hr class="hr_list">
										</div>

										<div class="col-md-4 col-md-offset-1">
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Amount Paid: ######</h6>
											<h6>Payment Mode: ######</h6>
											<h6>Transaction ID: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Year Paid: ######</h6>
											<h6>Date Paid: ######</h6>

											<hr class="hr_list">
										</div>


									</div>




								</div>



							</div>



							<!--  	<div class="col-md-4 col-md-offset-1 cards formelement">
							<div class="row secondary-assent">Studen't Info
							<br>
							<br>
							
							</div>
								<div class="row">
									<div class="col-md-6">
										<h3>Amount Paid:</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">######</h3>
									</div>
								</div>


								<div class="row">
									<div class="col-md-6">
										<h3>Payment Mode:</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">######</h3>
									</div>
								</div>


								<div class="row">
									<div class="col-md-6">
										<h3>Transaction ID:</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">######</h3>
									</div>

								</div>


								<div class="row">
									<div class="col-md-6">
										<h3>Term Paid:</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">######</h3>
									</div>
								</div>


								<div class="row">
									<div class="col-md-6">
										<h3>Date Paid:</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">######</h3>
									</div>

								</div>




								<br> <br>


								<div class="row">
									<div class="col-md-6">
										<h3>
											<b>Balance:</b>
										</h3>
									</div>

									<div class="col-md-6">
										<h3 class="pull-right">
											<b> ######</b>
										</h3>
									</div>

								</div>







							</div>
 -->




							<div class="col-md-4  col-md-offset-1 cards formelement">
							
							<div id="showOtherHistory">
							<br>
							<button class="btn btn-primary btn-block" onclick="showOtherHistory()">Show other History</button>
							
							</div>
							
							<div id="OtherHistory" style="display: none">



								<div class="row secondary-assent">
									Other Student's Fee History <br> <br>

								</div>

								<div class="scale">
									<div class="row">
										<div class="col-md-11 col-md-offset-1">
											<h6>Description: ######</h6>
											<h6>Amount: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Date Paid: ######</h6>
											<hr class="hr_list">
										</div>


									</div>

									<div class="row">
										<div class="col-md-11 col-md-offset-1">
											<h6>Description: ######</h6>
											<h6>Amount: ######</h6>
											<h6>Term Paid: ######</h6>
											<h6>Date Paid: ######</h6>
											<hr class="hr_list">
										</div>


									</div>



									<br> 
								</div>





								<div class="row secondary-assent">
									Reverted Student's Fee History <br> <br>

								</div>

								<div class="scale">
									<div class="row">
										<div class="col-md-11 col-md-offset-1">
											<h6>Description: ######</h6>
											<h6>Amount: ######</h6>
											<h6>Date Reverted: ######</h6>
											<hr class="hr_list">
										</div>
									</div>

									<div class="row">
										<div class="col-md-11 col-md-offset-1">
											<h6>Description: ######</h6>
											<h6>Amount: ######</h6>
											<h6>Date Reverted: ######</h6>
											<hr class="hr_list">
										</div>
									</div>


								</div>


							</div>







							<br> <br>
							
							</div>

						</div>

						<br> <br>






















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


<!-- fee js -->
<script src="js/fee.js"></script>



<script>
	
</script>
