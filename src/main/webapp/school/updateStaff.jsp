<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>


<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>

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
    String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);


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


        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Update staff  : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
            </div>
            <div class="panel-body">
              <div class="row">

                <form  class="form-horizontal"  role="form"   action="updateStaff" method="POST" >
                                 
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="employeeNo">Employee Number</label>
                        <div class="col-sm-9">
                            <input class="form-control"  name="employeeNo" type="text" value="<%=staffDetail.getEmployeeNo()%>" >
                        </div>
                    </div>

                  <div class="form-group">
                        <label class="col-sm-3 control-label" for="Category">Staff:</label>
                         <div class="col-sm-9">
                            <select name="category" class="form-control" required>
                                <option value="">Please select one</option> 
                                <option value="Teaching">Teaching</option>
                                <option value="Non-Teaching">Non-Teaching</option>
                               
                            </select>                           
                          
                        </div>
                    </div> 

                        
                    <div class="form-group" id="divid">
                        <label class="col-sm-3 control-label" for="name">Position</label>
                        <div class="col-sm-9">
                            <select name="position" class="form-control" required>
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (positionList != null) {
                                        for (Position p : positionList) {
                                %>
                               <option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="username">Username</label>
                        <div class="col-sm-9">
                            <input class="form-control"  name="username" type="text" value="<%=staff.getUserName()%>" >
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="firstname">First name</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="firstname" type="text" value="<%=StringUtils.capitalize(staffDetail.getFirstName().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="lastname">Middle name</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="lastname" type="text" value="<%=StringUtils.capitalize(staffDetail.getLastName().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="surname">Last name</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="surname" type="text" value="<%=StringUtils.capitalize(staffDetail.getSurname().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="Gender" class="col-sm-3 control-label">Gender:*</label>
                            <div class="col-sm-9">
                              <select name="gender" class="form-control" required>
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option> 
                            </select>
                        </div>
                    </div>


                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="nhif">NHIF</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="nhif" type="text" value="<%=staffDetail.getNhifNo()%>">
                        </div>
                    </div>

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="nssf">NSSF</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="nssf" type="text" value="<%=staffDetail.getNssfNo()%>">
                        </div>
                    </div>

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="phone">Phone*</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="phone" type="text" value="<%=staffDetail.getPhone()%>">
                        </div>
                    </div>


                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="dob">YOB</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="dob" type="text" value="<%=staffDetail.getdOB()%>">
                        </div>
                    </div>

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="idno">ID No</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="idno" type="text" value="<%=staffDetail.getNationalID()%>">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="county">County</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="county" type="text" value="<%=StringUtils.capitalize(staffDetail.getCounty().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>



                   <div class="form-group">
                      <div class="col-sm-9 col-sm-offset-3">
                          <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                           <input type="hidden" name="staffUuid" value="<%=staff.getUuid()%>">
                           <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                           <button type="submit" class="btn btn-primary btn-block">Update</button>
                        </div>
                    </div>


   
              </form>
                
                  
                 
                </div>
              </div>
              <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary">
                          <i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger">
                            <i class="glyphicon glyphicon-remove"></i> </a> 
                        </span>
                    </div>
            </div>
          <!--panel end-->
      </div>


    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />




