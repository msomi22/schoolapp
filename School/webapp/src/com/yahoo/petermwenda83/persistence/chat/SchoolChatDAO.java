/**
 * Copy Right 2016. FasTech Solutions Ltd.
 * 
 * Licensed under the Open Software License, Version 3.0 (the “License”); you may
 * not use this file except in compliance with the License. You may obtain a copy
 * of the License at:
 * http://opensource.org/licenses/OSL-3.0
 * 
 */
package com.yahoo.petermwenda83.persistence.chat;

import java.util.List;

import com.yahoo.petermwenda83.bean.chat.Chat;

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
	 * @param senderuuid This is the sender ID
	 * @param receiveruuid This is the recipient ID
	 * @return the {@link Chat} Object
	 */
	public Chat getChat(String senderuuid,String receiveruuid);
	
	/**
	 * 
	 * @param chat The chat object
	 * @return A List of type {@link Chat} 
	 */
	 
	public List<Chat> getChatList(Chat chat);
	/**
	 * 
	 * @param senderuuid The sender ID
	 * @return A {@link List} of type {@link Chat} for the given sender ID
	 */
	public List<Chat> getChatList(String senderuuid);
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
	public boolean deleteChat(Chat chat);

}
