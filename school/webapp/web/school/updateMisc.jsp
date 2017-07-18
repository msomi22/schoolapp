<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>
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
    String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);


    String schoolname = school.getSchoolName();
    String staffId = request.getParameter("staffuuid");

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
       
    String key = request.getParameter("key");
    String value = request.getParameter("value");


 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> : UPDATE MISCELLANEOUS VARIABLES: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
        <li>
            <a href="examConfig.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>


<div class="row-fluid sortable">

    <div class="box span12">
        <div class="box-content">

        <form class="form-horizontal" action="updateMisc" method="POST"  >
               <fieldset>

                 <%
                  if(StringUtils.contains(key,"CARD")){
                 %>


                 <div class="control-group">
                        <label class="control-label" for="nssf">Key</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="key" type="text" value="<%=key%>" readonly>
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="phone">Value</label>
                        <div class="controls">
                            <input class="jscolor input-xlarge focused" name="value" type="text" value="<%=value%>" size="70">
                        </div>
                    </div>


                 <%} else {%>
                     <div class="control-group">
                        <label class="control-label" for="nssf">Key</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="key" type="text" value="<%=key%>" readonly>
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="phone">Value</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="value" type="text" value="<%=value%>" size="70">
                        </div>
                    </div>

                    <%}%>
                  
                    <div class="form-actions">
                        <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                        <input type="hidden" name="miscUuid" value="<%=request.getParameter("miscUuid")%>">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             
    </div>

</div>


    <script>
    function setTextColor(picker) {
        document.getElementsByTagName('body')[0].style.color = '#' + picker.toString()
    }
    </script>



<jsp:include page="footer.jsp" />
