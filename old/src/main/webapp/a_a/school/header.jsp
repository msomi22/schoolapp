<!DOCTYPE html>


<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>
<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
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
            <li class="active"><a href="studentIndex.jsp">Students</a></li>
            <li><a href="#">Finance</a></li>
            <li><a href="#">Staff</a></li>
            <li><a href="#">Control Panel</a></li>
            <li> <a href="#">Library</a>  </li>
             
            <li><a href="#">Chat</a></li>

          </ul>
          <ul class="nav navbar-nav navbar-right">
               <li class="dropdown">
                <a href="" class="dropdown-toggle" data-toggle="dropdown">
                  <button type="button" class="btn btn-default btn-xs">
                 <span class="glyphicon glyphicon-user"></span> <%="" %>
                </button>
                <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="#">Profile</a></li> <br>
                    <li><a href="help.html" target="_blank">Help</a></li> <br>
                    <li><a href="#">Logout</a></li>
                </ul>
               </li>
          </ul>
        </div>
      </div>
    </nav> 