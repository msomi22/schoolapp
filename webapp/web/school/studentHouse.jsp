<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentHouseDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentHouse"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.HouseDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.House"%>

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
     
     StudentHouse studentHouse = new StudentHouse();
     HashMap<String, StudentHouse> studentHouseHash = new HashMap<String, StudentHouse>();
     StudentHouseDAO studentHouseDAO = StudentHouseDAO.getInstance();
     List<StudentHouse> studenthouseList = new ArrayList<StudentHouse>(); 

     studenthouseList = studentHouseDAO.getHouseList();
     for(StudentHouse stuhouse : studenthouseList){
        studentHouseHash.put(stuhouse.getStudentUuid(), stuhouse);
        }
    
    HashMap<String, String> houseHash = new HashMap<String, String>();
    HouseDAO houseDAO = HouseDAO.getInstance();
    List<House> houseList = new ArrayList<House>();
    houseList = houseDAO.getHouseList(accountuuid);
    for(House hh : houseList){
       houseHash.put(hh.getUuid(),hh.getHouseName());
       }
    
      String formatedFirstname = "";
      String formatedLastname = "";
      String formatedSurname = "";
      String fullname = "";

     HashMap<String, String> admNoHash = new HashMap<String, String>();
     HashMap<String, String> fullnameHash = new HashMap<String, String>();
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     studentList = studentDAO.getAllStudentList(accountuuid);  

     for(Student s : studentList){
       admNoHash.put(s.getUuid(),s.getAdmno());

                    String firstNameLowecase = StringUtils.capitalize(s.getFirstname().toLowerCase());
                    String lastNameLowecase = StringUtils.capitalize(s.getLastname().toLowerCase());
                    String surNameLowecase = StringUtils.capitalize(s.getSurname().toLowerCase());

                   formatedFirstname =  StringUtils.capitalize(s.getFirstname().toLowerCase());
                   formatedLastname =  StringUtils.capitalize(s.getLastname().toLowerCase());
                   formatedSurname =  StringUtils.capitalize(s.getSurname().toLowerCase());
                    fullname = formatedFirstname+" "+formatedLastname+" "+formatedSurname;
                   fullnameHash.put(s.getUuid(),fullname);            
         }


    



     

          //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
                             

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :HOUSE/DOMITPRY MANAGEMENT PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
      <li>
            <a href="addHouse.jsp">Assign House</a> <span class="divider">/</span>
      </li>
       <li>
            <a href="">Change House</a> <span class="divider">/</span>
      </li>

       <li>
            <a href="newHouse.jsp">New House</a> <span class="divider">/</span>
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
                        <th>Student Name</th>
                        <th>House</th>
                        <th>Date In</th>
                        <th>House Master</th>
                        
                            
                    </tr>
                </thead>   
                <tbody>
          
                    <%                 
                             
                   
                        int count = 1;
                        if(studentList !=null){
                       for(Student ss : studentList) { 

                         String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(ss.getStatusUuid(),statusUuid)){


                                for(StudentHouse stuhouse : studenthouseList){
                                   if(StringUtils.equals(ss.getUuid(),stuhouse.getStudentUuid())){
                                        studentHouse = studentHouseHash.get(ss.getUuid());
                             
                             out.println("<tr>"); 
                             out.println("<td width=\"3%\" >" + count + "</td>"); 
                             out.println("<td width=\"5%\" class=\"center\">" + ss.getAdmno() + "</td>"); 
                             out.println("<td width=\"16%\" class=\"center\">" + fullnameHash.get(ss.getUuid()) + "</td>"); 
                             out.println("<td width=\"8%\" class=\"center\">" + houseHash.get(studentHouse.getHouseUuid()) + "</td>");
                             out.println("<td width=\"8%\" class=\"center\">" + dateFormatter.format(studentHouse.getDateIn()) + "</td>"); 
                             out.println("<td width=\"5%\" class=\"center\">" + " " + "</td>"); 
 
                                     


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
