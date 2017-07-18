<!DOCTYPE html>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>
<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%   

    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    String classuuid = "";
    String room  ="";
    String staffPosition  ="";
    String accountuuid = "";

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
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

%>                       

<html lang="en">
    <head>
        
        <meta charset="utf-8">
        <title>FastPro School Management System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content=".">
        <meta name="author" content="Peter Mwenda" >
         <!-- The fav icon -->
        <link rel="shortcut icon" href="../images/favicon.ico">
        
         <script src="../js/jquery/jquery-1.8.2.min.js"></script>  
         <script src="../js/jquery/jquery-1.7.2.min.js"></script>
         <script src="../js/searchstudent.js"></script>
         
        <link href="../css/bootstrap/bootstrap-cerulean.css" rel="stylesheet">
        <style type="text/css">
            body {
                padding-bottom: 40px;
            }
            .sidebar-nav {
                padding: 9px 0;
            }
        </style>
        <!-- jQuery --> 
       
  
      
        
        <link href="../css/bootstrap/bootstrap-responsive.css" rel="stylesheet">
        <link href="../css/fastech/charisma-app.css" rel="stylesheet">
        <link href="../css/jquery/jquery-ui-1.8.21.custom.css" rel="stylesheet">
        <link href='../css/fastech/fullcalendar.css' rel='stylesheet'>
        <link href='../css/fastech/fullcalendar.print.css' rel='stylesheet'  media='print'>
        <link href='../css/fastech/chosen.css' rel='stylesheet'>
        <link href='../css/fastech/uniform.default.css' rel='stylesheet'>
        <link href='../css/fastech/colorbox.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.cleditor.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.noty.css' rel='stylesheet'>
        <link href='../css/fastech/noty_theme_default.css' rel='stylesheet'>
        <link href='../css/fastech/elfinder.min.css' rel='stylesheet'>
        <link href='../css/fastech/elfinder.theme.css' rel='stylesheet'>
        <link href='../css/jquery/jquery.iphone.toggle.css' rel='stylesheet'>
        <link href='../css/fastech/opa-icons.css' rel='stylesheet'>
        <link href='../css/fastech/uploadify.css' rel='stylesheet'>
        <link href='../css/fastech/template.css' rel='stylesheet'>
        <link href='../css/fastech/checkpass.css' rel='stylesheet'>
        <link href='../css/fastech/styles.css' rel='stylesheet'>
        <link href='../css/fastech/chatStyle.css' rel='stylesheet'>

        <link rel="stylesheet" href="../css/site.css" type="text/css" media="all">
        <link href="../js/fastech/cropper/cropper.min.css" rel="stylesheet">


        
        
       
        
      

    </head>

    <body>


    <div class="navbar">
          <div class="navbar-inner">
          <div class="container-fluid">
          <div class="btn-group pull-right theme-container" >
          </div>
         

          <!-- user dropdown starts -->
          <div class="btn-group pull-right" >
          <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
          <i class="icon-user"></i><span class="hidden-phone"> <%=staffUsername%></span>
          <span class="caret"></span>
          </a>
          <ul class="dropdown-menu">
          <!--<li><a href="#">Profile</a></li>-->
          <li class="divider"></li>
          <li><a href="../schoolLogout">Logout</a></li>
          <li><a href="profile.jsp">Profile</a></li>
          <li ><a href="help.html" target="_blank">Help</a></li>
          </ul>
          </div>

          <!-- user dropdown ends -->
          <div class="top-nav nav-collapse">
          </div><!--/.nav-collapse -->
          </div>

      <!-- top menu -->
      <div class="topmenu">


       <!--CLASS TEACHER-->


                <% if(StringUtils.contains(room, FORM1)){ %>
                  <a href="teacherClassF1.jsp">MY CLASS</a>   
                <%} else if(StringUtils.contains(room, FORM2)){%>
                  <a href="teacherClassF2.jsp">MY CLASS</a>   
                <%}else if(StringUtils.contains(room, FORM3)){%>
                  <a href="teacherClassF3.jsp">MY CLASS</a>   
                <%}else if(StringUtils.contains(room, FORM4)){%>
                 <a href="teacherClassF4.jsp">MY CLASS</a>   
                <%}%>




               <!--PRINCIPAL-->

        <% if(StringUtils.equals(staffPosition,pos_Pricipal)){ %>
          <a href="studentIndex.jsp">STUDENTS</a>
          <a href="fee.jsp">FINANCE</a>
          <a href="staff.jsp">STAFF</a>
                <a href="examConfig.jsp">CONTROL PANEL</a>
          
         
             <% }  %>

                <!--DEPUTY PRINCIPAL-->

            <%  if(StringUtils.equals(staffPosition,pos_DeputyPricipal)){ %>
          <a href="studentIndex.jsp">STUDENTS</a>
          <a href="teacherSubject.jsp">MY SUBJECTS</a>
                <a href="staff.jsp">STAFF</a>
                <a href="reports.jsp">REPORTS</a>  
             <% }  %>





             

           <!--CM-CURRICULUM MASTER-->

             <%  if(StringUtils.equals(staffPosition,pos_CM)){ %>
             <a href="studentIndex.jsp">STUDENTS</a>
                   <a href="teacherSubject.jsp">MY SUBJECTS</a>
             <a href="reports.jsp">REPORTS</a>  
                   <a href="examConfig.jsp">CONTROL PANEL</a>
                   <a href="staff.jsp">STAFF</a>
                   <a href="settings.jsp">MORE</a>
      
             <% }  %>


                   <!--HOD-->

              <%  if(StringUtils.equals(staffPosition,pos_HOD)){ %>
             <a href="studentIndex.jsp">STUDENTS</a>
                   <a href="teacherSubject.jsp">MY SUBJECTS</a>
             <a href="reports.jsp">REPORTS</a> 
    
             <% }  %>





                    <!--TEACHER-->


       <%  if(StringUtils.equals(staffPosition,pos_Teacher)){ %>
               <a href="perclassUpload.jsp">UPLOAD EXAM</a>
               <a href="teacherSubject.jsp">MY SUBJECTS</a>
       
            <% }%>

            


               <!--BURSAR -->

         <%  if(StringUtils.equals(staffPosition,pos_Bursar)){ %>
           <a href="fee.jsp">FINANCE</a>
                 <a href="studentIndex.jsp">STUDENTS</a>
                 
            <% }  %>


                   <!--SECRETARY--> 


           <%  if(StringUtils.equals(staffPosition,pos_Secretary)){ %>
         
               <a href="studentIndex.jsp">STUDENTS</a>
             
            <% }  %>
          
          
      </div>

      </div>
      </div>

    



 </div>

</div>
</div>
<!-- topbar ends -->
<div class="container-fluid">
<div class="row-fluid">
<!-- left menu starts -->
<div class="span2 main-menu-span">
<div class="well nav-collapse sidebar-nav">
<ul class="nav nav-tabs nav-stacked main-menu">

<!--menu to change depending on page requested-->
<li class="nav-header hidden-tablet">MORE</li>
<li><a href="schoolIndex.jsp" class="ajax-link" id ="btn-dangers1"href=""><i class="icon-envelope"></i><span class="hidden-tablet">HOME</span></a></li>
<li><a href="lib.jsp" class="ajax-link" id ="btn-dangers1"href=""><i class="icon-envelope"></i><span class="hidden-tablet">LIBRARY</span></a></li>
 <%  if(StringUtils.equals(staffPosition,pos_Pricipal)){ %>
<li><a href="studentHouse.jsp" id ="btn-dangers1" href="" title="student house" data-rel="tooltip"><i class="icon-folder-open"></i><span class="hidden-tablet">HOUSE</span></a></li>
<li><a href="parents.jsp" class="ajax-link"id ="btn-dangers1" href=""><i class="icon-home"></i><span class="hidden-tablet">PARENTS</span></a></li>
<li><a href="studentSponsor.jsp" class="ajax-link" id ="btn-dangers1"href=""><i class="icon-envelope"></i><span class="hidden-tablet">SPONSORS</span></a></li>
<li><a href="reports.jsp" class="ajax-link"id ="btn-dangers1" href=""><i class="icon-edit"></i><span class="hidden-tablet">REPORTS</span></a></li>
<li><a href="settings.jsp" class="ajax-link"id ="btn-dangers1" href=""><i class="icon-edit"></i><span class="hidden-tablet">MORE</span></a></li>


  <% }  %>



</ul>
</div><!--/.well -->
</div><!--/span-->
<!-- left menu ends -->


<noscript>
    <div class="alert alert-block span10">
    <h4 class="alert-heading">Warning!</h4>
    <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
    </div>
</noscript>
<div id="content" class="span10">
<!-- content starts -->

                   
              
            