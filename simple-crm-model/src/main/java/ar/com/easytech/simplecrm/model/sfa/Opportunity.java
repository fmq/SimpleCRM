package ar.com.easytech.simplecrm.model.sfa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.Note;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.enums.OpportunityStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author facundo
 */
@Entity
@Table(name="sfa_oportunity")
public class Opportunity extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 7526472395622776147L;

   public Opportunity() {
      super();
      status = OpportunityStatus.OPEN;
   }
   
   @Column(name="opportunity_Name")
   private String opportunityName;
   
   @Column(name="opportunity_Number")
   private long opportunityNumber;
   
   @Enumerated(EnumType.STRING)
   private OpportunityStatus status;
   
   @Temporal(TemporalType.DATE)
   @Column(name="expected_close_date")
   private Date expectedCloseDate;
   
   @Temporal(TemporalType.DATE)
   @Column(name="close_date")
   private Date closeDate;
   
   private String closeReason;
   
   @Column(name="win_probability")
   private int winProbability;
   
   @OneToOne(cascade= CascadeType.MERGE, fetch= FetchType.LAZY)
   private Customer customer;
   
   @OneToMany(cascade= CascadeType.MERGE, fetch= FetchType.LAZY)
   @JoinTable(name="sfa_oportunity_contacts",
            joinColumns = @JoinColumn(name = "opportunity_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
   private Set<Person> contacts;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinTable(name="sfa_oportunity_notes",
            joinColumns = @JoinColumn(name = "opportunity_id"),
            inverseJoinColumns = @JoinColumn(name = "note_id"))
   private Set<Note> notes;
   
   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name="opportunity_id")
   Set<OpportunityLine> lines;

   @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.LAZY)
   @JoinColumn(name="opportunity_id")
   Set<Proposal> proposals;

   public Date getCloseDate() {
      return closeDate;
   }

   public void setCloseDate(Date closeDate) {
      this.closeDate = closeDate;
   }

   public String getCloseReason() {
      return closeReason;
   }

   public void setCloseReason(String closeReason) {
      this.closeReason = closeReason;
   }

   public Set<Person> getContacts() {
      return contacts;
   }

   public void setContacts(Set<Person> contacts) {
      this.contacts = contacts;
   }

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }

   public Date getExpectedCloseDate() {
      return expectedCloseDate;
   }

   public void setExpectedCloseDate(Date expectedCloseDate) {
      this.expectedCloseDate = expectedCloseDate;
   }

   public Set<OpportunityLine> getLines() {
      return lines;
   }

   public void setLines(Set<OpportunityLine> lines) {
      this.lines = lines;
   }

   public Set<Note> getNotes() {
      return notes;
   }

   public void setNotes(Set<Note> notes) {
      this.notes = notes;
   }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public long getOpportunityNumber() {
        return opportunityNumber;
    }

    public void setOpportunityNumber(long opportunityNumber) {
        this.opportunityNumber = opportunityNumber;
    }

   public Set<Proposal> getProposals() {
      return proposals;
   }

   public void setProposals(Set<Proposal> proposals) {
      this.proposals = proposals;
   }

   public OpportunityStatus getStatus() {
      return status;
   }

   public void setStatus(OpportunityStatus status) {
      this.status = status;
   }

   public int getWinProbability() {
      return winProbability;
   }

   public void setWinProbability(int winProbability) {
      this.winProbability = winProbability;
   }
   
   
   public void addProposal(Proposal value){
      Set<Proposal> obj = getProposals();
      
      if (obj == null)
         obj = new HashSet<Proposal>();
      
      obj.add(value);
      setProposals(obj);
   }
   
   public void addContact(Person value){
      Set<Person> obj = getContacts();
      
      if (obj == null)
         obj = new HashSet<Person>();
      
      obj.add(value);
      setContacts(obj);
   }
   
   public void addNote(Note value) {
   
      Set<Note> obj = getNotes();
      
      if (obj == null)
         obj = new HashSet<Note>();
      
      obj.add(value);
      setNotes(obj);
   }   
    
   @Override
   public String toString() {
      return "OPP: "+ getOpportunityName();
   }
}
