package ar.com.easytech.simplecrm.web.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import ar.com.easytech.simplecrm.service.ProjectService;
import ar.com.easytech.simplecrm.service.ProposalService;
import ar.com.easytech.simplecrm.service.XeroService;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.OpportunityService;

/*
 * @Author: FMQ
 */
public class EjbUtil {

	protected static final String CUSTOMER_SERVICE_BEAN_JNDI = "java:app/simple-crm-service/CustomerService";
	protected static final String OPPORTUNITY_SERVICE_BEAN_JNDI = "java:app/simple-crm-service/OpportunityService";
	protected static final String PROJECT_SERVICE_BEAN_JNDI = "java:app/simple-crm-service/ProjectService";
	protected static final String PROPOSAL_SERVICE_BEAN_JNDI = "java:app/simple-crm-service/ProposalService";
	protected static final String XERO_SERVICE_BEAN_JNDI = "java:app/simple-crm-service/XeroService";
	
	private static CustomerService customerService;
	private static OpportunityService opportunityService;
	private static ProjectService projectService;
	private static ProposalService proposalService;
    private static XeroService xeroService;
	
	public static CustomerService retrieveCustomerService() {
		if (customerService == null) {
			synchronized (CustomerService.class) {
				if (customerService == null) {
					try {
						Context context = new InitialContext();
						return (CustomerService) context.lookup(CUSTOMER_SERVICE_BEAN_JNDI);
					} catch (NamingException ex) {
						Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return null;
	}
	
	public static OpportunityService retrieveOpportnityService() {
		if (opportunityService == null) {
			synchronized (OpportunityService.class) {
				if (opportunityService == null) {
					try {
						Context context = new InitialContext();
						return (OpportunityService) context.lookup(OPPORTUNITY_SERVICE_BEAN_JNDI);
					} catch (NamingException ex) {
						Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return null;
	}
	
	public static ProjectService retrieveProjectService() {
		if (projectService == null) {
			synchronized (ProjectService.class) {
				if (projectService == null) {
					try {
						Context context = new InitialContext();
						return (ProjectService) context.lookup(PROJECT_SERVICE_BEAN_JNDI);
					} catch (NamingException ex) {
						Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return null;
	}
	
	public static ProposalService retrieveProposalService() {
		if (proposalService == null) {
			synchronized (ProposalService.class) {
				if (proposalService == null) {
					try {
						Context context = new InitialContext();
						return (ProposalService) context.lookup(PROPOSAL_SERVICE_BEAN_JNDI);
					} catch (NamingException ex) {
						Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return null;
	}
        
	public static XeroService retrieveXeroService() {
		if (xeroService == null) {
			synchronized (XeroService.class) {
				if (xeroService == null) {
					try {
						Context context = new InitialContext();
						return (XeroService) context.lookup(XERO_SERVICE_BEAN_JNDI);
					} catch (NamingException ex) {
						Logger.getLogger(EjbUtil.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}
		return null;
	}
}
