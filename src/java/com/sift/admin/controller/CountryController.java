package com.sift.admin.controller;

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
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import com.sift.admin.model.Country;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.CurrencyService;
import com.sift.admin.bean.CountryBean;
import com.sift.loan.utility.BeanMapperUtility;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class CountryController{

@Autowired
private CountryService countryService;

@Autowired
private CurrencyService currencyService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();

@RequestMapping(value = "/saveCountry", method = RequestMethod.POST)
public ModelAndView saveCountry(@Valid @ModelAttribute("country") CountryBean countryBean,BindingResult result,HttpServletRequest req) {
	 System.out.println("LastModificationDate=:"+countryBean.getLastModificationDate());
	 
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("countrys",  prepareListofBean(countryService.listCountry()));

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addCountry":"editCountry";
	     return new ModelAndView(URI, model);
	 }

	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 countryBean.setCreatedBy(countryService.getCountry(countryBean.getId()).getCreatedBy());
		 countryBean.setLastModifiedBy(req.getRemoteUser());
		 countryBean.setLastModificationDate(new java.util.Date());
	 }

	 Country country = prepareModel(countryBean);
	 countryService.addCountry(country);
	 
	 //return new ModelAndView("redirect:/newCountry.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=Country Info Update was successful&redirectURI=newCountry.htm");
 }

 @RequestMapping(value="/countrys", method = RequestMethod.GET)
 public ModelAndView listCountrys() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("countrys",  prepareListofBean(countryService.listCountry()));
	 return new ModelAndView("countryList", model);
 }

 @RequestMapping(value = "/newCountry", method = RequestMethod.GET)
 public ModelAndView addCountry(@ModelAttribute("country")CountryBean countryBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("countrys",  prepareListofBean(countryService.listCountry()));
  	 model.put("currencies",  beanMapper.prepareListofCurrencyBean(currencyService.listCurrency()));
     return new ModelAndView("addCountry", model);
 }


@RequestMapping(value = "/deleteCountry", method = RequestMethod.GET)
public ModelAndView deleteCountry(@ModelAttribute("country")CountryBean countryBean,BindingResult result) {
	    countryService.deleteCountry(prepareModel(countryBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("country", null);
		model.put("countrys",  prepareListofBean(countryService.listCountry()));
		return new ModelAndView("addCountry", model);
 }

@RequestMapping(value = "/editCountry", method = RequestMethod.GET)
public ModelAndView editCountry(@ModelAttribute("country")CountryBean countryBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("country", prepareCountryBean(countryService.getCountry(countryBean.getId())));
		model.put("countrys",  prepareListofBean(countryService.listCountry()));
	  	model.put("currencies",  beanMapper.prepareListofCurrencyBean(currencyService.listCurrency()));
	  	
		return new ModelAndView("editCountry", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Country prepareModel(CountryBean countryBean){
	    Country country = new Country();

	    country.setActive(countryBean.getActive());
	    country.setDeleted(countryBean.getDeleted());
	    country.setCountryName(countryBean.getCountryName());
	    country.setCountryCode(countryBean.getCountryCode());
	    country.setCurrencyCode(countryBean.getCurrencyCode());
	    country.setCreatedBy(countryBean.getCreatedBy());
	    country.setCreationDate(countryBean.getCreationDate());
	    country.setLastModifiedBy(countryBean.getLastModifiedBy());
	    country.setLastModificationDate(countryBean.getLastModificationDate());
	    country.setId(countryBean.getId());
	    country.setTimeZone(countryBean.getTimeZone());

	    return country;
 }

 private List<CountryBean> prepareListofBean(List<Country> countrys){
        List<CountryBean> beans = null;

        if(countrys != null && !countrys.isEmpty()){
        	beans = new ArrayList<CountryBean>();
        	CountryBean country = null;

        	for(Country item : countrys){
        		country = new CountryBean();

        		country.setId(item.getId());
			    country.setActive(item.getActive());
			    country.setDeleted(item.getDeleted());
			    country.setCountryName(item.getCountryName());
			    country.setCountryCode(item.getCountryCode());
			    country.setCurrencyCode(item.getCurrencyCode());
			    country.setCreatedBy(item.getCreatedBy());
			    country.setCreationDate(item.getCreationDate());
			    country.setLastModifiedBy(item.getLastModifiedBy());
			    country.setLastModificationDate(item.getLastModificationDate());
			    country.setTimeZone(item.getTimeZone());

			    beans.add(country);
		   }
	    }

        return beans;
 }

 private CountryBean prepareCountryBean(Country country){
		  CountryBean 	bean = new CountryBean();

		  bean.setId(country.getId());
		  bean.setActive(country.getActive());
		  bean.setDeleted(country.getDeleted());
		  bean.setCountryName(country.getCountryName());
		  bean.setCountryCode(country.getCountryCode());
		  bean.setCurrencyCode(country.getCurrencyCode());
	      bean.setCreatedBy(country.getCreatedBy());
		  bean.setCreationDate(country.getCreationDate());
		  bean.setLastModifiedBy(country.getLastModifiedBy());
		  bean.setLastModificationDate(country.getLastModificationDate());
		  bean.setTimeZone(country.getTimeZone());

		  return bean;
 }

public CountryService getCountryService(){
	return countryService;
}

public void setCountryService(CountryService countryService){
	this.countryService = countryService;
}
}