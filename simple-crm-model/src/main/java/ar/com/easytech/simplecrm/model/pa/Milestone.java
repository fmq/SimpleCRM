package ar.com.easytech.simplecrm.model.pa;

import ar.com.easytech.simplecrm.model.AbstractEntity;
import ar.com.easytech.simplecrm.model.enums.YesNo;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author FMQ
 * Representa un hito de un proyecto
 * Puede o no ser facturable.
 * Si es facturable se permite la creación de una factura en el Xero(de estar
 * Integrado)
 * 
 */
@Entity
@Table(name="pa_milestone")
public class Milestone extends AbstractEntity implements Serializable {
   
   private static final long serialVersionUID = 7526472395622774547L;

   public Milestone() {
      super();
   }
   
   
   private String name;
   private String description;
   
   private int expectedDuration;
   private int durationDuration;
   
   @Temporal(TemporalType.DATE)
   @Column(name="planned_start_date")
   private Date plannedStartDate;
   
   @Temporal(TemporalType.DATE)
   @Column(name="start_date")
   private Date startDate;
   
   @Temporal(TemporalType.DATE)
   @Column(name="end_date")
   private Date endDate;
   
   @Temporal(TemporalType.DATE)
   @Column(name="planned_end_date")
   private Date plannedEndDate;
   
   private int percentage;
   private BigInteger billableAmount;

   @Column(name="billable_flag")
   @Enumerated(EnumType.STRING)
   private YesNo billableFlag;

   public BigInteger getBillableAmount() {
      return billableAmount;
   }

   public void setBillableAmount(BigInteger billableAmount) {
      this.billableAmount = billableAmount;
   }

   public YesNo getBillableFlag() {
      return billableFlag;
   }

   public void setBillableFlag(YesNo billableFlag) {
      this.billableFlag = billableFlag;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public int getDurationDuration() {
      return durationDuration;
   }

   public void setDurationDuration(int durationDuration) {
      this.durationDuration = durationDuration;
   }

   public Date getEndDate() {
      return endDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public int getExpectedDuration() {
      return expectedDuration;
   }

   public void setExpectedDuration(int expectedDuration) {
      this.expectedDuration = expectedDuration;
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

   public Date getPlannedEndDate() {
      return plannedEndDate;
   }

   public void setPlannedEndDate(Date plannedEndDate) {
      this.plannedEndDate = plannedEndDate;
   }

   public Date getPlannedStartDate() {
      return plannedStartDate;
   }

   public void setPlannedStartDate(Date plannedStartDate) {
      this.plannedStartDate = plannedStartDate;
   }

   public Date getStartDate() {
      return startDate;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }
   
}
