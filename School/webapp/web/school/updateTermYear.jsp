
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
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
     final int YEAR = calendar.get(Calendar.YEAR);
    
 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :CHANGE TERM/YEAR: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="examConfig.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>



<div class="row-fluid sortable">




    <div class="box span12">
         <div class="box-content">

        

            <form class="form-horizontal" action="updateTermYear" method="POST"  >
                <fieldset>

                    <div class="control-group">
                        <label class="control-label" for="Term">TERM</label>
                        <div class="controls">
                            <input class="input-xlarge"   name="term" type="text" value="<%=request.getParameter("term")%>">
                        </div>
                    </div>
                        
                    <div class="control-group">
                        <label class="control-label" for="Year">YEAR</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="year" type="text" value="<%=request.getParameter("year")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="TermYearSecKey">Security key</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="TermYearSecKey" type="text" value="" style="color: white;" autocomplete="off">
                        </div>
                    </div>

                    
                    <div class="form-actions">
                        <input class="input-xlarge focused"   name="exammode" type="hidden" value="<%=request.getParameter("exammode")%>">
                        <input class="input-xlarge focused"  name="exam" type="hidden" value="<%=request.getParameter("exam")%>">
                        <input type="hidden" name="schoolUuid" value="<%=request.getParameter("schoolUuid")%>">
                        <button type="submit" class="btn btn-primary">Save changes</button>
                    </div>

                </fieldset>
            </form>
  
       </div>
</div>


<jsp:include page="footer.jsp" />
