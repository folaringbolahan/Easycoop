/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.loan.controller;


import com.sift.admin.bean.MemberSummaryBean;
import com.sift.admin.model.MemberView;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.InterestTypeService;
import com.sift.admin.service.LoanRepayFreqService;
import com.sift.loan.model.LoanGuarantorChange;
import com.sift.loan.bean.LoanGuarantorChangeBean;
import com.sift.loan.service.LoanGuarantorServiceChange;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.service.UserService;
import com.sift.loan.bean.LoanGuarantorBean;
import com.sift.loan.bean.LoanRequestBean;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.service.LoanGuarantorService;
import com.sift.loan.service.LoanRequestExceptionService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;

import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Akpos
 */
@Controller
public class LoanGuarantorChangeController {
    //added loan request Service and loanGuarantorService
    @Autowired
    private LoanRequestService loanRequestService;
    @Autowired
    private LoanGuarantorService loanGuarantorService;
    //added today 27/09/2017
     @Autowired
    private CompanyService companyService;
     @Autowired
    private BranchService branchService;
   @Autowired
   private ProductService productService;
    @Autowired
    private LoanRepayFreqService loanRepayFreqService;
     @Autowired
    private InterestTypeService interestTypeService;
    @Autowired
    private LoanRequestExceptionService loanRequestExceptionService;
    @Autowired
    private LoanGuarantorServiceChange loanGuarantorServiceChange;
    @Autowired
    private MemberViewService memberViewService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private UserService userService;
    EazyCoopUtility eazyCoopUTIL = new EazyCoopUtility();
    BeanMapperUtility beanMapper = new BeanMapperUtility();

    @RequestMapping(value = "/listNewGuarantors", method = RequestMethod.GET)
    public ModelAndView pendingLoanGuarantorChange(@ModelAttribute("loanGuarantorChange") LoanGuarantorChangeBean loanGuarantorChangeBean, BindingResult result, HttpServletRequest req) {
        String logonUser = eazyCoopUTIL.getLoggedonUser(req);
        User currentUser = userService.getUserByEmail(logonUser);
        Map<String, Object> model = new HashMap<String, Object>();
        //int loanCase= req.getParameter("loanCaseId")
        //model.put("loanGuarant", prepareListofBean(loanGuarantorServiceChange.listLoanGuarantorsChanged()));
        //System.out.println("The branch ID is " + currentUser.getBranchId());
        model.put("loanGuarant", prepareListofBean(loanGuarantorServiceChange.listLoanGuarantorsChangedByBranchId(currentUser.getBranchId())));

        // List<LoanGuarantorChangeBean> requests = helperUTIL.listDistinctGuarantorChange(currentUser.getBranchId());
        //model.put("loanGuarant",requests );


        return new ModelAndView("verifyGuarantorChange", model);
    }

    @RequestMapping(value = "/editNewGuarantor", method = RequestMethod.GET)
    public ModelAndView editNewGuarantor(@ModelAttribute("loanGuarantorChange") LoanGuarantorChangeBean loanGuarantorChangeBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String loanCaseId = req.getParameter("id");
           
        
       
        System.out.println("loan case Id inside here");
        //loading loanguarantorchange obj here
         
      
       
        //ends here
        //putting new code here
           LoanRequestBean obj = prepareLoanRequestBean(loanRequestService.getLoanRequestByCaseId(loanCaseId));
           List<LoanGuarantorBean> guarantors = beanMapper.prepareListofLoanGuarantorBean(loanGuarantorService.listLoanGuarantorsByCaseId(obj.getLoanCaseId()));
          /*list of loanGuarantorChangeBean*/
           List<LoanGuarantorChangeBean> newGuarantors = prepareListofBeans(loanGuarantorServiceChange.listAllLoanGuarantorsChangedByCaseId(obj.getLoanCaseId()));
           /* ends here*/
           List<LoanRequest> loans = loanRequestService.listLoanRequestsByMember(obj.getMemberNo(), true);
        int runningLoansCount = 0;
        double runningLoansSum = 0.00;
        double totalContribution = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(obj.getMemberNo()), obj.getCompanyId(), obj.getBranchId());
        if (loans != null && loans.size() > 0) {
            for (LoanRequest item : loans) {
                runningLoansCount += 1;
                runningLoansSum += item.getApprovedAmount();
            }
        }
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

        //end of new code

        //model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct());
        model.put("loanGuarantorChange", prepareListofBeans(loanGuarantorServiceChange.listLoanGuarantorsChangedByCaseId(loanCaseId)));
        model.put("guarantorsSummaryBeans", summaryBeans);
        model.put("memberSummaryBean", memberSummaryBean);
        //trying to get loan details for this member here
         model.put("loanRequest", obj);
        model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
        model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
        model.put("products", beanMapper.prepareListofProductBean(productService.listProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType())));
        model.put("loanRepayFreqs", beanMapper.prepareListofLoanRepayFreqBean(loanRepayFreqService.listLoanRepayFreqsDistinct("code", String.valueOf(obj.getRepayFrequency()))));
        model.put("members", beanMapper.prepareListofMemberBean(memberViewService.listMembersByCompanyId(obj.getCompanyId())));
        model.put("membersInc", beanMapper.prepareListofMemberBean(memberViewService.listMembersIncByCompanyId(obj.getCompanyId(), obj.getMemberNo())));
        model.put("interestTypes", beanMapper.prepareListofInterestTypeBean(interestTypeService.listInterestTypes()));
        model.put("exceptions", beanMapper.prepareListofLoanRequestExceptionBean(loanRequestExceptionService.listLoanRequestExceptionsByCaseId(obj.getLoanCaseId())));
        //
        //trying to retreive loan details of the new guarantors
        
        String newGuarantorMemberIds = "";
        ArrayList<MemberSummaryBean> newSummaryBeans = new ArrayList<MemberSummaryBean>();

        for (LoanGuarantorChangeBean item :  newGuarantors ) {
            String newGuarantorMemberId = item.getGuarantorNo();
            System.out.println("guarantorMemberId:=" + newGuarantorMemberId);

            if (newGuarantorMemberIds.length() == 0) {
                newGuarantorMemberIds = newGuarantorMemberId;
            } else {
                newGuarantorMemberIds += "," + newGuarantorMemberId;
            }

            MemberSummaryBean summaryBean = new MemberSummaryBean();
            MemberView memberView = memberViewService.getMember(newGuarantorMemberId);

            List<LoanRequest> loansX = loanRequestService.listLoanRequestsByMember(newGuarantorMemberId, true);
            int runningLoansCountX = 0;
            double runningLoansSumX = 0.00;
            double totalContributionX = helperUTIL.getMemberTotalSavings(helperUTIL.getMemberNofromId(newGuarantorMemberId), obj.getCompanyId(), obj.getBranchId());
           //added by nelson akpos
            double memberBalance = helperUTIL.getMemberLoanBalance(obj.getMemberNo(), obj.getCompanyId(), obj.getBranchId());


            if (loansX != null && loansX.size() > 0) {
                for (LoanRequest itemX : loansX) {
                    runningLoansCountX += 1;
                    runningLoansSumX += itemX.getApprovedAmount();
                }
            }
         
            summaryBean.setMemberId(newGuarantorMemberId);
            summaryBean.setRunningLoanCount(runningLoansCountX);
            summaryBean.setRunningLoanSum(runningLoansSumX);
            summaryBean.setMemberName(memberView.getFirstname() + " " + memberView.getSurname());
            summaryBean.setCompMemberId(memberView.getCompmemberId());
               //added by nelson akpos
            memberSummaryBean.setRunningLoanBalance(memberBalance);
            summaryBean.setTotalMemberContribution(totalContributionX);
            System.out.println("memberName contains - " + summaryBean.getMemberName());
            System.out.println("memberName Id - " + summaryBean.getMemberId());
            newSummaryBeans.add(summaryBean);
        }

        //end of new code

        //model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct());

        model.put("newGuarantorsSummaryBeans", newSummaryBeans);
        //
       // model.put("loanGuarantorCObj", loanG_obj);
          System.out.println("loan guarantor change branch ID is "+ obj.getBranchId() );
                
       System.out.println("i am here after summary bean ");
          //System.out.println("what is inside this object "+ loanG.getBranchId());
        
        
        


        return new ModelAndView("editNewGuarantor", model);
    }

    @RequestMapping(value = "/approveNewLoanGuarantor", method = RequestMethod.POST)
    public ModelAndView approveNewLoanGuarantor(@ModelAttribute("loanGuarantorChange") LoanGuarantorChangeBean loanGuarantorChangeBean, BindingResult result, HttpServletRequest req) {
        String redurlpath = null;


        String[] loanCaseId = loanGuarantorChangeBean.getLoan().split(",");
        String uniqLoanCase = loanCaseId[0];

        // LoanGuarantorChange newLoanGuarantor = prepareModel(loanGuarantorChangeBean);
        String[] replaced = req.getParameterValues("replaced");

        String[] selectedGuarantor = req.getParameterValues("selectedGuarantor");
         String[] ids = req.getParameterValues("ids");
         
        //update loan guarantor change
        System.out.println("the loancase is - " + uniqLoanCase);
        for (int i = 0; i < selectedGuarantor.length; i++) {
            helperUTIL.UpdateNewLoanGuarantor(ids[i]);
           // helperUTIL.UpdateOriginalLoanGuarantor(uniqLoanCase, selectedGuarantor[i], replaced[i]);
        }

        //update original loan guarantor here 
       for (int i = 0; i < selectedGuarantor.length; i++) {
          helperUTIL.UpdateOriginalLoanGuarantor(uniqLoanCase, selectedGuarantor[i], replaced[i]);
       }





        redurlpath = "redirect:/doFeedback.htm?message= Changed to new Guarantor &redirectURI=listNewGuarantors.htm";
        return new ModelAndView(redurlpath);
    }

    private List<LoanGuarantorChangeBean> prepareListofBean(List<LoanGuarantorChange> newGuarantor) {
        List<LoanGuarantorChangeBean> beans = null;

        if (newGuarantor != null && !newGuarantor.isEmpty()) {
            beans = new ArrayList<LoanGuarantorChangeBean>();
            LoanGuarantorChangeBean newGuarantorBean = null;

            for (LoanGuarantorChange guarantorObj : newGuarantor) {
                newGuarantorBean = new LoanGuarantorChangeBean();

                newGuarantorBean.setId(guarantorObj.getId());
                System.out.println("the  id is " + guarantorObj.getId());
                newGuarantorBean.setLoanCaseId(guarantorObj.getLoanCaseId());
                System.out.println("the loan case id is " + guarantorObj.getLoanCaseId());
                //newGuarantorBean.setLoanCaseId("2206052016122751002");

                newGuarantorBean.setApproved(guarantorObj.getApproved());
                System.out.println("approved by " + guarantorObj.getApproved());
                // newGuarantorBean.setApproved("N");
                newGuarantorBean.setBranchId(guarantorObj.getBranchId());
                System.out.println("the branch id is " + guarantorObj.getBranchId());
                newGuarantorBean.setCompanyId(guarantorObj.getCompanyId());
                System.out.println("the  company id is " + guarantorObj.getCompanyId());
                newGuarantorBean.setCreatedBy(guarantorObj.getCreatedBy());
                System.out.println("created by " + guarantorObj.getCreatedBy());
                newGuarantorBean.setOldGuarantor(guarantorObj.getGuarantorReplaced());
                System.out.println("the old guarantor is " + guarantorObj.getGuarantorReplaced());
                // newGuarantorBean.setOldguarantor_1(guarantorObj.getGuarantorReplaced2());


                newGuarantorBean.setGuarantorNo(guarantorObj.getGuarantorNo());
                System.out.println("New Guarantor number -" + guarantorObj.getGuarantorNo());
                // MemberView memb = memberViewService.getMember(guarantorObj.getGuarantorNo());
                //// if(memb != null){
                //System.out.println("New guarantor one full name "+memb.getFirstname()+" "+memb.getSurname());
                //}else{
                //System.out.println("member object is null ");
                //}

                // newGuarantorBean.setGuarantorNo2(guarantorObj.getGuarantorNo2());
                //start


                beans.add(newGuarantorBean);
            }

        }

        return beans;
    }

    private List<LoanGuarantorChangeBean> prepareListofBeans(List<LoanGuarantorChange> newGuarantor) {
        List<LoanGuarantorChangeBean> beans = null;

        if (newGuarantor != null && !newGuarantor.isEmpty()) {
            beans = new ArrayList<LoanGuarantorChangeBean>();
            LoanGuarantorChangeBean newGuarantorBean = null;

            for (LoanGuarantorChange guarantorObj : newGuarantor) {
                newGuarantorBean = new LoanGuarantorChangeBean();



                newGuarantorBean.setId(guarantorObj.getId());
                System.out.println("the  id is " + guarantorObj.getId());
                newGuarantorBean.setLoanCaseId(guarantorObj.getLoanCaseId());
                System.out.println("the loan case id is " + guarantorObj.getLoanCaseId());
                //newGuarantorBean.setLoanCaseId("2206052016122751002");

                newGuarantorBean.setApproved(guarantorObj.getApproved());
               
                System.out.println("approved by " + guarantorObj.getApproved());
              
                newGuarantorBean.setBranchId(guarantorObj.getBranchId());
                System.out.println("the branch id is " + guarantorObj.getBranchId());
                newGuarantorBean.setCompanyId(guarantorObj.getCompanyId());
                System.out.println("the  company id is " + guarantorObj.getCompanyId());
                newGuarantorBean.setCreatedBy(guarantorObj.getCreatedBy());
                System.out.println("created by " + guarantorObj.getCreatedBy());
                newGuarantorBean.setOldGuarantor(guarantorObj.getGuarantorReplaced());
                System.out.println("the old guarantor is " + guarantorObj.getGuarantorReplaced());
                // newGuarantorBean.setOldguarantor_1(guarantorObj.getGuarantorReplaced2());
                newGuarantorBean.setMemberName(memberViewService.getMember(guarantorObj.getMemberNo()).getFirstname() + " " + memberViewService.getMember(guarantorObj.getMemberNo()).getSurname());
                newGuarantorBean.setGuarantorName1(memberViewService.getMember(guarantorObj.getGuarantorNo()).getFirstname() + " " + memberViewService.getMember(guarantorObj.getGuarantorNo()).getSurname());
                newGuarantorBean.setGuarantorReplaced(memberViewService.getMember(guarantorObj.getGuarantorReplaced()).getFirstname() + " " + memberViewService.getMember(guarantorObj.getGuarantorReplaced()).getSurname());
                newGuarantorBean.setGuarantorNo(guarantorObj.getGuarantorNo());
                newGuarantorBean.setGuarantorReplacedNo(guarantorObj.getGuarantorReplaced());
                System.out.println("New Guarantor number -" + guarantorObj.getGuarantorNo());
                // MemberView memb = memberViewService.getMember(guarantorObj.getGuarantorNo());
                //// if(memb != null){
                //System.out.println("New guarantor one full name "+memb.getFirstname()+" "+memb.getSurname());
                //}else{
                //System.out.println("member object is null ");
                //}

                // newGuarantorBean.setGuarantorNo2(guarantorObj.getGuarantorNo2());
                //start


                beans.add(newGuarantorBean);
            }

        }

        return beans;
    }

    private LoanGuarantorChange prepareModel(LoanGuarantorChangeBean loanGuarantorChangeBean) {
        LoanGuarantorChange newGuarantor = new LoanGuarantorChange();

        newGuarantor.setId(loanGuarantorChangeBean.getId());
        newGuarantor.setLoanCaseId(loanGuarantorChangeBean.getLoanCaseId());
        newGuarantor.setApproved("Y");
        //newGuarantor.setCreatedBy(guarantorBean.getCreatedBy());
        newGuarantor.setAcceptanceDate(loanGuarantorChangeBean.getAcceptanceDate());
        newGuarantor.setAcceptanceStatus(loanGuarantorChangeBean.getAcceptanceStatus());
        newGuarantor.setBranchId(loanGuarantorChangeBean.getBranchId());
        newGuarantor.setCompanyId(loanGuarantorChangeBean.getCompanyId());
        newGuarantor.setCreatedBy(loanGuarantorChangeBean.getCreatedBy());
        newGuarantor.setCreationDate(loanGuarantorChangeBean.getCreationDate());
        newGuarantor.setGuarantorComment(loanGuarantorChangeBean.getGuarantorComment());
        newGuarantor.setGuarantorNo(loanGuarantorChangeBean.getGuarantorNo());
        newGuarantor.setLastModifiedBy(loanGuarantorChangeBean.getLastModifiedBy());
        System.out.println("my member number - " + loanGuarantorChangeBean.getMemberNo());
        newGuarantor.setMemberNo(loanGuarantorChangeBean.getMemberNo());
        newGuarantor.setGuarantorReplaced(loanGuarantorChangeBean.getGuarantorReplaced());
        //newGuarantor.setGuarantorNo2(loanGuarantorChangeBean.getGuarantorNo2());
        //newGuarantor.setGuarantorReplaced2(loanGuarantorChangeBean.getGuarantorReplaced2());
        newGuarantor.setLastModificationDate(loanGuarantorChangeBean.getLastModificationDate());
        return newGuarantor;
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
    
    
    private LoanGuarantorChangeBean prepareLoanGuarantorChangeBean(LoanGuarantorChange loanGuarantorChange) {
        LoanGuarantorChangeBean bean = new LoanGuarantorChangeBean();
        //MemberView memb = memberViewService.getMember(loanGuarantorChange.getGuarantorNo());

        //MemberView oldmemb = memberViewService.getMember(loanGuarantorChange.getGuarantorReplaced());

        bean.setId(loanGuarantorChange.getId());
        System.out.println("i am here before the set loan case Id");
        bean.setLoanCaseId(loanGuarantorChange.getLoanCaseId());
        System.out.println("i am here after the set loan case Id");
        bean.setGuarantorNo(loanGuarantorChange.getGuarantorNo());

        bean.setOldGuarantor(loanGuarantorChange.getGuarantorReplaced());

        bean.setGuarantorReplaced(loanGuarantorChange.getGuarantorReplaced());

        bean.setMemberNo(loanGuarantorChange.getMemberNo());
        bean.setApproved(loanGuarantorChange.getApproved());
        bean.setBranchId(loanGuarantorChange.getBranchId());
        bean.setCompanyId(loanGuarantorChange.getCompanyId());
        //bean.setGuarantorName1(memb.getFirstname() + " " + memb.getSurname());
        //bean.setGuarantorName2(memb2.getFirstname() + " " + memb2.getSurname());
        //bean.setGuarantorReplacedName1(oldmemb.getFirstname() + " " + oldmemb.getSurname());
        //bean.setGuarantorReplacedName2(oldmemb2.getFirstname() + " " + oldmemb2.getSurname());



        return bean;
        
    }
}
