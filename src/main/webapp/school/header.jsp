<!DOCTYPE html>
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
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>
<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>
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

  
    HashMap<String, String> classroomHash = new HashMap<String, String>();
    List<ClassRoom> classList = new ArrayList<ClassRoom>();
    classList = roomDAO.getAllRooms(accountuuid);
    if(classList !=null){
    for(ClassRoom crr : classList){
       classroomHash.put(crr.getUuid(), crr.getRoomName()); 
         }
     }

%>                       

  <html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../img/favicon.ico">

    <title>FastPro V3</title>
 
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.min.css">
       
    <style type="text/css">
    body {
      padding-top: 70px;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: auto}
    
    /* Set gray background color and 100% height */
    .sidenav {
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;}
    }

  </style>


  </head>

  
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand">FastPro School</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
           <!--PRINCIPAL-->
            <% if(StringUtils.equals(staffPosition,pos_Pricipal)){ %>
            <li class="active"><a href="studentIndex.jsp">Students</a></li>
            <li><a href="fee.jsp">Finance</a></li>
            <li><a href="staff.jsp">Staff</a></li>
            <li><a href="examConfig.jsp">Control Panel</a></li>
            <li> <a href="lib.jsp">Library</a>  </li>
             <%}%>
            <!--DEPUTY PRINCIPAL-->
             <%  if(StringUtils.equals(staffPosition,pos_DeputyPricipal)){ %>
             <li class="active"><a href="studentIndex.jsp">Students</a></li>
             <li><a href="staff.jsp">Staff</a></li>
             <li><a href="examConfig.jsp">Control Panel</a></li>
             <li> <a href="lib.jsp">Library</a>  </li>
             <% }  %>

            <li class="dropdown">
              <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Examination <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Exam Results</li>
                <!--CLASS TEACHER-->
                <% if(StringUtils.contains(room, FORM1)){ %>
                <li class=""> <a href="teacherClassF1.jsp">My Class</a>   </li>
                 <%} else if(StringUtils.contains(room, FORM2)){%>
                <li class=""> <a href="teacherClassF2.jsp">My Class</a>  </li>
                 <%}else if(StringUtils.contains(room, FORM3)){%>
                <li class=""> <a href="teacherClassF3.jsp">My Class</a>  </li>
                 <%}else if(StringUtils.contains(room, FORM4)){%>
                <li class=""> <a href="teacherClassF4.jsp">My Class</a> </li>
                 <%}%>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">More..</li>
                <% if(StringUtils.equals(staffPosition,pos_Pricipal) || 
                      StringUtils.equals(staffPosition,pos_DeputyPricipal) || 
                      StringUtils.equals(staffPosition,pos_CM) || 
                      StringUtils.equals(staffPosition,pos_HOD) || StringUtils.equals(staffPosition,pos_Teacher) ){ %>
                <li><a href="reports.jsp">Reports</a></li> 
                 <%}%>

              </ul>
            </li>
             
             <!--CM-CURRICULUM MASTER-->
             <%  if(StringUtils.equals(staffPosition,pos_CM)){ %>
              <li class="active"><a href="studentIndex.jsp">Students</a></li>
              <li><a href="reports.jsp">Reports</a></li> 
              <li> <a href="teacherSubject.jsp">My Subjects</a> </li>
              <li><a href="staff.jsp">Staff</a></li>
              <li><a href="examConfig.jsp">Control Panel</a></li>

              <% }  %>
              <!--HOD-->
               <%  if(StringUtils.equals(staffPosition,pos_HOD)){ %>
                 <li> <a href="teacherSubject.jsp">My Subjects</a> </li>
                 <li><a href="reports.jsp">Reports</a></li> 
                <% }  %>

                 <!--TEACHER-->
                <%  if(StringUtils.equals(staffPosition,pos_Teacher)){ %>
                <li> <a href="perclassUpload.jsp">Upload Exam</a> </li>
                <li> <a href="teacherSubject.jsp">My Subjects</a> </li>

                 <% }%>

                  <!--BURSAR -->
                  <%  if(StringUtils.equals(staffPosition,pos_Bursar)){ %>
                   <li><a href="fee.jsp">Finance</a></li>
                   <% }  %>
                  <!--SECRETARY--> 
                  <%  if(StringUtils.equals(staffPosition,pos_Secretary)){ %>
                  <li class="active"><a href="studentIndex.jsp">Students</a></li>
                  <li> <a href="lib.jsp">Library</a>  </li>
                  <% }  %>

                <li><a href="chat.jsp">Chat</a></li>

          </ul>
          <ul class="nav navbar-nav navbar-right">
               <li class="dropdown">
                <a href="" class="dropdown-toggle" data-toggle="dropdown">
                  <button type="button" class="btn btn-default btn-xs">
                 <span class="glyphicon glyphicon-user"></span> <%=staffUsername%>
                </button>
                <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="profile.jsp">Profile</a></li> <br>
                    <li><a href="help.html" target="_blank">Help</a></li> <br>
                    <li><a href="../schoolLogout">Logout</a></li>
                </ul>
               </li>
          </ul>
        </div>
      </div>
    </nav> 