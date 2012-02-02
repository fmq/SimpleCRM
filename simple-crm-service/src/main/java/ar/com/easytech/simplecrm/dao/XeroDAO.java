package ar.com.easytech.simplecrm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ar.com.easytech.simplecrm.common.exceptions.ServiceException;
import ar.com.easytech.simplecrm.model.dto.AgedReceivablesRow;
import ar.com.easytech.xero.integration.XeroClientServices;
import ar.com.easytech.xero.integration.model.Invoice;
import ar.com.easytech.xero.integration.model.Report;
import ar.com.easytech.xero.integration.model.ReportCell;
import ar.com.easytech.xero.integration.model.ReportRow;
import ar.com.easytech.xero.integration.model.ReportRowType;
import ar.com.easytech.xero.integration.util.ParamBuilder;

/**
 *
 * @author FMQ
 */
public class XeroDAO {

    private static final Logger logger = Logger.getLogger(XeroDAO.class.toString());

    public List<Invoice> getInvoicesForId(String xeroCustomerId) throws ServiceException {

        try {
            ParamBuilder paramBuilder = new ParamBuilder();
            paramBuilder.append("where=Contact.ContactID");
            paramBuilder.addSpace();
            paramBuilder.addEquals();
            paramBuilder.addSpace();
            paramBuilder.append("Guid");
            paramBuilder.encloseAndQuote(xeroCustomerId);

            return XeroClientServices.executeGet("Invoices", paramBuilder.getParams()).getInvoices().getInvoice();
        } catch (Exception _ex) {
            throw new ServiceException(_ex.getClass().getName(), "getInvoicesForId", _ex.getLocalizedMessage());
        }

    }

    public List<Invoice> getUnpaidInvoicesForId(String xeroCustomerId) throws ServiceException {

        try {
            ParamBuilder paramBuilder = new ParamBuilder();
            paramBuilder.append("where=Contact.ContactID");
            paramBuilder.addEquals();
            paramBuilder.append("Guid");
            paramBuilder.encloseAndQuote(xeroCustomerId);
            paramBuilder.addAnd();
            paramBuilder.append("AmountDue");
            paramBuilder.addLtGt();
            paramBuilder.append("0");

            return XeroClientServices.executeGet("Invoices", paramBuilder.getParams()).getInvoices().getInvoice();
        } catch (Exception _ex) {
            throw new ServiceException(_ex.getClass().getName(), "getUnpaidInvoicesForId", _ex.getLocalizedMessage());
        }
    }

    public List<AgedReceivablesRow> getAgedReceivablesByContact(String xeroCustomerId) throws ServiceException {
        List<AgedReceivablesRow> rows = new ArrayList<AgedReceivablesRow>();

        try {
            ParamBuilder paramBuilder = new ParamBuilder();
            paramBuilder.append("contactID=");
            paramBuilder.append(xeroCustomerId);
            List<Report> rpts = XeroClientServices.executeGet("Reports/AgedReceivablesByContact", paramBuilder.getParams()).getReports().getReport();



            if (rpts.size() > 0) {
                Report rpt = rpts.get(0);
                for (ReportRow section : rpt.getRows().getRow()) {
                    if (section.getRowType() != null && section.getRowType().equals(ReportRowType.SECTION)) {
                        for (ReportRow row : section.getRows().getRow()) {
                            if (row.getRowType() != null && row.getRowType().equals(ReportRowType.ROW)) {
                                List<ReportCell> dRow = row.getCells().getCell();
                                rows.add(new AgedReceivablesRow(dRow));
                            }
                        }
                    }
                }
            }
        } catch (Exception _ex) {
            throw new ServiceException(_ex.getClass().getName(), "getAgedReceivablesByContact", _ex.getLocalizedMessage());
        }
        return rows;
    }
    
}
