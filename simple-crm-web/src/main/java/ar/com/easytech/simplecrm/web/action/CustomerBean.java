package ar.com.easytech.simplecrm.web.action;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.service.CustomerService;

@Named
@ConversationScoped 
public class CustomerBean implements Serializable {

	private static final long serialVersionUID = 7209541172070288764L;
	
	@Inject private Conversation conversation;
	@Inject private CustomerService customerService;
	
	@Inject Logger logger;
	
	private Customer customer;
	
	public void beginConversation() {
        if (!FacesContext.getCurrentInstance().isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public void endConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    @PostConstruct
    public void init() {
        logger.info("Init called");
        try {
			customer = customerService.getAllCustomers().get(0);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void saveCustomer() {
		logger.info(customer.getTaxpayerId());
		try {
			if (customer.getId() != null && customer.getId() > 0)
				customerService.updateCustomer(customer);
			else
				customerService.createCustomer(customer);
			endConversation();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /* Getters & Setters */
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	} 	
}
