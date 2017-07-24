<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.guardian.ParentsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.guardian.StudentParent"%>

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
     
     StudentParent studentParent = new StudentParent();
     HashMap<String, StudentParent> studentParentHash = new HashMap<String, StudentParent>();
     ParentsDAO parentsDAO = ParentsDAO.getInstance();
     List<StudentParent> parentList = new ArrayList<StudentParent>(); 

     parentList = parentsDAO.getParentList();
     for(StudentParent stuparent : parentList){
        studentParentHash.put(stuparent.getStudentUuid(), stuparent);
        }
    

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
     <li> <b> <%=schoolname%> :PARENTS PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
      <li>
            <a href="addParent.jsp">New Parent</a> <span class="divider">/</span>
        </li>
    </ul>
</div>

<div class="row-fluid sortable">

               




    <div class="box span12">
        <div class="box-content">

        <%
                  
                    

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS); 
                                    
                    

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS, null);
                                  } 
                                  

                           
                     %>

                <table class="table table-striped table-bordered bootstrap-datatable datatable">
                <thead>
                    <tr>
                        <th>*</th>
                        <th>AdmNo</th>
                        <th>Father Name</th>
                        <th>Father Phone</th>
                        <th>Father Email</th>
                        <th>Father ID</th>
                        <th>Father Occu</th>
                        <th>Mother Name</th>
                        <th>Mother Phone</th>
                        <th>Mother Email</th>
                        <th>Mother ID</th>
                        <th>Mother Occu</th>
                        <th>Update</th>
                        
                            
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                   
                         int count = 1;
                        if(studentList !=null){
                       for(Student ss : studentList) { 

                      String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(ss.getStatusUuid(),statusUuid)){

                       for(StudentParent stuparent : parentList){
                            if(StringUtils.equals(ss.getUuid(),stuparent.getStudentUuid())){
                                 studentParent = studentParentHash.get(ss.getUuid());
                             
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + ss.getAdmno() + "</td>");                            
                             out.println("<td width=\"13%\" class=\"center\">" + studentParent.getFathername() + "</td>");
                             out.println("<td width=\"10%\" class=\"center\">" + studentParent.getFatherphone() + "</td>"); 
                             out.println("<td width=\"12%\" class=\"center\">" + studentParent.getFatherEmail() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + studentParent.getFatherID() + "</td>");
                             out.println("<td width=\"15%\" class=\"center\">" + studentParent.getFatheroccupation() + "</td>"); 
                             out.println("<td width=\"13%\" class=\"center\">" + studentParent.getMothername() + "</td>"); 
                             out.println("<td width=\"10%\" class=\"center\">" + studentParent.getMotherphone() + "</td>"); 
                             out.println("<td width=\"12%\" class=\"center\">" + studentParent.getMotherEmail() + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + studentParent.getMotherID() + "</td>"); 
                             out.println("<td width=\"15%\" class=\"center\">" + studentParent.getMotheroccupation() + "</td>");

                                       %>
                                <td class="center">
                                <form name="view" method="POST" action="updateParent.jsp"> 
                                <input type="hidden" name="studentParentUuid" value="<%=studentParent.getUuid()%>">
                                <input type="hidden" name="studentUuid" value="<%=ss.getUuid()%>">
                                <input type="hidden" name="FatherName" value="<%=studentParent.getFathername()%>">
                                <input type="hidden" name="FatherPhone" value="<%=studentParent.getFatherphone()%>">
                                <input type="hidden" name="FatherOccupation" value="<%=studentParent.getFatheroccupation()%>">
                                <input type="hidden" name="FatherID" value="<%=studentParent.getFatherID()%>">
                                <input type="hidden" name="FatherEmail" value="<%=studentParent.getFatherEmail()%>">
                                <input type="hidden" name="MotherName" value="<%=studentParent.getMothername()%>">
                                <input type="hidden" name="MotherPhone" value="<%=studentParent.getMotherphone()%>">
                                <input type="hidden" name="MotherOccupation" value="<%=studentParent.getMotheroccupation()%>">
                                <input type="hidden" name="MotherID" value="<%=studentParent.getMotherID()%>">
                                <input type="hidden" name="MotherEmail" value="<%=studentParent.getMotherEmail()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Update" /> 
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
