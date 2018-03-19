/**
 * 
 */
package ke.co.qubintel.school.server.api.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ke.co.qubintel.school.server.api.rest.bean.ApiHouse;
import ke.co.qubintel.school.server.api.rest.bean.ApiStudentHouse;
import ke.co.qubintel.school.server.api.rest.bean.Response;
import ke.co.qubintel.school.server.bean.house.House;
import ke.co.qubintel.school.server.bean.house.StudentHouse;
import ke.co.qubintel.school.server.bean.student.Student;
import ke.co.qubintel.school.server.persistence.classroom.StreamDAO;
import ke.co.qubintel.school.server.persistence.house.HouseDAO;
import ke.co.qubintel.school.server.persistence.house.StudentHouseDAO;
import ke.co.qubintel.school.server.persistence.schoolaccount.AccountDAO;
import ke.co.qubintel.school.server.persistence.student.StudentDAO;

/**
 * @author peter
 *
 */
public class HouseService {
	
	private static StreamDAO streamDAO;
	private static AccountDAO accountDAO;
	
	private static StudentDAO studentDAO;

	private static HouseDAO houseDAO;
	private static StudentHouseDAO studentHouseDAO;


	static {
		streamDAO = StreamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
	
		studentDAO = StudentDAO.getInstance();

		houseDAO = HouseDAO.getInstance();
		studentHouseDAO = StudentHouseDAO.getInstance(); 
	}



	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getHouseList(String accountId) {
		Response response = new Response();
		if(houseDAO.getHouseList(accountId) != null) {
			return houseDAO.getHouseList(accountId); 

		}else {
			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;
		}
	}
	/**
	 * 
	 * @param accountId
	 * @param houseName
	 * @return
	 */
	public Object getHouse(String accountId, String houseName) {
		Response response = new Response();
		if(houseDAO.getHouse(accountId, houseName) != null) { 
			return houseDAO.getHouse(accountId, houseName);  

		}else {
			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;
		}
	}
	/**
	 * 
	 * @param apiHouse
	 * @return
	 */
	public Object putHouse(ApiHouse apiHouse) {
		Response response = new Response();
		if(houseDAO.getHouse(apiHouse.getAccountId(), apiHouse.getHouseName()) != null) { 
			response.setMessage("error");
			response.setDescription("An house with a similar name already exists!");
			return response;

		}else {

			House house = new House();
			house.setAccountId(apiHouse.getAccountId());
			house.setDescription(apiHouse.getDescription());
			house.setHouseName(apiHouse.getHouseName()); 

			if(houseDAO.putHouse(house)) {
				response.setMessage("success");
				response.setDescription("House added successfully!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, contact Admin!");
				return response;
			}


		}
	}
	/**
	 * 
	 * @param apiHouse
	 * @return
	 */
	public Object getUpdateHouse(ApiHouse apiHouse) {
		Response response = new Response();
		if(houseDAO.getHouseById(apiHouse.getAccountId(), apiHouse.getUuid()) == null) { 
			response.setMessage("error");
			response.setDescription("Nothing to update!");
			return response;

		}else {

			House house = houseDAO.getHouseById(apiHouse.getAccountId(), apiHouse.getUuid()); 
			house.setDescription(apiHouse.getDescription());
			house.setHouseName(apiHouse.getHouseName()); 

			if(houseDAO.updateHouse(house)) {
				response.setMessage("success");
				response.setDescription("House updated successfully!"); 
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, contact Admin!");
				return response;
			}


		}
	}
	/**
	 * 
	 * @param apiHouse
	 * @return
	 */
	public Object deleteHouse(String accountId, String uuid) {
		Response response = new Response();
		if(houseDAO.deleteHouse(accountId, uuid)) {  
			response.setMessage("success");
			response.setDescription("House deleted successfully!");
			return response;

		}else {
			response.setMessage("error");
			response.setDescription("Nothing to Delete!");
			return response;
		}
	}


	/////////////////////////////////////////////////////////////// TODO
	/**
	 * 
	 * @param apiStudentHouse
	 * @return
	 */
	public Object AssignHouse(ApiStudentHouse sh) {
		Response response = new Response();


		if(accountDAO.getAccountById(sh.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(studentDAO.getStudentById(sh.getAccountId(), sh.getStudentId()) == null) {
			response.setMessage("error");
			response.setDescription("Student not found!");
			return response;

		}else if(houseDAO.getHouseById(sh.getAccountId(), sh.getHouseId()) == null) {
			response.setMessage("error");
			response.setDescription("House not found!");
			return response;
		}else if(studentHouseDAO.getStudentHouse(sh.getAccountId(), sh.getStudentId()) != null) {
			response.setMessage("error");
			response.setDescription("House already assigned!"); 
			return response;

		}else {


			StudentHouse studentHouse = new StudentHouse();
			studentHouse.setAccountId(sh.getAccountId());
			studentHouse.setStudentId(sh.getStudentId());
			studentHouse.setHouseId(sh.getHouseId()); 

			if(studentHouseDAO.putStudentHouse(studentHouse, sh.getAccountId(), sh.getStudentId(), sh.getHouseId())) { 
				response.setMessage("success");
				response.setDescription("House assigned successfully!");
				return response;
			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, contact Admin!");
				return response;
			}

		}



	}
	/**
	 * 
	 * @param apiStudentHouse
	 * @return
	 */
	public Object changeHouse(ApiStudentHouse sh) {
		Response response = new Response();

		if(accountDAO.getAccountById(sh.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(studentDAO.getStudentById(sh.getAccountId(), sh.getStudentId()) == null) {
			response.setMessage("error");
			response.setDescription("Student not found!");
			return response;

		}else if(houseDAO.getHouseById(sh.getAccountId(), sh.getHouseId()) == null) {
			response.setMessage("error");
			response.setDescription("House not found!");
			return response;

		}else if(studentHouseDAO.getStudentHouse(sh.getAccountId(), sh.getStudentId()) == null) {
			response.setMessage("error");
			response.setDescription("No house to change!"); 
			return response;

		}else {


			if(studentHouseDAO.getStudentHouseById(sh.getAccountId(), sh.getUuid()) == null) {
				response.setMessage("error");
				response.setDescription("Nothing to change!");
				return response;

			}else{  

				StudentHouse studentHouse = studentHouseDAO.getStudentHouseById(sh.getAccountId(), sh.getUuid());
				studentHouse.setHouseId(sh.getHouseId()); 

				if(studentHouseDAO.changeHouse(studentHouse)) {
					response.setMessage("success");
					response.setDescription("House changed successfully!");
					return response;
				}else {
					response.setMessage("error");
					response.setDescription("Something went wrong, contact Admin!");
					return response;
				}

			}

		}



	}
	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object exitAssignedHouse(String accountId, String uuid) {
		Response response = new Response();

		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response;

		}else if(houseDAO.getHouseById(accountId, uuid) == null) {  
			response.setMessage("error");
			response.setDescription("House not found!");
			return response;

		}else if(studentHouseDAO.getStudentHouseById(accountId, uuid) == null) {
			response.setMessage("error");
			response.setDescription("No house to exit!"); 
			return response;

		}else {

			StudentHouse studentHouse = studentHouseDAO.getStudentHouseById(accountId, uuid);
			studentHouse.setDateOut(new StudentHouse().getDateOut()); 

			if(studentHouseDAO.exitHouse(studentHouse)) {
				response.setMessage("success");
				response.setDescription("House exited successfully!");
				return response;

			}else {
				response.setMessage("error");
				response.setDescription("Something went wrong, contact Admin!"); 
				return response;
			}

		}

	}
	
	/**
	 * 
	 * @param accountId
	 * @param studentId
	 * @return
	 */
	public Object getStudentHouse(String accountId, String studentId) {
		Response response = new Response();

		if(studentHouseDAO.getStudentHouse(accountId, studentId) != null) { 

			StudentHouse sh = studentHouseDAO.getStudentHouse(accountId, studentId);
			Student student = studentDAO.getStudentById(accountId, studentId); 
			String name = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();

			ApiStudentHouse studentHouse = new ApiStudentHouse();
			studentHouse.setUuid(sh.getUuid());
			studentHouse.setAccountId(accountId);
			studentHouse.setStudentId(sh.getStudentId());
			studentHouse.setStudentName(name);
			studentHouse.setRegNo(student.getRegNo());
			studentHouse.setHouseId(sh.getHouseId());
			studentHouse.setHouseName(houseDAO.getHouseById(accountId, sh.getHouseId()).getHouseName()); 
			studentHouse.setDateOut(sh.getDateOut().toString());
			studentHouse.setDateIn(sh.getDateIn().toString());

			return studentHouse;
		}else {
			response.setMessage("error");
			response.setDescription("Nothing to display!");
			return response;
		}

	}





	/**
	 * 
	 * @param apiStudentHouseList
	 * @return
	 */
	public Object AssignHouseList(List<ApiStudentHouse> sh_list) {
		Response response = new Response();

		//boolean success = false;

		if(sh_list.isEmpty()) {
			response.setMessage("error");
			response.setDescription("List is empty!");
			return response;
		}else {

			sh_list.stream().forEach(sh -> {


				StudentHouse studentHouse = new StudentHouse();
				studentHouse.setAccountId(sh.getAccountId());
				studentHouse.setStudentId(sh.getStudentId());
				studentHouse.setHouseId(sh.getHouseId()); 

				if(studentHouseDAO.getStudentHouse(sh.getAccountId(), sh.getStudentId()) == null) {
					studentHouseDAO.putStudentHouse(studentHouse, sh.getAccountId(), sh.getStudentId(), sh.getHouseId());
				}


			});

			response.setMessage("success");
			response.setDescription("House assigned successfully!");
			return response;
		}

	}

	/**
	 * 
	 * @param apiStudentHouseList
	 * @return
	 */
	public Object exitAssignedHouseList(List<ApiStudentHouse> sh_list) {
		Response response = new Response();

		//boolean success = false;

		if(sh_list.isEmpty()) {
			response.setMessage("error");
			response.setDescription("List is empty!");
			return response;
		}else {

			sh_list.stream().forEach(sh -> {

				StudentHouse studentHouse = studentHouseDAO.getStudentHouseById(sh.getAccountId(), sh.getUuid()); 
				studentHouse.setDateOut(new StudentHouse().getDateOut()); 

				studentHouseDAO.exitHouse(studentHouse);

			});

			response.setMessage("success");
			response.setDescription("House exited successfully!");
			return response;

		}

	}



	/**
	 * 
	 * @param accountId
	 * @param houseId
	 * @param streamId
	 * @return
	 */
	public Object studentHouseFilter(String accountId, String houseId, String streamId) {
		
		Response response = new Response();
		List<ApiStudentHouse> stu_house_list = new ArrayList<>();

		//filter by house
		if(houseId.length() > 5 && streamId.length() == 0) {

			if(houseDAO.getHouseById(accountId, houseId) != null) {
				//good, give me the students

				studentHouseDAO.getStudentHouseList(accountId, houseId)
				.stream()
				.forEach(sh ->{

					Student student = new Student();
					student = studentDAO.getStudentById(accountId, sh.getStudentId()); 
					String name = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();

					ApiStudentHouse studentHouse = new ApiStudentHouse();
					studentHouse.setUuid(sh.getUuid());
					studentHouse.setAccountId(accountId);
					studentHouse.setStudentId(sh.getStudentId());
					studentHouse.setStudentName(name);
					studentHouse.setRegNo(student.getRegNo());
					studentHouse.setHouseId(houseId);
					studentHouse.setHouseName(houseDAO.getHouseById(accountId, sh.getHouseId()).getHouseName());
					studentHouse.setDateOut(sh.getDateOut().toString());
					studentHouse.setDateIn(sh.getDateIn().toString());

					stu_house_list.add(studentHouse);
				});

				if(stu_house_list.isEmpty()) {
					response.setMessage("error");
					response.setDescription("nothing to display!"); 
					return response;

				}else {
					return stu_house_list; 
				}


			}else {
				response.setMessage("error");
				response.setDescription("house not found!"); 
				return response;
				//house not found
			}

		}
		//filter by stream
		else if(streamId.length() > 5 && houseId.length() == 0) {
			
			if(streamDAO.getStream(accountId, streamId) != null) {
				//good, give me the students
				
				studentDAO.getStudentByStream(accountId, streamId, "1")
				.stream()
				.forEach(student -> {
					
					ApiStudentHouse studentHouse = new ApiStudentHouse();
					
					String name = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();
					studentHouse.setStudentId(student.getUuid());
					studentHouse.setStudentName(name);
					studentHouse.setRegNo(student.getRegNo());
					
					StudentHouse sh = new StudentHouse();
					studentHouse.setUuid(sh.getUuid());
					studentHouse.setAccountId(accountId);
					
					if(studentHouseDAO.getStudentHouse(accountId, student.getUuid()) != null) {
						
						sh = studentHouseDAO.getStudentHouse(accountId, student.getUuid()); 
						
						studentHouse.setHouseId(sh.getHouseId());
						studentHouse.setHouseName(houseDAO.getHouseById(accountId, sh.getHouseId()).getHouseName());
						studentHouse.setDateOut(sh.getDateOut().toString());
						studentHouse.setDateIn(sh.getDateIn().toString());
					}
					
					stu_house_list.add(studentHouse);

				});

				if(stu_house_list.isEmpty()) {
					response.setMessage("error");
					response.setDescription("nothing to display!"); 
					return response;

				}else {
					return stu_house_list;
				}


			}else {
				//stream not found
				response.setMessage("error");
				response.setDescription("stream not found!"); 
				return response;
			}

		}
		//give me students for this stream and this house
		else if(streamId.length() > 5 && houseId.length() > 5) {

			if(streamDAO.getStream(accountId, streamId) != null) {

				if(houseDAO.getHouseById(accountId, houseId) != null) {
					//good, give me the students

					studentDAO.getStudentByStream(accountId, streamId, "1").stream().forEach(student -> {


						studentHouseDAO.getStudentHouseList(accountId, houseId)
						.stream()
						.filter(stu -> StringUtils.equals(stu.getStudentId(), student.getUuid()))  
						.forEach(sh ->{

							String name = student.getFirstname() + " " + student.getMiddlename() + " " + student.getLastname();

							ApiStudentHouse studentHouse = new ApiStudentHouse();
							studentHouse.setUuid(sh.getUuid());
							studentHouse.setAccountId(accountId);
							studentHouse.setStudentId(sh.getStudentId());
							studentHouse.setStudentName(name);
							studentHouse.setRegNo(student.getRegNo());
							studentHouse.setHouseId(houseId);
							studentHouse.setHouseName(houseDAO.getHouseById(accountId, sh.getHouseId()).getHouseName());
							studentHouse.setDateOut(sh.getDateOut().toString());
							studentHouse.setDateIn(sh.getDateIn().toString());

							stu_house_list.add(studentHouse);
						});
					});

					if(stu_house_list.isEmpty()) {
						response.setMessage("error");
						response.setDescription("nothing to display!"); 
						return response;

					}else {
						return stu_house_list;
					}


				}else {
					//house not found
					response.setMessage("error");
					response.setDescription("house not found!"); 
					return response;
				}


			}else {
				//stream not found
				response.setMessage("error");
				response.setDescription("stream not found!"); 
				return response;
			}


		}else {
			//nothing to display
			response.setMessage("error");
			response.setDescription("nothing to display!"); 
			return response;

		}
	}




}
