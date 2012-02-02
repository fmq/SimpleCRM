package ar.com.easytech.simplecrm.web.authenticator;

import ar.com.easytech.simplecrm.model.sys.SysUser;
import org.picketlink.idm.api.User;

/**
 *
 * @author FMQ
 */
public class SiteUser implements User {

   private SysUser sysUser;

   public SiteUser(SysUser sysUser) {
      this.sysUser = sysUser;
   }
   
   public String getDisplayName() {
      return sysUser.getDisplayName();
   }
   
   @Override
   public String getId() {
      return sysUser.getId().toString();
   }

   @Override
   public String getKey() {
      return sysUser.getId().toString();
   }
   
}
