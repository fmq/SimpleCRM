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
public class ProjectSortingBean extends SortManager implements Serializable {
   
   private static final long serialVersionUID = -6237417487105926855L;
   
   private SortOrder projectNumberOrder = SortOrder.unsorted;
   private SortOrder projectNameOrder = SortOrder.unsorted;
   
   @Override
   public void sortAction(ActionEvent ae) {
      super.sorter(ae, this);
   }

    public SortOrder getProjectNameOrder() {
        return projectNameOrder;
    }

    public void setProjectNameOrder(SortOrder projectNameOrder) {
        this.projectNameOrder = projectNameOrder;
    }

    public SortOrder getProjectNumberOrder() {
        return projectNumberOrder;
    }

    public void setProjectNumberOrder(SortOrder projectNumberOrder) {
        this.projectNumberOrder = projectNumberOrder;
    }

}
