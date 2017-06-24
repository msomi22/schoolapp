<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>


<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.AcessLevelDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.AcessLevel"%>

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

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

<%@page import="java.text.SimpleDateFormat"%>


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
    String staffId = request.getParameter("staffuuid");

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   

     
     
     
     Staff staff = new Staff();
     StaffDAO staffDAO = StaffDAO.getInstance(); 
     staff = staffDAO.getStaff(accountuuid,staffId);

     StaffDetails staffDetail = new StaffDetails();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     staffDetail = staffDetailsDAO.getStaffDetail(staff.getUuid());


     HashMap<String, String> positionHash = new HashMap<String, String>();
     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();
     for(Position pp : positionList){
      positionHash.put(pp.getUuid(),pp.getPosition());  
       }
      

     
                          

 %>






<jsp:include page="header.jsp" />





<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li><a href="#section2">Library</a></li>
        <li><a href="#section3">SMSes</a></li>
        <li><a href="#section3">House/Classes</a></li>
        <li><a href="#section3">Parents</a></li>
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
            <li> <a href="staff.jsp">Back</a> </li>
       </div>

      <h4><small>STAFF PROFILE - Staff No:  <%=staffDetail.getEmployeeNo()%> </small></h4>

      <!-- profile start -->
      
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> (<%=staff.getUserName()%> ) -
              <%=StringUtils.capitalize(staffDetail.getFirstName().toLowerCase())%> -
              <%=StringUtils.capitalize(staffDetail.getLastName().toLowerCase())%> -
              <%=StringUtils.capitalize(staffDetail.getSurname().toLowerCase())%>

              </h3>
            </div>
            <div class="panel-body">
              <div class="row">
               <div class="table-responsive ">
                <div class="col-md-3 col-lg-3 " align="center">
                  <img alt="User Pic" src="../img/avatar-300x300.png" class="img-circle img-responsive"> 
                 </div>
                
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Staff Category:</td>
                        <td><%=staff.getCategory()%></td>
                      </tr>

                      <tr>
                        <td>Position:</td>
                        <td><%=positionHash.get(staff.getPositionUuid())%></td>
                      </tr>

                      <tr>
                        <td>Hire date:</td>
                        <td><%=staffDetail.getRegistrationDate()%></td>
                      </tr>

                      <tr>
                        <td>NHIF:</td>
                        <td><%=staffDetail.getNhifNo()%></td>
                      </tr>

                       <tr>
                        <td>NSSF:</td>
                        <td><%=staffDetail.getNssfNo()%></td>
                      </tr>

                      <tr>
                        <td>ID Number:</td>
                        <td><%=staffDetail.getNationalID()%></td>
                      </tr>
                   
                       <tr>
                        <tr>
                        <td>Gender:</td>
                        <td><%=staffDetail.getGender()%></td>
                      </tr>
                        <tr>
                        <td>YOB:</td>
                        <td><%=staffDetail.getdOB()%></td>
                      </tr>
                      <tr>
                        <td>County:</td>
                        <td><%=StringUtils.capitalize(staffDetail.getCounty().toLowerCase())%></td>
                      </tr>
                        <td>Phone Number:</td>
                        <td><%=staffDetail.getPhone()%></td>  
                      </tr>
                     
                    </tbody>
                  </table>
                  
                  <a href="#" class="btn btn-primary">Performance</a>
                </div>
              </div>
            </div>
                 <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary">
                          <i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning">
                            <i class="glyphicon glyphicon-edit"></i></a>
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger">
                            <i class="glyphicon glyphicon-remove"></i></a> 
                        </span>
                    </div>
            
          </div>
          <!--panel end-->
      </div>
  
      <hr>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />


