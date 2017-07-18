
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

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

    String accountuuid = "";

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
     <li> <b> <%=schoolname%> :SEND SMS PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
          <li>
            <a href="settings.jsp">Back</a> <span class="divider">/</span>
          </li>

          <li>
            <a href="outPending.jsp">Out Going-Pending</a> <span class="divider">/</span>
          </li>

          <li>
            <a href="outSuccess.jsp">Out Going-Success</a> <span class="divider">/</span>
          </li>

          <li>
            <a href="outFailed.jsp">Out Going-Failed</a> <span class="divider">/</span>
          </li>

           <li>
            <a href="deleteSms.jsp">Delete SMSes</a> <span class="divider">/</span>
          </li>

        
    </ul>
</div>





<div class="row-fluid sortable">


    <div class="box span12">
        <div class="box-content">


                <%
                  
                    

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.SMS_SEND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.SMS_SEND_SUCCESS); 
                                    
                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.SMS_SEND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, null);
                                  } 
                                  

                           
                     %>






                     <form id="SendSMS" name="SendSMS" method="post" action="sendSMS"> <!--sendSMS-->
                                <p>
                                    <label for="destination">Destination</label>
                                    <select id="destination" name="destination">
                                    <option value="Parents">All Parents</option>
                                    <option value="C143978A-E021-4015-BC67-5A00D6C910D1">Parents FORM 1</option>
                                    <option value="3E22E428-3155-42F5-B73E-66553ED501C9">Parents FORM 2</option>
                                    <option value="A4BFC2BD-262F-4207-99C8-057D6ADF80C7">Parents FORM 3</option>
                                    <option value="14E56350-08DA-45CC-97D9-C225AF74A7AD">Parents FORM 4</option>
                                    <option value="Teaching Staff">Teaching Staff</option>
                                    <option value="Non Teaching Staff">Non Teaching Staff</option>
                                    </select>
                                   
                                   
                                </p>
                                <p>
                                    <label for="source">Source</label>
                                    <select id="source" name="source">
                                    <option value="AfricasTalking">AfricasTalking</option>
                                    </select>
                                </p>
                                <p>
                                    <label for="smsText">Message</label>                                
                                    <textarea id="smsText" name="smsText" cols="40" rows="5" 
                                              onFocus="counterOutgoing()" onKeyDown="counterOutgoing()" onKeyUp="counterOutgoing() ">
                                    </textarea>
                                    <span id="characterCounter" class="counter">Characters typed: <span id="characters"></span></span>
                                    <span id="creditCounter" class="counter">SMS credits to be used: <span id="credits"></span></span>
                                </p>                                                                                          
                                <p>
                                    <span id="testSMSButtons">
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                        <button class="button" id="reset" type="button" onclick="initialiseOutgoing('smsText', 'characters', 'credits', 'characterCounter')">Reset</button>
                                        <button type="submit" name="sendSMS" id="sendSMS" class="button">Send SMS</button>
                                    </span>                                    
                                </p>
                            </form>                  

<script type="text/javascript">
	


</script>
            


        
       


    </div>

</div>
<jsp:include page="footer.jsp" />
