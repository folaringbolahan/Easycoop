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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.UserRole;
import com.sift.admin.service.UserGroupService;
import com.sift.admin.service.UserRoleService;
import com.sift.admin.bean.BranchBean;
import com.sift.admin.bean.CompanyBean;
import com.sift.admin.bean.UserRoleBean;
import com.sift.loan.utility.HelperUtility;

/**
 * @author XTOFFEL CONSULT
 */
@Controller
public class UserRoleController{

@Autowired
private UserGroupService userGroupService;

@Autowired
private HelperUtility helperUTIL;

@Autowired
private UserRoleService userRoleService;

@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
public ModelAndView saveRole(@Valid @ModelAttribute("userRole")UserRoleBean userRoleBean,BindingResult result,HttpServletRequest req) {
	 if(result.hasErrors()) {
		 return new ModelAndView("addRole");
	 }
	
	 UserRole userRole = prepareModel(userRoleBean);
	 userRoleService.addUserRole(userRole);

	 return new ModelAndView("redirect:/newRole.htm");
 }

 @RequestMapping(value="/userRoles", method = RequestMethod.GET)
 public ModelAndView listUserRoles(){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("userRoles",  prepareListofBean(userRoleService.listUserRoles()));
	 return new ModelAndView("userRolesList", model);
 }

 @RequestMapping(value = "/roleByGroupAjaxList", method = RequestMethod.GET)
 public @ResponseBody List<UserRoleBean> rolesForGroup(
	  @RequestParam(value = "groupId", required = true) String id){
	  return prepareListofBean(userRoleService.listUserRoles(id));
 }

 @RequestMapping(value = "/newRole", method = RequestMethod.GET)
 public ModelAndView addUserRole(@ModelAttribute("userRole")UserRoleBean userRoleBean,BindingResult result){
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("userRoles",  prepareListofBean(userRoleService.listUserRoles()));
  	 model.put("userGroups", helperUTIL.getUserGroupBeanList());
     return new ModelAndView("addrole", model);
}

@RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
public ModelAndView deleteUserRole(@ModelAttribute("userRole")UserRoleBean userRoleBean,BindingResult result) {
	    userRoleService.deleteUserRole(prepareModel(userRoleBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("userRole", null);
		model.put("userRoles",  prepareListofBean(userRoleService.listUserRoles()));
		return new ModelAndView("addrole", model);
 }

@RequestMapping(value = "/editRole", method = RequestMethod.GET)
public ModelAndView editUserRole(@ModelAttribute("userRole")UserRoleBean userRoleBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userRole", prepareUserRoleBean(userRoleService.getUserRole(userRoleBean.getId())));
        //model.put("userGroups", helperUTIL.getUserGroupBeanList());
		model.put("userRoles",  prepareListofBean(userRoleService.listUserRoles()));
		return new ModelAndView("editrole", model);
 }

 private UserRole prepareModel(UserRoleBean userRoleBean){
	    UserRole userRole = new UserRole();

	    userRole.setId(userRoleBean.getId());
	    userRole.setRoleCode(userRoleBean.getRoleCode());
	    userRole.setRoleName(userRoleBean.getRoleName());
	    userRole.setActive(userRoleBean.getActive());
	    userRole.setDeleted(userRoleBean.getDeleted());
	    //userRole.setGroupId(userRoleBean.getGroupId());
	    /*userRole.setCreatedBy(userRoleBean.getCreatedBy());
	    userRole.setCreationDate(userRoleBean.getCreationDate());
	    userRole.setLastModifiedBy(userRoleBean.getLastModifiedBy());
	    userRole.setLastModificationDate(userRoleBean.getLastModificationDate());*/

	    return userRole;
 }

 private List<UserRoleBean> prepareListofBean(List<UserRole> userRoles){
        List<UserRoleBean> beans = null;

        if(userRoles != null && !userRoles.isEmpty()){
        	beans = new ArrayList<UserRoleBean>();
        	UserRoleBean bean = null;

        	for(UserRole userRole : userRoles){
			    bean = new UserRoleBean();

			    bean.setRoleName(userRole.getRoleName());
			    bean.setRoleCode(userRole.getRoleCode());
			    bean.setId(userRole.getId());
			    //bean.setGroupId(userRole.getGroupId());
			    bean.setActive(userRole.getActive());
			    bean.setDeleted(userRole.getDeleted());
			    /*bean.setCreatedBy(userRole.getCreatedBy());
			    bean.setCreationDate(userRole.getCreationDate());
			    bean.setLastModifiedBy(userRole.getLastModifiedBy());
			    bean.setLastModificationDate(userRole.getLastModificationDate());*/

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private UserRoleBean prepareUserRoleBean(UserRole userRole){
		  UserRoleBean 	bean = new UserRoleBean();

		  bean.setRoleCode(userRole.getRoleCode());
		  bean.setRoleName(userRole.getRoleName());
		  bean.setId(userRole.getId());
		  //bean.setGroupId(userRole.getGroupId());
		  bean.setActive(userRole.getActive());
		  bean.setDeleted(userRole.getDeleted());
		  /*bean.setCreatedBy(userRole.getCreatedBy());
		  bean.setCreationDate(userRole.getCreationDate());
		  bean.setLastModifiedBy(userRole.getLastModifiedBy());
		  bean.setLastModificationDate(userRole.getLastModificationDate());*/

		  return bean;
 }
}