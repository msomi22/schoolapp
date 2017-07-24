
<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<body>

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

<div class="row-fluid sortable">    
    <div class="box span12">
    <div class="box-header well" data-original-title>
          <p> <a href="adminIndex.jsp">Back</a> </p>
        </div>
      

        <div class="box-content">

        
                     <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_ADD_SUCCESS, null);
                                  } 


                     %>




            <form  class="form-horizontal"   action="addSchool" method="POST" >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="name">School Name*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="schoolname" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("schoolname")) %>"  >

                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="School Username">School Username*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schusername" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("schoolusername")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="School Motto">School Motto*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schoolmotto" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("schoolmotto")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">School Password*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="password" name="schpassword" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("schoolpassword")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">School Phone*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schphone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolphone")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">School Email*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schemail"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolemail")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="schoolwebsite">School Website*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="schoolwebsite"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolwebsite")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="dayBoarding">Day/Boarding status*:</label>
                         <div class="controls">
                            <select name="dayBoarding" >
                                <option value="">Please select one</option> 
                                <option value="NO-D">Day Only</option>
                                 <option value="NO-B">Boarding Only</option>
                                <option value="YES">Both Day and Boarding</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name"> Postal Address*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="postaladdress"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolpostaladdress")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name"> Home Town*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="hometown"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("schoolhometown")) %>"  >
                        </div>
                    </div> 
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save</button>
                    </div>

              </fieldset>
              </form>


 </div>      


  </div>
    </div>
    
  <jsp:include page="footer.jsp" />