package ar.com.easytech.simplecrm.model.ca;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import ar.com.easytech.simplecrm.model.AbstractEntity;

/**
 *
 * @author facundo
 */
@Entity
@Table(name="ca_email")
public class Emails extends AbstractEntity implements Serializable, Cloneable {
   
   private static final long serialVersionUID = 7526472295622776116L;

   public Emails() {
      super();
   }

   public Emails(String emailAddress) {
      this.emailAddress = emailAddress;
   }

   @Column(name="email_address")
   @Email
   @NotNull
   private String emailAddress;

   public String getEmailAddress() {
      return emailAddress;
   }

   public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
   }
   
   @Override
   public Object clone() {
      Object o = null;
      try {
         o = super.clone();
         
      } catch (CloneNotSupportedException e) {
         e.printStackTrace();
      }
      return o;
   }
}
