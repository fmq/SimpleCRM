package ar.com.easytech.simplecrm.dao;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.sfa.Proposal;

import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author FMQ
 */
public class ProposalDAO extends BaseDAO<Proposal> {

   public ProposalDAO(EntityManager em) {
      super(Proposal.class, em);
   }
   
   public long createProposal(Proposal proposal) throws ServiceException {
      // Must have customer
      if (proposal.getCustomer() == null)
         throw new ServiceException("CUSTOMER_REQUIRED", "createProposal","Customer is required");
      
      // I Remove the Customer and Contacts from the opportunity
      Customer customer = proposal.getCustomer();
      proposal.setCustomer(null);
      Set<Person> contacts =  proposal.getContacts();
      proposal.setContacts(null);
      // We Persists
      insert(proposal);
      // Attach customer and contact again
      proposal.setCustomer(customer);
      proposal.setContacts(contacts);
      // Merge
      update(proposal);
      
      return proposal.getId();
   }
   
   
}
