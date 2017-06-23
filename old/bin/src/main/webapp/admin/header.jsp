<!DOCTYPE html>

<%@page import="com.yahoo.petermwenda83.server.session.AdminSessionConstants"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
if (session == null) {
        response.sendRedirect("index.jsp");
    }
 String username = (String) session.getAttribute(AdminSessionConstants.ADMIN_SESSION_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("index.jsp");
    }

     session.setMaxInactiveInterval(AdminSessionConstants.SESSION_TIMEOUT);
     response.setHeader("Refresh", AdminSessionConstants.SESSION_TIMEOUT + "; url=../adminLogout");

     
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

    <title>Admin FasTech School Management System</title>
 
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
                <li><a href="adminIndex.jsp">Home</a></li>
                <li><a href="rootUser.jsp">Add Principal</a></li>

          </ul>
          <ul class="nav navbar-nav navbar-right">
               <li class="dropdown">
                <a href="" class="dropdown-toggle" data-toggle="dropdown">
                  <button type="button" class="btn btn-default btn-xs">
                 <span class="glyphicon glyphicon-user"></span> <%=username%>
                </button>
                <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="index.jsp">Logout</a></li>
                </ul>
               </li>
          </ul>
        </div>
      </div>
    </nav> 

                <noscript>
                <div class="alert alert-block span10">
                    <h4 class="alert-heading">Warning!</h4>
                    <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
                </div>
                </noscript>





