/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;

import com.sift.admin.bean.MemberExtraFieldBean;
import com.sift.admin.model.MemberExtraField;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.MemberExtraFieldService;

import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import com.sift.loan.utility.HelperUtility;
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

/**
 *
 * @author Nelson Akpos
 */
@Controller
public class MemberGroupController {
     @Autowired
    private CompanyService companyService;	
 @Autowired
 private MemberExtraFieldService memberExtraFieldService;
 @Autowired
   private HelperUtility helperUTIL;
  
 
 EazyCoopUtility  eazyCoopUTIL=new EazyCoopUtility();
  BeanMapperUtility beanMapper = new BeanMapperUtility();
    
 @RequestMapping(value = "/setMemberGroup", method = RequestMethod.GET)
 public ModelAndView manageFields(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	
         model.put("allMemberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraFieldGrouped()));
          model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
          return new ModelAndView("membergroup", model);
 }   
 
 @RequestMapping(value = "/viewGroupedExtrafield", method = RequestMethod.GET)
 public ModelAndView viewGroupedExtrafield(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ExtraField", prepareMemberExtraFieldBean(memberExtraFieldService.getMemberExtraField(memberExtraFieldBean.getId())));   
		return new ModelAndView("viewgroupedfield", model);               
 }
  @RequestMapping(value = "/saveMemberGroup", method = RequestMethod.POST)
 public ModelAndView saveMemberGroup(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result, HttpServletRequest req) {
		 String redurlpath="";
                 Map<String, Object> model = new HashMap<String, Object>();    
                 String id =req.getParameter("id");
                 String companyid =req.getParameter("companyid");
                 String branchid =req.getParameter("branchid");
                 helperUTIL.deleteMemberGroupSettings(companyid);
                 helperUTIL.saveMemberGroupSettings( id,companyid,branchid);
		 redurlpath = "redirect:/doFeedback.htm?message= Successfully Set as Member Group&redirectURI=setMemberGroup";   
		return new ModelAndView(redurlpath);               
 }
    @RequestMapping(value = "/deleteMemberGroup", method = RequestMethod.GET)
public ModelAndView deleteExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
	        helperUTIL.deleteMemberGroupSettingById(memberExtraFieldBean.getId());
		return new ModelAndView("redirect:/setMemberGroup");
 }
 private List<MemberExtraFieldBean> prepareListofBean(List<MemberExtraField> memberExtraFields){
        List<MemberExtraFieldBean> beans = null;
     
        if( memberExtraFields!= null && ! memberExtraFields.isEmpty()){
        	beans = new ArrayList<MemberExtraFieldBean>();
        	MemberExtraFieldBean bean = null;

        	for(MemberExtraField memberExtraField :memberExtraFields){
			    bean = new MemberExtraFieldBean();
                           
                            bean.setCompanyid(memberExtraField.getCompanyid());
                            
                            bean.setBranchid(memberExtraField.getBranchid());
			   
			    bean.setId(memberExtraField.getId());
                           
			    bean.setActive(memberExtraField.getActive());
			    bean.setDeleted(memberExtraField.getDeleted());
			    bean.setCreatedBy(memberExtraField.getCreatedBy());
			    bean.setCreationDate(memberExtraField.getCreationDate());
			    bean.setLastModifiedBy(memberExtraField.getLastModifiedBy());
			    bean.setLastModificationDate(memberExtraField.getLastModificationDate());
                            bean.setDescription(memberExtraField.getDescription());
                            bean.setGrouped(memberExtraField.getGrouped());
                            bean.setCompanyName(companyService.getCompany(memberExtraField.getCompanyid()).getName());
                            bean.setGroupstatus(helperUTIL.checkifMemberGroupExist(memberExtraField.getId(),memberExtraField.getCompanyid(),memberExtraField.getBranchid()));
                             
			    beans.add(bean);
		   }
	    }

        return beans;
 }
  private MemberExtraFieldBean prepareMemberExtraFieldBean(MemberExtraField memberExtraField){
		  MemberExtraFieldBean 	bean = new MemberExtraFieldBean();

		  
		  bean.setId(memberExtraField.getId());
		  bean.setActive(memberExtraField.getActive());
		  bean.setDeleted(memberExtraField.getDeleted());
		  bean.setCreatedBy(memberExtraField.getCreatedBy());
		  bean.setCreationDate(memberExtraField.getCreationDate());
		  bean.setLastModifiedBy(memberExtraField.getLastModifiedBy());
		  bean.setLastModificationDate(memberExtraField.getLastModificationDate());
                  bean.setBranchid(memberExtraField.getBranchid());
                  bean.setCompanyid(memberExtraField.getCompanyid());
                  bean.setDescription(memberExtraField.getDescription());
                  bean.setGrouped(memberExtraField.getGrouped());
                  bean.setCompanyName(companyService.getCompany(memberExtraField.getCompanyid()).getName());
                  
		  return bean;
 }
}
