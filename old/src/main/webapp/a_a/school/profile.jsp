<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%

     if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }

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
            <h4><small>PROFILE INFO</small></h4>
       </div>

      

      <!-- profile start -->
      
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Njeru Mwenda Peter</h3>
            </div>
            <div class="panel-body">
              <div class="row">
               <div class="table-responsive ">
                <div class="col-md-3 col-lg-3 " align="center">
                  <img alt="User Pic" src="../img/avatar-300x300.png" class="img-circle img-responsive"> 
                 </div>
                
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td>Department:</td>
                        <td>Programming</td>
                      </tr>
                      <tr>
                        <td>Hire date:</td>
                        <td>20/05/2016</td>
                      </tr>
                      <tr>
                        <td>Date of Birth:</td>
                        <td>31/01/1990</td>
                      </tr>
                   
                         <tr>
                             <tr>
                        <td>Gender:</td>
                        <td>Male</td>
                      </tr>
                        <tr>
                        <td>Home Address:</td>
                        <td>P.O. BOX,51-60400</td>
                      </tr>
                      <tr>
                        <td>Email:</td>
                        <td><a href="">mwendapeter72@gmail.com</a></td>
                      </tr>
                        <td>Phone Number:</td>
                        <td>254-718-953-974</td>  
                      </tr>
                     
                    </tbody>
                  </table>
                  
                  <a href="#" class="btn btn-primary">My Teaching Performance</a>
                </div>
              </div>
            </div>
                 <div class="panel-footer">
                        <a data-original-title="Broadcast Message" data-toggle="tooltip" type="button" class="btn btn-sm btn-primary">
                          <i class="glyphicon glyphicon-envelope"></i></a>
                        <span class="pull-right">
                            <a data-original-title="Edit this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-warning">
                            <i class="glyphicon glyphicon-edit"></i></a>
                            <a data-original-title="Remove this user" data-toggle="tooltip" type="button" class="btn btn-sm btn-danger">
                            <i class="glyphicon glyphicon-remove"></i></a> 
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