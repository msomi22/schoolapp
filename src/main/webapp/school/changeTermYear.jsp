<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.GradingSystem"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.account.Miscellanous"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%
      String accountuuid = "";

     if (session == null) {
       response.sendRedirect("../index.jsp");
      
    }

    String username = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_KEY);
    if (StringUtils.isEmpty(username)) {
        response.sendRedirect("../index.jsp");
       
    }
    CacheManager mgr = CacheManager.getInstance();
    Cache accountsCache = mgr.getCache(CacheVariables.CACHE_SCHOOL_ACCOUNTS_BY_USERNAME);
    Cache statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_SCHOOL_ACCOUNT);
    SessionStatistics statistics = new SessionStatistics();
    

    SchoolAccount school = new SchoolAccount();
    Element element;
   

    int incount = 0;  // Generic counter

    if ((element = accountsCache.get(username)) != null) {
        school = (SchoolAccount) element.getObjectValue();
    }

    accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    TermFeeDAO termFeeDAO = TermFeeDAO.getInstance();
    MiscellanousDAO miscellanousDAO = MiscellanousDAO.getInstance();

    List<TermFee> termlist = new ArrayList<TermFee>();
    termlist = termFeeDAO.getTermFeeList(accountuuid);

     List<Miscellanous> misclist = new ArrayList<Miscellanous>();
     misclist = miscellanousDAO.getMiscellanousList(accountuuid);


    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    GradingSystemDAO gradingSystemDAO = GradingSystemDAO.getInstance();
    GradingSystem gradingSystem = gradingSystemDAO.getGradingSystem(accountuuid);

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
    
      
      int sessiontime = SessionConstants.SESSION_TIMEOUT;
      //out.println(sessiontime);

     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);

    Locale locale = new Locale("en","KE"); 
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    
 %>






<jsp:include page="header.jsp" />




<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="schoolIndex.jsp">Home</a></li>
        
      </ul><br>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search Anything...">
        <span class="input-group-btn">
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
                  data-slide="prev">&lsaquo;</a>
              <a class="carousel-control right" href="#myCarousel"
                  data-slide="next">&rsaquo;</a>
          </div>
          <hr>
    </div>

      <div class="col-sm-9">
        <div class="breadcrumb">
                 TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>  
       </div>

                         <%

                                String updateErrStr = "";
                                String updatesuccessStr = "";
                                session = request.getSession(false);
                                     updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                     updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS); 

                                if(session != null) {
                                    updateErrStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR);
                                    updatesuccessStr = (String) session.getAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(updateErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + updateErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + updatesuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS,null);
                                  } 

                                String addError = "";
                                String addsuccess = "";
                                session = request.getSession(false);
                                addError = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR);
                                addsuccess = (String) session.getAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS); 


                                 if (StringUtils.isNotEmpty(addError)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addError);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccess)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccess);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_FEE_ADD_SUCCESS, null);
                                  } 
                          


                        %>
  



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                 Welcome: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %> 
              </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">




                              <!-- start********start  -->                                     
                              <div class="panel panel-default panel-table">
                                      <div class="panel-heading">
                                        <div class="row">
                                          <div class="col col-xs-6">
                                            <h3 class="panel-title"> 
                                                Update Term/Year: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear() %>   
                                            </h3>
                                          </div>
                                        </div>
                                      </div>
                                      <div class="panel-body">
                                      <div class="table-responsive ">

                                <table class="table table-striped table-bordered bootstrap-datatable ">
                                  <thead>
                                      <tr>
                                         
                                          <th>Term</th>
                                          <th>Year</th> 
                                          <th>Action</th>
                                         
                                      </tr>
                                  </thead>   
                                  <tbody>
                                      
                                      <tr>
                                           <td class="center"><%=examConfig.getTerm() %></td>
                                           <td class="center"><%=examConfig.getYear() %></td>
                                           <td class="center">
                                                  <form name="edit" method="POST" action="updateTermYear.jsp" > 
                                                  <input type="hidden" name="schoolUuid" value="<%=examConfig.getSchoolAccountUuid()%>">
                                                  <input type="hidden" name="term" value="<%=examConfig.getTerm()%>">
                                                  <input type="hidden" name="year" value="<%=examConfig.getYear()%>">
                                                  <input type="hidden" name="exam" value="<%=examConfig.getExam()%>">
                                                  <input type="hidden" name="exammode" value="<%=examConfig.getExamMode()%>">
                                                  <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                                  </form>                          
                                           </td>  
                                          
                                      </tr>

                                  </tbody>
                              </table> 
                               </div>
                                    </div>
                                     <div class="panel-footer">
                                        <div class="row">
                                          <div class="col col-xs-4"> <small> <i> Hi, you can change term or year.</i> </small>
                                          </div>
                                        </div>
                                      </div>
                               </div>

                                  <!--end************end-->




                                  <!-- start********start  -->                                     
                                      <div class="panel panel-default panel-table">
                                      <div class="panel-heading">
                                        <div class="row">
                                          <div class="col col-xs-6">
                                            <h3 class="panel-title"> School Fee: for a "day school only", use boarding fee  </h3>
                                          </div>
                                        </div>
                                      </div>
                                      <div class="panel-body">
                                      <div class="table-responsive ">

                                      <table class="table table-striped table-bordered bootstrap-datatable datatable">
                                        <thead>
                                            <tr >
                                                <th>*</th>
                                                <th>Term</th>
                                                <th>Year</th>                
                                                <th>Boarding Fee </th>
                                                <th>Day Fee </th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>   
                                        <tbody>
                                            <%
                                             int feecount = 1;
                                            for(TermFee tfee : termlist){
                                             %>

                                            <tr>
                                                 <td width="3%"><%=feecount%></td>
                                                 <td class="center"><%=tfee.getTerm()%></td>   
                                                 <td class="center"><%=tfee.getYear()%></td>
                                                 <td class="center"><%=nf.format(tfee.getTermAmount())%></td>
                                                 <td class="center"><%=nf.format(tfee.getDayAmount())%></td>
                                                 <td class="center" width="5%">
                                                    <form name="update" method="POST" action="updatetermfee.jsp"> 
                                                      <input type="hidden" name="Term" value="<%=tfee.getTerm()%>">
                                                      <input type="hidden" name="Year" value="<%=tfee.getYear()%>">
                                                      <input type="hidden" name="BAmount" value="<%=tfee.getTermAmount()%>">
                                                      <input type="hidden" name="DAmount" value="<%=tfee.getDayAmount()%>">
                                                      <input type="hidden" name="uuid" value="<%=tfee.getUuid()%>"> 
                                                      <input class="btn btn-success" type="submit" name="update" id="submit" value="Update" /> 
                                                    </form>                          
                                                </td>  



                                            </tr>

                                            <%
                                                  feecount++;
                                               }
                                                    
                                            %>
                                        </tbody>
                                    </table>  


                                   </div>
                                    </div>
                                     <div class="panel-footer">
                                        <div class="row">
                                          <div class="col col-xs-4"> <small> <i>Fee must be paid.</i> </small>
                                          </div>
                                        </div>
                                      </div>
                                    </div>

                                  <!--end************end-->


                           </div>  
                     </div>   <!-- end panel body -->
                     <div class="panel-footer">
                      <div class="row">
                        <div class="col col-xs-4"> <small> <i> Sasa! Uko poa?.</i> </small> </div>
                      </div>
                     </div>
        </div>    <!-- end panel  -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />


