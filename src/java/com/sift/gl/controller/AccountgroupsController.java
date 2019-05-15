/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.AccountgroupValidator;
import com.sift.gl.dao.AccountgroupImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Accountgroupdetail;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Users;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("gl/")
public class AccountgroupsController{
   //@Autowired 
   private AccountgroupImpl accountgroupService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private AccountgroupValidator accountgroupValidator;
   private Accountgroupdetail accountgroupdet;
   private String clientIpAddress;
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountgroupValidator = new AccountgroupValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setAccountgroupService(AccountgroupImpl accountgroupService) {
      this.accountgroupService = accountgroupService;
      accountgroupdet = new Accountgroupdetail();
   }
   
  /// public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
  ///    this.currentuserdisplayService = currentuserdisplayService;
  /// }
   @RequestMapping(value = {"gl/gl_accountgroups","/gl_accountgroups"},method=RequestMethod.POST)
   public ModelAndView addaccountgroup( @ModelAttribute("accountgroupdet")Accountgroupdetail accountgroupdet2, BindingResult result)  {
     //String dcode = accountgroupdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dclsid = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     Integer dgrpid = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     
     accountgroupdet = accountgroupdet2;
     accountgroupValidator.validate(accountgroupdet2, result);
     
    
     
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      //  return "redirect:addaccountgroup.htm";
      // return new ModelAndView("gl_accountgrouplist","accountgroupdet",accountgroupdet2);
      return new ModelAndView("gl/gl_accountgrouplist");
     }
     
      ddesc = accountgroupdet2.getDescription() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     dclsid = accountgroupdet2.getClassId();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     //drep = accountgroupdet2.getReportGroup();
     dgrpid = accountgroupdet2.getAcGrpId();
     if (dclsid.equalsIgnoreCase("I")) {
         drep = "A";
     }
     else if (dclsid.equalsIgnoreCase("E")) {
         drep = "B";
     }
     
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     System.out.println("The company code is - " + dcompany);
     
     String retval = accountgroupService.addAccountgroup(ddesc,dgrpid,dclsid,drep,dcompany);
     //redurlpath = "redirect:accountgrouplist.htm";
     if (retval.equals("ok")) {
         auditlogcall(4,"GLAGS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),retval.toUpperCase(),dbranch,dcompany);
         redurlpath = "redirect:gl_accountgrouplist.htm";
     }
     else {
         auditlogcall(4,"GLAGS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),"Creating Account Group Failed",dbranch,dcompany);
         redurlpath = "redirect:/error.htm";
     }
     accountgroupdet = new Accountgroupdetail();
     //return redurlpath;
     return new ModelAndView(redurlpath,"accountgroupdet",accountgroupdet2);
   }
   
   @RequestMapping(value = "/gl_removeaccountgroup",method=RequestMethod.GET, params="id")
   public ModelAndView removeaccountgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     String retval = accountgroupService.removeAccountgroup(dcode,dcompany);
     clientIpAddress = request.getRemoteAddr();
     String redurlpath;
     if (retval.equals("ok")) {
         auditlogcall(6,"GLDAG",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_accountgrouplist.htm";
     }
     else {
         auditlogcall(6,"GLDAG",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),"Account Group Deletion Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
        
     }
     return new ModelAndView(redurlpath);
   }
   @RequestMapping(value = "/gl_editaccountgroup",method=RequestMethod.GET, params="id")
   public ModelAndView editaccountgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     clientIpAddress = request.getRemoteAddr();
     //String ddesc = ServletRequestUtils.getRequiredStringParameter(request, "nm");
     //String dloc = ServletRequestUtils.getRequiredStringParameter(request, "loc");
     //String dacno = ServletRequestUtils.getRequiredStringParameter(request, "acno");
     Integer dcompany = user.getCurruser().getCompanyid();
     Accountgroupdetail accountgroupdet2 = accountgroupService.getAccountgroup(dcode,dcompany) ;
    
    /** List<Cadredetails> cadres = accountgroupService.getCadres() ;
     List<Salaryleveldetails> salarylevels = accountgroupService.getSalarylevels() ; */
     List<Accountgrpclassdetails> accountclasses = accountgroupService.getAccountgrpclasses();
     List<Accountgrprepdetails> accreportgroups = accountgroupService.getAccountgrpreps() ; 
     Map dmodel = new HashMap();
     dmodel.put("accountgroupdet",accountgroupdet2);
     dmodel.put("accountclasses", accountclasses);
   ///  dmodel.put("accreportgroups", accreportgroups);
     return new ModelAndView("gl/gl_pfeditaccountgroup",dmodel);
     //return new ModelAndView("pfeditaccountgroup","accountgroupdet",accountgroupdet2);
  
   }
   
   @RequestMapping(value = "/gl_pfeditaccountgroup",method=RequestMethod.POST)
   public ModelAndView pfeditaccountgroup( @ModelAttribute("accountgroupdet")Accountgroupdetail accountgroupdet2, BindingResult result)  {
     Integer dcode = accountgroupdet2.getGroupId();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String ddesc = accountgroupdet2.getDescription() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dclass = accountgroupdet2.getClassId();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drepgrp = "";//accountgroupdet2.getReportGroup();
     if (dclass.equalsIgnoreCase("I")) {
         drepgrp = "A";
     }
     else if (dclass.equalsIgnoreCase("E")) {
         drepgrp = "B";
     }
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dgrpid = accountgroupdet2.getAcGrpId();
     accountgroupdet = accountgroupdet2;
     accountgroupValidator.validate(accountgroupdet2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("gl/gl_pfeditaccountgroup","accountgroupdet",accountgroupdet2);
     }
     String retval = accountgroupService.editAccountgroup(dcode,dgrpid,ddesc,dclass,drepgrp,dcompany);
     if (retval.equals("ok")) {
         auditlogcall(5,"GLEAG",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_accountgrouplist.htm";
     }
     else {
         auditlogcall(5,"GLEAG",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),"Account Group Edit Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     accountgroupdet = new Accountgroupdetail();
     return new ModelAndView(redurlpath,"accountgroupdet",accountgroupdet2);
   }
   
   @RequestMapping(value = {"gl_accountgrouplist"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String accountgrouplist(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       clientIpAddress = request.getRemoteAddr();
     List<Accountgroupdetail> accountgroups = accountgroupService.getAccountgroups(dcompany) ;
     //Accountgroupsdetails statdet = new Accountgroupsdetails();
     //model.addAttribute("accountgroups", accountgroups);
     model.addAttribute("accountgroups", accountgroupService.getAccountgroups(dcompany));
     model.addAttribute("accountgroupdet", accountgroupdet);
     //
     //accountclasses = accountgroupService.getAccountgrpclasses();
     //List<Accountgrprepdetails> accreportgroups = accountgroupService.getAccountgrpreps() ; 
     
     model.addAttribute("accountclasses", accountgroupService.getAccountgrpclasses());
   //////  model.addAttribute("accreportgroups", accountgroupService.getAccountgrpreps()); 
     //
    /* String dbranch = user.getBranch();
     String dcompany = user.getCompanyid();  
     String daccess = user.getAccessLevel();
     
     model.addAttribute("usermodules", currentuserdisplayService.getModules(daccess,dbranch,dcompany));
     model.addAttribute("usermenus", currentuserdisplayService.getModulemenus(daccess,dbranch,dcompany));
     */
     return "gl/gl_accountgrouplist";
     //return new ModelAndView("accountgroupList", "command", accountgroups);
   }

   @ModelAttribute("accountclasses")
   public List<Accountgrpclassdetails> getAllAccountclasses() {
     // Delegate to service
     return accountgroupService.getAccountgrpclasses();
   }
   @ModelAttribute("accreportgroups")
   public List<Accountgrprepdetails> getAllAccreportgroups() {
     return accountgroupService.getAccountgrpreps();
   }
   @ModelAttribute("accountgroups")
   public List<Accountgroupdetail> getAllAccountgroups() {
     Integer  dcompany = user.getCurruser().getCompanyid();
     return accountgroupService.getAccountgroups(dcompany);
   }
   
   public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
       //ent.setEvent(1);
       //ent.setAction("GLGAC");
       ent.setEvent(eventid);
       ent.setAction(eventactioncode);
       ent.setCompanyid(company); 
       ent.setUsername(username);
       ent.setTimezone(timezone); 
       ent.setDescription(""); 
       ent.setIpaddress(ipaddr);
       ent.setActionItem(actionitem);
       ent.setActionResult(result); 
       AuditlogService cliserv = new AuditlogService();
       String respo = cliserv.create(ent);
       System.out.println(respo);
       //theerrmess= cliserv.gettheerrmess();
       //System.out.println(theerrmess);
    }
}
