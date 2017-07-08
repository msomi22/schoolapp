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
        //staffList = staffPage.getContents();
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
              <h3 class="panel-title">Staff list </h3>
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


