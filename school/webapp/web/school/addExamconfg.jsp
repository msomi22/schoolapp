<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page import="java.util.Calendar" %>
<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

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
    
      
      int sessiontime = SessionConstants.SESSION_TIMEOUT;
      //out.println(sessiontime);

     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

    Calendar calendar = Calendar.getInstance();
    final int DAYS_IN_MONTH = calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
    final int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
    final int MONTH = calendar.get(Calendar.MONTH) + 1;
    final int YEAR = calendar.get(Calendar.YEAR)-1;
    final int YEAR_COUNT = YEAR + 2;
    
 %>






<jsp:include page="header.jsp" />



<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :EXAM CONFIGURATION PANEL (ADD CONFIGURATIONS): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="examConfig.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>

<div class="row-fluid sortable">




    <div class="box span12">
        <div class="box-content">

        <%
                         HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }

                            
                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS,null);
                                  } 



        %>
            
   

   
    <form  class="form-horizontal"   action="addExamConfig" method="POST" >
                 <fieldset>
                    
                                     

                                <div class="control-group">
                                        <label class="control-label" for="name">Term*:</label>
                                    <div class="controls">
                                          <select name="term" >
                                           <option value="">Please select one</option> 
                                            <option value="1"> 1</option> 
                                             <option value="2"> 2</option> 
                                              <option value="3"> 3</option> 
                                               <option value="4"> 4</option>  
                                               </select>   
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="name">Year*:</label>
                                         <div class="controls">
                                                <select name="Year" id="input" style="max-width:8%;">
                                                        <%
                                                            for (int j = YEAR; j < YEAR_COUNT; j++) {
                                                                if (j == YEAR) {
                                                                    out.println("<option selected=\"selected\" value=\"" + j + "\">" + j + "</option>");
                                                                } else {
                                                                    out.println("<option value=\"" + j + "\">" + j + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                            </div>
                                    </div> 

                                    <div class="control-group">
                                    <label class="control-label" for="name">Exam*:</label>
                                    <div class="controls">
                                          <select name="exam" >
                                           <option value="">Please select one</option> 
                                            <option value="C1"> C1</option> 
                                             <option value="C2"> C2</option> 
                                              <option value="ET"> ET</option> 
                                               <option value="P1"> P1</option>  
                                               <option value="P2"> P2</option>  
                                               <option value="P3"> P3</option>  
                                               </select>   
                                        </div>
                                    </div> 

                                     <div class="control-group">
                                    <label class="control-label" for="name">Exam Mode*:</label>
                                    <div class="controls">
                                          <select name="examMode" >
                                           <option value="">Please select one</option> 
                                            <option value="ON"> ON</option> 
                                             <option value="OFF"> OFF</option> 
                                            
                                               </select>   
                                        </div>
                                    </div> 

                                    
                                    
                                    <div class="form-actions">
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                        <button type="submit" class="btn btn-primary">Save</button>
                                    </div> 

              </fieldset>
              </form>
       











    </div>

</div>


<jsp:include page="footer.jsp" />
