<!DOCTYPE html>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.AcessLevel"%>
<%@page import="org.apache.commons.lang3.RandomStringUtils"%>
<%@page import="org.jasypt.util.text.BasicTextEncryptor"%>
<%@page
	import="com.yahoo.petermwenda83.server.servlet.util.FontImageGenerator"%>
<%@page
	import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page
	import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page
	import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Calendar"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%

   

     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();

    BasicTextEncryptor textEncryptor = new BasicTextEncryptor();   
    textEncryptor.setPassword(PropertiesConfig.getConfigValue("ENCRYPT_PASSWORD")); 
      
    String captcha = RandomStringUtils.randomAlphabetic(4); 
    String encryptedCaptcha = textEncryptor.encrypt(captcha);


%>



<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>School - app</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="css/blue.css">

</head>
<body class="hold-transition login-page">
	<div class="login-box">
		<div class="login-logo">
			<a href="#"><b>School APP</b></a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg">Sign in here</p>


			<%
  String loginErrStr = "";
  session = request.getSession(false);

  if(session != null) {
      loginErrStr = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR);
  }                        

  if (StringUtils.isNotEmpty(loginErrStr)) {
      %>
			<div class="alert alert-warning">
				<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Warning!</strong>
				<%
     out.println("Login error: " + loginErrStr);
     %>
			</div>

			<%                                 
    session.setAttribute(SessionConstants.SCHOOL_ACCOUNT_LOGIN_ERROR, null);
  } 

 %>





			<form action="schoolLogin" method="post">

				<div class="form-group has-feedback">
					<input type="text" class="form-control" placeholder="Username">
					<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
				</div>

				<div class="form-group">
					<label>Category</label> 
					<select class="form-control" name="staffposition" required>
						<%
                  int count = 1;
                  if (positionList != null) {
                      for (Position p : positionList) {
                   %>
							<option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
							<%
                          count++;
                      }
                  }
                 %>
					</select>
				</div>


				<div class="form-group has-feedback">
					<input type="password" class="form-control" placeholder="Password">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>

				<div class="row">
					<div class="col-xs-8">
						<div class="checkbox icheck">
							<label> <input type="checkbox"> Remember Me
							</label>
						</div>
					</div>
					<!-- /.col -->
					<div class="col-xs-4">
						<button type="submit" class="btn btn-primary btn-block btn-flat">Sign
							In</button>
					</div>
					<!-- /.col -->
				</div>
			</form>

			<a href="#">I forgot my password</a><br>
		</div>
		<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->

	<!-- jQuery 2.2.3 -->
	<script src="js/jquery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="js/bootstrap/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="js/icheck.min.js"></script>
	<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>
