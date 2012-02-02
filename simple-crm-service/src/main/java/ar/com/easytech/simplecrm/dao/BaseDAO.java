package ar.com.easytech.simplecrm.dao;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.enums.StatusTypes;

/**
 *
 * @author FMQ
 */
public class BaseDAO<T extends AbstractEntity> {

   
   EntityManager em;
   
   protected Class<T> clazz;
   protected CriteriaQuery query;
   protected CriteriaBuilder queryBuilder;

   public BaseDAO(Class<T> clazz, EntityManager em) {
      this.clazz = clazz;
      this.em = em;
   }
   
   public long insert(T object) {
      em.persist(object);
      em.flush();
      return object.getId();
   }

   public void update(T object) {
      em.merge(object);
   }

   public void delete(T object) {
      object.setRecordStatus(StatusTypes.DELETED);
      em.merge(object);
   }

   public List<T> getAll() throws ServiceException {

      queryBuilder = em.getCriteriaBuilder();
      query = queryBuilder.createQuery();

      Root<T> object = query.from(clazz);
      query.select(object);
      query.where(queryBuilder.equal(object.get("recordStatus"), StatusTypes.ACTIVE));
      try {
	      TypedQuery<T> q = em.createQuery(query);
	      return q.getResultList();
      } catch (NoResultException _ex) {
    	  throw new ServiceException("NO_DATA_FOUND","getAll","No data found");
      }
   }

   public T getById(long entityId) throws ServiceException {

      queryBuilder = em.getCriteriaBuilder();
      query = queryBuilder.createQuery();
      Root<T> object = query.from(clazz);
      query.select(object);
      query.where(queryBuilder.and(queryBuilder.equal(object.get("recordStatus"), StatusTypes.ACTIVE),
              queryBuilder.equal(object.get("id"), entityId)));
      try {
	      TypedQuery<T> q = em.createQuery(query);
	      return q.getSingleResult();
      } catch (NoResultException _ex) {
    	  throw new ServiceException("NO_DATA_FOUND","getById","No data found");
      }
   }

   public List<T> getForCustomerId(long entityId) throws ServiceException {

      queryBuilder = em.getCriteriaBuilder();
      query = queryBuilder.createQuery();
      Root<T> object = query.from(clazz);
      query.select(object);
      query.where(queryBuilder.and(queryBuilder.equal(object.get("recordStatus"), StatusTypes.ACTIVE),
              queryBuilder.equal(object.get("customer"), entityId)));
      try {
	      TypedQuery<T> q = em.createQuery(query);
	      return q.getResultList();
      } catch (NoResultException _ex) {
    	  throw new ServiceException("NO_DATA_FOUND","getForCustomerId","No data found");
      }
   }
   
   public List<T> findByExactProperties(Map<String, Object> params) throws ServiceException {
	      queryBuilder = em.getCriteriaBuilder();
	      query = queryBuilder.createQuery();
	      Root<T> object = query.from(clazz);
	      query.select(object);
	      query.where(queryBuilder.equal(object.get("recordStatus"), StatusTypes.ACTIVE));
	      for (String key: params.keySet()) {
	    	  query.where(queryBuilder.and(queryBuilder.equal(object.get(key), params.get(key))));
	      }
	      try {
	    	  
		      TypedQuery<T> q = em.createQuery(query);
		      return q.getResultList();
	      } catch (NoResultException _ex) {
	    	  throw new ServiceException("NO_DATA_FOUND","findByExactProperties","No data found");
	      }
   }
   
}
