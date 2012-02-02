package ar.com.easytech.test.service;

import ar.com.easytech.simplecrm.dao.BaseDAO;
import ar.com.easytech.simplecrm.dao.CustomerDAO;
import ar.com.easytech.simplecrm.dao.OpportunityDAO;
import ar.com.easytech.simplecrm.dao.ProjectDAO;
import ar.com.easytech.simplecrm.dao.ProposalDAO;
import ar.com.easytech.simplecrm.dao.SysDAO;
import ar.com.easytech.simplecrm.dao.SysSequenceHelper;
import ar.com.easytech.simplecrm.dao.XeroDAO;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.ca.Address;
import ar.com.easytech.simplecrm.model.ca.Phone;
import ar.com.easytech.simplecrm.model.ca.Emails;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.Note;
import ar.com.easytech.simplecrm.model.enums.CurrencyCode;
import ar.com.easytech.simplecrm.model.enums.OpportunityStatus;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.OpportunityLine;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.DaoFactory;
import ar.com.easytech.simplecrm.service.OpportunityService;
import ar.com.easytech.simplecrm.service.SfaInfoService;
import ar.com.easytech.simplecrm.service.XeroService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
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
import org.junit.*;
import org.junit.runner.RunWith;

/**
 *
 * @author FMQ
 */
@RunWith(Arquillian.class)
public class OpportunityServiceTest {

   private static final Logger logger = Logger.getLogger(OpportunityServiceTest.class.toString());
   
   private static String customerName = "Opportunity Test Customer";
   private static String xeroId = new Long(new Random().nextLong()).toString();
   
   private long customerId = 0;
   
   @Inject CustomerService customerService;
   @Inject SfaInfoService sfaInfoService;
   @Inject OpportunityService opportunityService;
   
   @PersistenceContext(name="SimpleCRMTest")
   EntityManager em;
   
   @Deployment
   public static JavaArchive createTestArchive() {
      try {
         JavaArchive model = ShrinkWrap.create(ZipImporter.class, "model.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-model/1.0/simple-crm-model-1.0.jar"))
                 .as(JavaArchive.class);

         JavaArchive xero = ShrinkWrap.create(ZipImporter.class, "xero.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/xero-integration/1.0/xero-integration-1.0.jar"))
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
                             XeroDAO.class,
                             OpportunityDAO.class,
                             BaseDAO.class,
                             CustomerService.class,
                             DaoFactory.class, 
						     SysSequenceHelper.class,
						     OpportunityDAO.class,
							 ProjectDAO.class,
							 ProposalDAO.class,
							 XeroService.class,
							 SysDAO.class, 
							 SysSequenceHelper.class,
							 OpportunityService.class,
							 SfaInfoService.class)
                 .merge(model)
                 .merge(commons)
                 .merge(xero)
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
   public void testCreateOpportunity() throws Exception {
      
      Customer customer = customerService.findCustomerByXeroId(xeroId);
      
      Opportunity opp = new Opportunity();
      opp.setCustomer(customer);
      opp.setOpportunityName("Test Opp");
      opp.setWinProbability(20);
      opp.setStatus(OpportunityStatus.OPEN);
      opp.addNote(new Note("Esto es una nota"));
      
      List<Person> custContacts = new ArrayList<Person>(customer.getContacts());
      opp.addContact(custContacts.get(0));
      
      OpportunityLine line = new OpportunityLine();
      line.setItem("Hora Desarrollo Jr");
      line.setQuantity(2);
      line.setCurrencyCode(CurrencyCode.ARS);
      line.setAmount(new BigDecimal("100.50"));
      Set<OpportunityLine> lines = new HashSet<OpportunityLine>();
      lines.add(line);
      opp.setLines(lines);
      
      long id = opportunityService.createOpportunity(opp);
      Assert.assertNotNull(id);
              
   }
   
   @Test
   public void testFindAllOpportunities() throws Exception {
      
      List<Opportunity> opportunities = opportunityService.getAllOpportunites();
      Assert.assertNotNull(opportunities);
      
      for (Opportunity opp: opportunities ) {
    	 Opportunity opportunity = opportunityService.getOpportunityData(opp.getId());
         List<OpportunityLine> lines = new ArrayList<OpportunityLine>(opportunity.getLines());
         Assert.assertNotNull(lines);
         Assert.assertFalse(lines.isEmpty());
         Assert.assertTrue(lines.size() > 0);
         Assert.assertNotNull(opportunity.getCustomer());
      }
   }
   
   @Test
   public void testGetOppForCustomer() throws Exception {
      
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
      List<Opportunity> opportunities = opportunityService.findOpportunituesForCustomer(customer.getId());
      Assert.assertNotNull(opportunities);
      Assert.assertFalse(opportunities.isEmpty());
      Assert.assertTrue(opportunities.size() > 0);
      
   }
   
   @Test
   public void testGetPipelineOpp() throws Exception {
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
	   Assert.assertNotNull(customer);
	   
	   BigDecimal pipeline = sfaInfoService.getPipelineTotal(customer.getId());
	   Assert.assertNotNull(pipeline);
	   Assert.assertTrue(pipeline.compareTo(BigDecimal.ZERO) > 0);
   }
   
   @Test
   public void testGetForecastOppEqZero() throws Exception {
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
	   Assert.assertNotNull(customer);
	   
	   BigDecimal forecast = sfaInfoService.getForecastTotal(customer.getId());
	   Assert.assertNotNull(forecast);
	   Assert.assertTrue(forecast.compareTo(BigDecimal.ZERO) == 0);
   }
   
   @Test
   public void testGetForecastOpp() throws Exception {
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
	   Assert.assertNotNull(customer);
	   List<Opportunity> opportunities = opportunityService.findOpportunituesForCustomer(customer.getId());
	   Assert.assertNotNull(opportunities);
	   for (Opportunity opportunity: opportunities) {
		   Opportunity opp = opportunityService.getOpportunityData(opportunity.getId());
		   Assert.assertNotNull(opp);
		   Assert.assertNotNull(opp.getLines());
		   opp.setStatus(OpportunityStatus.FORECAST);
		   opportunityService.updateOpportunity(opp);
	   }
	   
	   BigDecimal forecast = sfaInfoService.getForecastTotal(customer.getId());
	   Assert.assertNotNull(forecast);
	   Assert.assertTrue(forecast.compareTo(BigDecimal.ZERO) == 0);
   }
   
   @Test
   public void testDeleteData() throws Exception {
    
	  Customer customer = customerService.findCustomerByXeroId(xeroId);
      List<Opportunity> opportunities = opportunityService.findOpportunituesForCustomer(customer.getId());
      Assert.assertNotNull(opportunities);
      Assert.assertTrue(opportunities.size() >0);
      opportunityService.deleteOpportunity(opportunities.get(0));
      
      
   }
}
