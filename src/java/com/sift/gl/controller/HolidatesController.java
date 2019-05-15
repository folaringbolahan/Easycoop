/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;
import com.sift.cli.AuditlogJerseyClient;
import com.sift.gl.AuditlogService;
import com.sift.gl.validator.HolidatesValidator;
import com.sift.gl.dao.HolidatesImpl;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Holidatesdet;
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
public class HolidatesController{
   //@Autowired 
   private HolidatesImpl holidatesService;
   ///private CurrentuserdisplayImpl currentuserdisplayService;
   //@Autowired
   private HolidatesValidator holidatesValidator;
   private Holidatesdet holidatesdet;
   @Autowired
   private CurrentuserdisplayImpl user;
   private String clientIpAddress;
   @InitBinder
   public void initBinder(WebDataBinder binder) {
          holidatesValidator = new HolidatesValidator();
          //user = new Users();
   }
   //@Autowired
   public void setUser(CurrentuserdisplayImpl user) {
      this.user.setdCurruser(user.getCurruser()); //= user;
   }
   
   public void setHolidatesService(HolidatesImpl holidatesService) {
      this.holidatesService = holidatesService;
      holidatesdet = new Holidatesdet();
   }
   
   @RequestMapping(value = {"gl/gl_holidates","/gl_holidates"},method=RequestMethod.POST)
   public ModelAndView adddetails( @ModelAttribute("holidatesdet")Holidatesdet holidatesdet2, BindingResult result)  {
     String ddesc = "" ;
     String dclsid = "";
     String drep = "";
     Integer dgrpid = 0;
     Integer dbranch = 0;
     Integer dcompany = 0;
     holidatesdet = holidatesdet2;
     holidatesValidator.validate(holidatesdet2, result);
     
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      //  return "redirect:addaccountgroup.htm";
      // return new ModelAndView("gl_accountgrouplist","accountgroupdet",accountgroupdet2);
      return new ModelAndView("gl/gl_holidayslist");
     }
     
      ddesc = holidatesdet2.getDescription() ;
      dclsid = holidatesdet2.getHolidate();
      dgrpid = holidatesdet2.getRecurring();
     dbranch = user.getCurruser().getBranch();
     dcompany = user.getCurruser().getCompanyid();
     
     String retval = holidatesService.addHolidate(ddesc,dclsid,dgrpid,dbranch,dcompany);
     redurlpath = "redirect:gl_holidayslist.htm";
    /* if (retval.equals("ok")) {
         auditlogcall(4,"GLHOS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),retval.toUpperCase(),dbranch,dcompany);
         redurlpath = "redirect:gl_holidateslist.htm";
     }
     else {
         auditlogcall(4,"GLHOS",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dgrpid.toString(),"Creating Holiday Failed",dbranch,dcompany);
         redurlpath = "redirect:/error.htm";
     }*/
     holidatesdet = new Holidatesdet();
     //return redurlpath;
     return new ModelAndView(redurlpath,"holidatesdet",holidatesdet2);
   }
   
   @RequestMapping(value = "/gl_removeholiday",method=RequestMethod.GET, params="id")
   public ModelAndView removeholiday(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     String retval = holidatesService.removeHolidate(dcode,dbranch,dcompany);
     clientIpAddress = request.getRemoteAddr();
     String redurlpath;
     redurlpath = "redirect:gl_holidayslist.htm";
     /*
     if (retval.equals("ok")) {
         auditlogcall(6,"GLHOR",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_holidayslist.htm";
     }
     else {
         auditlogcall(6,"GLHOR",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),dcode.toString(),"Holiday Deletion Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     * */
     return new ModelAndView(redurlpath);
   }
   
   @RequestMapping(value = "/gl_editholidays",method=RequestMethod.GET, params="id")
   public ModelAndView editholidates(HttpServletRequest request,HttpServletResponse response) throws Exception {
     Integer dcode = ServletRequestUtils.getRequiredIntParameter(request, "id");
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbranch = user.getCurruser().getBranch();
     Holidatesdet holidatesdet2 = holidatesService.getHolidate(dcode,dbranch,dcompany) ;
     clientIpAddress = request.getRemoteAddr();
     Map dmodel = new HashMap();
     dmodel.put("holidatesdet",holidatesdet2);
     return new ModelAndView("gl/gl_pfeditholidays",dmodel);
    }
   
   @RequestMapping(value = "/gl_pfeditholidays",method=RequestMethod.POST)
   public ModelAndView pfeditholidates( @ModelAttribute("holidatesdet")Holidatesdet holidatesdet2, BindingResult result)  {
     String ddate = holidatesdet2.getHolidate();//ServletRequestUtils.getRequiredStringParameter(request, "code");
     String ddesc = holidatesdet2.getDescription();//ServletRequestUtils.getRequiredStringParameter(request, "description"); 
     Integer drec = holidatesdet2.getRecurring() ;
     Integer did = holidatesdet2.getId() ;
     Integer dcompany = user.getCurruser().getCompanyid();
     Integer dbrid = user.getCurruser().getBranch();
     holidatesdet = holidatesdet2;
     holidatesValidator.validate(holidatesdet2, result);
     String redurlpath;
     if(result.hasErrors()==true) {
             List<FieldError> lerr = result.getFieldErrors();
                        for (FieldError err: lerr) {
                                System.out.println(err);                
                        }
                      return new ModelAndView("gl/gl_pfeditholidays","holidatesdet",holidatesdet2);
     }
     String retval = holidatesService.editHolidate(did,ddesc,ddate,drec,dbrid,dcompany);
    redurlpath = "redirect:gl_holidayslist.htm";
     /*  if (retval.equals("ok")) {
         auditlogcall(15,"GLHOE",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),did.toString(),retval.toUpperCase(),user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:gl_holidayslist.htm";
     }
     else {
         auditlogcall(15,"GLHOE",clientIpAddress,user.getCurruser().getUserId(),user.getCurrusercompany().getTimezone(),did.toString(),"Holidates Edit Failed",user.getCurruser().getBranch(),dcompany);
         redurlpath = "redirect:includes/error.htm";
     }
     */
     holidatesdet = new Holidatesdet();
     return new ModelAndView(redurlpath);
   }
   
   @RequestMapping(value = {"gl_holidayslist"}, method = {RequestMethod.GET,RequestMethod.POST})
   public String holidateslist(ModelMap model,HttpServletRequest request)  {
       Integer dcompany = user.getCurruser().getCompanyid();
       Integer dbr = user.getCurruser().getBranch();
       clientIpAddress = request.getRemoteAddr();
     List<Holidatesdet> holidates = holidatesService.getHolidates(dbr,dcompany) ;
     model.addAttribute("holidays", holidatesService.getHolidates(dbr,dcompany));
     model.addAttribute("holidatesdet", holidatesdet);
    return "gl/gl_holidayslist";
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
