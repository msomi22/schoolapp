<!DOCTYPE html>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>
<%@page
	import="com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO"%>

<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page
	import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="java.util.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
	if (session == null) {
		response.sendRedirect("../index.jsp");
		//return;
	}

	String user_name = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
	if (StringUtils.isEmpty(user_name)) {
		response.sendRedirect("../index.jsp");
		//return;
	}

	String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID);
	if (StringUtils.isEmpty(accountId)) {
		response.sendRedirect("../index.jsp");
		//return;
	}

	session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
	//response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
	response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");

	String username = "";

	AccountDAO accountDAO = AccountDAO.getInstance();
	accountDAO.getAccountById(accountId);

	Account account = new Account();

	account = accountDAO.getAccountById(accountId);

	username = account.getUsername();

	String user = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
	String userId = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
	String userAccessLevel = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_CATEGORY);
%>


<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>ScoolApp</title>

<!-- Bootstrap -->
<link href="../vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<link
	href="../vendors/bootstrap-datetimepicker/bootstrap-datepicker.min.css"
	rel="stylesheet">

<!-- Font Awesome -->
<link href="../vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link href="../vendors/nprogress/nprogress.css" rel="stylesheet">
<!-- iCheck -->
<link href="../vendors/iCheck/skins/flat/green.css" rel="stylesheet">

<!-- bootstrap-progressbar -->
<link
	href="../vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"
	rel="stylesheet">
<!-- JQVMap -->
<link href="../vendors/jqvmap/dist/jqvmap.min.css" rel="stylesheet" />
<!-- bootstrap-daterangepicker -->
<link href="../vendors/bootstrap-daterangepicker/daterangepicker.css"
	rel="stylesheet">

<link
	href="../vendors/datatables.net-bs/css/dataTables.bootstrap.min.css"
	rel="stylesheet">
<link
	href="../vendors/datatables.net-buttons-bs/css/buttons.bootstrap.min.css"
	rel="stylesheet">
<link
	href="../vendors/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css"
	rel="stylesheet">
<link
	href="../vendors/datatables.net-responsive-bs/css/responsive.bootstrap.min.css"
	rel="stylesheet">
<link
	href="../vendors/datatables.net-scroller-bs/css/scroller.bootstrap.min.css"
	rel="stylesheet">

<!-- sumoselect -->

<link href="../vendors/sumoselect/sumoselect.css" rel="stylesheet">

<!-- Custom Theme Style -->
<link href="../build/css/custom2.css" rel="stylesheet">


<!-- custom form elements style -->


<link rel="stylesheet" href="css/formelementBorder.css">

<link rel="stylesheet" href="css/ui-styling2.css">



<link rel="icon" href="images/favicon.ico">

<!-- Date and time picker -->

<link rel="stylesheet" href="css/pikaday.css">





</head>

<body class="nav-md footer_fixed"  oncontextmenu="return false" onload="disableBackButton()">
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col menu_fixed">
				<div class="left_col scroll-view">
					<div class="navbar nav_title" style="border: 0;">
						<a href="studentIndex.jsp" class="site_title"><i
							class="fa fa-graduation-cap"></i> <span>SchoolApp</span></a>
					</div>

					<div class="clearfix"></div>

					<!-- menu profile quick info -->
					<div class="profile clearfix">
						<div class="profile_pic">
							<img src="images/user.png" alt="..."
								class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>Welcome,</span>
							<h2><%=user%></h2>
						</div>
					</div>
					<!-- /menu profile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">
						<div class="menu_section">

							<h3>General</h3>
							<ul class="nav side-menu">
								<li id="studentMenu"><a><i class="fa fa-home"></i> Students <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li id="studentListMenu"><a href="studentIndex.jsp">Students List</a></li>
										<li id="studentNewMenu"><a href="registerStudent.jsp">New Student</a></li>

										<li><a href="studentsClasses.jsp">Student and Class</a></li>
									</ul></li>

								<li id="academicsMenu"><a><i class="fa fa-book"></i> Academics <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="generateReport.jsp">Exam Reports</a></li>
										<li><a href="submitExam.jsp">Submit Exam</a></li>


									</ul></li>

								<li id="staffMenu"><a><i class="fa fa-users"></i> Staff <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="staff.jsp">Staff</a></li>
										<li><a href="classTeachers.jsp">Class Teachers</a></li>
									</ul></li>



								<li id="financeMenu"><a><i class="fa fa-money"></i> Finance <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li><a href="fee.jsp">Fee Payment</a></li>
										<li><a href="termAndOthersFee.jsp">Term and Misc Fee</a></li>
										<li><a href="freeEducation.jsp">Govt Fund</a></li>
										<li><a href="#">Pocket Money</a></li>
									</ul></li>

								<li id="controlMenu"><a><i class="fa fa-cog"></i> Control Panel <span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu">
										<li id="examsMenu"><a href="exam.jsp">Exam</a></li>
										<li id="streamsMenu"><a href="streams.jsp">Stream</a></li>
										<li id="settingsMenu"><a href="settings.jsp">Settings</a></li>
										<li id="miscSettingsMenu"><a href="misc.jsp">Misc Settings</a></li>
									</ul></li>


							</ul>
						</div>



					</div>
					<!-- /sidebar menu -->

					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="Settings">
							<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="FullScreen">
							<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Lock"> <span
							class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Logout"
							href="#"> <span class="glyphicon glyphicon-off"
							aria-hidden="true"></span>
						</a>
					</div>
					<!-- /menu footer buttons -->
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">
				<div class="nav_menu themeColor">
					<nav class="themeColor">
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>




						<ul  class="nav navbar-nav navbar-right">

							<li class="pull-left">
								<h3><%=account.getName()%></h3>
							</li>

							<li class="pull-left">
								<h3 class="year sec_text "></h3>
							</li>
							<li class="pull-left">
								<h3 class="term sec_text "></h3>
							</li>
							
							
						



							<li class="pull-right"><a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img src="images/user.png" alt=""><%=user%>
									<span class=" fa fa-angle-down"></span>
							</a>
								<ul class="dropdown-menu dropdown-usermenu pull-right">
									<li><a href="#" onclick="changePasswordModal()">
											Profile</a> <input type="hidden" id="user" value="<%=user%>">
											<input type="hidden" id="globalAccountId" value="<%=accountId%>">
										<input type="hidden" id="userId" value="<%=userId%>">
										<input type="hidden" id="accessLevel" value="<%=userAccessLevel%>">

									</li>
									<li><a href="settings.jsp"> <span
											class="badge bg-red pull-right">New</span> <span>Settings</span>
									</a></li>
									<li><a href="javascript:;">Help</a></li>
									<li><a href="../schoolLogout"><i
											class="fa fa-sign-out pull-right"></i> Log Out</a></li>
								</ul></li>
								
								<!-- 	<li style="" role="presentation" class="dropdown"><a
								href="javascript:;" class="dropdown-toggle info-number"
								data-toggle="dropdown" aria-expanded="false"> <i
									class="fa fa-envelope-o"></i> <span class="badge bg-green"></span>
							</a>
								<ul id="menu1" class="dropdown-menu list-unstyled msg_list"
									role="menu">
									<li><a> <span class="image"><img
												src="images/img.jpg" alt="Profile Image" /></span> <span> <span>Peter
													Mwenda</span> <span class="time">3 mins ago</span>
										</span> <span class="message"> New system coming soon... </span>
									</a></li>
									<li>
										<div class="text-center">
											<a> <strong>See All Alerts</strong> <i
												class="fa fa-angle-right"></i>
											</a>
										</div>
									</li>
								</ul></li> -->

							
						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->