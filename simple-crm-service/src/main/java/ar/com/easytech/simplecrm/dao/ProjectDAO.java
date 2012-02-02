package ar.com.easytech.simplecrm.dao;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.pa.Project;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author FMQ
 */
public class ProjectDAO extends BaseDAO<Project> {

   public ProjectDAO(EntityManager em) {
      super(Project.class, em);
   }
   
   public long createProject(Project project) throws ServiceException {
      // Must have customer
      if (project.getCustomer() == null)
         throw new ServiceException("CUSTOMER_REQUIRED", "createProject","Customer is required");
      
      // I Remove the Customer and Contacts from the opportunity
      Customer customer = project.getCustomer();
      project.setCustomer(null);
      Set<Person> contacts =  project.getContacts();
      project.setContacts(null);
      // We Persists
      insert(project);
      // Attach customer and contact again
      project.setCustomer(customer);
      project.setContacts(contacts);
      // Merge
      update(project);
      
      return project.getId();
   }
   
}
