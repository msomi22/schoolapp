

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.PrimaryDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentPrimary"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>




<%


     if (session == null) {
       response.sendRedirect("../index.jsp");
       //return;
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
        //return;
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
    //return;


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

     String accountuuid = school.getUuid();
     String schoolname = school.getSchoolName();


    String admno = request.getParameter("admissNo");
     
    
    if ((element = statisticsCache.get(accountuuid)) != null) {
        statistics = (SessionStatistics) element.getObjectValue();
    }

    
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     studentList = studentDAO.getStudentByAdmNo(accountuuid, admno);
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");


     HashMap<String, String> classroomHash = new HashMap<String, String>();
    RoomDAO roomDAO = RoomDAO.getInstance();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();
    classList = roomDAO.getAllRooms(accountuuid);
    for(ClassRoom cr : classList){
       classroomHash.put(cr.getUuid(), cr.getRoomName()); 
         }

    
    StudentPrimary studentPrimary = new StudentPrimary();
    HashMap<String, StudentPrimary> studentPrimaryHash = new HashMap<String, StudentPrimary>();
    PrimaryDAO primaryDAO = PrimaryDAO.getInstance();
    List<StudentPrimary> studentPrimaryList = new ArrayList<StudentPrimary>();
    studentPrimaryList = primaryDAO.getAllPrimary(); 
       for(StudentPrimary sprimary : studentPrimaryList){
         studentPrimaryHash.put(sprimary.getStudentUuid(), sprimary); 
         }
        
%>       
                    <%  
                  int count =1;                                                        
                   String fullname = "";
                  

                     String gender = "";

                    String status = "";
                     String primaryschool = "";
                      String kcpeindex = "";
                       String kcpemark = "";
                        String kcpeyear = ""; 
                       for (Student st : studentList) {
                          
                    String firstNameLowecase = "";
                    String lastNameLowecase ="";
                    String surNameLowecase ="";

                    firstNameLowecase = StringUtils.capitalize(st.getFirstname().toLowerCase());
                    lastNameLowecase = StringUtils.capitalize(st.getLastname().toLowerCase());
                    surNameLowecase = StringUtils.capitalize(st.getSurname().toLowerCase());

                     gender = st.getGender();
                    if(StringUtils.equalsIgnoreCase(gender, "FEMALE")) {
                                gender = "F";
                                     }else{
                                    gender = "M";
                                 }
                    
                                  
                    fullname = firstNameLowecase+" "+" "+" "+lastNameLowecase;

                    studentPrimary = studentPrimaryHash.get(st.getUuid());
                    if(studentPrimary !=null){
                        primaryschool = studentPrimary.getSchoolname();
                        kcpeindex =  studentPrimary.getIndex();
                        kcpemark =  studentPrimary.getKcpemark();
                        kcpeyear =  studentPrimary.getKcpeyear();
                    }else{
                        primaryschool = "";
                        kcpeindex = "";
                        kcpemark = "";
                        kcpeyear = "";
                    }

                    String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(st.getStatusUuid(),statusUuid)){
                       status = "Active";
                       %>

                       <tr class="tabledit" style='color:black;'>
                        <td width="3%"><%=count%></td>
                         <td class="center"><%=st.getAdmno()%></td> 
                         <td class="center"><%=fullname%></td>
                         <td class="center"><%=gender%></td>
                         <td class="center"><%=st.getdOB()%></td>
                         <td class="center"><%=st.getBcertno()%></td>
                         <td class="center"><%=classroomHash.get(st.getClassRoomUuid())%></td>
                         <td class="center"><%=st.getCounty()%></td>
                         <td class="center"><%=primaryschool%></td>
                         <td class="center"><%=kcpeindex%></td>
                         <td class="center"><%=kcpemark%></td>
                         <td class="center"><%=kcpeyear%></td>
                         <td class="center"><%=dateFormatter.format(st.getAdmissionDate())%></td>    
                        <td class="center">
                                <form name="view" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admNo" value="<%=st.getAdmno()%>">
                                <input type="hidden" name="firstname" value="<%=firstNameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastNameLowecase%>">
                                <input type="hidden" name="surname" value="<%=surNameLowecase%>">
                                <input type="hidden" name="gender" value="<%=st.getGender()%>">
                                <input type="hidden" name="dob" value="<%=st.getdOB()%>">
                                <input type="hidden" name="BcertNo" value="<%=st.getBcertno()%>">
                                <input type="hidden" name="county" value="<%=st.getCounty()%>">
                                <input type="hidden" name="primary" value="<%=primaryschool%>">
                                <input type="hidden" name="kcpeindex" value="<%=kcpeindex%>">
                                <input type="hidden" name="kcpemark" value="<%=kcpemark%>">
                                <input type="hidden" name="kcpeyear" value="<%=kcpeyear%>">
                                <input type="hidden" name="studentUuid" value="<%=st.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input type="hidden" name="finalYear" value="<%=st.getFinalYear()%>">
                                <input type="hidden" name="finalTerm" value="<%=st.getFinalTerm()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                               
                               </td>   
                                          
                    </tr>



                       <%
                    }else{
                      status = "Inactive";
                      %>

                      <tr class="tabledit" style='color:black;'>
                        <td width="3%"><%=count%></td>
                         <td class="center"><%=st.getAdmno()%></td> 
                         <td class="center"><%=fullname%></td>
                         <td class="center"><%=gender%></td>
                         <td class="center"><%=st.getdOB()%></td>
                         <td class="center"><%=st.getBcertno()%></td>
                         <td class="center"><%=classroomHash.get(st.getClassRoomUuid())%></td>
                         <td class="center"><%=st.getCounty()%></td>
                         <td class="center"><%=primaryschool%></td>
                         <td class="center"><%=kcpeindex%></td>
                         <td class="center"><%=kcpemark%></td>
                         <td class="center"><%=kcpeyear%></td>
                         <td class="center"><%=dateFormatter.format(st.getAdmissionDate())%></td>    
                        <td class="center">
                                <form name="view" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admNo" value="<%=st.getAdmno()%>">
                                <input type="hidden" name="firstname" value="<%=firstNameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastNameLowecase%>">
                                <input type="hidden" name="surname" value="<%=surNameLowecase%>">
                                <input type="hidden" name="gender" value="<%=st.getGender()%>">
                                <input type="hidden" name="dob" value="<%=st.getdOB()%>">
                                <input type="hidden" name="BcertNo" value="<%=st.getBcertno()%>">
                                <input type="hidden" name="county" value="<%=st.getCounty()%>">
                                <input type="hidden" name="primary" value="<%=primaryschool%>">
                                <input type="hidden" name="kcpeindex" value="<%=kcpeindex%>">
                                <input type="hidden" name="kcpemark" value="<%=kcpemark%>">
                                <input type="hidden" name="kcpeyear" value="<%=kcpeyear%>">
                                <input type="hidden" name="studentUuid" value="<%=st.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input type="hidden" name="finalYear" value="<%=st.getFinalYear()%>">
                                <input type="hidden" name="finalTerm" value="<%=st.getFinalTerm()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                               
                               </td>   
                                          
                    </tr>



                      <%
                    }
                    %>
                    

                    <%       
                           count++;
                            } 
                    %>
               


       
          