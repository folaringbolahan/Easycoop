/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;

import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.bean.VotMembersBean;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotMemberService;
import com.sift.votes.service.VoterscredgenService;
import java.util.HashMap;
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
public class PasswordResetController {
    @Autowired
    private VotAgmService votAgmService;
    
    @Autowired
    private VotMemberService votMemberService;
    
    @Autowired
    private VoterscredgenService voterscredgenService;
    
     @RequestMapping(value="/resetVotersPassword", method = RequestMethod.GET)
      public ModelAndView displayAgms(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result) {
	 Map<String ,Object> model = new HashMap<String, Object>();
         model.put("listAgms", votAgmService.listClosedAgms());
	 return new ModelAndView("passwordReset", model);
 } 
  @RequestMapping(value="/selectMembers", method = RequestMethod.POST)
      public ModelAndView selectMembers(@ModelAttribute("votMemb")VotMembersBean votMembersBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>();
         String agmid= req.getParameter("description");
         System.out.println("selected agmid is "+ agmid);
         model.put("listMembers",votMemberService.listApprovedAgmMembers(Integer.parseInt(agmid)) );
	 return new ModelAndView("agmMembPasswrd", model);
 }    
    @RequestMapping(value="/resetAndSendPasswrd", method = RequestMethod.POST)
      public ModelAndView resetPassword(@ModelAttribute("votMemb")VotMembersBean votMembersBean, BindingResult result,HttpServletRequest req) {
	 String redurlpath="";
        Map<String ,Object> model = new HashMap<String, Object>();
         String[] selectedMemberIds = req.getParameterValues("selectedMemberId");
         String[] selectedMails= req.getParameterValues("email");
         //loop through all selected members and pass the memberrefid to a service call
        // System.out.println("check 00");
         for(int i=0; i< selectedMemberIds.length; i++){
             //System.out.println("check 001 " + selectedMemberIds[i]);
            voterscredgenService.resetvoterscred(Integer.parseInt(selectedMemberIds[i]));
         }
         
        
	  redurlpath = "redirect:/doFeedback.htm?message= Password Reset Successful &redirectURI=resetVotersPassword";
        return new ModelAndView(redurlpath);
 }    
}
