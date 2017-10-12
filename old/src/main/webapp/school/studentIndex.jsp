
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
    
    	String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID);


    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    
    Account school = new Account();
    Element element;
   

    if ((element = accountsCache.get(username)) != null) {
        school = (Account) element.getObjectValue();
    }

   
     String schoolname = school.getName();
     
     StudentDAO studentDAO = StudentDAO.getInstance();
     SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();
     SysConfig sysConfig = new SysConfig();
     sysConfig = sysConfigDAO.getSysConfig(accountId);
     
    


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
                  
                  <input type="hidden" id="accountId" value="<%=accountId%>">

                <div class="x_content">
                
                <div class="row">
                
                <div class="col-md-4 pull-right">
                
                <input type="text" id="query" name="query"
													class="form-control formelement cards"
													placeholder="Search for students" 
													maxlength="10" onkeyup="delayInput()" onpaste="return false;"
												
													required> <span
													class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>
                
                
                
                </div>

                  <div class="table-responsive">
                    <table class="table table-striped jambo_table bulk_action" id="studentsList">
                        <thead>
                          <tr class="headings secondary-assent">

                           <th class="column-title hidden">uuid</th>
                           <th class="column-title hidden">accountId</th>
                           <th class="column-title hidden">regStream</th>
                           <th class="column-title hidden">currentStream</th>
                           <th class="column-title hidden">isActive</th>
                           <th class="column-title hidden">isAlumni</th>
                           <th class="column-title hidden">isBoarding</th>
                           
                            <th class="column-title">regNo</th>
                            <th class="column-title">firstname</th>
                            <th class="column-title">middlename</th>
                            <th class="column-title">lastname</th>
                            <th class="column-title">gender</th>
                            <th class="column-title hidden">dob</th>
                            <th class="column-title hidden">bcertNo</th>
                            <th class="column-title hidden">county</th>
                            <th class="column-title hidden">regTerm</th>
                            <th class="column-title hidden">finalYear</th>
                            <th class="column-title hidden">finalTerm</th>
                            <th class="column-title hidden">passport</th>
                            
                            <th class="column-title hidden">hasParent</th>
                            <th class="column-title hidden">parentName</th>
                            <th class="column-title hidden">parentMobile</th>
                            <th class="column-title hidden">parentEmail</th>
                            <th class="column-title hidden">hasPrimary</th>
                            <th class="column-title hidden">schoolName</th>
                            <th class="column-title hidden">index</th>
                            <th class="column-title hidden">kcpeyear</th>
                            <th class="column-title hidden">kcpemark</th>
                           
                            <th class="column-title"> 
                               Profile
                            </th>
                            
                          </tr>
                        </thead>

              <tbody class='tablebody'>

               <%--   <%
                  for(Student student : studentList){                   
                    %>

                <tr class="tabledit" style='color: black;'>

                 
                  <td class="center"><%=student.getRegNo() %></td>
                  <td class="center"><%=student.getFirstname() %></td>
                  <td class="center"><%=student.getMiddlename() %></td>
                  <td class="center"><%=student.getLastname() %></td>
                  <td class="center"><%=student.getGender() %></td>
                  <td class="center"> <a class="btn btn-info" href="profile.jsp?uuid=<%=student.getUuid() %>" > Profile <i class="fa fa-info"></i></a> </td>                 

                </tr>

                <%      
                    studentCount++;
                  }
                  
                  %>  --%>

                        </tbody>
                      </table>

            <div id="pagination">
              <form name="pageForm" method="post" action="#">
               
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="button" onclick="pagination(this.id)" name="page"
                  id="F" value="First" style="display:none" /> <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="button" onclick="pagination(this.id)"
                  name="page" id="P" value="Previous"  style="display:none"/>
               
                <span class="pageInfo">Page <span
                  class="pagePosition currentPage">##</span>
                  of <span class="pagePosition">##</span>
                </span>
                
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="button" name="page" onclick="pagination(this.id)" id="N" value="Next">
                <input class="toolbarBtn btn btn-default secondary-assent whiteme" type="button" name="page" onclick="pagination(this.id)" id="L" value="Last">
              
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


<script src="js/student.js"></script>





