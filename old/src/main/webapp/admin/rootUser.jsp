
<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.AcessLevelDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.AcessLevel"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<body>

<%
if (session == null) {
        response.sendRedirect("index.jsp");
    }

    String username = (String) session.getAttribute(AdminSessionConstants.ADMIN_SESSION_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("index.jsp");
    }

     session.setMaxInactiveInterval(AdminSessionConstants.SESSION_TIMEOUT);
     response.setHeader("Refresh", AdminSessionConstants.SESSION_TIMEOUT + "; url=Logout");


     AccountDAO accountDAO = AccountDAO.getInstance();
     List<Account> schoolList = new ArrayList(); 
     schoolList = accountDAO.getAccounts();
%>











<jsp:include page="header.jsp" /> 

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"> <a href="adminIndex.jsp">Home</a></li>
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

                       <div class="item">
                      <img src="../img/slide/slide4.jpg" alt="Third slide">
                      <div class="carousel-caption">This Caption 4</div>
                      </div>

                       <div class="item">
                      <img src="../img/slide/slide5.png" alt="Third slide">
                      <div class="carousel-caption">This Caption 5</div>
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
            <h4><small> WELCOME ADMIN </small></h4>
       </div>
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> Schoools </h3>
            </div>

            <div class="panel-body">
                  <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                             String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS, null);
                                  } 


                     %>


                 <form  class="form-horizontal"   action="addRootUser" method="POST" >
                <fieldset>

                 <div class="control-group">
                        <label class="control-label" for="SchoolAccount">School Account:</label>
                         <div class="controls">
                            <select name="accountUuid" >
                                <option value="">Please select one</option> 
                               <%
                                    int acount = 1;
                                    if (schoolList != null) {
                                        for (Account ac : schoolList) {
                                %>
                                <option value="<%= ac.getUuid()%>"><%=ac.getName()%></option>
                                <%
                                            acount++;
                                        }
                                    }
                                    %>
                                
                            </select>                           
                          
                        </div>
                    </div> 




                    <div class="control-group">
                        <label class="control-label" for="name">Principal Username*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="principalusername" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("principalusername")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">Principal Password*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="password" name="principalpassword" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("principalpassword")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">Employee Number:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">FirstName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="firstname" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">LastName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("lastname")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">SurName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="surname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("surname")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="gender">Gender*:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="name">NHIF NO:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nhif"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nhif")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="name">NSSF NO:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nssf"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nssf")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">Phone NO*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">ID NO*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="idno"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("idno")) %>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">County*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">YOB*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("dob")) %>"  >
                        </div>
                    </div>  


                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>

              </fieldset>
              </form>
        

                   
                    
                           </div> 
          </div>  <!-- end panel body-->
          <div class="panel-footer">
                <div class="row">
                  <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
                </div>
            </div>
        </div>
        </div>  <!-- end panel -->
        </div>
        </div>
        </div>


        <jsp:include page="footer.jsp" />


