<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.OtherstypeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.othermoney.Otherstype"%>

<%@page import="com.yahoo.petermwenda83.persistence.othermoney.TermOtherMoniesDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.othermoney.TermOtherMonies"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>


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
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


     OtherstypeDAO otherstypeDAO = OtherstypeDAO.getInstance();
     List<Otherstype> othertypeList = new ArrayList<Otherstype>(); 
     othertypeList = otherstypeDAO.getOtherstypeList(accountuuid,examConfig.getTerm(),examConfig.getYear());  

     TermOtherMoniesDAO termOtherMoniesDAO = TermOtherMoniesDAO.getInstance();
     List<TermOtherMonies> termothermoneyList = new ArrayList<TermOtherMonies>(); 
     termothermoneyList = termOtherMoniesDAO.getTermOtherMoniesList(accountuuid); 


     TermOtherMonies termOtherMonies = new TermOtherMonies();
     HashMap<String, TermOtherMonies> termOtherMoniesHash = new HashMap<String, TermOtherMonies>(); 
     if(termothermoneyList !=null){
     for(TermOtherMonies tom : termothermoneyList){
         termOtherMoniesHash.put(tom.getOtherstypeUuid(),tom);
         }
       }
    

     RoomDAO roomDAO = RoomDAO.getInstance();
     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 
     
    

    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     
      
                             

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
         <li>  <a href="studentAddOM.jsp">Back</a> </li>
       </div>


                    <%  
     
                                String addErrStr = "";
                                String addsuccessStr = "";
                             
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_SUCCESS); 

                                     if (StringUtils.isNotEmpty(addErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_SUCCESS, null);
                                  }       
                                    


            %>
      



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                 Assign Payment : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">
              <div class="table-responsive ">

              <form  class="form-horizontal"   action="addPaymentTOStudentperClasss" method="POST" >
                 <fieldset>


                 <div class="form-group" id="divid">
                        <label class="col-sm-3 control-label" for="class">Select class</label>
                        <div class="col-sm-9">
                            <select name="classUuid" class="form-control" required>
                                <option value="">select one</option>
                                 <%
                                    int class_count = 1;
                                    if (classroomList != null) {
                                        for (ClassRoom cc : classroomList) {
                                %>
                                <option value="<%=cc.getUuid()%>"><%=cc.getRoomName()%></option>
                                <%
                                            class_count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                    
                           

                  <div class="form-group">
                      <label class="col-sm-3 control-label" for="name">Type of Money*:</label>
                         <div class="col-sm-9">
                             <select name="moneyTypeUuid" class="form-control" required>
                                  <option value="">Please select one</option> 
                                      <%               
                             
                                int count = 1;
                                   double amount = 0;
                                    if(othertypeList !=null){
                                       for(Otherstype ot : othertypeList) {
                                        amount = 0;
                                      if(termOtherMoniesHash.get(ot.getUuid()) !=null){
                                       termOtherMonies = termOtherMoniesHash.get(ot.getUuid());
                                       amount = termOtherMonies.getAmount();
                                     }
                                 %>
                              <option value="<%=ot.getUuid()%>"> <%= ot.getType()+" "+amount%> </option>
                            <%
                          
                          count++;

                          } 
                     }
                    %>
                  </select>   
                </div>
              </div> 

                                    
              <div class="form-group">
                <div class="col-sm-9 col-sm-offset-3">
                  <button type="submit" class="btn btn-primary btn-block">Assign</button>
                </div> 
              </div>

              </fieldset>
              </form>

   
  </div>
</div> <!-- end panel body -->
        <div class="panel-footer">
                <div class="row">
                  <div class="col col-xs-4"> <small> <i>Live is good with money.</i> </small>
                </div>
              </div>
        </div>
</div> <!-- end panel  -->
</div>
</div>
</div>

<jsp:include page="footer.jsp" />


