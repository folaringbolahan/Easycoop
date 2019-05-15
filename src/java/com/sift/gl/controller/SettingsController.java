/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.SettingsValidator;
import com.sift.gl.dao.SettingsImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Accountgrprepdetails;
import com.sift.gl.model.Accountgrpclassdetails;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Settingsdet;
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
public class SettingsController{
   //@Autowired 
   private SettingsImpl settingsService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private SettingsValidator settingsValidator;
   private Settingsdet settingsdet;
   List<Accountgrpclassdetails> accountclasses;
   List<Accountgrprepdetails> accreportgroups;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String clientIpAddress;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          settingsValidator = new SettingsValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setSettingsService(SettingsImpl settingsService) {
      this.settingsService = settingsService;
      settingsdet = new Settingsdet();
   }
   
  
   @RequestMapping(value = "/gl_editsettings",method=RequestMethod.GET, params="ds")
   public ModelAndView editsettings(HttpServletRequest request,HttpServletResponse response) throws Exception {
     String dcode = ServletRequestUtils.getRequiredStringParameter(request, "ds");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     Settingsdet settingsdet2 = settingsService.getSetting(dcode,dbranch,dcompany) ;
     clientIpAddress = request.getRemoteAddr();
     Map dmodel = new HashMap();
     dmodel.put("settingsdet",settingsdet2);
     return new ModelAndView("gl/gl_pfeditsettings",dmodel);
    }
   
   @RequestMapping(value = "/gl_pfeditsettings",method=RequestMethod.POST)
   public ModelAndView pfeditsettings( @ModelAttribute("settingsdet")Settingsdet settingsdet2, BindingResult result)  {
     String dset = settingsdet2.getSetting();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String dval = settingsdet2.getSettingval() ;//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbrid = user.getCurruser().getBranch();
     settingsdet = settingsdet2;
     settingsValidator.validate(settingsdet2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("gl/gl_pfeditsettings","settingsdet",settingsdet2);
     }
     String retval = settingsService.editSetting(dset,dval,dbrid,dcompany);
     if (retval.equals("ok")) {
         auditlogcall(15,"GLSET",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dset,retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_settingslist.htm";
     }
     else {
         auditlogcall(15,"GLSET",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dset,"Settings Edit Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     settingsdet = new Settingsdet();
     return new ModelAndView(redurlpath);
   }
   
   @RequestMapping(value = {"gl_settingslist"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String settingslist(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbr = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
     List<Settingsdet> settings = settingsService.getSettings(dbr,dcompany) ;
     model.addAttribute("settings", settingsService.getSettings(dbr,dcompany));
     model.addAttribute("settings", settings);
     model.addAttribute("accountclasses", settingsService.getAccountgrpclasses());
     //model.addAttribute("accreportgroups", settingsService.getAccountgrpreps()); 
     return "gl/gl_settingslist";
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
