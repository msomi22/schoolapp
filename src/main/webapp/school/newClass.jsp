<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

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

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    RoomDAO roomDAO = RoomDAO.getInstance();
    List<ClassRoom> classroomList = new ArrayList<ClassRoom>();
    classroomList = roomDAO.getAllRooms(accountuuid);


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
        <li>  <a href="settings.jsp">Back</a> </li>
        <li>   <a href="addnewClass.jsp">New Class</a> </li>
       </div>

                  <%
                        
                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);

                                     addError = (String) session.getAttribute(SessionConstants.ROOM_REG_ERROR);
                                     addsuccess = (String) session.getAttribute(SessionConstants.ROOM_REG_SUCCESS); 

                

                                if (StringUtils.isNotEmpty(addError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.ROOM_REG_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.ROOM_REG_SUCCESS, null);
                                  }      
                                  

                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                Classrooms : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">

                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Class </th>
                        <th>Update </th>
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                        if(classroomList !=null){
                       for(ClassRoom cr : classroomList) { 

                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" +cr.getRoomName() + "</td>"); 
                                     %>
                            <td class="center" width ="10%">
                             <form name="update" method="POST" action="updateClass.jsp"> 
                             <input type="hidden" name="classname" value="<%=cr.getRoomName()%>">
                              <input type="hidden" name="roomuuid" value="<%=cr.getUuid()%>">
                             <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                             </form>                          
                             </td>  

                             <%

                           count++;
                          } 
                     }
                    %>
                    
                    </tbody>
            </table> 
 
      </div>
    </div>
    <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
   </div>
  </div>
 </div>
</div>

<jsp:include page="footer.jsp" />




