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
     String schoolname = "";


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
    schoolname = school.getSchoolName();
    String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);


    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
                          

 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :PARENTS MANAGEMENT PANEL(UPDATE PARENT INFORMATION): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="parents.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>


<div class="row-fluid sortable">

               




    <div class="box span12">
        <div class="box-content">

        <form class="form-horizontal" action="updateParent" method="POST"  >
                <fieldset>


                     <div class="control-group">
                        <label class="control-label" for="FatherName">Parent 1 Full Name*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="FatherName" type="text" value="<%=request.getParameter("FatherName")%>" style="text-transform: capitalize;">
                        </div>
                    </div>
                    
                    <div class="control-group">
                        <label class="control-label" for="FatherPhone">Parent 1 Phone*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="FatherPhone" type="text" value="<%=request.getParameter("FatherPhone")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="FatherOccupation">Parent 1 Occupation</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="FatherOccupation" type="text" value="<%=request.getParameter("FatherOccupation")%>" style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="FatherID">Parent 1 ID No</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="FatherID" type="text" value="<%=request.getParameter("FatherID")%>">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="FatherEmail">Parent 1 Email</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="FatherEmail" type="text" value="<%=request.getParameter("FatherEmail")%>">
                        </div>
                    </div>







                    <div class="control-group">
                        <label class="control-label" for="MotherName">Parent 2 Full Name</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="MotherName" type="text" value="<%=request.getParameter("MotherName")%>" style="text-transform: capitalize;">
                        </div>
                    </div>
                    
                    <div class="control-group">
                        <label class="control-label" for="MotherPhone">Parent 2 Phone</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="MotherPhone" type="text" value="<%=request.getParameter("MotherPhone")%>">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="MotherOccupation">Parent 2 Occupation</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="MotherOccupation" type="text" value="<%=request.getParameter("MotherOccupation")%>" style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="MotherID">Parent 2 ID No</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="MotherID" type="text" value="<%=request.getParameter("MotherID")%>">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="MotherEmail">Parent 2 Email</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="MotherEmail" type="text" value="<%=request.getParameter("MotherEmail")%>">
                        </div>
                    </div>


                  
                    <div class="form-actions">
                          <input type="hidden" name="studentParentUuid" value="<%=request.getParameter("studentParentUuid")%>">
                          <input type="hidden" name="studentUuid" value="<%=request.getParameter("studentUuid")%>">
                          <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                          <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             


    </div>

</div>


<jsp:include page="footer.jsp" />
