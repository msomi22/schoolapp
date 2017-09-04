/**
 * 
 */
package com.yahoo.petermwenda83.server.api.rest;

import java.util.ArrayList;
import java.util.List;

import com.yahoo.petermwenda83.bean.classroom.Stream;
import com.yahoo.petermwenda83.persistence.classroom.StreamDAO;
import com.yahoo.petermwenda83.persistence.schoolaccount.AccountDAO;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiResponse;
import com.yahoo.petermwenda83.server.api.rest.bean.ApiStream;

/**
 * @author peter
 *
 */
public class GeneralService {

	private static StreamDAO streamDAO;
	private static AccountDAO accountDAO;

	static {
		streamDAO = StreamDAO.getInstance();
		accountDAO = AccountDAO.getInstance();
	}
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Object> getStreamList(String accountId) {

		if(streamDAO.getStreamList(accountId) != null) {
			
			List<Object>  list = new ArrayList<>();
			streamDAO.getStreamList(accountId).forEach(stream -> {
				ApiStream apiStream = new ApiStream();
				apiStream.setAccountId(stream.getUuid());
				apiStream.setClassRoomId(stream.getClassRoomId());
				apiStream.setDescription(stream.getDescription());
				apiStream.setUuid(stream.getUuid()); 
				
				list.add(apiStream);
			});

			return list;
			
		}else {
			
			List<Object>  response = new ArrayList<>();
			ApiResponse re = new ApiResponse();
			re.setMessage("error");
			re.setDescription("No stream to display.");
			
			response.add(re);
			return response;

		}

	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object getStream(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(streamDAO.getStream(accountId, uuid) != null) {
			
			ApiStream apiStream = new ApiStream();
			apiStream.setAccountId(streamDAO.getStream(accountId, uuid).getAccountId());
			apiStream.setClassRoomId(streamDAO.getStream(accountId, uuid).getClassRoomId());
			apiStream.setDescription(streamDAO.getStream(accountId, uuid).getDescription());
			apiStream.setUuid(streamDAO.getStream(accountId, uuid).getUuid());
			
			return apiStream; 
			
		}else {
			
			apiResponse.setMessage("error");
			apiResponse.setDescription("No stream to display.");
			
			return apiResponse; 
		}

		
	}

	/**
	 * 
	 * @param apiStream
	 * @return
	 */
	public Object putStream(ApiStream apiStream) {

		ApiResponse apiResponse = new ApiResponse();
		
		if(accountDAO.getAccountById(apiStream.getAccountId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;
			
		}else if(streamDAO.getStreamByDesc(apiStream.getAccountId(), apiStream.getDescription()) != null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("A stream with such a name already exist! '" + apiStream.getDescription() + "'");  
			return apiResponse;
			
		}else {
			Stream stream = new Stream();
			stream.setAccountId(apiStream.getAccountId());
			stream.setClassRoomId(apiStream.getClassRoomId());
			stream.setDescription(apiStream.getDescription());
			
			if(streamDAO.putStream(stream)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream added successfully.");
				
			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Try again later or contact Admin.");
				
			}
			
		}
		
		return apiResponse;
	}
	/**
	 * 
	 * @param apiStream
	 * @return
	 */
	public Object updateStream(ApiStream apiStream) {

		ApiResponse apiResponse = new ApiResponse();
		
		if(accountDAO.getAccountById(apiStream.getAccountId()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Account not found!");
			return apiResponse;
			
		}else if(streamDAO.getStream(apiStream.getAccountId(), apiStream.getUuid()) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Stream not found!");
			return apiResponse;
			
		}else {
			
			Stream stream = streamDAO.getStream(apiStream.getAccountId(), apiStream.getUuid());
			stream.setClassRoomId(apiStream.getClassRoomId());
			stream.setDescription(apiStream.getDescription());
			
			if(streamDAO.updateStream(stream)) { 
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream updated successfully.");
				
			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Try again later or contact Admin.");
				
			}

			
		}
		
		
		return apiResponse;
	}

	/**
	 * 
	 * @param accountId
	 * @param uuid
	 * @return
	 */
	public Object deleteStream(String accountId, String uuid) {

		ApiResponse apiResponse = new ApiResponse();

		if(streamDAO.getStream(accountId, uuid) == null) {
			apiResponse.setMessage("error");
			apiResponse.setDescription("Invalid account/Stream Id(s)!");  
			return apiResponse;

		}else {

			if(streamDAO.deleteStream(accountId, uuid)) {
				apiResponse.setMessage("success");
				apiResponse.setDescription("Stream removed successfully!");  

			}else {
				apiResponse.setMessage("error");
				apiResponse.setDescription("Something went wrong, contact Admin or try again later."); 
			}

		}



		return apiResponse;
	}



}
