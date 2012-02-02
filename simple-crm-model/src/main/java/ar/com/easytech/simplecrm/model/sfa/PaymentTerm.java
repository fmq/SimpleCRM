package ar.com.easytech.simplecrm.model.sfa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Null;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="sfa_payment_terms")
public class PaymentTerm extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 7526472395622774547L;

   public PaymentTerm() {
      super();
   }
   
   private String name;
   private String description;
   
   private int percentage;
   
   @Temporal(TemporalType.DATE)
   @Column(name="due_date")
   private Date dueDate;
   
   private BigInteger amount;

   public BigInteger getAmount() {
      return amount;
   }

   public void setAmount(BigInteger amount) {
      this.amount = amount;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Date getDueDate() {
      return dueDate;
   }

   public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPercentage() {
      return percentage;
   }

   public void setPercentage(int percentage) {
      this.percentage = percentage;
   }
   
}
