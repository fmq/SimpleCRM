package ar.com.easytech.test.service;

import java.io.IOException;
import java.util.List;
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

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
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
import ar.com.easytech.simplecrm.model.dto.CustomerInfoRow;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.DaoFactory;
import ar.com.easytech.simplecrm.service.OpportunityService;
import ar.com.easytech.simplecrm.service.SfaInfoService;
import ar.com.easytech.simplecrm.service.XeroService;
import ar.com.easytech.xero.integration.util.ParamBuilder;

/**
 * 
 * @author FMQ
 */
@RunWith(Arquillian.class)
public class CustomerServiceTest {

	private static final Logger logger = Logger
			.getLogger(CustomerServiceTest.class.toString());

	private static String customerName = "Test Customer";
	private static String xeroId = "7456ac14-9940-4e46-9135-1fca8797527c";

	@Inject
	CustomerService customerService;

	@PersistenceContext(name = "SimpleCRMTest")
	EntityManager em;

	@Deployment
	public static JavaArchive createTestArchive() {
		try {
			JavaArchive model = ShrinkWrap
					.create(ZipImporter.class, "model.jar")
					.importFrom(
							new ZipFile(
									"/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-model/1.0/simple-crm-model-1.0.jar"))
					.as(JavaArchive.class);

			JavaArchive xero = ShrinkWrap
					.create(ZipImporter.class, "xero.jar")
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-httpclient4/20100601/oauth-httpclient4-20100601.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-provider/20100601/oauth-provider-20100601.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth/20100601/oauth-20100601.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/net/oauth/core/oauth-consumer/20100601/oauth-consumer-20100601.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/org/apache/httpcomponents/httpcore/4.0.1/httpcore-4.0.1.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/org/apache/httpcomponents/httpclient/4.0.1/httpclient-4.0.1.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar"))
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/xero-integration/1.0/xero-integration-1.0.jar"))
					.as(JavaArchive.class);

			JavaArchive commons = ShrinkWrap
					.create(ZipImporter.class, "commons.jar")
					.importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-common/1.0/simple-crm-common-1.0.jar"))
					.as(JavaArchive.class);

			return ShrinkWrap
					.create(JavaArchive.class, "test.jar")
					.addClasses(CustomerDAO.class, 
								ParamBuilder.class,
								XeroDAO.class, 
								BaseDAO.class,
								DaoFactory.class,
								CustomerService.class,
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
					.addAsManifestResource(EmptyAsset.INSTANCE,ArchivePaths.create("beans.xml"))
					.addAsManifestResource("test-persistence.xml","persistence.xml");

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
	public void testCreateCustomer() throws Exception {

		Assert.assertNotNull(customerService);
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

		long customerId = customerService.createCustomer(customer);
		Assert.assertNotNull(customerId);

	}
	
	@Test
	public void testCreateQuickCustomer() throws Exception {
		Customer customer = customerService.createQuickCustomer("Test createQuickCustomer");
		Assert.assertNotNull(customer);
		Assert.assertTrue(customer.getCustomerNumber()>0);
		logger.log(Level.INFO, "Customer Name: " ,customer.getCustomerNumber());
	}

	@Test
	public void testFindByName() throws Exception {

		List<Customer> customers = customerService.findCustomerByName(customerName);
		Assert.assertNotNull(customers);
		Assert.assertFalse(customers.isEmpty());
		Assert.assertTrue(customers.size() > 0);
		for (Customer customer : customers) {
			logger.log(Level.INFO, "Customer name: {0}",customer.getCustomerName());
		}

	}

	@Test
	public void testFindByXeroId() throws Exception {

		Customer customer = customerService.findCustomerByXeroId(xeroId);
		Assert.assertNotNull(customer);
	}

	@Test
	public void getAllActiveCustomer() throws Exception {
		List<Customer> customers = customerService.getAllCustomers();

		Assert.assertNotNull(customers);
		Assert.assertFalse(customers.isEmpty());
		Assert.assertTrue(customers.size() > 0);
	}

	@Test
	public void testGetCustomerData() throws Exception {
		List<Customer> customers = customerService.findCustomerByName(customerName);
		Assert.assertNotNull(customers);
		Assert.assertFalse(customers.isEmpty());
		Assert.assertTrue(customers.size() > 0);
		Customer customer = customerService.getCustomerData(customers.get(0));
		Assert.assertNotNull(customer.getContacts());
		Assert.assertTrue(customer.getContacts().size() > 0);
		Assert.assertTrue(customer.getPhones().size() > 0);
		Assert.assertTrue(customer.getEmails().size() > 0);
	}

	@Test
	public void testDeleteCustomer() throws Exception {

		List<Customer> customers = customerService.findCustomerByName(customerName);
		Assert.assertNotNull(customers);
		Assert.assertFalse(customers.isEmpty());
		Assert.assertTrue(customers.size() > 0);
		customerService.deleteCustomer(customers.get(0));
		customers = customerService.getAllCustomers();
	}

	@Test
	public void testGetRowData() throws Exception {
		List<CustomerInfoRow> rows = customerService.getCustomerSummaryData();
		Assert.assertNotNull(rows);
		Assert.assertNotNull(rows.get(0).getForecastTotal());
	}
}
