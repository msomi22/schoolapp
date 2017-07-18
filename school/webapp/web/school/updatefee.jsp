<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>


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
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

   
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STUDENT FEE MANAGEMENT PANEL(FEE UPDATE): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="fee.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>


<div class="row-fluid sortable">



               



    <div class="box span12">
        <div class="box-content">

                   <%


                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);
                                addError = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS); 

                                                    


                                if (StringUtils.isNotEmpty(addError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, null);
                                  } 
          
            
                     %>
                               
                             
      <p color="red"> Confirm that the amount and Bank slip number shown below are correct. <br>
       Then replace the amount with new correct ammount, Note that the new correct amount should be less <br>
       than the original amount </p>




             <table class="table ">
                <thead>
                    <tr >             
                        <th>Student AdmNo</th>
                        <th>Student name</th>
                    </tr>
                </thead>   
                <tbody >
                    <%  
                               out.println("<tr>"); 
                               out.println("<td width=\"10%\" class=\"center\">" + request.getParameter("admNumber") + "</td>");  
                               out.println("<td width=\"10%\" class=\"center\">" + request.getParameter("fullname") + "</td>");    
                             
                    %> 

                </tbody>                  
            </table>  


                  <form  class="form-horizontal"   action="updateFeeDetail" method="POST" >
                  <fieldset>
                              

                              <div class="control-group">
                                  <label class="control-label" for="name">Bank Slip Number*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="transactionid"
                                        value="<%=request.getParameter("transactionid")%>" readonly >
                                  </div>
                              </div> 

                               <div class="control-group">
                                  <label class="control-label" for="name">Amount Paid*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="text" name="amountpaid"
                                        value="<%=request.getParameter("amountpaid")%>"  >
                                  </div>
                              </div> 

                               <div class="control-group">
                                  <label class="control-label" for="schoolpassword">School Password*:</label>
                                  <div class="controls">
                                      <input class="input-xlarge focused" id="receiver" type="password" name="schoolpassword"
                                        value=""  >
                                  </div>
                              </div> 


                              <div class="form-actions">
                               <input type="hidden" name="amountpaidold" value="<%=request.getParameter("amountpaid")%>" >
                               <input type="hidden" name="systemuser" value="<%=staffUsername%>" >
                               <input type="hidden" name="schooluuid" value="<%=accountuuid%>" >
                               <input type="hidden" name="studentuuid" value="<%=request.getParameter("studentuuid")%>" >
                               <button type="submit" class="btn btn-primary">Update</button>
                              </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
