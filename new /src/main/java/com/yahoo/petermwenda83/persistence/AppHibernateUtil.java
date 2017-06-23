/**
 * 
 */
package com.yahoo.petermwenda83.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/** 
 * @author peter
 *
 */

public class AppHibernateUtil {
	

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            sessionFactory =  new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                   
        } catch (Throwable ex) {
        	
            System.err.println("Initial SessionFactory creation failed." + ex);
            
            throw new ExceptionInInitializerError(ex); 
        }
    }

    /**
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    /**
     * @return
     */
    public static Session openSession() {
        return sessionFactory.openSession();
    }
    
}