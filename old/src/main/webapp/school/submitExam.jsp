
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Stream"%>

<%@page import="com.yahoo.petermwenda83.persistence.subject.SubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.subject.Subject"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.PerfomanceDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Perfomance"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.SysConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.SysConfig"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
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
    
    String accountId = (String) session.getAttribute(SessionConstants.SCHOOL_ACCOUNT_SIGN_IN_ACCOUNTUUID); 
    
    
    StreamDAO streamDAO = StreamDAO.getInstance();
    SubjectDAO subjectDAO = SubjectDAO.getInstance();
    ExamDAO examDAO = ExamDAO.getInstance();
    StudentDAO studentDAO = StudentDAO.getInstance();
    PerfomanceDAO perfomanceDAO = PerfomanceDAO.getInstance();
    SysConfigDAO sysConfigDAO = SysConfigDAO.getInstance();
    
    SysConfig sysConfig = sysConfigDAO.getSysConfig(accountId);
    
    List<Stream> streamList = new ArrayList<>();
    List<Subject> subjectList = new ArrayList<>();
    List<Exam> examList = new ArrayList<>();
    
    if(streamDAO.getStreamList(accountId) != null){
    	streamList = streamDAO.getStreamList(accountId);
    }
    
    if(subjectDAO.getSubjects(accountId) != null){
    	subjectList = subjectDAO.getSubjects(accountId);
    }
    
    if(examDAO.getExamList(accountId) != null){
    	examList = examDAO.getExamList(accountId);
    }
    
    
   
%>
<jsp:include page="header.jsp" />


        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Exam Submission Panel</h3> 
              </div>
            </div>
            
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_content">
                  
                  
                  
                  
                  
                  <%
                  List<Student> studentsList = new ArrayList<>(); 
                  
                  if((List<Student>) session.getAttribute(SessionConstants.EXAM_GET_STUDENTS) != null){
                	  
                	  studentsList = (List<Student>) session.getAttribute(SessionConstants.EXAM_GET_STUDENTS); 
                	  
                  }
                  
                  HashMap<String, String> idsHash = new HashMap<>();
                  if((HashMap<String, String>) session.getAttribute(SessionConstants.EXAM_GET_STUDENTS_IDS) != null){
                	  
                	 idsHash = (HashMap<String, String>) session.getAttribute(SessionConstants.EXAM_GET_STUDENTS_IDS);
                	  
                  }
                  
                  String examId = "";
                  String streamId = "";
                  String subjectId = "";
                  
                  if(!idsHash.isEmpty()){
                	  examId = (String)idsHash.get("examId"); 
                	  streamId = (String)idsHash.get("streamId"); 
                	  subjectId = (String)idsHash.get("subjectId"); 
                  }
                  
                  String currentExam = "";                 
                  String currentStream = "";
                  
                  
                  if(examDAO.getExam(accountId, examId) != null){
                	  Exam exam = examDAO.getExam(accountId, examId);
                	  currentExam = exam.getDescription() + " , OutOf: " + exam.getOutOf();
                  }
                  
                  
                  if(streamDAO.getStream(accountId, streamId) != null){
                	  Stream stream = streamDAO.getStream(accountId, streamId);
                	  currentStream = stream.getDescription();
                  }
                  
                  
                  String respo = "";
                  if (session != null) {
                    respo = (String) session.getAttribute(SessionConstants.GENERIC_ERROR);
                  }
                  
                  if (StringUtils.isNotEmpty(respo)) {
                      %>
                      <div class="alert alert-warning">
                        <a href="#" class="close" data-dismiss="alert"> &times; </a> <strong>Warning!</strong>
                        <%
                          out.println("Login error: " + respo);
                        %>
                      </div>

                      <%
                        session.setAttribute(SessionConstants.GENERIC_ERROR, null);
                        }
                   
                  %>
                  




                  <div class="row">

                  
                  <form class="form-horizontal" method="POST" action="getStudents" id="submitExam"> 
                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">

                   <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Stream</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control formelement" name="streamId" id="streamId">
                          <%
                             int studentsCount = 0;
                             if(!StringUtils.isBlank(streamId)){
                            	 studentsCount = studentDAO.classStudentCount(accountId, streamId, "1"); 
                            	   Stream stream1 = streamDAO.getStream(accountId, streamId);
                            	   %>
                            	  <option value="<%=streamId %>"> <%=stream1.getDescription() + " (" + studentsCount + ")"%> </option>
                            	  <%
                            	  for(Stream stream : streamList){    
                                 	if(!StringUtils.equals(stream.getUuid(), streamId)){
                                 	studentsCount = studentDAO.classStudentCount(accountId, stream.getUuid(), "1"); 
                             	   %>
                             	  <option value="<%=stream.getUuid() %>"> <%=stream.getDescription() + " (" + studentsCount + ")"%> </option>
                             	   <%
                                     } 
                            	  }
                            	  
                      	       }else{
                      	    	
                      	    	 for(Stream stream : streamList){    
                                 	
                                 	studentsCount = studentDAO.classStudentCount(accountId, stream.getUuid(), "1"); 
                             	   %>
                             	  <option value="<%=stream.getUuid() %>"> <%=stream.getDescription() + " (" + studentsCount + ")"%> </option>
                             	   <%
                                     } 
                      	       }
                               %>
                            
                          
                          </select>
                        </div>
                      </div>

                  </div>

                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">
                    
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Subject</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control formelement" name="subjectId" id="subjectId">
                            <%
                            if(!StringUtils.isBlank(subjectId)){
                            	
                            	Subject subject1 = subjectDAO.getSubjectById(accountId, subjectId);
                            	  %>
                            	  <option value="<%=subjectId %>"> <%=subject1.getDescription() %> </option>
                            	   <%
                            	
                            	for(Subject subject : subjectList){ 
                            		
                            		if(!StringUtils.equals(subject.getUuid(), subjectId)){
                                	%>
                              	  <option value="<%=subject.getUuid() %>"> <%=subject.getDescription() %> </option>
                              	   <%
    			                  }
                            	}
                            	
                            }else{
                            	
                            	for(Subject subject : subjectList){                            	
                                	%>
                              	  <option value="<%=subject.getUuid() %>"> <%=subject.getDescription() %> </option>
                              	   <%
    			                  }
                            }
                            
                            
                            %>                           
                          </select>
                        </div>
                      </div>

                  </div>

                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">
                     
                     <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Exam</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control formelement" name="examId" id="examId">
                            <%
                            
                            if(!StringUtils.isBlank(examId)){
                            	
                            	Exam exam1 = examDAO.getExam(accountId, examId); 
                            	

					             %>
              	                <option value="<%=exam1.getUuid() %>"> <%=exam1.getDescription() + " , OutOf: " + exam1.getOutOf() %></option> 
              	             
              	              <%
                            	
                            	for(Exam exam : examList){	
                            		
                            		if(!StringUtils.equals(exam.getUuid(), examId)){
                            		
						             %>
                   	            <option value="<%=exam.getUuid() %>"> <%=exam.getDescription() + " , OutOf: " + exam.getOutOf() %></option> 
                   	             
                   	              <%
					              }
                            	}
                            	
                            }else{
                            	
                            	for(Exam exam : examList){							  
						             %>
                  	            <option value="<%=exam.getUuid() %>"> <%=exam.getDescription() + " , OutOf: " + exam.getOutOf() %></option> 
                  	             
                  	              <%
                            	
                               }
                            }

						     
                          %>                   
                          </select>
                        </div>
                      </div>

                  </div>
                  
                  <input type="hidden" name="studentId" id="studentId" value="">
                  <input type="hidden" name="score" id ="score" value="">
                  <input type="hidden" name="decision" id ="decision" value="submitExam">

                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">
                    <button type="submit" class="btn btn-primary">
                      Submit
                    </button>
                  </div>
                  </form>



                  <div class="col-md-12 col-sm-12 col-xs-12 form-group">

                  <div class="x_panel">
                  <div class="x_title">
                  
                  
                  
                     
                    <h2>Students <small>List for stream : <%=currentStream %> </small></h2>
                    <ul class="nav navbar-right panel_toolbox">
                      <li><a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
                      </li>                      
                    </ul>
                    <div class="clearfix"></div>
                  </div>

                <div class="x_content">
                     
                <div class="table-responsive">
                    <table class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings secondary-assent">

                            <th class="column-title"># </th>
                            <th class="column-title">RegNo </th>
                            <th class="column-title">First name </th>
                            <th class="column-title">Middle name </th>
                            <th class="column-title">Last name </th>
                            <th class="column-title">Score </th>
                            <th class="column-title">Score ( <%=currentExam %> ) </th>   
                            
                          </tr>
                        </thead>

              <tbody class='tablebody'>
              
                 <%


                 String p1 = "AE24F15B-5038-4A15-8607-1DB2A7A0B7DE";
                 String p2 = "4531A31D-1F8A-40D7-BFE6-D3CB3D91951A";
                 String p3 = "69A569CA-1D4F-458E-99DD-FB2BE705BF5C";

                 String p123 = "C3915245-00EE-4EF4-9898-ACE59683DD60";

                 if(StringUtils.equals(examId, p1)) {
                     examId = p123;

                  }else if(StringUtils.equals(examId, p2)) {
                      examId = p123;

                  }else if(StringUtils.equals(examId, p3)) {
                      examId = p123;

                  }

                 // out.println("examId: " + examId);



                  int studentCount = 1;
                  if(studentsList != null){
                  for(Student student : studentsList){
                	 
                	  Perfomance perfomance = new Perfomance();
                	  String score = "";
                	  if(perfomanceDAO.getPerformance(accountId, examId, student.getUuid(), streamId, sysConfig.getTerm(), sysConfig.getYear(),subjectId) != null){
                		  perfomance =  perfomanceDAO.getPerformance(accountId, examId, student.getUuid(), streamId, sysConfig.getTerm(), sysConfig.getYear(),subjectId);
                	  }
                	 

                     if(StringUtils.equals(examId, p123)) {
                    	 
                    	 String paper1 = "";
                    	 String paper2 = "";
                    	 String paper3 = "";
                    	 if(perfomance.getPaper1() > 0){
                    		 paper1 = "P1: " + perfomance.getPaper1();
                    	 }
                    	 
                    	 if(perfomance.getPaper2() > 0){
                    		 paper2 = ", P1: " + perfomance.getPaper2();
                    	 }
                    	 
                    	 if(perfomance.getPaper3() > 0){
                    		 paper3 = ", P1: " + perfomance.getPaper3();
                    	 }
                    	 
                         score = paper1 + paper2 + paper3;
               
                         
                      }else{
                    	 
                    	  score = perfomance.getScore() + "";
                      }
 
                	    

                	  %>
                 
                 <tr id="score<%=studentCount %>" onkeyup="validateScore(this.id)" onkeypress="return event.keyCode != 13;">

                    <td width="5%"><%=studentCount %>. </td>
                    <td ><%=student.getRegNo() %> </td>
                    <td ><%=student.getFirstname() %> </td>
                    <td ><%=student.getMiddlename() %> </td>
                    <td ><%=student.getLastname() %> </td>
                     <td > <%=score+"" %> </td>

                    <td id="editscore<%=studentCount %>" class="examelement form-control"  contenteditable='true' > </td>

                   
                 
                     <td class="hidden" ><%=student.getUuid() %> </td>

                 </tr>
                 <%
                   studentCount++;
                   }
                  }
                 %>
              </tbody>
              </table>
              </div>  
              </div>
              </div> 
              <!-- end panel  examAjax -->

            </div>



          </div>


                    
                    
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /page content -->
        
        
        <!-- state modal -->
        
        <jsp:include page="modals/statemodals.html" />

        <!-- footer -->
        
      
<jsp:include page="footer.jsp" />
        