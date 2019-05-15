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
import com.sift.admin.model.MemberView;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.bean.MemberViewBean;
/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class MemberViewController{

@Autowired
private MemberViewService memberViewService;

@RequestMapping(value = "/saveMemberView", method = RequestMethod.POST)
public ModelAndView saveMemberView(@ModelAttribute("member")MemberViewBean memberViewBean,BindingResult result,HttpServletRequest req) {
	 MemberView memberView = prepareModel(memberViewBean);
	 memberViewService.addMemberView(memberView);

	 return new ModelAndView("redirect:/newMember.htm");
 }

 @RequestMapping(value="/members", method = RequestMethod.GET)
 public ModelAndView listMembers() {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 model.put("members",  prepareListofBean(memberViewService.listMembers()));
	 return new ModelAndView("membersList", model);
 }

 @RequestMapping(value = "/newMemberView", method = RequestMethod.GET)
 public ModelAndView addMember(@ModelAttribute("member")MemberViewBean memberViewBean, BindingResult result) {
	    Map<String, Object> model = new HashMap<String, Object>();
  	    model.put("members",  prepareListofBean(memberViewService.listMembers()));
        return new ModelAndView("addMember", model);
 }

@RequestMapping(value = "/deleteMemberView", method = RequestMethod.GET)
public ModelAndView deleteMember(@ModelAttribute("member")MemberViewBean memberViewBean,BindingResult result) {
	    memberViewService.deleteMember(prepareModel(memberViewBean));
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("member", null);
		model.put("members",  prepareListofBean(memberViewService.listMembers()));
		return new ModelAndView("addMember", model);
 }

@RequestMapping(value = "/editMemberView", method = RequestMethod.GET)
public ModelAndView editMember(@ModelAttribute("member")MemberViewBean memberViewBean,BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("member", prepareMemberBean(memberViewService.getMember(memberViewBean.getMemberId())));
		model.put("members",  prepareListofBean(memberViewService.listMembers()));
		return new ModelAndView("editMember", model);
 }

 private MemberView prepareModel(MemberViewBean memberViewBean){
	    MemberView memberView = new MemberView();

	    memberView.setMemberId(memberViewBean.getMemberId());
	    memberView.setMemberTypeId(memberViewBean.getMemberTypeId());
	    memberView.setCompanyId(memberViewBean.getCompanyId());
	    memberView.setBranchId(memberViewBean.getBranchId());
	    memberView.setCompmemberId(memberViewBean.getCompmemberId());
	    memberView.setMemberNo(memberViewBean.getMemberNo());

	    memberView.setMiddlename(memberViewBean.getMiddlename());
	    memberView.setStatusId(memberViewBean.getStatusId());
	    memberView.setGender(memberViewBean.getGender());
	    memberView.setReligionId(memberViewBean.getReligionId());
	    memberView.setFirstname(memberViewBean.getFirstname());
	    memberView.setSurname(memberViewBean.getSurname());
	    memberView.setIdentificationCode(memberViewBean.getIdentificationCode());
	    memberView.setIdentificationId(memberViewBean.getIdentificationId());
	    memberView.setDob(memberViewBean.getDob());
	    memberView.setDelDate(memberViewBean.getDelDate());
	    memberView.setDelFlg(memberViewBean.getDelFlg());
	    memberView.setCreatedBy(memberViewBean.getCreatedBy());
	    memberView.setCreatedDate(memberViewBean.getCreatedDate());
	    
	    memberView.setEmail(memberViewBean.getEmail());
	    memberView.setEmail2(memberViewBean.getEmail2());
	    memberView.setEmail3(memberViewBean.getEmail3());

	    memberView.setPhone1(memberViewBean.getPhone1());
	    memberView.setPhone2(memberViewBean.getPhone2());
	    memberView.setPhone3(memberViewBean.getPhone3());
	    
	    memberView.setBankAccount(memberViewBean.getBankAccount());
	    
	    return memberView;
 }

 private List<MemberViewBean> prepareListofBean(List<MemberView> memberViews){
        List<MemberViewBean> beans = null;

        if(memberViews != null && !memberViews.isEmpty()){
        	beans = new ArrayList<MemberViewBean>();
        	MemberViewBean bean = null;

        	for(MemberView memberView : memberViews){
			    bean = new MemberViewBean();

			    bean.setMemberId(memberView.getMemberId());
			    bean.setMemberTypeId(memberView.getMemberTypeId());
			    bean.setCompanyId(memberView.getCompanyId());
			    bean.setBranchId(memberView.getBranchId());
			    bean.setCompmemberId(memberView.getCompmemberId());
			    bean.setMemberNo(memberView.getMemberNo());
			    bean.setMiddlename(memberView.getMiddlename());
			    bean.setStatusId(memberView.getStatusId());
			    bean.setGender(memberView.getGender());
			    bean.setReligionId(memberView.getReligionId());
			    bean.setFirstname(memberView.getFirstname());
			    bean.setSurname(memberView.getSurname());
			    bean.setIdentificationCode(memberView.getIdentificationCode());
			    bean.setIdentificationId(memberView.getIdentificationId());
			    bean.setDob(memberView.getDob());
			    bean.setDelDate(memberView.getDelDate());
			    bean.setDelFlg(memberView.getDelFlg());
			    bean.setCreatedBy(memberView.getCreatedBy());
			    bean.setCreatedDate(memberView.getCreatedDate());
			    
			    bean.setEmail(memberView.getEmail());
			    bean.setEmail2(memberView.getEmail2());
			    bean.setEmail3(memberView.getEmail3());

			    bean.setPhone1(memberView.getPhone1());
			    bean.setPhone2(memberView.getPhone2());
			    bean.setPhone3(memberView.getPhone3());
			    
			    bean.setBankAccount(memberView.getBankAccount());

			    beans.add(bean);
		   }
	    }

        return beans;
 }

 private MemberViewBean prepareMemberBean(MemberView memberView){
		    MemberViewBean 	bean = new MemberViewBean();

		    bean.setMemberId(memberView.getMemberId());
		    bean.setMemberTypeId(memberView.getMemberTypeId());
		    bean.setCompanyId(memberView.getCompanyId());
		    bean.setBranchId(memberView.getBranchId());
		    bean.setCompmemberId(memberView.getCompmemberId());
		    bean.setMemberNo(memberView.getMemberNo());
		    bean.setMiddlename(memberView.getMiddlename());
		    bean.setStatusId(memberView.getStatusId());
		    bean.setGender(memberView.getGender());
		    bean.setReligionId(memberView.getReligionId());
		    bean.setFirstname(memberView.getFirstname());
		    bean.setSurname(memberView.getSurname());
		    bean.setIdentificationCode(memberView.getIdentificationCode());
		    bean.setIdentificationId(memberView.getIdentificationId());
		    bean.setDob(memberView.getDob());
		    bean.setDelDate(memberView.getDelDate());
		    bean.setDelFlg(memberView.getDelFlg());
		    bean.setCreatedBy(memberView.getCreatedBy());
		    bean.setCreatedDate(memberView.getCreatedDate());
		    bean.setEmail(memberView.getEmail());
		    bean.setEmail2(memberView.getEmail2());
		    bean.setEmail3(memberView.getEmail3());

		    bean.setPhone1(memberView.getPhone1());
		    bean.setPhone2(memberView.getPhone2());
		    bean.setPhone3(memberView.getPhone3());
		    
		    bean.setBankAccount(memberView.getBankAccount());
		    
		    return bean;
   }
}