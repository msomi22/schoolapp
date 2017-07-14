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
                    
                    <h2>HIDE:</h2>

							<div class="row">
											
											<div>
												<input type="radio" id="points" name="p" value="hp" checked>
												<label for="points">
													<h3>Points</h3>
													<p>Hide points i.e 12, 11, 10, 9, ...</p>
												</label>
											</div>
											
											<div class="">
												<input type="radio" id="grades" name="g" value="hg"
													> <label for="grades">
													<h3>Grades</h3>
													<p>Hide grades i.e A, B, C, D, E, F..</p>
												</label>
											</div>


										</div>
										
										
										
											<h1>Show Fee INFO:</h1>

						<div class="row">
										<div>
											<input type="checkbox" id="fee" name="fee" value="1" checked>
											<label for="fee">
												<h3>FEE</h3>
												<p>Show school fees info i.e balances</p>
											</label>
										</div>
										
							</div>
										
										
									
					<h1>RANK:</h1>

						<div class="row">
										<div>
											<input type="radio" id="pointsrank" name="rank" value="points" checked>
											<label for="pointsrank">
												<h3>Points</h3>
												<p>Rank with points</p>
											</label>
										</div>
										<div>
											<input type="radio" id="gradesrank"  name="rank" value="marks"
												> <label for="gradesrank">
												<h3>Marks</h3>
												<p>Rank with total marks</p>
											</label>
										</div>
										
							</div>
							
							
							<h1>No_ of Subjects:</h1>

						<div class="row">
										<div>
											<input type="radio" id="7sub" name="subjects" value="seven" checked>
											<label for="7sub">
												<h3>7 Subjects</h3>
												<p>Grade 7 subjects</p>
											</label>
										</div>
										<div>
											<input type="radio" id="11sub" name="subjects" value="eleven"
												> <label for="11sub">
												<h3>11 Subjects</h3>
												<p>Garde 11 subjects</p>
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
        