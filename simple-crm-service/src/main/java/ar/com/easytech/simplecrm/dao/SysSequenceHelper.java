package ar.com.easytech.simplecrm.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import ar.com.easytech.simplecrm.model.sys.SysSequences;

public class SysSequenceHelper {
	
	private static final Logger logger = Logger.getLogger(SysSequenceHelper.class.toString());
	public static long getNextValue(String sequenceName, EntityManager em) {
		SysSequences seq;
		long sequenceValue =0;
		try {
			seq = (SysSequences) em.createNamedQuery("getNextValue")
										.setParameter("sequenceName", sequenceName)
										.getSingleResult();
			sequenceValue = seq.getSequenceValue();
			seq.setSequenceValue(++sequenceValue);
			em.merge(seq);
		} catch (Exception _ex) {
			seq = new SysSequences();
			seq.setSequenceName(sequenceName);
			seq.setSequenceValue(++sequenceValue);
			em.persist(seq);
		}
		
		return sequenceValue;
	}
}
