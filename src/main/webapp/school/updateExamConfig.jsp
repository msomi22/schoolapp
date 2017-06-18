
<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@ page import="java.util.Calendar" %>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

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
    
      
      int sessiontime = SessionConstants.SESSION_TIMEOUT;
      //out.println(sessiontime);

     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

      Calendar calendar = Calendar.getInstance();
     final int YEAR = calendar.get(Calendar.YEAR);
    
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
        <li>   <a href="examConfig.jsp">Back</a> </li>
       </div>

                   

      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                  UPDATE CONFIGURATIONS : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">
             
               <form class="form-horizontal" action="updateExamConfig" method="POST"  >
                
                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="Exam">EXAM</label>
                        <div class="col-sm-9">
                            <input class="form-control"  name="exam" type="text" value="<%=request.getParameter("exam")%>">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="Exam Mode">EXAM MODE</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="exammode" type="text" value="<%=request.getParameter("exammode")%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="SendSms Enable">SMS send</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="sendSmsEnable" type="text" value="<%=request.getParameter("sendSmsEnable")%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="eTFone">End Term F1</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="eTFone" type="text" value="<%=request.getParameter("eTFone")%>">
                        </div>
                    </div>

                    <hr>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="eT">End Term</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="eT" type="text" value="<%=request.getParameter("eT")%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="eTCtwo">End Term + C2</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="eTCtwo" type="text" value="<%=request.getParameter("eTCtwo")%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="eTConetwo">End Term + C1 + C2</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="eTConetwo" type="text" value="<%=request.getParameter("eTConetwo")%>">
                        </div>
                    </div>

                
                    <div class="form-group">
                      <div class="col-sm-9 col-sm-offset-3">
                        <input name="year" type="hidden" value="<%=request.getParameter("year")%>" readonly>
                        <input  name="term" type="hidden" value="<%=request.getParameter("term")%>" readonly>
                        <input type="hidden" name="schoolUuid" value="<%=request.getParameter("schoolUuid")%>">
                        <button type="submit" class="btn btn-primary btn-block">Save changes</button>
                        </div>
                    </div>
            </form>


             </div>  <!-- end panel body-->
                   <div class="panel-footer">
                      <div class="row">
                        <div class="col col-xs-4"> <small> <i>Niaje! Be carefully here</i> </small> </div>
                      </div>
                  </div>
           </div>  <!-- end panel -->
      </div>  
    </div> 
  </div>


<jsp:include page="footer.jsp" />



