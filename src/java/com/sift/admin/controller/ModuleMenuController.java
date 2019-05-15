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
import com.sift.admin.model.ModuleMenu;
import com.sift.admin.model.User;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ModuleMenuService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.ModuleMenuBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class ModuleMenuController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;

@Autowired
private ModuleMenuService moduleMenuService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveModuleMenu", method = RequestMethod.POST)
public ModelAndView saveModuleMenu(@ModelAttribute("moduleMenu")ModuleMenuBean moduleMenuBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("moduleMenus",  prepareListofBean(moduleMenuService.listModuleMenu()));
	  	 model.put("moduleMenu",  moduleMenuBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addModuleMenu":"editModuleMenu";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 ModuleMenu moduleMenu = prepareModel(moduleMenuBean);
	 moduleMenuService.addModuleMenu(moduleMenu);

	 return new ModelAndView("redirect:/newModuleMenu.htm");
 }

 @RequestMapping(value="/moduleMenus", method = RequestMethod.GET)
 public ModelAndView listModuleMenus() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("moduleMenus",  prepareListofBean(moduleMenuService.listModuleMenu()));
	 return new ModelAndView("moduleMenuList", model);
 }

 @RequestMapping(value = "/newModuleMenu", method = RequestMethod.GET)
 public ModelAndView addModuleMenu(@ModelAttribute("moduleMenu")ModuleMenuBean moduleMenuBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("moduleMenus",  prepareListofBean(moduleMenuService.listModuleMenu()));
  	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
  	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
		 	 
     model.put("moduleMenu",moduleMenuBean);     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
	 //model.put("moduleMenugroups", beanMapper.prepareListofModuleMenuGroupBean(moduleMenuGroupService.listModuleMenuGroupsByCompanyId(currentUser.getCompanyId())));
	 
     return new ModelAndView("addModuleMenu", model);
 }


@RequestMapping(value = "/deleteModuleMenu", method = RequestMethod.GET)
public ModelAndView deleteModuleMenu(@ModelAttribute("moduleMenu")ModuleMenuBean moduleMenuBean,BindingResult result,HttpServletRequest req) {
	    moduleMenuService.deleteModuleMenu(prepareModel(moduleMenuBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("moduleMenu", null);
		model.put("moduleMenus",  prepareListofBean(moduleMenuService.listModuleMenu()));
		return new ModelAndView("addModuleMenu", model);
 }

@RequestMapping(value = "/editModuleMenu", method = RequestMethod.GET)
public ModelAndView editModuleMenu(@ModelAttribute("moduleMenu")ModuleMenuBean moduleMenuBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		ModuleMenuBean obj= prepareModuleMenuBean(moduleMenuService.getModuleMenu(moduleMenuBean.getId()));
		model.put("moduleMenu", obj);
		model.put("moduleMenus",  prepareListofBean(moduleMenuService.listModuleMenu()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("moduleMenugroups", beanMapper.prepareListofModuleMenuGroupBean(moduleMenuGroupService.listModuleMenuGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editModuleMenu", model);
 }

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private ModuleMenu prepareModel(ModuleMenuBean moduleMenuBean){
	    ModuleMenu moduleMenu = new ModuleMenu();

	    moduleMenu.setId(moduleMenuBean.getId());
		moduleMenu.setId(moduleMenuBean.getId());
		moduleMenu.setModule(moduleMenuBean.getModule());
		moduleMenu.setMenucode(moduleMenuBean.getMenucode());
		moduleMenu.setMenupath(moduleMenuBean.getMenupath());
		moduleMenu.setMenusortorder(moduleMenuBean.getMenusortorder());
		moduleMenu.setMenurole(moduleMenuBean.getMenurole());
		moduleMenu.setDisplaytext(moduleMenuBean.getDisplaytext());
		moduleMenu.setAccessLevelCode(moduleMenuBean.getAccessLevelCode());

		return moduleMenu;
 }

 private List<ModuleMenuBean> prepareListofBean(List<ModuleMenu> moduleMenus){
        List<ModuleMenuBean> beans = null;

        if(moduleMenus != null && !moduleMenus.isEmpty()){
        	beans = new ArrayList<ModuleMenuBean>();
        	ModuleMenuBean moduleMenu = null;

        	for(ModuleMenu item : moduleMenus){
        		moduleMenu = new ModuleMenuBean();

        		moduleMenu.setId(item.getId());
        		moduleMenu.setModule(item.getModule());
        		moduleMenu.setMenucode(item.getMenucode());
        		moduleMenu.setMenupath(item.getMenupath());
        		moduleMenu.setMenusortorder(item.getMenusortorder());
        		moduleMenu.setMenurole(item.getMenurole());
        		moduleMenu.setDisplaytext(item.getDisplaytext());
        		moduleMenu.setAccessLevelCode(item.getAccessLevelCode());

        		beans.add(moduleMenu);
		   }
	    }

        return beans;
 }

 private ModuleMenuBean prepareModuleMenuBean(ModuleMenu moduleMenu){
		  ModuleMenuBean 	bean = new ModuleMenuBean();

		  bean.setId(moduleMenu.getId());
		  bean.setModule(moduleMenu.getModule());
		  bean.setMenucode(moduleMenu.getMenucode());
		  bean.setMenupath(moduleMenu.getMenupath());
		  bean.setMenusortorder(moduleMenu.getMenusortorder());
		  bean.setMenurole(moduleMenu.getMenurole());
		  bean.setDisplaytext(moduleMenu.getDisplaytext());
		  bean.setAccessLevelCode(moduleMenu.getAccessLevelCode());

		  return bean;
 }

 public ModuleMenuService getModuleMenuService(){
	return moduleMenuService;
 }

public void setModuleMenuService(ModuleMenuService moduleMenuService){
	this.moduleMenuService = moduleMenuService;
}
}