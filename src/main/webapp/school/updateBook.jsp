<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

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
        <li>  <a href="lib.jsp">Back</a>  </li>
       </div>

         
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                LIBRARY - Update Book : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">

              <form class="form-horizontal" role="form"  action="updateBook" method="POST" >
                <div class="form-group">
                    <label for="Book ISBN" class="col-sm-3 control-label">Book ISBN*:</label>
                    <div class="col-sm-9">
                        <input type="text" name="bkISBN" placeholder="Book ISBN" class="form-control" autofocus
                        value='<%=request.getParameter("bkISBN")%>' required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="Book Author" class="col-sm-3 control-label">Book Author*:</label>
                    <div class="col-sm-9">
                        <input type="text" name="bkAUTHOR" placeholder="Book Author" class="form-control" required autofocus
                        value='<%=request.getParameter("bkAUTHOR")%>' style="text-transform: capitalize;">
                    </div>
                </div>
                <div class="form-group">
                    <label for="Book Publisher" class="col-sm-3 control-label">Book Publisher*:</label>
                    <div class="col-sm-9">
                        <input type="text" name="bkPUBLISHER" placeholder="Book Publisher" class="form-control" required autofocus
                        value='<%=request.getParameter("bkPUBLISHER")%>' style="text-transform: capitalize;" >
                    </div>
                </div>
                <div class="form-group">
                    <label for="Book Title" class="col-sm-3 control-label">Book Title*:</label>
                    <div class="col-sm-9">
                        <input type="text" name="bkTitle" placeholder="Book Title" class="form-control" required autofocus
                        value='<%=request.getParameter("bkTitle")%>' style="text-transform: capitalize;">
                    </div>
                </div>
                <div class="form-group">
                    <label for="Book Category" class="col-sm-3 control-label">Book Category*:</label>
                    <div class="col-sm-9">
                        <select name="bookstatus" class="form-control" required>
                            <option value="">Please select one</option> 
                            <option value="Reference">Reference</option>
                            <option value="Shortloan">Shortloan</option>  
                        </select>
                    </div>
                </div> 
               
                <div class="form-group">
                    <div class="col-sm-9 col-sm-offset-3">
                        <input type="hidden" name="bookuuid" value="<%=request.getParameter("bookuuid")%>">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                        <button type="submit" class="btn btn-primary btn-block">Update</button>
                    </div>
                </div>
            </form> 


      </div> <!-- end <div class="panel-body">-->  
      <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
    </div> <!-- end  <div class="panel panel-info"> -->
  </div> <!-- end <div class="col-sm-9"> -->
 </div> <!-- end <div class="row content"> -->
</div> <!-- end <div class="container-fluid"> -->

<jsp:include page="footer.jsp" />




