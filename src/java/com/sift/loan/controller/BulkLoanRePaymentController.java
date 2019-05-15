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
import com.sift.admin.model.Country;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.UserService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.service.BulkLoanRePaymentService;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.bean.BulkLoanRePaymentBean;
import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.bean.LoanRequestBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class BulkLoanRePaymentController{
 @Autowired
 private CompanyService companyService;
 
 @Autowired
 private CurrentuserdisplayImpl currentuserdisplayService;

 @Autowired
 private BranchService branchService;
	 
 @Autowired
 private BulkLoanRePaymentService bulkLoanRePaymentService;

 @Autowired
 private LoanRequestService loanRequestService;
 
 @Autowired
 private LoanRepaymentScheduleService loanRepaymentScheduleService;

 @Autowired
 private UserService userService;
 
 @Autowired
 private CountryService countryService;
 
 @Autowired
 private ProductService productService;
 
 @Autowired
 private HelperUtility helperUTIL;
 
 BeanMapperUtility   beanMapper =new BeanMapperUtility();
 EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
 
 @RequestMapping(value = "/saveBulkLoanRePayment", method = RequestMethod.POST)
 public ModelAndView saveBulkLoanRePayment(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean,BindingResult result,HttpServletRequest req){
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 
	 /***
	 System.out.println("LoanCaseId=:" + bulkLoanRePaymentBean.getLoanCaseId());
	 System.out.println("Repay Tot Amount=:" + bulkLoanRePaymentBean.getRepayTotAmt());
	 System.out.println("Repay Tot Intr=:" + bulkLoanRePaymentBean.getRepayTotInt());
	 System.out.println("Repay Tot Penalty=:" + bulkLoanRePaymentBean.getRepayTotPenalty());
	 System.out.println("Repay Tot Principal=:" + bulkLoanRePaymentBean.getRepayTotPrl());
	 ***/
	 
	 BulkLoanRePayment bulkLoanRePayment = prepareModel(bulkLoanRePaymentBean);
	 bulkLoanRePaymentService.addBulkLoanRePayment(bulkLoanRePayment);
	 boolean updated=false;
	 
	 String  scheduleIds=req.getParameter("scheduleIds");
	 
	 Company coy=companyService.getCompany(Integer.parseInt(currentUser.getCompanyId()));
	 LoanRequest obj=loanRequestService.getLoanRequestByCaseId(bulkLoanRePaymentBean.getLoanCaseId());
	 Country country=countryService.getCountry(Integer.parseInt(coy.getCountryId()));
	 String timeZone=country.getTimeZone();
	 Date localDate=eazyCoopUTIL.getTimeByZone(timeZone);

	 boolean closeLoan=false;
	 
	 try{		 
		 eazyCoopUTIL.setHelperUTIL(helperUTIL);
		 
		 if(obj.getBalanceTotal()<=bulkLoanRePayment.getRepayTotAmt()){
			closeLoan=true;
		 }
		 String ipAdddress = req.getRemoteHost();
		 //update accounts
		 if(eazyCoopUTIL.processBulkLoanRepayment(coy,obj,bulkLoanRePayment,timeZone,currentuserdisplayService.getCurrusercompany().getPostDate(), ipAdddress)){		 
			 updated=loanRepaymentScheduleService.doBulkLoanRepayment(scheduleIds,bulkLoanRePayment,closeLoan);
			 
			 if(updated){
				 //update loan balances to reflect update
				 loanRequestService.doUpdateLoanBalance(obj, bulkLoanRePayment, closeLoan);
			 }
		 }
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }
	 
	 if(updated){
		 return new ModelAndView("redirect:/doFeedback.htm?message=Bulk Loan Repayment Processing was successful&redirectURI=newBulkLoanRePayment-0.htm?id=");
	 }else{
		 return new ModelAndView("redirect:/doError.htm?message=Bulk Loan Repayment Processing Failed. Please try aagin later.&redirectURI=newBulkLoanRePayment-0.htm?id=");
	 }
 }

 @RequestMapping(value="/bulkLoanRePayments", method = RequestMethod.GET)
 public ModelAndView listBulkLoanRePayments() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("bulkLoanRePayments",  prepareListofBean(bulkLoanRePaymentService.listBulkLoanRePayments()));
	 return new ModelAndView("bulkLoanRePaymentsList", model);
 }

 @RequestMapping(value = "/newBulkLoanRePayment-0", method = RequestMethod.GET)
 public ModelAndView addBulkLoanRePayment0(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
		
     return new ModelAndView("addBulkLoanRePayment-0", model);
 }
 
 @RequestMapping(value = "/newBulkLoanRePayment-1", method = RequestMethod.POST)
 public ModelAndView addBulkLoanRePayment1(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 
	 List<LoanRepaymentScheduleBean>    listX=beanMapper.prepareListofLoanRepaymentScheduleBean(loanRepaymentScheduleService.listPendingLoanRepaymentScheduleByLoanCaseId(bulkLoanRePaymentBean.getLoanCaseId()));
	 LoanRequestBean myBean= beanMapper.prepareLoanRequestBean(loanRequestService.getLoanRequestByCaseId(bulkLoanRePaymentBean.getLoanCaseId()));
  	 model.put("schedules",  listX);
  	 
  	 String loanStatus=myBean.getLoanStatus();
  	 
  	 if(!"D".equalsIgnoreCase(loanStatus.trim())){
		 return new ModelAndView("redirect:/doError.htm?message=The specified loan is not valid for disbursement&redirectURI=addBulkLoanRePayment-0.htm");
  	 }else{
  	     return new ModelAndView("addBulkLoanRePayment-1", model);
  	 }
 } 
 
 @RequestMapping(value = "/newBulkLoanRePayment-2", method = RequestMethod.POST)
 public ModelAndView addBulkLoanRePayment2(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 
	 double loanTotal=0.0;
	 double interestTotal=0.0;
	 double principalTotal=0.0;
	 double penaltyTotal=0.0;
	 
	 String[] scheduleIds=req.getParameterValues("scheduleIds");
	 String   scheduleConcat="";
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 LoanRequest objL=loanRequestService.getLoanRequestByCaseId(bulkLoanRePaymentBean.getLoanCaseId());
	 
	 for(String item: scheduleIds){		 
		 LoanRepaymentSchedule schedule=loanRepaymentScheduleService.getLoanRepaymentSchedule(Integer.parseInt(item));
		 Product product=productService.getProductsDistinctByCodeByBranch(currentUser.getBranchId(), objL.getLoanType());
		 
		 double penaltyIncurred=helperUTIL.getLoanPenalty(schedule, product, eazyCoopUTIL.getDaysDiff(schedule.getActualRepaymentDate(), schedule.getExpectedRepaymentDate()));
		 
		 loanTotal+=schedule.getExpectedRepaymentAmount();
		 interestTotal+=schedule.getAmountInterest();
		 principalTotal+=schedule.getAmountPrincipal();		
		 //penaltyTotal+=schedule.getPenaltyIncurred();
		 penaltyTotal+=penaltyIncurred;
		 
		 //scheduleConcat.length()>0?(scheduleConcat+="," + item) : (scheduleConcat+=item);		 
		 if(scheduleConcat.length()>0){scheduleConcat+="," + item;}
		 else{scheduleConcat+=item;}
	 }
	 
	 bulkLoanRePaymentBean.setRepayTotAmt(eazyCoopUTIL.FormatTo2DecimalD(loanTotal));
	 bulkLoanRePaymentBean.setRepayTotInt(eazyCoopUTIL.FormatTo2DecimalD(interestTotal));
	 bulkLoanRePaymentBean.setRepayTotPenalty(eazyCoopUTIL.FormatTo2DecimalD(penaltyTotal));
	 bulkLoanRePaymentBean.setRepayTotPrl(eazyCoopUTIL.FormatTo2DecimalD(principalTotal));
	 
  	 model.put("bulkLoanRePayment",  bulkLoanRePaymentBean);
  	 model.put("scheduleConcat",  scheduleConcat);
  	 
     return new ModelAndView("addBulkLoanRePayment-2", model);
 } 

@RequestMapping(value = "/deleteBulkLoanRePayment", method = RequestMethod.GET)
public ModelAndView deleteBulkLoanRePayment(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean,BindingResult result) {
	    bulkLoanRePaymentService.deleteBulkLoanRePayment(prepareModel(bulkLoanRePaymentBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("bulkLoanRePayment", null);
		model.put("bulkLoanRePayments",  prepareListofBean(bulkLoanRePaymentService.listBulkLoanRePayments()));
		return new ModelAndView("addBulkLoanRePayment", model);
 }

@RequestMapping(value = "/editBulkLoanRePayment", method = RequestMethod.GET)
public ModelAndView editBulkLoanRePayment(@ModelAttribute("bulkLoanRePayment")BulkLoanRePaymentBean bulkLoanRePaymentBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("bulkLoanRePayment", prepareBulkLoanRePaymentBean(bulkLoanRePaymentService.getBulkLoanRePayment(bulkLoanRePaymentBean.getId())));
		model.put("bulkLoanRePayments",  prepareListofBean(bulkLoanRePaymentService.listBulkLoanRePayments()));
		return new ModelAndView("editBulkLoanRePayment", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
}

 private BulkLoanRePayment prepareModel(BulkLoanRePaymentBean bulkLoanRePaymentBean){
	    BulkLoanRePayment bulkLoanRePayment = new BulkLoanRePayment();

	    bulkLoanRePayment.setCompanyId(bulkLoanRePaymentBean.getCompanyId());
	    bulkLoanRePayment.setBranchId(bulkLoanRePaymentBean.getBranchId());
	    bulkLoanRePayment.setLoanCaseId(bulkLoanRePaymentBean.getLoanCaseId());
	    bulkLoanRePayment.setLoanId(bulkLoanRePaymentBean.getLoanId());
	    bulkLoanRePayment.setProcessor(bulkLoanRePaymentBean.getProcessor());
	    bulkLoanRePayment.setRepayDate(bulkLoanRePaymentBean.getRepayDate());
	    bulkLoanRePayment.setRepayTotInt(bulkLoanRePaymentBean.getRepayTotInt());
	    bulkLoanRePayment.setRepayTotPrl(bulkLoanRePaymentBean.getRepayTotPrl());
	    bulkLoanRePayment.setRepayTotAmt(bulkLoanRePaymentBean.getRepayTotAmt());
	    bulkLoanRePayment.setRepayTotPenalty(bulkLoanRePaymentBean.getRepayTotPenalty());
	    bulkLoanRePayment.setCreatedBy(bulkLoanRePaymentBean.getCreatedBy());
	    bulkLoanRePayment.setCreationDate(bulkLoanRePaymentBean.getCreationDate());
	    bulkLoanRePayment.setLastModifiedBy(bulkLoanRePaymentBean.getLastModifiedBy());
	    bulkLoanRePayment.setLastModificationDate(bulkLoanRePaymentBean.getLastModificationDate());
	    bulkLoanRePayment.setId(bulkLoanRePaymentBean.getId());

	    return bulkLoanRePayment;
 }

 private List<BulkLoanRePaymentBean> prepareListofBean(List<BulkLoanRePayment> bulkLoanRePayments){
        List<BulkLoanRePaymentBean> beans = null;

        if(bulkLoanRePayments != null && !bulkLoanRePayments.isEmpty()){
        	beans = new ArrayList<BulkLoanRePaymentBean>();
        	BulkLoanRePaymentBean bulkLoanRePayment = null;

        	for(BulkLoanRePayment bulkLoanRePaymentBean : bulkLoanRePayments){
        		bulkLoanRePayment = new BulkLoanRePaymentBean();

			    bulkLoanRePayment.setCompanyId(bulkLoanRePaymentBean.getCompanyId());
			    bulkLoanRePayment.setBranchId(bulkLoanRePaymentBean.getBranchId());

        		bulkLoanRePayment.setId(bulkLoanRePaymentBean.getId());

        	    bulkLoanRePayment.setCompanyId(bulkLoanRePaymentBean.getCompanyId());
        	    bulkLoanRePayment.setBranchId(bulkLoanRePaymentBean.getBranchId());
        	    bulkLoanRePayment.setLoanCaseId(bulkLoanRePaymentBean.getLoanCaseId());
        	    bulkLoanRePayment.setLoanId(bulkLoanRePaymentBean.getLoanId());
        	    bulkLoanRePayment.setProcessor(bulkLoanRePaymentBean.getProcessor());
        	    bulkLoanRePayment.setRepayDate(bulkLoanRePaymentBean.getRepayDate());
        	    bulkLoanRePayment.setRepayTotInt(bulkLoanRePaymentBean.getRepayTotInt());
        	    bulkLoanRePayment.setRepayTotPrl(bulkLoanRePaymentBean.getRepayTotPrl());
        	    bulkLoanRePayment.setRepayTotAmt(bulkLoanRePaymentBean.getRepayTotAmt());
        	    bulkLoanRePayment.setRepayTotPenalty(bulkLoanRePaymentBean.getRepayTotPenalty());
			    bulkLoanRePayment.setCreatedBy(bulkLoanRePaymentBean.getCreatedBy());
			    bulkLoanRePayment.setCreationDate(bulkLoanRePaymentBean.getCreationDate());
			    bulkLoanRePayment.setLastModifiedBy(bulkLoanRePaymentBean.getLastModifiedBy());
			    bulkLoanRePayment.setLastModificationDate(bulkLoanRePaymentBean.getLastModificationDate());

			    beans.add(bulkLoanRePayment);
		   }
	    }

        return beans;
 }

 private BulkLoanRePaymentBean prepareBulkLoanRePaymentBean(BulkLoanRePayment bulkLoanRePayment){
		  BulkLoanRePaymentBean 	bean = new BulkLoanRePaymentBean();

		  bean.setId(bulkLoanRePayment.getId());
		  bean.setCompanyId(bulkLoanRePayment.getCompanyId());
		  bean.setBranchId(bulkLoanRePayment.getBranchId());
		  bean.setLoanCaseId(bulkLoanRePayment.getLoanCaseId());
		  bean.setLoanId(bulkLoanRePayment.getLoanId());
		  bean.setProcessor(bulkLoanRePayment.getProcessor());
		  bean.setRepayDate(bulkLoanRePayment.getRepayDate());
		  bean.setRepayTotInt(bulkLoanRePayment.getRepayTotInt());
		  bean.setRepayTotPrl(bulkLoanRePayment.getRepayTotPrl());
		  bean.setRepayTotAmt(bulkLoanRePayment.getRepayTotAmt());
		  bean.setRepayTotPenalty(bulkLoanRePayment.getRepayTotPenalty());
		  bean.setCreatedBy(bulkLoanRePayment.getCreatedBy());
		  bean.setCreationDate(bulkLoanRePayment.getCreationDate());
		  bean.setLastModifiedBy(bulkLoanRePayment.getLastModifiedBy());
		  bean.setLastModificationDate(bulkLoanRePayment.getLastModificationDate());

		  return bean;
 }
}