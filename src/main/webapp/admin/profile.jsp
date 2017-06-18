<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>
<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS, null);
                                  } 


                     %>


                  <h3><i class="icon-edit"></i>Change school password :</h3> 
                  <form  class="form-horizontal"   action="updateSchoolPass" method="POST" >
                  <fieldset>

                        <div class="control-group">
                        <label class="control-label" for="network">Old Password</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="password" name="oldpassword" value="" required="true"> 
                        </div>
                        </div> 

                      <div class="control-group">
                        <label class="control-label" for="network">New Password</label>
                        <div class="controls">
                        <input class="input-xlarge focused"  id="txtNewPassword" type="password" name="newpassword" value=""required="true">  
                        </div>
                     </div>

                       <div class="control-group">
                        <label class="control-label" for="network">Confirm Password</label>
                        <div class="controls">
                         <input class="input-xlarge focused"  id="txtConfirmPassword" type="password" name="confirmpassword" value="" required="true" onChange="checkPasswordMatch();"> 
                        </div>
                    
                    </div> 

                      <div class="control-group"> <div class="controls">
                       <div class="registrationFormAlert" id="divCheckPasswordMatch">
                         </div>
                      </div>
                      </div>

                      <div class="form-actions">
                      <input type="hidden" name="schooluuid" value="<%=request.getParameter("schooluuid")%>">
                        <button type="submit" name="sendsms" value="Send" class="btn btn-primary">Update</button>
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



