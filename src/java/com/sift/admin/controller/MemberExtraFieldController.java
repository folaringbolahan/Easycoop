/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;

import com.sift.admin.bean.MemberExtraFieldBean;
import com.sift.admin.model.MemberExtraField;
import com.sift.admin.model.User;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.MemberExtraFieldService;
import com.sift.admin.service.UserService;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.EazyCoopUtility;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class MemberExtraFieldController {
    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 @Autowired
    private CompanyService companyService;	
 @Autowired
 private MemberExtraFieldService memberExtraFieldService;
  @Autowired
private UserService userService;
 
 EazyCoopUtility  eazyCoopUTIL=new EazyCoopUtility();
  BeanMapperUtility beanMapper = new BeanMapperUtility();
 
 @RequestMapping(value = "/saveMemberExtraField", method = RequestMethod.POST)
public ModelAndView saveMemberExtraField( @ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result,HttpServletRequest req){
       String actionId=req.getParameter("ACTION_ID");
       String option=req.getParameter("grouped");
       
	  String logonUser=eazyCoopUTIL.getLoggedonUser(req);
         User currentUser=userService.getUserByEmail(logonUser);
     
     System.out.println("logonUser:="+ logonUser);
     System.out.println("currentUser.getCompanyId():="+ currentUser.getCompanyId());	 	 
     
	 if("2".equals(actionId)){
		memberExtraFieldBean.setCreatedBy(memberExtraFieldService.getMemberExtraField(memberExtraFieldBean.getId()).getCreatedBy());
		memberExtraFieldBean.setLastModifiedBy(req.getRemoteUser());
		memberExtraFieldBean.setLastModificationDate(new java.util.Date());
	 }
         if("N".equals(option)){
             memberExtraFieldBean.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
            memberExtraFieldBean.setBranchid(Integer.parseInt(currentUser.getBranchId()));
            memberExtraFieldBean.setLastModificationDate(new java.util.Date());
            memberExtraFieldBean.setCreationDate(new java.util.Date());
             MemberExtraField memberExtraField = prepareModel(memberExtraFieldBean);
	    memberExtraFieldService.addMemberExtraField(memberExtraField);
          return new ModelAndView("redirect:/newMemberExtraField");
         }else{
             
              memberExtraFieldBean.setCompanyid(Integer.parseInt(currentUser.getCompanyId()));
            memberExtraFieldBean.setBranchid(Integer.parseInt(currentUser.getBranchId()));
            memberExtraFieldBean.setLastModificationDate(new java.util.Date());
            memberExtraFieldBean.setCreationDate(new java.util.Date());
             MemberExtraField memberExtraField = prepareModel(memberExtraFieldBean);
	    memberExtraFieldService.addMemberExtraField(memberExtraField);
	   return new ModelAndView("redirect:/extrafield");
         }
 }
 //for Registrar Admin
  @RequestMapping(value = "/saveManageField", method = RequestMethod.POST)
public ModelAndView savesaveManageField( @ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result,HttpServletRequest req){
       String actionId=req.getParameter("ACTION_ID");
       String option=req.getParameter("grouped");
       
	 if("2".equals(actionId)){
		memberExtraFieldBean.setCreatedBy(memberExtraFieldService.getMemberExtraField(memberExtraFieldBean.getId()).getCreatedBy());
		memberExtraFieldBean.setLastModifiedBy(req.getRemoteUser());
		memberExtraFieldBean.setLastModificationDate(new java.util.Date());
	 }
         if("N".equals(option)){
             memberExtraFieldBean.setLastModificationDate(new java.util.Date());
            memberExtraFieldBean.setCreationDate(new java.util.Date());
             MemberExtraField memberExtraField = prepareModel(memberExtraFieldBean);
	    memberExtraFieldService.addMemberExtraField(memberExtraField);
          return new ModelAndView("redirect:/manageField");
         }else{
             memberExtraFieldBean.setLastModificationDate(new java.util.Date());
            memberExtraFieldBean.setCreationDate(new java.util.Date());
             MemberExtraField memberExtraField = prepareModel(memberExtraFieldBean);
	    memberExtraFieldService.addMemberExtraField(memberExtraField);
	   return new ModelAndView("redirect:/manageExtraField");
         }
 }
  @RequestMapping(value = "/updateField", method = RequestMethod.POST)
public ModelAndView updateField( @ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result,HttpServletRequest req){
         
	
           memberExtraFieldBean.setCreationDate(new java.util.Date());
            memberExtraFieldBean.setLastModificationDate(new java.util.Date());
            MemberExtraField memberExtraField = prepareModel(memberExtraFieldBean);
	    memberExtraFieldService.addMemberExtraField(memberExtraField);
	   return new ModelAndView("redirect:/manageField");
         
 }
 
 @RequestMapping(value="/memberExtraField", method = RequestMethod.GET)
 public ModelAndView listMemberExtraField() {
     
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("memberExtraFieldList",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
         System.out.println("i am here 2" );
	 return new ModelAndView("addExtraField", model);
 }
 
 @RequestMapping(value = "/newMemberExtraField", method = RequestMethod.GET)
 public ModelAndView addMemberExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("memberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
         model.put("listAllFields",  prepareListofBean(memberExtraFieldService.listAllMemberExtraField()));
          model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
          return new ModelAndView("addExtraField", model);
 }
 
 //for Registrar admin
 @RequestMapping(value = "/manageField", method = RequestMethod.GET)
 public ModelAndView manageFields(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("memberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
         model.put("allMemberExtraFields",  prepareListofBean(memberExtraFieldService.listAllMemberExtraField()));
          model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
          return new ModelAndView("manageField", model);
 }
 @RequestMapping(value = "/editMemberExtraField", method = RequestMethod.GET)
public ModelAndView editMemberExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberExtraField", prepareMemberExtraFieldBean(memberExtraFieldService.getMemberExtraField(memberExtraFieldBean.getId())));
		model.put("memberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
               
		return new ModelAndView("editMemberExtraField", model);               
 }
 //edit method for registrar
  @RequestMapping(value = "/editExtraField", method = RequestMethod.GET)
 public ModelAndView editExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ExtraField", prepareMemberExtraFieldBean(memberExtraFieldService.getMemberExtraField(memberExtraFieldBean.getId())));
		model.put("ExtraFields",  prepareListofBean(memberExtraFieldService.listAllMemberExtraField()));
               
		return new ModelAndView("editExtraField", model);               
 }
  //delete method for registrar
  @RequestMapping(value = "/deleteExtraField", method = RequestMethod.GET)
public ModelAndView deleteExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
	    memberExtraFieldService.deleteMemberExtraField(prepareModel(memberExtraFieldBean));
		return new ModelAndView("redirect:/manageField");
 }
 
 @RequestMapping(value = "/deleteMemberExtraField", method = RequestMethod.GET)
public ModelAndView deleteMemberExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
	    memberExtraFieldService.deleteMemberExtraField(prepareModel(memberExtraFieldBean));
		//Map<String,Object> model = new HashMap<String,Object>();
		//model.put("memberExtraField", null);
		//model.put("memberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
		return new ModelAndView("redirect:/newMemberExtraField");
 }
 
 @RequestMapping(value = "/pendingExtraFieldApprv", method = RequestMethod.GET)
 public ModelAndView pendingExtraFieldApprv(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean, BindingResult result) {
	 Map<String, Object> model = new HashMap<String, Object>();
  	 model.put("memberExtraFields",  prepareListofBean(memberExtraFieldService.listMemberExtraField()));
         model.put("allExtraFieldapprvs",  prepareListofBean(memberExtraFieldService.listAllMemberExtraFieldForApproval()));
          model.put("companies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
          return new ModelAndView("pendingExtraFieldApprv", model);
 }
 @RequestMapping(value = "approveExtraField", method = RequestMethod.GET)
public ModelAndView approveExtraField(@ModelAttribute("memberExtraField")MemberExtraFieldBean memberExtraFieldBean,BindingResult result) {
	    memberExtraFieldService.approveMemberExtraField(prepareModel(memberExtraFieldBean));
		return new ModelAndView("redirect:/pendingExtraFieldApprv");
 }
private List<MemberExtraFieldBean> prepareListofBean(List<MemberExtraField> memberExtraFields){
        List<MemberExtraFieldBean> beans = null;
       System.out.println("i am here 3");
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
                          System.out.println("active "+ memberExtraField.getActive() );
			    beans.add(bean);
		   }
	    }

        return beans;
 }
 private MemberExtraField prepareModel(MemberExtraFieldBean memberExtraFieldBean){
	     MemberExtraField  memberExtraField = new  MemberExtraField();
             
	   memberExtraField.setId(memberExtraFieldBean.getId());
	   memberExtraField.setActive(memberExtraFieldBean.getActive());
           memberExtraField.setBranchid(memberExtraFieldBean.getBranchid());
            memberExtraField.setCompanyid(memberExtraFieldBean.getCompanyid());
            memberExtraField.setCreatedBy(memberExtraFieldBean.getCreatedBy());
            memberExtraField.setCreationDate(memberExtraFieldBean.getCreationDate());
           memberExtraField.setDeleted(memberExtraFieldBean.getDeleted());
            memberExtraField.setLastModificationDate(memberExtraFieldBean.getLastModificationDate());
           memberExtraField.setLastModifiedBy(memberExtraFieldBean.getLastModifiedBy());
           memberExtraField.setGrouped(memberExtraFieldBean.getGrouped());
           memberExtraField.setDescription(memberExtraFieldBean.getDescription());
         
	     System.out.println("This is inside prepare model");

	    return  memberExtraField;

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


