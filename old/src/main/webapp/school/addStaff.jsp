<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.AcessLevel"%>


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

     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();


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
            <li> <a href="staff.jsp">Back</a> </li>
       </div>

      

          <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.STAFF_ADD_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_SUCCESS);
                                }   


                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    %>
                                    <div class="alert alert-danger">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Warning!</strong> 
                                           <%
                                          out.println("error: " + addErrStr);
                                           %>
                                    </div>

                                    <%                                 
                                    session.setAttribute(SessionConstants.STAFF_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                   %>
                                    <div class="alert alert-success">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Success!</strong> 
                                           <%
                                           out.println(": " + addsuccessStr);
                                           %>
                                    </div>

                                    <%                                
                                    session.setAttribute(SessionConstants.STAFF_ADD_SUCCESS,null);
                                  } 
                     



                     %>


        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Add staff : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
            </div>
            <div class="panel-body">
              
                <form  class="form-horizontal"  role="form"   action="addStaff" method="POST" >

                       <div class="form-group" >
                        <label class="col-sm-3 control-label" for="name">Position*</label>
                        <div class="col-sm-9">
                            <select name="Position" class="form-control" required>
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (positionList != null) {
                                        for (Position p : positionList) {
                                %>
                                <option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="Category">Staff*:</label>
                         <div class="col-sm-9">
                            <select name="category" class="form-control" required>
                                <option value="">Please select one</option> 
                                <option value="Teaching">Teaching</option>
                                <option value="Non-Teaching">Non-Teaching</option>
                               
                            </select>                           
                          
                        </div>
                    </div> 



                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">Employee Number*:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="employeeNo" required
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo"))%>">                                    
                        </div>
                    </div> 


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">Username*:</label>
                        <div class="col-sm-9">
                         <input class="form-control"  type="text" name="username" required
                            value="<%= StringUtils.trimToEmpty(paramHash.get("username")) %>"  >

                        </div>
                    </div>  

                   


                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">First name*:</label>
                        <div class="col-sm-9">
                            <input class="form-control" ="text" name="firstname" required
                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>" style="text-transform: capitalize;"  >                                    
                        </div>
                    </div> 


                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">Middle name*:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="lastname" required
                              value="<%= StringUtils.trimToEmpty(paramHash.get("lastname")) %>"  style="text-transform: capitalize;">
                        </div>
                    </div> 


                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">Last name:</label>
                        <div class="col-sm-9">
                            <input class="form-control"  type="text" name="surname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("surname")) %>" style="text-transform: capitalize;" >
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
                        <label class="col-sm-3 control-label" for="name">NHIF NO:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="nhif"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nhif")) %>"  >
                        </div>
                    </div> 

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">NSSF No:</label>
                        <div class="col-sm-9">
                            <input class="form-control"  type="text" name="nssf"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nssf")) %>"  >
                        </div>
                    </div> 

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">Phone No*:</label>
                        <div class="col-sm-9">
                            <input class="form-control"  type="text" name="phone" required
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div> 

                     <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">ID No*:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="idno" required
                              value="<%= StringUtils.trimToEmpty(paramHash.get("idno")) %>"  >
                        </div>
                    </div>  

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">County:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="county"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>" style="text-transform: capitalize;" >
                        </div>
                    </div>  

                    <div class="form-group">
                        <label class="col-sm-3 control-label" for="name">YOB:</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="dob" 
                              value="<%= StringUtils.trimToEmpty(paramHash.get("dob")) %>"  >
                        </div>
                    </div>  


                   <div class="form-group">
                      <div class="col-sm-9 col-sm-offset-3">
                                <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                              
                              <button type="submit" class="btn btn-primary btn-block">Register</button>
                        </div>
                    </div>
              </form>


                 
                </div>  <!--panel body end-->
                  <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary">
                          <i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger">
                            <i class="glyphicon glyphicon-remove"></i> </a> 
                        </span>
                    </div>
              </div> <!--panel end-->    
            </div>
      </div>
    </div>
  

<jsp:include page="footer.jsp" />





