/**
 * 
 */
package com.yahoo.petermwenda83.persistence.chat;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.chat.Chat;

/**
 * @author peter
 *
 */
public class TestChatDAO {
	
	final String databaseName = "schooldb";
	final String Host = "localhost";
	final String databaseUsername = "school";
	final String databasePassword = "AllaManO1";
	final int databasePort = 5432;
	
	final String SENDER_ID = "F49DB775-4952-4915-B978-9D9F3E36D6E9";//peter
	final String RECEIVER_ID = "0AFC69BF-408E-4E9F-9726-E0A2B53089F4";//diquin
	
	private ChatDAO store;

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.chat.ChatDAO#getChat(java.lang.String, java.lang.String)}.
	 */
	@Ignore
	@Test
	public final void testGetChat() {
		store = new ChatDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Chat chat = new Chat();
		chat = store.getChat(SENDER_ID, RECEIVER_ID);
		assertEquals(chat.getUuid(),"6cef2efd-a7e2-4065-b9a9-0279d69c4a02");
		/*assertEquals(chat.getSenderUuid(),SENDER_ID);
		assertEquals(chat.getReceiverUuid(),RECEIVER_ID);
		assertEquals(chat.getMessage(),"Hi Diquin");
		assertEquals(chat.getMgsStatus(),"Sent");*/
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.chat.ChatDAO#putChat(com.yahoo.petermwenda83.bean.chat.Chat)}.
	 */
	@Ignore
	@Test
	public final void testPutChat() {
		store = new ChatDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Chat chat = new Chat();
		/*chat.setSenderUuid(SENDER_ID);
		chat.setReceiverUuid(RECEIVER_ID);
		chat.setMessage("Hi Diquin Mwende");
		chat.setDateSent(new Date());
		chat.setMgsStatus("Sent"); //sent, received etc 
*/        store.putChat(chat);
	}

	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.chat.ChatDAO#deleteChat(com.yahoo.petermwenda83.bean.chat.Chat)}.
	 */
	@Ignore
	@Test
	public final void testDeleteChat() {
		store = new ChatDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Chat chat = new Chat();
		/*chat.setSenderUuid(SENDER_ID);
		chat.setReceiverUuid(RECEIVER_ID);
		store.deleteChat(chat);*/
	}
	
	/**
	 * Test method for {@link com.yahoo.petermwenda83.persistence.chat.ChatDAO#deleteChat(com.yahoo.petermwenda83.bean.chat.Chat)}.
	 */
	@Ignore
	@Test
	public final void testGetChatList() {
		store = new ChatDAO(databaseName, Host, databaseUsername, databasePassword, databasePort);
		Chat chat = new Chat();
		/*chat.setSenderUuid(SENDER_ID);
		chat.setReceiverUuid(RECEIVER_ID);
		List<Chat> list = store.getChatList(chat);
		for (Chat cht : list) {
			System.out.println(cht); 
		}*/
	}

}
