<%@page import="com.yahoo.petermwenda83.bean.schoolaccount.SchoolAccount"%>

<%@page import="com.yahoo.petermwenda83.persistence.exam.ExamConfigDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.exam.ExamConfig"%>


<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.StaffDetails"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>

<%@page import="com.yahoo.petermwenda83.persistence.staff.PositionDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Position"%>

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

<%@page import="net.sf.ehcache.Element"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>

<%@page import="java.text.SimpleDateFormat"%>


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
    String staffUsername = (String) session.getAttribute(SessionConstants.SCHOOL_STAFF_SIGN_IN_USERNAME);


    String schoolname = school.getSchoolName();
    String staffId = request.getParameter("staffuuid");

    ExamConfigDAO examConfigDAO = ExamConfigDAO.getInstance();
    ExamConfig examConfig = examConfigDAO.getExamConfig(accountuuid);


    session.setMaxInactiveInterval(SessionConstants.SESSION_TIMEOUT);
    response.setHeader("Refresh", SessionConstants.SESSION_TIMEOUT + "; url=../schoolLogout");
   

     
     
     
     Staff staff = new Staff();
     StaffDAO staffDAO = StaffDAO.getInstance(); 
     staff = staffDAO.getStaff(accountuuid,staffId);

     StaffDetails staffDetail = new StaffDetails();
     StaffDetailsDAO staffDetailsDAO = StaffDetailsDAO.getInstance();
     staffDetail = staffDetailsDAO.getStaffDetail(staff.getUuid());


     HashMap<String, String> positionHash = new HashMap<String, String>();
     PositionDAO positionDAO = PositionDAO.getInstance();
     List<Position> positionList = new ArrayList<Position>(); 
     positionList = positionDAO.getPositionList();
     for(Position pp : positionList){
      positionHash.put(pp.getUuid(),pp.getPosition());  
       }
      

     
                          

 %>






<jsp:include page="header.jsp" />


<div>
    <ul class="breadcrumb">
     <li> <b> <%=schoolname%> :STAFF MANAGEMENT PANEL(UPDATE STAFF INFORMATION): TERM <%=examConfig.getTerm()%>:<%=examConfig.getYear()%> <b> </li> <br>


        <li>
            <a href="staff.jsp">Back</a> <span class="divider">/</span>
        </li>

        
    </ul>
</div>


<div class="row-fluid sortable">

               




    <div class="box span12">
        <div class="box-content">

        <form class="form-horizontal" action="updateStaff" method="POST"  >
                <fieldset>


                 <div class="control-group">
                        <label class="control-label" for="employeeNo">Employee Number</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="employeeNo" type="text" value="<%=staffDetail.getEmployeeNo()%>" >
                        </div>
                    </div>

                  <div class="control-group">
                        <label class="control-label" for="Category">Staff:</label>
                         <div class="controls">
                            <select name="category" >
                                <option value="">Please select one</option> 
                                <option value="Teaching">Teaching</option>
                                <option value="Non-Teaching">Non-Teaching</option>
                               
                            </select>                           
                          
                        </div>
                    </div> 

                        
                    <div class="control-group" id="divid">
                        <label class="control-label" for="name">Position</label>
                        <div class="controls">
                            <select name="position" >
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
                        <label class="control-label" for="username">Username</label>
                        <div class="controls">
                            <input class="input-xlarge focused"  name="username" type="text" value="<%=staff.getUserName()%>" >
                        </div>
                    </div>
                    
                    <div class="control-group">
                        <label class="control-label" for="firstname">First name</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="firstname" type="text" value="<%=StringUtils.capitalize(staffDetail.getFirstName().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="lastname">Middle name</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="lastname" type="text" value="<%=StringUtils.capitalize(staffDetail.getLastName().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="surname">Last name</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="surname" type="text" value="<%=StringUtils.capitalize(staffDetail.getSurname().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="gender">Gender:</label>
                         <div class="controls">
                            <select name="gender" >
                                <option value="">Please select one</option> 
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                                
                            </select>                           
                          
                        </div>
                    </div> 

                     <div class="control-group">
                        <label class="control-label" for="nhif">NHIF</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="nhif" type="text" value="<%=staffDetail.getNhifNo()%>">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="nssf">NSSF</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="nssf" type="text" value="<%=staffDetail.getNssfNo()%>">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="phone">Phone*</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="phone" type="text" value="<%=staffDetail.getPhone()%>">
                        </div>
                    </div>


                     <div class="control-group">
                        <label class="control-label" for="dob">YOB</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="dob" type="text" value="<%=staffDetail.getdOB()%>">
                        </div>
                    </div>

                     <div class="control-group">
                        <label class="control-label" for="idno">ID No</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="idno" type="text" value="<%=staffDetail.getNationalID()%>">
                        </div>
                    </div>


                    <div class="control-group">
                        <label class="control-label" for="county">County</label>
                        <div class="controls">
                            <input class="input-xlarge focused"   name="county" type="text" value="<%=StringUtils.capitalize(staffDetail.getCounty().toLowerCase())%>" style="text-transform: capitalize;">
                        </div>
                    </div>


                  
                    <div class="form-actions">
                        <input type="hidden" name="schooluuid" value="<%=accountuuid%>">
                        <input type="hidden" name="staffUuid" value="<%=staff.getUuid()%>">
                        <input type="hidden" name="systemuser" value="<%=staffUsername%>">
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                   

                </fieldset>
            </form>
             
            
          


    </div>

</div>


<jsp:include page="footer.jsp" />
