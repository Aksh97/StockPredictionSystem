package com.nus.stock.dao.basic;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.nus.stock.util.Logger4j;


public class DaoBasicService implements BasicDao
{
    private static final Logger log = Logger4j.getLogger(DaoBasicService.class);
    
    private HibernateTemplate hibernateTemplate;
    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }
    
    private HibernateTemplate getHibernateTemplate()
    {
        if(hibernateTemplate == null)
        {
            hibernateTemplate =  new HibernateTemplate(sessionFactory);
        }
        return hibernateTemplate;
    }
    
    /**
     * Creates an object in the database.
     * @param obj the object to create.
     * @return the created object.
     */
    public Serializable create(Object obj)
    {
    	try {
    		return getHibernateTemplate().save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return 0;
    }

    /**
     * Creates objects in the database.
     * @param obj the objects to create.
     * @return the created objects.
     */
    public Object[] createAll(Object[] objs)
    {
    	try {
    		if (objs != null)
    		{
    			for (int n = 0; n < objs.length; n++)
    				create(objs[n]);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return objs;
    }

    /**
     * Updates an object in the database.
     * @param obj the object to update.
     * @return the updated object.
     */
    public Object update(Object obj)
    {
    	try {
    		if (obj != null && log.isDebugEnabled())
    		{
    			log.debug("Want to update an object[className:" + obj.getClass().getName() + "; toString:" + obj.toString() + "]");
    		}
    		getHibernateTemplate().update(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return obj;
    }

    /**
     * Updates objects in the database.
     * @param obj the objects to update.
     * @return the updated objects.
     */
    public Object[] updateAll(Object[] objs)
    {
    	try {
    		if (objs != null)
    		{
    			for (int n = 0; n < objs.length; n++)
    				update(objs[n]);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return objs;
    }

    /**
     * Deletes an object from the database.
     * @param obj the object to delete.
     */
    public void delete(Object obj)
    {
    	try {
    		if (obj != null)
    			getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Deletes objects from the database.
     * @param obj the objects to delete.
     */
    public void deleteAll(Object[] objs)
    {
    	try {
    		if (objs != null)
    		{
    			for (int n = 0; n < objs.length; n++)
    				delete(objs[n]);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Delete an object from the database
     * @param clazz
     * @param id
     */
    public void delete(Class<?> clazz, Integer id)
    {
    	try {
    		Object obj = getHibernateTemplate().load(clazz, id);
    		getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void delete(Class<?> clazz, String id)
    {
    	try {
			Object obj = getHibernateTemplate().load(clazz, id);
    		getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void delete(Class<?> clazz, Object obj)
    {
    	try {
    		getHibernateTemplate().delete(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Finds an object from the database using the primary key.
     * @param clazz the object class.
     * @param id the primary key ID.
     * @return the object.
     */
	public Object get(Class<?> clazz,Integer id)
    {
    	try {
    		return getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
	/**
     * Finds an object from the database using the primary key.
     * @param clazz the object class.
     * @param id the primary key ID.
     * @return the object.
     */
	public Object get(Class<?> clazz,String id)
    {
    	try {
    		return getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * Find all objects from database.
     * @param queryName is the query string.
     * @return List.
     */
    public List<?> query(String queryName)
    {
    	try {
    		return getHibernateTemplate().findByNamedQuery(queryName);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    

    /**
     * 
     * @param queryName
     * @param values
     * @return
     */
    public List<?> query(String queryName, Object[] values)
    {
    	try {
    		return getHibernateTemplate().findByNamedQuery(queryName, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    
}
