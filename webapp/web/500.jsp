<%
/**
Maasai Mara University Christian Union Online Management System.
Copyright 2015 Fastech Solutions Ltd
Licensed under the Open Software License, Version 3.0 
The codes herein AND/OR this file CAN BE copied without the author's approval for learning purposes or for use in one's own project
if need be, feel free to contact the author
Contacts, Mobile: +254718953974
         email: mwendapeter72@gmail.com
         email: petermwenda83@yahoo.com 
         @author peter<a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 */
%>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
    This page is displayed when there is a 500 server error (internal server error).
--%>
<%
    String contextPath = getServletContext().getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>We're sorry, but something went wrong (500)</title>
        <style type="text/css">
            body { background-color: #efefef; color: #333; font-family: Georgia,Palatino,'Book Antiqua',serif;padding:0;margin:0;text-align:center; }
            p {font-style:italic;}
            div.dialog {
                width: 490px;
                margin: 4em auto 0 auto;
            }
            img { border:none; }
        </style>
    </head>

    <body>
      <div class="dialog">
       
        <p>
           Looks like something went  wrong.<br /><br />
            
          go back <a href="<%= contextPath %>/school/schoolIndex.jsp">Home Page</a>. 
        </p>
        
      </div>
    </body>
    
</html>