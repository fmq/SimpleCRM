package ar.com.easytech.simplecrm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.dto.CustomerInfoRow;

/**
 * Customer Service
 * 
 * @author FMQ
 * @version 1.0
 * 
 * Servicios utilizados para transaccionar con los clientes.
 */
@Stateless
public class CustomerService {

	private static final Logger logger = Logger.getLogger(CustomerService.class.toString());
	Map<String, Object> params = new HashMap<String, Object>();
	
	@Inject DaoFactory factory;
	@Inject SfaInfoService sfaInfoService;
	
	/** 
	 * createCustomer 
	 * @param customer: Customer entity
	 * @return long : Customer Id
	 * @throws ServiceException
	 * 
	 * Creates a customer from the entity passed.
	 */
	public long createCustomer(Customer customer) throws ServiceException {
		
		return factory.getCustomerDAO().createCustomer(customer);
	}
	
	public void updateCustomer(Customer customer) throws ServiceException {
		factory.getCustomerDAO().update(customer);
	}
	/**
	 * createQuickCustomer
	 * Creates a quick customer with the only required parameter 
	 * 
	 * @param customerName
	 * @return
	 * @throws ServiceException
	 * 
	 */
	public Customer createQuickCustomer( String customerName) throws ServiceException {
		
		Customer customer = new Customer();
		customer.setCustomerName(customerName);
		try {
			long id = factory.getCustomerDAO().createCustomer(customer);
			return customer;
		} catch (ServiceException _ex) {
			throw new ServiceException("createQuickCustomer",_ex.getLocalizedMessage());
		} catch (Exception _ex) {
			throw new ServiceException(_ex.getClass().getSimpleName(),"createQuickCustomer",_ex.getLocalizedMessage());
		}	
	}
	
	public List<CustomerInfoRow> getCustomerSummaryData() throws ServiceException {
		List<Customer> customers = getAllCustomers();
		List<CustomerInfoRow> result = new ArrayList<CustomerInfoRow>();
		for (Customer customer:customers) {
			CustomerInfoRow row = new CustomerInfoRow();
			customer.getAddresses().size();
			customer.getContacts().size();
			customer.getEmails().size();
			customer.getPhones().size();
			row.setCustomerData(customer);
			row.setForecastTotal(sfaInfoService.getForecastTotal(customer.getId()));
			row.setPipelimeTotal(sfaInfoService.getPipelineTotal(customer.getId()));
			result.add(row);
		}
		return result;
	}
	
	public Customer getCustomerById(long id) throws ServiceException {
		return factory.getCustomerDAO().getById(id);
	}
	
	public List<Customer> findCustomerByName(String searchValue) {
		return factory.getCustomerDAO().findByName(searchValue);
	}
	
	public List<Customer> getAllCustomers() throws ServiceException {
		return factory.getCustomerDAO().getAll();
	}
	
	public Customer getCustomerData(Customer customer) throws ServiceException {
		customer = factory.getCustomerDAO().getById(customer.getId());
		
		customer.getAddresses().size();
		customer.getContacts().size();
		customer.getEmails().size();
		customer.getNotes().size();
		customer.getPhones().size();
		return customer;
	}
	
	public void deleteCustomer(Customer customer) {
		factory.getCustomerDAO().delete(customer);
	}
	
	public Customer findCustomerByXeroId(String customerXeroId) throws ServiceException {
		params.put("xeroId", customerXeroId);
		List<Customer> customers = factory.getCustomerDAO().findByExactProperties(params);
		
		if (customers.size() == 0)
			throw new ServiceException("NO_DATA_FOUND","findCustomerByXeroId","Csutomer not found for XeroID");
		if (customers.size() > 1)
			logger.info("Too many rows found for customer search");
			//throw new ServiceException("TOO_MANY_ROWS","findCustomerByXeroId","Too many rows found for customer search");
		Customer customer =customers.get(0); 
		customer.getAddresses().size();
		customer.getContacts().size();
		customer.getEmails().size();
		customer.getNotes().size();
		customer.getPhones().size();
		return (customer);
		
	}
	
}
