package ar.com.easytech.simplecrm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.dto.OpportunityInfoRow;
import ar.com.easytech.simplecrm.model.enums.OpportunityStatus;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.OpportunityLine;

/**
 * Service to get opportunity data
 * @author facundo
 * @version 1.0
 */
@Stateless
public class OpportunityService {

	@Inject DaoFactory factory;
	@Inject CustomerService customerService;
	
	public long createOpportunity(Opportunity opportunity) throws ServiceException {
		return factory.getOpportunityDAO().createOpportunity(opportunity); 
	}
	
	public long createQuickOpportunity(String opportunityName, long customerId) throws ServiceException {
		Customer customer = customerService.getCustomerById(customerId);
		
		Opportunity opportunity = new Opportunity();
		opportunity.setOpportunityName(opportunityName);
		opportunity.setCustomer(customer);
		return createOpportunity(opportunity);
	}
	
	public Opportunity getOpportunityById(long opportunityId) throws ServiceException {
		return factory.getOpportunityDAO().getById(opportunityId);
	}
	
	public Opportunity getOpportunityData(long opportunityId) throws ServiceException {
		Opportunity opportunity = getOpportunityById(opportunityId);
		opportunity.getContacts().size();
		opportunity.getLines().size();
		opportunity.getNotes().size();
		opportunity.getProposals().size();
		opportunity.getCustomer();
		
		return opportunity;
	}
	
	public void updateOpportunity(Opportunity opportunity) throws ServiceException {
		factory.getOpportunityDAO().update(opportunity);	
	}
	
	public void deleteOpportunity(Opportunity opportunity) {
		factory.getOpportunityDAO().delete(opportunity);
	}
	
	public List<Opportunity> getAllOpportunites() throws ServiceException{
		return factory.getOpportunityDAO().getAll();
	}
	
	public List<Opportunity> findOpportunituesForCustomer(long customerId) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customerId);
		return factory.getOpportunityDAO().findByExactProperties(params);
	}
	
	public List<OpportunityInfoRow> getOpportunitySummaryData() throws ServiceException {
		List<OpportunityInfoRow> rows = new ArrayList<OpportunityInfoRow>();
		for (Opportunity opportunity : getAllOpportunites()) {
			OpportunityInfoRow infoRow = new OpportunityInfoRow();
			opportunity.getCustomer();
			opportunity.getLines().size();
			infoRow.setOpportunityData(opportunity);
			rows.add(infoRow);
		}
		
		return rows;
	}
	
}
