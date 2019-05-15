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

import com.sift.admin.model.Company;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.UserService;
import com.sift.loan.model.BalloonPayment;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.service.BalloonPaymentService;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.bean.BalloonPaymentBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class BalloonPaymentController{
 @Autowired
 private CompanyService companyService;

 @Autowired
 private BranchService branchService;
	 
 @Autowired
 private BalloonPaymentService balloonPaymentService;

 @Autowired
 private LoanRequestService loanRequestService;
 
 @Autowired
 private LoanRepaymentScheduleService loanRepaymentScheduleService;

 @Autowired
 private UserService userService;
 
 @Autowired
 private HelperUtility helperUTIL;
 
 BeanMapperUtility   beanMapper =new BeanMapperUtility();
 EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
 
 @RequestMapping(value = "/saveBalloonPayment", method = RequestMethod.POST)
 public ModelAndView saveBalloonPayment(@ModelAttribute("balloonPayment")BalloonPaymentBean balloonPaymentBean,BindingResult result){
	 BalloonPayment balloonPayment = prepareModel(balloonPaymentBean);
	 balloonPaymentService.addBalloonPayment(balloonPayment);
	 boolean updated=false;
	 
	 Company coy=companyService.getCompany(Integer.parseInt(balloonPaymentBean.getCompanyId()));
	 LoanRequest obj=loanRequestService.getLoanRequestByCaseId(balloonPaymentBean.getLoanCaseId());
	 
	 try{		 
		 eazyCoopUTIL.setHelperUTIL(helperUTIL);
		 
		 //update accounts
		 if(eazyCoopUTIL.processBalloonRepayment(coy, obj)){		 
			 //balloonPaymentService.addBalloonPayment(balloonPayment);
			 updated=loanRepaymentScheduleService.doBalloonRepayment(balloonPaymentBean.getLoanCaseId(),balloonPayment);
			 
			 if(updated){
				 //update loan balances to reflect update
				 //loanRequestService.doUpdateLoanBalance(obj, null, true);
			 }
		 }
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }
	 
	 if(updated){
		 return new ModelAndView("redirect:/doFeedback.htm?message=Balloon Loan Repayment Processing was successful&redirectURI=newBalloonPayment.htm?id=");
	 }else{
		 return new ModelAndView("redirect:/doError.htm?message=Balloon Loan Repayment Processing Failed. Please try aagin later.&redirectURI=newBalloonPayment.htm?id=");
	 }
 }

 @RequestMapping(value="/balloonPayments", method = RequestMethod.GET)
 public ModelAndView listBalloonPayments() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("balloonPayments",  prepareListofBean(balloonPaymentService.listBalloonPayments()));
	 return new ModelAndView("balloonPaymentsList", model);
 }

 @RequestMapping(value = "/newBalloonPayment", method = RequestMethod.GET)
 public ModelAndView addBalloonPayment(@ModelAttribute("balloonPayment")BalloonPaymentBean balloonPaymentBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
		
  	 model.put("balloonPayments",  prepareListofBean(balloonPaymentService.listBalloonPayments()));
     return new ModelAndView("addBalloonPayment", model);
 }
 
 @RequestMapping(value = "/newBalloonPayment2", method = RequestMethod.POST)
 public ModelAndView addBalloonPayment2(@ModelAttribute("balloonPayment")BalloonPaymentBean balloonPaymentBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
	 
	 List<LoanRepaymentSchedule>    listX=loanRepaymentScheduleService.listPendingLoanRepaymentScheduleByLoanCaseId(balloonPaymentBean.getLoanCaseId());
	 double loanTotal=0.0;
	 double interestTotal=0.0;
	 double principalTotal=0.0;
	 
	 if(listX!=null && listX.size()>0){
		 for(LoanRepaymentSchedule itemX: listX){
		    loanTotal+=itemX.getExpectedRepaymentAmount();
		    interestTotal+=itemX.getAmountInterest();
		    principalTotal+=itemX.getAmountPrincipal();
		 }
		 
		 balloonPaymentBean.setRepayTotAmt(loanTotal);
		 balloonPaymentBean.setRepayTotInt(interestTotal);
		 balloonPaymentBean.setRepayTotPrl(principalTotal);
	 }
	 
  	 model.put("balloonPayments", prepareListofBean(balloonPaymentService.listBalloonPayments()));
  	 model.put("balloonPayment",  balloonPaymentBean);
     return new ModelAndView("addBalloonPayment-2", model);
 }

@RequestMapping(value = "/deleteBalloonPayment", method = RequestMethod.GET)
public ModelAndView deleteBalloonPayment(@ModelAttribute("balloonPayment")BalloonPaymentBean balloonPaymentBean,BindingResult result) {
	    balloonPaymentService.deleteBalloonPayment(prepareModel(balloonPaymentBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("balloonPayment", null);
		model.put("balloonPayments",  prepareListofBean(balloonPaymentService.listBalloonPayments()));
		return new ModelAndView("addBalloonPayment", model);
 }

@RequestMapping(value = "/editBalloonPayment", method = RequestMethod.GET)
public ModelAndView editBalloonPayment(@ModelAttribute("balloonPayment")BalloonPaymentBean balloonPaymentBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("balloonPayment", prepareBalloonPaymentBean(balloonPaymentService.getBalloonPayment(balloonPaymentBean.getId())));
		model.put("balloonPayments",  prepareListofBean(balloonPaymentService.listBalloonPayments()));
		return new ModelAndView("editBalloonPayment", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
}

 private BalloonPayment prepareModel(BalloonPaymentBean balloonPaymentBean){
	    BalloonPayment balloonPayment = new BalloonPayment();

	    balloonPayment.setCompanyId(balloonPaymentBean.getCompanyId());
	    balloonPayment.setBranchId(balloonPaymentBean.getBranchId());
	    balloonPayment.setLoanCaseId(balloonPaymentBean.getLoanCaseId());
	    balloonPayment.setLoanId(balloonPaymentBean.getLoanId());
	    balloonPayment.setProcessor(balloonPaymentBean.getProcessor());
	    balloonPayment.setRepayDate(balloonPaymentBean.getRepayDate());
	    balloonPayment.setRepayTotInt(balloonPaymentBean.getRepayTotInt());
	    balloonPayment.setRepayTotPrl(balloonPaymentBean.getRepayTotPrl());
	    balloonPayment.setRepayTotAmt(balloonPaymentBean.getRepayTotAmt());

	    balloonPayment.setCreatedBy(balloonPaymentBean.getCreatedBy());
	    balloonPayment.setCreationDate(balloonPaymentBean.getCreationDate());
	    balloonPayment.setLastModifiedBy(balloonPaymentBean.getLastModifiedBy());
	    balloonPayment.setLastModificationDate(balloonPaymentBean.getLastModificationDate());
	    balloonPayment.setId(balloonPaymentBean.getId());
	    //balloonPaymentBean.setId(null);

	    return balloonPayment;
 }

 private List<BalloonPaymentBean> prepareListofBean(List<BalloonPayment> balloonPayments){
        List<BalloonPaymentBean> beans = null;

        if(balloonPayments != null && !balloonPayments.isEmpty()){
        	beans = new ArrayList<BalloonPaymentBean>();
        	BalloonPaymentBean balloonPayment = null;

        	for(BalloonPayment balloonPaymentBean : balloonPayments){
        		balloonPayment = new BalloonPaymentBean();

			    balloonPayment.setCompanyId(balloonPaymentBean.getCompanyId());
			    balloonPayment.setBranchId(balloonPaymentBean.getBranchId());

        		balloonPayment.setId(balloonPaymentBean.getId());

        	    balloonPayment.setCompanyId(balloonPaymentBean.getCompanyId());
        	    balloonPayment.setBranchId(balloonPaymentBean.getBranchId());
        	    balloonPayment.setLoanCaseId(balloonPaymentBean.getLoanCaseId());
        	    balloonPayment.setLoanId(balloonPaymentBean.getLoanId());
        	    balloonPayment.setProcessor(balloonPaymentBean.getProcessor());
        	    balloonPayment.setRepayDate(balloonPaymentBean.getRepayDate());
        	    balloonPayment.setRepayTotInt(balloonPaymentBean.getRepayTotInt());
        	    balloonPayment.setRepayTotPrl(balloonPaymentBean.getRepayTotPrl());
        	    balloonPayment.setRepayTotAmt(balloonPaymentBean.getRepayTotAmt());

			    balloonPayment.setCreatedBy(balloonPaymentBean.getCreatedBy());
			    balloonPayment.setCreationDate(balloonPaymentBean.getCreationDate());
			    balloonPayment.setLastModifiedBy(balloonPaymentBean.getLastModifiedBy());
			    balloonPayment.setLastModificationDate(balloonPaymentBean.getLastModificationDate());

			    beans.add(balloonPayment);
		   }
	    }

        return beans;
 }

 private BalloonPaymentBean prepareBalloonPaymentBean(BalloonPayment balloonPayment){
		  BalloonPaymentBean 	bean = new BalloonPaymentBean();

		  bean.setId(balloonPayment.getId());
		  bean.setCompanyId(balloonPayment.getCompanyId());
		  bean.setBranchId(balloonPayment.getBranchId());
		  bean.setLoanCaseId(balloonPayment.getLoanCaseId());
		  bean.setLoanId(balloonPayment.getLoanId());
		  bean.setProcessor(balloonPayment.getProcessor());
		  bean.setRepayDate(balloonPayment.getRepayDate());
		  bean.setRepayTotInt(balloonPayment.getRepayTotInt());
		  bean.setRepayTotPrl(balloonPayment.getRepayTotPrl());
		  bean.setRepayTotAmt(balloonPayment.getRepayTotAmt());
		  bean.setCreatedBy(balloonPayment.getCreatedBy());
		  bean.setCreationDate(balloonPayment.getCreationDate());
		  bean.setLastModifiedBy(balloonPayment.getLastModifiedBy());
		  bean.setLastModificationDate(balloonPayment.getLastModificationDate());

		  return bean;
 }
}