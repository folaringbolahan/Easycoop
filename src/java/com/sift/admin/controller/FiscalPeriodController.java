package com.sift.admin.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.FiscalPeriod;
import com.sift.admin.model.FiscalPeriodItem;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.FiscalPeriodItemService;
import com.sift.admin.service.FiscalPeriodService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.FiscalPeriodBean;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class FiscalPeriodController{

@Autowired
private FiscalPeriodService fiscalPeriodService;

@Autowired
private FiscalPeriodItemService fiscalPeriodItemService;

@Autowired
private CompanyService companyService;

@Autowired
private UserService userService;

@Autowired
private BranchService branchService;

@Autowired
private HelperUtility helperUTIL;
 
BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
 
@RequestMapping(value = "/saveFiscalP", method = RequestMethod.POST)
public ModelAndView saveFiscalPeriod(@Valid @ModelAttribute("fiscalP")FiscalPeriodBean fiscalPeriodBean,BindingResult result,HttpServletRequest req) {
	 /******************************************************************************************************************
	  ******************************************************************************************************************
	  ******************************************************************************************************************/
	  if(result.hasErrors()){
		 for(ObjectError item: result.getAllErrors()){
			 System.out.println("Error=:"+item.getDefaultMessage());
		 }
		 
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 
		 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
		 User currentUser=userService.getUserByEmail(logonUser);
		 	 
		 fiscalPeriodBean.setCompanyId(currentUser.getCompanyId());
		 fiscalPeriodBean.setBranchId(currentUser.getBranchId());
		 
		 model.put("fiscalPeriods",  fiscalPeriodService.listFiscalPeriodsByBranchBean(currentUser.getBranchId()));
		 //model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriodsByCompany(currentUser.getCompanyId())));
		 model.put("fiscalP",fiscalPeriodBean);
		 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
		 
		 String URI=req.getParameter("ACTION_ID").equals("1")? "addFiscalPeriod":"editFiscalPeriod";
		 return new ModelAndView(URI,model);
	 }
	 /*****************************************************************************************************************
	 ******************************************************************************************************************
	 *****************************************************************************************************************/
	 ArrayList<FiscalPeriodItem> fpitems=null;
	 FiscalPeriodItem fp=null;
	 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	 String actionId=req.getParameter("ACTION_ID");
	 
	 for(int y=1; y<=fiscalPeriodBean.getNoOfPeriods(); y++){
		 fp=new FiscalPeriodItem();
		 if(fpitems==null){fpitems=new ArrayList<FiscalPeriodItem>();}
		 
		 String periodStart=req.getParameter("periodStart"+y);
		 String     periodEnd=req.getParameter("periodEnd"+y);
		 
		 try{
			 fp.setPeriodId(y);
			 fp.setPeriodStart(formatter.parse(periodStart));
			 fp.setPeriodEnd(formatter.parse(periodEnd));
			 fp.setYear(fiscalPeriodBean.getYear());
			 
			 fpitems.add(fp);
		 }catch(ParseException e){
			e.printStackTrace();
		 }
	 }	 	 
 	 
	 boolean good=false;
	 Activitylog activity=new Activitylog();
	 
	 if("2".equals(actionId)){
		 fiscalPeriodBean.setCreatedBy(fiscalPeriodService.getFiscalPeriod(fiscalPeriodBean.getId()).getCreatedBy());
		 fiscalPeriodBean.setLastModifiedBy(req.getRemoteUser());
		 fiscalPeriodBean.setLastModificationDate(new java.util.Date());
		 
		 FiscalPeriod fiscalPeriod = prepareModel(fiscalPeriodBean);
		 good=fiscalPeriodService.addFiscalPeriod(fiscalPeriod,fpitems);
		 
	     activity.setEvent(Definitions.EVENT_FISCAL_PERIOD_UPDATE);
	     activity.setAction("Fiscal Period Modification for Record Id: " + fiscalPeriod.getId());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Period Year: " + fiscalPeriod.getYear());
	     activity.setActionResult("Fiscal Period Modification for Year: " + fiscalPeriod.getYear());
	     activity.setDescription("Fiscal Period Modification for Year: " + fiscalPeriod.getYear());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(fiscalPeriodBean.getCompanyId()));
	     activity.setToDate("");

	 }else{
		 fiscalPeriodBean.setCreatedBy(req.getRemoteUser());
		 fiscalPeriodBean.setCreationDate(new java.util.Date());
		 fiscalPeriodBean.setLastModifiedBy(req.getRemoteUser());
		 fiscalPeriodBean.setLastModificationDate(new java.util.Date());
		 
		 FiscalPeriod fiscalPeriod = prepareModel(fiscalPeriodBean);
		 good=fiscalPeriodService.addNewFiscalPeriod(fiscalPeriod,fpitems);
		 
	     activity.setEvent(Definitions.EVENT_FISCAL_PERIOD_CREATION);
	     activity.setAction("Fiscal Period Setup for Year: " + fiscalPeriod.getYear());
	     activity.setActionDate(new java.util.Date());
	     activity.setActionItem("Period Year: " + fiscalPeriod.getYear());
	     activity.setActionResult("Fiscal Period Setup for Year: " + fiscalPeriod.getYear());
	     activity.setDescription("Fiscal Period Setup for Year: " + fiscalPeriod.getYear());
	     activity.setIpaddress(req.getRemoteHost());
	     activity.setUsername(req.getRemoteUser());	
	     activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(fiscalPeriod.getCompanyId()));
	     activity.setToDate("");
	 }
	 
	 if(good){try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}}
	 return new ModelAndView("redirect:/doFeedback.htm?message=Fiscal Period Record Setup/Update was successful&redirectURI=newFiscalP0.htm");
 }

 @RequestMapping(value="/fiscalPeriods", method = RequestMethod.GET)
 public ModelAndView listFiscalPeriods(){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriods()));
	 return new ModelAndView("fiscalPeriodsList", model);
 }

 @RequestMapping(value = "/newFiscalP0", method = RequestMethod.GET)
 public ModelAndView addFiscalPeriod(@ModelAttribute("fiscalP")FiscalPeriodBean fiscalPeriodBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 fiscalPeriodBean.setCompanyId(currentUser.getCompanyId());
	 fiscalPeriodBean.setBranchId(currentUser.getBranchId());
	 
	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));

	 //model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriodsByCompany(currentUser.getCompanyId())));
	 //model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriodsByBranch(currentUser.getBranchId())));
	 model.put("fiscalPeriods",  fiscalPeriodService.listFiscalPeriodsByBranchBean(currentUser.getBranchId()));
	 
	 model.put("fiscalP",fiscalPeriodBean);
  	 return new ModelAndView("addFiscalPeriod0", model);
 }
 
 @RequestMapping(value = "/newFiscalP", method = RequestMethod.POST)
 public ModelAndView addFiscalPeriod1(@ModelAttribute("fiscalP")FiscalPeriodBean fiscalPeriodBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>(); 	 
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);		 	 
	 fiscalPeriodBean.setCompanyId(currentUser.getCompanyId());
	 
	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
     model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 //model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriodsByBranch(currentUser.getBranchId())));
	 model.put("fiscalPeriods",  fiscalPeriodService.listFiscalPeriodsByBranchBean(currentUser.getBranchId()));

  	 model.put("fiscalP", fiscalPeriodBean);

  	 return new ModelAndView("addFiscalPeriod", model);
 }

@RequestMapping(value = "/deleteFiscalP", method = RequestMethod.GET)
public ModelAndView deleteFiscalPeriod(@ModelAttribute("fiscalP")FiscalPeriodBean fiscalPeriodBean,BindingResult result){
	    fiscalPeriodService.deleteFiscalPeriod(prepareModel(fiscalPeriodBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("fiscalPeriod", null);
		model.put("fiscalPeriods",  prepareListofBean(fiscalPeriodService.listFiscalPeriods()));
		return new ModelAndView("addFiscalPeriod", model);
}

@RequestMapping(value = "/editFiscalP", method = RequestMethod.GET)
public ModelAndView editFiscalPeriod(@ModelAttribute("fiscalP")FiscalPeriodBean fiscalPeriodBean,BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		
		FiscalPeriodBean obj=prepareFiscalPeriodBean(fiscalPeriodService.getFiscalPeriod(fiscalPeriodBean.getId()));
		
		model.put("fiscalP", 			obj);
	  	//model.put("fiscalPeriods",    prepareListofBean(fiscalPeriodService.listFiscalPeriods()));
		//model.put("fiscalPeriods",    prepareListofBean(fiscalPeriodService.listFiscalPeriodsByCompany(obj.getCompanyId())));
		//model.put("fiscalPeriods",    prepareListofBean(fiscalPeriodService.listFiscalPeriodsByBranch(obj.getBranchId())));
		model.put("fiscalPeriods",  	fiscalPeriodService.listFiscalPeriodsByBranchBean(obj.getBranchId()));

	  	model.put("fiscalPeriodItems",  fiscalPeriodItemService.listFiscalPeriodItemByYear(obj.getBranchId(),obj.getYear()));
	  	model.put("companies",   		beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	    model.put("branches",    		beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
	  	//model.put("branches",   		beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
	  	
	  	return new ModelAndView("editFiscalPeriod", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private FiscalPeriod prepareModel(FiscalPeriodBean fiscalPeriodBean){
	    FiscalPeriod fiscalPeriod = new FiscalPeriod();

	    fiscalPeriod.setActive(fiscalPeriodBean.getActive());
	    fiscalPeriod.setCompanyId(fiscalPeriodBean.getCompanyId());
	    fiscalPeriod.setBranchId(fiscalPeriodBean.getBranchId());
	    fiscalPeriod.setYear(fiscalPeriodBean.getYear());
	    fiscalPeriod.setNoOfPeriods(fiscalPeriodBean.getNoOfPeriods());
	    fiscalPeriod.setCreatedBy(fiscalPeriodBean.getCreatedBy());
	    fiscalPeriod.setCreationDate(fiscalPeriodBean.getCreationDate());
	    fiscalPeriod.setLastModifiedBy(fiscalPeriodBean.getLastModifiedBy());
	    fiscalPeriod.setLastModificationDate(fiscalPeriodBean.getLastModificationDate());
	    fiscalPeriod.setId(fiscalPeriodBean.getId());

	    return fiscalPeriod;
 }

 private List<FiscalPeriodBean> prepareListofBean(List<FiscalPeriod> fiscalPeriods){
        List<FiscalPeriodBean> beans = null;

        if(fiscalPeriods != null && !fiscalPeriods.isEmpty()){
        	beans = new ArrayList<FiscalPeriodBean>();
        	FiscalPeriodBean fiscalPeriod = null;

        	for(FiscalPeriod fiscalPeriodBean : fiscalPeriods){
        		fiscalPeriod = new FiscalPeriodBean();

        		fiscalPeriod.setId(fiscalPeriodBean.getId());
			    fiscalPeriod.setActive(fiscalPeriodBean.getActive());
			    fiscalPeriod.setCompanyId(fiscalPeriodBean.getCompanyId());
			    fiscalPeriod.setBranchId(fiscalPeriodBean.getBranchId());
			    fiscalPeriod.setYear(fiscalPeriodBean.getYear());
			    fiscalPeriod.setNoOfPeriods(fiscalPeriodBean.getNoOfPeriods());
			    fiscalPeriod.setCreatedBy(fiscalPeriodBean.getCreatedBy());
			    fiscalPeriod.setCreationDate(fiscalPeriodBean.getCreationDate());
			    fiscalPeriod.setLastModifiedBy(fiscalPeriodBean.getLastModifiedBy());
			    fiscalPeriod.setLastModificationDate(fiscalPeriodBean.getLastModificationDate());

			    beans.add(fiscalPeriod);
		   }
	    }

        return beans;
 }

 private FiscalPeriodBean prepareFiscalPeriodBean(FiscalPeriod fiscalPeriod){
		  FiscalPeriodBean 	bean = new FiscalPeriodBean();

		  bean.setId(fiscalPeriod.getId());
		  bean.setActive(fiscalPeriod.getActive());
		  bean.setCompanyId(fiscalPeriod.getCompanyId());
		  bean.setBranchId(fiscalPeriod.getBranchId());
		  bean.setYear(fiscalPeriod.getYear());
		  bean.setNoOfPeriods(fiscalPeriod.getNoOfPeriods());
		  bean.setCreatedBy(fiscalPeriod.getCreatedBy());
		  bean.setCreationDate(fiscalPeriod.getCreationDate());
		  bean.setLastModifiedBy(fiscalPeriod.getLastModifiedBy());
		  bean.setLastModificationDate(fiscalPeriod.getLastModificationDate());

		  return bean;
 }
}