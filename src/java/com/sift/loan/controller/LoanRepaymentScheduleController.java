package com.sift.loan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Company;
import com.sift.admin.model.Country;
import com.sift.admin.model.MemberView;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.service.UserService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.SMSBean;
import com.sift.gl.model.SmsLog;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.bean.LoanRepaymentScheduleBean;

/**
 * **************************************************************************
 ********************* @author XTOFFEL CONSULT ******************************
 * ***************************************************************************
 **************************************************************************
 */
@Controller
public class LoanRepaymentScheduleController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CurrentuserdisplayImpl currentuserdisplayService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberViewService memberViewService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private LoanRequestService loanRequestService;
    @Autowired
    private LoanRepaymentScheduleService loanRepaymentScheduleService;
    @Autowired
    private FileUploadItemsService fileUploadItemsService;
    BeanMapperUtility beanMapper = new BeanMapperUtility();
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();

    @RequestMapping(value = "/saveLoanRepaySchedule", method = RequestMethod.POST)
    public ModelAndView saveLoanRepaySchedule(@ModelAttribute("loanRepaySch") LoanRepaymentScheduleBean loanRepaymentScheduleBean, BindingResult result, HttpServletRequest req) {
        LoanRepaymentScheduleBean obj = beanMapper.prepareLoanRepaymentScheduleBean(loanRepaymentScheduleService.getLoanRepaymentSchedule(loanRepaymentScheduleBean.getId()));


        obj.setActualRepaymentDate(loanRepaymentScheduleBean.getActualRepaymentDate());
        obj.setPaymentStatus("P");
        obj.setActive("N");
        obj.setActualRepaymentAmount(loanRepaymentScheduleBean.getActualRepaymentAmount());
        obj.setLastModificationDate(loanRepaymentScheduleBean.getLastModificationDate());
        obj.setLastModifiedBy(loanRepaymentScheduleBean.getLastModifiedBy());
        // just addded
        obj.setPenaltyIncurred(loanRepaymentScheduleBean.getPenaltyIncurred());

        LoanRepaymentSchedule loanRepaymentSchedule = prepareModel(obj);
        Company coy = companyService.getCompany(Integer.parseInt(obj.getCompanyId()));
        Country country = countryService.getCountry(Integer.parseInt(coy.getCountryId()));
        String timeZone = country.getTimeZone();
        String currencyCode = country.getCurrencyCode();

        LoanRequest objL = loanRequestService.getLoanRequestByCaseId(obj.getLoanCaseId());
        Product product = productService.getProductsDistinctByCodeByBranch(obj.getBranchId(), objL.getLoanType());

       //double penaltyIncurred = helperUTIL.getLoanPenalty(loanRepaymentSchedule, product, eazyCoopUTIL.getDaysDiff(loanRepaymentSchedule.getActualRepaymentDate(), loanRepaymentSchedule.getExpectedRepaymentDate()));
        double penaltyIncurred = helperUTIL.getLoanPenalty(loanRepaymentSchedule, product, eazyCoopUTIL.getDaysDiff( loanRepaymentSchedule.getExpectedRepaymentDate(), loanRepaymentSchedule.getActualRepaymentDate()));
        
        obj.setPenaltyIncurred(penaltyIncurred);

        eazyCoopUTIL.setHelperUTIL(helperUTIL);
       
        if (eazyCoopUTIL.processCustomerLoanRepayment(loanRepaymentSchedule, coy, objL, timeZone, currentuserdisplayService.getCurrusercompany().getPostDate())) {
            loanRepaymentScheduleService.addLoanRepaymentSchedule(loanRepaymentSchedule);
            String scheduleIdStr = String.valueOf(helperUTIL.getLoanScheduleID(obj.getLoanCaseId()));

            //update loan table to reflect balances
            loanRequestService.doUpdateLoanBalance(objL, loanRepaymentSchedule, false);

            Activitylog activity = new Activitylog();

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_REPAYMENT);
            activity.setAction("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + obj.getLoanCaseId());
            activity.setActionResult("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
            activity.setDescription("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(timeZone);
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
            activity.setBranchid(Integer.parseInt(obj.getBranchId()));

            try {
                eazyCoopUTIL.LogUserAction(activity);
            } catch (Exception ex) {
            }

            LoanRequest objX = loanRequestService.getLoanRequestByCaseId(obj.getLoanCaseId());
            double bal = objX.getBalancePrincipal();
            String balStr = new java.text.DecimalFormat("#,###.00").format(bal);
            MemberView mem = memberViewService.getMember(objX.getMemberNo());

            System.out.println("Mobile No:" + mem.getPhone1());

            //Send SMS to user
            try {
                String message = "Your Loan Repayment processing [" + obj.getLoanCaseId() + "] was successful. Your outstanding Loan Balance is: " + currencyCode + " " + balStr;
                SMSBean sms = new SMSBean();

                sms.setMessage(message);
                sms.setSender(Definitions.SMS_DEFAULT_SENDER);
                sms.setSendto(mem.getPhone1());
                sms.setMsgtype(Definitions.SMS_DEFAULT_MESSAGE_TYPE);
                sms.setSendtime(null);

                if (eazyCoopUTIL.getCoopswthSMS().get(obj.getCompanyId())!=null) {
                 eazyCoopUTIL.sendSMS(sms);
                }
                
                //log sms
                SmsLog smsLog = new SmsLog();

                smsLog.setEventId(Definitions.EVENT_LOAN_REPAYMENT);
                smsLog.setDescription("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
                smsLog.setAction("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
                smsLog.setUsername(req.getRemoteUser());
                smsLog.setMobile(mem.getPhone1());
                smsLog.setMessage(message);
                smsLog.setStatus("Y");
                smsLog.setActionDate(eazyCoopUTIL.getTimeByZone(timeZone));
                smsLog.setActionItem("Loan ID: " + obj.getLoanCaseId());
                smsLog.setActionResult("Loan Repayment schedule");
                smsLog.setCompanyId(Integer.parseInt(obj.getCompanyId()));
                smsLog.setBranchId(Integer.parseInt(obj.getBranchId()));

                if (eazyCoopUTIL.getCoopswthSMS().get(obj.getCompanyId())!=null) {
                 fileUploadItemsService.addSmsLog(smsLog);
                } 
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (loanRepaymentSchedule.getId() == Integer.parseInt(scheduleIdStr)) {
                //LoanRequest objX=loanRequestService.getLoanRequestByCaseId(obj.getLoanCaseId());
                objX.setLoanStatus("C"); //mark loan as closed

                loanRequestService.addLoanRequest(objX);

                activity = new Activitylog();

                //Audit action
                activity.setEvent(Definitions.EVENT_LOAN_CLOSE_OUT);
                activity.setAction("Loan Closure Processing for Loan ID: " + objX.getLoanCaseId());
                activity.setActionDate(new java.util.Date());
                activity.setActionItem("Loan ID:" + objX.getLoanCaseId());
                activity.setActionResult("Loan Closure Processing for Loan ID: " + objX.getLoanCaseId());
                activity.setDescription("Loan Closure Processing for Loan ID: " + objX.getLoanCaseId());
                activity.setIpaddress(req.getRemoteHost());
                activity.setUsername(req.getRemoteUser());
                activity.setTimezone(timeZone);
                activity.setToDate("");
                 activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
                 activity.setBranchid(Integer.parseInt(obj.getBranchId()));


                try {
                    eazyCoopUTIL.LogUserAction(activity);
                } catch (Exception ex) {
                }
            }

            return new ModelAndView("redirect:/doFeedback.htm?message=Loan Repayment Processing was successful&redirectURI=loanRepayment0.htm?id=" + loanRepaymentSchedule.getId());
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Loan Repayment Processing Failed. Please try again later.&redirectURI=newLoanRepaySchedule.htm?id=" + loanRepaymentSchedule.getId());
        }
    }

    @RequestMapping(value = "/loanRepaymentSchedules", method = RequestMethod.GET)
    public ModelAndView listLoanRepaymentSchedules() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRepaymentSchedules", prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentSchedule()));
        return new ModelAndView("loanRepaymentSchedulesList", model);
    }

    @RequestMapping(value = "/newLoanRepaySchedule", method = RequestMethod.GET)
    public ModelAndView addLoanRepaymentSchedule(@ModelAttribute("loanRepaySch") LoanRepaymentScheduleBean loanRepaymentScheduleBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRepaymentSchedules", prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentSchedule()));
        return new ModelAndView("addLoanRepaySchedule", model);
    }

    @RequestMapping(value = "/deleteLoanRepaySchedule", method = RequestMethod.GET)
    public ModelAndView deleteLoanRepaymentSchedule(@ModelAttribute("loanRepaySch") LoanRepaymentScheduleBean loanRepaymentScheduleBean, BindingResult result) {
        loanRepaymentScheduleService.deleteLoanRepaymentSchedule(prepareModel(loanRepaymentScheduleBean));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRepaymentSchedule", null);
        model.put("loanRepaymentSchedules", prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentSchedule()));
        return new ModelAndView("addLoanRepaySchedule", model);
    }

    @RequestMapping(value = "/editLoanRepaySchedule", method = RequestMethod.GET)
    public ModelAndView editLoanRepaymentSchedule(@ModelAttribute("loanRepaySch") LoanRepaymentScheduleBean loanRepaymentScheduleBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRepaySch", prepareLoanRepaymentScheduleBean(loanRepaymentScheduleService.getLoanRepaymentSchedule(loanRepaymentScheduleBean.getId())));
        model.put("loanRepaymentSchedules", prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentSchedule()));

        return new ModelAndView("editLoanRepaySchedule", model);
    }

    @RequestMapping(value = "/newLoanSchedulePayment", method = RequestMethod.GET)
    public ModelAndView newLoanSchedulePayment(@ModelAttribute("loanRepaySch") LoanRepaymentScheduleBean loanRepaymentScheduleBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        LoanRepaymentSchedule obj = loanRepaymentScheduleService.getLoanRepaymentSchedule(loanRepaymentScheduleBean.getId());
        MemberView mem = memberViewService.getMember(obj.getMemberNo());



        if ("P".equalsIgnoreCase(obj.getPaymentStatus())) {
            //schedule already paid
            return new ModelAndView("redirect:/doError.htm?message=The specified loan schedule already paid off&redirectURI=loanRepayment0.htm");
        } else {


            LoanRequest loan = loanRequestService.getLoanRequestByCaseId(obj.getLoanCaseId());
            Product product = productService.getProductByTypeCode(loan.getLoanType(), loan.getCompanyId(), obj.getBranchId());
            
            int delayDays = eazyCoopUTIL.getDaysDiff(obj.getExpectedRepaymentDate(), eazyCoopUTIL.currentDate());
            double penaltyIncurred = 0.0;

            if (delayDays > 0) {
                penaltyIncurred = helperUTIL.getLoanPenalty(obj, product, delayDays);    
                System.out.println("penaltyIncurred in newLoanSchedulePayment() :: " + penaltyIncurred);
            } else {
                penaltyIncurred = 0.0;
            }

            obj.setPenaltyIncurred(eazyCoopUTIL.roundDouble(penaltyIncurred, 2));
            LoanRepaymentScheduleBean newBean = prepareLoanRepaymentScheduleBean(obj);

            newBean.setMemberNoStr(mem.getMemberNo());
            newBean.setCoyMemberNo(mem.getCompmemberId());
            newBean.setMemberNo(mem.getMemberId());
            newBean.setMemberName(mem.getSurname() + " " + mem.getFirstname());

            model.put("loanRepaySch", newBean);
            model.put("loanRepaymentSchedules", prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));

            String logonUser = eazyCoopUTIL.getLoggedonUser(req);
            User currentUser = userService.getUserByEmail(logonUser);

            loanRepaymentScheduleBean.setCompanyId(currentUser.getCompanyId());
            loanRepaymentScheduleBean.setBranchId(currentUser.getBranchId());

            model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
            model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));

            return new ModelAndView("newLoanSchedulePayment", model);
        }
    }

    /**
     * ***************************************************************************************************************************************************
     * @RequestMapping(value = "/newBalloonPayment", method = RequestMethod.GET)
     * public ModelAndView newBalloonPayment(
     * @ModelAttribute("loanRepaySch")LoanRepaymentScheduleBean
     * loanRepaymentScheduleBean,BindingResult result,HttpServletRequest req) {
     * Map<String, Object> model = new HashMap<String, Object>();
     * model.put("loanRepaySch",
     * prepareLoanRepaymentScheduleBean(loanRepaymentScheduleService.getLoanRepaymentSchedule(loanRepaymentScheduleBean.getId())));
     * model.put("loanRepaymentSchedules",
     * prepareListofBean(loanRepaymentScheduleService.listLoanRepaymentSchedule()));
     *
     * String logonUser=eazyCoopUTIL.getLoggedonUser(req); User
     * currentUser=userService.getUserByEmail(logonUser);
     *
     * loanRepaymentScheduleBean.setCompanyId(currentUser.getCompanyId());
     * loanRepaymentScheduleBean.setBranchId(currentUser.getBranchId());
     *
     * model.put("companies",
     * beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     * model.put("branches",
     * beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
     *
     * return new ModelAndView("newLoanSchedulePayment", model); }
****************************************************************************************************************************************************
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private LoanRepaymentSchedule prepareModel(LoanRepaymentScheduleBean loanRepaymentScheduleBean) {
        LoanRepaymentSchedule loanRepaymentSchedule = new LoanRepaymentSchedule();

        loanRepaymentSchedule.setCompanyId(loanRepaymentScheduleBean.getCompanyId());
        loanRepaymentSchedule.setBranchId(loanRepaymentScheduleBean.getBranchId());

        loanRepaymentSchedule.setLoanCaseId(loanRepaymentScheduleBean.getLoanCaseId());
        loanRepaymentSchedule.setActualRepaymentAmount(loanRepaymentScheduleBean.getActualRepaymentAmount());
        loanRepaymentSchedule.setActualRepaymentDate(loanRepaymentScheduleBean.getActualRepaymentDate());
        loanRepaymentSchedule.setAmountInterest(loanRepaymentScheduleBean.getAmountInterest());
        loanRepaymentSchedule.setAmountPrincipal(loanRepaymentScheduleBean.getAmountPrincipal());
        loanRepaymentSchedule.setExpectedRepaymentAmount(loanRepaymentScheduleBean.getExpectedRepaymentAmount());
        loanRepaymentSchedule.setExpectedRepaymentDate(loanRepaymentScheduleBean.getExpectedRepaymentDate());
        loanRepaymentSchedule.setMemberNo(loanRepaymentScheduleBean.getMemberNo());
        loanRepaymentSchedule.setPaymentStatus(loanRepaymentScheduleBean.getPaymentStatus());
        loanRepaymentSchedule.setPenaltyIncurred(loanRepaymentScheduleBean.getPenaltyIncurred());
        loanRepaymentSchedule.setCummPrincipal(loanRepaymentScheduleBean.getCummPrincipal());

        loanRepaymentSchedule.setCreatedBy(loanRepaymentScheduleBean.getCreatedBy());
        loanRepaymentSchedule.setCreationDate(loanRepaymentScheduleBean.getCreationDate());
        loanRepaymentSchedule.setLastModifiedBy(loanRepaymentScheduleBean.getLastModifiedBy());
        loanRepaymentSchedule.setLastModificationDate(loanRepaymentScheduleBean.getLastModificationDate());
        loanRepaymentSchedule.setId(loanRepaymentScheduleBean.getId());
        loanRepaymentSchedule.setActive(loanRepaymentScheduleBean.getActive());

        return loanRepaymentSchedule;
    }

    private List<LoanRepaymentScheduleBean> prepareListofBean(List<LoanRepaymentSchedule> loanRepaymentSchedules) {
        List<LoanRepaymentScheduleBean> beans = null;

        if (loanRepaymentSchedules != null && !loanRepaymentSchedules.isEmpty()) {
            beans = new ArrayList<LoanRepaymentScheduleBean>();
            LoanRepaymentScheduleBean loanRepaymentSchedule = null;

            for (LoanRepaymentSchedule loanRepaymentScheduleBean : loanRepaymentSchedules) {
                loanRepaymentSchedule = new LoanRepaymentScheduleBean();

                loanRepaymentSchedule.setId(loanRepaymentScheduleBean.getId());

                loanRepaymentSchedule.setCompanyId(loanRepaymentScheduleBean.getCompanyId());
                loanRepaymentSchedule.setBranchId(loanRepaymentScheduleBean.getBranchId());

                loanRepaymentSchedule.setLoanCaseId(loanRepaymentScheduleBean.getLoanCaseId());
                loanRepaymentSchedule.setActualRepaymentAmount(loanRepaymentScheduleBean.getActualRepaymentAmount());
                loanRepaymentSchedule.setActualRepaymentDate(loanRepaymentScheduleBean.getActualRepaymentDate());
                loanRepaymentSchedule.setAmountInterest(loanRepaymentScheduleBean.getAmountInterest());
                loanRepaymentSchedule.setAmountPrincipal(loanRepaymentScheduleBean.getAmountPrincipal());
                loanRepaymentSchedule.setExpectedRepaymentAmount(loanRepaymentScheduleBean.getExpectedRepaymentAmount());
                loanRepaymentSchedule.setExpectedRepaymentDate(loanRepaymentScheduleBean.getExpectedRepaymentDate());
                loanRepaymentSchedule.setMemberNo(loanRepaymentScheduleBean.getMemberNo());
                loanRepaymentSchedule.setPaymentStatus(loanRepaymentScheduleBean.getPaymentStatus());
                loanRepaymentSchedule.setPenaltyIncurred(loanRepaymentScheduleBean.getPenaltyIncurred());
                loanRepaymentSchedule.setCummPrincipal(loanRepaymentScheduleBean.getCummPrincipal());

                loanRepaymentSchedule.setCreatedBy(loanRepaymentScheduleBean.getCreatedBy());
                loanRepaymentSchedule.setCreationDate(loanRepaymentScheduleBean.getCreationDate());
                loanRepaymentSchedule.setLastModifiedBy(loanRepaymentScheduleBean.getLastModifiedBy());
                loanRepaymentSchedule.setLastModificationDate(loanRepaymentScheduleBean.getLastModificationDate());
                loanRepaymentSchedule.setActive(loanRepaymentScheduleBean.getActive());

                beans.add(loanRepaymentSchedule);
            }
        }

        return beans;
    }

    private LoanRepaymentScheduleBean prepareLoanRepaymentScheduleBean(LoanRepaymentSchedule loanRepaymentSchedule) {
        LoanRepaymentScheduleBean bean = new LoanRepaymentScheduleBean();

        bean.setId(loanRepaymentSchedule.getId());
        bean.setCompanyId(loanRepaymentSchedule.getCompanyId());
        bean.setBranchId(loanRepaymentSchedule.getBranchId());

        bean.setLoanCaseId(loanRepaymentSchedule.getLoanCaseId());
        bean.setActualRepaymentAmount(loanRepaymentSchedule.getActualRepaymentAmount());
        bean.setActualRepaymentDate(loanRepaymentSchedule.getActualRepaymentDate());
        bean.setAmountInterest(loanRepaymentSchedule.getAmountInterest());
        bean.setAmountPrincipal(loanRepaymentSchedule.getAmountPrincipal());
        bean.setExpectedRepaymentAmount(loanRepaymentSchedule.getExpectedRepaymentAmount());
        bean.setExpectedRepaymentDate(loanRepaymentSchedule.getExpectedRepaymentDate());
        bean.setMemberNo(loanRepaymentSchedule.getMemberNo());
        bean.setPaymentStatus(loanRepaymentSchedule.getPaymentStatus());
        bean.setPenaltyIncurred(loanRepaymentSchedule.getPenaltyIncurred());
        bean.setCummPrincipal(loanRepaymentSchedule.getCummPrincipal());

        bean.setCreatedBy(loanRepaymentSchedule.getCreatedBy());
        bean.setCreationDate(loanRepaymentSchedule.getCreationDate());
        bean.setLastModifiedBy(loanRepaymentSchedule.getLastModifiedBy());
        bean.setLastModificationDate(loanRepaymentSchedule.getLastModificationDate());
        bean.setActive(loanRepaymentSchedule.getActive());

        return bean;
    }
}