<%
/**
  Copyright (c) Fastech Solutions Ltd (Jan 16 2016).

  License
  THIS PRODUCT is Licensed under the Open Software License (the "License"), Version 3.0 .
  You may not use this SOFTWARE NOT UNLESS in compliance with the License.
  You may obtain a copy of the License at: http://opensource.org/licenses/OSL-3.0

  Disclaimer
  This SOFTWARE PRODUCT is provided BY THE PROVIDER "AS-IS".
  THE PROVIDER  makes no representations or warranties of any kind WHATSOEVER concerning the safety,inaccuracies and other harmful results that may arise out of using THE PRODUCT for non-intended purposes.
  THE DEVELOPER will not be liable for ANY data loss AND OR any other harm connected with using this PRODUCT contrary to the spesifications provided by THE PROVIDER in the terms and conditions.

 @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
**/
%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="org.joda.time.MutableDateTime"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

  if (session == null) {
       response.sendRedirect("../index.jsp");
       //return;
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
        //return;
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
    //return;


    Account school = new Account();
    %>
<jsp:include page="header.jsp" />



<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        <li> <a href="addStudent.jsp">New Student</a>  </li>
        <li>  <a href="importexcel.jsp">Import Excel</a> </li>
        <li> <a href="parents.jsp">Parents</a> </li>
        <li> <a href="studentSponsor.jsp">Sponsors</a> </li>
        <li> <a href="studentHouse.jsp">House</a>  </li>
        <li> <a href="studentSubjects.jsp">Student Subject</a>  </li>
        <li> <a href="classTeachersSec.jsp">Class List</a> </li>
        <li> <a href="settings.jsp">More...</a>  </li>
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
           WELCOME TO  
       </div>


      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Can i help you? </a>
        </div>
        <div>
          <form class="navbar-form navbar-left" role="search" action="#" method="get">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="Search By AdmNo" onkeyup="searchstudents(this.value)">
            </div>

               <div class="btn-group">
                  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" >
                         Sort-By <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="">CLASS</a></li>
                    <li><a href="">ADM-NO</a></li>
                    <li><a href="">FIRST-NAME</a></li>
                    <li><a href="">LAST-NAME</a></li>
                  </ul>
              </div>
          </form>
          
        </div>

     </nav>




                <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);
                                     updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                    updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    %>
                                    <div class="alert alert-warning">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Warning!</strong> 
                                           <%
                                          out.println("error: " + updateErr);
                                           %>
                                    </div>

                                    <%                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                   %>
                                    <div class="alert alert-success">
                                    <a href="#" class="close" data-dismiss="alert">
                                          &times;
                                    </a>
                                    <strong>Success!</strong> 
                                           <%
                                           out.println(": " + updateSuccess);
                                           %>
                                    </div>

                                    <%                                
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS,null);
                                  } 



                      %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Student list</h3>
            </div>
            <div class="panel-body">

              <div class="table-responsive ">

                    <table class="table table-bordered">
                    <thead>
                                  <tr>
                                    <th>*</th>
                                    <th>Adm No</th>
                                    <th>Name</th>                
                                    <th>Gender</th>
                                    <th>DOB</th>
                                    <th>Bcert</th>
                                    <th>Class</th>
                                    <th>County</th>
                                    <th>Primary</th>
                                    <th>Index</th>
                                    <th>Marks</th>
                                    <th>Year</th>
                                    <th>Adm Date</th>
                                    <th>Update</th>
                                </tr>
                    </thead>
        
                  <tbody class='tablebody'>
                    
                </tbody>
            </table>  


  
        </div> 
      </div>   <!-- end panel body-->
      <div class="panel-footer">
              <div class="row">
                <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
              </div>
            </div>
      </div>
     </div>   <!-- end panel -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />

