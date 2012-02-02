package ar.com.easytech.simplecrm.model.sys;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="sys_users", uniqueConstraints=@UniqueConstraint(columnNames="user_name"))
public class SysUser extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 5651011888463158208L;

   @Column(name="user_name")
   @NotNull
   @Email
   private String userName;
   
   @Column(name="password")
   @NotNull
   private String password;
   
   private String displayName;

   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
   @JoinTable(joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="role_id"))
   Set<SysRole> roles;
   
   /* Getters & Setters */
   public String getDisplayName() {
      return displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public Set<SysRole> getRoles() {
      return roles;
   }

   public void setRoles(Set<SysRole> roles) {
      this.roles = roles;
   }
   
}
