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
                    <h2>Select an Exam below<small> Click Generate when done</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>                      
                    </ul>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <br />

                    <form class="form-horizontal form-label-left">


                      <div class="form-group">
                        <label class="col-md-3 col-sm-3 col-xs-12 control-label">Check 1
                          <br>
                          <small class="text-navy">11</small>
                        </label>

                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <div class="checkbox">
                            <label>
                              <input type="checkbox" value=""> Option 1
                            </label>
                          </div>

                          <div class="checkbox">
                            <label>
                              <input type="checkbox" value=""> 11
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" checked="" value="option1" id="optionsRadios1" name="optionsRadios"> Option 2
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" value="option2" id="optionsRadios2" name="optionsRadios"> 22
                            </label>
                          </div>

                        </div>
                      </div>

                      <div class="form-group">
                        <label class="col-md-3 col-sm-3 col-xs-12 control-label">Check2
                          <br>
                          <small class="text-navy">22</small>
                        </label>

                        <div class="col-md-9 col-sm-9 col-xs-12">

                          <div class="checkbox">
                            <label>
                              <input type="checkbox" class="flat" checked="checked"> Checked
                            </label>
                          </div>

                          <div class="checkbox">
                            <label>
                              <input type="checkbox" class="flat"> Unchecked
                            </label>
                          </div>
                          
                          <div class="checkbox">
                            <label>
                              <input type="checkbox" class="flat" disabled="disabled" checked="checked"> Disabled & checked
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" class="flat" checked name="iCheck"> Checked
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" class="flat" name="iCheck"> Unchecked
                            </label>
                          </div>

                          <div class="radio">
                            <label>
                              <input type="radio" class="flat" name="iCheck3" disabled="disabled" checked> Disabled & Checked
                            </label>
                          </div>

                        </div>
                      </div>

                      <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Switch</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <div class="">
                            <label>
                              <input type="checkbox" class="js-switch" checked /> Checked
                            </label>
                          </div>

                          <div class="">
                            <label>
                              <input type="checkbox" class="js-switch" /> Unchecked
                            </label>
                          </div>

                          <div class="">
                            <label>
                              <input type="checkbox" class="js-switch" disabled="disabled" /> Disabled
                            </label>
                          </div>

                          <div class="">
                            <label>
                              <input type="checkbox" class="js-switch" disabled="disabled" checked="checked" /> Disabled Checked
                            </label>
                          </div>
                        </div>
                      </div>


                      <div class="ln_solid"></div>
                      <div class="form-group">
                        <div class="col-md-9 col-sm-9 col-xs-12 col-md-offset-3">
                          <button type="button" class="btn btn-primary">Back</button>
                          <button type="reset" class="btn btn-primary">Reset</button>
                          <button type="submit" class="btn btn-success">Generate</button>
                        </div>
                      </div>

                    </form>
                  </div>
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
        