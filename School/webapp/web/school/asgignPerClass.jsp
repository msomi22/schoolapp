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

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :NEW PAYMENT PANEL: TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>   
        <li>
            <a href="newPayment.jsp">Back</a> <span class="divider">/</span>
        </li>
       

    </ul>
</div>


<div class="row-fluid sortable">


                      

               
    <div class="box span12">
    <div class="box-content">



                                 <%  
     
                                String addErrStr = "";
                                String addsuccessStr = "";
                             
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_SUCCESS); 
                                    

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STUDENT_ADD_OTHER_MONIES_ADD_SUCCESS, null);
                                  } 


            %>



            <form  class="form-horizontal"   action="addPaymentTOStudentperClasss" method="POST" >
                 <fieldset>


                 <div class="control-group" id="divid">
                        <label class="control-label" for="class">Select class</label>
                        <div class="controls">
                            <select name="classUuid" >
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

                    
                           

                  <div class="control-group">
                      <label class="control-label" for="name">Type of Money*:</label>
                         <div class="controls">
                             <select name="moneyTypeUuid" >
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

                                    
              <div class="form-actions">
              <button type="submit" class="btn btn-primary">Assign</button>
              </div> 

              </fieldset>
              </form>
       




    

    </div>

</div>


<jsp:include page="footer.jsp" />
