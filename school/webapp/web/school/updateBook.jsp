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
     
          //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
                             

 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> : LIBRARY MANAGEMENT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
     <li>
            <a href="lib.jsp">Back</a> <span class="divider">/</span>
      </li>
    </ul>
</div>

<div class="row-fluid sortable">

               
    <div class="box span12">
        <div class="box-content">


         <form  class="form-horizontal"   action="updateBook" method="POST" >
                  <fieldset>
                              

                             <div class="control-group">
                                        <label class="control-label" for="book isbn">Book ISBN*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="bkISBN"
                                              value="<%=request.getParameter("bkISBN")%>"  >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="book author">Book Author*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="bkAUTHOR"
                                              value="<%=request.getParameter("bkAUTHOR")%>"  >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="book publisher">Book Publisher*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="bkPUBLISHER"
                                              value="<%=request.getParameter("bkPUBLISHER")%>"  >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="book title">Book Title*:</label>
                                        <div class="controls">
                                            <input class="input-xlarge focused" id="receiver" type="text" name="bkTitle"
                                              value="<%=request.getParameter("bkTitle")%>"  >
                                        </div>
                                    </div> 

                                    <div class="control-group">
                                        <label class="control-label" for="bookstatus">Book Category*:</label>
                                         <div class="controls">
                                            <select name="bookstatus" >
                                                <option value="">Please select one</option> 
                                                <option value="Reference">Reference</option>
                                                <option value="Shortloan">Shortloan</option>                                                 
                                            </select>                           
                                          
                                        </div>
                                    </div> 

                                    <div class="form-actions">
                                        <input type="hidden" name="bookuuid" value="<%=request.getParameter("bookuuid")%>">
                                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                        <button type="submit" class="btn btn-primary">Update</button>
                                    </div> 

              </fieldset>
              </form>
        
                


    </div>

</div>


<jsp:include page="footer.jsp" />
