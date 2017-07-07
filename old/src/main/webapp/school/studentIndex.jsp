
<%

%>

<%@page
	import="com.yahoo.petermwenda83.pagination.student.StudentPaginator"%>
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPage"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.PrimaryDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentPrimary"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page
	import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>


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

  if (session == null) {
       response.sendRedirect("../index.jsp");
       //return;
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
        //return;
    }

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../index.jsp");
    //return;


    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    
    Account school = new Account();
    Element element;
   

    if ((element = accountsCache.get(username)) != null) {
        school = (Account) element.getObjectValue();
    }

     String accountId = school.getUuid();
     String schoolname = school.getName();
     
     StudentDAO studentDAO = StudentDAO.getInstance();
     SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();
     SysConfig sysConfig = new SysConfig();
     sysConfig = sysConfigDAO.getSysConfig(accountId);
     
     List<Student> studentList = new ArrayList<>();
     if(studentDAO.getAllStudent(accountId, 0, 15) != null){
    	 studentList = studentDAO.getAllStudent(accountId, 0, 15);
     }
     
    

     int studentCount = 0;
     StudentPaginator paginator = new StudentPaginator(accountId);
     StudentPage studentpage;

     studentpage = (StudentPage) session.getAttribute("currentPage");
        String referrer = request.getHeader("referer");
        String pageParam = (String) request.getParameter("page");

        // We are to give the first page
        if (studentpage == null
                || !StringUtils.endsWith(referrer, "studentIndex.jsp")
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
        studentCount = (studentpage.getPageNum() - 1) * studentpage.getPagesize() + 1;
      // }


 //date format
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat timezoneFormatter = new SimpleDateFormat("z");
%>
<jsp:include page="header.jsp" />



<div class="container-fluid">
	<div class="row content">
		<div class="col-sm-3 sidenav">
			<h4>Quick Links</h4>
			<ul class="nav nav-pills nav-stacked">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#">New Student</a></li>
				<li><a href="#">Import Excel</a></li>
				<li><a href="#">Parents</a></li>
				<li><a href="#">More...</a></li>
			</ul>
			<br>
			<div class="input-group">
				<input type="text" class="form-control"
					placeholder="Search Anything..."> <span
					class="input-group-btn">
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
				</div>
				<!-- Carousel nav -->
				<a class="carousel-control left" href="#myCarousel"
					data-slide="prev">&lsaquo;</a> <a class="carousel-control right"
					href="#myCarousel" data-slide="next">&rsaquo;</a>
			</div>
			<hr>
		</div>

		<div class="col-sm-9">
			<div class="breadcrumb">
				WELCOME TO:
				<%=schoolname%>
				, Term:
				<%=sysConfig.getTerm() %>
				, Year:
				<%=sysConfig.getYear() %>
			</div>


			<nav class="navbar navbar-default" role="navigation">
				<div>
					<form class="navbar-form navbar-left" role="search" action="#"
						method="get">
						<div class="form-group">
							<input type="text" class="form-control"
								placeholder="Search By AdmNo"
								onkeyup="searchstudents(this.value)">
						</div>

						<div class="btn-group">
							<button type="button" class="btn btn-primary dropdown-toggle"
								data-toggle="dropdown">
								Sort-By <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="">RegNo</a></li>
								<li><a href="">First name</a></li>
								<li><a href="">Middle name</a></li>
								<li><a href="">Last name</a></li>
								<li><a href="">Gender</a></li>
								<li><a href="">Class</a></li>
							</ul>
						</div>
					</form>

				</div>

			</nav>




			<%             

                                String updateErr = "";
                                String updateSuccess = "";
                                session = request.getSession(false);
                                     updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                     updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErr = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_ERROR);
                                    updateSuccess = (String) session.getAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErr)) {
                                    %>
			<div class="alert alert-warning">
				<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Warning!</strong>
				<%
                                          out.println("error: " + updateErr);
                                           %>
			</div>

			<%                                 
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updateSuccess)) {
                                   %>
			<div class="alert alert-success">
				<a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Success!</strong>
				<%
                                           out.println(": " + updateSuccess);
                                           %>
			</div>

			<%                                
                                    session.setAttribute(SessionConstants.STUDENT_UPDATE_SUCCESS,null);
                                  } 



                      %>





			<!-- panel start -->
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">Student list</h3>
				</div>
				<div class="panel-body">

					<div class="table-responsive ">

						<table class="table table-bordered">
							<thead>
								<tr>
									<th>*</th>
									<th>RegNo</th>
									<th>First name</th>
									<th>Middle name</th>
									<th>Last name</th>
									<th>Gender</th>
									<th>Class</th>
									<th>Profile</th>
								</tr>
							</thead>

							<tbody class='tablebody'>

								<%
                  for(Student student : studentList){                	  
                	  %>

								<tr class="tabledit" style='color: black;'>

									<td width="3%"><%=studentCount%></td>
									<td class="center"><%=student.getRegNo() %></td>
									<td class="center"><%=student.getFirstname() %></td>
									<td class="center"><%=student.getMiddlename() %></td>
									<td class="center"><%=student.getLastname() %></td>
									<td class="center"><%=student.getGender() %></td>
									<td class="center"><%="" %></td>
									<td class="center"><a href=""> Profile</a></td>

								</tr>

								<%      
                	  studentCount++;
                  }
                  
                  %>

							</tbody>
						</table>

						<div id="pagination">
							<form name="pageForm" method="post" action="studentIndex.jsp">
								<%                                            
                        if (!studentpage.isFirstPage()) {
                    %>
								<input class="toolbarBtn" type="submit" name="page"
									value="First" /> <input class="toolbarBtn" type="submit"
									name="page" value="Previous" />
								<%
                        }
                    %>
								<span class="pageInfo">Page <span
									class="pagePosition currentPage"><%= studentpage.getPageNum()%></span>
									of <span class="pagePosition"><%= studentpage.getTotalPage()%></span>
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
				</div>
				
				<!-- end panel body-->
				<div class="panel-footer">
					<div class="row">
						<div class="col col-xs-4">
							<small> <i>****</i>
							</small>
						</div>
					</div>
				</div>
			</div>
			<!-- end panel -->
		</div>
	</div>
</div>

<jsp:include page="footer.jsp" />

