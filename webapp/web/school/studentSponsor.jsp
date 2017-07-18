<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.guardian.SponsorsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.guardian.StudentSponsor"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

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
     
     StudentSponsor studentSponsor = new StudentSponsor();
    
     SponsorsDAO sponsorsDAO = SponsorsDAO.getInstance();
     List<StudentSponsor> sponsorList = new ArrayList<StudentSponsor>(); 
     sponsorList = sponsorsDAO.getStudentSponsorList();
    
   

     HashMap<String, String> admNoHash = new HashMap<String, String>();
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     studentList = studentDAO.getAllStudentList(accountuuid);  
     for(Student s : studentList){
       admNoHash.put(s.getUuid(),s.getAdmno());
         }

     


     
                             

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STUDENTS-SPONSOR MANAGEMENT PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>  
     <li>
            <a href="addSponsor.jsp">New Sponsor</a> <span class="divider">/</span>
        </li> 
    </ul>
</div>


<div class="row-fluid sortable">

               




    <div class="box span12">
        <div class="box-content">
            

                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>Student AdmNo</th>
                        <th>SponsorName</th>
                        <th>SponsorPhone</th>
                        <th>Occupation</th>
                        <th>Country</th>
                        <th>County</th>
                        <th>Update</th>
                       
                            
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                   
                      
                        if(studentList !=null){
                        int count = 1;
                       for(Student ss : studentList) { 

                           String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                        if(StringUtils.equals(ss.getStatusUuid(),statusUuid)){

                          for(StudentSponsor stusponsor : sponsorList){
                            if(StringUtils.equals(ss.getUuid(),stusponsor.getStudentUuid())){
       
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + admNoHash.get(stusponsor.getStudentUuid()) + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorName() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorPhone() + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorOccupation() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorCountry() + "</td>");
                             out.println("<td width=\"13%\" class=\"center\">" + stusponsor.getSponsorCounty() + "</td>"); 

                             %>
                                <td class="center">
                                <form name="view" method="POST" action=""> 
                                <input type="hidden" name="studentUuid" value="<%=ss.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                          
                               </td>    


                             <%

                                count++;
                               }
                              }
                          } 
                        }
                     }
                    %>
                    
                    </tbody>
            </table> 


    </div>

</div>


<jsp:include page="footer.jsp" />
