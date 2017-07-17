<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Stream"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.ClassDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

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
    
    
    
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    
    Account school = new Account();
    Element element;
   

    if ((element = accountsCache.get(username)) != null) {
        school = (Account) element.getObjectValue();
    }

     String accountId = school.getUuid();
    
     
     
     //get exams list
     
     ExamDAO examDAO = ExamDAO.getInstance();
     
     List<Exam> examList= new ArrayList<>();
     
     examList= examDAO.getExamList(accountId);
     
     
     //get class list
     ClassDAO classDAO = ClassDAO.getInstance();
     
     List<ClassRoom> classroomList= new ArrayList<>();
     
     classroomList= classDAO.getClassRooms(accountId);
     
     
     
     //get stream list
     StreamDAO streamDAO = StreamDAO.getInstance();
     
     List<Stream> streamList= new ArrayList<>();
     
     streamList= streamDAO.getStreamList(accountId);
     
     
     
   
%>
<jsp:include page="header.jsp" />
  <!-- Custom report style -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Roboto:400,700'>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css'>
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
                    <h2>Customize Exam Report display<small> Click Generate when done</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>                      
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    
                    
                    <form  action="studentReportCard" id="generateReport" class="col-md-6 col-md-offset-3" method="get" target="_blank">
                    
                   
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
                    
                    <h2>Exam</h2>
                    
                     <c:forEach var = "i" begin = "1" end = "5">
         Item <c:out value = "${examList}"/><p>
      </c:forEach>
                    
                     <c:forEach  var="Exam" items="${examList}">
                    
                   <h1>hi there</h1>
                    
                    </c:forEach> --%>
                    
                    <!-- Exam element -->
                    
                      <h2>Exam</h2>
                    
                    <select id="exam" name="exam" class="form-control formelement" multiple>
                    
                   <% 
                   
                   if(examList !=null){
                   
                   for(Exam exam : examList){                   
                    %>
                    
                    <option  value="<%=exam.getUuid()%>"> <%=exam.getDescription() %></option>
                    
                    <%}
                   }
                   else {
                        %>
                    <option  value="">...</option>
                    
                    <%} %>
                    
                    </select>
                    
                    </div>
                    
                    
                    
                    </div>
                    
                    <!-- Scope element -->
                     <h2>Scope:</h2>
                     
                     	<div class="row">
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="class" class="form-control" name="scope" value="true" onclick="scopeSwap(this.id)" checked>
												<label for="class">
													<h6>Class</h6>
													
												</label>
											</div>
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="stream" name="scope" value="stream" onclick="scopeSwap(this.id)">
												<label for="stream">
													<h6>Stream</h6>
													
												</label>
											</div>
											
							</div>
							
							
                    <div class="row" id="classScope">
                    
                    <div class="col-md-5 col-md-offset-1">
                    
                    
                    <h2>Class</h2>
   
                    <select class= "form-control formelement" name="classroom">
                     <% 
                     if(classroomList !=null){
                     for(ClassRoom classroom : classroomList){                   
                    %>
                    
                    <option  value="<%=classroom.getUuid()%>"> <%=classroom.getDescription() %></option>
                    
                    <%}} 
                    else {
                    %>
                     <option  value="">...</option>
                      <%} %>
                    </select>
                    
                    
                    
                    </div>
                    
                    </div>
                    
                    <div class="row" id="streamScope" style="display:none">
                    
                    
                    <div class="col-md-5 col-md-offset-1">
                    
                    <h2>Stream</h2>
                    
                    <select class= "form-control formelement" name="stream" >
                    
                    <% 
                    
                    if(streamList !=null){
                    for(Stream stream : streamList){                   
                    %>
                    
                    <option  value="<%=stream.getUuid()%>"> <%=stream.getDescription() %></option>
                    
                    <%}}
                    
                    else {
                    %>
                     <option  value="">...</option>
                     
                      <%} %>
                    
                    
                    </select>
                    
                    </div>
                    
                    
                    </div>
                    
                     <!-- Hide points or grades element -->
                    
                    <h2>HIDE Points:</h2>

							<div class="row">
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="pointshide" class="form-control" name="p" value="true" checked>
												<label for="pointshide">
													<h6>Yes</h6>
													
												</label>
											</div>
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="points" name="p" value="false">
												<label for="points">
													<h6>No</h6>
													
												</label>
											</div>
											
							</div>
							
						 <h2>HIDE Grades:</h2>	
							
							<div class="row">
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="gradeshide" name="g" value="true"
													checked> <label for="gradeshide">
													<h6>YES</h6>
												</label>
											</div>
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="grades" name="g" value="false"> <label for="grades">
													<h6>NO</h6>
												</label>
											</div>


										</div>
										
										
										
										 <!-- Show fee element -->
										
				<h2>Show Fee INFO:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="fee" name="fee" value="true" checked>
											<label for="fee">
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
							
							
										
										
						
										
										
					 <!-- Rank element -->	
					 
					 			
					<h2>RANK:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1" >
											<input type="radio" id="pointsrank" name="rank" value="points" checked>
											<label for="pointsrank">
												<h6>Rank with points</h6>
												
											</label>
										</div>
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="gradesrank"  name="rank" value="marks"
												> <label for="gradesrank">
												<h6>Rank with total marks</h6>
												
											</label>
										</div>
										
							</div>
						
						
						 <!-- Number of subject element -->	
							
				<h2>No_ of Subjects:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="7sub" name="subjects" value="seven" checked>
											<label for="7sub">
												<h6>Grade 7 subjects</h6>
												
											</label>
										</div >
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="11sub" name="subjects" value="eleven"
												> <label for="11sub">
												<h6>Grade 11 subjects</h6>
												
											</label>
										</div>
										
							</div>
							
					 <!-- Type of report element -->		
				<h2>Type of Report:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="reportcard" name="reportcard" onclick="redirect(this.id)" value="reportcard" checked>
											<label for="reportcard">
												<h6>Report Card</h6>
												
											</label>
										</div >
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="ranklist" name="reportcard" value="ranklist" onclick="redirect(this.id)"
												> <label for="ranklist">
												<h6>Rank List</h6>
												
											</label>
										</div>
										
							</div>
							
							
						
							
							<br>
							
							 <!-- footer of the form elements: Back,Reset and generate -->
							
							<div class="row">
								<div class="col-md-4">
								<button class="btn btn-primary">Back</button>
								<button type="reset" class="btn btn-primary">Reset</button>
								</div>
								
								
								
								<div class="col-md-2 pull-right">
								<button type="submit" class="btn btn-lg btn-success">Generate</button>
								</div>
							
							
							</div>
							
							
							</form>
							
										

									</div><!-- ./content -->
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

 <script src="js/customReportJs.js"></script>
        