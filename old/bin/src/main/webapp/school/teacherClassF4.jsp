<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Perfomance"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>


<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

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
   
     String stffID ="";
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     String classroomuuid = "";

     ClassTeacherDAO classTeacherDAO = ClassTeacherDAO.getInstance();
     ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByteacherId(stffID);
     if(classTeacher !=null){
           classroomuuid = classTeacher.getClassRoomUuid();
         }
       
   
     
     HashMap<String, String> roomHash = new HashMap<String, String>();
     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 
      for(ClassRoom c : classroomList){
           roomHash.put(c.getUuid() , c.getRoomName());
            
            } 

   
   
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
       
       </div>


      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                EXAM MANAGEMENT : <%=roomHash.get(classroomuuid)%> TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> 
              </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">

               <table class="table table-striped  ">
                <thead>
                    <tr >             
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>   
                <tbody >    

                 <% if(StringUtils.equals(examConfig.getExamMode(), "ON")) {%>           
                    <td width="10%" class="center">    
                    <form  class=""   action="classListF3_4" method="POST" target="_blank">
                     <fieldset>                     
                     <input type="hidden" name="examID" value="4BE8AD46-EAE8-4151-BD18-CB23CF904DDB" > 
                     <input type="hidden" name="staffid" value="<%=stffID%>" >      
                     <input type="hidden" name="classID" value="14E56350-08DA-45CC-97D9-C225AF74A7AD" >  <!--F4 -->     
                     <button type="submit" name="Report" value="Report"   class="btn btn-primary">(P1,P2,P3) Performance List</button> 
                     </fieldset>
                     </form>                                      
                    </td> 


                     <td width="10%" class="center">                              
                     <form  class=""   action="reportFormF3_4_p1_p2_p3" method="POST" target="_blank">
                     <fieldset>                     
                     <input type="hidden" name="examID" value="4BE8AD46-EAE8-4151-BD18-CB23CF904DDB" >   
                     <input type="hidden" name="staffid" value="<%=stffID%>" >    
                      <input type="hidden" name="classID" value="14E56350-08DA-45CC-97D9-C225AF74A7AD" >  <!--F4 -->     
                     <button type="submit" name="Report" value="Report"   class="btn btn-primary">(P1,P2,P3) Report Form</button> 
                     </fieldset>
                     </form>                                            
                    </td> 

                     <%} else {%>

                    <td width="10%" class="center">                              
                    <form  class=""   action="classListF3_4" method="POST" target="_blank">
                     <fieldset>
                     <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" > 
                     <input type="hidden" name="staffid" value="<%=stffID%>" >    
                      <input type="hidden" name="classID" value="14E56350-08DA-45CC-97D9-C225AF74A7AD" >  <!--F4 -->     
                     <button type="submit" name="Report" value="Report"   class="btn btn-primary">(C1,C2,ET) Performance List</button> 
                     </fieldset>
                     </form>                                             
                    </td> 

                   

                    <td width="10%" class="center">                              
                    <form  class=""   action="reportFormF3_4_c1_c2_et" method="POST" target="_blank">
                     <fieldset>
                     <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" >  
                     <input type="hidden" name="staffid" value="<%=stffID%>" > 
                      <input type="hidden" name="classID" value="14E56350-08DA-45CC-97D9-C225AF74A7AD" >  <!--F4 -->       
                     <button type="submit" name="Report" value="Report"   class="btn btn-primary">(C1,C2,ET) Report Form</button> 
                     </fieldset>
                     </form>                                         
                    </td> 
                     <%}%>
                </tbody>                  
            </table>  

      
    </div>  
    </div>  <!-- end panel body-->
    <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
    </div>  <!-- end panel -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />


