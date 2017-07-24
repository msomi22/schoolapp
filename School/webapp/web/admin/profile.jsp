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

<div>
    <ul class="breadcrumb">
     <li> <b> SCHOOL PROFILE MANAGEMENT <b> </li> <br>   

    </ul>
</div>


<div class="row-fluid sortable">

               
    <div class="box span12">
        <div class="box-content">

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

</div>



<jsp:include page="footer.jsp" />
