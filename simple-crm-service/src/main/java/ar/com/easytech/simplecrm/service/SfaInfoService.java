package ar.com.easytech.simplecrm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.enums.OpportunityStatus;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.OpportunityLine;

@Stateless
public class SfaInfoService {
	
	@Inject DaoFactory factory;
	
	public BigDecimal getPipelineTotal(long customerId) throws ServiceException {
		// Busco las oportunidades por cliente que esten en estado de pipeline
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customerId);
		params.put("status", OpportunityStatus.OPEN);
		List<Opportunity> opportunities = factory.getOpportunityDAO().findByExactProperties(params);
		
		BigDecimal total =new BigDecimal("0");
		
		for (Opportunity opportunity : opportunities) {
			// Busco las lineas
			List<OpportunityLine> lines = new ArrayList<OpportunityLine>(opportunity.getLines());
			for (OpportunityLine line : lines)
				total = total.add(line.getAmount());
		}
		return total;
	}
	
	public BigDecimal getForecastTotal(long customerId) throws ServiceException {
		// Busco las oportunidades por cliente que esten en estado de pipeline
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customerId);
		params.put("status", OpportunityStatus.FORECAST);
		List<Opportunity> opportunities = factory.getOpportunityDAO().findByExactProperties(params);
		
		BigDecimal total =new BigDecimal("0");
		
		for (Opportunity opportunity : opportunities) {
			// Busco las lineas
			List<OpportunityLine> lines = new ArrayList<OpportunityLine>(opportunity.getLines());
			for (OpportunityLine line : lines)
				total = total.add(line.getAmount());
		}
		return total;
	}
}
