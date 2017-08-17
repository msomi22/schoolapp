<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Stream"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.ClassDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>


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

	//get exams list

	ExamDAO examDAO = ExamDAO.getInstance();
	StudentDAO studentDAO = StudentDAO.getInstance();

	List<Exam> examList = new ArrayList<>();

	examList = examDAO.getExamList(accountId);

	//get class list
	ClassDAO classDAO = ClassDAO.getInstance();

	List<ClassRoom> classroomList = new ArrayList<>();

	classroomList = classDAO.getClassRooms(accountId);

	//get stream list
	StreamDAO streamDAO = StreamDAO.getInstance();

	List<Stream> streamList = new ArrayList<>();

	streamList = streamDAO.getStreamList(accountId);

	//get the current year
	/*  int current= 0;
	 
	  GregorianCalendar cal = new GregorianCalendar();
	  current=cal.get(Calendar.YEAR); */

	SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();

	SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);

	int currentYear = Integer.parseInt(sysConfig.getYear());

	int currentTerm = Integer.parseInt(sysConfig.getTerm());
%>
<jsp:include page="header.jsp" />
<!-- Custom report style -->
<link rel="stylesheet"
	href="css/normalize.min.css">
<link rel='stylesheet prefetch'
	href='css/roboto.css'>
<link rel='stylesheet prefetch'
	href='../vendors/font-awesome/css/font-awesome.min.css'>
<link rel="stylesheet" href="css/customReportStyle.css">




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
											Customize Exam Report display<small> Click Generate
												when done</small>
										</h2>
										<ul class="nav navbar-right panel_toolbox">
											<li><a class="collapse-link"><i
													class="fa fa-chevron-up"></i></a></li>
										</ul>
										<div class="clearfix"></div>
									</div>
									<div class="x_content">
										<br />


										<form action="studentReportCard" id="generateReport"
											class="col-md-6 col-md-offset-3" method="post"
											target="_blank">


											<div class="row">

												<div class="col-md-6 col-md-offset-3">
													<%--    <%=accountId %>
                    
                    <%=examList %>
                      <%
                  for(Exam exam : examList){                   
                    %>
                    
                    <%=exam.getDescription() %>
                    
                    <%} %>
                    
                     --%>


													<%--   <c:out value="${accountId}"></c:out>
                    
                    <h4>Exam</h4>
                    
                     <c:forEach var = "i" begin = "1" end = "5">
         Item <c:out value = "${examList}"/><p>
      </c:forEach>
                    
                     <c:forEach  var="Exam" items="${examList}">
                    
                   <h1>hi there</h1>
                    
                    </c:forEach> --%>

													<!-- Exam element -->

												

													<h4>Exam</h4>

													<select id="exam" name="exam"
														onblur="validateExamSelected()"
														class="form-control formelement SlectBox"
														required="required" multiple>

														<%
															if (examList != null) {

																for (Exam exam : examList) {
														%>

														<option value="<%=exam.getUuid()%>">
															<%=exam.getDescription()%></option>

														<%
															}
															} else {
														%>
														<option value="">...</option>

														<%
															}
														%>

													</select>

												</div>

												<input type="hidden" name="examType" id="examType"
													value="others">



											</div>




											<!-- Choose time span -->





											<h4>Year:</h4>

											<div class="row">

												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="current" class="form-control"
														name="year" value="<%=currentYear%>" checked> <label
														for="current">
														<h6>
															Current
															<%=currentYear%></h6>


													</label>
												</div>

												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="current-1" name="year"
														value="<%=currentYear - 1%>"> <label
														for="current-1">
														<h6>
															Previous
															<%=currentYear - 1%></h6>


													</label>
												</div>


												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="current-2" name="year"
														value="<%=currentYear - 2%>"> <label
														for="current-2">
														<h6>
															2 years ago
															<%=currentYear - 2%></h6>


													</label>
												</div>

											</div>

											<h4>Term:</h4>

											<div class="row">
												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="term1" name="term" value="1"
														<%if (currentTerm == 1) {%> checked <%}%>> <label
														for="term1">
														<h6>Term 1</h6>
													</label>
												</div>

												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="term2" name="term" value="2"
														<%if (currentTerm == 2) {%> checked <%}%>> <label
														for="term2">
														<h6>Term 2</h6>
													</label>
												</div>


												<div class="col-md-3 col-md-offset-1">
													<input type="radio" id="term3" name="term" value="3"
														<%if (currentTerm == 3) {%> checked <%}%>> <label
														for="term3">
														<h6>Term 3</h6>
													</label>
												</div>


											</div>

											<br> <br>

											<!-- Scope element -->
											<h4>Scope:</h4>

											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="class" class="form-control"
														name="scope" value="true" onclick="scopeSwap(this.id)"
														checked> <label for="class">
														<h6>Class</h6>

													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="stream" name="scope" value="false"
														onclick="scopeSwap(this.id)"> <label for="stream">
														<h6>Stream</h6>

													</label>
												</div>

											</div>


											<div class="row" id="classScope">

												<div class="col-md-5 col-md-offset-1">


													<h4>Class</h4>

													<select class="form-control formelement" name="classroom"
														required>


														<%
															if (classroomList != null) {
																for (ClassRoom classroom : classroomList) {
														%>

														<option value="<%=classroom.getUuid()%>">
															<%=classroom.getDescription()%></option>

														<%
															}
															} else {
														%>
														<option value="">...</option>
														<%
															}
														%>
													</select>



												</div>

											</div>

											<div class="row" id="streamScope" style="display: none">


												<div class="col-md-5 col-md-offset-1">

													<h4>Stream</h4>

													<select class="form-control formelement" name="stream"
														required>



														<%
															int studentsCount = 0;
															if (streamList != null) {
																for (Stream stream : streamList) {

																	studentsCount = studentDAO.classStudentCount(accountId, stream.getUuid(), "1");
														%>

														<option value="<%=stream.getUuid()%>">
															<%=stream.getDescription() + " (" + studentsCount + ")"%></option>

														<%
															}
															}

															else {
														%>
														<option value="">...</option>

														<%
															}
														%>


													</select>

												</div>


											</div>

											<!-- Hide points or grades element -->

											<br> <br>

											<h4>HIDE Points:</h4>

											<div class="row">

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="pointshide" class="form-control"
														name="p" value="true"> <label for="pointshide">
														<h6>Yes</h6>

													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="points" name="p" value="false"
														checked> <label for="points">
														<h6>No</h6>

													</label>
												</div>

											</div>

											<h4>HIDE Grades:</h4>

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="gradeshide" name="g" value="true">
													<label for="gradeshide">
														<h6>YES</h6>
													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="grades" name="g" value="false"
														checked> <label for="grades">
														<h6>NO</h6>
													</label>
												</div>


											</div>


											<br> <br>


											<!-- Show fee element -->

											<h4>Show Fee INFO:</h4>

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="fee" name="fee" value="true"
														checked> <label for="fee">
														<h6>YES</h6>
													</label>
												</div>

												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="feehidden" name="fee" value="false">
													<label for="feehidden">
														<h6>NO</h6>
													</label>
												</div>

											</div>





											<br> <br>

											<!-- Rank element -->


											<h4>RANK:</h4>

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="pointsrank" name="rank"
														value="points" checked> <label for="pointsrank">
														<h6>Rank with points</h6>

													</label>
												</div>
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="gradesrank" name="rank"
														value="marks"> <label for="gradesrank">
														<h6>Rank with total marks</h6>

													</label>
												</div>

											</div>


											<!-- Number of subject element -->

											<h4>No_ of Subjects:</h4>

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="7sub" name="subjects" value="seven"
														checked> <label for="7sub">
														<h6>Grade 7 subjects</h6>

													</label>
												</div>
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="11sub" name="subjects"
														value="eleven"> <label for="11sub">
														<h6>Grade 11 subjects</h6>

													</label>
												</div>

											</div>

											<br> <br>

											<!-- Type of report element -->
											<h4>Type of Report:</h4>

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="reportcard" name="reportcard"
														onclick="redirect(this.id)" value="reportcard" checked>
													<label for="reportcard">
														<h6>Report Card</h6>

													</label>
												</div>
												<div class="col-md-5 col-md-offset-1">
													<input type="radio" id="ranklist" name="reportcard"
														value="ranklist" onclick="redirect(this.id)"> <label
														for="ranklist">
														<h6>Rank List</h6>

													</label>
												</div>

											</div>




											<br> <br>

											<!-- footer of the form elements: Back,Reset and generate -->

											<div class="row">
												<div class="col-md-5 col-md-offset-1">
													<button class="btn btn-primary">Back</button>
													<button type="reset" class="btn btn-primary">Reset</button>
												</div>



												<div class="col-md-2 col-md-offset-4">
													<button type="submit" class="btn btn-primary">Generate</button>
												</div>


											</div>


										</form>



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
<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->


<jsp:include page="footer.jsp" />


