
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.RoomDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.ClassRoom"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.ClassesDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Classes"%>


<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>
<%@ page import="java.util.Calendar" %>


<%@page import="org.apache.commons.lang3.StringUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <%

   
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

    String accountuuid = school.getUuid();
    String schoolname = school.getSchoolName();

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
   

      //ClassesDAO
     ClassesDAO classesDAO = ClassesDAO.getInstance();
     RoomDAO roomDAO = RoomDAO.getInstance();

     List<ClassRoom> classroomList = new ArrayList<ClassRoom>(); 
     classroomList = roomDAO.getAllRooms(accountuuid); 


      List<Classes> classesList = new ArrayList<Classes>(); 
      classesList = classesDAO.getClassList(); 


      Calendar calendar = Calendar.getInstance();
      final int YEAR = calendar.get(Calendar.YEAR)-4;
      final int YEAR_COUNT = YEAR + 5;
   
   
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
        <li>  <a href="reports.jsp">Back</a>  </li>
       </div>

      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> 
                FORM 3 AND 4 REPORTS PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%>
              </h3>
            </div>
            <div class="panel-body">
              
              <div class="table-responsive ">

               <h3><i class="icon-edit"></i> Top ten plus most improved students:</h3>  
                    <form  class="form-horizontal"   action="topTenF34" method="POST" target="_blank">
               
                       <div class="form-group">
                        <label class="col-sm-3 control-label" for="class">Class</label>
                        <div class="col-sm-9">
                            <select name="classID" class="form-control">
                                 <option value="A4BFC2BD-262F-4207-99C8-057D6ADF80C7">FORM THREE</option>
                                 <option value="14E56350-08DA-45CC-97D9-C225AF74A7AD">FORM FOUR</option>
                            </select>                           
                        </div>
                        </div> 


                            <div class="form-group">
                             <div class="col-sm-9 col-sm-offset-3">
                                  <input type="hidden" name="schooluuid" value="<%=accountuuid%>"> 
                                  <button type="submit" name="Find" value="promote"   class="btn btn-primary btn-block">Generate</button> 
                              </div>
                            </div>
                        </form>




                  <h3><i class="icon-edit"></i> Cats performance:</h3>  
                  <form  class="form-horizontal"   action="catResultF34" method="POST" target="_blank">
              
                             <div class="form-group" >
                                <label class="col-sm-3 control-label" for="class">Exam Type</label>
                                <div class="col-sm-9">
                                    <select name="examType" class="form-control">
                                    <option value="C1">(C1-Opener)</option>   
                                    <option value="C2">(C2-Midterm)</option>      
                                    </select>                           
                                  
                                </div>
                               </div> 




                               <div class="form-group">
                                    <label class="col-sm-3 control-label" for="Classroom">Classroom*:</label>
                                    <div class="col-sm-9">
                                    <select name="classroomuuid" class="form-control">

                                                 <%
                                                    int count = 1;
                                                    if (classroomList != null) {
                                                        for (ClassRoom cl : classroomList) {
                                                         if(StringUtils.contains(cl.getRoomName(), "FORM 3") || StringUtils.contains(cl.getRoomName(), "FORM 4")){
                                                            %>
                                                            <option value="<%=cl.getUuid()%>"><%=cl.getRoomName()%></option>
                                                            <%
                                                            count++;
                                                            }
                                                            
                                                        }
                                                    }
                                                %>
                                                
                                      </select>                           
                                          
                                  </div>
                                </div> 

                      <div class="form-group">
                       <div class="col-sm-9 col-sm-offset-3">
                       <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                       <button type="submit" name="Find" value="promote"   class="btn btn-primary btn-block">Generate</button> 
                       </div>
                      </div>

                 </form>






                  <h3><i class="icon-edit"></i> Classes performance:</h3>  
                  <form  class="form-horizontal"   action="performanceListF34" method="POST" target="_blank">
             
                         <div class="form-group" id="divid">
                            <label class="col-sm-3 control-label" for="class">Exam Type</label>
                            <div class="col-sm-9">
                                <select name="examID" class="form-control">
                                 <% if(StringUtils.equals(examConfig.getExamMode(), "ON")) {%>
                                <option value="4BE8AD46-EAE8-4151-BD18-CB23CF904DDB">(P1,P2,P3) Performance List</option>   

                                   <%} else {%>
                                <option value="1678664C-D955-4FA7-88C2-9461D3F1E782">(C1,C2,ET) Performance List</option> 

                                 <%}%>    
                                </select>                           
                              
                            </div>
                           </div> 




                            <div class="form-group">
                                <label class="col-sm-3 control-label" for="Classroom">Class*:</label>
                                   <div class="col-sm-9">
                                    <select name="classID" class="form-control">

                                                     <%
                                                        int count2 = 1;
                                                        if (classesList != null) {
                                                            for (Classes cl : classesList) {
                                                             if(StringUtils.contains(cl.getClassName(), "FORM 3") || StringUtils.contains(cl.getClassName(), "FORM 4")){
                                                                %>
                                                                <option value="<%=cl.getUuid()%>"><%=cl.getClassName()%></option>
                                                                <%
                                                                count2++;
                                                                }
                                                                
                                                            }
                                                        }
                                                    %>
                                                    
                                                </select>                           
                                              
                                </div>
                            </div> 





                        <div class="form-group">
                         <div class="col-sm-9 col-sm-offset-3">
                         <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                         <button type="submit" name="Find" value="promote"   class="btn btn-primary btn-block">Generate</button> 
                         </div>
                        </div>

                 </form>
        
    </div>  
    </div>  

<div class="panel-footer">
        <div class="row">
          <div class="col col-xs-4"> <small> <i>Live like there is no tomorrow.</i> </small>
        </div>
    </div>
</div>

    
    </div>  
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />


