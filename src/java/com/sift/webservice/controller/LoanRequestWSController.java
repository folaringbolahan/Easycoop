/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.controller;

import com.sift.admin.bean.MemberSummaryBean;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Company;
import com.sift.admin.model.LoanRepayFreq;
import com.sift.admin.model.MemberView;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.LoanRepayFreqService;
import com.sift.admin.service.MemberViewService;
import com.sift.gl.GetSetting;
import com.sift.gl.model.Activitylog;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.webservice.model.LoanGuarantorWS;
import com.sift.webservice.model.LoanRequestResponseWS;
import com.sift.webservice.model.LoanRequestWS;
import com.sift.webservice.model.MemberProfileUpdateWS;
import com.sift.webservice.model.UserWS;
import com.sift.webservice.service.LoanGuarantorWSService;
import com.sift.webservice.service.LoanRequestServiceWS;
import com.sift.webservice.service.MemberProfileUpdateWSService;
import com.sift.webservice.service.UserServiceWS;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Olakunle Awotunbo
 */
@Controller
@RequestMapping("/webservice/loanrequest")
public class LoanRequestWSController {
    
    private static final Logger logger = Logger.getLogger(LoanRequestWSController.class);
   @Autowired
    public UserServiceWS userServiceWS;
    @Autowired
    public LoanRequestServiceWS loanRequestServiceWS;
    @Autowired 
    public LoanGuarantorWSService loanGuarantorService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ProductService productService;    
    @Autowired
    private MemberViewService memberViewService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private MemberProfileUpdateWSService  memberProfileUpdateWSService;
    
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
   
    Date todayDate = new Date();
    Float rate;
    Activitylog activity = new Activitylog();

    @RequestMapping(value = "/loanrequest1", method = RequestMethod.GET)
    public ModelAndView handleRegistration(ModelMap map) {
        map.addAttribute("loanrequestws", new LoanRequestWS());        
        return new ModelAndView("loanrequestws", map);
    }
   
    //http://localhost:8080/EasycoopfinTEST3/webservice/loanrequest/addloan
    @RequestMapping(value = "/addloan", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveLoanRequestWS(@RequestBody LoanRequestWS loanRequestWS,
            HttpServletRequest req) {  
        
        logger.info("**** Inside saveloan method ***");
        
       DateFormat formatter = new SimpleDateFormat("ddMMyyyyhms");
      
        String loanCaseId = loanRequestWS.getCompanyId()
                + loanRequestWS.getBranchId() + formatter.format(new java.util.Date())
                + productService.getProductByTypeCode(loanRequestWS.getLoanType()).getCode();
  	 
        LoanRequestWS lr  = new LoanRequestWS();
        String guarantorOneId = "0";
        String guarantorTwoId = "0";
        String guarantorThreeId = "0";
        int compId = Integer.parseInt(loanRequestWS.getCompanyId());
        int branchId = Integer.parseInt(loanRequestWS.getBranchId());
        //System.out.println("Primary Guarantor: "+ loanRequestWS.getApprovalComment());
        //System.out.println("Secondary Guarantor: "+ loanRequestWS.getDisburseBy());
        //System.out.println("ChronoForm Loan ID : "+ loanRequestWS.getEasyCoopLoanId());        
        //System.out.println("EasyCoopLoan ID: "+ loanRequestServiceWS.getEasyCoopLoanId("33260311201553045010"));
        //Use member number to get memberId
        String memberId = memberViewService.getMember(loanRequestWS.getBranchId(), loanRequestWS.getMemberNo()).getMemberId();
        guarantorOneId = memberViewService.getMember(loanRequestWS.getBranchId(), 
                loanRequestWS.getApprovalComment()).getMemberId(); //guarantor 1 memberId
        guarantorTwoId = memberViewService.getMember(loanRequestWS.getBranchId(), 
                loanRequestWS.getDisburseBy()).getMemberId(); //guarantor 2 memberId
        Company coy = companyService.getCompany(Integer.parseInt(loanRequestWS.getCompanyId()));
        //get Product rate
        rate = productService.getProductByTypeCode(loanRequestWS.getLoanType(),
                loanRequestWS.getCompanyId(),loanRequestWS.getBranchId()).getInterestRate();
	Date localDate = eazyCoopUTIL.getTimeByZone(countryService.
                getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone());
      
        
        lr.setCompanyId(loanRequestWS.getCompanyId());
        lr.setBranchId(loanRequestWS.getBranchId());
        lr.setMemberNo(memberId); //get memberId
        lr.setLoanType(loanRequestWS.getLoanType());
        lr.setLoanStatus("P");
        lr.setAppliedRate(rate);
        lr.setProductRate(rate);
        lr.setRequestStatus("E");
        lr.setRequestedAmount(loanRequestWS.getRequestedAmount());
        lr.setRequestBy(memberId);
        lr.setNoOfInstallments(eazyCoopUTIL.
                deriveLoanInstallmentFromLoanDurationInMonths(
                        loanRequestWS.getRepayFrequency(),
                        loanRequestWS.getDuration()));
        lr.setDuration(loanRequestWS.getDuration());
        lr.setInterestType(productService.
                getProductsDistinctByCodeByBranch(loanRequestWS.getBranchId(),
                        loanRequestWS.getLoanType()).getLoanTypeCode()); //get interest type from lon product
        lr.setLastModificationDate(localDate);
        lr.setLastModifiedBy(memberId);
        lr.setRequestDate(localDate); // get date
        lr.setApprovedAmount(loanRequestWS.getRequestedAmount()); 
        lr.setProposedCommencementDate(loanRequestWS.getProposedCommencementDate());
        lr.setRepayFrequency(loanRequestWS.getRepayFrequency() /*repayFreq.getCode()*/);
        lr.setLoanCaseId(loanCaseId);
        lr.setEasyCoopLoanId(loanRequestWS.getEasyCoopLoanId());
        
          //First Guarantor
        LoanGuarantorWS lg1 = new LoanGuarantorWS();
        lg1.setAcceptanceStatus("N");
        lg1.setBranchId(loanRequestWS.getBranchId());
        lg1.setCompanyId(loanRequestWS.getCompanyId());
        lg1.setGuarantorNo(guarantorOneId); //getApprovalComment() is used for GuarantorNo1
        lg1.setLoanCaseId(loanCaseId);
        lg1.setMemberNo(memberId); //loan initiator
        lg1.setRequestDate(localDate);
        
        //Second Guarantor
        LoanGuarantorWS lg2 = new LoanGuarantorWS();
        lg2.setAcceptanceStatus("N");
        lg2.setBranchId(loanRequestWS.getBranchId());
        lg2.setCompanyId(loanRequestWS.getCompanyId());
        lg2.setGuarantorNo(guarantorTwoId); //getDisburseBy() used for of secondary guarantor number
        lg2.setLoanCaseId(loanCaseId);
        lg2.setMemberNo(memberId); //loan initiator
        lg2.setRequestDate(localDate);
       
        //save guarantors
        loanGuarantorService.saveGuarantor(lg1);
        loanGuarantorService.saveGuarantor(lg2);
        
        if(loanRequestWS.getDurationDays() != null && !loanRequestWS.getDurationDays().isEmpty() ){
            guarantorThreeId = memberViewService.getMember(loanRequestWS.getBranchId(), 
                loanRequestWS.getDurationDays()).getMemberId(); //guarantor 3 memberId
            
             //Second Guarantor
            LoanGuarantorWS lg3 = new LoanGuarantorWS();
            lg3.setAcceptanceStatus("N");
            lg3.setBranchId(loanRequestWS.getBranchId());
            lg3.setCompanyId(loanRequestWS.getCompanyId());
            lg3.setGuarantorNo(guarantorThreeId); //getDurationDays() used for of secondary guarantor number
            lg3.setLoanCaseId(loanCaseId);
            lg3.setMemberNo(memberId); //loan initiator
            lg3.setRequestDate(localDate);
            
            // save guarantor 3
             loanGuarantorService.saveGuarantor(lg3);
        }
        
        boolean good = loanRequestServiceWS.saveLoan(lr); 
         //Audit action
	     activity.setEvent(Definitions.EVENT_LOAN_INITIATION);
	     activity.setAction("Loan Request Initiation from EasyCoop Loan ID: " + loanRequestWS.getLoanCaseId());
	     activity.setActionDate(localDate);
	     activity.setActionItem("LR EasyCoop: LoanID: " + loanRequestWS.getLoanCaseId());
	     activity.setActionResult("Save Successfully Loan ID: " + loanRequestWS.getLoanCaseId());
	     activity.setDescription("Loan Request Initiation from EasyCoop Loan ID: " + loanRequestWS.getLoanCaseId());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(memberId);	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequestWS.getCompanyId()));
	     activity.setToDate("");
             activity.setBranchid(branchId);
             activity.setCompanyid(compId);
        if(good){
            
            try{
                eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
        }
        
        //log exception
        //logger.error("This is error message", new Exception("Unable to save loan"));
        logger.info("**** saveloan successful ***");
               
        return "OK";
    }
    
    //http://localhost:8080/EasycoopfinTEST3/webservice/loanrequest/listPendingLoanRequest
    @RequestMapping(value = "/listPendingLoanRequest", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public   List<LoanRequestWS> listPendingLoanRequestByCompanyId(@RequestBody LoanRequestWS loanRequestWS) {
        
        //
        logger.info("**** List pending loan method ***");
         //log debug message
        if(logger.isDebugEnabled()){
            logger.debug("Debug ... list PendingLoan here");
        }
       
        
        List<LoanRequestWS> listPendingLoanRequest = loanRequestServiceWS.listPendingLoanRequestByCompanyId(
                loanRequestWS.getCompanyId(), "E");
        System.out.println("I can rest reach here ... List loan by company");
        //log exception
        //logger.error("This is error message", new Exception("Unable to list pending loans"));
        
        return listPendingLoanRequest;
    }
    
    @RequestMapping(value = "/listallloan", method = RequestMethod.GET)
    public @ResponseBody
    List<LoanRequestWS> listData() {
        //List<LoanRequestWS> listLoanRequestWS = loanRequestServiceWS.getAllLoanRequest();
        logger.info("**** List loan method ***");
        String companyId = "4";
        List<LoanRequestWS> listLoanRequestWS = loanRequestServiceWS.listPendingLoanRequestByCompanyId(companyId, "E");

        return listLoanRequestWS;
    }

    @RequestMapping(value = "/listdata2", method = RequestMethod.GET, headers = "Accept=*/*")
    @ResponseBody
    public String listData2(@RequestParam("memberNo") String memberNo) {
        return "I AM WORKING " + memberNo;
    }
    
     //localhost:8080/Easycoopfin/webservice/loanrequest/runningloan
    /**
     *
     * @param memberNo
     * @return
     */
    @RequestMapping(value = "/runningloan", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,headers = "Accept=*/*" )
    @ResponseBody
    public LoanRequestResponseWS  runningloan(@RequestParam("memberNo") String memberNo, @RequestParam("bid") String brid) {
        LoanRequestResponseWS response = new LoanRequestResponseWS();
       GetSetting limitlnrqset = new GetSetting();
      String maxLoanbyCoop = limitlnrqset.GetSettingvalue("LIMITLNRQATMAX", brid);
       String memberid = memberViewService.getMember(brid,memberNo).getMemberId();
        List<LoanRequestWS> loans = loanRequestServiceWS.getLoanRequestByMemberNo(memberid);
        System.out.println("the list size is " + loans.size());
        int runningLoansCount = 0;
        if (loans != null && loans.size() > 0) {
            for (LoanRequestWS item : loans) {
                System.out.println("id - " + item.getId());
                runningLoansCount += 1;
            }
        }
        MemberSummaryBean memberSummaryBean = new MemberSummaryBean();
        memberSummaryBean.setRunningLoanCount(runningLoansCount);

     try {
       if (runningLoansCount < Integer.parseInt(maxLoanbyCoop)) {
            response.setMemberId(memberNo);
            response.setResponseCode(200);
            response.setResponseMessage("You are eligible for loan request");
            response.setLoanEligibility(true);
            System.out.println("You are eligible for loan request");

        } else {
            response.setMemberId(memberNo);
            response.setResponseCode(201);
            response.setResponseMessage("You are not eligible for loan request. " + memberSummaryBean.getRunningLoanCount()  + " Loans running");
            response.setLoanEligibility(false);
            System.out.println("you are not eligible for loan request");
        }
     }
     catch (NumberFormatException nfex) {
            response.setMemberId(memberNo);
            response.setResponseCode(200);
            response.setResponseMessage("Parameters not set. " + memberSummaryBean.getRunningLoanCount()  + " Loans running");
            response.setLoanEligibility(true);
     }
      
        //System.out.println("running loan count :: " + memberSummaryBean.getRunningLoanCount() + " size " + loans.size());
        //check for loan eligibility
       return response;
    }
    
     @RequestMapping(value = "/coopadminmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=*/*")
    @ResponseBody
    public List<UserWS> coopadminmail(@RequestParam("companyid") String companyid,@RequestParam("branchid") String branchid) {
        System.out.println("i am inside the webservice for users");
        
          List<UserWS> users_list_out = new ArrayList<>();
          String accesslevel="BAdmin";
      
       List<UserWS> users_list_dao= userServiceWS.getAllCoopMail(companyid, branchid, accesslevel);
       System.out.println("the list size is " + users_list_dao.size());
       for(UserWS uws : users_list_dao){
       UserWS uws_model = new UserWS();
     
       uws_model.setUserId(uws.getUserId());
       users_list_out.add(uws_model);
       }
         
        return users_list_out; 
    }
    //http://localhost:8080/EasycoopfinTEST3/webservice/loanrequest/profileupdate
    @RequestMapping(value = "/profileupdate", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveMemberProfileUpdate(@RequestBody MemberProfileUpdateWS memberProfileUpdateWS,
            HttpServletRequest req) {  
        
        logger.info("**** saveMemberProfileUpdate ***"); 
      MemberProfileUpdateWS mprofile  = new MemberProfileUpdateWS();
      mprofile.setMemberno(memberProfileUpdateWS.getMemberno());
      mprofile.setBranchid(memberProfileUpdateWS.getBranchid());
      mprofile.setCompanyid(memberProfileUpdateWS.getCompanyid());
      mprofile.setChangetype(memberProfileUpdateWS.getChangetype());
      mprofile.setFieldvalue(memberProfileUpdateWS.getFieldvalue());
      mprofile.setCreatedby(memberProfileUpdateWS.getCreatedby()); 
      mprofile.setCreated_date(todayDate);
      mprofile.setApproved("N");
        //save update
      memberProfileUpdateWSService.saveMemberUpdate(mprofile);
    logger.info("**** update member profile succefully ***");
               
        return "OK";
    }
   
  
}
