
<%@page import="com.yahoo.petermwenda83.pagination.student.StudentPaginator"%>
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



<%@page import="java.util.*"%>

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




        <!-- page content -->
        <div class="right_col" role="main">
         <div class="">
          <!-- top tiles -->
          <div class="row tile_count">
            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Active Students</span>
              <div class="count"><%=studentDAO.activeCount(accountId, "1") %></div> 
              <span class="count_bottom"><i class="green"><%=studentDAO.activeCount(accountId, "0") %> </i> Inactive</span>
            </div>


            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Day Students</span>
              <div class="count"><%=studentDAO.dayCount(accountId, "1", "0") %></div> 
              <span class="count_bottom"><i class="green"><i class="fa fa-sort-asc"></i><%=studentDAO.dayCount(accountId, "1", "1") %> </i> Boarding</span>
            </div>


            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Alumni</span>
              <div class="count green"><%=studentDAO.alumniCount(accountId, "1") %></div> 
              
            </div>


            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i> Female Students</span>
              <div class="count"><%=studentDAO.genderCount(accountId, "1", "F") %></div> 
              
            </div>


            <div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count">
              <span class="count_top"><i class="fa fa-user"></i>Male Students </span>
              <div class="count"><%=studentDAO.genderCount(accountId, "1", "M") %></div> 
             
            </div>


			<div class="col-md-2 col-sm-4 col-xs-6 tile_stats_count pull-right">
				<span class="count_top"><i class="fa fa-user"></i>Add new student </span>
				<div class="count"><a role="button" class="btn btn-lg btn-primary secondary-assent" href="registerStudent.jsp"><i class="fa fa-user-plus" aria-hidden="true"></i></a></div>

			</div>


		</div>
          <!-- /top tiles -->


         
          <div class="clearfix"></div>

           <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>Students <small>List</small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>                      
                    </ul>
                    <div class="clearfix"></div>
                  </div>

                <div class="x_content">

                  <div class="table-responsive">
                    <table class="table table-striped jambo_table bulk_action" id="studentsList">
                        <thead>
                          <tr class="headings secondary-assent">

                            <th class="column-title"># </th>
                            <th class="column-title">RegNo </th>
                            <th class="column-title">First name </th>
                            <th class="column-title">Middle name </th>
                            <th class="column-title">Last name </th>
                            <th class="column-title">Gender </th>
                            <th class="column-title">Class </th>
                            <th class="column-title no-link last"> 
                              <span class="nobr"> Profile </span> 
                            </th>
                            
                          </tr>
                        </thead>

              <tbody class='tablebody'>

                <%
                  for(Student student : studentList){                   
                    %>

                <tr class="tabledit" style='color: black;'>

                  <td width="5%"><%=studentCount%>. </td>
                  <td class="center"><%=student.getRegNo() %></td>
                  <td class="center"><%=student.getFirstname() %></td>
                  <td class="center"><%=student.getMiddlename() %></td>
                  <td class="center"><%=student.getLastname() %></td>
                  <td class="center"><%=student.getGender() %></td>
                  <td class="center"><%="" %></td>
                  <td class="center"> <a href="profile.jsp?uuid=<%=student.getUuid() %>" > Profile</a> </td>                 

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
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="submit" name="page"
                  value="First" /> <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="submit"
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
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="submit" name="page" value="Next">
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="submit" name="page" value="Last">
                <%
                       }
                    %>
              </form>
          </div>
            </div>              
          </div>
        </div>
      </div>
  </div>
  <div class="clearfix"></div>
  </div>
</div>
<div class="clearfix"></div>
<!-- /page content -->


<!-- footer -->
<jsp:include page="footer.jsp" />





