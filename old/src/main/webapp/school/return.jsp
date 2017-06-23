<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.book.BookDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.book.Book"%>
<%@page import="com.yahoo.petermwenda83.persistence.book.StudentBookDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.book.StudentBook"%>
<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

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
     
    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");

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
    StudentBookDAO studentBookDAO = StudentBookDAO.getInstance();
    BookDAO bookDAO = BookDAO.getInstance();

    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    List<StudentBook> studentBookslist = new ArrayList<StudentBook>();
    List<Book> bookslist = new ArrayList<Book>();

    if(studentBookDAO.getStudentBookList("Borrowed")!=null){
         studentBookslist = studentBookDAO.getStudentBookList("Borrowed");
       }

       if(bookDAO.getBookList(accountuuid)!=null){
           bookslist = bookDAO.getBookList(accountuuid);
         }

    HashMap<String, String> bookHash = new HashMap<String, String>();
    HashMap<String, String> isbnHash = new HashMap<String, String>();
     if(bookslist!=null){
    for(Book bk : bookslist){
        isbnHash.put(bk.getUuid(),bk.getISBN());
        bookHash.put(bk.getUuid(),bk.getTitle());
        }
      }

     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 

     if(studentDAO.getAllStudentList(accountuuid)!=null){
        studentList = studentDAO.getAllStudentList(accountuuid); 
      }
 
     HashMap<String, Student> studentHash = new HashMap<String, Student>();

     if(studentList!=null){
     for(Student stu : studentList){
         studentHash.put(stu.getUuid(),stu);
          }
        }

   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
     SimpleDateFormat formatter;
     formatter = new SimpleDateFormat("dd, MMM yyyy");
                             

 %>






<jsp:include page="header.jsp" />


<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li> <a href="newBook.jsp">New Book</a> </li> 
        <li> <a href="return.jsp">Return</a>  </li>
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
        <li>  <a href="lib.jsp">Back</a> </li>
       </div>
                           <%
                                String returnError = "";
                                String returnSuccess = "";
                                session = request.getSession(false);
                                     returnError = (String) session.getAttribute(SessionConstants.BOOK_RETURN_ERROR);
                                     returnSuccess = (String) session.getAttribute(SessionConstants.BOOK_RETURN_SUCCESS); 

                                if(session != null) {
                                    returnError = (String) session.getAttribute(SessionConstants.BOOK_RETURN_ERROR);
                                    returnSuccess = (String) session.getAttribute(SessionConstants.BOOK_RETURN_SUCCESS);
                                }   

                                if (StringUtils.isNotEmpty(returnError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(returnError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.BOOK_RETURN_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(returnSuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(returnSuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.BOOK_RETURN_SUCCESS, null);
                                  }                      


                     %>


      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
               LIBRARY - Return Book : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> 
              </h3>
            </div>

            <div class="panel-body">
              <div class="table-responsive ">
               <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Adm No</th>
                        <th>Student Name</th>
                        <th>Book ISBN</th>
                        <th>Book Title</th>
                        <th>Borrow Date</th>
                        <th>Action</th>
                    </tr>
                </thead>   
                <tbody>
                    <%
                     int count = 1;
                     if(studentBookslist!=null){
                    for(StudentBook stbk : studentBookslist){
                          Student student = new Student();
                          if(studentHash.get(stbk.getStudentUuid()) !=null){
                             student = studentHash.get(stbk.getStudentUuid()); 
                        
                     %>

                    <tr>
                         <td width="3%"><%=count%></td>
                         <td class="center"><%=student.getAdmno()%></td> 
                         <td class="center"><%=student.getFirstname()+" "+student.getLastname()%></td>
                         <td class="center"><%=isbnHash.get(stbk.getBookUuid())%></td>
                         <td class="center"><%=bookHash.get(stbk.getBookUuid())%></td>
                         <td class="center"><%=formatter.format(stbk.getBorrowDate())%></td>
                        
                         <td class="center">
                                <form name="Subject" method="POST" action="returnBook.jsp"> 

                                 <input type="hidden" name="admNo" value="<%=student.getAdmno()%>">
                                 <input type="hidden" name="stuName" value="<%=student.getFirstname()+" "+student.getLastname()%>">
                                 <input type="hidden" name="bookTitle" value="<%=bookHash.get(stbk.getBookUuid())%>">
                                  
                                 <input type="hidden" name="studentUuid" value="<%=stbk.getStudentUuid()%>">
                                 <input type="hidden" name="bookuuid" value="<%=stbk.getBookUuid()%>">
                                 <input type="hidden" name="bkISBN" value="<%=request.getParameter("bkISBN")%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="Return" /> 
                                </form>                          
                        </td> 
                    </tr>

                    <%
                          count++;
                            }
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

