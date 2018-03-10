<!DOCTYPE html>

<%@page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>
<%@page import="ke.co.qubintel.school.server.servlet.util.FontImageGenerator"%>
<%@page import="ke.co.qubintel.school.server.servlet.util.PropertiesConfig"%>

<%@page import="ke.co.qubintel.school.server.session.AdminSessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Calendar" %>



<%


    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();   
    textEncryptor.setPassword(PropertiesConfig.getConfigValue("ENCRYPT_PASSWORD")); 
      
    String captcha = RandomStringUtils.randomAlphabetic(4); 
    String encryptedCaptcha = textEncryptor.encrypt(captcha);


%>


<html lang="en">
<head>
  <title>Admin Login Page </title>
  <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.css">
  <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.css">
  <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.min.css">
    
  <script src="../js/bootstrap/jquery-3.1.1.min.js"></script>
  <script src="../js/bootstrap/bootstrap-modal.js"></script>
  
     <!-- Mobile first -->
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="icon" href="../img/favicon.ico">

  <style type="text/css">

        .colorgraph {
          height: 5px;
          border-top: 0;
          background: #c4e17f;
          border-radius: 5px;
          background-image: -webkit-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: -moz-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: -o-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
          background-image: linear-gradient(to right, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
        }
          </style>
</head>

<body>


        <div class="container" style="margin-top:30px">
        <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
        <div class="panel-heading"><h3 class="panel-title"><strong>Sign in </strong></h3>
            <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
        </div>
        
        <div class="panel-body">

              <form class="form-horizontal"  role="form" method="POST" action="adminLogin">
                 <h2>Please Sign in. <small>You are amazing and always will be.</small></h2>

                  <%
                                String loginErrStr = "";
                                session = request.getSession(false);

                                if(session != null) {
                                   loginErrStr = (String) session.getAttribute(AdminSessionConstants.ADMIN_SIGN_IN_ERROR_KEY);
                                }                        

                                if (StringUtils.isNotEmpty(loginErrStr)) {
                                    %>


                                    <div class="alert alert-warning">
                                      <a href="#" class="close" data-dismiss="alert">
                                            &times;
                                      </a>
                                      <strong>Warning!</strong> 
                                             <%
                                             out.println("Login error: " + loginErrStr);
                                             %>
                                        </div>

                                    <%                                 
                                    session.setAttribute(AdminSessionConstants.ADMIN_SIGN_IN_ERROR_KEY, null);
                                  } 

                     %>

                    <hr class="colorgraph">

                         <div class="form-group">
                        <label for="Username" class="col-sm-3 control-label">Username</label>
                        <div class="col-sm-9">
                            <input type="text" name="username" placeholder="Username" class="form-control" required autocomplete="off">
                        </div>
                        </div>

                    

                    <div class="form-group">
                        <label for="password" class="col-sm-3 control-label">Password</label>
                        <div class="col-sm-9">
                            <input type="password" name="password" placeholder="Password" class="form-control" required autocomplete="off">
                        </div>
                    </div>

                       <%
                             String fontImageUrl = "../fontImageGenerator?text=" + URLEncoder.encode(encryptedCaptcha, "UTF-8");
                           %> 

                           <p class="text-center"> 
                           <span id="captchaGuidelines">Type the characters you see in the image below</span>
                           </p>

                           <div class="form-group">
                           <label for="Captcha" class="col-sm-3 control-label">                          
                                
                           </label>
                            <div class="col-sm-9">
                               <img id="captcha" src=<% out.println("\"" + fontImageUrl + "\"");%> width="100" height="40" />
                            </div>
                       </div>

                      <div class="form-group">  
                            <label for="Captcha" class="col-sm-3 control-label">Captcha</label>                                  
                                    <div class="col-sm-9">
                                        <input type="text" name="captchaAnswer"  class="input_normal"/>
                                        <input type="hidden" name="captchaHidden" 
                                         value=<% out.println("\"" + URLEncoder.encode(encryptedCaptcha, "UTF-8") + "\"");%> />
                                    </div>
                      </div>
            
                  <div class="row">
                      <div class="col-xs-8 col-sm-9 col-md-9">
                           By clicking <strong class="label label-primary">Login</strong>, you agree to our <a href="#" data-toggle="modal" data-target="#t_and_c_m">Terms and Conditions</a>.
                      </div>
                  </div>
            
                        <hr class="colorgraph">
                        
                  <div class="form-group">
                        <div class="col-sm-9 col-sm-offset-3">
                              <button type="submit" class="btn btn-primary btn-block">Login</button>
                        </div>
                      </div>
  
                         <hr style="margin-top:10px;margin-bottom:10px;" >
                          

              </form>
      </div>
 </div>

<!-- Modal -->
<div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
            </div>
            <div class="modal-body">
                <p>Permission is hereby granted, to a school/person obtaining a copy
              of this software and associated documentation files (the "Software"), to
              use the Software, subject to the following conditions:</p>

              <p>The school/person pays a fee not less than KSH 20,000 to use the 
              software for a period of not more than 20 years, OR buys the software
              permanently for a cost of KSH 120,000.</p>

              <p>Service fee not less than KSH 1,000 will be required for fixing bugs and
                  for any other kind of maintainace or upgrade relating to this product.</p>

              <p>You can not ligally use this software, sell the software or part of
              the software without Licensor's permission.</p>

              <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
              IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
              FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
              AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
              LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
              FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
              IN THE SOFTWARE.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</div>
</body>
</html>


