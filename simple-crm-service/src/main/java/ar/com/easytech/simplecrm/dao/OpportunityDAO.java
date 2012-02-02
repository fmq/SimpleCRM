package ar.com.easytech.simplecrm.dao;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;

import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author FMQ
 */
public class OpportunityDAO extends BaseDAO<Opportunity> {

   public OpportunityDAO(EntityManager em) {
      super(Opportunity.class,em);
   }
   
   public long createOpportunity(Opportunity opportunity) throws ServiceException {
      // Must have customer
      if (opportunity.getCustomer() == null)
         throw new ServiceException("CUSTOMER_REQUIRED", "createOpportunity","Customer is required");
      
	  // Get next Opportunity number
	  long opportunityNumber = SysSequenceHelper.getNextValue("opportunityNumber", em);
	  opportunity.setOpportunityNumber(opportunityNumber);
      
      // I Remove the Customer and Contacts from the opportunity
      Customer customer = opportunity.getCustomer();
      
      opportunity.setCustomer(null);
      Set<Person> contacts =  opportunity.getContacts();
      opportunity.setContacts(null);
      // We Persists
      insert(opportunity);
      // Attach customer and contact again
      opportunity.setCustomer(customer);
      if (contacts != null)
    	  opportunity.setContacts(contacts);
      // Merge
      update(opportunity);
      
      return opportunity.getId();
   }
}
