package ar.com.easytech.simplecrm.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Root;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.enums.StatusTypes;

/**
 *
 * @author FMQ
 * @version 1.0
 */
public class CustomerDAO extends BaseDAO<Customer> {

   public CustomerDAO(EntityManager em) {
      super(Customer.class,em);
   }

   public long createCustomer(Customer customer) throws ServiceException {
      try {
    	  // Get next customer number
    	  SysSequenceHelper helper = new SysSequenceHelper();
    	  long customerNumber = helper.getNextValue("customerNumber", em);
    	  customer.setCustomerNumber(customerNumber);
    	  // Set the customer number if it's null
    	  insert(customer);
      } catch (Exception _ex) {
    	  throw new ServiceException(_ex.getClass().getSimpleName(),"createCustomer",_ex.getLocalizedMessage());
      }
      return customer.getId();
   }

   public List<Customer> findByName(String name) {
	   
      queryBuilder = em.getCriteriaBuilder();
      query = queryBuilder.createQuery();
      Root<Customer> customer = query.from(Customer.class);
      query.select(customer);
      
      query.where(queryBuilder.and(queryBuilder.equal(customer.get("recordStatus"), StatusTypes.ACTIVE),
              queryBuilder.like(customer.<String>get("customerName"), name)));
      TypedQuery<Customer> q = em.createQuery(query);
      return q.getResultList();
   }

}
