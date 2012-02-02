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
public class ProposalSortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417483405926855L;
   
   private SortOrder proposalNumberOrder = SortOrder.unsorted;
   private SortOrder proposalNameOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getProposalNameOrder() {
        return proposalNameOrder;
    }

    public void setProposalNameOrder(SortOrder proposalNameOrder) {
        this.proposalNameOrder = proposalNameOrder;
    }

    public SortOrder getProposalNumberOrder() {
        return proposalNumberOrder;
    }

    public void setProposalNumberOrder(SortOrder proposalNumberOrder) {
        this.proposalNumberOrder = proposalNumberOrder;
    }

}
