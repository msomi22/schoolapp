
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SmsSend"%>

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
    SmsSendDAO smsSendDAO = SmsSendDAO.getInstance();
    List<SmsSend> smssendList = new ArrayList<SmsSend>(); 
    
    if(smsSendDAO.getSmsSendList("failed")!=null){
      smssendList = smsSendDAO.getSmsSendList("failed"); 
       }

    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
     

                                      

 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :OUTGOING SMSes (PENDING SMSes): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
          <li>
            <a href="sendsms.jsp">Back</a> <span class="divider">/</span> 
          </li> 

        
    </ul>
</div>





<div class="row-fluid sortable">


    <div class="box span12">
        <div class="box-content">  

           <div>
                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Status</th>
                        <th>Phone </th>
                        <th>Message </th>
                        <th>Cost</th>
              
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                     int count = 1;
                     String status = "Pending";
                     if(smssendList!=null){
                    for(SmsSend sms : smssendList){
                    //sms.getStatus()
                     %>

                    <tr>
                        <td width="3%"><%=count%></td>
                         <td width="4%" class="center"><%=status%></td> 
                         <td width="5%" class="center"><%=sms.getPhoneNo()%></td>
                         <td width="20%" class="center"><%=sms.getMessageId()%></td>
                         <td width="4%"class="center"><%=sms.getCost()%></td>

                       
                    </tr>
                   

                    <%
                          count++;
                         }
                       }
                            
                    %>
                </tbody>
            </table>  
            
            </div>
        
       


    </div>

</div>
<jsp:include page="footer.jsp" />
