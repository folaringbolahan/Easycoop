package com.sift.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.sift.admin.model.FiscalPeriodItem;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.FiscalPeriodItemService;
import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.loan.utility.BeanMapperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class FiscalPeriodItemController{

 @Autowired
 private FiscalPeriodItemService fiscalPeriodItemService;

 @Autowired
 private CompanyService companyService;

 @Autowired
 private BranchService branchService;
 
 BeanMapperUtility   beanMapper =new BeanMapperUtility();
 
@RequestMapping(value = "/saveFiscalPItem", method = RequestMethod.POST)
public ModelAndView saveFiscalPeriodItem(@Valid @ModelAttribute("fiscalP")FiscalPeriodItemBean fiscalPeriodItemBean,BindingResult result,HttpServletRequest req) {
 
	 FiscalPeriodItem fiscalPeriodItem = prepareModel(fiscalPeriodItemBean);
	 fiscalPeriodItemService.addFiscalPeriodItem(fiscalPeriodItem);

	 return new ModelAndView("redirect:/newFiscalP.htm");
 }

 @RequestMapping(value="/fiscalPeriodItems", method = RequestMethod.GET)
 public ModelAndView listFiscalPeriodItems() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("fiscalPeriodItems",  prepareListofBean(fiscalPeriodItemService.listFiscalPeriodItems()));
	 return new ModelAndView("fiscalPeriodItemsList", model);
 }

 @RequestMapping(value = "/newFiscalPItem", method = RequestMethod.GET)
 public ModelAndView addFiscalPeriodItem(@ModelAttribute("fiscalP")FiscalPeriodItemBean fiscalPeriodItemBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("fiscalPeriodItems",  prepareListofBean(fiscalPeriodItemService.listFiscalPeriodItems()));
  	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));

  	 return new ModelAndView("addFiscalPeriodItem", model);
 }

@RequestMapping(value = "/deleteFiscalPItem", method = RequestMethod.GET)
public ModelAndView deleteFiscalPeriodItem(@ModelAttribute("fiscalP")FiscalPeriodItemBean fiscalPeriodItemBean,BindingResult result){
	    fiscalPeriodItemService.deleteFiscalPeriodItem(prepareModel(fiscalPeriodItemBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("fiscalPeriodItem", null);
		model.put("fiscalPeriodItems",  prepareListofBean(fiscalPeriodItemService.listFiscalPeriodItems()));
		return new ModelAndView("addFiscalPeriodItem", model);
 }

@RequestMapping(value = "/editFiscalPItem", method = RequestMethod.GET)
public ModelAndView editFiscalPeriodItem(@ModelAttribute("fiscalP")FiscalPeriodItemBean fiscalPeriodItemBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		
		FiscalPeriodItemBean obj=prepareFiscalPeriodItemBean(fiscalPeriodItemService.getFiscalPeriodItem(fiscalPeriodItemBean.getId()));
		
		model.put("fiscalP", 		obj);
	  	model.put("fiscalPeriodItemItems",  prepareListofBean(fiscalPeriodItemService.listFiscalPeriodItems()));
	  	model.put("companies",   	beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	  	//model.put("branches",   	beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
	  	
	  	return new ModelAndView("editFiscalPeriodItem", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private FiscalPeriodItem prepareModel(FiscalPeriodItemBean fiscalPeriodItemBean){
	    FiscalPeriodItem fiscalPeriodItem = new FiscalPeriodItem();

	    fiscalPeriodItem.setYear(fiscalPeriodItemBean.getYear());
	    fiscalPeriodItem.setPeriodId(fiscalPeriodItemBean.getPeriodId());
	    fiscalPeriodItem.setFiscalPeriodId(fiscalPeriodItemBean.getFiscalPeriodId());
	    fiscalPeriodItem.setPeriodStart(fiscalPeriodItemBean.getPeriodStart());
	    fiscalPeriodItem.setPeriodEnd(fiscalPeriodItemBean.getPeriodEnd());
	    fiscalPeriodItem.setId(fiscalPeriodItemBean.getId());

	    return fiscalPeriodItem;
 }

 private List<FiscalPeriodItemBean> prepareListofBean(List<FiscalPeriodItem> fiscalPeriodItems){
        List<FiscalPeriodItemBean> beans = null;

        if(fiscalPeriodItems != null && !fiscalPeriodItems.isEmpty()){
        	beans = new ArrayList<FiscalPeriodItemBean>();
        	FiscalPeriodItemBean fiscalPeriodItem = null;

        	for(FiscalPeriodItem fiscalPeriodItemBean : fiscalPeriodItems){
        		fiscalPeriodItem = new FiscalPeriodItemBean();

        	    fiscalPeriodItem.setYear(fiscalPeriodItemBean.getYear());
        	    fiscalPeriodItem.setPeriodId(fiscalPeriodItemBean.getPeriodId());
        	    fiscalPeriodItem.setFiscalPeriodId(fiscalPeriodItemBean.getFiscalPeriodId());
        	    fiscalPeriodItem.setPeriodStart(fiscalPeriodItemBean.getPeriodStart());
        	    fiscalPeriodItem.setPeriodEnd(fiscalPeriodItemBean.getPeriodEnd());
        	    fiscalPeriodItem.setId(fiscalPeriodItemBean.getId());

			    beans.add(fiscalPeriodItem);
		   }
	    }

        return beans;
 }

 private FiscalPeriodItemBean prepareFiscalPeriodItemBean(FiscalPeriodItem fiscalPeriodItem){
		  FiscalPeriodItemBean 	bean = new FiscalPeriodItemBean();

		  bean.setYear(fiscalPeriodItem.getYear());
		  bean.setPeriodId(fiscalPeriodItem.getPeriodId());
		  bean.setFiscalPeriodId(fiscalPeriodItem.getFiscalPeriodId());
		  bean.setPeriodStart(fiscalPeriodItem.getPeriodStart());
		  bean.setPeriodEnd(fiscalPeriodItem.getPeriodEnd());
		  bean.setId(fiscalPeriodItem.getId());

		  return bean;
 }
}