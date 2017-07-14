package com.yahoo.petermwenda83.persistence;

import java.util.List;

import com.yahoo.petermwenda83.bean.StorableBean;
import com.yahoo.petermwenda83.bean.StorableBeanById;
import com.yahoo.petermwenda83.bean.StorableBeanByUUID;


public abstract class StorageDAO {

	/**
	 * This can both save new objects, as well as update objects if a bean with
	 * a matching primary key is present in the database.
	 * 
	 * @param bean
	 * @return
	 */
	public abstract StorableBean save(StorableBean bean);
	
	/**
	 * 
	 * @param aClass
	 * @param uuid
	 * @return
	 */
	public abstract StorableBeanByUUID get(Class<?> aClass, String uuid);
	
	/**
	 * 
	 * @param aClass
	 * @param id
	 * @return
	 */
	public abstract StorableBeanById get(Class<?> aClass, long id);
	
	
	/**
	 * It is safe to get all objects whose primary key is an uuid because they
	 * are not intended to be many.
	 * 
	 * @param aClass
	 * @return a list of all objects in persistence
	 */
	public abstract List<StorableBeanByUUID> getAll(Class<?> aClass);
	
	
	/**
	 * Select a set of objects from persistence which has a certain fieldName (column) and whose
	 * value is fieldValue
	 * 
	 * @param aClass
	 * @param fieldName
	 * @param fieldValue
	 * @return a matching list of all objects in persistence
	 */
	public abstract List<StorableBean> get(Class<?> aClass, Object fieldName, Object fieldValue);
}
