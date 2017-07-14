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
	public Chat getChat(String senderId,String receiverId) {
		Chat chat = null;
        ResultSet rset = null;
        try(
        		  Connection conn = dbutils.getConnection();
           	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Chat WHERE senderId = ? AND receiverId =? ;");       
        		
        		){
        	
        	 pstmt.setString(1, senderId);
        	 pstmt.setString(2, receiverId);
	         rset = pstmt.executeQuery();
	     while(rset.next()){
	
	    	 chat  = beanProcessor.toBean(rset,Chat.class);
	   }
        	
        }catch(SQLException e){
        	 logger.error("SQL Exception when getting Chat for senderId " + senderId + " and receiverId " + receiverId);
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
			        		+"(uuid,accountId,senderId,receiverId,message,isRead,dateSent) VALUES (?,?,?,?,?,?,?);");
		             ){
			   
	            pstmt.setString(1, chat.getUuid());
	            pstmt.setString(2, chat.getAccountId());
	            pstmt.setString(3, chat.getSenderId());	  
	            pstmt.setString(4, chat.getReceiverId());
	            pstmt.setString(5, chat.getMessage());	  
	            pstmt.setString(6, chat.getIsRead());	
	            pstmt.setTimestamp(7, new Timestamp(chat.getDateSent().getTime()));
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
	public boolean deleteChat(String senderId,String receiverId) {
		boolean success = true; 
	      try(
	      		  Connection conn = dbutils.getConnection();
	         	  PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Chat"
	         	      		+ " WHERE senderId = ? AND receiverId =?;");       
	      		
	      		){
	      	
	      	     pstmt.setString(1, senderId);
	      	     pstmt.setString(2, receiverId);
		         pstmt.executeUpdate();
		     
	      }catch(SQLException e){
	      	   logger.error("SQL Exception when deletting Chat senderId " + senderId + " receiverId " + receiverId);
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
	public List<Chat> getChatList(String senderId,String receiverId) {
		List<Chat> list = null;
        try (
        		 Connection conn = dbutils.getConnection();
     	         PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Chat WHERE (senderId = ? AND receiverId =?) OR "
     	         		+ "(receiverId = ? AND senderId =?) ;");    		   
     	   ) {
         	   pstmt.setString(1, senderId);    
         	   pstmt.setString(2, receiverId); 
         	   pstmt.setString(3, receiverId);    
        	   pstmt.setString(4, senderId); 
         	   try( ResultSet rset = pstmt.executeQuery();){
     	       
     	       list = beanProcessor.toBeanList(rset, Chat.class);
         	   }
        } catch (SQLException e) {
            logger.error("SQLException when getting Chat List for senderId " + senderId + " and receiverId " + receiverId); 
            logger.error(ExceptionUtils.getStackTrace(e));
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        return list;
	
	}

	

}
