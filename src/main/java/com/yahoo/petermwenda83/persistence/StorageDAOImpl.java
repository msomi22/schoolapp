package com.yahoo.petermwenda83.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.yahoo.petermwenda83.bean.StorableBean;
import com.yahoo.petermwenda83.bean.StorableBeanById;
import com.yahoo.petermwenda83.bean.StorableBeanByUUID;



public class StorageDAOImpl extends StorageDAO{

	private SessionFactory sessionFactory;
	
	/**
	 * Disable default constructor
	 */
	private StorageDAOImpl() {}
	
	
	/**
	 * @param sessionFactory
	 */
	public StorageDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	/**
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
	/**
	 * @see StorageDAO.tawi.smsgw2.persistence.Storage#save(mobi.tawi.smsgw2.beans.StorableBean)
	 */
	@Override
	public StorableBean save(StorableBean bean) {
		Session session = sessionFactory.openSession();
        
        session.beginTransaction();            
        session.saveOrUpdate(bean);           
        session.getTransaction().commit();
        
        session.close();
        		
		return bean; 	// In the case where the primary key is an Id (integer), this bean
						// has the updated Id from the database.
	}
	

	/**
	 * @see StorageDAO.tawi.smsgw2.persistence.Storage#get(java.lang.Class, java.lang.String)
	 */
	@Override
	public StorableBeanByUUID get(Class<?> aClass, String uuid) {
		Session session = sessionFactory.openSession();
		
		session.getTransaction().begin();
		
		StorableBeanByUUID bean = (StorableBeanByUUID) session.get(aClass, uuid);
				
		session.close();
				
		return bean;
	}

	
	/**
	 * @see StorageDAO.tawi.smsgw2.persistence.Storage#get(java.lang.Class, long)
	 */
	@Override
	public StorableBeanById get(Class<?> aClass, long id) {		
		Session session = sessionFactory.openSession();
		
		session.getTransaction().begin();
		
		StorableBeanById bean = (StorableBeanById) session.get(aClass, id);
				
		session.close();
		
		return bean;
	}
	

	/**
	 * @see StorageDAO.tawi.smsgw2.persistence.Storage#getAll(java.lang.Class)
	 */
	@Override
	public List<StorableBeanByUUID> getAll(Class aClass) {
		List<StorableBeanByUUID> objectList = null;
		
		Session session = sessionFactory.openSession();
				
		objectList = session.createQuery("from " +  aClass.getSimpleName(), aClass).list();
				
		session.close();
		
		return objectList;
	}

	
	/**
	 * @see StorageDAO.tawi.smsgw2.persistence.Storage#get(java.lang.Class, java.lang.Object, java.lang.Object)
	 */
	@Override
	public List<StorableBean> get(Class aClass, Object fieldName, Object fieldValue) {
		List<StorableBean> list = null;
		
		String hql = "from " + aClass.getSimpleName() + " where " + fieldName.toString() + " = :" + fieldName.toString();
		// Example of hql string: "from Account where username = :username"
		
		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery(hql);
		query.setParameter(fieldName.toString(), fieldValue);
		list = query.list();						
				
		session.close();
		
		return list;
	}

}
