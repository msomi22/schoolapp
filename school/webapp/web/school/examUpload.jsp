 
<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.ClassTeacher"%>
<%@page import="com.yahoo.petermwenda83.server.servlet.upload.UploadExam"%>



<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.*"%>
<%@page import="java.util.stream.Collectors"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>


<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

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
     String classroomuuid = "";

     ClassTeacherDAO classTeacherDAO = ClassTeacherDAO.getInstance();
     ClassTeacher classTeacher = classTeacherDAO.getClassTeacherByteacherId(stffID);
     if(classTeacher !=null){
           classroomuuid = classTeacher.getClassRoomUuid();
         }

   String notNull=null;
 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">

    <li> <b> <%=schoolname%> :EXAM UPLOAD PANEL TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>
        <li>
            <a href="perclassUpload.jsp">Per class upload</a> <span class="divider">/</span>
      </li>
         
    </ul>
</div>



<div class="row-fluid sortable">




        <div class="box span12">
        <div class="box-content">
            
            <%
                                String uploadErr = "";
                                String uploadsuccess = "";

                                     session = request.getSession(false);
                                     uploadErr = (String) session.getAttribute(SessionConstants.FILE_UPLOAD_ERROR);
                                     uploadsuccess = (String) session.getAttribute(SessionConstants.FILE_UPLOAD_SUCCESS); 

                                     if(session != null) {
                                      uploadErr = (String) session.getAttribute(SessionConstants.FILE_UPLOAD_ERROR);
                                      uploadsuccess = (String) session.getAttribute(SessionConstants.FILE_UPLOAD_SUCCESS); 
                                       }                     

                                if (StringUtils.isNotEmpty(uploadErr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + uploadErr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.FILE_UPLOAD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(uploadsuccess)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + uploadsuccess);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.FILE_UPLOAD_SUCCESS, null);
                                  } 




                 %>

            <div>
           


                
                
                <p>Upload Excel file with format <code>admission number,score</code></p>
                <p>The results file must be saved as<code>subjectcode.classcode_examcode.xlsx/xls</code><p> 
                <form method="POST" action="resultUpload" enctype="multipart/form-data">
                <fieldset>
                    <div class="control-group" id="javascript" javaScriptCheck="<%=notNull%>">
                         
                    <input type="file" name="file" required="true" multiple accept=".csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel"/>

                   <div class="form-actions">                        
                        <button type="submit" class="btn btn-primary">Upload File</button>           
                    </div>
                    </div>
                </fieldset>
                </form> 

             
                 
                 <h3>
                <%
                    if(StringUtils.isNotBlank((String)session.getAttribute(UploadExam.UPLOAD_FEEDBACK ))) {
                    String servletResponse =(String)session.getAttribute(UploadExam.UPLOAD_FEEDBACK );
                        out.println(servletResponse);
                        //used by javascript 
                        if(servletResponse!=null){notNull=servletResponse.substring(0,10);
                        }                    
                        session.setAttribute(UploadExam.UPLOAD_FEEDBACK, null);
                    }
                %>  
                </h3>

    </div>

</div>
<!--scroll to the bottom the page if file upload is done -->
<script type="text/javascript">


$("document").ready(function() {   

        var check1 = $("#javascript").attr("javaScriptCheck");
        if(check1.length>2){
           $("html, body").animate({ scrollTop: $(document).height() });
        }   

    });
</script>


<jsp:include page="footer.jsp" />
