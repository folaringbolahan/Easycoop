/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.utility;

import com.sift.admin.interfaces.Definitions;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.EasyCoopFinPersistentManager;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.ProductAccount;
import com.sift.easycoopfin.models.ProductAccountCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsAccount;
import com.sift.easycoopfin.models.SavingsCriteria;
import com.sift.easycoopfin.models.TxnCode;
import com.sift.easycoopfin.models.TxnCodeCriteria;
import com.sift.gl.GendataService;
import com.sift.gl.NewSerialno;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Txnsheader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.sift.gl.model.SMSBean;

/**
 *
 * @author logzy
 */
public class GlPostCron extends QuartzJobBean {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(GlPostCron.class);
    private DAOFactory daoFactory;
    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String output;
    private String dpwd;
    private String dname;
    HelperUtil util = new HelperUtil();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            doBulkGlPosting();
        } catch (Exception e) {
            _logger.error("SavingsCron", e);
            e.printStackTrace();
        }

    }

    public void doBulkGlPosting() {

        PersistentSession session = null;
        SavingsCriteria criteria;
        byte isProcessed = 1;
        byte productStatus = 0;
        List<Savings> processedSavings = null;
        boolean status = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String referenceNumber = String.valueOf(System.currentTimeMillis());
        String controlAccount = null;

        int period;
        int year;
        String timezone;
        Date trx_date = null;
        String serial = "";
        Date entrydate = null;
        String accountCode = "SRC";
        Date postDate;
        try {
            criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("isApproved", isProcessed));
            criteria.add(Restrictions.eq("status", productStatus));

            processedSavings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingsByCriteria(criteria);


            for (Savings savings : processedSavings) {
                System.out.println("Savings amount: " + savings.getAmount());
                Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().getProductsByORMID(savings.getProductId());
                session = com.sift.easycoopfin.models.EasyCoopFinPersistentManager.instance().getSession();
                SQLQuery query = session.createSQLQuery("SELECT b.CurrentYear, b.CurrentPeriod,ct.TIMEZ,b.PostDate FROM company c join countries as ct on ct.id=c.country_id join branch as b on b.company_id=c.id where c.id = '" + savings.getCompanyId() + "' and b.id='" + savings.getBranchId() + "'");

                List<Object[]> rows = query.list();
                Object[] row = rows.get(0);
                year = Integer.valueOf(row[0].toString());
                period = Integer.valueOf(row[1].toString());
                timezone = row[2].toString();
                entrydate = HelperUtil.getCurrentDateByTimezone(timezone);

                //String reformattedStr = myFormat.format(fromUser.parse(row[3].toString()));
                postDate = myFormat.parse(row[3].toString());
                ProductAccountCriteria accountCriteria = new ProductAccountCriteria();
                accountCriteria.add(Restrictions.eq("productId", savings.getProductId()));
                accountCriteria.add(Restrictions.eq("productAccountTypeCode", accountCode));

                TxnCodeCriteria txnCriteria = new TxnCodeCriteria();
                txnCriteria.add(Restrictions.eq("transactionCode", "CAD"));

                TxnCode txnCode = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getTxnCodeDAO().loadTxnCodeByCriteria(txnCriteria);
                ProductAccount account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductAccountDAO().loadProductAccountByCriteria(accountCriteria);
                //String narrative = String.format(txnCode.getNarrative(), savings.getAccountNumber(), period, year);
                String tempNarration = "" + savings.getAccountNumber() + ", " +savings.getDescription();
                String narrative = String.format(txnCode.getNarrative(), tempNarration, period, year);
                if (narrative.length() > 80) {
                    narrative = narrative.substring(0, 80);
                }
                System.out.println("narrative:: " + narrative);
                if (account == null) {
                    System.out.println("This account does not have an account attached to it");
                } else {

                    LinkedList<Entry> entryList;
                    entryList = new LinkedList<Entry>();
                    NewSerialno nvSerial = new NewSerialno();
                    serial = nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId());
                    Entry controlEntry = new Entry();
                    Entry accountEntry = new Entry();
                    controlEntry.setTxnType("CAD");
                    controlEntry.setHeaderdocref(referenceNumber);
                    controlEntry.setDocref("REF" + referenceNumber);
                    controlEntry.setAmount((savings.getAmount()) * -1);
                    controlEntry.setCcyAmount((savings.getAmount()) * -1);
                    controlEntry.setBranchId(savings.getBranchId());
                    controlEntry.setCompany(savings.getCompanyId());
                    controlEntry.setDebit(savings.getAmount());
                    controlEntry.setNarrative(narrative);
                    controlEntry.setUserId(savings.getUserId());
                    controlEntry.setRate(new Double(1));
                    controlEntry.setTxnSerial(serial);
                    controlEntry.setTxncode("CAD");
                    controlEntry.setAccountNo(account.getGlAccountNumber());
                    controlEntry.setPeriod(period);
                    controlEntry.setYear(year);


                    accountEntry.setTxnType("CAD");
                    accountEntry.setHeaderdocref(referenceNumber);
                    accountEntry.setDocref("REF" + referenceNumber);
                    accountEntry.setAmount(savings.getAmount());
                    accountEntry.setCcyAmount(savings.getAmount());
                    accountEntry.setBranchId(savings.getBranchId());
                    accountEntry.setCompany(savings.getCompanyId());
                    accountEntry.setCredit(savings.getAmount());
                    accountEntry.setNarrative(narrative);
                    accountEntry.setUserId(savings.getUserId());
                    accountEntry.setRate(new Double(1));
                    accountEntry.setTxnSerial(nvSerial.returnSerialnostr("SAVREF", savings.getBranchId(), savings.getCompanyId()));
                    accountEntry.setTxncode("CAD");
                    accountEntry.setAccountNo(savings.getAccountNumber());
                    accountEntry.setPeriod(period);
                    accountEntry.setYear(year);


                    entryList.add(controlEntry);
                    entryList.add(accountEntry);

                    Txnsheader txnhdr = new Txnsheader(serial, "CAD", year, period, savings.getTrxDate(), postDate, entrydate, referenceNumber, txnCode.getDescription(), "SA", savings.getUserId(), savings.getBranchId(), savings.getCompanyId(), timezone);
                    Entrys entry = new Entrys();
                    entry.setTxnsheader(txnhdr);
                    entry.setEntrys(entryList);
                    System.out.println("Posting for savings with id: " + savings.getId());
                    if (postTransaction(entry)) {
                        System.out.println("Setting Status");
                        byte trxStatus = 1;
                        savings.setStatus(trxStatus);

                        session.close();
                        if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().save(savings)) {

                            SavingsAccount sv = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().getSavingsAccountByNumber(savings.getAccountNumber(), savings.getBranchId(), savings.getCompanyId());
                            System.out.println("Balance: "+sv.getBalance());
                            Member member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().getMemberByORMID(savings.getMemberId());
                            String message = "Account Number: "+savings.getAccountNumber()+"\n"+"Amount: "+savings.getAmount()+"\n"+"Date: "+savings.getTrxDate()+"\n"+"Balance: "+sv.getBalance();
                            System.out.println("Message :: " + message);
                            SMSBean sms = new SMSBean();

                            sms.setMessage(message);
                            sms.setSender(Definitions.SMS_DEFAULT_SENDER);
                            sms.setSendto(member.getPhoneNumber());
                            sms.setMsgtype(Definitions.SMS_DEFAULT_MESSAGE_TYPE);
                            sms.setSendtime(null);

                            util.sendSMS(sms);
                            System.out.println("Saved: " + savings.getId());
                        } else {
                            System.out.println("Couldn't save: " + savings.getId());
                        }
                    } else {
                    }

                }

            }

        } catch (PersistentException ex) {
            _logger.error("doBulkGlPosting", ex);
        } /**
         * catch (ParseException ex) { _logger.error("doBulkGlPosting", ex); } *
         */
        catch (Exception ex) {
            _logger.error("doBulkGlPosting", ex);
        }

    }

    public boolean postTransaction(Entrys entrys) {

        boolean success = false;

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            DBASE_URI = uri;
            System.out.println("URL: " + uri);
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(DBASE_URI).path("glws");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, entrys);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

    public void getRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = "select * FROM tblwebserv a where a.app = 'internal'";
        ResultSet agRecSet;
        try {
            agRecSet = dbobj.retrieveRecset(mySQLString);
            while (agRecSet.next()) {
                name = agRecSet.getString("user");
                password = agRecSet.getString("pwd");
            }
            dname = name;
            dpwd = password;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (dbobj != null) {
                dbobj.closecon();
            }
        }
    }
}
