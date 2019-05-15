package com.sift.loan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.sift.loan.model.LoanDisbursement;
import com.sift.loan.service.LoanDisbursementService;
import com.sift.loan.bean.LoanDisbursementBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanDisbursementController{

 @Autowired
 private LoanDisbursementService loanDisbursementService;

 @RequestMapping(value = "/saveLoanDisburse", method = RequestMethod.POST)
 public ModelAndView saveLoanDisburse(@ModelAttribute("loanDisburse")LoanDisbursementBean loanDisbursementBean,BindingResult result){
	 LoanDisbursement loanDisbursement = prepareModel(loanDisbursementBean);
	 loanDisbursementService.addLoanDisbursement(loanDisbursement);

	 return new ModelAndView("redirect:/newLoanDisburse.htm");
 }

 @RequestMapping(value="/loanDisbursements", method = RequestMethod.GET)
 public ModelAndView listLoanDisbursements() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanDisbursements",  prepareListofBean(loanDisbursementService.listLoanDisbursements()));
	 return new ModelAndView("loanDisbursementsList", model);
 }

 @RequestMapping(value = "/newLoanDisburse", method = RequestMethod.GET)
 public ModelAndView addLoanDisbursement(@ModelAttribute("loanDisburse")LoanDisbursementBean loanDisbursementBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanDisbursements",  prepareListofBean(loanDisbursementService.listLoanDisbursements()));
     return new ModelAndView("addLoanDisburse", model);
 }

 @RequestMapping(value = "/deleteLoanDisburse", method = RequestMethod.GET)
 public ModelAndView deleteLoanDisbursement(@ModelAttribute("loanDisburse")LoanDisbursementBean loanDisbursementBean,BindingResult result) {
	    loanDisbursementService.deleteLoanDisbursement(prepareModel(loanDisbursementBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanDisbursement", null);
		model.put("loanDisbursements",  prepareListofBean(loanDisbursementService.listLoanDisbursements()));
		return new ModelAndView("addLoanDisburse", model);
 }

 @RequestMapping(value = "/editLoanDisburse", method = RequestMethod.GET)
 public ModelAndView editLoanDisbursement(@ModelAttribute("loanDisburse")LoanDisbursementBean loanDisbursementBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanDisbursement", prepareLoanDisbursementBean(loanDisbursementService.getLoanDisbursement(loanDisbursementBean.getId())));
		model.put("loanDisbursements",  prepareListofBean(loanDisbursementService.listLoanDisbursements()));
		return new ModelAndView("editLoanDisburse", model);
 }

 @InitBinder
 public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
 }

 private LoanDisbursement prepareModel(LoanDisbursementBean loanDisbursementBean){
	    LoanDisbursement loanDisbursement = new LoanDisbursement();

	    loanDisbursement.setCompanyId(loanDisbursementBean.getCompanyId());
	    loanDisbursement.setBranchId(loanDisbursementBean.getBranchId());
	    loanDisbursement.setDisburseAmt(loanDisbursementBean.getDisburseAmt());
	    loanDisbursement.setDisburseDate(loanDisbursementBean.getDisburseDate());
	    loanDisbursement.setDisburseBy(loanDisbursementBean.getDisburseBy());
	    loanDisbursement.setCreatedBy(loanDisbursementBean.getCreatedBy());
	    loanDisbursement.setCreationDate(loanDisbursementBean.getCreationDate());
	    loanDisbursement.setLastModifiedBy(loanDisbursementBean.getLastModifiedBy());
	    loanDisbursement.setLastModificationDate(loanDisbursementBean.getLastModificationDate());
	    loanDisbursement.setId(loanDisbursementBean.getId());
	    //loanDisbursementBean.setId(null);

	    return loanDisbursement;
 }

 private List<LoanDisbursementBean> prepareListofBean(List<LoanDisbursement> loanDisbursements){
        List<LoanDisbursementBean> beans = null;

        if(loanDisbursements != null && !loanDisbursements.isEmpty()){
        	beans = new ArrayList<LoanDisbursementBean>();
        	LoanDisbursementBean loanDisbursement = null;

        	for(LoanDisbursement loanDisbursementBean : loanDisbursements){
        		loanDisbursement = new LoanDisbursementBean();

        		loanDisbursement.setId(loanDisbursementBean.getId());
			    loanDisbursement.setCompanyId(loanDisbursementBean.getCompanyId());
			    loanDisbursement.setBranchId(loanDisbursementBean.getBranchId());
			    loanDisbursement.setDisburseAmt(loanDisbursementBean.getDisburseAmt());
			    loanDisbursement.setDisburseDate(loanDisbursementBean.getDisburseDate());
			    loanDisbursement.setDisburseBy(loanDisbursementBean.getDisburseBy());
			    loanDisbursement.setCreatedBy(loanDisbursementBean.getCreatedBy());
			    loanDisbursement.setCreationDate(loanDisbursementBean.getCreationDate());
			    loanDisbursement.setLastModifiedBy(loanDisbursementBean.getLastModifiedBy());
			    loanDisbursement.setLastModificationDate(loanDisbursementBean.getLastModificationDate());

			    beans.add(loanDisbursement);
		   }
	    }

        return beans;
 }

 private LoanDisbursementBean prepareLoanDisbursementBean(LoanDisbursement loanDisbursement){
		  LoanDisbursementBean 	bean = new LoanDisbursementBean();

		  bean.setId(loanDisbursement.getId());
		  bean.setCompanyId(loanDisbursement.getCompanyId());
		  bean.setBranchId(loanDisbursement.getBranchId());
		  bean.setDisburseAmt(loanDisbursement.getDisburseAmt());
		  bean.setDisburseDate(loanDisbursement.getDisburseDate());
		  bean.setDisburseBy(loanDisbursement.getDisburseBy());
		  bean.setCreatedBy(loanDisbursement.getCreatedBy());
		  bean.setCreationDate(loanDisbursement.getCreationDate());
		  bean.setLastModifiedBy(loanDisbursement.getLastModifiedBy());
		  bean.setLastModificationDate(loanDisbursement.getLastModificationDate());

		  return bean;
 }
}