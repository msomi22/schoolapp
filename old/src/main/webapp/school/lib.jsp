<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.book.BookDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.book.Book"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.pagination.book.BookPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.book.BookPage"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

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
    BookDAO bookDAO = BookDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    List<Book> bookslist = new ArrayList<Book>();
    bookslist = bookDAO.getBookList(accountuuid,0,15);

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
          //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");

    int ussdCount = 0;
     BookPaginator paginator = new BookPaginator(accountuuid);
     BookPage bookPage;

     bookPage = (BookPage) session.getAttribute("currentPage4");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (bookPage == null
                || !StringUtils.endsWith(referrer, "lib.jsp")
                || StringUtils.equalsIgnoreCase(pageParam, "first")) {
              bookPage = paginator.getFirstPage();

            //We are to give the last page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
             bookPage = paginator.getLastPage();

            // We are to give the previous page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
            bookPage = paginator.getPrevPage(bookPage);

            // We are to give the next page 
        } else if (StringUtils.equalsIgnoreCase(pageParam, "next"))  {
           bookPage = paginator.getNextPage(bookPage);
        }

        session.setAttribute("currentPage4", bookPage);
        bookslist = bookPage.getContents();
        ussdCount = (bookPage.getPageNum() - 1) * bookPage.getPagesize() + 1;
                             

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
           <li> <a href="newBook.jsp">New Book</a> </li> 
           <li> <a href="return.jsp">Return</a>  </li>
         </div>

               <%
                                String borrowError = "";
                                String borrowSuccess = "";

                                String updateError = "";
                                String updateSuccess = "";
                                session = request.getSession(false);
                                     borrowError = (String) session.getAttribute(SessionConstants.BOOK_BORROW_ERROR);
                                     borrowSuccess = (String) session.getAttribute(SessionConstants.BOOK_BORROW_SUCCESS); 

                                     updateError = (String) session.getAttribute(SessionConstants.BOOK_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.BOOK_UPDATE_SUCCESS); 

                                if(session != null) {
                                     borrowError = (String) session.getAttribute(SessionConstants.BOOK_BORROW_ERROR);
                                     borrowSuccess = (String) session.getAttribute(SessionConstants.BOOK_BORROW_SUCCESS);

                                     updateError = (String) session.getAttribute(SessionConstants.BOOK_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.BOOK_UPDATE_SUCCESS); 
                                }                        


                                  if (StringUtils.isNotEmpty(borrowError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(borrowError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(borrowSuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(borrowSuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.BOOK_BORROW_SUCCESS, null);
                                  } 


                                  if (StringUtils.isNotEmpty(updateError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(updateError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(updateSuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.BOOK_UPDATE_SUCCESS, null);
                                  } 


                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> LIBRARY MANAGEMENT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>  </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>ISBN</th>
                        <th>Author</th>                
                        <th>Publisher</th>
                        <th>Title</th>
                        <th>BookStatus</th>
                        <th>BorrowStatus</th>
                        <th>Action</th>
                        <th>Action</th>
                    </tr>
                </thead>   
                <tbody>
                    <%
                     //int count = 1;
                    for(Book bk : bookslist){
                     %>

                    <tr>
                         <td width="3%"><%=ussdCount%></td>
                         <td class="center"><%=bk.getISBN()%></td> 
                         <td class="center"><%=bk.getAuthor()%></td>
                         <td class="center"><%=bk.getPublisher()%></td>
                         <td class="center"><%=bk.getTitle()%></td>
                         <td class="center"><%=bk.getBookStatus()%></td>
                         <td class="center"><%=bk.getBorrowStatus()%></td>
                         <td class="center">
                                <form name="Subject" method="POST" action="borrow.jsp"> 
                                <input type="hidden" name="bkTitle" value="<%=bk.getTitle()%>">
                                <input type="hidden" name="bookuuid" value="<%=bk.getUuid()%>">
                                <input type="hidden" name="bkISBN" value="<%=bk.getISBN()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="Borrow" /> 
                                </form>                          
                        </td> 

                        <td class="center">
                                <form name="" method="POST" action="updateBook.jsp"> 
                                <input type="hidden" name="bookuuid" value="<%=bk.getUuid()%>">
                                 <input type="hidden" name="bkISBN" value="<%=bk.getISBN()%>">
                                 <input type="hidden" name="bkAUTHOR" value="<%=bk.getAuthor()%>">
                                  <input type="hidden" name="bkPUBLISHER" value="<%=bk.getPublisher()%>">
                                   <input type="hidden" name="bkTitle" value="<%=bk.getTitle()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="Update" /> 
                                </form>                          
                        </td> 
                    </tr>

                    <%
                          ussdCount++;
                       }
                            
                    %>
                </tbody>
            </table>  

            <div class="pagination">
                <form name="pageForm" method="post" action="lib.jsp">                                
                    <%                                            
                        if (!bookPage.isFirstPage()) {
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="First" />
                        <input class="toolbarBtn" type="submit" name="page" value="Previous" />
                    <%
                        }
                    %>
                    <span class="pageInfo">Page 
                        <span class="pagePosition currentPage"><%= bookPage.getPageNum()%></span> of 
                        <span class="pagePosition"><%=bookPage.getTotalPage()%></span>
                    </span>   
                    <%
                        if (!bookPage.isLastPage()) {                        
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

