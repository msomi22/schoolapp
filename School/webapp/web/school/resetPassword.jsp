<!DOCTYPE html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>


<html>
    <head>
        <title>Forgot Password</title>
    <link rel="stylesheet" type="text/css" href="../css/fastech/resetPassword.css">    
       </head>
    
    
    <body>      
    
        <div class="container"> 

        <div class="babble">            
        <h1><a href="?">Reset Password</a><h1>
        </div>

        <form class="form-horizontal" action="forgotPassword" method="POST">
        <ul>
        <li>
        <div class="input-prepend" title="Enter Username" data-rel="tooltip">
        <label class="complex-input auth-input" autocapitalize="off" autocorrect="off" data-nb="input">        
                <input class="input-controller" autocapitalize="off" autocorrect="off" placeholder="Enter Username" type="text" 
                                name="username" id="username" value=""  size="15">
                                        
             
                </label> 
        </div>
        </li>

        <li>
        <button class="button1" type="submit" name="submit">Reset Password</button>
        </li>
        </ul>
       </form>

        </div>

      

    </body>
</html>
