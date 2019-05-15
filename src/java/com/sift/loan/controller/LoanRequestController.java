package com.sift.loan.controller;

import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.bean.MemberSummaryBean;
import com.sift.admin.bean.MemberViewBean;
import com.sift.admin.interfaces.*;
import com.sift.admin.model.AppConfiguration;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.LoanRepayFreq;
import com.sift.admin.model.MemberView;
import com.sift.admin.model.User;
import com.sift.admin.service.AppConfigurationService;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.InterestTypeService;
import com.sift.admin.service.LoanRepayFreqService;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.service.UserService;
import com.sift.gl.*;
import com.sift.gl.dao.Accountsenqdao;
import com.sift.gl.dao.Accountsetupdao;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.SMSBean;
import com.sift.gl.model.SmsLog;
import com.sift.gl.service.FileUploadItemsService;
import com.sift.loan.bean.LoanGuarantorBean;
import com.sift.loan.bean.LoanRequestBean;
import com.sift.loan.bean.LoanRequestExceptionBean;
import com.sift.loan.bean.ProductBean;
import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.model.LoanGuarantorChange;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.model.Product;
import com.sift.loan.service.LoanGuarantorService;
import com.sift.loan.service.LoanGuarantorServiceChange;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestExceptionService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.webservice.service.LoanRequestServiceWS;
import com.sift.webservice.utility.WebServiceUtility;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanRequestController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CurrentuserdisplayImpl currentuserdisplayService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private LoanRequestService loanRequestService;
    @Autowired
    private LoanRepayFreqService loanRepayFreqService;
    @Autowired
    private LoanRepaymentScheduleService loanRepaymentScheduleService;
    @Autowired
    private MemberViewService memberViewService;
    @Autowired
    private LoanGuarantorService loanGuarantorService;
    @Autowired
    private LoanGuarantorServiceChange loanGuarantorServiceChange;
    @Autowired
    private LoanRequestExceptionService loanRequestExceptionService;
    @Autowired
    private AppConfigurationService appConfigurationService;
    @Autowired
    private InterestTypeService interestTypeService;
    @Autowired
    public LoanRequestServiceWS loanRequestServiceWS;
    @Autowired
    private FileUploadItemsService fileUploadItemsService;
    BeanMapperUtility beanMapper = new BeanMapperUtility();
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    WebServiceUtility webServiceUtility = new WebServiceUtility();

    @RequestMapping(value = "/processLoanRequestApproval", method = RequestMethod.POST)
    public ModelAndView processLoanRequestApproval(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        LoanRequest obj = loanRequestService.getLoanRequest(loanRequestBean.getId());

        Company coy = companyService.getCompany(Integer.parseInt(obj.getCompanyId()));
        String timeZone = countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone();
        Date localDate = eazyCoopUTIL.getTimeByZone(timeZone);
        String loantitle = "";
        loanRequestBean.setLastModifiedBy(req.getRemoteUser());
        loanRequestBean.setLastModificationDate(new java.util.Date());
        loanRequestBean.setCreatedBy(obj.getRequestBy());
        loanRequestBean.setCreationDate(obj.getRequestDate());
        loanRequestBean.setApprovedBy(req.getRemoteUser());
        loanRequestBean.setApprovalDate(localDate);
        loanRequestBean.setRequestBy(obj.getRequestBy());
        loanRequestBean.setRequestDate(obj.getRequestDate());

        MemberView mem = memberViewService.getMember(loanRequestBean.getMemberNo());

        boolean created = false;

        //int loanCount=1+ loanRequestService.getLoanRequestsCountByMember(obj.getMemberNo());
        //String countStr=String.valueOf(loanCount);	 
        String countStr = helperUTIL.getActiveLoanProductsCount(mem.getMemberNo(), obj.getLoanType(), obj.getBranchId(), obj.getCompanyId());
        countStr = countStr == null ? "0" : countStr;
        int countValue = 1 + Integer.parseInt(countStr);
        String counterStr = String.valueOf(countValue);

        if (counterStr.length() < 2) {
            counterStr = "0" + counterStr;
        }

        Activitylog activity = new Activitylog();

        if ("R".equals(loanRequestBean.getRequestStatus())) {
            loanRequestBean.setLoanStatus(Definitions.LOAN_STATUS_REJECTED);

            LoanRequest loanRequest = prepareModel(loanRequestBean);
            loanRequestService.rejectLoanRequest(loanRequest);
            created = true;

            // call easycoop loan request rejected web service
            try {
                int branchid = 0;
                //LoanRequestWS easyCoopLoanId = loanRequestServiceWS.getEasyCoopLoanId(loanRequestBean.getLoanCaseId());

                try {
                    branchid = Integer.parseInt(obj.getBranchId());
                } catch (NumberFormatException nuex) {
                }
                int easyCoopLoanId = loanRequestServiceWS.getEasyCoopLoanId(loanRequestBean.getLoanCaseId());
                Branch branch = branchService.getBranch(branchid);
                if (easyCoopLoanId != 0 && branch.getConnectToEazyCoop().equalsIgnoreCase("Y")) {
                    String resource = "uploanreq";

                    webServiceUtility.webserviceClient(resource, webServiceUtility.
                            apprOrRejEasyCoopLoan(
                            easyCoopLoanId, "1", loanRequestBean.getApprovalComment(), "0"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }// easycoop web service end


            activity.setEvent(Definitions.EVENT_LOAN_APPROVAL);
            activity.setAction("Loan Request was rejected for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem(loanRequest.getLoanCaseId());
            activity.setActionResult("Loan Request Rejection for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setDescription("Loan Request Rejection for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
            activity.setBranchid(Integer.parseInt(obj.getBranchId()));
        } else if ("A".equals(loanRequestBean.getRequestStatus())) {
            loanRequestBean.setLoanStatus(Definitions.LOAN_STATUS_APPROVED);
            GenerateAccountno acctgen = new GenerateAccountno();

            //generating loan schedules
            //eazyCoopUTIL		 
            ArrayList<LoanRepaymentSchedule> schedules = null;
            java.util.Date startDate = null;

            LoanRepayFreq repayFreq = loanRepayFreqService.getLoanRepayFreqCustomized("code", loanRequestBean.getRepayFrequency());
            
            if ("FFL".equals(loanRequestBean.getInterestType())) {
                schedules = eazyCoopUTIL.generateLoanScheduleFlat(loanRequestBean, repayFreq, loanRequestBean.getActualCommencementDate(), loanRequestBean.getLastModifiedBy(), loanRequestBean.getLastModificationDate());
            } else if ("RED".equals(loanRequestBean.getInterestType())) {
                schedules = eazyCoopUTIL.generateLoanScheduleReducing(loanRequestBean, repayFreq, loanRequestBean.getActualCommencementDate(), loanRequestBean.getLastModifiedBy(), loanRequestBean.getLastModificationDate());
            } else if ("REQ".equals(loanRequestBean.getInterestType())) {
                schedules = eazyCoopUTIL.generateLoanScheduleReducingEqualInstallments(loanRequestBean, repayFreq, loanRequestBean.getActualCommencementDate(), loanRequestBean.getLastModifiedBy(), loanRequestBean.getLastModificationDate());
            } else if ("FFC".equals(loanRequestBean.getInterestType())) {
                schedules = eazyCoopUTIL.generateLoanScheduleFlatCoop(loanRequestBean, repayFreq, loanRequestBean.getActualCommencementDate(), loanRequestBean.getLastModifiedBy(), loanRequestBean.getLastModificationDate());
            }


            String loanAccountNo = null;
            Accnowbs accnowbs = new Accnowbs();
            Branch brnch = branchService.getBranch(Integer.parseInt(mem.getBranchId()));

            accnowbs.setBranchcode(brnch.getBranchCode());
            accnowbs.setBranchId(Integer.parseInt(mem.getBranchId()));
            accnowbs.setCompany(Integer.parseInt(mem.getCompanyId()));
            accnowbs.setCompanycode(eazyCoopUTIL.deriveCompanyCode(mem.getCompanyId()));
            accnowbs.setProductcode(obj.getLoanType());
            accnowbs.setSubno(counterStr);
            accnowbs.setCustomerno(mem.getMemberNo());
            accnowbs.setTimezone(timeZone);

            try {
                loanAccountNo = eazyCoopUTIL.createProductAccount(accnowbs);

                if (loanAccountNo.indexOf("OK.") != -1) {
                    created = true;
                    loanAccountNo = loanAccountNo.trim().toUpperCase().replace("OK.", "");

                    if (loanAccountNo != null) {
                        loanAccountNo = loanAccountNo.trim();
                        Accountsetupdao adao = new Accountsetupdao();
                        loantitle = adao.retrieveAccountstrfld(loanAccountNo, Integer.parseInt(mem.getBranchId()), Integer.parseInt(mem.getCompanyId()), "Name");
                        loantitle = loantitle.trim();
                        if (loantitle.length()>200) {
                         loantitle = loantitle.substring(0, 199);
                        } 
                        adao = null;
                    }

                    loanRequestBean.setLoanAccountNo(loanAccountNo);
                    loanRequestBean.setLoantitle(loantitle);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            LoanRequest loanRequest = prepareModel(loanRequestBean);

            //loanRequestService.addLoanRequest(loanRequest);	 
            //we need to generate loan schedule here such that it get displayed on the user's feedback page
            System.out.println("loanAccountNo=" + loanAccountNo);
            //created=false;
            if (created) {
                boolean nice = loanRequestService.approveLoanRequest(loanRequest, schedules);
                System.out.println("I am inside created condition ");
                //create schedule and dispatch to user's email
                if (nice) {
                    eazyCoopUTIL.generateLoanScheduleForDispatch(schedules, loanRequest, mem, coy);

                    //call easycoop loan request approved web service   
                    try {
                        int branchid = 0;
                        try {
                            branchid = Integer.parseInt(obj.getBranchId());
                        } catch (NumberFormatException nuex) {
                        }
                        int easyCoopLoanId = loanRequestServiceWS.getEasyCoopLoanId(loanRequestBean.getLoanCaseId());
                        Branch branch = branchService.getBranch(branchid);
                        if (easyCoopLoanId != 0 && branch.getConnectToEazyCoop().equalsIgnoreCase("Y")) {
                            String resource = "uploanreq";

                            webServiceUtility.webserviceClient(resource, webServiceUtility.
                                    apprOrRejEasyCoopLoan(
                                    easyCoopLoanId, "0", loanRequestBean.getApprovalComment(), "1"));
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // easycoop web service end                  
                }
            } else {
                //what do i do here... thinking			 
            }

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_APPROVAL);
            activity.setAction("Loan Request Approval for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + loanRequest.getLoanCaseId());
            activity.setActionResult("Loan Request Approval for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setDescription("Loan Request Approval for Loan ID: " + loanRequest.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
            activity.setBranchid(Integer.parseInt(obj.getBranchId()));
        }

        if (created) {
            try {
                eazyCoopUTIL.LogUserAction(activity);
            } catch (Exception ex) {
            }

            return new ModelAndView("redirect:/doFeedback.htm?message=Loan Request Approval/Rejection was successful&redirectURI=viewLoanSchedule.htm?id=" + loanRequestBean.getId());
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Loan Request Approval/Rejection failed. Please try again later&redirectURI=index.htm");
        }
    }

    @RequestMapping(value = "/doDisburseLoan", method = RequestMethod.POST)
    public ModelAndView doDisburseLoan(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        LoanRequest obj = loanRequestService.getLoanRequest(loanRequestBean.getId());

        Company coy = companyService.getCompany(Integer.parseInt(obj.getCompanyId()));
        Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

        loanRequestBean.setLastModifiedBy(req.getRemoteUser());
        loanRequestBean.setLastModificationDate(localDate);
        loanRequestBean.setCreatedBy(obj.getRequestBy());
        loanRequestBean.setCreationDate(obj.getRequestDate());

        loanRequestBean.setDisburseBy(req.getRemoteUser());
        loanRequestBean.setDisburseDate(localDate);

        loanRequestBean.setRequestBy(obj.getRequestBy());
        loanRequestBean.setRequestDate(obj.getRequestDate());

        loanRequestBean.setLoanAccountNo(obj.getLoanAccountNumber());
        loanRequestBean.setCompanyId(obj.getCompanyId());
        loanRequestBean.setBranchId(obj.getBranchId());

        loanRequestBean.setLoanCaseId(obj.getLoanCaseId());
        loanRequestBean.setLoanStatus(Definitions.LOAN_STATUS_DISBURSED);
        loanRequestBean.setRequestStatus(Definitions.LOAN_STATUS_DISBURSED);
        loanRequestBean.setLoantitle(obj.getLoantitle());
        loanRequestBean.setLoanIntTotal(obj.getLoanIntTotal());
        LoanRequest loanRequest = prepareModel(loanRequestBean);
        System.out.println("Post Date:=" + currentuserdisplayService.getCurrusercompany().getPostDate());

        //two things
        //1. do service call 
        //2. update loan status

        eazyCoopUTIL.setHelperUTIL(helperUTIL);
        String timeZone = countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone();
        String ChangeContraAcct= req.getParameter("disbursementAcct");
        System.err.println("Contract Account.... "+ChangeContraAcct);
        if (eazyCoopUTIL.doDisburseLoanToCustomerContraAcct(loanRequest, coy, timeZone, currentuserdisplayService.getCurrusercompany().getPostDate(),ChangeContraAcct)) {
          /**
             * *loanRequest.setBalanceInterest(loanRequest.getLoanIntTotal());
             * loanRequest.setBalancePrincipal(loanRequest.getApprovedAmount());
             * loanRequest.setBalanceTotal(loanRequest.getApprovedAmount() +
             * loanRequest.getLoanIntTotal());**
             */
            boolean good = loanRequestService.doDisburseLoan(loanRequest);

            Activitylog activity = new Activitylog();

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_DISBURSEMENT);
            activity.setAction("Loan Disbursement for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + loanRequestBean.getLoanCaseId());
            activity.setActionResult("Loan Disbursement for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setDescription("Loan Disbursement for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequestBean.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
            activity.setBranchid(Integer.parseInt(obj.getBranchId()));


            if (good) {
                try {
                    eazyCoopUTIL.LogUserAction(activity);
                } catch (Exception ex) {
                }
            }

            return new ModelAndView("redirect:/doFeedback.htm?message=Loan Disbursement Processing was successful&redirectURI=index.htm?id=" + loanRequestBean.getId());
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Loan Disbursement Processing Failed&redirectURI=disburseLoanRequests0.htm?id=" + loanRequestBean.getId());
        }

    }

    // cancel disburse loan
    @RequestMapping(value = "/cancelDisburseLoan", method = RequestMethod.POST)
    public ModelAndView cancelDisburseLoan(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        LoanRequest obj = loanRequestService.getLoanRequest(loanRequestBean.getId());

        Company coy = companyService.getCompany(Integer.parseInt(obj.getCompanyId()));
        Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

        loanRequestBean.setLastModifiedBy(req.getRemoteUser());
        loanRequestBean.setLastModificationDate(localDate);
        loanRequestBean.setCreatedBy(obj.getRequestBy());
        loanRequestBean.setCreationDate(obj.getRequestDate());

        loanRequestBean.setDisburseBy(req.getRemoteUser());
        loanRequestBean.setDisburseDate(localDate);

        loanRequestBean.setRequestBy(obj.getRequestBy());
        loanRequestBean.setRequestDate(obj.getRequestDate());

        loanRequestBean.setLoanAccountNo(obj.getLoanAccountNumber());
        loanRequestBean.setCompanyId(obj.getCompanyId());
        loanRequestBean.setBranchId(obj.getBranchId());

        loanRequestBean.setLoanCaseId(obj.getLoanCaseId());
        loanRequestBean.setLoanStatus(Definitions.LOAN_STATUS_CANCELLED);
        loanRequestBean.setRequestStatus(Definitions.LOAN_STATUS_CANCELLED);

        LoanRequest loanRequest = prepareModel(loanRequestBean);
        //System.out.println("Post Date:=" + currentuserdisplayService.getCurrusercompany().getPostDate());

        //two things
        //1. do service call 
        //2. update loan status

        eazyCoopUTIL.setHelperUTIL(helperUTIL);
        //String timeZone = countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone();
        boolean good = loanRequestService.doDisburseLoan(loanRequest);
        if (good) {

            Activitylog activity = new Activitylog();

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_DISBURSEMENT_CANCEL);
            activity.setAction("LN Disburse Cancel Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + loanRequestBean.getLoanCaseId());
            activity.setActionResult("LN Disbursefor Cancel Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setDescription("LN Disbursefor Cancel Loan ID:  " + loanRequestBean.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequestBean.getCompanyId()));
            activity.setToDate("");
            activity.setCompanyid(Integer.parseInt(obj.getCompanyId()));
            activity.setBranchid(Integer.parseInt(obj.getBranchId()));


            if (good) {
                try {
                    eazyCoopUTIL.LogUserAction(activity);
                } catch (Exception ex) {
                }
            }

            return new ModelAndView("redirect:/doFeedback.htm?message=Loan Disbursement Cancellation Processing was successful&redirectURI=disburseLoanRequests0.htm");
        } else {
            return new ModelAndView("redirect:/doError.htm?message=Loan Disbursement Processing Failed&redirectURI=disburseLoanRequests0.htm?id=" + loanRequestBean.getId());
        }

    }

    @RequestMapping(value = "/saveLoanRequest", method = RequestMethod.POST)
    public ModelAndView saveLoanRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        String actionId = req.getParameter("ACTION_ID");

        Company coy = companyService.getCompany(Integer.parseInt(loanRequestBean.getCompanyId()));
        //System.out.print("coy.getId():="+coy.getId());
        //System.out.print("countryService:="+countryService.toString());

        Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());
        Activitylog activity = new Activitylog();

        if ("2".equals(actionId)) {
            loanRequestBean.setCreatedBy(loanRequestService.getLoanRequest(loanRequestBean.getId()).getRequestBy());
            loanRequestBean.setLastModifiedBy(req.getRemoteUser());
            loanRequestBean.setLastModificationDate(localDate);

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_INITIATION);
            activity.setAction("Loan Request Modification for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + loanRequestBean.getLoanCaseId());
            activity.setActionResult("Loan Request Approval for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setDescription("Loan Request Approval for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setCompanyid(Integer.parseInt(loanRequestBean.getCompanyId()));
            activity.setBranchid(Integer.parseInt(loanRequestBean.getBranchId()));
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequestBean.getCompanyId()));
            activity.setToDate("");


        } else {
            loanRequestBean.setCreationDate(localDate);

            //Audit action
            activity.setEvent(Definitions.EVENT_LOAN_INITIATION);
            activity.setAction("Loan Request Initiation for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setActionDate(new java.util.Date());
            activity.setActionItem("Loan ID:" + loanRequestBean.getLoanCaseId());
            activity.setActionResult("Loan Initiation for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setDescription("Loan Request Initiation for Loan ID: " + loanRequestBean.getLoanCaseId());
            activity.setIpaddress(req.getRemoteHost());
            activity.setUsername(req.getRemoteUser());
            activity.setCompanyid(Integer.parseInt(loanRequestBean.getCompanyId()));
            activity.setBranchid(Integer.parseInt(loanRequestBean.getBranchId()));
            activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequestBean.getCompanyId()));
            activity.setToDate("");
        }

        LoanRequest loanRequest = prepareModel(loanRequestBean);

        int guarantorCount = Integer.parseInt(req.getParameter("guarantorCount"));
        int exceptionCount = Integer.parseInt(req.getParameter("exceptionCount"));

        System.out.println("guarantorCount:=" + guarantorCount);
        System.out.println("exceptionCount:=" + exceptionCount);

        LoanGuarantor lg = null;
        ArrayList<LoanGuarantor> lgList = null;
        ArrayList<String> guarantorMemberNos = new ArrayList<String>();

        for (int k = 1; k <= guarantorCount; k++) {
            String guarantorMemberId = req.getParameter("guarantor_" + k);
            lg = new LoanGuarantor();
            if (lgList == null) {
                lgList = new ArrayList<LoanGuarantor>();
            }

            lg.setCompanyId(loanRequestBean.getCompanyId());
            lg.setBranchId(loanRequestBean.getBranchId());
            lg.setGuarantorNo(guarantorMemberId);
            lg.setMemberNo(loanRequestBean.getMemberNo());
            lg.setLoanCaseId(loanRequestBean.getLoanCaseId());
            lg.setRequestDate(loanRequestBean.getRequestDate());
            lg.setAcceptanceStatus("N");

            guarantorMemberNos.add(guarantorMemberId);

            lgList.add(lg);
        }

        LoanRequestException lre = null;
        ArrayList<LoanRequestException> lreList = null;

        for (int k = 1; k <= exceptionCount; k++) {
            String exceptionMessage = req.getParameter("exception_" + k);
            lre = new LoanRequestException();

            if (lreList == null) {
                lreList = new ArrayList<LoanRequestException>();
            }

            lre.setExceptionMessage(exceptionMessage);
            lre.setCreationDate(loanRequestBean.getRequestDate());
            lre.setExceptionStatus(0);
            lre.setClosureStatus("N");
            lre.setLoanCaseId(loanRequestBean.getLoanCaseId());

            lreList.add(lre);
        }

        //save loan details,guarantors &  exceptions  if any
        loanRequest.setAppliedRate(loanRequest.getProductRate());
        loanRequest.setApprovedAmount(loanRequest.getRequestedAmount());

        boolean good = loanRequestService.addLoanRequest(loanRequest, lreList, lgList);

        if (good) {
            try {
                eazyCoopUTIL.LogUserAction(activity);
            } catch (Exception ex) {
            }

            //sms guarantors of this request
            for (String item : guarantorMemberNos) {
                MemberView mem = memberViewService.getMember(item);

                if (mem != null) {
                    System.out.println("Mobile No:" + mem.getPhone1());

                    //Send SMS to user
                    try {
                        String message = "You have been specified as a Guarantor for a Loan Request [ID: " + loanRequestBean.getLoanCaseId() + "]";
                        SMSBean sms = new SMSBean();

                        sms.setMessage(message);
                        sms.setSender(Definitions.SMS_DEFAULT_SENDER);
                        sms.setSendto(mem.getPhone1());
                        sms.setMsgtype(Definitions.SMS_DEFAULT_MESSAGE_TYPE);
                        sms.setSendtime(null);

                        eazyCoopUTIL.sendSMS(sms);
                        //log sms
                        SmsLog smsLog = new SmsLog();

                        smsLog.setEventId(Definitions.EVENT_LOAN_INITIATION);
                        smsLog.setDescription("Loan ID:" + loanRequestBean.getLoanCaseId());
                        smsLog.setAction("Loan Initiation for Loan ID: " + loanRequestBean.getLoanCaseId());
                        smsLog.setUsername(req.getRemoteUser());
                        smsLog.setMobile(mem.getPhone1());
                        smsLog.setMessage(message);
                        smsLog.setStatus("Y");
                        smsLog.setActionDate(localDate);
                        smsLog.setActionItem("Loan :" + loanRequestBean.getLoanCaseId());
                        smsLog.setActionResult("Loan Request Initiation for Loan ID: " + loanRequestBean.getLoanCaseId());
                        smsLog.setCompanyId(Integer.parseInt(loanRequestBean.getCompanyId()));
                        smsLog.setBranchId(Integer.parseInt(loanRequestBean.getBranchId()));

                        fileUploadItemsService.addSmsLog(smsLog);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        //return new ModelAndView("redirect:/newLoanRequest0.htm");
        return new ModelAndView("redirect:/doFeedback.htm?message=Loan Request Initiation was successful&redirectURI=newLoanRequest0.htm?id=");
    }

    @RequestMapping(value = "/updateLoanRequest", method = RequestMethod.POST)
    public ModelAndView updateLoanRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {

        LoanRequest loanRequest = loanRequestService.getLoanRequest(loanRequestBean.getId());
        Company coy = companyService.getCompany(Integer.parseInt(loanRequest.getCompanyId()));
        Date localDate = eazyCoopUTIL.getTimeByZone(countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());

        loanRequest.setProductRate(loanRequestBean.getProductRate());
        loanRequest.setAppliedRate(loanRequestBean.getProductRate());
        loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
        loanRequest.setApprovedAmount(loanRequestBean.getRequestedAmount());

        loanRequest.setInterestType(loanRequestBean.getInterestType());
        loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
        loanRequest.setDuration(loanRequestBean.getDuration());

        loanRequest.setProposedCommencementDate(loanRequestBean.getProposedCommencementDate());
        loanRequest.setActualCommencementDate(loanRequestBean.getProposedCommencementDate());

        loanRequest.setLastModifiedBy(req.getRemoteUser());
        loanRequest.setLastModificationDate(localDate);

        //update request
        loanRequestService.addLoanRequest(loanRequest, null, null);

        //return new ModelAndView("redirect:/newLoanRequest0.htm");
        return new ModelAndView("redirect:/doFeedback.htm?message=Loan Request Modification was successful&redirectURI=newLoanRequest0.htm?id=");
    }

    @RequestMapping(value = "/loanRequests", method = RequestMethod.GET)
    public ModelAndView listLoanRequests() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRequests", prepareListofBean(loanRequestService.listLoanRequests()));
        return new ModelAndView("loanRequestsList", model);
    }

    @RequestMapping(value = "/getProductRate", method = RequestMethod.GET)
    public @ResponseBody
    List<ProductBean> productRateInfo(@RequestParam(value = "loanType", required = true) String productCode) {
        ArrayList<ProductBean> listP = new ArrayList<ProductBean>();
        listP.add(beanMapper.prepareProductBean(productService.getProductByTypeCode(productCode)));

        return listP;
    }

    @RequestMapping(value = "/newLoanRequest0", method = RequestMethod.GET)
    public ModelAndView addLoanRequest0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        //model.put("companies",     	beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
        //model.put("loanRepayFreqs",  beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqs()));  	 

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //model.put("products", 	beanMapper.prepareListofProductBean(productService.listProducts(currentUser.getCompanyId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsByBranch(currentUser.getBranchId())));
        //model.put("loanRequests",  prepareListofBean(loanRequestService.listLoanRequestsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId())));
        model.put("loanRequests", helperUTIL.getLoanRequestList(currentUser.getBranchId()));

        loanRequestBean.setCompanyId(currentUser.getCompanyId());
        loanRequestBean.setBranchId(currentUser.getBranchId());

        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
        //model.put("branches",     	beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));

        model.put("loanRequest", loanRequestBean);

        /**
         * ********************************************************************************************************************
         ***********************************************************************************************************************
         ***********************************************************************************************************************
         * Map<String, String> configMap = new HashMap<String, String>(); //get
         * the following configuration by country and populate this map.
         *
         * String MAX_CON_LOAN="2"; String MAX_GUA_NUM="2"; String
         * MAX_LOAN_SUM="2"; String MAX_LEN_STAY="180";
         *
         * \
         * List<AppConfiguration>
         * loanConfigs=appConfigurationService.listAppConfigurations("LOAN",
         * companyId);
         *
         * if(loanConfigs!=null && loanConfigs.size()>0){ for(AppConfiguration
         * item: loanConfigs){
         * if(item.getConfigName().equalsIgnoreCase("CONCURRENT
         * LOAN")){MAX_CON_LOAN=item.getConfigMaxValue();} else
         * if(item.getConfigName().equalsIgnoreCase("RUNNING LOAN
         * SUM")){MAX_LOAN_SUM=item.getConfigMaxValue();} else
         * if(item.getConfigName().equalsIgnoreCase("LOAN
         * GUARANTORS")){MAX_GUA_NUM=item.getConfigMaxValue();} else
         * if(item.getConfigName().equalsIgnoreCase("LENGTH OF
         * STAY")){MAX_LEN_STAY=item.getConfigMaxValue();} } } *
         * configMap.put("MAX_CON_LOAN",MAX_CON_LOAN);
         * configMap.put("MAX_GUA_NUM", MAX_GUA_NUM);
         * configMap.put("MAX_LOAN_SUM",MAX_LOAN_SUM);
         * configMap.put("MAX_LEN_STAY",MAX_LEN_STAY);
         *
         * model.put("configMap", configMap);
         *
         ***********************************************************************************************************************
         ***********************************************************************************************************************
         *********************************************************************************************************************
         */
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByBranchId(currentUser.getBranchId())));
        return new ModelAndView("/addLoanRequest0", model);
    }

    @RequestMapping(value = "/viewApprovedRequest", method = RequestMethod.GET)
    public ModelAndView viewApprovedRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        //model.put("members",    		beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        //model.put("membersInc",      beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(),obj.getMemberNo())));

        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByBranchId(obj.getCompanyId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchId(obj.getCompanyId(), obj.getMemberNo())));

        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));

        //we need to get member details and exceptions details
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        //double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(obj.getMemberNo(), "0", 6, true), obj.getCompanyId(), obj.getBranchId());
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());


        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
        memberSummaryBean.setTotalMemberContribution(totalContribution);
        System.out.println("totalContribution :: " + totalContribution);

        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            //double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(guarantorMemberId, "0", 6, true), obj.getCompanyId(), obj.getBranchId());
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(guarantorMemberId), obj.getCompanyId(), obj.getBranchId());


            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);
            System.out.println("Guarantor k :: " + totalContributionX);
            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);

        return new ModelAndView("viewApprovedRequest", model);
    }

    @RequestMapping(value = "/viewLoanSchedule", method = RequestMethod.GET)
    public ModelAndView viewLoanSchedule(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));

        model.put("loanRequest", obj);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCode(obj.getLoanType())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(), obj.getMemberNo())));

        return new ModelAndView("viewLoanSchedule", model);
    }

    @RequestMapping(value = "/loanRepayment1", method = RequestMethod.GET)
    public ModelAndView loanRepayment1(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));

        model.put("loanRequest", obj);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        // model.put("membersInc",  beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(),obj.getMemberNo())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchIdNew(obj.getBranchId(), obj.getMemberNo())));
        return new ModelAndView("loanRepayment1", model);
    }

    @RequestMapping(value = "/approveLoanRequest0", method = RequestMethod.GET)
    public ModelAndView appoveLoanRequest0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByBranchId(obj.getBranchId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchId(obj.getBranchId(), obj.getMemberNo())));
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes(obj.getInterestType())));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);
        
        //we need to get member details and exceptions details
        //List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true, obj.getCompanyId(), obj.getBranchId());
        
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        // for contribution
        
        //double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(obj.getMemberNo(), "0", 6, true), obj.getCompanyId(), obj.getBranchId());
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());
       //added by Nelson Akpomuje
        double memberBalance = helperUTIL.getMemberLoanBalance(obj.getMemberNo(), obj.getCompanyId(), obj.getBranchId());

        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {               
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }           
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
        //added by Nelson Akpos
        memberSummaryBean.setRunningLoanBalance(memberBalance);
        // for contribution
        memberSummaryBean.setTotalMemberContribution(totalContribution);
        System.out.println("totalContribution :: " + totalContribution);
        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            //double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(guarantorMemberId, "0", 6, true), obj.getCompanyId(), obj.getBranchId());
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(guarantorMemberId), obj.getCompanyId(), obj.getBranchId());
            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);
            System.out.println("Guarantor k :: " + totalContributionX);

            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);
        // for loan schedule
        ArrayList<LoanRepaymentSchedule> schedules = null;
        java.util.Date startDate = null;


        LoanRepayFreq repayFreq = loanRepayFreqService.getLoanRepayFreqCustomized("code", obj.getRepayFrequency());
       // System.out.println("obj.getRepayFrequency() :: " + obj.getRepayFrequency());
        //System.out.println("repayFreq :: " + repayFreq);
        //System.out.println("obj.getActualCommencementDate() :: " + obj.getActualCommencementDate());
        //System.out.println("obj.getLastModifiedBy() :: " + obj.getLastModifiedBy());
        //System.out.println("obj.getLastModificationDate() :: " + obj.getLastModificationDate());
        Company coy = companyService.getCompany(Integer.parseInt(obj.getCompanyId()));
        String timeZone = countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone();
        Date localDate = eazyCoopUTIL.getTimeByZone(timeZone);
        System.out.println("Current date  :: " + localDate);
        // obj.getActualCommencementDate()

        if ("FFL".equals(obj.getInterestType())) {
            schedules = eazyCoopUTIL.generateLoanScheduleFlat(obj, repayFreq, localDate, obj.getLastModifiedBy(), obj.getLastModificationDate());
        } else if ("RED".equals(obj.getInterestType())) {
            schedules = eazyCoopUTIL.generateLoanScheduleReducing(obj, repayFreq, localDate, obj.getLastModifiedBy(), obj.getLastModificationDate());
        } else if ("REQ".equals(obj.getInterestType())) {
            schedules = eazyCoopUTIL.generateLoanScheduleReducingEqualInstallments(obj, repayFreq, localDate, obj.getLastModifiedBy(), obj.getLastModificationDate());
        } else if ("FFC".equals(obj.getInterestType())) {
            schedules = eazyCoopUTIL.generateLoanScheduleFlatCoop(obj, repayFreq, localDate, obj.getLastModifiedBy(), obj.getLastModificationDate());
        }
        //System.out.println("schedules:: " + schedules.toString());
        //model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));
        model.put("schedules", schedules);

        return new ModelAndView("approveLoanRequest0", model);
    }

    @RequestMapping(value = "/updateLoanRequest0", method = RequestMethod.GET)
    public ModelAndView updateLoanRequest0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(), obj.getMemberNo())));
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);

        //we need to get member details and exceptions details
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;

        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());

        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;

            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());

            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);

        return new ModelAndView("updateLoanRequest0", model);
    }

    @RequestMapping(value = "/disburseLoanRequest1", method = RequestMethod.GET)
    public ModelAndView disburseLoanRequest1(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        //model.put("members",    		beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        //model.put("membersInc",      beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(),obj.getMemberNo())));

        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersExclusiveByBranchId(obj.getBranchId(), obj.getMemberNo())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchId(obj.getBranchId(), obj.getMemberNo())));

        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);
        Accountsenqdao acctEnqdao = new Accountsenqdao();
        model.put("disurseAccts", acctEnqdao.retrieveAccountsGenwthoutcntrl(Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId())));

        //we need to get member details and exceptions details
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        //double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(obj.getMemberNo(), "0", 6, true), obj.getCompanyId(), obj.getBranchId());
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());


        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
        memberSummaryBean.setTotalMemberContribution(totalContribution);

        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            //double totalContributionX = 0.00;
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(guarantorMemberId), obj.getCompanyId(), obj.getBranchId());
            //BigDecimal totalContributionX2 = new BigDecimal(helperUTIL.getMemberTotalSavings(helperUTIL.padValue(guarantorMemberId, "0", 6, true), obj.getCompanyId(), obj.getBranchId()));
            //totalContributionX = totalContributionX2.doubleValue();
            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);

            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));

        return new ModelAndView("disburseLoanRequest1", model);
    }

    @RequestMapping(value = "/viewLoanRequestDetails", method = RequestMethod.GET)
    public ModelAndView viewLoanRequestDetails(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(), obj.getMemberNo())));
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);

        //we need to get member details and exceptions details
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());
       //added by nelson akpos
        double memberBalance = helperUTIL.getMemberLoanBalance(obj.getMemberNo(), obj.getCompanyId(), obj.getBranchId());

        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
        memberSummaryBean.setTotalMemberContribution(totalContribution);
        //added by nelson akpos
        memberSummaryBean.setRunningLoanBalance(memberBalance);
        System.out.println("totalContribution :: " + totalContribution);

        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(guarantorMemberId), obj.getCompanyId(), obj.getBranchId());


            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);

            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));

        return new ModelAndView("viewLoanRequestDetails", model);
    }

    @RequestMapping(value = "/pendingLoanRequests", method = RequestMethod.GET)
    public ModelAndView pendingLoanRequests(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //List<LoanRequestBean> requests=beanMapper.prepareListofLoanRequestBean(loanRequestService.listPendingLoanRequestByBranchId(currentUser.getBranchId(),"E"));
        List<LoanRequestBean> requests = helperUTIL.getLoanRequestList(currentUser.getBranchId(), "E");

        model.put("loanRequests", requests);
        model.put("logonUser", logonUser);

        return new ModelAndView("pendingLoanRequest", model);
    }

    @RequestMapping(value = "/disburseLoanRequests0", method = RequestMethod.GET)
    public ModelAndView disburseLoanRequests0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //List<LoanRequestBean> requests=beanMapper.prepareListofLoanRequestBean(loanRequestService.listPendingLoanRequestByCompanyId(currentUser.getCompanyId(),"A"));
        //List<LoanRequestBean> requests=beanMapper.prepareListofLoanRequestBean(loanRequestService.listPendingLoanRequestByBranchId(currentUser.getBranchId(),"A"));
        List<LoanRequestBean> requests = helperUTIL.getLoanRequestList(currentUser.getBranchId(), "A");
        model.put("loanRequests", requests);
        model.put("logonUser", logonUser);

        return new ModelAndView("disburseLoanRequests0", model);
    }

    @RequestMapping(value = "/loanRepayment0", method = RequestMethod.GET)
    public ModelAndView loanRepayment0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        //List<LoanRequestBean> requests=beanMapper.prepareListofLoanRequestBean(loanRequestService.listPendingLoanRequestByCompanyId(currentUser.getCompanyId(),"D"));
        //List<LoanRequestBean> requests=beanMapper.prepareListofLoanRequestBean(loanRequestService.listPendingLoanRequestByBranchId(currentUser.getBranchId(),"D"));

        //List<LoanRequestBean> requests=helperUTIL.getLoanRequestList(currentUser.getBranchId(), "D");
        List<LoanRequestBean> requests = helperUTIL.getLoanRequestListByLoanStatus(currentUser.getBranchId(), "D");

        model.put("loanRequests", requests);

        return new ModelAndView("loanRepayment0", model);
    }

    @RequestMapping(value = "/newLoanRequest", method = RequestMethod.POST)
    public ModelAndView addLoanRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        //model.put("companies",     	beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
        //model.put("branches",     	beanMapper.prepareListofBranchBean(branchService.listBranch()));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqs()));

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsByBranch(currentUser.getBranchId())));
        //model.put("loanRequests",  	prepareListofBean(loanRequestService.listLoanRequestsByCompanyBranch(currentUser.getCompanyId(),currentUser.getBranchId())));
        model.put("loanRequests", helperUTIL.getLoanRequestList(currentUser.getBranchId()));

        loanRequestBean.setCompanyId(currentUser.getCompanyId());
        loanRequestBean.setBranchId(currentUser.getBranchId());

        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));

        Map<String, String> configMap = new HashMap<String, String>();

        //Get the following configuration by country and populate this map.  	
        String MAX_CON_LOAN = Definitions.DEFAULT_MAX_CON_LOAN;
        String MAX_GUA_NUM = Definitions.DEFAULT_MAX_GUA_NUM;
        String MAX_LOAN_SUM = Definitions.DEFAULT_MAX_LOAN_SUM;
        String MAX_LEN_STAY = Definitions.DEFAULT_MAX_LEN_STAY;

        java.text.DateFormat formatter = new SimpleDateFormat("ddMMyyyyhms");
        List<AppConfiguration> loanConfigs = appConfigurationService.listAppConfigurations("LOAN", loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());

        loanRequestBean.setNoOfInstallments(eazyCoopUTIL.deriveLoanInstallmentFromLoanDurationInMonths(loanRequestBean.getRepayFrequency(), loanRequestBean.getDuration()));
        //loanRequestBean.setProductRate(productService.getProductByTypeCode(loanRequestBean.getLoanType()).getInterestRate());
        Product productL = productService.getProductByTypeCode(loanRequestBean.getLoanType(), loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());
        Float productRate = productL.getInterestRate();

        loanRequestBean.setDuration(productL.getLoanDurationInMonth() == null ? 12 : productL.getLoanDurationInMonth());
        loanRequestBean.setProductRate(productRate);
        loanRequestBean.setInterestType(productService.getProductsDistinctByCodeByBranch(loanRequestBean.getBranchId(), loanRequestBean.getLoanType()).getLoanTypeCode());

        loanRequestBean.setLoanCaseId(loanRequestBean.getCompanyId() + loanRequestBean.getBranchId() + formatter.format(new java.util.Date()) + productService.getProductByTypeCode(loanRequestBean.getLoanType()).getCode());
        System.out.println("loanConfigs.size() A=:" + loanConfigs.size());
        if (loanConfigs != null && loanConfigs.size() > 0) {
            for (AppConfiguration item : loanConfigs) {
                if (item.getConfigName().equalsIgnoreCase("CONCURRENT LOAN")) {
                    MAX_CON_LOAN = item.getConfigMaxValue();
                } else if (item.getConfigName().equalsIgnoreCase("RUNNING LOAN SUM")) {
                    MAX_LOAN_SUM = item.getConfigMaxValue();
                } else if (item.getConfigName().equalsIgnoreCase("LOAN GUARANTORS")) {
                    System.out.println("setting values for Num Guarantor A=:" + item.getConfigMaxValue());
                    MAX_GUA_NUM = item.getConfigMaxValue();
                } else if (item.getConfigName().equalsIgnoreCase("LENGTH OF STAY")) {
                    MAX_LEN_STAY = item.getConfigMaxValue();
                }
            }
        }

        configMap.put("MAX_CON_LOAN", MAX_CON_LOAN);
        configMap.put("MAX_GUA_NUM", MAX_GUA_NUM);
        configMap.put("MAX_LOAN_SUM", MAX_LOAN_SUM);
        configMap.put("MAX_LEN_STAY", MAX_LEN_STAY);

        model.put("loanRequest", loanRequestBean);
        model.put("configMap", configMap);
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersExclusiveByBranchId(loanRequestBean.getBranchId(), loanRequestBean.getMemberNo())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchId(loanRequestBean.getBranchId(), loanRequestBean.getMemberNo())));

        return new ModelAndView("addLoanRequest", model);
    }

    @RequestMapping(value = "/newLoanRequest1", method = RequestMethod.POST)
    public ModelAndView addLoanRequest1(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(loanRequestBean.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(loanRequestBean.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(loanRequestBean.getBranchId(), loanRequestBean.getLoanType())));
        //model.put("loanRepayFreqs",  beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct(String.valueOf(loanRequestBean.getRepayFrequency()))));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", loanRequestBean.getRepayFrequency())));

        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);

        loanRequestBean.setProductRate(Double.parseDouble(req.getParameter("productRate") == null ? "0" : req.getParameter("productRate")));
        //loanRequestBean.setInterestType(productService.getProductsDistinctByCodeByBranch(loanRequestBean.getBranchId(), loanRequestBean.getLoanType()).getLoanTypeCode());

        loanRequestBean.setCompanyId(currentUser.getCompanyId());
        loanRequestBean.setBranchId(currentUser.getBranchId());
        loanRequestBean.setNoOfInstallments(eazyCoopUTIL.deriveLoanInstallmentFromLoanDurationInMonths(loanRequestBean.getRepayFrequency(), loanRequestBean.getDuration()));

        //model.put("loanRequests",  	prepareListofBean(loanRequestService.listLoanRequestsByCompanyBranch(currentUser.getCompanyId(),currentUser.getBranchId())));
        model.put("loanRequests", helperUTIL.getLoanRequestList(currentUser.getBranchId()));
        model.put("loanRequest", loanRequestBean);

        Map<String, String> configMap = new HashMap<String, String>();
        //get the following configuration by country and populate this map.

        String MAX_CON_LOAN = Definitions.DEFAULT_MAX_CON_LOAN;
        String MAX_GUA_NUM = Definitions.DEFAULT_MAX_GUA_NUM;
        String MAX_LOAN_SUM = Definitions.DEFAULT_MAX_LOAN_SUM;
        String MAX_LEN_STAY = Definitions.DEFAULT_MAX_LEN_STAY;

        java.text.DateFormat formatter = new SimpleDateFormat("ddMMyyyyhms");
        List<AppConfiguration> loanConfigs = appConfigurationService.listAppConfigurations("LOAN", loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());
        System.out.println("loanConfigs.size() B=:" + loanConfigs.size());
        if (loanConfigs != null && loanConfigs.size() > 0) {
            for (AppConfiguration item : loanConfigs) {
                if (item.getConfigName().equalsIgnoreCase("CONCURRENT LOAN")) {
                    MAX_CON_LOAN = item.getConfigMaxValue();
                } else if (item.getConfigName().equalsIgnoreCase("RUNNING LOAN SUM")) {
                    if ("STATIC".equals(item.getComputationType())) {
                        MAX_LOAN_SUM = item.getConfigMaxValue() == null ? MAX_LOAN_SUM : item.getConfigMaxValue();
                    } else if ("FORMULA".equals(item.getComputationType())) {
                        double resultVal = 0.00;

                        if (Definitions.FORMULA_TOTAL_SAVINGS.equals(item.getFormulaValue())) {
                            //get Member Total Savings
                            //double savingsTotal=helperUTIL.getMemberTotalSavings(loanRequestBean.getMemberNo(),loanRequestBean.getCompanyId(),loanRequestBean.getBranchId());
                            double savingsTotal = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(loanRequestBean.getMemberNo(), "0", 6, true), loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());

                            if (Definitions.OPERAND_MULTIPLY.equals(item.getOperand())) {
                                resultVal = savingsTotal * Double.parseDouble(item.getMultiplier());
                            } else if (Definitions.OPERAND_DIVIDE.equals(item.getOperand())) {
                                resultVal = savingsTotal / Double.parseDouble(item.getMultiplier());
                            }

                            MAX_LOAN_SUM = new java.text.DecimalFormat("###.00").format(resultVal);
                        } else {
                            MAX_LOAN_SUM = item.getConfigMaxValue() == null ? MAX_LOAN_SUM : item.getConfigMaxValue();
                        }
                    } else {
                        MAX_LOAN_SUM = item.getConfigMaxValue() == null ? MAX_LOAN_SUM : item.getConfigMaxValue();
                    }
                } else if (item.getConfigName().equalsIgnoreCase("LOAN GUARANTORS")) {
                    System.out.println("setting values for Num Guarantor B=:" + item.getConfigMaxValue());
                    MAX_GUA_NUM = item.getConfigMaxValue();
                } else if (item.getConfigName().equalsIgnoreCase("LENGTH OF STAY")) {
                    MAX_LEN_STAY = item.getConfigMaxValue();
                }
            }
        }

        configMap.put("MAX_CON_LOAN", MAX_CON_LOAN);
        configMap.put("MAX_GUA_NUM", MAX_GUA_NUM);
        configMap.put("MAX_LOAN_SUM", MAX_LOAN_SUM);
        configMap.put("MAX_LEN_STAY", MAX_LEN_STAY);

        model.put("configMap", configMap);
        //model.put("members",    	beanMapper.prepareListofMemberBean(memberViewService.listMembersExclusiveByCompanyId(loanRequestBean.getCompanyId(),loanRequestBean.getMemberNo())));
        //model.put("membersInc",  beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(loanRequestBean.getCompanyId(),loanRequestBean.getMemberNo())));

        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersExclusiveByBranchId(loanRequestBean.getBranchId(), loanRequestBean.getMemberNo())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByBranchId(loanRequestBean.getBranchId(), loanRequestBean.getMemberNo())));

        //we need to get member details and exceptions details
        System.out.println("Member No:=" + loanRequestBean.getMemberNo());
        //List<LoanRequest> loans=loanRequestService.listLoanRequestsByMember(loanRequestBean.getMemberNo(),true);
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(loanRequestBean.getMemberNo(), true, currentUser.getCompanyId(), currentUser.getBranchId());
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        double runningLoansBalance = 0.00;
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(loanRequestBean.getMemberNo(), "0", 6, true), loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());

        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
                runningLoansBalance += item.getBalanceTotal();
            }
        }

        //trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(loanRequestBean.getMemberNo());

        memberSummaryBean.setMemberId(loanRequestBean.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setRunningLoanBalance(runningLoansBalance);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
        memberSummaryBean.setTotalMemberContribution(totalContribution);
        memberSummaryBean.getRunningLoanBalance();
        System.out.println("totalContribution :: " + totalContribution);

        ArrayList<LoanRequestExceptionBean> exceptions = new ArrayList<LoanRequestExceptionBean>();

        if (runningLoansCount >= Integer.parseInt(MAX_CON_LOAN)) {
            LoanRequestExceptionBean iBean = new LoanRequestExceptionBean();

            iBean.setLoanCaseId(loanRequestBean.getLoanCaseId());
            iBean.setExceptionMessage("Maximum Number of concurrent loans has been reached");

            exceptions.add(iBean);
        }

        if (runningLoansSum >= Double.parseDouble(MAX_LOAN_SUM)) {
            LoanRequestExceptionBean iBean = new LoanRequestExceptionBean();
            iBean.setLoanCaseId(loanRequestBean.getLoanCaseId());
            iBean.setExceptionMessage("Total Sum Allowed for running loans has been reached");
            exceptions.add(iBean);
        }

        //Retrieving Guarantors Details
        int guarantorCount = Integer.parseInt(req.getParameter("guarantorCount"));
        System.out.println("guarantorCount:=" + guarantorCount);
        String guarantorMemberIds = "";

        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (int k = 1; k <= guarantorCount; k++) {
            String guarantorMemberId = req.getParameter("guarantor_" + k);
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            //get all active loans attached to this guarantor
            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            double runningLoanBalanceX = 0.00;
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.padValue(guarantorMemberId, "0", 6, true), loanRequestBean.getCompanyId(), loanRequestBean.getBranchId());

            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest item : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += item.getApprovedAmount();
                    runningLoanBalanceX += item.getBalanceTotal();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setRunningLoanBalance(runningLoanBalanceX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);
            System.out.println("Guarantor k :: " + totalContributionX);

            summaryBeans.add(summaryBean);
        }

        model.put("exceptions", exceptions);
        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);

        return new ModelAndView("addLoanRequest-2", model);
    }

    @RequestMapping(value = "/deleteLoanRequest", method = RequestMethod.GET)
    public ModelAndView deleteLoanRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        loanRequestService.deleteLoanRequest(prepareModel(loanRequestBean));
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRequest", null);
        model.put("loanRequests", prepareListofBean(loanRequestService.listLoanRequests()));
        return new ModelAndView("addLoanRequest", model);
    }

    @RequestMapping(value = "/editLoanRequest", method = RequestMethod.GET)
    public ModelAndView editLoanRequest(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("loanRequest", prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId())));
        model.put("loanRequests", prepareListofBean(loanRequestService.listLoanRequests()));
        return new ModelAndView("editLoanRequest", model);
    }
    
    
     @RequestMapping(value = "/editNewLoanRequest", method = RequestMethod.GET)
    public ModelAndView addNewLoanRequest0(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsByBranch(currentUser.getBranchId())));
        model.put("loanRequests", helperUTIL.getLoanRequestList(currentUser.getBranchId()));

        loanRequestBean.setCompanyId(currentUser.getCompanyId());
        loanRequestBean.setBranchId(currentUser.getBranchId());
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
        model.put("loanRequest", loanRequestBean);
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByBranchId(currentUser.getBranchId())));
        return new ModelAndView("/addNewLoanRequest0", model);
    }
    
     //NELSON AKPOMUJE
    @RequestMapping(value = "/saveReplacedGuarantor", method = RequestMethod.POST)
    public ModelAndView saveReplacedGuarantor(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result,
            HttpServletRequest req) {
        String redurlpath = null;
         
        LoanGuarantorChange lgc = null;
        ArrayList<LoanGuarantorChange> listlgc = new ArrayList<>();
       // String[] listOfNewGuarantors = loanRequestBean.getMemberName().split(",");
        String[] oldG = req.getParameterValues("oldGuarantor");
        for(String id:oldG){
         System.out.println("old guarantor from form: "+ id);
        }
         String[] newG = req.getParameterValues("memberName");
        for(String ids:newG){
         System.out.println("new guarantor from form: "+ ids);
        }
        int guarantorCount = Integer.parseInt(req.getParameter("guarantorCount"));
        //System.out.println("guarantorCount:=" + guarantorCount); 
        for(int i=0; i<guarantorCount; i++) {
           
            lgc = new LoanGuarantorChange();
            lgc.setGuarantorReplaced(oldG[i]);
            lgc.setGuarantorNo(newG[i]);
            System.out.println("i passed here");
            lgc.setLoanCaseId(loanRequestBean.getLoanCaseId());
            lgc.setCompanyId(loanRequestBean.getCompanyId());
            lgc.setBranchId(loanRequestBean.getBranchId());
            lgc.setMemberNo(loanRequestBean.getMemberNo());
            lgc.setRequestDate(loanRequestBean.getRequestDate());
            lgc.setAcceptanceDate(null);
            lgc.setCreationDate(new Date());
            lgc.setLastModificationDate(new Date());
            lgc.setLastModifiedBy(loanRequestBean.getLastModifiedBy());
            lgc.setCreatedBy("null");
            lgc.setApproved("N");
            System.out.println("Old guarantor Id " + lgc.getGuarantorReplaced() + " new guarantor - " + lgc.getGuarantorNo());

            listlgc.add(lgc);
            
            System.out.println("added to the listlgc-"+listlgc.size());
        }
        System.out.println("listlgc size - ");
       loanGuarantorServiceChange.addLoanGuarantorChanged(listlgc);
        redurlpath = "redirect:/doFeedback.htm?message= Changed to new Guarantor &redirectURI=editNewLoanRequest.htm";
        return new ModelAndView(redurlpath);
    }

    @RequestMapping(value = "/viewNewLoanRequestDetails", method = RequestMethod.GET)
    public ModelAndView viewNewLoanRequestDetails(@ModelAttribute("loanRequest") LoanRequestBean loanRequestBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequest(loanRequestBean.getId()));
        List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));

        model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(), obj.getMemberNo())));
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        model.put("guarantors", guarantors);

        //we need to get member details and exceptions details
        List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());
        //added by nelson akpos
        double memberBalance = helperUTIL.getMemberLoanBalance(obj.getMemberNo(), obj.getCompanyId(), obj.getBranchId());

        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }

        //Trying to derive exceptions list based on member profile.
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        MemberView requestIngMember = memberViewService.getMember(obj.getMemberNo());

        memberSummaryBean.setMemberId(obj.getMemberNo());
        memberSummaryBean.setRunningLoanCount(runningLoansCount);
        memberSummaryBean.setRunningLoanSum(runningLoansSum);
        memberSummaryBean.setMemberName(requestIngMember.getFirstname() + " " + requestIngMember.getSurname());
        memberSummaryBean.setCompMemberId(requestIngMember.getCompmemberId());
         //added by nelson akpos
        memberSummaryBean.setRunningLoanBalance(memberBalance);
        memberSummaryBean.setTotalMemberContribution(totalContribution);
        System.out.println("totalContribution :: " + totalContribution);

        //Retrieving Guarantors Details
        String guarantorMemberIds = "";
        ArrayList<MemberSummaryBean> summaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorBean item : guarantors) {
            String guarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + guarantorMemberId);

            if (guarantorMemberIds.length() == 0) {
                guarantorMemberIds = guarantorMemberId;
            } else {
                guarantorMemberIds += "," + guarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(guarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(guarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(guarantorMemberId), obj.getCompanyId(), obj.getBranchId());


            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }

            summaryBean.setMemberId(guarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
            summaryBean.setTotalMemberContribution(totalContributionX);
            System.out.println("memberName contains - " + summaryBean.getMemberName());
            System.out.println("memberName Id - " + summaryBean.getMemberId());
            summaryBeans.add(summaryBean);
        }

        model.put("guarantorMemberIds", guarantorMemberIds);
        model.put("memberSummaryBean", memberSummaryBean);
        model.put("guarantorsSummaryBeans", summaryBeans);
        model.put("schedules", beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId())));
        model.put("oldguarantors", null);
        return new ModelAndView("viewNewLoanRequestDetails", model);
    }
    
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private LoanRequest prepareModel(LoanRequestBean loanRequestBean) {
        LoanRequest loanRequest = new LoanRequest();

        loanRequest.setCompanyId(loanRequestBean.getCompanyId());
        loanRequest.setBranchId(loanRequestBean.getBranchId());

        loanRequest.setLoanCaseId(loanRequestBean.getLoanCaseId());
        loanRequest.setAppliedRate(loanRequestBean.getAppliedRate());
        loanRequest.setApprovalComment(loanRequestBean.getApprovalComment());
        loanRequest.setApprovalDate(loanRequestBean.getApprovalDate());
        loanRequest.setApprovedAmount(loanRequestBean.getApprovedAmount());
        loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
        loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());
        loanRequest.setApprovedBy(loanRequestBean.getApprovedBy());
        loanRequest.setBalanceInterest(loanRequestBean.getBalanceInterest());
        loanRequest.setBalancePrincipal(loanRequestBean.getBalancePrincipal());
        loanRequest.setBalanceTotal(loanRequestBean.getBalanceTotal());
        loanRequest.setDuration(loanRequestBean.getDuration());
        loanRequest.setNoOfInstallments(loanRequestBean.getNoOfInstallments());
        loanRequest.setLastRepaymentDate(loanRequestBean.getLastRepaymentDate());
        loanRequest.setDisburseDate(loanRequestBean.getDisburseDate());
        loanRequest.setDisburseBy(loanRequestBean.getDisburseBy());
        loanRequest.setProposedCommencementDate(loanRequestBean.getProposedCommencementDate());
        loanRequest.setActualCommencementDate(loanRequestBean.getActualCommencementDate());
        loanRequest.setLoanAccountNumber(loanRequestBean.getLoanAccountNo());
        loanRequest.setLoanIntTotal(loanRequestBean.getLoanIntTotal());
        loanRequest.setLoanStatus(loanRequestBean.getLoanStatus());
        loanRequest.setLoanType(loanRequestBean.getLoanType());
        loanRequest.setInterestType(loanRequestBean.getInterestType());
        loanRequest.setMemberNo(loanRequestBean.getMemberNo());
        loanRequest.setProductRate(loanRequestBean.getProductRate());
        loanRequest.setRepayAmount(loanRequestBean.getRepayAmount());
        loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
        loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
        loanRequest.setRequestBy(loanRequestBean.getRequestBy());
        loanRequest.setRequestDate(loanRequestBean.getRequestDate());
        loanRequest.setLastModifiedBy(loanRequestBean.getLastModifiedBy());
        loanRequest.setLastModificationDate(loanRequestBean.getLastModificationDate());
        loanRequest.setId(loanRequestBean.getId());

        loanRequest.setLoantitle(loanRequestBean.getLoantitle());
        
        return loanRequest;
    }

    private List<LoanRequestBean> prepareListofBean(List<LoanRequest> loanRequests) {
        List<LoanRequestBean> beans = null;

        if (loanRequests != null && !loanRequests.isEmpty()) {
            beans = new ArrayList<LoanRequestBean>();
            LoanRequestBean loanRequest = null;

            for (LoanRequest loanRequestBean : loanRequests) {
                loanRequest = new LoanRequestBean();

                loanRequest.setId(loanRequestBean.getId());

                loanRequest.setCompanyId(loanRequestBean.getCompanyId());
                loanRequest.setBranchId(loanRequestBean.getBranchId());

                loanRequest.setLoanCaseId(loanRequestBean.getLoanCaseId());
                loanRequest.setAppliedRate(loanRequestBean.getAppliedRate());
                loanRequest.setApprovalComment(loanRequestBean.getApprovalComment());
                loanRequest.setApprovalDate(loanRequestBean.getApprovalDate());
                loanRequest.setApprovedAmount(loanRequestBean.getApprovedAmount());

                loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
                loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());

                loanRequest.setDisburseDate(loanRequestBean.getDisburseDate());
                loanRequest.setDisburseBy(loanRequestBean.getDisburseBy());

                loanRequest.setApprovedBy(loanRequestBean.getApprovedBy());
                loanRequest.setBalanceInterest(loanRequestBean.getBalanceInterest());
                loanRequest.setBalancePrincipal(loanRequestBean.getBalancePrincipal());
                loanRequest.setBalanceTotal(loanRequestBean.getBalanceTotal());
                loanRequest.setDuration(loanRequestBean.getDuration());
                loanRequest.setNoOfInstallments(loanRequestBean.getNoOfInstallments());
                loanRequest.setLastRepaymentDate(loanRequestBean.getLastRepaymentDate());
                loanRequest.setLoanAccountNo(loanRequestBean.getLoanAccountNumber());
                loanRequest.setLoanIntTotal(loanRequestBean.getLoanIntTotal());
                loanRequest.setLoanStatus(loanRequestBean.getLoanStatus());
                loanRequest.setLoanType(loanRequestBean.getLoanType());
                loanRequest.setInterestType(loanRequestBean.getInterestType());
                loanRequest.setMemberNo(loanRequestBean.getMemberNo());
                loanRequest.setProductRate(loanRequestBean.getProductRate());
                loanRequest.setRepayAmount(loanRequestBean.getRepayAmount());
                loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
                loanRequest.setRepayFrequency(loanRequestBean.getRepayFrequency());
                loanRequest.setRequestBy(loanRequestBean.getRequestBy());
                loanRequest.setRequestDate(loanRequestBean.getRequestDate());

                loanRequest.setProposedCommencementDate(loanRequestBean.getProposedCommencementDate());
                loanRequest.setActualCommencementDate(loanRequestBean.getActualCommencementDate());

                loanRequest.setRequestedAmount(loanRequestBean.getRequestedAmount());
                loanRequest.setRequestStatus(loanRequestBean.getRequestStatus());
                loanRequest.setTotPenaltyDue(loanRequestBean.getTotPenaltyDue());
                loanRequest.setTotPenaltyPaid(loanRequestBean.getTotPenaltyPaid());

                loanRequest.setLastModifiedBy(loanRequestBean.getLastModifiedBy());
                loanRequest.setLastModificationDate(loanRequestBean.getLastModificationDate());

                beans.add(loanRequest);
            }
        }

        return beans;
    }

    private LoanRequestBean prepareLoanRequestBean(LoanRequest loanRequest) {
        LoanRequestBean bean = new LoanRequestBean();

        bean.setId(loanRequest.getId());
        bean.setCompanyId(loanRequest.getCompanyId());
        bean.setBranchId(loanRequest.getBranchId());

        //bean.setLoanCaseId(loanRequest.getLoanCaseId());
        bean.setLoanCaseId(loanRequest.getLoanCaseId());
        bean.setAppliedRate(loanRequest.getAppliedRate());
        bean.setApprovalComment(loanRequest.getApprovalComment());
        bean.setApprovalDate(loanRequest.getApprovalDate());
        bean.setApprovedAmount(loanRequest.getApprovedAmount());

        bean.setDisburseDate(loanRequest.getDisburseDate());
        bean.setDisburseBy(loanRequest.getDisburseBy());

        bean.setRequestedAmount(loanRequest.getRequestedAmount());
        bean.setRequestStatus(loanRequest.getRequestStatus());

        bean.setApprovedBy(loanRequest.getApprovedBy());
        bean.setBalanceInterest(loanRequest.getBalanceInterest());
        bean.setBalancePrincipal(loanRequest.getBalancePrincipal());

        bean.setProposedCommencementDate(loanRequest.getProposedCommencementDate());
        bean.setActualCommencementDate(loanRequest.getActualCommencementDate());

        bean.setBalanceTotal(loanRequest.getBalanceTotal());
        bean.setDuration(loanRequest.getDuration());
        bean.setNoOfInstallments(loanRequest.getNoOfInstallments());
        bean.setLastRepaymentDate(loanRequest.getLastRepaymentDate());
        bean.setLoanAccountNo(loanRequest.getLoanAccountNumber());
        bean.setLoanIntTotal(loanRequest.getLoanIntTotal());
        bean.setLoanStatus(loanRequest.getLoanStatus());
        bean.setLoanType(loanRequest.getLoanType());
        bean.setInterestType(loanRequest.getInterestType());
        bean.setMemberNo(loanRequest.getMemberNo());
        bean.setProductRate(loanRequest.getProductRate());
        bean.setRepayAmount(loanRequest.getRepayAmount());
        bean.setRepayFrequency(loanRequest.getRepayFrequency());
        bean.setRepayFrequency(loanRequest.getRepayFrequency());
        bean.setRequestBy(loanRequest.getRequestBy());
        bean.setRequestDate(loanRequest.getRequestDate());
        bean.setLastModifiedBy(loanRequest.getLastModifiedBy());
        bean.setLastModificationDate(loanRequest.getLastModificationDate());

        return bean;
    }
}
/**
 *
 *
 * select * from member where member_no = '000004' and branch_id = 48 and
 * company_id = 52 select a.branch_code,b.id from branch a inner join company b
 * on a.company_id = b.id where a.branch_code = '001' and a.company_id = 52
 * select a.code from products a where a.code = 'LON' and a.company_id = 52 and
 * segment_code != '' and segment_code is not null
 *
 * INSERT INTO `easycoopfin`.`productaccount` (`id`, `product_id`,
 * `gl_account_number`, `branch_id`, `company_id`, `product_account_type_code`)
 * VALUES (NULL, '70', '0003222c', '48', '52', 'INT'); INSERT INTO
 * `easycoopfin`.`productaccount` (`id`, `product_id`, `gl_account_number`,
 * `branch_id`, `company_id`, `product_account_type_code`) VALUES (NULL, '70',
 * '0003223r', '48', '52', 'CTR'); INSERT INTO `easycoopfin`.`productaccount`
 * (`id`, `product_id`, `gl_account_number`, `branch_id`, `company_id`,
 * `product_account_type_code`) VALUES (NULL, '70', '0003223b', '48', '52',
 * 'SRC');
 *
 * INSERT INTO `easycoopfin`.`accounts` (`AcID`, `AccountNo`, `Name`,
 * `Shortname`, `AcType`, `AcGroup`, `AcStruct`, `Aseg1`, `Aseg2`, `Aseg3`,
 * `Aseg4`, `Aseg5`, `Aseg6`, `Aseg7`, `Aseg8`, `Aseg9`, `Aseg10`, `Asegc`,
 * `Currency`, `SubAccount`, `ControlAccount`, `BalanceType`, `CCyBalance`,
 * `CCyClearedBalance`, `Balance`, `ClearedBalance`, `Active`, `Closed`,
 * `Blocked`, `DateOpened`, `Branch`, `Companyid`, `Accounttype`,
 * `Controlaccountno`) VALUES (NULL, '0003223r', '0003223r account', NULL, '0',
 * '1', 'MEMBERACCT', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
 * NULL, NULL, 'NGN', '0', '0', 'D', '0', '0', '0', '0', '1', '0', '0',
 * '2015-01-07 00:00:00', '48', '52', 'G', NULL); INSERT INTO
 * `easycoopfin`.`accounts` (`AcID`, `AccountNo`, `Name`, `Shortname`, `AcType`,
 * `AcGroup`, `AcStruct`, `Aseg1`, `Aseg2`, `Aseg3`, `Aseg4`, `Aseg5`, `Aseg6`,
 * `Aseg7`, `Aseg8`, `Aseg9`, `Aseg10`, `Asegc`, `Currency`, `SubAccount`,
 * `ControlAccount`, `BalanceType`, `CCyBalance`, `CCyClearedBalance`,
 * `Balance`, `ClearedBalance`, `Active`, `Closed`, `Blocked`, `DateOpened`,
 * `Branch`, `Companyid`, `Accounttype`, `Controlaccountno`) VALUES (NULL,
 * '0003223b', '0003223b account', NULL, '0', '1', 'MEMBERACCT', '01', NULL,
 * NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'NGN', '0', '0', 'D',
 * '0', '0', '0', '0', '1', '0', '0', '2015-01-07 00:00:00', '48', '52', 'G',
 * NULL); INSERT INTO `easycoopfin`.`accounts` (`AcID`, `AccountNo`, `Name`,
 * `Shortname`, `AcType`, `AcGroup`, `AcStruct`, `Aseg1`, `Aseg2`, `Aseg3`,
 * `Aseg4`, `Aseg5`, `Aseg6`, `Aseg7`, `Aseg8`, `Aseg9`, `Aseg10`, `Asegc`,
 * `Currency`, `SubAccount`, `ControlAccount`, `BalanceType`, `CCyBalance`,
 * `CCyClearedBalance`, `Balance`, `ClearedBalance`, `Active`, `Closed`,
 * `Blocked`, `DateOpened`, `Branch`, `Companyid`, `Accounttype`,
 * `Controlaccountno`) VALUES (NULL, '0003222c', '0003222c account', NULL, '0',
 * '1', 'MEMBERACCT', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
 * NULL, NULL, 'NGN', '0', '0', 'D', '0', '0', '0', '0', '1', '0', '0',
 * '2015-01-07 00:00:00', '48', '52', 'G', NULL);
 *
 * INSERT INTO `member` (`member_id`, `member_comp_id`, `member_no`,
 * `first_name`, `middle_name`, `surname`, `dob`, `religion_id`,
 * `member_type_id`, `created_date`, `created_by`, `del_flg`, `del_date`,
 * `gender`, `branch_id`, `identification_id`, `identification_code`,
 * `status_id`, `company_id`, `phone_no_1`, `email_add_1`) VALUES (null,
 * '12348', '000004', 'Christopher', 'Kolade', 'Faseun', '2013-12-10', 1, 1,
 * '2015-06-27', 'ADMIN', 'N', NULL, 'M', 50, 1, '1234567890', 1, 54,
 * '08023456789', 'christofash@yahoo.com'), (null, '12349', '000005', 'Adebayo',
 * 'Bamidele', 'Ojo', '2013-12-10', 1, 1, '2015-06-27', 'ADMIN', 'N', NULL, 'M',
 * 50, 1, '1234567890', 1, 54, NULL, NULL), (null, '12350', '000006', 'Ado',
 * 'John', 'Sule', '2013-12-10', 1, 1, '2015-06-27', 'ADMIN', 'N', NULL, 'M',
 * 50, 1, '1234567890', 1, 54, NULL, NULL)
 *
 *
 * *
 */
