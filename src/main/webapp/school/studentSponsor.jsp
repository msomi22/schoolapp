<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.pagination.sponsor.SponsorPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.sponsor.SponsorPage"%>

<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPage"%>

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

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
     StudentSponsor studentSponsor = new StudentSponsor();
    
     SponsorsDAO sponsorsDAO = SponsorsDAO.getInstance();
     List<StudentSponsor> sponsorList = new ArrayList<StudentSponsor>(); 
     sponsorList = sponsorsDAO.getStudentSponsorList(0,15);
    
   

     HashMap<String, String> admNoHash = new HashMap<String, String>();
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     List<Student> studentList2 = new ArrayList(); 
     studentList = studentDAO.getAllStudentList(accountuuid);  
     studentList2 = studentDAO.getStudentList(school , 0 , 15); 
     for(Student s : studentList){
       admNoHash.put(s.getUuid(),s.getAdmno());
         }

     
    int ussdCount = 0;
     StudentPaginator paginator = new StudentPaginator(accountuuid);
     StudentPage studentpage;

     studentpage = (StudentPage) session.getAttribute("currentPage");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (studentpage == null
                || !StringUtils.endsWith(referrer, "studentSponsor.jsp")
                || StringUtils.equalsIgnoreCase(pageParam, "first")) {
              studentpage = paginator.getFirstPage();

            //We are to give the last page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
             studentpage = paginator.getLastPage();

            // We are to give the previous page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
            studentpage = paginator.getPrevPage(studentpage);

            // We are to give the next page 
        } else if (StringUtils.equalsIgnoreCase(pageParam, "next"))  {
           studentpage = paginator.getNextPage(studentpage);
        }

        session.setAttribute("currentPage", studentpage);
        studentList2 = studentpage.getContents();
        ussdCount = (studentpage.getPageNum() - 1) * studentpage.getPagesize() + 1;

     
                             

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
           <li>  <a href="addSponsor.jsp">New Sponsor</a>  </li>
       </div>


      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">STUDENTS-SPONSOR : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">
               <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Student AdmNo</th>
                        <th>SponsorName</th>
                        <th>SponsorPhone</th>
                        <th>Occupation</th>
                        <th>Country</th>
                        <th>County</th>
                        <th>Update</th>
                       
                            
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                   
                      
                        if(studentList2 !=null){
                        //int count = 1;
                       for(Student ss : studentList2) { 

                           String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                        if(StringUtils.equals(ss.getStatusUuid(),statusUuid)){

                          for(StudentSponsor stusponsor : sponsorList){
                            if(StringUtils.equals(ss.getUuid(),stusponsor.getStudentUuid())){
       
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + ussdCount + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + admNoHash.get(stusponsor.getStudentUuid()) + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorName() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorPhone() + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorOccupation() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorCountry() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorCounty() + "</td>"); 

                             %>
                                <td class="center">
                                <form name="view" method="POST" action=""> 
                                <input type="hidden" name="studentUuid" value="<%=ss.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                          
                               </td>    


                             <%

                                ussdCount++;
                               }
                              }
                          } 
                        }
                     }
                    %>
                    
                    </tbody>
            </table> 

            <div class="pagination">
                <form name="pageForm" method="post" action="studentSponsor.jsp">                                
                    <%                                            
                        if (!studentpage.isFirstPage()) {
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="First" />
                        <input class="toolbarBtn" type="submit" name="page" value="Previous" />
                    <%
                        }
                    %>
                    <span class="pageInfo">Page 
                        <span class="pagePosition currentPage"><%= studentpage.getPageNum()%></span> of 
                        <span class="pagePosition"><%=studentpage.getTotalPage()%></span>
                    </span>   
                    <%
                        if (!studentpage.isLastPage()) {                        
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="Next">  
                        <input class="toolbarBtn" type="submit" name="page" value="Last">
                    <%
                       }
                    %>                                
                </form>
            </div>

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




