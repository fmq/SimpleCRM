package ar.com.easytech.simplecrm.model.sfa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.Note;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.enums.CurrencyCode;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name = "sfa_proposal")
public class Proposal extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 7526472395622774547L;

    public Proposal() {
        super();
    }
    @Column(name = "proposal_number")
    private String proposalNumber;
    @Column(name = "proposal_name")
    private String proposalName;
    private String description;
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Customer customer;
    @Temporal(TemporalType.DATE)
    @Column(name = "submited_date")
    private Date submitedDate;
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;
    @Column(name = "conversion_rate")
    BigInteger conversionRate;
    BigInteger amount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sfa_proposal_notes",
    joinColumns =
    @JoinColumn(name = "proposal_id"),
    inverseJoinColumns =
    @JoinColumn(name = "note_id"))
    private Set<Note> notes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "opportunity_id")
    private Set<PaymentTerm> paymentTerms;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sfa_proposal_contacts",
    joinColumns =
    @JoinColumn(name = "proposal_id"),
    inverseJoinColumns =
    @JoinColumn(name = "contact_id"))
    private Set<Person> contacts;
    @Column(name = "proposal_file")
    private byte[] proposalFile;

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public BigInteger getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigInteger conversionRate) {
        this.conversionRate = conversionRate;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProposalName() {
        return proposalName;
    }

    public void setProposalName(String proposalName) {
        this.proposalName = proposalName;
    }

    public String getProposalNumber() {
        return proposalNumber;
    }

    public void setProposalNumber(String proposalNumber) {
        this.proposalNumber = proposalNumber;
    }

    public byte[] getProposalFile() {
        return proposalFile;
    }

    public void setProposalFile(byte[] proposalFile) {
        this.proposalFile = proposalFile;
    }

    public Date getSubmitedDate() {
        return submitedDate;
    }

    public void setSubmitedDate(Date submitedDate) {
        this.submitedDate = submitedDate;
    }

    public Set<Person> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Person> contacts) {
        this.contacts = contacts;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Set<PaymentTerm> getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Set<PaymentTerm> paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public void addPaymentTerm(PaymentTerm value) {

        Set<PaymentTerm> obj = getPaymentTerms();

        if (obj == null) {
            obj = new HashSet<PaymentTerm>();
        }

        obj.add(value);
        setPaymentTerms(obj);
    }

    public void addContact(Person value) {
        Set<Person> obj = getContacts();

        if (obj == null) {
            obj = new HashSet<Person>();
        }

        obj.add(value);
        setContacts(obj);
    }

    public void addNote(Note value) {

        Set<Note> obj = getNotes();

        if (obj == null) {
            obj = new HashSet<Note>();
        }

        obj.add(value);
        setNotes(obj);
    }

    @Override
    public String toString() {
        return "PRO:" + getProposalName();
    }
}
