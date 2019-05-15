/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.AccountstructValidator;
import com.sift.gl.dao.AccountstructImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountsegmentdetails;
import com.sift.gl.model.Accountstructuresdetails;
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
public class AccountstructController{
   //@Autowired 
   private AccountstructImpl accountstructService;
   //@Autowired
   private AccountstructValidator accountstructValidator;
   private Accountstructuresdetails accountstructdet;
   List<Accountsegmentdetails> accsegs;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String clientIpAddress;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          accountstructValidator = new AccountstructValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setAccountstructService(AccountstructImpl accountstructService) {
      this.accountstructService = accountstructService;
      accountstructdet = new Accountstructuresdetails();
   }
   
   
   @RequestMapping(value = {"gl/gl_accountstructs","/gl_accountstructs"},method=RequestMethod.POST)
   public ModelAndView addaccountstruct( @ModelAttribute("accountstructdet")Accountstructuresdetails accountstructdet2, BindingResult result)  {
     //String dcode = accountstructdet2.getCode();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String ddesc = "" ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     String dcode = "";//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String drep = "";
     int dgrpid = 0;
     int dbranch = 0;
     int dcompany = 0;
     
     accountstructdet = accountstructdet2;
     accountstructValidator.validate(accountstructdet2, result);
     
    
     
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      //  return "redirect:addaccountstruct.htm";
      // return new ModelAndView("gl_accountstructlist","accountstructdet",accountstructdet2);
      return new ModelAndView("gl/gl_accountstructlist");
     }
     
     dcode = accountstructdet2.getStructurecode();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     ddesc = accountstructdet2.getDescription() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
    // int did = accountstructdet2.getAcStrId();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     //String drepgrp = accountstructdet2.getReportGroup();
     int dseg1 = accountstructdet2.getSeg1();
     int dseg2 = accountstructdet2.getSeg2();
     int dseg3 = accountstructdet2.getSeg3();
     int dseg4 = accountstructdet2.getSeg4();
     int dseg5 = accountstructdet2.getSeg5();
     int dseg6 = accountstructdet2.getSeg6();
     int dseg7 = accountstructdet2.getSeg7();
     int dseg8 = accountstructdet2.getSeg8();
     int dseg9 = accountstructdet2.getSeg9();
     int dseg10 = accountstructdet2.getSeg10();
     String ddelim = accountstructdet2.getDelimiter();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     //System.out.println("The company code is - " + dcompany);
     
     String retval = accountstructService.addAccountstruct(ddesc,dcode,ddelim,dseg1,dseg2,dseg3,dseg4,dseg5,dseg6,dseg7,dseg8,dseg9,dseg10,dcompany);
     //redurlpath = "redirect:accountstructlist.htm";
     if (retval.equals("ok")) {
         auditlogcall(8,"GLASC",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,retval.toUpperCase(),dbranch,dcompany);
         redurlpath = "redirect:gl_accountstructlist.htm";
     }
     else {
         auditlogcall(8,"GLASC",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,"Account Structure Creation Failed",dbranch,dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     accountstructdet = new Accountstructuresdetails();
     //return redurlpath;
     return new ModelAndView(redurlpath,"accountstructdet",accountstructdet2);
   }
   
   @RequestMapping(value = "/gl_removeaccountstruct",method=RequestMethod.GET, params="id")
   public ModelAndView removeaccountstruct(HttpServletRequest request,HttpServletResponse response) throws Exception {
     int dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     int dcompany = user.getCurruser().getCompanyid();
     String retval = accountstructService.removeAccountstruct(dcode,dcompany);
     String redurlpath;
     clientIpAddress = request.getRemoteAddr();
     if (retval.equals("ok")) {
         auditlogcall(10,"GLASD",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),Integer.toString(dcode),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_accountstructlist.htm";
     }
     else {
         auditlogcall(10,"GLASD",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),Integer.toString(dcode),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     return new ModelAndView(redurlpath);
   }
   @RequestMapping(value = "/gl_editaccountstruct",method=RequestMethod.GET, params="id")
   public ModelAndView editaccountstruct(HttpServletRequest request,HttpServletResponse response) throws Exception {
     int dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     int dcompany = user.getCurruser().getCompanyid();
     //String ddesc = ServletRequestUtils.getRequiredStringParameter(request, "nm");
     //String dloc = ServletRequestUtils.getRequiredStringParameter(request, "loc");
     //String dacno = ServletRequestUtils.getRequiredStringParameter(request, "acno");
     Accountstructuresdetails accountstructdet2 = accountstructService.getAccountstruct(dcode,dcompany) ;
    clientIpAddress = request.getRemoteAddr();
    /** List<Cadredetails> cadres = accountstructService.getCadres() ;
     List<Salaryleveldetails> salarylevels = accountstructService.getSalarylevels() ; */
     List<Accountsegmentdetails> accsegs = accountstructService.getAccountsegs(user.getCurruser().getCompanyid()) ; 
     Map dmodel = new HashMap();
     dmodel.put("accountstructdet",accountstructdet2);
     dmodel.put("accsegs", accsegs);
     return new ModelAndView("gl/gl_pfeditaccountstruct",dmodel);
     //return new ModelAndView("pfeditaccountstruct","accountstructdet",accountstructdet2);
  
   }
   
   @RequestMapping(value = "/gl_pfeditaccountstruct",method=RequestMethod.POST)
   public ModelAndView pfeditaccountstruct( @ModelAttribute("accountstructdet")Accountstructuresdetails accountstructdet2, BindingResult result)  {
     String dcode = accountstructdet2.getStructurecode();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String ddesc = accountstructdet2.getDescription() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     int did = accountstructdet2.getAcStrId();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     //String drepgrp = accountstructdet2.getReportGroup();
     int dseg1 = accountstructdet2.getSeg1();
     int dseg2 = accountstructdet2.getSeg2();
     int dseg3 = accountstructdet2.getSeg3();
     int dseg4 = accountstructdet2.getSeg4();
     int dseg5 = accountstructdet2.getSeg5();
     int dseg6 = accountstructdet2.getSeg6();
     int dseg7 = accountstructdet2.getSeg7();
     int dseg8 = accountstructdet2.getSeg8();
     int dseg9 = accountstructdet2.getSeg9();
     int dseg10 = accountstructdet2.getSeg10();
     String ddelim = accountstructdet2.getDelimiter();
     
     accountstructdet = accountstructdet2;
     accountstructValidator.validate(accountstructdet2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("gl/gl_pfeditaccountstruct","accountstructdet",accountstructdet2);
     }
     String retval = accountstructService.editAccountstruct(did,ddesc,dcode,ddelim,dseg1,dseg2,dseg3,dseg4,dseg5,dseg6,dseg7,dseg8,dseg9,dseg10,this.user.getCurruser().getCompanyid());
     if (retval.equals("ok")) {
         auditlogcall(9,"GLASE",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,retval.toUpperCase(),user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
         redurlpath = "redirect:gl_accountstructlist.htm";
     }
     else {
         auditlogcall(9,"GLASE",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode,"Edit Account Structure Failed",user.getCurruser().getBranch(),user.getCurruser().getCompanyid());
         redurlpath = "redirect:includes/error.htm";
     }
     accountstructdet = new Accountstructuresdetails();
     return new ModelAndView(redurlpath,"accountstructdet",accountstructdet2);
   }
   
   @RequestMapping(value = "gl_accountstructlist", method = {RequestMethod.GET,RequestMethod.POST})
   public String accountstructlist(ModelMap model,HttpServletRequest request)  {
      int dcompany = user.getCurruser().getCompanyid();
     List<Accountstructuresdetails> accountstructs = accountstructService.getAccountstructs(dcompany) ;
     //Accountstructsdetails statdet = new Accountstructsdetails();
     //model.addAttribute("accountstructs", accountstructs);
     model.addAttribute("accountstructs", accountstructService.getAccountstructs(dcompany));
     model.addAttribute("accountstructdet", accountstructdet);
     clientIpAddress = request.getRemoteAddr();
     //
     //accountclasses = accountstructService.getAccountgrpclasses();
     //List<Accountgrprepdetails> accreportgroups = accountstructService.getAccountgrpreps() ; 
     
     model.addAttribute("accsegs", accountstructService.getAccountsegs(user.getCurruser().getCompanyid())); 
     return "gl/gl_accountstructlist";
     //return new ModelAndView("accountstructList", "command", accountstructs);
   }

   @ModelAttribute("accsegs")
   public List<Accountsegmentdetails> getAllAccsegs(Integer dcoy) {
     return accountstructService.getAccountsegs(dcoy);
   }
   @ModelAttribute("accountstructs")
   public List<Accountstructuresdetails> getAllAccountstructs() {
     Integer dcompany = user.getCurruser().getCompanyid();
     return accountstructService.getAccountstructs(dcompany);
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
