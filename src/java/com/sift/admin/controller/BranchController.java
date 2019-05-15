package com.sift.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.User;
import com.sift.admin.service.AddressEntriesService;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryAddressFilterService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.CurrencyService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AddressEntriesBean;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.admin.dao.AddressEntriesDao;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sift.webservice.utility.WebServiceUtility;

/*
 * @author XTOFFEL CONSULT
 */
@Controller
public class BranchController{

@Autowired
private BranchService branchService;

@Autowired
private CountryService countryService;

@Autowired
private CurrencyService currencyService;

@Autowired
private CompanyService companyService;

@Autowired
private AddressEntriesService addressEntriesService;

@Autowired
private AddressEntriesDao addressEntriesDao;

@Autowired
private UserService userService;

@Autowired
private HelperUtility helperUTIL;
BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
WebServiceUtility webServiceUtility = new WebServiceUtility();

@Autowired
private CountryAddressFilterService countryAddressFilterService;

@RequestMapping(value = "/saveBranch", method = RequestMethod.POST)
public ModelAndView saveBranch(@ModelAttribute("branch")BranchBean branchBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()){
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		 
	  	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	     User currentUser=userService.getUserByEmail(logonUser);
		 	 	 
	     branchBean.setCompanyId(currentUser.getCompanyId());
	     
	     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	     model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
	     
		 String URI=req.getParameter("ACTION_ID").equals("1")? "addCompany":"editCompany";
	     return new ModelAndView(URI, model);
	 }	
	 
	 String actionId=req.getParameter("ACTION_ID");
	 boolean isNew=false; 
	 
	 Activitylog activity=new Activitylog();
	   
	   
	 
	 if("2".equals(actionId)){
		 Branch iBranch=branchService.getBranch(branchBean.getId());
		 branchBean.setCreatedBy(iBranch.getCreatedBy());
		 branchBean.setLastModifiedBy(req.getRemoteUser());
		 branchBean.setLastModificationDate(new java.util.Date());
		 branchBean.setSetupDate(iBranch.getCreationDate());
		 
	     activity.setEvent(Definitions.EVENT_BRANCH_UPDATE);
	     activity.setAction("Existing Branch Update for company id: " + iBranch.getCompanyId());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Branch Name: " + iBranch.getBranchName());
	     activity.setActionResult("Existing Branch Update for company id: " + iBranch.getCompanyId());
	     activity.setDescription("Existing Branch Update for company id: " + iBranch.getCompanyId());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCountry(iBranch.getCountryId()));
	     activity.setToDate("");
	 }else{	
		 isNew=true;
		 
		 /*Integer serialPos=eazyCoopUTIL.getNextSerial(sessionFactory,branchBean.getCompanyId());
		    String  serialStr=serialPos.toString();
		 
		 for(int k=0; k<=3-serialStr.length(); k++){
			 serialStr="0"+serialStr;
		 }		 
		 branchBean.setBranchCode(serialStr);*/
		  
		 branchBean.setSetupDate(new java.util.Date());
		 
	     activity.setEvent(Definitions.EVENT_BRANCH_SETUP);
	     activity.setAction("New Branch setup for company id: " + branchBean.getCompanyId());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Branch Name: " + branchBean.getBranchName());
	     activity.setActionResult("New Branch setup for company id: " + branchBean.getCompanyId());
	     activity.setDescription("New Branch setup for company id: " + branchBean.getCompanyId());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCountry(branchBean.getCountryId()));
	     activity.setToDate("");
	 }	
	 
	 Branch branch = prepareModel(branchBean);

	 //get the mapped address field name/values for the selected country
	 String countryID=branchBean.getCountryId();
	 System.out.println("countryID=:"+countryID);

	 List<CountryAddressFilterBean> addressList=beanMapper.prepareCountryAddressFilterListofBean(countryAddressFilterService.listCountryAddressFilter(countryID));
	 List<AddressEntriesBean> beanList=new ArrayList<AddressEntriesBean>();
	 AddressEntriesBean bean=null;

	 for(CountryAddressFilterBean item: addressList){
		  bean=new AddressEntriesBean();
		  String addrFieldName=item.getAddrFieldName();
		  String serialPos=item.getAddrFieldIndx();
		  String addrFieldValue=req.getParameter(addrFieldName.trim())==null?" ":req.getParameter(addrFieldName.trim());
		  
		  System.out.println("addrFieldName:="+addrFieldName);
		  System.out.println("addrFieldValue:="+addrFieldValue);

		  bean.setActive("Y");
		  bean.setAddrFieldName(addrFieldName);
		  bean.setAddrFieldValue(addrFieldValue);
		  bean.setCreatedBy(req.getRemoteUser());
		  bean.setSerialPos(serialPos);
		  bean.setTypeId("2");
		  bean.setKeyId(null);

		  beanList.add(bean);
	 }

	 boolean done=branchService.addBranch(branch,beanList,isNew);
	 if(done){
		 try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
	 }
     //if(done && isNew){eazyCoopUTIL.incrementSerial(sessionFactory,branchBean.getCompanyId());}
     	 
	 return new ModelAndView("redirect:/doFeedback.htm?message=Branch Information Update was successful&redirectURI=newBranch.htm");
 }

 @RequestMapping(value="/branchs", method = RequestMethod.GET)
 public ModelAndView listBranchs(){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
	 return new ModelAndView("branchsList", model);
 }

 @RequestMapping(value = "/newBranch", method = RequestMethod.GET)
 public ModelAndView addBranch(@ModelAttribute("branch")BranchBean branchBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
	 
  	 //model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 
  	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     branchBean.setCompanyId(currentUser.getCompanyId());
     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     //model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(currentUser.getCompanyId())));
     model.put("branches",  branchService.listBranchBeans(currentUser.getCompanyId()));
     model.put("currencies",  beanMapper.prepareListofCurrencyBean(currencyService.listCurrency()));
     
     return new ModelAndView("addBranch", model);
}

@RequestMapping(value = "/deleteBranch", method = RequestMethod.GET)
public ModelAndView deleteBranch(@ModelAttribute("branch")BranchBean branchBean,BindingResult result) {
	    branchService.deleteBranch(prepareModel(branchBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("branch", null);
		model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
		return new ModelAndView("addBranch", model);
}

@RequestMapping(value = "/editBranch", method = RequestMethod.GET)
public ModelAndView editBranch(@ModelAttribute("branch")BranchBean branchBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		BranchBean obj=prepareBranchBean(branchService.getBranch(branchBean.getId()));
		model.put("branch", obj);
		//model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranch()));
	  	//model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	  	model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	//model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
	  	model.put("branches",  branchService.listBranchBeans(obj.getCompanyId()));
	  	model.put("countries",   beanMapper.prepareListofCountryBean(countryService.listCountry()));
	  	model.put("addrEntries", beanMapper.prepareListofAddressEntriesBean(addressEntriesService.listAddressEntries(branchBean.getId().toString(),Definitions.ADDRESSING_TYPE_BRANCH)));
	    model.put("currencies",  beanMapper.prepareListofCurrencyBean(currencyService.listCurrency()));

	    return new ModelAndView("editBranch", model);
 }

 @RequestMapping(value = "/activateBranch_11", method = RequestMethod.GET)
 public ModelAndView activateBranch_0(@ModelAttribute("branch")BranchBean branchBean,BindingResult result,HttpServletRequest req){
		Map<String, Object> model = new HashMap<String,Object>();
	  	String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
	     
	  	model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	  	model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listInActiveBranches(currentUser.getCompanyId())));

	  	return new ModelAndView("activateBranch_1", model);
 }
 
 @RequestMapping(value = "/activateBranch_1", method = RequestMethod.GET)
 public ModelAndView activateBranch_1(@ModelAttribute("branch")BranchBean branchBean,BindingResult result,HttpServletRequest req){
		Map<String, Object> model = new HashMap<String,Object>();
	  	String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
	     
	  	model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	  	model.put("branches",  branchService.listInActiveBranches());

	  	return new ModelAndView("activateBranch_1", model);
}
 
 @RequestMapping(value = "/activateBranch_2", method = RequestMethod.GET)
 public ModelAndView activateBranch_2(@ModelAttribute("branch")BranchBean branchBean,BindingResult result){
 		Map<String, Object> model = new HashMap<String, Object>();
 		BranchBean obj=prepareBranchBean(branchService.getBranch(branchBean.getId()));
 		model.put("branch", obj);

 		model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
 	  	//model.put("branches",  beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
 	  	
 		model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
 	  	model.put("addrEntries", beanMapper.prepareListofAddressEntriesBean(addressEntriesService.listAddressEntries(branchBean.getId().toString(),Definitions.ADDRESSING_TYPE_BRANCH)));
 	  	return new ModelAndView("activateBranch_2", model);
 }
 
 @RequestMapping(value = "/doUpdateBranchActivationStatus", method = RequestMethod.POST)
 public ModelAndView doUpdateBranchActivationStatus(@ModelAttribute("branch")BranchBean branchBean,BindingResult result,HttpServletRequest req){
	    Map<String, Object> model = new HashMap<String, Object>();
	    
	    BranchBean obj=prepareBranchBean(branchService.getBranch(branchBean.getId()));
	    
	    /**************************************************************************
	    System.out.println("getCompanyId:=" + obj.getCompanyId());
	    System.out.println("getBranchId:=" + obj.getBranchCode());
	    System.out.println("getBranchName:=" + obj.getBranchName());
	    System.out.println("getConnectToEazyCoop:=" + obj.getConnectToEazyCoop());
	    System.out.println("Active:=" + obj.getActive());
		***************************************************************************/
	    
	    boolean good=branchService.updateActiveStatus(prepareModel(branchBean));
	    
		Activitylog activity=new Activitylog();
		  
		activity.setEvent(Definitions.EVENT_BRANCH_ACTIVATION);
		activity.setAction("Branch Activation for Branch Code: " + obj.getBranchCode());
		activity.setActionDate(new java.util.Date());
		activity.setActionItem("Branch Name: " + obj.getBranchName());
		activity.setActionResult("Branch Activation for Branch Code: " + obj.getBranchCode());
		activity.setDescription("Branch Activation for Branch Code: " + obj.getBranchCode());
		activity.setIpaddress(req.getRemoteHost());
		activity.setBranchid(branchBean.getId()==null?0:branchBean.getId());
		activity.setCompanyid(Integer.parseInt(obj.getCompanyId()==null?"0":branchBean.getCompanyId()));
		activity.setUsername(req.getRemoteUser());	
		activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(obj.getCompanyId()));
		activity.setToDate("");
		
		
		if(good){
			    try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}
			    
                try{
                        int coopid = 0;
                        
			            try{
			               coopid = Integer.parseInt(obj.getCompanyId());
			            }catch(NumberFormatException nuex) {			                
			            }
			            
			            Company comp = companyService.getCompany(coopid);
			            if (obj.getConnectToEazyCoop().equalsIgnoreCase("Y") /*&& obj.getActive().equalsIgnoreCase("Y") */){
			                    String resource = "coop";
			
			                    webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(obj.getCompanyId(), 
			                         obj.getId().toString(), comp.getName(), obj.getBranchName(),comp.getShortName()));
			            }
                }catch(Exception ex){                	
                }
          }

 		return new ModelAndView("redirect:/doFeedback.htm?message=Branch Status Update was successful&redirectURI=activateBranch_1.htm");
 }

@RequestMapping(value = "/branchAjaxList1", method = RequestMethod.GET)
public @ResponseBody List<BranchBean> branchesForCompany(
		@RequestParam(value = "companyId", required = true) String id){
	    return prepareListofBean(branchService.listBranchByCompany(id));
}

@RequestMapping(value = "/branchAjaxList", method = RequestMethod.GET)
public @ResponseBody List<BranchBean> branchesForCompanyNew(
		@RequestParam(value = "companyId", required = true) String id){
	    return prepareListofBean(branchService.listBranchByCompany(id));
}

 private Branch prepareModel(BranchBean branchBean){
	    Branch branch = new Branch();

	    branch.setBranchCode(branchBean.getBranchCode());
	    branch.setBranchName(branchBean.getBranchName());
	    branch.setCompanyId(branchBean.getCompanyId());
	    branch.setCountryId(branchBean.getCountryId());
	    branch.setPhone1(branchBean.getPhone1());
	    branch.setPhone2(branchBean.getPhone2());
	    branch.setEmail(branchBean.getEmail());
	    branch.setActive(branchBean.getActive());
	    branch.setDeleted(branchBean.getDeleted());
	    	    
	    branch.setPostDate(branchBean.getPostDate());
	    branch.setCurrentYear(branchBean.getCurrentYear());
	    branch.setCurrentPeriod(branchBean.getCurrentPeriod());	
	    branch.setBaseCurrency(branchBean.getBaseCurrency());
	    branch.setSetupDate(branchBean.getSetupDate());	
	    
	    branch.setCreatedBy(branchBean.getCreatedBy());
	    branch.setCreationDate(branchBean.getCreationDate());
	    branch.setLastModifiedBy(branchBean.getLastModifiedBy());
	    branch.setLastModificationDate(branchBean.getLastModificationDate());
	    branch.setConnectToEazyCoop(branchBean.getConnectToEazyCoop());
		branch.setId(branchBean.getId());

	    branchBean.setId(null);
	    return branch;
 }

 private List<BranchBean> prepareListofBean(List<Branch> branchs){
        List<BranchBean> beans = null;

        if(branchs != null && !branchs.isEmpty()){
        	beans = new ArrayList<BranchBean>();
        	BranchBean bean = null;

        	for(Branch branch : branchs){
			    bean = new BranchBean();

			    bean.setBranchCode(branch.getBranchCode());
			    bean.setBranchName(branch.getBranchName());
			    bean.setCompanyId(branch.getCompanyId());
			    bean.setCountryId(branch.getCountryId());
			    bean.setPhone1(branch.getPhone1());
			    bean.setPhone2(branch.getPhone2());
			    bean.setEmail(branch.getEmail());
			    bean.setId(branch.getId());
			    bean.setActive(branch.getActive());
			    bean.setDeleted(branch.getDeleted());
			    
			    bean.setPostDate(branch.getPostDate());
			    bean.setCurrentYear(branch.getCurrentYear());
			    bean.setCurrentPeriod(branch.getCurrentPeriod());	
			    bean.setBaseCurrency(branch.getBaseCurrency());
			    bean.setSetupDate(branch.getSetupDate());	
			    
			    bean.setCreatedBy(branch.getCreatedBy());
			    bean.setCreationDate(branch.getCreationDate());
			    bean.setLastModifiedBy(branch.getLastModifiedBy());
			    bean.setLastModificationDate(branch.getLastModificationDate());
			    bean.setConnectToEazyCoop(branch.getConnectToEazyCoop());
			    
			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private BranchBean prepareBranchBean(Branch branch){
		  BranchBean 	bean = new BranchBean();

		  bean.setBranchCode(branch.getBranchCode());
		  bean.setBranchName(branch.getBranchName());
          bean.setCompanyId(branch.getCompanyId());
		  bean.setCountryId(branch.getCountryId());
		  bean.setPhone1(branch.getPhone1());
		  bean.setPhone2(branch.getPhone2());
		  bean.setEmail(branch.getEmail());
		  bean.setId(branch.getId());
		  bean.setActive(branch.getActive());
		  bean.setDeleted(branch.getDeleted());
		  
		  bean.setPostDate(branch.getPostDate());
		  bean.setCurrentYear(branch.getCurrentYear());
		  bean.setCurrentPeriod(branch.getCurrentPeriod());	
		  bean.setBaseCurrency(branch.getBaseCurrency());
		  bean.setSetupDate(branch.getSetupDate());	
		    
		  bean.setCreatedBy(branch.getCreatedBy());
		  bean.setCreationDate(branch.getCreationDate());
		  bean.setLastModifiedBy(branch.getLastModifiedBy());
		  bean.setLastModificationDate(branch.getLastModificationDate());
		  bean.setConnectToEazyCoop(branch.getConnectToEazyCoop());
		  
		  return bean;
 }
}