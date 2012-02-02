package ar.com.easytech.simplecrm.web.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.dto.CustomerInfoRow;
import ar.com.easytech.simplecrm.model.dto.OpportunityInfoRow;
import ar.com.easytech.simplecrm.model.pa.Project;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.Proposal;
import ar.com.easytech.simplecrm.service.CustomerService;
import ar.com.easytech.simplecrm.service.OpportunityService;
import ar.com.easytech.simplecrm.service.ProjectService;
import ar.com.easytech.simplecrm.service.ProposalService;
import ar.com.easytech.simplecrm.web.action.sort.CustomerSortingBean;
import ar.com.easytech.simplecrm.web.action.sort.OpportunitySortingBean;
import ar.com.easytech.simplecrm.web.action.sort.ProjectSortingBean;
import ar.com.easytech.simplecrm.web.action.sort.ProposalSortingBean;
import ar.com.easytech.simplecrm.web.enums.TabType;
import ar.com.easytech.simplecrm.web.tabmodel.CustomerTab;
import ar.com.easytech.simplecrm.web.tabmodel.OpportunityTab;
import ar.com.easytech.simplecrm.web.tabmodel.ProjectTab;
import ar.com.easytech.simplecrm.web.tabmodel.ProposalTab;

/**
 * 
 * @author FMQ
 */
@Named
@SessionScoped
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private CustomerService customerService;
	@Inject private OpportunityService opportunityService;
	@Inject private ProposalService proposalService;
	@Inject private ProjectService projectService;

	@Inject private Logger logger;
	@Inject private TabsBean tabsBean;

	/* Objects */
	private List<CustomerInfoRow> customers = new ArrayList<CustomerInfoRow>();
	private List<OpportunityInfoRow> opportunities = new ArrayList<OpportunityInfoRow>();
	private List<Proposal> proposals = new ArrayList<Proposal>();
	private List<Project> projects = new ArrayList<Project>();

	/* Sorters */
	@Inject private OpportunitySortingBean opportunitiesSortingBean;
	@Inject private ProjectSortingBean projectSortingBean;
	@Inject private ProposalSortingBean proposalSortingBean;
	@Inject private CustomerSortingBean customerSortingBean;

	@PostConstruct
	public void init() {
		doRefresh();
	}

	public void doRefresh() {
		// Refreshes all the content. We trap each call so we can at least
		// partially refresh the page
		try {
			customers = customerService.getCustomerSummaryData();
		} catch (ServiceException ex) {
			logger.infov("Excepcion obteniendo datos del cliente: {0}",
					ex.getCompleteMessage());
		}

		try {
			opportunities = opportunityService.getOpportunitySummaryData();
		} catch (ServiceException ex) {
			logger.infov("Excepcion obteniendo datos de las oportunidades: {0}",
					ex.getCompleteMessage());
		}

		try {
			projects = projectService.getAllProjects();
		} catch (ServiceException ex) {
			logger.infov("Excepcion obteniendo datos de los proyectos: {0}",
					ex.getCompleteMessage());
		}

		try {
			proposals = proposalService.getAllProposals();
		} catch (ServiceException ex) {
			logger.infov("Excepcion obteniendo datos de las propuestas: {0}",
					ex.getCompleteMessage());
		}
	}

	public void showDetails(String tabType, long id) {
		logger.infov("TabType: {0}",tabType);
		
		switch (TabType.valueOf(tabType)) {
			case CUSTOMER:
				CustomerTab custTab = new CustomerTab();
				custTab.doRefresh(id);
				logger.info("Customer ID: " + custTab.getCustomer().getId());
				tabsBean.addTab(new TabContent<CustomerTab>(custTab,
						TabType.CUSTOMER));
				break;
			case OPPORTUNITY:
				OpportunityTab oppTab = new OpportunityTab();
				oppTab.doRefresh(id);
				tabsBean.addTab(new TabContent<OpportunityTab>(oppTab,TabType.OPPORTUNITY));
				break;
			case PROJECT:
				ProjectTab projectTab = new ProjectTab();
				projectTab.doRefresh(id);
				tabsBean.addTab(new TabContent<ProjectTab>(projectTab,TabType.PROJECT));
				break;
			case PROPOSAL:
				ProposalTab proposalTab = new ProposalTab();
				proposalTab.doRefresh(id);
				tabsBean.addTab(new TabContent<ProposalTab>(proposalTab,TabType.PROPOSAL));
				break;
		}
	}

	/*
	 * Getters & Seters
	 */

	public CustomerSortingBean getCustomerSortingBean() {
		return customerSortingBean;
	}

	public void setCustomerSortingBean(CustomerSortingBean customerSortingBean) {
		this.customerSortingBean = customerSortingBean;
	}

	public List<CustomerInfoRow> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerInfoRow> customers) {
		this.customers = customers;
	}

	public OpportunitySortingBean getOpportunitiesSortingBean() {
		return opportunitiesSortingBean;
	}

	public void setOpportunitiesSortingBean(
			OpportunitySortingBean opportunitiesSortingBean) {
		this.opportunitiesSortingBean = opportunitiesSortingBean;
	}

	public ProjectSortingBean getProjectSortingBean() {
		return projectSortingBean;
	}

	public void setProjectSortingBean(ProjectSortingBean projectSortingBean) {
		this.projectSortingBean = projectSortingBean;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public ProposalSortingBean getProposalSortingBean() {
		return proposalSortingBean;
	}

	public void setProposalSortingBean(ProposalSortingBean proposalSortingBean) {
		this.proposalSortingBean = proposalSortingBean;
	}

	public List<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(List<Proposal> proposals) {
		this.proposals = proposals;
	}

	public List<OpportunityInfoRow> getOpportunities() {
		return opportunities;
	}

	public void setOpportunities(List<OpportunityInfoRow> opportunities) {
		this.opportunities = opportunities;
	}
	
}
