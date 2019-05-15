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

import com.sift.admin.model.Currency;
import com.sift.admin.service.CurrencyService;
import com.sift.admin.bean.CurrencyBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class CurrencyController{

 @Autowired
 private CurrencyService currencyService;

@RequestMapping(value = "/saveCurrency", method = RequestMethod.POST)
public ModelAndView saveCurrency(@ModelAttribute("currency")CurrencyBean currencyBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("currencys",  prepareListofBean(currencyService.listCurrency()));

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addCurrency":"editCurrency";
	     return new ModelAndView(URI, model);
	 }
	 
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 currencyBean.setCreatedBy(currencyService.getCurrency(currencyBean.getId()).getCreatedBy());
		 currencyBean.setLastModifiedBy(req.getRemoteUser());
		 currencyBean.setLastModificationDate(new java.util.Date());
	 }

	 Currency currency = prepareModel(currencyBean);	 
	 currencyService.addCurrency(currency);

	 //return new ModelAndView("redirect:/newCurrency.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=Currency Info Update was successful&redirectURI=newCurrency.htm");
 }

 @RequestMapping(value="/currencys", method = RequestMethod.GET)
 public ModelAndView listCurrencys() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("currencys",  prepareListofBean(currencyService.listCurrency()));
	 return new ModelAndView("currencysList", model);
 }

 @RequestMapping(value = "/newCurrency", method = RequestMethod.GET)
 public ModelAndView addCurrency(@ModelAttribute("currency")CurrencyBean currencyBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("currencys",  prepareListofBean(currencyService.listCurrency()));
     return new ModelAndView("addCurrency", model);
 }

@RequestMapping(value = "/deleteCurrency", method = RequestMethod.GET)
public ModelAndView deleteCurrency(@ModelAttribute("currency")CurrencyBean currencyBean,BindingResult result) {
	    currencyService.deleteCurrency(prepareModel(currencyBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("currency", null);
		model.put("currencys",  prepareListofBean(currencyService.listCurrency()));
		return new ModelAndView("addCurrency", model);
 }

@RequestMapping(value = "/editCurrency", method = RequestMethod.GET)
public ModelAndView editCurrency(@ModelAttribute("currency")CurrencyBean currencyBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("currency", prepareCurrencyBean(currencyService.getCurrency(currencyBean.getId())));
		model.put("currencys",  prepareListofBean(currencyService.listCurrency()));
		return new ModelAndView("editCurrency", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Currency prepareModel(CurrencyBean currencyBean){
	    Currency currency = new Currency();

	    currency.setActive(currencyBean.getActive());
	    currency.setDeleted(currencyBean.getDeleted());
	    currency.setIsBase(currencyBean.getIsBase());
	    currency.setCurrencyCode(currencyBean.getCurrencyCode());
	    currency.setCurrencyName(currencyBean.getCurrencyName());
	    currency.setCreatedBy(currencyBean.getCreatedBy());
	    currency.setCreationDate(currencyBean.getCreationDate());
	    currency.setLastModifiedBy(currencyBean.getLastModifiedBy());
	    currency.setLastModificationDate(currencyBean.getLastModificationDate());
	    currency.setId(currencyBean.getId());

	    return currency;
 }

 private List<CurrencyBean> prepareListofBean(List<Currency> currencys){
        List<CurrencyBean> beans = null;

        if(currencys != null && !currencys.isEmpty()){
        	beans = new ArrayList<CurrencyBean>();
        	CurrencyBean currency = null;

        	for(Currency currencyBean : currencys){
        		currency = new CurrencyBean();

        		currency.setId(currencyBean.getId());
			    currency.setActive(currencyBean.getActive());
			    currency.setDeleted(currencyBean.getDeleted());
			    currency.setCurrencyCode(currencyBean.getCurrencyCode());
			    currency.setCurrencyName(currencyBean.getCurrencyName());
			    currency.setIsBase(currencyBean.getIsBase());
				currency.setCreatedBy(currencyBean.getCreatedBy());
			    currency.setCreationDate(currencyBean.getCreationDate());
			    currency.setLastModifiedBy(currencyBean.getLastModifiedBy());
			    currency.setLastModificationDate(currencyBean.getLastModificationDate());

			    beans.add(currency);
		   }
	    }

        return beans;
 }

 private CurrencyBean prepareCurrencyBean(Currency currency){
		  CurrencyBean 	bean = new CurrencyBean();

		  bean.setId(currency.getId());
		  bean.setActive(currency.getActive());
		  bean.setDeleted(currency.getDeleted());
		  bean.setCurrencyCode(currency.getCurrencyCode());
		  bean.setCurrencyName(currency.getCurrencyName());
		  bean.setIsBase(currency.getIsBase());
		  bean.setCreatedBy(currency.getCreatedBy());
		  bean.setCreationDate(currency.getCreationDate());
		  bean.setLastModifiedBy(currency.getLastModifiedBy());
		  bean.setLastModificationDate(currency.getLastModificationDate());

		  return bean;
 }
}