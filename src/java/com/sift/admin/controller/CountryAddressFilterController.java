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

import com.sift.admin.model.CountryAddressFilter;
import com.sift.admin.service.AddressItemsService;
import com.sift.admin.service.CountryAddressFilterService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.loan.utility.BeanMapperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class CountryAddressFilterController{

 @Autowired
 private CountryAddressFilterService countryAddressFilterService;
 
 @Autowired
 private AddressItemsService addressItemsService;

 @Autowired
 private UserService userService;
 
 @Autowired
 private CountryService countryService;

 BeanMapperUtility   beanMapper =new BeanMapperUtility();

@RequestMapping(value = "/saveCountryAddrFilter", method = RequestMethod.POST)
public ModelAndView saveCountryAddrFilter(@ModelAttribute("countryAdrFilter")CountryAddressFilterBean countryAddressFilterBean,BindingResult result,HttpServletRequest req) {
	 CountryAddressFilter countryAddressFilter = prepareModel(countryAddressFilterBean);
	 countryAddressFilterService.addCountryAddressFilter(countryAddressFilter);

	 return new ModelAndView("redirect:/newCountryAddrFilter.htm");
 }

 @RequestMapping(value="/countryAddressFilters", method = RequestMethod.GET)
 public ModelAndView listCountryAddressFilters() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("countryAddressFilters",  prepareListofBean(countryAddressFilterService.listCountryAddressFilter()));
	 return new ModelAndView("countryAddressFiltersList", model);
 }

 @RequestMapping(value = "/newCountryAddrFilter", method = RequestMethod.GET)
 public ModelAndView addCountryAddressFilter(@ModelAttribute("countryAdrFilter")CountryAddressFilterBean countryAddressFilterBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 //model.put("countryAddressFilters",  prepareListofBean(countryAddressFilterService.listCountryAddressFilter()));
  	 model.put("countryAddressFilters",  countryAddressFilterService.listCountryAddressFilterBean());
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 model.put("addressItems", beanMapper.prepareListofAddressItemsBean(addressItemsService.listAddressItems()));

  	 return new ModelAndView("addCountryAddrFilter", model);
 }

@RequestMapping(value = "/deleteCountryAddrFilter", method = RequestMethod.GET)
public ModelAndView deleteCountryAddressFilter(@ModelAttribute("countryAdrFilter")CountryAddressFilterBean countryAddressFilterBean,BindingResult result) {
	    countryAddressFilterService.deleteCountryAddressFilter(prepareModel(countryAddressFilterBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("countryAdrFilter", null);
		model.put("countryAddressFilters",  prepareListofBean(countryAddressFilterService.listCountryAddressFilter()));
		return new ModelAndView("addCountryAddrFilter", model);
}

@RequestMapping(value = "/editCountryAddrFilter", method = RequestMethod.GET)
public ModelAndView editCountryAddressFilter(@ModelAttribute("countryAdrFilter")CountryAddressFilterBean countryAddressFilterBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("countryAdrFilter", prepareCountryAddressFilterBean(countryAddressFilterService.getCountryAddressFilter(countryAddressFilterBean.getId())));
		//model.put("countryAddressFilters",  prepareListofBean(countryAddressFilterService.listCountryAddressFilter()));
		model.put("countryAddressFilters",  countryAddressFilterService.listCountryAddressFilterBean());
	  	model.put("addressItems", beanMapper.prepareListofAddressItemsBean(addressItemsService.listAddressItems()));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	  	
		return new ModelAndView("editCountryAddrFilter", model);
 }

 private CountryAddressFilter prepareModel(CountryAddressFilterBean countryAddressFilterBean){
	    CountryAddressFilter countryAddressFilter = new CountryAddressFilter();

	    countryAddressFilter.setCountryId(countryAddressFilterBean.getCountryId());
	    countryAddressFilter.setAddrFieldName(countryAddressFilterBean.getAddrFieldName());
	    countryAddressFilter.setAddrFieldIndx(countryAddressFilterBean.getAddrFieldIndx());
	    /*countryAddressFilter.setCreatedBy(countryAddressFilterBean.getCreatedBy());
	    countryAddressFilter.setCreationDate(countryAddressFilterBean.getCreationDate());
	    countryAddressFilter.setLastModifiedBy(countryAddressFilterBean.getLastModifiedBy());
	    countryAddressFilter.setLastModificationDate(countryAddressFilterBean.getLastModificationDate());*/
	    countryAddressFilter.setId(countryAddressFilterBean.getId());
	    //countryAddressFilterBean.setId(null);

	    return countryAddressFilter;
 }

 private List<CountryAddressFilterBean> prepareListofBean(List<CountryAddressFilter> countryAddressFilters){
        List<CountryAddressFilterBean> beans = null;

        if(countryAddressFilters != null && !countryAddressFilters.isEmpty()){
        	beans = new ArrayList<CountryAddressFilterBean>();
        	CountryAddressFilterBean countryAddressFilter = null;

        	for(CountryAddressFilter countryAddressFilterBean : countryAddressFilters){
        		countryAddressFilter = new CountryAddressFilterBean();

        		countryAddressFilter.setId(countryAddressFilterBean.getId());
        	    countryAddressFilter.setCountryId(countryAddressFilterBean.getCountryId());
        	    countryAddressFilter.setAddrFieldName(countryAddressFilterBean.getAddrFieldName());
        	    countryAddressFilter.setAddrFieldIndx(countryAddressFilterBean.getAddrFieldIndx());

			    /*countryAddressFilter.setCreatedBy(countryAddressFilterBean.getCreatedBy());
			    countryAddressFilter.setCreationDate(countryAddressFilterBean.getCreationDate());
			    countryAddressFilter.setLastModifiedBy(countryAddressFilterBean.getLastModifiedBy());
			    countryAddressFilter.setLastModificationDate(countryAddressFilterBean.getLastModificationDate());*/

			    beans.add(countryAddressFilter);
		   }
	    }

        return beans;
 }

 private CountryAddressFilterBean prepareCountryAddressFilterBean(CountryAddressFilter countryAddressFilter){
		  CountryAddressFilterBean 	bean = new CountryAddressFilterBean();

		  bean.setId(countryAddressFilter.getId());
		  bean.setCountryId(countryAddressFilter.getCountryId());
		  bean.setAddrFieldName(countryAddressFilter.getAddrFieldName());
		  bean.setAddrFieldIndx(countryAddressFilter.getAddrFieldIndx());
		  /*bean.setCreatedBy(countryAddressFilter.getCreatedBy());
		  bean.setCreationDate(countryAddressFilter.getCreationDate());
		  bean.setLastModifiedBy(countryAddressFilter.getLastModifiedBy());
		  bean.setLastModificationDate(countryAddressFilter.getLastModificationDate());*/

		  return bean;
 }
}