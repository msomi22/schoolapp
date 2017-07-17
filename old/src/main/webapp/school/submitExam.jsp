
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="com.yahoo.petermwenda83.persistence.classroom.StreamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.classroom.Stream"%>

<%@page import="com.yahoo.petermwenda83.persistence.subject.SubjectDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.subject.Subject"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.Exam"%>

<%@page import="com.yahoo.petermwenda83.bean.student.Student"%>

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



                  <div class="row">

                  
                  <form class="form-horizontal" method="POST" action="getStudents"> 
                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">

                   <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">Stream</label>
                        <div class="col-md-9 col-sm-9 col-xs-12">
                          <select class="form-control" name="streamId">
                          <%
                          
                            for(Stream stream : streamList){                        	  
                        	   %>
                        	  <option value="<%=stream.getUuid() %>"> <%=stream.getDescription() %> </option>
                        	   <%
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
                          <select class="form-control" name="subjectId">
                            <%
                            for(Subject subject : subjectList){                            	
                            	%>
                          	  <option value="<%=subject.getUuid() %>"> <%=subject.getDescription() %> </option>
                          	   <%
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
                          <select class="form-control" name="examId">
                            <%

						     for(Exam exam : examList){							  
							             %>
                        	  <option value="<%=exam.getUuid() %>"> <%=exam.getDescription() + " , OutOf: " + exam.getOutOf() %></option> 
                        	   <%
						       }
                          %>                   
                          </select>
                        </div>
                      </div>

                  </div>

                  <div class="col-md-3 col-sm-12 col-xs-12 form-group">
                    <button type="submit" class="btn btn-success">
                      Submit
                    </button>
                  </div>
                  </form>



                  <div class="col-md-12 col-sm-12 col-xs-12 form-group">

                  <div class="x_panel">
                  <div class="x_title">
                  
                  
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
                  
                  if(!idsHash.isEmpty()){
                	  examId = (String)idsHash.get("examId"); 
                	  streamId = (String)idsHash.get("streamId"); 
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
                  
                  %>
                  
                  
                  
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
                          <tr class="headings">

                            <th class="column-title"># </th>
                            <th class="column-title">RegNo </th>
                            <th class="column-title">First name </th>
                            <th class="column-title">Middle name </th>
                            <th class="column-title">Last name </th>
                            <th class="column-title">Score ( <%=currentExam %> ) </th>   
                            
                          </tr>
                        </thead>

              <tbody class='tablebody'>
              
                 <%
                  int studentCount = 1;
                  if(studentsList != null){
                  for(Student student : studentsList){
                 %>
                 <tr>

                    <td width="5%"><%=studentCount %>. </td>
                    <td ><%=student.getRegNo() %> </td>
                    <td ><%=student.getFirstname() %> </td>
                    <td ><%=student.getMiddlename() %> </td>
                    <td ><%=student.getLastname() %> </td>
                    <td contenteditable='true'> </td>

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

        <!-- footer -->
<jsp:include page="footer.jsp" />
        