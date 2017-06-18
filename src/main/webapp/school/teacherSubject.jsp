
<%@page import="com.yahoo.petermwenda83.persistence.staff.TeacherSubClassDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.TeacherSubClass"%>
<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>
<%@page import="com.yahoo.petermwenda83.persistence.subject.SubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.subject.Subject"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%

       
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
   


    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    String accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
     String stffID = "";
     String staffPosition  ="";
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     staffPosition = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION);
     String pos_Teacher =(String) PropertiesConfig.getConfigValue("POSITION_TEACHER");
     String pos_HOD =(String) PropertiesConfig.getConfigValue("POSITION_HOD");
     String pos_CM =(String) PropertiesConfig.getConfigValue("POSITION_CM");
     stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

           

     
     TeacherSubClassDAO teacherSubClassDAO = TeacherSubClassDAO.getInstance();
     List<TeacherSubClass> teachersubclassList = new ArrayList<TeacherSubClass>(); 
     if(teacherSubClassDAO.getSubjectsANDClassesList(stffID) !=null){
      teachersubclassList = teacherSubClassDAO.getSubjectsANDClassesList(stffID);
      }
      
     
     HashMap<String, String> subjectHash = new HashMap<String, String>();
     HashMap<String, String> subjectCodeHash = new HashMap<String, String>();
     
     SubjectDAO subjectDAO = SubjectDAO.getInstance();
     List<Subject> subjectList = new ArrayList<Subject>(); 
     subjectList = subjectDAO.getAllSubjects(); 
      for(Subject s : subjectList){
           subjectHash.put(s.getUuid() , s.getSubjectName());  
           subjectCodeHash.put(s.getUuid() , s.getSubjectCode());
            }



     HashMap<String, String> roomHash = new HashMap<String, String>();
     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 
      for(ClassRoom c : classroomList){
           roomHash.put(c.getUuid() , c.getRoomName());

            }

    
    

    DecimalFormat df = new DecimalFormat("0.00"); 
    df.setRoundingMode(RoundingMode.DOWN);
                                        

 %>






<jsp:include page="header.jsp" />



<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
       
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
        <li> <a href="perclassUpload.jsp">Upload Exam</a> </li>
       </div>


      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
               MY SUBJECTS TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> 
              </h3>
            </div>
            <div class="panel-body">
              
              <div class="table-responsive ">

               <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>ClassRoom</th>
                        <th>Subject </th>
                        <th>Code </th>
                        <th>Scores </th>
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                       for(TeacherSubClass cs : teachersubclassList) {
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td class=\"center\">" + roomHash.get(cs.getClassRoomUuid()) + "</td>"); 
                             out.println("<td class=\"center\">" + subjectHash.get(cs.getSubjectUuid()) + "</td>");  
                             out.println("<td class=\"center\">" + subjectCodeHash.get(cs.getSubjectUuid()) + "</td>");  
                             
                             %> 
                                <td class="center">
                                <form name="edit" method="POST" action="viewScores.jsp"> 
                                <input type="hidden" name="subjectUuid" value="<%=cs.getSubjectUuid()%>">
                                <input type="hidden" name="classroomUuid" value="<%=cs.getClassRoomUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="View" /> 
                                </form>                          
                               </td>  
                               

                        <%      
                        count++;
                      } 
                    %>
                    
                    </tbody>
            </table> 

         
    </div>  
    </div>  <!-- end pnel body-->
              <div class="panel-footer">
                        <div class="row">
                          <div class="col col-xs-4"> <small> <i>Watu wafunze!</i> </small>
                          </div>
                        </div>
                  </div>
    </div>    <!-- end pnel -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />


