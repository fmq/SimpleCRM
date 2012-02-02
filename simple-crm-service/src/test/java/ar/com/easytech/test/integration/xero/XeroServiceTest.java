package ar.com.easytech.test.integration.xero;

import ar.com.easytech.simplecrm.dao.BaseDAO;
import ar.com.easytech.simplecrm.dao.CustomerDAO;
import ar.com.easytech.simplecrm.dao.XeroDAO;
import ar.com.easytech.simplecrm.model.dto.AgedReceivablesRow;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.XeroService;
import ar.com.easytech.test.service.CustomerServiceTest;
import ar.com.easytech.xero.integration.XeroClientServices;
import ar.com.easytech.xero.integration.model.ArrayOfContact;
import ar.com.easytech.xero.integration.model.Invoice;
import ar.com.easytech.xero.integration.util.ParamBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;
import javax.inject.Inject;
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



/**
 *
 * @author FMQ
 */
@RunWith(Arquillian.class)
public class XeroServiceTest {
   
   public static final Logger logger = Logger.getLogger(XeroServiceTest.class.toString());
   
   @Inject CustomerService customerService;
   
   @Inject XeroService xeroService;
   
   private static String customerName = "Xero Test Customer";
   private static String xeroId = "7456ac14-9940-4e46-9135-1fca8797527c";
   
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
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/xero-integration/1.0/xero-integration-1.0.jar"))
                 .as(JavaArchive.class);
         
         JavaArchive commons = ShrinkWrap.create(ZipImporter.class, "commons.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-common/1.0/simple-crm-common-1.0.jar"))
                 .as(JavaArchive.class);
         
         return ShrinkWrap.create(JavaArchive.class, "test.jar")
                 .merge(xero)
                 .merge(model)
                 .merge(commons)
                 .addClasses(CustomerDAO.class,
                             ParamBuilder.class,
                             XeroDAO.class,
                             BaseDAO.class,
                             XeroService.class,
                             CustomerService.class)
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
   public void testGetAgedReceivablesForCustomer() throws Exception {
      List<AgedReceivablesRow> rows = xeroService.getAgedReceivablesByCustomer(xeroId);
      for (AgedReceivablesRow row : rows) {
         logger.log(Level.INFO, "Number: {0} Date: {1} AMount: {2}", new Object[]{row.getInvoiceNumber(), row.getInvoiceDate(), row.getInvoiceAmount()});
      }
   }
   
   @Test
   public void testGetXeroInvoicesByXeroId() throws Exception {
      List<Invoice> invoices = xeroService.getInvoicesForCustomer(xeroId);
      Assert.assertNotNull(invoices);
      Assert.assertFalse(invoices.isEmpty());
      
      for (Invoice invoice : invoices) {
         logger.log(Level.INFO, "Invoice: {0} Amount: {1} Due: {2} Paid: {3} Status: {4}", new Object[]{invoice.getInvoiceNumber(), invoice.getTotal(), invoice.getAmountDue(), invoice.getAmountPaid(), invoice.getStatus()});
         
         }
   }
   
   @Test
   public void testGetXeroUnpaidInvoicesByXeroId() throws Exception {
      List<Invoice> invoices = xeroService.getUnpaidInvoicesForCustomer(xeroId);
      Assert.assertNotNull(invoices);
      Assert.assertFalse(invoices.isEmpty());
      
      for (Invoice invoice : invoices) {
         logger.log(Level.INFO, "Invoice: {0} Amount: {1} Invoice Status: {2}", new Object[]{invoice.getInvoiceNumber(), invoice.getTotal(), invoice.getStatus()});
         
         }
   }
   
   @Test
   public void testGgetInvoicePieData() throws Exception {
      Map<String, Object> pieData = xeroService.getDuePaidAmountsByCurrency(xeroId);
      Assert.assertNotNull(pieData);
      Assert.assertTrue(pieData.size() > 0);
   }
}
