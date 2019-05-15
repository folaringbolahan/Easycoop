/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.hp.controller;
import com.sift.gl.AuditlogService;
import com.sift.gl.NotificationService;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.hp.Hprequestutility;
import com.sift.hp.dao.HprequestImpl;
import com.sift.hp.model.Hpoperands;
import com.sift.hp.model.Hpproduct;
import com.sift.hp.model.Hprequestdetails;
import com.sift.hp.model.Hpvalidationrules;
import com.sift.hp.validator.HprequestValidator;
import com.sift.hp.validator.HpvalidationrulesValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
@Controller
@RequestMapping("hp/")
public class HpformasetupController{
   //@Autowired 
   private HprequestImpl hprequestService;
   @Autowired
    private NotificationService notificationService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private HpvalidationrulesValidator hpvalidationrulesValidator;
   private Hpvalidationrules hpvalidationrules;
   private String clientIpAddress;
   private String dmess = "";
   @Autowired
   private CurrentuserdisplayImpl user;
   
   @Value("${emailsubject.hpreqapprvemail}")
   private String emailsubjecthpreqapprvemail;
   @Value("${emailsubject.hppursalemail}")
   private String emailsubjecthppursalemail;
   
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          hpvalidationrulesValidator = new HpvalidationrulesValidator();
          hpvalidationrules = new Hpvalidationrules();
   
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setHprequestService(HprequestImpl hprequestService) {
      this.hprequestService = hprequestService;
      hpvalidationrules = new Hpvalidationrules();
   }
   

   @ModelAttribute("clientIpAddress")
	public String populateClientIpAddress(HttpServletRequest request) {
          return request.getRemoteAddr();
	}

   @RequestMapping(value = {"/hp_settings"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String account(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbranch = user.getCurruser().getBranch();
      clientIpAddress = request.getRemoteAddr();
      model.addAttribute("hpproducts", hprequestService.getHpproducts(dbranch,dcompany));
     model.addAttribute("hpoperands", hprequestService.getHpoperands());
     model.addAttribute("hpvalidationrules", hpvalidationrules);
     model.addAttribute("allhpvalidationrules", hprequestService.getAllhpvalidationrules(dbranch,dcompany));
    return "hp/hp_settings";
  }
   
   @ModelAttribute("hpproducts")
   public List<Hpproduct> getAllhpproducts() {
     // Delegate to service
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return hprequestService.getHpproducts(dbranch,dcompany);
   }
   
   @ModelAttribute("allhpvalidationrules")
   public List<Hpvalidationrules> getAllhpvalidationrules() {
     Integer  dcompany = user.getCurruser().getCompanyid();  
     Integer  dbranch = user.getCurruser().getBranch(); 
     return hprequestService.getAllhpvalidationrules(dbranch,dcompany);
   }
   
   @ModelAttribute("hpoperands")
   public List<Hpoperands> getAlloperands() {
     return hprequestService.getHpoperands();
   }
    
   
   @RequestMapping(value = {"hp/hp_validadd","/hp_validadd"},method=RequestMethod.POST)
   public ModelAndView addrule( @ModelAttribute("hpvalidationrules")Hpvalidationrules hpvalidationrules2, BindingResult result)  {
     String ddesc = "" ;
     Integer dacgrp = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     String dstr1 = hpvalidationrules2.getCode() ;
     String dstr2 = hpvalidationrules2.getDescription() ;
     String dstr3 = hpvalidationrules2.getProductcode() ;
     String dstr4 = hpvalidationrules2.getValidationtype() ;
     String dstr5 = hpvalidationrules2.getFormula() ;
     String dstr6 = hpvalidationrules2.getResultcond();
     
     hpvalidationrulesValidator.validate(hpvalidationrules2, result);
     
    
     
     String redurlpath="";
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
     return new ModelAndView("hp/hp_settings");
     }
     
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     
     dmess = "";
     String mailsubject = emailsubjecthpreqapprvemail;
    String retval = hprequestService.addRule(dstr1,dstr2,dstr3,dstr4,dstr5,dstr6,dbranch,dcompany,clientIpAddress , user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone());
     if (retval.contains("ok:")) {
         dmess = retval;
         redurlpath = "redirect:hp_settings.htm";
     }
     else {
         redurlpath = "redirect:/error.htm";
     }
     hpvalidationrules = new Hpvalidationrules();
     return new ModelAndView(redurlpath,"hpvalidationrules",hpvalidationrules);
   }
   
   @RequestMapping(value = "/hp_removesettings",method=RequestMethod.GET, params="id")
   public ModelAndView removesetting(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     String retval = hprequestService.removesetting(dcode,dbranch,dcompany);
     clientIpAddress = request.getRemoteAddr();
     String redurlpath;
     if (retval.equals("ok")) {
         auditlogcall(124,"HPVDL",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:hp_settings.htm";
     }
     else {
         auditlogcall(124,"HPVDL",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),"HP Settings Deletion Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:/error.htm";
        
     }
     return new ModelAndView(redurlpath);
   }
   
   
   @RequestMapping(value = "/hp_editsettings",method=RequestMethod.GET, params="id")
   public ModelAndView editaccountgroup(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     clientIpAddress = request.getRemoteAddr();
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     Hpvalidationrules hpvalidationrules2 = hprequestService.getHpvalidationrule(dcode,dbranch,dcompany) ;
     Map dmodel = new HashMap();
     //dmodel.put("accountgroupdet",accountgroupdet2);
     dmodel.put("hpproducts", hprequestService.getHpproducts(dbranch,dcompany));
     dmodel.put("hpoperands", hprequestService.getHpoperands());
     dmodel.put("hpvalidationrules", hpvalidationrules2);
     return new ModelAndView("hp/hp_pfeditsettings",dmodel);
    
   }
   
   @RequestMapping(value = "/hp_pfeditsettings",method=RequestMethod.POST)
   public ModelAndView pfeditaccountgroup( @ModelAttribute("hpvalidationrules")Hpvalidationrules hpvalidationrules2, BindingResult result)  {
     Integer dcode = hpvalidationrules2.getId();
     String ddesc = hpvalidationrules2.getDescription() ;
     String dformula = hpvalidationrules2.getFormula();
     String dprod = hpvalidationrules2.getProductcode() ;
     String dcond = hpvalidationrules2.getResultcond();
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     hpvalidationrulesValidator.validate(hpvalidationrules2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("hp/hp_pfeditsettings","hpvalidationrules",hpvalidationrules2);
     }
     String retval = hprequestService.edithpvalidationrules(dcode,ddesc,dformula,dprod,dcond,dbranch,dcompany);
     if (retval.equals("ok")) {
         auditlogcall(125,"HPVED",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:hp_settings.htm";
     }
     else {
         auditlogcall(125,"HPVED",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),"HP Validation Rule Edit Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:/error.htm";
     }
     //accountgroupdet = new Accountgroupdetail();
     return new ModelAndView(redurlpath,"hpvalidationrules",hpvalidationrules2);
   }
   
   public void auditlogcall( int eventid,String eventactioncode,String ipaddr, String username,String timezone,String actionitem,String result,int branch,int company) {
       Activitylog ent;
       ent = new Activitylog();
       String theerrmess;        
       ent.setBranchid(branch); 
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
    }
  
}
