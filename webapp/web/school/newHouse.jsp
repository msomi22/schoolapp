<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.HouseDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.House"%>


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

    HouseDAO houseDAO = HouseDAO.getInstance();
    List<House> houseList = new ArrayList<House>();
    houseList = houseDAO.getHouseList(accountuuid);




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
     <li> <b> <%=schoolname%> :HOUSE/DOMITORY MANAGEMENT : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
      <li>
            <a href="addnewHouse.jsp">New House</a> <span class="divider">/</span>
      </li>

       <li>
            <a href="">Assign House Master</a> <span class="divider">/</span>
      </li>

       <li>
            <a href="">Back</a> <span class="divider">/</span>
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
                        <th>House Name</th>
                        <th>Update </th>

                        
                        
                        
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                       int count = 1;
                        if(houseList !=null){
                       for(House hs : houseList) { 

                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" +hs.getHouseName() + "</td>"); 
                                     %>
                             <td class="center" width ="10%">
                             <form name="update" method="POST" action="updateHouse.jsp"> 
                             <input type="hidden" name="housename" value="<%=hs.getHouseName()%>">
                             <input type="hidden" name="houseuuid" value="<%=hs.getUuid()%>">
                             <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                             </form>                          
                             </td>  

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
