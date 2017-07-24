<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SmsApi"%>


<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

<%@page import="java.text.SimpleDateFormat"%>


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
    SmsApiDAO smsApiDAO = SmsApiDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);
    SmsApi smsApi = new SmsApi();
    if(smsApiDAO.getSmsApi(accountuuid) !=null){
      smsApi = smsApiDAO.getSmsApi(accountuuid);
    }
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
       

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> : SMS API SETTINGS : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   

      <li>
            <a href="examConfig.jsp">Back</a> <span class="divider">/</span>
     </li>
     
      
    </ul>
</div>


<div class="row-fluid sortable">

               
    <div class="box span12">
        <div class="box-content">

         <%
                                

                                String updateError = "";
                                String updateSuccess = "";
                                session = request.getSession(false);

                                     updateError = (String) session.getAttribute(SessionConstants.API_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.API_UPDATE_SUCCESS); 

                                if(session != null) {
                                     
                                     updateError = (String) session.getAttribute(SessionConstants.API_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.API_UPDATE_SUCCESS); 
                                }                        

                                

                                  if (StringUtils.isNotEmpty(updateError)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateError);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.API_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.API_UPDATE_SUCCESS, null);
                                  } 


                     %>



             <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr >
                        <th>API KEY</th>
                        <th>API USERNAME</th>                
                        <th>Action</th>
                    </tr>
                </thead>   
                <tbody>
                   
                    <tr>
                         
                         <td class="center"><%=smsApi.getApiKey()%></td> 
                         <td class="center"><%=smsApi.getApiPassword()%></td>
                         <td class="center">
                                <form name="apiform" method="POST" action="updatesmsApi.jsp"> 
                                <input type="hidden" name="apiKey" value="<%=smsApi.getApiKey()%>">
                                <input type="hidden" name="apiPassword" value="<%=smsApi.getApiPassword()%>">
                                <input type="hidden" name="apiuuid" value="<%=smsApi.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="" id="submit" value="Update API" /> 
                                </form>                          
                        </td> 
                    </tr>

                </tbody>
            </table>  

    

                


    </div>

</div>


<jsp:include page="footer.jsp" />
