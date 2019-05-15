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

import com.sift.loan.model.LoanGuarantor;
import com.sift.loan.service.LoanGuarantorService;
import com.sift.loan.bean.LoanGuarantorBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanGuarantorController{

 @Autowired
 private LoanGuarantorService loanGuarantorService;

@RequestMapping(value = "/saveLoanGuarantor", method = RequestMethod.POST)
public ModelAndView saveLoanGuarantor(@ModelAttribute("loanGuarantor")LoanGuarantorBean loanGuarantorBean,BindingResult result) {
	 LoanGuarantor loanGuarantor = prepareModel(loanGuarantorBean);
	 loanGuarantorService.addLoanGuarantor(loanGuarantor);

	 return new ModelAndView("redirect:/newLoanGuarantor.htm");
 }

 @RequestMapping(value="/loanGuarantors", method = RequestMethod.GET)
 public ModelAndView listLoanGuarantors() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanGuarantors",  prepareListofBean(loanGuarantorService.listLoanGuarantors()));
	 return new ModelAndView("loanGuarantorsList", model);
 }

 @RequestMapping(value = "/newLoanGuarantor", method = RequestMethod.GET)
 public ModelAndView addLoanGuarantor(@ModelAttribute("loanGuarantor")LoanGuarantorBean loanGuarantorBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanGuarantors",  prepareListofBean(loanGuarantorService.listLoanGuarantors()));
     return new ModelAndView("addLoanGuarantor", model);
 }

@RequestMapping(value = "/deleteLoanGuarantor", method = RequestMethod.GET)
public ModelAndView deleteLoanGuarantor(@ModelAttribute("loanGuarantor")LoanGuarantorBean loanGuarantorBean,BindingResult result) {
	    loanGuarantorService.deleteLoanGuarantor(prepareModel(loanGuarantorBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanGuarantor", null);
		model.put("loanGuarantors",  prepareListofBean(loanGuarantorService.listLoanGuarantors()));
		return new ModelAndView("addLoanGuarantor", model);
 }

@RequestMapping(value = "/editLoanGuarantor", method = RequestMethod.GET)
public ModelAndView editLoanGuarantor(@ModelAttribute("loanGuarantor")LoanGuarantorBean loanGuarantorBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanGuarantor", prepareLoanGuarantorBean(loanGuarantorService.getLoanGuarantor(loanGuarantorBean.getId())));
		model.put("loanGuarantors",  prepareListofBean(loanGuarantorService.listLoanGuarantors()));
		return new ModelAndView("editLoanGuarantor", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private LoanGuarantor prepareModel(LoanGuarantorBean loanGuarantorBean){
	    LoanGuarantor loanGuarantor = new LoanGuarantor();

	    loanGuarantor.setCompanyId(loanGuarantorBean.getCompanyId());
	    loanGuarantor.setBranchId(loanGuarantorBean.getBranchId());
	    loanGuarantor.setLoanCaseId(loanGuarantorBean.getLoanCaseId());
	    loanGuarantor.setMemberNo(loanGuarantorBean.getMemberNo());
	    loanGuarantor.setGuarantorNo(loanGuarantorBean.getGuarantorNo());
	    loanGuarantor.setGuarantorComment(loanGuarantorBean.getGuarantorComment());
	    loanGuarantor.setAcceptanceStatus(loanGuarantorBean.getAcceptanceStatus());
	    loanGuarantor.setRequestDate(loanGuarantorBean.getRequestDate());
	    loanGuarantor.setAcceptanceDate(loanGuarantorBean.getAcceptanceDate());

	    loanGuarantor.setCreatedBy(loanGuarantorBean.getCreatedBy());
	    loanGuarantor.setCreationDate(loanGuarantorBean.getCreationDate());
	    loanGuarantor.setLastModifiedBy(loanGuarantorBean.getLastModifiedBy());
	    loanGuarantor.setLastModificationDate(loanGuarantorBean.getLastModificationDate());
	    loanGuarantor.setId(loanGuarantorBean.getId());
	    //loanGuarantorBean.setId(null);

	    return loanGuarantor;
 }

 private List<LoanGuarantorBean> prepareListofBean(List<LoanGuarantor> loanGuarantors){
        List<LoanGuarantorBean> beans = null;

        if(loanGuarantors != null && !loanGuarantors.isEmpty()){
        	beans = new ArrayList<LoanGuarantorBean>();
        	LoanGuarantorBean loanGuarantor = null;

        	for(LoanGuarantor loanGuarantorBean : loanGuarantors){
        		loanGuarantor = new LoanGuarantorBean();

			    loanGuarantor.setCompanyId(loanGuarantorBean.getCompanyId());
			    loanGuarantor.setBranchId(loanGuarantorBean.getBranchId());

        		loanGuarantor.setId(loanGuarantorBean.getId());

			    loanGuarantor.setLoanCaseId(loanGuarantorBean.getLoanCaseId());
			    loanGuarantor.setMemberNo(loanGuarantorBean.getMemberNo());
			    loanGuarantor.setGuarantorNo(loanGuarantorBean.getGuarantorNo());
			    loanGuarantor.setGuarantorComment(loanGuarantorBean.getGuarantorComment());
			    loanGuarantor.setAcceptanceStatus(loanGuarantorBean.getAcceptanceStatus());
			    loanGuarantor.setRequestDate(loanGuarantorBean.getRequestDate());
			    loanGuarantor.setAcceptanceDate(loanGuarantorBean.getAcceptanceDate());

			    loanGuarantor.setCreatedBy(loanGuarantorBean.getCreatedBy());
			    loanGuarantor.setCreationDate(loanGuarantorBean.getCreationDate());
			    loanGuarantor.setLastModifiedBy(loanGuarantorBean.getLastModifiedBy());
			    loanGuarantor.setLastModificationDate(loanGuarantorBean.getLastModificationDate());

			    beans.add(loanGuarantor);
		   }
	    }

        return beans;
 }

 private LoanGuarantorBean prepareLoanGuarantorBean(LoanGuarantor loanGuarantor){
		  LoanGuarantorBean 	bean = new LoanGuarantorBean();

		  bean.setId(loanGuarantor.getId());
		  bean.setLoanCaseId(loanGuarantor.getLoanCaseId());
		  bean.setMemberNo(loanGuarantor.getMemberNo());
		  bean.setGuarantorNo(loanGuarantor.getGuarantorNo());
		  bean.setGuarantorComment(loanGuarantor.getGuarantorComment());
		  bean.setAcceptanceStatus(loanGuarantor.getAcceptanceStatus());
		  bean.setRequestDate(loanGuarantor.getRequestDate());
		  bean.setAcceptanceDate(loanGuarantor.getAcceptanceDate());
		  bean.setCreatedBy(loanGuarantor.getCreatedBy());
		  bean.setCreationDate(loanGuarantor.getCreationDate());
		  bean.setLastModifiedBy(loanGuarantor.getLastModifiedBy());
		  bean.setLastModificationDate(loanGuarantor.getLastModificationDate());

		  return bean;
 }
}