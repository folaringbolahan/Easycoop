package com.sift.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.User;
import com.sift.admin.model.TaxGroup;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.TaxGroupService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.TaxGroupBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class TaxGroupController{

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private UserService userService;

@Autowired
private TaxGroupService taxGroupService;
BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveTaxGroup", method = RequestMethod.POST)
public ModelAndView saveTaxGroup(@Valid @ModelAttribute("taxGroup")TaxGroupBean taxGroupBean,BindingResult result,HttpServletRequest req){

	 if(result.hasErrors()){
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("taxGroups",  prepareListofBean(taxGroupService.listTaxGroups()));

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addTaxGroup":"editTaxGroup";
	     return new ModelAndView(URI, model);
	 }
	 
	 TaxGroup taxGroup = prepareModel(taxGroupBean);
	 taxGroupService.addTaxGroup(taxGroup);	 

	 //return new ModelAndView("redirect:/newTaxGroup.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=Tax Group Record Setup/Update was successful&redirectURI=newTaxGroup.htm");
 }

 @RequestMapping(value="/taxGroups", method = RequestMethod.GET)
 public ModelAndView listTaxGroups() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 
	 model.put("taxGroups",  prepareListofBean(taxGroupService.listTaxGroups()));
	 return new ModelAndView("taxGroupsList", model);
 }

 @RequestMapping(value = "/newTaxGroup", method = RequestMethod.GET)
 public ModelAndView addTaxGroup(@ModelAttribute("taxGroup")TaxGroupBean taxGroupBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
    	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     taxGroupBean.setCompanyId(Integer.parseInt(currentUser.getCompanyId()));
     
     model.put("taxGroups",   prepareListofBean(taxGroupService.listTaxGroupsByBranchId(Integer.parseInt(currentUser.getBranchId()))));
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
     model.put("taxGroup", taxGroupBean);
     return new ModelAndView("addTaxGroup", model);
 }

@RequestMapping(value = "/deleteTaxGroup", method = RequestMethod.GET)
public ModelAndView deleteTaxGroup(@ModelAttribute("taxGroup")TaxGroupBean taxGroupBean,BindingResult result) {
	    taxGroupService.deleteTaxGroup(prepareModel(taxGroupBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("taxGroup", null);
		model.put("taxGroups",  prepareListofBean(taxGroupService.listTaxGroups()));
		return new ModelAndView("addTaxGroup", model);
 }

@RequestMapping(value = "/editTaxGroup", method = RequestMethod.GET)
public ModelAndView editTaxGroup(@ModelAttribute("taxGroup")TaxGroupBean taxGroupBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
		TaxGroupBean obj=  prepareTaxGroupBean(taxGroupService.getTaxGroup(taxGroupBean.getId()));
		
		model.put("taxGroup",     obj);
	    model.put("taxGroups",    prepareListofBean(taxGroupService.listTaxGroupsByBranchId(Integer.parseInt(currentUser.getBranchId()))));
	    model.put("companies",    beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId().toString())));
	    model.put("branches",     beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
		return new ModelAndView("editTaxGroup", model);
 }

 private TaxGroup prepareModel(TaxGroupBean taxGroupBean){
	    TaxGroup taxGroup = new TaxGroup();

	    taxGroup.setId(taxGroupBean.getId());
	    taxGroup.setActive(taxGroupBean.getActive());
	    taxGroup.setDeleted(taxGroupBean.getDeleted());
	    taxGroup.setDescription(taxGroupBean.getDescription());
	    taxGroup.setCode(taxGroupBean.getCode());
	    taxGroup.setCompanyId(taxGroupBean.getCompanyId());
	    taxGroup.setBranchId(taxGroupBean.getBranchId());	  
	    /*taxGroup.setCreatedBy(taxGroupBean.getCreatedBy());
	    taxGroup.setCreationDate(taxGroupBean.getCreationDate());
	    taxGroup.setLastModifiedBy(taxGroupBean.getLastModifiedBy());
	    taxGroup.setLastModificationDate(taxGroupBean.getLastModificationDate());*/

	    //taxGroupBean.setId(null);

	    return taxGroup;
 }

 private List<TaxGroupBean> prepareListofBean(List<TaxGroup> taxGroups){
        List<TaxGroupBean> beans = null;

        if(taxGroups != null && !taxGroups.isEmpty()){
        	beans = new ArrayList<TaxGroupBean>();
        	TaxGroupBean taxGroup = null;

        	for(TaxGroup taxGroupBean : taxGroups){
        		taxGroup = new TaxGroupBean();

        		taxGroup.setId(taxGroupBean.getId());
			    taxGroup.setActive(taxGroupBean.getActive());
			    taxGroup.setDeleted(taxGroupBean.getDeleted());
			    taxGroup.setDescription(taxGroupBean.getDescription());
			    taxGroup.setCode(taxGroupBean.getCode());
			    taxGroup.setCompanyId(taxGroupBean.getCompanyId());
			    taxGroup.setBranchId(taxGroupBean.getBranchId());	  
			    /*taxGroup.setCreatedBy(taxGroupBean.getCreatedBy());
			    taxGroup.setCreationDate(taxGroupBean.getCreationDate());
			    taxGroup.setLastModifiedBy(taxGroupBean.getLastModifiedBy());
			    taxGroup.setLastModificationDate(taxGroupBean.getLastModificationDate());*/

			    beans.add(taxGroup);
		   }
	    }

        return beans;
 }

 private TaxGroupBean prepareTaxGroupBean(TaxGroup taxGroup){
		  TaxGroupBean 	bean = new TaxGroupBean();

		  bean.setId(taxGroup.getId());
		  bean.setActive(taxGroup.getActive());
		  bean.setDeleted(taxGroup.getDeleted());
		  bean.setDescription(taxGroup.getDescription());
		  bean.setCode(taxGroup.getCode());
		  bean.setCompanyId(taxGroup.getCompanyId());
		  bean.setBranchId(taxGroup.getBranchId());	  
		  
		  /*bean.setCreatedBy(taxGroup.getCreatedBy());
		  bean.setCreationDate(taxGroup.getCreationDate());
		  bean.setLastModifiedBy(taxGroup.getLastModifiedBy());
		  bean.setLastModificationDate(taxGroup.getLastModificationDate());*/

		  return bean;
 }
}