package ar.com.easytech.simplecrm.model.dto;

import ar.com.easytech.xero.integration.model.ReportCell;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author FMQ
 */
public class AgedReceivablesRow {

   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public AgedReceivablesRow(List<ReportCell> row) {
      // Adapto la fila a los atributos
      invoiceDate = getDateValue(row,0);
      dueDate = getDateValue(row,2);
      invoiceNumber = getStringValue(row,1);
      //3- 12:24:07,279 INFO  [class ar.com.easytech.simplecrm.service.beans.XeroServiceBean] (http--127.0.0.1-8081-1) null
      invoiceAmount = getNumberValue(row,4);
      
      invoiceCurrency = getStringValue(row, 5);
      paidAmount = getNumberValue(row,6);
      paidCurrency = getStringValue(row, 7);
      creditedAmount = getNumberValue(row,8);
      creditedCurrency = getStringValue(row, 9);
      dueAmount = getNumberValue(row,10);
      dueCurrency = getStringValue(row, 11);
      defCurrencyDueAmount = getNumberValue(row,12);
   }
   private Date invoiceDate;
   private String invoiceNumber;
   private Date dueDate;
   private BigDecimal invoiceAmount;
   private String invoiceCurrency;
   private BigDecimal paidAmount;
   private String paidCurrency;
   private BigDecimal creditedAmount;
   private String creditedCurrency;
   private BigDecimal dueAmount;
   private String dueCurrency;
   private BigDecimal defCurrencyDueAmount;

   public BigDecimal getCreditedAmount() {
      return creditedAmount;
   }

   public void setCreditedAmount(BigDecimal creditedAmount) {
      this.creditedAmount = creditedAmount;
   }

   public String getCreditedCurrency() {
      return creditedCurrency;
   }

   public void setCreditedCurrency(String creditedCurrency) {
      this.creditedCurrency = creditedCurrency;
   }

   public BigDecimal getDefCurrencyDueAmount() {
      return defCurrencyDueAmount;
   }

   public void setDefCurrencyDueAmount(BigDecimal defCurrencyDueAmount) {
      this.defCurrencyDueAmount = defCurrencyDueAmount;
   }

   public BigDecimal getDueAmount() {
      return dueAmount;
   }

   public void setDueAmount(BigDecimal dueAmount) {
      this.dueAmount = dueAmount;
   }

   public String getDueCurrency() {
      return dueCurrency;
   }

   public void setDueCurrency(String dueCurrency) {
      this.dueCurrency = dueCurrency;
   }

   public Date getDueDate() {
      return dueDate;
   }

   public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
   }

   public BigDecimal getInvoiceAmount() {
      return invoiceAmount;
   }

   public void setInvoiceAmount(BigDecimal invoiceAmount) {
      this.invoiceAmount = invoiceAmount;
   }

   public String getInvoiceCurrency() {
      return invoiceCurrency;
   }

   public void setInvoiceCurrency(String invoiceCurrency) {
      this.invoiceCurrency = invoiceCurrency;
   }

   public Date getInvoiceDate() {
      return invoiceDate;
   }

   public void setInvoiceDate(Date invoiceDate) {
      this.invoiceDate = invoiceDate;
   }

   public String getInvoiceNumber() {
      return invoiceNumber;
   }

   public void setInvoiceNumber(String invoiceNumber) {
      this.invoiceNumber = invoiceNumber;
   }

   public BigDecimal getPaidAmount() {
      return paidAmount;
   }

   public void setPaidAmount(BigDecimal paidAmount) {
      this.paidAmount = paidAmount;
   }

   public String getPaidCurrency() {
      return paidCurrency;
   }

   public void setPaidCurrency(String paidCurrency) {
      this.paidCurrency = paidCurrency;
   }
   
   private String getStringValue(List<ReportCell> row, int attribute) {
      try {
         return row.get(attribute).getValue();
      } catch (Exception _ex) {
         return null;
      }
   }
   private Date getDateValue(List<ReportCell> row, int attribute) {
      try {
         return df.parse(row.get(attribute).getValue().replace("T", " "));
      } catch (Exception _ex) {
         return null;
      }
   }
   private BigDecimal getNumberValue(List<ReportCell> row, int attribute) {
      try {
         return new BigDecimal(row.get(attribute).getValue());
      } catch (Exception _ex) {
         return new BigDecimal(0);
      }
   }
}
