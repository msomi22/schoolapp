<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%
     String accountuuid = "";

     if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }
     
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
   

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

     ExamConfig examConfig = new ExamConfig();
    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    if(examConfigDAO.getExamConfig(accountuuid) !=null){
       examConfig = examConfigDAO.getExamConfig(accountuuid);
    }
   


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
      
                             

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :PROFILE MANAGEMENT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
       <li>
            <a href="chat.jsp">Chat</a> <span class="divider">/</span>
      </li>
     

    </ul>
</div>


<div class="row-fluid sortable">

               
    <div class="box span12">
        <div class="box-content">

                  <%
                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.STAFF_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_UPDATE_SUCCESS, null);
                                  } 

                        
                     %>

            


                 <p>Hi there! do you want some fun? Let me know your birthday. </p>


                  <form  class="form-horizontal"   action="findDOBFun" method="POST" >
                  <fieldset>

                        <div class="control-group">
                          <label class="control-label" for="year">Year of birth  e.g 1995 </label>
                            <div class="controls">
                              <input class="input-xlarge focused" type="text" name="year" value="" required="true"> 
                           </div>
                        </div> 

                      <div class="control-group">
                         <label class="control-label" for="month">Month of birthd  e.g 05 </label>
                           <div class="controls">
                            <input class="input-xlarge focused"  type="text" name="month" value=""required="true">  
                          </div>
                     </div>

                       <div class="control-group">
                          <label class="control-label" for="day">Day of birth  e.g 27 </label>
                              <div class="controls">
                             <input class="input-xlarge focused"   type="text" name="day" value="" required="true"> 
                        </div>
                    
                    </div> 

                      <div class="form-actions">
                        <button type="submit" name="go" value="GO!" class="btn btn-primary">GO!</button>
                     </div>


                  </fieldset>
                  </form>
        

            




                  <h3><i class="icon-edit"></i>Change your password :</h3> 
                  <form  class="form-horizontal"   action="updatePassword" method="POST" >
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
                      <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                        <button type="submit" name="sendsms" value="Send" class="btn btn-primary">Edit Password</button>
                     </div>


                  </fieldset>
                  </form>
        
                


    </div>

</div>



<jsp:include page="footer.jsp" />
