<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Config -->
<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>


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

						<input type="hidden" name="accountId" id="accountId"
							value="<%=accountId%>"> <input type="hidden" name="year"
							id="year" value="<%=currentYear%>"> <input type="hidden"
							name="term" id="term" value="<%=currentTerm%>"> <input
							type="hidden" name="studentId" id="studentId" value="">


						<div class="row ">

							<div class="col-md-4 ">
								<button class="btn btn-info pull-left cards" id="genReceipt"
									onclick="generateReceipt()" disabled>Generate Receipt</button>
							</div>

							<div class="col-md-4">

								<div class="row">

									<div id="regNoInfo" class="col-md-7 col-md-offset-3 alert alert-info">
										<h5 id="regNoInfoSms">Enter a student's registration
											number to view school fees details.</h5>
									</div>


									<div id="regNoError" style="display: none" class="col-md-7 col-md-offset-3 alert">
										<h5 id="regNoErrorSms">Enter a student's registration
											number to view school fees details.</h5>
									</div>

								</div>



								<form method="get" action="">



									<div class="form-group has-feedback">
										<div class="row">
											<div class="col-md-1">
												<label for="regno">Reg N0_:</label>
											</div>
											<div class="col-md-11">
												<input type="text" id="regno" name="regno"
													class="form-control formelement cards"
													placeholder="Enter Reg number" pattern="[0-9]{3,4}"
													maxlength="4" onkeyup="delayInput()"
													title="Registration number, should contain numerics only and should be 4 numbers only"
													required> <span
													class="glyphicon glyphicon-search form-control-feedback"></span>
											</div>
										</div>
									</div>
								</form>



							</div>


							<div class="col-md-4">

								<button class="btn btn-primary pull-right cards"
									onclick="initPayment()">
									Make fee payment <i class="fa fa-plus-circle"></i>
								</button>

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
							<input type="hidden" id="boarder" name="boarder" value="">
							<input type="hidden" id="day" name="day" value="">

							<div class="col-md-11 col-md-offset-1 cards formelement">
								<div class="row secondary-assent">
									<h4 class="fee_header">Student's Info</h4>

								</div>
								<div id="studentsInfo">
									<div class="row">
										<div class="col-md-3">
											<h4 id="regNo">Reg No: ###</h4>
										</div>

										<div class="col-md-3">
											<h4 id="name">Name: ###</h4>
										</div>


										<div class="col-md-3">
											<h4 id="stream">Stream: ##</h4>
										</div>


										<div class="col-md-2">
											<h4 id="isBoarding">Boarding: ##</h4>
										</div>
										<!-- <div class="col-md-4">
										<h4 id="middlename">Middle Name: ######</h4>
									</div> -->
									</div>


									<!-- <div class="row">
									


									<div class="col-md-4">
										<h4 id="lastname">Last Name: ######</h4>
									</div>
								

								</div> -->







									<div class="row">
										<div class="col-md-4">
											<h4 class="pull-right" id="balance">
												<b>Balance: ######</b>
											</h4>
										</div>

										<div class="col-md-4">
											<h4 class="pull-right" id="termfee">
												<b>Term Fee: ######</b>



											</h4>
										</div>

									</div>





									<br> <br>

								</div>

							</div>

						</div>


						<div class="row">
							<br> <br>



							<div class="col-md-6 col-md-offset-1 cards formelement">


								<div id="showHistory">
									<br>
									<button class="btn btn-primary btn-block" id="btn_history"
										onclick="showHistory('history')" disabled>Show
										History</button>

								</div>


								<div id="history" style="display: none">

									<div class="row secondary-assent">
										<h4 class="fee_header">Student's Fee History</h4>

									</div>
									<div>
										<div class="row" id="feeHistory">
											<!-- <div class="col-md-4 col-md-offset-1">
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
										</div> -->


										</div>




									</div>

								</div>



							</div>






							<div class="col-md-4  col-md-offset-1 cards formelement">

								<div id="showOtherHistory">
									<br>
									<button class="btn btn-primary btn-block" id="btn_otherHistory"
										onclick="showHistory('other')" disabled>Show other
										History</button>

								</div>

								<div id="OtherHistory" style="display: none">



									<div class="row secondary-assent">
										<h6 class="fee_header">Other Student's Fee History</h6>

									</div>

									<div id="otherfeeHistory">
										<!-- <div class="row" >
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


									</div> -->



										<br>
									</div>





									<div class="row secondary-assent">
										<h6 class="fee_header">Reverted Student's Fee History</h6>

									</div>

									<div id="revertedFeeList">
										<!-- <div class="row">
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
 -->

									</div>


								</div>









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



<!-- fee payment Modal -->
<jsp:include page="modals/feePaymentModal.html" />



<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />


<!-- fee js -->
<script src="js/fee.js"></script>



<script>
	
</script>
