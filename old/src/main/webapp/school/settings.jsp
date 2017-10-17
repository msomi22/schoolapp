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


        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Settings</h3>
              </div>
            </div>
            
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_content">
                  
                  
                  
                  <div class="row">
                  
                  
                  
                  <div class="col-md-8 col-md-offset-2">
                  <br>
                                  
                  <div class="row">
                  
                  <div class="col-md-1 col-md-offset-1">
								 <label for="edit_term">Term:</label> 
								 </div>
								 
								  <div class="col-md-3">
								 
								 <select
									class="form-control formelement cards" id="edit_term" name="term" required>
									<option>1</option>
									<option>2</option>

									<option>3</option>
								</select>
							</div>



							<div class="col-md-1">
								<label for="desc">Year:</label>
								
								 </div>
								 
								  <div class="col-md-3">
								  
								  <input
									type="text"  name="year" id="edit_year"
									class="form-control formelement cards c_year yearConfig"
									placeholder="Enter the Year" pattern="[0-9]{4}" maxlength="4"
									title="Year, should contain numerics only and should be 4 numbers only"
									required> <br>
							</div>
							
							<div class="col-md-2">
							
							
								<input
									type="button"  name="set_btn" id="set_btn"
									class="btn btn-primary btn-block cards"
									value="Submit"> <br>
							</div>
                  
                  </div>
                  
                  </div>
                  
                  </div>
                  
                  
                   <div class="row">
                   
                   <div class="col-md-8 col-md-offset-2">
                    <br>
                  <br>
                  
                  <hr class="hr_list">
                  <br>
                   
                  <div class="col-md-2 col-md-offset-2">
                 
                 
								 <label for="category">Category:</label> 
								 </div>
								 
								  <div class="col-md-4">
								 
								 <select
									class="form-control formelement cards" id="category" name="category" required>
									<option>General</option>
									<option>...</option>
									<option>...</option>
								</select>
							</div>
                  
                  
                  
                 
                  </div>
                  </div>
                  
                  
                  
                  <div class="row" >
                  
                  <div class="col-md-8 col-md-offset-2" id="gradingSystem">
                  <h4>Display the grading system</h4>
                  
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
        