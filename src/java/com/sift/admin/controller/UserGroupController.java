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

import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.User;
import com.sift.admin.model.UserGroup;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.UserGroupService;
import com.sift.admin.service.UserService;
import com.sift.admin.bean.UserGroupBean;
import com.sift.gl.model.Activitylog;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class UserGroupController{

@Autowired
private CompanyService companyService;

@Autowired
private BranchService branchService;

@Autowired
private UserService userService;

@Autowired
private UserGroupService userGroupService;

@Autowired
private HelperUtility helperUTIL;


BeanMapperUtility   beanMapper =new BeanMapperUtility();
EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();

@RequestMapping(value = "/saveUserGroup", method = RequestMethod.POST)
public ModelAndView saveUserGroup(@Valid @ModelAttribute("userGroup")UserGroupBean userGroupBean,BindingResult result,HttpServletRequest req){
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     System.out.println("logonUser:="+ logonUser);
     System.out.println("currentUser.getCompanyId():="+ currentUser.getCompanyId());	 	 
     userGroupBean.setCompanyId(currentUser.getCompanyId());
     userGroupBean.setBranchId(currentUser.getBranchId());
     String actionId=req.getParameter("ACTION_ID");
     
	 if(result.hasErrors()) {
		 Map<String, Object> model = new HashMap<String, Object>();
	  	 model.put("userGroups",  prepareListofBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"1")));
	   	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));

		 String URI=req.getParameter("ACTION_ID").equals("1")? "addUserGroup":"editUserGroup";
	     return new ModelAndView(URI, model);
	 }
	 
	 UserGroup userGroup = prepareModel(userGroupBean);
	 Activitylog activity=new Activitylog();
	 boolean good=false;
	 
	 if("1".equals(actionId)){
		  good=userGroupService.addUserGroup(userGroup,true);	 
  
	      activity.setEvent(Definitions.EVENT_USER_GROUP_CREATION);
	      activity.setAction("User Group Setup for group: " + userGroup.getDescription());
	      activity.setActionDate(new java.util.Date());
	      activity.setActionItem("User Group Desc: " + userGroup.getDescription());
	      activity.setActionResult("User Group Setup for group: " + userGroup.getDescription());
	      activity.setDescription("User Group Setup for group: " + userGroup.getDescription());
	      activity.setIpaddress(req.getRemoteHost());
	      activity.setUsername(req.getRemoteUser());	
	      activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(userGroup.getCompanyId()));
	      activity.setToDate("");
                activity.setCompanyid(Integer.parseInt(userGroup.getCompanyId()));
            activity.setBranchid(Integer.parseInt(userGroup.getBranchId()));
	 }else{
		  good=userGroupService.addUserGroup(userGroup,false);
		 
	      activity.setEvent(Definitions.EVENT_USER_GROUP_UPDATE);
	      activity.setAction("User Group Details Modification for group: " + userGroup.getDescription());
	      activity.setActionDate(new java.util.Date());
	      activity.setActionItem("User Group Desc: " + userGroup.getDescription());
	      activity.setActionResult("User Group Details Modification for group: " + userGroup.getDescription());
	      activity.setDescription("User Group Details Modification for group: " + userGroup.getDescription());
	      activity.setIpaddress(req.getRemoteHost());
	      activity.setUsername(req.getRemoteUser());	
	      activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(userGroup.getCompanyId()));
	      activity.setToDate("");
               activity.setCompanyid(Integer.parseInt(userGroup.getCompanyId()));
            activity.setBranchid(Integer.parseInt(userGroup.getBranchId()));
	 }
     
	 if(good){try{eazyCoopUTIL.LogUserAction(activity);}catch(Exception ex){}}
	 
	 //return new ModelAndView("redirect:/newUserGroup.htm");
	 return new ModelAndView("redirect:/doFeedback.htm?message=User Group Record Setup/Update was successful&redirectURI=newUserGroup.htm");
 }

 @RequestMapping(value="/userGroups", method = RequestMethod.GET)
 public ModelAndView listUserGroups() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("userGroups",  prepareListofBean(userGroupService.listUserGroups()));
	 return new ModelAndView("userGroupsList", model);
 }

 @RequestMapping(value = "/newUserGroup", method = RequestMethod.GET)
 public ModelAndView addUserGroup(@ModelAttribute("userGroup")UserGroupBean userGroupBean, BindingResult result,HttpServletRequest req) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	    	 
	 String logonUser=eazyCoopUTIL.getLoggedonUser(req);
     User currentUser=userService.getUserByEmail(logonUser);
     
     System.out.println("logonUser:="+ logonUser);
     System.out.println("currentUser.getCompanyId():="+ currentUser.getCompanyId());	 	 
     userGroupBean.setCompanyId(currentUser.getCompanyId());
     userGroupBean.setBranchId(currentUser.getBranchId());
     
     //model.put("userGroups",  prepareListofBean(userGroupService.listUserGroups()));
  	 model.put("userGroups",  prepareListofBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"2")));
   	 model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
     
     model.put("userGroup", userGroupBean);
     return new ModelAndView("addUserGroup", model);
 }

@RequestMapping(value = "/deleteUserGroup", method = RequestMethod.GET)
public ModelAndView deleteUserGroup(@ModelAttribute("userGroup")UserGroupBean userGroupBean,BindingResult result) {
	    userGroupService.deleteUserGroup(prepareModel(userGroupBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("userGroup", null);
		model.put("userGroups",  prepareListofBean(userGroupService.listUserGroups()));
		return new ModelAndView("addUserGroup", model);
 }

@RequestMapping(value = "/editUserGroup", method = RequestMethod.GET)
public ModelAndView editUserGroup(@ModelAttribute("userGroup")UserGroupBean userGroupBean,BindingResult result,HttpServletRequest req) {
		Map<String, Object> model = new HashMap<String, Object>();
		 
		String logonUser=eazyCoopUTIL.getLoggedonUser(req);
	    User currentUser=userService.getUserByEmail(logonUser);
	     
	    System.out.println("logonUser:="+ logonUser);
	    System.out.println("currentUser.getCompanyId():="+ currentUser.getCompanyId());	 	 
	    userGroupBean.setCompanyId(currentUser.getCompanyId());
	    userGroupBean.setBranchId(currentUser.getBranchId());
	     
		UserGroupBean obj=  prepareUserGroupBean(userGroupService.getUserGroup(userGroupBean.getId()));
		
		model.put("userGroup",   obj);
	  	model.put("userGroups",  prepareListofBean(userGroupService.listUserGroupsByCompanyBranch(currentUser.getCompanyId(), currentUser.getBranchId(),"1")));
	   	model.put("companies",   beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
	   	model.put("branches",    beanMapper.prepareListofBranchBean(branchService.listBranchByCompany(obj.getCompanyId())));
		return new ModelAndView("editUserGroup", model);
 }

 private UserGroup prepareModel(UserGroupBean userGroupBean){
	    UserGroup userGroup = new UserGroup();

	    userGroup.setId(userGroupBean.getId());
	    userGroup.setActive(userGroupBean.getActive());
	    userGroup.setDeleted(userGroupBean.getDeleted());
	    userGroup.setDescription(userGroupBean.getDescription());
	    userGroup.setCode(userGroupBean.getCode());
	    userGroup.setCompanyId(userGroupBean.getCompanyId());
	    userGroup.setBranchId(userGroupBean.getBranchId());
	    userGroup.setAccessId(userGroupBean.getAccessId());
	    /*userGroup.setCreatedBy(userGroupBean.getCreatedBy());
	    userGroup.setCreationDate(userGroupBean.getCreationDate());
	    userGroup.setLastModifiedBy(userGroupBean.getLastModifiedBy());
	    userGroup.setLastModificationDate(userGroupBean.getLastModificationDate());*/
	    //userGroupBean.setId(null);

	    return userGroup;
 }

 private List<UserGroupBean> prepareListofBean(List<UserGroup> userGroups){
        List<UserGroupBean> beans = null;

        if(userGroups != null && !userGroups.isEmpty()){
        	beans = new ArrayList<UserGroupBean>();
        	UserGroupBean userGroup = null;

        	for(UserGroup userGroupBean : userGroups){
        		userGroup = new UserGroupBean();

        		userGroup.setId(userGroupBean.getId());
			    userGroup.setActive(userGroupBean.getActive());
			    userGroup.setDeleted(userGroupBean.getDeleted());
			    userGroup.setDescription(userGroupBean.getDescription());
			    userGroup.setCode(userGroupBean.getCode());
			    userGroup.setCompanyId(userGroupBean.getCompanyId());
			    userGroup.setBranchId(userGroupBean.getBranchId());
			    userGroup.setAccessId(userGroupBean.getAccessId());
			    /*userGroup.setCreatedBy(userGroupBean.getCreatedBy());
			    userGroup.setCreationDate(userGroupBean.getCreationDate());
			    userGroup.setLastModifiedBy(userGroupBean.getLastModifiedBy());
			    userGroup.setLastModificationDate(userGroupBean.getLastModificationDate());*/

			    beans.add(userGroup);
		   }
	    }

        return beans;
 }

 private UserGroupBean prepareUserGroupBean(UserGroup userGroup){
		  UserGroupBean 	bean = new UserGroupBean();

		  bean.setId(userGroup.getId());
		  bean.setActive(userGroup.getActive());
		  bean.setDeleted(userGroup.getDeleted());
		  bean.setDescription(userGroup.getDescription());
		  bean.setCode(userGroup.getCode());
		  bean.setCompanyId(userGroup.getCompanyId());
		  bean.setBranchId(userGroup.getBranchId());
		  bean.setAccessId(userGroup.getAccessId());

		  /*bean.setCreatedBy(userGroup.getCreatedBy());
		  bean.setCreationDate(userGroup.getCreationDate());
		  bean.setLastModifiedBy(userGroup.getLastModifiedBy());
		  bean.setLastModificationDate(userGroup.getLastModificationDate());*/

		  return bean;
 }
}