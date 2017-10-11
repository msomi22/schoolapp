/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.staff.ClassTeacher;
import com.yahoo.petermwenda83.bean.staff.Staff;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.persistence.staff.ClassTeacherDAO;
import com.yahoo.petermwenda83.persistence.staff.StaffDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiClassTeacher;
import com.yahoo.petermwenda83.server.api.rest.bean.Response;

/**
 * @author peter
 *
 */
public class ClassTeacherService {
	
	private static ClassTeacherDAO classTeacherDAO;
	private static StaffDAO staffDAO;
	private static StreamDAO streamDAO;
	private static AccountDAO accountDAO;

	static {
		classTeacherDAO = ClassTeacherDAO.getInstance();
		staffDAO = StaffDAO.getInstance();
		streamDAO = StreamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
	}

	
	/**
	 * 
	 * @param accountId
	 * @param staffId
	 * @return
	 */
	public Object getClassTeacher(String accountId, String staffId) {
		
		Response response = new Response();
		
		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacher(accountId, staffId) == null) {
			response.setMessage("error");
			response.setDescription("Class Teacher Info not found!");
			return response; 
			
		}else if(staffDAO.getStaff(accountId, staffId) == null) {
			response.setMessage("error");
			response.setDescription("Staff Info not found!");
			return response; 
			
		}else if(streamDAO.getStream(accountId, classTeacherDAO.getClassTeacher(accountId, staffId).getStreamId()) == null) {
			response.setMessage("error");
			response.setDescription("Stream Info not found!");
			return response; 
			
		}else {
			
			Staff staff = staffDAO.getStaff(accountId, staffId);
			ApiClassTeacher object = new ApiClassTeacher();
			object.setAccountId(accountId);
			object.setStaffId(staffId);
			object.setStaffName(staff.getFirstname() + " " + staff.getMiddlename() + " " + staff.getLastname());
			object.setStaffNo(staff.getStaffNo()); 
			object.setUuid(classTeacherDAO.getClassTeacher(accountId, staffId).getUuid());  
			
			object.setStreamId(classTeacherDAO.getClassTeacher(accountId, staffId).getStreamId()); 
			object.setStreamDesc(streamDAO.getStream(accountId, classTeacherDAO.getClassTeacher(accountId, staffId).getStreamId()).getDescription()); 
			
			
			return object;
			
		}

	}
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public Object getClassTeachers(String accountId) {
		
		Response response = new Response();
		
		if(accountDAO.getAccountById(accountId) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacherList(accountId).isEmpty()) {
			
			response.setMessage("error");
			response.setDescription("Class Teacher Info not found!");
			return response; 
			
		}else {
			
			List<ApiClassTeacher> apiClassTeacherList = new ArrayList<>();
			
			classTeacherDAO.getClassTeacherList(accountId).parallelStream().forEach(classteacher -> {
				
				Staff staff = staffDAO.getStaff(accountId, classteacher.getTeacherId());
				
				ApiClassTeacher object = new ApiClassTeacher();
				object.setAccountId(accountId);
				object.setStaffId(classteacher.getTeacherId());
				object.setStaffName(staff.getFirstname() + " " + staff.getMiddlename() + " " + staff.getLastname());
				object.setStaffNo(staff.getStaffNo()); 
				object.setUuid(classTeacherDAO.getClassTeacher(accountId, classteacher.getTeacherId()).getUuid());  
				
				object.setStreamId(classTeacherDAO.getClassTeacher(accountId, classteacher.getTeacherId()).getStreamId()); 
				object.setStreamDesc(streamDAO.getStream(accountId, classTeacherDAO.getClassTeacher(accountId, classteacher.getTeacherId()).getStreamId()).getDescription()); 
				
				apiClassTeacherList.add(object);
				
			});	
			
			
			return apiClassTeacherList; 
			
		}
		
		
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public Object addClassTeacher(ApiClassTeacher obj) {
		
		Response response = new Response();
		
		if(accountDAO.getAccountById(obj.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacher(obj.getAccountId(), obj.getStaffId()) != null) {
			response.setMessage("error");
			response.setDescription("The Staff is already assigned a Class!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacher(obj.getAccountId(), obj.getStreamId()) != null) {
			response.setMessage("error");
			response.setDescription("The Class is already assigned a Teacher!"); 
			return response; 
			
		}else if(staffDAO.getStaff(obj.getAccountId(), obj.getStaffId()) == null) {
			response.setMessage("error");
			response.setDescription("Staff not found!");
			return response; 
			
		}else if(streamDAO.getStream(obj.getAccountId(), obj.getStreamId()) == null) { 
			response.setMessage("error");
			response.setDescription("Stream not found!");
			return response; 
			
		}else {
			
			ClassTeacher  classTeacher = new ClassTeacher();
			classTeacher.setAccountId(obj.getAccountId());
			classTeacher.setTeacherId(obj.getStaffId());
			classTeacher.setStreamId(obj.getStreamId());
			
			if(classTeacherDAO.putClassTeacher(classTeacher)) {
				response.setMessage("sucess");
				response.setDescription("Info saved successfully!"); 
				return response;
				
			}else {
				
				response.setMessage("error");
				response.setDescription("Unexpected error occured!");
				return response;
				
			}
			
		}
	
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public Object updateClassTeacher(ApiClassTeacher obj) {
		
		Response response = new Response();
		
		if(accountDAO.getAccountById(obj.getAccountId()) == null) {
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 
			
		}else if(staffDAO.getStaff(obj.getAccountId(), obj.getStaffId()) == null) {
			response.setMessage("error");
			response.setDescription("Staff not found!");
			return response; 
			
		}else if(streamDAO.getStream(obj.getAccountId(), obj.getStreamId()) == null) { 
			response.setMessage("error");
			response.setDescription("Stream not found!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacher(obj.getAccountId(), obj.getStaffId()) == null) {
			response.setMessage("error");
			response.setDescription("Nothing to update!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacher(obj.getAccountId(), obj.getStreamId()) == null) {
			response.setMessage("error");
			response.setDescription("Nothing to update!");
			return response; 
			
		}else if(hasDuplicate(obj.getAccountId(),obj.getStaffId())) {
			response.setMessage("error");
			response.setDescription("duplicates Staff not permitted!"); 
			return response; 
			
		}else if(hasDuplicate(obj.getAccountId(),obj.getStreamId())) {
			response.setMessage("error");
			response.setDescription("duplicates Stream not permitted!"); 
			return response; 
			
		}else {
			
			ClassTeacher  classTeacher = classTeacherDAO.getClassTeacher(obj.getAccountId(), obj.getStaffId());
			classTeacher.setStreamId(obj.getStreamId()); 
			
			if(classTeacherDAO.updateClassTeacher(classTeacher)) {
				
				response.setMessage("sucess");
				response.setDescription("Info updated successfully!"); 
				return response;
				
			}else {
				
				response.setMessage("error");
				response.setDescription("Unexpected error occured!");
				return response;
				
			}
			
		}
	
	}
	
	

	/**
	 * 
	 * @param accountId
	 * @param staffId
	 * @return
	 */
	public Object deleteClassTeacher(String accountId, String uuid) {
		
		Response response = new Response();
		
		if(accountDAO.getAccountById(accountId) == null) { 
			response.setMessage("error");
			response.setDescription("Account not found!");
			return response; 
			
		}else if(classTeacherDAO.getClassTeacherById(accountId, uuid) == null) {
			response.setMessage("error");
			response.setDescription("Class Teacher Info not found!");
			return response;
			
		}else {
			
			if(classTeacherDAO.deleteClassTeacher(accountId, uuid)) {
				
				response.setMessage("sucess");
				response.setDescription("Info Deleted successfully!"); 
				return response;
				
			}else {
				
				response.setMessage("error");
				response.setDescription("Unexpected error occured!");
				return response;
				
			}
			
		}
		
	}
	
	
	/**
	 * @param obj
	 * @return
	 */
	private boolean hasDuplicate(String accountId, String id) { 
		List<ClassTeacher> list = new ArrayList<>();
		//if not record with such a key, return true and proceed

		if(classTeacherDAO.getClassTeacherList(accountId, id) == null) { 
			return false;
		}else {
			list = classTeacherDAO.getClassTeacherList(accountId, id);  
		
			if(list.size() == 1) {
				return false;

			}else if(list.size() > 1) {

				return true;

			}else if(list.size() == 0) {
				return false;

			}else {
				return false;

			}
		}
	}
	
	
	
}
