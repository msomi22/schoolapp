package com.yahoo.petermwenda83.server.servlet.sms.send;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yahoo.petermwenda83.bean.account.SmsApi;
import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.bean.classroom.ClassRoom;
import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.bean.smsapi.AfricasTalking;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.bean.student.Student;
import com.yahoo.petermwenda83.bean.student.guardian.StudentParent;
import com.yahoo.petermwenda83.persistence.classroom.ClassesDAO;
import com.yahoo.petermwenda83.persistence.classroom.RoomDAO;
import com.yahoo.petermwenda83.persistence.guardian.ParentsDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsApiDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.SmsSendDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDetailsDAO;
import com.yahoo.petermwenda83.persistence.student.StudentDAO;
import com.yahoo.petermwenda83.server.session.SessionConstants;

/**
 * @author peter
 *
 */
public class SendSMS extends HttpServlet{


	final String SMS_SEND_SUCCESS = "SMSes were sent Successfully.";
	final String SMS_SEND_NOT_SENT = "Something went wrong, SMSes/Some SMSes were not sent.";
	final String SMS_SEND_ERROR = "You can't send a blank message.";

	final String STATUS_ACTIVE = "1";

	final String DESTINATION_PARENTS = "Parents";
	final String DESTINATION_TEACHING_STAFF = "Teaching Staff";
	final String DESTINATION_NON_TEACHING_STAFF = "Non Teaching Staff";

	final String FORM_1 = "C143978A-E021-4015-BC67-5A00D6C910D1";
	final String FORM_2 = "3E22E428-3155-42F5-B73E-66553ED501C9";
	final String FORM_3 = "A4BFC2BD-262F-4207-99C8-057D6ADF80C7";
	final String FORM_4 = "14E56350-08DA-45CC-97D9-C225AF74A7AD";

	private static ParentsDAO parentsDAO;
	private static RoomDAO roomDAO;
	private static ClassesDAO classesDAO;

	private static StudentDAO studentDAO;
	private static SmsSendDAO smsSendDAO;
	private static StaffDAO staffDAO;
	private static StaffDetailsDAO staffDetailsDAO;
	private static SmsApiDAO smsApiDAO;

	Stream stream = new Stream();
	List<ClassRoom> classRoomList  = new ArrayList<>(); 
	List<Student> studentPerClassList = new ArrayList<Student>();
	List<StudentParent> parentListPerClass = new ArrayList<StudentParent>();

	String classname= "";
	String classroomname = "";

	String message = "";

	/**    
	 *
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		parentsDAO = ParentsDAO.getInstance();
		studentDAO = StudentDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		smsSendDAO = SmsSendDAO.getInstance();
		staffDetailsDAO = StaffDetailsDAO.getInstance();
		roomDAO = RoomDAO.getInstance();
		classesDAO = ClassesDAO.getInstance();
		smsApiDAO = SmsApiDAO.getInstance();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		String destination = StringUtils.trimToEmpty(request.getParameter("destination"));//Parents,Teaching Staff,Non Teaching Staff
		message = StringUtils.trimToEmpty(request.getParameter("smsText"));
		String accountId = StringUtils.trimToEmpty(request.getParameter("schooluuid"));

		if(!StringUtils.isBlank(message)){
			//common
			SmsApi smsApi = smsApiDAO.getSmsApi(accountId);
			AfricasTalking africasTalking = new AfricasTalking();
			String username = smsApi.getApiPassword();//africasTalking.getUsername();
			String apiKey   = smsApi.getApiKey();//africasTalking.getApiKey();
			//end common

			if(StringUtils.equals(destination, DESTINATION_PARENTS)){

				List<StudentParent> parentList = new ArrayList<StudentParent>();
				List<Student> studentList = new ArrayList<Student>();
				//get all students for a particular school
				studentList = studentDAO.getAllStudentList(accountId);//STATUS_ACTIVE
				String studentuuid = "";
				for(Student st : studentList){
					//get student id where status is active
					if(StringUtils.equals(st.getStatusUuid(), STATUS_ACTIVE)){
						studentuuid = st.getUuid();

						String parentphone = "";
						String formatedparentphone = "";
						String realParentphone = "";
						//list of parents for active students 
						parentList = parentsDAO.getParentListByStudent(studentuuid); 
						//int pcount = 1;
						for(StudentParent sp : parentList){
							//get parents phone and name
							parentphone = sp.getFatherphone();
							formatedparentphone = parentphone.replaceFirst("^0+(?!$)", "");
							realParentphone = "+254"+formatedparentphone;
							//message
							africasTalking.setMessage(message); 
							africasTalking.setRecipients(realParentphone); 
							AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
							//send message to parent now
							//save to database
							OutGoingSMS outGoingSMS = new OutGoingSMS();
							if(realParentphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
								outGoingSMS.setStatus("failed");
								outGoingSMS.setAccountId(accountId);
								outGoingSMS.setMobile(realParentphone);
								outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
								outGoingSMS.setSmsCost("1"); 
								smsSendDAO.putSmsSend(outGoingSMS);
							}
							sendSmS(gateway,africasTalking,outGoingSMS,accountId); 

							session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 

							// pcount++;
						}
					}
				}
			}else if(StringUtils.equals(destination, FORM_1)){

				if(classesDAO.getClass(destination) !=null){
					stream = classesDAO.getClass(destination);
					classname = stream.getClassName();
				}

				if(roomDAO.getAllRooms(accountId) !=null){
					classRoomList = roomDAO.getAllRooms(accountId);
				}
				for(ClassRoom room : classRoomList){
					if(StringUtils.contains(room.getRoomName(), classname)){

						//get students to these stream,Student
						if(studentDAO.getAllStudents(accountId, room.getUuid()) !=null){
							studentPerClassList = studentDAO.getAllStudents(accountId, room.getUuid());
						}

						String studentUuid ="";
						for(Student st : studentPerClassList){
							studentUuid = st.getUuid();
							//get parents for the given students
							if(parentsDAO.getParentListByStudent(studentUuid) !=null){
								parentListPerClass = parentsDAO.getParentListByStudent(studentUuid); 
							}
							//get the parents StudentParent
							String parentphone = "";
							String formatedparentphone = "";
							String realParentphone = "";
							for(StudentParent stup : parentListPerClass){
								parentphone = stup.getFatherphone();

								formatedparentphone = parentphone.replaceFirst("^0+(?!$)", "");
								realParentphone = "+254"+formatedparentphone;
								//message
								africasTalking.setMessage(message); 
								africasTalking.setRecipients(realParentphone); 
								AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
								//send message to parent now
								//save to database
								OutGoingSMS outGoingSMS = new OutGoingSMS();
								if(realParentphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
									outGoingSMS.setStatus("failed");
									outGoingSMS.setAccountId(accountId);
									outGoingSMS.setMobile(realParentphone);
									outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
									outGoingSMS.setSmsCost("1"); 
									smsSendDAO.putSmsSend(outGoingSMS);
								}
								sendSmS(gateway,africasTalking,outGoingSMS,accountId); 

								session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
							}
						}

					}else {
						//SMS_SEND_NOT_SENT
						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_NOT_SENT); 
					}

				}

			}else if(StringUtils.equals(destination, FORM_2)){


				if(classesDAO.getClass(destination) !=null){
					stream = classesDAO.getClass(destination);
					classname = stream.getClassName();
				}

				if(roomDAO.getAllRooms(accountId) !=null){
					classRoomList = roomDAO.getAllRooms(accountId);
				}
				for(ClassRoom room : classRoomList){
					if(StringUtils.contains(room.getRoomName(), classname)){

						//get students to these stream,Student
						if(studentDAO.getAllStudents(accountId, room.getUuid()) !=null){
							studentPerClassList = studentDAO.getAllStudents(accountId, room.getUuid());
						}
						String studentUuid ="";
						for(Student st : studentPerClassList){
							studentUuid = st.getUuid();
							//get parents for the given students
							if(parentsDAO.getParentListByStudent(studentUuid) !=null){
								parentListPerClass = parentsDAO.getParentListByStudent(studentUuid); 
							}
							//get the parents StudentParent
							String parentphone = "";
							String formatedparentphone = "";
							String realParentphone = "";
							for(StudentParent stup : parentListPerClass){
								parentphone = stup.getFatherphone();
								formatedparentphone = parentphone.replaceFirst("^0+(?!$)", "");
								realParentphone = "+254"+formatedparentphone;
								//message
								africasTalking.setMessage(message); 
								africasTalking.setRecipients(realParentphone); 
								AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
								//send message to parent now
								//save to database
								OutGoingSMS outGoingSMS = new OutGoingSMS();
								if(realParentphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
									outGoingSMS.setStatus("failed");
									outGoingSMS.setAccountId(accountId);
									outGoingSMS.setMobile(realParentphone);
									outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
									outGoingSMS.setSmsCost("1"); 
									smsSendDAO.putSmsSend(outGoingSMS);
								}
								sendSmS(gateway,africasTalking,outGoingSMS,accountId); 

								session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
							}
						}


					}else {
						//SMS_SEND_NOT_SENT
						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_NOT_SENT); 
					}

				}

			}else if(StringUtils.equals(destination, FORM_3)){


				if(classesDAO.getClass(destination) !=null){
					stream = classesDAO.getClass(destination);
					classname = stream.getClassName();
				}

				if(roomDAO.getAllRooms(accountId) !=null){
					classRoomList = roomDAO.getAllRooms(accountId);
				}
				for(ClassRoom room : classRoomList){
					if(StringUtils.contains(room.getRoomName(), classname)){

						//get students to these stream,Student
						if(studentDAO.getAllStudents(accountId, room.getUuid()) !=null){
							studentPerClassList = studentDAO.getAllStudents(accountId, room.getUuid());
						}
						String studentUuid ="";
						for(Student st : studentPerClassList){
							studentUuid = st.getUuid();
							//get parents for the given students
							if(parentsDAO.getParentListByStudent(studentUuid) !=null){
								parentListPerClass = parentsDAO.getParentListByStudent(studentUuid); 
							}
							//get the parents StudentParent
							String parentphone = "";
							String formatedparentphone = "";
							String realParentphone = "";
							for(StudentParent stup : parentListPerClass){
								parentphone = stup.getFatherphone();
								formatedparentphone = parentphone.replaceFirst("^0+(?!$)", "");
								realParentphone = "+254"+formatedparentphone;
								//message
								africasTalking.setMessage(message); 
								africasTalking.setRecipients(realParentphone); 
								AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
								//send message to parent now
								//save to database
								OutGoingSMS outGoingSMS = new OutGoingSMS();
								if(realParentphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
									outGoingSMS.setStatus("failed");
									outGoingSMS.setAccountId(accountId);
									outGoingSMS.setMobile(realParentphone);
									outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
									outGoingSMS.setSmsCost("1"); 
									smsSendDAO.putSmsSend(outGoingSMS);
								}
								sendSmS(gateway,africasTalking,outGoingSMS,accountId); 

								session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
							}
						}


					}else {
						//SMS_SEND_NOT_SENT
						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_NOT_SENT); 
					}

				}

			}else if(StringUtils.equals(destination, FORM_4)){

				if(classesDAO.getClass(destination) !=null){
					stream = classesDAO.getClass(destination);
					classname = stream.getClassName();
				}

				if(roomDAO.getAllRooms(accountId) !=null){
					classRoomList = roomDAO.getAllRooms(accountId);
				}
				for(ClassRoom room : classRoomList){

					if(StringUtils.contains(room.getRoomName(), classname)){

						//get students to these stream,Student
						if(studentDAO.getAllStudents(accountId, room.getUuid()) !=null){
							studentPerClassList = studentDAO.getAllStudents(accountId, room.getUuid());
						}
						String studentUuid ="";
						for(Student st : studentPerClassList){
							studentUuid = st.getUuid();
							//get parents for the given students
							if(parentsDAO.getParentListByStudent(studentUuid) !=null){
								parentListPerClass = parentsDAO.getParentListByStudent(studentUuid); 
							}
							//get the parents StudentParent
							String parentphone = "";
							String formatedparentphone = "";
							String realParentphone = "";
							for(StudentParent stup : parentListPerClass){
								parentphone = stup.getFatherphone();
								formatedparentphone = parentphone.replaceFirst("^0+(?!$)", "");
								realParentphone = "+254"+formatedparentphone;
								//message
								africasTalking.setMessage(message); 
								africasTalking.setRecipients(realParentphone); 
								AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
								//send message to parent now
								//save to database
								OutGoingSMS outGoingSMS = new OutGoingSMS();
								if(realParentphone !=null && message.replaceAll("[\r\n]+", " ") !=null){
									outGoingSMS.setStatus("failed");
									outGoingSMS.setAccountId(accountId);
									outGoingSMS.setMobile(realParentphone);
									outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
									outGoingSMS.setSmsCost("1"); 
									smsSendDAO.putSmsSend(outGoingSMS);
								}
								sendSmS(gateway,africasTalking,outGoingSMS,accountId); 

								session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
							}

						}

					}else {
						//SMS_SEND_NOT_SENT
						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_NOT_SENT); 
					}

				}

			}
			else if(StringUtils.equals(destination, DESTINATION_TEACHING_STAFF)){

				String category = "";
				String TstaffPhone = "";
				String formatedTstaffPhone = "";
				String realTstaffPhone = "";
				List<Staff> staffList = new ArrayList<Staff>();
				staffList = staffDAO.getStaffList(accountId);
				// int count1 = 1;
				for(Staff stf : staffList){
					category = stf.getCategory();
					//Teaching staff
					if(StringUtils.equals(category, "Teaching")){
						//Non-Teaching staff
						Staff staffDetail = staffDetailsDAO.getStaffDetail(stf.getUuid()); 
						TstaffPhone = staffDetail.getPhone();
						formatedTstaffPhone = TstaffPhone.replaceFirst("^0+(?!$)", "");
						realTstaffPhone = "+254"+formatedTstaffPhone;
						//message
						africasTalking.setMessage(message); 
						africasTalking.setRecipients(realTstaffPhone); 
						AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
						//send message to parent now
						//save to database
						OutGoingSMS outGoingSMS = new OutGoingSMS();
						if(realTstaffPhone !=null && message.replaceAll("[\r\n]+", " ") !=null){
							outGoingSMS.setStatus("failed");
							outGoingSMS.setAccountId(accountId);
							outGoingSMS.setMobile(message.replaceAll("[\r\n]+", " "));
							outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
							outGoingSMS.setSmsCost("1"); 
							smsSendDAO.putSmsSend(outGoingSMS);
						}
						sendSmS(gateway,africasTalking,outGoingSMS,accountId); 



						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
						//count1++;
					}

				}


			} else if(StringUtils.equals(destination, DESTINATION_NON_TEACHING_STAFF)){
				String category = "";
				String NTstaffPhone = "";
				String formatedNTstaffPhone = "";
				String realNTstaffPhone = "";
				List<Staff> staffList = new ArrayList<Staff>();
				staffList = staffDAO.getStaffList(accountId);
				// int count2 = 1;
				for(Staff stf : staffList){
					category = stf.getCategory();
					//Teaching staff
					if(StringUtils.equals(category, "Non-Teaching")){
						//Non-Teaching staff
						Staff staffDetail = staffDetailsDAO.getStaffDetail(stf.getUuid()); 
						NTstaffPhone = staffDetail.getPhone();
						formatedNTstaffPhone = NTstaffPhone.replaceFirst("^0+(?!$)", "");
						realNTstaffPhone = "+254"+formatedNTstaffPhone;
						//message
						africasTalking.setMessage(message); 
						africasTalking.setRecipients(realNTstaffPhone); 
						AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
						//send message to parent now
						//save to database
						OutGoingSMS outGoingSMS = new OutGoingSMS();
						if(realNTstaffPhone !=null && message.replaceAll("[\r\n]+", " ") !=null){
							outGoingSMS.setStatus("failed");
							outGoingSMS.setAccountId(accountId);
							outGoingSMS.setMobile(message.replaceAll("[\r\n]+", " "));
							outGoingSMS.setMessage(message.replaceAll("[\r\n]+", " "));
							outGoingSMS.setSmsCost("1"); 
							smsSendDAO.putSmsSend(outGoingSMS);
						}
						sendSmS(gateway,africasTalking,outGoingSMS,accountId); 


						session.setAttribute(SessionConstants.SMS_SEND_SUCCESS, SMS_SEND_SUCCESS); 
						// count2++;
					}

				}

			}


		}else{//end if message is null
			//session - error message
			session.setAttribute(SessionConstants.SMS_SEND_ERROR, SMS_SEND_ERROR); 
		}

		response.sendRedirect("sendsms.jsp");  
		return; 

	}




	/**
	 * @param gateway
	 * @param africasTalking 
	 * @param outGoingSMS 
	 */
	private void sendSmS(AfricasTalkingGateway gateway, AfricasTalking africasTalking, OutGoingSMS outGoingSMS, String accountId) {
		String thestatus ="";
		String thenumber ="";
		String themessage ="";
		String thecost ="";
		try {
			JSONArray results = gateway.sendMessage(africasTalking.getRecipients(), africasTalking.getMessage());
			for( int i = 0; i < results.length(); ++i ) {
				JSONObject result = results.getJSONObject(i);

				thestatus = result.getString("status");
				thenumber = result.getString("number");
				themessage = message;
				thecost = result.getString("cost");

				if(outGoingSMS !=null){
					OutGoingSMS smsSend2 = smsSendDAO.getSmsSend(outGoingSMS.getUuid());
					smsSend2.setAccountId(accountId); 
					smsSend2.setStatus(thestatus);
					smsSend2.setMobile(thenumber);
					smsSend2.setMessage(themessage.replaceAll("[\r\n]+", " "));
					smsSend2.setSmsCost(thecost);
					smsSendDAO.updateSmsSend(smsSend2); 
					
					
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace(); 

		}

	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5115859501386056513L;

}
