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

import com.sift.admin.model.Tax;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.TaxGroupService;
import com.sift.admin.service.TaxService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.TaxBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class TaxController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private HelperUtility helperUTIL;

@Autowired
private TaxGroupService taxGroupService;

@Autowired
private UserService userService;

@Autowired
private TaxService taxService;
BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveTax", method = RequestMethod.POST)
public ModelAndView saveTax(@ModelAttribute("tax")TaxBean taxBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("taxs",  prepareListofBean(taxService.listTax()));
	  	 model.put("tax",  taxBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addTax":"editTax";
	     return new ModelAndView(URI, model);
	 }
	 
	 System.out.println("LastModificationDate=:"+taxBean.getLastModificationDate());
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 
	 if("2".equals(actionId)){
		 taxBean.setCreatedBy(taxService.getTax(taxBean.getId()).getCreatedBy());
		 taxBean.setLastModifiedBy(req.getRemoteUser());
		 taxBean.setLastModificationDate(new java.util.Date());
	 }

	 Tax tax = prepareModel(taxBean);
	 taxService.addTax(tax);

	 //return new ModelAndView("redirect:/newTax.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=Tax Record Setup/Update was successful&redirectURI=newTax.htm");
 }

 @RequestMapping(value="/taxs", method = RequestMethod.GET)
 public ModelAndView listTaxs() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("taxs",  prepareListofBean(taxService.listTax()));
	 return new ModelAndView("taxList", model);
 }

 @RequestMapping(value = "/newTax", method = RequestMethod.GET)
 public ModelAndView addTax(@ModelAttribute("tax")TaxBean taxBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     taxBean.setCompanyId(Integer.parseInt(currentUser.getCompanyId()));
     taxBean.setBranchId(Integer.parseInt(currentUser.getBranchId()));
     
     model.put("tax",taxBean);
     model.put("taxs",  prepareListofBean(taxService.listTaxByBranch(Integer.parseInt(currentUser.getBranchId()))));
     model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     model.put("branches", beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 model.put("taxgroups", beanMapper.prepareListofTaxGroupBean(taxGroupService.listTaxGroupsByBranchId(Integer.parseInt(currentUser.getBranchId()))));
	 
     return new ModelAndView("addTax", model);
 }


@RequestMapping(value = "/deleteTax", method = RequestMethod.GET)
public ModelAndView deleteTax(@ModelAttribute("tax")TaxBean taxBean,BindingResult result,HttpServletRequest req) {
	    taxService.deleteTax(prepareModel(taxBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("tax", null);
		model.put("taxs",  prepareListofBean(taxService.listTax()));
		return new ModelAndView("addTax", model);
 }

@RequestMapping(value = "/editTax", method = RequestMethod.GET)
public ModelAndView editTax(@ModelAttribute("tax")TaxBean taxBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
	     
		TaxBean obj= prepareTaxBean(taxService.getTax(taxBean.getId()));
		model.put("tax", obj);
		//model.put("taxs",  prepareListofBean(taxService.listTax()));		
	    model.put("taxs",  prepareListofBean(taxService.listTaxByBranch(Integer.parseInt(currentUser.getBranchId()))));
	    model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId().toString())));
	  	model.put("countries",   beanMapper.prepareListofCountryBean(countryService.listCountry()));
		model.put("taxgroups",   beanMapper.prepareListofTaxGroupBean(taxGroupService.listTaxGroupsByBranchId(obj.getBranchId())));
	    model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));

	    return new ModelAndView("editTax", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Tax prepareModel(TaxBean taxBean){
	    Tax tax = new Tax();

	    tax.setId(taxBean.getId());
	    tax.setCompanyId(taxBean.getCompanyId());
	    tax.setBranchId(taxBean.getBranchId());
	    tax.setTaxGroupId(taxBean.getTaxGroupId());
	    tax.setCountryId(taxBean.getCountryId());
	    tax.setLocationDependent(taxBean.getLocationDependent());
	    tax.setActive(taxBean.getActive());
	    tax.setDeleted(taxBean.getDeleted());
	    tax.setTaxName(taxBean.getTaxName());
	    tax.setTaxCode(taxBean.getTaxCode());
	    tax.setTaxDescription(taxBean.getTaxDescription());
	    tax.setRate(taxBean.getRate());
	    tax.setCreatedBy(taxBean.getCreatedBy());
	    tax.setCreationDate(taxBean.getCreationDate());
	    tax.setLastModifiedBy(taxBean.getLastModifiedBy());
	    tax.setLastModificationDate(taxBean.getLastModificationDate());
	    tax.setId(taxBean.getId());

	    return tax;
 }

 private List<TaxBean> prepareListofBean(List<Tax> taxs){
        List<TaxBean> beans = null;

        if(taxs != null && !taxs.isEmpty()){
        	beans = new ArrayList<TaxBean>();
        	TaxBean tax = null;

        	for(Tax item : taxs){
        		tax = new TaxBean();

        		tax.setId(item.getId());
        	    tax.setCompanyId(item.getCompanyId());
        	    tax.setBranchId(item.getBranchId());
        	    tax.setTaxGroupId(item.getTaxGroupId());
        	    tax.setCountryId(item.getCountryId());
        	    tax.setLocationDependent(item.getLocationDependent());
			    tax.setActive(item.getActive());
			    tax.setDeleted(item.getDeleted());
			    tax.setTaxName(item.getTaxName());
			    tax.setTaxCode(item.getTaxCode());
				tax.setTaxDescription(item.getTaxDescription());
			    tax.setRate(item.getRate());
			    tax.setCreatedBy(item.getCreatedBy());
			    tax.setCreationDate(item.getCreationDate());
			    tax.setLastModifiedBy(item.getLastModifiedBy());
			    tax.setLastModificationDate(item.getLastModificationDate());

			    beans.add(tax);
		   }
	    }

        return beans;
 }

 private TaxBean prepareTaxBean(Tax tax){
		  TaxBean 	bean = new TaxBean();

		  bean.setId(tax.getId());
		  bean.setCompanyId(tax.getCompanyId());
		  bean.setBranchId(tax.getBranchId());
		  bean.setTaxGroupId(tax.getTaxGroupId());
		  bean.setCountryId(tax.getCountryId());
		  bean.setLocationDependent(tax.getLocationDependent());
		  bean.setActive(tax.getActive());
		  bean.setDeleted(tax.getDeleted());
		  bean.setTaxName(tax.getTaxName());
		  bean.setTaxCode(tax.getTaxCode());
		  bean.setTaxDescription(tax.getTaxDescription());
		  bean.setRate(tax.getRate());
	      bean.setCreatedBy(tax.getCreatedBy());
		  bean.setCreationDate(tax.getCreationDate());
		  bean.setLastModifiedBy(tax.getLastModifiedBy());
		  bean.setLastModificationDate(tax.getLastModificationDate());

		  return bean;
 }

 public TaxService getTaxService(){
	return taxService;
 }

public void setTaxService(TaxService taxService){
	this.taxService = taxService;
}
}