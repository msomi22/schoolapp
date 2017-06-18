
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

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

    String pos_Bursar =(String) PropertiesConfig.getConfigValue("POSITION_BURSAR");
    String staffPos = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION);
    
    
    
 %>






<jsp:include page="header.jsp" />



<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li><a href="#section2">Library</a></li>
        <li><a href="#section3">SMSes</a></li>
        <li><a href="#section3">House/Classes</a></li>
        <li><a href="#section3">Parents</a></li>
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
            <li> <a href="studentIndex.jsp">Back</a> </li>
       </div>

      <h4><small> WELCOME TO  <%=schoolname%> : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </small></h4>


        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Update student</h3>
            </div>
            <div class="panel-body">
              <div class="row">

                <form  class="form-horizontal"  role="form"   action="updateStudentBasic" method="POST" >
                                 
                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="AdmissionNumber">Admission Number:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="admNo"
                                      value="<%=request.getParameter("admNo")%>" >  
                                </div>
                             </div> 

                              <%  if(StringUtils.equals(staffPos,pos_Bursar)){ %>

                              <div class="form-group">
                                <label class="col-sm-3 control-label" for="finalYear">Final Year:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="finalYear"
                                      value="<%=request.getParameter("finalYear")%>"  >
                                </div>
                             </div> 

                              <div class="form-group">
                                <label class="col-sm-3 control-label" for="finalTerm">Final Term:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="finalTerm"
                                      value="<%=request.getParameter("finalTerm")%>"  >
                                </div>
                             </div> 

                            
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="StydentType">StydentType*:</label>
                                         <div class="col-sm-9">
                                            <select name="StydentType" >
                                              <option value="Boarder">Boarder</option> 
                                              <option value="Day">Day</option> 
                                            </select>                           
                                          
                                        </div>
                            </div> 

                               <% } else { %>
                                   
                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="finalYear">Final Year:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="finalYear"
                                      value="<%=request.getParameter("finalYear")%>" readonly >
                                </div>
                             </div> 

                              <div class="form-group">
                                <label class="col-sm-3 control-label" for="finalTerm">Final Term:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="finalTerm"
                                      value="<%=request.getParameter("finalTerm")%>"  readonly>
                                </div>
                             </div> 

                               <%}%>

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="firstname">First Name:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="firstname"
                                      value="<%=request.getParameter("firstname") %>"  style="text-transform: capitalize;">
                                </div>
                             </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="lastname">Middle Name:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="lastname"
                                      value="<%=request.getParameter("lastname") %>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="surname">Last Name:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="surname"
                                      value="<%=request.getParameter("surname")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             <div class="form-group">
                                <label for="Gender" class="col-sm-3 control-label">Gender:*</label>
                                <div class="col-sm-9">
                                    <select name="gender" class="form-control" required>
                                       <option value="">Please select one</option> 
                                        <option value="MALE">Male</option>
                                        <option value="FEMALE">Female</option> 
                                    </select>
                                 </div>
                             </div>

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="dob">Date of Birth:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="dob"
                                      value="<%=request.getParameter("dob")%>"  >
                                </div>
                             </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="BcertNo">Birth Cert No:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="BcertNo"
                                      value="<%=request.getParameter("BcertNo")%>"  >
                                </div>
                             </div> 
                            
                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="County">County:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="county"
                                      value="<%=request.getParameter("county")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="Primary">Primary School:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="primary"
                                      value="<%=request.getParameter("primary")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="kcpeindex">KCPE Index:</label>
                                <div class="col-sm-9">
                                <input class="form-control"  type="text" name="kcpeindex"
                                      value="<%=request.getParameter("kcpeindex")%>"  style="text-transform: capitalize;">
                                </div>
                             </div> 
                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="kcpemark">KCPE Marks:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="kcpemark"
                                      value="<%=request.getParameter("kcpemark")%>"  style="text-transform: capitalize;">
                                </div>
                             </div> 

                             <div class="form-group">
                                <label class="col-sm-3 control-label" for="kcpeyear">KCPE Year:</label>
                                <div class="col-sm-9">
                                <input class="form-control" type="text" name="kcpeyear"
                                      value="<%=request.getParameter("kcpeyear")%>"  >
                                </div>
                             </div> 

                              <div class="row">
                                <div class="col-xs-12 col-md-6">  
                                    <input type="hidden" name="studentUuid" value="<%=request.getParameter("studentUuid")%>">
                                    <input type="hidden" name="schoolUuid" value="<%=request.getParameter("schoolUuid")%>">
                                     <button type="submit" class="btn btn-success btn-block btn-lg">Update</button>  
                                </div>
                            </div>
   
              </form>
                
                  
                 
                </div>
              </div>
              <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary">
                          <i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger">
                            <i class="glyphicon glyphicon-remove"></i> </a> 
                        </span>
                    </div>
            </div>
          <!--panel end-->
      </div>



      <hr>
    </div>
  </div>
</div>

<jsp:include page="footer.jsp" />


