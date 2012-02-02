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
public class InvoiceSortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417487105926855L;
   
   private SortOrder invoiceNumberOrder = SortOrder.unsorted;
   private SortOrder totalOrder = SortOrder.unsorted;
   private SortOrder currencyCodeOrder = SortOrder.unsorted;
   private SortOrder statusOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getCurrencyCodeOrder() {
        return currencyCodeOrder;
    }

    public void setCurrencyCodeOrder(SortOrder currencyCodeOrder) {
        this.currencyCodeOrder = currencyCodeOrder;
    }

    public SortOrder getInvoiceNumberOrder() {
        return invoiceNumberOrder;
    }

    public void setInvoiceNumberOrder(SortOrder invoiceNumberOrder) {
        this.invoiceNumberOrder = invoiceNumberOrder;
    }

    public SortOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(SortOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public SortOrder getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(SortOrder totalOrder) {
        this.totalOrder = totalOrder;
    }

   
}
