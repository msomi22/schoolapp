<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.pagination.staff.StaffPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.staff.StaffPage"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@page import="javax.servlet.ServletContext"%>

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
   
    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }


    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);


     StaffDetails staffdetail = new StaffDetails();
     HashMap<String, StaffDetails> staffHash = new HashMap<String, StaffDetails>();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     List<StaffDetails> staffdetailList = new ArrayList<StaffDetails>(); 
     staffdetailList = staffDetailsDAO.getSStaffDetailList();
         
         if(staffdetailList !=null){
      for(StaffDetails sd : staffdetailList){
          staffHash.put(sd.getStaffUuid(), sd);
         }
     }
     
     HashMap<String, String> positionHash = new HashMap<String, String>();
     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();
        if(positionList !=null){
     for(Position pp : positionList){
      positionHash.put(pp.getUuid(),pp.getPosition());  
       }
   }
    
     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList<Staff>(); 
     staffList = staffDAO.getStaffList(accountuuid,0,10);

     
    
    
       //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");  

   
     ServletContext context = getServletContext();
     Map<String,String> online =  (HashMap<String,String>)context.getAttribute("onlineUsersMap");
     String value_sessionId = "";
     String staff_status = "";


     int ussdCount = 0;
     StaffPaginator paginator = new StaffPaginator(accountuuid);
     StaffPage staffPage;

     staffPage = (StaffPage) session.getAttribute("currentPage3");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (staffPage == null
                || !StringUtils.endsWith(referrer, "staff.jsp")
                || StringUtils.equalsIgnoreCase(pageParam, "first")) {
              staffPage = paginator.getFirstPage();

            //We are to give the last page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
             staffPage = paginator.getLastPage();

            // We are to give the previous page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
            staffPage = paginator.getPrevPage(staffPage);

            // We are to give the next page 
        } else if (StringUtils.equalsIgnoreCase(pageParam, "next"))  {
           staffPage = paginator.getNextPage(staffPage);
        }

        session.setAttribute("currentPage3", staffPage);
        staffList = staffPage.getContents();
        ussdCount = (staffPage.getPageNum() - 1) * staffPage.getPagesize() + 1;
     
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
            <li> <a href="addStaff.jsp">New Staff</a> </li>
            <li> <a href="classTeachers.jsp">Class Teachers</a> </li>
            <li> <a href="teacherSubjects.jsp">Asign Subject</a> </li>
       </div>


                                 <%             

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                
                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS);
                                }                           

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                   %>
                                    <div class="alert alert-warning">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Warning!</strong> 
                                           <%
                                          out.println("error: " + updateErrStr);
                                           %>
                                    </div>

                                    <%                                 
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                   %>
                                    <div class="alert alert-success">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Success!</strong> 
                                           <%
                                           out.println(": " + updatesuccessStr);
                                           %>
                                    </div>

                                    <%                                
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS,null);
                                  } 



                      %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Staff list : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">

              <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Position</th>
                        <th>Username </th>
                        <th>Emp No </th>
                        <th>First name</th>
                        <th>Middle name </th>
                        <th>Last name</th>
                        <th>Gender </th>
                        <th>Phone </th>
                        <th>ID No</th>
                        <th>Subjects </th>
                        <th>More </th>
                        <th>Update </th>
                        <th id ="statusCell">Status</th>

                        
                        
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       //int count = 1;
                       String gender = "";
                       String formatedFirstname = " ";
                       String formatedLastname = " ";
                       String formatedSurname = " ";

                     
                       String staffid = "";
                       String staffCategory = "";
                          

                        if(staffList !=null){
                       for(Staff s : staffList) { 
                              staff_status = "";
                              staffid = s.getUuid();
                              staffCategory = s.getCategory();
              if ((element = statisticsCache.get(accountuuid)) != null) {
                  statistics = (SessionStatistics) element.getObjectValue();
              }

                         if(online.get(s.getUuid()) !=null){
                  value_sessionId = online.get(s.getUuid());
                  staff_status = "Online";
                  }else{
                  value_sessionId = "";
                   staff_status = "Offline";
                 }                               
  
                               
                             if(staffHash.get(s.getUuid()) !=null){
                             staffdetail = staffHash.get(s.getUuid());
                            formatedFirstname = StringUtils.capitalize(staffdetail.getFirstName().toLowerCase());
                            formatedLastname =StringUtils.capitalize(staffdetail.getLastName().toLowerCase());
                            formatedSurname = StringUtils.capitalize(staffdetail.getSurname().toLowerCase());
                                     }


                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + ussdCount + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + positionHash.get(s.getPositionUuid())  + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + s.getUserName() + "</td>"); 
                            if(staffdetail !=null){

                               if(StringUtils.equalsIgnoreCase(staffdetail.getGender(), "FEMALE")) {
                                gender = "F";
                                     }else{
                                    gender = "M";
                                 }

                           

                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getEmployeeNo() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + formatedFirstname + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + formatedLastname + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + formatedSurname + "</td>");
                             out.println("<td width=\"5%\" class=\"center\">" + gender + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getPhone() + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + staffdetail.getNationalID() + "</td>"); 
                            
                            
                                            }  %>


                             <%if(StringUtils.equalsIgnoreCase(staffCategory, "Teaching")) { %>

                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action="mySubjects.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="Subject" id="submit" value="Subjects" /> 
                                </form>                          
                                </td>    
                                <%}else{%>   

                                <td class="center" width="5%">
                                <form name="Subject" method="POST" action=""> 
                                <input type="hidden" name="" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="" /> 
                                </form>                          
                                </td>   

                                <%}%>      

                                <td class="center" width="5%">
                                <form name="view" method="POST" action="viewStaff.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="View" /> 
                                </form>                          
                                </td>   

                                <td class="center" width="5%">
                                <form name="update" method="POST" action="updateStaff.jsp"> 
                                <input type="hidden" name="staffuuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                                </form>                          
                                </td>   
                                  
                                  <%
                                    if(StringUtils.equalsIgnoreCase(staff_status,"Online")){
                                  %>

                                <td class="center" width="5%" >  
                                <%
                                    out.println("<p style='color:#FF4500;'>");                                 
                                    out.println(" " + staff_status);
                                    out.println("</p>");   
                                %>            
                                </td> 

                                <%
                                  }else{ %>

                                  <td class="center" width="5%" >  
                                  <%
                                    out.println("<p style='color:#8B4789;'>");                                 
                                    out.println(" " + staff_status);
                                    out.println("</p>");   
                                  %>            
                                 </td> 
 
                                <%}%> 



                             <%

                           ussdCount++;
                          } 
                     }
                    %>
                    
                    </tbody>
            </table> 

            <div class="pagination">
                <form name="pageForm" method="post" action="staff.jsp">                                
                    <%                                            
                        if (!staffPage.isFirstPage()) {
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="First" />
                        <input class="toolbarBtn" type="submit" name="page" value="Previous" />
                    <%
                        }
                    %>
                    <span class="pageInfo">Page 
                        <span class="pagePosition currentPage"><%= staffPage.getPageNum()%></span> of 
                        <span class="pagePosition"><%=staffPage.getTotalPage()%></span>
                    </span>   
                    <%
                        if (!staffPage.isLastPage()) {                        
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="Next">  
                        <input class="toolbarBtn" type="submit" name="page" value="Last">
                    <%
                       }
                    %>                                
                </form>
            </div>
               
               

        </div>  <!-- end panel body -->
               <div class="panel-footer">
                      <div class="row">
                   <div class="col col-xs-4"> 
                       <small> <i> Without teachers there is no world. </i> </small>
                   </div>
                    </div>
              </div>
      </div>  <!-- end panel  -->
    </div>  
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />


