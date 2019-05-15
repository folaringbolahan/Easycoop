package com.sift.admin.controller;

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

import com.sift.admin.model.LoanRepayFreq;
import com.sift.admin.service.LoanRepayFreqService;
import com.sift.admin.bean.LoanRepayFreqBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class LoanRepayFrequencyController{

 @Autowired
 private LoanRepayFreqService loanRepayFreqService;

@RequestMapping(value = "/saveLoanRepayFreq", method = RequestMethod.POST)
public ModelAndView saveLoanRepayFreq(@ModelAttribute("loanRepayFreq")LoanRepayFreqBean loanRepayFreqBean,BindingResult result,HttpServletRequest req) {
	 LoanRepayFreq loanRepayFreq = prepareModel(loanRepayFreqBean);
	 loanRepayFreqService.addLoanRepayFreq(loanRepayFreq);

	 return new ModelAndView("redirect:/newLoanRepayFreq.htm");
 }

 @RequestMapping(value="/loanRepayFreqs", method = RequestMethod.GET)
 public ModelAndView listLoanRepayFreqs() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("loanRepayFreqs",  prepareListofBean(loanRepayFreqService.listLoanRepayFreqs()));
	 return new ModelAndView("loanRepayFreqsList", model);
 }

 @RequestMapping(value = "/newLoanRepayFreq", method = RequestMethod.GET)
 public ModelAndView addLoanRepayFreq(@ModelAttribute("loanRepayFreq")LoanRepayFreqBean loanRepayFreqBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("loanRepayFreqs",prepareListofBean(loanRepayFreqService.listLoanRepayFreqs()));
     return new ModelAndView("addLoanRepayFreq", model);
 }

@RequestMapping(value = "/deleteLoanRepayFreq", method = RequestMethod.GET)
public ModelAndView deleteLoanRepayFreq(@ModelAttribute("loanRepayFreq")LoanRepayFreqBean loanRepayFreqBean,BindingResult result) {
	    loanRepayFreqService.deleteLoanRepayFreq(prepareModel(loanRepayFreqBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("loanRepayFreq", null);
		model.put("loanRepayFreqs",  prepareListofBean(loanRepayFreqService.listLoanRepayFreqs()));
		return new ModelAndView("addLoanRepayFreq", model);
 }

@RequestMapping(value = "/editLoanRepayFreq", method = RequestMethod.GET)
public ModelAndView editLoanRepayFreq(@ModelAttribute("loanRepayFreq")LoanRepayFreqBean loanRepayFreqBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("loanRepayFreq", prepareLoanRepayFreqBean(loanRepayFreqService.getLoanRepayFreq(loanRepayFreqBean.getId())));
		model.put("loanRepayFreqs",  prepareListofBean(loanRepayFreqService.listLoanRepayFreqs()));
		return new ModelAndView("editLoanRepayFreq", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private LoanRepayFreq prepareModel(LoanRepayFreqBean loanRepayFreqBean){
	    LoanRepayFreq loanRepayFreq = new LoanRepayFreq();

	    loanRepayFreq.setYearDivisor(loanRepayFreqBean.getYearDivisor());
	    loanRepayFreq.setActive(loanRepayFreqBean.getActive());
	    loanRepayFreq.setDeleted(loanRepayFreqBean.getDeleted());
	    loanRepayFreq.setName(loanRepayFreqBean.getName());
	    loanRepayFreq.setCode(loanRepayFreqBean.getCode());
	    loanRepayFreq.setCreatedBy(loanRepayFreqBean.getCreatedBy());
	    loanRepayFreq.setCreationDate(loanRepayFreqBean.getCreationDate());
	    loanRepayFreq.setLastModifiedBy(loanRepayFreqBean.getLastModifiedBy());
	    loanRepayFreq.setLastModificationDate(loanRepayFreqBean.getLastModificationDate());
	    loanRepayFreq.setId(loanRepayFreqBean.getId());

	    //loanRepayFreqBean.setId(null);
	    return loanRepayFreq;
 }

 private List<LoanRepayFreqBean> prepareListofBean(List<LoanRepayFreq> loanRepayFreqs){
        List<LoanRepayFreqBean> beans = null;

        if(loanRepayFreqs != null && !loanRepayFreqs.isEmpty()){
        	beans = new ArrayList<LoanRepayFreqBean>();
        	LoanRepayFreqBean loanRepayFreqBean = null;

        	for(LoanRepayFreq item: loanRepayFreqs){
        		loanRepayFreqBean = new LoanRepayFreqBean();

        		loanRepayFreqBean.setId(item.getId());
        		loanRepayFreqBean.setYearDivisor(item.getYearDivisor());
        		loanRepayFreqBean.setActive(item.getActive());
        		loanRepayFreqBean.setDeleted(item.getDeleted());
        		loanRepayFreqBean.setName(item.getName());
        	    loanRepayFreqBean.setCode(item.getCode());

        		loanRepayFreqBean.setCreatedBy(item.getCreatedBy());
        		loanRepayFreqBean.setCreationDate(item.getCreationDate());
        		loanRepayFreqBean.setLastModifiedBy(item.getLastModifiedBy());
        		loanRepayFreqBean.setLastModificationDate(item.getLastModificationDate());

			    beans.add(loanRepayFreqBean);
		   }
	    }

        return beans;
 }

 private LoanRepayFreqBean prepareLoanRepayFreqBean(LoanRepayFreq loanRepayFreq){
		  LoanRepayFreqBean bean = new LoanRepayFreqBean();

		  bean.setId(loanRepayFreq.getId());
		  bean.setYearDivisor(loanRepayFreq.getYearDivisor());
		  bean.setActive(loanRepayFreq.getActive());
		  bean.setDeleted(loanRepayFreq.getDeleted());
		  bean.setName(loanRepayFreq.getName());
		  bean.setCode(loanRepayFreq.getCode());
		  bean.setCreatedBy(loanRepayFreq.getCreatedBy());
		  bean.setCreationDate(loanRepayFreq.getCreationDate());
		  bean.setLastModifiedBy(loanRepayFreq.getLastModifiedBy());
		  bean.setLastModificationDate(loanRepayFreq.getLastModificationDate());

		  return bean;
 }
}