/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.springframework.web.servlet.ModelAndView;

import com.sift.admin.model.MemberCategory;
import com.sift.admin.service.MemberCategoryService;

import com.sift.admin.bean.MemberCategoryBean;
import com.sift.admin.model.User;
import com.sift.admin.service.UserService;

import com.sift.loan.utility.EazyCoopUtility;


/**
 *
 * @author Nelson Akpos
 */
@Controller
public class MemberCategoryController {
    
 @Autowired
private UserService userService;
	
 @Autowired
 private MemberCategoryService memberCategoryService;
 
 
 EazyCoopUtility     eazyCoopUTIL=new EazyCoopUtility();
 
 @RequestMapping(value = "/saveMemberCategory", method = RequestMethod.POST)
public ModelAndView saveNameType( @ModelAttribute("memberCategory")MemberCategoryBean memberCategoryBean, BindingResult result,HttpServletRequest req){
        String logonUser=eazyCoopUTIL.getLoggedonUser(req);
         User currentUser=userService.getUserByEmail(logonUser);
     
     System.out.println("logonUser:="+ logonUser);
     System.out.println("currentUser.getCompanyId():="+ currentUser.getCompanyId());	 	 
     memberCategoryBean.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
     memberCategoryBean.setBranchid(Integer.parseInt(currentUser.getBranchId()));
     
     String actionId=req.getParameter("ACTION_ID");
	 
	if("2".equals(actionId)){
		memberCategoryBean.setCreatedBy(memberCategoryService.getMemberCategory(memberCategoryBean.getId()).getCreatedBy());
		 memberCategoryBean.setLastModifiedBy(req.getRemoteUser());
		memberCategoryBean.setLastModificationDate(new java.util.Date());     
	 }
	 
	 MemberCategory memberCategory = prepareModel(memberCategoryBean);
	 memberCategoryService.addMemberCategory(memberCategory);

	 return new ModelAndView("redirect:/newMemberCategory");
 }
 
 @RequestMapping(value="/memberCategories", method = RequestMethod.GET)
 public ModelAndView listMembercategories() {
      System.out.println("i am here 1");
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("memberCategoriesList",  prepareListofBean(memberCategoryService.listMemberCategories()));
         System.out.println("i am here 2" );
	 return new ModelAndView("addNameType", model);
 }
 
 @RequestMapping(value = "/newMemberCategory", method = RequestMethod.GET)
 public ModelAndView addMemberCategory(@ModelAttribute("memberCategory")MemberCategoryBean memberCategoryBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("memberCategorys",  prepareListofBean(memberCategoryService.listMemberCategories()));
     return new ModelAndView("addNameType", model);
 }
 
 @RequestMapping(value = "/editMemberCategory", method = RequestMethod.GET)
public ModelAndView editMemberCategory(@ModelAttribute("memberCategory")MemberCategoryBean memberCategoryBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberCategory", prepareMemberCategoryBean(memberCategoryService.getMemberCategory(memberCategoryBean.getId())));
		model.put("memberCategorys",  prepareListofBean(memberCategoryService.listMemberCategories()));
		return new ModelAndView("editMemberCategory", model);
 }
 
 @RequestMapping(value = "/deleteMemberCategory", method = RequestMethod.GET)
public ModelAndView deleteMemberCategory(@ModelAttribute("memberCategory")MemberCategoryBean memberCategoryBean,BindingResult result) {
	    memberCategoryService.deleteMemberCategory(prepareModel(memberCategoryBean));
		Map<String,Object> model = new HashMap<String,Object>();
		//model.put("memberCategory", null);
		//model.put("memberCategorys",  prepareListofBean(memberCategoryService.listMemberCategories()));
		//return new ModelAndView("editMemberC", model);
                 return new ModelAndView("redirect:/newMemberCategory");
 }
 private List<MemberCategoryBean> prepareListofBean(List<MemberCategory> memberCategories){
        List<MemberCategoryBean> beans = null;
       System.out.println("i am here 3");
        if( memberCategories != null && ! memberCategories.isEmpty()){
        	beans = new ArrayList<MemberCategoryBean>();
        	MemberCategoryBean bean = null;

        	for(MemberCategory memberCategory :memberCategories){
			    bean = new MemberCategoryBean();
                           
                            bean.setCompanyid(memberCategory.getCompanyid());
                            bean.setBranchid(memberCategory.getBranchid());
			    bean.setTypeName(memberCategory.getTypeName());
			    bean.setId(memberCategory.getId());
			    bean.setActive(memberCategory.getActive());
			    bean.setDeleted(memberCategory.getDeleted());
			    bean.setCreatedBy(memberCategory.getCreatedBy());
			    bean.setCreationDate(memberCategory.getCreationDate());
			    bean.setLastModifiedBy(memberCategory.getLastModifiedBy());
			    bean.setLastModificationDate(memberCategory.getLastModificationDate());
                          System.out.println("active "+ memberCategory.getActive() );
			    beans.add(bean);
		   }
	    }

        return beans;
 }
 private MemberCategory prepareModel(MemberCategoryBean memberCategoryBean){
	    MemberCategory memberCategory = new MemberCategory();
              System.out.println("Type Name "+ memberCategoryBean.getTypeName() );
	   memberCategory.setId(memberCategoryBean.getId());
	    memberCategory.setActive(memberCategoryBean.getActive());
            memberCategory.setBranchid(memberCategoryBean.getBranchid());
            memberCategory.setCompanyid(memberCategoryBean.getCompanyid());
            memberCategory.setCreatedBy(memberCategoryBean.getCreatedBy());
            memberCategory.setCreationDate(memberCategoryBean.getCreationDate());
            memberCategory.setDeleted(memberCategoryBean.getDeleted());
            memberCategory.setLastModificationDate(memberCategoryBean.getLastModificationDate());
            memberCategory.setLastModifiedBy(memberCategoryBean.getLastModifiedBy());
            memberCategory.setTypeName(memberCategoryBean.getTypeName());
	     System.out.println("This is inside prepare model");

	    return memberCategory;

 }
 private MemberCategoryBean prepareMemberCategoryBean(MemberCategory memberCategory){
		  MemberCategoryBean 	bean = new MemberCategoryBean();

		  bean.setTypeName(memberCategory.getTypeName());
		  bean.setId(memberCategory.getId());
		  bean.setActive(memberCategory.getActive());
		  bean.setDeleted(memberCategory.getDeleted());
		  bean.setCreatedBy(memberCategory.getCreatedBy());
		  bean.setCreationDate(memberCategory.getCreationDate());
		  bean.setLastModifiedBy(memberCategory.getLastModifiedBy());
		  bean.setLastModificationDate(memberCategory.getLastModificationDate());
                  bean.setBranchid(memberCategory.getBranchid());
                  bean.setCompanyid(memberCategory.getCompanyid());
		  return bean;
 }

}
