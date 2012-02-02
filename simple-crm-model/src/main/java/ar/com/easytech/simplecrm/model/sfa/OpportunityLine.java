package ar.com.easytech.simplecrm.model.sfa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.enums.CurrencyCode;
import ar.com.easytech.simplecrm.model.enums.StatusTypes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author FMQ
 */
@Entity
@Table(name="sfa_opportunity_lines")
public class OpportunityLine extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 7526472325622772147L;

   public OpportunityLine() {
      super();
   }
   
   private String item;
   private int quantity;
   private BigDecimal amount;
   
   @Enumerated(EnumType.STRING)
   private CurrencyCode currencyCode;
   
   public String getItem() {
      return item;
   }

   public void setItem(String item) {
      this.item = item;
   }
   
   public CurrencyCode getCurrencyCode() {
      return currencyCode;
   }

   public BigDecimal getAmount() {
	return amount;
   }

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setCurrencyCode(CurrencyCode currencyCode) {
	      this.currencyCode = currencyCode;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   
   @Override
   public String toString() {
      return "opportunityLine:"+ getId() + ":Item:" + getItem();
   }
   
}
