package ar.com.easytech.simplecrm.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.easytech.simplecrm.model.enums.OpportunityStatus;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.OpportunityLine;

public class OpportunityInfoRow {

	private String opportunityName;
	private long opportunityId;
	
	private String customerName;
	private long customerId;
	
	private Date expectedCloseDate;
	private long winProbability;
	
	private BigDecimal totalAmount = new BigDecimal(0);
	private BigDecimal totalForecastAmount = new BigDecimal(0);
	
	public void setOpportunityData(Opportunity opportunity) {
		
		this.opportunityName = opportunity.getOpportunityName();
		this.opportunityId = opportunity.getId();
		this.customerName = opportunity.getCustomer().getCustomerName();
		this.customerId = opportunity.getCustomer().getId();
		this.expectedCloseDate = opportunity.getExpectedCloseDate();
		this.winProbability = opportunity.getWinProbability();
		List<OpportunityLine> lines = new ArrayList<OpportunityLine>(opportunity.getLines());
		for (OpportunityLine line : lines) {
			if (opportunity.getStatus().equals(OpportunityStatus.FORECAST)) {
				this.totalForecastAmount = this.totalForecastAmount.add(line.getAmount());
			} else if (opportunity.getStatus().equals(OpportunityStatus.OPEN)) {
				this.totalAmount = this.totalAmount.add(line.getAmount());
			}
		}
	}
	
	/* Getters & Setters */
	
	public String getOpportunityName() {
		return opportunityName;
	}

	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}

	public long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getExpectedCloseDate() {
		return expectedCloseDate;
	}

	public void setExpectedCloseDate(Date expectedCloseDate) {
		this.expectedCloseDate = expectedCloseDate;
	}

	public long getWinProbability() {
		return winProbability;
	}

	public void setWinProbability(long winProbability) {
		this.winProbability = winProbability;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalForecastAmount() {
		return totalForecastAmount;
	}

	public void setTotalForecastAmount(BigDecimal totalForecastAmount) {
		this.totalForecastAmount = totalForecastAmount;
	}
	
	public String getTotalAmountAsString() {
		return ((totalAmount.compareTo(new BigDecimal("1000")) < 0) ? totalAmount.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : totalAmount.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
	
	public String getTotalForecastAmountAsString() {
		return ((totalForecastAmount.compareTo(new BigDecimal("1000")) < 0) ? totalForecastAmount.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : totalForecastAmount.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
	
}
