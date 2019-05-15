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

import com.sift.loan.model.LoanRepayment;
import com.sift.loan.service.LoanRepaymentService;
import com.sift.loan.bean.LoanRepaymentBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanRepaymentController{

 @Autowired
 private LoanRepaymentService loanRepaymentService;

 @RequestMapping(value = "/saveLoanRepay", method = RequestMethod.POST)
 public ModelAndView saveLoanRepay(@ModelAttribute("loanRepay")LoanRepaymentBean loanRepaymentBean,BindingResult result){
	 LoanRepayment loanRepayment = prepareModel(loanRepaymentBean);
	 loanRepaymentService.addLoanRepayment(loanRepayment);

	 return new ModelAndView("redirect:/newLoanRepay.htm");
 }

 @RequestMapping(value="/loanRepayments", method = RequestMethod.GET)
 public ModelAndView listLoanRepayments() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanRepayments",  prepareListofBean(loanRepaymentService.listLoanRepayments()));
	 return new ModelAndView("loanRepaymentsList", model);
 }

 @RequestMapping(value = "/newLoanRepay", method = RequestMethod.GET)
 public ModelAndView addLoanRepayment(@ModelAttribute("loanRepay")LoanRepaymentBean loanRepaymentBean, BindingResult result){
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanRepayments",  prepareListofBean(loanRepaymentService.listLoanRepayments()));
     return new ModelAndView("addLoanRepay", model);
 }

@RequestMapping(value = "/deleteLoanRepay", method = RequestMethod.GET)
public ModelAndView deleteLoanRepayment(@ModelAttribute("loanRepay")LoanRepaymentBean loanRepaymentBean,BindingResult result) {
	    loanRepaymentService.deleteLoanRepayment(prepareModel(loanRepaymentBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanRepayment", null);
		model.put("loanRepayments",  prepareListofBean(loanRepaymentService.listLoanRepayments()));
		return new ModelAndView("addLoanRepay", model);
 }

@RequestMapping(value = "/editLoanRepay", method = RequestMethod.GET)
public ModelAndView editLoanRepayment(@ModelAttribute("loanRepay")LoanRepaymentBean loanRepaymentBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanRepayment", prepareLoanRepaymentBean(loanRepaymentService.getLoanRepayment(loanRepaymentBean.getId())));
		model.put("loanRepayments",  prepareListofBean(loanRepaymentService.listLoanRepayments()));
		return new ModelAndView("editLoanRepay", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
}

 private LoanRepayment prepareModel(LoanRepaymentBean loanRepaymentBean){
	    LoanRepayment loanRepayment = new LoanRepayment();

	    loanRepayment.setCompanyId(loanRepaymentBean.getCompanyId());
	    loanRepayment.setBranchId(loanRepaymentBean.getBranchId());
	    loanRepayment.setLoanid(loanRepaymentBean.getLoanid());
	    loanRepayment.setProcessor(loanRepaymentBean.getProcessor());
	    loanRepayment.setRepayDate(loanRepaymentBean.getRepayDate());
	    loanRepayment.setRepayInt(loanRepaymentBean.getRepayInt());
	    loanRepayment.setRepayPrl(loanRepaymentBean.getRepayPrl());
	    loanRepayment.setRepayTot(loanRepaymentBean.getRepayTot());

	    loanRepayment.setCreatedBy(loanRepaymentBean.getCreatedBy());
	    loanRepayment.setCreationDate(loanRepaymentBean.getCreationDate());
	    loanRepayment.setLastModifiedBy(loanRepaymentBean.getLastModifiedBy());
	    loanRepayment.setLastModificationDate(loanRepaymentBean.getLastModificationDate());
	    loanRepayment.setId(loanRepaymentBean.getId());
	    //loanRepaymentBean.setId(null);

	    return loanRepayment;
 }

 private List<LoanRepaymentBean> prepareListofBean(List<LoanRepayment> loanRepayments){
        List<LoanRepaymentBean> beans = null;

        if(loanRepayments != null && !loanRepayments.isEmpty()){
        	beans = new ArrayList<LoanRepaymentBean>();
        	LoanRepaymentBean loanRepayment = null;

        	for(LoanRepayment loanRepaymentBean : loanRepayments){
        		loanRepayment = new LoanRepaymentBean();

			    loanRepayment.setCompanyId(loanRepaymentBean.getCompanyId());
			    loanRepayment.setBranchId(loanRepaymentBean.getBranchId());

        		loanRepayment.setId(loanRepaymentBean.getId());

        	    loanRepayment.setCompanyId(loanRepaymentBean.getCompanyId());
        	    loanRepayment.setBranchId(loanRepaymentBean.getBranchId());
        	    loanRepayment.setLoanid(loanRepaymentBean.getLoanid());
        	    loanRepayment.setProcessor(loanRepaymentBean.getProcessor());
        	    loanRepayment.setRepayDate(loanRepaymentBean.getRepayDate());
        	    loanRepayment.setRepayInt(loanRepaymentBean.getRepayInt());
        	    loanRepayment.setRepayPrl(loanRepaymentBean.getRepayPrl());
        	    loanRepayment.setRepayTot(loanRepaymentBean.getRepayTot());

			    loanRepayment.setCreatedBy(loanRepaymentBean.getCreatedBy());
			    loanRepayment.setCreationDate(loanRepaymentBean.getCreationDate());
			    loanRepayment.setLastModifiedBy(loanRepaymentBean.getLastModifiedBy());
			    loanRepayment.setLastModificationDate(loanRepaymentBean.getLastModificationDate());

			    beans.add(loanRepayment);
		   }
	    }

        return beans;
 }

 private LoanRepaymentBean prepareLoanRepaymentBean(LoanRepayment loanRepayment){
		  LoanRepaymentBean 	bean = new LoanRepaymentBean();

		  bean.setId(loanRepayment.getId());
		  bean.setCompanyId(loanRepayment.getCompanyId());
		  bean.setBranchId(loanRepayment.getBranchId());
		  bean.setLoanid(loanRepayment.getLoanid());
		  bean.setProcessor(loanRepayment.getProcessor());
		  bean.setRepayDate(loanRepayment.getRepayDate());
		  bean.setRepayInt(loanRepayment.getRepayInt());
		  bean.setRepayPrl(loanRepayment.getRepayPrl());
		  bean.setRepayTot(loanRepayment.getRepayTot());
		  bean.setCreatedBy(loanRepayment.getCreatedBy());
		  bean.setCreationDate(loanRepayment.getCreationDate());
		  bean.setLastModifiedBy(loanRepayment.getLastModifiedBy());
		  bean.setLastModificationDate(loanRepayment.getLastModificationDate());

		  return bean;
 }
}