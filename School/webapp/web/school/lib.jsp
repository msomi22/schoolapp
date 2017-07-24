<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.book.BookDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.book.Book"%>


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

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    BookDAO bookDAO = BookDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    List<Book> bookslist = new ArrayList<Book>();
    bookslist = bookDAO.getBookList(accountuuid);

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
          //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
                             

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> : LIBRARY MANAGEMENT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
      <li>
            <a href="newBook.jsp">New Book</a> <span class="divider">/</span>
      </li>
       <li>
            <a href="return.jsp">Return</a> <span class="divider">/</span>
      </li>

    </ul>
</div>


<div class="row-fluid sortable">

               
    <div class="box span12">
        <div class="box-content">

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
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + borrowError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.BOOK_BORROW_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(borrowSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + borrowSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.BOOK_BORROW_SUCCESS, null);
                                  } 

                                  if (StringUtils.isNotEmpty(updateError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.BOOK_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.BOOK_UPDATE_SUCCESS, null);
                                  } 


                     %>

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
                     int count = 1;
                    for(Book bk : bookslist){
                     %>

                    <tr>
                         <td width="3%"><%=count%></td>
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
                          count++;
                       }
                            
                    %>
                </tbody>
            </table>  
        
                


    </div>

</div>


<jsp:include page="footer.jsp" />
