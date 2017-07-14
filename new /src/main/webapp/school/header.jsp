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
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
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

   

    Account school = new Account();
    

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
           
           </ul>
        </div>
      </div>
    </nav> 