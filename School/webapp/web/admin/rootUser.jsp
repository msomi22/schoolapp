
<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

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

     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();


     AccountDAO accountDAO = AccountDAO.getInstance();
     List<SchoolAccount> schoolList = new ArrayList(); 
     schoolList = accountDAO.getAllSchools();
%>

 <jsp:include page="header.jsp" />

<div class="row-fluid sortable"> 

    <div class="box span12">
    
      

        <div class="box-content">

        
                     <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                             String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.PRINCIPAL_ADD_SUCCESS, null);
                                  } 


                     %>




            <form  class="form-horizontal"   action="addRootUser" method="POST" >
                <fieldset>

                 <div class="control-group">
                        <label class="control-label" for="SchoolAccount">School Account:</label>
                         <div class="controls">
                            <select name="accountUuid" >
                                <option value="">Please select one</option> 
                               <%
                                    int acount = 1;
                                    if (schoolList != null) {
                                        for (SchoolAccount ac : schoolList) {
                                %>
                                <option value="<%= ac.getUuid()%>"><%=ac.getSchoolName()%></option>
                                <%
                                            acount++;
                                        }
                                    }
                                    %>
                                
                            </select>                           
                          
                        </div>
                    </div> 




                    <div class="control-group">
                        <label class="control-label" for="name">Principal Username*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="principalusername" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("principalusername")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">Principal Password*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="password" name="principalpassword" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("principalpassword")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">Employee Number:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">FirstName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="firstname" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">LastName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("lastname")) %>"  >
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">SurName*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="surname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("surname")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="gender">Gender*:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="name">NHIF NO:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nhif"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nhif")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="name">NSSF NO:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nssf"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nssf")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">Phone NO*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">ID NO*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="idno"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("idno")) %>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">County*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">YOB*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("dob")) %>"  >
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