
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>


<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

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
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    String accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    String stffID ="";
    stffID = request.getParameter("staffid");
    String classroomuuid = "";
    classroomuuid = request.getParameter("classroomuuid");

    HashMap<String, String> roomHash = new HashMap<String, String>();
     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 
      for(ClassRoom c : classroomList){
           roomHash.put(c.getUuid() , c.getRoomName());
      }


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
     
   
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
            <li> <a href="classTeachers.jsp">Back</a> </li>
            
       </div>

     


      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Exam panel: <%=roomHash.get(classroomuuid)%> , TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
            </div>
            <div class="panel-body">
              
              <div class="table-responsive ">

               <p>7 exams </p>              
                        
                    <table class="table table-striped  ">
                      <thead>
                          <tr >             
                              <th></th>
                              <th></th>
                          </tr>
                      </thead>   
                      <tbody >          
                          

                          <td width="6%" class="center">    
                          <form  class=""   action="classListF3_4" method="POST" target="_blank">
                          <fieldset>                      
                          <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" > 
                          <input type="hidden" name="classID" value="3E22E428-3155-42F5-B73E-66553ED501C9" >  <!--F2 -->   
                          <input type="hidden" name="staffid" value="<%=stffID%>" >     
                          <button type="submit" name="Report" value="Report"   class="btn btn-primary">Performance List F2</button> 
                          </fieldset>
                          </form>                                          
                          </td> 

                          <td width="6%" class="center">                              
                          <form  class=""   action="reportFormF3_4_c1_c2_et" method="POST" target="_blank">
                          <fieldset>                    
                          <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" >  
                          <input type="hidden" name="classID" value="3E22E428-3155-42F5-B73E-66553ED501C9" >  <!--F2 -->   
                          <input type="hidden" name="staffid" value="<%=stffID%>" >        
                          <button type="submit" name="Report" value="Report"   class="btn btn-primary">Report Form F2</button>                
                          </fieldset>
                          </form>                                                
                          </td> 
                      </tbody>                  
                  </table>  

                 <p>  11 exams  </p>
               

                <table class="table table-striped  ">
                <thead>
                    <tr >             
                        <th></th>
                        <th></th>
                    </tr>
                </thead>   
                <tbody >            
                    <td width="6%" class="center">    
                    <form  class=""   action="classListF1" method="POST" target="_blank">
                    <fieldset>                      
                    <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" > 
                    <input type="hidden" name="staffid" value="<%=stffID%>" >  
                    <input type="hidden" name="classID" value="3E22E428-3155-42F5-B73E-66553ED501C9" >  <!--F2 -->   
                    <button type="submit" name="Report" value="Report"   class="btn btn-primary">Performance List F2</button> 
                    </fieldset>
                    </form>                                          
                    </td> 

                    <td width="6%" class="center">                              
                    <form  class=""   action="reportFormF1" method="POST" target="_blank">
                    <fieldset>                    
                    <input type="hidden" name="examID" value="1678664C-D955-4FA7-88C2-9461D3F1E782" >  
                    <input type="hidden" name="staffid" value="<%=stffID%>" > 
                    <input type="hidden" name="classID" value="3E22E428-3155-42F5-B73E-66553ED501C9" >  <!--F2 -->          
                    <button type="submit" name="Report" value="Report"   class="btn btn-primary">Report Form F2</button>                
                    </fieldset>
                    </form>                                                
                    </td> 

                    
                </tbody>                  
            </table>  



    </div>  
    </div>  <!-- end panel body-->
                <div class="panel-footer">
                        <div class="row">
                          <div class="col col-xs-4"> <small> <i>Tujue mbichi na mbivu!</i> </small>
                          </div>
                        </div>
                  </div>
    </div>   <!-- end panel-->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />
















