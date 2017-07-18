<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>


<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>
<%@page import="com.yahoo.petermwenda83.server.session.SessionStatistics"%>
<%@page import="com.yahoo.petermwenda83.server.cache.CacheVariables"%>


<%@page import="org.apache.commons.lang3.StringUtils"%>


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

     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   
   
     String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);
     String stffID = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_ID);
     

 %>






<jsp:include page="header.jsp" />

<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STAFF REGISTRATION PANEL : TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="staff.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>

<div class="row-fluid sortable">
    <div class="box span12">
        <div class="box-content">
          
               <%
                    HashMap<String, String> paramHash = (HashMap<String, String>) session.getAttribute(SessionConstants.STAFF_ADD_PARAM);

                        if (paramHash == null) {
                             paramHash = new HashMap<String, String>();
                            }
                             

                                String addErrStr = "";
                                String addsuccessStr = "";
                                session = request.getSession(false);
                                     addErrStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_ERROR);
                                     addsuccessStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_SUCCESS); 

                                if(session != null) {
                                    addErrStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_ERROR);
                                    addsuccessStr = (String) session.getAttribute(SessionConstants.STAFF_ADD_SUCCESS);
                                }                        

                                if (StringUtils.isNotEmpty(addErrStr)) {
                                    out.println("<p style='color:red;'>");                 
                                    out.println("error: " + addErrStr);
                                    out.println("</p>");                                 
                                    session.setAttribute(SessionConstants.STAFF_ADD_ERROR, null);
                                  } 
                                   else if (StringUtils.isNotEmpty(addsuccessStr)) {
                                    out.println("<p style='color:green;'>");                                 
                                    out.println("success: " + addsuccessStr);
                                    out.println("</p>");                                   
                                    session.setAttribute(SessionConstants.STAFF_ADD_SUCCESS, null);
                                  } 


                     %>









            
            
                    <form  class="form-horizontal"   action="addStaff" method="POST" >
                     <fieldset>



                   <div class="control-group" id="divid">
                        <label class="control-label" for="name">Position*</label>
                        <div class="controls">
                            <select name="Position" >
                                <option value="">Please select one</option> 
                                 <%
                                    int count = 1;
                                    if (positionList != null) {
                                        for (Position p : positionList) {
                                %>
                                <option value="<%= p.getUuid()%>"><%=p.getPosition()%></option>
                                <%
                                            count++;
                                        }
                                    }
                                %>
                            </select>                           
                          
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="Category">Staff*:</label>
                         <div class="controls">
                            <select name="category" >
                                <option value="">Please select one</option> 
                                <option value="Teaching">Teaching</option>
                                <option value="Non-Teaching">Non-Teaching</option>
                               
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">Employee Number*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="employeeNo" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("employeeNo")) %>"  >                                    
                        </div>
                    </div> 



                    <div class="control-group">
                        <label class="control-label" for="name">Username*:</label>
                        <div class="controls">
                         <input class="input-xlarge focused" id="receiver" type="text" name="username" 
                            value="<%= StringUtils.trimToEmpty(paramHash.get("username")) %>"  >

                        </div>
                    </div>  

                   


                     <div class="control-group">
                        <label class="control-label" for="name">First name*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="firstname" 
                             value="<%= StringUtils.trimToEmpty(paramHash.get("firstname")) %>" style="text-transform: capitalize;"  >                                    
                        </div>
                    </div> 


                    <div class="control-group">
                        <label class="control-label" for="name">Middle name*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="lastname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("lastname")) %>"  style="text-transform: capitalize;">
                        </div>
                    </div> 


                     <div class="control-group">
                        <label class="control-label" for="name">Last name:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="surname"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("surname")) %>" style="text-transform: capitalize;" >
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
                        <label class="control-label" for="name">NHIF NO:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nhif"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nhif")) %>"  >
                        </div>
                    </div> 

                    <div class="control-group">
                        <label class="control-label" for="name">NSSF No:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="nssf"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("nssf")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">Phone No*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="phone"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("phone")) %>"  >
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="name">ID No*:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="idno"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("idno")) %>"  >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">County:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="county"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("county")) %>" style="text-transform: capitalize;" >
                        </div>
                    </div>  

                    <div class="control-group">
                        <label class="control-label" for="name">YOB:</label>
                        <div class="controls">
                            <input class="input-xlarge focused" id="receiver" type="text" name="dob"
                              value="<%= StringUtils.trimToEmpty(paramHash.get("dob")) %>"  >
                        </div>
                    </div>  







                    
                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                         <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                        <button type="submit" class="btn btn-primary">Register</button>
                    </div> 

              </fieldset>
              </form>
       


    </div>
     </div>

</div>


<jsp:include page="footer.jsp" />
