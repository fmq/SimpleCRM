package ar.com.easytech.simplecrm.model.ca;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.Note;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author facundo
 */
@Entity
@Table(name="ca_person")
public class Person extends AbstractEntity implements Serializable, Cloneable {
   
   private static final long serialVersionUID = 7526472212622776147L;

   public Person() {
      super();
   }
   
   @Column(name="last_name")
   @NotNull
   private String lastName;
   
   @Column(name="first_name")
   private String firstName;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "person_id")
   private Set<Address> addresses;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "person_id")
   private Set<Phone> phones;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name = "person_id")
   private Set<Emails> emails;

   @OneToMany
   @JoinTable(name="ca_customer_note",
            joinColumns = @JoinColumn(name = "contact_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"))
   private Set<Note> notes;

   public Set<Address> getAddresses() {
      return addresses;
   }

   public void setAddresses(Set<Address> addresses) {
      this.addresses = addresses;
   }

   public Set<Emails> getEmails() {
      return emails;
   }

   public void setEmails(Set<Emails> emails) {
      this.emails = emails;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public Set<Note> getNotes() {
      return notes;
   }

   public void setNotes(Set<Note> notes) {
      this.notes = notes;
   }

   public Set<Phone> getPhones() {
      return phones;
   }

   public void setPhones(Set<Phone> phones) {
      this.phones = phones;
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
      return "person:"+ getId() + ":lastName:" + getLastName() + ":firstName:" + getFirstName();
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
