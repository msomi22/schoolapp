
<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.student.StudentPhotoDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.student.StudentPhoto"%>

<%@page import="com.yahoo.petermwenda83.server.servlet.student.LoadStudentPhoto"%>

<%@page import="com.yahoo.petermwenda83.server.servlet.util.PropertiesConfig"%>

<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.stream.Collectors"%>
<%@ page import="java.util.Calendar" %>
<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>

<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.*"%>


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
    StudentPhotoDAO studentPhotoDAO = StudentPhotoDAO.getInstance();


    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);

    String photoPath = "";
    String studentUuid = "";
    


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");

    String pos_Bursar =(String) PropertiesConfig.getConfigValue("POSITION_BURSAR");
    String staffPos = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_POSITION);

     studentUuid = request.getParameter("studentUuid");

    if(studentPhotoDAO.getPhotoByStudentid(studentUuid) != null){
      StudentPhoto studentPhoto = studentPhotoDAO.getPhotoByStudentid(studentUuid);
      photoPath = studentPhoto.getImagePath();
    }
    

String b64 = "";

if(LoadStudentPhoto.loadPhoto(photoPath) != null){
    BufferedImage bImage = LoadStudentPhoto.loadPhoto(photoPath); 
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bImage, "png", baos);
    baos.flush();
    byte[] imageInByteArray = baos.toByteArray();
    baos.close();
    b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
}


 
 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STUDENT MANAGEMENT PANEL(UPDATE STUDENT INFORMATION): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="studentIndex.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>


<div class="row-fluid sortable">


    <div class="box span12">
        <div class="box-content">
          
          <style type="text/css">
              .imageDiv{
                background-color: whitesmoke;
                height: 200px;
                width: 200px;
              }
          </style>


         <div class="pull-right">

         <div class="imageDiv">
             <img src="data:image/png;base64, <%=b64%>" alt="studentPhoto" width="200px" height="200px">
         </div>

         </div>  

        <form class="form-horizontal" action="updateStudentBasic" method="POST" enctype="multipart/form-data">
                 <fieldset>
                    
                                     
                             <div class="control-group">
                                <label class="control-label" for="AdmissionNumber">Admission Number:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="admNo"
                                      value="<%=request.getParameter("admNo")%>" >  <!--readonly -->
                                </div>
                             </div> 

                              <%  if(StringUtils.equals(staffPos,pos_Bursar)){ %>

                              <div class="control-group">
                                <label class="control-label" for="finalYear">Final Year:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalYear"
                                      value="<%=request.getParameter("finalYear")%>"  >
                                </div>
                             </div> 

                              <div class="control-group">
                                <label class="control-label" for="finalTerm">Final Term:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalTerm"
                                      value="<%=request.getParameter("finalTerm")%>"  >
                                </div>
                             </div> 

                            
                            <div class="control-group">
                                <label class="control-label" for="StydentType">StydentType*:</label>
                                         <div class="controls">
                                            <select name="StydentType" >
                                              <option value="Boarder">Boarder</option> 
                                              <option value="Day">Day</option> 
                                            </select>                           
                                          
                                        </div>
                            </div> 

                               <% } else { %>
                                   
                                   <div class="control-group">
                                <label class="control-label" for="finalYear">Final Year:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalYear"
                                      value="<%=request.getParameter("finalYear")%>" readonly >
                                </div>
                             </div> 

                              <div class="control-group">
                                <label class="control-label" for="finalTerm">Final Term:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="finalTerm"
                                      value="<%=request.getParameter("finalTerm")%>"  readonly>
                                </div>
                             </div> 

                               <%}%>

                             <div class="control-group">
                                <label class="control-label" for="firstname">First name:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="firstname"
                                      value="<%=request.getParameter("firstname") %>"  style="text-transform: capitalize;">
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="lastname">Middle name:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                                      value="<%=request.getParameter("lastname") %>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="surname">Last name:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="surname"
                                      value="<%=request.getParameter("surname")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 

                             <div class="control-group">
                                        <label class="control-label" for="gender">Gender*:</label>
                                         <div class="controls">
                                            <select name="gender" >
                                               <option value="">Please select one</option> 
                                               <option value="MALE">Male</option>
                                               <option value="FEMALE">Female</option> 
                                                
                                            </select>                           
                                          
                                        </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="dob">Date of Birth:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                                      value="<%=request.getParameter("dob")%>"  >
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="BcertNo">Birth Cert No:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="BcertNo"
                                      value="<%=request.getParameter("BcertNo")%>"  >
                                </div>
                             </div> 
                            
                             <div class="control-group">
                                <label class="control-label" for="County">County:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="county"
                                      value="<%=request.getParameter("county")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 
                             <div class="control-group">
                                <label class="control-label" for="Primary">Primary School:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="primary"
                                      value="<%=request.getParameter("primary")%>" style="text-transform: capitalize;" >
                                </div>
                             </div> 
                             <div class="control-group">
                                <label class="control-label" for="kcpeindex">KCPE Index:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="kcpeindex"
                                      value="<%=request.getParameter("kcpeindex")%>"  style="text-transform: capitalize;">
                                </div>
                             </div> 
                             <div class="control-group">
                                <label class="control-label" for="kcpemark">KCPE Marks:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="kcpemark"
                                      value="<%=request.getParameter("kcpemark")%>"  style="text-transform: capitalize;">
                                </div>
                             </div> 

                             <div class="control-group">
                                <label class="control-label" for="kcpeyear">KCPE Year:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" id="receiver" type="text" name="kcpeyear"
                                      value="<%=request.getParameter("kcpeyear")%>"  >
                                </div>
                             </div> 



                             <div class="control-group">
                                <label class="control-label" for="kcpeyear">Upload Photo:</label>
                                <div class="controls">
                                <input class="input-xlarge focused" type="file" size="50" name="passport" accept='image/*'>
                                </div>
                             </div> 
 
                                    
                            <div class="form-actions">
                                <input type="hidden" name="studentUuid" value="<%=request.getParameter("studentUuid")%>">
                                <input type="hidden" name="schoolUuid" value="<%=request.getParameter("schoolUuid")%>">
                                  <button type="submit" class="btn btn-primary">Update</button>
                            </div> 

              </fieldset>
              </form>
       











    </div>

</div>


<jsp:include page="footer.jsp" />
