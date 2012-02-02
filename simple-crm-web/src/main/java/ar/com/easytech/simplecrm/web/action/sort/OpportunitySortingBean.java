package ar.com.easytech.simplecrm.web.action.sort;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.richfaces.component.SortOrder;

/**
 *
 * @author FMQ
 */
@Named
@SessionScoped
public class OpportunitySortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417487105926855L;
   
   private SortOrder opportunityNumberOrder = SortOrder.unsorted;
   private SortOrder opportunityNameOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getOpportunityNameOrder() {
        return opportunityNameOrder;
    }

    public void setOpportunityNameOrder(SortOrder opportunityNameOrder) {
        this.opportunityNameOrder = opportunityNameOrder;
    }

    public SortOrder getOpportunityNumberOrder() {
        return opportunityNumberOrder;
    }

    public void setOpportunityNumberOrder(SortOrder opportunityNumberOrder) {
        this.opportunityNumberOrder = opportunityNumberOrder;
    }

}
