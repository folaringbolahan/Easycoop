package com.sift.loan.controller;

import java.text.DecimalFormat;
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
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.UserService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.service.LoanPayOffService;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.bean.LoanPayOffBean;
/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanPayOffController{
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
 private LoanPayOffService loanPayOffService;

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
 
 @RequestMapping(value = "/saveLoanPayOff", method = RequestMethod.POST)
 public ModelAndView saveLoanPayOff(@ModelAttribute("loanPayOff")LoanPayOffBean loanPayOffBean,BindingResult result,HttpServletRequest req){
	 LoanPayOff loanPayOff = prepareModel(loanPayOffBean);
	 loanPayOffService.addLoanPayOff(loanPayOff);
	 boolean updated=false;
	 
	 Company coy=companyService.getCompany(Integer.parseInt(loanPayOffBean.getCompanyId()));
	 LoanRequest obj=loanRequestService.getLoanRequestByCaseId(loanPayOffBean.getLoanCaseId());
	 String timeZone=countryService.getCountry(Integer.parseInt(coy.getCountryId())).getTimeZone();

	 try{		 
		 eazyCoopUTIL.setHelperUTIL(helperUTIL);		 
		 //update accounts
		 if(eazyCoopUTIL.processLoanPayOff(coy, obj,loanPayOff,timeZone,currentuserdisplayService.getCurrusercompany().getPostDate())){		 
			 //loanPayOffService.addLoanPayOff(loanPayOff);
			 loanPayOff.setProcessor(req.getRemoteUser());
			 updated=loanRepaymentScheduleService.doLoanPayOff(loanPayOffBean.getLoanCaseId(),loanPayOff);
			 
			 if(updated){
				 //update loan balances to reflect update
				 //loanRequestService.doUpdateLoanBalance(obj,null, true);
				 
				 Activitylog activity=new Activitylog();
				 
				 //Audit action
			     activity.setEvent(Definitions.EVENT_LOAN_PAYOFF);
			     activity.setAction("Loan PayOff Processing for Loan ID: " + loanPayOff.getLoanCaseId());
			     activity.setActionDate(new java.util.Date());
			     activity.setActionItem("Loan ID: " + loanPayOff.getLoanCaseId());
			     activity.setActionResult("Loan PayOff for Loan ID: " + loanPayOff.getLoanCaseId());
			     activity.setDescription("Loan PayOff for Loan ID: " + loanPayOff.getLoanCaseId());
			     activity.setIpaddress(req.getRemoteHost());
			     activity.setUsername(req.getRemoteUser());	
			     activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(coy.getId().toString()));
			     activity.setToDate("");
			     
			     try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
			 }
		 }
	 }catch(Exception ex){
		 ex.printStackTrace();
	 }
	 
	 if(updated){
		 return new ModelAndView("redirect:/doFeedback.htm?message=Loan PayOff Processing was successful&redirectURI=newLoanPayOff.htm?id=");
	 }else{
		 return new ModelAndView("redirect:/doError.htm?message=Loan PayOff Processing Failed. Please try aagin later.&redirectURI=newLoanPayOff.htm?id=");
	 }
 }

 @RequestMapping(value="/loanPayOffs", method = RequestMethod.GET)
 public ModelAndView listLoanPayOffs() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanPayOffs",  prepareListofBean(loanPayOffService.listLoanPayOffs()));
	 return new ModelAndView("loanPayOffsList", model);
 }

 @RequestMapping(value = "/newLoanPayOff", method = RequestMethod.GET)
 public ModelAndView addLoanPayOff(@ModelAttribute("loanPayOff")LoanPayOffBean loanPayOffBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
  	 
	 //model.put("loanPayOffs",  prepareListofBean(loanPayOffService.listLoanPayOffs()));
     return new ModelAndView("addLoanPayOff", model);
 }
 
 @RequestMapping(value = "/newLoanPayOff2", method = RequestMethod.POST)
 public ModelAndView addLoanPayOff2(@ModelAttribute("loanPayOff")LoanPayOffBean loanPayOffBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 DecimalFormat dformat=new DecimalFormat("0.00");
	 
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 model.put("companies",     beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",      beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 
	 LoanRequest loan=loanRequestService.getLoanRequestByCaseId(loanPayOffBean.getLoanCaseId());
	 
	 if(loan!=null){	 
		 String loanStatus=loan.getLoanStatus();
		 
		 if(!"D".equalsIgnoreCase(loanStatus)){
			 return new ModelAndView("redirect:/doError.htm?message=Invalid Request. Loan PayOff is applicable for Loan that have been disbursed&redirectURI=newLoanPayOff.htm?id=");
		 }else{
			 Product product=productService.getProductByTypeCode(loan.getLoanType(),loan.getCompanyId(),loan.getBranchId());
			 
			 List<LoanRepaymentSchedule>    listX=loanRepaymentScheduleService.listPendingLoanRepaymentScheduleByLoanCaseId(loanPayOffBean.getLoanCaseId());
			 double loanTotal=0.0;
			 double interestTotal=0.0;
			 double principalTotal=0.0;
			 double penaltyTotal=0.0;
			 int    delayDays=0;
			 double penaltyIncurred=0.0;	 
			 double scheduleAmount=0.0;
			 double payOffAmount=0.0;
			 
			 if(listX!=null && listX.size()>0){
				 for(LoanRepaymentSchedule itemX: listX){
					scheduleAmount=itemX.getAmountPrincipal();
					delayDays=eazyCoopUTIL.getDaysDiff(itemX.getExpectedRepaymentDate(), eazyCoopUTIL.currentDate());
									
					if(delayDays>0){
						//penaltyIncurred=eazyCoopUTIL.calculatePenalty(scheduleAmount, delayDays,product.getPenalty());
						penaltyIncurred=helperUTIL.getLoanPenalty(itemX, product, eazyCoopUTIL.getDaysDiff(itemX.getActualRepaymentDate(), itemX.getExpectedRepaymentDate()));
					}else{
						penaltyIncurred=0.0;
					}
					
				    loanTotal+=itemX.getExpectedRepaymentAmount();
				    interestTotal+=itemX.getAmountInterest();
				    principalTotal+=itemX.getAmountPrincipal();
				    penaltyTotal+=penaltyIncurred;
				 }
				 
				 loanPayOffBean.setRepayTotAmt(eazyCoopUTIL.FormatTo2DecimalD(loanTotal + penaltyTotal));
				 loanPayOffBean.setRepayTotInt(eazyCoopUTIL.FormatTo2DecimalD(interestTotal));
				 loanPayOffBean.setRepayTotPrl(eazyCoopUTIL.FormatTo2DecimalD(principalTotal));
				 loanPayOffBean.setRepayPenalty(eazyCoopUTIL.FormatTo2DecimalD(penaltyTotal));
			 }
			 
		  	 //model.put("loanPayOffs", prepareListofBean(loanPayOffService.listLoanPayOffs()));
		  	 model.put("loanPayOff",  loanPayOffBean);
		     return new ModelAndView("addLoanPayOff-2", model);
		 } 
	 }else{
		 return new ModelAndView("redirect:/doError.htm?message=Invalid Request. Loan ID does not exist&redirectURI=newLoanPayOff.htm?id=");
	 }
 }

 @RequestMapping(value = "/deleteLoanPayOff", method = RequestMethod.GET)
 public ModelAndView deleteLoanPayOff(@ModelAttribute("loanPayOff")LoanPayOffBean loanPayOffBean,BindingResult result){
	    loanPayOffService.deleteLoanPayOff(prepareModel(loanPayOffBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanPayOff", null);
		model.put("loanPayOffs",  prepareListofBean(loanPayOffService.listLoanPayOffs()));
		return new ModelAndView("addLoanPayOff", model);
 }

 @RequestMapping(value = "/editLoanPayOff", method = RequestMethod.GET)
 public ModelAndView editLoanPayOff(@ModelAttribute("loanPayOff")LoanPayOffBean loanPayOffBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanPayOff", prepareLoanPayOffBean(loanPayOffService.getLoanPayOff(loanPayOffBean.getId())));
		model.put("loanPayOffs",  prepareListofBean(loanPayOffService.listLoanPayOffs()));
		return new ModelAndView("editLoanPayOff", model);
 }

 @InitBinder
 public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
 }

 private LoanPayOff prepareModel(LoanPayOffBean loanPayOffBean){
	    LoanPayOff loanPayOff = new LoanPayOff();

	    loanPayOff.setCompanyId(loanPayOffBean.getCompanyId());
	    loanPayOff.setBranchId(loanPayOffBean.getBranchId());
	    loanPayOff.setLoanCaseId(loanPayOffBean.getLoanCaseId());
	    loanPayOff.setLoanId(loanPayOffBean.getLoanId());
	    loanPayOff.setProcessor(loanPayOffBean.getProcessor());
	    loanPayOff.setRepayDate(loanPayOffBean.getRepayDate());
	    loanPayOff.setRepayTotInt(loanPayOffBean.getRepayTotInt());
	    loanPayOff.setRepayTotPrl(loanPayOffBean.getRepayTotPrl());
	    loanPayOff.setRepayTotAmt(loanPayOffBean.getRepayTotAmt());
	    loanPayOff.setRepayPenalty(loanPayOffBean.getRepayPenalty());
	    loanPayOff.setCreatedBy(loanPayOffBean.getCreatedBy());
	    loanPayOff.setCreationDate(loanPayOffBean.getCreationDate());
	    loanPayOff.setLastModifiedBy(loanPayOffBean.getLastModifiedBy());
	    loanPayOff.setLastModificationDate(loanPayOffBean.getLastModificationDate());
	    loanPayOff.setId(loanPayOffBean.getId());
	    //loanPayOffBean.setId(null);

	    return loanPayOff;
 }

 private List<LoanPayOffBean> prepareListofBean(List<LoanPayOff> loanPayOffs){
        List<LoanPayOffBean> beans = null;

        if(loanPayOffs != null && !loanPayOffs.isEmpty()){
        	beans = new ArrayList<LoanPayOffBean>();
        	LoanPayOffBean loanPayOff = null;

        	for(LoanPayOff loanPayOffBean : loanPayOffs){
        		loanPayOff = new LoanPayOffBean();

			    loanPayOff.setCompanyId(loanPayOffBean.getCompanyId());
			    loanPayOff.setBranchId(loanPayOffBean.getBranchId());

        		loanPayOff.setId(loanPayOffBean.getId());

        	    loanPayOff.setCompanyId(loanPayOffBean.getCompanyId());
        	    loanPayOff.setBranchId(loanPayOffBean.getBranchId());
        	    loanPayOff.setLoanCaseId(loanPayOffBean.getLoanCaseId());
        	    loanPayOff.setLoanId(loanPayOffBean.getLoanId());
        	    loanPayOff.setProcessor(loanPayOffBean.getProcessor());
        	    loanPayOff.setRepayDate(loanPayOffBean.getRepayDate());
        	    loanPayOff.setRepayTotInt(loanPayOffBean.getRepayTotInt());
        	    loanPayOff.setRepayTotPrl(loanPayOffBean.getRepayTotPrl());
        	    loanPayOff.setRepayTotAmt(loanPayOffBean.getRepayTotAmt());
        	    loanPayOff.setRepayPenalty(loanPayOffBean.getRepayPenalty());
			    loanPayOff.setCreatedBy(loanPayOffBean.getCreatedBy());
			    loanPayOff.setCreationDate(loanPayOffBean.getCreationDate());
			    loanPayOff.setLastModifiedBy(loanPayOffBean.getLastModifiedBy());
			    loanPayOff.setLastModificationDate(loanPayOffBean.getLastModificationDate());

			    beans.add(loanPayOff);
		   }
	    }

        return beans;
 }

 private LoanPayOffBean prepareLoanPayOffBean(LoanPayOff loanPayOff){
		  LoanPayOffBean 	bean = new LoanPayOffBean();

		  bean.setId(loanPayOff.getId());
		  bean.setCompanyId(loanPayOff.getCompanyId());
		  bean.setBranchId(loanPayOff.getBranchId());
		  bean.setLoanCaseId(loanPayOff.getLoanCaseId());
		  bean.setLoanId(loanPayOff.getLoanId());
		  bean.setProcessor(loanPayOff.getProcessor());
		  bean.setRepayDate(loanPayOff.getRepayDate());
		  bean.setRepayTotInt(loanPayOff.getRepayTotInt());
		  bean.setRepayTotPrl(loanPayOff.getRepayTotPrl());
		  bean.setRepayPenalty(loanPayOff.getRepayPenalty());
		  bean.setRepayTotAmt(loanPayOff.getRepayTotAmt());
		  bean.setCreatedBy(loanPayOff.getCreatedBy());
		  bean.setCreationDate(loanPayOff.getCreationDate());
		  bean.setLastModifiedBy(loanPayOff.getLastModifiedBy());
		  bean.setLastModificationDate(loanPayOff.getLastModificationDate());

		  return bean;
 }
}