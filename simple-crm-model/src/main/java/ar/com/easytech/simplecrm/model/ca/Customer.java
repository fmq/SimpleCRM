package ar.com.easytech.simplecrm.model.ca;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotEmpty;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.Note;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="ca_customer", uniqueConstraints={@UniqueConstraint(columnNames="customer_number"),
											  @UniqueConstraint(columnNames="taxpayer_id")})
public class Customer extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 7526472295622772147L;

   public Customer() {
      super();
   }
   
   @Column(name="customer_name")
   @NotEmpty
   private String customerName;
   
   @Column(name="customer_number")
   private long customerNumber;
   
   @Column(name="taxpayer_id")
   String taxpayerId;
   
   @Column(name="receivabales_tax_type")
   String receivableTaxType;
   
   @Column(name="payables_tax_type")
   String payableTaxType;
   
   @Column(name="defaultCurrency")
   String defaultCurrency;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "customer_id")
   private Set<Address> addresses;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "customer_id")
   private Set<Phone> phones;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "customer_id")
   private Set<Emails> emails;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "customer_id")
   private Set<Person> contacts;

   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinTable(name="ca_customer_notes",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"))
   private Set<Note> notes;
   
   @Column(name="xero_id")
   private String xeroId;

   public Set<Address> getAddresses() {
      return addresses;
   }

   public void setAddresses(Set<Address> addresses) {
      this.addresses = addresses;
   }

   public Set<Person> getContacts() {
      return contacts;
   }

   public void setContacts(Set<Person> contacts) {
      this.contacts = contacts;
   }

   public String getCustomerName() {
      return customerName;
   }

   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }

    public long getCustomerNumber() {
    	return customerNumber;
	}
	
	public void setCustomerNumber(long customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public String getDefaultCurrency() {
      return defaultCurrency;
   }

   public void setDefaultCurrency(String defaultCurrency) {
      this.defaultCurrency = defaultCurrency;
   }

   public Set<Emails> getEmails() {
      return emails;
   }

   public void setEmails(Set<Emails> emails) {
      this.emails = emails;
   }

   public Set<Note> getNotes() {
      return notes;
   }

   public void setNotes(Set<Note> notes) {
      this.notes = notes;
   }

   public String getPayableTaxType() {
      return payableTaxType;
   }

   public void setPayableTaxType(String payableTaxType) {
      this.payableTaxType = payableTaxType;
   }

   public Set<Phone> getPhones() {
      return phones;
   }

   public void setPhones(Set<Phone> phones) {
      this.phones = phones;
   }

   public String getReceivableTaxType() {
      return receivableTaxType;
   }

   public void setReceivableTaxType(String receivableTaxType) {
      this.receivableTaxType = receivableTaxType;
   }

   public String getTaxpayerId() {
      return taxpayerId;
   }

   public void setTaxpayerId(String taxpayerId) {
      this.taxpayerId = taxpayerId;
   }

   public String getXeroId() {
      return xeroId;
   }

   public void setXeroId(String xeroId) {
      this.xeroId = xeroId;
   }

   public void addContact(Person value){
      Set<Person> obj = getContacts();
      
      if (obj == null)
         obj = new HashSet<Person>();
      
      obj.add(value);
      setContacts(obj);
   }
   
   public void addAddress(Address value){
      Set<Address> obj = getAddresses();
      if (obj == null)
         obj = new HashSet<Address>();
      obj.add(value);
      setAddresses(obj);
   }
   
   public void addPhone(Phone value){
      Set<Phone> obj = getPhones();
      if (obj == null)
         obj = new HashSet<Phone>();
      
      obj.add(value);
      setPhones(obj);
   }
   
   public void addEmail(Emails email){
      Set<Emails> obj = getEmails();
      
      if (obj == null)
         obj = new HashSet<Emails>();
      obj.add(email);
      setEmails(obj);
   }

   @Override
   public String toString() {
      return "CUST: " + getCustomerName();
   }
}
