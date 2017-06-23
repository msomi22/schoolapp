<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@ page import="java.util.Calendar" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%
     String accountuuid = "";

     if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }
     
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();

   

    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    RoomDAO roomDAO = RoomDAO.getInstance();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();
    classList = roomDAO.getAllRooms(accountuuid);

     StudentDAO studentDAO = StudentDAO.getInstance();
     Student student = studentDAO.getStudentADmNo(accountuuid);

      int admno = 0; 
      String studentadm = student.getAdmno();
      admno = NumberUtils.toInt(studentadm);
      if(admno <=0){
          final  String INITIAL_ADM_NO =(String)  PropertiesConfig.getConfigValue("INITIAL_ADM_NO");
          admno = NumberUtils.toInt(INITIAL_ADM_NO);
       }else{
            admno = NumberUtils.toInt(studentadm);
        }
     
     admno = admno + 1;
     String newAdmno = ""+admno;
      
    
    
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     

    Calendar calendar = Calendar.getInstance();
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR)-18;
    final int YEAR_COUNT = YEAR + 10;

    final int YEAR2 = calendar.get(Calendar.YEAR)-10;
    final int YEAR_COUNT2 = YEAR2 + 10;

   

 %>






<jsp:include page="header.jsp" />


<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li> <a href="addStudent.jsp">New Student</a>  </li>
        <li>  <a href="importexcel.jsp">Import Excel</a> </li>
        <li> <a href="parents.jsp">Parents</a> </li>
        <li> <a href="studentSponsor.jsp">Sponsors</a> </li>
        <li> <a href="studentHouse.jsp">House</a>  </li>
        <li> <a href="studentSubjects.jsp">Student Subject</a>  </li>
        <li> <a href="classTeachersSec.jsp">Class List</a> </li>
        <li> <a href="settings.jsp">More...</a>  </li>
      </ul><br>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search Anything...">
        <span class="input-group-btn">
          <button class="btn btn-default" type="button">
            <span class="glyphicon glyphicon-search"></span>
          </button>
        </span>
      </div>
       <hr>
      <div id="myCarousel" class="carousel slide">
          <!-- Carousel indicators -->
            <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                  <li data-target="#myCarousel" data-slide-to="2"></li>
                  </ol>
                  <!-- Carousel items -->
                  <div class="carousel-inner">
                  <div class="item active">
                  <img src="../img/slide/slide1.jpg" alt="First slide">
                  <div class="carousel-caption">This Caption 1</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide2.jpg" alt="Second slide">
                  <div class="carousel-caption">This Caption 2</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide3.jpg" alt="Third slide">
                  <div class="carousel-caption">This Caption 3</div>
                  </div>
                  </div>
                  <!-- Carousel nav -->
              <a class="carousel-control left" href="#myCarousel"
                  data-slide="prev">&lsaquo;</a>
              <a class="carousel-control right" href="#myCarousel"
                  data-slide="next">&rsaquo;</a>
          </div>
          <hr>
    </div>

    <div class="col-sm-9">
        <div class="breadcrumb">
           STUDENT REGISTRATION : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
       </div>

     
              <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.STUDENT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_SUCCESS);
                                } 

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_ADD_SUCCESS, null);
                                  } 
                       


                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> STUDENT REGISTRATION : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </h3>
            </div>
            <div class="panel-body">
             
              <p>Fields marked with a * are compulsory.</p>
                    <form  class="form-horizontal"   action="addStudentBacic" method="POST" >
                    <fieldset>

                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="Classroom">Classroom*:</label>
                                         <div class="col-sm-9">
                                            <select name="classroomUuid" class="form-control" required>

                                                <option value="">Please select one</option> 
                                                 <%
                                                    int count = 1;
                                                    if (classList != null) {
                                                        for (ClassRoom cl : classList) {
                                                %>
                                                <option value="<%=cl.getUuid()%>"><%=cl.getRoomName()%></option>
                                                <%
                                                            count++;
                                                        }
                                                    }
                                                %>
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 


                                  
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Admission Number*:</label>
                                        <div class="col-sm-9">
                                         <input class="form-control" id="receiver" type="text" name="admNO" 
                                            value="<%=newAdmno%>" required> 

                                        </div>
                                    </div>  


                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">First name*:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="firstname" 
                                             value='<%=StringUtils.trimToEmpty(paramHash.get("firstname"))%>' style="text-transform: capitalize;" required>                                    
                                        </div>
                                    </div> 


                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Middle name*:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="lastname"
                                              value='<%=StringUtils.trimToEmpty(paramHash.get("lastname"))%>' style="text-transform: capitalize;" required>
                                        </div>
                                    </div> 


                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Last name:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="surname"
                                              value='<%=StringUtils.trimToEmpty(paramHash.get("surname"))%>' style="text-transform: capitalize;" >
                                        </div>
                                    </div> 

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="gender">Gender*:</label>
                                         <div class="col-sm-9">
                                            <select name="gender" class="form-control" required>
                                               <option value="">Please select one</option> 
                                                <option value="MALE">Male</option>
                                              <option value="FEMALE">Female</option> 
                                                
                                            </select>                           
                                          
                                        </div>
                                    </div> 
                                     

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">DOB (DD-MM-YYYY)*:</label>
                                        <div class="col-sm-9">
                                                  <select name="dobaddDay" id="input" style="max-width:14%;" class="form-control" required>
                                                        <%
                                                            for (int j = 1; j < DAYS_IN_MONTH; j++) {
                                                                if (j == DAY_OF_MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                   <select name="dobaddMonth" id="input" style="max-width:14%;" class="form-control" required>
                                                        <%
                                                            for (int j = 1; j < 13; j++) {
                                                                if (j == MONTH) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                    <select name="dobaddYear" id="input" style="max-width:14%;" class="form-control" required>
                                                        <%
                                                            for (int j = YEAR; j < YEAR_COUNT; j++) {
                                                                if (j == YEAR) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                        </div>
                                        </div>


                                    

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Birth Cert:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="BcertNo"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("BcertNo")) %>"  >
                                        </div>
                                    </div> 

                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">County:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="County"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("County")) %>" style="text-transform: capitalize;" >
                                        </div>
                                    </div> 

                                   <h3><i class="icon-edit"></i> Primary School Details:</h3>  

                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Primary School:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="primary"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("primary")) %>"  style="text-transform: capitalize;">
                                        </div>
                                    </div> 


                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">Index Number:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="indexno"
                                              value="<%= StringUtils.trimToEmpty(paramHash.get("indexno")) %>"  >
                                        </div>
                                    </div> 

                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">KCPE Year:</label>
                                         <div class="col-sm-9">
                                                <select name="kcpeaddYear" id="input" style="max-width:14%;" class="form-control" required>
                                                        <%
                                                            for (int j = YEAR2; j < YEAR_COUNT2; j++) {
                                                                if (j == YEAR2) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                            </div>
                                    </div> 

                                     <div class="form-group">
                                        <label class="col-sm-3 control-label" for="name">KCPE Marks*:</label>
                                        <div class="col-sm-9">
                                            <input class="form-control" id="receiver" type="text" name="kcpemark"
                                              value='<%=StringUtils.trimToEmpty(paramHash.get("kcpemark"))%>'  required>
                                        </div>
                                    </div> 

                                    <div class="form-group">
                                        <label class="col-sm-3 control-label" for="StydentType">StydentType*:</label>
                                         <div class="col-sm-9">
                                            <select name="StydentType" class="form-control" required>
                                              <option value="Boarder">Boarder</option> 
                                              <option value="Day">Day</option> 
                                            </select>                           
                                          
                                        </div>
                                    </div> 

                                    
                                    <div class="form-group">
                                      <div class="col-sm-9 col-sm-offset-3">
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                         <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                                        <button type="submit" class="btn btn-primary btn-block">Register</button>
                                      </div>
                                    </div> 

              </fieldset>
              </form>

  </div>
  <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Life like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>

<jsp:include page="footer.jsp" />


