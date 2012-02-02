package ar.com.easytech.test.service;

import ar.com.easytech.simplecrm.dao.*;
import ar.com.easytech.simplecrm.model.sys.SysUser;
import ar.com.easytech.xero.integration.model.ReportCell;

import java.io.IOException;
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
import org.junit.*;
import org.junit.runner.RunWith;

/**
 *
 * @author FMQ
 */
@RunWith(Arquillian.class)
public class SysServiceTest {

   private static final Logger logger = Logger.getLogger(SysServiceTest.class.toString());
   
   
   @Inject
   SysDAO sysService;

   @Deployment
   public static JavaArchive createTestArchive() {
      try {
         JavaArchive model = ShrinkWrap.create(ZipImporter.class, "model.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-model/1.0/simple-crm-model-1.0.jar"))
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
         
         JavaArchive commons = ShrinkWrap.create(ZipImporter.class, "commons.jar")
                 .importFrom(new ZipFile("/Users/facundo/.m2/repository/ar/com/easytech/simplecrm/simple-crm-common/1.0/simple-crm-common-1.0.jar"))
                 .as(JavaArchive.class);
         
         return ShrinkWrap.create(JavaArchive.class, "test.jar")
                 .addClasses(SysDAO.class)
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
      Assert.assertNotNull(sysService);
   }
   
   @Test
   public void testCreateUser() throws Exception  {
       SysUser user = new SysUser();
       user.setUserName("facundo@easytech.com");
       user.setPassword("fooBar");
       long id = sysService.createUser(user);
       Assert.assertNotNull(id);
   }
   
   @Test
   public void testUpdateUser() throws Exception {
       SysUser user = new SysUser();
       user.setUserName("foo@gmail.com");
       user.setPassword("fooBar");
       long id = sysService.createUser(user);
       Assert.assertNotNull(id);
       user.setDisplayName("Facundo");
       sysService.updateUser(user);
   }
   
   @Test
   public void testDeleteUser() throws Exception {
       SysUser user = new SysUser();
       user.setUserName("foo1@gmail.com");
       user.setPassword("fooBar");
       long id = sysService.createUser(user);
       Assert.assertNotNull(id);
       sysService.deleteUser(user);
   }
   
   @Test
   public void testGetUsers() throws Exception {
      SysUser user = sysService.getUser("facundo@easytech.com");
      Assert.assertNotNull(user);
   }
   
   
}
