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

import com.sift.admin.model.AppConfiguration;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.AppConfigurationService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.AppConfigurationBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class AppConfigurationController{

@Autowired
private AppConfigurationService appConfigurationService;

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveAppConfig", method = RequestMethod.POST)
public ModelAndView saveAppConfig(@Valid @ModelAttribute("appConfig")AppConfigurationBean appConfigurationBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	 	 model.put("appConfig",appConfigurationBean);
	  	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	  	 model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurationsByCompany(appConfigurationBean.getCompanyId())));
	  	 String URI=req.getParameter("ACTION_ID").equals("1")? "addAppConfig":"editAppConfig";

		 return new ModelAndView(URI,model);
	 }
	 
	 String actionId=req.getParameter("ACTION_ID");
	 String computationType=appConfigurationBean.getComputationType();
	 
	 if("FORMULA".equalsIgnoreCase(computationType.trim().toUpperCase())){
		 appConfigurationBean.setConfigMaxValue("");
		 appConfigurationBean.setConfigMinValue("");
	 }else if("STATIC".equalsIgnoreCase(computationType.trim().toUpperCase())){
		 appConfigurationBean.setMultiplier(null);
		 appConfigurationBean.setFormulaValue(null);
		 appConfigurationBean.setOperand(null);
	 }
	 
	 if("2".equals(actionId)){
		 appConfigurationBean.setCreatedBy(appConfigurationService.getAppConfiguration(appConfigurationBean.getId()).getCreatedBy());
		 appConfigurationBean.setLastModifiedBy(req.getRemoteUser());
		 appConfigurationBean.setLastModificationDate(new java.util.Date());
	 }
	 
	 AppConfiguration appConfiguration = prepareModel(appConfigurationBean);
	 appConfigurationService.addAppConfiguration(appConfiguration);

	 return new ModelAndView("redirect:/newAppConfig.htm");
 }

 @RequestMapping(value="/appConfigurations", method = RequestMethod.GET)
 public ModelAndView listAppConfigurations() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurations()));
	 return new ModelAndView("appConfigurationsList", model);
 }

 @RequestMapping(value = "/newAppConfig", method = RequestMethod.GET)
 public ModelAndView addAppConfiguration(@ModelAttribute("appConfig")AppConfigurationBean appConfigurationBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 //model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurations()));
  	 //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));

	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 appConfigurationBean.setCompanyId(currentUser.getCompanyId());
	 appConfigurationBean.setBranchId(currentUser.getBranchId());
	 
	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurationsByCompanyByBranch(currentUser.getCompanyId(),currentUser.getBranchId())));
	 	 
	 model.put("appConfig",appConfigurationBean);
     return new ModelAndView("addAppConfig",model);
 }
 
 @RequestMapping(value = "/viewAppConfig", method = RequestMethod.GET)
 public ModelAndView viewAppConfigs(@ModelAttribute("appConfig")AppConfigurationBean appConfigurationBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 //model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurations()));
  	 //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));

	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 User currentUser=userService.getUserByEmail(logonUser);
	 	 
	 appConfigurationBean.setCompanyId(currentUser.getCompanyId());
	 appConfigurationBean.setBranchId(currentUser.getBranchId());
	 
	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurationsByCompanyByBranch(currentUser.getCompanyId(),currentUser.getBranchId())));
	 	 
	 model.put("appConfig",appConfigurationBean);
     return new ModelAndView("viewAppConfigs",model);
 }

@RequestMapping(value = "/deleteAppConfig", method = RequestMethod.GET)
public ModelAndView deleteAppConfiguration(@ModelAttribute("appConfig")AppConfigurationBean appConfigurationBean,BindingResult result) {
	    appConfigurationService.deleteAppConfiguration(prepareModel(appConfigurationBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("appConfig", null);
		model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurations()));
		return new ModelAndView("addAppConfig", model);
}

@RequestMapping(value = "/editAppConfig", method = RequestMethod.GET)
public ModelAndView editAppConfiguration(@ModelAttribute("appConfig")AppConfigurationBean appConfigurationBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		AppConfigurationBean obj=prepareAppConfigurationBean(appConfigurationService.getAppConfiguration(appConfigurationBean.getId()));
		model.put("appConfig",            obj);
		//model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurations()));
	  	//model.put("companies",          beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	  	
		model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
		model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(obj.getBranchId())));
		model.put("appConfigurations",  prepareListofBean(appConfigurationService.listAppConfigurationsByCompanyByBranch(obj.getCompanyId(),obj.getBranchId())));

		return new ModelAndView("editAppConfig", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private AppConfiguration prepareModel(AppConfigurationBean appConfigurationBean){
	    AppConfiguration appConfiguration = new AppConfiguration();

	    appConfiguration.setActive(appConfigurationBean.getActive());
	    appConfiguration.setDeleted(appConfigurationBean.getDeleted());
	    appConfiguration.setCompanyId(appConfigurationBean.getCompanyId());
	    appConfiguration.setBranchId(appConfigurationBean.getBranchId());

	    appConfiguration.setConfigName(appConfigurationBean.getConfigName());
	    appConfiguration.setConfigType(appConfigurationBean.getConfigType());
	    appConfiguration.setConfigMinValue(appConfigurationBean.getConfigMinValue());
	    appConfiguration.setConfigMaxValue(appConfigurationBean.getConfigMaxValue());

	    appConfiguration.setComputationType(appConfigurationBean.getComputationType());
	    appConfiguration.setFormulaValue(appConfigurationBean.getFormulaValue());

	    appConfiguration.setOperand(appConfigurationBean.getOperand());
	    appConfiguration.setMultiplier(appConfigurationBean.getMultiplier());

	    appConfiguration.setCreatedBy(appConfigurationBean.getCreatedBy());
	    appConfiguration.setCreationDate(appConfigurationBean.getCreationDate());
	    appConfiguration.setLastModifiedBy(appConfigurationBean.getLastModifiedBy());
	    appConfiguration.setLastModificationDate(appConfigurationBean.getLastModificationDate());
	    appConfiguration.setId(appConfigurationBean.getId());

	    return appConfiguration;
 }

 private List<AppConfigurationBean> prepareListofBean(List<AppConfiguration> appConfigurations){
        List<AppConfigurationBean> beans = null;

        if(appConfigurations != null && !appConfigurations.isEmpty()){
        	beans = new ArrayList<AppConfigurationBean>();
        	AppConfigurationBean appConfiguration = null;

        	for(AppConfiguration appConfigurationBean : appConfigurations){
        		appConfiguration = new AppConfigurationBean();

        		appConfiguration.setId(appConfigurationBean.getId());
			    appConfiguration.setActive(appConfigurationBean.getActive());
			    appConfiguration.setDeleted(appConfigurationBean.getDeleted());
			    appConfiguration.setCompanyId(appConfigurationBean.getCompanyId());
			    appConfiguration.setBranchId(appConfigurationBean.getBranchId());

			    appConfiguration.setConfigName(appConfigurationBean.getConfigName());
			    appConfiguration.setConfigType(appConfigurationBean.getConfigType());
			    appConfiguration.setConfigMinValue(appConfigurationBean.getConfigMinValue());
			    appConfiguration.setConfigMaxValue(appConfigurationBean.getConfigMaxValue());

			    appConfiguration.setComputationType(appConfigurationBean.getComputationType());
			    appConfiguration.setFormulaValue(appConfigurationBean.getFormulaValue());

			    appConfiguration.setOperand(appConfigurationBean.getOperand());
			    appConfiguration.setMultiplier(appConfigurationBean.getMultiplier());
				  
			    appConfiguration.setCreatedBy(appConfigurationBean.getCreatedBy());
			    appConfiguration.setCreationDate(appConfigurationBean.getCreationDate());
			    appConfiguration.setLastModifiedBy(appConfigurationBean.getLastModifiedBy());
			    appConfiguration.setLastModificationDate(appConfigurationBean.getLastModificationDate());

			    beans.add(appConfiguration);
		   }
	    }

        return beans;
 }

 private AppConfigurationBean prepareAppConfigurationBean(AppConfiguration appConfiguration){
		  AppConfigurationBean 	bean = new AppConfigurationBean();

		  bean.setId(appConfiguration.getId());
		  bean.setActive(appConfiguration.getActive());
		  bean.setDeleted(appConfiguration.getDeleted());
		  bean.setCompanyId(appConfiguration.getCompanyId());
		  bean.setBranchId(appConfiguration.getBranchId());

		  bean.setConfigName(appConfiguration.getConfigName());
		  bean.setConfigType(appConfiguration.getConfigType());
		  bean.setConfigMinValue(appConfiguration.getConfigMinValue());
		  bean.setConfigMaxValue(appConfiguration.getConfigMaxValue());
		  
		  bean.setComputationType(appConfiguration.getComputationType());
		  bean.setFormulaValue(appConfiguration.getFormulaValue());

		  bean.setOperand(appConfiguration.getOperand());
		  bean.setMultiplier(appConfiguration.getMultiplier());

		  bean.setCreatedBy(appConfiguration.getCreatedBy());
		  bean.setCreationDate(appConfiguration.getCreationDate());
		  bean.setLastModifiedBy(appConfiguration.getLastModifiedBy());
		  bean.setLastModificationDate(appConfiguration.getLastModificationDate());

		  return bean;
 }
}