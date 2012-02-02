package ar.com.easytech.simplecrm.model;

import ar.com.easytech.simplecrm.model.enums.StatusTypes;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.PrePassivate;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class implementing the basic properties of all entities.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public AbstractEntity() {
		recordStatus = StatusTypes.ACTIVE;
	}

	@PrePassivate
	public void setAuditDates() {
		if (creationDate == null)
			creationDate = new Date();
		lastUpdate = new Date();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlTransient
	private final Long id = null;

	private String type;
	@Enumerated(EnumType.STRING)
	private StatusTypes recordStatus;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	private Date creationDate;
	@Basic
	@Column(name = "created_by")
	private String createdBy;

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_date")
	private Date lastUpdate;

	@Basic
	@Version
	@XmlTransient
	private int version;

	@Basic
	@Column(name = "updated_by")
	private String updatedBy;

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public Long getId() {
		return id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public StatusTypes getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(StatusTypes recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Logger.getLogger(AbstractEntity.class.toString()).info(
				"Cloned on Abstract Entity called..");
		Object o = null;

		try {
			o = super.clone();
			Logger.getLogger(AbstractEntity.class.toString()).log(Level.INFO,
					"Cloned object..{0}", o.getClass().getName());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new CloneNotSupportedException();
		}
		return o;
	}
}
