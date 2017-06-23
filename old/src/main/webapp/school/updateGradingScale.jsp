<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>


<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR)-1;
    final int YEAR_COUNT = YEAR + 2;
    
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
        <li>   <a href="examConfig.jsp">Back</a>  </li>
       </div>
                             <%             

                                String gSyupdateErrStr = "";
                                String gSyupdatesuccessStr = "";
                                session = request.getSession(false);
                                     gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                     gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS); 

                                if(session != null) {
                                    gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                    gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(gSyupdateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + gSyupdateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.GRADE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(gSyupdatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + gSyupdatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.GRADE_ADD_SUCCESS,null);
                                  } 



                                 %>
            
      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
               EXAM GRADING SCALE: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
                      <div class="panel-body">
                        <form  class="form-horizontal"   action="addGradeScale" method="POST" >
                       
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">A*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="A"
                                      value="<%=request.getParameter("A")%>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">A-*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="A-"
                                      value="<%=request.getParameter("Am") %>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">B+*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="B+"
                                      value="<%=request.getParameter("Bp") %>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">B*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="B"
                                      value="<%=request.getParameter("B")%>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">B-*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="B-"
                                      value="<%=request.getParameter("Bm")%>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">C+*:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="C+"
                                      value="<%=request.getParameter("Cp")%>"  >
                                </div>
                             </div> 

                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">C*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="C"
                                      value="<%=request.getParameter("C")%>"  >
                                </div>
                             </div> 
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">C-*:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="C-"
                                      value="<%=request.getParameter("Cm")%>"  >
                                </div>
                             </div> 
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">D+*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="D+"
                                      value="<%=request.getParameter("Dp")%>"  >
                                </div>
                             </div> 
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">D*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="D"
                                      value="<%=request.getParameter("D")%>"  >
                                </div>
                             </div> 
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">D-*:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="D-"
                                      value="<%=request.getParameter("Dm")%>"  >
                                </div>
                             </div> 
                             <div class="from-group">
                                <label class="col-sm-3 control-label" for="FatherName">E*:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="E"
                                      value="<%=request.getParameter("E")%>"  >
                                </div>
                             </div> 

                                    
                                    
                            <div class="form-actions">
                               <div class="col-sm-9 col-sm-offset-3">
                                  <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                  <button type="submit" class="btn btn-primary btn-block">Save</button>
                                  </div>
                            </div> 
                     </form>


                   </div>  <!-- end panel body-->
                     <div class="panel-footer">
                      <div class="row">
                        <div class="col col-xs-4"> <small> <i> Hi there! You look smart.</i> </small> </div>
                      </div>
                     </div>
              </div>   <!-- end panel -->
          </div>  
    </div> 
  </div>


<jsp:include page="footer.jsp" />


