
<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

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

    String accountuuid = "";

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
          <li> <a href="settings.jsp">Back</a> </li>
          <li> <a href="outPending.jsp">Out Going-Pending</a> </li>
          <li> <a href="outSuccess.jsp">Out Going-Success</a> </li>
          <li> <a href="outFailed.jsp">Out Going-Failed</a> </li>
           <li> <a href="deleteSms.jsp">Delete SMSes</a> </li>
       </div>
                       <%
                  
                    

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.SMS_SEND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.SMS_SEND_SUCCESS); 
                                    
                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.SMS_SEND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, null);
                                  } 
                                  

                           
                     %>




      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
               SEND SMS PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> 
              </h3>
            </div>
            <div class="panel-body">
              <form class="form-horizontal" role="form" method="post" action="sendSMS"> 

                  <div class="form-group">
                        <label for="country" class="col-sm-3 control-label">Destination*:</label>
                        <div class="col-sm-9">
                            <select name="destination" class="form-control"  required>
                                <option value="">select one</option>
                                <option value="Parents">All Parents</option>
                                <option value="C143978A-E021-4015-BC67-5A00D6C910D1">Parents FORM 1</option>
                                <option value="3E22E428-3155-42F5-B73E-66553ED501C9">Parents FORM 2</option>
                                <option value="A4BFC2BD-262F-4207-99C8-057D6ADF80C7">Parents FORM 3</option>
                                <option value="14E56350-08DA-45CC-97D9-C225AF74A7AD">Parents FORM 4</option>
                                <option value="Teaching Staff">Teaching Staff</option>
                                <option value="Non Teaching Staff">Non Teaching Staff</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="Destination" class="col-sm-3 control-label">Destination*:</label>
                        <div class="col-sm-9">
                            <select name="source" class="form-control"  required>
                                <option value="">select one</option>
                                <option value="AfricasTalking">AfricasTalking</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="Message" class="col-sm-3 control-label">Message</label>
                        <div class="col-sm-9">
                         <textarea name="smsText" class="form-control" autofocus required> </textarea>
                         <span id="characterCounter" >Characters typed: <span id="characters"></span></span>
                         <span id="creditCounter" class="counter">SMS credits to be used: <span id="credits"></span></span>
                        </div>
                    </div>

                      <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                               <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                              <button type="submit" class="btn btn-primary btn-block">Send SMS</button>
                        </div>
                      </div>
                      
              </form>              

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


