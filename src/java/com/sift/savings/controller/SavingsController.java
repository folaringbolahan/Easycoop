/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.controller;

import com.sift.admin.interfaces.Definitions;
import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.FileMeta;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsAccount;
import com.sift.easycoopfin.models.SavingsCriteria;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.SMSBean;
import com.sift.gl.model.Users;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.loan.utility.MailBean;
import com.sift.savings.service.SavingService;
import com.sift.savings.utility.EasyCoopFinValidator;
import com.sift.savings.utility.GlPostTaskth;
import com.sift.savings.utility.HelperUtil;
import com.sift.savings.utility.SavingsDefinitions;
import com.sift.savings.utility.SavingsTaskth;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author logzy
 */
@Controller
@RequestMapping(value = "/savings")
public class SavingsController {

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsController.class);
    
    @Autowired
    private SavingService savingService;
    
    @Autowired
    private CurrentuserdisplayImpl user;
    HelperUtil util = new HelperUtil();
    PersistentSession session;
    String clientIPAddress;
    
    @Autowired
    private FileUploadItemsService fileUploadItemsService;
    //just added yomi
    @Autowired
    private TaskExecutor taskExecutor;
    //just added by Kunle
    @Autowired
    @Value("${DOWNLOAD_PATH}")
    private String downLoadPath;

    /**
     * @InitBinder public void initBinder(WebDataBinder binder) {
     * SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
     * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class, new
     * CustomDateEditor(dateFormat, true));
     *
     * }*
     */
    //@Autowired
    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showUploadForm(ModelMap model) {
        List<Products> products = null;
        String productTypeCode = "S";
        byte isActive = 1;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            ProductsCriteria criteria = new ProductsCriteria();
            Criterion savingsType = Restrictions.eq("productTypeCode", "S");
            Criterion contributionType = Restrictions.eq("productTypeCode", "C");
            LogicalExpression orExp = Restrictions.or(savingsType, contributionType);
            criteria.add(orExp);
            criteria.add(Restrictions.eq("companyId", dcompany));
            criteria.add(Restrictions.eq("branchId", dbranch));
            criteria.add(Restrictions.eq("isActive", isActive));
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listAllProductsByCriteria(criteria);



            // util.sendMailByUserGroup("SA7", dbranch, dcompany, user.getCurruser().getUserId(),"","");
        } catch (PersistentException ex) {
            _logger.error("showUploadForm calling method listAllProductsByCriteria(null, null)", ex);
        } catch (Exception ex) {
            _logger.error("showUploadForm calling method listAllProductsByCriteria(null, null)", ex);
        }
        model.put("products", products);
        return "savingsupload";
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) throws PersistentException {
        LinkedList<FileMeta> returnobj = null;
        String referenceNumber;

        ////just added yomi
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        //returnobj = savingService.performBulkSave(request, user.getCurruser());
        referenceNumber = savingService.performBulkSave2(request, user.getCurruser());
        SavingsTaskth performsavtaskobj = new SavingsTaskth();
        performsavtaskobj.setTaskExecutor(taskExecutor);
        //performsavtaskobj.readExcelasync(dbranch, dcompany);
        //System.exit(1);
        performsavtaskobj.readExcelasync(dbranch, dcompany, referenceNumber);
        //end just added  uncomment next line
        //// return savingService.performBulkSave(request, user.getCurruser());
        return returnobj;
    }

    @RequestMapping(value = "/pendingsavings", method =
            RequestMethod.GET)
    public ModelAndView listPendingSavings(ModelMap model) {
        List<Savings> savings = savingService.loadPendingSavings(user.getCurruser());
        return new ModelAndView("savingspending", "savings", savings);
    }

    @RequestMapping(value = "/savingsuploaderror/{referenceNumber}", method =
            RequestMethod.GET)
    public ModelAndView listSavingsUploadError(ModelMap model, @PathVariable String referenceNumber) {
        List<SavingsError> savings;
        if (referenceNumber == null) {
            savings = savingService.loadSavingsError(user.getCurruser());
        } else {
            savings = savingService.loadSavingsErrorByReferenceNumber(referenceNumber);
        }
        model.put("referenceNumber", referenceNumber);
        return new ModelAndView("savingsuploaderror", "savings", savings);
    }

    @RequestMapping(value = "/successfulupload/{referenceNumber}", method =
            RequestMethod.GET)
    public ModelAndView listSuccessfulUploads(ModelMap model, @PathVariable String referenceNumber) {

        List<Savings> savings = savingService.loadSavingsByReferenceNumber(referenceNumber);
        model.put("referenceNumber", referenceNumber);
        return new ModelAndView("successfulupload", "savings", savings);
    }

    @RequestMapping(value = "/savingsuploaderror2", method =
            RequestMethod.GET)
    public ModelAndView loadSavingsUploadError(ModelMap model) {
        List<SavingsError> savings = savingService.loadSavingsError(user.getCurruser());
        return new ModelAndView("savingsuploaderror", "savings", savings);
    }

    @RequestMapping(value = "/dobulk", method =
            RequestMethod.GET)
    public String createProduct(ModelMap model) {
        savingService.doBulkGlPosting();
        return "index";
    }

    @RequestMapping(value = "/list", method =
            RequestMethod.GET)
    public ModelAndView listSavings(ModelMap model) {
        List<Savings> savings = null;

        try {

            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            String condition = " Savings.branchId=" + dbranch;
            condition += " AND Savings.companyId=" + dcompany;
            String order = " Savings.trxDate desc";
            savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingByQuery(condition, order);
             model.addAttribute("savings",
                    savings);
        } catch (Exception ex) {

            _logger.error("listProducts calling method listAllProductsByQuery(null, null)", ex);
        }


        return new ModelAndView("viewsavings", "savings", savings);
    }

    @RequestMapping(value = "/list/{accountNumber}", method =
            RequestMethod.GET)
    public ModelAndView listSavings(ModelMap model, @PathVariable String accountNumber) {
        List<Savings> savings = null;

        try {

            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            byte status = 1;
            //String condition = " Savings.accountNumber='" + accountNumber + "'";
            String condition = " Savings.accountNumber='" + accountNumber + "' AND Savings.status='" + status + "'";
            condition += " AND Savings.companyId=" + dcompany;
            condition += " AND Savings.branchId=" + dbranch;
            String order = " Savings.trxDate desc";
            savings = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().listAllSavingByQuery(condition, order);
            model.addAttribute("savings",
                    savings);
        } catch (Exception ex) {

            _logger.error("listProducts calling method listAllProductsByQuery(null, null)", ex);
        }


        return new ModelAndView("viewsavings", "savings", savings);
    }

    @RequestMapping(value = "/accounts", method =
            RequestMethod.GET)
    public ModelAndView listAccounts(ModelMap model) {
        List<SavingsAccount> accounts = null;

        try {

            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();

            accounts = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getSavingsDAO().getSavingsAccountByBranch(dbranch, dcompany);
            model.addAttribute("accounts",
                    accounts);
        } catch (Exception ex) {

            _logger.error("listProducts calling method listAllProductsByQuery(null, null)", ex);
        }


        return new ModelAndView("savingsbalances", "accounts", accounts);
    }

    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/checkaccount", method = RequestMethod.GET)
    public @ResponseBody
    String checkAccount(@RequestParam("account") String account) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        String returnMessage;
        boolean status = savingService.checkIfAccountByBranchIsValid(account, branchId, companyId);
        if (status) {
            if (savingService.checkIfSavingsAccount(account, branchId, companyId)) {
                returnMessage = "ok";
            } else {
                returnMessage = "The requested operation is not supported on this account";
            }

        } else {
            returnMessage = "notok";
        }
        return returnMessage;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String editSavings(
            @ModelAttribute("savingsForm") Savings savings,
            ModelMap model, HttpServletRequest request) throws PersistentException {
        String returnUrl;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            int period = user.getCurrusercompany().getCurrentPeriod();
            int year = user.getCurrusercompany().getCurrentYear();
            String timezone = user.getCurrusercompany().getTimezone();
            String userName = user.getCurruser().getUserId();
            Date postDate = user.getCurrusercompany().getPostDate();
            clientIPAddress = request.getRemoteAddr();
            com.sift.gl.model.Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(savings.getAccountNumber(), dbranch, dcompany);
            if (account == null) {
                return "redirect:/views/savings/add?error=1";
            }
            MemberCriteria mcriteria = new MemberCriteria();
            System.out.println("Savings account:  " + savings.getAccountNumber());

            mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
            //Just added 
            mcriteria.add(Restrictions.eq("companyId", dcompany));
            mcriteria.add(Restrictions.eq("branchId", dbranch));

            System.out.println("Member No: " + account.getAsegc());
            Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
            System.out.println("Member ID: " + m.getId());
            savings.setMemberId(m.getId());
            savings.setUserId(userName);
            int productId = savingService.getProductIdByAccountNumber(savings.getAccountNumber(), dbranch);
            savings.setProductId(productId);
            if (savingService.saveTransaction(savings)) {
                HelperUtil.logEvent(5, "CREATESSAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), savings.getReferenceNumber(), "OK", user.getCurruser().getBranch(), dcompany);
                returnUrl = "redirect:/savings/add.htm?s=1";
            } else {
                HelperUtil.logEvent(5, "CREATESSAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), savings.getReferenceNumber(), "FAILED", user.getCurruser().getBranch(), dcompany);
                returnUrl = "redirect:/views/savings/add?error=1";
            }

        } catch (Exception ex) {
            _logger.error("editSavings", ex);
            returnUrl = "redirect:/views/savings/add?error=1";
        }
        return returnUrl;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    @ResponseBody
    public String withdrawSavings(
            @RequestParam("accountNumber") String acc,
            @RequestParam("sproductCode") String code,
            @RequestParam("amount") float amount,
            @RequestParam("description") String description,
            @RequestParam("stringDate") String stringDate,
            ModelMap model) throws PersistentException {

        boolean status = false;
        String message = "";
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String userName = user.getCurruser().getUserId();
        String timezone = user.getCurrusercompany().getTimezone();
        ProductsCriteria criteria = new ProductsCriteria();
        criteria.add(Restrictions.eq("companyId", dcompany));
        criteria.add(Restrictions.eq("branchId", dbranch));
        criteria.add(Restrictions.eq("code", code));
        Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(criteria);

        Savings savings = new Savings();
        savings.setAccountNumber(acc);
        savings.setAmount(amount);
        savings.setBranchId(dbranch);
        savings.setCompanyId(dcompany);
        savings.setDescription(description);
        savings.setProductId(product.getId());
        savings.setStringDate(stringDate);
        savings.setUserId(userName);
        savings.setTrxType("W");

        Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(savings.getAccountNumber(), dbranch, dcompany);
        MemberCriteria mcriteria = new MemberCriteria();
        mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
        //Just added 
        mcriteria.add(Restrictions.eq("companyId", dcompany));
        mcriteria.add(Restrictions.eq("branchId", dbranch));
        Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
        savings.setMemberId(m.getId());
        int period = user.getCurrusercompany().getCurrentPeriod();
        int year = user.getCurrusercompany().getCurrentYear();
        Date postDate = user.getCurrusercompany().getPostDate();
        if (savingService.getProductTypeByAccountNumber(acc, dbranch, dcompany)) {
            message = "You cannot withdraw from a Contribution account";
        } else {
            if (savingService.withdraw(savings, period, year, timezone, postDate)) {
                status = true;
                message = "Operation Successful";
            } else {
                status = false;
                message = "Operation failed";

            }
        }
        return message;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    @ResponseBody
    public String saveSavings(
            @RequestParam("accountNumber") String acc,
            @RequestParam("sproductCode") String code,
            @RequestParam("amount") float amount,
            @RequestParam("description") String description,
            @RequestParam("stringDate") String stringDate,
            ModelMap model,
            HttpServletRequest request) throws PersistentException {

        boolean status = false;
        String message = "";
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String userName = user.getCurruser().getUserId();
        ProductsCriteria criteria = new ProductsCriteria();
        criteria.add(Restrictions.eq("branchId", dbranch));
        criteria.add(Restrictions.eq("companyId", dcompany));
        criteria.add(Restrictions.eq("code", code));
        Products product = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().loadProductsByCriteria(criteria);

        Savings savings = new Savings();
        savings.setAccountNumber(acc);
        savings.setAmount(amount);
        savings.setBranchId(dbranch);
        savings.setCompanyId(dcompany);
        savings.setDescription(description);
        savings.setProductId(product.getId());
        savings.setStringDate(stringDate);
        savings.setUserId(userName);

        Account account = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAccountDAO().getAccountByAccountNumber(savings.getAccountNumber(), dbranch, dcompany);
        MemberCriteria mcriteria = new MemberCriteria();
        mcriteria.add(Restrictions.eq("memberNo", account.getAsegc()));
        //Just added 
        mcriteria.add(Restrictions.eq("companyId", dcompany));
        mcriteria.add(Restrictions.eq("branchId", dbranch));

        Member m = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(mcriteria);
        savings.setMemberId(m.getId());
        int period = user.getCurrusercompany().getCurrentPeriod();
        int year = user.getCurrusercompany().getCurrentYear();
        String timezone = user.getCurrusercompany().getTimezone();
        Date postDate = user.getCurrusercompany().getPostDate();
        if (savingService.uploadSingleTransaction(savings, period, year, timezone, postDate)) {
            status = true;
            HelperUtil.logEvent(5, "CREATESSAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), savings.getReferenceNumber(), "OK", user.getCurruser().getBranch(), dcompany);
            message = "Operation Successful";
        } else {
            status = false;
            HelperUtil.logEvent(5, "CREATESSAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), savings.getReferenceNumber(), "FAILED", user.getCurruser().getBranch(), dcompany);
            message = "Operation failed";

        }
        return message;
    }

    @RequestMapping(value = "/getaccount", method = RequestMethod.GET)
    @ResponseBody
    public String saveAccount(@RequestParam("customerId") int customerId,
            @RequestParam("productCode") String productCode) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String timezone = user.getCurrusercompany().getTimezone();
        Date postDate = user.getCurrusercompany().getPostDate();
        String accountNumber = savingService.createAccount(customerId, productCode, dbranch, dcompany, timezone);
        String output = accountNumber;
        if (!accountNumber.isEmpty() && accountNumber.indexOf("OK.") != -1) {
            accountNumber = accountNumber.replace("OK. ", "");
            output = "Customer account: " + accountNumber;
        }

        System.out.println("Customer Number: " + accountNumber);
        return output;
    }

    @RequestMapping(value = "/createaccount", method = RequestMethod.GET)
    public String createAccount(ModelMap model) {
        List<Products> products = null;
        List<SavingsAccount> accounts = null;
        String productTypeCode = "S";
        byte isActive = 1;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            ProductsCriteria criteria = new ProductsCriteria();
            Criterion savingsType = Restrictions.eq("productTypeCode", "S");
            Criterion contributionType = Restrictions.eq("productTypeCode", "C");
            LogicalExpression orExp = Restrictions.or(savingsType, contributionType);
            criteria.add(orExp);
            criteria.add(Restrictions.eq("companyId", dcompany));
            criteria.add(Restrictions.eq("branchId", dbranch));
            criteria.add(Restrictions.eq("isActive", isActive));
            String condition = " Custaccountdetails.branchId=" + dbranch;
            CustaccountdetailsCriteria accountCriteria = new CustaccountdetailsCriteria();
            accountCriteria.add(Restrictions.eq("branchId", dbranch));
            accountCriteria.add(Restrictions.eq("companyId", dcompany));
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listAllProductsByCriteria(criteria);
            accounts = DAOFactory.getDAOFactory().getSavingsDAO().getAllSavingsAccountByBranch(dbranch, dcompany);

        } catch (PersistentException ex) {
            _logger.error("showUploadForm calling method listAllProductsByCriteria(null, null)", ex);
        } catch (Exception ex) {
            _logger.error("showUploadForm calling method listAllProductsByCriteria(null, null)", ex);
        }
        model.put("products", products);
        model.put("accounts", accounts);
        return "createaccount";
    }

    @RequestMapping(value = "/sendmail", method = RequestMethod.GET)
    public String getAllAccounts() throws PersistentException {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        String mailBody = "	<style type=\"text/css\">"
                + "	<!--"
                + "	.style2 {"
                + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                + "		font-size: 14px;"
                + "	}"
                + "	-->"
                + "	</style>"
                + "	 <p class=\"style2\">Hello, </p>"
                + "	<p class=\"style2\">Your batch upload with reference #sfdf has been successfully processed <br>"
                + "   <strong>Below are the details: </strong></p>"
                + "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
                + "	  <tr>"
                + "	    <td width=\"50%\"><span class=\"style2\">Failure Count:</span></td>"
                + "	    <td width=\"50%\"><span class=\"style2\">27</span></td>"
                + "	  </tr>"
                + "	  <tr>"
                + "	    <td><span class=\"style2\">Success Count:</span></td>"
                + "	    <td><span class=\"style2\">5 </span></td>"
                + "	  </tr>"
                + "	  <tr>"
                + "	    <td><span class=\"style2\">Processed Sum:</span></td>"
                + "	    <td><span class=\"style2\">6 </span></td>"
                + "	  </tr>"
                + "     <tr>"
                + "	    <td colspan=2><hr/></td>"
                + "	    "
                + "	  </tr>"
                + "     <tr>"
                + "	    <td colspan=2><hr/></td>"
                + "	    "
                + "	  </tr>"
                + "	</table>"
                + "	<p class=\"style2\"><br>"
                + "	  Thanks </p> ";
        /**
         * MailBean MB = HelperUtil.createMailBean();
         * MB.setSubject(SavingsDefinitions.BULK_UPLOAD_PROCESSED_SUBJECT);
         * MB.setToemail("logzyy@gmail.com");
         *
         * MB.setMailBody(mailBody); HelperUtil.sendMail(MB);
         *
         * HelperUtil.sendMailByUserGroup("SA7", dbranch,
         * dcompany,"christofash@gmail.com" , "Test Mail", mailBody);
         */
        return "index";

    }

    public List<Member> getM(String value) throws PersistentException {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        String condition = " (m.firstName like '%" + value + "%' or m.surname  like '%" + value + "%' or m.middleName like '%" + value + "%' ) and m.branchId=" + dbranch;
        List<Member> members = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().listMembersByQuery(condition, null);
        System.out.println("condition: " + condition);
        for (Member m : members) {
            System.out.println("Company ID: " + m.getCompanyId());
        }
        return members;
    }

    @RequestMapping(value = "/getMembers", method = RequestMethod.GET)
    public @ResponseBody
    List<Member> getMembers(@RequestParam String name) {
        List<Member> members = null;
        try {
            members = getM(name);
        } catch (PersistentException ex) {
        }
        // return savingService.getMembers(name);
        return members;

    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public @ResponseBody
    String approveSavings(@RequestParam String id, HttpServletRequest request) {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        savingService.updateSavingsStatus(id);
        byte isProcessed = 1;
        byte isApproved = 1;
        byte status = 0;
        SavingsCriteria criteria = null;
        try {
            criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("companyId", dcompany));
            criteria.add(Restrictions.eq("branchId", dbranch));
        } catch (PersistentException ex) {
            Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
        }


        HelperUtil.logEvent(5, "APPROVESAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), id, "OK", user.getCurruser().getBranch(), dcompany);

        ////just added yomi
        GlPostTaskth performglposttaskobj = new GlPostTaskth();
        performglposttaskobj.setTaskExecutor(taskExecutor);
        //performglposttaskobj.doBulkGlPostingasync(dbranch, dcompany);
        // performglposttaskobj.doBulkGlPostingasync(dbranch, dcompany, fileUploadItemsService);
        performglposttaskobj.doBulkGlPostingasync(dbranch, dcompany, fileUploadItemsService, criteria);
        //end just added  uncomment next line        
        return "ok";

    }

    @RequestMapping(value = "/verifytransaction", method = RequestMethod.GET)
    public @ResponseBody
    String verify(@RequestParam("ReferenceNumber") String referenceNumber) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();

        return savingService.verify(referenceNumber, user.getCurruser().getUserId());

    }

    // new for batch verification 
    @RequestMapping(value = "/verifybatch/{referenceNumber}", method = RequestMethod.POST)
    public ModelAndView verifybatch(@PathVariable("referenceNumber") String referenceNumber, ModelMap map, HttpServletRequest request, HttpServletResponse response) {


        System.out.println("referenceNumber :: " + referenceNumber);
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String currentUser = request.getRemoteUser();
        /*
         String processStatus = "3"; //1 = Approved, 2 = In Progress and  3 =  reject 
         String savingsTable = "savings";
         String fileUploadTable = "fileupload";
         String currentUser =request.getRemoteUser();
         savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
         savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);
         String msg = "Record Approval/Rejection in Progress";
         */
        //savingService.verify(referenceNumber, user.getCurruser().getUserId());

        String processStatus = "2"; //1 = Approved, 2 = In Progress and  3 =  reject 
        String fileUploadTable = "fileupload";
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);

        // verify with async
        savingService.verifyBatchUpload(referenceNumber, user.getCurruser().getUserId());
        HelperUtil.logEvent(5, "BATCH_VERIFICATION", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);


        return new ModelAndView("redirect:/savings/verifyuploads.htm");
    }

    // new for batch rejection  at verification
    @RequestMapping(value = "/rejectVerifybatch2/{referenceNumber}", method = RequestMethod.POST)
    public ModelAndView rejectVerifybatch2(@PathVariable("referenceNumber") String referenceNumber, ModelMap map, HttpServletRequest request, HttpServletResponse response) {


        System.out.println("referenceNumber :: " + referenceNumber);
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String processStatus = "3"; //1 = Approved, 2 = In Progress and  3 =  reject 
        String savingsTable = "savings";
        String fileUploadTable = "fileupload";
        String currentUser = request.getRemoteUser();
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);
        String msg = "Record Approval/Rejection in Progress";

        HelperUtil.logEvent(5, "BATCH_REJECTION", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);


        return new ModelAndView("redirect:/savings/verifyuploads.htm");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showSavingsForm(ModelMap model) {

        Savings savingsForm = new Savings();
        model.put("savingsForm", savingsForm);
        List<Products> products = null;
        String productTypeCode = "S";
        byte isActive = 1;
        try {

            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();



            ProductsCriteria criteria = new ProductsCriteria();
            //criteria.add(Restrictions.eq("branchId", dbranch));
            //criteria.add(Restrictions.eq("productTypeCode", productTypeCode));
            Criterion savingsType = Restrictions.eq("productTypeCode", "S");
            Criterion contributionType = Restrictions.eq("productTypeCode", "C");
            LogicalExpression orExp = Restrictions.or(savingsType, contributionType);
            criteria.add(orExp);
             criteria.add(Restrictions.eq("companyId", dcompany));
            criteria.add(Restrictions.eq("branchId", dbranch));
            criteria.add(Restrictions.eq("isActive", isActive));
            products = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getProductsDAO().listAllProductsByCriteria(criteria);



            // String condition = "";

            model.put("products", products);
            model.put("user", user);


        } catch (Exception ex) {
            _logger.error("listProducts calling method listAllProductsByCriteria(null, null)", ex);
        }

        //return "product";
        return new ModelAndView("postsaving", "saving", savingsForm);
    }

    @RequestMapping(value = "/verifyuploads", method = RequestMethod.GET)
    public ModelAndView verifyUploads(ModelMap model) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        List<FileUpload> uploads = savingService.listUnVerifiedUploads(dbranch, dcompany);
        /**
         * for (FileUpload u : uploads) { System.out.println("Name" +
         * u.getFileName() + " Verified" + u.getVerifierId()); }*
         */
        model.addAttribute("uploads", uploads);

        return new ModelAndView("verifyuploads", "uploads", uploads);
    }

    // To list batch for approval
    @RequestMapping(value = "/uploadforapproval", method = RequestMethod.GET)
    public ModelAndView uploadforapproval(ModelMap model) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();

        //List<FileUpload> uploads = savingService.listUnVerifiedUploads(dbranch, dcompany);
        List<FileUpload> uploads = savingService.listBatchForApproval(dbranch, dcompany);
        /**
         * for (FileUpload u : uploads) { System.out.println("Name" +
         * u.getFileName() + " Verified" + u.getVerifierId()); }*
         */
        model.addAttribute("uploads", uploads);

        return new ModelAndView("uploadforapproval", "uploads", uploads);
    }

    // To list batch for approval
    @RequestMapping(value = "/savingsuploaderror", method = RequestMethod.GET)
    public ModelAndView savingsfailedUpload(ModelMap model) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        List<FileUpload> uploads = savingService.listFailedUploads(dbranch, dcompany);

        model.addAttribute("uploads", uploads);

        return new ModelAndView("savingsfailedUpload", "uploads", uploads);
    }

    @RequestMapping(value = "/approvebatch", method = RequestMethod.GET)
    public @ResponseBody
    void approvebatch(@RequestParam("ReferenceNumber") String referenceNumber, HttpServletRequest request) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String processStatus = "2";
        String savingsTable = "savings";
        String fileUploadTable = "fileupload";
        // get userId and use it to update the table 
        String currentUser = request.getRemoteUser();
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);

        byte isProcessed = 1;
        byte isApproved = 2;
        byte status = 0;
        SavingsCriteria criteria = null;
        try {
            criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
        } catch (PersistentException ex) {
            Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
        }


        // update batch for approval

        HelperUtil.logEvent(5, "BATCH_APPROVESAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);

        ////just added yomi
        GlPostTaskth performglposttaskobj = new GlPostTaskth();
        performglposttaskobj.setTaskExecutor(taskExecutor);
        //performglposttaskobj.doBulkGlPostingasync(dbranch, dcompany);
        //performglposttaskobj.doBulkGlPostingasync(branchId, companyId, fileUploadItemsService);
        performglposttaskobj.doBulkGlPostingasync(branchId, companyId, fileUploadItemsService, criteria);
        // return savingService.verify(referenceNumber, user.getCurruser().getUserId());

        //   return  "ok";

    }

    // new for batch approval
    @RequestMapping(value = "/approvebatch2/{referenceNumber}", method = RequestMethod.POST)
    public ModelAndView approveByBatch(@PathVariable("referenceNumber") String referenceNumber, ModelMap map, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("referenceNumber :: " + referenceNumber);
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String processStatus = "2";
        String savingsTable = "savings";
        String fileUploadTable = "fileupload";
        // get userId and use it to update the table 
        String currentUser = request.getRemoteUser();
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);

        byte isProcessed = 1;
        byte isApproved = 2;
        byte status = 0;
        SavingsCriteria criteria = null;
        try {
            criteria = new SavingsCriteria();
            criteria.add(Restrictions.eq("isProcessed", isProcessed));
            criteria.add(Restrictions.eq("isApproved", isApproved));
            criteria.add(Restrictions.eq("status", status));
            criteria.add(Restrictions.eq("companyId", companyId));
            criteria.add(Restrictions.eq("branchId", branchId));
            criteria.add(Restrictions.eq("referenceNumber", referenceNumber));
        } catch (PersistentException ex) {
            Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
        }


        // update batch for approval

        HelperUtil.logEvent(5, "BATCH_APPROVESAV", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);

        ////just added yomi
        GlPostTaskth performglposttaskobj = new GlPostTaskth();
        performglposttaskobj.setTaskExecutor(taskExecutor);
        //performglposttaskobj.doBulkGlPostingasync(dbranch, dcompany);
        //performglposttaskobj.doBulkGlPostingasync(branchId, companyId, fileUploadItemsService);
        performglposttaskobj.doBulkGlPostingasync(branchId, companyId, fileUploadItemsService, criteria);


        return new ModelAndView("redirect:/savings/uploadforapproval");
    }

    // new for batch rejection 
    @RequestMapping(value = "/rejectbatchappr2/{referenceNumber}", method = RequestMethod.POST)
    public ModelAndView rejectbatchappr2(@PathVariable("referenceNumber") String referenceNumber, ModelMap map, HttpServletRequest request, HttpServletResponse response) {

        System.out.println("referenceNumber :: " + referenceNumber);
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String processStatus = "3"; //1 = Approved, 2 = In Progress and  3 =  reject 
        String savingsTable = "savings";
        String fileUploadTable = "fileupload";
        String currentUser = request.getRemoteUser();
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);
        String msg = "Record Approval/Rejection in Progress";

        HelperUtil.logEvent(5, "BATCH_REJECTION", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);


        return new ModelAndView("redirect:/savings/uploadforapproval");
    }

    @RequestMapping(value = "/rejectbatchappr", method = RequestMethod.GET)
    public @ResponseBody
    String /*ModelAndView*/ rejectbatchappr(@RequestParam("ReferenceNumber") String referenceNumber, HttpServletRequest request) {
        int branchId = user.getCurruser().getBranch();
        int companyId = user.getCurruser().getCompanyid();
        clientIPAddress = request.getRemoteAddr();
        String processStatus = "3"; //1 = Approved, 2 = In Progress and  3 =  reject 
        String savingsTable = "savings";
        String fileUploadTable = "fileupload";
        String currentUser = request.getRemoteUser();
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, savingsTable, currentUser);
        savingService.updateBatchStatus(branchId, companyId, referenceNumber, processStatus, fileUploadTable, currentUser);
        String msg = "Record Approval/Rejection in Progress";

        HelperUtil.logEvent(5, "BATCH_REJECTION", clientIPAddress, user.getCurruser().getUserId(), user.getCurrusercompany().getTimezone(), referenceNumber, "OK", branchId, companyId);

        //return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + msg);
        return "ok";
    }

    // download saving/contribution template
    @RequestMapping(value = "/downloadsavingstmpl", method = RequestMethod.GET)
    public void downloadSavingsContribTempl(HttpServletRequest req, HttpServletResponse response) throws Exception {

        File file = new File(downLoadPath + req.getParameter("downFile").replace("/", "").replace("\\", ""));

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(SavingsController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        // MIME type of the file
        response.setContentType("application/octet-stream");
        // Response header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        // Read from the file and write into the response
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException ex) {
            // Logger.getLogger(MemberBatchController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        os.close();
        is.close();

    }
}
