
<%@page import="ke.co.qubintel.school.server.session.AdminSessionConstants"%>


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
                                     addErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS, null);
                                  } 


                     %>





                     <form  class="form-horizontal"   action="addSchool" method="POST" >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="name">School Name*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="schoolname" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("schoolname")) %>"  >

                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">School Username*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schusername" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("schoolusername")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">School Password*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="password" name="schpassword" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("schoolpassword")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">School Phone*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schphone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolphone")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">School Email*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schemail"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolemail")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="dayBoarding">Day/Boarding status*:</label>
                         <div class="controls">
                            <select name="dayBoarding" >
                                <option value="">Please select one</option> 
                                <option value="NO">Day/Boarding Only</option>
                                <option value="YES">Both Day and Boarding</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name"> Postal Address*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="postaladdress"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolpostaladdress")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name"> Home Town*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="hometown"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolhometown")) %>"  >
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



