
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%-- <%@page import="com.yahoo.petermwenda83.persistence"%>
<%@page import="com.yahoo.petermwenda83.bean.staff.Staff"%> --%>


<%
	/* StaffDAO staffDAO= StaffDAO.getInstance();
	
	 List<Staff> staffList = new ArrayList<>();
	 if(staffDAO.getStaff(accountId) != null){
		 staffList = staffDAO.getStaff(accountId);
	 }
	 
	 int staffCount = 0; */
%>
<jsp:include page="header2.jsp" />






<!-- page content -->
<div class="right_col" role="main">
	<div class="">
		<div class="page-title">
			<div class="title_left">
				<h2>Sites List</h2>
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

							<div class="col-md-4">
								<h3 class="">Available sites</h3>

							</div>

						</div>







						<div class="table-responsive">
							<table class="table table-striped jambo_table bulk_action"
								id="sites">
								<thead>
									<tr class="headings secondary-assent">
										<th class="column-title hidden">id</th>
										<th class="column-title hidden">identityId</th>
										<th class="column-title">Name</th>
										<th class="column-title">County</th>
										<th class="column-title">Town</th>
										<th class="column-title">Street</th>
										<th class="column-title hidden">gpslocation</th>
										<th class="column-title">Antennae</th>




									</tr>
								</thead>

								<tbody class='tablebody'>


									<tr class="tabledit" style='color: black;'>

										<td class="center hidden"></td>
										<td class="center hidden"></td>
										<td width="center">Site ###</td>
										<td class="center">Nairobi</td>
										<td class="center">Westlands</td>
										<td class="center">Muthithi road</td>
										<td class="center hidden"></td>
										<td>

											<button class="btn btn-info showAntennae" 
												onclick="showAntennae()">
												Show Antennae Info <span class="fa fa-info"></span>
											</button>
										</td>

									</tr>
									
									
									<tr class="tabledit" style='color: black;'>

										<td class="center hidden"></td>
										<td class="center hidden"></td>
										<td width="center">Site ###</td>
										<td class="center">Nairobi</td>
										<td class="center">Westlands</td>
										<td class="center">Ojiji road</td>
										<td class="center hidden"></td>
										<td>

											<button class="btn btn-info showAntennae" 
												onclick="showAntennae()">
												Show Antennae Info <span class="fa fa-info"></span>
											</button>
										</td>

									</tr>
									
									
									
									<tr class="tabledit" style='color: black;'>

										<td class="center hidden"></td>
										<td class="center hidden"></td>
										<td width="center">Site ###</td>
										<td class="center">Nairobi</td>
										<td class="center">Westlands</td>
										<td class="center">Mpaka road</td>
										<td class="center hidden"></td>
										<td>

											<button class="btn btn-info showAntennae" 
												onclick="showAntennae()">
												Show Antennae Info <span class="fa fa-info"></span>
											</button>
											
										</td>

									</tr>
									
									
									<tr class="tabledit" style='color: black;'>

										<td class="center hidden"></td>
										<td class="center hidden"></td>
										<td width="center">Site ###</td>
										<td class="center">Nairobi</td>
										<td class="center">Nairobi City</td>
										<td class="center">Moi lane</td>
										<td class="center hidden"></td>
										<td>

											<button class="btn btn-info showAntennae" 
												onclick="showAntennae()">
												Show Antennae Info <span class="fa fa-info"></span>
											</button>
										</td>

									</tr>
									
									<tr class="tabledit" style='color: black;'>

										<td class="center hidden"></td>
										<td class="center hidden"></td>
										<td width="center">Site ###</td>
										<td class="center">Kiambu</td>
										<td class="center">Banana</td>
										<td class="center">Backyard</td>
										<td class="center hidden"></td>
										<td>

											<button class="btn btn-info showAntennae" 
												onclick="showAntennae()">
												Show Antennae Info <span class="fa fa-info"></span>
											</button>
										</td>

									</tr>

									<%--  <%
                  for(Staff staff : staffList){                   
                    %> --%>
									<%-- 
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
										
										
										<td>
										 
										<button class="btn btn-warning editStaff" id="edit" onclick="StaffModal(this.id)"> Edit  <span class="fa fa-edit"></span></button>
										<button class="btn btn-danger" id="Code Code" onclick="disableStaff(this.id)"> Disable  <span class="fa fa-chain-broken"></span></button>
										</td>

									</tr>
									
									
							  <%      
                    staffCount++;
                  }
                  
                  %> --%>

									<!-- <tr class="tabledit">

										<td class="center">#</td>
										<td class="center btn btn-info">7.77</td>
										<td class="center btn btn-warning">40.888</td>

										<td class="center btn btn-warning">42.77</td>


									</tr> -->


									<!-- <tr class="tabledit" style='color: black;'>

										<td width="5%">#</td>
										<td class="center">
											<button class="btn btn-info btn-block" >
												7.77</button>
										</td>
										<td class="center">
											<button class="btn btn-warning btn-block" >
												40.88</button>
										</td>
										<td class="center">
											<button class="btn btn-warning btn-block"
												>
												42.77
											</button></td>





									</tr> -->
								</tbody>
								
								
								<!-- <tfoot>
									<tr class="headings secondary-assent">
										<th class="column-title hidden">id</th>
										<th class="column-title hidden">identityId</th>
										<th class="column-title">Name</th>
										<th class="column-title">County</th>
										<th class="column-title">Town</th>
										<th class="column-title">Street</th>
										<th class="column-title hidden">gpslocation</th>
										<th class="column-title">Antennae</th>




									</tr>
								</tfoot> -->


							</table>




							<!--  <table id="example" class="display" width="100%">
							 
							 <thead>
							 <tr class="headings secondary-assent">
							  <th class="column-title hidden">uuid</th>
							 <th class="column-title">addDate</th>
										<th class="column-title">pitch</th>
										<th class="column-title">roll</th>
										<th class="column-title">yaw</th>
							 
							 </tr>
							 
							 </thead>
							 </table> -->

						</div>

















					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /page content -->





<!-- Antennae Modal -->
<jsp:include page="modals/showAntennaeModal.html" />

<!-- State Modal -->
<jsp:include page="modals/statemodals.html" />

<!-- footer -->
<jsp:include page="footer2.jsp" />




<script>
	
</script>
