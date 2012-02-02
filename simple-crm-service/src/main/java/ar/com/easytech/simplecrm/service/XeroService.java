package ar.com.easytech.simplecrm.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.dto.AgedReceivablesRow;
import ar.com.easytech.xero.integration.model.CurrencyCode;
import ar.com.easytech.xero.integration.model.Invoice;

@Stateless
public class XeroService {

	private static final Logger logger = Logger.getLogger(XeroService.class
			.toString());

	@Inject
	private DaoFactory factory;
	
	public List<AgedReceivablesRow> getAgedReceivablesByCustomer(
			String xeroCustomerId) throws ServiceException {
		return factory.getXeroDAO().getAgedReceivablesByContact(
				xeroCustomerId);
	}

	public List<Invoice> getInvoicesForCustomer(String xeroCustomerId)
			throws ServiceException {
		return factory.getXeroDAO().getInvoicesForId(xeroCustomerId);
	}

	public List<Invoice> getUnpaidInvoicesForCustomer(String xeroCustomerId)
			throws ServiceException {
		return factory.getXeroDAO().getUnpaidInvoicesForId(xeroCustomerId);
	}

	public Map<String, Object> getDuePaidAmountsByCurrency(String xeroCustomerId)
			throws ServiceException {

		Map<String, Object> result = new HashMap<String, Object>();

		List<Invoice> data = factory.getXeroDAO().getInvoicesForId(
				xeroCustomerId);

		BigDecimal totalAmountPaidUsd = new BigDecimal(0);
		BigDecimal totalAmountDueUsd = new BigDecimal(0);
		BigDecimal totalAmountPaidArs = new BigDecimal(0);
		BigDecimal totalAmountDueArs = new BigDecimal(0);

		for (Invoice row : data) {
			if (row.getCurrencyCode().equals(CurrencyCode.ARS)) {
				totalAmountDueArs = totalAmountDueArs.add(row.getAmountDue());
				totalAmountPaidArs = totalAmountPaidArs
						.add(row.getAmountPaid());
			} else if (row.getCurrencyCode().equals(CurrencyCode.USD)) {
				totalAmountDueUsd = totalAmountDueUsd.add(row.getAmountDue());
				totalAmountPaidUsd = totalAmountPaidUsd
						.add(row.getAmountPaid());
			}
		}

		result.put("AMOUNT_DUE_ARS", totalAmountDueArs);
		result.put("AMOUNT_DUE_USD", totalAmountDueUsd);
		result.put("AMOUNT_PAID_ARS", totalAmountPaidArs);
		result.put("AMOUNT_PAID_USD", totalAmountPaidUsd);

		return result;
	}

	/**
	 * Returns Xero invoices for a given customer.
	 * 
	 * @param customerId
	 *            Customer id to get data from
	 * @return List of Xero invoices
	 * @throws ServiceException
	 */
	public List<Invoice> getXeroInvoicesForCustomer(long customerId)
			throws ServiceException {

		Customer customer = factory.getCustomerDAO().getById(customerId);

		if (customer == null) {
			throw new ServiceException("NO_DATA_FOUND",
					"getXeroInvoicesForCustomer", "Customer with id: "
							+ customerId + " not found");
		}

		if (customer.getXeroId() == null && customer.getXeroId().equals("")) {
			throw new ServiceException("INVALID_XERO_ID",
					"getXeroInvoicesForCustomer", "Customer with id: "
							+ customerId + " is not linked with xero");
		}

		return getInvoicesForCustomer(customer.getXeroId());
	}

	/**
	 * Return unpaid xeroInvoices for a given customer
	 * 
	 * @param customerId
	 *            Customer id to get data from
	 * @return List of Xero invoices
	 * @throws ServiceException
	 */
	public List<Invoice> getUnpaidXeroInvoicesForCustomer(long customerId)
			throws ServiceException {

		Customer customer = factory.getCustomerDAO().getById(customerId);

		if (customer == null) {
			throw new ServiceException("NO_DATA_FOUND",
					"getXeroInvoicesForCustomer", "Customer with id: "
							+ customerId + " not found");
		}

		if (customer.getXeroId() == null && customer.getXeroId().equals("")) {
			throw new ServiceException("INVALID_XERO_ID",
					"getXeroInvoicesForCustomer", "Customer with id: "
							+ customerId + " is not linked with xero");
		}

		return getUnpaidInvoicesForCustomer(customer.getXeroId());
	}
}
