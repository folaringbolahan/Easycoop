/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.controller;

import com.sift.admin.bean.MemberExtraFieldBean;
import com.sift.admin.bean.MemberExtraFieldGrpBean;
import com.sift.admin.model.MemberExtraField;
import com.sift.admin.model.MemberExtraFieldGrp;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.MemberExtraFieldGrpService;
import com.sift.admin.service.MemberExtraFieldService;
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
public class MemberExtraFieldGrpController {

    @Autowired
    MemberExtraFieldGrpService memberExtraFieldGrpService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    MemberExtraFieldService memberExtraFieldService;

    @RequestMapping(value = "/saveMemberExtraFieldGrp", method = RequestMethod.POST)
    public ModelAndView saveMemberExtraFieldGrp(@ModelAttribute("memberExtraFieldGrp") MemberExtraFieldGrpBean memberExtraFieldGrpBean, BindingResult result, HttpServletRequest req) {
        String groupid = req.getParameter("groupid");
        memberExtraFieldGrpBean.setGroupid(Integer.parseInt(groupid));
        MemberExtraFieldGrp memberExtraFieldGrp = prepareModel(memberExtraFieldGrpBean);
        memberExtraFieldGrpService.addMemberExtraFieldGrp(memberExtraFieldGrp);

        return new ModelAndView("redirect:/manageExtraField");
    }
    
    
   
    @RequestMapping(value = "/memberExtraFieldGrp", method = RequestMethod.GET)
    public ModelAndView listmemberExtraFieldGrps() {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("memberExtraFieldGrps", prepareListofBean(memberExtraFieldGrpService.ListMemberExtraFieldGrps()));
        return new ModelAndView("fieldsetup", model);
    }

    @RequestMapping(value = "/extrafield", method = RequestMethod.GET)
    public ModelAndView addMemberExtraFieldGrp(@ModelAttribute("memberExtraFieldGrp") MemberExtraFieldGrpBean memberExtraFieldGrpBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("memberExtraFieldGrps", prepareListofBean(memberExtraFieldGrpService.ListMemberExtraFieldGrps()));
        model.put("memberExtraFields", prepareListofFieldBean(memberExtraFieldService.listMemberExtraField()));
        return new ModelAndView("fieldsetup", model);
    }
    //For Admin

    @RequestMapping(value = "/manageExtraField", method = RequestMethod.GET)
    public ModelAndView manageExtraField(@ModelAttribute("memberExtraFieldGrp") MemberExtraFieldGrpBean memberExtraFieldGrpBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("memberExtraFieldGrps", prepareListofBean(memberExtraFieldGrpService.ListMemberExtraFieldGrps()));
        model.put("memberExtraFields", prepareListofFieldBean(memberExtraFieldService.listMemberExtraField()));

        return new ModelAndView("manageExtraField", model);
    }

    @RequestMapping(value = "/editMemberExtraFieldGrp", method = RequestMethod.GET)
    public ModelAndView editMemberExtraFieldGrp(@ModelAttribute("memberExtraFieldGrp") MemberExtraFieldGrpBean memberExtraFieldGrpBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("memberExtraFieldGrp", prepareMemberExtraFieldGrpBean(memberExtraFieldGrpService.getMemberExtraFieldGrp(memberExtraFieldGrpBean.getId())));
        model.put("memberExtraFieldGrps", prepareListofBean(memberExtraFieldGrpService.ListMemberExtraFieldGrps()));
        return new ModelAndView("editMemberExtraFieldGrp", model);
    }

    @RequestMapping(value = "/deleteMemberExtraFieldGrp", method = RequestMethod.GET)
    public ModelAndView deleteMemberExtraFieldGrp(@ModelAttribute("memberExtraFieldGrp") MemberExtraFieldGrpBean memberExtraFieldGrpBean, BindingResult result) {
        memberExtraFieldGrpService.deleteMemberExtraFieldGrp(prepareModel(memberExtraFieldGrpBean));
        Map<String, Object> model = new HashMap<String, Object>();
        //model.put("memberCategory", null);
        //model.put("memberCategorys",  prepareListofBean(memberCategoryService.listMemberCategories()));
        //return new ModelAndView("editMemberC", model);
        return new ModelAndView("redirect:/extrafield");
    }

    private MemberExtraFieldGrp prepareModel(MemberExtraFieldGrpBean memberExtraFieldGrpBean) {
        MemberExtraFieldGrp memberExtraFieldGrp = new MemberExtraFieldGrp();
        memberExtraFieldGrp.setId(memberExtraFieldGrpBean.getId());
        memberExtraFieldGrp.setFieldOption(memberExtraFieldGrpBean.getFieldOption());
      // int grpid = memberExtraFieldGrpBean.getGroupid();
        memberExtraFieldGrp.setGrpid(memberExtraFieldService.getMemberExtraField(memberExtraFieldGrpBean.getGroupid()));
       
       // memberExtraFieldGrp.setGroupid(); 
        //memberExtraFieldGrp.setGroupid();please remove the method i wrote and write yours, i just showed you how to 
        return memberExtraFieldGrp;

    }

    private List<MemberExtraFieldGrpBean> prepareListofBean(List<MemberExtraFieldGrp> memberExtraFieldGrps) {
        List<MemberExtraFieldGrpBean> beans = null;

        if (memberExtraFieldGrps != null && !memberExtraFieldGrps.isEmpty() && memberExtraFieldGrps.size() > 0) {
            beans = new ArrayList<MemberExtraFieldGrpBean>();
            MemberExtraFieldGrpBean bean = null;

            for (MemberExtraFieldGrp memberExtraFieldGrp : memberExtraFieldGrps) {
                bean = new MemberExtraFieldGrpBean();

                MemberExtraField grp = memberExtraFieldGrp.getGrpid();
                bean.setFieldOption(memberExtraFieldGrp.getFieldOption());
                bean.setGroupid(grp.getId());
                bean.setId(memberExtraFieldGrp.getId());
                bean.setDescription(grp.getDescription());
                bean.setCompanyName(companyService.getCompany(grp.getCompanyid()).getName());


                beans.add(bean);
            }
        }

        return beans;
    }

    private MemberExtraFieldGrpBean prepareMemberExtraFieldGrpBean(MemberExtraFieldGrp memberExtraFieldGrp) {
        MemberExtraFieldGrpBean bean = new MemberExtraFieldGrpBean();
          MemberExtraField grp = memberExtraFieldGrp.getGrpid();
        bean.setFieldOption(memberExtraFieldGrp.getFieldOption());
        bean.setId(memberExtraFieldGrp.getId());
        bean.setGroupid(memberExtraFieldGrp.getGrpid().getId());
        bean.setCompanyName(companyService.getCompany(grp.getCompanyid()).getName());

        return bean;
    }

    private List<MemberExtraFieldBean> prepareListofFieldBean(List<MemberExtraField> memberExtraFields) {
        List<MemberExtraFieldBean> beans = null;
        System.out.println("i am here 3");
        if (memberExtraFields != null && !memberExtraFields.isEmpty()) {
            beans = new ArrayList<MemberExtraFieldBean>();
            MemberExtraFieldBean bean = null;

            for (MemberExtraField memberExtraField : memberExtraFields) {
                bean = new MemberExtraFieldBean();

                bean.setCompanyid(memberExtraField.getCompanyid());

                System.out.println("company names " + memberExtraField.getCompanyid());
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
                System.out.println("active " + memberExtraField.getActive());
                beans.add(bean);
            }
        }

        return beans;
    }
}
