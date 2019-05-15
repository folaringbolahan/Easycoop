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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.model.Company;
import com.sift.admin.model.Country;
import com.sift.admin.model.User;
import com.sift.admin.service.AddressEntriesService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryAddressFilterService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.bean.AddressItemsBean;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.CompanyBean;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.webservice.utility.WebServiceUtility;
import com.sift.admin.interfaces.*;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class CompanyController{

@Autowired
private CompanyService companyService;

@Autowired
private CountryService countryService;

@Autowired
private CountryAddressFilterService countryAddressFilterService;

@Autowired
private AddressEntriesService addressEntriesService;

@Autowired
private UserService userService;

@Autowired
private HelperUtility helperUTIL;
BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility   EazyCoopUTIL =new EazyCoopUtility();
WebServiceUtility webServiceUtility = new WebServiceUtility();

@RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
public ModelAndView saveCompany(@ModelAttribute("company")CompanyBean companyBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("companys",  prepareListofBean(companyService.listCompanies()));
	  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addCompany":"editCompany";
	     return new ModelAndView(URI, model);
	 }	
	User currentUser=userService.getUserByEmail(req.getRemoteUser());
	 String contextPath=req.getRealPath("/");
	 String actionId=req.getParameter("ACTION_ID");
	 boolean createCompanyUser=false;
	 Activitylog activity=new Activitylog();

	 System.out.println("contextPath:="+contextPath);
	 
	 if("2".equals(actionId)){
		 companyBean.setCreatedBy(companyService.getCompany(companyBean.getId()).getCreatedBy());
		 companyBean.setLastModifiedBy(req.getRemoteUser());
		 companyBean.setLastModificationDate(new java.util.Date());
		 
	     activity.setEvent(Definitions.EVENT_COMPANY_UPDATE);
	     activity.setAction("Existing Company Modification : " + companyBean.getId());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Company Name: " + companyBean.getName());
	     activity.setActionResult("Existing Company Update for company id: " + companyBean.getId());
	     activity.setDescription("Existing Company Update for company id: " + companyBean.getId());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCountry(companyBean.getCountryId()));
	     activity.setToDate("");
              activity.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
             activity.setBranchid(Integer.parseInt(currentUser.getBranchId()));
             
           // activity.setBranchid(Integer.parseInt(userGroup.getBranchId()));
	 }else{
		 companyBean.setCreatedBy(req.getRemoteUser());
		 companyBean.setCreationDate(new java.util.Date());
		 createCompanyUser=true;
             
             //System.out.println("companyBean.getId() :: " + companyBean.getId());
	     activity.setEvent(Definitions.EVENT_COMPANY_SETUP);
	     activity.setAction("New Company Setup : " + companyBean.getId());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Company Name: " + companyBean.getName());
	     activity.setActionResult("New Company Setup for company name: " + companyBean.getName());
	     activity.setDescription("New Company Setup for company name: " + companyBean.getName());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCountry(companyBean.getCountryId()));
	     activity.setToDate("");
            
             activity.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
             activity.setBranchid(Integer.parseInt(currentUser.getBranchId()));
             
             //activity.setCompanyid((int) (companyBean.getId() == null ? 0 : 0));
            
             //req.getParameter(addrFieldName.trim())==null?" ":req.getParameter(addrFieldName.trim());
	 }
	 
	 Company company = prepareModel(companyBean);
	 String  countryId=company.getCountryId();
	 
	 Country country=countryService.getCountry(Integer.parseInt(countryId));
	 String  currencyCode=country.getCurrencyCode();

	 //get the mapped address field name/values for the selected country
	 String countryID=companyBean.getCountryId();
	 List<CountryAddressFilterBean> addressList=beanMapper.prepareCountryAddressFilterListofBean(countryAddressFilterService.listCountryAddressFilter(countryID));
	 List<AddressEntriesBean> beanList=new ArrayList<AddressEntriesBean>();
	 AddressEntriesBean bean=null;

	 for(CountryAddressFilterBean item: addressList){
		  bean=new AddressEntriesBean();
		  
		  String serialPos=item.getAddrFieldIndx();
		  String addrFieldName=item.getAddrFieldName();
		  String addrFieldValue=req.getParameter(addrFieldName.trim())==null?" ":req.getParameter(addrFieldName.trim());
		  
		  //System.out.println("addrFieldName:="+addrFieldName);
		  //System.out.println("addrFieldValue:="+addrFieldValue);

		  bean.setActive("Y");
		  bean.setAddrFieldName(addrFieldName);
		  bean.setAddrFieldValue(addrFieldValue);
		  bean.setCreatedBy(req.getRemoteUser());
		  bean.setSerialPos(serialPos);
		  bean.setTypeId("1");
		  bean.setKeyId(null);	

		  beanList.add(bean);
	 }
	 
	 boolean saved=false;
	 
	 if("2".equals(actionId)){
		 saved=companyService.addCompany(company,beanList); 
	 }else{
	    saved=companyService.addCompany(company,beanList,createCompanyUser,contextPath,currencyCode);
	 }
	 
	 if(saved){
		 try{EazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
		 
		 return new ModelAndView("redirect:/doFeedback.htm?message=Company Information Update was successful&redirectURI=newCompany.htm");
	 }else{
		 return new ModelAndView("redirect:/doError.htm?message=Company Information Update failed. Please try again later.&redirectURI=newCompany.htm");
	 }
 }

 @RequestMapping(value="/companys", method = RequestMethod.GET)
 public ModelAndView listCompanys(){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("companys",  prepareListofBean(companyService.listCompanies()));
	 return new ModelAndView("companysList", model);
 }

 @RequestMapping(value = "/companyAjaxList", method = RequestMethod.GET)
 public @ResponseBody List<CompanyBean> companyAjaxList(){
	return prepareListofBean(companyService.listCompanies());
 }

 @RequestMapping(value = "/countryAdressItemAjaxList", method = RequestMethod.GET)
 public @ResponseBody List<CountryAddressFilterBean> countryAddrItemList(@RequestParam(value = "countryId", required = true) String countryId) {
	//System.out.println("countryId=:"+countryId);
	return beanMapper.prepareCountryAddressFilterListofBean(countryAddressFilterService.listCountryAddressFilter(countryId));
 }

 @RequestMapping(value = "/newCompany", method = RequestMethod.GET)
 public ModelAndView addCompany(@ModelAttribute("company")CompanyBean companyBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("companys",  prepareListofBean(companyService.listCompanies()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));

     return new ModelAndView("addCompany", model);
 }

@RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
public ModelAndView deleteCompany(@ModelAttribute("company")CompanyBean companyBean,BindingResult result) {
	 companyService.deleteCompany(prepareModel(companyBean));
	 Map<String,Object> model = new HashMap<String,Object>();
	 model.put("company", null);
	 model.put("companys",  prepareListofBean(companyService.listCompanies()));
	 return new ModelAndView("addCompany", model);
 }

@RequestMapping(value = "/editCompany", method = RequestMethod.GET)
public ModelAndView editCompany(@ModelAttribute("company")CompanyBean companyBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("company", prepareCompanyBean(companyService.getCompany(companyBean.getId())));
		model.put("companys",  prepareListofBean(companyService.listCompanies()));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	  	model.put("addrEntries", beanMapper.prepareListofAddressEntriesBean(addressEntriesService.listAddressEntries(companyBean.getId().toString(),Definitions.ADDRESSING_TYPE_COMPANY)));

		return new ModelAndView("editCompany", model);
 }

 private Company prepareModel(CompanyBean companyBean){
	    Company company = new Company();

	    company.setName(companyBean.getName());
	    company.setCode(companyBean.getCode());
	    company.setShortName(companyBean.getShortName());
	    company.setEmail(companyBean.getEmail());
	    company.setCountryId(companyBean.getCountryId());
	    company.setFax(companyBean.getFax());
	    company.setRegNo(companyBean.getRegNo());
	    company.setPhone1(companyBean.getPhone1());
	    company.setPhone2(companyBean.getPhone2());
	    company.setWebsite(companyBean.getWebsite());

	    company.setActive(companyBean.getActive());
	    company.setDeleted(companyBean.getDeleted());
	    company.setCreatedBy(companyBean.getCreatedBy());
	    company.setCreationDate(companyBean.getCreationDate());
	    company.setLastModifiedBy(companyBean.getLastModifiedBy());
	    company.setLastModificationDate(companyBean.getLastModificationDate());
	    company.setConnectToEazyCoop(companyBean.getConnectToEazyCoop());
		  
	    company.setId(companyBean.getId());

	    return company;
 }

 private List<CompanyBean> prepareListofBean(List<Company> companys){
        List<CompanyBean> beans = null;

        if(companys != null && !companys.isEmpty()){
        	beans = new ArrayList<CompanyBean>();
        	CompanyBean bean = null;

        	for(Company company : companys){
			    bean = new CompanyBean();

			    bean.setName(company.getName());
			    bean.setCode(company.getCode());
			    bean.setShortName(company.getShortName());
			    bean.setEmail(company.getEmail());
			    bean.setCountryId(company.getCountryId());
			    bean.setFax(company.getFax());
			    bean.setRegNo(company.getRegNo());
			    bean.setPhone1(company.getPhone1());
			    bean.setPhone2(company.getPhone2());
			    bean.setWebsite(company.getWebsite());

			    bean.setId(company.getId());
			    bean.setActive(company.getActive());
			    bean.setDeleted(company.getDeleted());
			    bean.setCreatedBy(company.getCreatedBy());
			    bean.setCreationDate(company.getCreationDate());
			    bean.setLastModifiedBy(company.getLastModifiedBy());
			    bean.setLastModificationDate(company.getLastModificationDate());
			    bean.setConnectToEazyCoop(company.getConnectToEazyCoop());
			   
			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private CompanyBean prepareCompanyBean(Company company){
		  CompanyBean 	bean = new CompanyBean();

		  bean.setName(company.getName());
		  bean.setCode(company.getCode());
		  bean.setShortName(company.getShortName());
		  bean.setEmail(company.getEmail());
	      bean.setCountryId(company.getCountryId());
		  bean.setFax(company.getFax());
		  bean.setRegNo(company.getRegNo());
		  bean.setPhone1(company.getPhone1());
		  bean.setPhone2(company.getPhone2());
		  bean.setWebsite(company.getWebsite());

		  bean.setId(company.getId());
		  bean.setActive(company.getActive());
		  bean.setDeleted(company.getDeleted());
		  bean.setCreatedBy(company.getCreatedBy());
		  bean.setCreationDate(company.getCreationDate());
		  bean.setLastModifiedBy(company.getLastModifiedBy());
		  bean.setLastModificationDate(company.getLastModificationDate());
		  bean.setConnectToEazyCoop(company.getConnectToEazyCoop());


		  return bean;
   }
}