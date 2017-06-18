<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

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
     String schoolname = "";


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
    schoolname = school.getSchoolName();
    String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);


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
        <li>  <a href="parents.jsp">Back</a>  </li>
       </div>

                   <%                  
                       HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.BOOK_ADD_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                                String addError = "";
                                String addSuccess = "";
                                session = request.getSession(false);
                                     addError = (String) session.getAttribute(SessionConstants.BOOK_ADD_ERROR);
                                     addSuccess = (String) session.getAttribute(SessionConstants.BOOK_ADD_SUCCESS); 

                                if(session != null) {
                                    addError = (String) session.getAttribute(SessionConstants.BOOK_ADD_ERROR);
                                    addSuccess = (String) session.getAttribute(SessionConstants.BOOK_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.BOOK_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.BOOK_ADD_SUCCESS, null);
                                  } 


                     %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                UPDATE PARENT INFORMATION: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> 
              </h3>
            </div>
            <div class="panel-body">
               <form class="form-horizontal" action="updateParent" method="POST"  >
        
                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="FatherName">Parent 1 Full Name*</label>
                        <div class="col-sm-9">
                            <input class="form-control" required  name="FatherName" type="text" value='<%=request.getParameter("FatherName")%>' style="text-transform: capitalize;">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="FatherPhone">Parent 1 Phone*</label>
                        <div class="col-sm-9">
                            <input class="form-control" required   name="FatherPhone" type="text" value='<%=request.getParameter("FatherPhone")%>'>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="FatherOccupation">Parent 1 Occupation</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="FatherOccupation" type="text" value='<%=request.getParameter("FatherOccupation")%>' style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="FatherID">Parent 1 ID No</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="FatherID" type="text" value='<%=request.getParameter("FatherID")%>'>
                        </div>
                    </div>

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="FatherEmail">Parent 1 Email</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="FatherEmail" type="text" value='<%=request.getParameter("FatherEmail")%>'>
                        </div>
                    </div>







                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="MotherName">Parent 2 Full Name</label>
                        <div class="col-sm-9">
                            <input class="form-control"  name="MotherName" type="text" value='<%=request.getParameter("MotherName")%>'style="text-transform: capitalize;">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="MotherPhone">Parent 2 Phone</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="MotherPhone" type="text" value='<%=request.getParameter("MotherPhone")%>'>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="MotherOccupation">Parent 2 Occupation</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="MotherOccupation" type="text" value='<%=request.getParameter("MotherOccupation")%>'style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="MotherID">Parent 2 ID No</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="MotherID" type="text" value='<%=request.getParameter("MotherID")%>'>
                        </div>
                    </div>

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="MotherEmail">Parent 2 Email</label>
                        <div class="col-sm-9">
                            <input class="form-control"   name="MotherEmail" type="text" value='<%=request.getParameter("MotherEmail")%>'>
                        </div>
                    </div>


                  
                    <div class="form-group">
                      <div class="col-sm-9 col-sm-offset-3">
                          <input type="hidden" name="studentParentUuid" value='<%=request.getParameter("studentParentUuid")%>'>
                          <input type="hidden" name="studentUuid" value='<%=request.getParameter("studentUuid")%>'>
                          <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                          <button type="submit" class="btn btn-primary btn-block">Update</button>
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



