
package com.yahoo.petermwenda83.test.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.yahoo.petermwenda83.bean.StorableBeanByUUID;
import com.yahoo.petermwenda83.bean.account.Account;
import com.yahoo.petermwenda83.bean.account.OutGoingSMS;
import com.yahoo.petermwenda83.bean.account.SmsApi;
import com.yahoo.petermwenda83.persistence.StorageDAO;
import com.yahoo.petermwenda83.persistence.StorageDAOImpl;


/**
 * Test our {@link StorageDAO}
 * <p>
 * 
 * 
 * 
 */
public class TestStorageImpl {
			
	private StorageDAO storageDAO;
	
	private SessionFactory sessionFactory;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		try {
            // Create the SessionFactory from hibernate.cfg.xml            
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        	
			storageDAO = new StorageDAOImpl(sessionFactory);
			
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}

	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		sessionFactory.close();
	}
	
	
	/**
	 * Test method for {@link StorageDAO.co.tawi.babblesms.server.persistence.Storage#get(Class, String)}.
	 */	
	@Ignore
	@Test
	public void testGetByUUID() {
			
		
		//*****************
		// Test Account
		//*****************
		String uuid = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";  // The DEMO account
		
		Account account = (Account) storageDAO.get(Account.class, uuid);
		
		assertEquals(account.getUsername(), "demo");
		assertEquals(account.getEmail(), "fastech@info.co.ke");
		
		System.out.println("Account is: " + account);		
	}

	
	/**
	 * Test method for {@link StorageDAO.co.tawi.babblesms.server.persistence.Storage#save(mobi.tawi.smsgw2.beans.StorableBean)}.
	 */
	@Ignore
	@Test
	public void testUPdate() {
		//***********************************
		// Test updating an existing Account
		//***********************************
		String uuid = "E3CDC578-37BA-4CDB-B150-DAB0409270CD";  // The DEMO account
		String newEmail = "update@info.co.ke";
		
		Account account = (Account)storageDAO.get(Account.class, uuid);	
		account.setEmail(newEmail);
		
		storageDAO.save(account);
		
		Account account2 = (Account)storageDAO.get(Account.class, uuid);
		assertEquals(account2.getEmail(), newEmail);		
	}
	
	
	/**
	 * Test method for {@link StorageDAO.co.tawi.babblesms.server.persistence.Storage#get(Class, String)}.
	 */
	@Ignore
	@Test
	public void testGetById() {	
		//*****************
		// Test OutgoingSMS
		//*****************	
		long id = 3l;
		
		OutGoingSMS outgoingSMS = (OutGoingSMS) storageDAO.get(OutGoingSMS.class, id);
		
		assertEquals(outgoingSMS.getMessage(), "HI peter, your new password is uOQdM");
		assertTrue(outgoingSMS.getAccount().getUuid().equals("E3CDC578-37BA-4CDB-B150-DAB0409270CD"));
		
		
		SmsApi smsApi = (SmsApi) storageDAO.get(SmsApi.class, id);
		assertEquals(smsApi.getApiKey(), "6B689FBB-9AE2-4DD2-9674-B21BD05C155B");
		
	}
	
	
	/**
	 * Test method for {@link StorageDAO.co.tawi.babblesms.server.persistence.Storage#get(Class, String)}.
	 */
	//@Ignore
	@Test
	public void testGetAll() {
			
		List<StorableBeanByUUID> list = storageDAO.getAll(Account.class);
		assertEquals(list.size(), 2);
		
		System.out.println("1st Account is " + list.get(0));
	}
	
	
	/**
	 * Test method for {@link StorageDAO.co.tawi.babblesms.server.persistence.Storage#get(Class, Object, Object)}.
	 */
	@Ignore
	@Test
	public void testGetList() {
		
		
	}
	
}
