<%@page import="com.yahoo.petermwenda83.server.session.SessionConstants"%>

<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@page import="com.yahoo.petermwenda83.persistence.staff.StaffDAO"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%>


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
	
	StaffDAO staffDAO= StaffDAO.getInstance();
	
	 List<Staff> staffList = new ArrayList<>();
     if(staffDAO.getStaff(accountId) != null){
    	 staffList = staffDAO.getStaff(accountId);
     }
     
     int staffCount = 0;
%>
<jsp:include page="header.jsp" />






<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>Staffs List</h2>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="x_panel">
					<div class="x_content">





						<!-- <h1>Test the Api call via jquery</h1>


						<button class="btn btn-primary btn-block" onclick="StaffApiCall()">
							Test API call</button> -->


						<div class="row ">

							<div class="col-md-4 pull-right">
								<h3 class="pull-right">
									Add a new Staff
									<button class="btn btn-primary" style="border-radius: 90%" id="add"
										onclick="StaffModal(this.id)">
										<i class="fa fa-user-plus fa-2x"></i>
									</button>

								</h3>

							</div>

						</div>






						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="staffs">
								<thead>
									<tr class="headings secondary-assent">

										<th class="column-title">#</th>
										<th class="column-title">Staff No</th>
										<th class="column-title">First name</th>
										<th class="column-title hidden">Middle name</th>
										<th class="column-title">Last name</th>
										<th class="column-title">Gender</th>
										<th class="column-title">Mobile</th>
										<th class="column-title">Email</th>
										<th class="column-title">User name</th>
										
										<th class="column-title hidden">uuid</th>
										
										<th class="column-title">Modify</th>

									</tr>
								</thead>

								<tbody class='tablebody'>

								 <%
								 String name;
                  for(Staff staff : staffList){  
                	  
                	  name= staff.getFirstname()+" "+staff.getLastname();
                	  
                    %>

									<tr class="tabledit" style='color: black;'>

										<td width="5%"><%=staffCount %></td>
										<td class="center"><%=staff.getStaffNo() %></td>
										<td class="center"><%=staff.getFirstname() %></td>
										<td class="center hidden"><%=staff.getMiddlename() %></td>
										<td class="center"><%=staff.getLastname() %></td>
										<td class="center"><%=staff.getGender() %></td>
										<td class="center"><%=staff.getMobile() %></td>
										<td class="center"><%=staff.getEmail() %></td>
										<td class="center"><%=staff.getUsername() %></td>
										<td class="center hidden"><%=staff.getUuid() %></td>
										
										
										<td>
										 
										<button class="btn btn-warning editStaff" id="edit" onclick="StaffModal(this.id)"> Edit  <span class="fa fa-edit"></span></button>
										<button class="btn btn-danger" id="Code" onclick="disableStaff('<%=name%>')"> Disable  <span class="fa fa-chain-broken"></span></button>
										</td>

									</tr>
									
									
							  <%      
                    staffCount++;
                  }%>	
                  </tbody>


							</table>

						</div>

















					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->






<!-- Staff Modal -->
<jsp:include page="modals/staffModals.jsp" />


<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer.jsp" />



<script>
	
</script>
