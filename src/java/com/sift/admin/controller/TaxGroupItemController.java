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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.model.TaxGroupItem;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ModuleService;
import com.sift.admin.service.ReportsService;
import com.sift.admin.service.TaxGroupService;
import com.sift.admin.service.TaxService;
import com.sift.admin.service.UserGroupService;
import com.sift.admin.service.TaxGroupItemService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.bean.ReportsBean;
import com.sift.admin.bean.TaxBean;
import com.sift.admin.bean.TaxGroupItemBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class TaxGroupItemController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;


@Autowired
private BranchService branchService;

@Autowired
private TaxGroupItemService taxGroupItemService;

@Autowired
private UserGroupService userGroupService;

@Autowired
private ReportsService reportsService;

@Autowired
private TaxService taxService;

@Autowired
private TaxGroupService taxGroupService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveTaxGroupItem", method = RequestMethod.POST)
public ModelAndView saveTaxGroupItem(@ModelAttribute("taxGroupItem")TaxGroupItemBean taxGroupItemBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("taxGroupItems",  prepareListofBean(taxGroupItemService.listTaxGroupItem()));
	  	 model.put("taxGroupItem",  taxGroupItemBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addTaxGroupItem":"editTaxGroupItem";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 String actionId=req.getParameter("ACTION_ID");
	 String selectedTaxIds[]=req.getParameterValues("selectedTaxIds");	 
	 System.out.println("selectedTaxIds.length="+selectedTaxIds==null?"0":selectedTaxIds.length);
	 
	 TaxGroupItem taxGroupItem = prepareModel(taxGroupItemBean);
	 taxGroupItemService.addTaxGroupItem(taxGroupItem,selectedTaxIds);

	 return new ModelAndView("redirect:/doFeedback.htm?message=TaxId Assignment/Re-Assignment was successful&redirectURI=index.htm");
 }

 @RequestMapping(value="/taxGroupItems", method = RequestMethod.GET)
 public ModelAndView listTaxGroupItems() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("taxGroupItems",  prepareListofBean(taxGroupItemService.listTaxGroupItem()));
	 return new ModelAndView("taxGroupItemList", model);
 }
 
 @RequestMapping(value = "/taxgroupitemlist", method = RequestMethod.GET)
 public @ResponseBody Map<String ,Object> taxGroupItemElementsList(@RequestParam(value = "taxGroupId", required = true) String taxGroupId,HttpServletRequest req) {
	Map<String ,Object> iMap = new HashMap<String, Object>();
 	String logonUser=eazyCoopUTIL.getLoggedonUser(req);
    User currentUser=userService.getUserByEmail(logonUser);
     
    List<TaxBean> taxitems=beanMapper.prepareListofTaxBean(taxService.listTaxByBranch(Integer.parseInt(currentUser.getBranchId())));
	
	iMap.put("taxGroupItems",  prepareListofBean(taxGroupItemService.listTaxGroupItem(taxGroupId)));
	iMap.put("taxitems",  taxitems);
	
	return iMap;
 }

 @RequestMapping(value = "/addTaxItemsToGroup", method = RequestMethod.GET)
 public ModelAndView addTaxItemsToGroup(@ModelAttribute("taxGroupItem")TaxGroupItemBean taxGroupItemBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 model.put("taxgroups",   beanMapper.prepareListofTaxGroupBean(taxGroupService.listTaxGroupsByBranchId(Integer.parseInt(currentUser.getBranchId()))));

     model.put("taxGroupItem",taxGroupItemBean);    
     return new ModelAndView("addTaxItemsToGroup", model);
}

@RequestMapping(value = "/deleteTaxItemFromGroup", method = RequestMethod.GET)
public ModelAndView deleteTaxItemFromGroup(@ModelAttribute("taxGroupItem")TaxGroupItemBean taxGroupItemBean,BindingResult result,HttpServletRequest req) {
	    taxGroupItemService.deleteTaxGroupItem(prepareModel(taxGroupItemBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("taxGroupItem", null);
		model.put("taxGroupItems",  prepareListofBean(taxGroupItemService.listTaxGroupItem()));
		return new ModelAndView("addTaxGroupItem", model);
 }

@RequestMapping(value = "/editTaxGroupItem", method = RequestMethod.GET)
public ModelAndView editTaxGroupItem(@ModelAttribute("taxGroupItem")TaxGroupItemBean taxGroupItemBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		TaxGroupItemBean obj= prepareTaxGroupItemBean(taxGroupItemService.getTaxGroupItem(taxGroupItemBean.getId().toString()));
		model.put("taxGroupItem", obj);
		model.put("taxGroupItems",  prepareListofBean(taxGroupItemService.listTaxGroupItem()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("taxGroupItemgroups", beanMapper.prepareListofTaxGroupItemGroupBean(taxGroupItemGroupService.listTaxGroupItemGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editTaxGroupItem", model);
}

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private TaxGroupItem prepareModel(TaxGroupItemBean taxGroupItemBean){
	    TaxGroupItem taxGroupItem = new TaxGroupItem();

		taxGroupItem.setId(taxGroupItemBean.getId());
		taxGroupItem.setTaxId(taxGroupItemBean.getTaxId());
		taxGroupItem.setTaxGroupId(taxGroupItemBean.getTaxGroupId());
		taxGroupItem.setCompanyId(taxGroupItemBean.getCompanyId());
		taxGroupItem.setBranchId(taxGroupItemBean.getBranchId());

	    return taxGroupItem;
 }

 private List<TaxGroupItemBean> prepareListofBean(List<TaxGroupItem> taxGroupItems){
        List<TaxGroupItemBean> beans = null;

        if(taxGroupItems != null && !taxGroupItems.isEmpty()){
        	beans = new ArrayList<TaxGroupItemBean>();
        	TaxGroupItemBean taxGroupItem = null;

        	for(TaxGroupItem item : taxGroupItems){
        		taxGroupItem = new TaxGroupItemBean();

        		taxGroupItem.setId(item.getId());
        		taxGroupItem.setTaxId(item.getTaxId());
        		taxGroupItem.setTaxGroupId(item.getTaxGroupId());
        		taxGroupItem.setCompanyId(item.getCompanyId());
        		taxGroupItem.setBranchId(item.getBranchId());

			    beans.add(taxGroupItem);
		   }
	    }

        return beans;
 }

 private TaxGroupItemBean prepareTaxGroupItemBean(TaxGroupItem taxGroupItem){
		  TaxGroupItemBean 	bean = new TaxGroupItemBean();

		  bean.setId(taxGroupItem.getId());
		  bean.setTaxId(taxGroupItem.getTaxId());
		  bean.setTaxGroupId(taxGroupItem.getTaxGroupId());
		  bean.setCompanyId(taxGroupItem.getCompanyId());
		  bean.setBranchId(taxGroupItem.getBranchId());

		  return bean;
 }
}