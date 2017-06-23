<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.account.SmsApi"%>


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
   

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    SmsApiDAO smsApiDAO = SmsApiDAO.getInstance();
    
    ExamConfig examConfig = new ExamConfig();
    if (examConfigDAO.getExamConfig(accountuuid) !=null){
         examConfig = examConfigDAO.getExamConfig(accountuuid);
     }
    
    
      SmsApi smsApi = new SmsApi();
    if(smsApiDAO.getSmsApi(accountuuid) !=null){
          smsApi = smsApiDAO.getSmsApi(accountuuid);
     }
    

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
        <li> <a href="examConfig.jsp">Back</a> </li>
       </div>

                    <%
                                

                                String updateError = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateError = (String) session.getAttribute(SessionConstants.API_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.API_UPDATE_SUCCESS); 

                                if(session != null) {
                                     
                                     updateError = (String) session.getAttribute(SessionConstants.API_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.API_UPDATE_SUCCESS); 
                                }                        

                                

                                  if (StringUtils.isNotEmpty(updateError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.API_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.API_UPDATE_SUCCESS, null);
                                  } 


                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                SMS API SETTINGS : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">

               <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr >
                        <th>API KEY</th>
                        <th>API USERNAME</th>                
                        <th>Action</th>
                    </tr>
                </thead>   
                <tbody>
                   
                    <tr>
                         
                         <td class="center"><%=smsApi.getApiKey()%></td> 
                         <td class="center"><%=smsApi.getApiPassword()%></td>
                         <td class="center">
                                <form name="apiform" method="POST" action="updatesmsApi.jsp"> 
                                <input type="hidden" name="apiKey" value="<%=smsApi.getApiKey()%>">
                                <input type="hidden" name="apiPassword" value="<%=smsApi.getApiPassword()%>">
                                <input type="hidden" name="apiuuid" value="<%=smsApi.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="Update API" /> 
                                </form>                          
                        </td> 
                    </tr>

                </tbody>
            </table>  
        

      </div> <!-- end panel body--> 
                     <div class="panel-footer">
                      <div class="row">
                        <div class="col col-xs-4"> <small> <i> Sasa! APIs are mazing things.</i> </small> </div>
                      </div>
                     </div>
    </div> <!-- end panel --> 
    </div>  
  </div>
</div>

<jsp:include page="footer.jsp" />

