package ar.com.easytech.simplecrm.web.tabmodel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.ca.Customer;
import ar.com.easytech.simplecrm.model.pa.Project;
import ar.com.easytech.simplecrm.model.sfa.Opportunity;
import ar.com.easytech.simplecrm.model.sfa.Proposal;
import ar.com.easytech.simplecrm.web.action.sort.CustomerSortingBean;
import ar.com.easytech.simplecrm.web.action.sort.InvoiceSortingBean;
import ar.com.easytech.simplecrm.web.action.sort.OpportunitySortingBean;
import ar.com.easytech.simplecrm.web.action.sort.ProjectSortingBean;
import ar.com.easytech.simplecrm.web.action.sort.ProposalSortingBean;
import ar.com.easytech.simplecrm.web.util.EjbUtil;
import ar.com.easytech.xero.integration.model.Invoice;


/**
 *
 * @author FMQ
 */
public class CustomerTab extends BaseTabObject<Customer> implements
        Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CustomerTab.class.toString());
    private CustomerSortingBean sortingBean;

    /*
     * Related Objects Sorters
     */
    private OpportunitySortingBean opportunitiesSortingBean;
    private ProjectSortingBean projectSortingBean;
    private ProposalSortingBean proposalSortingBean;
    private InvoiceSortingBean unpaidInvoicesSortingBean;
    private Customer customer;

    /*
     * Related Objects
     */
    private List<Invoice> unpaidInvoices = new ArrayList<Invoice>();
    private List<Opportunity> opportunities = new ArrayList<Opportunity>();
    private List<Proposal> proposals = new ArrayList<Proposal>();
    private List<Project> projects = new ArrayList<Project>();

    public void doRefresh(long customerId) {
        init();
        try {
            customer = EjbUtil.retrieveCustomerService().getCustomerById(customerId);
            customer = EjbUtil.retrieveCustomerService().getCustomerData(customer);
            this.setEntity(customer);
        } catch (ServiceException ex) {
            logger.log(Level.INFO, "Excepcion obteniendo datos del cliente: {0}", ex.getCompleteMessage());
        }

        try {
            // Get customer Data
            unpaidInvoices = EjbUtil.retrieveXeroService().getUnpaidXeroInvoicesForCustomer(customerId);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            logger.log(Level.INFO, "Excepcion obteniendo datos del cliente: {0}", ex.getCompleteMessage());
        }

        try {
            opportunities = EjbUtil.retrieveOpportnityService().findOpportunituesForCustomer(customerId);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            logger.log(Level.INFO, "Excepcion obteniendo datos del cliente: {0}", ex.getCompleteMessage());
        }

        try {
            proposals = EjbUtil.retrieveProposalService().getProposalsForCustomerId(customerId);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            logger.log(Level.INFO, "Excepcion obteniendo datos del cliente: {0}", ex.getCompleteMessage());
        }

        try {
            projects = EjbUtil.retrieveProjectService().getProjectsForCustomerId(customerId);
        } catch (ServiceException ex) {
            ex.printStackTrace();
            logger.log(Level.INFO, "Excepcion obteniendo datos del cliente: {0}", ex.getCompleteMessage());
        }
    }

    public void init() {
        tabContent = "customer/customerDetailsRG.xhtml";
    }
    
    public void generatePieChart(OutputStream out, Object data) {
        try {
            
            
            Map<String, Object> pieData = EjbUtil.retrieveXeroService().getDuePaidAmountsByCurrency(customer.getXeroId());
            
            DefaultPieDataset dataset = new DefaultPieDataset();
            
            for (String key  : pieData.keySet()) {
                logger.log(Level.INFO,"Valor: {0} ",(BigDecimal)pieData.get(key));
                dataset.setValue(key, (BigDecimal)pieData.get(key));
            }
            
            JFreeChart chart = ChartFactory.createPieChart("Facturas", dataset, true, true, Locale.ENGLISH);
                    

            BufferedImage buffImg = chart.createBufferedImage(
                                        500, //width
                                        375, //height
                                        BufferedImage.TYPE_INT_RGB, //image type
                                        null);

            ImageIO.write(buffImg, "jpeg", out);
            
        } catch (ServiceException ex) {
            Logger.getLogger(CustomerTab.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }
    /*
     * Getters & Seters
     */

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }

    public OpportunitySortingBean getOpportunitiesSortingBean() {
        return opportunitiesSortingBean;
    }

    public void setOpportunitiesSortingBean(
            OpportunitySortingBean opportunitiesSortingBean) {
        this.opportunitiesSortingBean = opportunitiesSortingBean;
    }

    public ProjectSortingBean getProjectSortingBean() {
        return projectSortingBean;
    }

    public void setProjectSortingBean(ProjectSortingBean projectSortingBean) {
        this.projectSortingBean = projectSortingBean;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public ProposalSortingBean getProposalSortingBean() {
        return proposalSortingBean;
    }

    public void setProposalSortingBean(ProposalSortingBean proposalSortingBean) {
        this.proposalSortingBean = proposalSortingBean;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public CustomerSortingBean getSortingBean() {
        return sortingBean;
    }

    public void setSortingBean(CustomerSortingBean sortingBean) {
        this.sortingBean = sortingBean;
    }

    public List<Invoice> getUnpaidInvoices() {
        return unpaidInvoices;
    }

    public void setUnpaidInvoices(List<Invoice> unpaidInvoices) {
        this.unpaidInvoices = unpaidInvoices;
    }

    public InvoiceSortingBean getUnpaidInvoicesSortingBean() {
        return unpaidInvoicesSortingBean;
    }

    public void setUnpaidInvoicesSortingBean(
            InvoiceSortingBean unpaidInvoicesSortingBean) {
        this.unpaidInvoicesSortingBean = unpaidInvoicesSortingBean;
    }
}
