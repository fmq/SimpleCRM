package ar.com.easytech.simplecrm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.sfa.Proposal;

@Stateless
public class ProposalService {

	private static final Logger logger = Logger.getLogger(ProposalService.class.toString());
	Map<String, Object> params = new HashMap<String, Object>();
	
	@Inject DaoFactory factory;
	
	public long createProposal(Proposal proposal) throws ServiceException {
		return factory.getProposalDAO().createProposal(proposal);
	}
	
	public List<Proposal> getAllProposals() throws ServiceException {
		return factory.getProposalDAO().getAll();
	}
	
	public List<Proposal> getProposalsForCustomerId(long customerId) throws ServiceException {
		
		return factory.getProposalDAO().getForCustomerId(customerId);		
	}
	
	public Proposal getProposalById(long proposalId) throws ServiceException {
		return factory.getProposalDAO().getById(proposalId);
	}
	
	public void updateProposal(Proposal proposal) {
		factory.getProposalDAO().update(proposal);
	}
	
	public Proposal getProposalData(long proposalId) throws ServiceException {
		Proposal proposal = getProposalById(proposalId);
		proposal.getContacts().size();
		proposal.getCustomer();
		proposal.getNotes().size();
		proposal.getPaymentTerms().size();
		return proposal;
	}
}
