/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package ke.co.qubintel.school.server.persistence.chat;

import java.util.List;

import ke.co.qubintel.school.server.bean.chat.Chat;

/**
 * Persistent implementation for {@link Chat} 
 * 
 * @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public interface SchoolChatDAO {
	/**
	 * Get the chat object for the given senderuuid and receiveruuid
	 * 
	 * @param senderId This is the sender ID
	 * @param receiverId This is the recipient ID
	 * @return the {@link Chat} Object
	 */
	public Chat getChat(String senderId,String receiverId);
	
	/**
	 * 
	 * @param senderuuid The sender ID
	 * @return A {@link List} of type {@link Chat} for the given sender ID
	 */
	public List<Chat> getChatList(String senderId,String receiverId);
	/**
	 * 
	 * @param chat The chat object
	 * @return Whether the {@link Chat} was inserted in the database successfully
	 */
	public boolean putChat(Chat chat);
	/**
	 * 
	 * @param chat The chat object
	 * @return Whether the {@link Chat} was deleted  successfully
	 */
	public boolean deleteChat(String senderId,String receiverId);

}
