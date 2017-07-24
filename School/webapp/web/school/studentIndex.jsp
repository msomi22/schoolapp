<%
/**
  Copyright (c) Fastech Solutions Ltd (Jan 16 2016).

  License
  THIS PRODUCT is Licensed under the Open Software License (the "License"), Version 3.0 .
  You may not use this SOFTWARE NOT UNLESS in compliance with the License.
  You may obtain a copy of the License at: http://opensource.org/licenses/OSL-3.0

  Disclaimer
  This SOFTWARE PRODUCT is provided BY THE PROVIDER "AS-IS".
  THE PROVIDER  makes no representations or warranties of any kind WHATSOEVER concerning the safety,inaccuracies and other harmful results that may arise out of using THE PRODUCT for non-intended purposes.
  THE DEVELOPER will not be liable for ANY data loss AND OR any other harm connected with using this PRODUCT contrary to the spesifications provided by THE PROVIDER in the terms and conditions.

 @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
**/
%>

<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPage"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.PrimaryDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentPrimary"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>


<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="org.joda.time.MutableDateTime"%>

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
   

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

     String accountuuid = school.getUuid();
     String schoolname = school.getSchoolName();


    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     studentList = studentDAO.getStudentList(school , 0 , 15); 

    HashMap<String, String> classroomHash = new HashMap<String, String>();
    RoomDAO roomDAO = RoomDAO.getInstance();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();

    classList = roomDAO.getAllRooms(accountuuid);
    if(classList !=null){
    for(ClassRoom cr : classList){
       classroomHash.put(cr.getUuid(), cr.getRoomName()); 
         }
       }

    
    StudentPrimary studentPrimary = new StudentPrimary();
    HashMap<String, StudentPrimary> studentPrimaryHash = new HashMap<String, StudentPrimary>();
    PrimaryDAO primaryDAO = PrimaryDAO.getInstance();
    List<StudentPrimary> studentPrimaryList = new ArrayList<StudentPrimary>();

       studentPrimaryList = primaryDAO.getAllPrimary(); 
       if(studentPrimaryList !=null){
       for(StudentPrimary sprimary : studentPrimaryList){
         studentPrimaryHash.put(sprimary.getStudentUuid(), sprimary); 
         }
       }


     int ussdCount = 0;
     StudentPaginator paginator = new StudentPaginator(accountuuid);
     StudentPage studentpage;

     studentpage = (StudentPage) session.getAttribute("currentPage");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (studentpage == null
                || !StringUtils.endsWith(referrer, "studentIndex.jsp")
                || StringUtils.equalsIgnoreCase(pageParam, "first")) {
              studentpage = paginator.getFirstPage();

            //We are to give the last page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "last")) {
             studentpage = paginator.getLastPage();

            // We are to give the previous page
        } else if (StringUtils.equalsIgnoreCase(pageParam, "previous")) {
            studentpage = paginator.getPrevPage(studentpage);

            // We are to give the next page 
        } else if (StringUtils.equalsIgnoreCase(pageParam, "next"))  {
           studentpage = paginator.getNextPage(studentpage);
        }

        session.setAttribute("currentPage", studentpage);
        studentList = studentpage.getContents();
        ussdCount = (studentpage.getPageNum() - 1) * studentpage.getPagesize() + 1;
      // }


 //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");
%> 
<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STUDENT MANAGENENT PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="addStudent.jsp">New Student</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="addParent.jsp">New Parent</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="addSponsor.jsp">New Student Sponsor</a> <span class="divider">/</span>
        </li>

        <li>
            <a href="addHouse.jsp">Assign House</a> <span class="divider">/</span>
        </li>

        <li>
            <a href="studentSubjects.jsp">Student Subject</a> <span class="divider">/</span>
        </li>

          <li>
            <a href="classTeachersSec.jsp">Class List</a> <span class="divider">/</span>
         </li>

         <li>
            <a href="importexcel.jsp">Import Excel</a> <span class="divider">/</span>
        </li>

         <li>
            <a href="idcards.jsp">ID Cards</a> <span class="divider">/</span>
        </li>




        
        
    </ul>
</div>


<div class="row-fluid sortable">    
    <div class="box span12">
        <div class="box-content">
                 
                  <%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);
                                     updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                    updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updateSuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS,null);
                                  } 



                      %>






                <div id="search_box">
                <form action="#" method="get">
                 <input type="text" placeholder="Search By AdmNo" name="q" size="10" id="searchfield" title="searchfield" onkeyup="searchstudents(this.value)" />
                </form>
                </div>

              

            <div>
            <table class="table table-striped table-bordered bootstrap-datatable ">
                <thead>
                    <tr >
                        <th>*</th>
                        <th>Adm No</th>
                        <th>Name</th>                
                        <th>Gender</th>
                        <th>DOB</th>
                        <th>Bcert</th>
                        <th>Class</th>
                        <th>County</th>
                        <th>Primary</th>
                        <th>Index</th>
                        <th>Marks</th>
                        <th>Year</th>
                        <th>Adm Date</th>
                        <th>Update</th>
                    </tr>
                </thead>   
                <tbody class='tablebody'>
                    <%
                    String fullname = "";
                   

                    String status = "";
                    String primaryschool = "";
                    String kcpeindex = "";
                    String kcpemark = "";
                    String kcpeyear = ""; 
                    String gender = "";
                      
                      if(studentList !=null){
                    for(Student s : studentList){

                      String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(s.getStatusUuid(),statusUuid)){
                   
                    String firstNameLowecase = "";
                    String lastNameLowecase ="";
                    String surNameLowecase ="";

                    firstNameLowecase = StringUtils.capitalize(s.getFirstname().toLowerCase());
                    lastNameLowecase = StringUtils.capitalize(s.getLastname().toLowerCase());
                    surNameLowecase = StringUtils.capitalize(s.getSurname().toLowerCase());

                    gender = s.getGender();
                    if(StringUtils.equalsIgnoreCase(gender, "FEMALE")) {
                                gender = "F";
                                     }else{
                                    gender = "M";
                                 }
                                  

                    fullname = firstNameLowecase+" "+" "+" "+lastNameLowecase;



                    studentPrimary = studentPrimaryHash.get(s.getUuid());
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

                    if(StringUtils.equals(s.getStatusUuid(),statusUuid)){
                       status = "Active";
                       %>

                       <tr class="tabledit" style='color:black;'>
                         <td width="3%"><%=ussdCount%></td>
                         <td class="center"><%=s.getAdmno()%></td> 
                         <td class="center"><%=fullname%></td>
                         <td class="center"><%=gender%></td>
                         <td class="center"><%=s.getdOB()%></td>
                         <td class="center"><%=s.getBcertno()%></td>
                         <td class="center"><%=classroomHash.get(s.getClassRoomUuid())%></td>
                         <td class="center"><%=s.getCounty()%></td>
                         <td class="center"><%=primaryschool%></td>
                         <td class="center"><%=kcpeindex%></td>
                         <td class="center"><%=kcpemark%></td>
                         <td class="center"><%=kcpeyear%></td>
                         <td class="center"><%=dateFormatter.format(s.getAdmissionDate())%></td>  
                         <td class="center">
                                <form name="view" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admNo" value="<%=s.getAdmno()%>">
                                <input type="hidden" name="firstname" value="<%=firstNameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastNameLowecase%>">
                                <input type="hidden" name="surname" value="<%=surNameLowecase%>">
                                <input type="hidden" name="gender" value="<%=s.getGender()%>">
                                <input type="hidden" name="dob" value="<%=s.getdOB()%>">
                                <input type="hidden" name="BcertNo" value="<%=s.getBcertno()%>">
                                <input type="hidden" name="county" value="<%=s.getCounty()%>">
                                <input type="hidden" name="primary" value="<%=primaryschool%>">
                                <input type="hidden" name="kcpeindex" value="<%=kcpeindex%>">
                                <input type="hidden" name="kcpemark" value="<%=kcpemark%>">
                                <input type="hidden" name="kcpeyear" value="<%=kcpeyear%>">
                                <input type="hidden" name="studentUuid" value="<%=s.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input type="hidden" name="finalYear" value="<%=s.getFinalYear()%>">
                                <input type="hidden" name="finalTerm" value="<%=s.getFinalTerm()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                          
                               </td>            
                        
                      

                    </tr>





                       <%
                    }else{
                      status = "Inactive";
                      %>


                      <tr class="tabledit" style='color:black;'>
                         <td width="3%"><%=ussdCount%></td>
                         <td class="center"><%=s.getAdmno()%></td> 
                         <td class="center"><%=fullname%></td>
                         <td class="center"><%=gender%></td>
                         <td class="center"><%=s.getdOB()%></td>
                         <td class="center"><%=s.getBcertno()%></td>
                         <td class="center"><%=classroomHash.get(s.getClassRoomUuid())%></td>
                         <td class="center"><%=s.getCounty()%></td>
                         <td class="center"><%=primaryschool%></td>
                         <td class="center"><%=kcpeindex%></td>
                         <td class="center"><%=kcpemark%></td>
                         <td class="center"><%=kcpeyear%></td>
                         <td class="center"><%=dateFormatter.format(s.getAdmissionDate())%></td>  
                         <td class="center">
                                <form name="view" method="POST" action="updateStudent.jsp"> 
                                <input type="hidden" name="admNo" value="<%=s.getAdmno()%>">
                                <input type="hidden" name="firstname" value="<%=firstNameLowecase%>">
                                <input type="hidden" name="lastname" value="<%=lastNameLowecase%>">
                                <input type="hidden" name="surname" value="<%=surNameLowecase%>">
                                <input type="hidden" name="gender" value="<%=s.getGender()%>">
                                <input type="hidden" name="dob" value="<%=s.getdOB()%>">
                                <input type="hidden" name="BcertNo" value="<%=s.getBcertno()%>">
                                <input type="hidden" name="county" value="<%=s.getCounty()%>">
                                <input type="hidden" name="primary" value="<%=primaryschool%>">
                                <input type="hidden" name="kcpeindex" value="<%=kcpeindex%>">
                                <input type="hidden" name="kcpemark" value="<%=kcpemark%>">
                                <input type="hidden" name="kcpeyear" value="<%=kcpeyear%>">
                                <input type="hidden" name="studentUuid" value="<%=s.getUuid()%>">
                                <input type="hidden" name="schoolUuid" value="<%=accountuuid%>">
                                <input type="hidden" name="finalYear" value="<%=s.getFinalYear()%>">
                                <input type="hidden" name="finalTerm" value="<%=s.getFinalTerm()%>">
                                <input class="btn btn-success" type="submit" name="view" id="submit" value="Edit" /> 
                                </form>                          
                               </td>           
                        
                      

                    </tr>





                      <%
                    }
                    %>
                    

                    <%
                          ussdCount++;
                         }
                       }
                     }
                            
                    %>
                </tbody>
            </table>  

             <div id="pagination">
                <form name="pageForm" method="post" action="studentIndex.jsp">                                
                    <%                                            
                        if (!studentpage.isFirstPage()) {
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="First" />
                        <input class="toolbarBtn" type="submit" name="page" value="Previous" />
                    <%
                        }
                    %>
                    <span class="pageInfo">Page 
                        <span class="pagePosition currentPage"><%= studentpage.getPageNum()%></span> of 
                        <span class="pagePosition"><%= studentpage.getTotalPage()%></span>
                    </span>   
                    <%
                        if (!studentpage.isLastPage()) {                        
                    %>
                        <input class="toolbarBtn" type="submit" name="page" value="Next">  
                        <input class="toolbarBtn" type="submit" name="page" value="Last">
                    <%
                       }
                    %>                                
                </form>
            </div>

            </div>

      

        </div>
    </div><!--/span-->

</div><!--/row-->


<jsp:include page="footer.jsp" />
