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
import com.sift.admin.model.Usergrpmdl;
import com.sift.admin.model.User;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.CountryService;
import com.sift.admin.service.ModuleMenuService;
import com.sift.admin.service.ModuleService;
import com.sift.admin.service.ReportGroupService;
import com.sift.admin.service.ReportsService;
import com.sift.admin.service.UserGroupService;
import com.sift.admin.service.UsergrpmdlService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.CountryAddressFilterBean;
import com.sift.admin.bean.ReportsBean;
import com.sift.admin.bean.UsergrpmdlBean;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.PassEncoder;

/**
 * @author XTOFFEL CONSULT
 **/
@Controller
public class UsergrpmdlController{

@Autowired
private CountryService countryService;

@Autowired
private CompanyService companyService;


@Autowired
private BranchService branchService;

@Autowired
private UsergrpmdlService usergrpmdlService;

@Autowired
private UserGroupService userGroupService;

@Autowired
private ModuleMenuService moduleMenuService;

@Autowired
private ReportGroupService reportGroupService;

@Autowired
private ReportsService reportsService;

@Autowired
private ModuleService moduleService;

@Autowired
private UserService userService;

BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveMenuAssign", method = RequestMethod.POST)
public ModelAndView saveUsergrpmdl(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));
	  	 model.put("usergrpmdl",  usergrpmdlBean);

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addUsergrpmdl":"editUsergrpmdl";
	     return new ModelAndView(URI, model);
	 }
	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	 //System.out.println("logonUser="+logonUser);	 
	 
	 String actionId=req.getParameter("ACTION_ID");
	 String selectedMenus[]=req.getParameterValues("selectedMenus");	 
	 String reportMenus[]=req.getParameterValues("selectedMenus2");	 
	 System.out.println("selectedMenus.length="+selectedMenus==null?"0":selectedMenus.length);
	 
	 Usergrpmdl usergrpmdl = prepareModel(usergrpmdlBean);
	 usergrpmdlService.addUsergrpmdl(usergrpmdl,selectedMenus,reportMenus);

	 //return new ModelAndView("redirect:/assignMenu.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=Menu Assignment/Re-Assignment was successful&redirectURI=assignMenu.htm");
 }

 @RequestMapping(value="/usergrpmdls", method = RequestMethod.GET)
 public ModelAndView listUsergrpmdls() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));
	 return new ModelAndView("usergrpmdlList", model);
 }
 
 @RequestMapping(value = "/usergroupmenulist", method = RequestMethod.GET)
 public @ResponseBody Map<String ,Object> userGroupMenuList(@RequestParam(value = "usergroup", required = true) String usergroup,HttpServletRequest req) {
	 Map<String ,Object> iMap = new HashMap<String, Object>();
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
	 List<ReportsBean> reportmenus=beanMapper.prepareListofReportsBean(reportsService.listReports());
	
	 for(ReportsBean item: reportmenus){
		System.out.println(""+item.getDescription());
	 }
	  if(currentUser.getGroupId().equalsIgnoreCase("BAdmin")){
              
                                iMap.put("usergrpmdls", prepareListofBean(usergrpmdlService.listUsergrpmdl(currentUser.getCompanyId(), currentUser.getBranchId(), usergroup)));
                                            }
              //else{    
                //  iMap.put("usergrpmdls", prepareListofBean(usergrpmdlService.listUsergrpmdl(usergroup)));
                           //}
	 iMap.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl(usergroup)));
	 
	 iMap.put("modules",  beanMapper.prepareListofModuleBean(moduleService.listModule()));
	 iMap.put("reports",  beanMapper.prepareListofReportGroupBean(reportGroupService.listReportGroup()));		 
	
	 if(currentUser.getGroupId().equalsIgnoreCase("CAdmin")){
		 iMap.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BA")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("BAdmin")){
		 iMap.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BU")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("Audit") || currentUser.getGroupId().equalsIgnoreCase("Acct")){
		 iMap.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("CA")));
	 }	
	 
	 iMap.put("reportmenus",  reportmenus);
	
	 return iMap;
 }

 @RequestMapping(value = "/assignMenu", method = RequestMethod.GET)
 public ModelAndView addUsergrpmdl(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean, BindingResult result,HttpServletRequest req){
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("modules",      beanMapper.prepareListofModuleBean(moduleService.listModule()));
  	   	   	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
  	 model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl(currentUser.getCompanyId(), currentUser.getBranchId())));

  	 if(currentUser.getGroupId().equalsIgnoreCase("CAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BA")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"1")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("BAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BU")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"2")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("Audit") || currentUser.getGroupId().equalsIgnoreCase("Acct")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("CA")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"0")));
	 }

     model.put("usergrpmdl",usergrpmdlBean);    
     return new ModelAndView("assignMenu", model);
 }
 
 @RequestMapping(value = "/assignMenuCA", method = RequestMethod.GET)
 public ModelAndView addUsergrpmdlCA(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));
  	 model.put("modules",      beanMapper.prepareListofModuleBean(moduleService.listModule()));
  	   	   	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 
	 if(currentUser.getGroupId().equalsIgnoreCase("CAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BA")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"1")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("BAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BU")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"2")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("Audit") || currentUser.getGroupId().equalsIgnoreCase("Acct")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("CA")));
		 model.put("usergroups",  beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"0")));
	 }

     model.put("usergrpmdl",usergrpmdlBean);    
     return new ModelAndView("assignMenu", model);
}
 
 
 @RequestMapping(value = "/assignMenuBA", method = RequestMethod.GET)
 public ModelAndView addUsergrpmdlBA(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));
  	 model.put("modules",      beanMapper.prepareListofModuleBean(moduleService.listModule()));
  	   	   	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(currentUser.getCompanyId())));
	 model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchsDistinct(currentUser.getBranchId())));
	 
	 if(currentUser.getGroupId().equalsIgnoreCase("CAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BA")));
		 model.put("usergroups",   beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"1")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("BAdmin")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("BU")));
		 model.put("usergroups",   beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"2")));
	 }else if(currentUser.getGroupId().equalsIgnoreCase("Audit") || currentUser.getGroupId().equalsIgnoreCase("Acct")){
		 model.put("modulemenus",  beanMapper.prepareListofModuleMenuBean(moduleMenuService.listModuleMenu("CA")));
		 model.put("usergroups",   beanMapper.prepareListofUserGroupBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"0")));
	 }

     model.put("usergrpmdl",usergrpmdlBean);    
     return new ModelAndView("assignMenu", model);
}

@RequestMapping(value = "/deleteUsergrpmdl", method = RequestMethod.GET)
public ModelAndView deleteUsergrpmdl(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean,BindingResult result,HttpServletRequest req) {
	    usergrpmdlService.deleteUsergrpmdl(prepareModel(usergrpmdlBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("usergrpmdl", null);
		model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));
		return new ModelAndView("addUsergrpmdl", model);
 }

@RequestMapping(value = "/editUsergrpmdl", method = RequestMethod.GET)
public ModelAndView editUsergrpmdl(@ModelAttribute("usergrpmdl")UsergrpmdlBean usergrpmdlBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		UsergrpmdlBean obj= prepareUsergrpmdlBean(usergrpmdlService.getUsergrpmdl(usergrpmdlBean.getId()));
		model.put("usergrpmdl", obj);
		model.put("usergrpmdls",  prepareListofBean(usergrpmdlService.listUsergrpmdl()));		
	    
	    //model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompaniesDistinct(obj.getCompanyId())));
	  	model.put("countries", beanMapper.prepareListofCountryBean(countryService.listCountry()));
		//model.put("usergrpmdlgroups", beanMapper.prepareListofUsergrpmdlGroupBean(usergrpmdlGroupService.listUsergrpmdlGroupsByCompanyId(obj.getCompanyId())));
		
	    return new ModelAndView("editUsergrpmdl", model);
}

@InitBinder
public void initBinder(WebDataBinder binder){
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
}

 private Usergrpmdl prepareModel(UsergrpmdlBean usergrpmdlBean){
	    Usergrpmdl usergrpmdl = new Usergrpmdl();

		usergrpmdl.setId(usergrpmdlBean.getId());
		usergrpmdl.setMenu(usergrpmdlBean.getMenu());
		usergrpmdl.setUsergroup(usergrpmdlBean.getUsergroup());
		usergrpmdl.setCompanyid(usergrpmdlBean.getCompanyid());
		usergrpmdl.setBranchid(usergrpmdlBean.getBranchid());

		return usergrpmdl;
 }

 private List<UsergrpmdlBean> prepareListofBean(List<Usergrpmdl> usergrpmdls){
        List<UsergrpmdlBean> beans = null;

        if(usergrpmdls != null && !usergrpmdls.isEmpty()){
        	beans = new ArrayList<UsergrpmdlBean>();
        	UsergrpmdlBean usergrpmdl = null;

        	for(Usergrpmdl item : usergrpmdls){
        		usergrpmdl = new UsergrpmdlBean();

        		usergrpmdl.setId(item.getId());
        		usergrpmdl.setMenu(item.getMenu());
        		usergrpmdl.setUsergroup(item.getUsergroup());
        		usergrpmdl.setCompanyid(item.getCompanyid());
        		usergrpmdl.setBranchid(item.getBranchid());

      		    beans.add(usergrpmdl);
		   }
	    }

        return beans;
 }

 private UsergrpmdlBean prepareUsergrpmdlBean(Usergrpmdl usergrpmdl){
		  UsergrpmdlBean 	bean = new UsergrpmdlBean();

		  bean.setId(usergrpmdl.getId());
		  bean.setMenu(usergrpmdl.getMenu());
		  bean.setUsergroup(usergrpmdl.getUsergroup());
		  bean.setCompanyid(usergrpmdl.getCompanyid());
		  bean.setBranchid(usergrpmdl.getBranchid());

		  return bean;
 }
}