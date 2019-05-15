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

import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.loan.service.LoanRequestExceptionService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.model.LoanRequestException;
import com.sift.loan.bean.LoanRequestExceptionBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanRequestExceptionController{

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private HelperUtility helperUTIL;
	
@Autowired
private LoanRequestExceptionService loanRequestExceptionService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();

@RequestMapping(value = "/saveLoanRequestException", method = RequestMethod.POST)
public ModelAndView saveLoanRequestException(@ModelAttribute("loanRequestException")LoanRequestExceptionBean loanRequestExceptionBean,BindingResult result,HttpServletRequest req){
	 LoanRequestException loanRequestException = prepareModel(loanRequestExceptionBean);
	 loanRequestExceptionService.addLoanRequestException(loanRequestException);

	 return new ModelAndView("redirect:/newLoanRequestException.htm");
 }

 @RequestMapping(value="/loanRequestExceptions", method = RequestMethod.GET)
 public ModelAndView listLoanRequestExceptions() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanRequestExceptions",  prepareListofBean(loanRequestExceptionService.listLoanRequestExceptions()));
	 return new ModelAndView("loanRequestExceptionsList", model);
 }

 @RequestMapping(value = "/newLoanRequestException", method = RequestMethod.GET)
 public ModelAndView addLoanRequestException(@ModelAttribute("loanRequestException")LoanRequestExceptionBean loanRequestExceptionBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanRequestExceptions",  prepareListofBean(loanRequestExceptionService.listLoanRequestExceptions()));
  	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
     
  	 return new ModelAndView("addLoanRequestException", model);
 }

 @RequestMapping(value = "/deleteLoanRequestException", method = RequestMethod.GET)
public ModelAndView deleteLoanRequestException(@ModelAttribute("loanRequestException")LoanRequestExceptionBean loanRequestExceptionBean,BindingResult result) {
	    loanRequestExceptionService.deleteLoanRequestException(prepareModel(loanRequestExceptionBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanRequestException", null);
		model.put("loanRequestExceptions",  prepareListofBean(loanRequestExceptionService.listLoanRequestExceptions()));
		return new ModelAndView("addLoanRequestException", model);
 }

@RequestMapping(value = "/editLoanRequestException", method = RequestMethod.GET)
public ModelAndView editLoanRequestException(@ModelAttribute("loanRequestException")LoanRequestExceptionBean loanRequestExceptionBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanRequestException", prepareLoanRequestExceptionBean(loanRequestExceptionService.getLoanRequestException(loanRequestExceptionBean.getId())));
		model.put("loanRequestExceptions",  prepareListofBean(loanRequestExceptionService.listLoanRequestExceptions()));
		return new ModelAndView("editLoanRequestException", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private LoanRequestException prepareModel(LoanRequestExceptionBean loanRequestExceptionBean){
	    LoanRequestException loanRequestException = new LoanRequestException();

	    loanRequestException.setId(loanRequestExceptionBean.getId());
	    loanRequestException.setLoanCaseId(loanRequestExceptionBean.getLoanCaseId());
	    loanRequestException.setExceptionMessage(loanRequestExceptionBean.getExceptionMessage());
	    loanRequestException.setExceptionStatus(loanRequestExceptionBean.getExceptionStatus());
	    loanRequestException.setClosureStatus(loanRequestExceptionBean.getClosureStatus());
	    loanRequestException.setClosureComment(loanRequestExceptionBean.getClosureComment());
	    loanRequestException.setClosedBy(loanRequestExceptionBean.getClosedBy());
	    loanRequestException.setClosureDate(loanRequestExceptionBean.getClosureDate());
	    loanRequestException.setCreatedBy(loanRequestExceptionBean.getCreatedBy());
	    loanRequestException.setCreationDate(loanRequestExceptionBean.getCreationDate());
	    loanRequestException.setLastModifiedBy(loanRequestExceptionBean.getLastModifiedBy());
	    loanRequestException.setLastModificationDate(loanRequestExceptionBean.getLastModificationDate());

	    return loanRequestException;
 }

 private List<LoanRequestExceptionBean> prepareListofBean(List<LoanRequestException> loanRequestExceptions){
        List<LoanRequestExceptionBean> beans = null;

        if(loanRequestExceptions != null && !loanRequestExceptions.isEmpty()){
        	beans = new ArrayList<LoanRequestExceptionBean>();
        	LoanRequestExceptionBean loanRequestExceptionBean = null;

        	for(LoanRequestException loanRequestException : loanRequestExceptions){
        		loanRequestExceptionBean = new LoanRequestExceptionBean();

        		loanRequestExceptionBean.setId(loanRequestException.getId());
        		loanRequestExceptionBean.setLoanCaseId(loanRequestException.getLoanCaseId());
        		loanRequestExceptionBean.setExceptionMessage(loanRequestException.getExceptionMessage());
        		loanRequestExceptionBean.setExceptionStatus(loanRequestException.getExceptionStatus());
        		loanRequestExceptionBean.setClosureStatus(loanRequestException.getClosureStatus());
        		loanRequestExceptionBean.setClosureComment(loanRequestException.getClosureComment());
        		loanRequestExceptionBean.setClosedBy(loanRequestException.getClosedBy());
        	    loanRequestExceptionBean.setClosureDate(loanRequestException.getClosureDate());
        	    loanRequestExceptionBean.setCreatedBy(loanRequestException.getCreatedBy());
        	    loanRequestExceptionBean.setCreationDate(loanRequestException.getCreationDate());
        	    loanRequestExceptionBean.setLastModifiedBy(loanRequestException.getLastModifiedBy());
        	    loanRequestExceptionBean.setLastModificationDate(loanRequestException.getLastModificationDate());

			    beans.add(loanRequestExceptionBean);
		   }
	    }

        return beans;
 }

 private LoanRequestExceptionBean prepareLoanRequestExceptionBean(LoanRequestException loanRequestException){
	    LoanRequestExceptionBean 	loanRequestExceptionBean = new LoanRequestExceptionBean();

  		loanRequestExceptionBean.setId(loanRequestException.getId());
		loanRequestExceptionBean.setLoanCaseId(loanRequestException.getLoanCaseId());
		loanRequestExceptionBean.setExceptionMessage(loanRequestException.getExceptionMessage());
		loanRequestExceptionBean.setExceptionStatus(loanRequestException.getExceptionStatus());
		loanRequestExceptionBean.setClosureStatus(loanRequestException.getClosureStatus());
		loanRequestExceptionBean.setClosureComment(loanRequestException.getClosureComment());
		loanRequestExceptionBean.setClosedBy(loanRequestException.getClosedBy());
	    loanRequestExceptionBean.setClosureDate(loanRequestException.getClosureDate());
	    loanRequestExceptionBean.setCreatedBy(loanRequestException.getCreatedBy());
	    loanRequestExceptionBean.setCreationDate(loanRequestException.getCreationDate());
	    loanRequestExceptionBean.setLastModifiedBy(loanRequestException.getLastModifiedBy());
	    loanRequestExceptionBean.setLastModificationDate(loanRequestException.getLastModificationDate());

	    return loanRequestExceptionBean;
 }
}