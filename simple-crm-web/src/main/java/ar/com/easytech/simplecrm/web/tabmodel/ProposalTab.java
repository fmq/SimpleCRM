package ar.com.easytech.simplecrm.web.tabmodel;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.sfa.Proposal;
import ar.com.easytech.simplecrm.web.action.sort.ProposalSortingBean;
import ar.com.easytech.simplecrm.web.util.EjbUtil;

/**
 *
 * @author FMQ
 */
public class ProposalTab extends BaseTabObject<Proposal> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(OpportunityTab.class.toString());
    private ProposalSortingBean proposalSortingBean;
    
    private Proposal proposal;
    
    public void doRefresh(long proposalId) {
    	init();
        try {
            proposal = EjbUtil.retrieveProposalService().getProposalById(proposalId);
            setEntity(proposal);
        } catch (ServiceException ex) {
            logger.info("No data found");
        }
    }
    
    public void init() {
    	tabContent = "proposal/proposalDetailsRG.xhtml";
    }
    /*
     * Getters & Seters
     */

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

	public ProposalSortingBean getProposalSortingBean() {
		return proposalSortingBean;
	}

	public void setProposalSortingBean(ProposalSortingBean proposalSortingBean) {
		this.proposalSortingBean = proposalSortingBean;
	}
    
    
}
