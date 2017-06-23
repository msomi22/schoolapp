<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.GradingSystemDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.GradingSystem"%>

<%@page import="com.yahoo.petermwenda83.persistence.money.TermFeeDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.money.TermFee"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

<%@page import="com.yahoo.petermwenda83.persistence.schoolaccount.MiscellanousDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.account.Miscellanous"%>

<%@page import="com.yahoo.petermwenda83.bean.account.Account"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

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
    ExamDAO examDAO = ExamDAO.getInstance();

    List<TermFee> termlist = new ArrayList<TermFee>();
    termlist = termFeeDAO.getTermFeeList(accountuuid);

     List<Miscellanous> misclist = new ArrayList<Miscellanous>();
     misclist = miscellanousDAO.getMiscellanousList(accountuuid);

    List<Exam> examlist = new ArrayList<Exam>();
    examlist = examDAO.getExamList(accountuuid);
     

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
            <li> <a href="changeTermYear.jsp">Update Term/Year</a> </li>
            <li>  <a href="smsApi.jsp">SMS API</a> </li>
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
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(updateErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(updatesuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(updatesuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.EXAM_CONFIG_UPDATE_SUCCESS, null);
                                  }                        

                               

                                String gSyupdateErrStr = "";
                                String gSyupdatesuccessStr = "";
                                session = request.getSession(false);
                                     gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                     gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS); 

                                if(session != null) {
                                    gSyupdateErrStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_ERROR);
                                    gSyupdatesuccessStr = (String) session.getAttribute(SessionConstants.GRADE_ADD_SUCCESS);
                                }  

                                if (StringUtils.isNotEmpty(gSyupdateErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(gSyupdateErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.GRADE_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(gSyupdatesuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(gSyupdatesuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.GRADE_ADD_SUCCESS, null);
                                  }                          

                               


                                 String addErrStr = "";
                                 String addsuccessStr = "";
                             
                                 session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_FIND_SUCCESS); 

                                     if (StringUtils.isNotEmpty(addErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(addErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUDENT_FIND_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(addsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUDENT_FIND_SUCCESS, null);
                                  }      
                                    

                                


                                   String delErrStr = "";
                                    String delsuccessStr = "";
                             
                                 session = request.getSession(false);
                                     delErrStr = (String) session.getAttribute(SessionConstants.STUENT_DELETE_ERROR);
                                     delsuccessStr = (String) session.getAttribute(SessionConstants.STUENT_DELETE_SUCCESS); 

                                     if (StringUtils.isNotEmpty(delErrStr)) {
                                      %>
                                     <div class="alert alert-danger">
                                     <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Warning!</strong> <%out.println(delErrStr);%> 
                                     </div>         
                                      <%                              
                                    session.setAttribute(SessionConstants.STUENT_DELETE_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(delsuccessStr)) {
                                      %>
                                     <div class="alert alert-success">
                                      <a href="#" class="close" data-dismiss="alert">&times;</a>
                                       <strong>Success!</strong> <%out.println(delsuccessStr);%> 
                                     </div>         
                                      <%                      
                                    session.setAttribute(SessionConstants.STUENT_DELETE_SUCCESS, null);
                                  }      
                                    
                                 




        %>



      
      <!-- panel start -->
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title"> CONFIGURATION PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%></h3>
            </div>
            <div class="panel-body">

   
            <div class="panel panel-default panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">  Important Notes!  </h3>
                  </div>
                </div>
              </div>
              <div class="panel-body">
               <strong>ET F1:</strong>  Stands for, end of term form one, when ON, <u>only</u> end term exam will be counted, affects FORM 1 only.<br>
               <strong>ET:</strong> Means end term, when ON it means  <u>only</u> end term exam is considered, all cats will be excluded, affects F1-F4. <br>
               <strong>ET C2:</strong> When ON  means  <u>only</u> end term and cat 2 exams are included, cat 1 will be discarded, affects F1-F4. <br>
               <strong>ET C1 C2:</strong> When ON means cat 1 , cat 2 and end term exams will be counted, affects F1-F4. <br>
               <strong>Mode:</strong> When ON the system will compute   P1,P2 and P3 , otherwise C1,C2 and ET (form 3 & 4 only). <br>
               <strong>SMS send:</strong> Put this OFF to disable SMS sending. <br>
               <small style="color:red;">If SMS send is ON, SMSes will be sent when fee is paid and when report cards are generated.</small>
               <p>C1 stands for cat1 , C2 - cat2, ET - end term , P1 - paper1, P2 - paper2 and P3 paper3. </p>

              </div>
              <div class="panel-footer">
                <div class="row">
                  <div class="col col-xs-4"> <small> <i>Read carefully.</i> </small>
                  </div>
                </div>
              </div>
            </div>


            <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  -->


                          <ul id="myTab" class="nav nav-tabs">
                              <li class="active">
                                  <a href="#examConfig" data-toggle="tab">Exam Configurations </a>
                              </li>

                              <li>
                                  <a href="#gradeScale" data-toggle="tab">Grading Scale</a>
                              </li>

                              <li>
                                  <a href="#basicInfo" data-toggle="tab">Basic Information</a>
                              </li>

                              <li>
                                  <a href="#examOutof" data-toggle="tab">Exam Outof</a>
                              </li>

                              <li>
                                  <a href="#delteStude" data-toggle="tab">Delete Student</a>
                              </li>
                            </ul>


                            <div id="myTabContent" class="tab-content">

                                <div class="tab-pane fade in active" id="examConfig">
                                     
                                  <!-- start********start  -->
                                     
                                     <div class="panel panel-default panel-table">
                                      <div class="panel-heading">
                                        <div class="row">
                                          <div class="col col-xs-6">
                                            <h3 class="panel-title"> Configurations  </h3>
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
                                                <th>Exam</th>                
                                                <th>Mode</th>
                                                <th>SMS send</th>
                                                <th>ET F1</th>

                                                <th>ET</th>
                                                <th>ET C2</th>
                                                <th>ET C1 C2</th>
                                                
                                                <th>Action</th>
                                               
                                            </tr>
                                        </thead>   
                                        <tbody>
                                            
                                            <tr>
                                                 <td class="center"><%=examConfig.getTerm() %></td>
                                                 <td class="center"><%=examConfig.getYear() %></td>
                                                 <td class="center"><%=examConfig.getExam() %></td>
                                                 <td class="center"><%=examConfig.getExamMode() %></td> 
                                                 <td class="center"><%=examConfig.getSendSMS() %></td>   
                                                 <td class="center"><%=examConfig.geteTFone()%></td>  
                                                 <td class="center"><%=examConfig.geteT() %></td>  
                                                 <td class="center"><%=examConfig.geteTCtwo() %></td>  
                                                 <td class="center"><%=examConfig.geteTConetwo() %></td>  
                                                
                                                 <td class="center">
                                                        <form name="edit" method="POST" action="updateExamConfig.jsp" > 
                                                        <input type="hidden" name="schoolUuid" value="<%=examConfig.getSchoolAccountUuid()%>">
                                                        <input type="hidden" name="term" value="<%=examConfig.getTerm()%>">
                                                        <input type="hidden" name="year" value="<%=examConfig.getYear()%>">
                                                        <input type="hidden" name="exam" value="<%=examConfig.getExam()%>">
                                                        <input type="hidden" name="exammode" value="<%=examConfig.getExamMode()%>">
                                                        <input type="hidden" name="eTFone" value="<%=examConfig.geteTFone()%>">
                                                        <input type="hidden" name="eT" value="<%=examConfig.geteT()%>">
                                                        <input type="hidden" name="eTCtwo" value="<%=examConfig.geteTCtwo()%>">
                                                        <input type="hidden" name="eTConetwo" value="<%=examConfig.geteTConetwo()%>">
                                                        <input type="hidden" name="sendSmsEnable" value="<%=examConfig.getSendSMS()%>">
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
                                          <div class="col col-xs-4"> <small> <i>Change only if you understand the effect.</i> </small>
                                          </div>
                                        </div>
                                      </div>
                                    </div>




                                   <!--end************end-->

                                </div>

                                <div class="tab-pane fade" id="gradeScale">
                                   
                                   <!-- start********start  -->
                                     
                                      <div class="panel panel-default panel-table">
                                      <div class="panel-heading">
                                        <div class="row">
                                          <div class="col col-xs-6">
                                            <h3 class="panel-title"> Grading Scale  </h3>
                                          </div>
                                        </div>
                                      </div>
                                      <div class="panel-body">
                                      <div class="table-responsive ">
                                      <table class="table table-striped table-bordered bootstrap-datatable ">
                                        <thead>
                                            <tr>
                                               
                                                <th>A</th>
                                                <th>A-</th> 
                                                <th>B+</th>                
                                                <th>B</th>
                                                <th>B-</th>
                                                <th>C+</th>
                                                <th>C</th>
                                                <th>C-</th>
                                                <th>D+</th>
                                                <th>D</th>
                                                <th>D-</th>
                                                <th>E</th>
                                                <th>Action</th>
                                            
                                            </tr>
                                        </thead>   
                                        <tbody>
                                            
                                            <tr>
                                                 <td class="center"> 100 - <%=gradingSystem.getGradeAplain()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeAplain()-1%> - <%=gradingSystem.getGradeAminus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeAminus()-1%> - <%=gradingSystem.getGradeBplus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeBplus()-1%> - <%=gradingSystem.getGradeBplain()%> </td>  
                                                 <td class="center"> <%=gradingSystem.getGradeBplain()-1%> - <%=gradingSystem.getGradeBminus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeBminus()-1%> - <%=gradingSystem.getGradeCplus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeCplus()-1%> - <%=gradingSystem.getGradeCplain()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeCplain()-1%> - <%=gradingSystem.getGradeCminus()%> </td>  
                                                 <td class="center"> <%=gradingSystem.getGradeCminus()-1%> - <%=gradingSystem.getGradeDplus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeDplus()-1%> - <%=gradingSystem.getGradeDplain()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeDplain()-1%> - <%=gradingSystem.getGradeDminus()%> </td>
                                                 <td class="center"> <%=gradingSystem.getGradeDminus()-1%> - <%=gradingSystem.getGradeE()%> </td>  
                                                 <td class="center">
                                                        <form name="edit" method="POST" action="updateGradingScale.jsp" > 
                                                        <input type="hidden" name="schoolUuid" value="<%=gradingSystem.getSchoolAccountUuid()%>">
                                                        <input type="hidden" name="A" value="<%=gradingSystem.getGradeAplain()%>">
                                                        <input type="hidden" name="Am" value="<%=gradingSystem.getGradeAminus()%>">
                                                        <input type="hidden" name="Bp" value="<%=gradingSystem.getGradeBplus()%>">
                                                        <input type="hidden" name="B" value="<%=gradingSystem.getGradeBplain()%>">
                                                        <input type="hidden" name="Bm" value="<%=gradingSystem.getGradeBminus()%>">
                                                        <input type="hidden" name="Cp" value="<%=gradingSystem.getGradeCplus()%>">
                                                        <input type="hidden" name="C" value="<%=gradingSystem.getGradeCplain()%>">
                                                        <input type="hidden" name="Cm" value="<%=gradingSystem.getGradeCminus()%>">
                                                        <input type="hidden" name="Dp" value="<%=gradingSystem.getGradeDplus()%>">
                                                        <input type="hidden" name="D" value="<%=gradingSystem.getGradeDplain()%>">
                                                        <input type="hidden" name="Dm" value="<%=gradingSystem.getGradeDminus()%>">
                                                        <input type="hidden" name="E" value="<%=gradingSystem.getGradeE()%>">
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
                                          <div class="col col-xs-4"> <small> <i>This can be treaky to modify.</i> </small>
                                          </div>
                                        </div>
                                      </div>
                                    </div>

                                  <!--end************end-->
                                </div>

                                <div class="tab-pane fade" id="basicInfo">
                                   
                                   <!-- start********start  -->
                                    
                                     <div class="panel panel-default panel-table">
                                        <div class="panel-heading">
                                          <div class="row">
                                            <div class="col col-xs-6">
                                              <h3 class="panel-title">  Basic Infomation to be embedded in report card  </h3>
                                            </div>
                                          </div>
                                        </div>
                                        <div class="panel-body">
                                         <div class="table-responsive ">
                                        <table class="table table-striped table-bordered bootstrap-datatable ">
                                          <thead>
                                              <tr >
                                                  <th>*</th>
                                                  <th>Key</th>
                                                  <th>Value</th> 
                                                  <th></th>              
                                              </tr>
                                          </thead>   
                                          <tbody>
                                              <%  //misclist   Miscellanous
                                               int misccount = 1;
                                              for(Miscellanous misc : misclist){

                                               %>

                                              <tr>
                                                   <td width="3%"><%=misccount%></td>
                                                   <td class="center"><%=misc.getKey()%> </td>   
                                                   <td class="center"> <%= misc.getValue()%> </td>
                                                    <td class="center">
                                                          <form name="edit" method="POST" action="updateMisc.jsp" > 
                                                          <input type="hidden" name="schoolUuid" value="<%=misc.getSchoolAccountUuid()%>">
                                                          <input type="hidden" name="miscUuid" value="<%=misc.getUuid()%>">
                                                          <input type="hidden" name="key" value="<%=misc.getKey()%>">
                                                          <input type="hidden" name="value" value="<%=misc.getValue()%>">
                                                          <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                                          </form>                          
                                                   </td>  
                                              </tr>

                                              <%
                                                    misccount++;
                                                 }
                                                      
                                              %>
                                          </tbody>
                                      </table>  
                                         
                                        </div>                                        
                                      </div>
                                      <div class="panel-footer">
                                          <div class="row">
                                            <div class="col col-xs-4"> <small> <i>Update b4 generating report cards.</i> </small>
                                            </div>
                                          </div>
                                        </div>
                                      </div>


                                  <!--end************end-->
                                </div>

                                <div class="tab-pane fade" id="examOutof">
                                    

                                    <!-- start********start  -->
                                      
                                      <div class="panel panel-default panel-table">
                                        <div class="panel-heading">
                                          <div class="row">
                                            <div class="col col-xs-6">
                                              <h3 class="panel-title">  Exam should be out-of what? set it here  </h3>
                                            </div>
                                          </div>
                                        </div>
                                        <div class="panel-body">
                                        <div class="table-responsive ">
                                        <table class="table table-striped table-bordered bootstrap-datatable ">
                                          <thead>
                                              <tr >
                                                  <th>*</th>
                                                  <th>Exam type</th>
                                                  <th>Out-of</th>  
                                                  <th>Action</th>            
                                                  
                                               
                                              </tr>
                                          </thead>   
                                          <tbody>
                                              <%  //examlist Exam
                                               int examcount = 1;
                                              for(Exam exm : examlist){

                                               %>

                                              <tr>
                                                   <td width="3%"><%=examcount%></td>
                                                   <td class="center"><%=exm.getExamName()%> </td>   
                                                   <td class="center"> <%= exm.getOutOf()%> </td>
                                                    <td class="center">
                                                          <form name="edit" method="POST" action="updateExam.jsp" >  
                                                          <input type="hidden" name="schoolUuid" value="<%=exm.getSchoolAccountUuid()%>">
                                                          <input type="hidden" name="examUuid" value="<%=exm.getUuid()%>">
                                                          <input type="hidden" name="examName" value="<%=exm.getExamName()%>">
                                                          <input type="hidden" name="examOutOf" value="<%=exm.getOutOf()%>">
                                                          <input class="btn btn-success" type="submit" name="edit" id="submit" value="Update" /> 
                                                          </form>                          
                                                   </td>  
                                              </tr>

                                              <%
                                                    examcount++;
                                                 }
                                                      
                                              %>
                                          </tbody>
                                      </table>  
                                         
                                        </div>
                                      </div>
                                         <div class="panel-footer">
                                          <div class="row">
                                            <div class="col col-xs-4"> <small> <i>Update b4 submitting exam.</i> </small>
                                            </div>
                                          </div>
                                        </div>
                                      </div>

                                    <!--end************end-->
                                   
                                </div>

                                <div class="tab-pane fade" id="delteStude">
                                    

                                    <!-- start********start  -->
                                    
                                    <div class="panel panel-default panel-table">
                                                  <div class="panel-heading">
                                                    <div class="row">
                                                      <div class="col col-xs-6">
                                                        <h3 class="panel-title">  Delete student's exam data (for this term  only )  </h3>
                                                      </div>
                                                    </div>
                                                  </div>
                                                  <div class="panel-body">
                                                   <div class="table-responsive ">
                                                  <table class="table table-striped  ">

                                                    <thead>
                                                        <tr >             
                                                            <th></th>
                                                            <th></th>
                                                            <th>Search</th>
                                                        </tr>
                                                    </thead>   

                                                    <tbody >

                                                                  <form name="view" method="POST" action="findStudentToDelete"> 

                                                                   <td width="8%" class="center">                              
                                                                  <p><b>Student Admission Number:</b><p>                                                    
                                                                   </td> 

                                                                    <td width="10%" class="center">                              
                                                                       <input class="input-xlarge focused" id="receiver" type="text" name="AdmNo" 
                                                                        value=""  >                                                    
                                                                   </td> 

                                                                   <td width="10%" class="center">                                
                                                                <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                                                    <input class="btn btn-success" type="submit" name="view" id="submit" value="Find" />                                                         
                                                                   </td> 
                                                                   </form> 


                                                    </tbody>                  
                                                </table>  

                                                <%

                                                          HashMap<String, Student> paramHash = (HashMap<String, Student>) session.getAttribute(SessionConstants.STUENT_DELETE_PARAM);

                                                                       if (paramHash == null) {
                                                                         paramHash = new HashMap<String, Student>();
                                                                        }

                                                                        Student student = new Student();
                                                                        student = paramHash.get("studentTOdelete");


                                                                         String fullname = "";

                                                                         String formatedFirstname = "";
                                                                         String formatedLastname = "";
                                                                         String formatedSurname = "";

                                                                         String firstNameLowecase  = "";
                                                                         String lastNameLowecase  = "";
                                                                         String surNameLowecase  = "";

                                                                         String admNumber ="";    
                                                                         String studentUuid ="";
                                                                         String studentType = "";                            

                                                                         if(student !=null){

                                                                             admNumber = student.getAdmno();
                                                                             studentUuid = student.getUuid();
                                                                             studentType = student.getStudentType();

                                                                             firstNameLowecase = student.getFirstname().toLowerCase();
                                                                             lastNameLowecase =student.getLastname().toLowerCase();
                                                                             surNameLowecase = student.getSurname().toLowerCase();

                                                                             formatedFirstname = firstNameLowecase;
                                                                             formatedLastname = lastNameLowecase;
                                                                             formatedSurname = surNameLowecase; 

                                                                             fullname = formatedFirstname +" "+formatedLastname+" "+formatedSurname ;
                                                     
                                                                           }




                                                        %>

                                                



                                                 <table class="table ">
                                                    <thead>
                                                        <tr >             
                                                            <th>Student AdmNo</th>
                                                            <th>Student name</th>
                                                        </tr>
                                                    </thead>   
                                                    <tbody >
                                                        <%  
                                                                   out.println("<tr>"); 
                                                                   out.println("<td width=\"10%\" class=\"center\">" + admNumber + "</td>");  
                                                                   out.println("<td width=\"10%\" class=\"center\">" + fullname + "</td>");    
                                                                 
                                                        %> 

                                                    </tbody>                  
                                                </table>  


                                                <form  class="form-horizontal"   action="deleteStudent" method="POST" autocomplete="off">
                                                      <fieldset>
                                                                  
                                                                  <div class="form-actions">
                                                                       <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                                                                       <input type="hidden" name="studentuuid" value="<%=studentUuid%>">
                                                                      <button type="submit" class="btn btn-primary">Delete</button>
                                                                  </div> 

                                                  </fieldset>
                                                  </form>


                                                   
                                                  </div>
                                                 </div>
                                                  <div class="panel-footer">
                                                    <div class="row">
                                                      <div class="col col-xs-4"> <small> <i>This feature can be useful.</i> </small>
                                                      </div>
                                                    </div>
                                                  </div>
                                                </div>




                                    <!--end************end-->
                                   
                                </div>

                            </div>
                      




            <!-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ -->




       </div> <!-- end panel body -->
                     <div class="panel-footer">
                      <div class="row">
                        <div class="col col-xs-4"> <small> <i> Sasa! Uko poa?.</i> </small> </div>
                      </div>
                     </div>
      </div>  <!-- end panel -->
    </div> 
  </div>
</div>

<jsp:include page="footer.jsp" />




