/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.financial.member.controller;

import com.sift.financial.Result;
import com.sift.financial.member.Event;
import com.sift.financial.member.Member;
import com.sift.financial.member.MemberEnquiry;
import com.sift.financial.member.Status;
import com.sift.financial.member.StatusFlow;
import com.sift.financial.member.services.MemberManageImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author baydel200
 */
@Controller
public class MemberEnquiryController {
    
    
    private static final Log log = LogFactory.getLog(MemberEnquiryController.class);

	@Autowired
	private MemberManageImpl memberService;
	
	//@Autowired
	private ConversionService conversionService;

	@Resource (name="pageData")
	private Map<String, Map<String, Object>> pageData;
       
        @Autowired
	private CurrentuserdisplayImpl user;
        
        @Resource (name="enqEvents")
	private Map<String, String> enqEvents;
        
        
        @RequestMapping(value={"/enquiry/getMember","/enquiry/getAdminMember"}, method = RequestMethod.GET)
	//public View saveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status,HttpServletRequest req) 
	public ModelAndView getMemberInfo(HttpServletRequest req, HttpServletResponse res) 
        {
	   Map<String, List<Map<String,Object>>> model = null;
           String vwName ="/members/enquiry";
           
           ModelAndView mv = new ModelAndView(vwName);
           memberService.setUser(user);
           
           String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
           int pos1 = pattern.lastIndexOf("/");
           int pos2 = pattern.indexOf(".");
           String viewPage = pattern.substring(pos1+1, pos2);
           
           
           String curEventShort = getEnqEvents().get(viewPage);
           System.out.println("curEventShort :: " + curEventShort);
         
            Event curEvent = (Event)memberService.getEventDAO().findByEventShort(curEventShort).get(0);
            
            if(req.getParameter("id")!=null)
            {
                if(viewPage.equals("getMember"))
                {
                    model = memberService.doEnquiry(req.getParameter("id"), pageData,false, curEvent,req);
                }
                else if(viewPage.equals("getAdminMember"))
                {
                    model = memberService.doEnquiry(req.getParameter("id"), pageData, true, curEvent,req);
                }
                else
                {
                   model = null;
                }
                    
                if ( model != null)
                {
                    mv.addObject("pageData", pageData);
                    mv.addObject("data",model);
                    return mv;
                }
                else
                {
                    memberService.setMsg("Your request cannot be serviced....");
                }
            }
            else
            {
              memberService.setMsg("You have not accessed this page correctly....");
            }

         return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
	}

        
        @RequestMapping(value="/enquiry/searchMember", method = RequestMethod.POST)
	//public View saveMemberInfo(@ModelAttribute("member") Member member,BindingResult result, SessionStatus status,HttpServletRequest req) 
	public ModelAndView doSearchInfo(@ModelAttribute("memberEnquiry") MemberEnquiry memberEnquiry,BindingResult result, SessionStatus status,HttpServletRequest req) 
        {
	
            Result res = new Result();
            Map<String, String> theResult = new HashMap();
            memberService.setUser(user);
            
            status.setComplete();

            theResult.put("message", memberService.getMsg());
            res.setMessage(theResult);

            //return new RedirectView("/finResult/result.htm?textMsg=" + memberService.getMsg(),true);
         
         return new ModelAndView("redirect:/finResult/result.htm?textMsg=" + memberService.getMsg());
	}
        
        
        
        
        
        @RequestMapping(value={"/enquiry/enquire"} , method = {RequestMethod.GET})
	public ModelAndView doMemberEnquiry(@ModelAttribute("memberEnquiry") MemberEnquiry enquiry, HttpServletRequest req){
           
             
		Date today = new Date();
		System.out.println("here2");	
		enquiry = new MemberEnquiry();
		enquiry.setCreatedBy(user.getCurruser().getUserId());
		enquiry.setCreatedDate(today);
		//member.setAction("ADD");
                enquiry.getMember().setBranch(memberService.getBranchDAO().findById(user.getCurruser().getBranch()));
		enquiry.getMember().setCompany(memberService.getCompanyDAO().findById(user.getCurruser().getCompanyid()));
		System.out.println("here3");		
	
        return   new ModelAndView("/members/enquire", "memberEnquiry", enquiry);
	}
        
        
        @RequestMapping(value = {"/enquiry/enquiryList", "/enquiry/enquiryBranchList"}, method = RequestMethod.GET)
	public ModelAndView pendingMemberRequests(HttpServletRequest req, HttpServletResponse res){
		
		Map<String, Object> model = new HashMap<String, Object>();

                String vwName = "/members/";
                List<Object> requests= null;
                
                String pattern = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
                int pos1 = pattern.lastIndexOf("/");
                int pos2 = pattern.indexOf(".");
                String viewPage = pattern.substring(pos1+1, pos2);
                vwName = vwName + viewPage;
                
                if(viewPage.equalsIgnoreCase("enquiryList"))
                {
                  requests = memberService.getMemberDao().getMemberListByStatus(user.getCurruser().getCompanyid(), null); 
                }
                else if (viewPage.equalsIgnoreCase("enquiryBranchList"))
                {
                  //requests = memberService.getMemberDao().getMemberListByStatus(user.getCurruser().getCompanyid().toString(), null, user.getCurruser().getBranch().toString());
                }
        
		model.put("listMember", requests);   	 

	  	return new ModelAndView(vwName, model);
	
	 }
        
        
          public ConversionService getConversionService() {
		return conversionService;
	}
        
	public void setConversionService(ConversionService conversionService)
        {
		 this.conversionService=conversionService;
	}

    public MemberManageImpl getMemberService() {
        return memberService;
    }

    public void setMemberService(MemberManageImpl memberService) {
        this.memberService = memberService;
    }

    public Map<String, String> getEnqEvents() {
        return enqEvents;
    }

    public void setEnqEvents(Map<String, String> enqEvents) {
        this.enqEvents = enqEvents;
    }
    
        
        
    
}
