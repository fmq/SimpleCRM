package ar.com.easytech.simplecrm.model.pa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.Note;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author FMQ Representa un projecto a ser ejecutado Un proyecto debiera salir
 * de una propuesta (relacion 1 a 1) Un proyecto tiene N hitos a ser facturados
 * Se debe integrar con el track de horas
 */
@Entity
@Table(name = "pa_project")
public class Project extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 7526472395622774547L;

    public Project() {
        super();
    }
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "project_number")
    private String projectNumber;
    private String description;
    @Column(name = "estimated_hours")
    private int estimatedHours;
    @Column(name = "consumed_hours")
    private int consumedHours;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Customer customer;
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "calculated_end_date")
    private Date calculatedEndDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Set<Milestone> milestones;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "pa_project_contacts",
    joinColumns =
    @JoinColumn(name = "proposal_id"),
    inverseJoinColumns =
    @JoinColumn(name = "project_id"))
    private Set<Person> contacts;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "pa_project_notes",
    joinColumns =
    @JoinColumn(name = "project_id"),
    inverseJoinColumns =
    @JoinColumn(name = "note_id"))
    private Set<Note> notes;
    @Column(name = "et_project_id")
    private long etProjectId;

    public Date getCalculatedEndDate() {
        return calculatedEndDate;
    }

    public void setCalculatedEndDate(Date calculatedEndDate) {
        this.calculatedEndDate = calculatedEndDate;
    }

    public int getConsumedHours() {
        return consumedHours;
    }

    public void setConsumedHours(int consumedHours) {
        this.consumedHours = consumedHours;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public long getEtProjectId() {
        return etProjectId;
    }

    public void setEtProjectId(long etProjectId) {
        this.etProjectId = etProjectId;
    }

    public Set<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(Set<Milestone> milestones) {
        this.milestones = milestones;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void addMilestone(Milestone value) {

        Set<Milestone> obj = getMilestones();

        if (obj == null) {
            obj = new HashSet<Milestone>();
        }

        obj.add(value);
        setMilestones(obj);
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
        return "PROJ: " + getProjectName();
    }
}
