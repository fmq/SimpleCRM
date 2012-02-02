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
public class CustomerSortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417487105926855L;
   
   private SortOrder customerNumberOrder = SortOrder.unsorted;
   private SortOrder customerNameOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getCustomerNameOrder() {
        return customerNameOrder;
    }

    public void setCustomerNameOrder(SortOrder customerNameOrder) {
        this.customerNameOrder = customerNameOrder;
    }

    public SortOrder getCustomerNumberOrder() {
        return customerNumberOrder;
    }

    public void setCustomerNumberOrder(SortOrder customerNumberOrder) {
        this.customerNumberOrder = customerNumberOrder;
    }

}
