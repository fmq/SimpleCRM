package ar.com.easytech.test.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
import ar.com.easytech.simplecrm.model.Note;
import ar.com.easytech.simplecrm.model.ca.Address;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Emails;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.ca.Phone;
import ar.com.easytech.simplecrm.model.dto.AgedReceivablesRow;
import ar.com.easytech.simplecrm.model.enums.CurrencyCode;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.OpportunityLine;
import ar.com.easytech.simplecrm.model.sfa.PaymentTerm;
import ar.com.easytech.simplecrm.model.sfa.Proposal;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.DaoFactory;
import ar.com.easytech.simplecrm.service.OpportunityService;
import ar.com.easytech.simplecrm.service.ProjectService;
import ar.com.easytech.simplecrm.service.ProposalService;
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
public class ProposalServiceTest {

   private static final Logger logger = Logger.getLogger(ProposalServiceTest.class.toString());
   
   private static String customerName = "Proposal Test Customer";
   private static String xeroId = new Long(new Random().nextLong()).toString();
   private long customerId = 0;
   
   @Inject
   CustomerService customerService;

   @Inject OpportunityService opportunityService;
   @Inject ProposalService proposalService;
   
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
	                         ProjectService.class,
	                         ProposalService.class)
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
   public void testCreateProposal() throws Exception {
      Customer customer = customerService.findCustomerByXeroId(xeroId);
      Assert.assertNotNull(customer);
      
      Proposal proposal = new Proposal();
      proposal.setProposalName("Test Proposal");
      proposal.setProposalNumber(new Long(new Random().nextLong()).toString());
      proposal.setAmount(new BigInteger("1000"));
      proposal.setCurrencyCode(CurrencyCode.ARS);
      proposal.setSubmitedDate(new Date());
      
      PaymentTerm term = new PaymentTerm();
      term.setDescription("Adelanto");
      term.setPercentage(20);
      term.setDueDate(new Date());
      proposal.addPaymentTerm(term);
      
      term = new PaymentTerm();
      term.setDescription("Produccion");
      term.setPercentage(80);
      term.setDueDate(new Date());
      proposal.addPaymentTerm(term);
      
      proposal.setCustomer(customer);
      proposal.setContacts(customer.getContacts());
      
      long id = proposalService.createProposal(proposal);
      Assert.assertNotNull(id);
   }
   
   @Test
   public void testFindAllProposals() throws Exception {
      
      List<Proposal> proposals = proposalService.getAllProposals();
      Assert.assertNotNull(proposals);
      
      for (Proposal prop : proposals) {
    	  Proposal proposal = proposalService.getProposalData(prop.getId());
         List<PaymentTerm> terms = new ArrayList<PaymentTerm>(proposal.getPaymentTerms());
         Assert.assertNotNull(terms);
         Assert.assertFalse(terms.isEmpty());
         
         Assert.assertNotNull(proposal.getNotes());
         Assert.assertNotNull(proposal.getContacts());
      }
   }
   
   @Test
   public void testGetProposalrCustomer() throws Exception {
      
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
      Assert.assertNotNull(customer);
      List<Proposal> proposals = proposalService.getProposalsForCustomerId(customer.getId());
      
      Assert.assertNotNull(proposals);
      Assert.assertFalse(proposals.isEmpty());
      Assert.assertTrue(proposals.size()>0);
      
      for (Proposal prop : proposals) {
    	  Proposal proposal = proposalService.getProposalData(prop.getId());
         List<PaymentTerm> terms = new ArrayList<PaymentTerm>(proposal.getPaymentTerms());
         Assert.assertNotNull(terms);
         Assert.assertFalse(terms.isEmpty());
         
         Assert.assertNotNull(proposal.getNotes());
         Assert.assertNotNull(proposal.getContacts());
      }
   }
   
   @Test
   public void testAddProposalToOpportunity() throws Exception {
      
      // Generlo la opp
	   Customer customer = customerService.findCustomerByXeroId(xeroId);
      Assert.assertNotNull(customer);
      
      Opportunity opp = new Opportunity();
      opp.setCustomer(customer);
      opp.setOpportunityName("Test Opp");
      opp.setOpportunityNumber(new Random().nextLong());
      opp.setWinProbability(20);
      
      opp.addNote(new Note("Esto es una nota"));
      
      List<Person> custContacts = new ArrayList<Person>(customer.getContacts());
      opp.addContact(custContacts.get(0));
      
      OpportunityLine line = new OpportunityLine();
      line.setItem("Hora Desarrollo Jr");
      line.setQuantity(2);
      line.setCurrencyCode(CurrencyCode.ARS);
      line.setAmount(new BigDecimal("100"));
      Set<OpportunityLine> lines = new HashSet<OpportunityLine>();
      lines.add(line);
      opp.setLines(lines);
      
      long id = opportunityService.createOpportunity(opp);
      Assert.assertNotNull(id);
      // LE agrego la primer propuesta del cliente 
      List<Proposal> proposals = proposalService.getProposalsForCustomerId(customer.getId());
      
      Assert.assertNotNull(proposals);
      Assert.assertFalse(proposals.isEmpty());
      Assert.assertTrue(proposals.size()>0);
      
      opp.addProposal(proposals.get(0));
      opportunityService.updateOpportunity(opp);
   }
}
