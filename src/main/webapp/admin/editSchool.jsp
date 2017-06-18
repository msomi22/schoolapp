

<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>


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

                    <form class="form-horizontal" action="updateSchool" method="POST"  >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="schoolname">schoolname</label>
                        <div class="controls">
                            <input class="input-xlarge"   name="schoolname" type="text" value="<%=request.getParameter("schoolname")%>">
                        </div>
                    </div>
                        
                    <div class="control-group">
                        <label class="control-label" for="username">username</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="username" type="text" value="<%=request.getParameter("username")%>">
                        </div>
                    </div>

                    
                    <div class="control-group">
                        <label class="control-label" for="mobile">mobile</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="mobile" type="text" value="<%=request.getParameter("mobile")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="email">email</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="email" type="text" value="<%=request.getParameter("email")%>">
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
                        <label class="control-label" for="postaladdress">School Postal Address</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="postaladdress" type="text" value="<%=request.getParameter("postaladdress")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="hometown">School Home Town*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="hometown" type="text" value="<%=request.getParameter("hometown")%>">
                        </div>
                    </div>

                   
                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=request.getParameter("schooluuid")%>">
                        <button type="submit" class="btn btn-primary">Save changes</button>
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



