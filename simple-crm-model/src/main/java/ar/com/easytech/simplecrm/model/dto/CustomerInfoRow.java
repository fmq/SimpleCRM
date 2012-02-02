package ar.com.easytech.simplecrm.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ar.com.easytech.simplecrm.model.ca.Address;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.ca.Person;
import ar.com.easytech.simplecrm.model.ca.Phone;

public class CustomerInfoRow {

	private String customerName;
	private long customerNumber;
	private long customerId;
	
	private String primaryAddress;
	private long primaryAddressId;
	
	private String primaryPhoneNumber;
	private long primaryPhoneNumberId;
	
	private String primaryEmailAddress;
	private long primaryEmailAddressId;
	
	private String primaryContactName;
	private long primaryContactId;
	
	private BigDecimal invoiceDue;
	private BigDecimal invoicePaid;
	private BigDecimal forecastTotal;
	private BigDecimal pipelimeTotal;
	
	
	public void setCustomerData(Customer customer) {
		this.customerId = customer.getId();
		this.customerName = customer.getCustomerName();
		this.customerNumber = customer.getCustomerNumber();
		
		if (customer.getAddresses().size() >0) {
			List<Address> addresses = new ArrayList<Address>(customer.getAddresses());
			this.primaryAddress = addresses.get(0).getFormatedAddress();
			this.primaryAddressId = addresses.get(0).getId();
		}
		
		if (customer.getPhones().size() >0) {
			List<Phone> phones = new ArrayList<Phone>(customer.getPhones());
			this.primaryPhoneNumber = phones.get(0).getFormatedNumber();
			this.primaryPhoneNumberId = phones.get(0).getId();
		}
		
		if (customer.getContacts().size() > 0) {
			
			List<Person> contacts = new ArrayList<Person>(customer.getContacts());
			this.primaryContactName =  contacts.get(0).getLastName() + ", " +contacts.get(0).getFirstName();
			this.primaryContactId = contacts.get(0).getId();
			
		}
	}
	
	/* Getters & Setters */
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(long customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getPrimaryAddress() {
		return primaryAddress;
	}
	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}
	public long getPrimaryAddressId() {
		return primaryAddressId;
	}
	public void setPrimaryAddressId(long primaryAddressId) {
		this.primaryAddressId = primaryAddressId;
	}
	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}
	public long getPrimaryPhoneNumberId() {
		return primaryPhoneNumberId;
	}
	public void setPrimaryPhoneNumberId(long primaryPhoneNumberId) {
		this.primaryPhoneNumberId = primaryPhoneNumberId;
	}
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}
	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}
	public long getPrimaryEmailAddressId() {
		return primaryEmailAddressId;
	}
	public void setPrimaryEmailAddressId(long primaryEmailAddressId) {
		this.primaryEmailAddressId = primaryEmailAddressId;
	}
	public String getPrimaryContactName() {
		return primaryContactName;
	}
	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}
	public long getPrimaryContactId() {
		return primaryContactId;
	}
	public void setPrimaryContactId(long primaryContactId) {
		this.primaryContactId = primaryContactId;
	}
	public BigDecimal getInvoiceDue() {
		return invoiceDue;
	}
	public void setInvoiceDue(BigDecimal invoiceDue) {
		this.invoiceDue = invoiceDue;
	}
	public BigDecimal getInvoicePaid() {
		return invoicePaid;
	}
	public void setInvoicePaid(BigDecimal invoicePaid) {
		this.invoicePaid = invoicePaid;
	}
	public BigDecimal getForecastTotal() {
		return forecastTotal;
	}
	public void setForecastTotal(BigDecimal forecastTotal) {
		this.forecastTotal = forecastTotal;
	}
	public BigDecimal getPipelimeTotal() {
		return pipelimeTotal;
	}
	public void setPipelimeTotal(BigDecimal pipelimeTotal) {
		this.pipelimeTotal = pipelimeTotal;
	}
	
	public String getInvoiceDueAsString() {
		return ((invoiceDue.compareTo(new BigDecimal("1000")) < 0) ? invoiceDue.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : invoiceDue.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
	
	public String getInvoicePaidAsString() {
		return ((invoicePaid.compareTo(new BigDecimal("1000")) < 0) ? invoicePaid.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : invoicePaid.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
	
	public String getForecastTotalAsString() {
		return ((forecastTotal.compareTo(new BigDecimal("1000")) < 0) ? forecastTotal.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : forecastTotal.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
	
	public String getPipelimeTotalAsString() {
		return ((pipelimeTotal.compareTo(new BigDecimal("1000")) < 0) ? pipelimeTotal.setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() : pipelimeTotal.divide(new BigDecimal("1000")).setScale(0, BigDecimal.ROUND_HALF_EVEN).toString() + "K");
	}
}
