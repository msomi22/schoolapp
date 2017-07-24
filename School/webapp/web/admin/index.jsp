<!DOCTYPE html>

<%@page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.FontImageGenerator"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>

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
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Admin Login</title>
  <link rel="stylesheet" href="../css/style.css">
 
</head>

<body>
 <div class="alert alert-info">
                           




  <div class="container">

                          
         
                         <%
                                String loginErrStr = "";
                                session = request.getSession(false);

                                if(session != null) {
                                    loginErrStr = (String) session.getAttribute(AdminSessionConstants.ADMIN_SIGN_IN_ERROR_KEY);
                                }                        

                                if (StringUtils.isNotEmpty(loginErrStr)) {
                                    out.println("<p style='color:red;'>");                   
                                    out.println("Login error: " + loginErrStr);
                                    out.println("</p>");                                    
                                    session.setAttribute(AdminSessionConstants.ADMIN_SIGN_IN_ERROR_KEY, null);
                                  } 
                            %>






    <section class="register">


      <h1>Admin Login</h1>

      <form autocomplete="off" method="POST" action="adminLogin" >

      <div class="reg_section personal_info">
      <h3>Username</h3>
      <input type="text" name="username" value=""  placeholder="Username" >
      </div>
       <div class="reg_section password">
       <h3>Passworld</h3>
       <input autocomplete="off" type="password" name="password"  placeholder="Password" />


                       <%
                        String fontImageUrl = "../fontImageGenerator?text=" + URLEncoder.encode(encryptedCaptcha, "UTF-8");
                        %> 
                        <div class="field_container">
                        <div class='wrapper'>
                    
                            <div id="spam-check">
                                <span id="captchaGuidelines">Type the characters you see in the image below</span><br>
                                <img id="captcha" src=<% out.println("\"" + fontImageUrl + "\"");%> width="80" height="40" /> <br>
                                <input type="text" name="captchaAnswer" id="captchaAnswer" size="5" class="input_normal"/>
                                <input type="hidden" name="captchaHidden" id="captchaHidden"
                                       value=<% out.println("\"" + URLEncoder.encode(encryptedCaptcha, "UTF-8") + "\"");%> />
                            </div>
                        </div>
                      </div>

       </div>
      
      <p class="submit"><input type="submit" name="submit" value="Login"></p>
      </form>
    </section>


  </div>


</body>
</html>