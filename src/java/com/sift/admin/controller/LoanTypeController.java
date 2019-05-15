package com.sift.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.LoanType;
import com.sift.admin.service.LoanTypeService;
import com.sift.admin.bean.LoanTypeBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanTypeController{

 @Autowired
 private LoanTypeService loanTypeService;

@RequestMapping(value = "/saveLoanType", method = RequestMethod.POST)
public ModelAndView saveLoanType(@ModelAttribute("loanT")LoanTypeBean loanTypeBean,BindingResult result,HttpServletRequest req) {
	 LoanType loanType = prepareModel(loanTypeBean);
	 loanTypeService.addLoanType(loanType);

	 return new ModelAndView("redirect:/newLoanType.htm");
 }

 @RequestMapping(value="/loanTypes", method = RequestMethod.GET)
 public ModelAndView listLoanTypes() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanTypes",  prepareListofBean(loanTypeService.listLoanTypes()));
	 return new ModelAndView("loanTypesList", model);
 }

 @RequestMapping(value = "/newLoanType", method = RequestMethod.GET)
 public ModelAndView addLoanType(@ModelAttribute("loanT")LoanTypeBean loanTypeBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanTypes",  prepareListofBean(loanTypeService.listLoanTypes()));
     return new ModelAndView("addLoanType", model);
 }


@RequestMapping(value = "/deleteLoanType", method = RequestMethod.GET)
public ModelAndView deleteLoanType(@ModelAttribute("loanT")LoanTypeBean loanTypeBean,BindingResult result) {
	    loanTypeService.deleteLoanType(prepareModel(loanTypeBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanType", null);
		model.put("loanTypes",  prepareListofBean(loanTypeService.listLoanTypes()));
		return new ModelAndView("addLoanType", model);
 }

@RequestMapping(value = "/editLoanType", method = RequestMethod.GET)
public ModelAndView editLoanType(@ModelAttribute("loanT")LoanTypeBean loanTypeBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanType", prepareLoanTypeBean(loanTypeService.getLoanType(loanTypeBean.getId())));
		model.put("loanTypes",  prepareListofBean(loanTypeService.listLoanTypes()));
		return new ModelAndView("editLoanType", model);
 }

 private LoanType prepareModel(LoanTypeBean loanTypeBean){
	    LoanType loanType = new LoanType();

	    loanType.setId(loanTypeBean.getId());
	    loanType.setActive(loanTypeBean.getActive());
	    loanType.setDeleted(loanTypeBean.getDeleted());
	    loanType.setTypeName(loanTypeBean.getTypeName());
	    loanType.setCreatedBy(loanTypeBean.getCreatedBy());
	    loanType.setCreationDate(loanTypeBean.getCreationDate());
	    loanType.setLastModifiedBy(loanTypeBean.getLastModifiedBy());
	    loanType.setLastModificationDate(loanTypeBean.getLastModificationDate());

	    loanTypeBean.setId(null);

	    return loanType;
 }

 private List<LoanTypeBean> prepareListofBean(List<LoanType> loanTypes){
        List<LoanTypeBean> beans = null;

        if(loanTypes != null && !loanTypes.isEmpty()){
        	beans = new ArrayList<LoanTypeBean>();
        	LoanTypeBean bean = null;

        	for(LoanType loanType : loanTypes){
			    bean = new LoanTypeBean();

			    //bean.setCompanyCode(loanType.getCompanyCode());
			    bean.setId(loanType.getId());
			    bean.setActive(loanType.getActive());
			    bean.setDeleted(loanType.getDeleted());
			    bean.setTypeName(loanType.getTypeName());
			    bean.setCreatedBy(loanType.getCreatedBy());
			    bean.setCreationDate(loanType.getCreationDate());
			    bean.setLastModifiedBy(loanType.getLastModifiedBy());
			    bean.setLastModificationDate(loanType.getLastModificationDate());

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private LoanTypeBean prepareLoanTypeBean(LoanType loanType){
		  LoanTypeBean 	bean = new LoanTypeBean();

		  //bean.setCompanyCode(loanType.getCompanyCode());
		  bean.setId(loanType.getId());
		  bean.setActive(loanType.getActive());
		  bean.setDeleted(loanType.getDeleted());
		  bean.setTypeName(loanType.getTypeName());
		  bean.setCreatedBy(loanType.getCreatedBy());
		  bean.setCreationDate(loanType.getCreationDate());
		  bean.setLastModifiedBy(loanType.getLastModifiedBy());
		  bean.setLastModificationDate(loanType.getLastModificationDate());

		  return bean;
 }
}