<!DOCTYPE html>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>
<%@page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.FontImageGenerator"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Calendar" %>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%

   

     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();

    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();   
    textEncryptor.setPassword(PropertiesConfig.getConfigValue("ENCRYPT_PASSWORD")); 
      
    String captcha = RandomStringUtils.randomAlphabetic(4); 
    String encryptedCaptcha = textEncryptor.encrypt(captcha);


%>


<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Login Form</title>
  <link rel="stylesheet" href="css/style.css">
 
</head>

<body>
 <div class="alert alert-info">
                           




  <div class="container">

                          
         
                         <%
                                String loginErrStr = "";
                                session = request.getSession(false);

                                if(session != null) {
                                    loginErrStr = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR);
                                }                        

                                if (StringUtils.isNotEmpty(loginErrStr)) {
                                    out.println("<p style='color:red;'>");                   
                                    out.println("Login error: " + loginErrStr);
                                    out.println("</p>");                                    
                                    session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, null);
                                  } 



                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("Message : " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, null);
                                  } 

                            %>






    <section class="register">


      <h1>Login to Your School</h1>

      <form autocomplete="off" method="POST" action="schoolLogin" >

      <div class="reg_section personal_info">
      <h3>School Username</h3>
      <input type="text" name="schoolusername" value=""  placeholder="School Username" >
      </div>
      <div class="reg_section password">
      <h3>Your Position</h3>

      <select name="staffposition" >
      
                                     <%
                                    int count = 1;
                                    if (positionList != null) {
                                        for (Position p : positionList) {
                                %>
                                <option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
      </select>
      </div>

       <div class="reg_section password">
       <h3>Staff Username </h3>
       <input autocomplete="off" type="text" name="staffusername" value="" placeholder="Staff username">
       <h3>Staff Passworld</h3>
       <input autocomplete="off" type="password" name="staffpassword"  placeholder="Staff password" />


                       <%
                        String fontImageUrl = "fontImageGenerator?text=" + URLEncoder.encode(encryptedCaptcha, "UTF-8");
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
                      <a href="school/resetPassword.jsp">Forgot Password?</a>

       </div>
      
      <p class="submit"><input type="submit" name="submit" value="Login"></p>
      </form>
    </section>


  </div>


</body>
</html>