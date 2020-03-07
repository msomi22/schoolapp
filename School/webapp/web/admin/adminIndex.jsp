
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>

<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="net.sf.ehcache.CacheManager" %>
<%@ page import="net.sf.ehcache.Element" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<body>

<%
if (session == null) {
        response.sendRedirect("index.jsp");
    }

    String username = (String) session.getAttribute(AdminSessionConstants.ADMIN_SESSION_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("index.jsp");
    }

     session.setMaxInactiveInterval(AdminSessionConstants.SESSION_TIMEOUT);
     response.setHeader("Refresh", AdminSessionConstants.SESSION_TIMEOUT + "; url=Logout");

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_ACCOUNTS_BY_UUID);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    
    Element element;
    SchoolAccount schoolAccount = new SchoolAccount();

     List keys;
     List<SchoolAccount> schoolList = new ArrayList(); 
    keys = accountsCache.getKeys();
    for (Object key : keys) {
        element = accountsCache.get(key);
        schoolAccount = (SchoolAccount) element.getObjectValue();
        schoolList.add(schoolAccount);
    }
   


     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     

     String principalUuid = "C3915245-00EE-4EF4-9898-ACE59683DD60";

     String principalUsername = "";
     String staffname = "";
    

     StaffDAO staffDAO = StaffDAO.getInstance();
     List<Staff> staffList = new ArrayList(); 

     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     
     
     


%>




 <jsp:include page="header.jsp" />

<div class="row-fluid sortable">    
    <div class="box span12">
    <div class="box-header well" data-original-title>
          <p> <a href="addSchool.jsp">New School</a> </p> 
        </div>
       
        <div class="box-content">
                    <%

                        

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(AdminSessionConstants.SCHOOL_ACCOUNT_UPDATE_SUCCESS, null);
                                  } 

                                 

                            %>

        
                <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>School Name</th>
                        <th>UserName</th> 
                        <th>Principal</th> 
                        <th>Students</th>                
                        <th>Mobile</th>
                        <th>Email</th>
                        <th>Postal address</th>
                        <th>Home Town</th>
                        <th>Status</th>
                        <th>Actions</th>

                    </tr>
                </thead>   
                <tbody>
                    <%                                                          
                      int count = 1;
                         for (SchoolAccount s : schoolList) {
                          String status = "Active";

                          String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                          if(StringUtils.equals(s.getStatusUuid(),statusUuid)){
                            status = "Active";
                              }else{
                             status = "Inactive";
                               }

                           StaffDetails staffDetails = new StaffDetails();
                           staffList = staffDAO.getStaffList(s.getUuid()); 
                           for(Staff staff : staffList){
                            

                           if(StringUtils.equals(staff.getPositionUuid(), principalUuid)) {
                              principalUsername = staff.getUserName();
                            
                              staffDetails = staffDetailsDAO.getStaffDetail(staff.getUuid());

                              if(staffDetails != null) {
                                  staffname = "";
                                  staffname = "("+staffDetails.getSurname()+" "+staffDetails.getFirstName()+" "+staffDetails.getLastName()+")";

                              }else{
                                staffname = "";
                              }
                           }
                         }     
                    %>
                    <tr>
                        <td width="3%"><%=count%></td>
                         <td class="center"><%=s.getSchoolName()%></td> 
                         <td class="center"><%=s.getUsername()%></td>
                         <td class="center"><%=principalUsername + " "+staffname+""%></td>
                         <td class="center"><%=studentDAO.getStudentCount(statusUuid,s.getUuid())%></td>
                         <td class="center"><%=s.getMobile()%></td>
                         <td class="center"><%=s.getEmail()%></td>  
                         <td class="center"><%=s.getPostalAddress()%></td>
                         <td class="center"><%=s.getTown()%></td>  
                         <td class="center"><%=status%></td>  
                         <td class="center">
                                <form name="edit" method="POST" action="editSchool.jsp"> 
                                <input type="hidden" name="schoolname" value="<%=s.getSchoolName()%>">
                                <input type="hidden" name="username" value="<%=s.getUsername()%>">
                                <input type="hidden" name="password" value="<%=s.getPassword()%>">
                                <input type="hidden" name="mobile" value="<%=s.getMobile()%>">
                                <input type="hidden" name="email" value="<%=s.getEmail()%>">
                                <input type="hidden" name="postaladdress" value="<%=s.getPostalAddress()%>">
                                <input type="hidden" name="hometown" value="<%=s.getTown()%>">
                                <input type="hidden" name="schooluuid" value="<%=s.getUuid()%>">
                                
                                <input type="hidden" name="motto" value="<%=s.getSchoolMotto()%>">
                                <input type="hidden" name="website" value="<%=s.getWebsite()%>">
                                
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Edit School" /> 
                                </form>     

                                <form name="edit" method="POST" action="profile.jsp"> 
                                <input type="hidden" name="schooluuid" value="<%=s.getUuid()%>">
                                <input class="btn btn-success" type="submit" name="edit" id="submit" value="Edit Password" /> 
                                </form>                          
                        </td>   
                    </tr>

                    <%     
                           count++;
                           principalUsername = " ";
                           staffname = " ";
                            
                            } 
                    %>
                </tbody>
            </table>  



       
       </div>
     </div>
    </div>
    
  <jsp:include page="footer.jsp" />

