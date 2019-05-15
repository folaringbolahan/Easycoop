/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;


import com.sift.admin.bean.UserBean;
import com.sift.admin.model.Company;
import com.sift.admin.service.CompanyService;

import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.loan.utility.BeanMapperUtility;
import com.sift.loan.utility.HelperUtility;
import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.bean.VotBallottypesBean;

import com.sift.votes.bean.VotCompanyBean;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotBallottypes;
import com.sift.votes.model.VotCompany;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotBallottypesService;
import com.sift.votes.service.VotCompanyService;
import com.sift.votes.utility.AgmHelperUtility;
import com.sift.votes.utility.VotBeanMapperUtility;
import com.sift.votes.utility.VotMailBean;
import java.text.ParseException;
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
import com.sift.votes.model.Activitylog;
import com.sift.votes.service.AuditlogService;
import com.sift.votes.service.VoterscredgenService;
import org.springframework.core.task.TaskExecutor;
import java.text.DateFormat;
/**
 *
 * @author Nelson Akpos
 */
@Controller
public class AgmSetupController {
    
    @Autowired
    private VotCompanyService votCompanyService;
    
    @Autowired
    private CompanyService companyService;
    
     @Autowired
    private VotAgmService votAgmService;
    
      @Autowired
   private VotBallottypesService votBallottypesService;
    
      BeanMapperUtility beanMapper = new BeanMapperUtility();
      VotBeanMapperUtility votutility = new VotBeanMapperUtility();
     @Autowired
    private CurrentuserdisplayImpl user;
     
    @Autowired
    private  AgmHelperUtility agmhelperUTIL;
    @Autowired
    private VoterscredgenService voterscredgenService;
    @Autowired
    private TaskExecutor taskExecutor; 
    @RequestMapping(value="/createAgm", method = RequestMethod.GET)
      public ModelAndView createAgm(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result) {
	 Map<String ,Object> model = new HashMap<String, Object>();
	 return new ModelAndView("newAgm", model);
 } 
    @RequestMapping(value="/checkOption", method = RequestMethod.POST)
      public ModelAndView checkOption(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	String option=req.getParameter("importsource");
        if("E".equals(option)){
        return new ModelAndView("redirect:/companySetup2"); } 
        else{
       return new ModelAndView("redirect:/companySetup1");
         }
   
   }
    @RequestMapping(value="/companySetup1", method = RequestMethod.GET)
      public ModelAndView companySetup1(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
        Map<String ,Object> model = new HashMap<String, Object>();  
	  model.put("agmcompanies", beanMapper.prepareListofCompanyBean(companyService.listCompanies()));
         //  model.put("agmcompanies", prepareListofBean(votCompanyService.listCompanies()));
            return new ModelAndView("companySetup1", model);
   
   }
     @RequestMapping(value="/companySetup2", method = RequestMethod.GET)
      public ModelAndView companySetup2(@ModelAttribute("votCompany")VotCompanyBean votcompanyBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>();  

            return new ModelAndView("companySetup2", model);
   
   }
    @RequestMapping(value="/agmSetup", method = RequestMethod.POST)
      public ModelAndView agmSetup(@ModelAttribute("votCompany")VotCompanyBean votcompanyBean, BindingResult result,HttpServletRequest req)
    {   String id= req.getParameter("companyrefid"); 
    System.out.println("the company rf id is "+ id);
        int companyid= Integer.parseInt(id);
        Company company=companyService.getCompany(companyid);
         // votcompanyBean.setCompanyrefid();
           votcompanyBean.setCompanyname(company.getName());
        
	 Map<String ,Object> model = new HashMap<String, Object>(); 
         model.put("ballot", prepareListofBallottypesBean(votBallottypesService.listBallotypes()));
           VotCompany votcompany = prepareVotCompanyModel(votcompanyBean);
            votCompanyService.addCompanySetup(votcompany );
            Activitylog activity = new Activitylog();
        activity.setEvent(133);
        activity.setAction("CRE-VOT-COMPANY");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + votcompanyBean.getCompanyname() );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(0);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
         
            return new ModelAndView("redirect:/agmSetup2");
   
   }
    @RequestMapping(value="/agmSetup2", method = RequestMethod.GET)
      public ModelAndView agmSetup2(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          model.put("current", votCompanyService.getLastRecord());
          
          System.out.println("vot company obj "+ votCompanyService.getLastRecord() );
         model.put("ballot", prepareListofBallottypesBean(votBallottypesService.listBallotypes()));
            return new ModelAndView("agmSetup2", model);
   
   }
    @RequestMapping(value="/easycoopAgm", method = RequestMethod.GET)
      public ModelAndView easycoopAgm(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          model.put("current", votCompanyService.getLastRecord());
          
          System.out.println("vot company obj "+ votCompanyService.getLastRecord() );
         model.put("ballot", prepareListofBallottypesBean(votBallottypesService.listBallotypes()));
            return new ModelAndView("agmSetup", model);
   
   }
     @RequestMapping(value="/saveAgmSetup", method = RequestMethod.POST)
      public ModelAndView saveAgmSetup(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) throws ParseException {
          String redurlpath = null;
          String startdate= req.getParameter("startdate");
         System.out.println("the start date is "+ startdate);
          String enddate= req.getParameter("enddate");
         System.out.println("the end date is "+ enddate );
         System.out.println("start date from bean "+ votagmBean.getStartdate());
         System.out.println("End date from bean "+ votagmBean.getEnddate());
          //votagmBean.setCompanyid(id);
	 //Map<String ,Object> model = new HashMap<String, Object>();
         Date sDate=new SimpleDateFormat("dd/MM/yyyy").parse(startdate); 
         Date eDate=new SimpleDateFormat("dd/MM/yyyy").parse(enddate); 
         System.out.println("Cast start date "+sDate );
         System.out.println("Cast end date "+eDate );
         votagmBean.setStartdate(sDate);
         votagmBean.setEnddate(eDate);
          votagmBean.setBaseurl(votutility.getEasyCoopAgmURI());
           VotAgm votagm = prepareModelExternal(votagmBean);
            votAgmService.addAgmSetup(votagm );
            
        Activitylog activity = new Activitylog();
        activity.setEvent(135);
        activity.setAction("CRE-AGM");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + votagmBean.getDescription());
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(0);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        
	
             redurlpath = "redirect:/doFeedback.htm?message=Agm setup is successful&redirectURI=createAgm";   
            return new ModelAndView(redurlpath);
   
   }
      @RequestMapping(value="/saveAgmSetup2", method = RequestMethod.POST)
      public ModelAndView saveAgmSetup2(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) throws ParseException {
          String redurlpath = null;
                 String startdate= req.getParameter("startdate");
         System.out.println("the start date is "+ startdate);
          String enddate= req.getParameter("enddate");
         System.out.println("the end date is "+ enddate );
         System.out.println("start date from bean "+ votagmBean.getStartdate());
         System.out.println("End date from bean "+ votagmBean.getEnddate());
          //votagmBean.setCompanyid(id);
	 //Map<String ,Object> model = new HashMap<String, Object>();
         Date sDate=new SimpleDateFormat("dd/MM/yyyy").parse(startdate); 
         Date eDate=new SimpleDateFormat("dd/MM/yyyy").parse(enddate); 
         System.out.println("Cast start date "+sDate );
         System.out.println("Cast end date "+eDate );
         votagmBean.setStartdate(sDate);
         votagmBean.setEnddate(eDate);
           votagmBean.setBaseurl(votutility.getEasyCoopAgmURI());
           VotAgm votagm = prepareModel(votagmBean);
            votAgmService.addAgmSetup(votagm );
            //activity log
         Activitylog activity = new Activitylog();
        activity.setEvent(135);
        activity.setAction("CRE-AGM");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + votagmBean.getDescription());
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(0);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        //end of activity log
            
             redurlpath = "redirect:/doFeedback.htm?message=Agm setup is Successful&redirectURI=createAgm";   
            return new ModelAndView(redurlpath);
   
   }
     @RequestMapping(value="/saveCompanySetup2", method = RequestMethod.POST)
      public ModelAndView saveCompanySetup2(@ModelAttribute("votCompany")VotCompanyBean votcompanyBean, BindingResult result,HttpServletRequest req) {
          String redurlpath = null;
	 //Map<String ,Object> model = new HashMap<String, Object>();
          
           VotCompany votcompany = prepareVotCompanyModel(votcompanyBean);
            votCompanyService.addCompanySetup(votcompany );
         Activitylog activity = new Activitylog();
        activity.setEvent(133);
        activity.setAction("CRE-VOT-COMPANY");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + votcompanyBean.getCompanyname() );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(0);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
             redurlpath = "redirect:/easycoopAgm";   
            return new ModelAndView(redurlpath);
   
   }
     @RequestMapping(value="/memberImport", method = RequestMethod.GET)
      public ModelAndView memberImport(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          ///model.put("agmList", votAgmService.listEasycoopAgm());
         model.put("agmList", votAgmService.listActiveEasycoopAgm());
            return new ModelAndView("agmimport", model);
   
   }
      @RequestMapping(value="/saveEasycoopMemberimport", method = RequestMethod.POST)
      public ModelAndView saveEasycoopMemberimport(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 String redurlpath="";
         String agmid=req.getParameter("description");
         int agmid_int= Integer.parseInt(agmid);
         String loggeduser=req.getParameter("createdby");
          Map<String ,Object> model = new HashMap<String, Object>(); 
         if(agmhelperUTIL.moveMembers(agmid,loggeduser)== "2"){
            redurlpath = "redirect:/doFeedback.htm?message=You cannot import members for this Agm,Members have already Voted&redirectURI=memberImport";   
            return new ModelAndView(redurlpath);
            }
         else
         {
              
        Activitylog activity = new Activitylog();
        activity.setEvent(137);
        activity.setAction("IMP-VOT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + "easycoop member import" );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(agmid_int);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
               //String loggeduser=req.getParameter("createdby");
              
           redurlpath = "redirect:/doFeedback.htm?message=Members Imported Successfully&redirectURI=memberImport";   
            return new ModelAndView(redurlpath);
         }
   }
   //for agm set up approval
    @RequestMapping(value="/pendingAgmSetup", method = RequestMethod.GET)
      public ModelAndView pendingAgmSetup(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>();
          model.put("setupList",agmhelperUTIL.listAgmSetup());
            return new ModelAndView("setupapprv", model);
   
   }
       @RequestMapping(value = "/approveAgmSetup", method = RequestMethod.POST)
    public ModelAndView agmSetupApproval(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
        String redurlpath = null;
        String[] selectedAgm = req.getParameterValues("selectedAgm");
        String[] ids= req.getParameterValues("ids");
        String modifiedby=req.getParameter("modifiedby");
        String modifieddate= req.getParameter("modifieddate");
        String lastreminderdate= req.getParameter("lastreminderdate");
       
        System.out.println("the length of selected agm  " + selectedAgm.length);
        for (int i = 0; i < selectedAgm.length; i++) {
            agmhelperUTIL.UpdateAgmStatus(selectedAgm[i], modifiedby,modifieddate,lastreminderdate);
            
          //call service to create  voters credentials passing agmid
          /*
           * votersCredgenService.addVoterscred(Integer.parseInt(selectedAgm[i]));
           
           
           */
            //voterscredgenService.setTaskExecutor(taskExecutor);
           // voterscredgenService.addvoterscred(Integer.parseInt(ids[i]));
            voterscredgenService.gencred(Integer.parseInt(selectedAgm[i]));
            
             Activitylog activity = new Activitylog();
        activity.setEvent(138);
        activity.setAction("APRV-AGM-VOT-COM");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + "Agm approval " );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(Integer.parseInt(selectedAgm[i]));
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
           // helperUTIL.UpdateOriginalLoanGuarantor(uniqLoanCase, selectedGuarantor[i], replaced[i]);
        }
         redurlpath = "redirect:/doFeedback.htm?message= Agm Setup Approved &redirectURI=pendingAgmSetup";
        return new ModelAndView(redurlpath);
       }
     @RequestMapping(value="/editAgmCompany", method = RequestMethod.GET)
      public ModelAndView editExternalCompany(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          model.put("externalAgmList", agmhelperUTIL.listOfExternalAgmSetup());
            return new ModelAndView("editExternalCompany", model);
   
   }
      @RequestMapping(value="/editAgm", method = RequestMethod.GET)
      public ModelAndView editAllAgms(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
          model.put("agmList",  prepareListofVotAgmBean(votAgmService.listInactiveAgms()));
            return new ModelAndView("editAgmCompany", model);
   
   }
   @RequestMapping(value="/editExternalAgm", method = RequestMethod.GET)
      public ModelAndView editExternalView(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
         System.out.println("the selected id is "+votagmBean.getId());
          model.put("extSetup", agmhelperUTIL.getExternalAgmSetup(votagmBean.getId()));
            return new ModelAndView("editAgmSetup", model);
   
   }
  
     @RequestMapping(value="/editAgmSetup", method = RequestMethod.GET)
      public ModelAndView editAgmSetupView(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 Map<String ,Object> model = new HashMap<String, Object>(); 
         System.out.println("the selected id is "+votagmBean.getId());
          model.put("intSetup", agmhelperUTIL.getAgmSetup(votagmBean.getId()));
            return new ModelAndView("editAgmCompanySetup", model);
   
   }
  
      @RequestMapping(value="/updateInternalAgmSetup", method = RequestMethod.POST)
      public ModelAndView updateInternalAgm(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
          String redurlpath = null;
          String id=req.getParameter("id");
          String starttime=req.getParameter("starttime");
          String endtime=req.getParameter("endtime");
          String startdate=req.getParameter("startdate");
          String enddate=req.getParameter("enddate");
          String reminderfreq=req.getParameter("reminderfrequency");
          String agmyear=req.getParameter("agmyear");
          String agmname=req.getParameter("description");
          int agmid=Integer.parseInt(id);
	 //Map<String ,Object> model = new HashMap<String, Object>();
          
           agmhelperUTIL.updateInternalAgmSetup(startdate,enddate, starttime, endtime, reminderfreq, agmyear, agmname, agmid);
           Activitylog activity = new Activitylog();
        activity.setEvent(136);
        activity.setAction("EDT-AGM");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : "+ agmname );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(agmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
             redurlpath = "redirect:/doFeedback.htm?message=Agm Setup Updated Successfully &redirectURI=editAgm";   
            return new ModelAndView(redurlpath);
   
   }   
      
      
    @RequestMapping(value = "/updateAgmSetup", method = RequestMethod.POST)
    public ModelAndView updateAgmSetup(@ModelAttribute("votAgm") VotAgmBean votagmBean, BindingResult result, HttpServletRequest req) throws ParseException {
        String redurlpath = null;
        String id = req.getParameter("id");
        String starttime = req.getParameter("starttime");
        String endtime = req.getParameter("endtime");

        String startdate = req.getParameter("startdate");
        String enddate = req.getParameter("enddate");
        String reminderfreq = req.getParameter("reminderfrequency");
        String agmyear = req.getParameter("agmyear");
        String agmname = req.getParameter("description");
        String modifiedby = req.getParameter("modifiedby");
      
        int agmid = Integer.parseInt(id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
       
        agmhelperUTIL.updateAgmSetup(startdate, enddate, starttime, endtime, reminderfreq, agmyear, agmname, agmid, modifiedby, dateFormat.format(date));
      
     
        Activitylog activity = new Activitylog();
        activity.setEvent(136);
        activity.setAction("EDT-AGM");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + agmname);
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(agmid);

        AuditlogService as = new AuditlogService();
        as.create(activity);
        redurlpath = "redirect:/doFeedback.htm?message=Agm Setup Updated Successfully &redirectURI=editAgm";
        return new ModelAndView(redurlpath);

    }  
      
      
 @RequestMapping(value="/updateExternalAgmSetup", method = RequestMethod.POST)
      public ModelAndView updateExternalAgm(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
          String redurlpath = null;
          String id=req.getParameter("id");
          
          String agmname=req.getParameter("description");
         String companyrefid=req.getParameter("companyrefid");
          String companyname=req.getParameter("companyName");
          String company=req.getParameter("companyid");
          int agmid=Integer.parseInt(id);
          int refid=Integer.parseInt(companyrefid);
          int companyid=Integer.parseInt(company);
         
	 //Map<String ,Object> model = new HashMap<String, Object>();
          
            // agmhelperUTIL.updateExternalAgmSetup(startdate,enddate, starttime, endtime, reminderfreq, agmyear, agmname, agmid);
             agmhelperUTIL.updateVotCompany(refid, companyname, companyid);
               Activitylog activity = new Activitylog();
        activity.setEvent(134);
        activity.setAction("EDT-VOT-COMPANY");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " +  agmname );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(agmid);
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
             redurlpath = "redirect:/doFeedback.htm?message=Agm Setup Updated Successfully &redirectURI=editAgmCompany";   
            return new ModelAndView(redurlpath);
   
   }    

      @RequestMapping(value="/deleteAgmSetup", method = RequestMethod.GET)
      public ModelAndView deleteAgmSetup(@ModelAttribute("votAgm")VotAgmBean votagmBean, BindingResult result,HttpServletRequest req) {
	 String redurlpath = null;
          Map<String ,Object> model = new HashMap<String, Object>(); 
         System.out.println("the selected id is "+votagmBean.getId());
            agmhelperUTIL.deleteAgmSetup(votagmBean.getId());
          //model.put("intSetup", agmhelperUTIL.getInternalAgmSetup(votagmBean.getId()));
           redurlpath = "redirect:/doFeedback.htm?message=Agm Setup deleted successfully &redirectURI=editAgm";   
            return new ModelAndView(redurlpath);
   
   }
      
       @RequestMapping(value = "/activeAgms", method = RequestMethod.GET)
    public ModelAndView listAgmForDeactivation(@ModelAttribute("votAgm") VotAgmBean votagmBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("activeAgms", prepareListofVotAgmBean(votAgmService.listActiveAgms()));
        return new ModelAndView("activeAgms", model); 

    }
     
    @RequestMapping(value = "/deactivateAgms", method = RequestMethod.POST)
    public ModelAndView deactivateAgm (@ModelAttribute("votAgm") VotAgmBean votagmBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String redurlpath="";
         String createdby= req.getParameter("createdby");
         String[] agmid= req.getParameterValues("agmid");
         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           Date date = new Date();
           
          
           System.out.println("selected id " + agmid);
          // System.out.println("createdby value "+ );
           for(int i=0; i<agmid.length; i++){
         agmhelperUTIL.deactivateAgm( agmid[i],createdby,dateFormat.format(date));
           }
         //send mail to admin
          List<UserBean> mails= agmhelperUTIL.getAdminMails();
               String admin1= mails.get(0).getEmail().toString();
               String admin2= mails.get(1).getEmail().toString();
                System.out.println("the admin mail1 is "+ mails.get(0).getEmail().toString());
                System.out.println("the logged in User is  "+ createdby);
                     String mailBody="An Agm has been selected for deactivation";
                    if( createdby.equals(admin1)){
                     System.out.println("i am in this block");
                    
                       VotMailBean MB = votutility.getMailConfig();
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SMTP PASSWORD "+MB.getPassword());
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SSLORTLS "+MB.getSslortls());
                      MB.setSubject("AGM DEACTIVATION REQUEST");
                      MB.setToemail(admin2);
                      MB.setMailBody(mailBody);
                     votutility.sendMail(MB);
                       }else if(createdby.equals(admin2)){
                     
                      
                       VotMailBean MB = VotBeanMapperUtility.getMailConfig();
                      MB.setSubject("AGM DEACTIVATION REQUEST");
                      MB.setToemail(admin1);
                      MB.setMailBody(mailBody);
                       votutility.sendMail(MB);
                       }
        redurlpath = "redirect:/doFeedback.htm?message=Agm Deactivation has been sent for approval &redirectURI=activeAgms";
        return new ModelAndView(redurlpath);

    }

    
  //for internal source
   private VotAgm prepareModel(VotAgmBean voteAgmBean) {
        VotAgm votAgm = new VotAgm();
        votAgm.setId(voteAgmBean.getId());
         votAgm.setAgmyear(voteAgmBean.getAgmyear());
         votAgm.setBallotid(voteAgmBean.getBallotid());
         votAgm.setClosed(voteAgmBean.getClosed());
         votAgm.setBaseurl(voteAgmBean.getBaseurl());
         votAgm.setCompanyid(voteAgmBean.getCompanyid());
         votAgm.setCreatedate(new java.util.Date());
         votAgm.setEnddate(voteAgmBean.getEnddate());
         votAgm.setEndtime(voteAgmBean.getEndtime());
         votAgm.setImportsource("I");
         votAgm.setStartdate(voteAgmBean.getStartdate());
         votAgm.setStarttime(voteAgmBean.getStarttime());
         votAgm.setDescription(voteAgmBean.getDescription());
         votAgm.setCreatedby(voteAgmBean.getCreatedby());
         votAgm.setActive(voteAgmBean.getActive());
         votAgm.setReminderfrequency(voteAgmBean.getReminderfrequency());
         votAgm.setLastreminderdate(new java.util.Date());
        
        
        
       
       // memberExtraFieldGrp.setGroupid(); 
        //memberExtraFieldGrp.setGroupid();please remove the method i wrote and write yours, i just showed you how to 
        return votAgm;

    }  
   
   
   //for external source
   private VotAgm prepareModelExternal(VotAgmBean voteAgmBean) {
        VotAgm votAgm = new VotAgm();
        votAgm.setId(voteAgmBean.getId());
         votAgm.setAgmyear(voteAgmBean.getAgmyear());
         votAgm.setBallotid(voteAgmBean.getBallotid());
         votAgm.setClosed(voteAgmBean.getClosed());
         votAgm.setBaseurl(voteAgmBean.getBaseurl());
         votAgm.setCompanyid(voteAgmBean.getCompanyid());
         votAgm.setCreatedate(new java.util.Date());
         votAgm.setEnddate(voteAgmBean.getEnddate());
         votAgm.setEndtime(voteAgmBean.getEndtime());
         votAgm.setImportsource("E");
         votAgm.setStartdate(voteAgmBean.getStartdate());
         votAgm.setStarttime(voteAgmBean.getStarttime());
         votAgm.setDescription(voteAgmBean.getDescription());
         votAgm.setCreatedby(voteAgmBean.getCreatedby());
         votAgm.setActive(voteAgmBean.getActive());
         votAgm.setReminderfrequency(voteAgmBean.getReminderfrequency());
         votAgm.setLastreminderdate(new java.util.Date());
        
        
       
       // memberExtraFieldGrp.setGroupid(); 
        //memberExtraFieldGrp.setGroupid();please remove the method i wrote and write yours, i just showed you how to 
        return votAgm;

    }   
   private VotCompany prepareVotCompanyModel(VotCompanyBean voteCompanyBean) {
        VotCompany votCompany = new VotCompany();
        votCompany.setActive(voteCompanyBean.getActive());
        votCompany.setCompanyid(voteCompanyBean.getCompanyid());
        votCompany.setCompanyname(voteCompanyBean.getCompanyname());
        votCompany.setCompanyrefid(voteCompanyBean.getCompanyrefid());
       return votCompany;

    }   
    private List<VotBallottypesBean> prepareListofBallottypesBean(List<VotBallottypes> votBallottypes) {
        List<VotBallottypesBean> beans = null;
        System.out.println("i am here 2");
        if (votBallottypes != null && !votBallottypes.isEmpty()) {
            beans = new ArrayList<VotBallottypesBean>();
          VotBallottypesBean bean = null;

            for (VotBallottypes votBallottype : votBallottypes) {
                bean = new VotBallottypesBean();
               bean.setDescription(votBallottype.getDescription());
               bean.setId(votBallottype.getId());
              

                System.out.println("Description " + votBallottype.getDescription());
             
                beans.add(bean);
            }
        }

        return beans;
    }
 private List<VotAgmBean> prepareListofVotAgmBean(List<VotAgm> allvotAgm) {
        List<VotAgmBean> beans = null;
        System.out.println("i am here 2");
        if ((allvotAgm != null )&& (! allvotAgm.isEmpty())) {
            beans = new ArrayList<VotAgmBean>();
          VotAgmBean bean = null;

            for (VotAgm votagm : allvotAgm ) {
                bean = new VotAgmBean();
          bean.setId(votagm.getId());
          bean.setAgmyear(votagm.getAgmyear());
          bean.setBallotid( votagm.getBallotid());
          bean.setClosed( votagm.getClosed());
          bean.setBaseurl( votagm.getBaseurl());
          bean.setCompanyid( votagm.getCompanyid());
          bean.setCreatedate( votagm.getCreatedate());
          bean.setEnddate( votagm.getEnddate());
          bean.setEndtime( votagm.getEndtime());
          bean.setImportsource( votagm.getImportsource());
          bean.setStartdate( votagm.getStartdate());
          bean.setStarttime( votagm.getStarttime());
          bean.setDescription( votagm.getDescription());
          bean.setCreatedby( votagm.getCreatedby());
          bean.setActive( votagm.getActive());
          bean.setReminderfrequency( votagm.getReminderfrequency());
          bean.setLastreminderdate( votagm.getLastreminderdate());
          bean.setCompanyName(votCompanyService.getVotCompany( votagm.getCompanyid()).getCompanyname());
               
               
              

              
             
                beans.add(bean);
            }
        }

        return beans;
    }
    
}