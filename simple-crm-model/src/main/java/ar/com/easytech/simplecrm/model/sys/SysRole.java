package ar.com.easytech.simplecrm.model.sys;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="sys_roles")
public class SysRole extends AbstractEntity implements Serializable {
   
   @Column(name="role_name", nullable=false, length=50)
   @NotNull
   private String roleName;
   
   private String description;

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getRoleName() {
      return roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
   }
  
   
}
