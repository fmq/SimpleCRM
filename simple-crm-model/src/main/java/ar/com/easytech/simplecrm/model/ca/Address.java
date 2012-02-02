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
@Table(name="ca_address")
public class Address extends AbstractEntity implements Serializable , Cloneable {

   private static final long serialVersionUID = 7526472292522776147L;

   public Address() {
   }

   public Address(String addressLine1, String city, String state, String country) {
      this.addressLine1 = addressLine1;
      this.city = city;
      this.state = state;
      this.country = country;
   }

   @Column(name="address_line_1")
   private String addressLine1;
   
   @Column(name="address_line_2")
   private String addressLine2;
   
   @Column(name="address_line_3")
   private String addressLine3;
   
   private String city;
   private String state;
   
   private String country;
   
   @Column(name="postal_code")
   private String postalCode;
   
   public String getAddressLine1() {
      return addressLine1;
   }

   public void setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
   }

   public String getAddressLine2() {
      return addressLine2;
   }

   public void setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
   }

   public String getAddressLine3() {
      return addressLine3;
   }

   public void setAddressLine3(String addressLine3) {
      this.addressLine3 = addressLine3;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getPostalCode() {
      return postalCode;
   }

   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getFormatedAddress() {
	   StringBuilder builder = new StringBuilder();
	   
	   builder.append(addressLine1);
	   if (city != null)
		   builder.append(", ").append(city);
	   
	   if (state != null)
		   builder.append(", ").append(state);
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
