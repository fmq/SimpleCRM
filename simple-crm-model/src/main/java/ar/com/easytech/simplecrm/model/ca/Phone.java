package ar.com.easytech.simplecrm.model.ca;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author facundo
 */
@Entity
@Table(name="ca_phone")
public class Phone extends AbstractEntity implements Serializable, Cloneable {
   
   private static final long serialVersionUID = 7526472295622776557L;

   public Phone() {
      super();
   }

   public Phone(int countryCode, int areaCode, long phoneNumber) {
      this.countryCode = countryCode;
      this.areaCode = areaCode;
      this.phoneNumber = phoneNumber;
   }
   
   @Column(name="country_code")
   private int countryCode;
   
   @Column(name="area_code")
   private int areaCode;
   
   @Column(name="phone_number", length=15)
   private long phoneNumber;
   
   @Column(name="extension", length=15)
   private int extension;

   public int getAreaCode() {
      return areaCode;
   }

   public void setAreaCode(int areaCode) {
      this.areaCode = areaCode;
   }

   public int getCountryCode() {
      return countryCode;
   }

   public void setCountryCode(int countryCode) {
      this.countryCode = countryCode;
   }

   public int getExtension() {
      return extension;
   }

   public void setExtension(int extension) {
      this.extension = extension;
   }

   public long getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(long phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
 
   public String getFormatedNumber() {
	   StringBuilder builder = new StringBuilder();
	   
	   if (countryCode > 0)
		   builder.append("+").append(countryCode);
	   
	   if (areaCode > 0)
		   builder.append(" ").append(areaCode).append(" ");
	   
	   builder.append(phoneNumber);
	   
	   if (extension > 0)
		   builder.append("(").append(extension).append(")");
	   
	   return builder.toString();
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
