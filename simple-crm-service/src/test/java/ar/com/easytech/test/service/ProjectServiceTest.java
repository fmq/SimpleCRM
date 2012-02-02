package ar.com.easytech.test.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ar.com.easytech.simplecrm.dao.BaseDAO;
import ar.com.easytech.simplecrm.dao.CustomerDAO;
import ar.com.easytech.simplecrm.dao.OpportunityDAO;
import ar.com.easytech.simplecrm.dao.ProjectDAO;
import ar.com.easytech.simplecrm.dao.ProposalDAO;
import ar.com.easytech.simplecrm.dao.SysDAO;
import ar.com.easytech.simplecrm.dao.SysSequenceHelper;
import ar.com.easytech.simplecrm.dao.XeroDAO;
import ar.com.easytech.simplecrm.model.ca.Address;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Emails;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.ca.Phone;
import ar.com.easytech.simplecrm.model.dto.AgedReceivablesRow;
import ar.com.easytech.simplecrm.model.pa.Milestone;
import ar.com.easytech.simplecrm.model.pa.Project;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.DaoFactory;
import ar.com.easytech.simplecrm.service.OpportunityService;
import ar.com.easytech.simplecrm.service.ProjectService;
import ar.com.easytech.simplecrm.service.SfaInfoService;
import ar.com.easytech.xero.integration.SingleClient;
import ar.com.easytech.xero.integration.XeroClientServices;
import ar.com.easytech.xero.integration.model.Invoice;
import ar.com.easytech.xero.integration.util.ParamBuilder;

/**
 *
 * @author FMQ
 */
@RunWith(Arquillian.class)
public class ProjectServiceTest {

   private static final Logger logger = Logger.getLogger(ProjectServiceTest.class.toString());
   
   private static String customerName = "Project Test Customer";
   private static String xeroId = new Long(new Random().nextLong()).toString();
   private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
   private long customerId = 0;
   
   @Inject
   CustomerService customerService;

   @Inject OpportunityService opportunityService;
   
   @Inject ProjectService projectService;
   
   @PersistenceContext(name="SimpleCRMTest")
   EntityManager em;
   
   @Deployment
   public static JavaArchive createTestArchive() {
      try {
         JavaArchive model = ShrinkWrap.create(ZipImporter.class, "model.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-model/1.0/simple-crm-model-1.0.jar"))
                 .as(JavaArchive.class);

         JavaArchive xero = ShrinkWrap.create(ZipImporter.class, "xero.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-httpclient4/20100601/oauth-httpclient4-20100601.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-provider/20100601/oauth-provider-20100601.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth/20100601/oauth-20100601.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-consumer/20100601/oauth-consumer-20100601.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/org/apache/httpcomponents/httpcore/4.0.1/httpcore-4.0.1.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/org/apache/httpcomponents/httpclient/4.0.1/httpclient-4.0.1.jar"))
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar"))
                 .as(JavaArchive.class);
         
         JavaArchive commons = ShrinkWrap.create(ZipImporter.class, "commons.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-common/1.0/simple-crm-common-1.0.jar"))
                 .as(JavaArchive.class);
         
         return ShrinkWrap.create(JavaArchive.class, "test.jar")
                 .addClasses(CustomerDAO.class,
                             ParamBuilder.class,
                             XeroDAO.class,
                             BaseDAO.class,
                             XeroClientServices.class,
                             SingleClient.class,
                             OpportunityDAO.class,
                             OpportunityService.class,
                             ProjectDAO.class, 
                             ProposalDAO.class,
                             OpportunityService.class,
							 SysSequenceHelper.class,
                             AgedReceivablesRow.class, 
                             CustomerService.class, 
                             DaoFactory.class,
                             SysDAO.class,
                             SfaInfoService.class,
                             ProjectService.class)
                 .merge(model)
                 .merge(commons)
                 .merge(xero)
                 .addPackage(Invoice.class.getPackage())
                 .addPackage(XeroClientServices.class.getPackage())
                 .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
                 .addAsManifestResource("test-persistence.xml", "persistence.xml");


      } catch (IOException ex) {
         logger.info(ex.getLocalizedMessage());
         ex.printStackTrace();
      }

      return null;
   }
   
   @Before
   public void init() {
      Assert.assertNotNull(customerService);
      Assert.assertNotNull(projectService);
   }
   
   @Test
   public void createCustomerTest() throws Exception {
      Customer customer = new Customer();
      Address address = new Address();
      address.setAddressLine1("Ramallo 2920");
      address.setType("PERSONAL");
      address.setCity("CABA");
      
      customer.addAddress(address);

      Phone phone = new Phone();
      phone.setAreaCode(11);
      phone.setCountryCode(54);
      phone.setPhoneNumber(52398899);
      customer.addPhone(phone);

      customer.addEmail(new Emails("facundo@easytech"));
      
      Person contact = new Person();
      contact.addAddress(address);
      contact.addEmail(new Emails("Facundo@gmail"));
      contact.addPhone(phone);
      contact.setLastName("Miquel");
      contact.setFirstName("Facundo");
      
      customer.setCustomerName(customerName);
      customer.setXeroId(xeroId);
      customer.addContact(contact);

      customerId = customerService.createCustomer(customer);
      logger.log(Level.INFO, "Customer id returned: {0}", customerId);
      Assert.assertNotNull(customerId);
   }
   
   @Test
   public void testCreateProject() throws Exception {
      Customer customer = customerService.findCustomerByXeroId(xeroId);
      Assert.assertNotNull(customer);
      
      Project project = new Project();
      project.setProjectName("Test Project");
      project.setProjectNumber(new Long(new Random().nextLong()).toString());
      project.setEstimatedHours(100);
      
      Milestone milestone = new Milestone();
      milestone.setName("Project Start");
      milestone.setPlannedStartDate(new Date());
      milestone.setPlannedEndDate(new Date());
      milestone.setExpectedDuration(0);
      project.addMilestone(milestone);
      
      milestone = new Milestone();
      milestone.setName("Project End");
      milestone.setPlannedStartDate(df.parse("25/03/2012"));
      milestone.setPlannedEndDate(df.parse("25/03/2012"));
      milestone.setExpectedDuration(0);
      project.addMilestone(milestone);
      
      milestone = new Milestone();
      milestone.setName("Other Milestone");
      milestone.setPlannedStartDate(df.parse("01/03/2012"));
      milestone.setPlannedEndDate(df.parse("01/03/2012"));
      milestone.setExpectedDuration(10);
      project.addMilestone(milestone);
      
      project.setCustomer(customer);
      project.setContacts(customer.getContacts());
      
      long id = projectService.createProject(project);
      Assert.assertNotNull(id);
   }
   
   @Test
   public void testFindAllProjects() throws Exception {
      
      List<Project> projects = projectService.getAllProjects();
      Assert.assertNotNull(projects);
      
      for (Project row : projects) {
    	 Project project = projectService.getProjectData(row.getId());
         List<Milestone> milestone = new ArrayList<Milestone>(project.getMilestones());
         Assert.assertNotNull(milestone);
         Assert.assertFalse(milestone.isEmpty());         
         Assert.assertNotNull(project.getNotes());
         Assert.assertNotNull(project.getContacts());
      }
   }
   
   @Test
   public void testGetProjectrCustomer() throws Exception {
      
      Customer customer = customerService.findCustomerByXeroId(xeroId);
      Assert.assertNotNull(customer);
      List<Project> projects = projectService.getProjectsForCustomerId(customer.getId());
      
      Assert.assertNotNull(projects);
      Assert.assertFalse(projects.isEmpty());
      Assert.assertTrue(projects.size()>0);
      
      for (Project row : projects) {
    	  
     	 Project project = projectService.getProjectData(row.getId());
         List<Milestone> milestone = new ArrayList<Milestone>(project.getMilestones());
         Assert.assertNotNull(milestone);
         Assert.assertFalse(milestone.isEmpty());
         
         Assert.assertNotNull(project.getNotes());
         Assert.assertNotNull(project.getContacts());
      }
   }
   
}
