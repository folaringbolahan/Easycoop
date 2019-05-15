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
import com.sift.admin.model.Module;
import com.sift.admin.model.User;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ModuleService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.ModuleBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class ModuleController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;

@Autowired
private ModuleService moduleService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveModule", method = RequestMethod.POST)
public ModelAndView saveModule(@ModelAttribute("module")ModuleBean moduleBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("modules",  prepareListofBean(moduleService.listModule()));
	  	 model.put("module",  moduleBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addModule":"editModule";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 Module module = prepareModel(moduleBean);
	 moduleService.addModule(module);

	 return new ModelAndView("redirect:/newModule.htm");
 }

 @RequestMapping(value="/modules", method = RequestMethod.GET)
 public ModelAndView listModules() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("modules",  prepareListofBean(moduleService.listModule()));
	 return new ModelAndView("moduleList", model);
 }

 @RequestMapping(value = "/newModule", method = RequestMethod.GET)
 public ModelAndView addModule(@ModelAttribute("module")ModuleBean moduleBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("modules",  prepareListofBean(moduleService.listModule()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     model.put("module",moduleBean);     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 
     return new ModelAndView("addModule", model);
 }

@RequestMapping(value = "/deleteModule", method = RequestMethod.GET)
public ModelAndView deleteModule(@ModelAttribute("module")ModuleBean moduleBean,BindingResult result,HttpServletRequest req) {
	    moduleService.deleteModule(prepareModel(moduleBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("module", null);
		model.put("modules",  prepareListofBean(moduleService.listModule()));
		return new ModelAndView("addModule", model);
 }

@RequestMapping(value = "/editModule", method = RequestMethod.GET)
public ModelAndView editModule(@ModelAttribute("module")ModuleBean moduleBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		ModuleBean obj= prepareModuleBean(moduleService.getModule(moduleBean.getId()));
		model.put("module", obj);
		model.put("modules",  prepareListofBean(moduleService.listModule()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("modulegroups", beanMapper.prepareListofModuleGroupBean(moduleGroupService.listModuleGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editModule", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Module prepareModel(ModuleBean moduleBean){
	    Module module = new Module();

		module.setId(moduleBean.getId());
		module.setCode(moduleBean.getCode());
		module.setDescription(moduleBean.getDescription());
		module.setIcon(moduleBean.getIcon());
		module.setSortorder(moduleBean.getSortorder());

	    return module;
 }

 private List<ModuleBean> prepareListofBean(List<Module> modules){
        List<ModuleBean> beans = null;

        if(modules != null && !modules.isEmpty()){
        	beans = new ArrayList<ModuleBean>();
        	ModuleBean module = null;

        	for(Module item : modules){
        		module = new ModuleBean();

        		module.setId(item.getId());
        		module.setCode(item.getCode());
        		module.setDescription(item.getDescription());
        		module.setIcon(item.getIcon());
        		module.setSortorder(item.getSortorder());

			    beans.add(module);
		   }
	    }

        return beans;
 }

 private ModuleBean prepareModuleBean(Module module){
		  ModuleBean 	bean = new ModuleBean();

		  bean.setId(module.getId());
		  bean.setCode(module.getCode());
		  bean.setDescription(module.getDescription());
		  bean.setIcon(module.getIcon());
		  bean.setSortorder(module.getSortorder());

		  return bean;
 }

 public ModuleService getModuleService(){
	return moduleService;
 }

 public void setModuleService(ModuleService moduleService){
	this.moduleService = moduleService;
 }
}