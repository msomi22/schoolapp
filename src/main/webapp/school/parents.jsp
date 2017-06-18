<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.guardian.ParentsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.guardian.StudentParent"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.pagination.parent.ParentPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.parent.ParentPage"%>

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
     
     StudentParent studentParent = new StudentParent();
     HashMap<String, StudentParent> studentParentHash = new HashMap<String, StudentParent>();
     ParentsDAO parentsDAO = ParentsDAO.getInstance();
     List<StudentParent> parentList = new ArrayList<StudentParent>(); 
     List<StudentParent> parentList2 = new ArrayList<StudentParent>(); 

     parentList = parentsDAO.getParentList();
     parentList2 = parentsDAO.getParentList(0,15);
     for(StudentParent stuparent : parentList){
        studentParentHash.put(stuparent.getStudentUuid(), stuparent);
        }
    

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
                || !StringUtils.endsWith(referrer, "parents.jsp")
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
          <li> <a href="addParent.jsp">New Parent</a> </li>
       </div>
               <%
                  
                    

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS); 
                               

                                  if (StringUtils.isNotEmpty(updateErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(updateErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(updatesuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, null);
                                  } 
                       
                                  

                           
                     %>
      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> PARENTS PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">
              <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>AdmNo</th>
                        <th>Father Name</th>
                        <th>Father Phone</th>
                        <th>Father Email</th>
                        <th>Father ID</th>
                        <th>Father Occu</th>
                        <th>Mother Name</th>
                        <th>Mother Phone</th>
                        <th>Mother Email</th>
                        <th>Mother ID</th>
                        <th>Mother Occu</th>
                        <th>Update</th>
                        
                            
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                   
                        // int count = 1;
                        if(studentList2 !=null){
                       for(Student ss : studentList2) { 

                      String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(ss.getStatusUuid(),statusUuid)){

                       for(StudentParent stuparent : parentList2){
                            if(StringUtils.equals(ss.getUuid(),stuparent.getStudentUuid())){
                                 studentParent = studentParentHash.get(ss.getUuid());
                             
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + ussdCount + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + ss.getAdmno() + "</td>");                            
                             out.println("<td width=\"13%\" class=\"center\">" + studentParent.getFathername() + "</td>");
                             out.println("<td width=\"10%\" class=\"center\">" + studentParent.getFatherphone() + "</td>"); 
                             out.println("<td width=\"12%\" class=\"center\">" + studentParent.getFatherEmail() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + studentParent.getFatherID() + "</td>");
                             out.println("<td width=\"15%\" class=\"center\">" + studentParent.getFatheroccupation() + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + studentParent.getMothername() + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + studentParent.getMotherphone() + "</td>"); 
                             out.println("<td width=\"12%\" class=\"center\">" + studentParent.getMotherEmail() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + studentParent.getMotherID() + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + studentParent.getMotheroccupation() + "</td>");

                                       %>
                                <td class="center">
                                <form name="view" method="POST" action="updateParent.jsp"> 
                                <input type="hidden" name="studentParentUuid" value="<%=studentParent.getUuid()%>">
                                <input type="hidden" name="studentUuid" value="<%=ss.getUuid()%>">
                                <input type="hidden" name="FatherName" value="<%=studentParent.getFathername()%>">
                                <input type="hidden" name="FatherPhone" value="<%=studentParent.getFatherphone()%>">
                                <input type="hidden" name="FatherOccupation" value="<%=studentParent.getFatheroccupation()%>">
                                <input type="hidden" name="FatherID" value="<%=studentParent.getFatherID()%>">
                                <input type="hidden" name="FatherEmail" value="<%=studentParent.getFatherEmail()%>">
                                <input type="hidden" name="MotherName" value="<%=studentParent.getMothername()%>">
                                <input type="hidden" name="MotherPhone" value="<%=studentParent.getMotherphone()%>">
                                <input type="hidden" name="MotherOccupation" value="<%=studentParent.getMotheroccupation()%>">
                                <input type="hidden" name="MotherID" value="<%=studentParent.getMotherID()%>">
                                <input type="hidden" name="MotherEmail" value="<%=studentParent.getMotherEmail()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Update" /> 
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
                <form name="pageForm" method="post" action="parents.jsp">                                
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

