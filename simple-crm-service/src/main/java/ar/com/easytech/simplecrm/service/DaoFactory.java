package ar.com.easytech.simplecrm.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.inject.New;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.com.easytech.simplecrm.dao.CustomerDAO;
import ar.com.easytech.simplecrm.dao.OpportunityDAO;
import ar.com.easytech.simplecrm.dao.ProjectDAO;
import ar.com.easytech.simplecrm.dao.ProposalDAO;
import ar.com.easytech.simplecrm.dao.SysDAO;
import ar.com.easytech.simplecrm.dao.XeroDAO;
import ar.com.easytech.simplecrm.ifaces.CurrentUser;
import ar.com.easytech.simplecrm.model.sys.SysUser;

@Stateless
public class DaoFactory {

	@PersistenceContext
	EntityManager em;
	
	@Inject @CurrentUser
	SysUser currentUser;
	
	Logger logger = Logger.getLogger(DaoFactory.class.toString());
	
	public CustomerDAO getCustomerDAO() {
		logger.info("XXXXXXXXXXXXXXXXXXXX -> " + currentUser.getId());
		return new CustomerDAO(em);
	}
	
	public OpportunityDAO getOpportunityDAO() {
		return new OpportunityDAO(em);
	}
	
	public ProjectDAO getProjectDAO() {
		return new ProjectDAO(em);
	}
	
	public ProposalDAO getProposalDAO() {
		return new ProposalDAO(em);
	}
	
	public SysDAO getSysDAO() {
		return new SysDAO();
	}
	
	public XeroDAO getXeroDAO() {
		return new XeroDAO();
	}
	
}
