/**
 * 
 */
package com.yahoo.petermwenda83.persistence.chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.yahoo.petermwenda83.bean.chat.Chat;
import com.yahoo.petermwenda83.persistence.GenericDAO;

/** 
 * @author peter
 *
 */
public class ChatDAO extends GenericDAO implements SchoolChatDAO {

	private static ChatDAO chatDAO;
	private Logger logger = Logger.getLogger(this.getClass());
	private BeanProcessor beanProcessor = new BeanProcessor();
	/**
	 * 
	 * @return
	 */
	
	public static ChatDAO getInstance(){
		
		if(chatDAO == null){
			chatDAO = new ChatDAO();		
		}
		return chatDAO;
	}
	
	/**  
	 * 
	 */
	public ChatDAO() { 
		super();
	}
	
	/**
	 * 
	 */
	public ChatDAO(String databaseName, String Host, String databaseUsername, String databasePassword, int databasePort) {
		super(databaseName, Host, databaseUsername, databasePassword, databasePort);
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.chat.SchoolChatDAO#getChat(java.lang.String, java.lang.String)
	 */
	@Override
	public Chat getChat(String senderUuid, String receiverUuid) {
		Chat chat = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Chat WHERE senderUuid = ? AND receiverUuid =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, senderUuid);
        	 pstmt.setString(2, receiverUuid);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 chat  = beanProcessor.toBean(rset,Chat.class);
	   }
        	
        	
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Chat for senderID : " + senderUuid +" and receiverID"+ receiverUuid);
             logger.error(ExceptionUtils.getStackTrace(e));
             System.out.println(ExceptionUtils.getStackTrace(e));
        }
		return chat; 
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.chat.SchoolChatDAO#putChat(com.yahoo.petermwenda83.bean.chat.Chat)
	 */
	@Override
	public boolean putChat(Chat chat) {
		boolean success = true;
		
		  try(   Connection conn = dbutils.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Chat" 
			        		+"(Uuid,SenderUuid,ReceiverUuid,Message,DateSent,MgsStatus) VALUES (?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, chat.getUuid());
	            pstmt.setString(2, chat.getSenderUuid());
	            pstmt.setString(3, chat.getReceiverUuid());	  
	            pstmt.setString(4, chat.getMessage());	  
	            pstmt.setTimestamp(5, new Timestamp(chat.getDateSent().getTime()));
	            pstmt.setString(6, chat.getMgsStatus());	  
	            pstmt.executeUpdate();
			 
		 }catch(SQLException e){
		   logger.error("SQL Exception trying to put Chat " + chat);
           logger.error(ExceptionUtils.getStackTrace(e)); 
           System.out.println(ExceptionUtils.getStackTrace(e));
           success = false;
		 }
		
		return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.chat.SchoolChatDAO#deleteChat(com.yahoo.petermwenda83.bean.chat.Chat)
	 */
	@Override
	public boolean deleteChat(Chat chat) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Chat"
	         	      		+ " WHERE senderUuid = ? AND receiverUuid =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, chat.getSenderUuid());
	      	     pstmt.setString(2, chat.getReceiverUuid());
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting Chat : " +chat);
	           logger.error(ExceptionUtils.getStackTrace(e));
	           System.out.println(ExceptionUtils.getStackTrace(e));
	           success = false;
	           
	      }
	      
			return success;
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.chat.SchoolChatDAO#getChatList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Chat> getChatList(Chat chat) {
		List<Chat> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Chat WHERE senderUuid = ? AND receiverUuid =? OR senderUuid = ? AND receiverUuid =?;");    		   
     	   ) {
         	   pstmt.setString(1, chat.getSenderUuid());    
         	   pstmt.setString(2, chat.getReceiverUuid()); 
         	   pstmt.setString(3, chat.getReceiverUuid());    
        	   pstmt.setString(4, chat.getSenderUuid()); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Chat.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Chat List for Chat " + chat ); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	
	}

	/**
	 * @see com.yahoo.petermwenda83.persistence.chat.SchoolChatDAO#getChatList(java.lang.String)
	 */
	@Override
	public List<Chat> getChatList(String senderUuid) {
		List<Chat> list = null;
     
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Chat WHERE senderUuid = ?;");    		   
     	   ) {
         	   pstmt.setString(1, senderUuid);    
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Chat.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Chat List for senderID " + senderUuid); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	
	}

}
