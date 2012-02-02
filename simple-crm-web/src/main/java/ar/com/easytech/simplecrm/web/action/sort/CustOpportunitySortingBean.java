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
public class CustOpportunitySortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417487105926855L;
   
   private SortOrder custOpportunityNumberOrder = SortOrder.unsorted;
   private SortOrder custOpportunityNameOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getCustOpportunityNameOrder() {
        return custOpportunityNameOrder;
    }

    public void setCustOpportunityNameOrder(SortOrder custOpportunityNameOrder) {
        this.custOpportunityNameOrder = custOpportunityNameOrder;
    }

    public SortOrder getCustOpportunityNumberOrder() {
        return custOpportunityNumberOrder;
    }

    public void setCustOpportunityNumberOrder(SortOrder custOpportunityNumberOrder) {
        this.custOpportunityNumberOrder = custOpportunityNumberOrder;
    }

    
}
