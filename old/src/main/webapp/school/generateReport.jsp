<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
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
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
   
%>
<jsp:include page="header.jsp" />
  <!-- Custom report style -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">

  <link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Roboto:400,700'>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/customReportStyle.css">




        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Result Generation Window</h3>
              </div>
            </div>
            
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_content">






            <div class="row">
             
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Customize Exam Report display<small> Click Generate when done</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>                      
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />
                    
                    
                    <form  action="studentReportCard" class="col-md-6 col-md-offset-3" method="get" target="_blank">
                    
                   
                    <div class="row">
                    
                    <div class="col-md-6 col-md-offset-3">
                    
                    <h2>Exam</h2>
                    
                    <select id="exam" class="form-control formelement" multiple>
                    
                    <option>CAT 1</option>
                    <option>CAT 2</option>
                    <option>MID-TERM</option>
                    <option>END-TERM</option>
                    <option>Math's Contest</option>
                    <option>Easy challenge</option>
                    
                    </select>
                    
                    </div>
                    
                    
                    
                    </div>
                    
                    
                    
                    <div class="row">
                     
                    <div class="col-md-5 col-md-offset-1">
                    <h2>Class</h2>
                    
                    
   
                    <select class= "form-control formelement" >
                    
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    
                    </select>
                    
                    
                    
                    </div>
                    
                    
                    <div class="col-md-5 col-md-offset-1">
                    
                    <h2>Stream</h2>
                    
                    <select class= "form-control formelement"  >
                    
                    <option>1 s</option>
                    <option>1 Q</option>
                    <option>1 R</option>
                    <option>1 T</option>
                    <option>2 s</option>
                    <option>2 Q</option>
                    <option>2 R</option>
                    <option>2 T</option>
                    
                    
                    </select>
                    
                    </div>
                    
                    
                    </div>
                    
                    <h2>HIDE Points:</h2>

							<div class="row">
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="pointshide" class="form-control" name="p" value="true" checked>
												<label for="pointshide">
													<h6>Yes</h6>
													
												</label>
											</div>
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="points" name="p" value="false">
												<label for="points">
													<h6>No</h6>
													
												</label>
											</div>
											
							</div>
							
						 <h2>HIDE Grades:</h2>	
							
							<div class="row">
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="gradeshide" name="g" value="true"
													checked> <label for="gradeshide">
													<h6>YES</h6>
												</label>
											</div>
											
											<div class="col-md-5 col-md-offset-1">
												<input type="radio" id="grades" name="g" value="false"> <label for="grades">
													<h6>NO</h6>
												</label>
											</div>


										</div>
										
										
										
											<h2>Show Fee INFO:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="fee" name="fee" value="true" checked>
											<label for="fee">
												<h6>YES</h6>
											</label>
										</div>
										
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="feehidden" name="fee" value="false">
											<label for="feehidden">
												<h6>NO</h6>
											</label>
										</div>
										
							</div>
							
							
										
										
						
										
										
									
					<h2>RANK:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1" >
											<input type="radio" id="pointsrank" name="rank" value="points" checked>
											<label for="pointsrank">
												<h6>Rank with points</h6>
												
											</label>
										</div>
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="gradesrank"  name="rank" value="marks"
												> <label for="gradesrank">
												<h6>Rank with total marks</h6>
												
											</label>
										</div>
										
							</div>
							
							
							<h2>No_ of Subjects:</h2>

						<div class="row">
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="7sub" name="subjects" value="seven" checked>
											<label for="7sub">
												<h6>Grade 7 subjects</h6>
												
											</label>
										</div >
										<div class="col-md-5 col-md-offset-1">
											<input type="radio" id="11sub" name="subjects" value="eleven"
												> <label for="11sub">
												<h6>Grade 11 subjects</h6>
												
											</label>
										</div>
										
							</div>
							
							
						
							
							<br>
							
							<div class="row">
								<div class="col-md-4">
								<button class="btn btn-primary">Back</button>
								<button type="reset" class="btn btn-primary">Reset</button>
								</div>
								
								
								
								<div class="col-md-2 pull-right">
								<button type="submit" class="btn btn-lg btn-success">Generate</button>
								</div>
							
							
							</div>
							
							
							</form>
							
										

									</div><!-- ./content -->
                </div>
              </div>

            </div>

                    
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        
       
        
        
        <!-- /page content -->

        <!-- footer -->
        
       
<jsp:include page="footer.jsp" />

 <script src="js/customReportJs.js"></script>
        