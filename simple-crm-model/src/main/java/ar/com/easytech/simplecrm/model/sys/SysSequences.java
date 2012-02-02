package ar.com.easytech.simplecrm.model.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import ar.com.easytech.simplecrm.model.AbstractEntity;

@Entity
@Table(name="sys_sequences")
@NamedQuery(name="getNextValue", query="select ss from SysSequences ss where sequenceName = :sequenceName")
public class SysSequences extends AbstractEntity {

	private static final long serialVersionUID = -5487941108250228684L;

	@Column(name="sequence_name")
	private String sequenceName;
	
	@Column(name="sequence_value")
	private long sequenceValue;

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public long getSequenceValue() {
		return sequenceValue;
	}

	public void setSequenceValue(long sequenceValue) {
		this.sequenceValue = sequenceValue;
	}
	
}
