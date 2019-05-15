package com.sift.votes.controller;

import com.sift.gl.model.SMSBean;

import com.sift.votes.dao.CurrentvoterdisplayImpl;
import com.sift.votes.model.Activitylog;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotMembers;
import com.sift.votes.service.AuditlogService;
import com.sift.votes.service.VoterLimitLoginAuthenticationProvider;
import com.sift.votes.service.VoterscredgenService;
import com.sift.votes.service.VotersvotesService;
import com.sift.votes.service.VotesquestService;
import com.sift.webservice.service.LoanRequestServiceWS;
import com.sift.webservice.utility.WebServiceUtility;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 
 *
 */
@Controller
//@RequestMapping("votes/")
public class VotequestController {
    @Autowired 
     private CurrentvoterdisplayImpl currentvoterdisplayService;
    @Autowired
    private VotesquestService votesquestService;
    @Autowired 
    private VotersvotesService votersvotesService;
    @Autowired
    private VoterscredgenService voterscredgenService;
    @Autowired
    private VoterLimitLoginAuthenticationProvider votlogatauthenticationProvider;
    @Autowired
    private TaskExecutor taskExecutor;
    private VotMembers duser=new VotMembers();
    private VotAgm dcoy = new VotAgm();
    Integer dagmid = 1;
    String curremoteaddr ="";
    Integer currentuserid;
   
    @RequestMapping(value = "/votes/questions", method = RequestMethod.GET)
    public String viewVotequestions(ModelMap model,HttpServletRequest request) {
        //Integer dagmid = user.getCurruser().getAgmid();
        
        
        String currentUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
           currentUserName = authentication.getName();
        }
        currentuserid=votlogatauthenticationProvider.getMemberid();
        duser = currentvoterdisplayService.getdCurruser(currentuserid);
        dagmid=duser.getAgmid();
        dcoy = currentvoterdisplayService.getAgmdetails(dagmid);
        model.addAttribute("agmvotesession", votesquestService.buildVotpagession(dagmid,duser.getVoteunits()));
        model.addAttribute("agmdet", dcoy);
        String returnurl =  "votes/questions";
        // retrieve agm
        //VotAgm vtagm = new VotAgm();
       // if (dcoy.getBallotid().equals(1)==true) {
       //     duser.setVoteunits(1);
       // }
        Activitylog activity = new Activitylog();
        activity.setEvent(143);
        activity.setAction("VOT-LOGIN");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + currentuserid);
        activity.setActionResult("Success : " + currentuserid);
        activity.setDescription("");
        activity.setIpaddress(request.getRemoteHost());
        activity.setUsername(request.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(dcoy.getCompanyid());
        activity.setAgmid(dagmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        if (dcoy.getBallotid().equals(3)==true)
        {    
          returnurl =  "votes/questionssv";
        }
        return  returnurl;
    }
        
       
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = {"/votes/saveVotes"},method=RequestMethod.POST)
   public ModelAndView savevotes( @ModelAttribute("agm")VotAgm agmdet, BindingResult result,HttpServletRequest request,
			HttpServletResponse response)  {
     //String dcode = accountgroupdet2.getCode(testcred);//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dagm = 1;
     Integer dcompany = 0;
     
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
          
     String redurlpath;
     //voterscredgenService.setTaskExecutor(taskExecutor);
      redurlpath = "redirect:/votes/questions";
     // if success on save - 
     // update record as completed
      boolean success = false;
      
      success = votersvotesService.addvotes(dagmid, dcoy.getCompanyid(), currentuserid,duser.getVoteunits(),request);
      if (success==true)
      {    
        Activitylog activity = new Activitylog();
        activity.setEvent(144);
        activity.setAction("VOT-VOTS");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + currentuserid);
        activity.setActionResult("Success : " + currentuserid);
        activity.setDescription("");
        activity.setIpaddress(curremoteaddr);
        activity.setUsername(duser.getUserid());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(dcoy.getCompanyid());
        activity.setAgmid(dagmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
          new SecurityContextLogoutHandler().logout(request, response, auth);
          //redurlpath = "redirect:/votes/login.htm";
          redurlpath = "redirect:/votes/login.htm?rf="+dagmid.toString()+"&mg=sc";
          return new ModelAndView(redurlpath);
        }
      }
    return new ModelAndView(redurlpath);
   }
    
    
    
   @RequestMapping(value = {"/votes/saveVotestestcred"},method=RequestMethod.POST)
   public ModelAndView createvoterscredtestcred( @ModelAttribute("agm")VotAgm agmdet, BindingResult result)  {
     //String dcode = accountgroupdet2.getCode(testcred);//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dagm = 1;
     Integer dcompany = 0;
     
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
          
     String redurlpath;
     voterscredgenService.setTaskExecutor(taskExecutor);
     voterscredgenService.gencred(dagm);
     redurlpath = "redirect:/votes/questions";
     
    return new ModelAndView(redurlpath);
   }
   
  public void setUserdet(VotMembers userdet) {
      this.duser = userdet;
   }
   
   public void setCurrentvoterdisplayService(CurrentvoterdisplayImpl currentvoterdisplayService) {
      this.currentvoterdisplayService = currentvoterdisplayService;
   }
   
   
   @RequestMapping(value = "/votes/questionssv", method = RequestMethod.GET)
    public String viewVotequestionssv(ModelMap model,HttpServletRequest request) {
        //Integer dagmid = user.getCurruser().getAgmid();
        
        
        String currentUserName = "";
       // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // if (!(authentication instanceof AnonymousAuthenticationToken)) {
       //    currentUserName = authentication.getName();
       // }
       // currentuserid=votlogatauthenticationProvider.getMemberid();
       // duser = currentvoterdisplayService.getdCurruser(currentuserid);
       // dagmid=duser.getAgmid();
      //  dcoy = currentvoterdisplayService.getAgmdetails(dagmid);
        model.addAttribute("agmvotesession", votesquestService.buildVotpagession(dagmid,duser.getVoteunits()));
        model.addAttribute("agmdet", dcoy);
        String returnurl =  "votes/questionssv";
        // retrieve agm
        //VotAgm vtagm = new VotAgm();
       // if (dcoy.getBallotid().equals(1)==true) {
       //     duser.setVoteunits(1);
       // }
        /*
        Activitylog activity = new Activitylog();
        activity.setEvent(143);
        activity.setAction("VOT-LOGIN");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + currentuserid);
        activity.setActionResult("Success : " + currentuserid);
        activity.setDescription("");
        activity.setIpaddress(request.getRemoteHost());
        activity.setUsername(request.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(dcoy.getCompanyid());
        activity.setAgmid(dagmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        if (dcoy.getBallotid().equals(2)==true)
        {    
          returnurl =  "votes/questionssv";
        }
        */
        return  returnurl;
    }
   
    @RequestMapping(value = {"/votes/saveVotessv"},method=RequestMethod.POST)
   public ModelAndView savevotessv( @ModelAttribute("agm")VotAgm agmdet, BindingResult result,HttpServletRequest request,
			HttpServletResponse response)  {
     //String dcode = accountgroupdet2.getCode(testcred);//ServletRequestUtils.getRequiredStringParameter(request, "code");@ModelAttribute("clientIpAddress") String clientIpAddress,
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dacno = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     String dacstruct = "";
     Integer dagm = 1;
     Integer dcompany = 0;
     
     
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
     String opendateinstring = "";
          
     String redurlpath;
     //voterscredgenService.setTaskExecutor(taskExecutor);
      redurlpath = "redirect:/votes/questionssv";
     // if success on save - 
     // update record as completed
      boolean success = false;
      
      success = votersvotesService.addvotessv(dagmid, dcoy.getCompanyid(), currentuserid,duser.getVoteunits(),request);
      if (success==true)
      {    
        Activitylog activity = new Activitylog();
        activity.setEvent(144);
        activity.setAction("VOT-VOTS");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + currentuserid);
        activity.setActionResult("Success : " + currentuserid);
        activity.setDescription("");
        activity.setIpaddress(curremoteaddr);
        activity.setUsername(duser.getUserid());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(dcoy.getCompanyid());
        activity.setAgmid(dagmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
          new SecurityContextLogoutHandler().logout(request, response, auth);
          //redurlpath = "redirect:/votes/login.htm";
          redurlpath = "redirect:/votes/login.htm?rf="+dagmid.toString()+"&mg=sc";
          return new ModelAndView(redurlpath);
        }
      }
    return new ModelAndView(redurlpath);
   }
   
}