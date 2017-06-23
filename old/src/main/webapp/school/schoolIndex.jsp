
<%
/**
  Copyright (c) Fastech Solutions Ltd (Jan 16 2016).

  License
  THIS PRODUCT is Licensed under the Open Software License (the "License"), Version 3.0 .
  You may not use this SOFTWARE NOT UNLESS in compliance with the License.
  You may obtain a copy of the License at: http://opensource.org/licenses/OSL-3.0

  Disclaimer
  This SOFTWARE PRODUCT is provided BY THE PROVIDER "AS-IS" (The buyer buys the product in whatever condition it presently
  exist,the buyer accept THE PRODUCT "with all faults").
  THE PROVIDER  makes no representations or warranties of any kind WHATSOEVER concerning the safety,inaccuracies and other harmful results that may arise out of using THE PRODUCT for non-intended purposes.
  THE DEVELOPER will not be liable for ANY data loss AND OR any other harm connected with using this PRODUCT contrary to the spesifications provided by THE PROVIDER in the terms and conditions.

 @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
**/
%>


<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPage"%>
<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>
<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>
<%@page import="com.yahoo.petermwenda83.persistence.student.PrimaryDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentPrimary"%>
<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>
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

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    

    Account school = new Account();
    Element element;
   

    String classuuid = "";
    String room  ="";
    String staffPosition  ="";
    String accountuuid = "";

    if ((element = accountsCache.get(username)) != null) {
        school = (Account) element.getObjectValue();
    }

   if(school !=null){ 
     accountuuid = school.getUuid();
   }
     
      ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance(); 
      ExamConfig  examConfig = examConfigDAO.getExamConfig(accountuuid);
       
   

     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     staffPosition = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION);

     ClassTeacherDAO classTeacherDAO = ClassTeacherDAO.getInstance();
     RoomDAO roomDAO = RoomDAO.getInstance();
     
     if(stffID !=null){  
     ClassTeacher ct = classTeacherDAO.getClassTeacherByteacherId(stffID); 
       if(ct !=null){
       classuuid = ct.getClassRoomUuid();
          }
              }

     
     ClassRoom cr = roomDAO.getroom(accountuuid, classuuid);
      if(cr !=null){
      room = cr.getRoomName(); 
       }
       
        final String FORM1 = "FORM 1";
        final String FORM2 = "FORM 2";
        final String FORM3 = "FORM 3";
        final String FORM4 = "FORM 4";

  
        String pos_Pricipal =(String)  PropertiesConfig.getConfigValue("POSITION_PRINCIPAL");
        String pos_DeputyPricipal =(String)  PropertiesConfig.getConfigValue("POSITION_DEPUTY");
        String pos_Teacher =(String) PropertiesConfig.getConfigValue("POSITION_TEACHER");
        String pos_HOD =(String) PropertiesConfig.getConfigValue("POSITION_HOD");
        String pos_CM =(String) PropertiesConfig.getConfigValue("POSITION_CM");
        String pos_Secretary =(String) PropertiesConfig.getConfigValue("POSITION_SECRETARY");
        String pos_Bursar =(String) PropertiesConfig.getConfigValue("POSITION_BURSAR");


      String schoolname = "";

      if (session == null) {
           response.sendRedirect("../index.jsp");
        }


    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
        //return;
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
    
    schoolname = school.getName();

   
     StudentDAO studentDAO = StudentDAO.getInstance();
     List<Student> studentList = new ArrayList(); 
     studentList = studentDAO.getStudentList(school , 0 , 15); 

    HashMap<String, String> classroomHash = new HashMap<String, String>();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();
    classList = roomDAO.getAllRooms(accountuuid);
    if(classList !=null){
    for(ClassRoom crr : classList){
       classroomHash.put(crr.getUuid(), crr.getRoomName()); 
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

    

 //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");


     int ussdCount = 0;
     StudentPaginator paginator = new StudentPaginator(accountuuid);
     StudentPage studentpage;

     studentpage = (StudentPage) session.getAttribute("currentPage");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (studentpage == null
                || !StringUtils.endsWith(referrer, "schoolIndex.jsp")
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
        

        final String ACTIVE_STATUS = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
        final String DAY_STATE = "Day";
        final String BOARDER_STATE = "Boarder";

        int dayStudentCount = 0;
        int boardingStudentCount = 0;

        dayStudentCount = studentDAO.getStudentCount(ACTIVE_STATUS,DAY_STATE,accountuuid);
        boardingStudentCount = studentDAO.getStudentCount(ACTIVE_STATUS,BOARDER_STATE,accountuuid);

%>                       
<jsp:include page="header.jsp" /> 

<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"> <a href="schoolIndex.jsp">Home</a></li>
      </ul><br>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search Anything...">
        <span class="input-group-btn">
          <button class="btn btn-default" type="button">
            <span class="glyphicon glyphicon-search"></span>
          </button>
        </span>
      </div>
       <hr>
      <div id="myCarousel" class="carousel slide">
          <!-- Carousel indicators -->
            <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                  <li data-target="#myCarousel" data-slide-to="2"></li>
                  </ol>
                  <!-- Carousel items -->
                  <div class="carousel-inner">
	                  <div class="item active">
	                  <img src="../img/slide/slide1.jpg" alt="First slide">
	                  <div class="carousel-caption">This Caption 1</div>
	                  </div>

	                  <div class="item">
	                  <img src="../img/slide/slide2.jpg" alt="Second slide">
	                  <div class="carousel-caption">This Caption 2</div>
	                  </div>

	                  <div class="item">
	                  <img src="../img/slide/slide3.jpg" alt="Third slide">
	                  <div class="carousel-caption">This Caption 3</div>
	                  </div>

	                   <div class="item">
	                  <img src="../img/slide/slide4.jpg" alt="Third slide">
	                  <div class="carousel-caption">This Caption 4</div>
	                  </div>

	                   <div class="item">
	                  <img src="../img/slide/slide5.png" alt="Third slide">
	                  <div class="carousel-caption">This Caption 5</div>
	                  </div>
                  </div>
                  <!-- Carousel nav -->
              <a class="carousel-control left" href="#myCarousel"
                  data-slide="prev">&lsaquo;</a>
              <a class="carousel-control right" href="#myCarousel"
                  data-slide="next">&rsaquo;</a>
          </div>
          <hr>
    </div>

    <div class="col-sm-9">
        <div class="breadcrumb">
            <h4><small> WELCOME TO  <%=schoolname%> : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> </small></h4>
       </div>
        <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> Day student count:  <%=dayStudentCount%>   ,    Boarding student count: <%=boardingStudentCount%> </h3>
            </div>

            <div class="panel-body">
                   <div class="table-responsive ">

                    <table class="table table-bordered">
                    <thead>
                      <tr>
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
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>

                   <%
                    String fullname = "";
                    
                    String status = "";
                    String primaryschool = "";
                    String kcpeindex = "";
                    String kcpemark = "";
                    String kcpeyear = ""; 

                    String gender = "";

                    int count = 1;
                    if(studentList !=null){
                    for(Student s : studentList){
                   

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



                    String statusUuid = "85C6F08E-902C-46C2-8746-8C50E7D11E2E";
                    if(StringUtils.equals(s.getStatusUuid(),statusUuid)){
                       status = "Active";
                       //active
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
                         <td class="center"><%=status%></td>
                            
                    </tr>

                       <%

                    }else{
                      status = "Inactive";
                         %>

                         <tr class="tabledit" style='color:#8B4789;'>
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
                         <td class="center"><%=status%></td>                            
                         </tr>

                        <%
                        }
                    %>
                   

                         <%
                          ussdCount++;
                       }
                   }
                            
                    %>
                 
                </tbody>
                </table>

           <div class="pagination">
                <form name="pageForm" method="post" action="schoolIndex.jsp">                                
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
                        <span class="pagePosition"><%=studentpage.getTotalPage()%></span>
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
  </div>  <!-- end panel body-->
  <div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>
</div>  <!-- end panel -->
</div>
</div>
</div>


<jsp:include page="footer.jsp" />