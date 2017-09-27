<!-- Add a new/edit staff -->
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="staff" class="modal modal-info fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content alert alert-info">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="staffTiltle">Add a new Staff</h4>
			</div>

			<form method="post" action="#" id="staffForm">
				<div class="modal-body">

					<div class="box-body">

						<div class="row">


							<input type="hidden" name="acessLevelId" id="acessLevelId"
								value="BDF7F33D-1936-43F3-B14B-8FC3EA3A1265">

							<div class="col-md-8 col-md-offset-2">
								<label for="staffNo">Staff Number:</label> <input type="text"
									id="staffNo" class="form-control formelement" name="staffNo"
									placeholder="Staff number" pattern="[0-9]{4}"
									title="Staff Number,enter a valid number, exactly four digits e.g 1234, 4567">

							</div>

							<div class="col-md-8 col-md-offset-2">
								<label for="fname">First Name</label> <input type="text"
									id="fname" class="form-control formelement" name="firstname"
									placeholder="First Name" pattern="[A-Za-z]{3,20}"
									title="First Name,Only characters are allowed and should be more than two and less than 20 characters"
									required>
							</div>

							<div class="col-md-8 col-md-offset-2">
								<label for="mname">Middle Name</label> <input type="text"
									id="mname" name="middlename" class="form-control formelement"
									placeholder="Middle Name" pattern="[A-Za-z]{3,20}"
									title="Middle Name,Only characters are allowed and should be less than 20 characters ">
							</div>


							<div class="col-md-8 col-md-offset-2">
								<label for="lname">Last Name</label> <input type="text"
									id="lname" name="lastname" class="form-control formelement"
									placeholder="Last Name" pattern="[A-Za-z]{3,20}"
									title="Last Name,Only characters are allowed and should be more than two and less than 20 characters"
									required>
							</div>

							<div class="col-md-8 col-md-offset-2">
								<label for="gender">Gender</label> <select id="gender"
									name="gender" class="form-control formelement">
									<option value="M">Male</option>
									<option value="F">Female</option>
								</select>
							</div>

							<div class="col-md-8 col-md-offset-2">
								<label for="email">Email</label> <input type="email" id="email"
									class="form-control formelement" name="email">

							</div>

							<div class="col-md-8 col-md-offset-2">
								<label for="phone">Phone Number</label> <input type="text"
									id="phone" class="form-control formelement" name="mobile"
									placeholder="Phone number" pattern="[0-9]{9}"
									title="Phone,enter a valid number e.g 712345678">

							</div>



							<div class="col-md-8 col-md-offset-2">

								<br> <br>

								<hr></hr>

								<label for="username">User name</label> <input type="text"
									id="username" class="form-control formelement" name="username"
									placeholder="Username" pattern="[A-Za-z0-9]{3,20}"
									title="Username, Alpha numeric characters are allowed and should be more than two and less than 20 characters"
									required>
							</div>


							<div class="col-md-8 col-md-offset-2">
								<label for="password">Password</label> <input type="password"
									id="password" class="form-control formelement" name="password"
									placeholder="Username" pattern="[A-Za-z0-9]{6,20}"
									title="Password, Alpha numeric characters are allowed and should be more than six characters"
									required>
							</div>





							<%
								String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID);
							%>
							
							<input type="hidden" name="uuid" id="uuid"
								value="n0t_set">

							<input type="hidden" name="accountId" id="accountId"
								value="<%=accountId%>">



						</div>






					</div>
					<!-- /.box-body -->



				</div>
				<div class="modal-footer">
					<button type="submit" onclick="StaffApiCall()"
						class="btn btn-info pull-right" id="staff_btn">
						Submit <i class="fa fa-save"></i>
					</button>
					<button type="button" class="btn btn-default pull-left"
						data-dismiss="modal">Close</button>
				</div>

			</form>
		</div>

	</div>
</div>




<div id="staffSujectModal" class="modal modal-info fade" role="dialog">
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content alert alert-info">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="streamTiltle">Assign A new Subject to a staff</h4>
			</div>

			<form method="post" action="#" id="editStreamForm">
				<div class="modal-body">

					<div class="box-body">

						<div class="row">



							<div class="col-md-8 col-md-offset-2" id="classDiv">
								<br> <label for="classId">Class/Form:</label> <select
									class="form-control formelement populateOptions classId"  name="classRoomId" id="classId_edit">

									<option value="">Form 1</option>

									<option value="">Form 2</option>
									<option value="">Form 3</option>

									<option value="">Form 4</option>


								</select>
							</div>
							
							
							<div class="col-md-8 col-md-offset-2" id="classDiv">
								<br> <label for="classId">Stream:</label> <select
									class="form-control formelement populateOptions classId"  name="classRoomId" id="classId_edit">

									<option value="">Form 1 N</option>

									<option value="">Form 2 S</option>
									<option value="">Form 3 S</option>

									<option value="">Form 4 N</option>


								</select>
							</div>



							
							
							
								<div class="col-md-8 col-md-offset-2" id="classDiv">
								<br> <label for="classId">Subject:</label> <select
									class="form-control formelement populateOptions classId"  name="classRoomId" id="classId_edit">

									<option value="">Mathematics</option>

									<option value="">English</option>
									<option value="">Kiswahili</option>

									<option value="">Physics</option>


								</select>
							</div>
							
							
							
							<input type="hidden" name="uuid" id="uuid"
								value="n0t_set">
								
								<input type="hidden" name="accountId" id="accountId"
								value="n0t_set">









						</div>

						<input type="hidden" name="" id="" value="not_set"> <input
							type="hidden" name="" id="" value="not_set">





					</div>
					<!-- /.box-body -->



				</div>
				<div class="modal-footer">
				<!-- 	<button type="submit" style="display:none" onclick="addNewStream()" class="btn btn-info pull-right" id="stream_btn_add">
						Submit <i class="fa fa-save"></i>
					</button> -->
					
					
					
					
					<button type="button" onclick="alterStaffSubject()" class="btn btn-info pull-right" id="stream_btn_update">
						Submit <i class="fa fa-save"></i>
					</button>
					
					
					<button type="button" class="btn btn-default pull-left"
						data-dismiss="modal">Close</button>
				</div>

			</form>
		</div>

	</div>
</div>



