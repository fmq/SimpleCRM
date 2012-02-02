package ar.com.easytech.simplecrm.web.tabmodel;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.web.action.sort.OpportunitySortingBean;
import ar.com.easytech.simplecrm.web.util.EjbUtil;

/**
 *
 * @author FMQ
 */
public class OpportunityTab extends BaseTabObject<Opportunity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = Logger.getLogger(OpportunityTab.class.toString());
    private Opportunity opportunity;
    private OpportunitySortingBean opportunitiesSortingBean;
    
    public void doRefresh(long opportunityId) {
    	init();
        try {
            opportunity = EjbUtil.retrieveOpportnityService().getOpportunityById(opportunityId);
            setEntity(opportunity);
        } catch (ServiceException ex) {
            logger.info("Error getting oportunity");
        }
    }
    
    public void init() {
    	tabContent = "opportunity/opportunityDetailsRG.xhtml";
    }
    /*
     * Getters & Seters
     */

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }

	public OpportunitySortingBean getOpportunitiesSortingBean() {
		return opportunitiesSortingBean;
	}

	public void setOpportunitiesSortingBean(
			OpportunitySortingBean opportunitiesSortingBean) {
		this.opportunitiesSortingBean = opportunitiesSortingBean;
	}
    
}
