
<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
if (session == null) {
        response.sendRedirect("index.jsp");
    }

    String username = (String) session.getAttribute(AdminSessionConstants.ADMIN_SESSION_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("index.jsp");
    }

     session.setMaxInactiveInterval(AdminSessionConstants.SESSION_TIMEOUT);
     response.setHeader("Refresh", AdminSessionConstants.SESSION_TIMEOUT + "; url=Logout");

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    
    Element element;
    Account account = new Account();

     List keys;
     List<Account> schoolList = new ArrayList(); 
    keys = accountsCache.getKeys();
    for (Object key : keys) {
        element = accountsCache.get(key);
        account = (Account) element.getObjectValue();
        schoolList.add(account);
    }
   


     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     

     String principalUuid = "C3915245-00EE-4EF4-9898-ACE59683DD60";

     String principalUsername = "";
     String staffname = "";
    

     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList(); 

     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     
     
     


%>


<jsp:include page="header.jsp" /> 

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"> <a href="adminIndex.jsp">Home</a></li>
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

                       <div class="item">
                      <img src="../img/slide/slide4.jpg" alt="Third slide">
                      <div class="carousel-caption">This Caption 4</div>
                      </div>

                       <div class="item">
                      <img src="../img/slide/slide5.png" alt="Third slide">
                      <div class="carousel-caption">This Caption 5</div>
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
            <h4><small> WELCOME ADMIN </small></h4>
       </div>
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> Schoools </h3>
            </div>

            <div class="panel-body">

                             <%

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS, null);
                                  } 

                                 

                            %>



                   <div class="table-responsive ">

                   <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>School Name</th>
                        <th>UserName</th> 
                        <th>Principal</th> 
                        <th>Students</th>                
                        <th>Mobile</th>
                        <th>Email</th>
                        <th>Postal address</th>
                        <th>Home Town</th>
                        <th>Status</th>
                        <th>Actions</th>

                    </tr>
                </thead>   
                <tbody>
                    <%                                                          
                      int count = 1;
                         for (Account s : schoolList) {
                          String status = "Active";

                          if(StringUtils.equals(s.getIsActive(),"1")){
                            status = "Active";
                              }else{
                             status = "Inactive";
                               }

                           StaffDetails staffDetails = new StaffDetails();
                           staffList = staffDAO.getStaffList(s.getUuid()); 
                           for(Staff staff : staffList){
                            

                           if(StringUtils.equals(staff.getPositionUuid(), principalUuid)) {
                              principalUsername = staff.getUserName();
                            
                              staffDetails = staffDetailsDAO.getStaffDetail(staff.getUuid());

                              if(staffDetails != null) {
                                  staffname = "";
                                  staffname = "("+staffDetails.getSurname()+" "+staffDetails.getFirstName()+" "+staffDetails.getLastName()+")";

                              }else{
                                staffname = "";
                              }
                           }
                         }     
                    %>
                    <tr>
                        <td width="3%"><%=count%></td>
                         <td class="center"><%=s.getName()%></td> 
                         <td class="center"><%=s.getUsername()%></td>
                         <td class="center"><%=principalUsername + " "+staffname+""%></td>
                         <td class="center"><%=studentDAO.getStudentCount("1",s.getUuid())%></td>
                         <td class="center"><%=s.getMobile()%></td>
                         <td class="center"><%=s.getEmail()%></td>  
                         <td class="center"><%=s.getAddress()%></td>
                         <td class="center"><%=s.getTown()%></td>  
                         <td class="center"><%=status%></td>  
                         <td class="center">
                                <form name="edit" method="POST" action="editSchool.jsp"> 
                                <input type="hidden" name="schoolname" value="<%=s.getName()%>">
                                <input type="hidden" name="username" value="<%=s.getUsername()%>">
                                <input type="hidden" name="password" value="<%=s.getPassword()%>">
                                <input type="hidden" name="mobile" value="<%=s.getMobile()%>">
                                <input type="hidden" name="email" value="<%=s.getEmail()%>">
                                <input type="hidden" name="postaladdress" value="<%=s.getAddress()%>">
                                <input type="hidden" name="hometown" value="<%=s.getTown()%>">
                                <input type="hidden" name="schooluuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Edit School" /> 
                                </form>     

                                <form name="edit" method="POST" action="profile.jsp"> 
                                <input type="hidden" name="schooluuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Edit Password" /> 
                                </form>                          
                        </td>   
                    </tr>

                    <%     
                           count++;
                           principalUsername = " ";
                           staffname = " ";
                            
                            } 
                    %>
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




